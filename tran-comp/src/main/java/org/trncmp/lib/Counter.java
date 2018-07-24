// ====================================================================== BEGIN FILE =====
// **                                   C O U N T E R                                   **
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
 * @brief Provides the interface and methods for an arbitrary radix counter.
 * @file  Counter.hh
 *
 * @author Stephen W. Soliday
 * @date 2017-09-12
 */
// =======================================================================================

package org.trncmp.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class Counter {
    // -----------------------------------------------------------------------------------

    private static final Logger logger = LogManager.getLogger();

    protected int[] buffer         = null; /**< Buffer to hold digits.                  */
    protected int   counting_base  = 0;    /**< base of radix of this counter.          */
    protected int   numberofdigits = 0;    /**< number of digits used by this counter.  */

    // ===================================================================================
    /** @brief New.
     *  @param b base
     *  @param n number of digits.
     *
     *   Allocate storage for the counter's structure.
     */
    // -----------------------------------------------------------------------------------
    public Counter( int b, int n ) {
	// -------------------------------------------------------------------------------
	this.init(b, n);
    }

    
    // ===================================================================================
    /** @brief Clone.
     *  @param reference to as source counter.
     *
     *  Clone a counter.
     */
    // -----------------------------------------------------------------------------------
    public Counter( Counter src ) {
	// -------------------------------------------------------------------------------
	this.init(src.counting_base, src.numberofdigits);
	this.copy( src );
    }

    
    // ===================================================================================
    /** @brief Initialize.
     *  @param b base
     *  @param n number of digits.
     *
     *   Allocate storage for the counter's structure.
     */
    // -----------------------------------------------------------------------------------
    public void init( int b, int n ) {
	// -------------------------------------------------------------------------------
	buffer         = new int[n];
	counting_base  = b;
	numberofdigits = n;
	reset();
    }

    
    // ===================================================================================
    /** @brief Destroy.
     *
     *  Free the allcoated space for the counter. This function will zero out the memory.
     */
    // -----------------------------------------------------------------------------------
    public void destroy( ) {
	// -------------------------------------------------------------------------------
	if ( null != buffer ) {
	    reset();
	}
	buffer         = null;
	counting_base  = 0;
	numberofdigits = 0;
    }

    // ===================================================================================
    /** @brief Reset.
     *
     *   Set the digits of the counter to zero.
     */
    // -----------------------------------------------------------------------------------
    public void reset( ) {
	// -------------------------------------------------------------------------------
	if ( null != buffer ) {
	    for (int i=0; i<numberofdigits; i++) {
		buffer[i] = 0;
	    }
	}
    }

    
   
    // ===================================================================================
    /** @brief Value.
     *  @param idx index of a digit to retrive.
     *  @return decimal(10) representation of this digit.
     */
    // -----------------------------------------------------------------------------------
    public int value( int idx ) {
	// -------------------------------------------------------------------------------
	return buffer[idx]; 
    }

    
    // ===================================================================================
    /** @brief Base.
     *  return decimal(10) representation of the base(radix) of this counter.
     */
    // -----------------------------------------------------------------------------------
    public int base( ) {
	// -------------------------------------------------------------------------------
	return counting_base;
    }

    
    // ===================================================================================
    /** @brief Digits.
     *  @return number of digits represented by this counter.
     */
    // -----------------------------------------------------------------------------------
    public int N( ) {
	// -------------------------------------------------------------------------------
	return numberofdigits;
    }

    
    // ===================================================================================
    /** @brief Decimal.
     *  @return a decimal representation of this counter.
     */
    // -----------------------------------------------------------------------------------
    public int decimal( ) {
	// -------------------------------------------------------------------------------
	int S = 0;
	int M = 1;
	for (int i=0; i<numberofdigits; i++ ) {
	    S += buffer[i] * M;
	    M *= counting_base;
	}
	return S;
    }

    
    // ===================================================================================
    /** @brief Space.
     *  @return the maximum number of symbols that can be represented by this counter.
     */
    // -----------------------------------------------------------------------------------
    public int space( ) {
	// -------------------------------------------------------------------------------
	int S = 1;
	for (int i=0; i<numberofdigits; i++ ) {
	    S *= counting_base;
	}
	return S;
    }

    
    // ===================================================================================
    /** @brief To String.
     *  @return a string representation of this counter.
     */
    // -----------------------------------------------------------------------------------
    public String toString( ) {
	// -------------------------------------------------------------------------------
	StringBuffer temp = new StringBuffer();

	for (int i=numberofdigits-1; i>0; i--) {
	    temp.append( String.format( "%d ", buffer[i] ) );
	}
	temp.append( String.format( "%d", buffer[0] ) );

	return temp.toString();
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public void copy( Counter that ) {
	// -------------------------------------------------------------------------------
	if ( this.numberofdigits != that.numberofdigits ) {
	    logger.error( "Counters have different number of digits" );
	} else {
	    this.counting_base = that.counting_base;
	    for (int i=0; i<this.numberofdigits; i++) {
		this.buffer[i] = that.buffer[i];
	    }
	}
    }

    
    // ===================================================================================
    /** @brief Load.
     *  @param src pointer to a source of digits.
     *  @param offset starting index for the source.
     *  @return pointer to next unused digit.
     */
    // -----------------------------------------------------------------------------------
    public int load( int[] src, int offset ) {
	// -------------------------------------------------------------------------------
	int idx = offset;

	for (int i=0; i<numberofdigits; i++ ) {
	    buffer[i] = src[idx];
	    idx += 1;
	}

	return idx;
    }

    
    // ===================================================================================
    /** @brief Load.
     *  @param src pointer to a source of digits.
     *  @return pointer to next unused digit.
     */
    // -----------------------------------------------------------------------------------
    public int load( int[] src ) {
	// -------------------------------------------------------------------------------
	return load( src, 0 );
    }

    
    // ===================================================================================
    /** @brief Store.
     *  @param src pointer to a destination array of digits.
     *  @param offset starting index for the destination.
     *  @return pointer to next unused digit.
     */
    // -----------------------------------------------------------------------------------
    public int store( int[] dst, int offset ) {
	// -------------------------------------------------------------------------------
	int idx = offset;

	for (int i=0; i<numberofdigits; i++ ) {
	    dst[idx] = buffer[i];
	    idx += 1;
	}

	return idx;
    }

    
    // ===================================================================================
    /** @brief Store.
     *  @param src pointer to a destination array of digits.
     *  @return pointer to next unused digit.
     */
    // -----------------------------------------------------------------------------------
    public int store( int[] dst ) {
	// -------------------------------------------------------------------------------
	return store( dst, 0 );
    }

    
    // ===================================================================================
    /** @brief Increment.
     *  @param d index of a digit to increment.
     *  @return 0=rolled over, 1=less than max value
     *
     *   Increment a specific digit.
     */
    // -----------------------------------------------------------------------------------
    public boolean inc( int d ) {
	// -------------------------------------------------------------------------------
	if (d == numberofdigits) {
	    reset();
	    return false;
	}

	buffer[d] += 1;
	if (buffer[d] >= counting_base) {
	    buffer[d] = 0;
	    return inc( d+1 );
	}

	return true;
    }

    
    // ===================================================================================
    /** @brief Increment.
     *  @return 0=rolled over, 1=less than max value
     *
     *   Increment the digits.
     */
    // -----------------------------------------------------------------------------------
    public boolean inc( ) {
	// -------------------------------------------------------------------------------
	return inc(0);
    }

}
    

// =======================================================================================
// **                                   C O U N T E R                                   **
// ======================================================================== END FILE =====
