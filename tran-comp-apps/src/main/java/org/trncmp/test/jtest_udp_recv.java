// ====================================================================== BEGIN FILE =====
// **                            J T E S T _ U D P _ R E C V                            **
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
 * @file jtest_udp_recv.java
 *  Provides .
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date   2018-05-20
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.*;
import org.trncmp.lib.net.*;

// =======================================================================================
public class jtest_udp_recv {
  // -------------------------------------------------------------------------------------
  protected boolean running = true;

  public static class MyWork implements UDPTransport.Listener {
    public boolean running = true;

    public void onMessage( byte[] buf, int n ) {
      String str = new String( buf, 0, n );
      if ( str.equals( "END" ) ) {
        running = false;
      } else {
        System.out.println( "Received: "+str );
      }
    }
  }
  

  // =====================================================================================
  public void test02() throws Exception {
    // -----------------------------------------------------------------------------------
    UDPTransport.Receiver db = new UDPTransport.Receiver( "localhost", 33333,
                                                          128, 1000 );
    db.start();

    MyWork work = new MyWork();

    UDPTransport.Notifier N = new UDPTransport.Notifier( db, work );

    N.start();

    try {
      while ( work.running ) {
        Thread.sleep( 500 );
      }
    } catch( InterruptedException e ) {
    }

    N.halt();
    db.halt();

  }

  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
    try {
      jtest_udp_recv JT = new jtest_udp_recv();
      JT.test02();
    } catch( Exception e ) {
      System.err.println( e.toString() );
    }
  }
  
}

// =======================================================================================
// **                            J T E S T _ U D P _ R E C V                            **
// ======================================================================== END FILE =====
