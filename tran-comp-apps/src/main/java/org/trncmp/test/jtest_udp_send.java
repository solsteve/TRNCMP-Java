// ====================================================================== BEGIN FILE =====
// **                            J T E S T _ U D P _ S E N D                            **
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
 * @file jtest_udp_send.java
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
public class jtest_udp_send {
  // -------------------------------------------------------------------------------------

  
  // =====================================================================================
  public void test01() throws Exception {
    // -----------------------------------------------------------------------------------
    UDPTransport.Sender engine = new UDPTransport.Sender( "localhost", 33333 );
    engine.start();

    try {

      Thread.sleep( 1000 ); engine.send( new String("1st message").getBytes() );
      Thread.sleep( 3000 ); engine.send( new String("2nd message").getBytes() );
      Thread.sleep( 2000 ); engine.send( new String("3rd message").getBytes() );
      Thread.sleep( 4000 ); engine.send( new String("END").getBytes() );

    } catch( InterruptedException e ) {
    }

    engine.halt();
  }

  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
    try {
      jtest_udp_send JT = new jtest_udp_send();
      JT.test01();
    } catch( Exception e ) {
      System.err.println( e.toString() );
    }
  }
  
}

// =======================================================================================
// **                            J T E S T _ U D P _ S E N D                            **
// ======================================================================== END FILE =====
