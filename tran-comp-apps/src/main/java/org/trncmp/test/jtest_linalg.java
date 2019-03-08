// ====================================================================== BEGIN FILE =====
// **                              J T E S T _ L I N A L G                              **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2018, Stephen W. Soliday                                           **
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
 * @file jtest_linalg.java
 *  Provides Unit Test the linear Algebra functions.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2018-03-28
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.Dice;
import org.trncmp.lib.linear.*;
import org.trncmp.lib.Math2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class jtest_linalg extends octave_utest {
  // -------------------------------------------------------------------------------------

  public static final Logger logger = LogManager.getLogger();

  // =====================================================================================
  public static Matrix RandomPosDef( int n ) {
    // -----------------------------------------------------------------------------------
    Dice dd = Dice.getInstance();

    Matrix M  = new Matrix( n, n );
    Matrix Mt = new Matrix( n, n );

    M.set(0.0e0);
    Mt.set(0.0e0);

    for ( int r=0; r<n;   r++ ) {
      for ( int c=0; c<r+1; c++ ) {
        double x = dd.uniform()*1.0e1;
          M.A[r][c]  = x;
          Mt.A[c][r] = x;
      }
    }

    Matrix Mout = new Matrix( n, n );

    linalg.dot( Mout, M, Mt );

    return Mout;
  }
  
  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
    Dice dd = Dice.getInstance();

    dd.seed_set();

    final int n_row = dd.index(7) + 3;
    final int n_col = dd.index(7) + 3;
    final int n_mid = dd.index(9) + 5;

    oprint( "---- matrix vector ------------------------------------------------" );

    Mat2 m2 = new Mat2();
    Vec2 x2 = new Vec2();
    Vec2 y2 = new Vec2();

    rand( m2, dd );
    rand( x2, dd );
    rand( y2, dd );

    linalg.dot( y2, m2, x2 );

    oprint( "m", m2 );
    oprint( "x", x2 );
    oprint( "y", y2 );

    ocheck( "dot2(m,v)", "sum(sum( y - m*x ))" );
        
    rand( m2, dd );
    rand( x2, dd );
    rand( y2, dd );

    linalg.dot( y2, x2, m2 );

    oprint( "m", m2 );
    oprint( "x", x2 );
    oprint( "y", y2 );

    ocheck( "dot2(v,m)", "sum(sum( y - x'*m ))" );
        
    oprint( "---------------------------" );

    Mat3 m3 = new Mat3();
    Vec3 x3 = new Vec3();
    Vec3 y3 = new Vec3();

    rand( m3, dd );
    rand( x3, dd );
    rand( y3, dd );

    linalg.dot( y3, m3, x3 );

    oprint( "m", m3 );
    oprint( "x", x3 );
    oprint( "y", y3 );

    ocheck( "dot3(m,v)", "sum(sum( y - m*x ))" );
        
    rand( m3, dd );
    rand( x3, dd );
    rand( y3, dd );

    linalg.dot( y3, x3, m3 );

    oprint( "m", m3 );
    oprint( "x", x3 );
    oprint( "y", y3 );

    ocheck( "dot3(v,m)", "sum(sum( y - x'*m ))" );
        
    oprint( "---------------------------" );

    Mat4 m4 = new Mat4();
    Vec4 x4 = new Vec4();
    Vec4 y4 = new Vec4();

    rand( m4, dd );
    rand( x4, dd );
    rand( y4, dd );

    linalg.dot( y4, m4, x4 );

    oprint( "m", m4 );
    oprint( "x", x4 );
    oprint( "y", y4 );

    ocheck( "dot4(m,v)", "sum(sum( y - m*x ))" );
        
    rand( m4, dd );
    rand( x4, dd );
    rand( y4, dd );

    linalg.dot( y4, x4, m4 );

    oprint( "m", m4 );
    oprint( "x", x4 );
    oprint( "y", y4 );

    ocheck( "dot4(v,m)", "sum(sum( y - x'*m ))" );
        
    oprint( "---------------------------" );

    Matrix Mrc = new Matrix( n_row, n_col );
    Vector Vr  = new Vector( n_row );
    Vector Vc  = new Vector( n_col );

    rand( Mrc, dd );
    rand( Vr,  dd );
    rand( Vc,  dd );

    linalg.dot( Vr, Mrc, Vc );

    oprint( "m", Mrc );
    oprint( "x", Vc );
    oprint( "y", Vr );

    ocheck( "dot(m,v)", "sum(sum( y - m*x ))" );

    rand( Mrc, dd );
    rand( Vr,  dd );
    rand( Vc,  dd );

    linalg.dot( Vc, Vr, Mrc );

    oprint( "m", Mrc );
    oprint( "x", Vr );
    oprint( "y", Vc );

    ocheck( "dot(v,m)", "sum(sum( y - x'*m ))" );

    oprint( "---- matrix vector ------------------------------------------------" );

    Mat2 A2 = new Mat2();
    Mat2 B2 = new Mat2();
    Mat2 C2 = new Mat2();

    rand( A2, dd );
    rand( B2, dd );
    rand( C2, dd );

    linalg.dot( C2, A2, B2 );

    oprint( "a", A2 );
    oprint( "b", B2 );
    oprint( "c", C2 );

    ocheck( "dot2(m,m)", "sum(sum( c - a*b ))" );

    Mat3 A3 = new Mat3();
    Mat3 B3 = new Mat3();
    Mat3 C3 = new Mat3();

    rand( A3, dd );
    rand( B3, dd );
    rand( C3, dd );

    linalg.dot( C3, A3, B3 );

    oprint( "a", A3 );
    oprint( "b", B3 );
    oprint( "c", C3 );

    ocheck( "dot3(m,m)", "sum(sum( c - a*b ))" );

    Mat4 A4 = new Mat4();
    Mat4 B4 = new Mat4();
    Mat4 C4 = new Mat4();

    rand( A4, dd );
    rand( B4, dd );
    rand( C4, dd );

    linalg.dot( C4, A4, B4 );

    oprint( "a", A4 );
    oprint( "b", B4 );
    oprint( "c", C4 );

    ocheck( "dot4(m,m)", "sum(sum( c - a*b ))" );

    Matrix A = new Matrix( n_row, n_mid );
    Matrix B = new Matrix( n_mid, n_col );
    Matrix C = new Matrix( n_row, n_col );

    rand( A, dd );
    rand( B, dd );
    rand( C, dd );

    linalg.dot( C, A, B );

    oprint( "a", A );
    oprint( "b", B );
    oprint( "c", C );

    ocheck( "dot(m,m)", "sum(sum( c - a*b ))" );

    oprint( "---- Mahalanobis ------------------------------------------------" );

    Matrix S  = new Matrix(2*n_mid,2*n_mid);
    Vector mu = new Vector(2*n_mid);
    Vector x  = new Vector(2*n_mid);

    rand( S,  dd );
    rand( mu, dd );
    rand( x,  dd );
    
    double d = linalg.Mahalanobis( x, mu, S );

    oprint( "d", d );
    oprint( "x", x );
    oprint( "m", mu );
    oprint( "S", S );
    
    ocheck( "Mahal(v,mu,S)", "d - (x-m)'*S*(x-m)" );

    oprint( "---- Cholesky ------------------------------------------------" );

    Matrix PD = RandomPosDef(n_mid);
    Matrix RF = new Matrix(n_mid,n_mid);
    Matrix UT = new Matrix(n_mid,n_mid);

    Matrix LT = linalg.cholesky( PD, 1.0e-8 );
    UT.transpose(LT);
    linalg.dot( RF, LT, UT );

    double dif = PD.sumsq( RF );
    

    oprint( "P", PD );
    oprint( "L", LT );
    oprint( "U", UT );
    oprint( "R", RF );
    oprint( "d", dif );

    ocheck( "Cholesky", "sum(sum( U - chol(P) ) )" );
    ocheck( "        ", "sum(sum( R - P ) )" );

    
  }

}

// =======================================================================================
// **                              J T E S T _ V E C T O R                              **
// ======================================================================== END FILE =====

