// ====================================================================== BEGIN FILE =====
// **                          C L U S T E R / E V A L U A T E                          **
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
 * Evaluate cluster model using labled data.
 * <p>
 * @author  Stephen W. Soliday
 * @date    2018-11-26
 */
// =======================================================================================

package org.trncmp.apps.cluster;

import java.io.PrintStream;

import java.util.List;
import java.util.LinkedList;

import org.trncmp.lib.AppOptions;
import org.trncmp.lib.ConfigDB;
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
public class Evaluate {
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

    int n = cls.count();
    int[][] result = new int[n][n];
    for ( int r=0; r<n; r++ ) {
      for ( int c=0; c<n; c++ ) {
        result[r][c] = 0;
      }
    }

    if ( null == cls ) { System.out.println( "Classifier NULL" ); }
    
    for ( ClusterPoint point : labeled_data ) {
      if ( null == point ) { System.out.println( "Point NULL" ); }
      int a = point.member_of_cluster();
      int b = cls.classify( point );
      int label = map.get( point.member_of_cluster() );
      int test  = map.get( cls.classify( point ) );
      result[test][label] += 1;
    }

    return result;
  }

  
  // =====================================================================================
  protected static int[][] TestE( String fspc, List<ClusterPoint>  labeled_data) {
    // -----------------------------------------------------------------------------------
    EuclideanClassifier EC = EuclideanClassifier.read( fspc );
    return Test( EC, labeled_data);
  }


  // =====================================================================================
  protected static int[][] TestG( String fspc, List<ClusterPoint>  labeled_data) {
    // -----------------------------------------------------------------------------------
    GaussianClassifier GC = GaussianClassifier.read( fspc );
    return Test( GC, labeled_data);
  }


  // =====================================================================================
  protected static void processEuclidean( String modelFilename, String inputFilename ) {
    // -----------------------------------------------------------------------------------
    System.err.println( "" );    
    System.err.println( "Processing "+inputFilename+" as a Euclidian cluster." );
    System.err.println( "" );
    List<ClusterPoint> labeled_data = ClusterPoint.readLabeledPointsFromFile( inputFilename );
    if ( null != labeled_data ) {
      displayResult( System.out, TestE( modelFilename, labeled_data ) );
    } else {
      logger.error( "Failed to read labeled data" );
    }
  }

  
  // =====================================================================================
  protected static void processGaussian( String modelFilename, String inputFilename ) {
    // -----------------------------------------------------------------------------------
    System.err.println( "" );    
    System.err.println( "Processing "+inputFilename+" as a Gaussian cluster" );
    System.err.println( "" );    
    List<ClusterPoint> labeled_data = ClusterPoint.readLabeledPointsFromFile( inputFilename );
    if ( null != labeled_data ) {
      displayResult( System.out, TestG( modelFilename, labeled_data ) );
    } else {
      logger.error( "Failed to read labeled data" );
    }
  }

  
  // =====================================================================================
  static class myusage implements AppOptions.Usage {
    // -----------------------------------------------------------------------------------
    public void display( String pn ) {
      // ---------------------------------------------------------------------------------
      System.err.println( "" );    
      System.err.println( "Clustering Classifier * Evaluate model * 2018" );    
      System.err.println( "" );    
      System.err.println( "USAGE: JRun "+pn+" [options]" );
      System.err.println( "   mod - path to save a cluster config file" );
      System.err.println( "   dat - path to a labeled data file" );
      System.err.println( "   tp  - E[uclidean] or G[aussian]" );
      System.err.println( "" );    
      System.err.println( "Example: JRun "+pn+" mod=/tmp/unsup-relabeled-e.cfg"+
                          " dat=data/Iris/iris.labeled tp=E" );    
      System.err.println( "" );
    }
  }

  
  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------
    
    AppOptions.cli_map[] CLI = {
      //               name   sect   cfgkey   req.  def   description
      AppOptions.INIT( "mod", "APP", "model", true, null, "path to a model file" ),
      AppOptions.INIT( "dat", "APP", "data",  true, null, "path to a labeled data file" ),
      AppOptions.INIT( "tp",  "APP", "type",  true, null, "type [E] or [G]" ),
    };

    AppOptions.init( CLI, new myusage() );
    AppOptions.setAppName( "cluster.Evaluate" );
    AppOptions.setConfigBase( "app" );
    AppOptions.setOptConfigFilename( "cfg" );
    AppOptions.setHelp( "help" );
    AppOptions.setCommandLine( args );

    ConfigDB cfg = AppOptions.getConfigDB();

    if ( null != cfg ) {

      try {
    
        char type          = cfg.get( "APP", "type").charAt(0);
        String modFilename = cfg.get( "APP", "model");
        String datFilename = cfg.get( "APP", "data");
    
        switch( type ) {
          case 'E':
          case 'e':
            processEuclidean( modFilename, datFilename );
            break;
          case 'G':
          case 'g':
            processGaussian( modFilename, datFilename );
            break;
          default:
            System.err.println( "" );
            System.err.println( "wrong type provided [" +
                                type + "], Please use E or G" );
            AppOptions.usage();
            System.exit(2);
        }
        
        System.exit(0);

      } catch( ConfigDB.NoSuchKey e1 ) {
        logger.error( e1.toString() );
      } catch( ConfigDB.NoSection e2 ) {
        logger.error( e2.toString() );
      }
    }

    System.exit(1);
  }

  
} // end class Evaluate


// =======================================================================================
// **                             C L U S T E R / L E A R N                             **
// =========================================================================== END FILE ==
