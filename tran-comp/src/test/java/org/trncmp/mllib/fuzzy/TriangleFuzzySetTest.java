// ====================================================================== BEGIN FILE =====
// **                      T R I A N G L E F U Z Z Y S E T T E S T                      **
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
 * @file TriangleFuzzySetTest.java
 * <p>
 * Provides the interface and methods for a triangular shaped fuzzy set.
 *
 * @date 2018-08-06
 *
 * ---------------------------------------------------------------------------------------
 *
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 *
 * @author Stephen W. Soliday
 * @date 2014-06-27
 */
// =======================================================================================

package org.trncmp.mllib.fuzzy;

import        org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// =======================================================================================
public class TriangleFuzzySetTest {
  // -------------------------------------------------------------------------------------

  static final double epsilon = 1.0e-8;
  
  // =====================================================================================
  @Test
  public void testMu() {
    // -----------------------------------------------------------------------------------
    Function set = new TriangleFunction(3.0,7.0,15.0);

    double[][] test = {
      {  2.0, 0.00 },
      {  2.9999, 0.00 },
      {  3.0, 0.00 },
      {  4.0, 0.25 },
      {  5.0, 0.50 },
      {  6.0, 0.75 },
      {  7.0, 1.00 },
      {  9.0, 0.75 },
      { 11.0, 0.50 },
      { 13.0, 0.25 },
      { 15.0, 0.00 },
      { 15.9999, 0.00 },
      { 16.0, 0.00 }
    };

    for ( int i=0; i<test.length; i++ ) {
      assertEquals( set.mu( test[i][0] ), test[i][1], epsilon );
    }

  }

} // end class TriangleFuzzySetTest

// =======================================================================================
// **                      T R I A N G L E F U Z Z Y S E T T E S T                      **
// ======================================================================== END FILE =====
