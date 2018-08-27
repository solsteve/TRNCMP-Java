// ====================================================================== BEGIN FILE =====
// **                            J T E S T _ F U Z Z Y S E T                            **
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
 * @file jtest_function.java
 *
 * @author Stephen W. Soliday
 * @date 2018-08-09
 */
// =======================================================================================

package org.trncmp.test;

import  java.io.BufferedReader;
import  java.io.PrintStream;

import  org.trncmp.lib.PSGraph;
import  org.trncmp.lib.PSDraw;
import  org.trncmp.lib.Dice;
import  org.trncmp.lib.FileTools;

import  org.trncmp.mllib.fuzzy.Partition;
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
    if ( null != pd ) {
      if ( null != S ) {
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
      } else {
        System.err.println("jtest_fuzzyset.TestSet: fuzzy.Function is NULL");
      }
    } else {
      System.err.println("jtest_fuzzyset.TestSet: PSDraw is NULL");
    }
  }

  // =====================================================================================
  public static void TestFunction() {
    // -----------------------------------------------------------------------------------

    PSGraph ps = new PSGraph(1);
    PSDraw  pd = new PSDraw( 9.0, 6.5, MIN_X, MIN_ALT, MAX_X, MAX_ALT );

    pd.setRGB( 0.0, 1.0, 1.0 );
    pd.drawBorder();

    pd.setRGB( 0.0, 0.0, 0.0 );
    pd.drawLine( 1.0, 0.0, 11.0, 0.0 );

    Function L = new LeftTrapezoidFunction(C1,C2);
    Function T = new TriangleFunction(C1,C2,C3);
    Function R = new RightTrapezoidFunction(C2,C3);

    pd.setRGB( 1.0, 0.0, 0.0 );
    TestSet( pd, L, MIN_X, MAX_X, DIF_X );

    pd.setRGB( 0.0, 1.0, 0.0 );
    TestSet( pd, T, MIN_X, MAX_X, DIF_X );

    pd.setRGB( 0.0, 0.0, 1.0 );
    TestSet( pd, R, MIN_X, MAX_X, DIF_X );

    ps.add( pd, 0, 1.0, 1.0 );
    ps.pswrite( "triangle-func.ps" );

    ps.delete();
  }

  
  // =====================================================================================
  public static void TestPartition() {
    // -----------------------------------------------------------------------------------

    PSGraph ps = new PSGraph(1);
    PSDraw  pd = new PSDraw( 9.0, 6.5, 0.0, MIN_ALT, 10.0, MAX_ALT );

    pd.setRGB( 0.0, 1.0, 1.0 );
    pd.drawBorder();

    double[] ctrs = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0 };

    pd.setRGB( 0.75, 0.75, 1.0 );
    for ( int i=0; i<ctrs.length; i++ ) {
      pd.drawLine( ctrs[i], MIN_ALT, ctrs[i], MAX_ALT );
    }

    Partition par = new Partition();
    par.set( ctrs, 2, 5 );

    int n = par.size();
    for ( int i=0; i<n; i++ ) {
      TestSet( pd, par.function(i), MIN_X, MAX_X, DIF_X );
    }
    
    ps.add( pd, 0, 1.0, 1.0 );
    ps.pswrite( "triangle-part.ps" );

    ps.delete();

    double area_sum1 = 0.0e0;
    double prod_sum1 = 0.0e0;
    double area_sum2 = 0.0e0;
    double prod_sum2 = 0.0e0;
        
    for ( int i=0; i<n; i++ ) {
      Function F = par.function(i);
      double a1  = F.area(0.5);
      double ax1 = F.coa(0.5) * a1;
      double a2  = par.area(i,0.5);
      double ax2 = par.coa(i,0.5) * a2;
      area_sum1 += a1;
      prod_sum1 += ax1;
      area_sum2 += a2;
      prod_sum2 += ax2;
    }

    double cm1 = prod_sum1 / area_sum1;
    double cm2 = prod_sum2 / area_sum2;

    double[] test = { 0.5, 0.5, 0.5, 0.5, 0.5 };

    System.out.println( area_sum1+" == "+area_sum2+" == "+par.area(test,0) );
    System.out.println( cm1+" == "+cm2+" == "+par.coa(test,0) );
  }

  static double rnd_round( Dice dd ) {
    return 10 + Math.floor(dd.uniform() * 1000.0 + 0.5) / 100.0;
  }
  
  
  // =====================================================================================
  public static void TestPartition2() {
    // -----------------------------------------------------------------------------------
    Dice dd = Dice.getInstance();
    dd.seed_set();

    int np = 3 + dd.index(5);

    double[] x = new double[ np ];

    x[0] = 20.0 + rnd_round(dd);
    for ( int i=1; i<np; i++ ) {
      x[i] = x[i-1] + rnd_round(dd);
    }

    // -----------------------------------------------------------------------------------
    Partition par1 = new Partition();
    par1.set( x );
 
    PrintStream ps = FileTools.openWrite( "fuzzy_part_init.dat" );
    par1.write( ps );
    ps.close();

    Partition par2 = new Partition();
    BufferedReader br = FileTools.openRead( "fuzzy_part_init.dat" );
    par2.read( br );
    ps.close();

     ps = FileTools.openWrite( "fuzzy_part_save.dat" );
    par2.write( ps );
    ps.close();









    
    
  }

  // =====================================================================================
  public static void TestRecall() {
    // -----------------------------------------------------------------------------------

    PSGraph ps = new PSGraph(1);
    PSDraw  pd = new PSDraw( 6.5, 6.5, -10.0, -10.0, 110.0, 110 );

    pd.setRGB( 0.5, 0.5, 0.5 );
    pd.drawBorder();

    pd.setRGB(   0.0, 1.0, 1.0 );
    pd.drawLine( 0.0, 0.0, 0.0, 100.0 );
    pd.drawLine( 0.0, 0.0, 100.0, 0.0 );

    pd.setRGB(   1.0, 0.0, 0.0 );
    pd.drawLine( 0.0, 0.0, 100.0, 100.0 );

   
    pd.setRGB( 0.0, 0.0, 0.0 );
    for ( int i=10; i<16; i++ ) {
      double[] wgts = new double[i];
      Partition par = new Partition();
      par.set( i, 0.0, 100.0 );
      double dx = 0.01;
      double  x = dx;
      double mx = 100.0 + (dx/2.0);
      double last_x = 0.0;
      par.mu( wgts, 0, last_x );
      double last_y = par.coa( wgts, 0 );

      while( x < mx ) {
        par.mu( wgts, 0, x );
        double y = par.coa( wgts, 0 );
        pd.drawLine( last_x, last_y, x, y );
        last_x = x;
        last_y = y;
        x += dx;
      }
    }

    ps.add( pd, 0, 2.0, 1.0 );
    ps.pswrite( "recall.ps" );

    ps.delete();
  }
  
  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------

    //TestFunction();
    TestPartition();
    TestPartition2();
    TestRecall();
    
    System.exit(0);
  }
}

// =======================================================================================
// **                             J T E S T _ F U Z Z Y S E T                           **
// ======================================================================== END FILE =====
