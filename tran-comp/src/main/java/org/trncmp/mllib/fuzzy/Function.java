// ====================================================================== BEGIN FILE =====
// **                                  F U N C T I O N                                  **
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
 * @file Function.java
 * <p>
 * Provides an abstract fuzzy function.
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
public abstract class Function {
  // -------------------------------------------------------------------------------------


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  abstract static class Builder<T extends Builder<T>> {
    // -----------------------------------------------------------------------------------

    abstract Function build();

    protected abstract T self();
    
  } // end class Function.Builder
  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  Function(Builder<?> builder) {
    // -----------------------------------------------------------------------------------
  }

  public abstract double getLeft();
  public abstract double getCenter();
  public abstract double getRight();

  // =====================================================================================
  /** @brief Mu.
   *  @param p pointer to a source Encoding.
   *
   *  Perform an element by element copy of the source Encoding.
   */
  // -------------------------------------------------------------------------------------
  public abstract double mu( double x );

  
  // =====================================================================================
  /** @brief Area.
   *  @param degree degree of membership.
   *  @return area.
   *
   *  Compute the area based on the degree of membership in this set. 
   *  The domain is 0 to 1 inclusive.
   */
  // -------------------------------------------------------------------------------------
  public abstract double area( double degree );


  // =====================================================================================
  /** @brief Centroid.
   *  @param degree degree of membership.
   *  @return centroid.
   *
   *  Compute the area based on the degree of membership in this set. 
   *  The domain is 0 to 1 inclusive.
   */
  // -------------------------------------------------------------------------------------
  public abstract double centroid( double degree );
  

} // end class Function


// =======================================================================================
// **                                  F U N C T I O N                                  **
// ======================================================================== END FILE =====
