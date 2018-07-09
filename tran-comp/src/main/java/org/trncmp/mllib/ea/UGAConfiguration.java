// ====================================================================== BEGIN FILE =====
// **                          U G A C O N F I G U R A T I O N                          **
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
 * @file UGAConfiguration.java
 *  Provides interface and methods for a builder/configuration class for UGA.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2018-07-06
 */
// =======================================================================================

package org.trncmp.mllib.ea;

import org.trncmp.lib.ConfigDB;
import org.trncmp.lib.Dice;
import org.trncmp.lib.StringTool;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Level;


// =======================================================================================
public class UGAConfiguration {
  // -------------------------------------------------------------------------------------
  static final Logger logger = LogManager.getRootLogger();

  /** Display an abbreviated report if users model supports it. */
  private boolean p_fullReport = false;

  /** Maximum number of generations. */
  private int p_maxgen = 10000;

  /** Number of generations between reports. */
  private int p_report = 100;

  /** Number of generations between saves 0=no save */
  private int p_save = 1000;

  /** Number of members in the population. */
  private int p_popSize = 128;

  /** Number of members examined in a tournament. */
  private int p_tourSize = 7;

  /** Probability of Crossover vs. Clone at Gen 0. */
  private double p_pCrossStart = 0.8;

  /** Probability of Crossover vs. Clone at Gen Final. */
  private double p_pCrossFinal = 0.9;

  /** Probability for an individual allele to mutate at Gen 0. */
  private double p_pMutateStart = 0.25;

  /** Probability for an individual allele to mutate at Gen Final. */
  private double p_pMutateFinal = 0.02;

  /** Mutation scale at Gen 0. */
  private double p_sMutateStart = 0.5;

  /** Mutation scale at Gen Final. */
  private double p_sMutateFinal = 0.1;

  /** Percentage of population bracketed during randomization */
  private double p_pBracket = 0.25;


  private Model model = null;

  // =====================================================================================
  /** @brief Void constructor.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration( Model mod ) {
    // -----------------------------------------------------------------------------------
    model = mod;
  }


  // =====================================================================================
  /** @brief Final build procedure.
   */
  // -------------------------------------------------------------------------------------
  public UGA build() {
    // -----------------------------------------------------------------------------------
    return new UGA( this, model );
  }








