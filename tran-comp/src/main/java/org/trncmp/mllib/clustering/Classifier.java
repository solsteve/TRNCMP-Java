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
 * Provides interface for a Clustering Classifier.
 * <p>
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
  private static final Logger logger = LogManager.getRootLogger();

  /** Array of clusters */
  IntegerMap<Cluster> cluster = null;

  /** number of dimensions. */
  int num_dims = 0;

  // =====================================================================================
  public static abstract class Builder<T extends Builder<T>> {
    // -----------------------------------------------------------------------------------

    protected int                      num_centers      = 0;
    protected int                      num_dims         = 0;

    protected List<ClusterPoint>       provided_centers = null;
    protected List<List<ClusterPoint>> provided_samples = null;

    protected int                      init_method      = 0;
    
    protected abstract T getThis();

    // ===================================================================================
    /** Set number of dimensions                                                        */
    // -----------------------------------------------------------------------------------
    public T dims( int d ) {
      // ---------------------------------------------------------------------------------
      num_dims = d;

      return getThis();
    }

    // ===================================================================================
    /** Set number of dimensions                                                        */
    // -----------------------------------------------------------------------------------
    public T centers( int c ) {
      // ---------------------------------------------------------------------------------
      num_centers = c;

      return getThis();
    }

    // ===================================================================================
    /** Set number of dimensions                                                        */
    // -----------------------------------------------------------------------------------
    public T centers( List<ClusterPoint> C ) {
      // ---------------------------------------------------------------------------------
      provided_centers = C;

      return getThis();
    }

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public T samples( List<List<ClusterPoint>> S ) {
      // ---------------------------------------------------------------------------------
      provided_samples = S;

      return getThis();
    }

  } // end class Classifier.Builder


  // =====================================================================================
  /** Void Constructor.
   */
  // -------------------------------------------------------------------------------------
  protected Classifier( Builder<?> builder ) {
    // -----------------------------------------------------------------------------------

    builder.init_method = 0;
    
    if ( null != builder.provided_centers ) { builder.init_method += 1; }
    if ( null != builder.provided_samples ) { builder.init_method += 2; }
    if ( 0    <  builder.num_centers )      { builder.init_method += 4; }
    if ( 0    <  builder.num_dims )         { builder.init_method += 8; }
    
    switch( builder.init_method ) {
      // ----- these are the only valid combinations -----------------
      case 0:
      case 1:
      case 2:
      case 12:
        logger.debug( "builder.init_method = "+builder.init_method );
        break;
        // -------------------------------------------------------------

      case 3:
      case 7:
      case 11:
      case 15:
        logger.error( ".samples() and .centers(List) cannot be used togeather" );
        System.exit(1);
        break;

      case 4:
      case 8:
        logger.error( ".centers(n) and .dims() must be used togeather" );
        System.exit(1);
        break;
        
      case 5:
        logger.error( ".centers(n) and .centers(List) cannot be used togeather" );
        System.exit(1);
        break;
        
      case 6:
        logger.error( ".centers(n) and .samples() cannot be used togeather" );
        System.exit(1);
        break;

      case 9:
        logger.error( ".dims(n) and .centers(List) cannot be used togeather" );
        System.exit(1);
        break;
        
      case 10:
        logger.error( ".dims() and .samples() cannot be used togeather" );
        System.exit(1);
        break;

      case 13:
        logger.error( ".centers(n) .dims() and .centers(List) cannot be used togeather" );
        System.exit(1);
        break;
        
      case 14:
        logger.error( ".centers(n) .dims() and .samples() cannot be used togeather" );
        System.exit(1);
        break;
        
      default:
        logger.error( "this really should not have happened" );
        System.exit(1);
        break;
    }

    cluster = new IntegerMap<Cluster>();
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public int count() {
    // -----------------------------------------------------------------------------------
    return cluster.size();
  }

  
  // =====================================================================================
  /** Get Count.
   *  @return number of samples compiled.
   */
  // -------------------------------------------------------------------------------------
  public int dims() {
    // -----------------------------------------------------------------------------------
    return num_dims;
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
  // -------------------------------------------------------------------------------------
  protected void clear_clusters() {
    // -----------------------------------------------------------------------------------
    IntegerMap.Iterator<Cluster> it = cluster.iterator();
    while ( it.hasNext() ) {
      it.next().clear();
    }
  }


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void recenter_clusters() {
    // -----------------------------------------------------------------------------------
    IntegerMap.Iterator<Cluster> it = cluster.iterator();
    while ( it.hasNext() ) {
      Cluster C = it.next();
      if ( 1 < C.count() ) {
        C.recenter();
      } else {
        System.err.println( "Empty cluster detected" );
      }
    }
  }


  // =====================================================================================
  public int[] getLabels() {
    // -----------------------------------------------------------------------------------
    return cluster.getKeys();
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
    num_dims = cls.dims();
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

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void learn( List<ClusterPoint> points, int max_gen ) {
    // -----------------------------------------------------------------------------------

    for (int gen=0; gen<max_gen; gen++) {

      clear_clusters();
        
      for ( ClusterPoint point : points ) {
        point.remove_cluster_assignment();
        int label = classify( point );
        cluster.get(label).add( point );
      }
      
      recenter_clusters();

    }
    
  }    




} // end class Classifier

// =======================================================================================
// **                                C L A S S I F I E R                                **
// ======================================================================== END FILE =====
