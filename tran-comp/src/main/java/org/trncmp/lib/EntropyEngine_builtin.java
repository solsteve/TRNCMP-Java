// ====================================================================== BEGIN FILE =====
// **                     E N T R O P Y E N G I N E _ B U I L T I N                     **
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
 * @file EntropyEngine_builtin.java
 *  Provides Source of random numbers.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-08-24
 */
// =======================================================================================

package org.trncmp.lib;

import java.util.Random;

// =======================================================================================
class EntropyEngine_builtin implements EntropyEngine {
    // -----------------------------------------------------------------------------------

    private Random builtin = null;
    
    // ===================================================================================
    public EntropyEngine_builtin() {
	// -------------------------------------------------------------------------------
	builtin = new Random();
    }

    // ===================================================================================
    public void reset() {
	// -------------------------------------------------------------------------------
    }

    // ===================================================================================
    public int seed_size() {
	// -------------------------------------------------------------------------------
	return 8;
    }

    // ===================================================================================
    public void seed_set( byte[] sm, int n ) {
	// -------------------------------------------------------------------------------
	byte[] sdat = { 0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};

	int k = 8;
	if ( n > 8 ) { k = n; }

	for ( int i=0; i<k; i++ ) {
	    sdat[i % 8] = (byte) ((short)sdat[i % 8] ^ (short)sm[i % n]);
	}

	java.nio.ByteBuffer buf = java.nio.ByteBuffer.wrap(sdat);

	builtin.setSeed( buf.getLong(0) );
    }

    // ===================================================================================
    public String name() {
	// -------------------------------------------------------------------------------
	return "builtin";
    }

    // ===================================================================================
    public long max_integer() {
	// -------------------------------------------------------------------------------
	return 4294967295L;
    }

    // ===================================================================================
    /** @brief Uniform Random Number.
     *  @return 31-bit unsigned value.
     *
     *  Return the next 31-bits of entropy. 0 <= x < 2,147,483,647              ( 2^31-1 )
     */
    // -----------------------------------------------------------------------------------
    public long get_integer() {
	// -------------------------------------------------------------------------------
	return builtin.nextLong();
    }

    // ===================================================================================
    /** @brief Uniform Random Number.
     *  @return 23-bit single precision.
     *
     *  Return the next 31-bits of entropy as a double.        0 <= x < 1
     */
    // -----------------------------------------------------------------------------------
    public double get_real() {
	// -------------------------------------------------------------------------------
	return builtin.nextDouble();
    }
    
}

// =======================================================================================
// **                     E N T R O P Y E N G I N E _ B U I L T I N                     **
// ======================================================================== END FILE =====

