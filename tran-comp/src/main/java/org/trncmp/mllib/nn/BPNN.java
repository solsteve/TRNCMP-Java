// ====================================================================== BEGIN FILE =====
// **                                      B P N N                                      **
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
public class BPNN {
  // -------------------------------------------------------------------------------------

  private static final Logger logger = LogManager.getLogger();

  private static Dice dd = Dice.getInstance();
  
  // =====================================================================================
  static public class Layer {
    // -----------------------------------------------------------------------------------

    public int        num_inp = 0;    /** Number of inputs */
    public int        num_out = 0;    /** Number of nodes (or Outputs) */

    public double[][] W       = null; /** Weight Matrix */
    public double[]   b       = null; /** Bias Vector   */

    public double[][] dW      = null; /** Cumulative delta Weight Matrix */
    public double[]   db      = null; /** Cumulative delta Bias Vector   */

    public double[]   Z       = null; /** Weighted sum vector. */
    public double[]   A       = null; /** Activation Vector. */
    public double[]   E       = null; /** Error vector */
    public double[]   D       = null; /** Delta vector */

    
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
      
      W  = new double[num_out][num_inp];
      dW = new double[num_out][num_inp];
      b  = new double[num_out];
      db = new double[num_out];
      Z  = new double[num_out];
      A  = new double[num_out];
      E  = new double[num_out];
      D  = new double[num_out];
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
    /** Reset each weight or bias delta to zero.
     */
    // -------------------------------------------------------------------------------------
    public void reset( ) {
      // -----------------------------------------------------------------------------------
      for ( int k=0; k<num_out; k++ ) {
        db[k] = 0.0e0;
        for ( int j=0; j<num_inp; j++ ) {
          dW[k][j] = 0.0e0;
        }
      }
    }
    
  } // end class BPNN.Layer


