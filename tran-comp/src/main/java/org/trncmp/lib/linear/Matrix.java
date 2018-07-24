// ====================================================================== BEGIN FILE =====
// **                                    M A T R I X                                    **
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
/**
 * @brief Provides the interface and methods for arbitrary length matrix.
 * @file Matrix.java
 *
 * @author Stephen W. Soliday
 * @date 2017-09-21
 */
// =======================================================================================

package org.trncmp.lib.linear;

import org.trncmp.lib.Math2;

import org.apache.commons.math3.linear.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class Matrix {
    // -----------------------------------------------------------------------------------
    public double[][] A  = null;
    public int        nr = 0;
    public int        nc = 0;

     private static final Logger logger = LogManager.getLogger();
   
    // ===================================================================================
    private static int check_dims( int nr1, int nc1, int nr2, int nc2 ) {
	// -------------------------------------------------------------------------------
	if ( nr1 < nr2 ) return -1;
	if ( nc1 < nc2 ) return -2;
	if ( nr1 > nr2 ) return  1;
	if ( nc1 > nc2 ) return  2;
	return 0;
    }


    // ===================================================================================
    /** @brief Destroy.
     *
     *  Deallocate the buffer.
     */
    // -----------------------------------------------------------------------------------
    private void destroy( ) {
	// -------------------------------------------------------------------------------
	this.A  = null;
	this.nr = 0;
	this.nc = 0;
    }
    
    // ===================================================================================
    /** @brief Resize.
     *  @param n_row number of rows.
     *  @param n_col number of columns.
     *
     *  Reallocate the buffer if necessary
     */
    // -----------------------------------------------------------------------------------
    private void resize( int n_row, int n_col ) {
	// -------------------------------------------------------------------------------
	if ( ( n_row != this.nr ) || ( n_col != this.nc ) ) {
	    this.destroy();
	    this.nr = n_row;
	    this.nc = n_col;
	    this.A  = new double[n_row][n_col];
	}
    }


    // ===================================================================================
    /** @brief Constructor.
     *  @param n_row number of rows.
     *  @param n_col number of columns.
     *
     *  Create a new matrix.
     */
    // -----------------------------------------------------------------------------------
    public Matrix( int n_row, int n_col ) {
	// -------------------------------------------------------------------------------
	this.resize( n_row, n_col );
    }

    
    // ===================================================================================
    /** @brief Constructor.
     *  @param src reference to a source matrix.
     *
     *  Create a copy of the source matrix.
     */
    // -----------------------------------------------------------------------------------
    public Matrix( Matrix src ) {
	// -------------------------------------------------------------------------------
	this.resize( src.nr, src.nc );
	this.copy( src );
    }

    
    // ===================================================================================
    /** @brief Identity.
     *
     *  Make this an identity matrix.
     */
    // -----------------------------------------------------------------------------------
    public void ident( ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
		this.A[r][c] = (r==c) ? (1.0e0) : (0.0e0);
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
	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
		this.A[r][c] = val;
	    }
	}
    }

    
    // ===================================================================================
    /** @brief Set.
     *
     *  Set each element of this matrix to zero.
     */
    // -----------------------------------------------------------------------------------
    public void set( ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
		this.A[r][c] = 0.0e0;
	    }
	}
    }

    
    // ===================================================================================
    /** @brief Copy.
     *  @param src reference to a NxM source matrix.
     *
     *  Make a deep copy of the source matrix.
     */
    // -----------------------------------------------------------------------------------
    public void copy( Matrix src ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, src.nr, src.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}
	
	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
		this.A[r][c] = src.A[r][c];
	    }
	}
    }

    
    // ===================================================================================
    /** @brief Transpose.
     *  @param src reference to a NxM source matrix.
     *
     *  Make a this matrix a transpose of the source matrix
     */
    // -----------------------------------------------------------------------------------
    public void transpose( Matrix M ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, M.nc, M.nr ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}
	
	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
		this.A[r][c] = M.A[c][r];
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
	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
    public void add( Matrix m1 ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
    public void sub( Matrix m1 ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
    public void pmul( Matrix m1 ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
    public void pdiv( Matrix m1 ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
    public void add( Matrix m1, double scl ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
    public void sub( Matrix m1, double scl ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
    public void pmul( Matrix m1, double scl ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
    public void pdiv( Matrix m1, double scl ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
    public void add( Matrix m1, Matrix m2 ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	if ( 0 != check_dims( m2.nr, m2.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
    public void sub( Matrix m1, Matrix m2 ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	if ( 0 != check_dims( m2.nr, m2.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
    public void pmul( Matrix m1, Matrix m2 ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	if ( 0 != check_dims( m2.nr, m2.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
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
    public void pdiv( Matrix m1, Matrix m2 ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	if ( 0 != check_dims( m2.nr, m2.nc, m1.nr, m1.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
		this.A[r][c] = m1.A[r][c] / m2.A[r][c];
	    }
	}
    }








    // ===================================================================================
    /** @brief Load.
     *  @param src    reference to a source array.
     *  @param offset initial index in the source array.
     *  @return next unused index.
     *
     *  Load this matrix from an array.
     */
    // -----------------------------------------------------------------------------------
    public int load( double[] src, int offset ) {
	// -------------------------------------------------------------------------------
	int idx = offset;

	if ( (this.nr*this.nc)+idx > src.length ) {
	    throw( new java.lang.IllegalArgumentException("Not enough data") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
		this.A[r][c] = src[idx++];
	    }
	}

	return idx;
    }

    
    // ===================================================================================
    /** @brief Load.
     *  @param src reference to a source array.
     *  @return next unused index.
     *
     *  Load this matrix from an array.
     */
    // -----------------------------------------------------------------------------------
    public int load( double[] src ) {
	// -------------------------------------------------------------------------------
	return this.load( src, 0 );
    }

    
    // ===================================================================================
    /** @brief Store.
     *  @param dst    reference to a destination array.
     *  @param offset initial index in the destination array.
     *  @return next unused index.
     *
     *  Store this matrix in an array.
     */
    // -----------------------------------------------------------------------------------
    public int store( double[] dst, int offset ) {
	// -------------------------------------------------------------------------------
	int idx = offset;

	if ( (this.nr*this.nc)+idx > dst.length ) {
	    throw( new java.lang.IllegalArgumentException("Matrix is not square") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
		dst[idx++] = this.A[r][c];
	    }
	}

	return idx;
    }

    
    // ===================================================================================
    /** @brief Store.
     *  @param dst    reference to a destination array.
     *  @return next unused index.
     *
     *  Store this matrix in an array.
     */
    // -----------------------------------------------------------------------------------
    public int store( double[] dst ) {
	// -------------------------------------------------------------------------------
	return this.store( dst, 0 );
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public double det( ) {
	// -------------------------------------------------------------------------------
	double DT = 0.0e0;

	if ( this.nr == this.nc ) {
	    switch( this.nr ) {
	    case 0:
		DT = 1.0e0;
		break;

	    case 1:
		DT = this.A[0][0];
		break;

	    case 2:
		DT = this.det_2x2();
		break;
		
	    case 3:
		DT = this.det_3x3();
		break;
		
	    case 4:
		DT = this.det_4x4();
		break;
		
	    default:
		DT = this.det_NxN();
		break;
	    }
	} else {
	    throw( new java.lang.IllegalArgumentException("Matrix is not square") );
	}

	return DT;
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public double invert( Matrix M ) {
	// -------------------------------------------------------------------------------

	double DT = 0.0e0;

	if ( this.nr == this.nc ) {
	    if ( 0 == this.check_dims( this.nr, this.nc, M.nr, M.nc ) ) {
		switch( this.nr ) {
		case 0:
		    logger.error( "inverse: zero size matrix" );
		    break;

		case 1:
		    DT = M.A[0][0];
		    this.A[0][0] = 1.0e0 / DT;
		    break;

		case 2:
		    DT = this.inv_2x2( M );
		    break;
		
		case 3:
		    DT = this.inv_3x3( M );
		    break;
		
		case 4:
		    DT = this.inv_4x4( M );
		    break;
		
		default:
		    DT = this.inv_NxN( M );
		    break;
		}
	    } else {
		logger.error( "" );
		throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	    }
	} else {
	    throw( new java.lang.IllegalArgumentException("Matrix is not square") );
	}

	return DT;
    }


    // ===================================================================================
    /** @brief Swap.
     *  @param M reference to another matrix.
     *
     *  Swap each element of the two matrixs with each other.
     */
    // -----------------------------------------------------------------------------------
    public void swap( Matrix M ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, M.nr, M.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
		double temp  = this.A[r][c];
		this.A[r][c] = M.A[r][c];
		M.A[r][c]    = temp;
	    }
	}
    }

    
    // ===================================================================================
    /** @brief Sum of Squares.
     *  @return sum of the squares for the elements.
     *
     *  Compute the sum of the squares of the elements of this matrix.
     *  @note this is an inline alias for self dot member function.
     */
    // -----------------------------------------------------------------------------------
    public double sumsq( ) {
	// -------------------------------------------------------------------------------
	double sum = 0.0e0;
	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
		double d = this.A[r][c];
		sum += (d*d);
	    }
	}
	return sum;
    }

    
    // ===================================================================================
    /** @brief Sum Square Difference.
     *  @param rhs reference to another matrix.
     *  @return the sum of the squares of the differences.
     *
     *  Compute the sum of the squares of the differences   ret = sum (this[i]-rhs[i])**2
     */
    // -----------------------------------------------------------------------------------
    public double sumsq( Matrix M ) {
	// -------------------------------------------------------------------------------
	if ( 0 != check_dims( this.nr, this.nc, M.nr, M.nc ) ) {
	    throw( new java.lang.IllegalArgumentException("Matrix dims do not match") );
	}

	double sum = 0.0e0;
	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
		double d = this.A[r][c] - M.A[r][c];
		sum += (d*d);
	    }
	}
	return sum;
    }








    // ===================================================================================
    /** @brief Convert Matrix to String.
     *  @param fmt format specifier of each element.
     *  @param rdlm delimeter separating each row.
     *  @param cdlm delimeter separating each column.
     *  @return this matrix as a formated string.
     */
    // -----------------------------------------------------------------------------------
    public String toString( String fmt, String rdlm, String cdlm ) {
	// -------------------------------------------------------------------------------
	StringBuffer buffer = new StringBuffer();

	for ( int i=0; i<this.nr; i++ ) {
	    if ( 0 < i ) { buffer.append( rdlm ); }
	    for ( int j=0; j<this.nc; j++ ) {
		if ( 0 < j ) { buffer.append( cdlm ); }
		buffer.append( String.format( fmt, this.A[i][j] ) );
	    }
	}

	return buffer.toString();
    }

    
    // ===================================================================================
    /** @brief Convert Matrix to String.
     *  @param fmt format specifier of each element.
     *  @return this matrix as a formated string.
     */
    // -----------------------------------------------------------------------------------
    public String toString( String fmt ) {
	// -------------------------------------------------------------------------------
	return this.toString( fmt, "\n", " " );
    }


    // ===================================================================================
    /** @brief Convert Matrix to String.
     *  @return this matrix as a formated string.
     */
    // -----------------------------------------------------------------------------------
    public String toString() {
	// -------------------------------------------------------------------------------
	return this.toString( "%g", "\n", " " );
    }




    // ===================================================================================
    /** @brief Convert Matrix to String.
     *  @param fmt format specifier of each element.
     *  @return this matrix as a string compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    public String octave_toString( String fmt ) {
	// -------------------------------------------------------------------------------
	return "[ " + this.toString( fmt, " ; ", ", " ) + " ];";
    }


    // ===================================================================================
    /** @brief Convert Matrix to String.
     *  @return this matrix as a string compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    public String octave_toString( ) {
	// -------------------------------------------------------------------------------
	return this.octave_toString( "%g" );
    }




    // ===================================================================================
    /** @brief Convert Matrix to String.
     *  @param fmt format specifier of each element  (default: %g ).
     *  @param dlm delimeter separating each element (default: ' ').
     *  @return this matrix as a string compatable with Sage Math.
     */
    // -----------------------------------------------------------------------------------
    public String sage_toString( String fmt ) {
	// -------------------------------------------------------------------------------
	return "Matrix(SR,[[" + this.toString( fmt, "],[", "," ) + "]])";
    }


    // ===================================================================================
    /** @brief Convert Matrix to String.
     *  @param fmt format specifier of each element  (default: %g ).
     *  @param dlm delimeter separating each element (default: ' ').
     *  @return this matrix as a string compatable with Sage Math.
     */
    // -----------------------------------------------------------------------------------
    public String sage_toString( ) {
	// -------------------------------------------------------------------------------
	return this.sage_toString( "%g" );
    }








    // ===================================================================================
    /** @brief Determinant.
     *  @return the determinant of this matrix.
     */
    // -----------------------------------------------------------------------------------
    private double det_2x2( ) {
	// -------------------------------------------------------------------------------
	return A[0][0]*A[1][1] - A[0][1]*A[1][0];
    }

    
    // ===================================================================================
    /** @brief Determinant.
     *  @return the determinant of this matrix.
     */
    // -----------------------------------------------------------------------------------
    private double det_3x3( ) {
	// -------------------------------------------------------------------------------
	return
	    (A[0][1]*A[1][2] - A[0][2]*A[1][1])*A[2][0] +
	    (A[0][2]*A[1][0] - A[0][0]*A[1][2])*A[2][1] +
	    (A[0][0]*A[1][1] - A[0][1]*A[1][0])*A[2][2];
    }

    
    // ===================================================================================
    /** @brief Determinant.
     *  @return the determinant of this matrix.
     */
    // -----------------------------------------------------------------------------------
    private double det_4x4( ) {
	// -------------------------------------------------------------------------------
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
    /** @brief Determinant.
     *  @return the determinant of this matrix.
     */
    // -----------------------------------------------------------------------------------
    private double det_NxN( ) {
	// -------------------------------------------------------------------------------
	LUDecomposition LU = new LUDecomposition( new Array2DRowRealMatrix( this.A, false ) );

	return LU.getDeterminant();
    }

    
    // ===================================================================================
    /** @brief Invert.
     *  @param M matrix.
     *  @return the determinant of the source matrix.
     *
     *  Set this matrix to the inverse of the source matrix.
     */
    // -----------------------------------------------------------------------------------
    private double inv_2x2( Matrix M ) {
	// -------------------------------------------------------------------------------
	double D = M.det_2x2();
	
	if ( Math2.isZero( D ) ) { return 0.0e0; }

	A[0][0] =  M.A[1][1] / D;
	A[0][1] = -M.A[0][1] / D;

	A[1][0] = -M.A[1][0] / D;
	A[1][1] =  M.A[0][0] / D;

	return D;
    }
     

    // ===================================================================================
    /** @brief Invert.
     *  @param M matrix.
     *  @return the determinant of the source matrix.
     *
     *  Set this matrix to the inverse of the source matrix.
     */
    // -----------------------------------------------------------------------------------
    private double inv_3x3( Matrix M ) {
	// -------------------------------------------------------------------------------
	double D = M.det_3x3();
	
	if ( Math2.isZero( D ) ) { return 0.0e0; }

	A[0][0] = ( M.A[1][1]*M.A[2][2] - M.A[1][2]*M.A[2][1] ) / D;
	A[0][1] = ( M.A[0][2]*M.A[2][1] - M.A[0][1]*M.A[2][2] ) / D;
	A[0][2] = ( M.A[0][1]*M.A[1][2] - M.A[0][2]*M.A[1][1] ) / D;

	A[1][0] = ( M.A[1][2]*M.A[2][0] - M.A[1][0]*M.A[2][2] ) / D;
	A[1][1] = ( M.A[0][0]*M.A[2][2] - M.A[0][2]*M.A[2][0] ) / D;
	A[1][2] = ( M.A[0][2]*M.A[1][0] - M.A[0][0]*M.A[1][2] ) / D;

	A[2][0] = ( M.A[1][0]*M.A[2][1] - M.A[1][1]*M.A[2][0] ) / D;
	A[2][1] = ( M.A[0][1]*M.A[2][0] - M.A[0][0]*M.A[2][1] ) / D;
	A[2][2] = ( M.A[0][0]*M.A[1][1] - M.A[0][1]*M.A[1][0] ) / D;

	return D;
    }
     

    // ===================================================================================
    /** @brief Invert.
     *  @param M matrix.
     *  @return the determinant of the source matrix.
     *
     *  Set this matrix to the inverse of the source matrix.
     */
    // -----------------------------------------------------------------------------------
    private double inv_4x4( Matrix M ) {
	// -------------------------------------------------------------------------------
	double D = M.det_4x4();
	
	if ( Math2.isZero( D ) ) { return 0.0e0; }

	A[0][0] = ( - (M.A[1][3]*M.A[2][2] - M.A[1][2]*M.A[2][3])*M.A[3][1]
		    + (M.A[1][3]*M.A[2][1] - M.A[1][1]*M.A[2][3])*M.A[3][2]
		    - (M.A[1][2]*M.A[2][1] - M.A[1][1]*M.A[2][2])*M.A[3][3] ) / D;

	A[0][1] = (   (M.A[0][3]*M.A[2][2] - M.A[0][2]*M.A[2][3])*M.A[3][1]
		      - (M.A[0][3]*M.A[2][1] - M.A[0][1]*M.A[2][3])*M.A[3][2]
		      + (M.A[0][2]*M.A[2][1] - M.A[0][1]*M.A[2][2])*M.A[3][3] ) / D;

	A[0][2] = ( - (M.A[0][3]*M.A[1][2] - M.A[0][2]*M.A[1][3])*M.A[3][1]
		    + (M.A[0][3]*M.A[1][1] - M.A[0][1]*M.A[1][3])*M.A[3][2]
		    - (M.A[0][2]*M.A[1][1] - M.A[0][1]*M.A[1][2])*M.A[3][3] ) / D;

	A[0][3] = (   (M.A[0][3]*M.A[1][2] - M.A[0][2]*M.A[1][3])*M.A[2][1]
		      - (M.A[0][3]*M.A[1][1] - M.A[0][1]*M.A[1][3])*M.A[2][2]
		      + (M.A[0][2]*M.A[1][1] - M.A[0][1]*M.A[1][2])*M.A[2][3] ) / D;

 
	// ---------------------------------


	A[1][0] = (    (M.A[1][3]*M.A[2][2] - M.A[1][2]*M.A[2][3])*M.A[3][0]
		       - (M.A[1][3]*M.A[2][0] - M.A[1][0]*M.A[2][3])*M.A[3][2]
		       + (M.A[1][2]*M.A[2][0] - M.A[1][0]*M.A[2][2])*M.A[3][3] ) / D;

	A[1][1] = ( - (M.A[0][3]*M.A[2][2] - M.A[0][2]*M.A[2][3])*M.A[3][0]
		    + (M.A[0][3]*M.A[2][0] - M.A[0][0]*M.A[2][3])*M.A[3][2]
		    - (M.A[0][2]*M.A[2][0] - M.A[0][0]*M.A[2][2])*M.A[3][3] ) / D;

	A[1][2] = (   (M.A[0][3]*M.A[1][2] - M.A[0][2]*M.A[1][3])*M.A[3][0]
		      - (M.A[0][3]*M.A[1][0] - M.A[0][0]*M.A[1][3])*M.A[3][2]
		      + (M.A[0][2]*M.A[1][0] - M.A[0][0]*M.A[1][2])*M.A[3][3] ) / D;

	A[1][3] = ( - (M.A[0][3]*M.A[1][2] - M.A[0][2]*M.A[1][3])*M.A[2][0]
		    + (M.A[0][3]*M.A[1][0] - M.A[0][0]*M.A[1][3])*M.A[2][2]
		    - (M.A[0][2]*M.A[1][0] - M.A[0][0]*M.A[1][2])*M.A[2][3] ) / D;

	// ---------------------------------

	A[2][0] = ( - (M.A[1][3]*M.A[2][1] - M.A[1][1]*M.A[2][3])*M.A[3][0]
		    + (M.A[1][3]*M.A[2][0] - M.A[1][0]*M.A[2][3])*M.A[3][1]
		    - (M.A[1][1]*M.A[2][0] - M.A[1][0]*M.A[2][1])*M.A[3][3] ) / D;

	A[2][1] = (   (M.A[0][3]*M.A[2][1] - M.A[0][1]*M.A[2][3])*M.A[3][0]
		      - (M.A[0][3]*M.A[2][0] - M.A[0][0]*M.A[2][3])*M.A[3][1]
		      + (M.A[0][1]*M.A[2][0] - M.A[0][0]*M.A[2][1])*M.A[3][3] ) / D;

	A[2][2] = ( - (M.A[0][3]*M.A[1][1] - M.A[0][1]*M.A[1][3])*M.A[3][0]
		    + (M.A[0][3]*M.A[1][0] - M.A[0][0]*M.A[1][3])*M.A[3][1]
		    - (M.A[0][1]*M.A[1][0] - M.A[0][0]*M.A[1][1])*M.A[3][3] ) / D;

	A[2][3] = (   (M.A[0][3]*M.A[1][1] - M.A[0][1]*M.A[1][3])*M.A[2][0]
		      - (M.A[0][3]*M.A[1][0] - M.A[0][0]*M.A[1][3])*M.A[2][1]
		      + (M.A[0][1]*M.A[1][0] - M.A[0][0]*M.A[1][1])*M.A[2][3] ) / D;

	// ---------------------------------


	A[3][0] = (   (M.A[1][2]*M.A[2][1] - M.A[1][1]*M.A[2][2])*M.A[3][0]
		      - (M.A[1][2]*M.A[2][0] - M.A[1][0]*M.A[2][2])*M.A[3][1]
		      + (M.A[1][1]*M.A[2][0] - M.A[1][0]*M.A[2][1])*M.A[3][2] ) / D;

	A[3][1] = ( - (M.A[0][2]*M.A[2][1] - M.A[0][1]*M.A[2][2])*M.A[3][0]
		    + (M.A[0][2]*M.A[2][0] - M.A[0][0]*M.A[2][2])*M.A[3][1]
		    - (M.A[0][1]*M.A[2][0] - M.A[0][0]*M.A[2][1])*M.A[3][2] ) / D;

	A[3][2] = (   (M.A[0][2]*M.A[1][1] - M.A[0][1]*M.A[1][2])*M.A[3][0]
		      - (M.A[0][2]*M.A[1][0] - M.A[0][0]*M.A[1][2])*M.A[3][1]
		      + (M.A[0][1]*M.A[1][0] - M.A[0][0]*M.A[1][1])*M.A[3][2] ) / D;

	A[3][3] = ( - (M.A[0][2]*M.A[1][1] - M.A[0][1]*M.A[1][2])*M.A[2][0]
		    + (M.A[0][2]*M.A[1][0] - M.A[0][0]*M.A[1][2])*M.A[2][1]
		    - (M.A[0][1]*M.A[1][0] - M.A[0][0]*M.A[1][1])*M.A[2][2] ) / D;

	return D;
    }
     

    // ===================================================================================
    /** @brief Invert.
     *  @param M matrix.
     *  @return the determinant of the source matrix.
     *
     *  Set this matrix to the inverse of the source matrix.
     */
    // -----------------------------------------------------------------------------------
    private double inv_NxN( Matrix M ) {
	// -------------------------------------------------------------------------------
	LUDecomposition LU = new LUDecomposition( new Array2DRowRealMatrix( M.A, false ) );

	double D = LU.getDeterminant();

	if ( Math2.isZero( D ) ) { return 0.0e0; }

	double[][] temp = LU.getSolver().getInverse().getData();

	for ( int r=0; r<this.nr; r++ ) {
	    for ( int c=0; c<this.nc; c++ ) {
		this.A[r][c] = temp[r][c];
	    }
	}

	return D;
    }

    
}


// =======================================================================================
// **                                    M A T R I X                                    **
// ======================================================================== END FILE =====
