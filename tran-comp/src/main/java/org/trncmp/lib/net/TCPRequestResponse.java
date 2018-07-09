// ====================================================================== BEGIN FILE =====
// **                        T C P R E Q U E S T R E S P O N S E                        **
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
 * @file TCPRequestResponse.java
 *  
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date   2018-05-20
 */
// =======================================================================================

package org.trncmp.lib.net;

import org.trncmp.lib.StringTool;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import org.apache.log4j.*;

// =======================================================================================
public class TCPRequestResponse {
  // -------------------------------------------------------------------------------------
  protected static final Logger logger = LogManager.getRootLogger();


  // =====================================================================================
  /** @brief Proceesing Interface.
   *
   *  A processor is required to listen to a request and form a response.
   */
  // -------------------------------------------------------------------------------------
  public static interface ResponseProcessor {
    // -----------------------------------------------------------------------------------
    public String[] onQuerry( String request );
  }



  
  // ===================================================================================
  /** @brief Handler for requests.
   *
   *  Read the incoming request for a socket and send it to the Response Processor.
   */
  // -----------------------------------------------------------------------------------
  protected static void HandleRequest( Socket s, ResponseProcessor resp_proc ) {
    // ---------------------------------------------------------------------------------
    BufferedReader in      = null;
    PrintWriter    out     = null;
    String         request = null;

    try {
      in = new BufferedReader(new InputStreamReader(s.getInputStream()));
      request = in.readLine();
      //logger.debug( "TCPRequestResponse.HandleRequest: new request ["+request+"]" );
      out     = new PrintWriter(s.getOutputStream(), true);
      String[] response = resp_proc.onQuerry( request );
      if ( null != response ) {
        out.println( response.length );
        for ( int i = 0; i<response.length; i++ ) {
          out.println( response[i] );
        }
      } else {
        logger.debug( "Zero length response generated" );
        out.println( "0" );
      }
      out.flush();
      out.close();
      s.close();
    } catch (IOException e) {
      logger.error("Failed respond to request: " + e.getMessage());
    } finally {
      if (s != null) {
        try {
          s.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    //logger.debug( "TCPRequestResponse.HandleRequest: request handled" );
    return;
  }



  
  // =====================================================================================
  /** @brief Response Server
   *
   *  Thread to handle incoming tcp connections.
   */
  // -------------------------------------------------------------------------------------
  public static class ResponseServer extends Thread {
    // -----------------------------------------------------------------------------------
    protected ResponseProcessor resp_proc   = null;
    protected Executor          thread_pool = null;
    protected InetAddress       bind_addr   = null;
    protected int               tcp_port    = 0;
    protected boolean           running     = false;

    protected static final int NUM_THREADS = 128;
    protected static final int BACKLOG     = 64;
    

    // ===================================================================================
    /** @brief Constructor.
     *  @param hostname name of the interface to bind new incomming connections.
     *  @param port     port to bind new incomming connections.
     *  @param proc     reference to the reponse processor
     */
    // -----------------------------------------------------------------------------------
    public ResponseServer( String hostname, int port, ResponseProcessor proc ) {
      // ---------------------------------------------------------------------------------
      try {
        tcp_port    = port;
        bind_addr   = InetAddress.getByName( hostname );
        
        resp_proc   = proc;
        thread_pool = Executors.newFixedThreadPool( NUM_THREADS );
      } catch( UnknownHostException e ) {
        System.err.println( "TCPRequestResponse.ResponseServer: "+e.toString() );
        System.exit( 1 );
      }
    }

    
    // ===================================================================================
    /** @brief Halt.
     *
     *  Mark this thread to stop execution. The maximum wait should be the set time out.
     */
    // -----------------------------------------------------------------------------------
    public void halt( ) {
      // ---------------------------------------------------------------------------------
      logger.debug( "ResponseServer thread marked for halt" );
      running = false;
    }

    
    // ===================================================================================
    /** @brief Run.
     *
     *  Thread run function. Listen for incoming connections and spawn threads to
     *  handle the requests.
     */
    // -----------------------------------------------------------------------------------
    public void run( ) {
      // ---------------------------------------------------------------------------------
      logger.debug( "ResponseServer thread started" );
      try {
        ServerSocket socket = new ServerSocket( tcp_port, BACKLOG, bind_addr );
        socket.setSoTimeout(1000);
        running = true;
        while( running ) {
          try {
          final Socket connection = socket.accept();
          Runnable task = new Runnable() {
              @Override
              public void run() {
                HandleRequest( connection, resp_proc );
              }
            };
          thread_pool.execute( task );
          } catch(SocketTimeoutException e1) {
          }
        }
      } catch( IOException e ) {
        System.err.println( "TCPRequestResponse.ResponseServer : Early termination: "+
                            e.toString() );
      }
      logger.debug( "ResponseServer thread stopped" );
     
    }

  } // end class TCPRequestResponse.ResponseServer



  
  // =====================================================================================
  /** @brief Requester.
   *
   *  Generate requests to the reponse server.
   */
  // -------------------------------------------------------------------------------------
  public static class Requester {
    // -----------------------------------------------------------------------------------
    protected String server_name = null;
    protected int    server_port = 0;

    
    // ===================================================================================
    /** @brief Constructor.
     *  @param hostname name of the response server.
     *  @param port     tcp on the reponse server listening to connections.
     */
    // -----------------------------------------------------------------------------------
    public Requester( String hostname, int port ) {
      // ---------------------------------------------------------------------------------
      server_name = new String( hostname );
      server_port = port;
    }


    // ===================================================================================
    /** @brief Query.
     *  @param request request string.
     *  @retrun reponse from the reponse server.
     */
    // -----------------------------------------------------------------------------------
    public String[] query( String request ) {
      // ---------------------------------------------------------------------------------
      Socket sock = null;

      try {
        sock = new Socket( server_name, server_port );

        DataOutputStream out  = new DataOutputStream( sock.getOutputStream() );
        BufferedReader   in   = new BufferedReader(
            new InputStreamReader( sock.getInputStream() ) );
      
        out.writeBytes( request  + '\n' );

        try {
        String ret = in.readLine();
        
          int lines = StringTool.asInt32( ret.trim() );
          String[] response = new String[lines];
          if ( 0 < lines ) {
            for ( int i=0; i<lines; i++ ) {
              response[i] = in.readLine();
            }

            sock.close();
          }
          return response;
        } catch( IOException e3 ) {
          logger.warn( "No lines returned from "+server_name+":"+server_port );
        }
      } catch( UnknownHostException e1 ) {
        logger.error( "Unknown Host: "+server_name+":"+server_port );
      } catch( IOException e2 ) {
        logger.error( "Socket connection to "+server_name+":"+server_port+" not established" );
      }
      
      if ( null != sock ) {
        try {
          sock.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      
      return null;
    }
        
  } // end class TCPRequestResponse.Requester

  
} // end class TCPRequestResponse


// =======================================================================================
// **                        T C P R E Q U E S T R E S P O N S E                        **
// ======================================================================== END FILE =====
