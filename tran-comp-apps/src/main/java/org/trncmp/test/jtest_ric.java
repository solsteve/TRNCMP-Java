// ====================================================================== BEGIN FILE =====
// **                                 J T E S T _ R I C                                 **
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
 * @file jtest_ric.java
 *  Provides test eval for random integer cycle.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date   2018-05-24
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.*;

// =======================================================================================
public class jtest_ric {
  // -------------------------------------------------------------------------------------

  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------

    int SAMPLES = 0;
    int TESTS   = 0;

    if ( 2 == args.length ) {
      SAMPLES = Integer.parseInt( args[0] );
      TESTS   = Integer.parseInt( args[1] );
    } else {
      System.err.format( "\nUSAGE: JTest ric samp lines\n" );
      System.err.format( "  samp  - number of samples\n" );
      System.err.format( "  lines - number of lines\n" );
      System.err.format( "\nExample: JTest ric 8 6\n\n" );
      System.exit(1);
    }

    Dice.getInstance().seed_set();

    RandomIntegerCycle R = new RandomIntegerCycle( SAMPLES );

    System.out.format( "\n" );
    for ( int i=0; i<TESTS; i++ ) {
      for ( int j=0; j<SAMPLES; j++ ) {
        int k = R.next();
        System.out.format( " %2d", k );
      }
      System.out.format( "\n" );
    }
    System.out.format( "\n" );
    
  }
  
}

// =======================================================================================
// **                                 J T E S T _ R I C                                 **
// ======================================================================== END FILE =====
