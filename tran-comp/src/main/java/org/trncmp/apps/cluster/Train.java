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
 * Perform supervised training using labeled data.
 * <p>
 * @author Stephen W. Soliday
 * @date   2018-11-15
 */
// =======================================================================================

package org.trncmp.apps.cluster;

import java.io.PrintStream;

import org.trncmp.lib.AppOptions;
import org.trncmp.lib.ConfigDB;
import java.util.List;
import java.util.LinkedList;

import org.trncmp.lib.IntegerMap;
import org.trncmp.mllib.clustering.Cluster;
import org.trncmp.mllib.clustering.ClusterPoint;
import org.trncmp.mllib.clustering.Classifier;
import org.trncmp.mllib.clustering.EuclideanClassifier;
import org.trncmp.mllib.clustering.GaussianClassifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// =======================================================================================
public class Train {
  // -------------------------------------------------------------------------------------
  /** Logging */
  private static final Logger logger = LogManager.getRootLogger();


  // =====================================================================================
  protected static void processEuclidean( String inputFilename, String modelFilename ) {
    // -----------------------------------------------------------------------------------
    System.err.println( "" );    
    System.err.println( "Processing "+inputFilename+" as a Euclidian cluster." );
    System.err.println( "" );

    List<ClusterPoint> labeled_data = ClusterPoint.readLabeledPointsFromFile( inputFilename );
    if ( null != labeled_data ) {

      logger.debug( "Read "+labeled_data.size()+" exemplars and labels" );

      List<List<ClusterPoint>> clusters = ClusterPoint.collate( labeled_data );
      
      if ( null != clusters ) {
        EuclideanClassifier cls = new EuclideanClassifier.Builder().samples( clusters ).build(); 
        cls.write( modelFilename );
      } else {
        logger.error( "No clusters were collated" );
      }
    } else {
      logger.error( "Failed to read labeled data" );
    }
  }

  
  // =====================================================================================
  protected static void processGaussian( String inputFilename, String modelFilename ) {
    // -----------------------------------------------------------------------------------
    System.err.println( "" );    
    System.err.println( "Processing "+inputFilename+" as a Gaussian cluster" );
    System.err.println( "" );    
    List<ClusterPoint> labeled_data = ClusterPoint.readLabeledPointsFromFile( inputFilename );
    if ( null != labeled_data ) {

      logger.debug( "Read "+labeled_data.size()+" exemplars and labels" );

      List<List<ClusterPoint>> clusters = ClusterPoint.collate( labeled_data );
      
      if ( null != clusters ) {
        GaussianClassifier cls = new GaussianClassifier.Builder().samples( clusters ).build(); 
        cls.write( modelFilename );
      } else {
        logger.error( "No clusters were collated" );
      }
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
      System.err.println( "Clustering Classifier * Supervised Learning * 2018" );    
      System.err.println( "" );    
      System.err.println( "USAGE: JRun "+pn+" [options]" );
      System.err.println( "   mod - path to save a cluster config file" );
      System.err.println( "   dat - path to a labeled data file" );
      System.err.println( "   tp  - E[uclidean] or G[aussian]" );
      System.err.println( "" );    
      System.err.println( "Example: JRun "+pn+"mod=test-ecl.cfg "+
                          "dat=data/Iris/iris.labeled tp=E" );
      System.err.println( "" );    
    }
  }

  
  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------

    AppOptions.cli_map[] CLI = {
      //               name   sect   cfgkey   req.  def   description
      AppOptions.INIT( "mod", "APP", "model", true, null, "path to a model file" ),
      AppOptions.INIT( "dat", "APP", "data",  true, null, "path to an data file" ),
      AppOptions.INIT( "tp",  "APP", "type",  true, null, "type [E] or [G]" ),
    };

    AppOptions.init( CLI, new myusage() );
    AppOptions.setAppName( "cluster.Train" );
    AppOptions.setConfigBase( "app" );
    AppOptions.setOptConfigFilename( "cfg" );
    AppOptions.setHelp( "help" );
    AppOptions.setCommandLine( args );

    ConfigDB cfg = AppOptions.getConfigDB();

    if ( null != cfg ) {

      try {
    
        char   type        = cfg.get( "APP", "type").charAt(0);
        String modFilename = cfg.get( "APP", "model");
        String datFilename = cfg.get( "APP", "data");

        switch( type ) {
          case 'E':
          case 'e':
            processEuclidean( datFilename, modFilename );
            break;
          case 'G':
          case 'g':
            processGaussian( datFilename, modFilename );
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

  
} // end class Train


// =======================================================================================
// **                             C L U S T E R / T R A I N                             **
// =========================================================================== END FILE ==
