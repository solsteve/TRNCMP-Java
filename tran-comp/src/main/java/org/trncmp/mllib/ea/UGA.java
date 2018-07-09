// ====================================================================== BEGIN FILE =====
// **                                       U G A                                       **
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
 * @file UGA.java
 *  Provides interface and methods to reproduce a weighted distribution of indicies.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2018-07-06
 */
// =======================================================================================

package org.trncmp.mllib.ea;

import org.trncmp.lib.Math2;
import org.trncmp.lib.AppOptions;

import org.trncmp.lib.ConfigDB;
import org.trncmp.lib.Dice;
import org.trncmp.lib.StringTool;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Level;

// =======================================================================================
public class UGA {
  // -------------------------------------------------------------------------------------
  static final Logger logger = LogManager.getRootLogger();
  protected static org.trncmp.mllib.Entropy ent = org.trncmp.mllib.Entropy.getInstance();

  public static final int RANDOM = 11;
  public static final int BEST   = 12;
  public static final int WORST  = 13;

  public static final int RANDOMIZE = 21;
  public static final int BRACKET   = 22;
  public static final int NOISE     = 23;
  public static final int CLONE     = 24;

  private UGAConfiguration config = null;
  private Model            model  = null;

  private Population primary = null; /**< Evolving population. */
  private Population working = null; /**< Intermediate population. */



    // -----------------------------------------------------------------------------------

    public static AppOptions.cli_map[] DEFAULT_CLI = {
	// name    section  cfgkey   required     default                description
	AppOptions.INIT( "pop",     "UGA", "pop",     true,  "200",                     "population size"           ),
	AppOptions.INIT( "tour",    "UGA", "tour",    true,  "7",                       "tournament size"           ),
	AppOptions.INIT( "pcross",  "UGA", "pcross",  true,  "[ 0.5, 0.9 ]",            "probability of cross over" ),
	AppOptions.INIT( "pmutate", "UGA", "pmutate", true,  "[ 0.4, 0.08, 0.2, 0.01]", "probability of mutation"   ),
	AppOptions.INIT( "bracket", "UGA", "bracket", true,  "0.333",                   "percent bracketed"         ),
	AppOptions.INIT( "maxgen",  "UGA", "maxgen",  true,  "5000",                    "maximum generation"        ),
	AppOptions.INIT( "report",  "UGA", "report",  true,  "100",                     "report interval"           ),
	AppOptions.INIT( "save",    "UGA", "save",    true,  "0",                       "save interval 0=no save"   ),
	AppOptions.INIT( "old",     "AUX", "oldpop",  false, null,                      "path to old population"    ),
	AppOptions.INIT( "new",     "AUX", "newpop",  false, null,                      "path to new population"    ),
    };


  
  
  // =====================================================================================
  public static UGAConfiguration factory( Model mod ) {
    // -----------------------------------------------------------------------------------
    return new UGAConfiguration( mod );
  }

  // =====================================================================================
  UGA( UGAConfiguration cfg, Model mod ) {
    // -----------------------------------------------------------------------------------
    config = cfg;
    model  = mod;

    initialize();
  }

  // =====================================================================================
  public void initialize() {
    // -----------------------------------------------------------------------------------
    primary = new Population( config.nPop(), model );
    working = new Population( config.nPop(), model );
  }

  // =====================================================================================
  /** @brief CrossOver.
   *  @param pCross probability of crossover vs. clone.
   *  @param p1 pointer to parent number one.
   *  @param p2 pointer to parent number two.
   *  @param c1 pointer to child number one.
   *  @param c2 pointer to child number two.
   *  @return true if crossover took place.
   *
   *  Determine if crossover will take place. If not, perform a simple clone. Crossover is
   *  performed by calling the functions of the underling structure.
   */
  // -------------------------------------------------------------------------------------
  protected static boolean Crossover( double pCross,
                                      PopulationMember c1, PopulationMember c2,
                                      PopulationMember p1, PopulationMember p2 ) {
    // -----------------------------------------------------------------------------------
    if ( null == p1 ) {
      throw new NullPointerException("Parent 1 ( NULL )");
    }

    if ( null == p2 ) {
      throw new NullPointerException("Parent 2 ( NULL )");
    }

    if ( null == c1 ) {
      throw new NullPointerException("Child 1 ( NULL )");
    }

    if ( null == c2 ) {
      throw new NullPointerException("Child 2 ( NULL )");
    }

    if ( ent.bool( pCross ) ) {
      c1.param.crossover( c2.param, p1.param, p2.param );
      c1.age = 0;
      c2.age = 0;
      return true;
    }
    
    c1.copy( p1 );
    c2.copy( p2 );
    c1.age = p1.age + 1;
    c2.age = p2.age + 1;

    return false;
  }


