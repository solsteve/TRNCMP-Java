// ====================================================================== BEGIN FILE =====
// **                           M E S S A G E P R O V I D E R                           **
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
 * @file MessageSender.java
 *  
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date   2018-05-1 6
 */
// =======================================================================================

package org.trncmp.lib.net;

import org.trncmp.lib.*;
import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.zeromq.ZMQ;

// =======================================================================================
public class MessageSender extends Thread {
  // -----------------------------------------------------------------------------------
  ConcurrentLinkedQueue<String> queue        = null;
  boolean                       running      = false;
  String                        connect_addr = null;
  String                        bind_addr    = null;
  ConnectionFactory             factory      = null;
  Destination                   destination  = null;
  Connection                    connection   = null;


  // ===================================================================================
  public MessageSender( String bind, String name ) {
    // ---------------------------------------------------------------------------------
    try {
      connect_addr = new String( "tcp://" + bind );
      bind_addr    = new String( "broker:(" + connect_addr + ")" );

      factory      = new ActiveMQConnectionFactory( connect_addr );

      queue        = new ConcurrentLinkedQueue<String>();
        
      destination  = new ActiveMQQueue( name );
      connection   = factory.createConnection();
        
    } catch( Exception e ) {
      System.err.println( e.toString() );
      System.exit(1);
    }
  }


  // ===================================================================================
  public boolean send( String msg ) {
    // ---------------------------------------------------------------------------------
    return queue.add( msg );
  }
    

  // ===================================================================================
  public void halt() {
    // ---------------------------------------------------------------------------------
    running = false;
  }
    

  // ===================================================================================
  public void run( ) {
    // ---------------------------------------------------------------------------------
    try {
      try {
        Session         session  = connection.createSession( false,
                                                             Session.AUTO_ACKNOWLEDGE );
        MessageProducer producer = session.createProducer(destination);

        while( running ) {
          if ( queue.isEmpty() ) {
            yield();
          } else {
            String  str = queue.remove();
            Message msg = session.createTextMessage(str);
            producer.send(msg);
          }
        }

        session.close();

      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch( javax.jms.JMSException e ) {
      System.err.println( e.toString() );
      System.exit(2);
    }
  }

}


// =======================================================================================
// **                           M E S S A G E P R O V I D E R                           **
// ======================================================================== END FILE =====
