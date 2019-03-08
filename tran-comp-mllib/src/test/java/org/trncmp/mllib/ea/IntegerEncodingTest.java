// ====================================================================== BEGIN FILE =====
// **                       I N T E G E R E N C O D I N G T E S T                       **
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
 * @file IntegerEncodingTest.java
 * <p>
 * Provides unit testing for the org.trncmp.mllib.ea.IntegerEncoding class.
 *
 * @date 2018-07-18
 */
// =======================================================================================

package org.trncmp.mllib.ea;

import        org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.trncmp.lib.RunStat;

// =======================================================================================
public class IntegerEncodingTest {
  // -------------------------------------------------------------------------------------

  static final double TOL = 1.0e-10;

  static final int SAMPLES = 100000;
  static final int DIM     = 4;
  
  // =====================================================================================
  @Test
  public void testSetGet() {
    // -----------------------------------------------------------------------------------
    final int a   = 1;
    final int b   = 3;
    final int c   = 5;
      
    IntegerEncoding m = new IntegerEncoding(3);
    assertEquals( 3, m.size() );
    m.set( 0, a );
    m.set( 1, b );
    m.set( 2, c );
    assertEquals( a, m.get(0) );
    assertEquals( b, m.get(1) );
    assertEquals( c, m.get(2) );

    m.zero();
    assertEquals( 0, m.get(0) );
    assertEquals( 0, m.get(1) );
    assertEquals( 0, m.get(2) );
  }


  // =====================================================================================
  @Test
  public void testZero() {
    // -----------------------------------------------------------------------------------
    final int[] minv = { -12, -6,  3 };
    final int[] zero = {  -2,  0,  3 };
    final int[] maxv = {  -2,  5, 13 };
    
    final int[] a = {  -4,  3,  5 };
    final int[] b = {  -8, -4, 11 };
    final int[] c = {  -6,  1,  9 };
    final int[] d = { -10, -2,  7 };

    for ( int i=0; i<3; i++ ) {
      IntegerEncoding m1 = new IntegerEncoding(4);
      m1.setMin( minv[i] );
      m1.setMax( maxv[i] );
      assertEquals( 4, m1.size() );
      m1.set( 0, a[i] );
      m1.set( 1, b[i] );
      m1.set( 2, c[i] );
      m1.set( 3, d[i] );
      assertEquals( a[i], m1.get(0) );
      assertEquals( b[i], m1.get(1) );
      assertEquals( c[i], m1.get(2) );
      assertEquals( d[i], m1.get(3) );
      m1.zero();
      assertEquals( zero[i], m1.get(0) );
      assertEquals( zero[i], m1.get(1) );
      assertEquals( zero[i], m1.get(2) );
      assertEquals( zero[i], m1.get(3) );
    }

  }

  
   // =====================================================================================
  @Test
  public void testCopy() {
    // -----------------------------------------------------------------------------------
    final int a   = 1;
    final int b   = 3;
    final int c   = 5;
    final int d   = 7;
      
    IntegerEncoding m1 = new IntegerEncoding(4);
    assertEquals( 4, m1.size() );    
    m1.set( 0, a );
    m1.set( 1, b );
    m1.set( 2, c );
    m1.set( 3, d );
    assertEquals( a, m1.get(0) );
    assertEquals( b, m1.get(1) );
    assertEquals( c, m1.get(2) );
    assertEquals( d, m1.get(3) );

    IntegerEncoding m2 = new IntegerEncoding(4);
    assertEquals( 4, m2.size() );
    m2.set( 0, 5 );
    m2.set( 1, 7 );
    m2.set( 2, 3 );
    m2.set( 3, 8 );
    assertEquals( 5, m2.get(0) );
    assertEquals( 7, m2.get(1) );
    assertEquals( 3, m2.get(2) );
    assertEquals( 8, m2.get(3) );

    m2.copy( m1 );
    assertEquals( 4, m2.size() );
    assertEquals( m1.get(0), m2.get(0) );
    assertEquals( m1.get(1), m2.get(1) );
    assertEquals( m1.get(2), m2.get(2) );
    assertEquals( m1.get(3), m2.get(3) );  
  }

  
 // =====================================================================================
  @Test
  public void testStringFormat() {
    // -----------------------------------------------------------------------------------
    final int a   =  1;
    final int b   = -3;
    final int c   =  500;
    final String fmt = "%5d";
    final String dlm = "|";

    final String test1 = "1 -3 500";
    final String test2 = "    1|   -3|  500";

    IntegerEncoding m = new IntegerEncoding(3);
    assertEquals( 3, m.size() );
    m.set( 0, a );
    m.set( 1, b );
    m.set( 2, c );

    assertEquals( test1, m.toString() );
    assertEquals( test2, m.format( fmt, dlm ) );
  }

  // =====================================================================================
  @Test
  public void testRandomize() {
    // -----------------------------------------------------------------------------------
    int minv = 100;
    int maxv = 500;
    int mean = 300;
    
    IntegerEncoding E = new IntegerEncoding(DIM);
    E.setMin( minv );
    E.setMax( maxv );
    assertEquals( DIM, E.size() );

    RunStat[] S = new RunStat[DIM];
    for ( int j=0; j<DIM; j++ ) {
      S[j] = new RunStat();
    }
    
    for ( int i=0; i<SAMPLES; i++ ) {
      E.randomize();
      for ( int j=0; j<DIM; j++ ) {
        S[j].update( E.get(j) );
      }
    }

    //for ( int j=0; j<DIM; j++ ) {
    //System.out.format( "%g %g %g\n", S[j].minv(), S[j].mean(), S[j].maxv() );
    //}

    for ( int j=0; j<DIM; j++ ) {
      assertEquals( mean, S[j].mean(), 15 );
      assertTrue( minv+5 > S[j].minv() );
      assertTrue(  maxv-5 < S[j].maxv() );
    }
  }
 
