// ====================================================================== BEGIN FILE =====
// **                               J T E S T _ T A B L E                               **
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
 * @file jtest_table.java
 *  Provides Unit Test the Table class.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date   2017-09-25
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.*;

// =======================================================================================
public class jtest_table {
    // -----------------------------------------------------------------------------------

    // ===================================================================================
    public static void Test01( Dice dd ) {
	// -------------------------------------------------------------------------------

	final int n_row = 3*dd.index( 20 ) + 40;
	final int n_col = 2*dd.index( 5 ) + 3;

	Table T1 = new Table( n_row, n_col );
	Table T2 = new Table();

	for ( int r=0; r<n_row; r++ ) {
	    for ( int c=0; c<n_col; c++ ) {
		T1.set( r, c, 1000.0 * dd.normal() );
	    }
	}

	Table.write_ascii( T1, "/tmp/test.dat" );
	Table.read_ascii( T2,  "/tmp/test.dat" );

	System.out.println( "MSE = " + T1.sumsq( T2 ) );
    }

    // ===================================================================================
    public static void Test02( Dice dd ) {
	// -------------------------------------------------------------------------------

	final int n_row  = 3*dd.index( 20 ) + 40;
	final int n_col1 = 2*dd.index( 5 ) + 3;
	final int n_col2 = 2*dd.index( 5 ) + 3;

	Table aT1 = new Table( n_row, n_col1 );
	Table bT1 = new Table( n_row, n_col2 );
	Table aT2 = new Table();
	Table bT2 = new Table();

	for ( int r=0; r<n_row; r++ ) {
	    for ( int c=0; c<n_col1; c++ ) {
		aT1.set( r, c, 1000.0 * dd.normal() );
	    }
	    for ( int c=0; c<n_col2; c++ ) {
		bT1.set( r, c, 1000.0 * dd.normal() );
	    }
	}

	Table.write_ascii( aT1, bT1, "/tmp/test.dat" );
	Table.read_ascii(  aT2, bT2, "/tmp/test.dat" );

	System.out.println( "MSE = " + aT1.sumsq( aT2 ) + " "  + bT1.sumsq( bT2 ) );
    }

    
    // ===================================================================================
    public static double f( int i, int r, int c ) {
	// -------------------------------------------------------------------------------
	return ((double) i) + ((double)(r+1)) + (((double)(c+1))/100.0);
    }
    
    // ===================================================================================
    public static void Test03( ) {
	// -------------------------------------------------------------------------------
	
	Table T1 = new Table( 8, 4 );
	Table T2 = new Table( 8, 3 );

	for ( int r=0; r<8; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		T1.set( r, c, f(100, r, c) );
	    }
	    for ( int c=0; c<3; c++ ) {
		T2.set( r, c, f(200, r, c) );
	    }
	}

	Table.write_ascii( T1, "./test1.dat", "%7.2f" );
	Table.write_ascii( T1, T2, "./test2.dat", "%7.2f" );

	try {
	    Runtime.getRuntime().exec( "cat ./test1.dat" ).waitFor();
	    Runtime.getRuntime().exec( "cat ./test2.dat" ).waitFor();
	} catch(java.io.IOException e1 ) {
	    e1.printStackTrace( System.err );
	} catch(java.lang.InterruptedException e2 ) {
	    e2.printStackTrace( System.err );
	}
    }



    // ===================================================================================
    public static void main(String[] args) {
	// -------------------------------------------------------------------------------

	Dice dd = Dice.getInstance();
	dd.seed_set();

	Test01( dd );
	Test02( dd );
	Test03( );
    }
}


// =======================================================================================
// **                               J T E S T _ T A B L E                               **
// ======================================================================== END FILE =====

