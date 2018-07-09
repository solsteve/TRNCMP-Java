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

    public static boolean isEqual(float a, float b) {
	if (a < b) { return false; }
	if (a > b) { return false; }
	return true;
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
     *  Copy each element of the source array into the destination array.
     *
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
     *
     *  Copy each element of the source array into the destination array.
     *
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
     *
     *  Copy each element of the source array into the destination array.
     *
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
     *
     *  Copy each element of the source array into the destination array.
     *
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
     *
     *  Copy each element of the source array into the destination array.
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
     *  Copy each element of the source array into the destination array.
     *
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
     *
     *  Copy each element of the source array into the destination array.
     *
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
     *
     *  Copy each element of the source array into the destination array.
     *
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
     *
     *  Copy each element of the source array into the destination array.
     *
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
     *
     *  Copy each element of the source array into the destination array.
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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









    
}

// =======================================================================================
// **                                     M A T H 2                                     **
// ======================================================================== END FILE =====
