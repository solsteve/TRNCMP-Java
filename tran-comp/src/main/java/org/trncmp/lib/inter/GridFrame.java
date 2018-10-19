// ====================================================================== BEGIN FILE =====
// **                                 G R I D F R A M E                                 **
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
 * @brief   Frame for the Interactive Grid Display.
 * @file    GridFrame.java
 *
 * @details Provides the interface and procedures a frame to house the GridPanel.
 *
 * @author  Stephen W. Soliday
 * @date    2018-10-17
 */
// =======================================================================================

package org.trncmp.lib.inter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// =======================================================================================
public class GridFrame extends JFrame implements KeyListener {
  // -------------------------------------------------------------------------------------

  GridPanel panel = null;

  /** listener for keyboard actions */
    GridKeyListener grid_key_listener = null;

  JButton    run_button     = null;
  JButton    reset_button   = null;
  JButton    quit_button    = null;
  JTextField status_display = null;

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
    public GridFrame build() {
      // ---------------------------------------------------------------------------------

      int cell_width  = (init_screen_width  / grid_ncol) + 1;
      int cell_height = (init_screen_height / grid_nrow) + 1;

      int actual_width  = ( grid_ncol * cell_width ) - 1;
      int actual_height = ( grid_nrow * cell_height) - 1;

      GridFrame gf = new GridFrame( grid_nrow, grid_nrow );
      
      gf.initUI( frame_title, actual_width, actual_height );
      
      return gf;
    }
    

  } // end class GridFrame.Builder

  
  // =====================================================================================
  protected GridFrame( int nr, int nc ) {
    // -----------------------------------------------------------------------------------
    panel = new GridPanel( nr, nc );
    addKeyListener(this);
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void addGridListener( GridEventListener gel ) {
    // -----------------------------------------------------------------------------------
    panel.addListener( gel );
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void addKeyboardListener( GridKeyListener gkl ) {
    // -----------------------------------------------------------------------------------
    grid_key_listener = gkl;
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void press_run_button() {
    // -----------------------------------------------------------------------------------
    System.out.println( "the run button pressed" );
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void press_reset_button() {
    // -----------------------------------------------------------------------------------
    System.out.println( "the reset button pressed" );
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void press_quit_button() {
    // -----------------------------------------------------------------------------------
    System.out.println( "the quit button pressed" );
    close_action();
    System.exit(0);
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void close_action() {
    // -----------------------------------------------------------------------------------
    System.out.println( "take close action" );
  }


  

  
  // =====================================================================================
  private void initUI( String title, int w, int h ) {
    // -----------------------------------------------------------------------------------
    
    JPanel button_panel = new JPanel();

    run_button     = new JButton("RUN");
    reset_button   = new JButton("RESET");
    quit_button    = new JButton("QUIT");
    status_display = new JTextField();
    status_display.setEditable( false );

    button_panel.add( run_button );
    button_panel.add( reset_button );
    button_panel.add( quit_button );

    add( button_panel,     BorderLayout.PAGE_START );
    add( panel,     BorderLayout.CENTER );
    add( status_display, BorderLayout.PAGE_END );


    // ------------------------------------------------------
    run_button.addActionListener( new ActionListener() {
        // --------------------------------------------------
        @Override
        public void actionPerformed(ActionEvent e) {
          press_run_button();
        }
      });
  
    // ------------------------------------------------------
    reset_button.addActionListener( new ActionListener() {
        // --------------------------------------------------
        @Override
        public void actionPerformed(ActionEvent e) {
          press_reset_button();
        }
      });
  
     // ------------------------------------------------------
    quit_button.addActionListener( new ActionListener() {
        // --------------------------------------------------
        @Override
        public void actionPerformed(ActionEvent e) {
          press_quit_button();
        }
      });
  
     // ------------------------------------------------------
    addWindowListener(new WindowAdapter() {
        // --------------------------------------------------
        @Override
        public void windowClosing(WindowEvent e) {
          close_action();
        }
      });

    setTitle( title );
    setSize( w, h );
    setLocationRelativeTo( null );
    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
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
    return panel.get( r, c );
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
    panel.set( r, c, v );
  }

   // =====================================================================================
  /** @brief Number of Grid Rows.
   *  @return Number or rows in the grid.
   */
  // -------------------------------------------------------------------------------------
  public int nRow() {
    // -----------------------------------------------------------------------------------
    return panel.nRow();
  }

  
  // =====================================================================================
  /** @brief Number of Grid Columns.
   *  @return Number or columns in the grid.
   */
  // -------------------------------------------------------------------------------------
  public int nCol() {
    // -----------------------------------------------------------------------------------
    return panel.nCol();
  }

  
  public void keyPressed( KeyEvent e ) {
    if ( null != grid_key_listener ) {
      grid_key_listener.keyPressed(e);
    }
  }
  
  public void keyReleased ( KeyEvent e ) {}
  public void keyTyped    ( KeyEvent e ) {}

  
} // end class GridFrame


// =======================================================================================
// **                               G R I D D I S P L A Y                               **
// ======================================================================== END FILE =====
