// ====================================================================== BEGIN FILE =====
// **                              T R A F F I C L I G H T                              **
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
 * @brief   Traffic Light Widget.
 * @file    TrafficLight.java
 *
 * @details Provides a status widget.
 *
 * @author  Stephen W. Soliday
 * @date    2018-10-17
 */
// =======================================================================================

package org.trncmp.lib.inter;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.Dimension;

// =======================================================================================
public class TrafficLight extends JPanel {
  // -------------------------------------------------------------------------------------

  public static final int HORIZONTAL = 0;
  public static final int VERTICAL   = 1;

  boolean red_light    = false;
  boolean yellow_light = false;
  boolean green_light  = false;

  int orientation = VERTICAL;

  protected final Color RED_ON      = new Color( 255,   0,  0 );
  protected final Color RED_OFF     = new Color(  64,  32, 32 );
   
  protected final Color YELLOW_ON   = new Color( 255, 255,  0 );
  protected final Color YELLOW_OFF  = new Color(  64,  64, 32 );
   
  protected final Color GREEN_ON    = new Color(   0, 255,  0 );
  protected final Color GREEN_OFF   = new Color(  32,  64, 32 );
   
  protected final Color BACK_GROUND = new Color(  32,  32, 32 );

  protected static final int LL = 38 * 2;
  protected static final int WW = 14 * 2;
   

  // =====================================================================================
  public TrafficLight() {
    // -------------------------------------------------------------------------------------
    orientation = VERTICAL;
    setPreferredSize( new Dimension( WW, LL ) );
    setMinimumSize( new Dimension( WW, LL ) );
  }

  
  // =====================================================================================
  public TrafficLight( int orient ) {
    // -------------------------------------------------------------------------------------
    orientation = orient;
    if ( VERTICAL == orient ) {
      setPreferredSize( new Dimension( WW, LL ) );
      setMinimumSize( new Dimension( WW, LL ) );
    } else {
      setPreferredSize( new Dimension( LL, WW ) );
      setMinimumSize( new Dimension( LL, WW ) );
    }
  }

  // =====================================================================================
  /** Paint Component
   *  @param g reference to a graphics contect.
   *
   *  Render the widget.
   */
  // -------------------------------------------------------------------------------------
  @Override
  public void paintComponent(Graphics g) {
    // -----------------------------------------------------------------------------------
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int w = getWidth();
    int h = getHeight();
    System.out.format( "W: %4d %4d\n", w, h );

    int rx = 0;    int rw = 0;
    int ry = 0;    int rh = 0;
    int yx = 0;    int yw = 0;
    int yy = 0;    int yh = 0;
    int gx = 0;    int gw = 0;
    int gy = 0;    int gh = 0;
   
    if ( VERTICAL == orientation ) {
      // ============================
      int hpad = w / 10;
      if ( hpad < 2 ) { hpad = 2; }
      int wdt = w - (2*hpad);
      rx = hpad;  rw = wdt;
      yx = hpad;  yw = wdt;
      gx = hpad;  gw = wdt;
      // ----------------------------
      int vpad = h / 10;
      if ( vpad < 2 ) { vpad = 2; }
      int hgt = (h - (4*vpad))/3;
      int i = vpad;
      ry = i;  rh = hgt; i += (vpad + hgt);
      yy = i;  yh = hgt; i += (vpad + hgt);
      gy = i;  gh = hgt;
      // ----------------------------
    } else {
      // ============================
      int hpad = w / 10;
      if ( hpad < 2 ) { hpad = 2; }
      int wdt = (w - (4*hpad))/3;
      int i = hpad;
      rx = i;  rw = wdt; i += (hpad + wdt);
      yx = i;  yw = wdt; i += (hpad + wdt);
      gx = i;  gw = wdt;
      // ----------------------------
      int vpad = h / 10;
      if ( vpad < 2 ) { vpad = 2; }
      int hgt = h - (2*vpad);
      ry = vpad;  rh = hgt;
      yy = vpad;  yh = hgt;
      gy = vpad;  gh = hgt;
      // ----------------------------
    }
    
    g2d.setColor( BACK_GROUND );
    g2d.fillRect( 0, 0, w, h );
    
    if ( red_light ) {
      g2d.setColor( RED_ON );
    } else {
      g2d.setColor( RED_OFF );
    }
    g2d.fillOval( rx, ry, rw, rh );

    if ( yellow_light ) {
      g2d.setColor( YELLOW_ON );
    } else {
      g2d.setColor( YELLOW_OFF );
    }
    g2d.fillOval( yx, yy, yw, yh );

    if ( green_light ) {
      g2d.setColor( GREEN_ON );
    } else {
      g2d.setColor( GREEN_OFF );
    }
    g2d.fillOval( gx, gy, gw, gh );

    System.out.format( "R: %4d %4d %4d %4d\n",   rx, ry, rw, rh );
    System.out.format( "Y: %4d %4d %4d %4d\n",   yx, yy, yw, yh );
    System.out.format( "G: %4d %4d %4d %4d\n\n", gx, gy, gw, gh );

  }
  
 
  // =====================================================================================
  public void allOn()    {
    // -----------------------------------------------------------------------------------
    red_light    = true;
    yellow_light = true;
    green_light  = true;
    repaint();
  }

  
  public void redOn()    { red_light    = true; repaint(); }
  public void yellowOn() { yellow_light = true; repaint(); }
  public void greenOn()  { green_light  = true; repaint(); }

  
  // =====================================================================================
  public void allOff()    {
    // -----------------------------------------------------------------------------------
    red_light    = false;
    yellow_light = false;
    green_light  = false;
    repaint();
  }

  
  public void redOff()    { red_light    = false; repaint(); }
  public void yellowOff() { yellow_light = false; repaint(); }
  public void greenOff()  { green_light  = false; repaint(); }

} // end class TrafficLight


// =======================================================================================
// **                              T R A F F I C L I G H T                              **
// ======================================================================== END FILE =====
