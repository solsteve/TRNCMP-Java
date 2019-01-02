// ====================================================================== BEGIN FILE =====
// **                          P O P U L A T I O N M E M B E R                          **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2018, Stephen W. Soliday                                           **
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
