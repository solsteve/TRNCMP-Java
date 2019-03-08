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
 * @file jtest_RR_client.java
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
public class jtest_RR_client {
  // -------------------------------------------------------------------------------------

  
  // =====================================================================================
  public void process( String[] resp ) {
    // -----------------------------------------------------------------------------------
    System.out.format( "\nRecived %d lines\n", resp.length );
    for ( int i=0; i<resp.length; i++ ) {
      System.out.format( "%d - [%s]\n", i, resp[i] );
    }
  }
  
  // =====================================================================================
  public void test01() throws Exception {
    // -----------------------------------------------------------------------------------

    TCPRequestResponse.Requester req = new TCPRequestResponse.Requester( "127.0.0.1",
                                                                         32323 );
    try {
      Thread.sleep( 1000 );  process( req.query( "Sat 1" ) );
      Thread.sleep( 2000 );  process( req.query( "Sat 2" ) );
      Thread.sleep( 3000 );  process( req.query( "Sat 3" ) );
      Thread.sleep( 1000 );  process( req.query( "Sat 4" ) );
    } catch( InterruptedException e ) {
    }
    
  }

  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
    try {
      jtest_RR_client JT = new jtest_RR_client();
      JT.test01();
    } catch( Exception e ) {
      System.err.println( e.toString() );
    }
  }
  
}

// =======================================================================================
// **                            J T E S T _ U D P _ S E N D                            **
// ======================================================================== END FILE =====
