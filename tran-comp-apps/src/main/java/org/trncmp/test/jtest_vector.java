// ====================================================================== BEGIN FILE =====
// **                              J T E S T _ V E C T O R                              **
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
 * @file jtest_vector.java
 *  Provides Unit Test the Vector functions.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-09-12
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.Dice;
import org.trncmp.lib.linear.Vector;
import org.trncmp.lib.Math2;

// =======================================================================================
public class jtest_vector extends octave_utest {
    // -----------------------------------------------------------------------------------

    // ===================================================================================
    public static void main(String[] args) {
	// -------------------------------------------------------------------------------

	Dice dd = Dice.getInstance();

	dd.seed_set();

	  final int n_row = dd.index(4) + 3;


	oprint( "---- vector ------------------------------------------------" );

	Vector a = new Vector(n_row);
	Vector b = new Vector(n_row);

	rand( a, dd );
	rand( b, dd );

	oprint( "a", a );
	oprint( "b", b );

	oprint( "k", a.sumsq(b) );
	oprint( "m", a.sumsq()  );

	ocheck( "sumsq", "k - (a-b)'*(a-b)" );
	ocheck( "sumsq", "m - a'*a" );
    
	// -----------------------------

	double[] testA = new double[n_row];
	double[] testB = new double[n_row];

	rand( testA, dd );

	Vector c = new Vector( testA );

	c.store( testB );

	oprint( "k", Math2.sumsq( testA, testB ) );

	ocheck( "vector(q[])", "k" );

	// -----------------------------

	rand( a, dd );
	Vector e = new Vector( a );

	oprint( "a", a );
	oprint( "e", e );

	ocheck( "vector(vec)", "norm(a-e)" );

	// -----------------------------

	rand( a, dd );

	a.set( 3.14 );

	oprint( "a", a );

	ocheck( "set", "norm( a - 3.14*ones(size(a)) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );

	b.copy( a );

	oprint( "a", a );
	oprint( "b", b );

	ocheck( "copy", "norm( a - b )" );

	// -----------------------------

	rand( a, dd );

	oprint( "a", a );

	oprint( "k", a.mag() );

	ocheck( "mag", "k - sqrt( a'*a )" );

	// -----------------------------

	rand( a, dd );

	oprint( "a", a );

	a.norm();

	oprint( "b", a );

	ocheck( "norm()", "norm( b - a./sqrt(a'*a) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );

	b.norm(a);

	oprint( "a", a );
	oprint( "b", b );

	ocheck( "norm(vec)", "norm( b - a./sqrt(a'*a) )" );

	// ==========================================================

	rand( a, dd );
	oprint( "a", a );
	a.add( 2.78 );
	oprint( "b", a );

	ocheck( "add(scl)", "norm( b - (a .+ 2.78) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "a", a );
	oprint( "b", b );
	a.add(b);
	oprint( "c", a );

	ocheck( "add(vec)", "norm( c - (a .+ b) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "b", b );
	a.add( b, 3.14 );
	oprint( "a", a );

	ocheck( "add(vec,scl)", "norm( a - (b .+ 3.14) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	rand( c, dd );
	oprint( "b", b );
	oprint( "c", c );
	a.add( b, c );
	oprint( "a", a );

	ocheck( "add(vec,vec)", "norm( a - (b .+ c) )" );

	// ==========================================================

	rand( a, dd );
	oprint( "a", a );
	a.sub( 2.78 );
	oprint( "b", a );

	ocheck( "sub(scl)", "norm( b - (a .- 2.78) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "a", a );
	oprint( "b", b );
	a.sub(b);
	oprint( "c", a );

	ocheck( "sub(vec)", "norm( c - (a .- b) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "b", b );
	a.sub( b, 3.14 );
	oprint( "a", a );

	ocheck( "sub(vec,scl)", "norm( a - (b .- 3.14) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	rand( c, dd );
	oprint( "b", b );
	oprint( "c", c );
	a.sub( b, c );
	oprint( "a", a );

	ocheck( "sub(vec,vec)", "norm( a - (b .- c) )" );

	// ==========================================================

	rand( a, dd );
	oprint( "a", a );
	a.pmul( 2.78 );
	oprint( "b", a );

	ocheck( "pmul(scl)", "norm( b - (a .* 2.78) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "a", a );
	oprint( "b", b );
	a.pmul(b);
	oprint( "c", a );

	ocheck( "pmul(vec)", "norm( c - (a .* b) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "b", b );
	a.pmul( b, 3.14 );
	oprint( "a", a );

	ocheck( "pmul(vec,scl)", "norm( a - (b .* 3.14) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	rand( c, dd );
	oprint( "b", b );
	oprint( "c", c );
	a.pmul( b, c );
	oprint( "a", a );

	ocheck( "pmul(vec,vec)", "norm( a - (b .* c) )" );

	// ==========================================================

	rand( a, dd );
	oprint( "a", a );
	a.pdiv( 2.78 );
	oprint( "b", a );

	ocheck( "pdiv(scl)", "norm( b - (a ./ 2.78) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "a", a );
	oprint( "b", b );
	a.pdiv(b);
	oprint( "c", a );

	ocheck( "pdiv(vec)", "norm( c - (a ./ b) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "b", b );
	a.pdiv( b, 3.14 );
	oprint( "a", a );

	ocheck( "pdiv(vec,scl)", "norm( a - (b ./ 3.14) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	rand( c, dd );
	oprint( "b", b );
	oprint( "c", c );
	a.pdiv( b, c );
	oprint( "a", a );

	ocheck( "pdiv(vec,vec)", "norm( a - (b ./ c) )" );


	// ==========================================================

	rand( a, dd );
	oprint( "a", a );
	oprint( "k", a.dot() );
	ocheck( "dot()", "k - (a'*a)" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "a", a );
	oprint( "b", b );
	oprint( "k", a.dot(b) );
	ocheck( "dot(vec)", "k - (a'*b)" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	rand( testA, dd );

	a.store( testA );
	b.load( testA );

	oprint( "k", a.sumsq(b) );

	ocheck( "load/store", "k" );
	
	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "a1", a );
	oprint( "b1", b );

	a.swap( b );
	oprint( "a2", a );
	oprint( "b2", b );

	ocheck( "swap", "sum(a1-b2) + sum(a2-b1)" );

    }

}

// =======================================================================================
// **                              J T E S T _ V E C T O R                              **
// ======================================================================== END FILE =====

