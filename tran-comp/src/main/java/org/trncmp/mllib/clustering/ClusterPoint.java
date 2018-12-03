// ====================================================================== BEGIN FILE =====
// **                                     P O I N T                                     **
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
 * Provides interface for a sample point used in clustering.
 * <p>
 * @date 2018-11-13
 *
 */
// =======================================================================================

package org.trncmp.mllib.clustering;

import java.util.List;
import java.util.LinkedList;

import org.trncmp.lib.IntegerMap;
import org.trncmp.lib.Table;
import org.trncmp.lib.linear.Matrix;
import org.trncmp.lib.Dice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// =======================================================================================
public class ClusterPoint {
  // -------------------------------------------------------------------------------------

  public static final int UNIFORM_SEED  = 0;
  public static final int KMEANSPP_SEED = 1;
  
  /** Logging */
  private static final Logger logger = LogManager.getRootLogger();

  /** point coordinates in N-dimensional space. */
  protected double[] cluster_coord = null;

  /** Index of the cluster this point may be assigned to.
   *  Negative number indicates no assignment
   */
  protected int cluster_index = -1;


  // =====================================================================================
  public static class Builder {
    // -----------------------------------------------------------------------------------

    /** coordinates */
    private double[] cluster_coord = null;

    /** identification number of the cluster tis point is a member of */
    private int      id_number     = -1;

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public Builder dims( int d ) {
      // ---------------------------------------------------------------------------------
      cluster_coord = new double[d];
      for ( int i=0; i<d; i++ ) {
        cluster_coord[i] = 0.0e0;
      }
      return this;
    }
    
    // ===================================================================================
    public Builder coords( double[] src ) {
      // ---------------------------------------------------------------------------------
      int d = src.length;
      cluster_coord = new double[d];
      for ( int i=0; i<d; i++ ) {
        cluster_coord[i] = src[i];
      }
      return this;
    }
    
    // ===================================================================================
    public Builder coords( double[] src, int offset, int d ) {
      // ---------------------------------------------------------------------------------
      cluster_coord = new double[d];
      for ( int i=0; i<d; i++ ) {
        cluster_coord[i] = src[i+offset];
      }
      return this;
    }
    
    // ===================================================================================
    public Builder id( int i ) {
      // ---------------------------------------------------------------------------------
      id_number = i;
      return this;
    }
    
    // ===================================================================================
    public Builder copy( ClusterPoint src ) {
      // ---------------------------------------------------------------------------------
      int d = src.coord_dims();
      cluster_coord = new double[d];
      for ( int i=0; i<d; i++ ) {
        cluster_coord[i] = src.get_coord(i);
      }
      id_number = src.member_of_cluster();
      return this;     
    }

    // ===================================================================================
    public ClusterPoint build() {
      // ---------------------------------------------------------------------------------
      return new ClusterPoint(this);
    }
    
    
  } // end class ClusterPoint.Builder



  
  // =====================================================================================
  /** Constructor.
   *  <p>
   *  Void constructor.
   */
  // -------------------------------------------------------------------------------------
  protected ClusterPoint( Builder builder ) {
    // -----------------------------------------------------------------------------------
    cluster_coord = builder.cluster_coord;
    cluster_index = builder.id_number;
  }


 
  // =====================================================================================
  /** Clone.
   *  @return deep copy. 
   */
  // -------------------------------------------------------------------------------------
  public ClusterPoint clone() {
    // -----------------------------------------------------------------------------------
    return new ClusterPoint.Builder()
        .copy( this )
        .build();
  }






