// ====================================================================== BEGIN FILE =====
// **                           J T E S T _ R U N S C R I P T                           **
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
 * @file jtest_ric.java
 *  Provides test eval for random integer cycle.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date   2018-05-24
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.*;

// =======================================================================================
public class jtest_runscript {
  // -------------------------------------------------------------------------------------

  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------

    System.out.println( "=========================" );
    RunShell.script( "bin/ScriptTest.sh", "A1 A2 A3", true );
    System.out.println( "-------------------------" );
    RunShell.script( "bin/ScriptTest.sh", "A1 A2 A3" );
    System.out.println( "-------------------------" );
    RunShell.script( "bin/ScriptTest.sh", true );
    System.out.println( "-------------------------" );
    RunShell.script( "bin/ScriptTest.sh" );
    System.out.println( "=========================" );

  }
  
}

// =======================================================================================
// **                           J T E S T _ R U N S C R I P T                           **
// ======================================================================== END FILE =====
