// ====================================================================== BEGIN FILE =====
// **                                  G R I D T E S T                                  **
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
 * @file    GridTest.java
 *
 * @details Provides the interface and procedures testing the GridPanel.
 *
 * @author  Stephen W. Soliday
 * @date    2018-10-17
 */
// =======================================================================================

package org.trncmp.apps.map;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;

import org.trncmp.lib.Dice;

import org.trncmp.lib.inter.GridFrame;
import org.trncmp.lib.inter.GridEventListener;
import org.trncmp.lib.inter.GridKeyListener;

// =======================================================================================
public class GridTest {
  // -------------------------------------------------------------------------------------

  static Dice dd = Dice.getInstance();
  
  static void randomize( GridFrame gd ) {
    int nr = gd.nRow();
    int nc = gd.nCol();
    for ( int r=0; r<nr; r++ ) {
      for ( int c=0; c<nc; c++ ) {
        int t = ((0.5 < dd.uniform()) ? (0) : (1));
        gd.set( r, c, t );
      }
    }
  }


  // =====================================================================================
  static class Checkers implements GridEventListener, GridKeyListener {
    // -----------------------------------------------------------------------------------

    GridFrame grid = null;

    // ===================================================================================
    public Checkers( GridFrame g ) {
      // ---------------------------------------------------------------------------------
      grid = g;
      grid.addGridListener( this );
      grid.addKeyboardListener( this );
    }

    // ===================================================================================
    public void cellClicked( int r, int c, int button ) {      
      // ---------------------------------------------------------------------------------
      System.out.format( "Mouse(%d,%d) = %d\n", r, c, button );
    }
    
    // ===================================================================================
    public void keyPressed( KeyEvent e ) {
      // ---------------------------------------------------------------------------------
      System.out.format( "Key = %d\n", e.getKeyCode() );
    }

  } // end class GridTest.Checkers
  


  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------

    dd.seed_set();

    EventQueue.invokeLater(new Runnable() {
        @Override
        public void run() {

          GridFrame grid = new GridFrame.Builder( 10, 10 )
              .width(640)
              .height(480)
              .title("Test")
              .build();

          randomize( grid );

          Checkers C = new Checkers( grid );

          grid.setVisible(true);
        }
      });
  }

} // end class MapTest
