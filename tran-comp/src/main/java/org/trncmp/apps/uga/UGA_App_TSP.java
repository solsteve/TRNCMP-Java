// ====================================================================== BEGIN FILE =====
// **                               U G A _ A P P _ T S P                               **
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
 * @file UGA_App_TSP.java
 *  Provides a basic template for executing an ordered Genetic Algorithm using the TSP.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2018-07-17
 */
// =======================================================================================

package org.trncmp.apps.uga;

import org.trncmp.mllib.ea.Model;
import org.trncmp.mllib.ea.UGA;

import org.trncmp.lib.ConfigDB;
import org.trncmp.lib.AppOptions;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Level;

// =======================================================================================
public class UGA_App_TSP {
  // -------------------------------------------------------------------------------------
  static final Logger logger = LogManager.getRootLogger();
  
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

    LogManager.getRootLogger().setLevel((Level) Level.INFO);

    System.exit( (new UGA_App_TSP( args )).run() );
  }
  
} // end class UGA_App_Real


// =======================================================================================
// **                               U G A _ A P P _ T S P                               **
// ======================================================================== END FILE =====
