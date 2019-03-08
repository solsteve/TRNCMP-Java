// ====================================================================== BEGIN FILE =====
// **                                   E N T R O P Y                                   **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2017, Stephen W. Soliday                                           **
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
 * @file Entropy.java
 *  Provides Source of random numbers.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-06-18
 */
// =======================================================================================

package org.trncmp.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class Entropy {
  // -----------------------------------------------------------------------------------
    
  private static final Logger logger = LogManager.getLogger();

  public static final int BUILTIN_ENGINE    =  1;
  public static final int SIMPLE_LCG_ENGINE =  2;
    
  public static final int DEFAULT_ENGINE    =  2;
  public static final int NO_ENGINE         = -1;
  private EntropyEngine eng    = null;

  // ===================================================================================
  /** @brief Constructor.
   *
   *  Create an instance of the EntropyEngine.
   */
  // -----------------------------------------------------------------------------------
  public Entropy() {
    // -------------------------------------------------------------------------------	
    this.create_engine( DEFAULT_ENGINE );
  }
    
  // ===================================================================================
  /** @brief Constructor.
   *
   *  Create an instance of the EntropyEngine.
   */
  // -----------------------------------------------------------------------------------
  public Entropy( int etype ) {
    // -------------------------------------------------------------------------------
    this.create_engine( etype );
  }


  // ===================================================================================
  /** @brief Constructor.
   *
   *  Create an instance of the EntropyEngine.
   */
  // -----------------------------------------------------------------------------------
  private boolean create_engine( int etype ) {
    // -------------------------------------------------------------------------------
    if ( SIMPLE_LCG_ENGINE == etype ) {
      eng = new EntropyEngine_simple_lcg();
      return false;
    }

    if ( BUILTIN_ENGINE == etype ) {
      eng = new EntropyEngine_builtin();
      return false;
    }

    logger.fatal( "Unknown engine id" + etype );
    return true;	
  }


  // ===================================================================================
  /** @brief Reset.
   *
   *  Reset the entropy engine.
   */
  // -----------------------------------------------------------------------------------
  public void reset() {
    // -------------------------------------------------------------------------------
    eng.reset();
  }

    
  // ===================================================================================
  /** @brief Name.
   *  @return name of the engine.
   *
   *  Name of the entropy engine.
   */
  // -----------------------------------------------------------------------------------
  public String name() {
    // -------------------------------------------------------------------------------
    return eng.name();
  }

    
  // ===================================================================================
  /** @brief Seed.
   *
   *  Generate a random seed using an OS specific source of entropy
   */
  // -----------------------------------------------------------------------------------
  public int seed_size() {
    // -------------------------------------------------------------------------------
    return eng.seed_size();
  }

  // ===================================================================================
  /** @brief Seed.
   *
   *  Generate a random seed using an OS specific source of entropy
   */
  // -----------------------------------------------------------------------------------
  public void seed_set() {
    // -------------------------------------------------------------------------------
    int n = eng.seed_size();
    byte[] sm = new byte[n];

    FileTools.urandom( sm );

    this.seed_set( sm, n );
  }

  // ===================================================================================
  /** @brief Seed.
   *  @param sm byte array containing seed material.
   *  @param n  amount of the seed material to use.
   */
  // -----------------------------------------------------------------------------------
  public void seed_set( byte[] sm, int n ) {
    // -------------------------------------------------------------------------------
    eng.seed_set( sm, n );
  }

  // ===================================================================================
  /** @brief Random Real.
   *  @return double precision number in the range 0 <= x < 1.0e0
   */
  // -----------------------------------------------------------------------------------
  public double get_real() {
    // -------------------------------------------------------------------------------
    return eng.get_real();
  }

  // ===================================================================================
  /** @brief Random Integer.
   *  @return integer random number.
   */
  // -----------------------------------------------------------------------------------
  public long get_integer() {
    // -------------------------------------------------------------------------------
    return eng.get_integer();
  }

  // ===================================================================================
  /** @brief Maximum Integer.
   *  @return maximum integer representation.
   */
  // -----------------------------------------------------------------------------------
  public long max_integer() {
    // -------------------------------------------------------------------------------
    return eng.max_integer();
  }

}

// =======================================================================================
// **                                   E N T R O P Y                                   **
// ======================================================================== END FILE =====

