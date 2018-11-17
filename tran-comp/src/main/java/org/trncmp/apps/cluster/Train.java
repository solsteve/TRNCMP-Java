// ====================================================================== BEGIN FILE =====
// **                             C L U S T E R / T R A I N                             **
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
 * @brief   
 * @file    
 *
 * @details Provides the interface and procedures 
 *
 * @author  Stephen W. Soliday
 * @date    2018-11-15
 */
// =======================================================================================

package org.trncmp.apps.cluster;

import java.io.PrintStream;

import java.util.List;
import java.util.LinkedList;
import java.util.List;

import org.trncmp.lib.IntegerMap;
import org.trncmp.mllib.clustering.Cluster;
import org.trncmp.mllib.clustering.ClusterPoint;
import org.trncmp.mllib.clustering.Classifier;
import org.trncmp.mllib.clustering.EuclideanClassifier;
import org.trncmp.mllib.clustering.GaussianClassifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.log4j.Level;

// =======================================================================================
public class Train {
  // -------------------------------------------------------------------------------------
  /** Logging */
  private static final Logger logger = LogManager.getLogger();

  // =====================================================================================
  protected static void displayResult( PrintStream ps, int[][] R ) {
    // -----------------------------------------------------------------------------------
    int nr = R.length;
    int nc = R[0].length;

    ps.format( "\n       Truth\n      " );
    for ( int c=0; c<nc; c++ ) {
      ps.format( "   %3d", c );   
    }
    ps.format( "\n Class +-----+-----+-----+\n" );

    for ( int r=0; r<nr; r++ ) {
      ps.format( "  %3d  |", r );
      for ( int c=0; c<nc; c++ ) {
        ps.format( " %3d |", R[r][c] );   
      }
      ps.format( "\n       +-----+-----+-----+\n" );    
    }
    
  }


  // =====================================================================================
  protected static int[][] Test( Classifier cls, List<ClusterPoint>  labeled_data) {
    // -----------------------------------------------------------------------------------
    IntegerMap<Integer> map = ClusterPoint.constructClassMap( labeled_data );

    int n = cls.size();
    int[][] result = new int[n][n];
    for ( int r=0; r<n; r++ ) {
      for ( int c=0; c<n; c++ ) {
        result[r][c] = 0;
      }
    }
    
    for ( ClusterPoint point : labeled_data ) {
      int label = map.get( point.member_of_cluster() );
      int test  = map.get( cls.classify( point ) );          
      result[test][label] += 1;
    }

    return result;
  }


  // =====================================================================================
  protected static void processEuclidean( String inputFilename ) {
    // -----------------------------------------------------------------------------------
    System.err.println( "" );    
    System.err.println( "Processing "+inputFilename+" as a Euclidian cluster." );
    System.err.println( "" );
    List<ClusterPoint> labeled_data = ClusterPoint.readLabeledPointsFromFile( inputFilename );
    if ( null != labeled_data ) {

      logger.debug( "Read "+labeled_data.size()+" exemplars and labels" );

      List<List<ClusterPoint>> clusters = ClusterPoint.collate( labeled_data );
      
      if ( null != clusters ) {

        Classifier cls = new EuclideanClassifier( clusters );

        int[][] result = Test( cls, labeled_data );

        displayResult( System.out, result );
      } else {
        logger.error( "No clusters were collated" );
      }
    } else {
        logger.error( "Failed to read labeled data" );
    }
  }
    
  // =====================================================================================
  protected static void processGaussian( String inputFilename ) {
    // -----------------------------------------------------------------------------------
    System.err.println( "" );    
    System.err.println( "Processing "+inputFilename+" as a Gaussian cluster" );
    System.err.println( "" );    
    List<ClusterPoint> labeled_data = ClusterPoint.readLabeledPointsFromFile( inputFilename );
    if ( null != labeled_data ) {

      logger.debug( "Read "+labeled_data.size()+" exemplars and labels" );

      List<List<ClusterPoint>> clusters = ClusterPoint.collate( labeled_data );
      
      if ( null != clusters ) {

        Classifier cls = new GaussianClassifier( clusters );

        int[][] result = Test( cls, labeled_data );

        displayResult( System.out, result );
      } else {
        logger.error( "No clusters were collated" );
      }
    } else {
        logger.error( "Failed to read labeled data" );
    }
  }
    
  // =====================================================================================
  protected static void usage() {
    // -----------------------------------------------------------------------------------
    System.err.println( "" );    
    System.err.println( "Clustering Classifier * Supervised Learning * 2018" );    
    System.err.println( "" );    
    System.err.println( "USAGE: JRun cluster.Train type input.dat" );
    System.err.println( "   type      - E[uclidean] or G[aussian]" );
    System.err.println( "   input.dat - path to a file containing labeled data" );
    System.err.println( "" );    
    System.err.println( "Example: JRun cluster.Train E data/Iris/iris.labeled" );    
    System.err.println( "" );    
  }

  
  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------

    if ( 2 != args.length ) {
      usage();
      System.exit(1);
    }

    char type = args[0].charAt(0);
    
    switch( type ) {
      case 'E':
      case 'e':
        processEuclidean( args[1] );
        break;
      case 'G':
      case 'g':
        processGaussian( args[1] );
        break;
      default:
        System.err.println( "" );
        System.err.println( "wrong type provided [" +
                            type + "], Please use E or G" );
        usage();
        System.exit(2);
    }

    System.exit(0);
  }

  
} // end class Train


// =======================================================================================
// **                             C L U S T E R / T R A I N                             **
// =========================================================================== END FILE ==
