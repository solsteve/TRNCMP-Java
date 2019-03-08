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
 * @date 2018-08-24
 *
 * ---------------------------------------------------------------------------------------
 *
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 *
 * @author Stephen W. Soliday
 * @date 2014-06-27
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class BPNNold {
  // -------------------------------------------------------------------------------------

  private static final Logger logger = LogManager.getLogger();

  protected int        nInp = 0;     // number of inputs
  protected int        nHid = 0;     // number of hidden nodes
  protected int        nOut = 0;     // number of outputs

  protected double[][] w1   = null;  // input-hidden  layer weights
  protected double[][] w2   = null;  // hidden-output layer weights

  protected double[]   b1   = null;  // input-hidden  layer bias
  protected double[]   b2   = null;  // hidden-output layer bias
  
  protected double[]   z1   = null;  // sum of weighted inputs
  protected double[]   z2   = null;  // sum of weighted hidden
  
  protected double[]   a1   = null;  // output of hidden nodes
  protected double[]   a2   = null;  // output of hidden nodes

  protected double[]   d1   = null;  // output layer delta
  protected double[]   d2   = null;  // hidden layer delta

  protected double[][] dW1  = null;  // input-hidden  layer accumulated weight delta
  protected double[][] dW2  = null;  // hidden-output layer accumulated weight delta

  protected double[]   dB1  = null;  // input-hidden  layer accumulated bias delta
  protected double[]   dB2  = null;  // hidden-output layer accumulated bias delta
  
  

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public static class Builder {
    // -----------------------------------------------------------------------------------

    protected int    nInp           = 0; // number of inputs
    protected int    nHid           = 0; // number of hidden nodes
    protected int    nOut           = 0; // number of outputs
    protected String configFilename = null;

    private   int    cfg_count      = 0;

    
    // ===================================================================================
    /** @brief Constructor
     */
    // -----------------------------------------------------------------------------------
    public Builder() {
      // ---------------------------------------------------------------------------------
    }

    
    // ===================================================================================
    /** @brief Add I/O
     *  @param ni number of inputs.
     *  @param no number of outputs.
     */
    // -----------------------------------------------------------------------------------
    public Builder io( int ni, int no ) {
      // ---------------------------------------------------------------------------------
      nInp       = ni;
      nOut       = no;
      nHid       = ni+no;
      cfg_count += 1;
      return this;
    }

    
    // ===================================================================================
    /** @brief Add hidden
     *  @param nh number of hidden nodes.
     */
    // -----------------------------------------------------------------------------------
    public Builder hidden( int nh ) {
      // ---------------------------------------------------------------------------------
      nHid = nh;
      return this;
    }

    
    // ===================================================================================
    /** @brief Add Filename
     *  @param fspc path to file containing configuration.
     */
    // -----------------------------------------------------------------------------------
    public Builder file( String fspc ) {
      // ---------------------------------------------------------------------------------
      configFilename = fspc;
      cfg_count     += 1;
      return this;
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public BPNNold build() {
      // ---------------------------------------------------------------------------------

      switch( cfg_count ) {
        case 0:
          logger.error( "BPNN.Builder requires either .io() or .file()" );
          System.exit(1);
          break;
          
        case 1:
          break;
          
        default:
          logger.error( "BPNN.Builder may not have both .io() and .file()" );
          System.exit(2);
          break;
      }

      BPNNold net = null;

      if ( 0 < nHid ) {
        net = new BPNNold();
        if ( 0 != net.init( nInp, nHid, nOut ) ) {
          logger.error( "Network failed to initialize" );
          System.exit(3);
        }
      } else {
        net = new BPNNold();
        if ( 0 != net.read( configFilename ) ) {
          logger.error( "Network failed to read from file: "+configFilename );
          System.exit(4);
        }
      }

      return net;
    }

  } // end class BPNN.Builder

  
  // =====================================================================================
  /** @brief Constructor
   */
  // -------------------------------------------------------------------------------------
  protected BPNNold( ) {
    // -----------------------------------------------------------------------------------
  }

  // =====================================================================================
  /** @brief Constructor
   *  @param number of inputs.
   *  @param number of hidden nodes.
   *  @param number of outputs.
   */
  // -------------------------------------------------------------------------------------
  protected int init( int ni, int nh, int no ) {
    // -----------------------------------------------------------------------------------
    nInp = ni;
    nHid = nh;
    nOut = no;
    
    dW1 = new double[nHid][nInp];
    dB1 = new double[nHid];
    w1  = new double[nHid][nInp];
    b1  = new double[nHid];
    d1  = new double[nHid];
    z1  = new double[nHid];
    a1  = new double[nHid];

    dW2 = new double[nOut][nHid];
    dB2 = new double[nOut];
    w2  = new double[nOut][nHid];
    b2  = new double[nOut];
    d2  = new double[nOut];
    z2  = new double[nOut];
    a2  = new double[nOut];

    return 0;
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void init_weights( double s ) {
    // -----------------------------------------------------------------------------------
    Dice dd = Dice.getInstance();

    for ( int node=0; node<nHid; node++ ) {
      b1[node] = s*dd.normal();
      for ( int con=0; con<nInp; con++ ) {
        w1[node][con] = s*dd.normal();
      }
    }
    
    for ( int node=0; node<nOut; node++ ) {
      b2[node] = s*dd.normal();
      for ( int con=0; con<nHid; con++ ) {
        w2[node][con] = s*dd.normal();
      }
    }

    reset();
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void reset( ) {
    // -----------------------------------------------------------------------------------
    Dice dd = Dice.getInstance();

    for ( int node=0; node<nHid; node++ ) {
      z1[node]  = 0.0e0;
      a1[node]  = 0.0e0;
      d1[node]  = 0.0e0;
      dB1[node] = 0.0e0;
      for ( int con=0; con<nInp; con++ ) {
        dW1[node][con] = 0.0e0;
      }
    }
    
    for ( int node=0; node<nOut; node++ ) {
      z2[node]  = 0.0e0;
      a2[node]  = 0.0e0;
      a2[node]  = 0.0e0;
      dB2[node] = 0.0e0;
      for ( int con=0; con<nHid; con++ ) {
        dW2[node][con] = 0.0e0;
      }
    }
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void execute( double y[], double x[] ) {
    // -----------------------------------------------------------------------------------

    // ----- hidden layer --------------------------------------
    for ( int node=0; node<nHid; node++ ) {
      double s = b1[node];
      for ( int con=0; con<nInp; con++ ) {
        s = s + ( w1[node][con] * x[con] );
      }
      a1[node] = 1.0e0 / ( 1.0e0 + Math.exp( -s ) );
    }

    // ----- output layer --------------------------------------
    for ( int node=0; node<nOut; node++ ) {
      double s = b2[node];
      for ( int con=0; con<nHid; con++ ) {
        s = s + ( w2[node][con] * a1[con] );
      }
      y[node] = 1.0e0 / ( 1.0e0 + Math.exp( -s ) );
    }
    
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void forward_pass( double x[] ) {
    // -----------------------------------------------------------------------------------

    // ----- hidden layer --------------------------------------
    for ( int node=0; node<nHid; node++ ) {
      double s = b1[node];
      for ( int con=0; con<nInp; con++ ) {
        s = s + ( w1[node][con] * x[con] );
      }
      z1[node] = s;
      a1[node] = 1.0e0 / ( 1.0e0 + Math.exp( -s ) );
    }

    // ----- output layer --------------------------------------
    for ( int node=0; node<nOut; node++ ) {
      double s = b2[node];
      for ( int con=0; con<nHid; con++ ) {
        s = s + ( w2[node][con] * a1[con] );
      }
      z2[node] = s;
      a2[node] = 1.0e0 / ( 1.0e0 + Math.exp( -s ) );
    }
    
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public double backwards_pass( double x[], double[] t ) {
    // -----------------------------------------------------------------------------------

    double cost = 0.0;

    // ----- output layer --------------------------------------
    for ( int node=0; node<nOut; node++ ) {
      double dif = a2[node] - t[node];
      cost += ( dif*dif );
      //                 |<-- derivative of activation --->|
      d2[node]   = dif * ( a2[node] * ( 1.0e0 - a2[node] ) );
      dB2[node] += d2[node];
      for ( int con=0; con<nHid; con++ ) {
        dW2[node][con] += ( d2[node] * a1[con] );
      }
    }

    // ----- hidden layer --------------------------------------
    for ( int node=0; node<nHid; node++ ) {
      double s = 0.0e0;
      for ( int fwd=0; fwd<nOut; fwd++ ) {
        s += ( d2[fwd] * w2[fwd][node] );
      }
      //               |<-- derivative of activation --->|
      d1[node]   = s * a1[node] * ( a1[node] * ( 1.0e0 - a1[node] ) );
      dB1[node] += d1[node];
      for ( int con=0; con<nInp; con++ ) {
        dW1[node][con] += ( d1[node] * x[con] );
      }
    }
    
    return cost / (double) nOut;
  }

  // =====================================================================================
  /** @brief Update the Weights.
   *  @param alf learning parameter.
   */
  // -------------------------------------------------------------------------------------
  public void update( double alf ) {
    // -----------------------------------------------------------------------------------

    // ----- hidden layer --------------------------------------
    for ( int node=0; node<nHid; node++ ) {
      b1[node] -= ( alf * dB1[node] );
      for ( int con=0; con<nInp; con++ ) {
        w1[node][con] -= ( alf * dW1[node][con] );
      }
    }
    
    // ----- output layer --------------------------------------
    for ( int node=0; node<nOut; node++ ) {
      b2[node] -= ( alf * dB2[node] );
      for ( int con=0; con<nHid; con++ ) {
        w2[node][con] -= ( alf * dW2[node][con] );
      }
    }
    
    reset();
  }

  // =====================================================================================
  /** @brief Read
   *  @param fspc path to file containing configuration.
   */
  // -------------------------------------------------------------------------------------
  public int read( String fspc ) {
    // -----------------------------------------------------------------------------------

    if ( null == fspc ) {
      logger.error( "Filename can not be null" );
      return 1;
    }
    
    InputStream is    = null;
    int         count = 0;
    
    try {
      is = new FileInputStream( new File( fspc ) );
    } catch( FileNotFoundException e ) {
      logger.error( "Cannot open [" + fspc + "] for reading" );
      return 2;
    }

    try {
      Scanner scan = new Scanner( is );

      int ni = scan.nextInt();
      int nh = scan.nextInt();
      int no = scan.nextInt();

      init( ni, nh, no );

      count = 2;
      for ( int i=0; i<nHid; i++ ) {
        b1[i] = scan.nextDouble();
        for ( int j=0; j<nInp; j++ ) {
          w1[i][j] = scan.nextDouble();
        }
        count += 1;
      }

      for ( int i=0; i<nOut; i++ ) {
        b2[i] = scan.nextDouble();
        for ( int j=0; j<nHid; j++ ) {
          w2[i][j] = scan.nextDouble();
        }
        count += 1;
      }

    } catch( InputMismatchException e1 ) {
      logger.error( "Table.read: value error on line: "+count );
      return 3;
    }

    try {
      is.close();
    } catch( IOException e ) {
      logger.error( "Cannot close [" + fspc + "] after reading" );
      return 4;
    }
   
    return 0;
  }

  
  // =====================================================================================
  /** @brief Write
   *  @param fspc path to file containing configuration.
   */
  // -------------------------------------------------------------------------------------
  public int write( String fspc ) {
    // -----------------------------------------------------------------------------------
    PrintStream ps = FileTools.openWrite( fspc );
    
    ps.format( "%d %d %d\n", nInp, nHid, nOut );
    
    for ( int i=0; i<nHid; i++ ) {
      ps.format( "%17.10e ", b1[i] );
      for ( int j=0; j<nInp; j++ ) {
        ps.format( " %17.10e", w1[i][j] );
      }
      ps.format( "\n" );
    }
    
    for ( int i=0; i<nOut; i++ ) {
      ps.format( "%17.10e ", b2[i] );
      for ( int j=0; j<nHid; j++ ) {
        ps.format( " %17.10e", w2[i][j] );
      }
      ps.format( "\n" );
    }
    
    ps.close();
    
    return 0;
  }


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void compare( PrintStream ps, BPNNold that ) {
    // -----------------------------------------------------------------------------------
    ps.format( "B1: %g\n", Math2.sumsq( this.b1, that.b1 ) );
    ps.format( "W1: %g\n", Math2.sumsq( this.w1, that.w1 ) );
    ps.format( "B2: %g\n", Math2.sumsq( this.b2, that.b2 ) );
    ps.format( "W2: %g\n", Math2.sumsq( this.w2, that.w2 ) );
  }
  

} // end class BPNN


// =======================================================================================
// **                                      B P N N                                      **
// ======================================================================== END FILE =====
