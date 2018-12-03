// ====================================================================== BEGIN FILE =====
// **                                     M A T H 2                                     **
// =======================================================================================
// **                                                                                   **
// **  This file is part of the TRNCMP Research Library. (formerly SolLib)              **
// **                                                                                   **
// **  Copyright (c) 2011, Stephen W. Soliday                                           **
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
// @file Math2.java
// <p>
// Provides extended math.
// $Id$
// $Log$
//
// @author Stephen W. Soliday
// @date 2011-06-04
//
// =======================================================================================

package org.trncmp.lib;

import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;

// =======================================================================================
public class Math2 implements MathConstants {
  // -----------------------------------------------------------------------------------

  // ===================================================================================
  // -----------------------------------------------------------------------------------
  public static int Pow2( int bitpix ) {
    // -------------------------------------------------------------------------------
    int rv = 1;
    for ( int i=0; i<bitpix; i++ ) {
      rv *= 2;
    }
    return rv;
  }

  // ===================================================================================
  /**
   * Arc Tangent.
   * Provides a full four quadrant arc tangent.
   * @param y numerator coordinate
   * @param x denominator coordinate
   * @return radian in range 0 <= r < 2PI
   */
  // -----------------------------------------------------------------------------------
  public static double ArcTan( double y, double x ) {
    // -------------------------------------------------------------------------------
    if (x == N_ZERO)
      if (y > N_ZERO)       return  N_PI_2;
      else if (y < N_ZERO)  return  N_3PI_2;
      else                  return  N_ZERO;

    if (y > N_ZERO)           return  Math.atan2( y, x );
    else if (y < N_ZERO)      return  N_2PI + Math.atan2( y, x );
    else if (x > N_ZERO)      return  N_ZERO;

    return  N_PI;
  }

  // ===================================================================================
  /** Parametric.
   *  Compute the parametric value in the range (A, B) for parameter t
   * @param A t=0 value
   * @param B t=1 value
   * @param t parameter 0 <= t <= 1
   * @return parametric value V,  A <= V <= B
   */
  // -----------------------------------------------------------------------------------
  public static double PARAMETRIC( double A, double B, double t ) {
    // -------------------------------------------------------------------------------
    return t * ( B - A ) + A;
  }

  // ===================================================================================
  /** Reverse parametric.
   *  Compute the parameter for a value in range (A, B) 
   * @param A t=0 value
   * @param B t=1 value
   * @param V value A <= V <= B
   * @return parameter t,  0 <= t <= 1
   */
  // -----------------------------------------------------------------------------------
  public static double REVPARAM( double A, double B, double V ) {
    // -------------------------------------------------------------------------------
    return (V-A)/(B-A);
  }
  // ===================================================================================
  /** Arbitray Root.
   *  Find the \a d root of \a x using the log identity.
   *  @param x number.
   *  @param d root.
   *  @return the \a d root of \a x.
   */
  // -----------------------------------------------------------------------------------
  public static double root( double x, double d ) {
    // -------------------------------------------------------------------------------
    return Math.exp(Math.log(x)/(d));
  }

  // ===================================================================================
  /** Arbitray Power.
   *  Find the \a d power of \a x using the log identity.
   *  @param x base.
   *  @param d power.
   *  @return the \a d power of \a x.
   */
  // -----------------------------------------------------------------------------------
  public static double power( double x, double d ) {
    // -------------------------------------------------------------------------------
    return Math.exp(Math.log(x)*(d));
  }

  // ===================================================================================
  /**
   *  Round V to d decimal places
   * @param v number to round
   * @param d number of decimal places to round to
   * @return number rounded to d decimal places
   */
  // -----------------------------------------------------------------------------------
  public static double round( double v, double div ) {
    // -------------------------------------------------------------------------------
    double temp = v * div;

    return Math.floor(temp+0.5) / div;
  }

  // ===================================================================================
  /**
   *  Round V to d decimal places
   * @param v number to round
   * @param d number of decimal places to round to
   * @return number rounded to d decimal places
   */
  // -----------------------------------------------------------------------------------
  public static double round( double v, int d ) {
    // -------------------------------------------------------------------------------
    double div = 1.0;
    for (int i=0; i<d; i++) {
      div *= 10.0;
    }

    double temp = v * div;

    return Math.floor(temp+0.5) / div;
  }


  public static boolean isZero(double a) {
    if (a < 0.0) { return false; }
    if (a > 0.0) { return false; }
    return true;
  }

  public static boolean isZero(double a, double tol) {
    if (a < -tol) { return false; }
    if (a >  tol) { return false; }
    return true;
  }

  public static boolean isZero(float a) {
    if (a < 0.0f) { return false; }
    if (a > 0.0f) { return false; }
    return true;
  }

  public static boolean isNotZero(float a) {
    if (a < 0.0f) { return true; }
    if (a > 0.0f) { return true; }
    return false;
  }

  public static boolean isNotZero(double a) {
    if (a < 0.0) { return true; }
    if (a > 0.0) { return true; }
    return false;
  }

  public static boolean isEqual(double a, double b) {
    if (a < b) { return false; }
    if (a > b) { return false; }
    return true;
  }

  public static boolean isNotEqual(double a, double b) {
    if (a < b) { return true; }
    if (a > b) { return true; }
    return false;
  }

  public static boolean isEqual(float a, float b) {
    if (a < b) { return false; }
    if (a > b) { return false; }
    return true;
  }

  public static boolean isEqual(double a, double b, double tol) {
    double dif = a - b;
    if (dif < -tol) { return false; }
    if (dif >  tol) { return false; }
    return true;
  }

  public static boolean isNotEqual(double a, double b, double tol) {
    double dif = a - b;
    if (dif < -tol) { return true; }
    if (dif >  tol) { return true; }
    return false;
  }

  public static double Min( double a, double b ) { if (b < a) { return b; } return a; }
  public static float  Min( float  a, float  b ) { if (b < a) { return b; } return a; }
  public static int    Min( int    a, int    b ) { if (b < a) { return b; } return a; }
  public static long   Min( long   a, long   b ) { if (b < a) { return b; } return a; }

  public static double Max( double a, double b ) { if (b > a) { return b; } return a; }
  public static float  Max( float  a, float  b ) { if (b > a) { return b; } return a; }
  public static int    Max( int    a, int    b ) { if (b > a) { return b; } return a; }
  public static long   Max( long   a, long   b ) { if (b > a) { return b; } return a; }

  public static double Abs( double a ) { if (0.0e0 > a) { return -a; } return a; }
  public static float  Abs( float  a ) { if (0.0e0 > a) { return -a; } return a; }
  public static int    Abs( int    a ) { if (0     > a) { return -a; } return a; }
  public static long   Abs( long   a ) { if (0     > a) { return -a; } return a; }


  public static double fraction( double a ) {
    return a - Math.floor( a );
  }

  // ===================================================================================
  /** @brief Maximum Index
   *  @param x array of integers.
   *  @return index of the maximum array element.
   *  <p>
   * Find the array element that contains the maximum value.
   */
  // -----------------------------------------------------------------------------------
  public static int MaxIndex( int[] x ) {
    // -------------------------------------------------------------------------------
    int n   = x.length;
    int idx = 0;
    int mxv = x[0];
    for ( int i=1; i<n; i++ ) {
      if ( x[i] > mxv ) { idx=i; mxv = x[i]; }
    }
    return idx;
  }

  // ===================================================================================
  /** @brief Minimum Index
   *  @param x array of integers.
   *  @return index of the minimum array element.
   *  <p>
   * Find the array element that contains the minimum value.
   */
  // -----------------------------------------------------------------------------------
  public static int MinIndex( int[] x ) {
    // -------------------------------------------------------------------------------
    int n   = x.length;
    int idx = 0;
    int mnv = x[0];
    for ( int i=1; i<n; i++ ) {
      if ( x[i] < mnv ) { idx=i; mnv = x[i]; }
    }
    return idx;
  }


  // ===================================================================================
  /** @brief Maximum Index
   *  @param x array of reals.
   *  @return index of the maximum array element.
   *  <p>
   * Find the array element that contains the maximum value.
   */
  // -----------------------------------------------------------------------------------
  public static int MaxIndex( double[] x ) {
    // -------------------------------------------------------------------------------
    int    n   = x.length;
    int    idx = 0;
    double mxv = x[0];
    for ( int i=1; i<n; i++ ) {
      if ( x[i] > mxv ) { idx=i; mxv = x[i]; }
    }
    return idx;
  }

