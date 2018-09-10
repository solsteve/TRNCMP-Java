// ====================================================================== BEGIN FILE =====
// **                          L I N E A R R E G R E S S I O N                          **
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
 * @file LinearRegression.java
 *  Provides Interface and methods for linear regression.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2018-09-09
 */
// =======================================================================================

package org.trncmp.lib;

import org.hipparchus.linear.LUDecomposition;
import org.hipparchus.linear.RealMatrix;
import org.hipparchus.linear.Array2DRowRealMatrix;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class LinearRegression {
  // -------------------------------------------------------------------------------------

  private static final Logger logger = LogManager.getLogger();

  protected final RealMatrix w;
  
  // =====================================================================================
  /** @brief Constructor.
   *  @param Y output table (row,col) == (num_samp, num_out_vars).
   *  @param X input  table (row,col) == (num_samp, num_in_vars).
   *
   *  Generate the transformation matrices.
   */
  // -------------------------------------------------------------------------------------
  public LinearRegression( double[][] Y, double[][] X ) {
    // -----------------------------------------------------------------------------------
    int nRow = X.length;
    if ( Y.length != nRow ) {
      logger.error( "Input and output have different sample count" );
      System.exit(1);
    }

    RealMatrix y = new Array2DRowRealMatrix(Y);
    RealMatrix x = new Array2DRowRealMatrix(X);

    /** xT */
    RealMatrix xT = x.transpose();

    /** a = (xT * x ) */
    RealMatrix a  = xT.multiply(x);
    
    /** b = (xT * x )^-1 = a^-1 */
    RealMatrix b = new LUDecomposition(a).getSolver().getInverse();
    
    /** w = (xT * X)^-1 * (xT * y) = b * xT * y */
    w = b.multiply(xT).multiply(y);
    
  }

  // =====================================================================================
  /** @brief Solve forward expression.
   *  @param Y output table (row,col) == (num_samp, num_out_vars).
   *  @param X input  table (row,col) == (num_samp, num_in_vars).
   *
   *  Solver the forward expression Y = f(X)
   */
  // -------------------------------------------------------------------------------------
  public double[][] forward( double[][] X ) {
    // -----------------------------------------------------------------------------------
    RealMatrix x = new Array2DRowRealMatrix(X);

    return x.multiply(w).getData();
  }

  // =====================================================================================
  /** @brief Solve reverse expression.
   *  @param X output table (row,col) == (num_samp, num_out_vars).
   *  @param Y input  table (row,col) == (num_samp, num_in_vars).
   *
   *  Solver the inverse expression X = inv(f)(Y)
   */
  // -------------------------------------------------------------------------------------
  public void inverse( double[][] X, double[][] Y ) {
    // -----------------------------------------------------------------------------------
  }

} // end class LinearRegression

// =======================================================================================
// **                          L I N E A R R E G R E S S I O N                          **
// ======================================================================== END FILE =====

