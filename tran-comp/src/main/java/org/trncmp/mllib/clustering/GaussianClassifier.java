// ====================================================================== BEGIN FILE =====
// **                        G A U S S I A N C L A S S I F I E R                        **
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
 * @file GaussianClassifier.java
 * <p>
 * Provides interface and methods for a Gaussian based Clustering Classifier.
 *
 * @date 2018-11-13
 *
 */
// =======================================================================================

package org.trncmp.mllib.clustering;

import java.util.List;
import java.io.PrintStream;
import java.util.Scanner;

import org.trncmp.lib.IntegerMap;
import org.trncmp.lib.FileTools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// =======================================================================================
public class GaussianClassifier extends Classifier {
  // -------------------------------------------------------------------------------------

  /** Logging */
  private static final Logger logger = LogManager.getLogger();


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public GaussianClassifier() {
    // -----------------------------------------------------------------------------------
    super();
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public GaussianClassifier( int n, int m ) {
    // -----------------------------------------------------------------------------------
    super();
    for ( int i=0; i<n; i++ ) {
      Cluster C = new GaussianCluster( m, i );
      cluster.set( i, C );
    }
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public GaussianClassifier( List<List<ClusterPoint>> labled_data ) {
    // -----------------------------------------------------------------------------------
    super();
    logger.debug( "Presented with "+labled_data.size()+" unique classes" );

    for ( List<ClusterPoint> group : labled_data ) {
      int n = group.size();
      if ( 0 < n ) {
        int key = group.get(0).member_of_cluster();
        
        cluster.set( key, new GaussianCluster( group, key ) );
      }
    }
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  @Override
  public void learn( List<ClusterPoint> C ) {
    // -----------------------------------------------------------------------------------
  }    


  // =====================================================================================
  /** Read.
   *  @param fspc Path to a Classifier Configuration file.
   *  <p>
   *  Static.
   */
  // -------------------------------------------------------------------------------------
  static public GaussianClassifier read( Scanner inp ) {
    // -----------------------------------------------------------------------------------
    GaussianClassifier cls = new GaussianClassifier();
    int n = inp.nextInt();
    for ( int i=0; i<n; i++ ) {
      GaussianCluster EC = GaussianCluster.read( inp );
      cls.set( EC.getID(), EC );
    }
    
    return cls;
  }


  // =====================================================================================
  /** Read.
   *  @param fspc Path to a Classifier Configuration file.
   *  <p>
   *  Static.
   */
  // -------------------------------------------------------------------------------------
  static public GaussianClassifier read( String fspc ) {
    // -----------------------------------------------------------------------------------
    Scanner scn = new Scanner( FileTools.openRead( fspc ) );
    GaussianClassifier cls = GaussianClassifier.read( scn );
    scn.close();
    logger.debug( "Successfully read Gaussian classifier." );
    return cls;
  }    


} // end class GaussianClassifier


// =======================================================================================
// **                        G A U S S I A N C L A S S I F I E R                        **
// ======================================================================== END FILE =====
