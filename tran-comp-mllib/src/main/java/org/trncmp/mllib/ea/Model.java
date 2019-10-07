// ====================================================================== BEGIN FILE =====
// **                                    M O D E L                                      **
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
 * @file Model.java
 * <p>
 * Provides interface for an evolvable model.
 *
 * @date 2018-07-12
 * 
 * ---------------------------------------------------------------------------------------
 * 
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 * 
 * @author Stephen W. Soliday
 * @date 2015-08-09
 */
// =======================================================================================

package org.trncmp.mllib.ea;

import org.trncmp.lib.ConfigDB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
/** @class Model
 *
 * Abstract interface for an evolvable model.
 *
 * The user must overload the following functions.
 *   - alloc_metric   -- Allocate space for a metric object.
 *   - alloc_encoding -- Allocate space for an encoding object.
 *   - config         -- Initialize the model. One time setup activities belong here.
 *   - save
 *   - display_long   -- Present a full representation of the model parameters
 *                       and metrics.
 *   - display_short  -- Present an abbreviated representation of the variable
 *                       parameters, should be one line.
 *   - execute        -- Execute the model on one set of variable parameters and
 *                       generate metrics.
 *   - isLeftBetter   -- Compare two metrics and return true if the left metric is
 *                       better than the right.
 *   - meetsThreshold -- Stop the generations if the metric is good enough.
 */
// ---------------------------------------------------------------------------------------
public abstract class Model {
  // -------------------------------------------------------------------------------------
  static final Logger logger = LogManager.getLogger();

  
  // =====================================================================================
  /** @brief Allocate metric.
   *  @return pointer to a new allocation of a metric.
   *
   *  Allocate space for a metric object.
   */
  // -------------------------------------------------------------------------------------
  public abstract Metric alloc_metric( );

  
  // =====================================================================================
  /** @brief Allocate encoding.
   *  @return pointer to a new allocation of an encoding.
   *
   *  Allocate space for an encoding object.
   */
  // -------------------------------------------------------------------------------------
  public abstract Encoding alloc_encoding ( );

  
  // =====================================================================================
  /** @brief Initialize Model.
   *  @return true if errors occur.
   *
   *  Initialize the model. One time setup activities belong here.
   */
  // -------------------------------------------------------------------------------------
  public abstract boolean config();

  
  // =====================================================================================
  /** @brief Save.
   *  @param E pointer to the encoding for this model.
   *
   *  Provides a means for intermediate saves. C.temp_save controls the interval
   *  for saving the best population model. 0=no intermediate save. non zero will save
   *  every C.temp_save generations.
   */
  // -------------------------------------------------------------------------------------
  public abstract void save( Encoding E );

  
  // =====================================================================================
  /** @brief Display Short.
   *  @param msg message to prefix.
   *  @param M   pointer to the return metrics
   *  @param E   pointer to the encoding for this model.
   *
   *  Present an abbreviated representation of the variable parameters,
   *  should be one line.
   */
  // -------------------------------------------------------------------------------------
  public abstract void display_short( String msg, Metric M, Encoding E );

  
  // =====================================================================================
  /** @brief Execute.
   *  @param M pointer to the return metrics
   *  @param E pointer to the encoding for this model.
   *
   *  Execute the model on one set of variable parameters and generate metrics.
   */
  // -------------------------------------------------------------------------------------
  public abstract void execute( Metric M, Encoding E );

  
  // =====================================================================================
  /** @brief Test.
   *  @param lhs pointer to the left hand side metric.
   *  @param rhs pointer to the right hand side metric.
   *  @return true if the left hand metric is better than the right.
   *
   *  Compare two metrics and return true if the left metric 
   *  is better than the rightM.get.
   */
  // -------------------------------------------------------------------------------------
  public abstract boolean isLeftBetter( Metric lhs, Metric rhs );

  
  // =====================================================================================
  /** @brief Test.
   *  @param M pointer to a metric.
   *  @return true if the metric is good enough.
   *
   *  Stop the generations if the metric is good enough.
   */
  // -------------------------------------------------------------------------------------
  public abstract boolean meetsThreshold( Metric M );




