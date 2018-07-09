// ====================================================================== BEGIN FILE =====
// **                                      V E C 3                                      **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2017, Stephen W. Soliday                                           **
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
//
// @file Vec3.java
// <p>
// Provides 3x1 vector.
//
// @author Stephen W. Soliday
// @date 2017-09-10
//
// =======================================================================================

package org.trncmp.lib.linear;

// =======================================================================================
public class Vec3 {
  // -------------------------------------------------------------------------------------
  public double x = 0.0e0;
  public double y = 0.0e0;
  public double z = 0.0e0;




  // =====================================================================================
  /** @brief Constructor.
   *  @param M reference to a matrix.
   *  @return an array of column vectors.
   *
   *  Create an array of vectors from the columns of a matrix.
   */
  // -------------------------------------------------------------------------------------
  public static Vec3[] columns( Mat3 M ) {
    // -----------------------------------------------------------------------------------
    Vec3[] v = new Vec3[3];

    v[0] = new Vec3( M.A[0][0], M.A[1][0], M.A[2][0] );
    v[1] = new Vec3( M.A[0][1], M.A[1][1], M.A[2][1] );
    v[2] = new Vec3( M.A[0][2], M.A[1][2], M.A[2][2] );

    return v;
  }

    
  // =====================================================================================
  /** @brief Constructor.
   *  @param M reference to a matrix.
   *  @return an array of column vectors.
   *
   *  Create an array of vectors from the columns of a matrix.
   */
  // -------------------------------------------------------------------------------------
  public static Vec3[] rows( Mat3 M ) {
    // -----------------------------------------------------------------------------------
    Vec3[] v = new Vec3[3];

    v[0] = new Vec3( M.A[0][0], M.A[0][1], M.A[0][2] );
    v[1] = new Vec3( M.A[1][0], M.A[1][1], M.A[1][2] );
    v[2] = new Vec3( M.A[2][0], M.A[2][1], M.A[2][2] );

    return v;
  }

    
  // =====================================================================================
  /** @brief Constructor.
   *
   *  Create an empty vector.
   */
  // -------------------------------------------------------------------------------------
  public Vec3() {
    // -----------------------------------------------------------------------------------
  }

    
  // =====================================================================================
  /** @brief Constructor.
   *  @param q1 first  component.
   *  @param q2 second component.
   *  @param q3 third  component.
   *
   *  Create a copy of the source material.
   */
  // -------------------------------------------------------------------------------------
  public Vec3( double q1, double q2, double q3 ) {
    // -----------------------------------------------------------------------------------
    this.x = q1;
    this.y = q2;
    this.z = q3;
  }

    
  // =====================================================================================
  /** @brief Constructor.
   *  @param q reference to source array.
   *
   *  Create a copy of the source material.
   */
  // -------------------------------------------------------------------------------------
  public Vec3( double[] q ) {
    // -----------------------------------------------------------------------------------
    this.x = q[0];
    this.y = q[1];
    this.z = q[2];
  }

    
  // =====================================================================================
  /** @brief Constructor.
   *  @param src reference to a length 4 source vector.
   *
   *  Create a copy of the source vector.
   */
  // -------------------------------------------------------------------------------------
  public Vec3( Vec3 src) {
    // -----------------------------------------------------------------------------------
    this.copy( src );
  }

    
  // =====================================================================================
  /** @brief Set.
   *  Set each element of this vector to zero.
   */
  // -------------------------------------------------------------------------------------
  public void set() {
    // -----------------------------------------------------------------------------------
    this.x = 0.0e0;
    this.y = 0.0e0;
    this.z = 0.0e0;
  }

    
  // =====================================================================================
  /** @brief Set.
   *  @param val value to set each element ( default: 0 ).
   *
   *  Set each element of this vector to the value of the argument val.
   */
  // -------------------------------------------------------------------------------------
  public void set( double val ) {
    // -----------------------------------------------------------------------------------
    this.x = val;
    this.y = val;
    this.z = val;
  }


