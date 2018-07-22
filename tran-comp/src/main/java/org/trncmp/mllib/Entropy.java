// ====================================================================== BEGIN FILE =====
// **                                   E N T R O P Y                                   **
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
// @file Entropy.java
// <p>
// Provides a singlton interface for UGA Entropy.
//
// @author Stephen W. Soliday
// @date 2015-08-20
//
// =======================================================================================

package org.trncmp.mllib;

import java.util.Random;

import org.trncmp.lib.FileTools;
import org.trncmp.lib.SeedMatter;

// =======================================================================================
public class Entropy {
  // -------------------------------------------------------------------------------------

  /** lazy constructor */
  static Entropy singleton_instance = null;

  /** Use the built-in random generator for now */
  private Random builtin = null;
  

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
    double x, y, yt, d, r, m;

    r = (high-low);

    do {
      x  = r*uniform() + low;
      y  = uniform();
      d  = (x - mean)/std;
      yt = Math.exp(-d*d/2.0e0);
    } while (y > yt);

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
