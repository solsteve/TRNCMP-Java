// ====================================================================== BEGIN FILE =====
// **                                    P S D R A W                                    **
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
 * @file PSDraw.java
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
public class PSDraw extends PSWindow {
    // -----------------------------------------------------------------------------------
  private static final Logger logger = LogManager.getLogger();

    protected int         id            = -1;

    protected String         temp_filename   = null;
    protected FileWriter     file_writer     = null;
    protected PrintWriter    print_writer    = null;
    protected FileReader     file_reader     = null;
    protected BufferedReader buffered_reader = null;

    protected static final int FILE_CLOSED = 0;
    protected static final int OPEN_READ   = 1;
    protected static final int OPEN_APPEND = 2;
    protected int              file_state  = FILE_CLOSED;

    protected static int  window_count  = 0;

    protected double world_x1 = -1.0;  //< windows lower left  device x coordinate
    protected double world_y1 = -1.0;  //< windows lower left  device y coordinate
    protected double world_x2 =  1.0;  //< windows upper right device x coordinate
    protected double world_y2 =  1.0;  //< windows upper right device x coordinate

    protected double slope_x  =  1.0;
    protected double slope_y  =  1.0;
    protected double inter_x  =  0.0;
    protected double inter_y  =  0.0;

    protected PSColor last    =  new PSColor();
    protected PSColor saved   =  new PSColor();

    // ===================================================================================
    /**
     */
    // -----------------------------------------------------------------------------------
    protected boolean OpenTempFileRead() {
	// -------------------------------------------------------------------------------
	logger.debug("OpenTempFileRead("+temp_filename+")");

	switch( file_state ) {

	case OPEN_APPEND: // ----- the file is currently opened for appending ------------
	    logger.debug("   OPEN_APPEND ==> FILE_CLOSED");
	    try {
		file_writer.flush();
		file_writer.close();
	    } catch( IOException e ) { }
	    file_writer  = null;
	    print_writer = null;
	    file_state   = FILE_CLOSED;

	case FILE_CLOSED: // ----- the file is currently closed --------------------------
	    logger.debug("   FILE_CLOSED ==> OPEN_READ");
	    try {
		file_reader     = new FileReader( temp_filename );
		buffered_reader = new BufferedReader( file_reader );
		file_state      = OPEN_READ;
		return false;
	    } catch( FileNotFoundException e ) {
		logger.error( "Failed to open temp file ["+temp_filename+"]" );
	    }
	    break;

	case OPEN_READ: // ----- the file is currently open for reading ------------------
	    logger.debug("   OPEN_READ ==> OPEN_READ");
	    // DO NOTHING
	    return false;

	default: // ----- the file is in an unknown state --------------------------------
	    logger.error( "File is in unknown state" );
	    if ( null != file_writer ) {
		try {
		    file_writer.flush();
		    file_writer.close();
		} catch( IOException e ) { }
	    }
	    file_writer  = null;
	    print_writer = null;
	    file_state   = FILE_CLOSED;
	}

	return true;
    }

    // ===================================================================================
    /**
     */
    // -----------------------------------------------------------------------------------
    protected boolean OpenTempFileAppend() {
	// -------------------------------------------------------------------------------
	logger.debug("OpenTempFileAppend("+temp_filename+")");

	switch( file_state ) {

	case OPEN_READ: // ----- the file is currently open for reading ------------------
	    logger.debug("   OPEN_READ ==> FILE_CLOSED");
	    try {
		file_reader.close();
	    } catch( IOException e ) { }
	    file_reader     = null;
	    buffered_reader = null;
	    file_state      = FILE_CLOSED;

	case FILE_CLOSED: // ----- the file is currently closed --------------------------
	    logger.debug("   FILE_CLOSED ==> OPEN_APPEND");
	    try {
		file_writer  = new FileWriter(  temp_filename, true );
		print_writer = new PrintWriter( file_writer,   true );
		file_state = OPEN_APPEND;
		return false;
	    } catch( IOException e ) {
		logger.error( "Failed to open temp file ["+temp_filename+"]" );
	    }
	    break;

	case OPEN_APPEND: // ----- the file is currently opened for appending ------------
	    logger.debug("   OPEN_APPEND ==> OPEN_APPEND");
	    // DO NOTHING
	    return false;

	default: // ----- the file is in an unknown state --------------------------------
	    logger.error( "File is in unknown state" );
	    if ( null != file_writer ) {
		try {
		    file_writer.flush();
		    file_writer.close();
		} catch( IOException e ) { }
	    }
	    file_writer  = null;
	    print_writer = null;
	    file_state   = FILE_CLOSED;
	}

	return true;
    }