  // =====================================================================================
  /** @brief Copy.
   *  @param src reference to a length 4 source vector.
   *
   *  Make a deep copy of the source vector.
   */
  // -------------------------------------------------------------------------------------
  public void copy( Vec3 src ) {
    // -----------------------------------------------------------------------------------
    this.x = src.x;
    this.y = src.y;
    this.z = src.z;
  }


  // =====================================================================================
  /** @brief Euclidian Norm.
   *  @return Euclidian Norm.
   * 
   *  Compute the Euclidian norm = sqrt( x' * x ).
   */
  // -------------------------------------------------------------------------------------
  public double mag( ) {
    // -----------------------------------------------------------------------------------
    return Math.sqrt( x*x + y*y + z*z );
  }


  // =====================================================================================
  /** @brief Normalize.
   * 
   *  Normalize this vector. self = self / ||self||
   */
  // -------------------------------------------------------------------------------------
  public double norm() {
    // -----------------------------------------------------------------------------------
    double nrm = this.mag();
    this.x /= nrm;
    this.y /= nrm;
    this.z /= nrm;
    return nrm;
  }


  // =====================================================================================
  /** @brief Normalize.
   * 
   *  Normalize this vector. self = self / ||self||
   */
  // -------------------------------------------------------------------------------------
  public double norm( Vec3 src ) {
    // -----------------------------------------------------------------------------------
    double nrm = src.mag();
    this.x = src.x / nrm;
    this.y = src.y / nrm;
    this.z = src.z / nrm;
    return nrm;
  }








  // =====================================================================================
  /** @brief Vector-scalar Addition.
   *  @param scl scalar input.
   * 
   *  Add scl to each element of this vector.
   */
  // -------------------------------------------------------------------------------------
  public void add( double scl ) {
    // -----------------------------------------------------------------------------------
    this.x += scl;
    this.y += scl;
    this.z += scl;
  }


  // =====================================================================================
  /** @brief Vector-scalar Subtraction.
   *  @param scl scalar input.
   * 
   *  Subtract scl from each element of this vector.
   */
  // -------------------------------------------------------------------------------------
  public void sub( double scl ) {
    // -----------------------------------------------------------------------------------
    this.x -= scl;
    this.y -= scl;
    this.z -= scl;
  }


  // =====================================================================================
  /** @brief Vector-scalar Multiplication.
   *  @param scl scalar input.
   * 
   *  Multiply each element of this vector by scl.
   */
  // -------------------------------------------------------------------------------------
  public void pmul( double scl ) {
    // -----------------------------------------------------------------------------------
    this.x *= scl;
    this.y *= scl;
    this.z *= scl;
  }


  // =====================================================================================
  /** @brief Vector-scalar Division.
   *  @param scl scalar input.
   * 
   *  Divide each element of this vector by scl.
   */
  // -------------------------------------------------------------------------------------
  public void pdiv( double scl ) {
    // -----------------------------------------------------------------------------------
    this.x /= scl;
    this.y /= scl;
    this.z /= scl;
  }








  // =====================================================================================
  /** @brief Element Wise Inplace Vector Addition.
   *  @param v1 reference to the right source vector.
   * 
   *  Add each element of m1 to this vector.
   */
  // -------------------------------------------------------------------------------------
  public void add( Vec3 v1 ) {
    // -----------------------------------------------------------------------------------
    this.x += v1.x;
    this.y += v1.y;
    this.z += v1.z;
  }


  // =====================================================================================
  /** @brief Element Wise Inplace Vector Subtraction.
   *  @param v1   reference to the right source vector.
   * 
   *  Subtract each element of m1 from this vector.
   */
  // -------------------------------------------------------------------------------------
  public void sub( Vec3 v1 ) {
    // -----------------------------------------------------------------------------------
    this.x -= v1.x;
    this.y -= v1.y;
    this.z -= v1.z;
  }


