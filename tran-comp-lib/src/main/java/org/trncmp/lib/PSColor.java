// ====================================================================== BEGIN FILE =====
// **                                   P S C O L O R                                   **
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
 * @file PSColor.java
 *
 * @author Stephen W. Soliday
 * @date 2015-08-26
 */
// =======================================================================================

package org.trncmp.lib;

import java.io.*;
import java.util.*;

// =======================================================================================
// ---------------------------------------------------------------------------------------
public class PSColor {
    // -----------------------------------------------------------------------------------

    public static final PSColor white   = new PSColor( 1.0, 1.0, 1.0 );
    public static final PSColor black   = new PSColor( 0.0, 0.0, 0.0 );
    public static final PSColor red     = new PSColor( 1.0, 0.0, 0.0 );
    public static final PSColor green   = new PSColor( 0.0, 1.0, 0.0 );
    public static final PSColor blue    = new PSColor( 0.0, 0.0, 1.0 );
    public static final PSColor cyan    = new PSColor( 0.0, 1.0, 1.0 );
    public static final PSColor magenta = new PSColor( 1.0, 0.0, 1.0 );
    public static final PSColor yellow  = new PSColor( 1.0, 1.0, 0.0 );

    public double r = 0.0;
    public double g = 0.0;
    public double b = 0.0;

    // ===================================================================================
    /** @brief Constructor.
     *  Create an empty object.
     */
    // -----------------------------------------------------------------------------------
    public PSColor( ) {
	// -------------------------------------------------------------------------------
    }

    // ===================================================================================
    /** @brief Constructor.
     *  @param _r red.
     *  @param _g green.
     *  @param _b blue.
     */
    // -----------------------------------------------------------------------------------
    public PSColor( double _r, double _g, double _b ) {
	// -------------------------------------------------------------------------------
	r = _r;
	g = _g;
	b = _b;
	correct();
    }

    // ===================================================================================
    /** @brief Constructor.
     *  @param C PSColor object.
     */
    // -----------------------------------------------------------------------------------
    public PSColor( PSColor C ) {
	// -------------------------------------------------------------------------------
	r = C.r;
	g = C.g;
	b = C.b;
	correct();
    }

    // ===================================================================================
    /** @brief Set Color.
     *  @param _r red.
     *  @param _g green.
     *  @param _b blue.
     */
    // -----------------------------------------------------------------------------------
    public void set( double _r, double _g, double _b ) {
	// -------------------------------------------------------------------------------
	r=_r;
	g=_g;
	b=_b;
	correct();
    }

    // ===================================================================================
    /** @brief Copy.
     *  @param C PSColor object.
     */
    // -----------------------------------------------------------------------------------
    public void copy( PSColor C ) {
	// -------------------------------------------------------------------------------
	r=C.r;
	g=C.g;
	b=C.b;
    }

    // ===================================================================================
    /** @brief Correct.
     *
     *  Correct the color range
     */
    // -----------------------------------------------------------------------------------
    protected void correct( ) {
	// -------------------------------------------------------------------------------
	if (r < 0.0) { r = 0.0; }  if (r > 1.0) { r = 1.0; }
	if (g < 0.0) { g = 0.0; }  if (g > 1.0) { g = 1.0; }
	if (b < 0.0) { b = 0.0; }  if (b > 1.0) { b = 1.0; }
    }
}

// =======================================================================================
// **                                   P S C O L O R                                   **
// ======================================================================== END FILE =====
