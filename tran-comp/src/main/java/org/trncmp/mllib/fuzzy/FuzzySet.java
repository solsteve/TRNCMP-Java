// ====================================================================== BEGIN FILE =====
// **                                  F U Z Z Y S E T                                  **
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
 * @file AbstractFuzzySet.java
 * <p>
 * Provides an abstract fuzzy set
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



// =======================================================================================
public abstract class FuzzySet {
  // -------------------------------------------------------------------------------------


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  abstract static class Builder<T extends Builder<T>> {
    // -----------------------------------------------------------------------------------

    abstract FuzzySet build();

    protected abstract T self();
    
  } // end class FuzzySet.Builder
  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  FuzzySet(Builder<?> builder) {
    // -----------------------------------------------------------------------------------
  }

  // =====================================================================================
  /** @brief Copy.
   *  @param p pointer to a source Encoding.
   *
   *  Perform an element by element copy of the source Encoding.
   */
  // -------------------------------------------------------------------------------------
  public abstract double mu( double x );

} // end class AbstractFuzzySet


// =======================================================================================
// **                                  F U Z Z Y S E T                                  **
// ======================================================================== END FILE =====
