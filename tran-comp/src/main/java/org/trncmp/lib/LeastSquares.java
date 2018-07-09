// ====================================================================== BEGIN FILE =====
// **                              L E A S T S Q U A R E S                              **
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
 * @brief Provides the interface and methods for Least Squares.
 * @file LeastSquares.java
 *
 * @author Stephen W. Soliday
 * @date 2017-09-23
 */
// =======================================================================================

package org.trncmp.lib;
import org.apache.log4j.*;

// =======================================================================================
public class LeastSquares {
    // -----------------------------------------------------------------------------------
    private static final Logger logger = LogManager.getRootLogger();

    // ===================================================================================
    public static class RetVal {
	// -------------------------------------------------------------------------------
	public double slope     = 0.0;
	public double intercept = 0.0;

	// -------------------------------------------------------------------------------
	public RetVal( double s, double i ) {
	    // ---------------------------------------------------------------------------
	    slope     = s;
	    intercept = i;
	}
    }

    
    // ===================================================================================
    /** @brief Least Squares.
     *  @param data array of xy coordinates (Nx2).
     */
    // -----------------------------------------------------------------------------------
    public static LeastSquares.RetVal solve( double[][] data ) {
	// -------------------------------------------------------------------------------
	return LeastSquares.solve( data, 1.0e-10 );
    }

    
    // ===================================================================================
    /** @brief Least Squares.
     *  @param data array of xy coordinates (Nx2).
     *  @param tol  tolleance for zero test of determinate.
     */
    // -----------------------------------------------------------------------------------
    public static LeastSquares.RetVal solve( double[][] data, double tol ) {
	// -------------------------------------------------------------------------------
	int nr = data.length;
	int nc = data[0].length;

	double[] xco = null;
	double[] yco = null;

	if ( 2 == nr ) {
	    xco = new double[nc];
	    yco = new double[nc];
	    for ( int i=0; i<nc; i++ ) {
		xco[i] = data[0][i];
		yco[i] = data[1][i];
	    }
	} else {
	    if ( 2 == nc ) {
		xco = new double[nr];
		yco = new double[nr];
		for ( int i=0; i<nr; i++ ) {
		    xco[i] = data[i][0];
		    yco[i] = data[i][1];
		}
	    } else {
		logger.error( "LeastSquares: data must be Nx2 or 2xN" );
		return null;
	    }
	}

	return LeastSquares.solve( xco, yco, tol );
    }

    
    // ===================================================================================
    /** @brief Least Squares.
     *  @param xco array of x coordinates.
     *  @param yco array of y coordinates.
     */
    // -----------------------------------------------------------------------------------
    public static LeastSquares.RetVal solve( double[] xco, double[] yco ) {
	// -------------------------------------------------------------------------------
	return LeastSquares.solve( xco, yco, 1.0e-10 );
    }

    
    // ===================================================================================
    /** @brief Least Squares.
     *  @param xco array of x coordinates.
     *  @param yco array of y coordinates.
     *  @param tol tolleance fo zero test of determinate.
     */
    // -----------------------------------------------------------------------------------
    public static LeastSquares.RetVal solve( double[] xco, double[] yco, double tol ) {
	// -------------------------------------------------------------------------------

	int ns = xco.length;
	if ( ns > yco.length ) { ns = yco.length; }
	
	double X2 = 0.0e0;
	double XY = 0.0e0;
	double X  = 0.0e0;
	double Y  = 0.0e0;

	double N = (double) ns;

	for ( int i=0; i<ns; i++ ) {
	    double xt = xco[i];
	    double yt = yco[i];
	    X2 += ( xt * xt );
	    XY += ( xt * yt );
	    X  +=   xt;
	    Y  +=   yt;
	}

	double det = N * X2 - ( X * X );

	if ( Math2.isZero( det, tol ) ) {
	    return null;
	}

	return new LeastSquares.RetVal( ( N * XY - ( X * Y ) ) / det,
					( ( X2 * Y ) - ( X * XY ) ) / det );	
    }

}


// =======================================================================================
// **                              L E A S T S Q U A R E S                              **
// ======================================================================== END FILE =====
