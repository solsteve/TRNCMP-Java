// ====================================================================== BEGIN FILE =====
// **                              J T E S T _ S I G I N T                              **
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
 * @file jtest_sigint.java
 *  Provides test for SIGINT and SIGTERM capture.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date   2018-05-24
 */
// =======================================================================================

package org.trncmp.test;

// =======================================================================================
public class jtest_sigint extends Thread {
    // -----------------------------------------------------------------------------------

  protected boolean running = false;

  // =====================================================================================
  public jtest_sigint() {
    // -----------------------------------------------------------------------------------

    
  }
  
  // =====================================================================================
  public void halt() {
    // -----------------------------------------------------------------------------------
    running = false;
  }
  
  // =====================================================================================
  public void run() {
    // -----------------------------------------------------------------------------------
    System.err.println( "thread started" );
    running = true;
    try {
      while( running ) {
        sleep( 1000 );
        System.err.println( "beep" );
      }
    } catch( InterruptedException e ) {
      System.err.println( "thread was interrupted" );
    }

    
    System.err.println( "thread stopped" );
  }

  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------
    jtest_sigint J = new jtest_sigint();

    J.start();

    try {
      J.join();
    } catch( InterruptedException e ) {
      System.err.println( "Join was interrupted" );
    }

    System.err.println( "static main terminated" );
    
  }

}

// =======================================================================================
// **                              J T E S T _ S I G I N T                              **
// ======================================================================== END FILE =====
