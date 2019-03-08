// ====================================================================== BEGIN FILE =====
// **                          U G A C O N F I G U R A T I O N                          **
// ====================================================================== BEGIN FILE =====
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
 * <p>
 * Provides interface and methods for a builder/configuration class for UGA.
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
import org.trncmp.lib.Dice;
import org.trncmp.lib.StringTool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class UGAConfiguration {
  // -------------------------------------------------------------------------------------
  static final Logger logger = LogManager.getLogger();

  static final int DEF_NCPU = 4;

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

  /** Maximum number of concurrent threads that are available */
  private int p_nCPU = 4;

  private Model model = null;

  // =====================================================================================
  /** @brief Void constructor.
   *  @param mod pointer to a user model.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration( Model mod ) {
    // -----------------------------------------------------------------------------------
    model = mod;
  }


  // =====================================================================================
  /** @brief Final build procedure.
   *  @return pointer to a new UGA object.
   */
  // -------------------------------------------------------------------------------------
  public UGA build() {
    // -----------------------------------------------------------------------------------
    return new UGA( this, model );
  }








  // =====================================================================================
  /** @brief Set full report flag.
   *  @param f flag.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Display an abbreviated report if users model supports it.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration fullReport( boolean f ) {
    // -----------------------------------------------------------------------------------
    p_fullReport = f;
    return this;
  }

  
  // =====================================================================================
  /** @brief Set maximum generations.
   *  @param n maximum number of generations.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Maximum number of generations.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration maxgen( int n ) {
    // -----------------------------------------------------------------------------------

    if ( 1 > n ) {
      logger.error( "maxgen=n ; n must be greater than 0" );
      System.exit(1);
    }
    
    p_maxgen = n;
    
    return this;
  }

  
  // =====================================================================================
  /** @brief Set report interval.
   *  @param n report interval.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Number of generations between reports.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration report( int n ) {
    // -----------------------------------------------------------------------------------

    if ( 1 > n ) {
      logger.error( "report=n ; n must be greater than 0" );
      System.exit(1);
    }

    p_report = n;
     
    return this;
  }

  
  // =====================================================================================
  /** @brief Set save interval.
   *  @return save interval.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Number of generations between saves 0=no save.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration save( int n ) {
    // -----------------------------------------------------------------------------------

    if ( 0 > n ) {
      logger.error( "save=n ; n may not be negative" );
      System.exit(1);
    }

    p_save = n;

    return this;
  }

  
  // =====================================================================================
  /** @brief Set population size.
   *  @param n population size.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Number of members in the population.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration nPop( int n ) {
    // -----------------------------------------------------------------------------------

    if ( 1 > n ) {
      logger.error( "pop=n ; n must be greater than 0" );
      System.exit(1);
    }

    p_popSize = n;

    return this;
  }

  
  // =====================================================================================
  /** @brief Set tournament size.
   *  @param n tournament size.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Number of members examined in a tournament.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration nTour( int n ) {
    // -----------------------------------------------------------------------------------

    if ( 1 > n ) {
      logger.error( "tour=n ; n must be greater than 0" );
      System.exit(1);
    }

    p_tourSize = n;

    return this;
  }

  
  // =====================================================================================
  /** @brief Set bracket probability.
   *  @param p probability that population member will be bracketed instead of randomized.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Percentage of population bracketed during randomization.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration pBracket( double p ) {
    // -----------------------------------------------------------------------------------

    if ( 0.0e0 > p ) {
      logger.error( "bracket=p ; p must be greater than or equal to 0.0" );
      System.exit(1);
    }

    if ( 1.0e0 < p ) {
      logger.error( "bracket=p ; p must be less than or equal to 1.0" );
      System.exit(1);
    }

    p_pBracket = p;
    
    return this;
  }

  
  // =====================================================================================
  /** @brief Set crossover probability.
   *  @param po probability at generation 0
   *  @param pf probability at genreation maxgen.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Probability of Crossover vs. Clone.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration pCross( double po, double pf ) {
    // -----------------------------------------------------------------------------------

    if ( 0.0e0 > po ) {
      logger.error( "pcross=[po, pf] ; po must be greater than or equal to 0.0" );
      System.exit(1);
    }

    if ( 1.0e0 < po ) {
      logger.error( "pcross=[po, pf] ; po must be less than or equal to 1.0" );
      System.exit(1);
    }

    if ( 0.0e0 > pf ) {
      logger.error( "pcross=[po, pf] ; pf must be greater than or equal to 0.0" );
      System.exit(1);
    }

    if ( 1.0e0 < pf ) {
      logger.error( "pcross=[po, pf] ; pf must be less than or equal to 1.0" );
      System.exit(1);
    }

    p_pCrossStart = po;
    p_pCrossFinal = pf;
    
    return this;
  }

  
  // =====================================================================================
  /** @brief Set start mutation probability.
   *  @param po probability at generation 0
   *  @param pf probability at genreation maxgen.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Probability for an individual allele to mutate.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration pMutate( double po, double pf ) {
    // -----------------------------------------------------------------------------------

    if ( 0.0e0 > po ) {
      logger.error( "pmutate=[po, pf] ; po must be greater than or equal to 0.0" );
      System.exit(1);
    }

    if ( 1.0e0 < po ) {
      logger.error( "pmutate=[po, pf] ; po must be less than or equal to 1.0" );
      System.exit(1);
    }

    if ( 0.0e0 > pf ) {
      logger.error( "pmutate=[po, pf] ; pf must be greater than or equal to 0.0" );
      System.exit(1);
    }

    if ( 1.0e0 < pf ) {
      logger.error( "pmutate=[po, pf] ; pf must be less than or equal to 1.0" );
      System.exit(1);
    }

    p_pMutateStart = po;
    p_pMutateFinal = pf;
    
    return this;
  }

  
  // =====================================================================================
  /** @brief Set start mutation scale.
   *  @param so scale at generation 0
   *  @param sf scale at genreation maxgen.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Mutation scale.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration sMutate( double so, double sf ) {
    // -----------------------------------------------------------------------------------

    if ( 0.0e0 > so ) {
      logger.error( "smutate=[so, sf] ; so must be greater than or equal to 0.0" );
      System.exit(1);
    }

    if ( 1.0e0 < so ) {
      logger.error( "smutate=[so, sf] ; so must be less than or equal to 1.0" );
      System.exit(1);
    }

    if ( 0.0e0 > sf ) {
      logger.error( "smutate=[po, sf] ; sf must be greater than or equal to 0.0" );
      System.exit(1);
    }

    if ( 1.0e0 < sf ) {
      logger.error( "smutate=[so, sf] ; sf must be less than or equal to 1.0" );
      System.exit(1);
    }

    p_sMutateStart = so;
    p_sMutateFinal = sf;

    return this;
  }


  // =====================================================================================
  /** @brief Set maximum number of concurrent threads.
   *  @param n maximum number of concurrent threads.
   *  @return Pointer to this UGAConfiguration object.
   *
   *  Maximum number of concurrent threads.
   */
  // -------------------------------------------------------------------------------------
  public UGAConfiguration nCPU( int n ) {
    // -----------------------------------------------------------------------------------
    p_nCPU = n;

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
  /** @brief Get maximum number of concurrent threads.
   *  @return maximum number of concurrent threads.
   *
   *  Maximum number of concurrent threads.
   */
  // -------------------------------------------------------------------------------------
  public int nCPU() {
    // -----------------------------------------------------------------------------------
    return p_nCPU;
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

        // -------------------------------------------------------------------------------
        if ( sec.hasKey( "maxgen" ) ) {
          try {
            maxgen( StringTool.asInt32( sec.get( "maxgen" ) ) );
          } catch (java.lang.NumberFormatException e ) {
            logger.error( "maxgen=integer ; "+e.toString() );
            System.exit(2);
          }
        }

        // -------------------------------------------------------------------------------
        if ( sec.hasKey( "report" ) ) {
          try {
            report( StringTool.asInt32( sec.get( "report" ) ) );
           } catch (java.lang.NumberFormatException e ) {
            logger.error( "report=integer ; "+e.toString() );
            System.exit(2);
          }
       }

        // -------------------------------------------------------------------------------
        if ( sec.hasKey( "save" ) ) {
          try {
            save( StringTool.asInt32( sec.get( "save" ) ) );
           } catch (java.lang.NumberFormatException e ) {
            logger.error( "save=integer ; "+e.toString() );
            System.exit(2);
          }
       }

        // -------------------------------------------------------------------------------
        if ( sec.hasKey( "pop" ) ) {
          try {
            nPop( StringTool.asInt32( sec.get( "pop" ) ) );
          } catch (java.lang.NumberFormatException e ) {
            logger.error( "pop=integer ; "+e.toString() );
            System.exit(2);
          }
        }

        // -------------------------------------------------------------------------------
        if ( sec.hasKey( "tour" ) ) {
           try {
             nTour( StringTool.asInt32( sec.get( "tour" ) ) );
          } catch (java.lang.NumberFormatException e ) {
            logger.error( "tour=integer ; "+e.toString() );
            System.exit(2);
          }
        }

        // -------------------------------------------------------------------------------
        if ( sec.hasKey( "pcross" ) ) {
          String   params = sec.get( "pcross" );
          String[] list   = StringTool.asStringList( params );

          try {
            switch ( list.length ) {
              case 1:
                double p = StringTool.asReal8( params );
                pCross( p, p );
                break;
              case 2:
                double po = StringTool.asReal8( list[0] );
                double pf = StringTool.asReal8( list[1] );
                pCross( po, pf );
                break;
              default:
                logger.error( "pcross=p || [po,pf] ; must be a scalar or 2-tupple" );
            }
          } catch (java.lang.NumberFormatException e ) {
            logger.error( "pcross=[real,real] ; "+e.toString() );
            System.exit(2);
          }
        }

        // -------------------------------------------------------------------------------
        if ( sec.hasKey( "pmutate" ) ) {
          String   params = sec.get( "pmutate" );
          String[] list   = StringTool.asStringList( params );

          try {
            switch ( list.length ) {
              case 1:
                double p = StringTool.asReal8( params );
                pMutate( p, p );
                break;
              case 2:
                double po = StringTool.asReal8( list[0] );
                double pf = StringTool.asReal8( list[1] );
                pMutate( po, pf );
                break;
              default:
                logger.error( "pmutate=p || [po,pf] ; must be a scalar or 2-tupple" );
            }
          } catch (java.lang.NumberFormatException e ) {
            logger.error( "pmutate=[real,real] ; "+e.toString() );
            System.exit(2);
          }
        }

        // -------------------------------------------------------------------------------
        if ( sec.hasKey( "smutate" ) ) {
          String   params = sec.get( "smutate" );
          String[] list   = StringTool.asStringList( params );

          try {
            switch ( list.length ) {
              case 1:
                double p = StringTool.asReal8( params );
                sMutate( p, p );
                break;
              case 2:
                double po = StringTool.asReal8( list[0] );
                double pf = StringTool.asReal8( list[1] );
                sMutate( po, pf );
                break;
              default:
                logger.error( "smutate=p || [po,pf] ; must be a scalar or 2-tupple" );
            }
          } catch (java.lang.NumberFormatException e ) {
            logger.error( "smutate=[real,real] ; "+e.toString() );
            System.exit(2);
          }
        }

        // -------------------------------------------------------------------------------
        if ( sec.hasKey( "bracket" ) ) {
          try {
            pBracket( StringTool.asReal8( sec.get( "bracket" ) ) );
          } catch (java.lang.NumberFormatException e ) {
            logger.error( " bracket=integer ; "+e.toString() );
            System.exit(2);
          }
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
        int cn = DEF_NCPU;
        if ( cdb.hasSection( "MP" ) ) {
          try {
            cn = StringTool.asInt32( cdb.get( "MP", "cpu" ) );
          } catch( ConfigDB.NoSuchKey e1 ) {
            logger.warn( "[MP] section found but no kye cpu=, using default cpu="+cn );
          }
        } else {
          logger.debug( "Using default MP.cpu="+cn );
        }
        return config( cdb.get( "UGA" ) ).nCPU( cn );
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
