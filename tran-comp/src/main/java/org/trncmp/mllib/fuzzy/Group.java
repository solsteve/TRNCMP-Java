// ====================================================================== BEGIN FILE =====
// **                                     G R O U P                                     **
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
 * @file Group.java
 * <p>
 * Provides the interface and methods for a group of fuzzy partitions.
 *
 * @date 2018-08-12
 *
 * ---------------------------------------------------------------------------------------
 *
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 *
 * @author Stephen W. Soliday
 * @date 2014-06-27
 */
// =======================================================================================

package org.trncmp.mllib.fuzzy;

// =======================================================================================
public class Group {
  // -------------------------------------------------------------------------------------

  protected Partition[] partitions = null;
  protected int         num_part   = 0;

  public Group() {};

  public Partition part     ( int p )        { return partitions[p]; }
  public Function  function ( int p, int f ) { return partitions[p].function(f); }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void fuzzify( double[] mu, double[] x ) {
    // -----------------------------------------------------------------------------------
    int idx = 0;
    for ( int i=0; i<num_part; i++ ) {
      partitions[i].mu( mu, idx, x[i] );
      idx += partitions[i].size();
    }
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void defuzzify( double[] x, double[] mu ) {
    // -----------------------------------------------------------------------------------
    int idx = 0;
    for ( int i=0; i<num_part; i++ ) {
      x[i] = partitions[i].coa( mu, idx );
      idx += partitions[i].size();
    }
  }

} //end class Group

// =======================================================================================
// **                                 P A R T I T I O N                                 **
// ======================================================================== END FILE =====