  // =====================================================================================
  /** @brief Element Wise Inplace Vector Multiplication.
   *  @param v1 reference to the right source vector.
   * 
   *  Multiply each element of this vector by m1.
   */
  // -------------------------------------------------------------------------------------
  public void pmul( Vec3 v1 ) {
    // -----------------------------------------------------------------------------------
    this.x *= v1.x;
    this.y *= v1.y;
    this.z *= v1.z;
  }


  // =====================================================================================
  /** @brief Element Wise Inplace Vector Division.
   *  @param v1 reference to the right source vector.
   * 
   *  Divide each element of this vector by m1.
   */
  // -------------------------------------------------------------------------------------
  public void pdiv( Vec3 v1 ) {
    // -----------------------------------------------------------------------------------
    this.x /= v1.x;
    this.y /= v1.y;
    this.z /= v1.z;
  }








  // =====================================================================================
  /** @brief Vector-scalar Addition.
   *  @param v1  reference to the left source vector.
   *  @param scl scalar input.
   * 
   *  Add scl to each element of v1 and store in this vector.
   */
  // -------------------------------------------------------------------------------------
  public void add( Vec3 v1, double scl ) {
    // -----------------------------------------------------------------------------------
    this.x = v1.x + scl;
    this.y = v1.y + scl;
    this.z = v1.z + scl;
  }


  // =====================================================================================
  /** @brief Vector-scalar Subtraction.
   *  @param v1  reference to the left source vector.
   *  @param scl scalar input.
   * 
   *  Subtract scl from each element of v1 and store in this vector.
   */
  // -------------------------------------------------------------------------------------
  public void sub( Vec3 v1, double scl ) {
    // -----------------------------------------------------------------------------------
    this.x = v1.x - scl;
    this.y = v1.y - scl;
    this.z = v1.z - scl;
  }


  // =====================================================================================
  /** @brief Vector-scalar Multiplication.
   *  @param v1  reference to the left source vector.
   *  @param scl scalar input.
   * 
   *  Multiply each element of v1 by scl and store in this vector.
   */
  // -------------------------------------------------------------------------------------
  public void pmul( Vec3 v1, double scl ) {
    // -----------------------------------------------------------------------------------
    this.x = v1.x * scl;
    this.y = v1.y * scl;
    this.z = v1.z * scl;
  }


  // =====================================================================================
  /** @brief Vector-scalar Division.
   *  @param v1  reference to the left source vector.
   *  @param scl scalar input.
   * 
   *  Divide each element of v1 by scl and store in this vector.
   */
  // -------------------------------------------------------------------------------------
  public void pdiv( Vec3 v1, double scl ) {
    // -----------------------------------------------------------------------------------
    this.x = v1.x / scl;
    this.y = v1.y / scl;
    this.z = v1.z / scl;
  }








  // =====================================================================================
  /** @brief Element Wise Vector Addition.
   *  @param v1   reference to the left  source vector.
   *  @param v2   reference to the right source vector.
   * 
   *  Add each element of v1 to v2 and store in this vector.
   */
  // -------------------------------------------------------------------------------------
  public void add( Vec3 v1, Vec3 v2 ) {
    // -----------------------------------------------------------------------------------
    this.x = v1.x + v2.x;
    this.y = v1.y + v2.y;
    this.z = v1.z + v2.z;
  }


  // =====================================================================================
  /** @brief Element Wise Vector Subtraction.
   *  @param v1   reference to the left  source vector.
   *  @param v2   reference to the right source vector.
   * 
   *  Subtract each element of v2 from v1 and store in this vector.
   */
  // -------------------------------------------------------------------------------------
  public void sub( Vec3 v1, Vec3 v2 ) {
    // -----------------------------------------------------------------------------------
    this.x = v1.x - v2.x;
    this.y = v1.y - v2.y;
    this.z = v1.z - v2.z;
  }


  // =====================================================================================
  /** @brief Element Wise Vector Multiplication.
   *  @param v1   reference to the left  source vector.
   *  @param v2   reference to the right source vector.
   * 
   *  Multipliy each element of v1 by v2 and store in this vector.
   */
  // -------------------------------------------------------------------------------------
  public void pmul( Vec3 v1, Vec3 v2 ) {
    // -----------------------------------------------------------------------------------
    this.x = v1.x * v2.x;
    this.y = v1.y * v2.y;
    this.z = v1.z * v2.z;
  }


