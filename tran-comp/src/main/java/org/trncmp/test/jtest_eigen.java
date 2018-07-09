// ====================================================================== BEGIN FILE =====
// **                               J T E S T _ E I G E N                               **
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
 * @brief   .
 * @file    file.java.hh.cc
 *
 * @details Provides the interface and procedures for.
 *
 * @author  Stephen W. Soliday
 * @date    2017-10-01
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.*;
import org.trncmp.lib.linear.*;

// =======================================================================================
public class jtest_eigen extends octave_utest {
    // -----------------------------------------------------------------------------------

    // ===================================================================================
    public static void rand_sym( Matrix M, Dice dd ) {
	// -------------------------------------------------------------------------------
	for ( int i=0; i<M.nr; i++ ) {
	    for ( int j=0; j<M.nc; j++ ) {
		double x = dd.normal();
		M.A[i][j] = x;
		M.A[j][i] = x;
	    }
	}
    }

    
    // ===================================================================================
    public static void main(String[] args) {
	// -------------------------------------------------------------------------------

	Dice dd = Dice.getInstance();

	dd.seed_set();
	
	int order = 2*dd.index(5) + 3;

	Eigen eig = new Eigen( order );
  
	Matrix mat = new Matrix( order, order );

	rand_sym( mat, dd );

	oprint( "M", mat );
  
	eig.compile( mat );

	oprint( "VA", eig.eval );
	oprint( "VC", eig.evec );

	System.out.println( "[U, S, V] = svd(M);" );

	ocheck( "eig val", "norm(abs(diag(S)) - abs(VA))" );
	ocheck( "eig vec", "norm(V' - VC)" );
    }

}

// =======================================================================================
// **                               J T E S T _ E I G E N                               **
// ======================================================================== END FILE =====
