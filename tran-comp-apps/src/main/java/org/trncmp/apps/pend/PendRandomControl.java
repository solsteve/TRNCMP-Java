// ====================================================================== BEGIN FILE =====
// **                         P E N D R A N D O M C O N T R O L                         **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2019, Stephen W. Soliday                                           **
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
 * @file    PendRandomControl.java
 *
 * @details Provides the interface and methods for Pendulum Control.
 *
 * @author  Stephen W. Soliday
 * @date    2019-01-03
 */
// =======================================================================================

package org.trncmp.apps.pend;

import org.trncmp.lib.Dice;

// =======================================================================================
public class PendRandomControl implements PendControl{
  // -------------------------------------------------------------------------------------
  Dice dd = Dice.getInstance();
  
  // =====================================================================================
  public PendRandomControl() {
    // -----------------------------------------------------------------------------------
  }
  
  // =====================================================================================
  public void reset() {
    // -----------------------------------------------------------------------------------
    dd.seed_set();
  }
  
  // =====================================================================================
  public double action( double[] state ) {
    // -----------------------------------------------------------------------------------
    if ( dd.uniform() < 0.1 ) {
      return 0.01*dd.normal();
    }
    
    return 0.0;
  }

} // end class PendRandomControl

// =======================================================================================
// **                         P E N D R A N D O M C O N T R O L                         **
// ======================================================================== END FILE =====
