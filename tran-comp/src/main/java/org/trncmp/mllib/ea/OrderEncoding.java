// ====================================================================== BEGIN FILE =====
// **                             O R D E R E N C O D I N G                             **
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
 * @file OrderEncoding.java
 * <p>
 * Provides a derived class to handle order valued encodings.
 *
 * @date 2018-07-19
 *
 * ---------------------------------------------------------------------------------------
 *
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 *
 * @author Stephen W. Soliday
 * @date 2015-08-16
 */
// =======================================================================================

package org.trncmp.mllib.ea;

import java.io.PrintStream;

// =======================================================================================
/** @class OrderEncoding
 *  @brief Order valued encoding.
 *
 * Provides the model parameter encoding.
 */
// ---------------------------------------------------------------------------------------
public abstract class OrderEncoding extends Encoding {
  // -------------------------------------------------------------------------------------

  /** buffer containing the order valued parameters for this OrderEncoding. */
  protected int[] data = null;

  /** number of values in this OrderEncoding. */
  protected int data_len = 0;

  /** variable used to count the elements swaped */
  protected int noise_count = 0;

  protected org.trncmp.mllib.Entropy ent = org.trncmp.mllib.Entropy.getInstance();


  // =====================================================================================
  /** @brief Constructor.
   *  @param n number of elements in the OrderEncoding.
   *
   *  Allocate space for this OrderEncoding.
   */
  // -------------------------------------------------------------------------------------
  public OrderEncoding( int n ) {
    // -----------------------------------------------------------------------------------
    data     = new int[n];
    data_len = n;
    zero();       // this actually loads 0 1 2 3 4 ...
    randomize();  // this scrambles the list.
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
   *  @param idx index of the desired OrderEncoding element.
   *  @return OrderEncoding element indexed by idx.
   */
  // -------------------------------------------------------------------------------------
  public int get( int idx ) {
    // -----------------------------------------------------------------------------------
    return data[idx];
  }

  
  // =====================================================================================
  /** @brief Set
   *  @param idx index of the OrderEncoding element to be set.
   *  @param val value to set OrderEncoding element.
   *  @return value to be set.
   */
  // -------------------------------------------------------------------------------------
  public int set( int idx, int val ) {
    // -----------------------------------------------------------------------------------
    return ( data[idx] = val );
  }


  // =====================================================================================
  /** @brief Zero.
   *
   *  Reset the list to 0 1 2 3 4 ... n-1
   */
  // -------------------------------------------------------------------------------------
  public void zero() {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<data_len; i++ ) {
      data[i] = i;
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
      throw new NullPointerException("OrderEncoding.copy( src==NULL )");
    }

    OrderEncoding E = (OrderEncoding)ap;

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
    ent.scramble( data, 2*data_len );
  }

  
  // =====================================================================================
  /** @brief Bracket.
   *
   *  Create a forward or reverse rotated list.
   */
  // -------------------------------------------------------------------------------------
  public void bracket() {
    // -----------------------------------------------------------------------------------

    int start = ent.index( data_len );

    if ( ent.bool() ) {
      for ( int i=0; i<data_len; i++ ) {
        data[i] = (start + i) % data_len;
      }
    } else {
      for ( int i=0; i<data_len; i++ ) {
        data[i] = (data_len + start - i) % data_len;
      }
    }
        
  }

  
  // =====================================================================================
  /** @brief Noise.
   *  @param scale  scale of the noise.
   *
   *  Add noise as a various amounts of scrambling. As scale varies from 0 to 1.0
   *  the number of swap operations vary from 0 to 2x the order.
   */
  // -------------------------------------------------------------------------------------
  public void noise( double scale ) {
    // -----------------------------------------------------------------------------------
    noise_count = ( int ) Math.floor( ( double )data_len * 2.0 * scale );

    ent.scramble( data, noise_count );
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
  /** @brief Mutation.
   *  @param S      pointer to original Encoding.
   *  @param perc   percentage of elements that get mutated.
   *  @param scale  scale of the noise
   *  @return number of elements mutated.
   *
   *  @note The go/no-go decision was made higher up. At this point we are going
   *        to perform Mutation.
   */
  // -------------------------------------------------------------------------------------
  public abstract int mutate( Encoding S, double perc, double scale );


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
  public abstract void crossover( Encoding ac2,
                                  Encoding ap1, Encoding ap2 );


} // end class OrderEncoding

// =======================================================================================
// **                             O R D E R E N C O D I N G                             **
// ======================================================================== END FILE =====
