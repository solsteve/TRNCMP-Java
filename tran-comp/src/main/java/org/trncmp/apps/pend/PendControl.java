// ====================================================================== BEGIN FILE =====
// **                               P E N D C O N T R O L                               **
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
 * @file    PendControl.java
 *
 * @details Provides the interface for Pendulum Control.
 *
 * @author  Stephen W. Soliday
 * @date    2019-01-03
 */
// =======================================================================================

package org.trncmp.apps.pend;

// =======================================================================================
public interface PendControl {
  // -------------------------------------------------------------------------------------
  
  abstract public void   reset();
  abstract public double action( double[] state );

} // end interface PendControl

// =======================================================================================
// **                               P E N D C O N T R O L                               **
// ======================================================================== END FILE =====
