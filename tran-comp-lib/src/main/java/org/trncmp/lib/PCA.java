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
 *
 *  ****************************************************
 *  ****************************************************
 *  TODO:  normalize input data by dividing the variance
 *  ****************************************************
 *  ****************************************************
 *
 * @author Stephen W. Soliday
 * @date 2018-04-20
 */
// =======================================================================================

package org.trncmp.lib;

import java.io.PrintStream;

import org.hipparchus.linear.ArrayRealVector;
import org.hipparchus.linear.RealVector;
import org.hipparchus.linear.Array2DRowRealMatrix;
import org.hipparchus.linear.RealMatrix;
import org.hipparchus.linear.SingularValueDecomposition;


// =======================================================================================
public class PCA {
  // -------------------------------------------------------------------------------------

  protected int        num_var    = 0;                /** Number of variables           */
  protected RealVector mu         = null;             /** Sample mean                   */
  protected RealVector variance   = null;             /** Principle axis variance       */
  protected RealMatrix covariance = null;             /** Covariance                    */
  protected RealMatrix fwdTrans   = null;             /** Transformation matrix         */
  protected RealMatrix rvsTrans   = null;             /** Reverse Transformation matrix */

  
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
  /** @brief Get Principle Vectors.
   *  @return the principle vectors.
   */
  // -------------------------------------------------------------------------------------
  public double[][] getForwardTransform() {
    // -----------------------------------------------------------------------------------
    return fwdTrans.getData();
  }

  // =====================================================================================
  /** @brief Get Tranpose of Principle Vectors.
   *  @return the transpose of the principle vectors.
   */
  // -------------------------------------------------------------------------------------
  public double[][] getReverseTransform() {
    // -----------------------------------------------------------------------------------
    return rvsTrans.getData();
  }


  // =====================================================================================
  /** @brief Get Principle Components.
   *  @return the variance of the principle axes.
   */
  // -------------------------------------------------------------------------------------
  public double[] getPrinciple() {
    // -----------------------------------------------------------------------------------
    return variance.toArray();
  }


  // =====================================================================================
  /** @brief Get Variance.
   *  @param index index of the mean variable.
   *  @return the indexed variance of a principle axis.
   */
  // -------------------------------------------------------------------------------------
  public double getPrinciple( int index ) {
    // -----------------------------------------------------------------------------------
    return variance.getEntry(index);
  }


  // =====================================================================================
  /** @brief Compile from Samples.
   *  @param data sample data.
   *
   *  Ingest a table of data. Each row is one sample. Columns are the correlated
   *  variables.
   */
  // -------------------------------------------------------------------------------------
  public void compileFromSamples( double[][] data ) {
    // -----------------------------------------------------------------------------------
    int num_sample = data.length;
    num_var        = data[0].length;

    // ----- compute the mean ------------------------------------------------------------
    
    mu = new ArrayRealVector( Math2.mean( data, 0 ) );

    // ----- create matrix from the mean centered data -----------------------------------
    
    RealMatrix A = new Array2DRowRealMatrix( num_sample, num_var );

    for ( int i=0; i<num_sample; i++ ) {
      for ( int j=0; j<num_var; j++ ) {
        A.setEntry( i, j, data[i][j] - mu.getEntry(j) );
      }
    }

    // ----- perform singular decomposition on the mean shifted data --------------------

    SingularValueDecomposition USV = new SingularValueDecomposition( A );

    fwdTrans = USV.getVT();
    rvsTrans = USV.getV();

    double[] sv = USV.getSingularValues();
    int k = sv.length;

    if ( k < num_var ) {
      System.err.println( "PCA: Number of singular values = "+k+" expected "+num_var );
    }
    
    for ( int j=0; j<k; j++ ) {
      double x = sv[j];
      sv[j] = x*x/(double)(num_sample-1);
    }
    variance = new ArrayRealVector( sv );

    // ----- recover the covariance -----------------------------------------------------

    covariance = new Array2DRowRealMatrix( Math2.covariance( data ) );
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

    num_var = cov.length;
    
    covariance = new Array2DRowRealMatrix( cov );
    mu  = new ArrayRealVector( mean );

    SingularValueDecomposition USV = new SingularValueDecomposition( covariance );
    
    fwdTrans = USV.getUT();
    rvsTrans = USV.getU();

    variance = new ArrayRealVector( USV.getSingularValues() );
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
    RealVector y = new ArrayRealVector( num_var );
    for ( int i=0; i<num_var; i++ ) {
      y.setEntry( i, in[i] - mu.getEntry( i ) );
    }
    RealVector x = fwdTrans.operate(y);

    int k = out.length;
    if ( x.getDimension​() < k ) { k = x.getDimension​(); }
    for ( int i=0; i<k; i++ ) {
      out[i] = x.getEntry(i);
    }
  }
  

  // =====================================================================================
  /** @brief Forward Transform.
   *  @param out output matrix.
   *  @param in  input matrix.
   *
   *  Mean shift the matrix to the origin. Rotate it to align the principle axes.
   */
  // -------------------------------------------------------------------------------------
  public void transform( double[][] out, double[][] in ) {
    // -----------------------------------------------------------------------------------
    int n = out.length;
    for ( int i=0; i<n; i++ ) {
      transform( out[i], in[i] );
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
    RealVector y = rvsTrans.operate(new ArrayRealVector( in ));
    for ( int i=0; i<num_var; i++ ) {
      out[i] = y.getEntry(i) + mu.getEntry(i);
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
  public void recover( double[][] out, double[][] in ) {
    // -----------------------------------------------------------------------------------
    int n = out.length;
    for ( int i=0; i<n; i++ ) {
      recover( out[i], in[i] );
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
    ps.format("Mean = %s\n", array.toString( getMean(), fmt ) );
    ps.format("\nCovariance =\n%s\n", array.toString( getCovariance(), fmt ) );
    ps.format("\nPrinciple Components =\n%s\n", array.toString( getPrinciple(), fmt ) );
    ps.format("\nForward Transform =\n%s\n", array.toString( getForwardTransform(), fmt ) );
    ps.format("\nReverse Transform =\n%s\n", array.toString( getReverseTransform(), fmt ) );
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
