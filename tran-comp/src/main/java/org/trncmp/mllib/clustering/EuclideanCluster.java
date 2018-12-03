// ====================================================================== BEGIN FILE =====
// **                          E U C L I D E A N C L U S T E R                          **
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
 * @file EuclideanCluster.java
 * <p>
 * Provides interface and methods for a Cluster based on Euclidean statistics.
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// =======================================================================================
public class EuclideanCluster extends Cluster {
  // -------------------------------------------------------------------------------------

  /** Logging */
  private static final Logger logger = LogManager.getRootLogger();

  /** Standard deviation of the mean */
  protected double[] stddev_val = null;


  // =====================================================================================
  public static class Builder extends Cluster.Builder<Builder> {
    // -----------------------------------------------------------------------------------
    private double[] defined_stddev = null;

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    @Override
    public Builder getThis() {
      // ---------------------------------------------------------------------------------
      return this;
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public Builder stddev( double[] S ) {
      // ---------------------------------------------------------------------------------
      int n = S.length;
      defined_stddev = new double[n];
      for ( int i=0; i<n; i++ ) {
        defined_stddev[i] = S[i];
      }
      return this;
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public Builder stddev( double[] S, int offset, int length ) {
      // ---------------------------------------------------------------------------------
      defined_stddev = new double[length];
      for ( int i=0; i<length; i++ ) {
        defined_stddev[i] = S[i+offset];
      }
      return this;
    }


    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public EuclideanCluster build() {
      // ---------------------------------------------------------------------------------
      return new EuclideanCluster(this);
    }

  } // end class EuclideanCluster.Builder



  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void compute_stddev() {
    // -----------------------------------------------------------------------------------
    int n = members.size();
    if ( 0 < n ) {
      if ( ! stats_run ) { basic_stats(); }

      for ( int i=0; i<num_var; i++ ) {
        stddev_val[i] = 0.0e0;
      }

      if ( ! stats_run ) { basic_stats(); }

      for ( ClusterPoint sample : members ) {
        double[] x = sample.get_coord();
        for ( int i=0; i<num_var; i++ ) {
          double d = mean_val[i] - x[i];
          stddev_val[i] += (d*d);
        }
      }
      
      for ( int i=0; i<num_var; i++ ) {
        stddev_val[i] = Math.sqrt( stddev_val[i] / (double)(n - 1) );
      }
      
      
    } else {
      logger.error( "There are no members to sample" );
    }
  }


  // =====================================================================================
  /** Constructor
   *  @param n number of dimensions.
   */
  // -------------------------------------------------------------------------------------
  protected EuclideanCluster( Builder builder ) {
    // -----------------------------------------------------------------------------------
    super( builder );

    stddev_val = new double[ num_var ];

    if ( stats_run ) {
      compute_stddev();
    } else {
      for ( int i=0; i<num_var; i++ ) {
        stddev_val[i] = ( (null == builder.defined_stddev) ?
                          (1.0e0) :
                          (builder.defined_stddev[i]) );
      }
    }
  }




  // =====================================================================================
  /** Get Standard Deviations.
   *  @return pointer to the vector containing the component standard deviations.
   */
  // -------------------------------------------------------------------------------------
  public double[] getStdevs() {
    // -----------------------------------------------------------------------------------
    return stddev_val;
  }

  
  // =====================================================================================
  /** Get Standard Deviation.
   *  @param i vector index.
   *  @return the ith vector value.
   */
  // -------------------------------------------------------------------------------------
  public double getStdev( int i ) {
    // -----------------------------------------------------------------------------------
    return stddev_val[i];
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
    compute_stddev();
    return members.size();
  }


  // =====================================================================================
  /** Euclidean Distance.
   *  @param P sample ClusterPoint.
   *  @return scalar square of the Euclidian distance metric.
   *  <p>
   *  Compute the square of the Euclidean distance between a sample point and these
   *  statistics.
   */
  // -------------------------------------------------------------------------------------
  @Override
  public double distanceSquared( ClusterPoint P ) {
    // -----------------------------------------------------------------------------------
    return ClusterPoint.euclid( P.get_coord(), mean_val, num_var );
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
      ps.format( "%17.10f %17.10f %17.10f %17.10f\n",
                 min_val[i], max_val[i], mean_val[i], stddev_val[i] );
    }
  }    


  // =====================================================================================
  /** Read.
   *  @param inp reference to an open Scanner.
   */
  // -------------------------------------------------------------------------------------
  static public EuclideanCluster read( Scanner inp ) {
    // -----------------------------------------------------------------------------------

    int cid = inp.nextInt();
    int nvr = inp.nextInt();

    EuclideanCluster EC = new EuclideanCluster.Builder()
        .dims(nvr)
        .id(cid)
        .build();

    for ( int i=0; i<nvr; i++ ) {
      EC.min_val[i]    = inp.nextDouble();
      EC.max_val[i]    = inp.nextDouble();
      EC.mean_val[i]   = inp.nextDouble();
      EC.stddev_val[i] = inp.nextDouble();
    }

    return EC;
  }    


} // end class EuclideanCluster


// =======================================================================================
// **                          E U C L I D E A N C L U S T E R                          **
// ======================================================================== END FILE =====
