// ====================================================================== BEGIN FILE =====
// **                             J T E S T _ H A R D M A T                             **
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
 *  Provides Octave test of 2x2, 3x3 and 4x4 DET, INV, and EIGEN
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2018-03-28
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.Dice;
import org.trncmp.lib.Math2;

// =======================================================================================
public class jtest_hardmat extends octave_utest {
  // -------------------------------------------------------------------------------------
  static Dice dd = Dice.getInstance();

  // =====================================================================================
  static double RandValue() {
    // -----------------------------------------------------------------------------------
    return Math.floor(200.0*dd.uniform())/100.0 - 1.0;
  }
  

  // =====================================================================================
  public static double[][] RandomPosDef( int n ) {
    // -----------------------------------------------------------------------------------
    double[][] M  = new double[n][n];
    double[][] Mt = new double[n][n];

    for ( int r=0; r<n; r++ ) {
      for ( int c=0; c<n; c++ ) {
        M[r][c] = 0.0e0;
        Mt[r][c] = 0.0e0;
      }
    }
    
    for ( int r=0; r<n;   r++ ) {
      for ( int c=0; c<r+1; c++ ) {
        double x = RandValue();
        M[r][c]  = x;
        Mt[c][r] = x;
      }
    }

    double[][] out  = new double[n][n];

    for ( int r=0; r<n; r++ ) {
      for ( int c=0; c<n; c++ ) {
        double sum = 0.0e0;
        for ( int k=0; k<n; k++ ) {
          sum += ( M[r][k] * Mt[k][c] );
        }
        out[r][c] = sum;
      }
    }

    return out;
  }
  

  // =====================================================================================
  public static double[][] RandomSym( int n ) {
    // -----------------------------------------------------------------------------------
    double[][] M  = new double[n][n];

    for ( int r=0; r<n; r++ ) {
      for ( int c=r; c<n; c++ ) {
        M[r][c] = RandValue();
        M[c][r] = M[r][c];
      }
    }

    return M;
  }

  // =====================================================================================
  public static double[][] Random( int n ) {
    // -----------------------------------------------------------------------------------
    double[][] M  = new double[n][n];

    for ( int r=0; r<n; r++ ) {
      for ( int c=0; c<n; c++ ) {
        M[r][c] = RandValue();
      }
    }

    return M;
  }

  // =====================================================================================
  public static double testEigen( double[][] A, double[] V, double L, int n ) {
    // -----------------------------------------------------------------------------------
    double err = 0.0;
    for ( int r=0; r<n; r++ ) {
      double s = -L * V[r];
      for ( int c=0; c<n; c++ ) {
        s += ( A[r][c] * V[c] );
      }
      err += ( s*s );
    }
    return err;
  }


  // =====================================================================================
  public static void Test2x2() {
    // -----------------------------------------------------------------------------------

    {
      double[][] A = Random(2);
      double[][] B = Random(2);
      double[][] C = Random(2);

      Math2.dot_2x2( C, A, B );
      
      oprint( "A", A );
      oprint( "B", B );
      oprint( "C", C );
      
      ocheck( "DOT(2,2)", "norm(C - A*B)" );
    }
    
    {
      double[][] A = Random(2);
      double     d = Math2.det_2x2( A );

      oprint( "A", A );
      oprint( "d", d );
      
      ocheck( "DET(2,2)", "d - det(A)" );
    }

    {
      double[][] A = Random(2);
      double[][] B = Random(2);
      double[][] C = Random(2);
      double[][] D = Random(2);
      
      Math2.inv_2x2( B, A );
      Math2.dot_2x2( C, A, B );
      Math2.dot_2x2( D, B, A );
      
      oprint( "A", A );
      oprint( "B", B );
      oprint( "C", 2.0e0 - Math2.sumsq(C) );
      oprint( "D", 2.0e0 - Math2.sumsq(D) );

      ocheck( "INV(2,2)", "1 - norm(A*B)" );
      ocheck( "   (2,2)", "C" );
      ocheck( "   (2,2)", "D" );
    }

    while (true) {
      double[][] A  = Random(2);
      double[]   V1 = { 0.0, 0.0 };
      double[]   V2 = { 0.0, 0.0 };
      double[]   ev = { 0.0, 0.0 };

      if ( Math2.eigen_2x2( ev, V1, V2, A ) ) {
        double k1 = testEigen( A, V1, ev[0], 2 );
        double k2 = testEigen( A, V2, ev[1], 2 );
        System.err.println( "Eigen test 2x2 = ( "+k1+" "+k2+" )" );
        break;
      }

    }

  }