  // =====================================================================================
  /** Get Point Data.
   *  @return pointer to the data vector.
   *  <p>
   *  Return a pointer to the vector containing this points coordinates.
   */
  // -------------------------------------------------------------------------------------
  public double[] get_coord() {
    // -----------------------------------------------------------------------------------
    return cluster_coord;
  }

  
  // =====================================================================================
  /** Get Element of Point Data.
   *  @param i index to the data vector.
   *  @return value at the indexed element.
   *  <p>
   *  Return the value of the ith vector component.
   */
  // -------------------------------------------------------------------------------------
  public double get_coord( int i ) {
    // -----------------------------------------------------------------------------------
    return cluster_coord[i];
  }

  
  // =====================================================================================
  /** Size.
   *  @return data vector length.
   *  <p>
   *  Return then number of dimensions represented by this point.
   */
  // -------------------------------------------------------------------------------------
  public int coord_dims() {
    // -----------------------------------------------------------------------------------
    return cluster_coord.length;
  }

  
  // =====================================================================================
  /** Set.
   *  @param i index to the data vector.
   *  @param v value to set.
   *  <p>
   *  Set the value of the ith vector component to v.
   */
  // -------------------------------------------------------------------------------------
  public void set_coord( int i, double v ) {
    // -----------------------------------------------------------------------------------
    cluster_coord[i] = v;
  }

  
  // =====================================================================================
  /** Load.
   *  @param src pointer to a source array.
   *  @param offset offset from begining of source array.
   *  @return next available location in source array.
   *  <p>
   *  Load this ClusterPoint's coordinates from a source array.
   */
  // -------------------------------------------------------------------------------------
  public int load_coord( double[] src, int offset ) {
    // -----------------------------------------------------------------------------------
    int n = cluster_coord.length;
    for ( int i=0; i<n; i++ ) {
      cluster_coord[i] = src[i+offset];
    }
    return offset;
  }

  
  // =====================================================================================
  /** Store.
   *  @param dst pointer to a destination array.
   *  @param offset offset from begining of destination array.
   *  @return next available location in destination array.
   *  <p>
   *  Stiore this ClusterPoint's coordinates into a destination array.
   */
  // -------------------------------------------------------------------------------------
  public int store_coord( double[] dst, int offset ) {
    // -----------------------------------------------------------------------------------
    int n = cluster_coord.length;
    for ( int i=0; i<n; i++ ) {
      dst[i+offset] = cluster_coord[i];
    }
    return offset;
  }

  
  // =====================================================================================
  /** Assigned.
   *  @return true if this point has been assigned to a cluster.
   *  <p>
   *  Determine whether this point has been assigned to a cluster.
   */
  // -------------------------------------------------------------------------------------
  public boolean assigned_to_cluster() {
    // -----------------------------------------------------------------------------------
    return ( (0 > cluster_index) ? false : true );
  }

  
  // =====================================================================================
  /** Get Cluster index.
   *  @return cluster index
   *  <p>
   *  Return a cluster index for this point.
   *  @note a negative value indicates that this point is unassigned.
   */
  // -------------------------------------------------------------------------------------
  public int member_of_cluster() {
    // -----------------------------------------------------------------------------------
    return cluster_index;
  }

  
  // =====================================================================================
  /** Copy.
   *  @param index of the cluster.
   *  <p>
   *  Assign this point as a member of the ith cluster.
   */
  // -------------------------------------------------------------------------------------
  public void assign_to_cluster( int i ) {
    // -----------------------------------------------------------------------------------
    cluster_index = i;
  }

  
  // =====================================================================================
  /** Remove Asignment.
   *  <p>
   *  Set the cluster assignment to -1.
   */
  // -------------------------------------------------------------------------------------
  public void remove_cluster_assignment() {
    // -----------------------------------------------------------------------------------
    cluster_index = -1;
  }

  // =====================================================================================
  /** Read Points.
   *  @param fspc path to a file containing data.
   *  @return List containing the data points.
   *  <p>
   *  Read a data file containg only data and no labels.
   *  <pre>
   *  r c
   *  X11 X12 X13 ... X1c
   *  X21 X22 X23 ... X2c
   *  X31 X32 X33 ... X3c
   *   :   :   :   \   : 
   *  Xr1 Xr2 Xr3 ... Xrc
   */
  // -------------------------------------------------------------------------------------
  public static List<ClusterPoint> readPointsFromFile( String fspc ) {
    // -----------------------------------------------------------------------------------
    Table data  = new Table();
    
    if ( Table.read_ascii(  data, fspc ) ) {
      logger.error( "Failed to read "+fspc+" for unlabeled input" );
      return null;
    }
    
    List<ClusterPoint> pop = new LinkedList<ClusterPoint>();

    int nr = data.rows();

    for ( int r=0; r<nr; r++ ) {
      pop.add( new ClusterPoint.Builder()
               .coords( data.row(r) )
               .build() );
    }
   
    return pop;
  }


