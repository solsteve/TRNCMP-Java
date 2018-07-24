// ====================================================================== BEGIN FILE =====
// **                          P O P U L A T I O N M E M B E R                          **
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
 * @file PopulationMember.java
 * <p>
 * Provides the interfaces for a population member.
 *
 * @date 2018-07-11
 *
 * ---------------------------------------------------------------------------------------
 *
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 *
 * @author Stephen W. Soliday
 * @date 2015-08-09
 */
// =======================================================================================

package org.trncmp.mllib.ea;

import org.trncmp.lib.*;
import java.io.*;


// =======================================================================================
/** @class Member
 *  @brief Array of members.
 *
 * Provides the interfaces for a population member.
 */
// ---------------------------------------------------------------------------------------
public class PopulationMember {
  // -------------------------------------------------------------------------------------

  public Metric   metric = null;
  public Encoding param  = null;
  public int      age    = 0;

  
  // =====================================================================================
  /** @brief Constructor.
   *  @param mod pointer to a Model for memory allocation.
   */
  // -------------------------------------------------------------------------------------
  public PopulationMember( Model mod ) {
    // -----------------------------------------------------------------------------------
    metric = mod.alloc_metric();
    param  = mod.alloc_encoding();
    age    = 0;
    metric.zero();
  }

  
  // =====================================================================================
  /** @brief Copy.
   *  @param M pointer to a source PopulationMember
   */
  // -------------------------------------------------------------------------------------
  void copy( PopulationMember M ) {
    // -----------------------------------------------------------------------------------
    metric.copy( M.metric );
    param.copy(  M.param );
    age = M.age;
  }

}

// =======================================================================================
// **                          P O P U L A T I O N M E M B E R                          **
// ======================================================================== END FILE =====
