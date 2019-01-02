// ====================================================================== BEGIN FILE =====
// **                               B P N N _ M A T R I X                               **
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
 * @file BPNN.java
 * <p>
 * Provides interface and methods for a Backpropagation Neural network.
 *
 * @date 2018-12-23
 *
 */
// =======================================================================================

package org.trncmp.mllib.nn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.trncmp.lib.Dice;
import org.trncmp.lib.FileTools;
import org.trncmp.lib.Math2;

import org.hipparchus.util.FastMath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class BPNN_matrix {
  // -------------------------------------------------------------------------------------

  private static final Logger logger = LogManager.getLogger();

  private static Dice dd = Dice.getInstance();
  
  // =====================================================================================
  static public class Layer {
    // -----------------------------------------------------------------------------------

    public double[][] W = null;       /** Weight Matrix */
    public double[]   b = null;       /** Bias Vector   */

    public int        num_inp = 0;    /** Number of inputs */
    public int        num_out = 0;    /** Number of nodes (or Outputs) */

    public double[][] Z = null;       /** Weighted sums for each sample. */
    public double[][] A = null;       /** Activation values for each sample. */
    public double[][] E = null;       /** Error matrix */
    public double[][] D = null;       /** Delta matrix */

    public int        num_sample = 0; /** Number of samples **/

    
    // =====================================================================================
    /** Allocate space.
     *  @param nInp number of inputs to this layer.
     *  @param nOut number of nodes (outputs) in this layer.
     */
    // -------------------------------------------------------------------------------------
    protected void alloc( int nInp, int nOut ) {
      // -----------------------------------------------------------------------------------
      num_inp = nInp;
      num_out = nOut;
      
      W = new double[num_out][num_inp];
      b = new double[num_out];
    }
    
    // =====================================================================================
    /** Constructor.
     *  @param nInp number of inputs to this layer.
     *  @param nOut number of nodes (outputs) in this layer.
     */
    // -------------------------------------------------------------------------------------
    public Layer( int nInp, int nOut ) {
      // -----------------------------------------------------------------------------------
      alloc( nInp, nOut );
    }
    
    // =====================================================================================
    /** Randomize weights and bias.
     *  @param scale scale for random numbers
     *
     *  Set each weight or bias uniformily between (-scale,+scale)
     */
    // -------------------------------------------------------------------------------------
    public void randomize( double scale ) {
      // -----------------------------------------------------------------------------------
      for ( int k=0; k<num_out; k++ ) {
        b[k] = scale*(2.0*dd.uniform() - 1.0);
        for ( int j=0; j<num_inp; j++ ) {
          W[k][j] = scale*(2.0*dd.uniform() - 1.0);
        }
      }
    }
    
    // =====================================================================================
    /** Prepare Network for forward pass.
     *  @param nS number of samples.
     *
     *  Allocate temporary arrays to hold work if there are a different number of
     *  samples since the last time.
     */
    // -------------------------------------------------------------------------------------
    public void prepare( int nS ) {
      // -----------------------------------------------------------------------------------
      if ( num_sample != nS ) {
        num_sample = nS;
        A = new double[num_sample][num_out];
        Z = new double[num_sample][num_out];
        E = new double[num_sample][num_out];
        D = new double[num_sample][num_out];
      }
    }
    
    
  } // end class BPNN_matrix.Layer


  protected Layer[] L         = null;  /** Layers */
  protected int     num_layer = 0;     /** number of layers */
  protected int     num_inp   = 0;     /** number of inputs */
  protected int     num_out   = 0;     /** number of outputs (nodes in last layer) */


  // =====================================================================================
  /** Allocate network layers.
   *  @param nInp number of inputs to the network.
   *  @param nHid array of the number of nodes in each layer.
   *  @param nOut number of outputs (or nodes in last layer)
   */
  // -------------------------------------------------------------------------------------
  protected void alloc( int nInp, int[] nHid, int nOut ) {
    // -----------------------------------------------------------------------------------
    int nh = nHid.length;

    num_layer = nh + 1;
    num_inp   = nInp;
    num_out   = nOut;

    L = new Layer[num_layer];

    L[0] = new Layer( num_inp, nHid[0] );

    for ( int i=1; i<nh; i++ ) {
      L[i] = new Layer( nHid[i-1], nHid[i] );
    }

    L[nh] = new Layer( nHid[nh-1], nOut );
  }


  // =====================================================================================
  /** Constructor.
   *  @param nInp number of inputs to the network.
   *  @param nHid array of the number of nodes in each layer.
   *  @param nOut number of outputs (or nodes in last layer)
   */
  // -------------------------------------------------------------------------------------
  public BPNN_matrix( int nInp, int[] nHid, int nOut ) {
    // -----------------------------------------------------------------------------------
    alloc( nInp, nHid, nOut );
  }

  // =====================================================================================
  /** Randomize weights and bias.
   *  @param scale scale for random numbers
   *
   *  Set each weight or bias uniformily between (-scale,+scale)
   */
  // -------------------------------------------------------------------------------------
  public void randomize( double scale ) {
    // -----------------------------------------------------------------------------------
    for ( int k=0; k<num_layer; k++ ) {
      L[k].randomize( scale );
    }
  }
    



  // =====================================================================================
  /** Forward pass.
   *  @param input  input table
   *  @return 0 for success, non zero for failure.
   *
   *  Perform a forward pass through this network.
   */
  // -------------------------------------------------------------------------------------
  protected int forward( double[][] input ) {
    // -----------------------------------------------------------------------------------
    if ( num_inp != input[0].length ) {
      logger.error( "input matrix has different number of columns than this network" );
      return 1;
    }

    int num_sample = input.length;

    for ( int k=0; k<num_layer; k++ ) {
      L[k].prepare( num_sample );
    }

    // -----------------------------------------------------------------------------------

    
    mul_weight( L[0].Z, L[0].W, L[0].b, input, num_inp, L[0].num_out, num_sample );
    transfer( L[0].A, L[0].Z, L[0].num_out, num_sample );

    for ( int i=1; i<num_layer; i++ ) {
      mul_weight( L[i].Z, L[i].W, L[i].b, L[i-1].A, num_inp, L[i].num_out, num_sample );
      transfer( L[i].A, L[i].Z, L[i].num_out, num_sample );
    }

    return 0;
  }
    

  // =====================================================================================
  /** Forward pass.
   *  @param output output table
   *  @param input  input  table
   *  @return 0 for success, non zero for failure.
   *
   *  Perform a forward pass through this network and copy the output.
   */
  // -------------------------------------------------------------------------------------
  public int forward( double[][] output, double[][] input ) {
    // -----------------------------------------------------------------------------------

    if ( input.length != output.length ) {
      logger.error( "input and output matrices have different number of rows" );
      return 2;
    }

    if ( num_out != output[0].length ) {
      logger.error( "output matrix has different number of columns than this network" );
      return 3;
    }

    int rv = forward( input );
    
    if ( 0 == rv ) {    
      copy( output, L[num_layer-1].A, num_out, output.length );
    }

    return rv;
  }
    

  // =====================================================================================
  /** Train pass.
   *  @param input  input  table
   *  @param desired_output output table
   *  @param alpha training parameter
   *  @return 0 for success, non zero for failure.
   *
   *  Perform a forward pass through this network.
   */
  // -------------------------------------------------------------------------------------
  public int backpropagate( double[][] input, double[][] desired_output, double alpha ) {
    // -----------------------------------------------------------------------------------

    int num_sample = input.length;
    
    if ( num_out != desired_output[0].length ) {
      logger.error( "desired output matrix has different number of columns than this network" );
      return 3;
    }

    // ----- perform a forward pass ------------------------------------------------------
    int rv = forward( input );

    if ( 0 == rv ) {

      // ----- accumulate the gradient with reverse passes ---------------------------------

      subtract( L[num_layer-1].E, desired_output,   L[num_layer-1].A, num_out, num_sample );
      delta(    L[num_layer-1].D, L[num_layer-1].E, L[num_layer-1].A, num_out, num_sample );

      for ( int i=num_layer-2; i>=0; i-- ) {
        error_matrix_mul( L[i].E, L[i+1].D, L[i+1].W, L[i+1].num_inp, L[i+1].num_out, num_sample );
        delta( L[i].D, L[i].E, L[i].A, L[i].num_out, num_sample );
      }
    
      // ----- update the weights ----------------------------------------------------------

      for ( int i=num_layer-1; i>0; i-- ) {
        update_weight_matrix( L[i].W, L[i].num_inp, L[i].num_out, L[i].b,
                              L[i].D, L[i-1].A, num_sample, alpha );
      }

      update_weight_matrix( L[0].W, L[0].num_inp, L[0].num_out, L[0].b,
                              L[0].D, input, num_sample, alpha );

    }

    return rv;
  }



  // =====================================================================================
  /** Mean Square Error.
   *  @param input  input  table
   *  @param desired_output output table
   *  @return 0 for success, non zero for failure.
   *
   *  Perform a forward pass through this network.
   */
  // -------------------------------------------------------------------------------------
  public double mse( double[][] input, double[][] desired_output ) {
    // -----------------------------------------------------------------------------------

    int num_sample = input.length;
    
    if ( num_out != desired_output[0].length ) {
      logger.error( "desired output matrix has different number of columns than this network" );
      return 3;
    }

    // ----- perform a forward pass ------------------------------------------------------
    int rv = forward( input );

    double mse = -10.0;
    
    if ( 0 == rv ) {

      mse = 0.0e0;

      for ( int s=0; s<num_sample; s++ ) {
        for ( int k=0; k<num_out; k++ ) {
          double d = desired_output[s][k] - L[num_layer-1].A[s][k];
          mse += (d*d);
        }
      }

      mse /= (double) (num_sample * num_out);
    } else {
      mse = -(double)rv;
    }

    return mse;
  }

  

  // =====================================================================================
  /** Activation Function.
   *  @param A  activation matrix.
   *  @param Z  input matrix.
   *  @param nz number of nodes.
   *  @param ns number of samples.
   */
  // -------------------------------------------------------------------------------------
  protected void transfer( double[][] A, double[][] Z, int nz, int ns ) {
    // -----------------------------------------------------------------------------------
      for ( int s=0; s<ns; s++ ) {
        for ( int k=0; k<nz; k++ ) {
          A[s][k] = 1.0e0 / ( 1.0e0 + FastMath.exp( -Z[s][k] ) );
        }
      }
  }


  // =====================================================================================
  /** Compute layer delta.
   *  @param D  error matrix times the gradient
   *  @param E  the gradient
   *  @param A  the forward activation values
   *  @param nz number of nodes.
   *  @param ns number of samples.
   */   
  // -------------------------------------------------------------------------------------
  protected void delta( double[][] D, double[][] E, double[][] A, int nz, int ns ) {
    // -----------------------------------------------------------------------------------
    for ( int s=0; s<ns; s++ ) {
      for ( int k=0; k<nz; k++ ) {
        D[s][k] = E[s][k]*A[s][k]*(1.0e0 - A[s][k]);
      }
    }
  }
  

  // =====================================================================================
  /** Forward multiply (eq1)
   *  @param Z  weight sum matrix
   *  @param W  weight matrix
   *  @param b  bias vector
   *  @param X  input matrix
   *  @param nx number of inputs
   *  @param nz number of weighted sums
   *  @param ns number of samples
   */
  // -------------------------------------------------------------------------------------
  protected void mul_weight( double[][] Z, double[][] W, double[] B,
                             double[][] X, int nx, int nz, int ns ) {
    // -----------------------------------------------------------------------------------
    for ( int s=0; s<ns; s++ ) {
      for ( int k=0; k<nz; k++ ) {
        double sum = B[k];
        for ( int j=0; j<nx; j++ ) {
          sum += ( W[k][j] * X[s][j]);
        }
        Z[s][k] = sum;
      }
    }
  }


  // =====================================================================================
  /** Activation Function.
   *  @param D  destination matrix.
   *  @param S  source      matrix.
   *  @param nr number of rows.
   *  @param nc number of columns.
   */
  // -------------------------------------------------------------------------------------
  protected void copy( double[][] D, double[][] S, int nr, int nc ) {
    // -----------------------------------------------------------------------------------
    for ( int r=0; r<nr; r++ ) {
      for ( int c=0; c<nc; c++ ) {
        D[r][c] = S[r][c];
      }
    }
  }


  // =====================================================================================
  /** Activation Function.
   *  @param D  destination matrix.
   *  @param S  source      matrix.
   *  @param nz number of nodes.
   *  @param ns number of samples.
   */
  // -------------------------------------------------------------------------------------
  protected void subtract( double[][] S, double[][] A, double[][] B, int nz, int ns ) {
    // -----------------------------------------------------------------------------------
    for ( int s=0; s<ns; s++ ) {
      for ( int k=0; k<nz; k++ ) {
        S[s][k] = A[s][k] - B[s][k];
      }
    }
  }


  // =====================================================================================
  /** Compute layer delta.
   *  @param E  error matrix
   *  @param D  delta matrix
   *  @param W  weight matrix
   *  @param nx number of inputs.
   *  @param nz number of outputs (nodes).
   *  @param ns number of samples.
   */   
  // -------------------------------------------------------------------------------------
  protected void error_matrix_mul( double[][] E, double[][] D, double[][] W,
                                   int nx, int nz, int ns ) {
    // -----------------------------------------------------------------------------------
    for ( int s=0; s<ns; s++ ) {
      for ( int k=0; k<nx; k++ ) {
        double sum = 0.0e0;
        for ( int m=0; m<nz; m++ ) {
          sum += ( D[s][m] * W[m][k] );
        }
        E[s][k] = sum;
      }
    }
  }
  

  void show( String lab, double S ) {
    System.out.format( "%s = %g\n", lab, S );
  }
  
  void show( String lab, int S ) {
    System.out.format( "%s = %d\n", lab, S );
  }
  
  void show( String lab, double[] V ) {
    System.out.format( "%s.shape = (%d,)\n", lab, V.length );
  }
  
  void show( String lab, double[][] M ) {
    System.out.format( "%s.shape = (%d,%d)\n", lab, M.length, M[0].length );
  }
  
  // =====================================================================================
  /** Compute layer delta.
   *  @param W     weight matrix being updated
   *  @param nx    number of inputs to this layer
   *  @param nz    number of nodes in this layer
   *  @param B     bias vector being updated
   *  @param D     error matrix
   *  @param A     inputs that feed the weights being updated
   *  @param ns    number of batch samples
   *  @param alpha learning rate
   */   
  // -------------------------------------------------------------------------------------
  protected void update_weight_matrix( double[][] W, int nx, int nz, double[] B,
                                       double[][] D, double[][] A, int ns, double alpha ) {
    // -----------------------------------------------------------------------------------

    double train_const = alpha / (double)ns;
    
    for ( int j=0; j<nx; j++ ) {
      for ( int k=0; k<nz; k++ ) {
        double sum = 0.0e0;
        for ( int s=0; s<ns; s++ ) {
          sum += ( A[s][j] * D[s][k] );
        }
        W[k][j] += ( train_const * sum );
      }
    }

    for ( int k=0; k<nz; k++ ) {
      double sum = 0.0e0;
      for ( int s=0; s<ns; s++ ) {
        sum += D[s][k];
      }
      B[k] += ( train_const * sum );
    }
 
  }

  
} // end class BPNN


// =======================================================================================
// **                               B P N N _ M A T R I X                               **
// ======================================================================== END FILE =====
