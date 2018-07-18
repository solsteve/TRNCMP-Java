// ====================================================================== BEGIN FILE =====
// **                              R E A L E N C O D I N G                              **
// =======================================================================================
// **                                                                                   **
// **  This file is part of the TRNCMP Research Library. (formerly SolLib)              **
// **                                                                                   **
// **  Copyright (c) 2015, Stephen W. Soliday                                           **
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
//
// @file RealEncoding.java
// <p>
// Provides a derived class to handle real valued encodings.
//
// @author Stephen W. Soliday
// @date 2015-08-20
//
// =======================================================================================

package org.trncmp.mllib.ea;

import java.io.PrintStream;

// =======================================================================================
/** @class RealEncoding
 *  @brief Real valued encoding.
 *
 * Provides the model parameter encoding.
 */
// ---------------------------------------------------------------------------------------
public class RealEncoding extends Encoding {
  // -------------------------------------------------------------------------------------

  /** buffer containing the real valued parameters for this RealEncoding. */
  protected double[] data = null;

  /** number of values in this RealEncoding. */
  protected int data_len = 0;

  protected org.trncmp.mllib.Entropy ent = org.trncmp.mllib.Entropy.getInstance();


  // =====================================================================================
  /** @brief Constructor.
   *  @param n number of elements in the RealEncoding.
   *
   *  Allocate space for this RealEncoding.
   */
  // -------------------------------------------------------------------------------------
  public RealEncoding( int n ) {
    // -----------------------------------------------------------------------------------
    data     = new double[n];
    data_len = n;
    randomize();
  }


  // =====================================================================================
  /** @brief Size.
   *  @return number of elements being used.
   */
  // -------------------------------------------------------------------------------------
  public int size() {
    // -----------------------------------------------------------------------------------
    return data_len;
  }


  // =====================================================================================
  /** @brief Get
   *  @param idx index of the desired RealEncoding element.
   *  @return RealEncoding element indexed by idx.
   */
  // -------------------------------------------------------------------------------------
  public double get( int idx ) {
    // -----------------------------------------------------------------------------------
    return data[idx];
  }

  
  // =====================================================================================
  /** @brief Set
   *  @param idx index of the RealEncoding element to be set.
   *  @param val value to set RealEncoding element.
   *  @return value to be set.
   */
  // -------------------------------------------------------------------------------------
  public double set( int idx, double val ) {
    // -----------------------------------------------------------------------------------
    return ( data[idx] = val );
  }


