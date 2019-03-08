// ====================================================================== BEGIN FILE =====
// **                                G A M E E N G I N E                                **
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
 * @file GameEngine.java
 * <p>
 * 
 *
 * @date 2018-12-26
 *
 */
// =======================================================================================

package org.trncmp.apps.connect4;


// =======================================================================================
public class GameEngine {
  // -------------------------------------------------------------------------------------

  protected int     order = 0;
  protected int[][] board = null;


  public final int getOrder() { return order; }

  
  // =====================================================================================
  public void reset() {
    // -----------------------------------------------------------------------------------
    for ( int r=0; r<order; r++ ) {
      for ( int c=0; c<order; c++ ) {
        board[r][c] = 0;
      }
    }
  }


  // =====================================================================================
  public GameEngine( int n ) {
    // -----------------------------------------------------------------------------------
    order = n;
    board = new int[order][order];
    reset();
  }

  
  // =====================================================================================
  public int[] getState() {
    // -----------------------------------------------------------------------------------
    int[] state = new int[ order*order ];

    int idx = 0;

    for ( int r=0; r<order; r++ ) {
      for ( int c=0; c<order; c++ ) {
        state[idx] = board[r][c];
        idx += 1;
      }
    }

    return state;
  }


  // =====================================================================================
  public int get( int r, int c ) {
    // -----------------------------------------------------------------------------------
    return board[r][c];
  }


  // =====================================================================================
  public int set( int r, int c, int x ) {
    // -----------------------------------------------------------------------------------
    if ( 0 == board[r][c] ) {
      board[r][c] = x;
      return 0;
    }
    return 1;
  }


  // =====================================================================================
  protected boolean winner( int type ) {
    // -----------------------------------------------------------------------------------

    int sum = 0;
    
    // ----- check horizontal ------------------------------------
    for (int r=0; r<order; r++) {
       sum = 0;
      for (int c=0; c<order; c++) {
        if ( type == board[r][c] ) {
          sum += 1;
        }
        if ( order == sum ) {
          return true;
        }
      }
    }

    // ----- check vertical --------------------------------------
    for (int c=0; c<order; c++) {
       sum = 0;
      for (int r=0; r<order; r++) {
        if ( type == board[r][c] ) {
          sum += 1;
        }
        if ( order == sum ) {
          return true;
        }
      }
    }

    // ----- check right diagonal --------------------------------

     sum = 0;
    for (int i=0; i<order; i++) {
      if ( type == board[i][i] ) {
        sum += 1;
      }
    }
    if ( order == sum ) {
      return true;
    }

    // ----- check left diagonal ---------------------------------

     sum = 0;
    for (int i=0; i<order; i++) {
      if ( type == board[i][i-order+1] ) {
        sum += 1;
      }
    }
    if ( order == sum ) {
      return true;
    }

    return false;
   }
  
  
  // =====================================================================================
  public int checkWinner() {
    // -----------------------------------------------------------------------------------
    if ( winner(1) ) {
      return 1;
    }
    if ( winner(-1) ) {
      return -1;
    }
    return 0;
  }

} // end class GameEngine


// =======================================================================================
// **                                G A M E E N G I N E                                **
// ======================================================================== END FILE =====
