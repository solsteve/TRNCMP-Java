// ====================================================================== BEGIN FILE =====
// **                                       P C A                                       **
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
 * @file PCA.java
 *  Provides interface and methods to perform Principle Component Analysis.
 *  <p>
 * Uses single value decomposition where    X = U*S*Vt
 *                                          p = Vt*(x-mu)    transform
 *                                          x = U*p + mu     recover
 * and eigen value decomposition
 *
 * @author Stephen W. Soliday
 * @date 2018-04-20
 */
// =======================================================================================

package org.trncmp.lib;

import java.io.*;

import org.trncmp.lib.linear.*;

import org.hipparchus.linear.*;

// =======================================================================================
public class PCA {
  // -------------------------------------------------------------------------------------

  protected int        num_var    = 0;       /** Number of variables           */
  protected RealVector mu         = null;    /** Sample mean                   */
  protected RealMatrix covariance = null;    /** Sample covariance             */
  protected double[]   variance   = null;    /** Principle axis variance       */
  protected RealMatrix fwdR       = null;    /** Forward transformation matrix */
  protected RealMatrix rvsR       = null;    /** Recover transformation matrix */

  
  // =====================================================================================
  /** @brief Constructor.
   */
  // -------------------------------------------------------------------------------------
  public PCA() {
    // -----------------------------------------------------------------------------------
  }

  
  // =====================================================================================
  /** @brief Get Mean.
   *  @return the mean.
   */
  // -------------------------------------------------------------------------------------
  public double[] getMean() {
    // -----------------------------------------------------------------------------------
    return mu.toArray();
  }

  
  // =====================================================================================
  /** @brief Get Mean.
   *  @param index index of the mean variable.
   *  @return the indexed mean.
   */
  // -------------------------------------------------------------------------------------
  public double getMean( int index ) {
    // -----------------------------------------------------------------------------------
    return mu.getEntry(index);
  }

  
  // =====================================================================================
  /** @brief Get Covariance.
   *  @return the covariance.
   */
  // -------------------------------------------------------------------------------------
  public double[][] getCovariance() {
    // -----------------------------------------------------------------------------------
    return covariance.getData();
  }


  // =====================================================================================
  /** @brief Get Covariance.
   *  @return the covariance.
   */
  // -------------------------------------------------------------------------------------
  public double[][] getRotation() {
    // -----------------------------------------------------------------------------------
    return rvsR.getData();
  }


  // =====================================================================================
  /** @brief Get Variance.
   *  @return the variance of the principle axes.
   */
  // -------------------------------------------------------------------------------------
  public double[] getVariance() {
    // -----------------------------------------------------------------------------------
    return variance;
  }


  // =====================================================================================
  /** @brief Get Variance.
   *  @param index index of the mean variable.
   *  @return the indexed variance of a principle axis.
   */
  // -------------------------------------------------------------------------------------
  public double getVariance( int index ) {
    // -----------------------------------------------------------------------------------
    return variance[index];
  }


  // =====================================================================================
  /** @brief Compile from Samples.
   *  @param data sample data.
   *
   *  Ingest a table of data. Each row is one sample. Columns are the correlated
   *  variables. Generate the mean vector, the covariance matrix and the forward and
   *  reverse transformation matrices.
   */
  // -------------------------------------------------------------------------------------
  public void compileFromSamples( double[][] data ) {
    // -----------------------------------------------------------------------------------
    int num_sample = data.length;
    num_var        = data[0].length;

    double[] mean = new double[ num_var ];

    for ( int j=0; j<num_var; j++ ) {
      mean[j] = 0.0e0;
      for ( int i=0; i<num_sample; i++ ) {
        mean[j] += data[i][j];
      }
      mean[j] /= (double) num_sample;
    }

    double[][] M = new double[num_var][num_var];

    double fnm1 = (double)(num_sample - 1);
            
    for ( int i=0; i<num_var; i++ ) {
      double iMean = mean[i];
      for ( int j=i; j<num_var; j++ ) {
        double jMean = mean[j];
        double c_sum = Math2.N_ZERO;
        for ( int k=0; k<num_sample; k++ ) {
          double x = data[k][i] - iMean;
          double y = data[k][j] - jMean;
          c_sum += ( x * y );
        }
        c_sum /= fnm1;
        M[i][j] = c_sum;
        M[j][i] = c_sum;
      }
    }

    compileFromCovariance( M, mean );
  }

