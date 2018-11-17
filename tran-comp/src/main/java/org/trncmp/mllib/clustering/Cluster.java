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
  private static final Logger logger = LogManager.getLogger();

  /** Identification Number */
  protected int id;
  
  /** Members of this cluster */
  protected List<ClusterPoint> members = null;

  /** Number of variables */
  protected int num_var = 0;

  /** Minimum values. */
  protected double[] min_val  = null;

  /** Maximum values */
  protected double[] max_val  = null;

  /** Mean values */
  protected double[] mean_val = null;

  
  // =====================================================================================
  /** Initialize.
   *  @param n number of dimensions.
   */
  // -------------------------------------------------------------------------------------
  protected void resize( int n ) {
    // -----------------------------------------------------------------------------------
    num_var  = n;
    min_val  = new double[num_var];
    max_val  = new double[num_var];
    mean_val = new double[num_var];
  }

  
  // =====================================================================================
  /** Constructor
   *  @param n number of dimensions.
   */
  // -------------------------------------------------------------------------------------
  public Cluster() {
    // -----------------------------------------------------------------------------------
    members = new LinkedList<ClusterPoint>();
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
  /** Basic Statisitics.
   *  @param samples.
   *  <p>
   *  Set the basic statistics for this cluster based on the ClusterPoints in the members list.
   */
  // -------------------------------------------------------------------------------------
  protected int basic_stats() {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<num_var; i++ ) {
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
    }

    return number_of_samples;
  }


  abstract public int    recenter        ();
  abstract public double distanceSquared ( ClusterPoint P );








} // end class Cluster
     
// =======================================================================================
// **                                   C L U S T E R                                   **
// ======================================================================== END FILE =====
