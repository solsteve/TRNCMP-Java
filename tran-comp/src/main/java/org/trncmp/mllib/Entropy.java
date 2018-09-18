// ====================================================================== BEGIN FILE =====
// **                                   E N T R O P Y                                   **
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
 * @file Entropy.java
 * <p>
 * Provides a singlton interface for UGA Entropy.
 *
 * @date 2018-07-09
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

package org.trncmp.mllib;

import java.util.Random;

import org.trncmp.lib.FileTools;
import org.trncmp.lib.SeedMatter;
import org.trncmp.lib.Math2;

// =======================================================================================
public class Entropy {
  // -------------------------------------------------------------------------------------

  /** lazy constructor */
  static Entropy singleton_instance = null;

  /** Use the built-in random generator for now */
  private Random builtin = null;
  
  private boolean have_spare = false; //< state flag      for Box-Muller.
  private double  rand1, rand2;       //< state variables for Box-Muller.

  // =====================================================================================
  /** @breif Get Instance
   *  @return pointer to the singlton instance of the Entropy class.
   */
  // -------------------------------------------------------------------------------------
  public static Entropy getInstance() {
    // -----------------------------------------------------------------------------------
    if ( null == singleton_instance ) {
      singleton_instance = new Entropy();
    }
    return Entropy.singleton_instance;
  }

  // =====================================================================================
  /** @brief Private Constructor.
   */
  // -------------------------------------------------------------------------------------
  private Entropy( ) {
    // -----------------------------------------------------------------------------------
    builtin = new Random();
    seed_set();
  }


  // =====================================================================================
  /** @brief Seed Size.
   *  @return the native seed size for the chosen entropy engine.
   */
  // -------------------------------------------------------------------------------------
  public int seed_size() {
    // -----------------------------------------------------------------------------------
    return 8;
  }

  
  // =====================================================================================
  /** @brief Seed.
   */
  // -------------------------------------------------------------------------------------
  public void seed_set() {
    // -----------------------------------------------------------------------------------
    int n = this.seed_size();
    byte[] sm = new byte[n];

    FileTools.urandom( sm );

    this.seed_set( sm );
  }

  
  // =====================================================================================
  /** @brief Seed.
   *  @param sm pointer to a SeedMatter class.
   */
  // -------------------------------------------------------------------------------------
  public void seed_set( SeedMatter sm ) {
    // -----------------------------------------------------------------------------------
    builtin.setSeed( java.nio.ByteBuffer.wrap(
        sm.get_bytes(seed_size())).getLong() );
  }


  // =====================================================================================
  /** @brief Seed.
   *  @param sdat byte array containing seed material.
   */
  // -------------------------------------------------------------------------------------
  public void seed_set( byte[] sdat ) {
    // -----------------------------------------------------------------------------------
    seed_set( new SeedMatter( sdat ) );
  }

  






  // =====================================================================================
  /** @brief Random Integer.
   *  @param  max_index upper bound.
   *  @return integer random number distributed uniformally  [0,max_index).
   *
   *  Uniformly distributed integer in the range [0,max_index)
   */
  // -------------------------------------------------------------------------------------
  public int index( int max_index ) {
    // -----------------------------------------------------------------------------------
    int i = 0;
    do {
      i = builtin.nextInt();
    } while( i < 0 );

    return i % max_index;
  }

  
  // =====================================================================================
  /** @brief Random Real.
   *  @return real random number distributed uniformally.
   *
   *  Uniformly distributed reals in the range [0,1)
   */
  // -------------------------------------------------------------------------------------
  public double uniform( ) {
    // -----------------------------------------------------------------------------------
    return builtin.nextDouble();
  }

  
  // =====================================================================================
  /** @brief Boolean Real.
   *  @return boolean random number distributed uniformally.
   *
   *  Uniformly distributed boolean in the range {false,true}
   */
  // -------------------------------------------------------------------------------------
  public boolean bool() {
    // -----------------------------------------------------------------------------------
    return (5.0e-1 < uniform()) ? false : true;
  }

  
  // =====================================================================================
  /** @brief Boolean Real.
   *  @return boolean random number distributed uniformally.
   *
   *  Weighted distributed boolean in the range {(1-wgt)*false, (wgt)*true}
   */
  // -------------------------------------------------------------------------------------
  public boolean bool( double wgt ) {
    // -----------------------------------------------------------------------------------
    return (wgt < uniform()) ? false : true;
  }


  // =====================================================================================
  /** @brief Normal distribution.
   *  @return a number with a normal distribution..
   *
   *  Use Box-Muller algorithm to generate numbers with a normal distribution.
   *  The mean is 0.0, the standard deviation is 1.0 and limits +/- 6.15 * StdDev,
   *  based on experimental results of rolling 1 trillion values.
   */
  // -------------------------------------------------------------------------------------
  protected double box_muller() {
    // -----------------------------------------------------------------------------------
    if ( have_spare ) {
      have_spare = false;
      return Math.sqrt(rand1) * Math.sin(rand2);
    }
  
    have_spare = true;
  
    rand1 = uniform();
  
    if ( rand1 < 1e-100 ) { rand1 = 1e-100; }
  
    rand1 = -Math2.N_TWO * Math.log(rand1);
    rand2 =  Math2.N_2PI * uniform();
  
    return Math.sqrt(rand1) * Math.cos(rand2);
  }
  
  
  // =====================================================================================
  /** @brief Gaussian distribution.
   *  @param low  minimum value to return
   *  @param high maximum value to return
   *  @param mean mean
   *  @param std  one standard deviation        
   *  @return a number with a Gaussian distribution.
   *
   *  use Monte Carlo to approximate a Gaussian distribution
   */
  // -------------------------------------------------------------------------------------
  public double gauss( double low,  double high,
                       double mean, double std ) {
    // -----------------------------------------------------------------------------------

    if ( mean < low  ) { return low;  }
    if ( mean > high ) { return high; }

    double x = mean + std*box_muller();

    if ( x < low  ) { return low;  }
    if ( x > high ) { return high; }

    return x;
  }

  // =====================================================================================
  /** @brief Scramble Array.
   *  @param ary pointer to an array.
   *  @param exc number of exchanges to perform
   *
   *  Exchange random elements of the array.
   */
  // -------------------------------------------------------------------------------------
  public void scramble( int[] ary, int exc ) {
    // -----------------------------------------------------------------------------------
    int n=ary.length;
    int a=0;
    int b=1;
    try {
      for ( int i=0; i<exc; i++ ) {
        do {
          a = index( n );
          b = index( n );
        } while( a == b );
        int t  = ary[a];
        ary[a] = ary[b];
        ary[b] = t;
      }
    } catch (java.lang.ArrayIndexOutOfBoundsException e) {
      System.out.format( "\n%s\n\n", e.toString() );
      System.out.format( "LOOP:%d LEN:%d A:%d B:%d\n\n", exc, n, a, b );
      System.exit(1);
    }
  }


} // end class Entropy


// =======================================================================================
// **                                   E N T R O P Y                                   **
// ======================================================================== END FILE =====
