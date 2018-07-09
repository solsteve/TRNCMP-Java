// ====================================================================== BEGIN FILE =====
// **                             J T E S T _ C O U N T E R                             **
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
 * @file jtest_counter.java
 *  Provides Unit Test the Counter class.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-09-10
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.Counter;

// =======================================================================================
public class jtest_counter {
    // -----------------------------------------------------------------------------------

    // ===================================================================================
    public static void main(String[] args) {
	// -------------------------------------------------------------------------------

  Counter C = new Counter( 2, 4 );

  C.reset();

   do {
       System.out.println( C.toString() );
   } while( C.inc() );

    }

}

// =======================================================================================
// **                             J T E S T _ C O U N T E R                             **
// ======================================================================== END FILE =====

