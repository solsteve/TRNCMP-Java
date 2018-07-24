// ====================================================================== BEGIN FILE =====
// **                                P O P U L A T I O N                                **
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
 * @file Population.java
 * <p>
 * Provides the interfaces for a population member.
 *
 * @date 2018-07-11
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
/** @class Population.
 *  @brief Array of members.
 *
 * Provides the interfaces for a population member.
 */
// ---------------------------------------------------------------------------------------
class Population {
  // -------------------------------------------------------------------------------------
  static final Logger logger = LogManager.getLogger();
  protected static org.trncmp.mllib.Entropy ent = org.trncmp.mllib.Entropy.getInstance();

  /** Pointer to a user defined model to be evaluated.  */
  protected Model model = null;

  /** Pointer to a copy of the best population member.  */
  protected PopulationMember bestMember = null;

  /** Pointer to a copy of the worst population member. */
  protected PopulationMember worstMember = null;
  
  protected PopulationMember[] member = null;
  protected int                count  = 0;

  
  // =====================================================================================
  /** @brief Constructor.
   *  @param n   number of population members.
   *  @param mod pointer to a model for allocating encodings and metrics.
   */
  // -------------------------------------------------------------------------------------
  public Population( int n, Model mod ) {
    // -----------------------------------------------------------------------------------
    model  = mod;
    count  = n;
    member = new PopulationMember[ count ];

    bestMember  = new PopulationMember( model );
    worstMember = new PopulationMember( model );

    for ( int i=0; i<count; i++ ) {
      member[i] = new PopulationMember( model );
    }
  }

  
  // =====================================================================================
  /** @brief Best.
   *  @return pointer to the best recorded PopulationMember.
   */
  // -------------------------------------------------------------------------------------
  public PopulationMember best( ) {
    // -----------------------------------------------------------------------------------
    return bestMember;
  }

  
  // =====================================================================================
  /** @brief Worst.
   *  @return pointer to the worst recorded PopulationMember.
   */
  // -------------------------------------------------------------------------------------
  public PopulationMember worst( ) {
    // -----------------------------------------------------------------------------------
    return worstMember;
  }

  
  // =====================================================================================
  /** @brief Size.
   *  @return number of population members.
   */
  // -------------------------------------------------------------------------------------
  public int size( ) {
    // -----------------------------------------------------------------------------------
    return count;
  }

  
  // =====================================================================================
  /** @brief Get.
   *  @param idx index of population member.
   *  @return pointer to population member by index.
   */
  // -------------------------------------------------------------------------------------
  public PopulationMember get( int idx ) {
    // -----------------------------------------------------------------------------------
    return member[idx];
  }

  
  // =====================================================================================
  /** @brief Set.
   *  @param idx index of population member.
   *  @param M   pointer to source PopulationMember.
   *
   *  Make a deep copy of a PopulationMember and store it at index.
   */
  // -------------------------------------------------------------------------------------
  public void set( int idx, PopulationMember M ) {
    // -----------------------------------------------------------------------------------
    member[idx].copy( M );
  }

  
  // =====================================================================================
  /** @brief Randomize.
   *  @param pb probability of bracket.
   *
   *  Randomize the population. With probability pb use Braketing and (1-pb) use uniform
   *  randomization.
   */
  // -------------------------------------------------------------------------------------
  public void randomize( double pb ) {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<count; i++ ) {
      if ( ent.bool( pb ) ) {
        member[i].param.bracket();
      } else {
        member[i].param.randomize();
      }
    }
  }

  
  // =====================================================================================
  /** @brief Randomize.
   *
   *  Randomize the population.
   */
  // -------------------------------------------------------------------------------------
  public void randomize( ) {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<count; i++ ) {
      member[i].param.randomize();
    }
  }

  
  // =====================================================================================
  /** @brief Bracket.
   *
   *  Bracket the population.
   */
  // -------------------------------------------------------------------------------------
  void bracket( ) {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<count; i++ ) {
      member[i].param.bracket();
    }
  }

  
  // =====================================================================================
  /** @brief Noise.
   *  @param scale  scale of the noise (see {@link Encoding.N_SIGMA_SCALE})
   *
   *  Add Gaussian noise to the population.
   */
  // -------------------------------------------------------------------------------------
  void noise( double scale ) {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<count; i++ ) {
      member[i].param.noise( scale );
    }
  }

  
  // =====================================================================================
  /** @brief Noise.
   *  @param sample pointer to a reference PopulationMember.
   *  @param scale  scale of the noise (see {@link Encoding.N_SIGMA_SCALE})
   *
   *  Add Gaussian noise to the population. Use the sample PopulationMember as a
   *  mean reference.
   */
  // -------------------------------------------------------------------------------------
  void noise( PopulationMember sample, double scale ) {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<count; i++ ) {
      member[i].copy( sample );
      member[i].param.noise( scale );
    }
  }


  // =====================================================================================
  /** @brief Select.
   *  @param  pop a pointer to either popA or popB..
   *  @return index to the best member out of tourSize selected members..
   *
   *  Select ( tourSize ) members from a population of ( popSize ) members.
   *  Choose the member with the best set of metrics as determined by the
   *  user supplied function:
   *     Model.isLeftBetter( Metric, Metric )
   */
  // -------------------------------------------------------------------------------------
  int select( int tour ) {
    // -----------------------------------------------------------------------------------
    int idx = ent.index( count );

    for ( int i=1; i<tour; i++ ) {
      int t = ent.index( count );
      if ( model.isLeftBetter( member[t].metric, member[idx].metric ) ) {
        idx = t;
      }
    }

    return idx;
  }



  
  // =====================================================================================
  /** @class ScoreReturn.
   *  @brief Score Return.
   *
   *  Simple object to return a score.
   */
  // -------------------------------------------------------------------------------------
  static class ScoreReturn {
    // -----------------------------------------------------------------------------------
    public boolean newBest    = false;
    public int     worstIndex = -1;
    
    public ScoreReturn() { }
    
  } // end class Population.ScoreReturn



  
  // =====================================================================================
  /** @brief Score Sequentially.
   *
   *  Score the entire population sequentially.
   */
  // -------------------------------------------------------------------------------------
  private void score_sequential() {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<count; i++ ) {
      PopulationMember test = member[i];
      model.execute( test.metric, test.param );
    }
  }


  // =====================================================================================
  /** @brief Score.
   *
   *  Score the entire population.
   */
  // -------------------------------------------------------------------------------------
  public void score() {
    // -----------------------------------------------------------------------------------
    score_sequential();
  }


  // =====================================================================================
  /** @brief Score.
   *  @param rezero flag to find new best.
   *
   *  Score the entire population. Optionally zero the current best place holder.
   */
  // -------------------------------------------------------------------------------------
  public ScoreReturn genStats( boolean rezero ) {
    // -----------------------------------------------------------------------------------

	int start=0;

	ScoreReturn SR = new ScoreReturn();
	SR.newBest     = false;
        SR.worstIndex  = ent.index( count );
        
	if ( rezero ) {
	    bestMember.copy( member[0] );
	    worstMember.copy( member[0] );
	    start = 1;
	}

	for ( int i=start; i<count; i++ ) {
	    PopulationMember test = member[i];

	    if ( model.isLeftBetter( test.metric, bestMember.metric ) ) {
		bestMember.copy( test );
		SR.newBest = true;
	    }

	    if ( model.isLeftBetter( worstMember.metric, test.metric ) ) {
		worstMember.copy( test );
		SR.worstIndex = i;
	    }
	}

	return SR;
  }


  // =====================================================================================
  /** @brief Find member.
   *  @param mt member type.
   *  @return index of the resulting population member.
   *
   *  Search the primary population for an mt type member.
   */
  // -------------------------------------------------------------------------------------
  int find( int mt ) {
    // -----------------------------------------------------------------------------------
    int rv = 0;
    switch( mt ) {
      // ---------------------------------------------------------------------------------
      case UGA.RANDOM:
        rv = ent.index(count);
        break;
        // -------------------------------------------------------------------------------
      case UGA.BEST:
        rv = 0;
        for ( int i=1; i<count; i++ ) {
          if ( model.isLeftBetter( member[i].metric, member[rv].metric ) ) {
            rv = i;
          }
        }
        break;
        // -------------------------------------------------------------------------------
      case UGA.WORST:
        rv = 0;
        for ( int i=1; i<count; i++ ) {
          if ( model.isLeftBetter( member[rv].metric, member[i].metric ) ) {
            rv = i;
          }
        }
        break;
        // -------------------------------------------------------------------------------
      default:
        logger.error( "Switch improbability" );
        rv = 0;
        break;
    }
    return rv;
  }

}

// =======================================================================================
// **                                P O P U L A T I O N                                **
// ======================================================================== END FILE =====
