// ====================================================================== BEGIN FILE =====
// **                             J T E S T _ S T A T M O D                             **
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
 * @brief   StatMod.
 * @file    jtest_statmod.java
 *
 * @details Provides the interface and procedures for testing the StatMod class.
 *
 * @author  Stephen W. Soliday
 * @date    2018-03-31
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.*;
import java.io.*;
import org.trncmp.lib.linear.*;

// =======================================================================================
public class jtest_statmod {
  // -------------------------------------------------------------------------------------

  public static final double MIN_X   = 3.0e0;
  public static final double MEAN_X  = 9.0e0;
  public static final double SIGMA_X = 2.0e0;
  public static final double MAX_X   = 1.5e1;

  public static final double MEAN_Y  = 7.0e0;
  public static final double MEAN_Z  = 1.5e1;
  
  public static final double SIGMA_A = 7.0e0;
  public static final double SIGMA_B = 3.0e0;
  public static final double SIGMA_C = 1.0e0;
  
  public static final double ANGLE_1 = 1.5e1;
  public static final double ANGLE_2 = 3.5e1;
  public static final double ANGLE_3 = 2.0e1;
  
  public static final double MIN_A = -SIGMA_A*6.15;
  public static final double MIN_B = -SIGMA_B*6.15;
  public static final double MIN_C = -SIGMA_C*6.15;
  public static final double MAX_A =  SIGMA_A*6.15;
  public static final double MAX_B =  SIGMA_B*6.15;
  public static final double MAX_C =  SIGMA_C*6.15;
  
  static Mat3 R = null;


  // =====================================================================================
  public static void originalCovariance( PrintStream ps, String fmt ) {
    // -----------------------------------------------------------------------------------
    Mat3 cv = new Mat3();
    Mat3 T  = new Mat3();
    Mat3 E  = new Mat3( R );
    Mat3 ET = new Mat3( R );
    Vec3 L  = new Vec3( SIGMA_A*SIGMA_A, SIGMA_B*SIGMA_B, SIGMA_C*SIGMA_C);
    Mat3 S  = linalg.diagonal( L );
    ET.transpose();

    linalg.dot( T, S, ET );
    linalg.dot( cv, E, T );

    ps.format( "Covariance =\n%s\n", array.toString( cv.A, fmt ) );
  }

  
  // =====================================================================================
  public static void expected1( PrintStream ps, String fmt ) {
    // -----------------------------------------------------------------------------------
    ps.format( "Expected Parameters\n" );
    ps.format( "  [ " );
    ps.format( fmt, MIN_X );
    ps.format( " <= N( " );
    ps.format( fmt, MEAN_X );
    ps.format( ", " );
    ps.format( fmt, SIGMA_X );
    ps.format( " ) <= " );
    ps.format( fmt, MAX_X );
    ps.format( " ]\n" );
  }
  
  // =====================================================================================
  public static void expected3( PrintStream ps, String fmt ) {
    // -----------------------------------------------------------------------------------
    ps.format( "Expected Parameters\n" );
    ps.format( "Min Value = " );
    ps.format( fmt, MIN_A ); ps.format( " " ); 
    ps.format( fmt, MIN_B ); ps.format( " " );
    ps.format( fmt, MIN_C );
    ps.format( "\n" );

    ps.format( "Max Value = " );
    ps.format( fmt, MAX_A ); ps.format( " " ); 
    ps.format( fmt, MAX_B ); ps.format( " " );
    ps.format( fmt, MAX_C );
    ps.format( "\n" );

    originalCovariance( ps, fmt );
    ps.format( "\n" );
    
  }
  
  // =====================================================================================
  public static void generated1( PrintStream ps, String fmt,
                                 double mn, double mu, double sig, double mx) {
    // -----------------------------------------------------------------------------------
    ps.format( "Generated Parameters\n" );
    ps.format( "  [ " );
    ps.format( fmt, mn );
    ps.format( " <= N( " );
    ps.format( fmt, mu );
    ps.format( ", " );
    ps.format( fmt, sig );
    ps.format( " ) <= " );
    ps.format( fmt, mx );
    ps.format( " ]\n" );
  }
  
  // =====================================================================================
  public static double[] GenTable1Var( int n_sam ) {
    // -----------------------------------------------------------------------------------

    Dice dd = Dice.getInstance();

    double[] table = new double[n_sam];

    double x = 0.0e0;
    for ( int i=0; i<n_sam; i++ ) {
      do {
        x = MEAN_X + SIGMA_X*dd.normal();
      } while( ( x < MIN_X ) ||
              ( x > MAX_X ) );
      table[i] = x;
    }

    return table;
  }


