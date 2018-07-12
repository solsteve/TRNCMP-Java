// ====================================================================== BEGIN FILE =====
// **                              U D P T R A N S P O R T                              **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2018, Stephen W. Soliday                                           **
// **                      stephen.soliday@trncmp.org                                   **
// **                      http://research.trncmp.org                                   **
// **                                                                                   **
// **  -------------------------------------------------------------------------------  **
// **                                                                                   **
// **  This program is free software: you can redistribute it and/or modify it under    **
// **  the terms of the GNU General Public License as published by the Free Software    **
// **  Foundation, either version 3 of the License, or (at your option)                 **
// **  any later version.                                                               **
// **                                                                                   **
// **  This program is distributed in the hope that it will be useful, but WITHOUT      **
// **  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS    **
// **  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.   **
// **                                                                                   **
// **  You should have received a copy of the GNU General Public License along with     **
// **  this program. If not, see <http://www.gnu.org/licenses/>.                        **
// **                                                                                   **
// ----- Modification History ------------------------------------------------------------
/**
 * @file UDPTransport.java
 *  
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date   2018-05-20
 */
// =======================================================================================

package org.trncmp.lib.net;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import org.apache.log4j.*;
import java.nio.charset.*;

// =======================================================================================
public class UDPTransport {
  // -------------------------------------------------------------------------------------
  protected static final Logger logger = LogManager.getRootLogger();

  protected static final Charset CHAR_SET = StandardCharsets.UTF_8;

  
  // =====================================================================================
  public static class UData {
  // -------------------------------------------------------------------------------------
    public int    len  = 0;
    public byte[] data = null;

    // ===================================================================================
    public UData() {
      // ---------------------------------------------------------------------------------
    }

    // ===================================================================================
    public UData( int n ) {
      // ---------------------------------------------------------------------------------
      data = new byte[n];
      len  = n;
    }

    // ===================================================================================
    public UData( byte[] d, int n ) {
      // ---------------------------------------------------------------------------------
      this(n);
      for ( int i=0; i<n; i++ ) { data[i] = d[i]; }
    }

     // ===================================================================================
    public UData( String str ) {
      // ---------------------------------------------------------------------------------
      parse( str );
    }

   // ===================================================================================
    public String toString( ) {
      // ---------------------------------------------------------------------------------
      return new String( data, 0, len, CHAR_SET );
    }

   // ===================================================================================
    public void parse( String str ) {
      // ---------------------------------------------------------------------------------
      data = str.getBytes( CHAR_SET );
      len  = str.length();
    }


  } // end class UDPTransport.UData






  // =====================================================================================
  /** @brief Receiver.
   *
   * Thread to listen for incoming UDP messages and store them as strings in a
   * concurrent linked queue of Strings.
   */
  // -------------------------------------------------------------------------------------
  public static class Receiver extends Thread {
    // -----------------------------------------------------------------------------------
    protected ConcurrentLinkedQueue<UData> queue      = null;
    protected SocketAddress                bind_addr  = null;
    protected int                          msg_length = 0;
    protected boolean                      running    = false;
    protected int                          time_out   = 500;
    
    // ===================================================================================
    /** @brief Constructor.
     *  @param hostname host interface to bind the listener to.
     *  @param port     UDP port to bind the listener to.
     *  @param ml       Maximum message length in bytes.
     *  @param to       Socket Time out in milliseconds.
     */
    // -----------------------------------------------------------------------------------
    public Receiver( String hostname, int port, int ml, int to ) {
      // ---------------------------------------------------------------------------------
      queue      = new ConcurrentLinkedQueue<UData>();
      msg_length = ml;
      time_out   = to;
      bind_addr  = new InetSocketAddress( hostname, port );

      logger.debug( "UDP.receiver("+hostname+":"+port+") created" );
    }

    
    // ===================================================================================
    /** @brief Halt.
     *
     *  Mark this thread to stop execution. The maximum wait should be the set time out.
     */
    // -----------------------------------------------------------------------------------
    public void halt( ) {
      // ---------------------------------------------------------------------------------
      running = false;
      logger.debug( "UDP.receiver thread marked for stop" );
     try {
        this.join();
      } catch( InterruptedException e ) {
      }
    }

    
    // ===================================================================================
    /** @brief Run.
     *
     *  Thread run function. Listen for incoming packets. Add them to the queue.
     *  Time out causes a yield befor looping back to listen again.
     */
    // -----------------------------------------------------------------------------------
    public void run( ) {
      // ---------------------------------------------------------------------------------
      byte[]         buf    = new byte[msg_length];
      DatagramPacket packet = new DatagramPacket(buf, buf.length);
      DatagramSocket socket = null;

      logger.debug( "UDP.receiver thread started" );
      
      try {
        socket = new DatagramSocket( bind_addr );
        socket.setSoTimeout( time_out );
        running = true;
       
        while( running ) {
          try {
            socket.receive(packet);
            int    nr    = packet.getLength();
            byte[] inbuf = packet.getData();
            queue.add( new UData( inbuf, nr ) );
            //logger.debug( "recv udp: "+nr+" bytes" );
           } catch( IOException e1 ) {
            this.yield();
          }
        }

      } catch( SocketException e2 ) {
        logger.error( "UDP.Receiver: "+e2.toString() );
      }
      
      if ( null != socket ) {
        socket.close();  
      }
 
      logger.debug( "UDP.receiver thread stopped" );
   }

    
    // ===================================================================================
    /** @brief Get.
     *  @return next String off of the queue or NULL if the queue is empty.
     *
     */
    // -----------------------------------------------------------------------------------
    public UData get( ) {
      // ---------------------------------------------------------------------------------
      return queue.poll();
    }

    
    // ===================================================================================
    /** @brief Is Empty.
     *  @return true if the queue is empty.
     */
    // -----------------------------------------------------------------------------------
    public boolean isEmpty( ) {
      // ---------------------------------------------------------------------------------
      return queue.isEmpty();
    }

  } // end class UDPTransport.Receiver


