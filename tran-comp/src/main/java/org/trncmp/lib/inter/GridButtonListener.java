// ====================================================================== BEGIN FILE =====
// **                        G R I D B U T T O N L I S T E N E R                        **
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
 * @file    GridButtonListener.java
 *
 * @details Provides the interface for grid button events.
 *
 * @author  Stephen W. Soliday
 * @date    2018-12-27
 */
// =======================================================================================

package org.trncmp.lib.inter;

// =======================================================================================
public interface GridButtonListener {
  // -------------------------------------------------------------------------------------

  abstract public void requestRun();
  abstract public void requestStop();
  abstract public void resetButtonPressed();
  abstract public void closeAction();

} // end interface GridButtonListener


// =======================================================================================
// **                        G R I D B U T T O N L I S T E N E R                        **
// ======================================================================== END FILE =====
