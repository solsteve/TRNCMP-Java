// ====================================================================== BEGIN FILE =====
// **                            U G A _ M O D E L _ R E A L                            **
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
 * @file UGA_Model_Real.java
 *  Provides interface and methods for a toy model to test the optimization of as list
 *  of real valued parameters.
 *
 * @author Stephen W. Soliday
 * @date 2018-07-17
 */
// =======================================================================================


package org.trncmp.apps.uga;

import org.trncmp.lib.ConfigDB;

import org.trncmp.mllib.ea.Model;
import org.trncmp.mllib.Entropy;
import org.trncmp.mllib.ea.Metric;
import org.trncmp.mllib.ea.Encoding;
import org.trncmp.mllib.ea.RealEncoding;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Level;


// =======================================================================================
public class UGA_Model_Real extends Model {
  // -------------------------------------------------------------------------------------
  static final Logger logger = LogManager.getRootLogger();

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
