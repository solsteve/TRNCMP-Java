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
 * @file jtest_RR_server.java
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
public class jtest_RR_server implements TCPRequestResponse.ResponseProcessor {
  // -------------------------------------------------------------------------------------

  Dice dd = Dice.getInstance();
  
  // ===================================================================================
  public String[] onQuerry( String request ) {
    // ---------------------------------------------------------------------------------

    int n = 3 + dd.index(5);

    String[] resp  = new String[n];

    for ( int i=0; i<n; i++ ) {
      resp[i] = String.format( "func(%d,%s) = working on it", i, request );
    }

    return resp;
  }
    
  
  // =====================================================================================
  public void test01() throws Exception {
    // -----------------------------------------------------------------------------------

    TCPRequestResponse.ResponseServer server =
        new TCPRequestResponse.ResponseServer( "", 32323, this );

    server.start();

    try {
      Thread.sleep( 60000 );
    } catch( InterruptedException e ) {
      System.err.println( "Interrupted" );
    } finally {
      System.err.println( "Halt Server" );
      server.halt();
      System.err.println( "Bye" );
    }

  }

  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
    try {
      jtest_RR_server JT = new jtest_RR_server();
      JT.test01();
    } catch( Exception e ) {
      System.err.println( e.toString() );
    }
  }
  
}

// =======================================================================================
// **                            J T E S T _ U D P _ R E C V                            **
// ======================================================================== END FILE =====
