// ====================================================================== BEGIN FILE =====
// **                           U G A _ A P P _ I N T E G E R                           **
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
 * @file UGA_App_Integer.java
 * <p>
 * Provides a basic template for executing a genetic algorithm with real value encoding.
 *
 * @date 2018-07-18
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

import org.trncmp.mllib.ea.Model;
import org.trncmp.mllib.ea.UGA;

import org.trncmp.lib.AppOptions;
import org.trncmp.lib.ConfigDB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class UGA_App_Integer {
  // -------------------------------------------------------------------------------------
  static final Logger logger = LogManager.getLogger();
  
  protected ConfigDB         cfg     = null;
  protected ConfigDB.Section aux_sec = null;

  // =====================================================================================
  public UGA_App_Integer( String[] args ) {
    // -----------------------------------------------------------------------------------

    // ----- set up the ConfigDB ---------------------------------------------------------

    AppOptions.init( UGA.DEFAULT_CLI );
    AppOptions.setHelp( "help" );
    AppOptions.setConfigBase( "integer" );
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

    Model model = (Model) new UGA_Model_Integer( cfg );

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
    System.exit( (new UGA_App_Integer( args )).run() );
  }
  
} // end class UGA_App_Integer


// =======================================================================================
// **                           U G A _ A P P _ I N T E G E R                           **
// ======================================================================== END FILE =====
