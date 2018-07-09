// ====================================================================== BEGIN FILE =====
// **                                  E N C O D I N G                                  **
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
// @file Encoding.java
// <p>
// Provides a common interface for UGA Encodings.
//
// @author Stephen W. Soliday
// @date 2015-08-20
//
// =======================================================================================

package org.trncmp.mllib.ea;

import java.io.PrintStream;

// =======================================================================================
/** @class Encoding
 *  @brief Model encoding.
 *
 * Provides the abstract model parameter encoding.
 */
// ---------------------------------------------------------------------------------------
public abstract class Encoding {
  // -------------------------------------------------------------------------------------

  /** This constant is used with noise and mutation. It represents the number of standard
   *  deviations that are covered by the range of parameter values when 'scale' is set
   *  to 1. Example: if the mean is -1.0 and the scale is 1.0 then the opposite end
   *  parameter 1.0 is N_SIGMA_SCALE * sigma away.
   */
  public static final double N_SIGMA_SCALE = 4.0e0;
  

  // =====================================================================================
  /** @brief Zero.
   *
   *  Clear the values of the elements.
   */
  // -------------------------------------------------------------------------------------
  public abstract void zero( );

  
  // =====================================================================================
  /** @brief Copy.
   *  @param p pointer to a source Encoding.
   *
   *  Perform an element by element copy of the source Encoding.
   */
  // -------------------------------------------------------------------------------------
  public abstract void copy( Encoding p );

  
  // =====================================================================================
  /** @brief Randomize.
   *
   *  Fill the Encoding with uniformly distributed random values.
   */
  // -------------------------------------------------------------------------------------
  public abstract void randomize( );

  
  // =====================================================================================
  /** @brief Bracket.
   *
   *  Fill the string with {min max} values, evenly distributed.
   */
  // -------------------------------------------------------------------------------------
  public abstract void bracket( );

  
  // =====================================================================================
  /** @brief Noise.
   *  @param scale  scale of the noise (see {@link Encoding.N_SIGMA_SCALE})
   *
   *  Add Gaussian random noise to each element.
   */
  // -------------------------------------------------------------------------------------
  public abstract void noise( double scale );


  // =====================================================================================
  /** @brief Format Encoding as a String.
   *  @param fmt edit descriptor of each element.
   *  @param dlm delimeter separating each element.
   *  @return white space separated values of the Metric elements
   */
  // -------------------------------------------------------------------------------------
  public abstract String format( String fmt, String dlm );


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
  public abstract void crossover( Encoding ac2, Encoding ap1, Encoding ap2 );

  
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
  public abstract int mutate( Encoding S, double perc, double scale );

  

  
  // =====================================================================================
  /** @brief Convert Encoding to String.
   *  @param fmt edit descriptor of each element.
   *  @return white space separated values of the Encoding elements
   */
  // -------------------------------------------------------------------------------------
@Override
public String toString() {
    // -----------------------------------------------------------------------------------
    return format( "%11.4e", " " );
  }

  
} // end class Encoding


// =======================================================================================
// **                                  E N C O D I N G                                  **
// ======================================================================== END FILE =====
