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
 * @file ClusterPoint.java
 * <p>
 * Provides interface for a sample point used in clustering.
 *
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// =======================================================================================
public class ClusterPoint {
  // -------------------------------------------------------------------------------------

  /** Logging */
  private static final Logger logger = LogManager.getLogger();

  /** point coordinates in N-dimensional space. */
  protected double[] cluster_coord = null;

  /** Index of the cluster this point may be assigned to.
   *  Negative number indicates no assignment
   */
  protected int cluster_index = -1;


  // =====================================================================================
  /** Initialize.
   *  @param src source for the data.
   *  @param clu cluster index to assign this point to.
   *  <p>
   *  Initialize this point using src for the data and clu for the cluster asignment.
   */
  // -------------------------------------------------------------------------------------
  protected void init_cluster_point( double[] src, int clu ) {
    // -----------------------------------------------------------------------------------
    cluster_coord = src;
    cluster_index = clu;
  }
  

  // =====================================================================================
  /** Constructor.
   *  <p>
   *  Void constructor.
   */
  // -------------------------------------------------------------------------------------
  public ClusterPoint() {
    // -----------------------------------------------------------------------------------
    cluster_coord          = null;
    cluster_index = -1;
  }

  
  // =====================================================================================
  /** Constructor.
   *  @param n number of dimensions.
   *  <p>
   *  Create an empty point with space for n dimesions.
   */
  // -------------------------------------------------------------------------------------
  public ClusterPoint( int n ) {
    // -----------------------------------------------------------------------------------
    init_cluster_point( new double[n], -1 );
  }

  
  // =====================================================================================
  /** Constructor.
   *  @param src source for the data.
   *  <p>
   *  Create an unassigned point using src as the source for the data.
   */
  // -------------------------------------------------------------------------------------
  public ClusterPoint( double[] src ) {
    // -----------------------------------------------------------------------------------
    init_cluster_point( src, -1 );
  }

  
  // =====================================================================================
  /** Constructor.
   *  @param src source for the data.
   *  @param clu cluster index to assign this point to.
   *  <p>
   *  Create an assigned point using src as the source for the data,
   *  and clu for the assignment.
   */
  // -------------------------------------------------------------------------------------
  public ClusterPoint( double[] src, int clu ) {
    // -----------------------------------------------------------------------------------
    init_cluster_point( src, clu );
  }


  // =====================================================================================
  /** Clone.
   *  @return deep copy. 
   */
  // -------------------------------------------------------------------------------------
  public ClusterPoint clone() {
    // -----------------------------------------------------------------------------------
    ClusterPoint np = new ClusterPoint( this.cluster_coord );
    np.assign_to_cluster( this.member_of_cluster() );
    return np;
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
   *  @param src source data
   *  <p>
   *  Copy the source data.
   *  @note this will leave remove any previous assignment.
   */
  // -------------------------------------------------------------------------------------
  public void fromDoubleArray( double[] src ) {
    // -----------------------------------------------------------------------------------
    int n = src.length;
    if ( n == cluster_coord.length ) {
      for ( int i=0; i<n; i++ ) {
        cluster_coord[i] = src[i];
      }
      remove_cluster_assignment();
    } else {
      init_cluster_point( src, -1 );
    }
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
    int nc = data.cols();

    for ( int r=0; r<nr; r++ ) {
      ClusterPoint pt = new ClusterPoint( nc );
      pt.load_coord( data.row(r), 0 );
      pt.remove_cluster_assignment();
      pop.add( pt );
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
      ClusterPoint pt = new ClusterPoint( nc );
      pt.load_coord( data.row(r), 0 );
      pt.assign_to_cluster( (int) label.get(r,0) );
      pop.add( pt );
    }

    return pop;
  }



  
  // =====================================================================================
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


} // end class ClusterPoint


// =======================================================================================
// **                              C L U S T E R P O I N T                              **
// ======================================================================== END FILE =====
