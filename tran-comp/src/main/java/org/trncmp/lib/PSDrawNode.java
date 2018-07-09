// ====================================================================== BEGIN FILE =====
// **                                P S D R A W N O D E                                **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2015, Stephen W. Soliday                                           **
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
 * @file PSDrawNode.java
 * Provide a link list of draw window objects.
 *
 * @author Stephen W. Soliday
 * @date 2015-08-26
 */
// =======================================================================================

package org.trncmp.lib;

import java.io.*;
import java.util.*;

// =======================================================================================
/** Provide a link list of draw window objects.
 */
// ---------------------------------------------------------------------------------------
class PSDrawNode {
    // -----------------------------------------------------------------------------------
    public PSWindow   window = null; //< reference to the window
    public PSDrawNode next = null;   //< reference to the next PSDrawNode
    
    // ===================================================================================
    /** @brief Constructor.
     */
    // -----------------------------------------------------------------------------------
    public PSDrawNode( ) {
	// -------------------------------------------------------------------------------
    }

    // ===================================================================================
    /** @brief Constructor.
     *  @param w pointer to a window.
     *  @param n pointer to the next PSDrawNode.
     */
    // -----------------------------------------------------------------------------------
    public PSDrawNode( PSWindow w, PSDrawNode n ) {
	// -------------------------------------------------------------------------------
	window = w;
	next   = n;
    }
}

// =======================================================================================
// **                                P S D R A W N O D E                                **
// ======================================================================== END FILE =====
