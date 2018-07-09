// ====================================================================== BEGIN FILE =====
// **                             J T E S T _ Z C L I E N T                             **
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
 * @file jtest_zclient.java
 *  Provides test eval for zeromq client.
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

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.zeromq.ZMQ;
import org.apache.log4j.*;

// =======================================================================================
public class jtest_zclient {
  // -------------------------------------------------------------------------------------

  
  // =====================================================================================
  public void test01() throws Exception {
    // -----------------------------------------------------------------------------------
    ZMQ.Context context = ZMQ.context(1);

    //  Socket to talk to server
    System.out.println("Connecting to hello world server…");

    ZMQ.Socket requester = context.socket(ZMQ.REQ);
    requester.connect("tcp://localhost:5555");

    for (int requestNbr = 0; requestNbr != 10; requestNbr++) {
      String request = "Hello";
      System.out.println("Sending Hello " + requestNbr);
      requester.send(request.getBytes(), 0);

      byte[] reply = requester.recv(0);
      System.out.println("Received " + new String(reply) + " " + requestNbr);
    }
    requester.close();
    context.term();
  }



  
  // =====================================================================================
  public void test02() throws Exception {
    // -----------------------------------------------------------------------------------

    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
        "tcp://localhost:61616");
    
    Destination destination = new ActiveMQQueue( "someQueue" );
    Connection  connection  = connectionFactory.createConnection();
    Session     session     = connection.createSession( false,
                                                        Session.AUTO_ACKNOWLEDGE );
    try {
      String payload = "Hi";
      Message msg = session.createTextMessage(payload);
      MessageProducer producer = session.createProducer(destination);
      System.out.println("Send text '" + payload + "'");
      producer.send(msg);
      session.close();
    } finally {
      if (connection != null) {
        connection.close();
      }
    }
 
  }

  
  // =====================================================================================
  public void test03() {
    // -----------------------------------------------------------------------------------
    try {

      Transport.BrokerConnection connect = new Transport.BrokerConnection( "localhost:32123" );

      Transport.BrokerConnection.Sender sndr = connect.getSender( "POSUPD" );

      Thread.sleep(2000);
      sndr.send( "First Message" );

      Thread.sleep(1500);
      sndr.send( "Second Message" );

      Thread.sleep(3000);
      sndr.send( "Third Message" );

      Thread.sleep(2000);
      sndr.send( "END" );

      sndr.end();
      
      connect.close();

    } catch( InterruptedException w ) {
      System.out.println( w.toString() );
    }
  }


  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
    LogManager.getRootLogger().setLevel((Level) Level.INFO);
    jtest_zclient JT = new jtest_zclient();
    JT.test03();
  }
  
}

// =======================================================================================
// **                             J T E S T _ Z C L I E N T                             **
// ======================================================================== END FILE =====
