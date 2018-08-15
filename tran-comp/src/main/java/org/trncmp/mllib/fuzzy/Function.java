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
  public enum Type {
    // -----------------------------------------------------------------------------------
    LEFTTRAP  ( "LT", "LeftTrapezoid" ),
    TRIANGLE  ( "T",  "Triangle" ),
    RIGHTTRAP ( "RT", "RightTrapezoid" );
    private final String code_value;
    private final String name_value;
    Type( String cd, String nm )        { this.code_value = cd; this.name_value = nm; }
    public String  toString()           { return this.name_value; }
    public boolean isEqual( String cd ) { return ( cd == this.code_value ); }
    public boolean isEqual( Type   tp ) { return ( tp.code_value == this.code_value ); }
  } // enum Type

  protected final Type function_type;

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  static public Function create( Type tp, double... x ) {
    // -----------------------------------------------------------------------------------
    Function rf = null;
    switch(tp) {
      case LEFTTRAP:
        rf  = new LeftTrapezoidFunction( x[0], x[1] );
        break;
      case TRIANGLE:
        rf  = new TriangleFunction( x[0], x[1], x[2] );
        break;
      case RIGHTTRAP:
        rf  = new TriangleFunction( x[0], x[1], x[2] );
        break;
      default:
        System.err.println("Unknown type: "+tp.toString());
    }
    return rf;
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  static public Function create( String tp, double... x ) {
    // -----------------------------------------------------------------------------------
    if ( tp.startsWith( "LT" ) ) { return create( Type.LEFTTRAP, x ); }
    if ( tp.startsWith( "T"  ) ) { return create( Type.TRIANGLE, x ); }
    if ( tp.startsWith( "RT" ) ) { return create( Type.RIGHTTRAP, x ); }
    return null;
  }




  // =====================================================================================
  // -------------------------------------------------------------------------------------
  Function( Type tp ) {
    // -----------------------------------------------------------------------------------
    function_type = tp;
  }

  public abstract double getLeft();
  public abstract double getCenter();
  public abstract double getRight();

  
  // =====================================================================================
  /** @brief Membership.
   *  @param x crisp value.
   *  @return degree of membership.
   *
   *  Compute the degree of membership in this function based on a crisp value x.
   *  The domain is all real numbers. The range is 0 to 1 inclusive.
   */
  // -------------------------------------------------------------------------------------
  public abstract double mu( double x );

  
  // =====================================================================================
  /** @brief Area.
   *  @param d degree of membership.
   *  @return area.
   *
   *  Compute the area under the degree of membership for this fuzzy function.
   *  The domain is 0 to 1 inclusive. The range is 0 to max area for this function.
   */
  // -------------------------------------------------------------------------------------
  public abstract double area( double d );


  // =====================================================================================
  /** @brief Center of area.
   *  @param d degree of membership.
   *  @return center of area.
   *
   *  Compute the center of area based on the degree of membership in this fuzzy function. 
   *  The domain is 0 to 1 inclusive. The range is (left) to (right) inclusive.
   */
  // -------------------------------------------------------------------------------------
  public abstract double coa( double degree );
  

  // =====================================================================================
  /** @brief To String.
   *  @param fmt edit descriptor.
   */
  // -------------------------------------------------------------------------------------
  public abstract String toString( String fmt );


  public abstract int load(  double[] src, int offset );
  public abstract int store( double[] dst, int offset );

} // end class Function


// =======================================================================================
// **                                  F U N C T I O N                                  **
// ======================================================================== END FILE =====