  // =====================================================================================
  @Test
  public void testBracket() {
    // -----------------------------------------------------------------------------------
    int minv = 100;
    int maxv = 500;
    IntegerEncoding E = new IntegerEncoding(DIM);
    E.setMin( minv );
    E.setMax( maxv );
    assertEquals( DIM, E.size() );

    int count_lower = 0;
    int count_upper = 0;
    for ( int i=0; i<SAMPLES; i++ ) {
      E.bracket();
      for ( int j=0; j<DIM; j++ ) {
        if ( minv == E.get(j) ) { count_lower += 1; }
        if ( maxv == E.get(j) ) { count_upper += 1; }
      }
    }

    double ratio = (double) count_upper / (double) count_lower;

    int MID_POINT = (SAMPLES * DIM) / 2;
    int BUMP      =  MID_POINT / 20;       // +/- 5 percent
    int LOW       =  MID_POINT-BUMP;
    int HIGH      =  MID_POINT+BUMP;

    //System.out.format( "LOW:         %d\n", LOW );
    //System.out.format( "count_lower: %d\n", count_lower );
    //System.out.format( "MID POINT:   %d\n", MID_POINT );
    //System.out.format( "count_upper: %d\n", count_upper );
    //System.out.format( "HIGH:        %d\n", HIGH );
    
    assertTrue( LOW  < count_lower );
    assertTrue( LOW  < count_upper );
    assertTrue( HIGH > count_lower );
    assertTrue( HIGH > count_upper );
    assertEquals( 1.0e0, ratio, 0.2 ); // // +/- 5 percent

  }

  // =====================================================================================
  @Test
  public void testNoise() {
    // -----------------------------------------------------------------------------------
    final double SIGMA = 0.12;
    final double SCALE = Encoding.N_SIGMA_SCALE * SIGMA * 5.0e-1;
    
    IntegerEncoding E = new IntegerEncoding(DIM);

    int MIN_VAL = 5;
    int MAX_VAL = 25;
    int AVG_VAL = (MIN_VAL+MAX_VAL)/2;

    E.setMin( MIN_VAL );
    E.setMax( MAX_VAL );
    
    assertEquals( DIM, E.size() );

    RunStat[] S = new RunStat[DIM];
    for ( int j=0; j<DIM; j++ ) {
      S[j] = new RunStat();
      E.set( j, 10 );
    }
    
    for ( int i=0; i<SAMPLES; i++ ) {
      E.noise( SCALE );
      for ( int j=0; j<DIM; j++ ) {
        S[j].update( E.get(j) );
      }
    }

    //for ( int j=0; j<DIM; j++ ) {
      //System.err.println( "mean  ("+AVG_VAL+")"+S[j].mean() );
      //System.err.println( "sigma ("+SIGMA+")"+S[j].sigma() );
      //System.err.println( "min   ("+MIN_VAL+")"+S[j].minv() );
      //System.err.println( "max   ("+MAX_VAL+")"+S[j].maxv() );

      //assertEquals( (double)AVG_VAL , S[j].mean(),  0.5 );
      //assertEquals( SIGMA,            S[j].sigma(), 0.5 );

      
      //assertTrue( -(SIGMA*3.0) > S[j].minv() );
      //assertTrue(  (SIGMA*3.0) < S[j].maxv() );
    //}

  }
 
  // =====================================================================================
  @Test
  public void testParam() {
    // -----------------------------------------------------------------------------------

    int[] p1 = {  5, 11,  9, 16 };
    int[] p2 = { 25, 15, 29, 24 };
    int[] c1 = { 10, 12, 14, 18 };
    int[] c2 = { 20, 14, 24, 22 };
   
    double   t  = 0.25;

    int[] xc1 = new int[ p1.length ];
    int[] xc2 = new int[ p1.length ];

    IntegerEncoding.parametric( xc1, xc2, p1, p2, t );

    for ( int i=0; i<p1.length; i++ ) {
      assertEquals( c1[i], xc1[i], TOL );
      assertEquals( c2[i], xc2[i], TOL );
    }

  }


  // =====================================================================================
  boolean isNotZero( int x ) {
    // -----------------------------------------------------------------------------------
    if ( 0.0 > x ) { return true; }
    if ( 0.0 < x ) { return true; }
    return false;
  }

  // =====================================================================================
  @Test
  public void testMutate() {
    // -----------------------------------------------------------------------------------

    final int    NPAR  = 1500;
    final double SIGMA = 0.01;
    final double SCALE = Encoding.N_SIGMA_SCALE * SIGMA * 5.0e-1;
    final double PERC  = 0.35;

    IntegerEncoding C = new IntegerEncoding( NPAR );
    IntegerEncoding P = new IntegerEncoding( NPAR );

    P.zero();
    
    C.mutate( P, PERC, SCALE );

    RunStat stat = new RunStat();

    int count = 0;
    for ( int i=0; i<NPAR; i++ ) {
      int x =P.get(i);
      if ( isNotZero(x) ) {
        count++;
        stat.update( x );
      }
    }

    double ratio = (double) count / (double) NPAR;

    for ( int i=0; i<NPAR; i++ ) {
      assertEquals( 0.0e0, stat.mean(),  0.05 );
      assertEquals( SIGMA, stat.sigma(), 0.05 );
    }

  }

  
} // end class IntegerEncodingTest



// =======================================================================================
// **                       I N T E G E R E N C O D I N G T E S T                       **
// ======================================================================== END FILE =====
