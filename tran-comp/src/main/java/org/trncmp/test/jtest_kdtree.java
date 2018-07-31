// ====================================================================== BEGIN FILE =====
// **                              J T E S T _ K D T R E E                              **
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
 * @file jtest_kdtree.java
 *  Provides Test the KDTree class.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2018-07-28
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.KDTree;
import org.trncmp.lib.array;
import org.trncmp.lib.Math2;
import org.trncmp.lib.Dice;


// =======================================================================================
public class jtest_kdtree {
  // -------------------------------------------------------------------------------------

  public static final int KDIMS   = 5;
  public static final int SAMPLES = 1000000;
  

  // =====================================================================================
  public static String ary_print( double[] point, String fmt ) {
    // -----------------------------------------------------------------------------------
    return "(" + array.toString( point, fmt, ", " ) + ")";
  }

  // =====================================================================================
  public static String ary_print( double[][] list, String fmt ) {
    // -----------------------------------------------------------------------------------
    return array.toString( list, fmt );
  }




  

  // =====================================================================================
  public static void print_results( String name, double[] test, double[] found,
                                    double dist, int visit, int len, int true_idx,
                                    double true_dist, double[] true_found, String fmt ) {
    // -----------------------------------------------------------------------------------

    System.out.format( "\n>> %s\n", name );
    System.out.format( "searching for %s\n",
                       ary_print( test,  fmt ) );
    System.out.format( "found %s dist %s0\n",
                       ary_print( found, fmt ),
                       String.format( fmt, dist ) );
    System.out.format( "seen %d nodes out of %d\n\n", visit, len );
    System.out.format( "Exhaustive idx: %d dist: %s %s\n\n",
                       true_idx,
                       String.format( fmt, true_dist ),
                       ary_print( true_found, fmt ) );
  }

  // =====================================================================================
  public static void wiki_test() {
    // -----------------------------------------------------------------------------------
    double[]   test_point = {9,2};
    double[][] list       = {{2,3}, {5,4}, {9,6}, {4,7}, {8,1}, {7,2}};
    double[]   found      = null;
    double[]   e_found    = null;
    double     dist, e_dist;
    int        e_idx;
    // -----------------------------------------------------------------------------------

    KDTree tree = new KDTree( list );

    found = tree.search( test_point );
    dist  = Math.sqrt( Math2.dist2( test_point, found ) );
    
    e_idx   = KDTree.exhaustive_search( list, test_point );
    e_found = list[e_idx];
    e_dist  = Math.sqrt( Math2.dist2( test_point, e_found ) );


    print_results( "Wiki", test_point, found,
                   dist, tree.getVisit(), list.length,
                   e_idx, e_dist, e_found, "%7.4f" );
  }

  // =====================================================================================
  public static void cmu_test() {
    // -----------------------------------------------------------------------------------
    double[]   test_point = {72,72};
    double[][] list       = {{30,40}, {5,25}, {10,12}, {70,70}, {50,30}, {35,45}};
    double[]   found      = null;
    double[]   e_found    = null;
    double     dist, e_dist;
    int        e_idx;
    // -----------------------------------------------------------------------------------

    KDTree tree = new KDTree( list );

    found = tree.search( test_point );
    dist  = Math.sqrt( Math2.dist2( test_point, found ) );
    
    e_idx   = KDTree.exhaustive_search( list, test_point );
    e_found = list[e_idx];
    e_dist  = Math.sqrt( Math2.dist2( test_point, e_found ) );


    print_results( "CMU", test_point, found,
                   dist, tree.getVisit(), list.length,
                   e_idx, e_dist, e_found, "%7.4f" );
  }


  // =====================================================================================
  public static void million_test() {
    // -----------------------------------------------------------------------------------
    double[]   test_point = null;
    double[][] list       = null;
    double[]   found      = null;
    double[]   e_found    = null;
    double     dist, e_dist;
    int        e_idx;
    // -----------------------------------------------------------------------------------

    Dice dd = Dice.getInstance();

    dd.seed_set();
    
    test_point = new double[ KDIMS ];
    for ( int j=0; j<KDIMS; j++ ) {
      test_point[j] = Math.floor(dd.uniform() * 10000.0) / 1000.0;
    }

    list = new double[ SAMPLES ][ KDIMS ];
    for ( int i=0; i<SAMPLES; i++ ) {
      for ( int j=0; j<KDIMS; j++ ) {
        list[i][j] = Math.floor(dd.uniform() * 10000.0) / 1000.0;
      }
    }
    
    // -----------------------------------------------------------------------------------

    KDTree tree = new KDTree( list );

    found = tree.search( test_point );
    dist  = Math.sqrt( Math2.dist2( test_point, found ) );
    
    e_idx   = KDTree.exhaustive_search( list, test_point );
    e_found = list[e_idx];
    e_dist  = Math.sqrt( Math2.dist2( test_point, e_found ) );


    print_results( "Million", test_point, found,
                   dist, tree.getVisit(), list.length,
                   e_idx, e_dist, e_found, "%7.4f" );
  }


  
  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
    wiki_test();
    cmu_test();
  }

} // end class jtest_kdtest

// =======================================================================================
// **                              J T E S T _ K D T R E E                              **
// ======================================================================== END FILE =====
