// ====================================================================== BEGIN FILE =====
// **                                    P S P A G E                                    **
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
 * @file PSPage.java
 *
 * @author Stephen W. Soliday
 * @date 2015-08-26
 */
// =======================================================================================

package org.trncmp.lib;

import java.io.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
// ---------------------------------------------------------------------------------------
class PSPage {
    // -----------------------------------------------------------------------------------
  private static final Logger logger = LogManager.getLogger();

    protected PSDrawNode firstWindow = null;
    protected String     name        = null;
    protected int        page_number = -1;

    // ===================================================================================
    /** @brief Constructor.
     *  @param pn page number.
     */
    // -----------------------------------------------------------------------------------
    public PSPage( int pn ) {
	// -------------------------------------------------------------------------------
	page_number = pn;
    }

    // ===================================================================================
    /** @brief Destructor.
     */
    // -----------------------------------------------------------------------------------
    public void delete( ) {
	// -------------------------------------------------------------------------------
	logger.debug( "PSDraw::delete(page:"+page_number+")" );

       	for (PSDrawNode node = firstWindow; null != node; node = node.next) {
	    node.window.delete();
	}
    }

    // ===================================================================================
    /** @brief Set page name.
     *  @param nm source name.
     */
    // -----------------------------------------------------------------------------------
    public void setName( String nm ) {
	// -------------------------------------------------------------------------------
	name = new String( nm );
    }

    // ===================================================================================
    /** @brief Add.
     *  @param w reference to a window object.
     *  @param x position in device coordinates.
     *  @param y position in device coordinates.
     *  @return false.
     */
    // -----------------------------------------------------------------------------------
    public boolean add( PSWindow w, double x, double y ) {
	// -------------------------------------------------------------------------------
	PSDrawNode temp = new PSDrawNode( w, firstWindow );
	w.setDevice( x, y );
	firstWindow = temp;
	return false;
    }

    // ===================================================================================
    /** @brief Name.
     *  @return the name
     *
     */
    // -----------------------------------------------------------------------------------
    public PSWindow pop( ) {
	// -------------------------------------------------------------------------------
	if (null == firstWindow) {
	    return null;
	}

	PSDrawNode temp = firstWindow;
	PSWindow   w    = temp.window;

	firstWindow = temp.next;
	temp.next   = null;
	temp.window = null;

	return w;
    }

    // ===================================================================================
    /** @brief Write postscript.
     *  @param fp reference to a print writer.
     */
    // -----------------------------------------------------------------------------------
    public void pswrite( PrintWriter fp ) {
	// -------------------------------------------------------------------------------
	logger.debug( "pswrite page: "+page_number );
	write_ps_page_header( fp );

       	for (PSDrawNode node = firstWindow; null != node; node = node.next) {
	    node.window.pswrite(fp);
	}

	write_ps_page_trailer(fp);
    }

    // ===================================================================================
    /** @brief Write the postscript header.
     *  @param psout pointer to a file.
     */
    // -----------------------------------------------------------------------------------
    protected void write_ps_page_header( PrintWriter psout ) {
	// -------------------------------------------------------------------------------
	psout.printf( "%%%%==============================================================\n");
	if (null == name) {
	    psout.printf( "%%%%Page: %d %d\n", (int)page_number, (int)page_number);
	} else {
	    psout.printf( "%%%%Page: %s %d\n", name, (int)page_number);
	}
	psout.printf( "%%%%--------------------------------------------------------------\n");
	psout.printf( "%%%%BeginPageSetup\n");
	psout.printf( "startpage\n");
	psout.printf( "TIMES\n");
	psout.printf( "%%%%EndPageSetup\n");
    }

    // ===================================================================================
    /** @brief Write the postscript trailer.
     *  @param psout pointer to a file.
     */
    // -----------------------------------------------------------------------------------
    protected void write_ps_page_trailer( PrintWriter psout ) {
	// -------------------------------------------------------------------------------
	psout.printf( "%%%%----------------------------------\n");
	psout.printf( "stoppage\n");
	psout.printf( "showpage\n");
    }

}

// =======================================================================================
// **                                    P S P A G E                                    **
// ======================================================================== END FILE =====
