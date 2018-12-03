// ====================================================================== BEGIN FILE =====
// **                             C L U S T E R / L E A R N                             **
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
 * Unsupervised learning.
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
import org.trncmp.lib.Dice;
import org.trncmp.lib.IntegerMap;
import org.trncmp.lib.StringTool;
import org.trncmp.mllib.clustering.Cluster;
import org.trncmp.mllib.clustering.ClusterPoint;
import org.trncmp.mllib.clustering.Classifier;
import org.trncmp.mllib.clustering.EuclideanClassifier;
import org.trncmp.mllib.clustering.GaussianClassifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.log4j.Level;


// =======================================================================================
public class Learn {
  // -------------------------------------------------------------------------------------
  /** Logging */
  private static final Logger logger = LogManager.getLogger();


  // =====================================================================================
  protected static void display( List<ClusterPoint> points ) {
    // -----------------------------------------------------------------------------------
    for ( ClusterPoint point : points ) {
      double[] coord = point.get_coord();
      for ( double x : coord ) {
        System.out.format( "%5.2f ", x );
      }
      System.out.format( " <=> %d\n", point.member_of_cluster() );
    }
  }


  // =====================================================================================
  protected static void processEuclidean( String inputFilename,
                                          String modelFilename,
                                          int init_cls,
                                          int maxgen,
                                          int seed_type ) {
    // -----------------------------------------------------------------------------------
    System.err.println( "" );    
    System.err.println( "Processing "+inputFilename+" as a Euclidian cluster." );
    System.err.println( "" );
    
    List<ClusterPoint> unlabeled_data = ClusterPoint.readPointsFromFile( inputFilename );

    if ( null != unlabeled_data ) {
      logger.debug( "Read "+unlabeled_data.size()+" samples" );

      List<ClusterPoint> centers = ClusterPoint.gen_seed( unlabeled_data,
                                                          init_cls,
                                                          seed_type );
      if ( null != centers ) {
        Classifier cls = new EuclideanClassifier.Builder()
            .centers( centers)
            .build();
        
        cls.learn( unlabeled_data, maxgen );
        
        cls.write( modelFilename );
      } else {
        logger.error( "No clusters were collated" );
      }
    } else {
      logger.error( "Failed to read labeled data" );
    }
  }


  // =====================================================================================
  protected static void processGaussian( String inputFilename,
                                         String modelFilename,
                                         int init_cls,
                                         int maxgen,
                                         int seed_type ) {
    // -----------------------------------------------------------------------------------
    System.err.println( "" );    
    System.err.println( "Processing "+inputFilename+" as a Gaussian cluster." );
    System.err.println( "" );
    
    List<ClusterPoint> unlabeled_data = ClusterPoint.readPointsFromFile( inputFilename );

    if ( null != unlabeled_data ) {
      logger.debug( "Read "+unlabeled_data.size()+" samples" );

      List<ClusterPoint> centers = ClusterPoint.gen_seed( unlabeled_data,
                                                          init_cls,
                                                          seed_type );
      if ( null != centers ) {
        Classifier cls = new GaussianClassifier.Builder()
            .centers( centers)
            .build();
        
        cls.learn( unlabeled_data, maxgen );
        
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
      System.err.println( "Clustering Classifier * Unsupervised Learning * 2018" );    
      System.err.println( "" );    
      System.err.println( "USAGE: JRun "+pn+" [options]" );
      System.err.println( "   mod  - path to save a cluster config file" );
      System.err.println( "   dat  - path to an unlabeled data file" );
      System.err.println( "   tp   - E[uclidean] or G[aussian]" );
      System.err.println( "   nc   - initial number of classes" );
      System.err.println( "   gen  - maximum itterations" );
      System.err.println( "   seed - 0=random, 1=kmeans++" );
      System.err.println( "" );    
      System.err.println( "Example: JRun "+pn+"mod=test-ecl.cfg "+
                          "dat=data/Iris/iris.labeled tp=E nc=3 gen=500" );    
      System.err.println( "" );    
    }
  }

  
  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------
    
    Dice.getInstance().seed_set();

    AppOptions.cli_map[] CLI = {
      //               name    sect   cfgkey    req.   def    description
      AppOptions.INIT( "mod",  "APP", "model",  true,  null,  "path to a model file" ),
      AppOptions.INIT( "dat",  "APP", "data",   true,  null,  "path to an unlabeled data file" ),
      AppOptions.INIT( "tp",   "APP", "type",   true,  null,  "type [E] or [G]" ),
      AppOptions.INIT( "nc",   "APP", "ncls",   true,  null,  "initial number of classes" ),
      AppOptions.INIT( "gen",  "APP", "maxgen", true, "1000", "maximum iterations" ),
      AppOptions.INIT( "seed", "APP", "seed",   true,  null,  "0=rnd, 1=kmeans++" ),
    };

    AppOptions.init( CLI, new myusage() );
    AppOptions.setAppName( "cluster.Learn" );
    AppOptions.setConfigBase( "app" );
    AppOptions.setOptConfigFilename( "cfg" );
    AppOptions.setHelp( "help" );
    AppOptions.setCommandLine( args );

    ConfigDB cfg = AppOptions.getConfigDB();

    if ( null != cfg ) {

      try {
    
        char   type        = cfg.get( "APP", "type").charAt(0);
        int    ncls        = StringTool.asInt32( cfg.get( "APP", "ncls" ) );
        int    maxgen      = StringTool.asInt32( cfg.get( "APP", "maxgen" ) );
        String modFilename = cfg.get( "APP", "model");
        String datFilename = cfg.get( "APP", "data");
        int    seed_type   = StringTool.asInt32( cfg.get( "APP", "seed" ) );

        switch( type ) {
          case 'E':
          case 'e':
            processEuclidean( datFilename, modFilename, ncls, maxgen, seed_type );
            break;
          case 'G':
          case 'g':
            processGaussian( datFilename, modFilename, ncls, maxgen, seed_type );
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

  
} // end class Learn


// =======================================================================================
// **                             C L U S T E R / L E A R N                             **
// =========================================================================== END FILE ==
