// ====================================================================== BEGIN FILE =====
// **                                  F U N C T I O N                                  **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2018, Stephen W. Soliday                                           **
// **                      stephen.soliday@trncmp.org                                   **
// **                      http://research.trncmp.org                                   **
// **                                                                                   **
// **  -------------------------------------------------------------------------------  **
// **                                                                                   **
// **  This program is free software: you can redistribute it and/or modify it under    **
// **  the terms of the GNU General Public License as published by the Free Software    **
// **  Foundation, either version 3 of the License, or (at your option)                 **
// **  any later version.                                                               **
// **                                                                                   **
// **  This program is distributed in the hope that it will be useful, but WITHOUT      **
// **  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS    **
// **  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.   **
// **                                                                                   **
// **  You should have received a copy of the GNU General Public License along with     **
// **  this program. If not, see <http://www.gnu.org/licenses/>.                        **
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