  // =====================================================================================
  /** @brief Zero.
   *
   *  Clear the values of the elements.
   */
  // -------------------------------------------------------------------------------------
  public void zero() {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<data_len; i++ ) {
      data[i] = 0.0e0;
    }    
  }
  
  // =====================================================================================
  /** @brief Copy.
   *  @param ap pointer to a source Encoding.
   *
   *  Perform an element by element copy of the source Encoding.
   */
  // -------------------------------------------------------------------------------------
  public void copy( Encoding ap ) {
    // -----------------------------------------------------------------------------------
    if ( null == ap ) {
      throw new NullPointerException("RealEncoding.copy( src==NULL )");
    }

    RealEncoding E = (RealEncoding)ap;

    for ( int i=0; i<data_len; i++ ) {
      this.data[i] = E.data[i];
    }    
  }

  
  // =====================================================================================
  /** @brief Randomize.
   *
   *  Fill the Encoding with uniformly distributed random values.
   */
  // -------------------------------------------------------------------------------------
  public void randomize() {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<data_len; i++ ) {
      data[i] = 2.0e0 * ent.uniform() - 1.0e0;
    }
  }

  
  // =====================================================================================
  /** @brief Bracket.
   *
   *  Fill the string with {min max} values, evenly distributed.
   */
  // -------------------------------------------------------------------------------------
  public void bracket() {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<data_len; i++ ) {
      data[i] = ent.bool() ? 1.0e0: -1.0e0;
    }
  }

  
  // =====================================================================================
  /** @brief Noise.
   *  @param scale  scale of the noise (see {@link Encoding.N_SIGMA_SCALE})
   *
   *  Add Gaussian random noise to each element.
   */
  // -------------------------------------------------------------------------------------
  public void noise( double scale ) {
    // -----------------------------------------------------------------------------------
    double sigma = 2.0e0 * scale / N_SIGMA_SCALE;

    for ( int i=0; i<data_len; i++ ) {
      data[i] = ent.gauss( -1.0e0, 1.0e0, data[i], sigma );
    }
  }


  // =====================================================================================
  /** @brief Format Encoding as a String.
   *  @param fmt edit descriptor of each element.
   *  @param dlm delimeter separating each element.
   *  @return white space separated values of the Metric elements
   */
  // -------------------------------------------------------------------------------------
  public String format( String fmt, String dlm ) {
    // -----------------------------------------------------------------------------------
    if ( 0 == data_len ) { return "{empty}"; }
    
    StringBuffer buffer = new StringBuffer();

    for ( int i=0; i<data_len; i++ ) {
      if ( 0 < i ) { buffer.append( dlm ); }
      buffer.append( String.format( fmt, data[i] ) );
    }

    return buffer.toString();
  }

  
  // =====================================================================================
  /** @brief Convert Encoding to String.
   *  @param fmt edit descriptor of each element.
   *  @return white space separated values of the Encoding elements
   */
  // -------------------------------------------------------------------------------------
  public String toString() {
    // -----------------------------------------------------------------------------------
    return format( "%11.4e", " " );
  }

  







  // =====================================================================================
  /** Parametric.
   *  Compute the parametric value in the range (A, B) for parameter t
   * @param C1 array representing the t=0 dependent value
   * @param C2 array representing the t=1 dependent value
   * @param P1 array representing the t=0 independent value
   * @param P2 array representing the t=1 independent value
   * @param t independent parameter 0 <= t <= 1
   */
  // -------------------------------------------------------------------------------------
  public static void parametric( double[] C1, double[] C2,
                                 double[] P1, double[] P2, double t ) {
    // -----------------------------------------------------------------------------------
    int n = C1.length;
    for ( int i=0; i<n; i++ ) {
      double a = P1[i];
      double b = P2[i];
      C1[i] = t * ( b - a ) + a;
      C2[i] = t * ( a - b ) + b;
    }
  }


  // =====================================================================================
  /** @brief Crossover.
   *  @param ac2 pointer to child number two.
   *  @param ap1 pointer to parent number one.
   *  @param ap2 pointer to parent number two.
   *  @return true if crossover took place.
   *
   *  (this) is child number one.
   *
   *  @note The go/no-go decision was made higher up. At this point we are going
   *        to perform Crossover.
   */
  // -------------------------------------------------------------------------------------
  public void crossover( Encoding ac2,
                         Encoding ap1, Encoding ap2 ) {
    // -----------------------------------------------------------------------------------
    if ( null == ap1 ) {
      throw new NullPointerException("Parent 1 ( NULL )");
    }
      
    if ( null == ap2 ) {
      throw new NullPointerException("Parent 2 ( NULL )");
    }

    if ( null == ac2 ) {
      throw new NullPointerException("Child 2 ( NULL )");
    }

    RealEncoding p1 = (RealEncoding)ap1;
    RealEncoding p2 = (RealEncoding)ap2;
    RealEncoding c1 = this;
    RealEncoding c2 = (RealEncoding)ac2;

    parametric( c1.data, c2.data, p1.data, p2.data, ent.uniform() );
  }


  // =====================================================================================
  /** @brief Mutation.
   *  @param S      pointer to original Encoding.
   *  @param perc   percentage of elements that get mutated.
   *  @param scale  scale of the noise (see {@link Encoding.N_SIGMA_SCALE})
   *  @return number of elements mutated.
   *
   *  @note The go/no-go decision was made higher up. At this point we are going
   *        to perform Mutation.
   */
  // -------------------------------------------------------------------------------------
  public int mutate( Encoding S, double perc, double scale ) {
    // -----------------------------------------------------------------------------------
    if ( null == S ) {
      throw new NullPointerException("source ( NULL )");
    }

    RealEncoding src = (RealEncoding)S;

    int    count = 0;
    double sigma = 2.0e0 * scale / N_SIGMA_SCALE;

    for ( int i=0; i<data_len; i++ ) {
      if ( ent.bool( perc ) ) {
        this.data[i] = ent.gauss( -1.0e0, 1.0e0, src.data[i], sigma );
        count++;
      } else {
        this.data[i] = src.data[i];
      }
    }
    
    return count;
  }


} // end class RealEncoding

// =======================================================================================
// **                              R E A L E N C O D I N G                              **
// ======================================================================== END FILE =====
