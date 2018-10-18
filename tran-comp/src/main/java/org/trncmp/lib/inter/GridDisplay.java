// ====================================================================== BEGIN FILE =====
// **                               G R I D D I S P L A Y                               **
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
 * @brief   Interactive Grid Display.
 * @file    GridDisplay.java
 *
 * @details Provides the interface and procedures for a simple interactive grid display.
 *
 * @author  Stephen W. Soliday
 * @date    2018-10-17
 */
// =======================================================================================

package org.trncmp.lib.inter;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;



// =======================================================================================
public class GridDisplay extends JFrame {
  // -------------------------------------------------------------------------------------

  int[][] grid    = null;
  int     num_row = 0;
  int     num_col = 0;

  Panel   panel   = null;

  public int  get  ( int r, int c )        { return grid[r][c]; }
  public void set  ( int r, int c, int v ) { grid[r][c] = v; }
  public int  nRow ()                      { return num_row; }
  public int  nCol ()                      { return num_row; }

  // =====================================================================================
  public void addEventListener( EventListener el ) {
    // -----------------------------------------------------------------------------------
    panel.addEventListener( el );
  }

  // =====================================================================================
  public static interface EventListener {
    // -----------------------------------------------------------------------------------

    abstract public void mouseClicked ( int r, int c, int button );
    abstract public void keyPressed   ( int key );

  } // end class GridDisplay.Panel

  
  // =====================================================================================
  public static class Builder {
    // -----------------------------------------------------------------------------------

    String frame_title        = "GridDisplay";
    int    grid_nrow          = 0;
    int    grid_ncol          = 0;
    int    init_screen_width  = 640;
    int    init_screen_height = 480;

    
    // ===================================================================================
    public Builder( int nr, int nc ) {
      // ---------------------------------------------------------------------------------
      grid_nrow = nr;
      grid_ncol = nc;
    }

    
    public Builder width  ( int    v ) { init_screen_width  = v;  return this; }
    public Builder height ( int    v ) { init_screen_height = v;  return this; }
    public Builder title  ( String v ) { frame_title        = v;  return this; }

    
    // ===================================================================================
    public GridDisplay build() {
      // ---------------------------------------------------------------------------------

      int cell_width  = (init_screen_width  / grid_ncol) + 1;
      int cell_height = (init_screen_height / grid_nrow) + 1;

      int actual_width  = ( grid_ncol * cell_width ) - 1;
      int actual_height = ( grid_nrow * cell_height) - 1;

      GridDisplay gd = new GridDisplay( grid_nrow, grid_nrow );
      
      gd.initUI( frame_title, actual_width, actual_height );
      
      return gd;
    }
    

  } // end class GridDisplay.Builder

  
  // =====================================================================================
  protected GridDisplay( int nr, int nc ) {
    // -----------------------------------------------------------------------------------
    num_row = nr;
    num_col = nc;
    grid    = new int[nr][nc];
    for ( int r=0; r<nr; r++ ) {
      for ( int c=0; c<nc; c++ ) {
        grid[r][c] = 0;
      }
    }
  }


  // =====================================================================================
  private void initUI( String title, int w, int h ) {
    // -----------------------------------------------------------------------------------
    panel = new Panel( this, w, h );
    add( panel );
  
    // ------------------------------------------------
    addWindowListener(new WindowAdapter() {
        // --------------------------------------------
        @Override
        // --------------------------------------------
        public void windowClosing(WindowEvent e) {
          // ------------------------------------------
          System.out.println( "GridDisplay is closing" );
        }
      });

    setTitle( title );
    setSize( w, h );
    setLocationRelativeTo( null );
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
  }

  
  // =====================================================================================
  public static class Panel extends JPanel implements MouseListener, KeyListener {
    // -----------------------------------------------------------------------------------

    GridDisplay gd = null;

    EventListener ev_list = null;
    
    // ===================================================================================
    public void addEventListener( EventListener el ) {
      // ---------------------------------------------------------------------------------
      ev_list = el;
    }

    
    // ===================================================================================
    public Panel( GridDisplay g, int w, int h ) {
      // ---------------------------------------------------------------------------------
      gd = g;
    }
    

    // ===================================================================================
    @Override
    public void paintComponent(Graphics g) {
      // ---------------------------------------------------------------------------------
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int nr = gd.nRow();
        int nc = gd.nCol();
        
        int w = getWidth();
        int h = getHeight();

        int cw = (w +1) / nc;
        int ch = (h +1) / nr;

        // ----- draw horizontal grid lines ---------------------
        for ( int i=1; i<nr; i++ ) {
          int y = i*ch;
          g2d.drawLine( 0, y, w-1, y );
        }

        // ----- draw vertical grid lines -----------------------
        for ( int i=1; i<nr; i++ ) {
          int x = i*cw;
          g2d.drawLine( x, 0, x, h-1 );
        }

        // ----- draw cells -------------------------------------
        for ( int r=0; r<nr; r++ ) {
          for ( int c=0; c<nc; c++ ) {
            int x = c*cw;
            int y = r*ch;
            if ( 0 == gd.get(r,c) ) {
              g2d.setColor(Color.red);
            } else {
              g2d.setColor(Color.blue);
            }
            g2d.fillRect( x+1, y+1, cw-2, ch-2 );
          }
        }
        
    }




    public void mouseClicked  ( MouseEvent e ) {}
    public void mouseEntered  ( MouseEvent e ) {}
    public void mouseExited   ( MouseEvent e ) {}
    public void mousePressed  ( MouseEvent e ) {}
    public void mouseReleased ( MouseEvent e ) {}
    public void keyPressed    ( KeyEvent e )   {}
    public void keyReleased   ( KeyEvent e )   {}
    public void keyTyped      ( KeyEvent e )   {}
    







    

  } // end class GridDisplay.Panel

  
  
} // end class GridDisplay


// =======================================================================================
// **                               G R I D D I S P L A Y                               **
// ======================================================================== END FILE =====
