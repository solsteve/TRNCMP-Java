// ====================================================================== BEGIN FILE =====
// **                                 T R A N S P O R T                                 **
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
 * @file Transport.java
 *  Provides a simple wrapper around some MessageQueuing System (e.g. JMS/ActiveMQ)
 *
 * @author Stephen W. Soliday
 * @date 2018-05-14
 */
// =======================================================================================

package org.trncmp.lib.net;

import org.trncmp.lib.*;
import javax.jms.*;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

// =======================================================================================
public class Transport {
  // -------------------------------------------------------------------------------------


  // =====================================================================================
  public static interface Listener {
    // -----------------------------------------------------------------------------------
    public abstract void onMessage( String str );
  }
  
  
  // =====================================================================================
  public static class InternalBroker {
    // -----------------------------------------------------------------------------------
    protected BrokerService broker       = null; 
    protected String        connect_addr = null;
    protected String        bind_addr    = null;

    
    // ===================================================================================
    public InternalBroker( String bind ) {
      // ---------------------------------------------------------------------------------
      try {
        connect_addr = new String( "tcp://" + bind );
        bind_addr    = new String( "broker:(" + connect_addr + ")" );

        broker = BrokerFactory.createBroker( new URI( bind_addr ) );
        System.err.println( "Start Broker "+bind_addr );
        broker.start();
      } catch( Exception e ) {
        System.err.println( "IB: "+e.toString() );
        System.exit(1);
      }
    }

    
    // ===================================================================================
    public void stop() {
      // ---------------------------------------------------------------------------------
      try {
        broker.stop();
      } catch( Exception e ) {
        System.err.println( e.toString() );
        System.exit(1);
      }
    }

    
  } // end class Transport.InternalBroker




  // =====================================================================================
  public static class BrokerConnection {
    // -----------------------------------------------------------------------------------
    protected String     connect_addr = null;
    protected Connection connection   = null;

    
    // ===================================================================================
    public static class Receiver implements javax.jms.MessageListener {
      // ---------------------------------------------------------------------------------
      protected MessageConsumer    consumer    = null;
      protected Session            session     = null;
      protected Destination        destination = null;
      protected Transport.Listener listener    = null;

      
      // =================================================================================
      protected Receiver( Connection connection, String queue_name ) {
        // -------------------------------------------------------------------------------
        try {
          session     = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
          destination = session.createQueue( queue_name );
          consumer    = session.createConsumer( destination );
          consumer.setMessageListener(this);
          System.err.println( "Start Session "+queue_name );
        } catch( javax.jms.JMSException e ) {
          System.err.println( e.toString() );
          System.exit(2);
        }
      }

      
      // =================================================================================
      public void setListener( Transport.Listener list ) {
        // -------------------------------------------------------------------------------
        System.err.println( "Set Listener" );
        listener = list;
      }

      
      // =================================================================================
      public void onMessage( Message message ) {
        // -------------------------------------------------------------------------------
        try {
          if ( null != listener ) {
            if (message instanceof TextMessage) {
              TextMessage textMessage = (TextMessage) message;
              String str = textMessage.getText();
              System.out.println( "Internal Rec: "+str );
              listener.onMessage( str );
            } else {
              System.out.println( "No Listener" );
            }
          }
        } catch( javax.jms.JMSException e ) {
          System.err.println( e.toString() );
        }
      }


      // =================================================================================
      public void end() {
        // -------------------------------------------------------------------------------
        try {
          session.close();
        } catch( Exception e ) {
          System.err.println( e.toString() );
          System.exit(1);
        }
      }
      
    } // end class Transport.BrokerConnection.Receiver



    
    // ===================================================================================
    public static class Sender {
      // ---------------------------------------------------------------------------------
      protected MessageProducer producer    = null;
      protected Session         session     = null;
      protected Destination     destination = null;

      
      // =================================================================================
      protected Sender( Connection connection, String queue_name ) {
        // -------------------------------------------------------------------------------
        try {
          session     = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
          destination = session.createQueue( queue_name );
          producer    = session.createProducer(destination);
          producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
          System.err.println( "Start Session "+queue_name );
        } catch( javax.jms.JMSException e ) {
          System.err.println( e.toString() );
          System.exit(2);
        }
      }

      
      // ===================================================================================
      public boolean send( String str ) {
        // ---------------------------------------------------------------------------------
        try {
          TextMessage msg = session.createTextMessage(str);
          producer.send(msg);
          System.err.println( "Internal Send: "+str );
          return false;
        } catch( javax.jms.JMSException e ) {
          System.err.println( e.toString() );
        }
        return true;
      }

      
      // =================================================================================
      public void end() {
        // -------------------------------------------------------------------------------
        try {
          session.close();
        } catch( Exception e ) {
          System.err.println( e.toString() );
          System.exit(1);
        }
      }
      

    } // end class Transport.BrokerConnection.Receiver




    // ===================================================================================
    public BrokerConnection( String broker_addr ) {
      // ---------------------------------------------------------------------------------
      try {
        connect_addr = new String( "tcp://" + broker_addr );
        ConnectionFactory factory = new ActiveMQConnectionFactory( connect_addr );
        connection = factory.createConnection();
        connection.start();
        System.err.println( "Start Connection "+broker_addr );
      } catch( Exception e ) {
        System.err.println( e.toString() );
        System.exit(1);
      }
    }

    
    // ===================================================================================
    public Receiver getReceiver( String queue_name ) {
      // ---------------------------------------------------------------------------------
      return new Transport.BrokerConnection.Receiver( connection, queue_name );
    }


    // ===================================================================================
    public Sender getSender( String queue_name ) {
      // ---------------------------------------------------------------------------------
      return new Transport.BrokerConnection.Sender( connection, queue_name );
    }

    
    // ===================================================================================
    public void close() {
      // ---------------------------------------------------------------------------------
      try {
        if (connection != null) {
          connection.close();
          connection = null;
        }
      } catch( Exception e ) {
        System.err.println( e.toString() );
        System.exit(1);
      }
    }


  } // end class Transport.BrokerConnection
  
  
} // end class Transport


// =======================================================================================
// **                                 T R A N S P O R T                                 **
// ======================================================================== END FILE =====
