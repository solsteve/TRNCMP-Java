// ====================================================================== BEGIN FILE =====
// **                                 J T E S T _ F F T                                 **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2019, Stephen W. Soliday                                           **
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
 * @file jtest_fft.java
 *  Perform a system test on the FFT class.
 *
 * @author Stephen W. Soliday
 * @date 2019-01-24
 */
// =======================================================================================

package org.trncmp.test;

import  org.trncmp.lib.FFT;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
// ---------------------------------------------------------------------------------------
public class jtest_fft {
  // -------------------------------------------------------------------------------------

  // =====================================================================================
  public void Test01() {
    // -----------------------------------------------------------------------------------
  
    final double[][] TV = FFT.testVectors();

    int N = TV.length;
  
    double[][] X = new double[2][N];

    for ( int i=0; i<N; i++ ) {
      X[0][i] = TV[i][1];
      X[1][i] = 0.0e0;
    }

    FFT.forward( X );

    double mse_r = 0.0e0;
    double mse_i = 0.0e0;

    for ( int i=0; i<N; i++ ) {
      double dr = X[0][i] - TV[i][2];
      double di = X[1][i] - TV[i][3];
      mse_r += (dr*dr);
      mse_i += (di*di);
    }

    System.out.format( "Forward MSE = (%12.5e %12.5e)\n", mse_r, mse_i );

    FFT.inverse( X );
  
    mse_r = 0.0e0;

    for ( int i=0; i<N; i++ ) {
      double dr = X[0][i] - TV[i][1];
      mse_r += (dr*dr);
    }

    System.out.format( "Inverse MSE =  %12.5e\n", mse_r );









    
  }


  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------

    jtest_fft T = new jtest_fft();
    
    T.Test01();
      
    System.exit(0);
  }

  
} // end class jtest_fft


// =======================================================================================
// **                                 J T E S T _ F F T                                 **
// ======================================================================== END FILE =====
