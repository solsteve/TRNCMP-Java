// ====================================================================== BEGIN FILE =====
// **                                 G R I D P A N E L                                 **
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
 * @brief   Panel for the Interactive Grid Display.
 * @file    GridPanel.java
 *
 * @details Provides the interface and procedures for a simple interactive grid panel.
 *
 * @author  Stephen W. Soliday
 * @date    2018-10-17
 */
// =======================================================================================

package org.trncmp.lib.inter;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;


// =======================================================================================
public class GridPanel extends JPanel implements MouseListener {
  // -------------------------------------------------------------------------------------

  /** Grid storage */
  protected int[][] grid = null;

  /** Number of rows in the grid */
  protected int num_row = 0;

  /** number of columns in the grid */
  protected int num_col = 0;

  /** listener for grid actions */
  GridEventListener grid_listener = null;

  protected final Color[] CELL_COLOR = { Color.white, Color.red, Color.blue };
  protected final int     MAX_COLOR  = 3;

  
  // =====================================================================================
  /** @brief Constructor.
   *  @param nr  Number or rows in the grid.
   *  @param nc  Number or columns in the grid.
   */
  // -------------------------------------------------------------------------------------
  public GridPanel( int nr, int nc ) {
    // -----------------------------------------------------------------------------------
    num_row = nr;
    num_col = nc;
    grid    = new int[nr][nc];

    for ( int r=0; r<nr; r++ ) {
      for ( int c=0; c<nc; c++ ) {
        grid[r][c] = 0;
      }
    }

    addMouseListener(this);
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void addListener( GridEventListener gl ) {
    // -----------------------------------------------------------------------------------
    grid_listener = gl;
  }

  
  // =====================================================================================
  /** @brief Get Grid Value.
   *  @param r row index.
   *  @param c coloumn index.
   *  @return value of the grid at grid(r,c).
   *
   *  Return the value contained at grid index (r,c)
   */
  // -------------------------------------------------------------------------------------
  public int get( int r, int c ) {
    // -----------------------------------------------------------------------------------
    return grid[r][c];
  }

  
  // =====================================================================================
  /** @brief Set Grid Value.
   *  @param r row index.
   *  @param c coloumn index.
   *  @param v  value of the grid at grid(r,c).
   *
   *  Set the value contained at grid index (r,c)
   */
  // -------------------------------------------------------------------------------------
  public void set( int r, int c, int v ) {
    // -----------------------------------------------------------------------------------
    grid[r][c] = v;
  }

  
  // =====================================================================================
  /** @brief Number of Grid Rows.
   *  @return Number or rows in the grid.
   */
  // -------------------------------------------------------------------------------------
  public int nRow() {
    // -----------------------------------------------------------------------------------
    return num_row;
  }

  
  // =====================================================================================
  /** @brief Number of Grid Columns.
   *  @return Number or columns in the grid.
   */
  // -------------------------------------------------------------------------------------
  public int nCol() {
    // -----------------------------------------------------------------------------------
    return num_row;
  }

  
  // =====================================================================================
  /** @brief Paint Component
   *  @param g reference to a graphics contect.
   *
   *  Render the grid.
   */
  // -------------------------------------------------------------------------------------
  @Override
  public void paintComponent(Graphics g) {
    // -----------------------------------------------------------------------------------
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    int w = getWidth();
    int h = getHeight();

    int cw = (w +1) / num_col;
    int ch = (h +1) / num_row;

    // ----- draw horizontal grid lines ---------------------
    for ( int i=1; i<num_row; i++ ) {
      int y = i*ch;
      g2d.drawLine( 0, y, w-1, y );
    }

    // ----- draw vertical grid lines -----------------------
    for ( int i=1; i<num_col; i++ ) {
      int x = i*cw;
      g2d.drawLine( x, 0, x, h-1 );
    }

    // ----- draw cells -------------------------------------
    for ( int r=0; r<num_row; r++ ) {
      for ( int c=0; c<num_col; c++ ) {
        int x = c*cw;
        int y = r*ch;
        g2d.setColor( CELL_COLOR[ grid[r][c] % MAX_COLOR ] );
        g2d.fillRect( x+1, y+1, cw-2, ch-2 );
      }
    }
        
  }


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void mouseClicked  ( MouseEvent e ) {
    // -----------------------------------------------------------------------------------
    if ( null != grid_listener ) {
      int cw = (getWidth()  + 1 ) / num_col;
      int ch = (getHeight() + 1 ) / num_row;
      int b = e.getButton();
      int r = e.getY() / ch;
      int c = e.getX() / cw;
      grid_listener.cellClicked( r, c, b );
    }
  }


  public void mouseEntered  ( MouseEvent e ) { }
  public void mouseExited   ( MouseEvent e ) { }
  public void mousePressed  ( MouseEvent e ) { }
  public void mouseReleased ( MouseEvent e ) { }
  
} // end class GridPanel


// =======================================================================================
// **                                 G R I D P A N E L                                 **
// ======================================================================== END FILE =====
