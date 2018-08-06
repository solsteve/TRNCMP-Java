// ====================================================================== BEGIN FILE =====
// **                          T R I A N G L E F U Z Z Y S E T                          **
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
// **                g                                                                   **
// **  Any reproduction of computer software or portions thereof marked with this       **
// **  legend must also reproduce the markings.  Any person who has been provided       **
// **  access to such software must promptly notify L3 Technologies Advanced Programs.  **
// **                                                                                   **
// ----- Modification History ------------------------------------------------------------
/**
 * @file TriangleFuzzySet.java
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



// =======================================================================================
public class TriangleFuzzySet extends FuzzySet {
  // -------------------------------------------------------------------------------------
  
  /** Left extreme of this fuzzy set */
  protected double L;
  
  /** Point of maximum membership extreme of this fuzzy set */
  protected double C;
  
  /** Right extreme of this fuzzy set */
  protected double R;


  public static class Builder extends FuzzySet.Builder<Builder> {

    private double left_extreme   = -1.0;
    private double center_value   =  0.0;
    private double right_extreme  =  1.0;

    public Builder( double ctr ) {
      center_value = ctr;
      left_extreme  = center_value - 1.0;
      right_extreme = center_value + 1.0;
    }

    @Override public TriangleFuzzySet build() {
      return new TriangleFuzzySet(this);
    }

    @Override protected Builder self() { return this; }

    
  } // end class TriangleFuzzySet.Builder


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  private TriangleFuzzySet(Builder builder) {
    // -----------------------------------------------------------------------------------
    super(builder);
    L = builder.left_extreme;
    C = builder.center_value;
    R = builder.right_extreme;
  }

  public double getLeft()   { return L; }
  public double getCenter() { return C; }
  public double getRight()  { return R; }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void set( double _l, double _c, double _r ) {
    // -----------------------------------------------------------------------------------
    L = _l;
    C = _c;
    R = _r;

    /** UPDATE PRETUNED VALUES */
  }

  // =====================================================================================
  /** @brief Copy.
   *  @param p pointer to a source Encoding.
   *
   *  Perform an element by element copy of the source Encoding.
   */
  // -------------------------------------------------------------------------------------
  public double mu( double x ) {
    // -----------------------------------------------------------------------------------
    return 1.0 * x;
  }

} // end class TriangleFuzzySet


// =======================================================================================
// **                          T R I A N G L E F U Z Z Y S E T                          **
// ======================================================================== END FILE =====
