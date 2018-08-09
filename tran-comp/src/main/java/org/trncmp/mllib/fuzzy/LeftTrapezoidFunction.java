// ====================================================================== BEGIN FILE =====
// **                     L E F T T R A P E Z O I D F U N C T I O N                     **
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
 * @file LeftTrapezoidFunction.java
 * <p>
 * Provides the interface and methods for a left trapezoid shaped fuzzy function.
 *
 * @date 2018-08-08
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
public class LeftTrapezoidFunction extends Function {
  // -------------------------------------------------------------------------------------
  
  /** Point of maximum membership extreme of this fuzzy set */
  protected double C;
  
  /** Right extreme of this fuzzy set */
  protected double R;

  protected double W;

  public static class Builder extends Function.Builder<Builder> {

    private double center_value   =  0.0;
    private double right_extreme  =  1.0;

    public Builder( double ctr ) {
      center_value  = ctr;
      right_extreme = center_value + 1.0;
    }

    public Builder right ( double _r ) { right_extreme = _r; return this; }
    

    @Override public LeftTrapezoidFunction build() {
      return new LeftTrapezoidFunction(this);
    }

    @Override protected Builder self() { return this; }

    
  } // end class LeftTrapezoidFunction.Builder


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  private LeftTrapezoidFunction(Builder builder) {
    // -----------------------------------------------------------------------------------
    super(builder);
    set( builder.center_value,
         builder.right_extreme );
  }

  public double getLeft()   { return 5.0e-1*(3.0e0*C - R); }
  public double getCenter() { return C; }
  public double getRight()  { return R; }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void set( double _c, double _r ) {
    // -----------------------------------------------------------------------------------
    C = _c;
    R = _r;

    W = R - C;
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
    if ( x > C ) {
      if ( x < R ) {
        return (R - x)/W;
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
    return 5.0e-1*W*degree*(3.0e0-degree);
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
    return ( 9.0e0*(3.0e0*C + R) - (1.2e1*R - 4.0e0*W*degree)*degree )
        / (1.2e1*(3.0e0 - degree));
  }

  
} // end class TriangleFunction


// =======================================================================================
// **                     L E F T T R A P E Z O I D F U N C T I O N                     **
// ======================================================================== END FILE =====