  // =====================================================================================
  public static void Test3x3() {
    // -----------------------------------------------------------------------------------

    {
      double[][] A = Random(3);
      double[][] B = Random(3);
      double[][] C = Random(3);

      Math2.dot_3x3( C, A, B );
      
      oprint( "A", A );
      oprint( "B", B );
      oprint( "C", C );
      
      ocheck( "DOT(3,3)", "norm(C - A*B)" );
    }
    
    {
      double[][] A = Random(3);
      double     d = Math2.det_3x3( A );

      oprint( "A", A );
      oprint( "d", d );
      
      ocheck( "DET(3,3)", "d - det(A)" );
    }

    
    {
      double[][] A = Random(3);
      double[][] B = Random(3);
      double[][] C = Random(3);
      double[][] D = Random(3);
      
      Math2.inv_3x3( B, A );
      Math2.dot_3x3( C, A, B );
      Math2.dot_3x3( D, B, A );
      
      oprint( "A", A );
      oprint( "B", B );
      oprint( "C", 3.0e0 - Math2.sumsq(C) );
      oprint( "D", 3.0e0 - Math2.sumsq(D) );

      ocheck( "INV(3,3)", "1 - norm(A*B)" );
      ocheck( "   (3,3)", "C" );
      ocheck( "   (3,3)", "D" );
    }

    while (true) {
      double[][] A  = RandomSym(3);
      double[]   V1 = { 0.0, 0.0 , 0.0};
      double[]   V2 = { 0.0, 0.0, 0.0 };
      double[]   V3 = { 0.0, 0.0, 0.0 };
      double[]   ev = { 0.0, 0.0, 0.0 };

      if ( Math2.eigen_3x3( ev, V1, V2, V3, A ) ) {
        double k1 = testEigen( A, V1, ev[0], 3 );
        double k2 = testEigen( A, V2, ev[1], 3 );
        double k3 = testEigen( A, V3, ev[2], 3 );
        System.err.println( "Eigen test 3x3 = ( "+k1+" "+k2+" "+k3+" )" );
        break;
      }

    }

  }


  // =====================================================================================
  public static void Test4x4() {
    // -----------------------------------------------------------------------------------

    {
      double[][] A = Random(4);
      double[][] B = Random(4);
      double[][] C = Random(4);

      Math2.dot_4x4( C, A, B );
      
      oprint( "A", A );
      oprint( "B", B );
      oprint( "C", C );
      
      ocheck( "DOT(4,4)", "norm(C - A*B)" );
    }
    
    {
      double[][] A = Random(4);
      double     d = Math2.det_4x4( A );

      oprint( "A", A );
      oprint( "d", d );
      
      ocheck( "DET(4,4)", "d - det(A)" );
    }

        {
      double[][] A = Random(4);
      double[][] B = Random(4);
      double[][] C = Random(4);
      double[][] D = Random(4);
      
      Math2.inv_4x4( B, A );
      Math2.dot_4x4( C, A, B );
      Math2.dot_4x4( D, B, A );
      
      oprint( "A", A );
      oprint( "B", B );
      oprint( "C", 4.0e0 - Math2.sumsq(C) );
      oprint( "D", 4.0e0 - Math2.sumsq(D) );

      ocheck( "INV(4,4)", "1 - norm(A*B)" );
      ocheck( "   (4,4)", "C" );
      ocheck( "   (4,4)", "D" );
    }

    while (true) {
      double[][] A  = RandomPosDef(4);
      double[]   V1 = { 0.0, 0.0, 0.0, 0.0 };
      double[]   V2 = { 0.0, 0.0, 0.0, 0.0 };
      double[]   V3 = { 0.0, 0.0, 0.0, 0.0 };
      double[]   V4 = { 0.0, 0.0, 0.0, 0.0 };
      double[]   ev = { 0.0, 0.0, 0.0, 0.0 };

      if ( Math2.eigen_4x4( ev, V1, V2, V3, V4, A ) ) {
        double k1 = testEigen( A, V1, ev[0], 4 );
        double k2 = testEigen( A, V2, ev[1], 4 );
        double k3 = testEigen( A, V3, ev[2], 4 );
        double k4 = testEigen( A, V4, ev[3], 4 );
        System.err.println( "Eigen test 4x4 = ( "+k1+" "+k2+" "+k3+" "+k4+" )" );
        break;
      }

    }


  }






  
  
  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------

    dd.seed_set();
    
    Test2x2();
    Test3x3();
    Test4x4();
  }


    
} // end class jtest_hardmat


// =======================================================================================
// **                             J T E S T _ H A R D M A T                             **
// ======================================================================== END FILE =====
