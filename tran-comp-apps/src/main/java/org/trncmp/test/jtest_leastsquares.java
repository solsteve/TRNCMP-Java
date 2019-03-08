// ====================================================================== BEGIN FILE =====
// **                        J T E S T _ L E A S T S Q U A R E S                        **
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
 * @file jtest_leastsquares.java
 *  Provides Unit Test for the LeastSquares Class.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-09-23
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.*;


// =======================================================================================
class jtest_leastsquares {
    // -----------------------------------------------------------------------------------

    public static final int    SAMPLES   = 200;
    public static final double SLOPE     =   0.3e0;
    public static final double INTERCEPT = 100.0e0;
    public static final double STDY      =   1.0e0;
    public static final double MINX      =   0.0e0;
    public static final double MAXX      = 200.0e0;

    // ===================================================================================
    public static void Test01( Dice dd ) {
 	// -------------------------------------------------------------------------------
	System.out.println( "\n----- Nx1 and Nx1 ----------------------------\n\n" );

	double[] xco = new double[SAMPLES];
	double[] yco = new double[SAMPLES];

	double delta = ( MAXX - MINX ) / (double)SAMPLES;

	double x = MINX;

	for ( int i=0; i<SAMPLES; i++ ) {
	    double y = SLOPE * x + INTERCEPT + (STDY * dd.normal());

	    xco[i] = x;
	    yco[i] = y;

	    x = x + delta;
	}

	LeastSquares.RetVal RV = LeastSquares.solve( xco, yco );

	System.out.format( "slope     expect: %8.3f got: %8.3f\n", SLOPE,     RV.slope );
	System.out.format( "intercept expect: %8.3f got: %8.3f\n", INTERCEPT, RV.intercept );
    }


    // ===================================================================================
    public static void Test02( Dice dd ) {
 	// -------------------------------------------------------------------------------
	System.out.println( "\n----- Nx2 ------------------------------------\n\n" );

	double[][] data = new double[SAMPLES][2];

	double delta = ( MAXX - MINX ) / (double)SAMPLES;

	double x = MINX;

	for ( int i=0; i<SAMPLES; i++ ) {
	    double y = SLOPE * x + INTERCEPT + (STDY * dd.normal());

	    data[i][0] = x;
	    data[i][1] = y;

	    x = x + delta;
	}

	LeastSquares.RetVal RV = LeastSquares.solve( data );

	System.out.format( "slope     expect: %8.3f got: %8.3f\n", SLOPE,     RV.slope );
	System.out.format( "intercept expect: %8.3f got: %8.3f\n", INTERCEPT, RV.intercept );
    }


    // ===================================================================================
    public static void Test03( Dice dd ) {
 	// -------------------------------------------------------------------------------
	System.out.println( "\n----- 2xN ------------------------------------\n\n" );

	double[][] data = new double[2][SAMPLES];

	double delta = ( MAXX - MINX ) / (double)SAMPLES;

	double x = MINX;

	for ( int i=0; i<SAMPLES; i++ ) {
	    double y = SLOPE * x + INTERCEPT + (STDY * dd.normal());

	    data[0][i] = x;
	    data[1][i] = y;

	    x = x + delta;
	}

	LeastSquares.RetVal RV = LeastSquares.solve( data );

	System.out.format( "slope     expect: %8.3f got: %8.3f\n", SLOPE,     RV.slope );
	System.out.format( "intercept expect: %8.3f got: %8.3f\n", INTERCEPT, RV.intercept );
    }


    // ===================================================================================
    public static void main(String[] args) {
	// -------------------------------------------------------------------------------

	Dice dd = Dice.getInstance();

	dd.seed_set();

	Test01( dd );
	Test02( dd );
	Test03( dd );
    }
    
}

// =======================================================================================
// **                        J T E S T _ L E A S T S Q U A R E S                        **
// ======================================================================== END FILE =====
