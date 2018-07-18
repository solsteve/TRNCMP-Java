// ====================================================================== BEGIN FILE =====
// **                              R U N S T A T Y T E S T                              **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2015, Stephen W. Soliday                                           **
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
//
// @file RunStatTest.java
// <p>
// Provides unit testing for the org.trncmp.libRunStaty class.
//
// @author Stephen W. Soliday
// @date 2018-07-07
//
// =======================================================================================

package org.trncmp.lib;

import        org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.trncmp.mllib.Entropy;

// =======================================================================================
public class RunStatTest {
  // -------------------------------------------------------------------------------------

  // =====================================================================================
  static class QuickStat {
    // -----------------------------------------------------------------------------------
    double minv  = 0.0e0;
    double maxv  = 0.0e0;
    double mean  = 0.0e0;
    double sigma = 0.0e0;
    double var   = 0.0e0;

    // ===================================================================================
    public QuickStat( double[] x ) {
      // ---------------------------------------------------------------------------------
      if ( null != x ) {
        int    n  = x.length;
        double fn = (double) n;

        minv = x[0];
        maxv = x[0];
        mean = x[0];
        for ( int i=1; i<n; i++ ) {
          if ( minv > x[i] ) { minv = x[i]; }
          if ( maxv < x[i] ) { maxv = x[i]; }
          mean += x[i];
        }
        mean /= fn;

        var = 0.0e0;
        for ( int i=0; i<n; i++ ) {
          double d = x[i] - mean;
          var += (d*d);
        }
        var  /= (fn - 0.0e0);
        sigma = Math.sqrt( var );
      }
    }

    // ===================================================================================
    public void display( java.io.PrintStream ps ) {
      // ---------------------------------------------------------------------------------
      ps.format( "Min  value : %15.8e\n", minv  );
      ps.format( "Max  value : %15.8e\n", maxv  );
      ps.format( "Mean value : %15.8e\n", mean  );
      ps.format( "Sigma      : %15.8e\n", sigma );
    }
    
  } // end class RunStatTest.QuickStat




  // =====================================================================================
  static class BoxMuller {
    // -----------------------------------------------------------------------------------
    java.util.Random  prng = null;
    
    boolean have_spare = false;
    double  rand1      = 0.0e0;
    double  rand2      = 0.0e0;

    // ===================================================================================
    public BoxMuller( long seed ) {
      // ---------------------------------------------------------------------------------
      prng = new java.util.Random( seed );
    }

    // ===================================================================================
    public double next() {
      // ---------------------------------------------------------------------------------
      if ( have_spare ) {
        have_spare = false;
        return Math.sqrt(rand1) * Math.sin(rand2);
      }
  
      have_spare = true;
  
      rand1 = prng.nextDouble();
  
      if ( rand1 < 1e-100 ) { rand1 = 1e-100; }
  
      rand1 = -Math2.N_TWO * Math.log(rand1);
      rand2 =  Math2.N_2PI*prng.nextDouble();
  
      return Math.sqrt(rand1) * Math.cos(rand2);    
    }
    
  } // end class RunStatTest.BoxMuller


  // =====================================================================================
  static double[] GenSamples( int n, double mean, double sigma, long seed ) {
    // -----------------------------------------------------------------------------------
    BoxMuller B = new BoxMuller(seed);
    
    double[] samples = new double[n];

    for ( int i=0; i<n; i++ ) {
      samples[i] = mean + (sigma * B.next());
    }
    
    return samples;
  }

  // =====================================================================================
  static boolean compare( double[] A, double[] B ) {
    // -----------------------------------------------------------------------------------
    int n = A.length;
    if ( n != B.length ) { return false; }
    for ( int i=0; i<n; i++ ) {
      if ( A[i] < B[i] ) { return false; }
      if ( A[i] > B[i] ) { return false; }
    }
    return true;
  }

  // =====================================================================================
  @Test
  public void testBoxMuller() {
    // -----------------------------------------------------------------------------------

    double[] test1 = GenSamples( 100, 0.0, 0.2, 123456L );
    double[] test2 = GenSamples( 100, 0.0, 0.2, 123457L );
    double[] test3 = GenSamples( 100, 0.0, 0.2, 123456L );

    assertFalse( compare( test1, test2 ) );
    assertTrue(  compare( test1, test3 ) );
    assertFalse( compare( test2, test3 ) );
  }
  
  // =====================================================================================
  @Test
  public void testRunStat() {
    // -----------------------------------------------------------------------------------
    final long   SEED    = 314159265L;

    final int    SAMPLES = 100000;
    final double MEAN    = 7.0;
    final double SIGMA   = 0.2;

    final double TOL     = 1.0e-12;
    
    double[] test = GenSamples( SAMPLES, MEAN, SIGMA, SEED );

    QuickStat S = new QuickStat( test );

    RunStat R1 = new RunStat();
    
    for ( int i=0; i<SAMPLES; i++ ) {
      R1.update( test[i] );
    }

    assertEquals( SAMPLES, R1.count() );
    assertEquals( S.minv,  R1.minv(), TOL );
    assertEquals( S.maxv,  R1.maxv(), TOL );
    assertEquals( S.mean,  R1.mean(), TOL );
    assertEquals( S.var,   R1.var(),  TOL );
    
  }

} // end class RunStatTest


// =======================================================================================
// **                                      D I C E                                      **
// ======================================================================== END FILE =====
