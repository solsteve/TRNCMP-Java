// ====================================================================== BEGIN FILE =====
// **                        R A N D O M I N T E G E R C Y C L E                        **
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
 * @brief Provides random ordering with full cycle.
 *        Each cycle all integer elements are returned once. Each cycle the order is
 *        uniquly random.
 *
 *       cycle - order
 *         1   - 64831257
 *         2   - 71248635
 *         3   - 81754236
 *         :        :
 *         n   - 27145836
 *
 * @author Stephen W. Soliday
 * @date 2018-05-24
 */
// =======================================================================================

package org.trncmp.lib;

// =======================================================================================
public class RandomIntegerCycle {
  // -------------------------------------------------------------------------------------

  protected Dice dd = Dice.getInstance();

  protected int   num     = 0;
  protected int[] list    = null;
  protected int   current = 0;
  
  // =====================================================================================
  /** @brief Constructor.
   *  @param n number of array elements.
   *
   *  Construct an n element integer array an fill with the numbers 0,1,2,...,n-1.
   */
  // -------------------------------------------------------------------------------------
  public RandomIntegerCycle( int n ) {
    // -----------------------------------------------------------------------------------
    int[] temp = new int[n];
    for ( int i=0; i<n; i++ ) {
      temp[i] = i;
    }
    init( temp );
  }

  // =====================================================================================
  /** @brief Constructor.
   *  @param source reference to a list of integers.
   *
   *  make a copy of the provided integer list.
   */
  // -------------------------------------------------------------------------------------
  public RandomIntegerCycle( int[] source ) {
    // -----------------------------------------------------------------------------------
    init( source );
  }

  // =====================================================================================
  /** @brief initialize.
   *  @param source reference to a list of integers.
   *
   *  make a copy of the provided integer list.
   */
  // -------------------------------------------------------------------------------------
  protected void init( int[] source ) {
    // -----------------------------------------------------------------------------------
    num = source.length;
    list = new int[num];
    for ( int i=0; i<num; i++ ) {
      list[i] = source[i];
    }
    reset(num);
  }

  // =====================================================================================
  /** @brief Reset.
   *  @paran n number of passes.
   *
   *  Choose a new random permutation and set the current index to 0.
   */
  // -------------------------------------------------------------------------------------
  public void reset( int n ) {
    // -----------------------------------------------------------------------------------
    dd.scramble( list, num, n*num );
    current = 0;
  }

  // =====================================================================================
  /** @brief Reset.
   *  @paran n number of passes.
   *
   *  Choose a new random permutation and set the current index to 0.
   */
  // -------------------------------------------------------------------------------------
  public void reset( ) {
    // -----------------------------------------------------------------------------------
    reset( 3 );
  }

  // =====================================================================================
  /** @brief Next.
   *  @return the next random list element.
   *
   *  Choose a new random permutation and set the current index to 0.
   */
  // -------------------------------------------------------------------------------------
  public int next( ) {
    // -----------------------------------------------------------------------------------
    int rv = list[current];
    current += 1;
    if ( current == num ) {
      reset();
    }
    return rv;
  }

  public int size() { return num; }

} // end class RandomIntegerCycle
