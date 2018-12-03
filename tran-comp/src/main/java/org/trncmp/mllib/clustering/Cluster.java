// ====================================================================== BEGIN FILE =====
// **                                   C L U S T E R                                   **
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
 * @file Cluster.java
 * <p>
 * Provides interface and methods for a parent Cluster class.
 *
 * @date 2018-11-13
 *
 */
// =======================================================================================

package org.trncmp.mllib.clustering;

import java.io.PrintStream;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

import org.trncmp.lib.Table;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public abstract class Cluster {
  // -------------------------------------------------------------------------------------

  /** Logging */
  private static final Logger logger = LogManager.getRootLogger();

  /** Identification Number */
  protected int id;
  
  /** Number of variables */
  protected int num_var = 0;

  /** Members of this cluster */
  protected List<ClusterPoint> members = null;

  /** Minimum values. */
  protected double[] min_val       = null;
  protected double[] min_val_temp  = null;

  /** Maximum values */
  protected double[] max_val       = null;
  protected double[] max_val_temp  = null;

  /** Mean values */
  protected double[] mean_val      = null;
  protected double[] mean_val_temp = null;

  protected boolean stats_run = false;

  // =====================================================================================
  public static abstract class Builder<T extends Builder<T>> {
    // -----------------------------------------------------------------------------------

    private int                id_number            = -1;
    private int                number_of_dimensions =  0;
    private double[]           defined_mean         =  null;
    private List<ClusterPoint> provided_samples     =  null;

    
    protected abstract T getThis();

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public T id( int i ) {
      // ---------------------------------------------------------------------------------
      id_number = i;
      return getThis();
    }


    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public T dims( int d ) {
      // ---------------------------------------------------------------------------------
      number_of_dimensions = d;
      return getThis();
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public T mean( ClusterPoint P ) {
      // ---------------------------------------------------------------------------------
      int n = P.coord_dims();
      
      defined_mean         = new double[n];

      for ( int i=0; i<n; i++ ) {
        defined_mean[i] = P.get_coord(i);
      }

      return getThis();
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public T mean( double[] mu ) {
      // ---------------------------------------------------------------------------------
      int n = mu.length;
      
      defined_mean         = new double[n];

      for ( int i=0; i<n; i++ ) {
        defined_mean[i] = mu[i];
      }

      return getThis();
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public T mean( double[] mu, int offset, int length ) {
      // ---------------------------------------------------------------------------------

      defined_mean = new double[length];
      
      for ( int i=0; i<length; i++ ) {
        defined_mean[i] = mu[i+offset];
      }

      return getThis();
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public T samples( List<ClusterPoint> list ) {
      // ---------------------------------------------------------------------------------
      provided_samples = list;

      return getThis();
    }

  } // end class Cluster.Builder


  // =====================================================================================
  /** Initialize.
   *  @param n number of dimensions.
   */
  // -------------------------------------------------------------------------------------
  protected void resize( int n ) {
    // -----------------------------------------------------------------------------------
    num_var       = n;
    min_val       = new double[num_var];
    max_val       = new double[num_var];
    mean_val      = new double[num_var];
    min_val_temp  = new double[num_var];
    max_val_temp  = new double[num_var];
    mean_val_temp = new double[num_var];
  }

  
  // =====================================================================================
  /** Constructor */
  // -------------------------------------------------------------------------------------
  protected Cluster( Builder<?> builder ) {
    // -----------------------------------------------------------------------------------

    int init_method = 0;

    if ( 0 < builder.number_of_dimensions ) { init_method += 1; }
    if ( null != builder.defined_mean )     { init_method += 2; }
    if ( null != builder.provided_samples ) { init_method += 4; }

    id      = builder.id_number;
    members = new LinkedList<ClusterPoint>();
    
    switch( init_method ) {
      case 0: // ----- nothing was provided for creating a cluster -----------------------
        logger.error( "one of .dims() .mean() .samples() must be used" );
        System.exit(1);
        break;
        
      case 1:  // ----- only dims were supplied ------------------------------------------
        num_var = builder.number_of_dimensions;
        resize( num_var );
        break;

      case 3: // ----- mean was supplied -------------------------------------------------
        logger.warn( ".dims() is not necessary when using .mean()" );
      case 2:
        num_var = builder.defined_mean.length;
        resize( num_var );
        for ( int i=0; i<num_var; i++ ) {
          mean_val[i] = builder.defined_mean[i];
          min_val[i]  = mean_val[i];
          max_val[i]  = mean_val[i];
        }
        stats_run = false;
        break;

      case 5: // ----- samples were supplied ---------------------------------------------
        logger.warn( ".dims() is not necessary when using .samples()" );
      case 4:
        num_var = builder.provided_samples.get(0).coord_dims();
        resize( num_var );
        add( builder.provided_samples );
        if ( 0 == basic_stats() ) {
          logger.error( "No samples were found" );
          System.exit(1);
        }
        break;

      case 6: // ----- both samples and mean were provided -----------------------------
        logger.error("Only use .mean() or .samples() not both");
        System.exit(1);
        break;

      case 7:
      default: // ----- wrong number of things were provided ---------------------------
        logger.error( "one of .dims() .mean() .samples() must be used" );
        System.exit(1);
        break;
    } // end switch
    
  }


  // =====================================================================================
  /** Basic Statisitics.
   *  <p>
   *  Set the basic statistics for this cluster based on the ClusterPoints in the members list.
   */
  // -------------------------------------------------------------------------------------
  protected int basic_stats() {
    // -----------------------------------------------------------------------------------
    stats_run = false;

    for ( int i=0; i<num_var; i++ ) {
      // --- save previous values -------------------------------
      min_val_temp[i]  = min_val[i];
      max_val_temp[i]  = max_val[i];
      mean_val_temp[i] = mean_val[i];

      min_val[i]  =  1.0e299;
      max_val[i]  = -1.0e299;
      mean_val[i] = 0.0e0;
    }
    
    int number_of_samples = 0;
    
    for ( ClusterPoint sample : members ) {
      double[] x = sample.get_coord();
      for ( int i=0; i<num_var; i++ ) {
        if ( x[i] < min_val[i] ) { min_val[i] = x[i]; }
        if ( x[i] > max_val[i] ) { max_val[i] = x[i]; }
        mean_val[i] += x[i];
      }
      number_of_samples += 1;
    }

    if ( 0 < number_of_samples ) {
      for ( int i=0; i<num_var; i++ ) {
        mean_val[i] /= (double)number_of_samples;
      }
      stats_run = true;
    }

    return number_of_samples;
  }





  // =====================================================================================
  /** Clear.
   *  <p>
   *  Remove all members from this cluster.
   */
  // -------------------------------------------------------------------------------------
  public void clear() {
    // -----------------------------------------------------------------------------------
    members.clear();
  }

  
  // =====================================================================================
  /** Add.
   *  @param P reference to a ClusterPoint.
   *  <p>
   *  Add a new ClusterPoint to this class's members list.
   */
  // -------------------------------------------------------------------------------------
  public void add( ClusterPoint P ) {
    // -----------------------------------------------------------------------------------
    P.assign_to_cluster( id );
    members.add( P );
  }

  
  // =====================================================================================
  /** Add.
   *  @param P reference to a list of ClusterPoints.
   *  <p>
   *  Add a list of ClusterPoints to this class's members list.
   */
  // -------------------------------------------------------------------------------------
  public void add( List<ClusterPoint> C ) {
    // -----------------------------------------------------------------------------------
    for ( ClusterPoint P : C ) {
      add( P );
    }
  }


  // =====================================================================================
  /** Get ID.
   *  @return identification number of this cluster.
   */
  // -------------------------------------------------------------------------------------
  public int  getID() {
    // -----------------------------------------------------------------------------------
    return id;
  }

  
  // =====================================================================================
  /** Set ID.
   *  @param i new identification number for this cluster.
   */
  // -------------------------------------------------------------------------------------
  public void setID( int i ) {
    // -----------------------------------------------------------------------------------
    id = i;
  }


  // =====================================================================================
  /** Get Minimums.
   *  @return pointer to the vector containing the component minimums.
   */
  // -------------------------------------------------------------------------------------
  public double[] getMins() {
    // -----------------------------------------------------------------------------------
    return min_val;
  }

  
  // =====================================================================================
  /** Get Minimum.
   *  @param i vector index.
   *  @return the ith vector value.
   */
  // -------------------------------------------------------------------------------------
  public double getMin( int i ) {
    // -----------------------------------------------------------------------------------
    return min_val[i];
  }

    
  // =====================================================================================
  /** Get Maximums.
   *  @return pointer to the vector containing the component maximums.
   */
  // -------------------------------------------------------------------------------------
  public double[] getMaxs() {
    // -----------------------------------------------------------------------------------
    return max_val;
  }

  
  // =====================================================================================
  /** Get Maximum.
   *  @param i vector index.
   *  @return the ith vector value.
   */
  // -------------------------------------------------------------------------------------
  public double getMax( int i ) {
    // -----------------------------------------------------------------------------------
    return max_val[i];
  }

    
  // =====================================================================================
  /** Get Means.
   *  @return pointer to the vector containing the component .
   */
  // -------------------------------------------------------------------------------------
  public double[] getMeans() {
    // -----------------------------------------------------------------------------------
    return mean_val;
  }

  
  // =====================================================================================
  /** Get Mean.
   *  @param i vector index.
   *  @return the ith vector value.
   */
  // -------------------------------------------------------------------------------------
  public double getMean( int i ) {
    // -----------------------------------------------------------------------------------
    return mean_val[i];
  }


  // =====================================================================================
  /** Get Count.
   *  @return number of samples compiled.
   */
  // -------------------------------------------------------------------------------------
  public double count() {
    // -----------------------------------------------------------------------------------
    return members.size();
  }


  // =====================================================================================
  /** Get Count.
   *  @return number of samples compiled.
   */
  // -------------------------------------------------------------------------------------
  public int dims() {
    // -----------------------------------------------------------------------------------
    return num_var;
  }


  abstract public int    recenter        ();
  abstract public double distanceSquared ( ClusterPoint P );

  abstract public void    write          ( PrintStream ps );

  
} // end class Cluster


// =======================================================================================
// **                                   C L U S T E R                                   **
// ======================================================================== END FILE =====
