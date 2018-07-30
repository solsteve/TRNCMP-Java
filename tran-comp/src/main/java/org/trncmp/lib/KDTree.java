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
      d += (d*d);
    }
    return d2;
  }

  
  // =====================================================================================
  public static void swap( double[] a, double[] b ) {
    // -----------------------------------------------------------------------------------
    int n = a.length;
    for ( int i=0; i<n; i++ ) {
      double t = a[i];
      a[i] = b[i];
      b[i] = t;
    }
  }


  // =====================================================================================
  public static double[][] slice( double[][] src, int start, int end ) {
    // -----------------------------------------------------------------------------------
    int n = src[0].length;
    int k = end-start+1;
    double[][] dst = new double[k][n];
    for ( int i=0; i<k; i++ ) {
      for ( int j=0; j<n; j++ ) {
        dst[i][j] = src[start+i][j];
      }
    }
    return dst;
  }

  
  // =====================================================================================
  public static int fold_list( double[][] list, int dim ) {
    // -----------------------------------------------------------------------------------
    int n = list.length;

    if ( 1 == n ) return -1;

    int left   = 0;
    int middle = n/2;
    int right  = n - 1;

    //if ( 1==n%2 ) middle += 1;
    
    while ( left < right ) {
      double pivot = list[middle][dim];
      swap( list[middle], list[right] );

      int pos = left;
      for ( int i=left; i<right; i++ ) {
        if ( list[i][dim] < pivot ) {
            swap( list[i], list[pos] );
            pos += 1;
        }
      }

      swap( list[right], list[pos] );

      if ( pos == middle ) break;

      if ( pos < middle ) {
        left = pos + 1;
      } else {
        right = pos - 1;
      }
    }
    
    return middle;
  }


  // =====================================================================================
  public static kd_node_t build_tree( double[][] list, int dim, int max_dim ) {
    // -----------------------------------------------------------------------------------
    kd_node_t node = null;
    int       len  = list.length;

    if ( 0 < len ) {
      int midx = fold_list( list, dim );
      if ( 0 <= midx ) {
        node = new kd_node_t( list[midx] );
        int nj = (dim + 1) % max_dim;
        if (0 < midx ) {
          double[][] left_list = slice( list, 0, midx-1 );
          node.left = build_tree( left_list, nj, max_dim );
        }
        if (midx < len ) {
          double[][] right_list = slice( list, 0, midx-1 );
          node.right = build_tree( right_list, nj, max_dim );
        }
      }
    }

    return node;
  }




  

} // end class KDTree


// =======================================================================================
// **                                    K D T R E E                                    **
// ======================================================================== END FILE =====
