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
// **                                                                                   **
// **  Any reproduction of computer software or portions thereof marked with this       **
// **  legend must also reproduce the markings.  Any person who has been provided       **
// **  access to such software must promptly notify L3 Technologies Advanced Programs.  **
// **                                                                                   **
// ----- Modification History ------------------------------------------------------------
/**
 * @file TriangleFunction.java
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
public class TriangleFunction extends Function {
  // -------------------------------------------------------------------------------------
  
  /** Left extreme of this fuzzy set */
  protected double L;
  
  /** Point of maximum membership extreme of this fuzzy set */
  protected double C;
  
  /** Right extreme of this fuzzy set */
  protected double R;

  protected double LD;
  protected double RD;
  protected double W;


  public static class Builder extends Function.Builder<Builder> {

    private double left_extreme   = -1.0;
    private double center_value   =  0.0;
    private double right_extreme  =  1.0;

    public Builder( double ctr ) {
      center_value = ctr;
      left_extreme  = center_value - 1.0;
      right_extreme = center_value + 1.0;
    }

    public Builder left  ( double _l ) { left_extreme  = _l; return this; }
    public Builder right ( double _r ) { right_extreme = _r; return this; }
    

    @Override public TriangleFunction build() {
      return new TriangleFunction(this);
    }

    @Override protected Builder self() { return this; }

    
  } // end class TriangleFunction.Builder


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  private TriangleFunction(Builder builder) {
    // -----------------------------------------------------------------------------------
    super(builder);
    set( builder.left_extreme,
         builder.center_value,
         builder.right_extreme );
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

    LD = C - L;
    RD = R - C;
    W  = R - L;
  }
  

  // =====================================================================================
  /** @brief Membership.
   *  @param x crisp value.
   *  @return degree of membership.
   *
   *  Compute the degree of membership in this set based on the crisp value.
   *  The domain is all real numbers. The range is 0 to 1 inclusive.
   */
  // -------------------------------------------------------------------------------------
  public double mu( double x ) {
    // -----------------------------------------------------------------------------------
    if ( x < C ) {
      if ( x > L ) {
        return (x - L)/LD;
      } else {
        return 0.0;
      }
    }
    if ( x > C ) {
      if ( x < R ) {
        return (R - x)/RD;
      } else {
        return 0.0;
      }
    }

    return 1.0;
  }

  
  // =====================================================================================
  /** @brief Area.
   *  @param degree degree of membership.
   *  @return area.
   *
   *  Compute the area based on the degree of membership in this set. 
   *  The domain is 0 to 1 inclusive.
   */
  // -------------------------------------------------------------------------------------
  public double area( double degree ) {
    // -----------------------------------------------------------------------------------
    return 5.0e-1*W*degree*(2.0e0-degree);
  }


  // =====================================================================================
  /** @brief Centroid.
   *  @param degree degree of membership.
   *  @return centroid.
   *
   *  Compute the area based on the degree of membership in this set. 
   *  The domain is 0 to 1 inclusive.
   */
  // -------------------------------------------------------------------------------------
  public double centroid( double degree ) {
    // -----------------------------------------------------------------------------------
    return (3.0e0*(L+R) - (3.0e0*(R-C+L) - (R-2.0e0*C+L)*degree)*degree )
        / (3.0e0*(2.0e0-degree));
  }

  
} // end class TriangleFunction


// =======================================================================================
// **                          T R I A N G L E F U Z Z Y S E T                          **
// ======================================================================== END FILE =====
