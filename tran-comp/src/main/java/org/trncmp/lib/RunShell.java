// ====================================================================== BEGIN FILE =====
// **                                  R U N S H E L L                                  **
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
 * @brief   Run Shell Commands.
 * @file    RunShell.java
 *
 * @details Provides the interface and procedures to execute shell commands on linux.
 *
 * @author Stephen W. Soliday
 * @date    2018-01-08
 */
// =======================================================================================

package org.trncmp.lib;
import java.io.*;
import java.util.*;
import java.lang.ProcessBuilder.Redirect;
    
// =======================================================================================
public class RunShell {
    // -----------------------------------------------------------------------------------

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static void script( String command, boolean wait_for_shell ) {
	// -------------------------------------------------------------------------------
	try {
	    ProcessBuilder PB = new ProcessBuilder("/bin/bash",
						   "-c",
						   command);
	    File err_log = new File("/tmp/jexec.stderr");
	    File std_log = new File("/tmp/jexec.stdout");
	    PB.redirectError(Redirect.appendTo(err_log));
	    PB.redirectOutput(Redirect.appendTo(std_log));
	    Process p = PB.start();
	    if ( wait_for_shell ) { p.waitFor(); }
	} catch( java.io.IOException e1 ) {
	    System.err.println( e1.toString() );
	} catch( java.lang.InterruptedException e2 ) {
	    System.err.println( e2.toString() );
	}
   }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static void script( String command ) {
	// -------------------------------------------------------------------------------
	script( command, false );
    }
    
}


// =======================================================================================
// **                                  R U N S H E L L                                  **
// =========================================================================== END FILE ==