    // ===================================================================================
    /**
     */
    // -----------------------------------------------------------------------------------
    protected boolean CloseTempFile() {
	// -------------------------------------------------------------------------------
	logger.debug("CloseTempFile("+temp_filename+")");

	switch( file_state ) {

	case FILE_CLOSED: // ----- the file is currently closed --------------------------
	    logger.debug("   FILE_CLOSED ==> FILE_CLOSED");
	    // DO NOTHING
	    return false;

	case OPEN_READ: // ----- the file is currently open for reading ------------------
	    logger.debug("   OPEN_READ ==> FILE_CLOSED");
	    try {
		file_writer.close();
	    } catch( IOException e ) { }
	    file_state = FILE_CLOSED;
	    return false;

	case OPEN_APPEND: // ----- the file is currently opened for appending ------------
	    logger.debug("   OPEN_APPEND ==> FILE_CLOSED");
	    try {
		file_writer.close();
	    } catch( IOException e ) { }
	    file_state = FILE_CLOSED;
	    return false;

	default: // ----- the file is in an unknown state --------------------------------
	    logger.error( "File is in unknown state" );
	    if ( null != file_writer ) {
		try {
		    file_writer.close();
		} catch( IOException e ) { }
		file_state = FILE_CLOSED;
	    }
	}

	return true;
    }




    // ===================================================================================
    /** @brief Initialize.
     *  @param dw window width in device coordinates (inches).
     *  @param dh window height in device coordinates (inches).
     *  @param x1 windows lower left  device x coordinate
     *  @param y1 windows lower left  device y coordinate
     *  @param x2 windows upper right device x coordinate
     *  @param y2 windows upper right device x coordinate
     *  @return the true on error.
     *
     *  Initialize the physical position of this object on a device.
     */
    // -----------------------------------------------------------------------------------
    protected boolean init( double dw, double dh,
			    double x1, double y1, double x2, double y2 ) {
	// -------------------------------------------------------------------------------
	PSDraw.window_count += 1;
	id = PSDraw.window_count;
	// temp_filename = String.format( "pswork-%X-%X.tmp", PSGraph.psid, id );
	// temp_filename = SelfDeleteTempFile.getName();

	temp_filename = FileTools.tempName( "psg" );

	if ( OpenTempFileAppend() ) { return true; };

	world_x1 = x1;
	world_x2 = x2;
	world_y1 = y1;
	world_y2 = y2;

	double dx = world_x2 - world_x1;
	double dy = world_y2 - world_y1;

	slope_x = dw / dx;
	slope_y = dh / dy;

	inter_x = -slope_x * world_x1;
	inter_y = -slope_y * world_y1;

	return false;
    }

    // ===================================================================================
    /** @brief Constructor.
     *  @param dw window width in device coordinates (inches).
     *  @param dh window height in device coordinates (inches).
     *  @param x1 windows lower left  device x coordinate
     *  @param y1 windows lower left  device y coordinate
     *  @param x2 windows upper right device x coordinate
     *  @param y2 windows upper right device x coordinate
     */
    // -----------------------------------------------------------------------------------
    public PSDraw( double dw, double dh,
		   double x1, double y1, double x2, double y2 ) {
	// -------------------------------------------------------------------------------
	super(dw, dh);
	this.init(dw, dh, x1, y1, x2, y2);
    }

    // ===================================================================================
    /** @brief Constructor.
     *  @param dw window width in device coordinates (inches).
     *  @param dh window height in device coordinates (inches).
     */
    // -----------------------------------------------------------------------------------
    public PSDraw( double dw, double dh ) {
	// -------------------------------------------------------------------------------
	super(dw, dh);
	this.init(dw, dh, 0.0, 0.0, dw, dh);
    }


    // ===================================================================================
    /** @brief Destructor.
     */
    // -----------------------------------------------------------------------------------
    public void delete( ) {
	// -------------------------------------------------------------------------------
	logger.debug( "PSDraw::delete(id:"+id+")" );
	CloseTempFile();
	FileTools.delete( temp_filename );
    }


    protected double scaleX( double x ) { return ((slope_x * x) + inter_x); };
    protected double scaleY( double y ) { return ((slope_y * y) + inter_y); };

