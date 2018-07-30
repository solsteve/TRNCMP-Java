// ====================================================================== BEGIN FILE =====
// **                                    K D T R E E                                    **
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
 * @brief   K-D Tree.
 * @file    KDTree.java
 *
 * @details Provides the interface and procedures for generating k-d search trees.
 *
 * @author  Stephen W. Soliday
 * @date    2018-07-28
 */
// =======================================================================================

package org.trncmp.lib;

import java.io.PrintStream;

import org.trncmp.lib.array;


// =======================================================================================
public class KDTree {
  // -------------------------------------------------------------------------------------

  // =====================================================================================
  static class kd_node_t {
    // -----------------------------------------------------------------------------------
    /** k-dimensional point coordinates */
    double[] x = null;

    /** left branch of the tree from this node. */
    kd_node_t left = null;

    /** right branch of the tree from this node. */
    kd_node_t right = null;

    // ===================================================================================
    public kd_node_t( double[] _x ) {
      // ---------------------------------------------------------------------------------
      int n = _x.length;
      this.x = new double[n];
      for ( int i=0; i<n; i++ ) {
        this.x[i] = _x[i];
      }
    }

    // ===================================================================================
    public void destroy() {
      // ---------------------------------------------------------------------------------
      x     = null;
      left  = null;
      right = null;
    }

  } // end class KDTree.kd_note_t



  /** Root node in this tree */
  kd_node_t root = null;

  // =====================================================================================
  public static boolean isNotZero( double a ) {
    // -----------------------------------------------------------------------------------
    return ( 0 == Double.compare(0.0e0, a) ? false : true );
  }

  
  // =====================================================================================
  public static double distSQ( double[] a, double[] b ) {
    // -----------------------------------------------------------------------------------
    int n = a.length;
    double d2 = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      double d = a[i] - b[i];
      d2 += (d*d);
    }
    return d2;
  }

  // =====================================================================================
  protected static void insert( kd_node_t root_node, kd_node_t leaf_node, int dim, int max_dim ) {
    // -----------------------------------------------------------------------------------

    if ( leaf_node.x[dim] < root_node.x[dim] ) {
      if ( null == root_node.left ) {
        root_node.left = leaf_node;
      } else {
        insert( root_node.left, leaf_node, (dim+1)%max_dim, max_dim );
      }
    } else {
      if ( leaf_node.x[dim] > root_node.x[dim] ) {
        if ( null == root_node.right ) {
          root_node.right = leaf_node;
        } else {
          insert( root_node.right, leaf_node, (dim+1)%max_dim, max_dim );
        }
      } else {
        System.err.println( "Duplicate node" );
      }
    }

  }


  // =====================================================================================
  public void insert( double[] x ) {
    // -----------------------------------------------------------------------------------
    kd_node_t node = new kd_node_t( x );
    
    if ( null == root ) {
      root = node;
    } else {
      insert( root, node, 0, x.length );
    }
  }

  // =====================================================================================
  public void insert( double[][] list ) {
    // -----------------------------------------------------------------------------------
    int samples = list.length;
    for ( int i=0; i<samples; i++ ) {
      insert( list[i] );
    }
  }

  // =====================================================================================
  public KDTree() {
    // -----------------------------------------------------------------------------------
    root = null;
  }

  // =====================================================================================
  public KDTree( double[] x ) {
    // -----------------------------------------------------------------------------------
    root = null;
    insert( x );
  }

  // =====================================================================================
  public KDTree( double[][] list ) {
    // -----------------------------------------------------------------------------------
    root = null;
    insert( list );
  }


  // =====================================================================================
  public static int exhaustive_search( double[][] list, double[] x ) {
    // -----------------------------------------------------------------------------------
    double min_d2 = distSQ( list[0], x );
    int    min_id = 0;

    int samples = list.length;
    for ( int i=1; i<samples; i++ ) {
      double d2 = distSQ( list[i], x );
      if ( d2 < min_d2 ) {
        min_d2 = d2;
        min_id = i;
      }
    }
    return min_id;
  }



  
  // =====================================================================================
  static protected void recursive_print( kd_node_t node, PrintStream ps, String fmt ) {
    // -----------------------------------------------------------------------------------
    if ( null != node ) {
        recursive_print( node.left,  ps, fmt );
      
        ps.println(array.toString(node.x, fmt) );

         recursive_print( node.right, ps, fmt );
     }
  }

  // =====================================================================================
  public void print( PrintStream ps, String fmt ) {
    // -----------------------------------------------------------------------------------
    recursive_print( root,  ps, fmt );
  }

  

} // end class KDTree


// =======================================================================================
// **                                    K D T R E E                                    **
// ======================================================================== END FILE =====
