// ====================================================================== BEGIN FILE =====
// **                                J T E S T _ M A T 4                                **
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
 * @file jtest_mat4.java
 *  Provides Unit Test the Mat4 functions.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-09-10
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.Dice;
import org.trncmp.lib.linear.Mat4;
import org.trncmp.lib.Math2;
import org.trncmp.lib.array;

// =======================================================================================
public class jtest_mat4 extends octave_utest {
    // -----------------------------------------------------------------------------------

    // ===================================================================================
    public static void main(String[] args) {
	// -------------------------------------------------------------------------------

	Dice dd = Dice.getInstance();

	oprint( "---- mat 4 ------------------------------------------------" );

	Mat4 a = new Mat4();
	Mat4 b = new Mat4();

	rand( a, dd );
	rand( b, dd );

	oprint( "a", a );
	oprint( "b", b );

	oprint( "k", a.sumsq(b) );
	oprint( "m", a.sumsq()  );

	ocheck( "sumsq", "k - sum(sum((a-b).^2))" );
	ocheck( "sumsq", "m - sum(sum(a.^2))" );
   
 	// -----------------------------

	double[] testA = new double[16];
	double[] testB = new double[16];

	rand( testA, dd );

	Mat4 c = new Mat4( testA );

	c.store( testB );

	oprint( "k", Math2.sumsq( testA, testB ) );

	ocheck( "mat4(q[])", "k" );

 	// -----------------------------

	double[][] testA_44 = new double[4][4];
	double[][] testB_44 = new double[4][4];

	rand( testA_44, dd );
	copy( testA, testA_44 );

	Mat4 d = new Mat4( testA_44 );

	d.store( testB );

	oprint( "k", Math2.sumsq( testA, testB ) );

	ocheck( "mat4(q[][])", "k" );

 	// -----------------------------

	rand( a, dd );
	Mat4 e = new Mat4( a );

	oprint( "a", a );
	oprint( "e", e );

	ocheck( "mat4(mat)", "norm(a-e)" );

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

	ocheck( "add(mat)", "norm( c - (a .+ b) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "b", b );
	a.add( b, 3.14 );
	oprint( "a", a );

	ocheck( "add(mat,scl)", "norm( a - (b .+ 3.14) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	rand( c, dd );
	oprint( "b", b );
	oprint( "c", c );
	a.add( b, c );
	oprint( "a", a );

	ocheck( "add(mat,mat)", "norm( a - (b .+ c) )" );

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

	ocheck( "sub(mat)", "norm( c - (a .- b) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "b", b );
	a.sub( b, 3.14 );
	oprint( "a", a );

	ocheck( "sub(mat,scl)", "norm( a - (b .- 3.14) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	rand( c, dd );
	oprint( "b", b );
	oprint( "c", c );
	a.sub( b, c );
	oprint( "a", a );

	ocheck( "sub(mat,mat)", "norm( a - (b .- c) )" );

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

	ocheck( "pmul(mat)", "norm( c - (a .* b) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "b", b );
	a.pmul( b, 3.14 );
	oprint( "a", a );

	ocheck( "pmul(mat,scl)", "norm( a - (b .* 3.14) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	rand( c, dd );
	oprint( "b", b );
	oprint( "c", c );
	a.pmul( b, c );
	oprint( "a", a );

	ocheck( "pmul(mat,mat)", "norm( a - (b .* c) )" );

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

	ocheck( "pdiv(mat)", "norm( c - (a ./ b) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "b", b );
	a.pdiv( b, 3.14 );
	oprint( "a", a );

	ocheck( "pdiv(mat,scl)", "norm( a - (b ./ 3.14) )" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	rand( c, dd );
	oprint( "b", b );
	oprint( "c", c );
	a.pdiv( b, c );
	oprint( "a", a );

	ocheck( "pdiv(mat,mat)", "norm( a - (b ./ c) )" );


	// ==========================================================


	rand( a, dd );
	rand( b, dd );
	rand( testA, dd );

	a.store( testA );
	b.load( testA );

	oprint( "k", a.sumsq(b) );

	ocheck( "load/store", "k" );
	
	// -----------------------------

	rand( a, dd );

	oprint( "a", a );
	oprint( "k", a.det() );
	
	ocheck( "det", "k - det(a)" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );
	oprint( "a1", a );
	oprint( "b1", b );

	a.swap( b );
	oprint( "a2", a );
	oprint( "b2", b );

	ocheck( "swap", "sum(sum(a1-b2)) + sum(sum(a2-b1))" );

	// -----------------------------

	rand( a, dd );

	a.ident();
	oprint( "a", a );

	ocheck( "ident", "norm(eye(size(a)) - a)" );

	// -----------------------------

	rand( a, dd );

	oprint( "a", a );
	a.transpose();
	oprint( "b", a );

	ocheck( "transpose()", "norm(b - a')" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );

	b.transpose(a);
	oprint( "a", a );
	oprint( "b", b );

	ocheck( "transpose(mat)", "norm(b - a')" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );

	b.invert(a);

	oprint( "a", a );
	oprint( "b", b );

	ocheck( "invert(mat)", "norm(b - inv(a))" );
    }

}

// =======================================================================================
// **                                J T E S T _M A T 4                                **
// ======================================================================== END FILE =====

