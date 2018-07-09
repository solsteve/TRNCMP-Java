// ====================================================================== BEGIN FILE =====
// **                             J T E S T _ P S G R A P H                             **
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
 * @file jtest_psgraph.java
 *
 * @author Stephen W. Soliday
 * @date 2015-08-26
 */
// =======================================================================================

package org.trncmp.test;

import  org.trncmp.lib.*;
import org.apache.log4j.*;

// =======================================================================================
// ---------------------------------------------------------------------------------------
public class jtest_psgraph {
  // -----------------------------------------------------------------------------------


  // ===================================================================================
  public static void Test01( ) {
    // -------------------------------------------------------------------------------
    PSGraph ps = new PSGraph(2);
    PSDraw  pd = new PSDraw( 7.5, 7.5, -10.0, -10.0, 10.0, 10.0 );

    pd.setRGB( 0.0, 0.0, 1.0 );
    pd.drawBorder();

    pd.setRGB( 1.0, 0.0, 0.0 );
    pd.drawEllipse( 0.0, 0.0, 6.0, 3.0, 30.0 );

    ps.add( pd, 0, 0.5, 0.5 );

    ps.pswrite( "fix.ps" );

    ps.delete();
  }


  
  // ===================================================================================
  public static void main( String[] args ) {
    // -------------------------------------------------------------------------------

    Logger logger = LogManager.getRootLogger();
      
    Test01();

    PSGraph ps = new PSGraph(2);

    PSDraw pd01 = new PSDraw( 8.5, 3.0, -10.0, -10.0, 10.0, 10.0 );
    PSDraw pd02 = new PSDraw( 4.0, 3.0, -10.0, -10.0, 10.0, 10.0 );
    PSDraw pd03 = new PSDraw( 4.0, 2.0, -10.0, -10.0, 10.0, 10.0 );
    PSDraw pd04 = new PSDraw( 4.0, 0.5, -10.0, -10.0, 10.0, 10.0 );

    PSDraw pd11 = new PSDraw( 4.5, 3.5, -10.0, -10.0, 10.0, 10.0 );
    PSDraw pd12 = new PSDraw( 4.5, 2.5, -10.0, -10.0, 10.0, 10.0 );
    PSDraw pd13 = new PSDraw( 4.0, 6.5, 0.0, 0.0, 4.0, 6.5 );

    pd01.drawBorder();
    pd02.drawBorder();
    pd03.drawBorder();
    pd04.drawBorder();

    pd11.drawBorder();
    pd12.drawBorder();
    pd13.drawBorder();

    pd01.setRGB( 1.0, 0.0, 0.0 );
    pd01.drawEllipse( 1.0, 2.0, 3.0, 1.0, 45.0 );

    pd02.setRGB( PSColor.blue );
    pd02.drawLine( -9.0, -9.0, 9.0, 9.0 );

    pd04.setFont( PSGraph.NORMAL );
    pd04.write( "Heather", -10.0, -10.0, 10.0, 10.0 );

    pd03.setFont( PSGraph.ITALIC );
    pd03.write( "Rebekah", -8.0, -8.0, 2.0, 1.0, 30 );

    pd01.saveColor();

    pd01.setRGB( PSColor.cyan );
    pd01.setFont( PSGraph.ITALIC );
    pd01.write_inch( "Stephen", 0.0, 0.0, 2.0, 1.0, 30.0 );

    pd01.setRGB( PSColor.green );
    pd01.setFont( PSGraph.NORMAL );
    pd01.write_inch( "Stephen", 0.0, 0.0, 2.0, 1.0 );

    pd01.restoreColor();
    pd01.drawRay( -10.0, 10.0, 14.14, -45.0 );

    pd11.setFont( PSGraph.NORMAL );
    pd11.write_inch( "Roman Font",  0.5, 2.5, 2.0, 0.5 );
    pd11.setFont( PSGraph.BOLD );
    pd11.write_inch( "Bold Font",   0.5, 1.5, 2.0, 0.5 );
    pd11.setFont( PSGraph.ITALIC );
    pd11.write_inch( "Italic Font", 0.5, 0.5, 2.0, 0.5 );

    pd12.drawRectangle( -8.0, -8.0, -1.0, -1.0 );
    pd12.drawRectangle( 4.0, 4.0, 6.0, 5.0, true );


    double px1[] = { 0.8, 0.7, 1.4, 1.5, 3.2, 1.7, 2.0 };
    double py1[] = { 1.6, 2.6, 3.5, 4.2, 3.5, 2.5, 0.8 };
    int    pn1   = px1.length;

    double px2[] = { 1.2, 1.5, 2.2, 2.8, 3.3, 2.3 };
    double py2[] = { 5.0, 6.2, 5.2, 5.8, 5.3, 4.4 };
    int    pn2   = px2.length;

    pd13.drawPolygon( px1, py1, pn1 );
    pd13.setRGB( PSColor.blue );
    pd13.drawPolygon( px2, py2, pn2, true );

    ps.add( pd01, 0, 1.0, 1.0 );
    ps.add( pd02, 0, 1.0, 4.5 );
    ps.add( pd03, 0, 5.5, 5.5 );
    ps.add( pd04, 0, 5.5, 4.5 );

    ps.add( pd11, 1, 5.5, 1.0 );
    ps.add( pd12, 1, 5.5, 5.0 );
    ps.add( pd13, 1, 1.0, 1.0 );

    ps.pswrite( "psgraph-Java.ps" );

    ps.delete();

    System.exit(0);
  }
}

// =======================================================================================
// **                             J T E S T _ P S G R A P H                             **
// ======================================================================== END FILE =====