    // ===================================================================================
    /** @brief Draw Line.
     *  @param x1 starting x world coordinate for the line.
     *  @param y1 starting y world coordinate for the line.
     *  @param x2 ending   x world coordinate for the line.
     *  @param y2 ending   y world coordinate for the line.
     *
     *  Draw a line between two world coordinates.
     */
    // -----------------------------------------------------------------------------------
    public void drawLine( double x1, double y1, double x2,  double y2 ) {
	// -------------------------------------------------------------------------------
	print_writer.printf( "%.5f %.5f %.5f %.5f DL\n",
			     scaleX(x1), scaleY(y1), scaleX(x2), scaleY(y2) );
    }

    // ===================================================================================
    /** @brief Write Text.
     *  @param text string containing the text to write.
     *  @param xc starting x world coordinates.
     *  @param yc starting y world coordinates.
     *  @param w  width in world coordinates.
     *  @param h  height in world coordinates.
     *  @param theta rotation about the coordinates in degrees counter clockwise.
     *
     *  Scale a string of text within a box of width and height. Place the text at
     *  the x and y coordinates and rotate it counter clockwise theta degrees.
     */
    // -----------------------------------------------------------------------------------
    public void write( String text,
		       double xc, double yc, double w, double h, double theta ) {
	// -------------------------------------------------------------------------------
	double x      = scaleX(xc);
	double y      = scaleY(yc);
	double width  = scaleX(w) - x;
	double height = scaleY(h) - y;

	print_writer.printf( "%f %f %f %f %f (%s) BS\n",
			     x, y, width, height, theta, text );
    }

    // ===================================================================================
    /** @brief Write Text.
     *  @param text string containing the text to write.
     *  @param xc starting x world coordinates.
     *  @param yc starting y world coordinates.
     *  @param w  width in world coordinates.
     *  @param h  height in world coordinates.
     *
     *  Scale a string of text within a box of width and height. Place the text at
     *  the x and y coordinates.
     */
    // -----------------------------------------------------------------------------------
    public void write( String text,
		       double xc, double yc, double w, double h ) {
	// -------------------------------------------------------------------------------
	write( text, xc, yc, w, h, 0.0 );
    }

    // ===================================================================================
    /** @brief Write Text.
     *  @param text string containing the text to write.
     *  @param x1 starting x device coordinates.
     *  @param y1 starting y device coordinates.
     *  @param x2 width in device coordinates.
     *  @param y2 height in device coordinates.
     *  @param theta rotation about the starting coordinates in degrees counter clockwise.
     *
     *  Scale a string of text within a box of width and height. Place the text at
     *  the x and y coordinates and rotate it counter clockwise theta degrees.
     */
    // -----------------------------------------------------------------------------------
    public void write_inch( String text,
			    double x,  double y, double width, double height,
			    double theta ) {
	// -------------------------------------------------------------------------------
	print_writer.printf( "%f %f %f %f %f (%s) BS\n",
			     x, y, width, height, theta, text );
    }

    // ===================================================================================
    /** @brief Write Text.
     *  @param text string containing the text to write.
     *  @param x1 starting x device coordinates.
     *  @param y1 starting y device coordinates.
     *  @param x2 width in device coordinates.
     *  @param y2 height in device coordinates.
     *
     *  Scale a string of text within a box of width and height. Place the text at
     *  the x and y coordinates.
     */
    // -----------------------------------------------------------------------------------
    public void write_inch( String text,
			    double x, double y, double width, double height ) {
	// -------------------------------------------------------------------------------
	write_inch( text, x, y, width, height, 0.0 );
    }

    // ===================================================================================
    /** @brief Set Color.
     *  @param C Color color.
     *
     *  Set the current Color value.
     */
    // -----------------------------------------------------------------------------------
    public void setRGB( PSColor C ) {
	// -------------------------------------------------------------------------------
	last.copy( C );
	print_writer.printf( "%f %f %f setrgbcolor\n", C.r, C.g, C.b );
    }

    // ===================================================================================
    /** @brief Set Color.
     *  @param r red.
     *  @param g green.
     *  @param b blue.
     *
     *  Set the current RGB value.
     */
    // -----------------------------------------------------------------------------------
    public void setRGB( double r, double g, double b ) {
	// -------------------------------------------------------------------------------
	last.set( r, g, b );
	print_writer.printf( "%f %f %f setrgbcolor\n", r, g, b );
    }

