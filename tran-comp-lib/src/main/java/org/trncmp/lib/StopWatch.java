// =======================================================================================
// **                                 S T O P W A T C H                                 **
// ====================================================================== BEGIN FILE =====
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
 * @file ERRNO.java
 *
 *  Provides interface and methods for a precision timer.
 *
 * @author Stephen W. Soliday
 * @date 2017-09-18
 */
// =======================================================================================

package org.trncmp.lib;

// =======================================================================================
public class StopWatch {
    // -----------------------------------------------------------------------------------
    private long start_time;  /**< Start time in clock ticks */

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public StopWatch() {
	// -------------------------------------------------------------------------------
	this.reset();
    }

    // ===================================================================================
    /** @brief Reset.
     *
     *  Start the timmer.
     */
    // -----------------------------------------------------------------------------------
    public  void reset( ) {
	// -------------------------------------------------------------------------------
	start_time = System.nanoTime();
    }

    // ===================================================================================
    /** @brief Elapse.
     *  @return Elapsed time in seconds.
     *
     *  Return the time that lapsed between reset and now
     */
    // -----------------------------------------------------------------------------------
    public double seconds( ) {
	// -------------------------------------------------------------------------------
	return (double)(System.nanoTime() - start_time) * 1.0e-9;
    }

}

// =======================================================================================
// **                                 S T O P W A T C H                                 **
// ======================================================================== END FILE =====
