// ====================================================================== BEGIN FILE =====
// **                                  P S W I N D O W                                  **
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
 * @file PSWindow.java
 *  This class defines a graphics window that will be physically placed on a page.
 *
 * @author Stephen W. Soliday
 * @date 2015-08-26
 */
// =======================================================================================

package org.trncmp.lib;

import java.io.*;
import java.util.*;

// =======================================================================================
/** @brief Graphics Window.
 *  This class defines a graphics window that will be physically placed on a page.
 */
// ---------------------------------------------------------------------------------------
public abstract class PSWindow {
    // -----------------------------------------------------------------------------------
    protected double device_width  = 0.0; //< physical window width (inches)
    protected double device_height = 0.0; //< physical window height (inches)
    protected double device_x      = 0.0; //< physical window position (inches)
    protected double device_y      = 0.0; //< physical window position (inches)

    public abstract void pswrite ( PrintWriter fp ); //< ABSTRACT
    public abstract void delete  ( );                //< ABSTRACT

    // ===================================================================================
    /** @brief Write Header.
     *  @param psout pointer to an output file.
     *
     *  Write the postscript header for this window on the output stream.
     */
    // -----------------------------------------------------------------------------------
    protected void write_ps_window_header( PrintWriter psout ) {
	// -------------------------------------------------------------------------------
	psout.printf( "%%%%----------------------------------\n");
	psout.printf( "gsave\n");
	psout.printf( "%f %f translate\n", device_x, device_y);
    }

    // ===================================================================================
    /** @brief Write Trailer.
     *  @param psout pointer to an output file.
     *
     *  Write the postscript trailer for this window on the output stream.
     */
    // -----------------------------------------------------------------------------------
    protected void write_ps_window_trailer( PrintWriter psout ) {
	// -------------------------------------------------------------------------------
	psout.printf( "grestore\n");
    }

    // ===================================================================================
    /** @brief Constructor.
     *  Create an empty object.
     */
    // -----------------------------------------------------------------------------------
    public PSWindow( ) {
	// -------------------------------------------------------------------------------
    }

    // ===================================================================================
    /** @brief Constructor.
     *  @param dw window width in device coordinates (inches).
     *  @param dh window height in device coordinates (inches).
     */
    // -----------------------------------------------------------------------------------
    public PSWindow( double dw, double dh ) {
	// -------------------------------------------------------------------------------
	device_width  = dw;
	device_height = dh;
    }

    // ===================================================================================
    /** @brief Set Position.
     *  @param x window position in device coordinates (inches).
     *  @param y window position in device coordinates (inches).
     */
    // -----------------------------------------------------------------------------------
    public void setDevice( double dx, double dy ) {
	// -------------------------------------------------------------------------------
	device_x = dx;
	device_y = dy;
    }
}

// =======================================================================================
// **                                  P S W I N D O W                                  **
// ======================================================================== END FILE =====