  // =====================================================================================
  /** @brief Element Wise Vector Division.
   *  @param v1   reference to the left  source vector.
   *  @param v2   reference to the right source vector.
   * 
   *  Divide each element of v1 by v2 and store in this vector.
   */
  // -------------------------------------------------------------------------------------
  public void pdiv( Vec3 v1, Vec3 v2 ) {
    // -----------------------------------------------------------------------------------
    this.x = v1.x / v2.x;
    this.y = v1.y / v2.y;
    this.z = v1.z / v2.z;
  }








  // =====================================================================================
  /** @brief Load.
   *  @param dst reference to a source array.
   *  @param offset starting index.
   *  @return pointer to next unused array index.
   */
  // -------------------------------------------------------------------------------------
  public int load( double[] src ) {
    // -----------------------------------------------------------------------------------
    this.x = src[0];
    this.y = src[1];
    this.z = src[2];
    return 3;
  }


  // =====================================================================================
  /** @brief Load.
   *  @param dst reference to a source array.
   *  @param offset starting index.
   *  @return pointer to next unused array index.
   */
  // -------------------------------------------------------------------------------------
  public int load( double[] src, int offset ) {
    // -----------------------------------------------------------------------------------
    this.x = src[offset];
    this.y = src[offset+1];
    this.z = src[offset+2];
    return offset+3;
  }


  // =====================================================================================
  /** @brief Store.
   *  @param dst reference to a destination array.
   *  @param offset starting index.
   *  @return pointer to next unused array index.
   */
  // -------------------------------------------------------------------------------------
  public int store( double[] src ) {
    // -----------------------------------------------------------------------------------
    src[0] = this.x;
    src[1] = this.y;
    src[2] = this.z;
    return 3;
  }


  // =====================================================================================
  /** @brief Store.
   *  @param dst reference to a destination array.
   *  @param offset starting index.
   *  @return pointer to next unused array index.
   */
  // -------------------------------------------------------------------------------------
  public int store( double[] src, int offset ) {
    // -----------------------------------------------------------------------------------
    src[offset]   = this.x;
    src[offset+1] = this.y;
    src[offset+2] = this.z;
    return offset+3;
  }


  // =====================================================================================
  /** @brief Inner Product.
   *  @return inner product of this vector with itself.
   *
   *  Return the inner product of this vector with itself. dot = self' * self
   */
  // -------------------------------------------------------------------------------------
  public double dot( ) {
    // -----------------------------------------------------------------------------------
    return this.x*this.x + this.y*this.y + this.z*this.z;
  }


  // =====================================================================================
  /** @brief Inner Product.
   *  @param rhs right hand side vector.
   *  @return inner product of this vector with itself.
   *
   *  Return the inner product of this vector with itself. dot = self' * rhs
   */
  // -------------------------------------------------------------------------------------
  public double dot( Vec3 rhs ) {
    // -----------------------------------------------------------------------------------
    return this.x*rhs.x + this.y*rhs.y + this.z*rhs.z;
  }




  // =====================================================================================
  /** @brief Outer Product.
   *  @param lhs left hand side vector.
   *  @param rhs right hand side vector.
   *
   *  Set this vector to the outer product of the two vectors. This = lhs (cross) rhs.
   */
  // -------------------------------------------------------------------------------------
  public void cross( Vec3 lhs, Vec3 rhs ) {
    // -----------------------------------------------------------------------------------
    this.x = (lhs.y*rhs.z) - (lhs.z*rhs.y);
    this.y = (lhs.z*rhs.x) - (lhs.x*rhs.z);
    this.z = (lhs.x*rhs.y) - (lhs.y*rhs.x);
  }