  // =====================================================================================
  /** @brief Display.
   *  @param msg message to prefix the display.
   *  @param M   pointer to a uga::Metric.
   *  @param E   pointer to a uga::Encoding.
   *
   *  This function may be called at anytime, but should be called once after the
   *  evolution cycle, using the UGA::display_model( UGA::best, std::cout ) function.
   *
   *  You should convert the UGA representation (i.e. real -1 < x < +1) into something
   *  meaningful to your model.
   */
  // -------------------------------------------------------------------------------------
  public void display_long( String msg, Metric M, Encoding E ) {
    // -----------------------------------------------------------------------------------
    display_short( msg, M, E );
  }

  
  // =====================================================================================
  /** @brief Default Constructor.
   *
   *  Used incase the used calls super()
   */
  // -------------------------------------------------------------------------------------
  public Model( ) {
    // -----------------------------------------------------------------------------------
  }

  
  // =====================================================================================
  /** @brief Default Display.
   *  @param M pointer to a  Metric.
   *  @param E pointer to an Encoding.
   *
   *  Call the display procedure.
   */
  // -------------------------------------------------------------------------------------
  public void display( String msg, Metric M,  Encoding E ) {
    // -----------------------------------------------------------------------------------
    display( msg, M, E, false );
  }

  
  // =====================================================================================
  /** @brief Display.
   *  @param M     pointer to a  Metric.
   *  @param E     pointer to an Encoding.
   *  @param short flag, if true use the short display probided by the user.
   *
   *  Use one of the user supplied procedures to display something about this model
   *  in relationship to a metric and an encoding.
   */
  // -------------------------------------------------------------------------------------
  public void display( String msg, Metric M,  Encoding E,  boolean use_short ) {
    // -----------------------------------------------------------------------------------
    if ( use_short ) {
      display_short( msg, M, E );
    } else {
      display_long( msg, M, E );
    }
  }

  
  // =====================================================================================
  /** @brief Pre Evolve.
   *  @param BM pointer to a best  metric.
   *  @param BE pointer to a best  encoding.
   *  @param WM pointer to a wosrt metric.
   *  @param WE pointer to a worst encoding.
   *
   *  When the user overrides this procedure, they have the opportunity to execute code
   *  within the model before the evolution begins.
   *
   *  @note It is prefered but not required that this procedure should be overridden
   *        by the user.
   */
  // -------------------------------------------------------------------------------------
  public void run_before( Metric BM, Encoding BE, Metric WM, Encoding WE ) {
    // -----------------------------------------------------------------------------------
    logger.debug( "Model::run_before ( this default should be overridden )" );

    display_long( "Initial best  population member",  BM, BE );
    display_long( "Initial worst population member", WM, WE );
  }

  
  // =====================================================================================
  /** @brief Post Evolve.
   *  @param BM pointer to a best  metric.
   *  @param BE pointer to a best  encoding.
   *  @param WM pointer to a wosrt metric.
   *  @param WE pointer to a worst encoding.
   *
   *  When the user overrides this procedure, they have the opportunity to execute code
   *  within the model after the evolution is over.
   *
   *  @note It is prefered but not required that this procedure should be overridden
   *        by the user.
   */
  // -------------------------------------------------------------------------------------
  public void run_after( Metric BM, Encoding BE, Metric WM, Encoding WE ) {
    // -----------------------------------------------------------------------------------
    logger.debug( "Model::run_after ( this default should be overridden )" );

    display_long( "Final best  population member", BM, BE );
    display_long( "Final worst population member", WM, WE );
  }

  
  // =====================================================================================
  /** @brief Pre-process.
   *  @param E pointer to an array of encoding objects for this model.
   *  @param npop number of encodings to preprocess
   *
   *  Pre-process encodings prior to evaluation.
   */
  // -------------------------------------------------------------------------------------
  public void pre_process( Encoding[] E, int npop ) {
    // -----------------------------------------------------------------------------------
    // ADD CODE IN THE DERIVED CLASS TO HANDEL THIS
  }

  
} // end class AbstractModel

  
// =======================================================================================
// **                                     M O D E L                                     **
// ======================================================================== END FILE =====
