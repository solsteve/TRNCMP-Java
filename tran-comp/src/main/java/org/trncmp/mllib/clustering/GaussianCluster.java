// ====================================================================== BEGIN FILE =====
// **                           G A U S S I A N C L U S T E R                           **
// =======================================================================================
// **                                                                                   **
// **  This file is part of the TRNCMP Research Library. (formerly SolLib)              **
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
 * @file GaussianCluster.java
 * <p>
 * Provides interface and methods for a Cluster based on Gaussian statistics.
 *
 * @date 2018-11-13
 *
 */
// =======================================================================================

package org.trncmp.mllib.clustering;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.List;
import org.trncmp.lib.linear.Matrix;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// =======================================================================================
public class GaussianCluster extends Cluster {
  // -------------------------------------------------------------------------------------

  /** Logging */
  private static final Logger logger = LogManager.getLogger();

  /** Covariance of the ClusterPoints in the members list. */
  protected Matrix covariance = null;

  /** Inverse of the Covariance matrix. This is the precision matrix. */
  protected Matrix inv_cov    = null;

  
  // =====================================================================================
  /** Constructor
   *  @param n number of dimensions.
   */
  // -------------------------------------------------------------------------------------
  public GaussianCluster( int n, int id_num ) {
    // -----------------------------------------------------------------------------------
    resize(n); // set the parent class member data
    covariance = new Matrix(n,n);
    inv_cov    = new Matrix(n,n);
    id         = id_num;
  }


  // =====================================================================================
  /** Constructor
   *  @param P   point representing the Cluter's center..
   *  @param cov covariance of the cluster.
   */
  // -------------------------------------------------------------------------------------
  public GaussianCluster( ClusterPoint P, Matrix cov, int id_num ) {
    // -----------------------------------------------------------------------------------
    int n = P.coord_dims();
    resize(n); // set the parent class member data
    covariance = cov;
    inv_cov    = new Matrix(n,n);
    inv_cov.invert(cov);
    id         = id_num;
  }
    

  // =====================================================================================
  /** Constructor
   *  @param init_pop members in this cluster.
   */
  // -------------------------------------------------------------------------------------
  public GaussianCluster( List<ClusterPoint> init_pop, int id_num ) {
    // -----------------------------------------------------------------------------------
    if ( 0 < init_pop.size() ) {
      int n = init_pop.get(0).coord_dims();
      resize(n); // set the parent class member data
    
      covariance = new Matrix(n,n);
      inv_cov    = new Matrix(n,n);
      id         = id_num;

      add( init_pop );
      recenter();
    } else {
      logger.error( "Data point list is empty." );
      System.exit(1);
    }
  }






  // =====================================================================================
  /** Get Covariance.
   *  @param r row index.
   *  @param c column index.
   *  @return covariance between the row and col variables.
   */
  // -------------------------------------------------------------------------------------
  public double getCov( int r, int c ) {
    // -----------------------------------------------------------------------------------
    return covariance.A[r][c];
  }

  
  // =====================================================================================
  /** Get inverse Covariance.
   *  @param r row index.
   *  @param c column index.
   *  @return inverse covariance between the row and col variables.
   */
  // -------------------------------------------------------------------------------------
  public double getInvCov( int r, int c ) {
    // -----------------------------------------------------------------------------------
    return inv_cov.A[r][c];
  }

  
  // =====================================================================================
  /** Re-center.
   *  <p>
   *  Compute cluster statistics.
   */
  // -------------------------------------------------------------------------------------
  @Override
  public int recenter() {
    // -----------------------------------------------------------------------------------
    for ( int r=0; r<num_var; r++ ) {
      for ( int c=0; c<num_var; c++ ) {
        covariance.A[r][c] = 0.0e0;
        inv_cov.A[r][c]    = 1.0e0;
      }
    }

    int n = basic_stats();

    if ( 0 < n ) {
      for ( int r=0; r<num_var; r++ ) {
        double rmean = mean_val[r];
        for ( int c=r; c<num_var; c++ ) {
          double cmean = mean_val[c];
          double c_sum = 0.0e0;
          for ( ClusterPoint sample : members ) {
            double rx = sample.get_coord(r) - rmean;
            double cx = sample.get_coord(c) - cmean;
            c_sum += ( rx * cx );
          }
          c_sum /= (double)(n - 1);
          covariance.A[r][c] = c_sum;
          covariance.A[c][r] = c_sum;
        }
      }

      inv_cov.invert( covariance );
    }

    return n;
  }

  
  // =====================================================================================
  /** Mahalonobis Distance.
   *  @param P sample ClusterPoint.
   *  @return scalar square of the Mahalonobis distance metric.
   *  <p>
   *  Compute the square of the Mahalonobis distance between a sample point and these
   *  statistics.
   */
  // -------------------------------------------------------------------------------------
  @Override
  public double distanceSquared( ClusterPoint P ) {
    // -----------------------------------------------------------------------------------
    double[] x = P.get_coord();
    double   a = 0.0e0;

    for ( int i=0; i<num_var; i++ ) {
      double t = 0.0e0;
      for ( int j=0; j<num_var; j++ ) {
        t += ( inv_cov.A[i][j] * ( x[j] - mean_val[j] ) );
      }
      a += ( t * ( x[i] - mean_val[i] ) );
    }
    return a;
  }

 
  // =====================================================================================
  /** Write.
   *  @param ps reference to an open PrintStream.
   */
  // -------------------------------------------------------------------------------------
  @Override
  public void write( PrintStream ps ) {
    // -----------------------------------------------------------------------------------
    ps.format( "%d %d\n", id, num_var );
    for ( int i=0; i<num_var; i++ ) {
      ps.format( "%17.10e %17.10e %17.10e\n",
                 min_val[i], max_val[i], mean_val[i] );
    }
    
    for ( int r=0; r<num_var; r++ ) {
      ps.format( "%17.10e", covariance.A[r][r] );
      if ( r < (num_var+1) ) {
        for ( int c=r+1; c<num_var; c++ ) {
          ps.format( " %17.10e", covariance.A[r][c] );
        }
      }
      ps.format( "\n" );
    }
    
  }    




  // =====================================================================================
  /** Read.
   *  @param br reference to an open Scanner.
   */
  // -------------------------------------------------------------------------------------
  static public GaussianCluster read( Scanner inp ) {
    // -----------------------------------------------------------------------------------

    int cid = inp.nextInt();
    int nvr = inp.nextInt();

    GaussianCluster EC = new GaussianCluster( nvr, cid );

    for ( int i=0; i<nvr; i++ ) {
     EC.min_val[i]    = inp.nextDouble();
     EC.max_val[i]    = inp.nextDouble();
     EC.mean_val[i]   = inp.nextDouble();
    }

    for ( int r=0; r<nvr; r++ ) {
        for ( int c=r; c<nvr; c++ ) {
          EC.covariance.A[r][c] = inp.nextDouble();
          if ( r != c ) {
            EC.covariance.A[c][r] = EC.covariance.A[r][c];
          }
        }
    }

    EC.inv_cov.invert( EC.covariance );

    return EC;
  }

  

} // end class GaussianCluster


// =======================================================================================
// **                           G A U S S I A N C L U S T E R                           **
// ======================================================================== END FILE =====
