// ====================================================================== BEGIN FILE =====
// **                              J T E S T _ M A T R I X                              **
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
import org.trncmp.lib.linear.Matrix;
import org.trncmp.lib.Math2;

// =======================================================================================
public class jtest_matrix extends octave_utest {
    // -----------------------------------------------------------------------------------

    // ===================================================================================
    public static void main(String[] args) {
	// -------------------------------------------------------------------------------

	Dice dd = Dice.getInstance();

	dd.seed_set();
	
	final int n_row = dd.index(4) + 3;
	final int n_col = dd.index(4) + 3;

	oprint( "---- matrix ("+n_row+","+n_col+") -------------------------------------" );

	Matrix a = new Matrix( n_row, n_col );
	Matrix b = new Matrix( n_row, n_col );
	Matrix c = new Matrix( n_row, n_col );

	rand( a, dd );
	rand( b, dd );

	oprint( "a", a );
	oprint( "b", b );

	oprint( "k", a.sumsq(b) );
	oprint( "m", a.sumsq()  );

	ocheck( "sumsq", "k - sum(sum((a-b).^2))" );
	ocheck( "sumsq", "m - sum(sum(a.^2))" );
    
	// -----------------------------

	rand( a, dd );

	a.set( 3.14 );

	oprint( "a", a );

	ocheck( "set(mat,val)", "norm( a - 3.14*ones(size(a)) )" );

	// -----------------------------

	rand( a, dd );

	a.ident();
	oprint( "a", a );

	ocheck( "ident(mat)", "norm(eye(size(a)) - a)" );

	// -----------------------------

	rand( a, dd );
	rand( b, dd );

	b.copy( a );

	oprint( "a", a );
	oprint( "b", b );

	ocheck( "copy(mat,mat)", "norm( a - b )" );

	// -----------------------------

	Matrix d = new Matrix( n_col, n_row );

	rand( a, dd );
	rand( d, dd );
  
	d.transpose( a );

	oprint( "a", a );
	oprint( "d", d );

	ocheck( "transpose(mat,mat)", "norm(d - a')" );

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

	double[] testA = new double[ (n_row*n_col) + 7 ];

	rand( a, dd );
	rand( b, dd );
	rand( testA, dd );

	int off = 3;

	off = a.store( testA, off );

	off = off - (n_row*n_col);

	b.load( testA, off );
	
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

	ocheck( "swap(mat,mat)", "sum(sum(a1-b2)) + sum(sum(a2-b1))" );

	// -----------------------------

	Matrix e2 = new Matrix(2,2);
	Matrix e3 = new Matrix(3,3);
	Matrix e4 = new Matrix(4,4);
	Matrix eN = new Matrix(7,7);

	Matrix f2 = new Matrix(2,2);
	Matrix f3 = new Matrix(3,3);
	Matrix f4 = new Matrix(4,4);
	Matrix fN = new Matrix(7,7);

	// -----------------------------

	 rand( e2, dd );

	oprint( "e", e2 );
	oprint( "k", e2.det() );
	ocheck( "det(mat2)", "k - det(e)" );

	// -----------------------------

	 rand( e3, dd );

	oprint( "e", e3 );
	oprint( "k", e3.det() );
	ocheck( "det(mat3)", "k - det(e)" );

	// -----------------------------

	 rand( e4, dd );

	oprint( "e", e4 );
	oprint( "k", e4.det() );
	ocheck( "det(mat4)", "k - det(e)" );

	// -----------------------------

	 rand( eN, dd );

	oprint( "e", eN );
	oprint( "k", eN.det() );
	ocheck( "det(mat7)", "k - det(e)" );

	// -----------------------------

	double dt = 0.0e0;
	
	rand( e2, dd );
	rand( f2, dd );

	oprint( "e", e2 );
	dt = f2.invert(e2);
	oprint( "f", f2 );
	oprint( "d", dt );

	ocheck( "invert(mat2,mat2)", "norm(f - inv(e))" );
	ocheck( "det(recoverd)",   "(d - det(e))" );

	// -----------------------------

	rand( e3, dd );
	rand( f3, dd );

	oprint( "e", e3 );
	dt = f3.invert(e3);
	oprint( "f", f3 );
	oprint( "d", dt );

	ocheck( "invert(mat3,mat3)", "norm(f - inv(e))" );
	ocheck( "det(recoverd)",   "(d - det(e))" );

	// -----------------------------

	rand( e4, dd );
	rand( f4, dd );

	oprint( "e", e4 );
	dt = f4.invert(e4);
	oprint( "f", f4 );
	oprint( "d", dt );

	ocheck( "invert(mat4,mat4)", "norm(f - inv(e))" );
	ocheck( "det(recoverd)",   "(d - det(e))" );

	// -----------------------------

	rand( eN, dd );
	rand( fN, dd );

	oprint( "e", eN );
	dt = fN.invert(eN);
	oprint( "f", fN );
	oprint( "d", dt );

	ocheck( "invert(mat7,mat7)", "norm(f - inv(e))" );
	ocheck( "det(recoverd)",   "(d - det(e))" );

    }

}

// =======================================================================================
// **                              J T E S T _ M A T R I X                              **
// ======================================================================== END FILE =====

