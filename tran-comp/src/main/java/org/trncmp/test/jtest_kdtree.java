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

// =======================================================================================
public class jtest_kdtree {
  // -------------------------------------------------------------------------------------



  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
    double[][] list1 = {{2,3}, {5,4}, {9,6}, {4,7}, {8,1}, {7,2}};
    double[][] list2 = {{30,40}, {5,25}, {10,12}, {70,70}, {50,30}, {35,45}};

    double[] test1 = {9,2};
    double[] test2 = {72,72};

    System.out.println();
    System.out.println("-------------------------------------------------------");
    System.out.println();
    
    KDTree wiki = new KDTree( list1 );
    
    System.out.println();
    System.out.println("-------------------------------------------------------");
    System.out.println();
    wiki.print( System.out, "%4.1f" );
    int i1 = KDTree.exhaustive_search( list1, test1 );
    System.out.println("Match "+array.toString(list1[i1], "%4.1f") );


    KDTree cmu = new KDTree( list2 );
    
    System.out.println();
    System.out.println("-------------------------------------------------------");
    System.out.println();
    cmu.print( System.out, "%4.1f" );
    int i2 = KDTree.exhaustive_search( list2, test2 );
    System.out.println("Match "+array.toString(list2[i2], "%4.1f") );

    
  }

} // end class jtest_kdtest

// =======================================================================================
// **                              J T E S T _ K D T R E E                              **
// ======================================================================== END FILE =====
