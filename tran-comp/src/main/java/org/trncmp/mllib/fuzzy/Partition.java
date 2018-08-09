// ====================================================================== BEGIN FILE =====
// **                                 P A R T I T I O N                                 **
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
 * @file Partition.java
 * <p>
 * Provides an interface and methods for a fuzzy partition.
 *
 * @date 2018-08-09
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
public abstract class Partition {
  // -------------------------------------------------------------------------------------

  protected Function[] function = null;
  protected int        num_func = 0;

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public Partition( double[] ctrs ) {
    // -----------------------------------------------------------------------------------
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public Partition( int n, double minv, double maxv ) {
    // -----------------------------------------------------------------------------------
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  

} // end class Partition


// =======================================================================================
// **                                 P A R T I T I O N                                 **
// ======================================================================== END FILE =====
