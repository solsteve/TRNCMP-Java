// ====================================================================== BEGIN FILE =====
// **                               E I G E N S Y S T E M                               **
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
 * @brief   EigenSystem.
 * @file    EigenSystem.java
 *
 * @details Provides the interface and procedures for single and multi-dimensional
 *          Statistics. (This is an alias for legacy code)
 *
 * @author  Stephen W. Soliday
 * @date    2016-04-04 Original release.
 * @date    2017-09-30 Migration to the Proxima libraries.
 */
// =======================================================================================

package org.trncmp.lib;

import java.io.*;

import org.trncmp.lib.linear.Vector;
import org.trncmp.lib.linear.Matrix;
import org.apache.commons.math3.linear.*;
import org.apache.log4j.*;

// =======================================================================================
public class Eigen {
    // -----------------------------------------------------------------------------------
    
    public Vector eval  = null;      // Eigen values  (real)
    public Matrix evec  = null;      // Eigen vectors
    public int order = 0;     // Dimensions
    public int found = 0;     // Number of eigen-values found
    public Matrix S    = null;
    public Matrix U    = null;
    public Matrix V    = null;
    public Matrix work = null;

    private static final Logger logger = LogManager.getRootLogger();

	 

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public Eigen( int n ) {
	// -------------------------------------------------------------------------------
	eval  = new Vector( n );
	evec  = new Matrix( n, n );
	work  = new Matrix( n, n );
	S     = new Matrix( n, n );
	U     = new Matrix( n, n );
	V     = new Matrix( n, n );

	order  = n;
	found  = 0;
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public void clear( ) {
	// -------------------------------------------------------------------------------
	eval.set();
	evec.set();
	found = 0;
    }


    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public void report( PrintStream ps, String fmt ) {
	// -------------------------------------------------------------------------------

	if ( 0 < found ) {
	    for ( int i=0; i<found; i++ ) {
		ps.format( fmt, eval.x[i] );
		ps.format( " |" );
		for ( int j=0; j<order; j++ ) {
		    ps.format( " " );
		    ps.format( fmt, evec.A[i][j] );
		}
	    }
	} else {
	    logger.error( "Report: no eigen system has been compiled" );
	}

    }








    // ===================================================================================
    /** @brief Compile Eigensystem.
     *  @param data pointer to matrix data.
     *  @param n    matrix dimension (assume square n by n)
     *  @param tda  trailing dimension. If 0 tda is set to n.
     *
     *  @note the Vectors are stored in the columns
     */
    // -----------------------------------------------------------------------------------
    public boolean compile( Matrix M ) {
	// -------------------------------------------------------------------------------
	if ( M.nr != M.nc ) {
	    logger.error( "Source matrix must be square ("+M.nr+"x"+M.nc+")" );
	    return true;
	}

	SingularValueDecomposition svd
	    = new SingularValueDecomposition( new Array2DRowRealMatrix( M.A, false ) );

	double[] singleValues = svd.getSingularValues();

	double[][] Umatrix = svd.getVT().getData();

	this.eval.load( singleValues );

	this.found = 0;
	for ( int r=0; r<M.nr; r++ ) {
	    for ( int c=0; c<M.nc; c++ ) {
		this.evec.A[r][c] = Umatrix[r][c];
	    }
	    double x = this.eval.x[r];
	    if ( Math2.isNotZero( x ) ) {
		this.found += 1;
	    }
	}

	return (0 < this.found) ? (false) : (true);
    }


};

// =======================================================================================
// **                               E I G E N S Y S T E M                               **
// ======================================================================== END FILE =====