  // =====================================================================================
  /** @brief Set full report flag.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Display an abbreviated report if users model supports it.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration fullReport( boolean f ) {
    p_fullReport = f;
    return this;
  }

  
  // =====================================================================================
  /** @brief Set maximum generations.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Maximum number of generations.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration maxgen( int n ) {
    p_maxgen = n;
    return this;
  }

  
  // =====================================================================================
  /** @brief Set report interval.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Number of generations between reports.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration report( int n ) {
    p_report = n;
    return this;
  }

  
  // =====================================================================================
  /** @brief Set save interval.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Number of generations between saves 0=no save.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration save( int n ) {
    p_save = n;
    return this;
  }

  
  // =====================================================================================
  /** @brief Set population size.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Number of members in the population.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration nPop( int n ) {
    p_popSize = n;
    return this;
  }

  
  // =====================================================================================
  /** @brief Set tournament size.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Number of members examined in a tournament.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration nTour( int n ) {
    p_tourSize = n;
    return this;
  }

  
  // =====================================================================================
  /** @brief Set bracket probability.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Percentage of population bracketed during randomization.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration pBracket( double p ) {
    p_pBracket = p;
    return this;
  }

  
  // =====================================================================================
  /** @brief Set crossover probability.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Probability of Crossover vs. Clone.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration pCross( double vo, double vf ) {
    p_pCrossStart = vo;
    p_pCrossFinal = vf;
    return this;
  }

  
  // =====================================================================================
  /** @brief Set start mutation probability.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Probability for an individual allele to mutate.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration pMutate( double vo, double vf ) {
    p_pMutateStart = vo;
    p_pMutateFinal = vf;
    return this;
  }

  
  // =====================================================================================
  /** @brief Set start mutation scale.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Mutation scale.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration sMutate( double vo, double vf ) {
    p_sMutateStart = vo;
    p_sMutateFinal = vf;
    return this;
  }








  // =====================================================================================
  /** @brief Get full report flag.
   *  @return Flag for display of full report.
   *
   *  Display an abbreviated report if users model supports it.
   */
  // -------------------------------------------------------------------------------------
  public boolean fullReport( ) {
    // -----------------------------------------------------------------------------------
    return p_fullReport;
  }

  
  // =====================================================================================
  /** @brief Get maximum generations.
   *  @return Maximum number of generations.
   *
   *  Maximum number of generations.
   */
  // -------------------------------------------------------------------------------------
  public int maxgen( ) {
    // -----------------------------------------------------------------------------------
    return p_maxgen;
  }

  
  // =====================================================================================
  /** @brief Get report interval.
   *  @return Number of generations between report.
   *
   *  Number of generations between reports.
   */
  // -------------------------------------------------------------------------------------
  public int report( ) {
    // -----------------------------------------------------------------------------------
    return p_report;
  }

  
  // =====================================================================================
  /** @brief Get save interval.
   *  @return Number of generations between saves.
   *
   *  Number of generations between saves 0=no save.
   */
  // -------------------------------------------------------------------------------------
  public int save( ) {
    // -----------------------------------------------------------------------------------
    return p_save;
  }

  
  // =====================================================================================
  /** @brief Get population size.
   *  @return Number of members in the population.
   *
   *  Number of members in the population.
   */
  // -------------------------------------------------------------------------------------
  public int nPop( ) {
    // -----------------------------------------------------------------------------------
    return p_popSize;
  }

  
  // =====================================================================================
  /** @brief Get tournament size.
   *  @return Number of members examined in a tournament.
   *
   *  Number of members examined in a tournament.
   */
  // -------------------------------------------------------------------------------------
  public int nTour( ) {
    // -----------------------------------------------------------------------------------
    return p_tourSize;
  }

  
  // =====================================================================================
  /** @brief Get bracket probability.
   *  @return Percentage of population bracketed during randomization.
   *
   *  Percentage of population bracketed during randomization.
   */
  // -------------------------------------------------------------------------------------
  public double pBracket( ) {
    // -----------------------------------------------------------------------------------
    return p_pBracket;
  }

  
  // =====================================================================================
  /** @brief Get start crossover probability.
   *  @return Crossover probability at Gen 0.
   *
   *  Probability of Crossover vs. Clone at Gen 0.
   */
  // -------------------------------------------------------------------------------------
  public double pCrossStart( ) {
    // -----------------------------------------------------------------------------------
    return p_pCrossStart;
  }

  
  // =====================================================================================
  /** @brief Get final crossover probability.
   *  @return Crossover probability at Gen Final.
   *
   *  Probability of Crossover vs. Clone at Gen Final.
   */
  // -------------------------------------------------------------------------------------
  public double pCrossFinal( ) {
    // -----------------------------------------------------------------------------------
    return p_pCrossFinal;
  }

  
  // =====================================================================================
  /** @brief Get start mutation probability.
   *  @return Mutation probability at Gen 0.
   *
   *  Probability for an individual allele to mutate at Gen 0.
   */
  // -------------------------------------------------------------------------------------
  public double pMutateStart( ) {
    // -----------------------------------------------------------------------------------
    return p_pMutateStart;
  }

  
  // =====================================================================================
  /** @brief Get final mutation probability.
   *  @return Mutation probability at Gen Final.
   *
   *  Probability for an individual allele to mutate at Gen Final.
   */
  // -------------------------------------------------------------------------------------
  public double pMutateFinal( ) {
    // -----------------------------------------------------------------------------------
    return p_pMutateFinal;
  }

  
  // =====================================================================================
  /** @brief Get start mutation scale.
   *  @return Mutation scale at Gen 0.
   *
   *  Mutation scale at Gen 0.
   */
  // -------------------------------------------------------------------------------------
  public double sMutateStart( ) {
    // -----------------------------------------------------------------------------------
    return p_sMutateStart;
  }

  
  // =====================================================================================
  /** @brief Get final mutation scale.
   *  @return Mutation scale at Gen Final.
   *
   *  Mutation scale at Gen Final.
   */
  // -------------------------------------------------------------------------------------
  public double sMutateFinal( ) {
    // -----------------------------------------------------------------------------------
    return p_sMutateFinal;
  }








