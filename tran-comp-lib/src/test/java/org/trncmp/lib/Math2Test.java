// ====================================================================== BEGIN FILE =====
// **                                 M A T H 2 T E S T                                 **
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
//
// @file RunStatTest.java
// <p>
// Provides unit testing for the org.trncmp.libRunStaty class.
//
// @author Stephen W. Soliday
// @date 2018-07-07
//
// =======================================================================================

package org.trncmp.lib;

import        org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// =======================================================================================
public class Math2Test {
  // -------------------------------------------------------------------------------------
  
  final double TOL = 1.0e-12;
    

  // =====================================================================================
  @Test
  public void testBSpline() {
    // -----------------------------------------------------------------------------------
    double[] X     = { 3.0, 7.0, 13.0 };
    double[] realA = { 2.0, -5.0, 1.0 };
    double[] Y     = new double[3];
    double[] A     = new double[3];

    Y[0] = (realA[0]*X[0] + realA[1])*X[0] + realA[2];
    Y[1] = (realA[0]*X[1] + realA[1])*X[1] + realA[2];
    Y[2] = (realA[0]*X[2] + realA[1])*X[2] + realA[2];

    //System.err.format( "Y: [ %g %g %g ]\n", Y[0], Y[1], Y[2] );

    Math2.BSpline( A, X, Y );

    assertEquals( realA[0], A[0], TOL );
    assertEquals( realA[1], A[1], TOL );
    assertEquals( realA[2], A[2], TOL );
  }

} // end class RunStatTest


// =======================================================================================
// **                                 M A T H 2 T E S T                                 **
// ======================================================================== END FILE =====
