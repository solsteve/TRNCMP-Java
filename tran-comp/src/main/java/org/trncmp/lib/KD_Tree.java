// ====================================================================== BEGIN FILE =====
// **                                   K D _ T R E E                                   **
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
 * @file    KD_Tree.java
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
public class KD_Tree {
  // -------------------------------------------------------------------------------------

  // =====================================================================================
  public static class node {
    // -----------------------------------------------------------------------------------
    /** k-dimensional point coordinates */
    public double[] x = null;

    /** left branch of the tree from this node. */
    public node left = null;

    /** right branch of the tree from this node. */
    public node right = null;

    // ===================================================================================
    public node( double[] _x ) {
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

  } // end class KD_Tree.KD_Tree.node



  /** Root node in this tree */
  protected KD_Tree.node root = null;

  /** Nodes visited durring search */
  protected int nodes_visited = 0;

  /** Nodes in the tree */
  protected int nodes_in_tree = 0;


  public int getVisit() { return nodes_visited; };
  public int inTree()   { return nodes_in_tree; };
  

  // =====================================================================================
  protected static void recursive_insert( KD_Tree.node root_node, KD_Tree.node leaf_node,
                                          int dim ) {
    // -----------------------------------------------------------------------------------

    int max_dim = root_node.x.length;
    
    if ( leaf_node.x[dim] < root_node.x[dim] ) {
      if ( null == root_node.left ) {
        root_node.left = leaf_node;
      } else {
        recursive_insert( root_node.left, leaf_node, (dim+1)%max_dim );
      }
    } else {
      if ( leaf_node.x[dim] > root_node.x[dim] ) {
        if ( null == root_node.right ) {
          root_node.right = leaf_node;
        } else {
          recursive_insert( root_node.right, leaf_node, (dim+1)%max_dim );
        }
      } else {
        //System.err.println( "Duplicate node" );
      }
    }

  }


  // =====================================================================================
  public void insert( KD_Tree.node knode ) {
    // -----------------------------------------------------------------------------------
    nodes_in_tree += 1;
    
    if ( null == root ) {
      root = knode;
    } else {
      recursive_insert( root, knode, 0 );
    }
  }

  // =====================================================================================
  public KD_Tree() {
    // -----------------------------------------------------------------------------------
    root = null;
  }

  // =====================================================================================
  protected static class holder {
    // -----------------------------------------------------------------------------------
    public KD_Tree.node knode = null;
    public double       dist  = 0.0e0;
    public holder() { knode = null; dist = 1.0e30; }
  } // end class holder
    

  // =====================================================================================
  protected static void recursive_search( KD_Tree tree, holder best,
                                          KD_Tree.node root, KD_Tree.node nd,
                                          int dim, int max_dim ) {
    // -----------------------------------------------------------------------------------

    if ( null != root ) {
      double d   = Math2.dist2( root.x, nd.x );
      double dx  = root.x[dim] - nd.x[dim];
      double dx2 = dx*dx;

      if ( ( null == best.knode ) || ( d < best.dist ) ) {
        best.knode = root;
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
  public KD_Tree.node  search( KD_Tree.node test_node ) {
    // -----------------------------------------------------------------------------------
    int      max_dim = test_node.x.length;
    double[] match   = null;

    nodes_visited = 0;

    holder found = new holder();
    recursive_search( this, found, this.root, test_node, 0, max_dim );

    return found.knode;
  }

  
} // end class KD_Tree


// =======================================================================================
// **                                   K D _ T R E E                                   **
// ======================================================================== END FILE =====
