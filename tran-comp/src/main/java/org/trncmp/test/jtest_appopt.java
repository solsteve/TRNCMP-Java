// ====================================================================== BEGIN FILE =====
// **                                  F I L E N A M E                                  **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2017, Stephen W. Soliday                                           **
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
 * @brief Provides the interface and methods for.
 * @file file.java.hh.cc
 *
 * @author Stephen W. Soliday
 * @date 1992-06-18 Original release.
 * @date 2008-06-18 Major revision.
 * @date 2017-06-18 Migration to the Proxima libraries.
 */
// =======================================================================================

package org.trncmp.test;
import org.trncmp.lib.AppOptions;
import org.trncmp.lib.ConfigDB;
import org.trncmp.lib.StringTool;
import org.apache.log4j.*;

// =======================================================================================
class jtest_appopt {
    // -----------------------------------------------------------------------------------
    private static final Logger logger = LogManager.getRootLogger();

    // ===================================================================================
    public static void main( String[] args ) {
	// -------------------------------------------------------------------------------

	AppOptions.cli_map[] CLI = {
	    //               name                 section cfgkey    req.   default                    description
	    AppOptions.INIT( "pop",               "UGA", "pop",     true,  "100",                     "population size"           ),
	    AppOptions.INIT( "tour",              "UGA", "tour",    true,  null,                      "tournament size"           ),
	    AppOptions.INIT( AppOptions.FILENAME, "UGA", "input",   true,  null,                      "name of the input file"    ),
	    AppOptions.INIT( "pcross",            "UGA", "pcross",  true,  "[ 0.5, 0.9 ]",            "probability of cross over" ),
	    AppOptions.INIT( "maxgen",            "UGA", "maxgen",  false, null,                      "max gen"                   ),
	    AppOptions.INIT( "pmutate",           "UGA", "pmutate", true,  "[ 0.4, 0.08, 0.2, 0.01]", "probability of mutation"   ),
	    AppOptions.INIT( AppOptions.FILENAME, "UGA", "output",  false, null,                      "name of the output file"   ),
	    AppOptions.INIT( "report",            "UGA", "report",  false, null,                      "report interval"           )
	};

	for ( int i=0; i<CLI.length; i++ ) {
	    CLI[i].println();
	}

	AppOptions.init( CLI );
	AppOptions.setConfigBase( "apptest" );
	AppOptions.setOptConfigFilename( "cfg" );
	AppOptions.setHelp( "help" );
	AppOptions.setCommandLine( args );

	logger.error( "-----------------------------------------\n" );
	
	ConfigDB cfg = AppOptions.getConfigDB();

	if ( null == cfg ) { System.exit(1); }

	cfg.writeINI( System.out );

	logger.error( "-----------------------------------------\n" );

	try {
	    ConfigDB.Section sec = cfg.get( "CLI" );
	    String test = sec.get( "aurora" );
	    System.out.println( test + "\n" );

	    float[] list = StringTool.asReal4List( test );

	    if ( null != list ) {
		for ( int i=0; i<list.length; i++ ) {
		    System.out.println( "IDX="+i+" VAL=>>"+list[i]+"<<" );
		}
	    }

	} catch( ConfigDB.NoSuchKey e1 ) {
	} catch( ConfigDB.NoSection e2 ) {
	}

	
	System.exit(0);
    }

}

// =======================================================================================
// **                                  F I L E N A M E                                  **
// ======================================================================== END FILE =====

