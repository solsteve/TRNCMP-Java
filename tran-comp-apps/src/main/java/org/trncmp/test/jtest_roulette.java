// ====================================================================== BEGIN FILE =====
// **                            J T E S T _ R O U L E T T E                            **
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
 * @brief   StatMod.
 * @file    jtest_statmod.java
 *
 * @details Provides the interface and procedures for testing the StatMod class.
 *
 * @author  Stephen W. Soliday
 * @date    2018-03-31
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.*;
import java.io.*;


// =======================================================================================
public class jtest_roulette {
  // -------------------------------------------------------------------------------------

  
  // =====================================================================================
  static int sum( int[] samples ) {
    // -----------------------------------------------------------------------------------
    int n   = samples.length;
    int sum = samples[0];
    
    for ( int i=1; i<n; i++ ) {
      sum += samples[i];
    }
    
    return sum;
  }

  
  // =====================================================================================
  static double sum( double[] samples ) {
    // -----------------------------------------------------------------------------------
    int    n   = samples.length;
    double sum = samples[0];
    
    for ( int i=1; i<n; i++ ) {
      sum += samples[i];
    }
    
    return sum;
  }

  
  // =====================================================================================
  public static void Test01( Dice dd, int scl ) {
    // -----------------------------------------------------------------------------------
    int[] test = { 23, 74, 15, 22, 91, 64 };
    int   n    = test.length;
    
    int[] hist = new int[ n ];
    for ( int i=0; i<n; i++ ) {
      hist[i] = 0;
    }

    int m = sum( test ) * scl;

    Roulette RW = new Roulette( test );
    RW.display( System.out );

    for ( int i=0; i<m; i++ ) {
      int idx = RW.next();
      hist[idx] += 1;
    }

    for ( int i=0; i<n; i++ ) {
      System.out.format( "%2d : %10d : %8.4f : %d\n",
                         i, test[i],
                         ((double) hist[i])/((double) scl),
                         hist[i] );
    }

  }

  
  // =====================================================================================
  public static void Test02( Dice dd, int scl ) {
    // -----------------------------------------------------------------------------------
    double[] test = { 2.3, 27.4, 17.5, 26.2, 9.1, 6.4 };
    int   n    = test.length;
    
    int[] hist = new int[ n ];
    for ( int i=0; i<n; i++ ) {
      hist[i] = 0;
    }

    int m = (int) Math.floor( sum( test ) ) * scl;

    Roulette RW = new Roulette( test );
    RW.display( System.out );

    for ( int i=0; i<m; i++ ) {
      int idx = RW.next();
      hist[idx] += 1;
    }

    for ( int i=0; i<n; i++ ) {
      System.out.format( "%2d : %8.4f : %8.4f : %d\n",
                         i, test[i],
                         ((double) hist[i])/((double) scl),
                         hist[i] );
    }

  }

  
  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
    Dice dd = Dice.getInstance();
    dd.seed_set();

    System.out.format("\n----- test 01 ------------------------------------------\n\n");
    Test01( dd, 10000 );

    System.out.format("\n----- test 02 ------------------------------------------\n\n");
    Test02( dd, 10000 );

    System.exit(0);
  }

  
}

// =======================================================================================
// **                             J T E S T _ S T A T M O D                             **
// ======================================================================== END FILE =====
