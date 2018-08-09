// ====================================================================== BEGIN FILE =====
// **                            J T E S T _ F U Z Z Y S E T                            **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2015, Stephen W. Soliday                                           **
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
 * @file jtest_function.java
 *
 * @author Stephen W. Soliday
 * @date 2018-08-09
 */
// =======================================================================================

package org.trncmp.test;

import  org.trncmp.lib.PSGraph;
import  org.trncmp.lib.PSDraw;

import  org.trncmp.mllib.fuzzy.Function;
import  org.trncmp.mllib.fuzzy.LeftTrapezoidFunction;
import  org.trncmp.mllib.fuzzy.TriangleFunction;
import  org.trncmp.mllib.fuzzy.RightTrapezoidFunction;

// =======================================================================================
// ---------------------------------------------------------------------------------------
public class jtest_fuzzyset {
  // -------------------------------------------------------------------------------------

  static final double MIN_ALT = -0.5;
  static final double MAX_ALT =  1.5;
  static final double MIN_X   =  1.0;
  static final double MAX_X   = 12.0;
  static final double DIF_X   =  0.01;
  
  static final double C1      =  3.0;
  static final double C2      =  5.0;
  static final double C3      =  9.0;
  

  // =====================================================================================
  public static void TestSet( PSDraw pd, Function S, double x0, double x1, double dx ) {
    // -----------------------------------------------------------------------------------
    double x  = x0;
    double lastX = x;
    double lastY = S.mu( x );
    while( x < x1 ) {
      double y = S.mu( x );
      pd.drawLine( x, y, lastX, lastY );
      lastX = x;
      lastY = y;
      x += dx;
    }
    pd.setRGB( 0.75, 0.75, 0.75 );
    pd.drawLine( S.getLeft(),   MIN_ALT, S.getLeft(),   MAX_ALT );
    pd.drawLine( S.getCenter(), MIN_ALT, S.getCenter(), MAX_ALT );
    pd.drawLine( S.getRight(),  MIN_ALT, S.getRight(),  MAX_ALT );
  }

  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------

    PSGraph ps = new PSGraph(1);
    PSDraw  pd = new PSDraw( 9.0, 6.5, MIN_X, MIN_ALT, MAX_X, MAX_ALT );

    pd.setRGB( 0.0, 1.0, 1.0 );
    pd.drawBorder();

    pd.setRGB( 0.0, 0.0, 0.0 );
    pd.drawLine( 1.0, 0.0, 11.0, 0.0 );

    Function L = new LeftTrapezoidFunction.Builder(C1).right(C2).build();
    Function T = new TriangleFunction.Builder(C2).left(C1).right(C3).build();
    Function R = new RightTrapezoidFunction.Builder(C3).left(C2).build();

    pd.setRGB( 1.0, 0.0, 0.0 );
    TestSet( pd, L, MIN_X, MAX_X, DIF_X );

    pd.setRGB( 0.0, 1.0, 0.0 );
    TestSet( pd, T, MIN_X, MAX_X, DIF_X );

    pd.setRGB( 0.0, 0.0, 1.0 );
    TestSet( pd, R, MIN_X, MAX_X, DIF_X );

    ps.add( pd, 0, 1.0, 1.0 );
    ps.pswrite( "triangle-set.ps" );

    ps.delete();

    System.exit(0);
  }
}

// =======================================================================================
// **                             J T E S T _ P S G R A P H                             **
// ======================================================================== END FILE =====
