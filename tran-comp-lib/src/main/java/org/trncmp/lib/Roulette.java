// ====================================================================== BEGIN FILE =====
// **                                  R O U L E T T E                                  **
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
 * @file Roulette.java
 *  Provides interface and methods to reproduce a weighted distribution of indicies.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2018-03-31
 */
// =======================================================================================

package org.trncmp.lib;
import java.io.*;

// =======================================================================================
public class Roulette {
  // -------------------------------------------------------------------------------------

  Dice dd = Dice.getInstance();
  
  int      count  = 0;
  double[] accum  = null;
  double[] weight = null;

  // =====================================================================================
  /** @brief Constructor.
   *  @param  n  number of weights.
   *
   *  The number of weights will be allocated.
   */
  // -------------------------------------------------------------------------------------
  public Roulette( int n ) {
    // -----------------------------------------------------------------------------------
    resize( n );
  }

  // =====================================================================================
  /** @brief Constructor.
   *  @param wgts array of real weights.
   *
   *  The number of weights will be allocated. And real weights will be set.
   */
  // -------------------------------------------------------------------------------------
  public Roulette( double[] wgts ) {
    // -----------------------------------------------------------------------------------
    setWeights( wgts );
  }

  
  // =====================================================================================
  /** @brief Constructor.
   *  @param wgts array of integer weights.
   *
   *  The number of weights will be allocated. And integer weights will be set.
   */
  // -------------------------------------------------------------------------------------
  public Roulette( int[] wgts ) {
    // -----------------------------------------------------------------------------------
    setWeights( wgts );
  }


  // =====================================================================================
  /** @brief Display.
   *  @param ps  reference to a PrintStream.
   *  @param fmt edit descriptor.
   *
   *  Display the model parameters.
   */
  // -------------------------------------------------------------------------------------
  public void display( PrintStream ps, String fmt ) {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<count; i++ ) {
      ps.format( "%2d : ", i );
      ps.format( fmt, weight[i] );
      ps.format( " " );
      ps.format( fmt, accum[i] );
      ps.format( "\n" );
    }
  }


  // =====================================================================================
  /** @brief Display.
   *  @param ps  reference to a PrintStream.
   *
   *  Display the model parameters.
   */
  // -------------------------------------------------------------------------------------
  public void display( PrintStream ps ) {
    // -----------------------------------------------------------------------------------
    display( ps, "%g" );
  }

   
  // =====================================================================================
  /** @brief Resize.
   *  @param n desired number of weights.
   *
   *  @note this must be followed with a set and normalize.
   */
  // -------------------------------------------------------------------------------------
  public void resize( int n ) {
    // -----------------------------------------------------------------------------------
    if ( n != count ) {
      count = n;
      accum  = new double[n];
      weight = new double[n];

      for ( int i=0; i<n; i++ ) {
        accum[i]  = 0.0e0;
        weight[i] = 1.0e0;
      }
    }
  }


  // =====================================================================================
  /** @brief Set Weights.
   *  @param wgts array of real weights.
   *
   *  The number of weights will be reallocated if necessary.
   */
  // -------------------------------------------------------------------------------------
  public void setWeights( double[] wgts ) {
    // -----------------------------------------------------------------------------------

    int n = wgts.length;
    resize( n );

    for ( int i=0; i<n; i++ ) {
      weight[i] = wgts[i];
      accum[i]  = 0.0e0;
    }

    renormalize();
  }


  // =====================================================================================
  /** @brief Set Weights.
   *  @param wgts array of integer weights.
   *
   *  The number of weights will be reallocated if necessary.
   */
  // -------------------------------------------------------------------------------------
  public void setWeights( int[] wgts ) {
    // -----------------------------------------------------------------------------------

    int n = wgts.length;
    resize( n );

    for ( int i=0; i<n; i++ ) {
      weight[i] = (double) wgts[i];
      accum[i]  = 0.0e0;
    }

    renormalize();
  }


  // =====================================================================================
  /** @brief Renormalize.
   *
   *  Normalize the weights so that their sum totals to 1.0. Then fill the accumulators.
   *  Only the value of the accumulators is modified.
   */
  // -------------------------------------------------------------------------------------
  public void renormalize() {
    // -----------------------------------------------------------------------------------

    double sum = 0.0e0;

    for ( int i=0; i<count; i++ ) {
      sum += weight[i];
    }

    accum[0] = weight[0] / sum;
    for ( int i=1; i<count; i++ ) {
      accum[i] = accum[i-1] + ( weight[i] / sum );
    }
    
  }


  // =====================================================================================
  /** @brief Next.
   *  @return the next random index..
   */
  // -------------------------------------------------------------------------------------
  public int next() {
    // -----------------------------------------------------------------------------------

    double test = dd.uniform();

    for ( int i=0; i<count; i++ ) {
      if ( test < accum[i] ) {
        return i;
      }
    }
    
    return count - 1;
  }


}


// =======================================================================================
// **                                  R O U L E T T E                                  **
// ======================================================================== END FILE =====