  protected Layer[] L            = null;  /** Layers */
  protected int     num_layer    = 0;     /** number of layers */
  protected int     num_inp      = 0;     /** number of inputs */
  protected int     num_out      = 0;     /** number of outputs (nodes in last layer) */
  protected int     sample_count = 0;     /** number of samples presented since reset */


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
  public BPNN( int nInp, int[] nHid, int nOut ) {
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
  /** Reset each weight or bias delta to zero.
   */
  // -------------------------------------------------------------------------------------
  public void reset() {
    // -----------------------------------------------------------------------------------
    for ( int k=0; k<num_layer; k++ ) {
      L[k].reset();
    }
    sample_count = 0;
  }
    

  // =====================================================================================
  /** Forward pass.
   *  @param input input vector.
   *
   *  Perform a forward pass through this network.
   */
  // -------------------------------------------------------------------------------------
  protected void forward( double[] input ) {
    // -----------------------------------------------------------------------------------
    Layer T1 = L[0];
    mul_weight( T1.Z, T1.W, T1.b, input, num_inp, T1.num_out );
    transfer( T1.A, T1.Z, T1.num_out );

    for ( int i=1; i<num_layer; i++ ) {
      Layer T2 = L[i];
      mul_weight( T2.Z, T2.W, T2.b, T1.A, num_inp, T2.num_out );
      transfer( T2.A, T2.Z, T2.num_out );
      T1 = T2;
    }
  }
    

  // =====================================================================================
  /** Forward pass.
   *  @param output output vector.
   *  @param input  input  vector.
   *
   *  Perform a forward pass through this network and copy the output.
   */
  // -------------------------------------------------------------------------------------
  public void forward( double[] output, double[] input ) {
    // -----------------------------------------------------------------------------------
    forward( input );
    copy( output, L[num_layer-1].A, num_out );
  }
    

  // =====================================================================================
  /** Forward pass.
   *  @param output output table.
   *  @param input  input  table.
   *
   *  Perform a forward pass through this network and copy the output.
   */
  // -------------------------------------------------------------------------------------
  public void forward( double[][] output, double[][] input ) {
    // -----------------------------------------------------------------------------------
    int ns = input.length;

    for ( int i=0; i<ns; i++ ) {
      forward( input[i] );
      copy( output[i], L[num_layer-1].A, num_out );
    }
  }
    

  // =====================================================================================
  /** Full training.
   *  @param input  input  table.
   *  @param output output table.
   *  @param max_gen number of passes.
   *  @param report report interval. (0 = no report)
   *
   */
  // -------------------------------------------------------------------------------------
  public double train( double[][] input, double[][] output,
                       int max_gen, int report,
                       double alpha ) {
    // -----------------------------------------------------------------------------------
    double M = 1.0e10;
    if ( 0 < report ) {
      M = mse( input, output );
      System.out.format( "0: %13.6e\n", M );
    }

    int ns = input.length;

    double tc = alpha / (double)ns;
    
    for ( int i=1; i<max_gen; i++ ) {
      reset();
      for ( int j=0; j<ns; j++ ) {
        backpropagate( input[j], output[j] );
      }
      update( tc );

      if ( 0 < report ) {
        if ( 0 == ( i % 1000 ) ) {
          System.out.format( "%d: %13.6e\n", i, mse( input, output ) );
        }
      }

    }
    
    M = mse( input, output );
    if ( 0 < report ) {
      System.out.format( "%d: %13.6e\n", max_gen, M );
    }

    return M;
  }
    

  // =====================================================================================
  /** Full training.
   *  @param input  input  table.
   *  @param output output table.
   *  @param max_gen number of passes.
   *  @param report report interval. (0 = no report)
   *
   */
  // -------------------------------------------------------------------------------------
  public double batch( double[][] input, double[][] output,
                       int max_gen, int batch_size, int report,
                       double alpha ) {
    // -----------------------------------------------------------------------------------
    double M = 1.0e10;
    if ( 0 < report ) {
      M = mse( input, output );
      System.out.format( "0: %13.6e\n", M );
    }

    int ns = input.length;

    int[] index = new int[ns];

    for ( int j=0; j<ns; j++ ) { index[j] = j; }
    
    dd.scramble( index );

    int offset = 0;
    int max_offset = ns - batch_size - 1;

    for ( int i=1; i<max_gen; i++ ) {
      reset();
      for ( int j=0; j<batch_size; j++ ) {
        backpropagate( input[index[offset+j]], output[index[offset+j]] );
      }
      update( alpha );

      offset += batch_size;

      if ( offset > max_offset ) {
        dd.scramble( index );
        offset = 0;
      }

      if ( 0 < report ) {
        if ( 0 == ( i % 1000 ) ) {
          System.out.format( "%d: %13.6e\n", i, mse( input, output ) );
        }
      }

    }
    
    M = mse( input, output );
    if ( 0 < report ) {
      System.out.format( "%d: %13.6e\n", max_gen, M );
    }

    return M;
  }
    

  // =====================================================================================
  /** Back-propagation pass.
   *  @param input          input vector
   *  @param desired_output output vector
   *  @param alpha          training parameter
   *
   *  Perform a backward pass through this network incrementing dW and db.
   */
  // -------------------------------------------------------------------------------------
  public void backpropagate( double[] input, double[] desired_output ) {
    // -----------------------------------------------------------------------------------

    forward( input );
    
    // ----- accumulate the gradient with reverse passes -------------------------------

    Layer T1 = L[num_layer-1];
    subtract( T1.E, desired_output,   T1.A, num_out );
    delta(    T1.D, T1.E, T1.A, num_out );

    for ( int i=num_layer-2; i>=0; i-- ) {
      Layer TI  = L[i];
      Layer Tp1 = L[i+1];
      error_matrix_mul( TI.E, Tp1.D, Tp1.W, Tp1.num_inp, Tp1.num_out );
      delta( TI.D, TI.E, TI.A, TI.num_out );
    }

    // ----- update the weight deltas --------------------------------------------------

    for ( int i=num_layer-1; i>0; i-- ) {
      Layer TI  = L[i];
      Layer Tm1 = L[i-1];
      update_weight_delta( TI.dW, TI.num_inp, TI.num_out, TI.db,
                           TI.D, Tm1.A );
    }
    
    T1 = L[0];
    update_weight_delta( T1.dW, T1.num_inp, T1.num_out, T1.db,
                         T1.D, input );

    sample_count += 1;
  }


  // =====================================================================================
  /** Weight Updates.
   *  @param alpha training parameter
   *
   *  Update the weights and bias using the accumulated dW and db.
   */
  // -------------------------------------------------------------------------------------
  public void update( double alpha ) {
    // -----------------------------------------------------------------------------------

    double tc = alpha/(double)sample_count;
    
    for ( int i=0; i<num_layer; i++ ) {
      Layer TI = L[i];
      update_weight( TI.W, TI.dW, TI.num_inp, TI.num_out,
                     TI.b, TI.db, tc );
    }
    
  }


  // =====================================================================================
  /** Mean Square Error.
   *  @param input   input vector.
   *  @param desired desired output vector.
   *  @return mean square error.
   *
   *  Calculate the mean square difference between the actual network output and the
   *  desired output vector.
   */
  // -------------------------------------------------------------------------------------
  public double mse( double[] input, double[] desired ) {
    // -----------------------------------------------------------------------------------

    forward( input );

    double mse = 0.0e0;
    
    for ( int k=0; k<num_out; k++ ) {
      double d = desired[k] - L[num_layer-1].A[k];
      mse += (d*d);
    }

    return mse / (double)num_out;
  }

  
  // =====================================================================================
  /** Mean Square Error.
   *  @param input   input vector.
   *  @param desired desired output vector.
   *  @return mean square error.
   *
   *  Calculate the mean square difference between the actual network output and the
   *  desired output vector.
   */
  // -------------------------------------------------------------------------------------
  public double mse( double[][] input, double[][] desired ) {
    // -----------------------------------------------------------------------------------
    int    n   = input.length;

    double mse = 0.0e0;
    for ( int i=0; i<n; i++ ) {
      mse += mse( input[i], desired[i] );
    }
    return mse / (double)n;    

    
  }

  
  // =====================================================================================
  /** Activation Function.
   *  @param A  activation vector.
   *  @param Z  weighted sum vector.
   *  @param nz number of nodes.
   */
  // -------------------------------------------------------------------------------------
  static protected void transfer( double[] A, double[] Z, int nz ) {
    // -----------------------------------------------------------------------------------
    for ( int k=0; k<nz; k++ ) {
      A[k] = 1.0e0 / ( 1.0e0 + FastMath.exp( -Z[k] ) );
    }
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
   *  @param D  error vector times the gradient.
   *  @param E  the gradient vector.
   *  @param A  the forward activation values.
   *  @param nz number of nodes.
   */   
  // -------------------------------------------------------------------------------------
  static protected void delta( double[] D, double[] E, double[] A, int nz ) {
    // -----------------------------------------------------------------------------------
    for ( int k=0; k<nz; k++ ) {
      D[k] = E[k]*A[k]*(1.0e0 - A[k]);
    }
  }
  

  // =====================================================================================
  /** Forward multiply (eq1)
   *  @param Z  weight sum vector.
   *  @param W  weight matrix.
   *  @param b  bias vector.
   *  @param X  input vector.
   *  @param nx number of inputs.
   *  @param nz number of weighted sums.
   */
  // -------------------------------------------------------------------------------------
  static protected void mul_weight( double[] Z, double[][] W, double[] B,
                                    double[] X, int nx, int nz ) {
    // -----------------------------------------------------------------------------------
    for ( int k=0; k<nz; k++ ) {
      double sum = B[k];
      for ( int j=0; j<nx; j++ ) {
        sum += ( W[k][j] * X[j]);
      }
      Z[k] = sum;
    }
  }


  // =====================================================================================
  /** Activation Function.
   *  @param D destination vector.
   *  @param S source      vector.
   *  @param n number of elements
   */
  // -------------------------------------------------------------------------------------
  static protected void copy( double[] D, double[] S, int n ) {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<n; i++ ) {
      D[i] = S[i];
    }
  }


  // =====================================================================================
  /** Difference of two vectors.
   *  @param S  destination vector.
   *  @param A  left  hand vector.
   *  @param B  right hand vector.
   *  @param n number of elements.
   */
  // -------------------------------------------------------------------------------------
  static protected void subtract( double[] S, double[] A, double[] B, int n ) {
    // -----------------------------------------------------------------------------------
    for ( int k=0; k<n; k++ ) {
      S[k] = A[k] - B[k];
    }
  }


  // =====================================================================================
  /** Compute layer delta.
   *  @param E  error vector
   *  @param D  delta vector
   *  @param W  weight matrix
   *  @param nx number of inputs.
   *  @param nz number of outputs (nodes).
   */   
  // -------------------------------------------------------------------------------------
  static protected void error_matrix_mul( double[] E, double[] D, double[][] W,
                                          int nx, int nz ) {
    // -----------------------------------------------------------------------------------
    for ( int k=0; k<nx; k++ ) { E[k] = 0.0e0; }
    
    for ( int m=0; m<nz; m++ ) {
      for ( int k=0; k<nx; k++ ) {
        E[k] += ( D[m] * W[m][k] );
      }
    }
  }
  

  // =====================================================================================
  /** Compute layer delta.
   *  @param dW    weight delta being updated
   *  @param nx    number of inputs to this layer
   *  @param nz    number of nodes in this layer
   *  @param dB    bias delta being updated
   *  @param D     error vector
   *  @param A     inputs that feed the weights being updated
   */   
  // -------------------------------------------------------------------------------------
  static protected void update_weight_delta( double[][] dW, int nx, int nz, double[] dB,
                                             double[] D, double[] A ) {
    // -----------------------------------------------------------------------------------
    for ( int k=0; k<nz; k++ ) {
      dB[k] += D[k];
      for ( int j=0; j<nx; j++ ) {
        dW[k][j] += A[j] * D[k];
      }
    }
  }

  
  // =====================================================================================
  /** Compute layer delta.
   *  @param W     weight matrix being updated.
   *  @param dW    weight deltas.
   *  @param nx    number of inputs to this layer.
   *  @param nz    number of nodes in this layer.
   *  @param B     bias vector being updated.
   *  @param dB    bias deltas.
   *  @param alpha training constant.
   */   
  // -------------------------------------------------------------------------------------
  static protected void update_weight( double[][] W, double[][] dW, int nx, int nz,
                                       double[] B, double[] dB, double alpha ) {
    // -----------------------------------------------------------------------------------

    for ( int k=0; k<nz; k++ ) {
      B[k] += ( alpha * dB[k] );
      for ( int j=0; j<nx; j++ ) {
        W[k][j] += ( alpha * dW[k][j] );
      }
    }
    
  }
  
} // end class BPNN


// =======================================================================================
// **                                      B P N N                                      **
// ======================================================================== END FILE =====
