// ====================================================================== BEGIN FILE =====
// **                                      D I C E                                      **
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
 * @file Dice.java
 *  Provides Interface and methods for random number utilities.
 *  There is only one instance of this class per thread.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-08-24
 */
// =======================================================================================

package org.trncmp.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class Dice {
  // -------------------------------------------------------------------------------------

  private static final Logger logger = LogManager.getLogger();

  private static int  dice_singleton_entropy_type = Entropy.NO_ENGINE;
  private static Dice dice_singleton_instance     = null;

  private Entropy ent_engine = null;
  private boolean have_spare = false; //< state flag      for Box-Muller.
  private double  rand1, rand2;       //< state variables for Box-Muller.


  // =====================================================================================
  /** @brief Set Entropy Engine Type.
   *  @param etype entropy engine type.
   *  @see   Entropy
   *  @note this may only be called befor the first call to getInstance()
   */
  // -------------------------------------------------------------------------------------
  public static void set_entropy_engine( int etype ) {
    // -----------------------------------------------------------------------------------
    synchronized( Dice.class ) {
      if ( null == Dice.dice_singleton_instance ) {
        if ( Entropy.NO_ENGINE != Dice.dice_singleton_entropy_type ) {
          logger.info( "multiple call to: Dice.set_entropy_engine("+etype+")" );
          logger.info( "   last call was: Dice.set_entropy_engine("+
                       Dice.dice_singleton_entropy_type+")" );
                       }
              Dice.dice_singleton_entropy_type = etype;
        } else {
          logger.warn( "it is too late to call: Dice.set_entropy_engine("+etype+")" );
        }
      }
    }


    // =====================================================================================
    /** @breif Get Instance
     *  @return pointer to the singlton instance of the Dice class.
     */
    // -------------------------------------------------------------------------------------
    public static Dice getInstance() {
      // -----------------------------------------------------------------------------------
      if ( null == Dice.dice_singleton_instance ) {
        synchronized( Dice.class ) {
          if ( Entropy.NO_ENGINE == Dice.dice_singleton_entropy_type ) {
            Dice.dice_singleton_entropy_type = Entropy.DEFAULT_ENGINE;
            logger.debug( "Using default entropy engine" );
          }
          Dice.dice_singleton_instance = new Dice( Dice.dice_singleton_entropy_type );
        }
      }
      return Dice.dice_singleton_instance;
    }


    // =====================================================================================
    /** @breif Dispose Instance
     *  @return pointer to the singlton instance of the Dice class.
     */
    // -------------------------------------------------------------------------------------
    public static void dispose() {
      // -----------------------------------------------------------------------------------
      Dice.dice_singleton_entropy_type = Entropy.NO_ENGINE;
      Dice.dice_singleton_instance     = null;
    }

    // =====================================================================================
    /** @brief Private Constructor.
     */
    // -------------------------------------------------------------------------------------
    private Dice( int etype ) {
      // -----------------------------------------------------------------------------------
      ent_engine = new Entropy( etype );
    }

    // =====================================================================================
    /** @brief Seed Size.
     *  @return the native seed size for the chosen entropy engine.
     */
    // -------------------------------------------------------------------------------------
    public int seed_size() {
      // -----------------------------------------------------------------------------------
      return ent_engine.seed_size();
    }


    // =====================================================================================
    /** @brief Seed.
     */
    // -------------------------------------------------------------------------------------
    public void seed_set() {
      // -----------------------------------------------------------------------------------
      ent_engine.seed_set();
    }
    
    // =====================================================================================
    /** @brief Seed.
     *  @param sm byte array containing seed material.
     */
    // -------------------------------------------------------------------------------------
    public void seed_set( byte[] sm ) {
      // -----------------------------------------------------------------------------------
      ent_engine.seed_set( sm, sm.length );
    }
    
    // =====================================================================================
    /** @brief Seed.
     *  @param sm byte array containing seed material.
     */
    // -------------------------------------------------------------------------------------
    public void seed_set( SeedMatter sm ) {
      // -----------------------------------------------------------------------------------
      ent_engine.seed_set( sm.get_bytes(), sm.count_bytes() );
    }

    // =====================================================================================
    /** @brief Engine Name.
     *  @return the canonical name of the entropy engine.
     */
    // -------------------------------------------------------------------------------------
    public String engine_name() {
      // -----------------------------------------------------------------------------------
      return ent_engine.name();
    }
    
    // =====================================================================================
    /** @brief Random Integer.
     *  @param max_exclusive_value maximum value.
     *  @return integer random number distributed uniformally.
     *
     *  Uniformly distributed integers in the range [0,max_exclusive_value)
     */
    // -------------------------------------------------------------------------------------
    public short uniform( short max_exclusive_value ) {
      // -----------------------------------------------------------------------------------
      return (short)(ent_engine.get_integer() % (long)max_exclusive_value);
    }

    // =====================================================================================
    /** @brief Random Integer.
     *  @param max_exclusive_value maximum value.
     *  @return integer random number distributed uniformally.
     *
     *  Uniformly distributed integers in the range [0,max_exclusive_value)
     */
    // -------------------------------------------------------------------------------------
    public int uniform( int max_exclusive_value ) {
      // -----------------------------------------------------------------------------------
      return (int)(ent_engine.get_integer() % (long)max_exclusive_value);
    }

    // =====================================================================================
    /** @brief Random Integer.
     *  @param max_exclusive_value maximum value.
     *  @return integer random number distributed uniformally.
     *
     *  Uniformly distributed integers in the range [0,max_exclusive_value)
     */
    // -------------------------------------------------------------------------------------
    public long  uniform( long  max_exclusive_value ) {
      // -----------------------------------------------------------------------------------
      return ent_engine.get_integer() % max_exclusive_value;
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
      return ent_engine.get_real();
    }


    // =====================================================================================
    /** @brief Random Real.
     *  @return real random number distributed uniformally.
     *
     *  Uniformly distributed reals in the range [0,1)
     */
    // -------------------------------------------------------------------------------------
    public float uniformf( ) {
      // -----------------------------------------------------------------------------------
      return (float)ent_engine.get_real();
    }


    // =====================================================================================
    /** @brief Random Byte.
     *  @return integer random number distributed uniformally.
     *
     *  Uniformly distributed integers in the range [0,255]
     */
    // -------------------------------------------------------------------------------------
    public short get_byte( ) {
      // -----------------------------------------------------------------------------------
      return (short)(ent_engine.get_integer() % 256L);
    }


    // =====================================================================================
    /** @brief Random Integer.
     *  @param max_exclusive_value maximum value.
     *  @return integer random number distributed uniformally.
     *
     *  Uniformly distributed integers in the range [0,max_exclusive_value)
     */
    // -------------------------------------------------------------------------------------
    public int index( int max_exclusive_value ) {
      // -----------------------------------------------------------------------------------
      return (int)(ent_engine.get_integer() % (long)max_exclusive_value);
    }


    // =====================================================================================
    /** @brief Boolean.
     *  @param threshold divide between true/false.
     *  @return true if uniform random is less than the threshold, otherwise return false.
     *          true = [0,threshold), and false = [threshold,1)
     *
     *  Return a true/false based on the threshold. By default it is 1/2 providing just as
     *  many trues and falses. If threshold is set to 0.3 then 3 times out of 10 this will
     *  return a true and 7 times out of 10 a false will be returned. If the threshold is
     *  0.75 then 3 times out of 4 this will return a true and 1 time out of 4 a false.
     */
    // -------------------------------------------------------------------------------------
    public  boolean bool( double threshold ) {
      // -----------------------------------------------------------------------------------
      return ( ent_engine.get_real() < threshold) ? true : false;
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
    public double normal( ) {
      // -----------------------------------------------------------------------------------
      if ( have_spare ) {
        have_spare = false;
        return Math.sqrt(rand1) * Math.sin(rand2);
      }
  
      have_spare = true;
  
      rand1 = ent_engine.get_real();
  
      if ( rand1 < 1e-100 ) { rand1 = 1e-100; }
  
      rand1 = -Math2.N_TWO * Math.log(rand1);
      rand2 =  Math2.N_2PI*ent_engine.get_real();
  
      return Math.sqrt(rand1) * Math.cos(rand2);
    }


    // =====================================================================================
    /** @brief Normal 2D.
     *  @param C pointer to x and y.
     *  @param a standard deviation of the major axis.
     *  @param b standard deviation of the minor axis.
     *
     *  Generate a two dimentional point with a normal distribution.
     */
    // -------------------------------------------------------------------------------------
    public void normal( double[] C, double a, double b ) {
      // -----------------------------------------------------------------------------------
      C[0] = a * normal();
      C[1] = b * normal();
    }

    
    // =====================================================================================
    /** @brief Normal 2D.
     *  @param C     pointer to x and y.
     *  @param a     standard deviation of the major axis.
     *  @param b     standard deviation of the minor axis.
     *  @param theta rotation positve x towards positive y in radians.
     *
     *  Generate a two dimentional point with a normal distribution.
     */
    // -------------------------------------------------------------------------------------
    public void normal( double[] C, double a, double b, double theta ) {
      // -----------------------------------------------------------------------------------
      double[] r = { 0.0, 0.0 };
      double   s = Math.sin( theta );
      double   c = Math.cos( theta );
      normal( r, a, b );

      C[0] = c*r[0] - s*r[1];
      C[1] = s*r[0] + c*r[1];
    }


    // =====================================================================================
    /** @brief Uniform distribution.
     *  @param buffer pointer to a receiving one dimensional buffer.
     *  @param n      number of elements.
     *
     *  Load an array with uniformally distributed samples.
     */
    // -------------------------------------------------------------------------------------
    public void uniform( double[] buffer, int n ) {
      // -----------------------------------------------------------------------------------
      for ( int i=0; i<n; i++ ) {
        buffer[i] = uniform();
      }
    }

    // =====================================================================================
    /** @brief Uniform distribution.
     *  @param buffer pointer to a receiving two dimensional buffer.
     *  @param n      number of rows.
     *  @param m      number of columns.
     *
     *  Load a two dimensional array with uniformally distributed samples.
     */
    // -------------------------------------------------------------------------------------
    public void uniform( double[][] buffer, int n, int m ) {
      // -----------------------------------------------------------------------------------
      for ( int i=0; i<n; i++ ) {
        for ( int j=0; j<m; j++ ) {
          buffer[i][j] = uniform();
        }
      }
    }


    // =====================================================================================
    /** @brief Normal distribution.
     *  @param buffer pointer to a receiving one dimensional buffer.
     *  @param n      number of elements.
     *
     *  Load an array with uniformally distributed samples.
     */
    // -------------------------------------------------------------------------------------
    public void normal( double[] buffer, int n ) {
      // -----------------------------------------------------------------------------------
      for ( int i=0; i<n; i++ ) {
        buffer[i] = normal();
      }
    }

    // =====================================================================================
    /** @brief Normal distribution.
     *  @param buffer pointer to a receiving two dimensional buffer.
     *  @param n      number of rows.
     *  @param m      number of columns.
     *
     *  Load a two dimensional array with uniformally distributed samples.
     */
    // -------------------------------------------------------------------------------------
    public void normal( double[][] buffer, int n, int m ) {
      // -----------------------------------------------------------------------------------
      for ( int i=0; i<n; i++ ) {
        for ( int j=0; j<m; j++ ) {
          buffer[i][j] = normal();
        }
      }
    }


    // =====================================================================================
    /** @brief Scramble Arrays.
     *  @param ary pointer to an array.
     *  @param tms number of exchanges.
     */
    // -------------------------------------------------------------------------------------
    public void scramble( int[] ary, int len, int tms ) {
      // -----------------------------------------------------------------------------------
      for ( int i=0; i<tms; i++ ) {
        int a = index( len );
        int b = index( len );
        if ( a != b ) {
          int temp = ary[a];
          ary[a]   = ary[b];
          ary[b]   = temp;
        }
      }
    }

    // =====================================================================================
    /** @brief Scramble Arrays.
     *  @param ary pointer to an array.
     *  @param tms number of exchanges.
     */
    // -------------------------------------------------------------------------------------
    public void scramble( double[] ary, int len, int tms ) {
      // -----------------------------------------------------------------------------------
      for ( int i=0; i<tms; i++ ) {
        int a = index( len );
        int b = index( len );
        if ( a != b ) {
          double temp = ary[a];
          ary[a]   = ary[b];
          ary[b]   = temp;
        }
      }
    }

    // =====================================================================================
    /** @brief Scramble Arrays.
     *  @param ary pointer to an array.
     *  @param tms number of exchanges.
     */
    // -------------------------------------------------------------------------------------
    public void scramble( int[] ary ) {
      // -----------------------------------------------------------------------------------
      scramble( ary, ary.length, ary.length );
    }

    // =====================================================================================
    /** @brief Scramble Arrays.
     *  @param ary pointer to an array.
     *  @param tms number of exchanges.
     */
    // -------------------------------------------------------------------------------------
    public void scramble( double[] ary ) {
      // -----------------------------------------------------------------------------------
      scramble( ary, ary.length, ary.length );
    }


    // =====================================================================================
    /** @brief Scramble Arrays.
     *  @param ary pointer to an array.
     *  @param mul multiplier.
     */
    // -------------------------------------------------------------------------------------
    public void scramble( int[] ary, int mul ) {
      // -----------------------------------------------------------------------------------
      scramble( ary, ary.length, ary.length*mul );
    }

    // =====================================================================================
    /** @brief Scramble Arrays.
     *  @param ary pointer to an array.
     *  @param mul multiplier.
     */
    // -------------------------------------------------------------------------------------
    public void scramble( double[] ary, int mul ) {
      // -----------------------------------------------------------------------------------
      scramble( ary, ary.length, ary.length*mul );
    }


    

  }

  // =======================================================================================
  // **                                      D I C E                                      **
  // ======================================================================== END FILE =====

