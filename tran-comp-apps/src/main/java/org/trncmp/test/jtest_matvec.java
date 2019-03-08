// ====================================================================== BEGIN FILE =====
// **                                J T E S T _ V E C 4                                **
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
 * @file jtest_vec4.java
 *  Provides Unit Test the Vec4 functions.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-09-10
 */
// =======================================================================================

package org.trncmp.test;
import org.trncmp.lib.Dice;

// =======================================================================================
public class jtest_matvec {
    // -----------------------------------------------------------------------------------

    
    // ===================================================================================
    public static void main(String[] args) {
	// -------------------------------------------------------------------------------

	Dice.getInstance().seed_set();
	
	String[] test = { "V2", "V3", "V4" };
	
	jtest_vec2.main( test );
	jtest_vec3.main( test );
	jtest_vec4.main( test );
	
	jtest_mat2.main( test );
	jtest_mat3.main( test );
	jtest_mat4.main( test );

	jtest_linalg.main( test );

    }

}

// =======================================================================================
// **                                J T E S T _ S A G E                                **
// ======================================================================== END FILE =====

