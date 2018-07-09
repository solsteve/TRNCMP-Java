// ====================================================================== BEGIN FILE =====
// **                                    V E C T O R                                    **
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
 * @brief Provides the interface and methods for arbitrary length vector.
 * @file Vector.java
 *
 * @author Stephen W. Soliday
 * @date 2017-09-12
 */
// =======================================================================================

package org.trncmp.lib.linear;

// =======================================================================================
public class Vector {
    // -----------------------------------------------------------------------------------
    public double[] x  = null;
    public int      nx = 0;

    // ===================================================================================
    /** @brief Destroy.
     *
     *  Deallocate the buffer.
     */
    // -----------------------------------------------------------------------------------
    private void destroy( ) {
	// -------------------------------------------------------------------------------
	this.x  = null;
	this.nx = 0;
    }

    
    // ===================================================================================
    /** @brief Resize.
     *  @param nr number of rows.
     *
     *  Reallocate the buffer if necessary
     */
    // -----------------------------------------------------------------------------------
    private void resize( int nr ) {
	// -------------------------------------------------------------------------------
	if ( nr != this.nx ) {
	    this.destroy();
	    this.nx = nr;
	    this.x  = new double[nr];
	}
    }








    // ===================================================================================
    /** @brief Constructor.
     *
     *  Create an empty vector.
     */
    // -----------------------------------------------------------------------------------
    public Vector( ) {
	// -------------------------------------------------------------------------------
	this.x  = null;
	this.nx = 0;
    }

    
    // ===================================================================================
    /** @brief Constructor.
     *  @param nr number of rows.
     *
     *  Create a new vector.
     */
    // -----------------------------------------------------------------------------------
    public Vector( int nr ) {
	// -------------------------------------------------------------------------------
	this.resize( nr );
    }

    
    // ===================================================================================
    /** @brief Constructor.
     *  @param q pointer to a source array.
     *
     *  Create a copy of the source array.
     */
    // -----------------------------------------------------------------------------------
    public Vector( double[] q ) {
	// -------------------------------------------------------------------------------
	this.resize( q.length );
	this.load( q );
    }

    
    // ===================================================================================
    /** @brief Constructor.
     *  @param src reference to a source vector.
     *
     *  Create a copy of the source vector.
     */
    // -----------------------------------------------------------------------------------
    public Vector( Vector src ) {
	// -------------------------------------------------------------------------------
	this.resize( src.nx );
	this.copy( src );
    }

    
    // ===================================================================================
    /** @brief Set.
     *  @param val value to set each element ( default: 0 ).
     *
     *  Set each element of this vector to the value of the argument val.
     */
    // -----------------------------------------------------------------------------------
    public void set( double val ) {
	// -------------------------------------------------------------------------------
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = val;
	}
    }

    
    // ===================================================================================
    /** @brief Set.
     *
     *  Set each element of this vector to zero.
     */
    // -----------------------------------------------------------------------------------
    public void set( ) {
	// -------------------------------------------------------------------------------
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = 0.0e0;
	}
    }

    
    // ===================================================================================
    /** @brief Constructor.
     *  @param q pointer to a source array.
     *
     *  Create a copy of the source array.
     */
    // -----------------------------------------------------------------------------------
    public void copy( Vector src ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != src.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = src.x[i];
	}
    }

    
    // ===================================================================================
    /** @brief Euclidian Norm.
     *  @return Euclidian Norm.
     * 
     *  Compute the Euclidian norm = sqrt( x' * x ).
     */
    // -----------------------------------------------------------------------------------
    public double mag( ) {
	// -------------------------------------------------------------------------------
	return Math.sqrt( this.dot() );
    }

    
    // ===================================================================================
    /** @brief Normalize.
     * 
     *  Normalize this vector. self = self / ||self||
     */
    // -----------------------------------------------------------------------------------
    public double norm( ) {
	// -------------------------------------------------------------------------------
	double nrm = Math.sqrt( this.dot() );
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] /= nrm;
	}
	return nrm;
    }

    
    // ===================================================================================
    /** @brief Normalize.
     * 
     *  Normalize this vector. self = self / ||self||
     */
    // -----------------------------------------------------------------------------------
    public double norm( Vector src ) {
	// -------------------------------------------------------------------------------
	double nrm = Math.sqrt( src.dot() );
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = src.x[i] / nrm;
	}
	return nrm;
    }








    // ===================================================================================
    /** @brief Vector-scalar Addition.
     *  @param scl scalar input.
     * 
     *  Add scl to each element of this vector.
     */
    // -----------------------------------------------------------------------------------
    public void add( double scl ) {
	// -------------------------------------------------------------------------------
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] += scl;
	}
    }


    // ===================================================================================
    /** @brief Vector-scalar Subtraction.
     *  @param scl scalar input.
     * 
     *  Subtract scl from each element of this vector.
     */
    // -----------------------------------------------------------------------------------
    public void sub( double scl ) {
	// -------------------------------------------------------------------------------
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] -= scl;
	}
    }


    // ===================================================================================
    /** @brief Vector-scalar Multiplication.
     *  @param scl scalar input.
     * 
     *  Multiply each element of this vector by scl.
     */
    // -----------------------------------------------------------------------------------
    public void pmul( double scl ) {
	// -------------------------------------------------------------------------------
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] *= scl;
	}
    }


    // ===================================================================================
    /** @brief Vector-scalar Division.
     *  @param scl scalar input.
     * 
     *  Divide each element of this vector by scl.
     */
    // -----------------------------------------------------------------------------------
    public void pdiv( double scl ) {
	// -------------------------------------------------------------------------------
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] /= scl;
	}
    }








    // ===================================================================================
    /** @brief Element Wise Inplace Vector Addition.
     *  @param v1 reference to the right source vector.
     * 
     *  Add each element of m1 to this vector.
     */
    // -----------------------------------------------------------------------------------
    public void add( Vector v1 ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != v1.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] += v1.x[i];
	}
    }


    // ===================================================================================
    /** @brief Element Wise Inplace Vector Subtraction.
     *  @param v1   reference to the right source vector.
     * 
     *  Subtract each element of m1 from this vector.
     */
    // -----------------------------------------------------------------------------------
    public void sub( Vector v1 ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != v1.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] -= v1.x[i];
	}
    }


    // ===================================================================================
    /** @brief Element Wise Inplace Vector Multiplication.
     *  @param v1 reference to the right source vector.
     * 
     *  Multiply each element of this vector by m1.
     */
    // -----------------------------------------------------------------------------------
    public void pmul( Vector v1 ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != v1.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] *= v1.x[i];
	}
    }


    // ===================================================================================
    /** @brief Element Wise Inplace Vector Division.
     *  @param v1 reference to the right source vector.
     * 
     *  Divide each element of this vector by m1.
     */
    // -----------------------------------------------------------------------------------
    public void pdiv( Vector v1 ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != v1.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] /= v1.x[i];
	}
    }








    // ===================================================================================
    /** @brief Matrix-scalar Addition.
     *  @param v1  reference to the left source matrix.
     *  @param scl scalar input.
     * 
     *  Add scl to each element of v1 and store in this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void add( Vector v1, double scl ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != v1.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = v1.x[i] + scl;
	}
    }


    // ===================================================================================
    /** @brief Matrix-scalar Subtraction.
     *  @param v1  reference to the left source matrix.
     *  @param scl scalar input.
     * 
     *  Subtract scl from each element of v1 and store in this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void sub( Vector v1, double scl ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != v1.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = v1.x[i] - scl;
	}
    }


    // ===================================================================================
    /** @brief Matrix-scalar Multiplication.
     *  @param v1  reference to the left source matrix.
     *  @param scl scalar input.
     * 
     *  Multiply each element of v1 by scl and store in this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void pmul( Vector v1, double scl ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != v1.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = v1.x[i] * scl;
	}
    }


    // ===================================================================================
    /** @brief Matrix-scalar Division.
     *  @param v1  reference to the left source matrix.
     *  @param scl scalar input.
     * 
     *  Divide each element of v1 by scl and store in this matrix.
     */
    // -----------------------------------------------------------------------------------
    public void pdiv( Vector v1, double scl ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != v1.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = v1.x[i] / scl;
	}
    }








    // ===================================================================================
    /** @brief Element Wise Vector Addition.
     *  @param v1   reference to the left  source vector.
     *  @param v2   reference to the right source vector.
     * 
     *  Add each element of v1 to v2 and store in this vector.
     */
    // -----------------------------------------------------------------------------------
    public void add( Vector v1, Vector v2 ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != v1.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}

	if ( this.nx != v2.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = v1.x[i] + v2.x[i];
	}
    }

    
    // ===================================================================================
    /** @brief Element Wise Vector Subtraction.
     *  @param v1   reference to the left  source vector.
     *  @param v2   reference to the right source vector.
     * 
     *  Subtract each element of v2 from v1 and store in this vector.
     */
    // -----------------------------------------------------------------------------------
    public void sub( Vector v1, Vector v2 ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != v1.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}

	if ( this.nx != v2.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = v1.x[i] - v2.x[i];
	}
    }

    
    // ===================================================================================
    /** @brief Element Wise Vector Multiplication.
     *  @param v1   reference to the left  source vector.
     *  @param v2   reference to the right source vector.
     * 
     *  Multipliy each element of v1 by v2 and store in this vector.
     */
    // -----------------------------------------------------------------------------------
    public void pmul( Vector v1, Vector v2 ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != v1.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}

	if ( this.nx != v2.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = v1.x[i] * v2.x[i];
	}
    }

    
    // ===================================================================================
    /** @brief Element Wise Vector Division.
     *  @param v1   reference to the left  source vector.
     *  @param v2   reference to the right source vector.
     * 
     *  Divide each element of v1 by v2 and store in this vector.
     */
    // -----------------------------------------------------------------------------------
    public void pdiv( Vector v1, Vector v2 ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != v1.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}

	if ( this.nx != v2.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = v1.x[i] / v2.x[i];
	}
    }








    // ===================================================================================
    /** @brief Inner Product.
     *  @return inner product of this vector with itself.
     *
     *  Return the inner product of this vector with itself. dot = self' * self
     */
    // -----------------------------------------------------------------------------------
    public double dot( ) {
	// -------------------------------------------------------------------------------
	double sum = 0.0e0;
	for ( int i=0; i<this.nx; i++ ) {
	    double d = this.x[i];
	    sum += (d*d);
	}
	return sum;
    }

    
    // ===================================================================================
    /** @brief Inner Product.
     *  @param rhs right hand side vector.
     *  @return inner product of this vector with itself.
     *
     *  Return the inner product of this vector with itself. dot = self' * rhs
     */
    // -----------------------------------------------------------------------------------
    public double dot( Vector rhs ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != rhs.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	double sum = 0.0e0;
	for ( int i=0; i<this.nx; i++ ) {
	    sum += ( this.x[i] * rhs.x[i] );
	}
	return sum;
    }

    
    // ===================================================================================
    /** @brief Load.
     *  @param src    reference to a source array.
     *  @param offset starting index in the source array.
     *  @return next unused index.
     */
    // -----------------------------------------------------------------------------------
    public int load( double[] src, int offset ) {
	// -------------------------------------------------------------------------------
	int idx = offset;
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = src[idx++];
	}
	return idx;
    }

    
    // ===================================================================================
    /** @brief Load.
     *  @param src reference to a source array.
     *  @return next unused index.
     */
    // -----------------------------------------------------------------------------------
    public int load( double[] src ) {
	// -------------------------------------------------------------------------------
	int idx = 0;
	for ( int i=0; i<this.nx; i++ ) {
	    this.x[i] = src[idx++];
	}
	return idx;
    }

    
    // ===================================================================================
    /** @brief Store.
     *  @param dst    reference to a destination array.
     *  @param offset starting index in the destination array.
     *  @return next unused index.
     */
    // -----------------------------------------------------------------------------------
    public int store( double[] dst, int offset ) {
	// -------------------------------------------------------------------------------
	int idx = offset;
	for ( int i=0; i<this.nx; i++ ) {
	    dst[idx++] = this.x[i];
	}
	return idx;
    }

    
    // ===================================================================================
    /** @brief Store.
     *  @param dst    reference to a destination array.
     *  @return next unused index.
     */
    // -----------------------------------------------------------------------------------
    public int store( double[] dst ) {
	// -------------------------------------------------------------------------------
	int idx = 0;
	for ( int i=0; i<this.nx; i++ ) {
	    dst[idx++] = this.x[i];
	}
	return idx;
    }

    
    // ===================================================================================
    /** @brief Sum of Squares.
     *  @return sum of the squares for the elements.
     *
     *  Compute the sum of the squares of the elements of this vector.
     *  @note this is an inline alias for self dot member function.
     */
    // -----------------------------------------------------------------------------------
    public double sumsq( ) {
	// -------------------------------------------------------------------------------
	return this.dot();
    }

    
    // ===================================================================================
    /** @brief Sum Square Difference.
     *  @param rhs reference to another vector.
     *  @return the sum of the squares of the differences.
     *
     *  Compute the sum of the squares of the differences   ret = sum (this[i]-rhs[i])**2
     */
    // -----------------------------------------------------------------------------------
    public double sumsq( Vector rhs ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != rhs.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	double sum = 0.0e0;
	for ( int i=0; i<this.nx; i++ ) {
	    double d = this.x[i] - rhs.x[i];
	    sum += ( d * d );
	}
	return sum;
    }

    
    // ===================================================================================
    /** @brief Swap.
     *  @param rhs reference to the second vector.
     * 
     *  Swap each element of the this vector with those in the second vector.
     */
    // -----------------------------------------------------------------------------------
    public void swap( Vector rhs ) {
	// -------------------------------------------------------------------------------
	if ( this.nx != rhs.nx ) {
	    throw( new java.lang.IllegalArgumentException("Vectors are different lengths") );
	}
	for ( int i=0; i<this.nx; i++ ) {
	    double temp = this.x[i];
	    this.x[i]   = rhs.x[i];
	    rhs.x[i]    = temp;
	}
    }

    
    // ===================================================================================
    /** @brief Convert Vector to String.
     *  @param v reference to a vector.
     *  @param fmt format specifier of each element  (default: %g ).
     *  @param dlm delimeter separating each element (default: ' ').
     *  @return this vector as a formated string.
     */
    // -----------------------------------------------------------------------------------
    public String toString( String fmt, String dlm ) {
	// -------------------------------------------------------------------------------
	StringBuffer buffer = new StringBuffer();

	int n = this.nx;

	for ( int i=0; i<n; i++ ) {
	    if ( 0 < i ) { buffer.append( dlm ); }
	    buffer.append( String.format( fmt, this.x[i] ) );
	}

	return buffer.toString();
    }

    
    // ===================================================================================
    /** @brief Convert Vector to String.
     *  @param fmt format specifier of each element.
     *  @return this vector as a formated string.
     */
    // -----------------------------------------------------------------------------------
    public String toString( String fmt ) {
	// -------------------------------------------------------------------------------
	return this.toString( fmt, " " );
    }

    
    // ===================================================================================
    /** @brief Convert Vector to String.
     *  @return this vector as a formated string.
     */
    // -----------------------------------------------------------------------------------
    public String toString( ) {
	// -------------------------------------------------------------------------------
	return this.toString( "%g", " " );
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param fmt format specifier of each element.
     *  @return this vector as a string compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    public String octave_toString( String fmt ) {
	// -------------------------------------------------------------------------------
	return "[ "+ this.toString( fmt, " " ) + " ]';";
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @return this vector as a string compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    public String octave_toString( ) {
	// -------------------------------------------------------------------------------
	return this.octave_toString( "%g" );
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param fmt format specifier of each element.
     *  @return this vector as a string compatable with Sage Math.
     */
    // -----------------------------------------------------------------------------------
    public String sage_toString( String fmt ) {
	// -------------------------------------------------------------------------------
	return "Vector(SR,[" + this.toString( fmt, "," ) + "])";
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param fmt format specifier of each element.
     *  @return this vector as a string compatable with Sage Math.
     */
    // -----------------------------------------------------------------------------------
    public String sage_toString( ) {
	// -------------------------------------------------------------------------------
	return this.sage_toString( "%g" );
    }

    
}


// =======================================================================================
// **                                    V E C T O R                                    **
// ======================================================================== END FILE =====