  // =====================================================================================
  /** @brief Sum Square Difference.
   *  @return the sum of the squares of the elements.
   *
   *  Compute the sum of the squares of the elements   ret = sum e[i]**2
   */
  // -------------------------------------------------------------------------------------
  public double sumsq( ) {
    // -----------------------------------------------------------------------------------
    return this.x*this.x + this.y*this.y + this.z*this.z;
  }


  // =====================================================================================
  /** @brief Sum Square Difference.
   *  @param a reference to a vector.
   *  @param b reference to another vector.
   *  @return the sum of the squares of the differences.
   *
   *  Compute the sum of the squares of the differences   ret = sum (a[i]-b[i])**2
   */
  // -------------------------------------------------------------------------------------
  public double sumsq( Vec3 rhs ) {
    // -----------------------------------------------------------------------------------
    double dx = this.x - rhs.x;
    double dy = this.y - rhs.y;
    double dz = this.z - rhs.z;
    return dx*dx + dy*dy + dz*dz;
  }


  // =====================================================================================
  /** @brief Swap.
   *  @param src reference to the second vector.
   * 
   *  Swap each element of this vector with those in the second vector.
   */
  // -------------------------------------------------------------------------------------
  public void swap( Vec3 src ) {
    // -----------------------------------------------------------------------------------
    double tx = this.x; this.x = src.x; src.x = tx;
    double ty = this.y; this.y = src.y; src.y = ty;
    double tz = this.z; this.z = src.z; src.z = tz;
  }


  // =====================================================================================
  /** @brief Convert Vector to String.
   *  @param fmt format specifier of each element.
   *  @param dlm delimeter separating each element.
   */
  // -------------------------------------------------------------------------------------
  public String toString( String fmt, String dlm ) {
    // -----------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    buffer.append( String.format( fmt, this.x ) );
    buffer.append( dlm );
    buffer.append( String.format( fmt, this.y ) );
    buffer.append( dlm );
    buffer.append( String.format( fmt, this.z ) );

    return buffer.toString();
  }


  // =====================================================================================
  /** @brief Convert Vector to String.
   *  @param fmt format specifier of each element.
   */
  // -------------------------------------------------------------------------------------
  public String toString( String fmt ) {
    // -----------------------------------------------------------------------------------
    return this.toString( fmt, " " );
  }


  // =====================================================================================
  /** @brief Convert Vector to String.
   */
  // -------------------------------------------------------------------------------------
  public String toString( ) {
    // -----------------------------------------------------------------------------------
    return this.toString( "%g", " " );
  }


  // =====================================================================================
  /** @brief Display vector.
   *  @param fmt format specifier of each element.
   *
   *  Display as a string a vector that if compatable with Octave/Matlab.
   */
  // -------------------------------------------------------------------------------------
  public String octave_toString( String fmt ) {
    // -----------------------------------------------------------------------------------
    return "[ "+ this.toString( fmt, " " ) + " ]';";
  }

    
  // =====================================================================================
  /** @brief Display vector.
   *
   *  Display as a string a vector that if compatable with Octave/Matlab.
   */
  // -------------------------------------------------------------------------------------
  public String octave_toString( ) {
    // -----------------------------------------------------------------------------------
    return this.octave_toString( "%g" );
  }

    
  // =====================================================================================
  /** @brief Display vector.
   *  @param fmt format specifier of each element.
   *
   *  Display as a string a vector that if compatable with Sage Math.
   */
  // -------------------------------------------------------------------------------------
  public String sage_toString( String fmt ) {
    // -----------------------------------------------------------------------------------
    return "Matrix(SR,[" + this.toString( fmt, "," ) + "])";
  }

    
  // =====================================================================================
  /** @brief Display vector.
   *  @param fmt format specifier of each element.
   *
   *  Display as a string a vector that if compatable with Sage Math.
   */
  // -------------------------------------------------------------------------------------
  public String sage_toString( ) {
    // -----------------------------------------------------------------------------------
    return this.sage_toString( "%g" );
  }
}


// =======================================================================================
// **                                      V E C 3                                      **
// ======================================================================== END FILE =====
