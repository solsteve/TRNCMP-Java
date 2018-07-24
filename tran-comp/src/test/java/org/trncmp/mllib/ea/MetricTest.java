// ====================================================================== BEGIN FILE =====
// **                                M E T R I C T E S T                                **
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
 * @file MetricTest.java
 * <p>
 * Provides unit testing for the org.trncmp.mllib.ea.Metric class.
 *
 * @date 2018-07-10
 */
// =======================================================================================

package org.trncmp.mllib.ea;

import        org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


// =======================================================================================
public class MetricTest {
  // -------------------------------------------------------------------------------------

  static final double tol = 1.0e-12;
  
  // =====================================================================================
  @Test
  public void testConstruct() {
    // -----------------------------------------------------------------------------------
    Metric m1 = new Metric();
    Metric m2 = new Metric(3);
    Metric m3 = new Metric(m2);
    assertEquals( 0, m1.size() );
    assertEquals( 3, m2.size() );
    assertEquals( 3, m3.size() );
  }

  
  // =====================================================================================
  @Test
  public void testResize() {
    // -----------------------------------------------------------------------------------
    Metric m = new Metric(5);
    assertEquals( 5, m.size() );

    // same size
    m.resize(5);
    assertEquals( 5, m.size() );
      
    // smaller
    m.resize(2);
    assertEquals( 2, m.size() );

    // bigger
    m.resize(8);
    assertEquals( 8, m.size() );
  }

  
  // =====================================================================================
  @Test
  public void testSetGet() {
    // -----------------------------------------------------------------------------------
    final double a   = 1.2;
    final double b   = 3.4;
    final double c   = 5.6;
      
    Metric m = new Metric(3);
    assertEquals( 0.0e0, m.get(0), tol);
    assertEquals( 0.0e0, m.get(1), tol );
    assertEquals( 0.0e0, m.get(2), tol );

    m.set( 0, a );
    m.set( 1, b );
    m.set( 2, c );
    assertEquals( a, m.get(0), tol);
    assertEquals( b, m.get(1), tol );
    assertEquals( c, m.get(2), tol );

    m.zero();
    assertEquals( 0.0e0, m.get(0), tol);
    assertEquals( 0.0e0, m.get(1), tol );
    assertEquals( 0.0e0, m.get(2), tol );
  }


  // =====================================================================================
  @Test
  public void testZeroCopy() {
    // -----------------------------------------------------------------------------------
    final double a   = 1.2;
    final double b   = 3.4;
    final double c   = 5.6;
    final double d   = 7.8;
      
    Metric m1 = new Metric(4);
    assertEquals( 4, m1.size() );
    assertEquals( 0.0e0, m1.get(0), tol);
    assertEquals( 0.0e0, m1.get(1), tol );
    assertEquals( 0.0e0, m1.get(2), tol );
    assertEquals( 0.0e0, m1.get(3), tol );

    m1.set( 0, a );
    m1.set( 1, b );
    m1.set( 2, c );
    m1.set( 3, d );
    assertEquals( a, m1.get(0), tol);
    assertEquals( b, m1.get(1), tol );
    assertEquals( c, m1.get(2), tol );
    assertEquals( d, m1.get(3), tol );

    Metric m2 = new Metric(2);
    assertEquals( 2, m2.size() );
    assertEquals( 0.0e0, m2.get(0), tol);
    assertEquals( 0.0e0, m2.get(1), tol );

    m2.copy( m1 );
    assertEquals( 4, m2.size() );
    assertEquals( m1.get(0), m2.get(0), tol);
    assertEquals( m1.get(1), m2.get(1), tol);
    assertEquals( m1.get(2), m2.get(2), tol);
    assertEquals( m1.get(3), m2.get(3), tol);  
  }

  
  // =====================================================================================
  @Test
  public void testCompare() {
    // -----------------------------------------------------------------------------------
    final double a     = 1.2;
    final double b     = 3.4;
    final double c     = 5.6;
    
    final double big   = 9.9;
    final double small = 1.1;

    Metric m1 = new Metric(3);
    assertEquals( 3, m1.size() );
    m1.set( 0, a );
    m1.set( 1, b );
    m1.set( 2, c );

    Metric m2 = new Metric(3);
    assertEquals( 3, m2.size() );
    m2.set( 0, a );
    m2.set( 1, b );
    m2.set( 2, c );

    assertTrue( 0 == m1.compareTo( m2 ) );

    m1.set( 2, small );
    m2.set( 2, big );

    assertTrue( m1.compareTo( m2 ) < 0 );

    m1.set( 2, big );
    m2.set( 2, small );

    assertTrue( m1.compareTo( m2 ) > 0 );
  }


  // =====================================================================================
  @Test
  public void testStrinFormat() {
    // -----------------------------------------------------------------------------------
    final double a   =  1.2e-2;
    final double b   = -3.4e1;
    final double c   =  5.6e-1;
    final String fmt = "%6.3f";
    final String dlm = "|";

    final String test1 = " 1.2000e-02 -3.4000e+01  5.6000e-01";
    final String test2 = " 0.012|-34.000| 0.560";

    Metric m = new Metric(3);
    assertEquals( 3, m.size() );
    m.set( 0, a );
    m.set( 1, b );
    m.set( 2, c );

    assertEquals( test1, m.toString() );
    assertEquals( test2, m.format( fmt, dlm ) );
  }

  
  // =====================================================================================
  @Test
  public void testMath() {
    // -----------------------------------------------------------------------------------
    final double a   =  1.2e-2;
    final double b   = -3.4e1;
    final double c   =  5.6e-1;
    final double d   = -7.1e-2;

    final double sumsq = ( a*a + b*b + c*c + d*d );
    final double mse   = sumsq / 4.0e0;

    final double nrm = Math.sqrt( sumsq );
    
    final double an = a / nrm;
    final double bn = b / nrm;
    final double cn = c / nrm;
    final double dn = d / nrm;
    

    Metric m = new Metric(4);
    assertEquals( 4, m.size() );
    m.set( 0, a );
    m.set( 1, b );
    m.set( 2, c );
    m.set( 3, d );

    assertEquals( sumsq, m.sumsq(), tol );
    assertEquals( mse,   m.mse(), tol );

    double test = m.normalize();

    assertEquals( nrm, test, tol );

    assertEquals( an, m.get(0), tol );
    assertEquals( bn, m.get(1), tol );
    assertEquals( cn, m.get(2), tol );
    assertEquals( dn, m.get(3), tol );
  }

  
 
} // end class MetricTest



// =======================================================================================
// **                                M E T R I C T E S T                                **
// ======================================================================== END FILE =====