  // =====================================================================================
  /** @brief Mutation.
   *  @param pMutate probability that mutation is going to take place.
   *  @param sMutate percentage of elements that get mutated.
   *  @param dst pointer to mutated Member.
   *  @param src pointer to original Member.
   *  @return true if mutation took place.
   *
   *  Determine if mutation will take place. If not, perform a simple clone. Mutation is
   *  performed by calling the functions of the underling structure.
   */
  // -------------------------------------------------------------------------------------
  protected static int Mutate( PopulationMember dst, PopulationMember src,
                               double perc, double scale ) {
    // -----------------------------------------------------------------------------------
    if ( null == src ) {
      throw new NullPointerException("source ( NULL )");
    }

    dst.age = src.age;
    
    return dst.param.mutate( src.param, perc, scale );
  }


  // =====================================================================================
  /** @brief main loop.
   */
  // -------------------------------------------------------------------------------------
  public void run() {
    // -----------------------------------------------------------------------------------

    int popSize  = config.nPop();
    int tourSize = config.nTour();
    int maxgen   = config.maxgen();

    // ----- initialize population -------------------------------------------------------

    Encoding[] encode_array = new Encoding[ popSize ];

    for ( int i=0; i<popSize; i++ ) {
      encode_array[i] = primary.get(i).param;
    }
  
    model.pre_process( encode_array, popSize );

    primary.score();
    Population.ScoreReturn SR = primary.genStats( true );

    // ===================================================================================
    // -----              U G A   M a i n   E v o l u t i o n   L o o p              -----
    // ===================================================================================

    model.run_before( primary.best().metric,  primary.best().param,
                      primary.worst().metric, primary.worst().param );

    int iGen;

    logger.debug("UGA: Begin Evolution");

    for ( iGen=0; iGen<maxgen; iGen++ ) {
      double t = ( ( ( double )iGen )/( ( double )maxgen ) );
      double pCross  = Math2.PARAMETRIC( config.pCrossStart(),  config.pCrossFinal(),  t );
      double pMutate = Math2.PARAMETRIC( config.pMutateStart(), config.pMutateFinal(), t );
      double scale   = Math2.PARAMETRIC( config.sMutateStart(), config.sMutateFinal(), t );

      // ----- select & cross -- ( cycle 1 ) ---------------------------------------------

      for ( int i=0; i<popSize; i+=2 ) {
        int p1, p2;

        p1 = primary.select( tourSize );
        do {
          p2 = primary.select( tourSize );
        } while( p1 == p2 );

        Crossover( pCross,
                   working.get(i),  working.get(i+1),
                   primary.get(p1), primary.get(p2) );
      }

      // ----- mutate population -- ( cycle 2 ) ------------------------------------------

      for ( int i=0; i<popSize; i++ ) {
        Mutate( primary.get(i), working.get(i), pMutate, scale );
      }

      // ----- score population ----------------------------------------------------------

      model.pre_process( encode_array, popSize );

      primary.score();
      SR = primary.genStats( false );

      int     badIndex = SR.worstIndex;
      boolean newBest  = SR.newBest;

      // ----- reinsert best member ------------------------------------------------------

      if ( ! newBest ) {
        primary.set(badIndex, primary.best() );
      }

      // ----- report results ------------------------------------------------------------

      if ( 0 == ( iGen % config.report() ) ) {
        System.out.printf( "%d: ", iGen );
        model.display( primary.best().metric,  primary.best().param,  true );
        System.out.printf( "%d: ", iGen );
        model.display( primary.worst().metric, primary.worst().param, true );
        System.out.printf( "\n" );
      }

      if ( 0 < config.save() ) {
        if ( 0 == ( iGen % config.save() ) ) {
          logger.debug( "calling: model save" );
          model.save( primary.best().param );
        }
      }

      if ( model.meetsThreshold( primary.best().metric ) ) {
        break;
      }
    }

    logger.debug("UGA: End Evolution");

    model.run_after( primary.best().metric, primary.best().param,
                     primary.worst().metric, primary.worst().param );
  }


} // end class UGA

  
// =======================================================================================
// **                                       U G A                                       **
// ======================================================================== END FILE =====
