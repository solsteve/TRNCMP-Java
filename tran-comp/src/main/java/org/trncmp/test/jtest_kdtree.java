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
    double[][] test1 = {{2,3}, {5,4}, {9,6}, {4,7}, {8,1}, {7,1}};
    double[][] test = {{2,3}, {5,4}, {9,6}, {4,7}, {1,5}, {8,1}, {7,1}};
    int        len  = test.length;
    double[][] a = null;
    double[][] b = null;
    System.out.println(array.toString(test));
    System.out.println();

    int m = KDTree.fold_list( test, 0 );

    
    a = KDTree.slice( test, 0, m-1 );
    System.out.println(array.toString(a));
    System.out.println();
    System.out.println(array.toString(test[m]));
    System.out.println();
    b = KDTree.slice( test, m+1, len-1 );
    System.out.println(array.toString(b));
    System.out.println();


    
  }

} // end class jtest_kdtest

// =======================================================================================
// **                              J T E S T _ K D T R E E                              **
// ======================================================================== END FILE =====
