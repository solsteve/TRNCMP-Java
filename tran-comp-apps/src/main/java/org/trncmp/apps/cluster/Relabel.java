// ====================================================================== BEGIN FILE =====
// **                           C L U S T E R / R E L A B E L                           **
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
 * Relabel a configuration file. 
 *
 * @author  Stephen W. Soliday
 * @date    2018-11-29
 */
// =======================================================================================

package org.trncmp.apps.cluster;

import java.io.PrintStream;

import java.util.List;
import java.util.LinkedList;

import org.trncmp.lib.AppOptions;
import org.trncmp.lib.ConfigDB;
import org.trncmp.lib.array;
import org.trncmp.lib.Math2;
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
public class Relabel {
  // -------------------------------------------------------------------------------------
  /** Logging */
  private static final Logger logger = LogManager.getLogger();

   
  // =====================================================================================
  static class myusage implements AppOptions.Usage {
    // -----------------------------------------------------------------------------------
    public void display( String pn ) {
      // ---------------------------------------------------------------------------------
      System.err.println( "" );    
      System.err.println( "Clustering Classifier * Relabel model * 2018" );    
      System.err.println( "" );    
      System.err.println( "USAGE: JRun "+pn+" [options]" );
      System.err.println( "   smod - path to a cluster config file from supervised   training" );
      System.err.println( "   umod - path to a cluster config file from unsupervised learning" );
      System.err.println( "   mod  - path to a relabeld cluster config file" );
      System.err.println( "   tp   - E[uclidean] or G[aussian]" );
      System.err.println( "" );    
      System.err.println( "Example: JRun cluster.Relabel smod=/tmp/sup-test-e.cfg "+
                          "umod=/tmp/unsup-test-e.cfg mod=/tmp/unsup-relabeled-e.cfg tp=E" );    
      System.err.println( "" );    
    }
  }


  // =====================================================================================
  public static void  exclude( double array[][], int row, int col, double val ) {
    // -----------------------------------------------------------------------------------
    int nr = array.length;
    int nc = array[0].length;

    for ( int r=0; r<nr; r++ ) { array[r][col] = val; }
    for ( int c=0; c<nc; c++ ) { array[row][c] = val; }
  }


  // =====================================================================================
  protected static int process( Classifier s_cls,
                                Classifier u_cls,
                                String outputFilename ) {
    // -----------------------------------------------------------------------------------

    if ( null == s_cls ) {
      logger.error( "supervised classifier configuration was not read" );
      return 2;
    }

    if ( null == s_cls ) {
      logger.error( "unsupervised classifier configuration was not read" );
      return 3;
    }

    int ns = s_cls.count();
    int nu = u_cls.count();

    if ( ns != nu ) {
      logger.error( "supervised and unsupervised classifiers have different class counts" );
      return 1;
    }

    int[]          s_label   = s_cls.getLabels();
    int[]          u_label   = u_cls.getLabels();

    Cluster[]      s_cluster = new Cluster[ns];
    Cluster[]      u_cluster = new Cluster[nu];
    ClusterPoint[] u_point   = new ClusterPoint[nu];

    for ( int i=0; i<ns; i++ ) {
      s_cluster[i] = s_cls.get(s_label[i]);
    }

    for ( int i=0; i<nu; i++ ) {
      u_cluster[i] = u_cls.get(u_label[i]);
      if ( null == u_cluster[i] ) {
        logger.error( "Class not retrieved" );
        return 3;
      }
      u_point[i] = new ClusterPoint.Builder()
          .coords( u_cluster[i].getMeans() )
          .id(u_cluster[i].getID())
          .build();
    }

    double[][] dist = new double[nu][ns];

    for ( int u=0; u<nu; u++ ) {
      for ( int s=0; s<ns; s++ ) {
        dist[u][s] = s_cluster[s].distanceSquared( u_point[u] );
      }
    }

    //System.out.println( array.toString( dist, "%9.4f", "\n", " " ) );

    for ( int i=0; i<ns; i++ ) {
      int[] idx = Math2.minarg( dist );
 
      //System.out.format( "Excluding %d %d\n", idx[0], idx[1] );

      exclude( dist, idx[0], idx[1], 1.0e99 );

      u_cluster[idx[0]].setID( s_label[idx[1]] ); 
    }

    u_cls.write( outputFilename );
    return 0;
  }

  
  // =====================================================================================
  protected static void processGaussian( String trainedFilename,
                                         String learnedFilename,
                                         String outputFilename ) {
    // -----------------------------------------------------------------------------------

    GaussianClassifier sup   = GaussianClassifier.read( trainedFilename );
    GaussianClassifier unsup = GaussianClassifier.read( learnedFilename );

    unsup.write( outputFilename );
  }

  
  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------
    AppOptions.cli_map[] CLI = {
      //               name   sect   cfgkey    req.   def    description
      AppOptions.INIT( "smod", "APP", "supmod",  true,  null,  "path to a model file" ),
      AppOptions.INIT( "umod", "APP", "unsmod",  true,  null,  "path to a model file" ),
      AppOptions.INIT( "mod",  "APP", "relmod",  true,  null,  "path to a model file" ),
      AppOptions.INIT( "tp",  "APP",  "type",    true,  null,  "type [E] or [G]" ),
    };

    AppOptions.init( CLI, new myusage() );
    AppOptions.setAppName( "cluster.Relabel" );
    AppOptions.setConfigBase( "app" );
    AppOptions.setOptConfigFilename( "cfg" );
    AppOptions.setHelp( "help" );
    AppOptions.setCommandLine( args );

    ConfigDB cfg = AppOptions.getConfigDB();

    if ( null != cfg ) {

      try {
    
        char   type      = cfg.get( "APP", "type").charAt(0);
        String trained   = cfg.get( "APP", "supmod");
        String learned   = cfg.get( "APP", "unsmod");
        String relabeled = cfg.get( "APP", "relmod");

        switch( type ) {
          case 'E':
          case 'e':
            process( EuclideanClassifier.read( trained ),
                     EuclideanClassifier.read( learned ),
                     relabeled );
            break;
          case 'G':
          case 'g':
            process( GaussianClassifier.read( trained ),
                     GaussianClassifier.read( learned ),
                     relabeled );
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

  
} // end class Relabled


// =======================================================================================
// **                           C L U S T E R / R E L A B E L                           **
// =========================================================================== END FILE ==
