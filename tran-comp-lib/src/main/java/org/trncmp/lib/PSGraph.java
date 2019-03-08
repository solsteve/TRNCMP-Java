// ====================================================================== BEGIN FILE =====
// **                                   P S G R A P H                                   **
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
 * @file nD_Tools.java
 *  Provides interface for multipage/multiwindow postscript graphics.
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
public class PSGraph {
  // -----------------------------------------------------------------------------------
  private static final Logger logger = LogManager.getLogger();

  protected PSPage[] page  = null;
  protected int   num_page = -1;

  public static final int NORMAL = 10;
  public static final int BOLD   = 11;
  public static final int ITALIC = 12;

  // ===================================================================================
  /** @brief Initialize.
     *  @param n number of pages.
     */
    // -----------------------------------------------------------------------------------
    protected void init( int n ) {
	// -------------------------------------------------------------------------------
	num_page = n;
	page = new PSPage[n];
	for (int i=0; i<n; i++) {
	    page[i] = new PSPage(i);
	}
    }

    // ===================================================================================
    /** @brief Constructor.
     *  @param n number of pages.
     */
    // -----------------------------------------------------------------------------------
    public PSGraph( int n ) {
	// -------------------------------------------------------------------------------
	init(n);
    }

    // ===================================================================================
    /** @brief Constructor.
     */
    // -----------------------------------------------------------------------------------
    public PSGraph( ) {
	// -------------------------------------------------------------------------------
	init(1);
    }

    // ===================================================================================
    /** @brief Destructor
     */
    // -----------------------------------------------------------------------------------
    public void delete() {
	// -------------------------------------------------------------------------------
	logger.debug( "PSGraph::delete()" );
	for (int i=0; i<num_page; i++) {
	    page[i].delete();
	}
    }

    // ===================================================================================
    /** @brief Set page name.
     *  @param pn page number.
     *  @param nm page name.
     */
    // -----------------------------------------------------------------------------------
    public void setName ( int pn, String nm ) {
	// -------------------------------------------------------------------------------
	page[pn].setName(nm);
    }

    // ===================================================================================
    /** @brief Add.
     *  @param w  reference to the window.
     *  @param pn page number.
     *  @param x  position in device coordinates.
     *  @param y  position in device coordinates.
     */
    // -----------------------------------------------------------------------------------
    public void add( PSWindow w, int pn, double x, double y ) {
	// -------------------------------------------------------------------------------
	page[pn].add( w, x, y );
    }

    // ===================================================================================
    /** @brief Write the postscript file.
     *  @param fspc full path to the postscript file.
     */
    // -----------------------------------------------------------------------------------
    public void pswrite( String fspc ) {
	// -------------------------------------------------------------------------------
	try { 
	    PrintWriter fp = new PrintWriter( fspc );
  
	    write_ps_header( fspc, fp );

	    for (int i=0; i<num_page; i++) {
		page[i].pswrite(fp);
	    }

	    write_ps_trailer(fp);

	    fp.close();
	} catch ( FileNotFoundException e1 ) {
	    logger.error( e1.toString() );
	}
    }

