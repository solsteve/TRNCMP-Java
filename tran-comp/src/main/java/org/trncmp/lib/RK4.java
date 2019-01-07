// ====================================================================== BEGIN FILE =====
// **                                       R K 4                                       **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2006, Stephen W. Soliday                                           **
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
 * @brief   Fourth order Runge-Kutta Numerical Integrator.
 * @file    RK4.java
 *
 * @details Provides the interface and procedures for an abstract integrator.
 *
 * @author  Stephen W. Soliday
 * @date    2006-11-24
 */
// =======================================================================================

package org.trncmp.lib;

// =======================================================================================
/** Runge-Kutta Numerical Integrator. 
 *  Provides the interface for the Runge-Kutta numerical integrator.
 *  The user must supply the implementation for DIFEQ and CHECK.
 */
// ---------------------------------------------------------------------------------------
abstract public class RK4 {
  // -------------------------------------------------------------------------------------
  protected int      dim;  /** number of coupled first order equations */
  protected double[] A;    /** stage one state vector                  */
  protected double[] B;    /** stage two state vector                  */
  protected double[] C;    /** stage three state vector                */
  protected double[] D;    /** stage four state vector                 */
  protected double[] W;    /** DIFEQ input vector                      */

  // =====================================================================================
  /** Check State.
   *
   *  @param Q current state vector.
   *  @param t current time.
   *  @param P parameter vector.
   *  @return 0=continue, non-zero=stop
   */
  // -------------------------------------------------------------------------------------
  abstract public int  CHECK( double[] Q, double t, double[] P );
  // -------------------------------------------------------------------------------------


  
  // =====================================================================================
  /** Differential equations.
   *
   *  @param Qd first time derivative of the current state vector.
   *  @param Q  current state vector.
   *  @param t  current time.
   *  @param P  parameter vector.
   */
  // -------------------------------------------------------------------------------------
  abstract public void DIFEQ( double[] Qd, double[] Q, double t, double[] P );
  // -------------------------------------------------------------------------------------

  // =====================================================================================
  /** Constructor.
   *  Allocate work vectors for integration.
   *
   * @param n number of coupled first-order differential equations.
   */
  // -------------------------------------------------------------------------------------
  public RK4( int n ) {
    // -----------------------------------------------------------------------------------
    dim = n;
    A = new double[dim];
    B = new double[dim];
    C = new double[dim];
    D = new double[dim];
    W = new double[dim];
  }

  // =====================================================================================
  /** Fourth order Runge-Kutta.
   *  Provides the implementation for a fourth order Runge-Kutta numerical integrator with 
   *  uniform step sizes.
   *
   * @param Q    real vector containing the state.
   * @param t0   initial time.
   * @param t1   final time.
   * @param step number of steps between current time \a t0 and final time \a t1.
   * @param P    vector containing fixed parameters.
   * @return new time (\a t1).
   */
  // -------------------------------------------------------------------------------------
  public double integrate( double[] Q, double t0, double t1, int step, double[] P ) {
    // -----------------------------------------------------------------------------------
    double h  = (t1 - t0) / ((double) step);
    double t  = t0;
    double h2 = h / 2.0;

    for (int k=0; k<step; k++) {

      if (CHECK( Q, t, P ) != 0) { return t; }

      for (int j=0; j<dim; j++) { W[j] = Q[j];               } DIFEQ(A, W, t,    P);
      for (int j=0; j<dim; j++) { W[j] = Q[j] + (A[j] * h2); } DIFEQ(B, W, t+h2, P);
      for (int j=0; j<dim; j++) { W[j] = Q[j] + (B[j] * h2); } DIFEQ(C, W, t+h2, P);
      for (int j=0; j<dim; j++) { W[j] = Q[j] + (C[j] * h);  } DIFEQ(D, W, t+h,  P);

      for (int j=0; j<dim; j++) { Q[j] += (h*(A[j] + 2.0*(B[j] + C[j]) + D[j])/6.0); }

      t += h;
    }

    return t;
  }
}

// =======================================================================================
// **                                       R K 4                                       **
// ======================================================================== END FILE =====
