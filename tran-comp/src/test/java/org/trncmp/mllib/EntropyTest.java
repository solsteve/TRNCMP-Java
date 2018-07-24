// ====================================================================== BEGIN FILE =====
// **                               E N T R O P Y T E S T                               **
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
 * @file EntropyTest.java
 * <p>
 * Provides unit testing for the org.trncmp.mllib.Entropy class.
 *
 * @date 2018-07-09
 */
// =======================================================================================

package org.trncmp.mllib.ea;

import        org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.trncmp.mllib.Entropy;

// =======================================================================================
class EntropyTest {
  // -------------------------------------------------------------------------------------

  static final int SHORT_SAMPLES = 1000;

  // =====================================================================================
  @Test
  void testSeedSize() {
    // -----------------------------------------------------------------------------------
    Entropy ent = Entropy.getInstance();

    int ss = ent.seed_size();

    assertTrue( 8 == ss );
  }

  
  // =====================================================================================
  @Test
  void testSeedSetSmall() {
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

    assertEquals( 0, count );
  }

  
  // =====================================================================================
  @Test
  public void testSeedSetSame() {
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

    assertEquals( 0, count );
  }

  
  // =====================================================================================
  @Test
  public void testSeedSetBig() {
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

    assertEquals( 0, count );
  }

  
} // end class EntropyTest



// =======================================================================================
// **                               E N T R O P Y T E S T                               **
// ======================================================================== END FILE =====