    // ===================================================================================
    /** @brief Set Font
     *  @param ftype font as defined by PSGraph constants
     */
    // -----------------------------------------------------------------------------------
    public void setFont( int ftype ) {
	// -------------------------------------------------------------------------------
	switch(ftype) {
	case PSGraph.NORMAL: print_writer.printf( "TIMES\n"  ); break;
	case PSGraph.BOLD:   print_writer.printf( "TIMESB\n" ); break;
	case PSGraph.ITALIC: print_writer.printf( "TIMESI\n" ); break;
	default:
	    logger.error("What the frel");
	    print_writer.printf( "TIMES\n"  ); break;
	}
    }

    // ==========================================================================================
    /** @brief Save Color.
     *
     *  Save the current drawing color.
     */
    // ------------------------------------------------------------------------------------------
    public void saveColor( ) {
	// ----------------------------------------------------------------------------------------
	saved.copy( last );
    }

    // ==========================================================================================
    /** @brief Restore Color.
     *
     *  Restore the last saved drawing color.
     */
    // ------------------------------------------------------------------------------------------
    public void restoreColor( ) {
	// ----------------------------------------------------------------------------------------
	setRGB( saved );
    }

    // ==========================================================================================
    /** @brief Draw a ray.
     *  @param xc    x coordinate of the center of the ellipse.
     *  @param yc    y coordinate of the center of the ellipse.
     *  @param m     magnitude.
     *  @param theta angle in radians of the semi-marjor axis 
     *               counter clockwise from the positive x axis.
     *
     *  Draw a ray length m from (x,y) and rotated theta radians.
     */
    // ------------------------------------------------------------------------------------------
    public void drawRay( double xc, double yc, double m, double theta ) {
	// ----------------------------------------------------------------------------------------
	double x = xc + m*Math.cos(theta);
	double y = yc + m*Math.sin(theta);
	drawLine( xc, yc, x, y );
    }

    // ==========================================================================================
    /** @brief Draw an ellipse.
     *  @param xc    x coordinate of the center of the ellipse.
     *  @param yc    y coordinate of the center of the ellipse.
     *  @param a     value of the semi-major axis.
     *  @param b     value of the semi-minor axis.
     *  @param theta angle in radians of the semi-marjor axis 
     *               counter clockwise from the positive x axis.
     *  @param parts number of segments.
     *
     *  Draw an ellipse centered at (x,y) and rotated theta radians.
     */
    // ------------------------------------------------------------------------------------------
    public void drawEllipse( double xc, double yc,
			     double a, double b, double theta,
			     int parts ) {
	// ----------------------------------------------------------------------------------------
	double T  = 0.0;
	double dT = Math2.N_2PI / ((double) parts);
	double C  = Math.cos(theta);
	double S  = Math.sin(theta);

	double x0 = xc + C*a;
	double y0 = yc + S*a;

	for (int i=0; i<parts; i++) {
	    T += dT;
	    double X = a*Math.cos(T);
	    double Y = b*Math.sin(T);

	    double x1 = xc + C*X - S*Y;
	    double y1 = yc + S*X + C*Y;

	    drawLine( x0, y0, x1, y1 );
	    x0 = x1;
	    y0 = y1;
	}
    }

    // ==========================================================================================
    /** @brief Draw an ellipse.
     *  @param xc    x coordinate of the center of the ellipse.
     *  @param yc    y coordinate of the center of the ellipse.
     *  @param a     value of the semi-major axis.
     *  @param b     value of the semi-minor axis.
     *  @param theta angle in radians of the semi-marjor axis 
     *               counter clockwise from the positive x axis.
     *
     *  Draw an ellipse centered at (x,y) and rotated theta radians.
     */
    // ------------------------------------------------------------------------------------------
    public void drawEllipse( double xc, double yc,
			     double a, double b, double theta ) {
	// ----------------------------------------------------------------------------------------
	drawEllipse( xc, yc, a, b, theta, 64 );
    }

    // ==========================================================================================
    /** @brief Draw Polygon.
     *  @param x array of x world coordinates.
     *  @param y array of y world coordinates.
     *  @param n number of coordinates to use ( n <= x.length )
     *  @param fill fill the polygon if true.
     *
     *  Draw a polygon defined my a set of vertices and optionally fill.
     */
    // ------------------------------------------------------------------------------------------
    public void drawPolygon( double[] x, double[] y, int n, boolean fill ) {
	// ----------------------------------------------------------------------------------------
	print_writer.printf( "newpath\n  %.5f %.5f moveto\n", scaleX(x[0]), scaleY(y[0]) );
	for (int i=1; i<n; i++) {
	    print_writer.printf( "  %.5f %.5f lineto\n", scaleX(x[i]), scaleY(y[i]) );
	}
	print_writer.printf( "closepath " );

	if (fill) {
	    print_writer.printf( "fill\n " );
	} else {
	    print_writer.printf( "stroke\n " );
	}
    }

