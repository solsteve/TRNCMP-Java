// ====================================================================== BEGIN FILE =====
// **                           G R I D K E Y L I S T E N E R                           **
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
 * @file    GridKeyListener.java
 *
 * @details Provides the interface for grid keyboard events.
 *
 * @author  Stephen W. Soliday
 * @date    2018-10-17
 */
// =======================================================================================

package org.trncmp.lib.inter;

import java.awt.event.KeyEvent;

  // =====================================================================================
  public interface GridKeyListener {
    // -----------------------------------------------------------------------------------

    abstract public void keyPressed ( KeyEvent e );

  } // end  interface GridEventListener


// =======================================================================================
// **                           G R I D K E Y L I S T E N E R                           **
// ======================================================================== END FILE =====