  // ===================================================================================
  /** @brief Minimum Index
   *  @param x array of reals.
   *  @return index of the minimum array element.
   *  <p>
   * Find the array element that contains the minimum value.
   */
  // -----------------------------------------------------------------------------------
  public static int MinIndex( double[] x ) {
    // -------------------------------------------------------------------------------
    int    n   = x.length;
    int    idx = 0;
    double mnv = x[0];
    for ( int i=1; i<n; i++ ) {
      if ( x[i] < mnv ) { idx=i; mnv = x[i]; }
    }
    return idx;
  }

  public static int    MaxValue( int[]    x ) { return x[ Math2.MaxIndex(x) ]; }
  public static int    MinValue( int[]    x ) { return x[ Math2.MinIndex(x) ]; }
  public static double MaxValue( double[] x ) { return x[ Math2.MaxIndex(x) ]; }
  public static double MinValue( double[] x ) { return x[ Math2.MinIndex(x) ]; }

  // =====================================================================================
  /** Minimum row col.
   *  @param coord row and column containing minimum value.
   *  @param array pointer to 2D array.
   */
  // -------------------------------------------------------------------------------------
  public static int[] minarg( double[][] array ) {
    // -----------------------------------------------------------------------------------
    int nr = array.length;
    int nc = array[0].length;

    double min_val = array[0][0];
    int    min_row = 0;
    int    min_col = 0;
    
    for ( int r=0; r<nr; r++ ) {
      for ( int c=0; c<nc; c++ ) {
        double test = array[r][c];
        if ( test < min_val ) {
          min_val = test;
          min_row = r;
          min_col = c;
        }
      }
    }

    int[] rv = { min_row, min_col };

    return rv;
  }


  // =====================================================================================
  /** Maximum row col.
   *  @param coord row and column containing maximum value.
   *  @param array pointer to 2D array.
   */
  // -------------------------------------------------------------------------------------
  public static int[] maxarg( double[][] array ) {
    // -----------------------------------------------------------------------------------
    int nr = array.length;
    int nc = array[0].length;

    double max_val = array[0][0];
    int    max_row = 0;
    int    max_col = 0;
    
    for ( int r=0; r<nr; r++ ) {
      for ( int c=0; c<nc; c++ ) {
        double test = array[r][c];
        if ( test > max_val ) {
          max_val = test;
          max_row = r;
          max_col = c;
        }
      }
    }

    int[] rv = { max_row, max_col };

    return rv;
  }



  // ===================================================================================
  public static double dist2( double x1, double x2 ) {
    // -------------------------------------------------------------------------------
    double dx = x1 - x2;
    return (dx*dx);
  }


  // ===================================================================================
  public static double dist2( double x1, double y1, double x2, double y2 ) {
    // -------------------------------------------------------------------------------
    double dx = x1 - x2;
    double dy = y1 - y2;
    return (dx*dx) + (dy*dy);
  }


  // ===================================================================================
  public static double dist2( double x1, double y1, double z1,
                              double x2, double y2, double z2 ) {
    // -------------------------------------------------------------------------------
    double dx = x1 - x2;
    double dy = y1 - y2;
    double dz = z1 - z2;
    return (dx*dx) + (dy*dy) + (dz*dz);
  }

  // ===================================================================================
  /** @brief Sum of square difference.
   *  @param R1 pointer to the first  1D array.
   *  @param R1 pointer to the second 1D array.
   *  @param n number of elements.
   *  @return sum of the squares: s = sum(i=1,n) [ ( R1(i)-R2(i) )**2 ]
   *  <p>
   *  Sum the square of the difference of each element of the arrays.
   */
  // -----------------------------------------------------------------------------------
  public static double dist2( double[] R1, double[] R2 ) {
    // -------------------------------------------------------------------------------
    int    n   = R1.length;
    double sum = 0.0e0;
	
    for ( int i=0; i<n; i++ ) {
      double d = R1[i] - R2[i];
      sum += ( d*d );
    }
	
    return sum;
  }

  // ===================================================================================
  /** @brief Zero.
   *  @param array pointer to an array. 
   *  <p>
   *  Set all the elements of the array to zero.
   */
  // -----------------------------------------------------------------------------------
  public static void zero( short[] array ) {
    // -------------------------------------------------------------------------------
    int n = array.length;
    for ( int i=0; i<n; i++ ) {
      array[i] = (short) 0;
    }
  }

  // ===================================================================================
  /** @brief Zero.
   *  @param array pointer to an array. 
   *  <p>
   *  Set all the elements of the array to zero.
   */
  // -----------------------------------------------------------------------------------
  public static void zero( int[] array ) {
    // -------------------------------------------------------------------------------
    int n = array.length;
    for ( int i=0; i<n; i++ ) {
      array[i] = (int) 0;
    }
  }

  // ===================================================================================
  /** @brief Zero.
   *  @param array pointer to an array. 
   *  <p>
   *  Set all the elements of the array to zero.
   */
  // -----------------------------------------------------------------------------------
  public static void zero( long[] array ) {
    // -------------------------------------------------------------------------------
    int n = array.length;
    for ( int i=0; i<n; i++ ) {
      array[i] = (long) 0;
    }
  }

  // ===================================================================================
  /** @brief Zero.
   *  @param array pointer to an array. 
   *  <p>
   *  Set all the elements of the array to zero.
   */
  // -----------------------------------------------------------------------------------
  public static void zero( float[] array ) {
    // -------------------------------------------------------------------------------
    int n = array.length;
    for ( int i=0; i<n; i++ ) {
      array[i] = (float) 0.0;
    }
  }

  // ===================================================================================
  /** @brief Zero.
   *  @param array pointer to an array. 
   *  <p>
   *  Set all the elements of the array to zero.
   */
  // -----------------------------------------------------------------------------------
  public static void zero( double[] array ) {
    // -------------------------------------------------------------------------------
    int n = array.length;
    for ( int i=0; i<n; i++ ) {
      array[i] = (double) 0.0;
    }
  }



  // ===================================================================================
  /** @brief Copy.
   *  @param dst pointer to the destination array.
   *  @param src pointer to the source array.
   *  <p>
   *  Copy each element of the source array into the destination array.
   *  <p>
   *  @note it is the programer's responsibility to ensure that the receiving array will
   *        not over flow.
   */
  // -----------------------------------------------------------------------------------
  public static void copy( short[] dst, short[] src ) {
    // -------------------------------------------------------------------------------
    int n = src.length;
    for ( int i=0; i<n; i++ ) {
      dst[i] = src[i];
    }
  }


  // ===================================================================================
  /** @brief Copy.
   *  @param dst pointer to the destination array.
   *  @param src pointer to the source array.
   *  <p>
   *  Copy each element of the source array into the destination array.
   *  <p>
   *  @note it is the programer's responsibility to ensure that the receiving array will
   *        not over flow.
   */
  // -----------------------------------------------------------------------------------
  public static void copy( int[] dst, int[] src ) {
    // -------------------------------------------------------------------------------
    int n = src.length;
    for ( int i=0; i<n; i++ ) {
      dst[i] = src[i];
    }
  }


  // ===================================================================================
  /** @brief Copy.
   *  @param dst pointer to the destination array.
   *  @param src pointer to the source array.
   *  <p>
   *  Copy each element of the source array into the destination array.
   *  <p>
   *  @note it is the programer's responsibility to ensure that the receiving array will
   *        not over flow.
   */
  // -----------------------------------------------------------------------------------
  public static void copy( long[] dst, long[] src ) {
    // -------------------------------------------------------------------------------
    int n = src.length;
    for ( int i=0; i<n; i++ ) {
      dst[i] = src[i];
    }
  }


  // ===================================================================================
  /** @brief Copy.
   *  @param dst pointer to the destination array.
   *  @param src pointer to the source array.
   *  <p>
   *  Copy each element of the source array into the destination array.
   *  <p>
   *  @note it is the programer's responsibility to ensure that the receiving array will
   *        not over flow.
   */
  // -----------------------------------------------------------------------------------
  public static void copy( float[] dst, float[] src ) {
    // -------------------------------------------------------------------------------
    int n = src.length;
    for ( int i=0; i<n; i++ ) {
      dst[i] = src[i];
    }
  }


  // ===================================================================================
  /** @brief Copy.
   *  @param dst pointer to the destination array.
   *  @param src pointer to the source array.
   *  <p>
   *  Copy each element of the source array into the destination array.
   *  <p>
   *  @note it is the programer's responsibility to ensure that the receiving array will
   *        not over flow.
   */
  // -----------------------------------------------------------------------------------
  public static void copy( double[] dst, double[] src ) {
    // -------------------------------------------------------------------------------
    int n = src.length;
    for ( int i=0; i<n; i++ ) {
      dst[i] = src[i];
    }
  }






