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

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public LeftTrapezoidFunction( double _c, double _r ) {
    // -----------------------------------------------------------------------------------
    super(Type.LEFTTRAP);
    set( _c, _r );
  }

  
  @Override public double getLeft()   { return 5.0e-1*(3.0e0*C - R); }
  @Override public double getCenter() { return C; }
  @Override public double getRight()  { return R; }

  
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
   *  Compute the degree of membership in this function based on a crisp value x.
   *  The domain is all real numbers. The range is 0 to 1 inclusive.
   */
  // -------------------------------------------------------------------------------------
  @Override public double mu( double x ) {
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
   *  @param d degree of membership.
   *  @return area.
   *
   *  Compute the area under the degree of membership for this fuzzy function.
   *  The domain is 0 to 1 inclusive. The range is 0 to max area for this function.
   */
  // -------------------------------------------------------------------------------------
  @Override public double area( double d ) {
    // -----------------------------------------------------------------------------------
    return 5.0e-1*W*d*(3.0e0-d);
  }


  // =====================================================================================
  /** @brief Center of area.
   *  @param d degree of membership.
   *  @return center of area.
   *
   *  Compute the center of area based on the degree of membership in this fuzzy function. 
   *  The domain is 0 to 1 inclusive. The range is (left) to (right) inclusive.
   */
  // -------------------------------------------------------------------------------------
  @Override public double coa( double d ) {
    // -----------------------------------------------------------------------------------
    return ( 9.0e0*(3.0e0*C + R) - (1.2e1*R - 4.0e0*W*d)*d )
        / (1.2e1*(3.0e0 - d));
  }


  // =====================================================================================
  /** @brief To String.
   *  @param fmt edit descriptor.
   */
  // -------------------------------------------------------------------------------------
  @Override public String toString( String fmt ) {
    // -----------------------------------------------------------------------------------
    return String.format( "L(%s,%s)",
                          String.format( fmt, C ),
                          String.format( fmt, R ) );
  }
  

  // =====================================================================================
  /** @brief Load.
   *  @param src source array.
   *  @param offset start index for source data.
   *  @return next index for available data.
   *
   *  Read the parameters for this function from a source array starting at the
   *  provided offset.
   */
  // -------------------------------------------------------------------------------------
  @Override public int load( double[] src, int offset ) {
    // -----------------------------------------------------------------------------------
    double a = src[offset];
    double b = src[offset+1];
    set( a, b );
    return offset + 2;
  }

  
  // =====================================================================================
  /** @brief Store.
   *  @param src source array.
   *  @param offset start index for destination data.
   *  @return next index for available data.
   *
   *  Read the parameters for this function from a source array starting at the
   *  provided offset.
   */
  // -------------------------------------------------------------------------------------
  @Override public int store( double[] dst, int offset ) {
    // -----------------------------------------------------------------------------------
    dst[offset] = getCenter();
    dst[offset+1] = getRight();
    return offset + 2;
    
  }
  
    
} // end class TriangleFunction


// =======================================================================================
// **                     L E F T T R A P E Z O I D F U N C T I O N                     **
// ======================================================================== END FILE =====
