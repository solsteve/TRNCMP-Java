// ====================================================================== BEGIN FILE =====
// **                           C O N T R O L R E C E I V E R                           **
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
 * @file ControlReceiver.java
 *  Provides an interface for a control listener.
 *
 * @author Stephen W. Soliday
 * @date 2018-04-16
 */
// =======================================================================================

package org.trncmp.lib;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.SocketTimeoutException;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class ControlReceiver extends Thread {
  // -------------------------------------------------------------------------------------

  private final Logger logger = LogManager.getLogger();

  private boolean               running            = false;
  private List<ControlListener> ctrl_list          = null;
  private boolean               shutdown_listeners = false;
  private String                shutdown_message   = new String( "EXIT" );

  private DatagramSocket        server_socket      = null;
  private int                   max_packet_len     = 0;

  private String                host_string        = null;
  private int                   host_port          = -1;


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void writeScript( String script_name ) {
    // -----------------------------------------------------------------------------------

    PrintStream ps = FileTools.openWrite( script_name );

    ps.format( "#!/bin/bash\n" );
    ps.format( "echo \"%s\" | nc -w 3 -u %s %d\n",
               shutdown_message, host_string, host_port );
    ps.format( "exit 0\n" );

    ps.close();
  }
  

  // =====================================================================================
  /** @brief Init.
   *  @param port UDP port to listen for incoming messages.
   */
  // -------------------------------------------------------------------------------------
  private void init( InetSocketAddress bindaddr, int max_buffer ) {
    // -----------------------------------------------------------------------------------

    host_string = bindaddr.getHostName();
    host_port   = bindaddr.getPort();
    
    ctrl_list      = new ArrayList<ControlListener>();
    max_packet_len = max_buffer;
    
    try {
      server_socket = new DatagramSocket( bindaddr );
      server_socket.setSoTimeout( 1000 );
    } catch ( SocketException e2 ) {
      logger.error( e2.toString() );
    }

    start();
  }
  

  // =====================================================================================
  /** @brief Close Port.
   *
   *   Close the UDP listening port.
   */
  // -------------------------------------------------------------------------------------
  private void close_port( ) {
    // -----------------------------------------------------------------------------------
    server_socket.close();
  }

  
  // =====================================================================================
  /** @brief Contructor.
   *  @param port UDP port to listen for incoming messages.
   */
  // -------------------------------------------------------------------------------------
  public ControlReceiver( int port ) {
    // -----------------------------------------------------------------------------------
    try {
      InetSocketAddress bindaddr = new InetSocketAddress( InetAddress.getLocalHost(), port );
      init( bindaddr, 256 );
    } catch ( UnknownHostException e ) {
      logger.error( e.toString() );
    }
  }
  
  // =====================================================================================
  /** @brief Contructor.
   *  @param port    UDP port to listen for incoming messages.
   *  @param max_buf maximum receive buffer.
   */
  // -------------------------------------------------------------------------------------
  public ControlReceiver( int port, int max_buf ) {
    // -----------------------------------------------------------------------------------
    try {
      InetSocketAddress bindaddr = new InetSocketAddress( InetAddress.getLocalHost(), port );
      init( bindaddr, max_buf );
    } catch ( UnknownHostException e ) {
      logger.error( e.toString() );
    }
  }
  

  // =====================================================================================
  /** @brief Contructor.
   *  @param iaddr address to bind the listening socket to.
   *  @param port  UDP port to listen for incoming messages.
   */
  // -------------------------------------------------------------------------------------
  public ControlReceiver( InetSocketAddress bindaddr ) {
    // -----------------------------------------------------------------------------------
    init( bindaddr, 256 );
  }
  
  // =====================================================================================
  /** @brief Contructor.
   *  @param iaddr   address to bind the listening socket to.
   *  @param port    UDP port to listen for incoming messages.
   *  @param max_buf maximum receive buffer.
   */
  // -------------------------------------------------------------------------------------
  public ControlReceiver( InetSocketAddress bindaddr, int max_buf ) {
    // -----------------------------------------------------------------------------------
    init( bindaddr, max_buf );
  }
  

  // =====================================================================================
  /** @brief Add.
   *  @param msg message string to match.
   *  @param lst reference to a listening object.
   *
   *   Add a ControlListener object to the list. This object will be signaled
   *   if the incomming message matches msg.
   */
  // -------------------------------------------------------------------------------------
  public void add( ControlListener lst ) {
    // -----------------------------------------------------------------------------------
    ctrl_list.add( lst );
  }
  

  // =====================================================================================
  /** @brief Shutdown.
   *  @param wait boolean flag if true block until this thread shuts down.
   *
   *   Shutdown this thread possibly waiting.
   */
  // -------------------------------------------------------------------------------------
  public void shutdown( boolean wait ) {
    // -----------------------------------------------------------------------------------
    running = false;
    if ( wait ) {
      try {
        join();
      } catch ( InterruptedException e ) { }
    }
    close_port();
  }

  // =====================================================================================
  /** @brief Shutdown.
   *
   *   Shutdown this thread without waiting.
   */
  // -------------------------------------------------------------------------------------
  public void shutdown( ) {
    // -----------------------------------------------------------------------------------
    shutdown( false );
  }

  
  // =====================================================================================
  /** @brief Set Shutdown Option.
   *  @param flag shutdown flag.
   *
   *   Set whether or not Listeners should be notified that this receiver is going to
   *   shut down.
   */
  // -------------------------------------------------------------------------------------
  public void setShutdown( boolean flag ) {
    // -----------------------------------------------------------------------------------
    shutdown_listeners = flag;
  }

  
  // =====================================================================================
  /** @brief Set Shutdown Option.
   *  @param sm shutdown message.
   *
   *   Set the shutdown message sent to listeners.
   */
  // -------------------------------------------------------------------------------------
  public void setShutdown( String sm ) {
    // -----------------------------------------------------------------------------------
    shutdown_message   = new String( sm );
    shutdown_listeners = true;
  }

  
  // =====================================================================================
  /** @brief Clean Up.
   *
   *   Possibly loop through all the listeners sending exit messages.
   */
  // -------------------------------------------------------------------------------------
  private void clean_up( ) {
    // -----------------------------------------------------------------------------------
    if ( shutdown_listeners ) {
      for ( int i=0; i<ctrl_list.size(); i++ ) {
        final ControlListener C = ctrl_list.get(i);
        new Thread() {
          public void run() {
            C.on_receive( shutdown_message );
          }
        }.start();
      }
    }
    close_port();
  }

  
  // =====================================================================================
  /** @brief Receive Message.
   *  @param msg message.
   *
   *   This function allows linked procedures to send messages directly with out the
   *   need to go through the external network stack.
   */
  // -------------------------------------------------------------------------------------
  public void receive( String msg ) {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<ctrl_list.size(); i++ ) {
      final ControlListener C = ctrl_list.get(i);
      new Thread() {
        public void run() {
          C.on_receive( msg.trim() );
        }
      }.run();
    }
  }

  
  // =====================================================================================
  /** @brief Main Loop.
   *
   *   Loop indefinitly listening for new message.
   */
  // -------------------------------------------------------------------------------------
  public void run( ) {
    // -----------------------------------------------------------------------------------
    byte[]         buffer = new byte[max_packet_len];
    DatagramPacket packet = new DatagramPacket( buffer, max_packet_len );

    running = true;
    try {
      while ( running ) {
        try {
          server_socket.receive( packet );
          String msg = new String( packet.getData(), 0, packet.getLength() );
          this.receive( msg );
        } catch( SocketTimeoutException e ) {
          yield();
        }
      }
    } catch ( IOException  e ) {
      logger.debug( e.toString() );
    }
    clean_up();
  }

  
}

// =======================================================================================
// **                           C O N T R O L R E C E I V E R                           **
// ======================================================================== END FILE =====
