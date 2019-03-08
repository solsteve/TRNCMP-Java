// ====================================================================== BEGIN FILE =====
// **                           I N T E G E R E N C O D I N G                           **
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
 * @file IntegerEncoding.java
 * <p>
 * Provides a derived class to handle integer valued encodings.
 *
 * @date 2018-07-18
 *
 * ---------------------------------------------------------------------------------------
 *
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 *
 * @author Stephen W. Soliday
 * @date 2015-08-15
 */
// =======================================================================================

package org.trncmp.mllib.ea;

import java.io.PrintStream;

// =======================================================================================
/** @class IntegerEncoding
 *  @brief Integer valued encoding.
 *
 * Provides the model parameter encoding.
 */
// ---------------------------------------------------------------------------------------
public class IntegerEncoding extends Encoding {
  // -------------------------------------------------------------------------------------

  /** buffer containing the integer valued parameters for this IntegerEncoding. */
  protected int[] data = null;

  /** number of values in this IntegerEncoding. */
  protected int data_len = 0;

  /** Minumum value of this parameter's elements. */
  protected int min_int = 0;

  /** Maxumum value of this parameter's elements. */
  protected int max_int = 1000;

  protected org.trncmp.mllib.Entropy ent = org.trncmp.mllib.Entropy.getInstance();


  // =====================================================================================
  /** @brief Constructor.
   *  @param n number of elements in the IntegerEncoding.
   *
   *  Allocate space for this IntegerEncoding.
   */
  // -------------------------------------------------------------------------------------
  public IntegerEncoding( int n ) {
    // -----------------------------------------------------------------------------------
    data     = new int[n];
    data_len = n;
    min_int  = 0;
    max_int  = 1000;
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
   *  @param idx index of the desired IntegerEncoding element.
   *  @return IntegerEncoding element indexed by idx.
   */
  // -------------------------------------------------------------------------------------
  public int get( int idx ) {
    // -----------------------------------------------------------------------------------
    return data[idx];
  }

  
  // =====================================================================================
  /** @brief Set
   *  @param idx index of the IntegerEncoding element to be set.
   *  @param val value to set IntegerEncoding element.
   *  @return value to be set.
   */
  // -------------------------------------------------------------------------------------
  public int set( int idx, int val ) {
    // -----------------------------------------------------------------------------------
    return ( data[idx] = val );
  }


  // =====================================================================================
  /** @brief Set maximum.
   *  @param m maximum integer value.
   *
   *  Set the upper bound for integer values. This will be an inclusive number.
   */
  // -------------------------------------------------------------------------------------
  public void setMax( int m ) {
    // -----------------------------------------------------------------------------------
    max_int = m;
  }

  // =====================================================================================
  /** @brief Set minimum.
   *  @param m minimum integer value.
   *
   *  Set the lower bound for integer values. This will be an inclusive number.
   */
  // -------------------------------------------------------------------------------------
  public void setMin( int m ) {
    // -----------------------------------------------------------------------------------
    min_int = m;
  }


  // =====================================================================================
  /** @brief Zero.
   *
   *  Clear the values of the elements. If zero is greater then max_int set to max_int.
   *  If zero is less then min_int set to min_int. If zero if between min_int and max_int
   *  then set to zero.
   */
  // -------------------------------------------------------------------------------------
  public void zero() {
    // -----------------------------------------------------------------------------------
    int zval = 0;
    if ( min_int > zval ) { zval = min_int; }
    if ( max_int < zval ) { zval = max_int; }
    for ( int i=0; i<data_len; i++ ) {
      data[i] = zval;
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
      throw new NullPointerException("IntegerEncoding.copy( src==NULL )");
    }

    IntegerEncoding E = (IntegerEncoding)ap;

    for ( int i=0; i<data_len; i++ ) {
      this.data[i] = E.data[i];
    }    
  }

  // =====================================================================================
  /** @brief Gaussian.
   *  @param E reference to an Entropy instance.
   *  @param A minimum integer value.
   *  @param B maximum integer value.
   *  @param M mean integer value.
   *  @param s standard deviation.
   *  @return Gaussian distributed integer value.
   */
  // -------------------------------------------------------------------------------------
  static int gaussian( org.trncmp.mllib.Entropy E, int A, int B, int M, double s ) {
    // -----------------------------------------------------------------------------------
    int x = (int) Math.floor( E.gauss( (double)A, (double)B, (double)M, s ) + 0.5 );
    if ( A > x ) { x = A; }
    if ( B < x ) { x = B; }
    return x;
  }

  
  // =====================================================================================
  /** @brief Randomize.
   *
   *  Fill the Encoding with uniformly distributed random values.
   */
  // -------------------------------------------------------------------------------------
  public void randomize() {
    // -----------------------------------------------------------------------------------
    int d = max_int - min_int + 1;
    for ( int i=0; i<data_len; i++ ) {
      data[i] = min_int + ent.index( d );
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
      data[i] = ent.bool() ? min_int : max_int;
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
      //System.out.println( "min:"+min_int+" max:"+max_int+" mu:"+data[i]+" sig:"+sigma );
      data[i] = gaussian( ent, min_int, max_int, data[i], sigma );
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
    return format( "%d", " " );
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
  public static void parametric( int[] C1, int[] C2,
                                 int[] P1, int[] P2, double t ) {
    // -----------------------------------------------------------------------------------
    int n = C1.length;
    for ( int i=0; i<n; i++ ) {
      int x1 = (int) Math.floor( t * ( double )( P2[i] - P1[i] ) + 0.5 ) + P1[i];
      int x2 = (int) Math.floor( (1.0-t) * ( double )( P2[i] - P1[i] ) + 0.5 ) + P1[i];
      //      int x2 = (int) Math.floor( t * ( double )( P1[i] - P2[i] ) + 0.5 ) + P2[i];
      if ( P1[i] > x1 ) { x1 = P1[i]; }
      if ( P2[i] < x1 ) { x1 = P2[i]; }
      if ( P1[i] > x2 ) { x2 = P1[i]; }
      if ( P2[i] < x2 ) { x2 = P2[i]; }
      C1[i] = x1;
      C2[i] = x2;
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

    IntegerEncoding p1 = (IntegerEncoding)ap1;
    IntegerEncoding p2 = (IntegerEncoding)ap2;
    IntegerEncoding c1 = this;
    IntegerEncoding c2 = (IntegerEncoding)ac2;

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

    IntegerEncoding src = (IntegerEncoding)S;

    int    count = 0;
    double sigma = (double)(max_int - min_int + 1) *  scale / 6.0;

    for ( int i=0; i<data_len; i++ ) {
      if ( ent.bool( perc ) ) {
        this.data[i] = gaussian( ent, min_int, max_int, src.data[i], sigma );
        count++;
      } else {
        this.data[i] = src.data[i];
      }
    }
    
    return count;
  }


} // end class IntegerEncoding

// =======================================================================================
// **                           I N T E G E R E N C O D I N G                           **
// ======================================================================== END FILE =====
