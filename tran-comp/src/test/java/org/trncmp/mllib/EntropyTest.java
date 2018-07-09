// ====================================================================== BEGIN FILE =====
// **                               E N T R O P Y T E S T                               **
// =======================================================================================
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
// @file EntropyTest.java
// <p>
// Provides unit testing for the org.trncmp.mllib.Entropy class.
//
// @author Stephen W. Soliday
// @date 2018-07-06
//
// =======================================================================================

package org.trncmp.mllib.ea;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.trncmp.mllib.Entropy;

// =======================================================================================
public class EntropyTest {
  // -------------------------------------------------------------------------------------

  static final int SHORT_SAMPLES = 1000;

  // =====================================================================================
  @Test public void testSeedSize() {
    // -----------------------------------------------------------------------------------
    Entropy ent = Entropy.getInstance();

    int ss = ent.seed_size();

    Assert.assertTrue( 8 == ss );
  }

  
  // =====================================================================================
  @Test public void testSeedSetSmall() {
    // -----------------------------------------------------------------------------------
    Entropy ent = Entropy.getInstance();

    byte[] sm = new byte[3];

    int [] A = new int[SHORT_SAMPLES];
    int [] B = new int[SHORT_SAMPLES];

    ent.seed_set( sm );
    for ( int i=0; i<SHORT_SAMPLES; i++ ) { A[i] = ent.index( 314159 ); }

    ent.seed_set( sm );
    for ( int i=0; i<SHORT_SAMPLES; i++ ) { B[i] = ent.index( 314159 ); }

    long count = 0;
    for ( int i=0; i<SHORT_SAMPLES; i++ ) {
      if ( A[i] != B[i] ) { count += 1; }
    }

    Assert.assertEquals( 0, count );
  }

  
  // =====================================================================================
  @Test public void testSeedSetSame() {
    // -----------------------------------------------------------------------------------
    Entropy ent = Entropy.getInstance();

    int ss = ent.seed_size();

    byte[] sm = new byte[ss];

    int [] A = new int[SHORT_SAMPLES];
    int [] B = new int[SHORT_SAMPLES];

    ent.seed_set( sm );
    for ( int i=0; i<SHORT_SAMPLES; i++ ) { A[i] = ent.index( 314159 ); }

    ent.seed_set( sm );
    for ( int i=0; i<SHORT_SAMPLES; i++ ) { B[i] = ent.index( 314159 ); }

    long count = 0;
    for ( int i=0; i<SHORT_SAMPLES; i++ ) {
      if ( A[i] != B[i] ) { count += 1; }
    }

    Assert.assertEquals( 0, count );
  }

  
  // =====================================================================================
  @Test public void testSeedSetBig() {
    // -----------------------------------------------------------------------------------
    Entropy ent = Entropy.getInstance();

    int ss = ent.seed_size();

    byte[] sm = new byte[ss*3];

    int [] A = new int[SHORT_SAMPLES];
    int [] B = new int[SHORT_SAMPLES];

    ent.seed_set( sm );
    for ( int i=0; i<SHORT_SAMPLES; i++ ) { A[i] = ent.index( 314159 ); }

    ent.seed_set( sm );
    for ( int i=0; i<SHORT_SAMPLES; i++ ) { B[i] = ent.index( 314159 ); }

    long count = 0;
    for ( int i=0; i<SHORT_SAMPLES; i++ ) {
      if ( A[i] != B[i] ) { count += 1; }
    }

    Assert.assertEquals( 0, count );
  }

  
} // end class EntropyTest



// =======================================================================================
// **                               E N T R O P Y T E S T                               **
// ======================================================================== END FILE =====