  // =====================================================================================
  /** @brief Sender.
   * Thread to listen to a concurrent linked queue of Strings and send out UDP packets.
   */
  // -------------------------------------------------------------------------------------
  public static class Sender extends Thread {
    // -----------------------------------------------------------------------------------
    protected ConcurrentLinkedQueue<UData> queue      = null;
    protected SocketAddress                recv_addr  = null;
    protected boolean                      running    = false;

    // ===================================================================================
    /** @brief Constructor.
     *  @param hostname host of the UDP receiver.
     *  @param port     UDP port on the receiver.
     */
    // -----------------------------------------------------------------------------------
    public Sender( String hostname, int port ) {
      // ---------------------------------------------------------------------------------
      queue     = new ConcurrentLinkedQueue<UData>();
      recv_addr = new InetSocketAddress( hostname, port );

      logger.debug( "UDP.sender("+hostname+":"+port+") created" );
    }

    // ===================================================================================
    /** @brief Halt.
     *
     *  Mark this thread to stop execution. The maximum wait should be the set time out.
     */
    // -----------------------------------------------------------------------------------
    public void halt( ) {
      // ---------------------------------------------------------------------------------
      logger.debug( "UDP.sender thread marked for stop" );
      running = false;
      try {
        this.join();
      } catch( InterruptedException e ) {
      }
    }

    
    // ===================================================================================
    /** @brief Send.
     *  @param msg String to be added to the send queue.
     *
     *  Place a string on the queue to be sent to the receiver.
     */
    // -----------------------------------------------------------------------------------
    public void send( byte[] data, int len ) {
      // ---------------------------------------------------------------------------------
      queue.add( new UData( data, len ) );
    }

    // ===================================================================================
    /** @brief Send.
     *  @param msg String to be added to the send queue.
     *
     *  Place a string on the queue to be sent to the receiver.
     */
    // -----------------------------------------------------------------------------------
    public void send( byte[] data ) {
      // ---------------------------------------------------------------------------------
      queue.add( new UData( data, data.length ) );
    }

    
    // ===================================================================================
    /** @brief Run.
     *
     *  Thread run function. Watch the queue for new message and send them as they are
     *  available. Yield this thread if there are no available messages.
     */
    // -----------------------------------------------------------------------------------
    public void run( ) {
      // ---------------------------------------------------------------------------------
      DatagramSocket socket = null;

      logger.debug( "UDP.sender thread started" );
      
      try {
        socket = new DatagramSocket();
        running = true;

        while( running ) {
          UData temp = queue.poll();
          if ( null != temp ) {
            DatagramPacket packet = new DatagramPacket(temp.data, temp.len, recv_addr);
            socket.send( packet );
            //logger.debug( "send udp: "+temp.len+" bytes" );
         } else {
            this.yield();
          }
        }
  
      } catch( IOException e1 ) {
        logger.error( "UDP.Sender: "+e1.toString() );
      }

      if ( null != socket ) {
        socket.close();  
      }      

      logger.debug( "UDP.sender thread stopped" );
    }
    
  } // end class UDPTransport.Sender

  // =====================================================================================
  /** @brief Listener Interface.
   *
   *  A listener can be created by implementing this class, and used by
   *  UDPTransport.Notifier.
   */
  // -----------------------------------------------------------------------------------
  public static interface Listener {
    // -----------------------------------------------------------------------------------
    public void onMessage( byte[] str, int n );
  }

  
  // =====================================================================================
  /** @brief Notifier.
   *
   *  This class is a thead that may be used to monitor the Reciever and notify a
   *  Listener when new Strings are available.
   */
  // -----------------------------------------------------------------------------------
  public static class Notifier extends Thread {
    // -----------------------------------------------------------------------------------
    protected UDPTransport.Listener listener     = null;
    protected UDPTransport.Receiver receiver     = null;
    protected boolean               running      = false;

    
    // ===================================================================================
    /** @brief Constructor.
     *  @param recv Reference to the receiver.
     *  @param list Reference to the Listener.
     */
    // -----------------------------------------------------------------------------------
    public Notifier( UDPTransport.Receiver recv, UDPTransport.Listener list ) {
      // ---------------------------------------------------------------------------------
      receiver = recv;
      listener = list;
    }

    
    // ===================================================================================
    /** @brief Halt.
     *
     *  Mark this thread to stop execution. The maximum wait should be the set time out.
     */
    // -----------------------------------------------------------------------------------
    public void halt( ) {
      // ---------------------------------------------------------------------------------
      running = false;
      logger.debug( "UDP.notifier thread marked for stop" );
      try {
        this.join();
      } catch( InterruptedException e ) {
      }
    }

    // ===================================================================================
    /** @brief Run.
     *
     *  Thread run function. Monitor the receiver's queue for new messages. If the
     *  receiver's queue is empty this thread will yield execution be for trying again.
     */
    // -----------------------------------------------------------------------------------
    public void run( ) {
      // ---------------------------------------------------------------------------------

      running = true;

      logger.debug( "UDP.notifier thread started" );
      
      while( running ) {
        UData test = receiver.get();
        if ( null != test ) {
          listener.onMessage( test.data, test.len );
        } else {
          this.yield();
        }
      }

      logger.debug( "UDP.notifier thread stopped" );
    }


  } // end class UDPTransport.Notifier

  
} // end class UDPTransport


// =======================================================================================
// **                              U D P T R A N S P O R T                              **
// ======================================================================== END FILE =====