  // =====================================================================================
  /** @brief Compile from Covariance and Mean.
   *  @param cov  covariance matrix.
   *  @param mean sample mean.
   *
   *  Generate the forward and reverse transformation matrices.
   */
  // -------------------------------------------------------------------------------------
  public void compileFromCovariance( double[][] cov, double[] mean ) {
    // -----------------------------------------------------------------------------------

    covariance = new Array2DRowRealMatrix( cov );

    EigenDecomposition eig = new EigenDecomposition( covariance );

    num_var  = cov.length;
    mu       = new ArrayRealVector( mean );
    variance = eig.getRealEigenvalues();
    fwdR     = eig.getV();
    rvsR     = eig.getVT();
  }

  // =====================================================================================
  /** @brief Forward Transform.
   *  @param out output vector.
   *  @param in  input vector.
   *
   *  Mean shift the vector to the origin. Rotate it to align the principle axes.
   */
  // -------------------------------------------------------------------------------------
  public void transform( double[] out, double[] in ) {
    // -----------------------------------------------------------------------------------

    RealVector A = new ArrayRealVector(in).subtract( mu );
    
    RealVector V = fwdR.operate( A );
    for ( int i=0; i<num_var; i++ ) {
      out[i] = V.getEntry(i);
    }
  }
  

  // =====================================================================================
  /** @brief Recover Transform.
   *  @param out output vector.
   *  @param in  input vector.
   *
   *  Recover the rotation and shift out to the mean.
   */
  // -------------------------------------------------------------------------------------
  public void recover( double[] out, double[] in ) {
    // -----------------------------------------------------------------------------------

    RealVector A = new ArrayRealVector(in);
    
    RealVector V = rvsR.operate( A );
    for ( int i=0; i<num_var; i++ ) {
      out[i] = V.getEntry(i) + mu.getEntry(i);
    }
  }
  

  // =====================================================================================
  /** @brief Report.
   *  @param ps  reference to a print stream.
   *  @param fmt edit descriptor.
   *
   *  Dislay the values contained in this PCA on a PrintStream.
   */
  // -------------------------------------------------------------------------------------
  public void report( PrintStream ps, String fmt ) {
    // -----------------------------------------------------------------------------------
    ps.format("Number of variables = %d\n", num_var );
    ps.format("Mean      = %s\n", array.toString( mu.toArray(), fmt ) );
    double[][] C = covariance.getData();
    ps.format("\nCovariance =\n%s\n", array.toString( C, fmt ) );
    ps.format("\nEigen Values =\n%s\n", array.toString( variance, fmt ) );
    double[][] V1 = fwdR.getData();
    ps.format("\nEigen Vectors =\n%s\n", array.toString( V1, fmt ) );
    double[][] V2 = rvsR.getData();
    ps.format("\nReverse Vectors =\n%s\n", array.toString( V2, fmt ) );
  }

  
  // =====================================================================================
  /** @brief Report.
   *  @param ps  reference to a print stream.
   *
   *  Dislay the values contained in this PCA on a PrintStream, using "%g".
   */
  // -------------------------------------------------------------------------------------
  public void report( PrintStream ps ) {
    // -----------------------------------------------------------------------------------
    report( ps, "%g" );
  }

  
} // end of PCA class


// =======================================================================================
// **                                   S T A T M O D                                   **
// ======================================================================== END FILE =====
