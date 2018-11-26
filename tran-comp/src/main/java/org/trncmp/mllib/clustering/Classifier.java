// ====================================================================== BEGIN FILE =====
// **                                C L A S S I F I E R                                **
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
 * @file Classifier.java
 * <p>
 * Provides interface for a Clustering Classifier.
 *
 * @date 2018-11-13
 *
 */
// =======================================================================================

package org.trncmp.mllib.clustering;

import java.util.List;
import java.io.PrintStream;

import org.trncmp.lib.IntegerMap;
import org.trncmp.lib.Math2;
import org.trncmp.lib.FileTools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// =======================================================================================
abstract public class Classifier {
  // -------------------------------------------------------------------------------------

  /** Logging */
  private static final Logger logger = LogManager.getLogger();

  /** Array of clusters */
  IntegerMap<Cluster> cluster = null;

  public int size() { return cluster.size(); }
  
  // =====================================================================================
  /** Void Constructor.
   */
  // -------------------------------------------------------------------------------------
  Classifier() {
    // -----------------------------------------------------------------------------------
    logger.debug( "Executing root constructor" );
    cluster = new IntegerMap<Cluster>();
  }

  // =====================================================================================
  /** Classify Point.
   *  @param P reference to a ClusterPoint.
   *  @return the index of the closest cluster.
   */
  // -------------------------------------------------------------------------------------
  public int classify( ClusterPoint P ) {
    // -----------------------------------------------------------------------------------

    int    closest_cluster = -1;
    double minimum_metric  = Math2.N_MAX_POS;

    IntegerMap.Iterator<Cluster> it = cluster.iterator();
    while( it.hasNext() ) {
      Cluster C = it.next();
      double  m = C.distanceSquared(P);
      if ( m < minimum_metric ) {
        minimum_metric  = m;
        closest_cluster = C.getID();
      }
    }
    
    return closest_cluster;
  }


  // =====================================================================================
  public Cluster get( Integer key ) {
    // -----------------------------------------------------------------------------------
    return cluster.get( key );
  }

  
  // =====================================================================================
  public void set( Integer key, Cluster cls ) {
    // -----------------------------------------------------------------------------------
    cluster.set( key, cls );
  }



  
  // =====================================================================================
  /** Write.
   *  @param ps reference to an open PrintStream.
   */
  // -------------------------------------------------------------------------------------
  public void write( PrintStream ps ) {
    // -----------------------------------------------------------------------------------
    ps.format( "%d\n", cluster.size() );
    IntegerMap.Iterator<Cluster> it = cluster.iterator();
    while ( it.hasNext() ) {
      it.next().write( ps );
    }
  }    


  // =====================================================================================
  /** Write.
   *  @param fspc Path to a file to save the configuration.
   */
  // -------------------------------------------------------------------------------------
  public void write( String fspc ) {
    // -----------------------------------------------------------------------------------
    PrintStream ps = FileTools.openWrite( fspc );
    write( ps );
    ps.close();
  }    


  abstract public void learn    ( List<ClusterPoint> C );


} // end class Classifier

// =======================================================================================
// **                                C L A S S I F I E R                                **
// ======================================================================== END FILE =====
