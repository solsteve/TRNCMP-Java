// ====================================================================== BEGIN FILE =====
// **                            U G A _ M O D E L _ R E A L                            **
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
 * @file UGA_Model_Real.java
 * <p>
 * Provides interface and methods for a toy model to test the optimization of as list
 * of real valued parameters.
 *
 * @date 2018-07-16
 *
 * ---------------------------------------------------------------------------------------
 *
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 *
 * @author Stephen W. Soliday
 * @date 2015-08-15
 */
// =======================================================================================


package org.trncmp.apps.uga;

import org.trncmp.lib.ConfigDB;

import org.trncmp.mllib.ea.Model;
import org.trncmp.mllib.Entropy;
import org.trncmp.mllib.ea.Metric;
import org.trncmp.mllib.ea.Encoding;
import org.trncmp.mllib.ea.RealEncoding;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class UGA_Model_Real extends Model {
  // -------------------------------------------------------------------------------------
  static final Logger logger = LogManager.getLogger();

  protected final int TEST_LEN = 10;

  protected double[] fixed_r = new double[TEST_LEN];

  protected ConfigDB config = null;
  
  // =====================================================================================
  public UGA_Model_Real( ConfigDB cdb ) {
    // -----------------------------------------------------------------------------------
    super();
    config = cdb;
  }

  
  // =====================================================================================
  /** @brief Metric allocation.
   *  @return pointer to a new metric allocation.
   *
   *  Place code here to allocate and set up metrics for this model.
   */
  // -------------------------------------------------------------------------------------
  public Metric alloc_metric( ) {
    // -----------------------------------------------------------------------------------
    return new Metric( 2 );
  }

  
  // =====================================================================================
  /** @brief Encoding allocation.
   *  @return pointer to a new encoding allocation.
   *
   *  Place code here to allocate and set up encoding for this model.
   */
  // -------------------------------------------------------------------------------------
  public Encoding alloc_encoding( ) {
    // -----------------------------------------------------------------------------------
    return (Encoding) new RealEncoding( TEST_LEN );
  }

  
  // =====================================================================================
  /** @brief Initialize.
   *  @param cfg pointer to a ConfigDB database object.
   *  @return true if errors occur.
   *
   *  This is where you need to initialize your model prior to evolution.
   */
  // -------------------------------------------------------------------------------------
  public boolean config() {
    // -----------------------------------------------------------------------------------
    Entropy ent = Entropy.getInstance();

    System.out.printf( "\nTEST =" );

    for ( int i=0; i<TEST_LEN; i++ ) {
      fixed_r[i] = 2.0*ent.uniform() - 1.0;
      System.out.printf( " %f", fixed_r[i] );
    }
    System.out.printf( "\n\n" );

    return false;
  }

  
  // =====================================================================================
  public void save( Encoding dummy ) {
    // -----------------------------------------------------------------------------------
    logger.warn( "Save is not implemented here" );
  }

  
  // =====================================================================================
  /** @brief Display.
   *  @param msg message to prefix.
   *  @param M   pointer to a uga::Metric.
   *  @param E   pointer to a uga::Encoding.
   *
   *  This function may be called at anytime, but should be called once after the evolution
   *  cycle, using the UGA::display_model( UGA::best, std::cout ) function.
   *
   *  You should convert the UGA representation (i.e. real -1 < x < +1) into something
   *  meaningful to your model.
   */
  // -------------------------------------------------------------------------------------
  public void display_short( String msg, Metric M, Encoding E ) {
    // -----------------------------------------------------------------------------------
    System.out.format( "%s : %11.4e | %s\n", msg, M.get(0), E.format( "%f", ", " ) );
  }


  // =====================================================================================
  /** @brief Execute.
   *  @param M pointer to a uga::Metric.
   *  @param E pointer to a uga::Encoding.
   *
   *  This is where you run one instance of your model and generate metrics.
   *  Optimization is important this function fill be called once for each population
   *  member every generation. pop=500 gen=1000, this function will execute half a million
   *  times. Try not to do any disk IO.
   */
  // -------------------------------------------------------------------------------------
  public void execute( Metric M, Encoding E ) {
    // -----------------------------------------------------------------------------------
    double mR = 0.0e0;
    double dR = 0.0e0;

    RealEncoding param = (RealEncoding)E;

    for ( int i=0; i<TEST_LEN; i++ ) {
      dR = param.get(i) - fixed_r[i];
      mR += (dR*dR);
    }

    M.set( 0, mR );
  }

  // =====================================================================================
  /** @brief Example Metric.
   *  @param lhs pointer to a uga::Metric object.
   *  @param rhs pointer to a uga::Metric object.
   *  @return true if the lhs set of metrics is better than the rhs set.
   *
   *  This is probably the most important function. It determines which of two metrics is
   *  better. lhs = left hand side. rhs = right and side. Return a true if lhs is a better
   *  set of metrics than rhs. Optimization is critical. Imagine that pop=500 gen=1000 and
   *  tour=7. This function could be called over 3 million times.
   */
  // -------------------------------------------------------------------------------------
  public boolean isLeftBetter( Metric lhs, Metric rhs ) {
    // -----------------------------------------------------------------------------------

    if ( lhs.get(0) < rhs.get(0) ) { return true; }
    if ( lhs.get(0) > rhs.get(0) ) { return false; }

    return true;
  }

  // =====================================================================================
  /** @brief Threshold.
   *  @param M pointer to a uga::Metric set.
   *  @return true is the metric set is good enough.
   *
   *  You may use this to prematurely terminate the evolution if the metric set is 
   *  good enough. Just return a false if you do not want this feature.
   */
  // -------------------------------------------------------------------------------------
  public boolean meetsThreshold( Metric M ) {
    // -----------------------------------------------------------------------------------
    double x = M.get(0);
    if ( 1e-14 > x ) { return true; }
    return false;
  }
}

// =======================================================================================
// **                            U G A _ M O D E L _ R E A L                            **
// ======================================================================== END FILE =====
