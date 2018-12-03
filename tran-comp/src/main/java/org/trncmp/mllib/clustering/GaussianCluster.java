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
  private static final Logger logger = LogManager.getRootLogger();

  /** Covariance of the ClusterPoints in the members list. */
  protected Matrix covariance = null;

  /** Inverse of the Covariance matrix. This is the precision matrix. */
  protected Matrix inv_cov    = null;

  // =====================================================================================
  public static class Builder extends Cluster.Builder<Builder> {
    // -----------------------------------------------------------------------------------
    private Matrix defined_cov = null;

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    @Override
    public Builder getThis() {
      // ---------------------------------------------------------------------------------
      return this;
    }

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public Builder cov( Matrix C ) {
      // ---------------------------------------------------------------------------------
      defined_cov = new Matrix( C );
      return this;
    }

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public GaussianCluster build() {
      // ---------------------------------------------------------------------------------
      return new GaussianCluster(this);
    }

  } // end class GaussianCluster.Builder


  

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void compute_covariance() {
    // -----------------------------------------------------------------------------------
    int n = members.size();
    if ( 0 < n ) {
      if ( ! stats_run ) { basic_stats(); }

      for ( int r=0; r<num_var; r++ ) {
        for ( int c=0; c<num_var; c++ ) {
          covariance.A[r][c] = 0.0e0;
        }
      }

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

    } else {
      logger.error( "There are no members to sample" );
    }
  }

  
  // =====================================================================================
  /** Constructor
   *  @param n number of dimensions.
   */
  // -------------------------------------------------------------------------------------
  protected GaussianCluster(  Builder builder  ) {
    // -----------------------------------------------------------------------------------
    super( builder );

    covariance = new Matrix(num_var,num_var);
    inv_cov    = new Matrix(num_var,num_var);

    if ( stats_run ) {
      compute_covariance();
    } else {
      if ( null != builder.defined_cov ) {
        covariance.copy( builder.defined_cov );
        inv_cov.invert(covariance);
      } else {
        for ( int i=0; i<num_var; i++ ) {
          for ( int j=0; j<num_var; j++ ) {
            double x = ( (i==j) ? (1.0e0) : (0.0e0) );
            covariance.A[i][j] = x;
            inv_cov.A[i][j]    = x;
          }
        }
      }
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
    stats_run = false;
    compute_covariance();
    return members.size();
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

    GaussianCluster GC = new GaussianCluster.Builder()
        .dims(nvr)
        .id(cid)
        .build();

    for ( int i=0; i<nvr; i++ ) {
      GC.min_val[i]    = inp.nextDouble();
      GC.max_val[i]    = inp.nextDouble();
      GC.mean_val[i]   = inp.nextDouble();
    }

    for ( int r=0; r<nvr; r++ ) {
      for ( int c=r; c<nvr; c++ ) {
        GC.covariance.A[r][c] = inp.nextDouble();
        if ( r != c ) {
          GC.covariance.A[c][r] = GC.covariance.A[r][c];
        }
      }
    }

    GC.inv_cov.invert( GC.covariance );

    return GC;
  }


} // end class GaussianCluster


// =======================================================================================
// **                           G A U S S I A N C L U S T E R                           **
// ======================================================================== END FILE =====
