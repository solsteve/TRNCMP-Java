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

  } // end class KDTree.kd_node_t



  /** Root node in this tree */
  protected kd_node_t root = null;

  /** Nodes visited durring search */
  protected int nodes_visited = 0;

  /** Nodes in the tree */
  protected int nodes_in_tree = 0;


  public int getVisit() { return nodes_visited; };
  public int inTree()   { return nodes_in_tree; };
  

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
    nodes_in_tree += 1;
    
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
  protected static class holder {
    // -----------------------------------------------------------------------------------
    public kd_node_t node = null;
    public double    dist = 0.0e0;
    public holder() { node = null; dist = 1.0e30; }
  } // end class holder
    

  // =====================================================================================
  protected static void recursive_search( KDTree tree, holder best,
                                          kd_node_t root, kd_node_t nd,
                                          int dim, int max_dim ) {
    // -----------------------------------------------------------------------------------

    if ( null != root ) {
      double d   = Math2.dist2( root.x, nd.x );
      double dx  = root.x[dim] - nd.x[dim];
      double dx2 = dx*dx;

      if ( ( null == best.node ) || ( d < best.dist ) ) {
        best.node = root;
        best.dist = d;
      }

      if ( Math2.isNotZero( best.dist ) ) { // account for an exact match
        int next_dim = (dim + 1) % max_dim;
        
        if ( 0.0e0 < dx ) {
          recursive_search( tree, best, root.left,  nd, next_dim, max_dim );
        } else {
          recursive_search( tree, best, root.right, nd, next_dim, max_dim );
        }

        if ( dx2 < best.dist ) {
        if ( 0.0e0 < dx ) {
          recursive_search( tree, best, root.right, nd, next_dim, max_dim );
        } else {
          recursive_search( tree, best, root.left,  nd, next_dim, max_dim );
        }
        }

      }
    }
  }

  
  // =====================================================================================
  public double[] search( double[] test_point ) {
    // -----------------------------------------------------------------------------------
    int      max_dim = test_point.length;
    double[] match   = null;

    nodes_visited = 0;

    kd_node_t test_node = new kd_node_t( test_point );
    holder found = new holder();
    recursive_search( this, found, this.root, test_node, 0, max_dim );

    if ( null != found.node ) {
      match = new double[ max_dim ];
      for ( int i=0; i<max_dim; i++ ) {
        match[i] = found.node.x[i];
      }
    }
    
    return match;
  }


  // =====================================================================================
  public static int exhaustive_search( double[][] list, double[] x ) {
    // -----------------------------------------------------------------------------------
    double min_d2 = Math2.dist2( list[0], x );
    int    min_id = 0;

    int samples = list.length;
    for ( int i=1; i<samples; i++ ) {
      double d2 = Math2.dist2( list[i], x );
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
