// ====================================================================== BEGIN FILE =====
// **                                  E N C O D I N G                                  **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2018, L3 Technologies Advanced Programs                            **
// **                      One Wall Street #1, Burlington, MA 01803                     **
// **                                                                                   **
// **  -------------------------------------------------------------------------------  **
// **                                                                                   **
// **  This file, and associated source code, is not free software; you may not         **
// **  redistribute it and/or modify it. This file is part of a research project        **
// **  that is in a development phase. No part of this research has been publicly       **
// **  distributed. Research and development for this project has been at the sole      **
// **  cost in both time and funding by L3 Technologies Advanced Programs.              **
// **                                                                                   **
// **  Any reproduction of computer software or portions thereof marked with this       **
// **  legend must also reproduce the markings.  Any person who has been provided       **
// **  access to such software must promptly notify L3 Technologies Advanced Programs.  **
// **                                                                                   **
// ----- Modification History ------------------------------------------------------------
/**
 * @file Encoding.java
 * <p>
 * Provides a common interface for UGA Encodings.
 *
 * @date 2018-07-10
 *
 * ---------------------------------------------------------------------------------------
 *
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 *
 * @author Stephen W. Soliday
 * @date 2015-08-08
 */
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
  /** @brief Convert Encoding to String.
   *  @param fmt edit descriptor of each element.
   *  @return white space separated values of the Encoding elements
   */
  // -------------------------------------------------------------------------------------
  public abstract String toString();

    
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



  
} // end class Encoding


// =======================================================================================
// **                                  E N C O D I N G                                  **
// ======================================================================== END FILE =====
