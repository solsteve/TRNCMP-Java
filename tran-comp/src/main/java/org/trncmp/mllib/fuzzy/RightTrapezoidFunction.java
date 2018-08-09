// ====================================================================== BEGIN FILE =====
// **                    R I G H T T R A P E Z O I D F U N C T I O N                    **
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
 * Provides the interface and methods for a right trapezoid shaped fuzzy function.
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
public class RightTrapezoidFunction extends Function {
  // -------------------------------------------------------------------------------------
  
  /** Left extreme of this fuzzy set */
  protected double L;
  
  /** Point of maximum membership extreme of this fuzzy set */
  protected double C;
  
  protected double W;

  public static class Builder extends Function.Builder<Builder> {

    private double left_extreme   = -1.0;
    private double center_value   =  0.0;

    public Builder( double ctr ) {
      center_value  = ctr;
      left_extreme  = center_value - 1.0;
    }

    public Builder left  ( double _l ) { left_extreme  = _l; return this; }
    

    @Override public RightTrapezoidFunction build() {
      return new RightTrapezoidFunction(this);
    }

    @Override protected Builder self() { return this; }

    
  } // end class RightTrapezoidFunction.Builder


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  private RightTrapezoidFunction(Builder builder) {
    // -----------------------------------------------------------------------------------
    super(builder);
    set( builder.left_extreme,
         builder.center_value );
  }

  public double getLeft()   { return L; }
  public double getCenter() { return C; }
  public double getRight()  { return 5.0e-1*(3.0e0*C-L); }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void set( double _l, double _c ) {
    // -----------------------------------------------------------------------------------
    L = _l;
    C = _c;

    W  = C - L;
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
        return (x - L)/W;
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
    return 5.0e-1*(3.0e0 - degree)*W*degree;
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
    return ( 9.0e0*(3.0e0*C + L) - (4.0e0*W*degree + 1.2e1*L)*degree )
        / (1.2e1*(3.0e0 - degree));
  }

  
} // end class RightTrapezoidFunction


// =======================================================================================
// **                    R I G H T T R A P E Z O I D F U N C T I O N                    **
// ======================================================================== END FILE =====
