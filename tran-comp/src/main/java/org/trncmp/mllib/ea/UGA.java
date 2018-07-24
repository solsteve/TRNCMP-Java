// ====================================================================== BEGIN FILE =====
// **                                       U G A                                       **
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
 * @file UGA.java
 * <p>
 * Provides interface and methods to reproduce a weighted distribution of indicies.
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

import org.trncmp.lib.Math2;
import org.trncmp.lib.AppOptions;
import org.trncmp.lib.StopWatch;

import org.trncmp.lib.ConfigDB;
import org.trncmp.lib.StringTool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class UGA {
  // -------------------------------------------------------------------------------------
  static final Logger logger = LogManager.getLogger();
  protected static org.trncmp.mllib.Entropy ent = org.trncmp.mllib.Entropy.getInstance();

  public static final int RANDOM = 11;
  public static final int BEST   = 12;
  public static final int WORST  = 13;

  public static final int RANDOMIZE = 21;
  public static final int BRACKET   = 22;
  public static final int NOISE     = 23;
  public static final int CLONE     = 24;

  /** pointer to the configuration object */
  private UGAConfiguration config = null;

  /** Pointer to the user supplied model for fitness evaluation */
  private Model model  = null;

  /** Evolving population. */
  private Population primary = null;

  /** Intermediate population. */
  private Population working = null;


  // -------------------------------------------------------------------------------------

  public static AppOptions.cli_map[] DEFAULT_CLI = {
    // name    section  cfgkey   required     default                description
    AppOptions.INIT( "pop",     "UGA", "pop",     true,  "200",          "population size"           ),
    AppOptions.INIT( "tour",    "UGA", "tour",    true,  "7",            "tournament size"           ),
    AppOptions.INIT( "pcross",  "UGA", "pcross",  true,  "[ 0.5, 0.9 ]", "probability of cross over" ),
    AppOptions.INIT( "pmutate", "UGA", "pmutate", true,  "[ 0.4, 0.08]", "probability of alllele mutation"   ),
    AppOptions.INIT( "smutate", "UGA", "smutate", true,  "[ 0.2, 0.01]", "sacle of mutation"   ),
    AppOptions.INIT( "bracket", "UGA", "bracket", true,  "0.333",        "percent bracketed"         ),
    AppOptions.INIT( "maxgen",  "UGA", "maxgen",  true,  "5000",         "maximum generation"        ),
    AppOptions.INIT( "report",  "UGA", "report",  true,  "100",          "report interval"           ),
    AppOptions.INIT( "save",    "UGA", "save",    true,  "0",            "save interval 0=no save"   ),
    AppOptions.INIT( "old",     "AUX", "oldpop",  false, null,           "path to old population"    ),
    AppOptions.INIT( "new",     "AUX", "newpop",  false, null,           "path to new population"    ),
    AppOptions.INIT( "mp",      "MP",  "cpu",     false, null,           "max threads"    ),
  };


  
  
  // =====================================================================================
  public static UGAConfiguration factory( Model mod ) {
    // -----------------------------------------------------------------------------------
    return new UGAConfiguration( mod );
  }

  
  // =====================================================================================
  /** @brief private constructor.
   *  @param cfg pointer to a configuration object.
   *  @param mod pointer to a user defined model.
   */
  // -------------------------------------------------------------------------------------
  UGA( UGAConfiguration cfg, Model mod ) {
    // -----------------------------------------------------------------------------------
    config = cfg;
    model  = mod;

    initialize();
  }

  
  // =====================================================================================
  public void initialize() {
    // -----------------------------------------------------------------------------------
    if ( model.config() ) {
      logger.error( "Model experienced errors during initialization" );
      System.exit(1);
    }

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
   *  @param dst pointer to mutated Member.
   *  @param src pointer to original Member.
   *  @param perc probability that mutation is going to take place.
   *  @param scale percentage of elements that get mutated.
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
  /** @brief Randomize.
   *
   *  Randomize the population with configured probability of bracketing.
   */
  // -------------------------------------------------------------------------------------
  public void randomize( ) {
    // -----------------------------------------------------------------------------------
    randomize( config.pBracket() );
  }

  // =====================================================================================
  /** @brief Randomize.
   *  @param b probability to bracket vers. randomize.
   *
   *  Randomize the population with supplied probability of bracketing.
   */
  // -------------------------------------------------------------------------------------
  public void randomize( double b ) {
    // -----------------------------------------------------------------------------------
    primary.randomize( b );
  }

  


  // =====================================================================================
  /** @brief Display.
   *  @param msg message to prefix the display.
   *  @param M   pointer to population member.
   *
   *  Use the user supplied model to display a representation of the variable structure.
   */
  // -------------------------------------------------------------------------------------
  public void display_model( String msg, PopulationMember M ) {
    // -----------------------------------------------------------------------------------
    display_model( msg, M, false );
  }

  
  // =====================================================================================
  /** @brief Display.
   *  @param msg       message to prefix the display.
   *  @param M         pointer to population member.
   *  @param use_short boolean to use short display.
   *
   *  Use the user supplied model to display a representation of the variable structure.
   */
  // -------------------------------------------------------------------------------------
  public void display_model( String msg, PopulationMember M, boolean use_short ) {
    // -----------------------------------------------------------------------------------
    model.display( msg, M.metric, M.param, use_short );
  }




  // =====================================================================================
  /** @brief Display.
   *  @param msg message to prefix the display.
   *  @param idx index into the primary population.
   *
   *  Use the user supplied model to display a representation of the variable structure.
   */
  // -------------------------------------------------------------------------------------
  public void display_model_index( String msg, int idx ) {
    // -----------------------------------------------------------------------------------
    display_model_index( msg, idx, false );
  }

  
  // =====================================================================================
  /** @brief Display.
   *  @param msg       message to prefix the display.
   *  @param idx       index into the primary population.
   *  @param use_short boolean to use short display.
   *
   *  Use the user supplied model to display a representation of the variable structure.
   */
  // -------------------------------------------------------------------------------------
  public void display_model_index( String msg, int idx, boolean use_short ) {
    // -----------------------------------------------------------------------------------
    display_model( msg, primary.get(idx), use_short );
  }




  // =====================================================================================
  /** @brief Display.
   *  @param msg message to prefix the display.
   *  @param mt  member type to display.
   *
   *  Use the user supplied model to display a representation of the variable structure.
   */
  // -------------------------------------------------------------------------------------
  public void display_model_type( String msg, int mt ) {
    // -----------------------------------------------------------------------------------
    display_model_type( msg, mt, false );
  }


  // =====================================================================================
  /** @brief Display.
   *  @param msg       message to prefix the display.
   *  @param mt        member type to display.
   *  @param use_short boolean to use short display.
   *
   *  Use the user supplied model to display a representation of the variable structure.
   */
  // -------------------------------------------------------------------------------------
  public void display_model_type( String msg, int mt, boolean use_short ) {
    // -----------------------------------------------------------------------------------
    display_model_index( msg, primary.find( mt ), use_short );
  }


  

  // =====================================================================================
  /** @brief Read Population.
   *  @param fspc string containing the path to a population file.
   *  @return false if no errors occured.
   */
  // -------------------------------------------------------------------------------------
  public boolean read( String fspc ) {
    // -----------------------------------------------------------------------------------
    logger.info( "Cannot read populations yet" );
    return true;
  }

  
  // =====================================================================================
  /** @brief Write Population.
   *  @param fspc string containing the path to a population file.
   *  @return false if no errors occured.
   */
  // -------------------------------------------------------------------------------------
  public boolean write( String fspc ) {
    // -----------------------------------------------------------------------------------
    logger.info( "Cannot write populations yet" );
    return true;
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

    int num_procs = config.nCPU();

    // ----- initialize population -------------------------------------------------------

    Encoding[] encode_array = new Encoding[ popSize ];

    for ( int i=0; i<popSize; i++ ) {
      encode_array[i] = primary.get(i).param;
    }
  
    model.pre_process( encode_array, popSize );

    //primary.score();

    new ModelExecutor( model, num_procs, popSize ).runAll( primary );

    Population.ScoreReturn SR = primary.genStats( true );

    // ===================================================================================
    // -----              U G A   M a i n   E v o l u t i o n   L o o p              -----
    // ===================================================================================

    model.run_before( primary.best().metric,  primary.best().param,
                      primary.worst().metric, primary.worst().param );

    int iGen;

    logger.debug("UGA: Begin Evolution");

    StopWatch swatch = new StopWatch();

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

      // primary.score();

      new ModelExecutor( model, num_procs, popSize ).runAll( primary );

      SR = primary.genStats( false );

      int     badIndex = SR.worstIndex;
      boolean newBest  = SR.newBest;

      // ----- reinsert best member ------------------------------------------------------

      if ( ! newBest ) {
        primary.set(badIndex, primary.best() );
      }

      // ----- report results ------------------------------------------------------------

      if ( 0 == ( iGen % config.report() ) ) {
        model.display( String.format( "%d", iGen ),
                       primary.best().metric,
                       primary.best().param,  true );
        
        model.display( String.format( "%d", iGen ),
                       primary.worst().metric,
                       primary.worst().param, true );
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

    // ===================================================================================
    // -----              U G A   M a i n   E v o l u t i o n   L o o p              -----
    // ===================================================================================

    double elapsed = swatch.seconds();

    
    
    logger.info(
        String.format("UGA: End Evolution - %d population members %d generations %g seconds.",
                      popSize, maxgen, elapsed ) );

    model.run_after( primary.best().metric,  primary.best().param,
                     primary.worst().metric, primary.worst().param );
  }


} // end class UGA

  
// =======================================================================================
// **                                       U G A                                       **
// ======================================================================== END FILE =====