  // =====================================================================================
  /** Read Points.
   *  @param fspc path to a file containing data.
   *  @return List containing the data points.
   *  <p>
   *  Read a data file containg both data and labels.
   *  <pre>
   *  r c 1
   *  X11 X12 X13 ... X1c  L1
   *  X21 X22 X23 ... X2c  L2
   *  X31 X32 X33 ... X3c  L3
   *   :   :   :   \   :   :
   *  Xr1 Xr2 Xr3 ... Xrc  Lr
   */
  // -------------------------------------------------------------------------------------
  public static List<ClusterPoint> readLabeledPointsFromFile( String fspc ) {
    // -----------------------------------------------------------------------------------
    Table data  = new Table();
    Table label = new Table();
    
    if ( Table.read_ascii(  data, label, fspc ) ) {
      logger.error( "Failed to read "+fspc+" for labeled input" );
      return null;
    }
    
    if ( 1 < label.cols() ) {
      logger.warn( "Labels have "+label.cols()+" columns only the first is being used" );
    }
    
    List<ClusterPoint> pop = new LinkedList<ClusterPoint>();

    int nr = data.rows();
    int nc = data.cols();

    for ( int r=0; r<nr; r++ ) {
      pop.add( new ClusterPoint.Builder()
               .coords( data.row(r) )
               .id( (int) label.get(r,0) )
               .build() );
    }

    return pop;
  }



  
  // =====================================================================================
  // Map labels (a,s,d,f...) -> (0,1,2,3...)
  // -------------------------------------------------------------------------------------
  static public IntegerMap<Integer> constructClassMap( List<ClusterPoint> points ) {
    // -----------------------------------------------------------------------------------

    IntegerMap<Integer> map = new IntegerMap<Integer>();

    int cidx = 0;
    for ( ClusterPoint point : points ) {
      if ( point.assigned_to_cluster() ) {
        Integer label = new Integer( point.member_of_cluster() );
        if ( ! map.hasKey( label ) ) {
          map.set( label, new Integer( cidx ) );
          cidx += 1;
        }
      }
    }

    if ( 0 < cidx ) {
      return map;
    }

    logger.error( "No labels processed" );
    
    return null;
  }


  // =====================================================================================
  // Group Labeled Data
  // -------------------------------------------------------------------------------------
  static public List<List<ClusterPoint>> collate( List<ClusterPoint>  points ) {
    // -----------------------------------------------------------------------------------

    int[] class_id  = constructClassMap( points ).getKeys();
    int   num_class = class_id.length;

    logger.debug( "Map has "+num_class+" unique labels" );

    List<List<ClusterPoint>> list = new LinkedList<List<ClusterPoint>>();

    for ( int index=0; index<num_class; index++ ) {
      int id = class_id[ index ];

      List<ClusterPoint> group = new LinkedList<ClusterPoint>();
      for ( ClusterPoint point : points ) {
        int point_label = point.member_of_cluster();
        if ( id == point_label ) {
          group.add( point.clone() );
        }
      }
      list.add( group );
    }
    return list;
  }




  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected boolean isEqual( ClusterPoint P, double tol ) {
    // -----------------------------------------------------------------------------------
    int n = cluster_coord.length;

    for ( int i=0; i<n; i++ ) {
      double dif = this.cluster_coord[i] - P.cluster_coord[i];
      if ( dif < -tol ) return false;
      if ( dif >  tol ) return false;
    }
    return true;
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  static boolean not_in_list( ClusterPoint[] list, int n, ClusterPoint test ) {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<n; i++ ) {
      if ( test.isEqual( list[i], 1.0e-8 ) ) return false;
    }
    return true;
  }

  
  // =====================================================================================
  /** Generate Initial Centers.
   *  @param data list of points to sample.
   *  @param num_seed desired number of initial centroids.
   *  @param method 0=random, 1=k-means++, 3....
   *  @return list of the initial seed centrioids.
   */
  // -------------------------------------------------------------------------------------
  public static List<ClusterPoint> gen_seed( List<ClusterPoint> data, int num_seed,
                                             int method ) {
    // -----------------------------------------------------------------------------------
    if ( UNIFORM_SEED==method )  return gen_seed_random( data, num_seed );
    if ( KMEANSPP_SEED==method ) return gen_seed_kmeanspp( data, num_seed );
    logger.warn( "No method exists for ("+method+") reverting to uniform random" );
    return gen_seed_random( data, num_seed );
  }


  

