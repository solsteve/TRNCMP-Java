// ====================================================================== BEGIN FILE =====
// **                               U G A _ A P P _ T S P                               **
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
 * @file UGA_App_TSP.java
 * <p>
 * Provides a basic template for executing an ordered Genetic Algorithm using the TSP.
 *
 * @date 2018-07-20
 *
 * ---------------------------------------------------------------------------------------
 *
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 *
 * @author Stephen W. Soliday
 * @date 2015-08-16
 */
// =======================================================================================

package org.trncmp.apps.uga;

import org.trncmp.mllib.ea.Model;
import org.trncmp.mllib.ea.UGA;

import org.trncmp.lib.ConfigDB;
import org.trncmp.lib.AppOptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class UGA_App_TSP {
  // -------------------------------------------------------------------------------------
  static final Logger logger = LogManager.getLogger();
  
  protected ConfigDB         cfg     = null;
  protected ConfigDB.Section aux_sec = null;

  // =====================================================================================
  public UGA_App_TSP( String[] args ) {
    // -----------------------------------------------------------------------------------

    AppOptions.cli_map TSP_CLI[] = {
      //               name    section cfgkey     required default description
      AppOptions.INIT( "city", "TSP",  "tspfile", true,    null,   "path to the TSP data" ),
      AppOptions.INIT( "plot", "TSP",  "plot",    false,   null,   "path to a plot file for the results" ),
      AppOptions.INIT( "opt",  "TSP",  "optpath", false,   null,   "path to the known optimal path" )
    };

    // ----- set up the ConfigDB ---------------------------------------------------------

    AppOptions.init( UGA.DEFAULT_CLI );
    AppOptions.addOptions( TSP_CLI );

    AppOptions.setHelp( "help" );
    AppOptions.setConfigBase( "TSP" );
    AppOptions.setOptConfigFilename( "cfg" );
    AppOptions.setCommandLine( args );

    cfg = AppOptions.getConfigDB();

    // ----- check for an auxillary section in the ConfigDB ------------------------------

    if ( null != cfg ) {
      try {
        if ( cfg.hasSection( "AUX" ) ) {
          aux_sec = cfg.get( "AUX" );
        }
      } catch( ConfigDB.NoSection e ) {
        logger.error( e.toString() );
        System.exit(1);
      }
    } else {
      System.exit(2);
    }

  }

  // =====================================================================================
  public int run() {
    // -----------------------------------------------------------------------------------

    Model model = (Model) new UGA_Model_TSP( cfg );

    UGA uga = UGA.factory( model ).config( cfg ).build();

    // ----- if the auxiliary section exists load a previous population ------------------

    String fspc = null;
    
    if ( null != aux_sec ) {
      try {
        fspc =aux_sec.get( "oldpop" );
      } catch( ConfigDB.NoSuchKey e ) {
        logger.info( "section [AUX] exists but no key \"oldpop\" found" );
      }
    }

    if ( null == fspc ) {
      uga.randomize();
    } else {
      uga.read( fspc );
    }

    // ----- evolve the optimal set of parameters ----------------------------------------

    uga.run();

    // ----- if the auxiliary section exists save the current population -----------------

    fspc = null;
    
    if ( null != aux_sec ) {
      try {
        fspc = aux_sec.get( "newpop" );
      } catch( ConfigDB.NoSuchKey e ) {
        logger.info( "section [AUX] exists but no key \"newpop\" found" );
      }
    }

    if ( null == fspc ) {
      uga.write( fspc );
    }
    
    return 0;
  }


    
  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------

    System.exit( (new UGA_App_TSP( args )).run() );
  }
  
} // end class UGA_App_Real


// =======================================================================================
// **                               U G A _ A P P _ T S P                               **
// ======================================================================== END FILE =====