    // ===================================================================================
    /** @brief Write postscript header.
     *  @param psfile_name name of the postscript file for Title: xxxx
     *  @param psout pointer to a file.
     */
    // -----------------------------------------------------------------------------------
    protected void write_ps_header( String psfile_name, PrintWriter psout ) {
	// -------------------------------------------------------------------------------
	Calendar now = Calendar.getInstance();
	psout.printf( "%%!PS-Adobe-3.0\n");
	psout.printf( "%%%%============================================= BEGIN FILE =====\n");
	psout.printf( "%%%%Title: %s\n", psfile_name );
	psout.printf( "%%%%Creator: PSGraph (Java) TRNCMP15\n");
	psout.printf( "%%%%CreationDate: %s %s %d %d:%02d:%02d %04d\n",
		      now.getDisplayName( Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US ),
		      now.getDisplayName( Calendar.MONTH,       Calendar.SHORT, Locale.US ),
		      now.get( Calendar.DAY_OF_MONTH ),
		      now.get( Calendar.HOUR_OF_DAY ),
		      now.get( Calendar.MINUTE ),
		      now.get( Calendar.SECOND ),
		      now.get( Calendar.YEAR )
		      );
	psout.printf( "%%%%Orientation: Landscape\n");
	psout.printf( "%%%%Pages: %d\n", (int)num_page);
	psout.printf( "%%%%BoundingBox: 0 0 612 792\n");
	psout.printf( "%%%%DocumentPaperSizes: letter\n");
	psout.printf( "%%Magnification: 1.0000\n");
	psout.printf( "%%%%EndComments\n");
	psout.printf( "%%%%--------------------------------------------------------------\n");
	psout.printf( "%%%%BeginSetup\n");
	psout.printf( "[{\n");
	psout.printf( "%%%%BeginFeature: *PageRegion Letter\n");
	psout.printf( "<</PageSize [612 792]>> setpagedevice\n");
	psout.printf( "%%%%EndFeature\n");
	psout.printf( "} stopped cleartomark\n");
	psout.printf( "%%%%EndSetup\n");
	psout.printf( "%%%%--------------------------------------------------------------\n");
	psout.printf( "%%%%BeginProlog\n");
	psout.printf( "%%%%----------------------\n");
	psout.printf( "\n");
	psout.printf( "userdict /forms_ops 10 dict dup begin put\n");
	psout.printf( "\n");
	psout.printf( "/StartEPSF { %% prepare for EPSF inclusion\n");
	psout.printf( "userdict begin\n");
	psout.printf( "/PreEPS_state save def\n");
	psout.printf( "/dict_stack countdictstack def\n");
	psout.printf( "/ops_count count 1 sub def\n");
	psout.printf( "/showpage {} def\n");
	psout.printf( "} bind def\n");
	psout.printf( "\n");
	psout.printf( "/EPSFCleanUp { %% clean up after EPSF inclusion\n");
	psout.printf( "count ops_count sub {pop} repeat\n");
	psout.printf( "countdictstack dict_stack sub {end} repeat\n");
	psout.printf( "PreEPS_state restore\n");
	psout.printf( "end %% userdict\n");
	psout.printf( "} bind def\n");
	psout.printf( "\n");
	psout.printf( "%%%%----------------------\n");
	psout.printf( "\n");
	psout.printf( "/$PSGDict 200 dict def\n");
	psout.printf( "/$PSGBegin {$PSGDict begin /$PSGSaveState save def} def\n");
	psout.printf( "/$PSGEnd   {$PSGSaveState restore end} def\n");
	psout.printf( "%%%%----------------------\n");
	psout.printf( "/startpage {\n");
	psout.printf( "save\n");
	psout.printf( "newpath 0 792 moveto 0 0 lineto 612 0\n");
	psout.printf( "lineto 612 792 lineto closepath clip newpath\n");
	psout.printf( "$PSGBegin\n");
	psout.printf( "10 setmiterlimit 0 setlinejoin 0 setlinecap 0 setlinewidth\n");
	psout.printf( "612 0 translate 90 rotate 72 72 scale\n");
	psout.printf( "} bind def\n");
	psout.printf( "%%%%----------------------\n");
	psout.printf( "/stoppage { $PSGEnd restore } bind def\n");
	psout.printf( "/TIMESB { /NimbusRomNo9L-Med findfont setfont } bind def\n");
	psout.printf( "/TIMESI { /NimbusRomNo9L-RegIta findfont setfont } bind def\n");
	psout.printf( "/TIMES  { /NimbusRomNo9L-Reg findfont setfont } bind def\n");
	psout.printf( "/DL { newpath moveto lineto stroke } bind def\n");
	psout.printf( "/DR { newpath rectstroke } bind def\n");
	psout.printf( "/BS { %% x, y, width, height, string\n");
	psout.printf( "gsave\n");
	psout.printf( "  /str exch def /rot exch def /hgt exch def /wdt exch def\n");
	psout.printf( "  /yco exch def /xco exch def\n");
	psout.printf( "  xco yco translate 0 0 moveto rot rotate\n");
	psout.printf( "  wdt str stringwidth pop div hgt scale str show\n");
	psout.printf( "grestore\n");
	psout.printf( "} bind def\n");
	psout.printf( "%%%%EndProlog\n");
    }

    // ===================================================================================
    /** @brief Write postscript trailer.
     *  @param psout pointer to a file.
     */
    // -----------------------------------------------------------------------------------
    protected void write_ps_trailer ( PrintWriter psout ) {
	// -------------------------------------------------------------------------------
	psout.printf( "%%%%=============================================== END FILE =====\n");
	psout.printf( "%%%%Trailer\n");
	psout.printf( "%%EOF\n");
    }
}

// =======================================================================================
// **                                   P S G R A P H                                   **
// ======================================================================== END FILE =====
