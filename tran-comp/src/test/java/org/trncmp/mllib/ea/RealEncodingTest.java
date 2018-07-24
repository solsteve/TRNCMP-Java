// ====================================================================== BEGIN FILE =====
// **                          R E A L E N C O D I N G T E S T                          **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2018, L3 Technologies Advanced Programs                            **
// **                      One Wall Street #1, Burlington, MA 01803                     **
// **                                                                                   **
// **  -------------------------------------------------------------------------------  **
// **                                                                                   **
// **  This file, and associated source code, is not free software; you may not         **
// **  redistribute it and/or modify it. This file is part of a research project        **
// **  that is in a development phase. No part of this research has been publicly       **
// **  distributed. Research and development for this project has been at the sole      **
// **  cost in both time and funding by L3 Technologies Advanced Programs.              **
// **                                                                                   **
// **  Any reproduction of computer software or portions thereof marked with this       **
// **  legend must also reproduce the markings.  Any person who has been provided       **
// **  access to such software must promptly notify L3 Technologies Advanced Programs.  **
// **                                                                                   **
// ----- Modification History ------------------------------------------------------------
/**
 * @file RealEncodingTest.java
 * <p>
 * Provides unit testing for the org.trncmp.mllib.ea.RealEncoding class.
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
public class RealEncodingTest {
  // -------------------------------------------------------------------------------------

  static final double TOL = 1.0e-12;

  static final int SAMPLES = 10000;
  static final int DIM     = 4;
  
  // =====================================================================================
  @Test
  public void testSetGet() {
    // -----------------------------------------------------------------------------------
    final double a   = 1.2;
    final double b   = 3.4;
    final double c   = 5.6;
      
    RealEncoding m = new RealEncoding(3);
    assertEquals( 3, m.size() );
    m.set( 0, a );
    m.set( 1, b );
    m.set( 2, c );
    assertEquals( a, m.get(0), TOL);
    assertEquals( b, m.get(1), TOL );
    assertEquals( c, m.get(2), TOL );

    m.zero();
    assertEquals( 0.0e0, m.get(0), TOL);
    assertEquals( 0.0e0, m.get(1), TOL );
    assertEquals( 0.0e0, m.get(2), TOL );
  }


  // =====================================================================================
  @Test
  public void testZeroCopy() {
    // -----------------------------------------------------------------------------------
    final double a   = 1.2;
    final double b   = 3.4;
    final double c   = 5.6;
    final double d   = 7.8;
      
    RealEncoding m1 = new RealEncoding(4);
    m1.zero();
    assertEquals( 4, m1.size() );
    assertEquals( 0.0e0, m1.get(0), TOL);
    assertEquals( 0.0e0, m1.get(1), TOL );
    assertEquals( 0.0e0, m1.get(2), TOL );
    assertEquals( 0.0e0, m1.get(3), TOL );

    m1.set( 0, a );
    m1.set( 1, b );
    m1.set( 2, c );
    m1.set( 3, d );
    assertEquals( a, m1.get(0), TOL);
    assertEquals( b, m1.get(1), TOL );
    assertEquals( c, m1.get(2), TOL );
    assertEquals( d, m1.get(3), TOL );

    RealEncoding m2 = new RealEncoding(4);
    m2.zero();
    assertEquals( 4, m2.size() );
    assertEquals( 0.0e0, m2.get(0), TOL);
    assertEquals( 0.0e0, m2.get(1), TOL );
    assertEquals( 0.0e0, m2.get(2), TOL);
    assertEquals( 0.0e0, m2.get(3), TOL );

    m2.copy( m1 );
    assertEquals( 4, m2.size() );
    assertEquals( m1.get(0), m2.get(0), TOL);
    assertEquals( m1.get(1), m2.get(1), TOL);
    assertEquals( m1.get(2), m2.get(2), TOL);
    assertEquals( m1.get(3), m2.get(3), TOL);  
  }

  
  // =====================================================================================
  @Test
  public void testStringFormat() {
    // -----------------------------------------------------------------------------------
    final double a   =  1.2e-2;
    final double b   = -3.4e1;
    final double c   =  5.6e-1;
    final String fmt = "%6.3f";
    final String dlm = "|";

    final String test1 = " 1.2000e-02 -3.4000e+01  5.6000e-01";
    final String test2 = " 0.012|-34.000| 0.560";

    RealEncoding m = new RealEncoding(3);
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
    RealEncoding E = new RealEncoding(DIM);
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

    for ( int j=0; j<DIM; j++ ) {
      assertEquals( 0.0e0, S[j].mean(), 0.05 );
      assertTrue( -0.95 > S[j].minv() );
      assertTrue(  0.95 < S[j].maxv() );
    }
  }
 
  // =====================================================================================
  @Test
  public void testBracket() {
    // -----------------------------------------------------------------------------------
    RealEncoding E = new RealEncoding(DIM);
    assertEquals( DIM, E.size() );

    int count_lower = 0;
    int count_upper = 0;
    for ( int i=0; i<SAMPLES; i++ ) {
      E.bracket();
      for ( int j=0; j<DIM; j++ ) {
        if ( -0.99 > E.get(j) ) { count_lower += 1; }
        if (  0.99 < E.get(j) ) { count_upper += 1; }
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
    assertEquals( 1.0e0, ratio, 0.05 ); // // +/- 5 percent

  }

  // =====================================================================================
  @Test
  public void testNoise() {
    // -----------------------------------------------------------------------------------
    final double SIGMA = 0.01;
    final double SCALE = Encoding.N_SIGMA_SCALE * SIGMA * 5.0e-1;
    
    RealEncoding E = new RealEncoding(DIM);
    assertEquals( DIM, E.size() );

    RunStat[] S = new RunStat[DIM];
    for ( int j=0; j<DIM; j++ ) {
      S[j] = new RunStat();
    }
    
    for ( int i=0; i<SAMPLES; i++ ) {
      E.zero();
      E.noise( SCALE );
      for ( int j=0; j<DIM; j++ ) {
        S[j].update( E.get(j) );
      }
    }

    for ( int j=0; j<DIM; j++ ) {
      assertEquals( 0.0e0, S[j].mean(),  0.05 );
      assertEquals( SIGMA, S[j].sigma(), 0.05 );
      assertTrue( -(SIGMA*3.0) > S[j].minv() );
      assertTrue(  (SIGMA*3.0) < S[j].maxv() );
    }
  }
 
  // =====================================================================================
  @Test
  public void testParam() {
    // -----------------------------------------------------------------------------------

    double[] p1 = { 0.100, -0.800,  0.900, 0.700 };
    double[] p2 = { 0.300,  0.100, -0.500, 0.400 };
    double[] c1 = { 0.150, -0.575,  0.550, 0.625 };
    double[] c2 = { 0.250, -0.125, -0.150, 0.475 };
    
    double   t  = 0.250;

    double[] xc1 = new double[ p1.length ];
    double[] xc2 = new double[ p1.length ];

    RealEncoding.parametric( xc1, xc2, p1, p2, t );

    for ( int i=0; i<p1.length; i++ ) {
      assertEquals( c1[i], xc1[i], TOL );
      assertEquals( c2[i], xc2[i], TOL );
    }

  }


  // =====================================================================================
  boolean isNotZero( double x ) {
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

    RealEncoding C = new RealEncoding( NPAR );
    RealEncoding P = new RealEncoding( NPAR );

    P.zero();
    
    C.mutate( P, PERC, SCALE );

    RunStat stat = new RunStat();

    int count = 0;
    for ( int i=0; i<NPAR; i++ ) {
      double x =P.get(i);
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

  
} // end class RealEncodingTest



// =======================================================================================
// **                          R E A L E N C O D I N G T E S T                          **
// ======================================================================== END FILE =====
