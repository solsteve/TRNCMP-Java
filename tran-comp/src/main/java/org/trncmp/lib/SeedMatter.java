// ====================================================================== BEGIN FILE =====
// **                                S E E D M A T T E R                                **
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
 * @file SeedMatter.java
 *  Provides Interface and methods generating seed material for the entropy engines.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-08-28
 */
// =======================================================================================

package org.trncmp.lib;

import java.nio.*;

// =======================================================================================
public class SeedMatter {
  // -------------------------------------------------------------------------------------

  private byte[] seed_data = null;

  // =====================================================================================
  /** @brief Constructor.
   *  @param n number of bytes.
   *
   *  Create an [n] byte buffer and fill it from /dev/urandom
   */
  // -------------------------------------------------------------------------------------
  public SeedMatter( int n ) {
    // -----------------------------------------------------------------------------------
    seed_data = new byte[ n ];
    FileTools.urandom( seed_data );
  }

    
  // =====================================================================================
  /** @brief Constructor.
   *  @param src pointer to a source array of bytes.
   *
   *  Create a byte buffer and fill it from the source array.
   */
  // -------------------------------------------------------------------------------------
  public SeedMatter( byte[] src ) {
    // -----------------------------------------------------------------------------------
    int n = src.length;
    seed_data = new byte[ n ];
    for ( int i=0; i<n; i++ ) { seed_data[i] = src[i]; }
  }


  // =====================================================================================
  /** @brief Constructor.
   *  @param src pointer to a source array of bytes.
   *
   *  Create a byte buffer and fill it from the source array.
   */
  // -------------------------------------------------------------------------------------
  public SeedMatter( String source ) {
    // -----------------------------------------------------------------------------------
    byte[] temp = source.getBytes();
    int n = temp.length;
    seed_data = new byte[ n ];
    for ( int i=0; i<n; i++ ) { seed_data[i] = temp[i]; }
  }


  // =====================================================================================
  /** @brief Constructor.
   *  @param src pointer to a source array of bytes.
   *
   *  Create a byte buffer and fill it from the source array.
   */
  // -------------------------------------------------------------------------------------
  public SeedMatter( char[] source ) {
    // -----------------------------------------------------------------------------------
    java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(source.length);
    bb.asCharBuffer().put(source);
    int n = bb.array().length;
    seed_data = new byte[ n ];
    for ( int i=0; i<n; i++ ) { seed_data[i] = bb.array()[i]; }
  }


  // =====================================================================================
  /** @brief Constructor.
   *  @param src pointer to a source array of bytes.
   *
   *  Create a byte buffer and fill it from the source array.
   */
  // -------------------------------------------------------------------------------------
  public SeedMatter( short[] source ) {
    // -----------------------------------------------------------------------------------
    java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(source.length * 2);
    bb.asShortBuffer().put(source);
    int n = bb.array().length;
    seed_data = new byte[ n ];
    for ( int i=0; i<n; i++ ) { seed_data[i] = bb.array()[i]; }
  }


  // =====================================================================================
  /** @brief Constructor.
   *  @param src pointer to a source array of bytes.
   *
   *  Create a byte buffer and fill it from the source array.
   */
  // -------------------------------------------------------------------------------------
  public SeedMatter( int[] source ) {
    // -----------------------------------------------------------------------------------
    java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(source.length * 4);
    bb.asIntBuffer().put(source);
    int n = bb.array().length;
    seed_data = new byte[ n ];
    for ( int i=0; i<n; i++ ) { seed_data[i] = bb.array()[i]; }
  }


  // =====================================================================================
  /** @brief Constructor.
   *  @param src pointer to a source array of bytes.
   *
   *  Create a byte buffer and fill it from the source array.
   */
  // -------------------------------------------------------------------------------------
  public SeedMatter( long[] source ) {
    // -----------------------------------------------------------------------------------
    java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(source.length * 8);
    bb.asLongBuffer().put(source);
    int n = bb.array().length;
    seed_data = new byte[ n ];
    for ( int i=0; i<n; i++ ) { seed_data[i] = bb.array()[i]; }
  }


  // =====================================================================================
  /** @brief Constructor.
   *  @param src pointer to a source array of bytes.
   *
   *  Create a byte buffer and fill it from the source array.
   */
  // -------------------------------------------------------------------------------------
  public SeedMatter( float[] source ) {
    // -----------------------------------------------------------------------------------
    java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(source.length * 4);
    bb.asFloatBuffer().put(source);
    int n = bb.array().length;
    seed_data = new byte[ n ];
    for ( int i=0; i<n; i++ ) { seed_data[i] = bb.array()[i]; }
  }

  // =====================================================================================
  /** @brief Constructor.
   *  @param src pointer to a source array of bytes.
   *
   *  Create a byte buffer and fill it from the source array.
   */
  // -------------------------------------------------------------------------------------
  public SeedMatter( double[] source ) {
    // -----------------------------------------------------------------------------------
    java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(source.length * 8);
    bb.asDoubleBuffer().put(source);
    int n = bb.array().length;
    seed_data = new byte[ n ];
    for ( int i=0; i<n; i++ ) { seed_data[i] = bb.array()[i]; }
  }


  //======================================================================================
  /** @brief Seed Material.
   *  @return byte array containing the seed material.
   */
  // -------------------------------------------------------------------------------------
  public byte[] get_bytes( ) {
    // -----------------------------------------------------------------------------------
    int n = seed_data.length;
    byte[] temp = new byte[ n ];
    for ( int i=0; i<n; i++ ) { temp[i] = seed_data[i]; }
    return temp;
  }

    
  // =====================================================================================
  /** @brief Seed Material.
   *  @param n number of bytes to produce.
   *  @return new allocation of a byte array containing the seed material.
   *  @see  SeedMatter.mix_seed_matter
   */
  // -------------------------------------------------------------------------------------
  public byte[] get_bytes( int n ) {
    // -----------------------------------------------------------------------------------
    byte[] temp = new byte[n];
    SeedMatter.mix_seed_matter( temp, seed_data );
    return temp;
  }

    
  // =====================================================================================
  /** @brief Number of bytes.
   *  @return number of bytes of available seed material.
   */
  // -------------------------------------------------------------------------------------
  public int count_bytes( ) {
    // -----------------------------------------------------------------------------------
    return seed_data.length;
  }

    
  // =====================================================================================
  /** @brief Mix Seed Material.
   *  @param dst pointer to a destination buffer.
   *  @param src pointer to a source buffer.
   *
   *  fill the destination buffer using all the bytes of the source buffer regardless
   *  of the size of the source.
   */
  // -------------------------------------------------------------------------------------
  public static void mix_seed_matter( byte[] dst, byte[] src ) {
    // -----------------------------------------------------------------------------------

    int ns = src.length;
    int nd = dst.length;
	
    if ( nd > ns ) {                     // source has too few bytes.
      for ( int i=0; i<nd; i++ ) {
        dst[i] = src[i % ns];
      }
    } else {
      if ( nd < ns ) {                 // source has too many bytes.
        for ( int i=0; i<nd; i++ ) {
          dst[i] = 0;
        }
        for ( int i=0; i<ns; i++ ) {
          dst[i % nd] = src[i];
        }
      } else {
        for ( int i=0; i<nd; i++ ) { // source is the same size as the destination.
          dst[i] = src[i];
        }
      }
    }
  }


}


// =======================================================================================
// **                                S E E D M A T T E R                                **
// ======================================================================== END FILE =====
