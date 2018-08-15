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
 * @file RightTrapezoidFunction.java
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

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public RightTrapezoidFunction( double _l, double _c ) {
    // -----------------------------------------------------------------------------------
    super(Type.RIGHTTRAP);
    set(_l, _c );
  }

  
  @Override public double getLeft()   { return L; }
  @Override public double getCenter() { return C; }
  @Override public double getRight()  { return 5.0e-1*(3.0e0*C-L); }

  
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
   *  Compute the degree of membership in this function based on a crisp value x.
   *  The domain is all real numbers. The range is 0 to 1 inclusive.
   */
  // -------------------------------------------------------------------------------------
  @Override public double mu( double x ) {
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
   *  @param d degree of membership.
   *  @return area.
   *
   *  Compute the area under the degree of membership for this fuzzy function.
   *  The domain is 0 to 1 inclusive. The range is 0 to max area for this function.
   */
  // -------------------------------------------------------------------------------------
  @Override public double area( double d ) {
    // -----------------------------------------------------------------------------------
    return 5.0e-1*(3.0e0 - d)*W*d;
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
    return ( 9.0e0*(3.0e0*C + L) - (4.0e0*W*d + 1.2e1*L)*d )
        / (1.2e1*(3.0e0 - d));
  }

    // =====================================================================================
  /** @brief To String.
   *  @param fmt edit descriptor.
   */
  // -------------------------------------------------------------------------------------
  @Override public String toString( String fmt ) {
    // -----------------------------------------------------------------------------------
    return String.format( "R(%s,%s)",
                          String.format( fmt, L ),
                          String.format( fmt, C ) );
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
    dst[offset]   = getLeft();
    dst[offset+1] = getCenter();
    return offset + 2;
    
  }
  
  
} // end class RightTrapezoidFunction


// =======================================================================================
// **                    R I G H T T R A P E Z O I D F U N C T I O N                    **
// ======================================================================== END FILE =====