  // ===================================================================================
  /** @brief Sum of the squares.
   *  @param array pointer to an 1D array.
   *  @return sum of the squares: s = sum(i=1,n) array(i)
   *  <p>
   *  Sum the square of each element of the array.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( short[] array ) {
    // -------------------------------------------------------------------------------
    int    n   = array.length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      sum += ( (double)array[i] * (double)array[i] );
    }
    return sum;
  }

  // ===================================================================================
  /** @brief Sum of the squares.
   *  @param array pointer to an 1D array.
   *  @return sum of the squares: s = sum(i=1,n) array(i)
   *  <p>
   *  Sum the square of each element of the array.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( int[] array ) {
    // -------------------------------------------------------------------------------
    int    n   = array.length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      sum += ( (double)array[i] * (double)array[i] );
    }
    return sum;
  }

  // ===================================================================================
  /** @brief Sum of the squares.
   *  @param array pointer to an 1D array.
   *  @return sum of the squares: s = sum(i=1,n) array(i)
   *  <p>
   *  Sum the square of each element of the array.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( long[] array ) {
    // -------------------------------------------------------------------------------
    int    n   = array.length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      sum += ( (double)array[i] * (double)array[i] );
    }
    return sum;
  }

  // ===================================================================================
  /** @brief Sum of the squares.
   *  @param array pointer to an 1D array.
   *  @return sum of the squares: s = sum(i=1,n) array(i)
   *  <p>
   *  Sum the square of each element of the array.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( float[] array ) {
    // -------------------------------------------------------------------------------
    int    n   = array.length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      sum += ( (double)array[i] * (double)array[i] );
    }
    return sum;
  }

  // ===================================================================================
  /** @brief Sum of the squares.
   *  @param array pointer to an 1D array.
   *  @return sum of the squares: s = sum(i=1,n) array(i)
   *  <p>
   *  Sum the square of each element of the array.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( double[] array ) {
    // -------------------------------------------------------------------------------
    int    n   = array.length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      sum += ( array[i] * array[i] );
    }
    return sum;
  }





  // ===================================================================================
  /** @brief Sum of square difference.
   *  @param R1 pointer to the first  1D array.
   *  @param R1 pointer to the second 1D array.
   *  @param n number of elements.
   *  @return sum of the squares: s = sum(i=1,n) [ ( R1(i)-R2(i) )**2 ]
   *  <p>
   *  Sum the square of the difference of each element of the arrays.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( short[] R1, short[] R2 ) {
    // -------------------------------------------------------------------------------
    int    n   = R1.length;
    double sum = 0.0e0;
	
    for ( int i=0; i<n; i++ ) {
      double d = (double)(R1[i] - R2[i]);
      sum += ( d*d );
    }
	
    return sum;
  }

  // ===================================================================================
  /** @brief Sum of square difference.
   *  @param R1 pointer to the first  1D array.
   *  @param R1 pointer to the second 1D array.
   *  @param n number of elements.
   *  @return sum of the squares: s = sum(i=1,n) [ ( R1(i)-R2(i) )**2 ]
   *  <p>
   *  Sum the square of the difference of each element of the arrays.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( int[] R1, int[] R2 ) {
    // -------------------------------------------------------------------------------
    int    n   = R1.length;
    double sum = 0.0e0;
	
    for ( int i=0; i<n; i++ ) {
      double d = (double)(R1[i] - R2[i]);
      sum += ( d*d );
    }
	
    return sum;
  }

  // ===================================================================================
  /** @brief Sum of square difference.
   *  @param R1 pointer to the first  1D array.
   *  @param R1 pointer to the second 1D array.
   *  @param n number of elements.
   *  @return sum of the squares: s = sum(i=1,n) [ ( R1(i)-R2(i) )**2 ]
   *  <p>
   *  Sum the square of the difference of each element of the arrays.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( long[] R1, long[] R2 ) {
    // -------------------------------------------------------------------------------
    int    n   = R1.length;
    double sum = 0.0e0;
	
    for ( int i=0; i<n; i++ ) {
      double d = (double)(R1[i] - R2[i]);
      sum += ( d*d );
    }
	
    return sum;
  }

  // ===================================================================================
  /** @brief Sum of square difference.
   *  @param R1 pointer to the first  1D array.
   *  @param R1 pointer to the second 1D array.
   *  @param n number of elements.
   *  @return sum of the squares: s = sum(i=1,n) [ ( R1(i)-R2(i) )**2 ]
   *  <p>
   *  Sum the square of the difference of each element of the arrays.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( float[] R1, float[] R2 ) {
    // -------------------------------------------------------------------------------
    int    n   = R1.length;
    double sum = 0.0e0;
	
    for ( int i=0; i<n; i++ ) {
      double d = (double)(R1[i] - R2[i]);
      sum += ( d*d );
    }
	
    return sum;
  }

  // ===================================================================================
  /** @brief Sum of square difference.
   *  @param R1 pointer to the first  1D array.
   *  @param R1 pointer to the second 1D array.
   *  @param n number of elements.
   *  @return sum of the squares: s = sum(i=1,n) [ ( R1(i)-R2(i) )**2 ]
   *  <p>
   *  Sum the square of the difference of each element of the arrays.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( double[] R1, double[] R2 ) {
    // -------------------------------------------------------------------------------
    int    n   = R1.length;
    double sum = 0.0e0;
	
    for ( int i=0; i<n; i++ ) {
      double d = R1[i] - R2[i];
      sum += ( d*d );
    }
	
    return sum;
  }




  // ===================================================================================
  /** @brief Weighted sum.
   *  @param W pointer to 1D array of wieghts.
   *  @param X pointer to 1D array of values.
   *  @return sum of the products: s = sum(i=1,n) W(i)*X(i)
   *  <p>
   *  Perform a weighted sum.
   */
  // -----------------------------------------------------------------------------------
  public static double wsum( short[] W, short[] X ) {
    // -------------------------------------------------------------------------------
    int    n   = X.length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      sum += ( (double)W[i] * (double)X[i] );
    }
    return sum;
  }



  // ===================================================================================
  /** @brief Weighted sum.
   *  @param W pointer to 1D array of wieghts.
   *  @param X pointer to 1D array of values.
   *  @return sum of the products: s = sum(i=1,n) W(i)*X(i)
   *  <p>
   *  Perform a weighted sum.
   */
  // -----------------------------------------------------------------------------------
  public static double wsum( int[] W, int[] X ) {
    // -------------------------------------------------------------------------------
    int    n   = X.length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      sum += ( (double)W[i] * (double)X[i] );
    }
    return sum;
  }



  // ===================================================================================
  /** @brief Weighted sum.
   *  @param W pointer to 1D array of wieghts.
   *  @param X pointer to 1D array of values.
   *  @return sum of the products: s = sum(i=1,n) W(i)*X(i)
   *  <p>
   *  Perform a weighted sum.
   */
  // -----------------------------------------------------------------------------------
  public static double wsum( long[] W, long[] X ) {
    // -------------------------------------------------------------------------------
    int    n   = X.length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      sum += ( (double)W[i] * (double)X[i] );
    }
    return sum;
  }



  // ===================================================================================
  /** @brief Weighted sum.
   *  @param W pointer to 1D array of wieghts.
   *  @param X pointer to 1D array of values.
   *  @return sum of the products: s = sum(i=1,n) W(i)*X(i)
   *  <p>
   *  Perform a weighted sum.
   */
  // -----------------------------------------------------------------------------------
  public static double wsum( float[] W, float[] X ) {
    // -------------------------------------------------------------------------------
    int    n   = X.length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      sum += ( (double)W[i] * (double)X[i] );
    }
    return sum;
  }



  // ===================================================================================
  /** @brief Weighted sum.
   *  @param W pointer to 1D array of wieghts.
   *  @param X pointer to 1D array of values.
   *  @return sum of the products: s = sum(i=1,n) W(i)*X(i)
   *  <p>
   *  Perform a weighted sum.
   */
  // -----------------------------------------------------------------------------------
  public static double wsum( double[] W, double[] X ) {
    // -------------------------------------------------------------------------------
    int    n   = X.length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      sum += ( W[i] * X[i] );
    }
    return sum;
  }



















  // ===================================================================================
  /** @brief Zero.
   *  @param array pointer to an array. 
   *  <p>
   *  Set all the elements of the array to zero.
   */
  // -----------------------------------------------------------------------------------
  public static void zero( short[][] array ) {
    // -------------------------------------------------------------------------------
    int n = array.length;
    int m = array[0].length;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        array[i][j] = (short) 0;
      }
    }
  }


  // ===================================================================================
  /** @brief Zero.
   *  @param array pointer to an array. 
   *  <p>
   *  Set all the elements of the array to zero.
   */
  // -----------------------------------------------------------------------------------
  public static void zero( int[][] array ) {
    // -------------------------------------------------------------------------------
    int n = array.length;
    int m = array[0].length;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        array[i][j] = (int) 0;
      }
    }
  }


  // ===================================================================================
  /** @brief Zero.
   *  @param array pointer to an array. 
   *  <p>
   *  Set all the elements of the array to zero.
   */
  // -----------------------------------------------------------------------------------
  public static void zero( long[][] array ) {
    // -------------------------------------------------------------------------------
    int n = array.length;
    int m = array[0].length;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        array[i][j] = (long) 0;
      }
    }
  }


  // ===================================================================================
  /** @brief Zero.
   *  @param array pointer to an array. 
   *  <p>
   *  Set all the elements of the array to zero.
   */
  // -----------------------------------------------------------------------------------
  public static void zero( float[][] array ) {
    // -------------------------------------------------------------------------------
    int n = array.length;
    int m = array[0].length;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        array[i][j] = (float) 0.0e0;
      }
    }
  }


  // ===================================================================================
  /** @brief Zero.
   *  @param array pointer to an array. 
   *  <p>
   *  Set all the elements of the array to zero.
   */
  // -----------------------------------------------------------------------------------
  public static void zero( double[][] array ) {
    // -------------------------------------------------------------------------------
    int n = array.length;
    int m = array[0].length;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        array[i][j] = 0.0e0;
      }
    }
  }








  // ===================================================================================
  /** @brief Copy.
   *  @param dst pointer to the destination array.
   *  @param src pointer to the source array.
   *  <p>
   *  Copy each element of the source array into the destination array.
   *  <p>
   *  @note it is the programer's responsibility to ensure that the receiving array will
   *        not over flow.
   */
  // -----------------------------------------------------------------------------------
  public static void copy( short[][] dst, short[][] src ) {
    // -------------------------------------------------------------------------------
    int n = src.length;
    int m = src[0].length;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        dst[i][j] = src[i][j];
      }
    }
  }


  // ===================================================================================
  /** @brief Copy.
   *  @param dst pointer to the destination array.
   *  @param src pointer to the source array.
   *  <p>
   *  Copy each element of the source array into the destination array.
   *  <p>
   *  @note it is the programer's responsibility to ensure that the receiving array will
   *        not over flow.
   */
  // -----------------------------------------------------------------------------------
  public static void copy( int[][] dst, int[][] src ) {
    // -------------------------------------------------------------------------------
    int n = src.length;
    int m = src[0].length;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        dst[i][j] = src[i][j];
      }
    }
  }


  // ===================================================================================
  /** @brief Copy.
   *  @param dst pointer to the destination array.
   *  @param src pointer to the source array.
   *  <p>
   *  Copy each element of the source array into the destination array.
   *  <p>
   *  @note it is the programer's responsibility to ensure that the receiving array will
   *        not over flow.
   */
  // -----------------------------------------------------------------------------------
  public static void copy( long[][] dst, long[][] src ) {
    // -------------------------------------------------------------------------------
    int n = src.length;
    int m = src[0].length;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        dst[i][j] = src[i][j];
      }
    }
  }


  // ===================================================================================
  /** @brief Copy.
   *  @param dst pointer to the destination array.
   *  @param src pointer to the source array.
   *  <p>
   *  Copy each element of the source array into the destination array.
   *  <p>
   *  @note it is the programer's responsibility to ensure that the receiving array will
   *        not over flow.
   */
  // -----------------------------------------------------------------------------------
  public static void copy( float[][] dst, float[][] src ) {
    // -------------------------------------------------------------------------------
    int n = src.length;
    int m = src[0].length;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        dst[i][j] = src[i][j];
      }
    }
  }


  // ===================================================================================
  /** @brief Copy.
   *  @param dst pointer to the destination array.
   *  @param src pointer to the source array.
   *  <p>
   *  Copy each element of the source array into the destination array.
   *  <p>
   *  @note it is the programer's responsibility to ensure that the receiving array will
   *        not over flow.
   */
  // -----------------------------------------------------------------------------------
  public static void copy( double[][] dst, double[][] src ) {
    // -------------------------------------------------------------------------------
    int n = src.length;
    int m = src[0].length;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        dst[i][j] = src[i][j];
      }
    }
  }








  // ===================================================================================
  /** @brief Sum of the squares.
   *  @param array pointer to an 2D array.
   *  @return sum of the squares: s = sum(i=1,n) array(i)
   *  <p>
   *  Sum the square of each element of the array.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( short[][] array ) {
    // -------------------------------------------------------------------------------
    int    n   = array.length;
    int    m   = array[0].length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        sum += ( (double)array[i][j] * (double)array[i][j] );
      }
    }
    return sum;
  }


  // ===================================================================================
  /** @brief Sum of the squares.
   *  @param array pointer to an 2D array.
   *  @return sum of the squares: s = sum(i=1,n) array(i)
   *  <p>
   *  Sum the square of each element of the array.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( int[][] array ) {
    // -------------------------------------------------------------------------------
    int    n   = array.length;
    int    m   = array[0].length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        sum += ( (double)array[i][j] * (double)array[i][j] );
      }
    }
    return sum;
  }


  // ===================================================================================
  /** @brief Sum of the squares.
   *  @param array pointer to an 2D array.
   *  @return sum of the squares: s = sum(i=1,n) array(i)
   *  <p>
   *  Sum the square of each element of the array.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( long[][] array ) {
    // -------------------------------------------------------------------------------
    int    n   = array.length;
    int    m   = array[0].length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        sum += ( (double)array[i][j] * (double)array[i][j] );
      }
    }
    return sum;
  }


  // ===================================================================================
  /** @brief Sum of the squares.
   *  @param array pointer to an 2D array.
   *  @return sum of the squares: s = sum(i=1,n) array(i)
   *  <p>
   *  Sum the square of each element of the array.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( float[][] array ) {
    // -------------------------------------------------------------------------------
    int    n   = array.length;
    int    m   = array[0].length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        sum += ( (double)array[i][j] * (double)array[i][j] );
      }
    }
    return sum;
  }


  // ===================================================================================
  /** @brief Sum of the squares.
   *  @param array pointer to an 2D array.
   *  @return sum of the squares: s = sum(i=1,n) array(i)
   *  <p>
   *  Sum the square of each element of the array.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( double[][] array ) {
    // -------------------------------------------------------------------------------
    int    n   = array.length;
    int    m   = array[0].length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        sum += ( array[i][j] * array[i][j] );
      }
    }
    return sum;
  }








  // ===================================================================================
  /** @brief Sum of square difference.
   *  @param R1 pointer to the first  2D array.
   *  @param R1 pointer to the second 2D array.
   *  @param n number of elements.
   *  @return sum of the squares: s = sum(i=1,n) [ ( R1(i)-R2(i) )**2 ]
   *  <p>
   *  Sum the square of the difference of each element of the arrays.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( short[][] R1, short[][] R2 ) {
    // -------------------------------------------------------------------------------
    int    n   = R1.length;
    int    m   = R1[0].length;
    double sum = 0.0e0;
	
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        double d = (double)(R1[i][j] - R2[i][j]);
        sum += ( d*d );
      }
    }
	
    return sum;
  }

 
  // ===================================================================================
  /** @brief Sum of square difference.
   *  @param R1 pointer to the first  2D array.
   *  @param R1 pointer to the second 2D array.
   *  @param n number of elements.
   *  @return sum of the squares: s = sum(i=1,n) [ ( R1(i)-R2(i) )**2 ]
   *  <p>
   *  Sum the square of the difference of each element of the arrays.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( int[][] R1, int[][] R2 ) {
    // -------------------------------------------------------------------------------
    int    n   = R1.length;
    int    m   = R1[0].length;
    double sum = 0.0e0;
	
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        double d = (double)(R1[i][j] - R2[i][j]);
        sum += ( d*d );
      }
    }
	
    return sum;
  }

 
  // ===================================================================================
  /** @brief Sum of square difference.
   *  @param R1 pointer to the first  2D array.
   *  @param R1 pointer to the second 2D array.
   *  @param n number of elements.
   *  @return sum of the squares: s = sum(i=1,n) [ ( R1(i)-R2(i) )**2 ]
   *  <p>
   *  Sum the square of the difference of each element of the arrays.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( long[][] R1, long[][] R2 ) {
    // -------------------------------------------------------------------------------
    int    n   = R1.length;
    int    m   = R1[0].length;
    double sum = 0.0e0;
	
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        double d = (double)(R1[i][j] - R2[i][j]);
        sum += ( d*d );
      }
    }
	
    return sum;
  }

 
  // ===================================================================================
  /** @brief Sum of square difference.
   *  @param R1 pointer to the first  2D array.
   *  @param R1 pointer to the second 2D array.
   *  @param n number of elements.
   *  @return sum of the squares: s = sum(i=1,n) [ ( R1(i)-R2(i) )**2 ]
   *  <p>
   *  Sum the square of the difference of each element of the arrays.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( float[][] R1, float[][] R2 ) {
    // -------------------------------------------------------------------------------
    int    n   = R1.length;
    int    m   = R1[0].length;
    double sum = 0.0e0;
	
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        double d = (double)(R1[i][j] - R2[i][j]);
        sum += ( d*d );
      }
    }
	
    return sum;
  }

 
  // ===================================================================================
  /** @brief Sum of square difference.
   *  @param R1 pointer to the first  2D array.
   *  @param R1 pointer to the second 2D array.
   *  @param n number of elements.
   *  @return sum of the squares: s = sum(i=1,n) [ ( R1(i)-R2(i) )**2 ]
   *  <p>
   *  Sum the square of the difference of each element of the arrays.
   */
  // -----------------------------------------------------------------------------------
  public static double sumsq( double[][] R1, double[][] R2 ) {
    // -------------------------------------------------------------------------------
    int    n   = R1.length;
    int    m   = R1[0].length;
    double sum = 0.0e0;
	
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        double d = R1[i][j] - R2[i][j];
        sum += ( d*d );
      }
    }
	
    return sum;
  }








  // ===================================================================================
  /** @brief Weighted sum.
   *  @param W pointer to 2D array of wieghts.
   *  @param X pointer to 2D array of values.
   *  @return sum of the products: s = sum(i=1,n) W(i)*X(i)
   *  <p>
   *  Perform a weighted sum.
   */
  // -----------------------------------------------------------------------------------
  public static double wsum( short[][] W, short[][] X ) {
    // -------------------------------------------------------------------------------
    int    n   = X.length;
    int    m   = X[0].length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        sum += ( (double)W[i][j] * (double)X[i][j] );
      }
    }
    return sum;
  }

    
  // ===================================================================================
  /** @brief Weighted sum.
   *  @param W pointer to 2D array of wieghts.
   *  @param X pointer to 2D array of values.
   *  @return sum of the products: s = sum(i=1,n) W(i)*X(i)
   *  <p>
   *  Perform a weighted sum.
   */
  // -----------------------------------------------------------------------------------
  public static double wsum( int[][] W, int[][] X ) {
    // -------------------------------------------------------------------------------
    int    n   = X.length;
    int    m   = X[0].length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        sum += ( (double)W[i][j] * (double)X[i][j] );
      }
    }
    return sum;
  }

    
  // ===================================================================================
  /** @brief Weighted sum.
   *  @param W pointer to 2D array of wieghts.
   *  @param X pointer to 2D array of values.
   *  @return sum of the products: s = sum(i=1,n) W(i)*X(i)
   *  <p>
   *  Perform a weighted sum.
   */
  // -----------------------------------------------------------------------------------
  public static double wsum( long[][] W, long[][] X ) {
    // -------------------------------------------------------------------------------
    int    n   = X.length;
    int    m   = X[0].length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        sum += ( (double)W[i][j] * (double)X[i][j] );
      }
    }
    return sum;
  }

    
  // ===================================================================================
  /** @brief Weighted sum.
   *  @param W pointer to 2D array of wieghts.
   *  @param X pointer to 2D array of values.
   *  @return sum of the products: s = sum(i=1,n) W(i)*X(i)
   *  <p>
   *  Perform a weighted sum.
   */
  // -----------------------------------------------------------------------------------
  public static double wsum( float[][] W, float[][] X ) {
    // -------------------------------------------------------------------------------
    int    n   = X.length;
    int    m   = X[0].length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        sum += ( (double)W[i][j] * (double)X[i][j] );
      }
    }
    return sum;
  }

    
  // ===================================================================================
  /** @brief Weighted sum.
   *  @param W pointer to 2D array of wieghts.
   *  @param X pointer to 2D array of values.
   *  @return sum of the products: s = sum(i=1,n) W(i)*X(i)
   *  <p>
   *  Perform a weighted sum.
   */
  // -----------------------------------------------------------------------------------
  public static double wsum( double[][] W, double[][] X ) {
    // -------------------------------------------------------------------------------
    int    n   = X.length;
    int    m   = X[0].length;
    double sum = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      for ( int j=0; j<m; j++ ) {
        sum += ( W[i][j] * X[i][j] );
      }
    }
    return sum;
  }







  // ===================================================================================
  /** @brief Copy.
   *  @param src pointer to the source array.
   *  @return pointer to the destination array.
   *  <p>
   *  Clone the srce array and copy each element of the source array into
   *  the destination array.
   */
  // -----------------------------------------------------------------------------------
  public static int[] clone( int[] src ) {
    // -------------------------------------------------------------------------------
    int n = src.length;
    int[] dst = new int[n];
	
    for ( int i=0; i<n; i++ ) {
      dst[i] = src[i];
    }

    return dst;
  }

  // ===================================================================================
  /** @brief Copy.
   *  @param src pointer to the source array.
   *  @return pointer to the destination array.
   *  <p>
   *  Clone the srce array and copy each element of the source array into
   *  the destination array.
   */
  // -----------------------------------------------------------------------------------
  public static double[] clone( double[] src ) {
    // -------------------------------------------------------------------------------
    int n = src.length;
    double[] dst = new double[n];
	
    for ( int i=0; i<n; i++ ) {
      dst[i] = src[i];
    }

    return dst;
  }








  // =====================================================================================
  /** @brief Inner Product.
   *  @param c pointer to receiving       2x2 matrix.
   *  @param a pointer to left  hand side 2x2 matrix.
   *  @param b pointer to right hand side 2x2 matrix.
   */
  // -------------------------------------------------------------------------------------
  public static void dot_2x2( double[][] c, double[][] a, double[][] b ) {
    // -----------------------------------------------------------------------------------

    c[0][0] = a[0][0]*b[0][0] + a[0][1]*b[1][0];
    c[0][1] = a[0][0]*b[0][1] + a[0][1]*b[1][1];
    
    c[1][0] = a[1][0]*b[0][0] + a[1][1]*b[1][0];
    c[1][1] = a[1][0]*b[0][1] + a[1][1]*b[1][1];
  }

  
  // =====================================================================================
  /** @brief Inner Product.
   *  @param c pointer to receiving       3x3 matrix.
   *  @param a pointer to left  hand side 3x3 matrix.
   *  @param b pointer to right hand side 3x3 matrix.
   */
  // -------------------------------------------------------------------------------------
  public static void dot_3x3( double[][] c, double[][] a, double[][] b ) {
    // -----------------------------------------------------------------------------------

    c[0][0] = a[0][0]*b[0][0] + a[0][1]*b[1][0] + a[0][2]*b[2][0];
    c[0][1] = a[0][0]*b[0][1] + a[0][1]*b[1][1] + a[0][2]*b[2][1];
    c[0][2] = a[0][0]*b[0][2] + a[0][1]*b[1][2] + a[0][2]*b[2][2];

    c[1][0] = a[1][0]*b[0][0] + a[1][1]*b[1][0] + a[1][2]*b[2][0];
    c[1][1] = a[1][0]*b[0][1] + a[1][1]*b[1][1] + a[1][2]*b[2][1];
    c[1][2] = a[1][0]*b[0][2] + a[1][1]*b[1][2] + a[1][2]*b[2][2];

    c[2][0] = a[2][0]*b[0][0] + a[2][1]*b[1][0] + a[2][2]*b[2][0];
    c[2][1] = a[2][0]*b[0][1] + a[2][1]*b[1][1] + a[2][2]*b[2][1];
    c[2][2] = a[2][0]*b[0][2] + a[2][1]*b[1][2] + a[2][2]*b[2][2];

  }

  
  // =====================================================================================
  /** @brief Inner Product.
   *  @param c pointer to receiving       4x4 matrix.
   *  @param a pointer to left  hand side 4x4 matrix.
   *  @param b pointer to right hand side 4x4 matrix.
   */
  // -------------------------------------------------------------------------------------
  public static void dot_4x4( double[][] c, double[][] a, double[][] b ) {
    // -----------------------------------------------------------------------------------

    c[0][0] = a[0][0]*b[0][0] + a[0][1]*b[1][0] + a[0][2]*b[2][0] + a[0][3]*b[3][0];
    c[0][1] = a[0][0]*b[0][1] + a[0][1]*b[1][1] + a[0][2]*b[2][1] + a[0][3]*b[3][1];
    c[0][2] = a[0][0]*b[0][2] + a[0][1]*b[1][2] + a[0][2]*b[2][2] + a[0][3]*b[3][2];
    c[0][3] = a[0][0]*b[0][3] + a[0][1]*b[1][3] + a[0][2]*b[2][3] + a[0][3]*b[3][3];

    c[1][0] = a[1][0]*b[0][0] + a[1][1]*b[1][0] + a[1][2]*b[2][0] + a[1][3]*b[3][0];
    c[1][1] = a[1][0]*b[0][1] + a[1][1]*b[1][1] + a[1][2]*b[2][1] + a[1][3]*b[3][1];
    c[1][2] = a[1][0]*b[0][2] + a[1][1]*b[1][2] + a[1][2]*b[2][2] + a[1][3]*b[3][2];
    c[1][3] = a[1][0]*b[0][3] + a[1][1]*b[1][3] + a[1][2]*b[2][3] + a[1][3]*b[3][3];

    c[2][0] = a[2][0]*b[0][0] + a[2][1]*b[1][0] + a[2][2]*b[2][0] + a[2][3]*b[3][0];
    c[2][1] = a[2][0]*b[0][1] + a[2][1]*b[1][1] + a[2][2]*b[2][1] + a[2][3]*b[3][1];
    c[2][2] = a[2][0]*b[0][2] + a[2][1]*b[1][2] + a[2][2]*b[2][2] + a[2][3]*b[3][2];
    c[2][3] = a[2][0]*b[0][3] + a[2][1]*b[1][3] + a[2][2]*b[2][3] + a[2][3]*b[3][3];

    c[3][0] = a[3][0]*b[0][0] + a[3][1]*b[1][0] + a[3][2]*b[2][0] + a[3][3]*b[3][0];
    c[3][1] = a[3][0]*b[0][1] + a[3][1]*b[1][1] + a[3][2]*b[2][1] + a[3][3]*b[3][1];
    c[3][2] = a[3][0]*b[0][2] + a[3][1]*b[1][2] + a[3][2]*b[2][2] + a[3][3]*b[3][2];
    c[3][3] = a[3][0]*b[0][3] + a[3][1]*b[1][3] + a[3][2]*b[2][3] + a[3][3]*b[3][3];

  }








  // =====================================================================================
  /** @brief Determinant.
   *  @param A reference to an array containing a 2x2 matrix.
   *  @return the determinant of this matrix.
   */
  // -------------------------------------------------------------------------------------
  public static double det_2x2( double[][] A ) {
    // -----------------------------------------------------------------------------------
    return A[0][0]*A[1][1] - A[0][1]*A[1][0];
  }

    
  // =====================================================================================
  /** @brief Determinant.
   *  @param A reference to an array containing a 3x3 matrix.
   *  @return the determinant of this matrix.
   */
  // -------------------------------------------------------------------------------------
  public static double det_3x3( double[][] A ) {
    // -----------------------------------------------------------------------------------
    return
        (A[0][1]*A[1][2] - A[0][2]*A[1][1])*A[2][0] +
        (A[0][2]*A[1][0] - A[0][0]*A[1][2])*A[2][1] +
        (A[0][0]*A[1][1] - A[0][1]*A[1][0])*A[2][2];
  }

  
  // =====================================================================================
  /** @brief Determinant.
   *  @param A reference to an array containing a 3x3 matrix.
   *  @return the determinant of this matrix.
   */
  // -------------------------------------------------------------------------------------
  public static double det_4x4( double[][] A ) {
    // -----------------------------------------------------------------------------------
    double a1 = ((A[0][3]*A[1][2] - A[0][2]*A[1][3])*A[2][1] +
                 (A[0][1]*A[1][3] - A[0][3]*A[1][1])*A[2][2] +
                 (A[0][2]*A[1][1] - A[0][1]*A[1][2])*A[2][3])*A[3][0];

    double a2 = ((A[0][2]*A[1][3] - A[0][3]*A[1][2])*A[2][0] +
                 (A[0][3]*A[1][0] - A[0][0]*A[1][3])*A[2][2] +
                 (A[0][0]*A[1][2] - A[0][2]*A[1][0])*A[2][3])*A[3][1];

    double a3 = ((A[0][3]*A[1][1] - A[0][1]*A[1][3])*A[2][0] +
                 (A[0][0]*A[1][3] - A[0][3]*A[1][0])*A[2][1] +
                 (A[0][1]*A[1][0] - A[0][0]*A[1][1])*A[2][3])*A[3][2];

    double a4 = ((A[0][1]*A[1][2] - A[0][2]*A[1][1])*A[2][0] +
                 (A[0][2]*A[1][0] - A[0][0]*A[1][2])*A[2][1] +
                 (A[0][0]*A[1][1] - A[0][1]*A[1][0])*A[2][2])*A[3][3];

    return a1 + a2 + a3 + a4;
  }








  // ===================================================================================
  /** @brief Invert.
   *  @param A receiving 2x2 matrix.
   *  @param M 2x2 matrix
   *  @return the determinant of the source matrix.
   *  <p>
   *  Compute the inverse of matrix M and store in matrix A.
   */
  // -----------------------------------------------------------------------------------
  public static double inv_2x2( double[][] A, double[][] M ) {
    // -------------------------------------------------------------------------------
    double D = det_2x2(M);
	
    if ( Math2.isZero( D ) ) { return 0.0e0; }

    A[0][0] =  M[1][1] / D;
    A[0][1] = -M[0][1] / D;

    A[1][0] = -M[1][0] / D;
    A[1][1] =  M[0][0] / D;

    return D;
  }
     

  // ===================================================================================
  /** @brief Invert.
   *  @param A receiving 2x2 matrix.
   *  @param M 2x2 matrix
   *  @return the determinant of the source matrix.
   *  <p>
   *  Compute the inverse of matrix M and store in matrix A.
   */
  // -----------------------------------------------------------------------------------
  public static double inv_3x3( double[][] A, double[][] M ) {
    // -------------------------------------------------------------------------------
    double D = det_3x3(M);
	
    if ( Math2.isZero( D ) ) { return 0.0e0; }

    A[0][0] = ( M[1][1]*M[2][2] - M[1][2]*M[2][1] ) / D;
    A[0][1] = ( M[0][2]*M[2][1] - M[0][1]*M[2][2] ) / D;
    A[0][2] = ( M[0][1]*M[1][2] - M[0][2]*M[1][1] ) / D;

    A[1][0] = ( M[1][2]*M[2][0] - M[1][0]*M[2][2] ) / D;
    A[1][1] = ( M[0][0]*M[2][2] - M[0][2]*M[2][0] ) / D;
    A[1][2] = ( M[0][2]*M[1][0] - M[0][0]*M[1][2] ) / D;

    A[2][0] = ( M[1][0]*M[2][1] - M[1][1]*M[2][0] ) / D;
    A[2][1] = ( M[0][1]*M[2][0] - M[0][0]*M[2][1] ) / D;
    A[2][2] = ( M[0][0]*M[1][1] - M[0][1]*M[1][0] ) / D;

    return D;
  }
     

  // ===================================================================================
  /** @brief Invert.
   *  @param A receiving 2x2 matrix.
   *  @param M 2x2 matrix
   *  @return the determinant of the source matrix.
   *  <p>
   *  Compute the inverse of matrix M and store in matrix A.
   */
  // -----------------------------------------------------------------------------------
  public static double inv_4x4( double[][] A, double[][] M ) {
    // -------------------------------------------------------------------------------
    double D = det_4x4(M);
	
    if ( Math2.isZero( D ) ) { return 0.0e0; }

    A[0][0] = ( - (M[1][3]*M[2][2] - M[1][2]*M[2][3])*M[3][1]
                + (M[1][3]*M[2][1] - M[1][1]*M[2][3])*M[3][2]
                - (M[1][2]*M[2][1] - M[1][1]*M[2][2])*M[3][3] ) / D;

    A[0][1] = (   (M[0][3]*M[2][2] - M[0][2]*M[2][3])*M[3][1]
                  - (M[0][3]*M[2][1] - M[0][1]*M[2][3])*M[3][2]
                  + (M[0][2]*M[2][1] - M[0][1]*M[2][2])*M[3][3] ) / D;

    A[0][2] = ( - (M[0][3]*M[1][2] - M[0][2]*M[1][3])*M[3][1]
                + (M[0][3]*M[1][1] - M[0][1]*M[1][3])*M[3][2]
                - (M[0][2]*M[1][1] - M[0][1]*M[1][2])*M[3][3] ) / D;

    A[0][3] = (   (M[0][3]*M[1][2] - M[0][2]*M[1][3])*M[2][1]
                  - (M[0][3]*M[1][1] - M[0][1]*M[1][3])*M[2][2]
                  + (M[0][2]*M[1][1] - M[0][1]*M[1][2])*M[2][3] ) / D;

 
    // ---------------------------------


    A[1][0] = (   (M[1][3]*M[2][2] - M[1][2]*M[2][3])*M[3][0]
                  - (M[1][3]*M[2][0] - M[1][0]*M[2][3])*M[3][2]
                  + (M[1][2]*M[2][0] - M[1][0]*M[2][2])*M[3][3] ) / D;

    A[1][1] = ( - (M[0][3]*M[2][2] - M[0][2]*M[2][3])*M[3][0]
                + (M[0][3]*M[2][0] - M[0][0]*M[2][3])*M[3][2]
                - (M[0][2]*M[2][0] - M[0][0]*M[2][2])*M[3][3] ) / D;

    A[1][2] = (   (M[0][3]*M[1][2] - M[0][2]*M[1][3])*M[3][0]
                  - (M[0][3]*M[1][0] - M[0][0]*M[1][3])*M[3][2]
                  + (M[0][2]*M[1][0] - M[0][0]*M[1][2])*M[3][3] ) / D;

    A[1][3] = ( - (M[0][3]*M[1][2] - M[0][2]*M[1][3])*M[2][0]
                + (M[0][3]*M[1][0] - M[0][0]*M[1][3])*M[2][2]
                - (M[0][2]*M[1][0] - M[0][0]*M[1][2])*M[2][3] ) / D;

    // ---------------------------------

    A[2][0] = ( - (M[1][3]*M[2][1] - M[1][1]*M[2][3])*M[3][0]
                + (M[1][3]*M[2][0] - M[1][0]*M[2][3])*M[3][1]
                - (M[1][1]*M[2][0] - M[1][0]*M[2][1])*M[3][3] ) / D;

    A[2][1] = (   (M[0][3]*M[2][1] - M[0][1]*M[2][3])*M[3][0]
                  - (M[0][3]*M[2][0] - M[0][0]*M[2][3])*M[3][1]
                  + (M[0][1]*M[2][0] - M[0][0]*M[2][1])*M[3][3] ) / D;

    A[2][2] = ( - (M[0][3]*M[1][1] - M[0][1]*M[1][3])*M[3][0]
                + (M[0][3]*M[1][0] - M[0][0]*M[1][3])*M[3][1]
                - (M[0][1]*M[1][0] - M[0][0]*M[1][1])*M[3][3] ) / D;

    A[2][3] = (   (M[0][3]*M[1][1] - M[0][1]*M[1][3])*M[2][0]
                  - (M[0][3]*M[1][0] - M[0][0]*M[1][3])*M[2][1]
                  + (M[0][1]*M[1][0] - M[0][0]*M[1][1])*M[2][3] ) / D;

    // ---------------------------------


    A[3][0] = (   (M[1][2]*M[2][1] - M[1][1]*M[2][2])*M[3][0]
                  - (M[1][2]*M[2][0] - M[1][0]*M[2][2])*M[3][1]
                  + (M[1][1]*M[2][0] - M[1][0]*M[2][1])*M[3][2] ) / D;

    A[3][1] = ( - (M[0][2]*M[2][1] - M[0][1]*M[2][2])*M[3][0]
                + (M[0][2]*M[2][0] - M[0][0]*M[2][2])*M[3][1]
                - (M[0][1]*M[2][0] - M[0][0]*M[2][1])*M[3][2] ) / D;

    A[3][2] = (   (M[0][2]*M[1][1] - M[0][1]*M[1][2])*M[3][0]
                  - (M[0][2]*M[1][0] - M[0][0]*M[1][2])*M[3][1]
                  + (M[0][1]*M[1][0] - M[0][0]*M[1][1])*M[3][2] ) / D;

    A[3][3] = ( - (M[0][2]*M[1][1] - M[0][1]*M[1][2])*M[2][0]
                + (M[0][2]*M[1][0] - M[0][0]*M[1][2])*M[2][1]
                - (M[0][1]*M[1][0] - M[0][0]*M[1][1])*M[2][2] ) / D;

    return D;
  }
     

  // ===================================================================================
  public static boolean eigen_2x2( double[] eval, double[] ev1, double[] ev2,
                                   double[][] mat ) {
    // ---------------------------------------------------------------------------------
    double a = mat[0][0];
    double b = mat[0][1];
    double c = mat[1][0];
    double d = mat[1][1];

    double T = a+d;
    double D = (a*d) - (b*c);
    double R = T*T - 4.0e0*D;

    if ( 0.0 > R ) { return false; }

    double K = Math.sqrt( R );

    double L0 = (T + K)/2.0e0;
    double L1 = (T - K)/2.0e0;

    double e  = L0 - d;
    double f  = L1 - d;
    double g  = e*f;

    double v00 = 0.0e0;
    double v01 = 0.0e0;
    double v10 = 0.0e0;
    double v11 = 0.0e0;
    
    if ( ( 0.0e0 < g ) || ( 0.0e0 > g ) ) {
      v00 = 1.0e0; v01 = c/e;
      v10 = 1.0e0; v11 = c/f;
    } else {
      if ( ( 0.0e0 < b ) || ( 0.0e0 > b ) ) {
        v00 = 1.0e0; v01 = (L0-a)/b;
        v10 = 1.0e0; v11 = (L1-a)/b;
      } else {
        v00 = 1.0e0; v01 = 0.0e0;
        v10 = 0.0e0; v11 = 1.0e0;
      }
    }

    if ( L0 > L1 ) {
      eval[0] = L0;    ev1[0] = v00; ev1[1] = v01;
      eval[1] = L1;    ev2[0] = v10; ev2[1] = v11;
    } else {
      eval[0] = L1;    ev1[0] = v10; ev1[1] = v11;
      eval[1] = L0;    ev2[0] = v00; ev2[1] = v01;
    }

    return true;
  }


  // ===================================================================================
  public static boolean eigen_3x3( double[] eval,
                                   double[] ev1, double[] ev2, double[] ev3,
                                   double[][] mat ) {
    // ---------------------------------------------------------------------------------
    EigenDecomposition eig
        = new EigenDecomposition( new Array2DRowRealMatrix( mat, true ) );

    double D = eig.getDeterminant();

    if ( Math2.isZero( D ) ) { return false; }

    double[][] V = eig.getV().getData();

    for ( int r=0; r<3; r++ ) {
      eval[r] = eig.getRealEigenvalue(r);
      ev1[r]  = V[r][0];
      ev2[r]  = V[r][1];
      ev3[r]  = V[r][2];
    }
    
    return true;
  }


  // ===================================================================================
  public static boolean eigen_4x4( double[] eval,
                                   double[] ev1, double[] ev2, double[] ev3, double[] ev4,
                                   double[][] mat ) {
    // ---------------------------------------------------------------------------------
    EigenDecomposition eig
        = new EigenDecomposition( new Array2DRowRealMatrix( mat, true ) );

    double D = eig.getDeterminant();

    if ( Math2.isZero( D ) ) { return false; }

    double[][] V = eig.getV().getData();

    for ( int r=0; r<4; r++ ) {
      eval[r] = eig.getRealEigenvalue(r);
      ev1[r]  = V[r][0];
      ev2[r]  = V[r][1];
      ev3[r]  = V[r][2];
      ev4[r]  = V[r][3];
    }
    
    return true;
  }



  
  // ===================================================================================
  /** @brief Minimum.
   *  @param data array of data.
   *  @param return then minimum of each Column.
   *  <p>
   *  The minimum is computed for each column down the rows. The output has the
   *  dimension of the number of columns in data.
   */
  // -----------------------------------------------------------------------------------
  protected static double[] MinCol( double[][] data ) {
    // ---------------------------------------------------------------------------------
    int nr = data.length;
    int nc = data[0].length;
    double[] V = new double[nc];

    for ( int c=0; c<nc; c++ ) {
      double mn = data[0][c];
      for ( int r=0; r<nr; r++ ) {
        if ( mn > data[r][c] ) mn = data[r][c];
      }
      V[c] = mn;
    }

    return V;
  }


  // ===================================================================================
  /** @brief Minimum.
   *  @param data array of data.
   *  @param return the minimum of each Row.
   *  <p>
   *  The minimum is computed for each row across the columns. The output has the
   *  dimension of the number of rows in data.
   */
  // -----------------------------------------------------------------------------------
  protected static double[] MinRow( double[][] data ) {
    // ---------------------------------------------------------------------------------
    int nr = data.length;
    int nc = data[0].length;
    double[] V = new double[nr];

    for ( int r=0; r<nr; r++ ) {
      double mn = data[r][0];
      for ( int c=0; c<nc; c++ ) {
        if ( mn > data[r][c] ) mn = data[r][c];
      }
      V[r] = mn;
    }

    return V;
  }


  // ===================================================================================
  /** @brief Minimum.
   *  @param data array of data.
   *  @param dim dimension to find the minimum.
   *  @param return the minimum value of the columns or rows.
   *  <p>
   *  If dim=0 then the minimum is computed for each column down the rows.
   */
  // -----------------------------------------------------------------------------------
  protected static double[] Min( double[][] data, int dim ) {
    // ---------------------------------------------------------------------------------
    if ( 0==dim ) {
      return MinCol(data);
    }
    return MinRow(data);
  }




  // ===================================================================================
  /** @brief Maximum.
   *  @param data array of data.
   *  @param return then maximum of each Column.
   *  <p>
   *  The maximum is computed for each column down the rows. The output has the
   *  dimension of the number of columns in data.
   */
  // -----------------------------------------------------------------------------------
  protected static double[] MaxCol( double[][] data ) {
    // ---------------------------------------------------------------------------------
    int nr = data.length;
    int nc = data[0].length;
    double[] V = new double[nc];

    for ( int c=0; c<nc; c++ ) {
      double mx = data[0][c];
      for ( int r=0; r<nr; r++ ) {
        if ( mx < data[r][c] ) mx = data[r][c];
      }
      V[c] = mx;
    }

    return V;
  }


  // ===================================================================================
  /** @brief Maximum.
   *  @param data array of data.
   *  @param return the maximum of each Row.
   *  <p>
   *  The maximum is computed for each row across the columns. The output has the
   *  dimension of the number of rows in data.
   */
  // -----------------------------------------------------------------------------------
  protected static double[] MaxRow( double[][] data ) {
    // ---------------------------------------------------------------------------------
    int nr = data.length;
    int nc = data[0].length;
    double[] V = new double[nr];

    for ( int r=0; r<nr; r++ ) {
      double mx = data[r][0];
      for ( int c=0; c<nc; c++ ) {
        if ( mx < data[r][c] ) mx = data[r][c];
      }
      V[r] = mx;
    }

    return V;
  }


  // ===================================================================================
  /** @brief Maximum.
   *  @param data array of data.
   *  @param dim dimension to find the maximum.
   *  @param return the maximum value of the columns or rows.
   *  <p>
   *  If dim=0 then the maximum is computed for each column down the rows.
   */
  // -----------------------------------------------------------------------------------
  protected static double[] Max( double[][] data, int dim ) {
    // ---------------------------------------------------------------------------------
    if ( 0==dim ) {
      return MaxCol(data);
    }
    return MaxRow(data);
  }


  // ===================================================================================
  /** @brief Mean.
   *  @param data array of data.
   *  @param dim dimension to find the mean.
   *  @param return covariance.
   *  <p>
   *  If dim=0 then the mean is computed down the rows.
   */
  // -----------------------------------------------------------------------------------
  public static double[] mean( double[][] data, int dim ) {
    // ---------------------------------------------------------------------------------
    int nr = data.length;
    int nc = data[0].length;

    double[]   mu  = null;

    if ( dim == 0 ) {
      mu  = new double[nc];

      for ( int j=0; j<nc; j++ ) {
        mu[j] = 0.0e0;
      }
      
      for ( int i=0; i<nr; i++ ) {
        for ( int j=0; j<nc; j++ ) {
          mu[j] += data[i][j];
        }
      }

      for ( int j=0; j<nc; j++ ) {
        mu[j] /= (double)nr;
      }

    } else {
      mu  = new double[nr];
    
      for ( int i=0; i<nr; i++ ) {
        double s = 0.0e0;
        for ( int j=0; j<nc; j++ ) {
          s += data[i][j];
        }
        mu[i] = s / (double)nc;
      }

    }

    return mu;
  }



  // ===================================================================================
  /** @brief Covariance.
   *  @param data array of data.
   *  @param return covariance.
   *  <p>
   *  It is assumed that the number of samples is greater than the number of variables.
   */
  // -----------------------------------------------------------------------------------
  public static double[][] covariance( double[][] data ) {
    // ---------------------------------------------------------------------------------
    int nr = data.length;
    int nc = data[0].length;

    double[][] cov = null;
    double[]   mu  = null;

    if ( nr > nc ) {
      cov = new double[nc][nc];
      mu  = new double[nc];

      for ( int j=0; j<nc; j++ ) {
        mu[j] = 0.0e0;
      }
      
      for ( int i=0; i<nr; i++ ) {
        for ( int j=0; j<nc; j++ ) {
          mu[j] += data[i][j];
        }
      }

      for ( int j=0; j<nc; j++ ) {
        mu[j] /= (double)nr;
      }

      for ( int i=0; i<nc; i++ ) {
        for ( int j=i; j<nc; j++ ) {
          double s = 0.0e0;
          for ( int k=0; k<nr; k++ ) {
            double di = data[k][i] - mu[i];
            double dj = data[k][j] - mu[j];
            s += ( di*dj );
          }
          cov[i][j] = s / (double)(nr-1);
          cov[j][i] = s / (double)(nr-1);
        }
      }

    } else {
      cov = new double[nr][nr];
      mu  = new double[nr];
    
      for ( int i=0; i<nr; i++ ) {
        double s = 0.0e0;
        for ( int j=0; j<nc; j++ ) {
          s += data[i][j];
        }
        mu[i] = s / (double)nc;
      }

      for ( int i=0; i<nr; i++ ) {
        for ( int j=i; j<nr; j++ ) {
          double s = 0.0e0;
          for ( int k=0; k<nc; k++ ) {
            double di = data[i][k] - mu[i];
            double dj = data[j][k] - mu[j];
            s += ( di*dj );
          }
          cov[i][j] = s / (double)(nc-1);
          cov[j][i] = s / (double)(nc-1);
        }
      }
      
    }

    return cov;
  }




  // ===================================================================================
  /** @brief B-Spline.
   *  @param A return coefficients.
   *  @param X array of independent points.
   *  @param Y array of dependent points.
   *  <p>
   *  Fit a quadratic to the data points.
   *  If there are three data points then compute the fully determined problem directly.
   */
  // -----------------------------------------------------------------------------------
  public static void BSpline( double[] A, double[] X, double[] Y ) {
    // ---------------------------------------------------------------------------------
    int n = X.length;

    // ----- under determined ----------------------------------------------------------
    if ( n < 3 ) {
      System.err.println( "Math2.QSpline: data is underdetermined for quadratic" );
      return;
    }

    // ----- over determined -----------------------------------------------------------
    if ( n > 3 ) {
      System.err.println( "Math2.QSpline: overdetermined is not yet supported" );
      return;
    }

    double x1  = X[0];
    double x2  = X[1];
    double x3  = X[2];

    double x1s = x1*x1;
    double x2s = x2*x2;
    double x3s = x3*x3;

    double y1  = Y[0];
    double y2  = Y[1];
    double y3  = Y[2];
    
    double D =
        (x2 - x3)*x1s +
        (x3 - x1)*x2s +
        (x1 - x2)*x3s;

    if ( Math2.isZero( D ) ) {
      System.err.println( "Math2.QSpline: data is singular" );
      return;
    }

    A[0] = ( (x2 - x3)*y1 +
             (x3 - x1)*y2 +
             (x1 - x2)*y3 ) / D;

    A[1] = ( (x3s - x2s)*y1 +
             (x1s - x3s)*y2 +
             (x2s - x1s)*y3 ) / D;

    A[2] = ( (x2s*x3 - x2*x3s)*y1 +
             (x1*x3s - x1s*x3)*y2 +
             (x1s*x2 - x1*x2s)*y3 ) / D;
  }

}

// =======================================================================================
// **                                     M A T H 2                                     **
// ======================================================================== END FILE =====
