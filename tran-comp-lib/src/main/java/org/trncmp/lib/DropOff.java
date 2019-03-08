// ====================================================================== BEGIN FILE =====
// **                                   D R O P O F F                                   **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2017, Stephen W. Soliday                                           **
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
 * @brief   Drop Off.
 * @file    DropOff.java
 *
 * @details Provides the interface and procedures for.
 *
 * @author  Stephen W. Soliday
 * @date    2017-09-30
 */
// =======================================================================================


package org.trncmp.lib;


// =======================================================================================
public class DropOff {
  // -------------------------------------------------------------------------------------

  public int    index = 0;    //< index
  public double A     = 1.0;  //< first value
  public double B     = 1.0;  //< last value
  public char   model = 'L';  //< model L=linear, G=Gaussian, E=Exponetial


  // =====================================================================================
  /** @brief Constructor.
   *  @param Vo    initial value.
   *  @param Vf    final value.
   *  @param n     number of values to produce.
   *  @param model type of model to use. L=linear, G=Gaussian, E=Exponetial
   */
  // -------------------------------------------------------------------------------------
  public DropOff( double Vo, double Vf, int n, char mod ) {
    // -----------------------------------------------------------------------------------

    switch( mod ) {
      // ---------------------------------------------------------------------------------
      default:
        System.err.format( "DropOff: unknown model [%c] using Linear\n", model );
    
        // --------------------- Linear Model --------------------------------------------
      case 'l': case 'L':
        model = 'L';

        this.A = (Vf - Vo) / (double)(n-1);
        this.B =  Vo;
    
        break;

        // --------------------- Gaussian Model ------------------------------------------
      case 'g': case 'G':
        model = 'G';
    
        this.A = Vo;
        this.B = Math.log( Vf / Vo ) / (double)(n-1);

        break;

        // --------------------- Exponential Model ---------------------------------------
      case 'e': case 'E':
        model = 'E';

        this.A = Vo;
        this.B = Math.log( Vf / Vo ) / (double)((n-1)*(n-1));

        break;
    }
  }


  // =====================================================================================
  /** @brief Reset.
   */
  // -------------------------------------------------------------------------------------
  public void reset( ) {
    // -----------------------------------------------------------------------------------
    index = 0;
  }


  // =====================================================================================
  /** @brief Get.
   */
  // -------------------------------------------------------------------------------------
  public double get( ) {
    // -----------------------------------------------------------------------------------
    double x = 0.0e0;

    switch( model ) {
      // --------------------- Linear Model ----------------------------------------------
      case('L'):

        x = B + A * (double)index;

        break;
    
        // --------------------- Exponential Model ---------------------------------------
      case('E'):

        x = A * Math.exp( B * (double)index );

        break;
    
        // --------------------- Gaussian Model ------------------------------------------
      case('G'):

        x = A * Math.exp( B * (double)index * (double)index );

        break;

        // -------------------------------------------------------------------------------
      default:
        System.err.format( "DropOff: model was set to an invalid [%c] model.\n", model );
        break;
    }

    return x; 
  }


  // =====================================================================================
  /** @brief Get.
   *  @param n nth value.
   */
  // -------------------------------------------------------------------------------------
  public double get( int n ) {
    // -----------------------------------------------------------------------------------

    index = n;

    return get();
  }


}

// =======================================================================================
// **                                   D R O P O F F                                   **
// ======================================================================== END FILE =====
