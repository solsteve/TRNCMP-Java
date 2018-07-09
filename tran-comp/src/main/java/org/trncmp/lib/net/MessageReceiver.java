// ====================================================================== BEGIN FILE =====
// **                           M E S S A G E R E C E I V E R                           **
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
 * @file MessageReceiver.java
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

import java.net.URI;
import java.net.URISyntaxException;
 
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

// =====================================================================================
public class MessageReceiver extends Thread {
  // -----------------------------------------------------------------------------------
  ConcurrentLinkedQueue<String> queue        = null;
  boolean                       running      = false;
  String                        connect_addr = null;
  String                        bind_addr    = null;
  BrokerService                 broker       = null; 
  ConnectionFactory             factory      = null;
  Destination                   destination  = null;
  Connection                    connection   = null;

    
  // ===================================================================================
  public MessageReceiver( String bind, String name ) {
    // ---------------------------------------------------------------------------------
    try {
      connect_addr = new String( "tcp://" + bind );
      bind_addr    = new String( "broker:(" + connect_addr + ")" );
        
      broker = BrokerFactory.createBroker( new URI( bind_addr ) );
      broker.start();
        
      factory = new ActiveMQConnectionFactory( connect_addr );
        
      queue = new ConcurrentLinkedQueue<String>();
        
      destination = new ActiveMQQueue( name );
      connection  = factory.createConnection();
    } catch( Exception e ) {
      System.err.println( e.toString() );
      System.exit(1);
    }
  }
    
  // ===================================================================================
  public String get( ) {
    // ---------------------------------------------------------------------------------
    if ( queue.isEmpty() ) {
      return null;
    }
    return queue.remove();
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
        Session session = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
      
        MessageConsumer consumer = session.createConsumer(destination);
        connection.start();

        running = true;
        
        while ( running ) {
          TextMessage msg = (TextMessage) consumer.receive(1000);
          if ( null != msg ) {
            queue.add( msg.getText() );
          } else {
            yield();
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
    
  // =====================================================================================
  public synchronized void onException(JMSException ex) {
    // -----------------------------------------------------------------------------------
    System.err.println("JMS Exception occured in MessageReceiver class.");
  }
    
} // end class MessageReceiver

// =======================================================================================
// **                           M E S S A G E R E C E I V E R                           **
// ======================================================================== END FILE =====