  // =====================================================================================
  /** Generate Initial Centers uning uniform random.
   *  @param data list of points to sample.
   *  @param num_seed desired number of initial centroids.
   *  @return list of the initial seed centrioids.
   */
  // -------------------------------------------------------------------------------------
  public static List<ClusterPoint> gen_seed_random( List<ClusterPoint> data, int num_seed ) {
    // -----------------------------------------------------------------------------------

    Dice dd = Dice.getInstance();

    ClusterPoint[] points = data.toArray(new ClusterPoint[0]);
    int            n      = points.length;
    int[]          index  = new int[n];

    // -----------------------------------------------------------------------------------

    for ( int i=0; i<n; i++ ) {
      index[i] = i;
    }
    dd.scramble( index, n, 3*n );

    // -----------------------------------------------------------------------------------

    List<ClusterPoint> seed = new LinkedList<ClusterPoint>();
    for ( int i=0; i<num_seed; i++ ) {
      seed.add( new ClusterPoint.Builder()
                .copy(points[index[i]])
                .build() );
    }
    
    return seed;
  }


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public static double euclid( double[] a, double[] b, int n ) {
    // -----------------------------------------------------------------------------------
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      double d = a[i] - b[i];
      sum += (d*d);
    }
    return sum;
  }

  
  // =====================================================================================
  /** Generate Initial Centers using k-means++.
   *  @param data list of points to sample.
   *  @param num_seed desired number of initial centroids.
   *  @return list of the initial seed centrioids.
   */
  // -------------------------------------------------------------------------------------
  public static List<ClusterPoint> gen_seed_kmeanspp( List<ClusterPoint> data, int num_seed ) {
    // -----------------------------------------------------------------------------------

    Dice dd = Dice.getInstance();

    List<ClusterPoint> seed = new LinkedList<ClusterPoint>();
    ClusterPoint[] points  = data.toArray(new ClusterPoint[0]);
    int            num_pts = points.length;
    boolean[]      taken   = new boolean[num_pts];

    for ( int i=0; i<num_pts; i++ ) {
      taken[i] = false;
    }

    // -----------------------------------------------------------------------------------

    int first_idx = dd.uniform(num_pts);
    ClusterPoint P = points[ first_idx ];
    taken[first_idx] = true;
    int num_dim = P.coord_dims();

    seed.add( new ClusterPoint.Builder()
              .copy(P)
              .build() );
    
    double[] minD2 = new double[num_pts];

    for ( int i=0; i<num_pts; i++ ) {
      if ( i != first_idx ) {
        minD2[i] = euclid( P.get_coord(), points[i].get_coord(), num_dim );
      } else {
        minD2[i] = 0.0e0;
      }
    }

    for ( int k=1; k<num_seed; k++ ) {

      double sumD2 = 0.0e0;
      for ( int i=0; i<num_pts; i++ ) {
        if ( ! taken[i] ) {
          sumD2 += minD2[i];
        }
      }

      double r = dd.uniform() * sumD2;

      int next = -1;

      double sum = 0.0e0;
      for ( int i=0; i<num_pts; i++ ) {
        if ( ! taken[i] ) {
          sum += minD2[i];
          if ( ! (sum < r) ) {
            next = i;
            break;
          }
        }
      }

      if ( 0 > next ) {
        do {
          next = dd.uniform(num_pts);
        } while( taken[next] );
      }

      P = points[next];
      taken[next] = true;
      seed.add( new ClusterPoint.Builder()
                .copy(P)
                .build() );
      
      for ( int i=0; i<num_pts; i++ ) {
        if ( ! taken[i] ) {
          double d = euclid( P.get_coord(), points[i].get_coord(), num_dim );
          double d2 = d*d;
          if ( d2 < minD2[i] ) {
            minD2[i] = d2;
          }
        }
      }

    }

    return seed;
  }


} // end class ClusterPoint


// =======================================================================================
// **                              C L U S T E R P O I N T                              **
// ======================================================================== END FILE =====