  // =====================================================================================
  /** @brief Configure from ConfigDB.Section.
   *  @param sec pointer to a ConfigDB.Section.
   *
   *  Read configuration from a ConfigDB.Section.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration config( ConfigDB.Section sec ) {
    // -----------------------------------------------------------------------------------
    if ( null == sec ) {
      logger.error( "UGA.Builder.config(NULL) - ConfigBD.Section" );
    } else {
      try {

        if ( sec.hasKey( "maxgen" ) ) {
          p_maxgen = StringTool.asInt32( sec.get( "maxgen" ) );
        }

        if ( sec.hasKey( "report" ) ) {
          p_report = StringTool.asInt32( sec.get( "report" ) );
        }

        if ( sec.hasKey( "save" ) ) {
          p_save = StringTool.asInt32( sec.get( "save" ) );
        }

        if ( sec.hasKey( "pop" ) ) {
          p_popSize = StringTool.asInt32( sec.get( "pop" ) );
        }

        if ( sec.hasKey( "tour" ) ) {
          p_tourSize = StringTool.asInt32( sec.get( "tour" ) );
        }

        if ( sec.hasKey( "pcross" ) ) {
           double[] temp = StringTool.asReal8List( sec.get( "pcross" ) );
           if ( 2 == temp.length ) {
            p_pCrossStart = temp[0];
            p_pCrossFinal = temp[1];
           }
        }

        if ( sec.hasKey( "pmutate" ) ) {
           double[] temp = StringTool.asReal8List( sec.get( "pmutate" ) );
           if ( 2 == temp.length ) {
            p_pMutateStart = temp[0];
            p_pMutateFinal = temp[1];
           }
        }

        if ( sec.hasKey( "smutate" ) ) {
           double[] temp = StringTool.asReal8List( sec.get( "smutate" ) );
           if ( 2 == temp.length ) {
            p_sMutateStart = temp[0];
            p_sMutateFinal = temp[1];
           }
        }

        if ( sec.hasKey( "bracket" ) ) {
          p_pBracket = StringTool.asReal8( sec.get( "bracket" ) );
        }

     } catch ( ConfigDB.NoSuchKey e1 ) {
        logger.error( e1.toString() );
      }
    }
    return this;
  }

  
  // =====================================================================================
  /** @brief Configure from ConfigDB.
   *  @param pointer to a ConfigDB.
   *
   *  Read configuration from a ConfigDB.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration config( ConfigDB cdb ) {
    // -----------------------------------------------------------------------------------
    if ( null == cdb ) {
      logger.error( "UGA.Builder.config(NULL) - ConfigBD" );
    } else {
      try {
        return config( cdb.get( "UGA" ) );
      } catch ( ConfigDB.NoSection e2 ) {
        logger.error( e2.toString() );
      }
    }
    return this;
  }

  
  // =====================================================================================
  /** @brief Configure from file.
   *  @param cfg_fspc path to a Configuration INI file.
   *
   *  Read configuration from a Configuration INI file.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration config( String cfg_fspc ) {
    // -----------------------------------------------------------------------------------
    if ( null == cfg_fspc ) {
      logger.error( "UGA.Builder.config(NULL) - fspc" );
    } else {
      logger.debug( "parsing - "+cfg_fspc );
      ConfigDB tmp = new ConfigDB();
      if ( ! tmp.readINI( cfg_fspc ) ) {
        return config( tmp );
      }
    }
    return this;
  }

  
} // end class UGAConfiguration


// =======================================================================================
// **                          U G A C O N F I G U R A T I O N                          **
// ======================================================================== END FILE =====