  // =====================================================================================
  public static double[][] GenTable3Var( int n_sam ) {
    // -----------------------------------------------------------------------------------

    Dice dd = Dice.getInstance();
    
    Mat3 R1 = linalg.rot1( Math2.DEG2RAD * ANGLE_1 );
    Mat3 R2 = linalg.rot2( Math2.DEG2RAD * ANGLE_2 );
    Mat3 R3 = linalg.rot3( Math2.DEG2RAD * ANGLE_3 );
    Mat3 T  = new Mat3();
    R = new Mat3();

    linalg.dot( T, R2, R3 );
    linalg.dot( R, R1, T );
    
    Vec3 v1 = new Vec3();
    Vec3 v2 = new Vec3();
    
    double[][] table = new double[n_sam][3];

    for ( int i=0; i<n_sam; i++ ) {
      v1.x = SIGMA_A*dd.normal();
      v1.y = SIGMA_B*dd.normal();
      v1.z = SIGMA_C*dd.normal();
      linalg.dot( v2, R, v1 );
      table[i][0] = MEAN_X + v2.x;
      table[i][1] = MEAN_Y + v2.y;
      table[i][2] = MEAN_Z + v2.z;
    }

    return table;
  }

  // =====================================================================================
  public static void test01( double[] data ) {
    // -----------------------------------------------------------------------------------
    System.out.format( "\n----- single variable ------------------------\n\n" );
    StatMod.single mod1 = new StatMod.single( data );
    expected1( System.out, "%g" );
    mod1.display( System.out, "%g" );

    int n = data.length;

    try {
      PrintStream ps = FileTools.openWrite( "/tmp/single.mod" );
      mod1.write( ps );
      ps.close();

      BufferedReader br = FileTools.openRead( "/tmp/single.mod" );
      StatMod.single mod2 = new StatMod.single( br );
      br.close();
    

      double[] gen = new double[n];

      for ( int i=0; i<n; i++ ) {
        gen[i] = mod2.next();
      }

      Statistics.single stat = new Statistics.single();

      stat.compile( gen, n );

      generated1( System.out, "%g",
                  stat.minv,
                  stat.mean,
                  Math.sqrt( stat.var ),
                  stat.maxv
                  );
      System.out.format( "\n" );
    } catch( IOException e ) {
      System.err.println( e.toString() );
    }
  }
  
  // =====================================================================================
  public static void test02( double[][] data ) {
    // -----------------------------------------------------------------------------------
    int n = data.length;

    System.out.format( "\n----- multi variable -------------------------\n\n" );
    expected3( System.out, "%8.4f" );
    StatMod.multi mod1 = new StatMod.multi( data );
    mod1.display( System.out, "%8.4f" );

    System.out.format( "\nOriginal Affine =\n%s\n", array.toString( R.A, "%8.4f" ) );

    Statistics.multi stat = new Statistics.multi(3);
    stat.compile( data, n, 3 );
    stat.extra( data, n, 3 );
    stat.correlate( data, n, 3 );

    System.out.format( "\n----- Original Data -------------------------\n\n" );
    stat.report( System.out, "%8.4f" );

    try {
      PrintStream ps = FileTools.openWrite( "/tmp/multi.mod" );
      mod1.write( ps );
      ps.close();

      BufferedReader br = FileTools.openRead( "/tmp/multi.mod" );
      StatMod.multi mod2 = new StatMod.multi( br );
      br.close();

    double[][] gen = new double[n][3];

    Vector work = new Vector( 3 );
    for ( int i=0; i<n; i++ ) {
      mod2.next( work );
      gen[i][0] = work.x[0];
      gen[i][1] = work.x[1];
      gen[i][2] = work.x[2];
    }

    Statistics.multi sgen = new Statistics.multi(3);
    sgen.compile( gen, n, 3 );
    sgen.extra( gen, n, 3 );
    sgen.correlate( gen, n, 3 );

    System.out.format( "\n----- Generated Data --------------------------\n\n" );
    sgen.report( System.out, "%8.4f" );
    System.out.format( "\n" );

    System.out.format( "\n===== Model from Data =========================\n" );
    mod1.display( System.out, "%8.4f" );
    System.out.format( "\n----- Model from File -------------------------\n" );
    mod2.display( System.out, "%8.4f" );
    System.out.format( "\n===============================================\n\n" );

      
    } catch( IOException e ) {
      System.err.println( e.toString() );
    }

  }
  
  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
    int  n  = 0;
    Dice dd = Dice.getInstance();

    dd.seed_set();

    if ( 1 != args.length ) {
      System.err.format("\nUSAGE: JTest statmod n\n");
      System.err.format("   n - integer - number of sample points\n\n");
      System.err.format("Example: JTest statmod 1000\n\n");
      System.exit(1);
    }

    try{
      n = Integer.parseInt( args[0] );
    } catch( java.lang.NumberFormatException e ) {
      System.err.format("\n%s\n", e.toString() );
      System.err.format("Number of samples must be an integer\n\n");
      System.exit(2);
    }

    double[]   single = GenTable1Var( n );
    double[][] multi3 = GenTable3Var( n );

    test01( single );
    test02( multi3 );

    System.exit(0);
  }

}

// =======================================================================================
// **                             J T E S T _ S T A T M O D                             **
// ======================================================================== END FILE =====
