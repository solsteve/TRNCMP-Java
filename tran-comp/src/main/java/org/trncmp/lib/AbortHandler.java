// ====================================================================== BEGIN FILE =====
// **                              A B O R T H A N D L E R                              **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2013, Stephen W. Soliday                                           **
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
 * @file AbortHandeler.java
 *  Provides an interface for a callback function to abort the process.
 *
 * @author Stephen W. Soliday
 * @date 2013-06-24
 */
// =======================================================================================

package org.trncmp.lib;

// =======================================================================================
/** Provides an interface for a callback function to abort the process.                 */
// ---------------------------------------------------------------------------------------
public interface AbortHandler {
    // -----------------------------------------------------------------------------------
    /**  Hook for the abort handler.                                                    */
    // -----------------------------------------------------------------------------------
    public void handle( int n );
}

// =======================================================================================
// **                              A B O R T H A N D L E R                              **
// ======================================================================== END FILE =====
