// ====================================================================== BEGIN FILE =====
// **                           J T E S T _ C L S _ S T A T S                           **
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
 * @file jtest_cls_stats.java
 *  Provides Test the Clustering Statistics.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2018-11-13
 */
// =======================================================================================

package org.trncmp.test;

import java.util.List;
import java.util.LinkedList;

import org.trncmp.mllib.clustering.Cluster;
import org.trncmp.mllib.clustering.EuclideanCluster;
import org.trncmp.mllib.clustering.GaussianCluster;
import org.trncmp.mllib.clustering.ClusterPoint;
import org.trncmp.lib.Math2;
import org.trncmp.lib.Dice;


// =======================================================================================
public class jtest_cls_stats {
  // -------------------------------------------------------------------------------------

  public static final int SAMPLES = 2000000;
  public static final int DIMS    = 3;

  public static Dice dd = Dice.getInstance();

  // =====================================================================================
  static double rnd( int a, int b ) {
    // -----------------------------------------------------------------------------------
    return (double)( a + dd.uniform( b - a ) );
  }
  
  // =====================================================================================
  static void Test_Euclid() {
    // -----------------------------------------------------------------------------------
    List<ClusterPoint> samples = new LinkedList<ClusterPoint>();

    double[] expected_min  = new double[DIMS];
    double[] expected_max  = new double[DIMS];
    double[] expected_mean = new double[DIMS];
    double[] expected_std  = new double[DIMS];

    for ( int i=0; i<DIMS; i++ ) {
      expected_min[i]  =  1.0e100;
      expected_max[i]  = -1.0e100;
      expected_mean[i] = rnd( -30, 30 );
      expected_std[i]  = rnd( 1, 5 );
    }

    for ( int k=0; k<SAMPLES; k++ ) {
      double[] src = new double[DIMS];
      //System.out.format( "%3d", k );
      for ( int i=0; i<DIMS; i++ ) {
        double x = expected_mean[i] + expected_std[i] * dd.normal();
        if ( x < expected_min[i] ) { expected_min[i] = x; }
        if ( x > expected_max[i] ) { expected_max[i] = x; }
        src[i] = x;
        //System.out.format( ", %10.5f", x );
      }
      //System.out.format("\n");
      samples.add( new ClusterPoint( src ) );
    }

    EuclideanCluster stats = new EuclideanCluster( DIMS, 1 );

    stats.add( samples );
    int n = stats.recenter();

    System.out.format( "Euclidean test expected %d samples got %d\n\n", SAMPLES, n );
    for ( int i=0; i<DIMS; i++ ) {
      System.out.format( "  %3d Minimum expected %9.5f got %9.5f\n", i, expected_min[i],  stats.getMin(i)   );
      System.out.format( "      Mean    expected %9.5f got %9.5f\n",    expected_mean[i], stats.getMean(i)  );
      System.out.format( "      Maximum expected %9.5f got %9.5f\n",    expected_max[i],  stats.getMax(i)   );
      System.out.format( "      Stddev  expected %9.5f got %9.5f\n\n",  expected_std[i],  stats.getStdev(i) );
    }
    
  }
  
  // =====================================================================================
  static void Test_Gauss() {
    // -----------------------------------------------------------------------------------
    List<ClusterPoint> samples = new LinkedList<ClusterPoint>();

    double[] expected_min  = new double[DIMS];
    double[] expected_max  = new double[DIMS];
    double[] expected_mean = new double[DIMS];
    double[] expected_std  = new double[DIMS];

    for ( int i=0; i<DIMS; i++ ) {
      expected_min[i]  =  1.0e100;
      expected_max[i]  = -1.0e100;
      expected_mean[i] = rnd( -30, 30 );
      expected_std[i]  = rnd( 1, 5 );
    }

    for ( int k=0; k<SAMPLES; k++ ) {
      double[] src = new double[DIMS];
      //System.out.format( "%3d", k );
      for ( int i=0; i<DIMS; i++ ) {
        double x = expected_mean[i] + expected_std[i] * dd.normal();
        if ( x < expected_min[i] ) { expected_min[i] = x; }
        if ( x > expected_max[i] ) { expected_max[i] = x; }
        src[i] = x;
        //System.out.format( ", %10.5f", x );
      }
      //System.out.format("\n");
      samples.add( new ClusterPoint( src ) );
    }

    GaussianCluster stats = new GaussianCluster( DIMS, 1 );

    stats.add( samples );
    int n = stats.recenter();

    System.out.format( "Guassian test expected %d samples got %d\n\n", SAMPLES, n );
    for ( int i=0; i<DIMS; i++ ) {
      System.out.format( "  %3d Minimum expected %9.5f got %9.5f\n", i, expected_min[i],  stats.getMin(i)   );
      System.out.format( "      Mean    expected %9.5f got %9.5f\n",    expected_mean[i], stats.getMean(i)  );
      System.out.format( "      Maximum expected %9.5f got %9.5f\n",    expected_max[i],  stats.getMax(i)   );
      System.out.format( "      Stddev  expected %9.5f got %9.5f\n\n",  expected_std[i],
                         Math.sqrt(stats.getCov(i,i)) );
    }
    
    for ( int r=0; r<DIMS; r++ ) {
      for ( int c=0; c<DIMS; c++ ) {
        System.out.format( "%12.5f", stats.getCov(r,c) );
      }
      System.out.format( "\n" );
    }
  }
  
  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
    Dice.getInstance().seed_set();
    
    Test_Euclid();
    Test_Gauss();
  }

} // end class jtest_cls_stats

// =======================================================================================
// **                           J T E S T _ C L S _ S T A T S                           **
// ======================================================================== END FILE =====
