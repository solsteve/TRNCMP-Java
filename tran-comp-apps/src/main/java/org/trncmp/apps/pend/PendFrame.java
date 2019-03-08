// ====================================================================== BEGIN FILE =====
// **                                 P E N D F R A M E                                 **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2019, Stephen W. Soliday                                           **
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
 * @brief   Frame for the 
 * @file    PendFrame.java
 *
 * @details Provides the interface and procedure
 *
 * @author  Stephen W. Soliday
 * @date    2019-01-01
 */
// =======================================================================================

package org.trncmp.apps.pend;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// =======================================================================================
public class PendFrame extends JFrame {
  // -------------------------------------------------------------------------------------

  PendPanel panel = null;

  /** listener for button presses */
  DemoButtonListener demo_button_listener = null;

  JButton    run_stop_button = null;
  JButton    reset_button    = null;
  JButton    load_button     = null;
  JButton    save_button     = null;
  JTextField status_display  = null;

  public final String[] rs_label = { "RUN", "PAUSE" };

  public final int GSTOPPED = 0;
  public final int GRUNNING = 1;
  
  int current_state = GSTOPPED;

  
  // =====================================================================================
  public PendFrame( PendEngine eng ) {
    // -----------------------------------------------------------------------------------
    panel = new PendPanel(eng);
    initUI( "Inverted Pendulum", 1024, 768 );
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void addButtonListener( DemoButtonListener gbl ) {
    // -----------------------------------------------------------------------------------
    demo_button_listener = gbl;
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void toggle_run_stop_button() {
    // -----------------------------------------------------------------------------------
    if ( current_state == GRUNNING ) {
      demo_button_listener.requestStop();
      current_state = GSTOPPED;
    } else {
      demo_button_listener.requestRun();
      current_state = GRUNNING;
    }

    run_stop_button.setText(rs_label[current_state]);
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void press_reset_button() {
    // -----------------------------------------------------------------------------------
    if ( current_state == GSTOPPED ) {
      if ( null != demo_button_listener ) {
        demo_button_listener.resetButtonPressed();
      }
    }
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void press_load_button() {
    // -----------------------------------------------------------------------------------
    if ( current_state == GSTOPPED ) {
      if ( null != demo_button_listener ) {
        demo_button_listener.loadButtonPressed();
      }
    }
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void press_save_button() {
    // -----------------------------------------------------------------------------------
    if ( current_state == GSTOPPED ) {
      if ( null != demo_button_listener ) {
        demo_button_listener.saveButtonPressed();
      }
    }
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void close_action() {
    // -----------------------------------------------------------------------------------
    if ( null != demo_button_listener ) {
      demo_button_listener.closeAction();
    }
  }

  
  // =====================================================================================
  private void initUI( String title, int w, int h ) {
    // -----------------------------------------------------------------------------------
    
    current_state = GSTOPPED;

    JPanel button_panel = new JPanel();

    run_stop_button = new JButton(rs_label[current_state]);
    reset_button    = new JButton("RESET");
    load_button     = new JButton("LOAD");
    save_button     = new JButton("SAVE");
    status_display  = new JTextField();
    status_display.setEditable( false );

    button_panel.add( run_stop_button );
    button_panel.add( reset_button );
    button_panel.add( load_button );
    button_panel.add( save_button );

    add( button_panel,   BorderLayout.PAGE_START );
    add( panel,          BorderLayout.CENTER );
    add( status_display, BorderLayout.PAGE_END );


    // ------------------------------------------------------
    run_stop_button.addActionListener( new ActionListener() {
        // --------------------------------------------------
        @Override
        public void actionPerformed(ActionEvent e) {
          toggle_run_stop_button();
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
    load_button.addActionListener( new ActionListener() {
        // --------------------------------------------------
        @Override
        public void actionPerformed(ActionEvent e) {
          press_load_button();
        }
      });
  
     // ------------------------------------------------------
    save_button.addActionListener( new ActionListener() {
        // --------------------------------------------------
        @Override
        public void actionPerformed(ActionEvent e) {
          press_save_button();
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

  public void update() { panel.update();   }

   // =====================================================================================
  public void setStatus( String txt ) {
    // -----------------------------------------------------------------------------------
    status_display.setText( txt );
  }

} // end class PendFrame


// =======================================================================================
// **                                 P E N D F R A M E                                 **
// ======================================================================== END FILE =====
