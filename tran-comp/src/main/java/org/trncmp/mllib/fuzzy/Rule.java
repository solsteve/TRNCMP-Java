// ====================================================================== BEGIN FILE =====
// **                                      R U L E                                      **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2019, Stephen W. Soliday                                           **
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
 * @file Rule.java
 * <p>
 * Provides an interface and methods for a fuzzy rule.
 *
 *
 * @date 2019-01-04
 *
 * ---------------------------------------------------------------------------------------
 *
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 *
 * @author Stephen W. Soliday
 * @date 2014-06-27
 */
// =======================================================================================

package org.trncmp.mllib.fuzzy;

import java.io.PrintStream;
import java.io.BufferedReader;
import java.util.Scanner;

// =======================================================================================
public class Rule {
  // -------------------------------------------------------------------------------------
  protected int[] input  = null;
  protected int   output = -1;

  public final int getIn  ( int i )                    { return input[i]; }
  public final int getOut ()                           { return output;   }
  public void      setIn  ( final int i, final int v ) { input[i] = v; }
  public void      setOut ( final int v)               { output   = v; }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void resize( int n ) {
    // -----------------------------------------------------------------------------------
    input  = new int[n];
    for ( int i=0; i<n; i++ ) {
      input[i] = -1;
    }
    output = -1;
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public Rule( int n ) {
    // -----------------------------------------------------------------------------------
    resize(n);
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public Rule( int[] inp, int out ) {
    // -----------------------------------------------------------------------------------
    int n = input.length;
    resize(n);
    for ( int i=0; i<n; i++ ) {
      input[i] = inp[i];
    }
    output = out;
  }

  // =====================================================================================
  /** Activation.
   *  @param mu array of fuzzy memberships.
   *  @return index of the minimum membership.
   *
   *  Null rules have and index of -1 and are not examined.
   *  If this rule containes no sets then -1 is returned.
   */
  // -------------------------------------------------------------------------------------
  protected int activate( double[] mu ) {
    // -----------------------------------------------------------------------------------
    int n = input.length;
    double x   =  1.0;
    int    idx = -1;
    for ( int i=0; i<n; i++ ) {
      if ( input[i] > -1 ) {
        if ( mu[i] < x ) {
          x = mu[i];
          idx = i;
        }
      }
    }
    return idx;
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void write( PrintStream ps ) {
    // -----------------------------------------------------------------------------------
    int n = input.length;
    for ( int i=0; i<n; i++ ) {
      ps.format( "%d ", input[i] );
    }
    ps.format( "%d\n", output );
  }


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void read( Scanner scan, int n ) {
    // -----------------------------------------------------------------------------------
    resize(n);
    
    for ( int i=0; i<n; i++ ) {
      input[i] = scan.nextInt();
    }
    output = scan.nextInt();
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void read( BufferedReader br, int n ) {
    // -----------------------------------------------------------------------------------
    read( new Scanner( br ), n );
  }

} // end class Rule


// =======================================================================================
// **                                      R U L E                                      **
// ======================================================================== END FILE =====
