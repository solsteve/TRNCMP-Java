// ====================================================================== BEGIN FILE =====
// **                  E N T R O P Y E N G I N E _ S I M P L E _ L C G                  **
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
 * @file EntropyEngine_simple_lcg.java
 *  Provides Source of random numbers.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-08-24
 */
// =======================================================================================

package org.trncmp.lib;

// =======================================================================================
class EntropyEngine_simple_lcg implements EntropyEngine {
    
    // -----------------------------------------------------------------------------------
    public static final byte   MAX7  = 127;
    public static final short  MAX15 = 32767;
    public static final long   MAX31 = 2147483647;
    public static final long   MAX62 = 4611686018427387903L;

    public static final double D_MAX7  = (double) MAX7;
    public static final double D_MAX15 = (double) MAX15;
    public static final double D_MAX31 = (double) MAX31;
    public static final double D_MAX62 = (double) MAX62;

    public static final float  F_MAX7  = (float)  MAX7;
    public static final float  F_MAX15 = (float)  MAX15;
    public static final float  F_MAX31 = (float)  MAX31;
    public static final float  F_MAX62 = (float)  MAX62;

    private final long MULTIPLIER = 1103515245L; // gclib
    private final long INCREMENT  = 12345L;      // gclib
    private final long MODULUS    = 2147483647L; // gclib
    private       long state      = 3141592653L; // gclib

    private final Object lockA  = new Object();
    private final Object lock31 = new Object();

    // ===================================================================================
    public EntropyEngine_simple_lcg() {
	// -------------------------------------------------------------------------------
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

	synchronized( lockA ) {
	    state = buf.getLong(0);
	}
    }

    // ===================================================================================
    public String name() {
	// -------------------------------------------------------------------------------
	return "Simple LCG";
    }

    // ===================================================================================
    public long max_integer() {
	// -------------------------------------------------------------------------------
	return MAX31;
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
	long temp = 0;
	synchronized( lock31 ) {
	    state = ((MULTIPLIER * state) + INCREMENT) % MODULUS;
	    temp  = ( state & 0x000000007FFFFFFFL );
	}
	return temp;
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
	return (double) get_integer() / D_MAX31; 
    }
    
}

// =======================================================================================
// **                  E N T R O P Y E N G I N E _ S I M P L E _ L C G                  **
// ======================================================================== END FILE =====

