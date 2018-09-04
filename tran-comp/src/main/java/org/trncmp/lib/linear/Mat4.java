// ====================================================================== BEGIN FILE =====
// **                                      M A T 4                                      **
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
// @file Mat4.java
// <p>
// Provides 4x4 vector.
//
// @author Stephen W. Soliday
// @date 2017-09-10
//
// =======================================================================================

package org.trncmp.lib.linear;

import org.trncmp.lib.Math2;

// =======================================================================================
public class Mat4 {
    // -----------------------------------------------------------------------------------

    public double[][] A = new double[4][4];

 
    // ===================================================================================
    /** @brief Constructor.
     *
     *  Create an empty matrix.
     */
    // -----------------------------------------------------------------------------------
    public Mat4( ) {
	// -------------------------------------------------------------------------------
	this.set();
    }

    
    // ===================================================================================
    /** @brief Constructor.
     *  @param q pointer to a source array.
     *
     *  Create a copy of the source array.
     */
    // -----------------------------------------------------------------------------------
    public Mat4( double[] q ) {
	// -------------------------------------------------------------------------------
	this.load( q );
    }

    
    // ===================================================================================
    /** @brief Constructor.
     *  @param q pointer to a source array.
     *
     *  Create a copy of the source array.
     */
    // -----------------------------------------------------------------------------------
    public Mat4( double[][] q ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = q[r][c];
	    }
	}
    }

    
    // ===================================================================================
    /** @brief Constructor.
     *  @param src reference to a 4x4 source matrix.
     *
     *  Create a copy of the source matrix.
     */
    // -----------------------------------------------------------------------------------
    public Mat4( Mat4 src ) {
	// -------------------------------------------------------------------------------
	this.copy( src );
    }

    
    // ===================================================================================
    /** @brief Set.
     *
     *  Set each element of this matrix to the value of the argument val.
     */
    // -----------------------------------------------------------------------------------
    public void set( ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = 0.0e0;
	    }
	}
    }

    
    // ===================================================================================
    /** @brief Set.
     *  @param val value to set each element ( default: 0 ).
     *
     *  Set each element of this matrix to the value of the argument val.
     */
    // -----------------------------------------------------------------------------------
    public void set( double val ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = val;
	    }
	}
    }

    
    // ===================================================================================
    /** @brief Copy.
     *  @param src reference to a 4x4 source matrix.
     *
     *  Make a deep copy of the source matrix.
     */
    // -----------------------------------------------------------------------------------
    public void copy( Mat4 src ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = src.A[r][c];
	    }
	}
    }








    // ===================================================================================
    /** @brief Matrix-scalar Addition.
     *  @param scl scalar input.
     * 
     *  Add scl to each element of this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void add( double scl ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] += scl;
	    }
	}
    }


    // ===================================================================================
    /** @brief Matrix-scalar Subtraction.
     *  @param scl scalar input.
     * 
     *  Subtract scl from each element of this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void sub( double scl ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] -= scl;
	    }
	}
    }


    // ===================================================================================
    /** @brief Matrix-scalar Multiplication.
     *  @param scl scalar input.
     * 
     *  Multiply each element of this matrix by scl.
     */
    // -----------------------------------------------------------------------------------
    public void pmul( double scl ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] *= scl;
	    }
	}
    }


    // ===================================================================================
    /** @brief Matrix-scalar Division.
     *  @param scl scalar input.
     * 
     *  Divide each element of this matrix by scl.
     */
    // -----------------------------------------------------------------------------------
    public void pdiv( double scl ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] /= scl;
	    }
	}
    }








    // ===================================================================================
    /** @brief Element Wise Inplace Matrix Addition.
     *  @param m1 reference to the right source matrix.
     * 
     *  Add each element of m1 to this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void add( Mat4 m1 ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] += m1.A[r][c];
	    }
	}
    }


    // ===================================================================================
    /** @brief Element Wise Inplace Matrix Subtraction.
     *  @param m1   reference to the right source matrix.
     * 
     *  Subtract each element of m1 from this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void sub( Mat4 m1 ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] -= m1.A[r][c];
	    }
	}
    }


    // ===================================================================================
    /** @brief Element Wise Inplace Matrix Multiplication.
     *  @param m1 reference to the right source matrix.
     * 
     *  Multiply each element of this matrix by m1.
     */
    // -----------------------------------------------------------------------------------
    public void pmul( Mat4 m1 ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] *= m1.A[r][c];
	    }
	}
    }


    // ===================================================================================
    /** @brief Element Wise Inplace Matrix Division.
     *  @param m1 reference to the right source matrix.
     * 
     *  Divide each element of this matrix by m1.
     */
    // -----------------------------------------------------------------------------------
    public void pdiv( Mat4 m1 ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] /= m1.A[r][c];
	    }
	}
    }








    // ===================================================================================
    /** @brief Matrix-scalar Addition.
     *  @param m1  reference to the left source matrix.
     *  @param scl scalar input.
     * 
     *  Add scl to each element of m1 and store in this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void add( Mat4 m1, double scl ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = m1.A[r][c] + scl;
	    }
	}
    }


    // ===================================================================================
    /** @brief Matrix-scalar Subtraction.
     *  @param m1  reference to the left source matrix.
     *  @param scl scalar input.
     * 
     *  Subtract scl from each element of m1 and store in this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void sub( Mat4 m1, double scl ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = m1.A[r][c] - scl;
	    }
	}
    }


    // ===================================================================================
    /** @brief Matrix-scalar Multiplication.
     *  @param m1  reference to the left source matrix.
     *  @param scl scalar input.
     * 
     *  Multiply each element of m1 by scl and store in this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void pmul( Mat4 m1, double scl ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = m1.A[r][c] * scl;
	    }
	}
    }


    // ===================================================================================
    /** @brief Matrix-scalar Division.
     *  @param m1  reference to the left source matrix.
     *  @param scl scalar input.
     * 
     *  Divide each element of m1 by scl and store in this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void pdiv( Mat4 m1, double scl ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = m1.A[r][c] / scl;
	    }
	}
    }








    // ===================================================================================
    /** @brief Element Wise Matrix Addition.
     *  @param m1   reference to the left  source matrix.
     *  @param m2   reference to the right source matrix.
     * 
     *  Add each element of m1 to m2 and store in this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void add( Mat4 m1, Mat4 m2 ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = m1.A[r][c] + m2.A[r][c];
	    }
	}
    }


    // ===================================================================================
    /** @brief Element Wise Matrix Subtraction.
     *  @param m1   reference to the left  source matrix.
     *  @param m2   reference to the right source matrix.
     * 
     *  Subtract each element of m2 from m1 and store in this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void sub( Mat4 m1, Mat4 m2 ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = m1.A[r][c] - m2.A[r][c];
	    }
	}
    }


    // ===================================================================================
    /** @brief Element Wise Matrix Multiplication.
     *  @param m1   reference to the left  source matrix.
     *  @param m2   reference to the right source matrix.
     * 
     *  Multipliy each element of m1 by m2 and store in this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void pmul( Mat4 m1, Mat4 m2 ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = m1.A[r][c] * m2.A[r][c];
	    }
	}
    }


    // ===================================================================================
    /** @brief Element Wise Matrix Division.
     *  @param m1   reference to the left  source matrix.
     *  @param m2   reference to the right source matrix.
     * 
     *  Divide each element of m1 by m2 and store in this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void pdiv( Mat4 m1, Mat4 m2 ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = m1.A[r][c] / m2.A[r][c];
	    }
	}
    }








    // ===================================================================================
    /** @brief Load.
     *  @param src pointer to a source array.
     *  @return pointer to next unused array element.
     */
    // -----------------------------------------------------------------------------------
    public int load( double[] src ) {
	// -------------------------------------------------------------------------------
	int idx = 0;
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = src[idx];
		idx += 1;
	    }
	}
	return idx;
    }

    
    // ===================================================================================
    /** @brief Load.
     *  @param src pointer to a source array.
     *  @return pointer to next unused array element.
     */
    // -----------------------------------------------------------------------------------
    public int load( double[] src, int offset ) {
	// -------------------------------------------------------------------------------
	int idx = offset;
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = src[idx];
		idx += 1;
	    }
	}
	return idx;
    }

    
    // ===================================================================================
    /** @brief Store.
     *  @param dst pointer to a destination array.
     *  @return pointer to next unused array element.
     */
    // -----------------------------------------------------------------------------------
    public int store( double[] dst ) {
	// -------------------------------------------------------------------------------
	int idx = 0;
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		dst[idx] = this.A[r][c];
		idx += 1;
	    }
	}
	return idx;
    }

    
    // ===================================================================================
    /** @brief Store.
     *  @param dst pointer to a destination array.
     *  @return pointer to next unused array element.
     */
    // -----------------------------------------------------------------------------------
    public int store( double[] dst, int offset ) {
	// -------------------------------------------------------------------------------
	int idx = offset;
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		dst[idx] = this.A[r][c];
		idx += 1;
	    }
	}
	return idx;
    }

    // ===================================================================================
    /** @brief Swap.
     *  @param src reference to the second 4x4 source matrix.
     *
     *  Swap each element of this matrix with those in the second matrix.
     */
    // -----------------------------------------------------------------------------------
    public void swap( Mat4 src ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		double temp  = this.A[r][c];
		this.A[r][c] = src.A[r][c];
		src.A[r][c]  = temp;
	    }
	}
    }

    
    // ===================================================================================
    /** @brief Determinant.
     *  @return the determinant of this matrix.
     */
    // -----------------------------------------------------------------------------------
    public double det( ) {
	// -------------------------------------------------------------------------------
      return Math2.det_4x4( A );
    }









    // =======================================================================================
    /** @brief Invert.
     *  @param src matrix.
     *  @return the determinant of the source matrix.
     *
     *  Set this matrix to the inverse of the source matrix.
     */
    // ---------------------------------------------------------------------------------------
    public double invert( Mat4 M ) {
	// -------------------------------------------------------------------------------------
      return Math2.inv_4x4( this.A, M.A );
    }









    
    // ===================================================================================
    /** @brief Sum Square Difference.
     *  @return the sum of the squares of the elements.
     *
     *  Compute the sum of the squares of the elements   ret = sum ( sum ( mat[i][j]**2 ) )
     */
    // -----------------------------------------------------------------------------------
    public double sumsq( ) {
	// -------------------------------------------------------------------------------
	double sum = 0.0e0;
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		double d = this.A[r][c];
		sum += (d*d);
	    }
	}
	return sum;
    }

    
    // ===================================================================================
    /** @brief Sum Square Difference.
     *  @param rhs reference to another vector.
     *  @return the sum of the squares of the differences.
     *
     *  Compute the sum of the squares of the differences
     *                           ret = sum ( sum ( a[i][j] - b[i][j] )**2 )
     */
    // -----------------------------------------------------------------------------------
    public double sumsq( Mat4 rhs ) {
	// -------------------------------------------------------------------------------
	double sum = 0.0e0;
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		double d = this.A[r][c] - rhs.A[r][c];
		sum += (d*d);
	    }
	}
	return sum;
    }


    
    // ===================================================================================
    /** @brief Identity.
     *
     *  Set the diagonal to 1 and all other elements to 0.
     */
    // -----------------------------------------------------------------------------------
    public void ident( ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = (r==c) ? (1.0e0) : (0.0e0);
	    }
	}
    }

    // ===================================================================================
    /** @brief Transpose.
     *
     *  Transpose this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void transpose( ) {
	// -------------------------------------------------------------------------------
	for ( int r=1; r<4; r++ ) {
	    for ( int c=0; c<r; c++ ) {
		double temp = this.A[r][c];
		this.A[r][c] = this.A[c][r];
		this.A[c][r] = temp;
	    }
	}
    }


    // ===================================================================================
    /** @brief Transpose.
     *  @param src reference to a source matrix.
     *
     *  Make this matrix the transpose of the source matrix.
     */
    // -----------------------------------------------------------------------------------
    public void transpose( Mat4 src ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		this.A[r][c] = src.A[c][r];
	    }
	}
    }

    
    // ===================================================================================
    /** @brief Convert Vector to String.
     *  @param fmt format specifier of each element.
     *  @param dlm delimeter separating each element.
     */
    // -----------------------------------------------------------------------------------
    public String toString( String fmt, String rdlm, String cdlm ) {
	// -------------------------------------------------------------------------------
	StringBuffer buffer = new StringBuffer();

	for ( int r=0; r<4; r++ ) {
	    if ( 0 < r ) {
		buffer.append( rdlm );
	    }
	    for ( int c=0; c<4; c++ ) {
		if ( 0 < c ) {
		    buffer.append( cdlm );
		}
		buffer.append( String.format( fmt, this.A[r][c] ) );
	    }
	}

	return buffer.toString();
    }

    // ===================================================================================
    /** @brief Convert Vector to String.
     *  @param fmt format specifier of each element.
     */
    // -----------------------------------------------------------------------------------
    public String toString( String fmt ) {
	// -------------------------------------------------------------------------------
	return this.toString( fmt, "\n", " " );
    }

    
    // ===================================================================================
    /** @brief Convert Vector to String.
     */
    // -----------------------------------------------------------------------------------
    public String toString( ) {
	// -------------------------------------------------------------------------------
	return this.toString( "%g", "\n", " " );
    }



    
    // ===================================================================================
    /** @brief Display vector.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    public String octave_toString( String fmt ) {
	// -------------------------------------------------------------------------------
	return "[ "+ this.toString( fmt, ";", "," ) + " ];";
    }


    // ===================================================================================
    /** @brief Display vector.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    public String octave_toString( ) {
	// -------------------------------------------------------------------------------
	return this.octave_toString( "%g" );
    }



    // ===================================================================================
    /** @brief Display vector.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Sage Math.
     */
    // -----------------------------------------------------------------------------------
    public String sage_toString( String fmt ) {
	// -------------------------------------------------------------------------------
	return "Matrix(SR,[[" + this.toString( fmt, "],[", "," ) + "]])";
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Sage Math.
     */
    // -----------------------------------------------------------------------------------
    public String sage_toString( ) {
	// -------------------------------------------------------------------------------
	return this.sage_toString( "%g" );
    }
    

}


// =======================================================================================
// **                                      M A T 4                                      **
// ======================================================================== END FILE =====
