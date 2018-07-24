// ====================================================================== BEGIN FILE =====
// **                             J T E S T _ Z S E R V E R                             **
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
 * @file jtest_zserver.java
 *  Provides test eval for zeromq server.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date   2018-05-11
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.*;
import org.trncmp.lib.net.*;
import javax.jms.*;

import java.net.URI;
import java.net.URISyntaxException;
 
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.zeromq.ZMQ;

// =======================================================================================
public class jtest_zserver implements ExceptionListener {
  // -------------------------------------------------------------------------------------

  
  // =====================================================================================
  public void test01() throws Exception {
    // -----------------------------------------------------------------------------------
    ZMQ.Context context = ZMQ.context(1);

    //  Socket to talk to clients
    ZMQ.Socket responder = context.socket(ZMQ.REP);
    responder.bind("tcp://*:5555");

    while (!Thread.currentThread().isInterrupted()) {
      // Wait for next request from the client
      byte[] request = responder.recv(0);
      System.out.println("Received " + new String (request));

      // Do some 'work'
      Thread.sleep(1000);

      // Send reply back to client
      String reply = "World";
      responder.send(reply.getBytes(), 0);
    }
    responder.close();
    context.term();
  }

  // =====================================================================================
  public void test02() throws Exception {
    // -----------------------------------------------------------------------------------

    // ----- start broker ---------------------------------------

    BrokerService broker = BrokerFactory.createBroker(new URI(
        "broker:(tcp://localhost:32123)"));
    broker.start();

    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
        "tcp://localhost:61616");
    
    Destination destination = new ActiveMQQueue( "someQueue" );
    Connection  connection  = connectionFactory.createConnection();
    Session     session     = connection.createSession( false,
                                                        Session.AUTO_ACKNOWLEDGE );
    try {
      MessageConsumer consumer = session.createConsumer(destination);
      connection.start();
      
      TextMessage msg = (TextMessage) consumer.receive();
      //System.out.println(msg);
      System.out.println("Received: " + msg.getText());
      session.close();
    } finally {
      if (connection != null) {
        connection.close();
      }
    }
    
  }

  
  // =====================================================================================
  public synchronized void onException(JMSException ex) {
    // -----------------------------------------------------------------------------------
    System.out.println("JMS Exception occured.  Shutting down client.");
  }



  // =====================================================================================
  public static class TestListener implements Transport.Listener {
    // -----------------------------------------------------------------------------------
    protected boolean running_flag = false;

    public boolean keepRunning() { return running_flag; }

    public void    setRunning( boolean flag ) { running_flag = flag; }
    
    public TestListener() { running_flag = true; }

    public void onMessage( String str ) {
      System.out.println( "Message Received: "+str );
      if ( str.equals( "END" ) ) {
        running_flag = false;
      }
    }
    

  }
  
  
  
  // =====================================================================================
  public void test03() {
    // -----------------------------------------------------------------------------------

    try {
      
      Transport.InternalBroker broker = new Transport.InternalBroker( "localhost:32123" );

      Thread.sleep(2000);
    
      Transport.BrokerConnection connect = new Transport.BrokerConnection( "localhost:32123" );

      Transport.BrokerConnection.Receiver recv = connect.getReceiver( "POSUPD" );

      TestListener listen = new TestListener();

      recv.setListener( listen );

      int count = 0;
      while( listen.keepRunning() ) {
        Thread.sleep(1000);
        System.err.println( count );
        count += 1;
      }
      
      recv.end();
      
      connect.close();
      
      broker.stop();
      
    } catch( InterruptedException w ) {
      System.err.println( w.toString() );
    }

  }

  
  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
     jtest_zserver JT = new jtest_zserver();
    JT.test03();
  }
  
}

// =======================================================================================
// **                             J T E S T _ Z S E R V E R                             **
// ======================================================================== END FILE =====
