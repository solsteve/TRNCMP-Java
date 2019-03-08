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
 * @date    2018-12-27
 */
// =======================================================================================

package org.trncmp.apps.connect4;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;

import org.trncmp.lib.Dice;

import org.trncmp.lib.inter.GridFrame;
import org.trncmp.lib.inter.GridEventListener;
import org.trncmp.lib.inter.GridKeyListener;
import org.trncmp.lib.inter.GridButtonListener;

// =======================================================================================
public class Game implements GridEventListener, GridKeyListener, GridButtonListener {
  // -------------------------------------------------------------------------------------

  protected static Dice dd     = Dice.getInstance();
  protected GameEngine  engine = null;
  protected GridFrame   grid   = null;
  protected int         order  = 0;

  int player = 0;
  
  // =====================================================================================
  public Game( int ord ) {
    // -----------------------------------------------------------------------------------

    order = ord;

    String name = String.format( "Connect %d", order );
    if ( 3 == order ) {
      name = "Tic-tac-toe";
    }

    engine = new GameEngine( order );
    
    grid = new GridFrame.Builder( order, order )
        .width(640)
        .height(640)
        .title(name)
        .build();

    grid.addGridListener( this );
    grid.addKeyboardListener( this );
    grid.addButtonListener( this );

    grid.setVisible(true);
  }


  // =====================================================================================
  public void cellClicked( int r, int c, int button ) {      
    // -----------------------------------------------------------------------------------
    if ( 0 == player ) {
      if ( 0 == engine.set( r, c, -1 ) ) {
        grid.setStatus( "Player 0 moved" );
        grid.greenOn();
        grid.redOff();
        player = 1;
      } else {
        grid.setStatus( "Player 0 bad move, try again" );
        grid.greenOff();
        grid.redOn();
      }
    } else {
      if ( 0 == engine.set( r, c, 1 ) ) {
        grid.setStatus( "Player 1 moved" );
        player = 0;
        grid.greenOn();
        grid.redOff();
      } else {
        grid.setStatus( "Player 1 bad move, try again" );
        grid.greenOff();
        grid.redOn();
      }
    }
    updateBoard();
  }
    
  // =====================================================================================
  public void keyPressed( KeyEvent e ) {
    // -----------------------------------------------------------------------------------
    System.out.format( "Key = %d\n", e.getKeyCode() );
  } 


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void requestRun() {
    // -----------------------------------------------------------------------------------
    System.out.println( "Run has been requested" );
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void requestStop() {
    // -----------------------------------------------------------------------------------
    System.out.println( "Stop has been requested" );
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void resetButtonPressed() {
    // -----------------------------------------------------------------------------------
    System.out.println( "the reset button pressed" );
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void closeAction() {
    // -----------------------------------------------------------------------------------
    System.out.println( "Bye..." );
  }


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void updateBoard() {
    // -----------------------------------------------------------------------------------
    int n = engine.getOrder();
    for ( int r=0; r<n; r++ ) {
      for ( int c=0; c<n; c++ ) {
        int x   = engine.get( r, c );
        int clr = 0;
        if ( x == -1 ) { clr = 1; }
        if ( x ==  1 ) { clr = 2; }
        grid.set( r, c, clr );
      }
    }
    grid.update();
  }



  

  
  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------

    dd.seed_set();

    EventQueue.invokeLater(new Runnable() {
        @Override
        public void run() {

          Game G = new Game(3);
        }
      });
  }

} // end class Game