    // ==========================================================================================
    /** @brief Draw Polygon.
     *  @param x array of x world coordinates.
     *  @param y array of y world coordinates.
     *  @param n number of coordinates to use ( n <= x.length )
     *
     *  Draw a polygon defined my a set of vertices.
     */
    // ------------------------------------------------------------------------------------------
    public void drawPolygon( double[] x, double[] y, int n ) {
	// ----------------------------------------------------------------------------------------
	drawPolygon( x, y, n, false );
    }

    // ==========================================================================================
    /** @brief Draw Rectangle.
     *  @param x1 lower left  in device x coordinate
     *  @param y1 lower left  in device y coordinate
     *  @param x2 upper right in device x coordinate
     *  @param y2 upper right in device x coordinate
     *  @param fill fill the polygon if true.
     *
     *  Draw a rectangle in his window and optionally fill.
     */
    // ------------------------------------------------------------------------------------------
    public void drawRectangle( double x1, double y1, double x2, double y2, boolean fill ) {
	// ----------------------------------------------------------------------------------------
	double[] x = { x1, x2, x2, x1 };
	double[] y = { y1, y1, y2, y2 };

	// x[0] = x1;     y[0] = y1;
	// x[1] = x2;     y[1] = y1;
	// x[2] = x2;     y[2] = y2;
	// x[3] = x1;     y[3] = y2;

	drawPolygon( x, y, 4, fill );
    }

    // ==========================================================================================
    /** @brief Draw Rectangle.
     *  @param x1 lower left  in device x coordinate
     *  @param y1 lower left  in device y coordinate
     *  @param x2 upper right in device x coordinate
     *  @param y2 upper right in device x coordinate
     *
     *  Draw a rectangle in his windowl.
     */
    // ------------------------------------------------------------------------------------------
    public void drawRectangle( double x1, double y1, double x2, double y2 ) {
	// ----------------------------------------------------------------------------------------
	drawRectangle( x1, y1, x2, y2, false );
    }


    // ==========================================================================================
    /** @brief Draw Circle.
     *  @param xc    x coordinate of the center of the circle.
     *  @param yc    y coordinate of the center of the circle.
     *  @param r     value of the radius.
     *  @param parts number of segments.
     *
     *  Draw a circle of radius r centered at (x,y).
     */
    // ------------------------------------------------------------------------------------------
    public void drawCircle ( double xc, double yc, double r, int parts ) {
	// ----------------------------------------------------------------------------------------
	drawEllipse( xc, yc, r, r, 0.0, parts );
    }

    // ==========================================================================================
    /** @brief Draw Circle.
     *  @param xc    x coordinate of the center of the circle.
     *  @param yc    y coordinate of the center of the circle.
     *  @param r     value of the radius.
     *
     *  Draw a circle of radius r centered at (x,y).
     */
    // ------------------------------------------------------------------------------------------
    public void drawCircle ( double xc, double yc, double r ) {
	// ----------------------------------------------------------------------------------------
	drawEllipse( xc, yc, r, r, 0.0, 64 );
    }

    // ==========================================================================================
    /** @brief Draw border.
     *
     *  Draw a rectangle around the border of this window.
     */
    // ------------------------------------------------------------------------------------------
    public void drawBorder( ) {
	// ----------------------------------------------------------------------------------------
	drawLine( world_x1, world_y1,  world_x1, world_y2 );
	drawLine( world_x1, world_y2,  world_x2, world_y2 );
	drawLine( world_x2, world_y2,  world_x2, world_y1 );
	drawLine( world_x2, world_y1,  world_x1, world_y1 );
    }

    // =======================================================================================
    /** @brief Write postscript.
     *  @param fp print stream
     *
     */
    // ---------------------------------------------------------------------------------------
    public void pswrite( PrintWriter fp ) {
	// -------------------------------------------------------------------------------------

	logger.debug( "PSDraw::pswrite()" );
	OpenTempFileRead();
	write_ps_window_header(fp);
	String ts = null;
	try {
	    while( null != (ts = buffered_reader.readLine()) ) {
	    fp.println( ts );
	    }
	} catch( IOException e ) {
	    logger.debug( "EOF of temp file" );
	}

	write_ps_window_trailer(fp);
	OpenTempFileAppend();
    }

}

// =======================================================================================
// **                                    P S D R A W                                    **
// ======================================================================== END FILE =====
