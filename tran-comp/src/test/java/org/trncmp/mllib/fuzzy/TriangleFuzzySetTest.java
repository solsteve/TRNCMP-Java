// ====================================================================== BEGIN FILE =====
// **                      T R I A N G L E F U Z Z Y S E T T E S T                      **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2018, L3 Technologies Advanced Programs                            **
// **                      One Wall Street #1, Burlington, MA 01803                     **
// **                                                                                   **
// **  -------------------------------------------------------------------------------  **
// **                                                                                   **
// **  This file, and associated source code, is not free software; you may not         **
// **  redistribute it and/or modify it. This file is part of a research project        **
// **  that is in a development phase. No part of this research has been publicly       **
// **  distributed. Research and development for this project has been at the sole      **
// **  cost in both time and funding by L3 Technologies Advanced Programs.              **
// **                                                                                   **
// **  Any reproduction of computer software or portions thereof marked with this       **
// **  legend must also reproduce the markings.  Any person who has been provided       **
// **  access to such software must promptly notify L3 Technologies Advanced Programs.  **
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
    FuzzySet set = new TriangleFuzzySet.Builder(7.0).left(3.0).right(15.0).build();

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
