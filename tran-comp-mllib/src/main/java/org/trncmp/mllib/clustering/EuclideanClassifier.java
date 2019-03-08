// ====================================================================== BEGIN FILE =====
// **                       E U C L I D E A N C L A S S I F I E R                       **
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
 * @file EuclideanClassifier.java
 * <p>
 * Provides interface and methods for a Euclidian based Clustering Classifier.
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
public class EuclideanClassifier extends Classifier {
  // -------------------------------------------------------------------------------------

  /** Logging */
  private static final Logger logger = LogManager.getRootLogger();


  // =====================================================================================
  public static class Builder extends Classifier.Builder<Builder> {
    // -----------------------------------------------------------------------------------


    // ===================================================================================
    // -----------------------------------------------------------------------------------
    @Override
    public Builder getThis() {
      // ---------------------------------------------------------------------------------
      return this;
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public EuclideanClassifier build() {
      // ---------------------------------------------------------------------------------
      return new EuclideanClassifier( this );
    }

  } // end class EuclideanClassifier.Builder



  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public EuclideanClassifier( Builder builder ) {
    // -----------------------------------------------------------------------------------
    super( builder );
    
    int key = -1;

    switch( builder.init_method ) {

      case 0:   // ----- empty classifier -----------------------------------------------
        break;

      case 1:   // ----- provided centers -----------------------------------------------

        logger.debug( "Presented with "+builder.provided_centers.size()+" initial centers" );

        key = 1;
        for ( ClusterPoint center : builder.provided_centers ) {
          cluster.set( key,
                       new EuclideanCluster.Builder()
                       .id( key )
                       .mean( center )
                       .build() );
          key += 1;
        }

        break ;
        
      case 2:   // ----- provided samples -----------------------------------------------

        logger.debug( "Presented with "+builder.provided_samples.size()+" unique classes" );

        for ( List<ClusterPoint> group : builder.provided_samples ) {
          int n = group.size();
          if ( 0 < n ) {
            key = group.get(0).member_of_cluster();
        
            cluster.set( key,
                         new EuclideanCluster.Builder()
                         .id( key )
                         .samples( group )
                         .build() );
          }
        }

        break ;
        
      case 12:  // ----- empty classifier -----------------------------------------------

        for ( int i=0; i<builder.num_centers; i++ ) {
          Cluster C = new EuclideanCluster.Builder()
              .id(i)
              .dims(builder.num_dims)
              .build();
          cluster.set( i, C );
        }
        break ;
        
      default:  // ----------------------------------------------------------------------
        logger.error( "this really should not have happened" );
        System.exit(1);
        break;
    }

  }

  // =====================================================================================
  /** Read.
   *  @param fspc Path to a Classifier Configuration file.
   *  <p>
   *  Static.
   */
  // -------------------------------------------------------------------------------------
  static public EuclideanClassifier read( Scanner inp ) {
    // -----------------------------------------------------------------------------------
    EuclideanClassifier cls = new EuclideanClassifier.Builder().build();
    int n = inp.nextInt();
    for ( int i=0; i<n; i++ ) {
      EuclideanCluster EC = EuclideanCluster.read( inp );
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
  static public EuclideanClassifier read( String fspc ) {
    // -----------------------------------------------------------------------------------
    Scanner scn = new Scanner( FileTools.openRead( fspc ) );
    EuclideanClassifier cls = EuclideanClassifier.read( scn );
    scn.close();
    logger.debug( "Successfully read Euclidean classifier." );
    return cls;
  }    

  
} // end class EuclideanClassifier


// =======================================================================================
// **                       E U C L I D E A N C L A S S I F I E R                       **
// ======================================================================== END FILE =====
