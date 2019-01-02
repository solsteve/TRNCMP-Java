// ====================================================================== BEGIN FILE =====
// **                          T R I A N G L E F U N C T I O N                          **
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
 * @file TriangleFunction.java
 * <p>
 * Provides the interface and methods for a triangular shaped fuzzy function.
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

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public TriangleFunction( double _l, double _c, double _r ) {
    // -----------------------------------------------------------------------------------
    super(Type.TRIANGLE);
    set( _l, _c, _r );
  }

  
  @Override public double getLeft()   { return L; }
  @Override public double getCenter() { return C; }
  @Override public double getRight()  { return R; }

  
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
   *  Compute the degree of membership in this function based on a crisp value x.
   *  The domain is all real numbers. The range is 0 to 1 inclusive.
   */
  // -------------------------------------------------------------------------------------
  @Override public double mu( double x ) {
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
   *  @param d degree of membership.
   *  @return area.
   *
   *  Compute the area under the degree of membership for this fuzzy function.
   *  The domain is 0 to 1 inclusive. The range is 0 to max area for this function.
   */
  // -------------------------------------------------------------------------------------
  @Override public double area( double degree ) {
    // -----------------------------------------------------------------------------------
    return 5.0e-1*W*degree*(2.0e0-degree);
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
  @Override public double coa( double degree ) {
    // -----------------------------------------------------------------------------------
    return (3.0e0*(L+R) - (3.0e0*(R-C+L) - (R-2.0e0*C+L)*degree)*degree )
        / (3.0e0*(2.0e0-degree));
  }

  // =====================================================================================
  /** @brief To String.
   *  @param fmt edit descriptor.
   */
  // -------------------------------------------------------------------------------------
  @Override public String toString( String fmt ) {
    // -----------------------------------------------------------------------------------
    return String.format( "T(%s,%s,%s)",
                          String.format( fmt, L ),
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
    double c = src[offset+2];
    set( a, b, c );
    return offset + 3;
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
    dst[offset+2] = getRight();
    return offset + 3;
    
  }
  
  
} // end class TriangleFunction


// =======================================================================================
// **                          T R I A N G L E F U N C T I O N                          **
// ======================================================================== END FILE =====
