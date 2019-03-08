// ====================================================================== BEGIN FILE =====
// **                                 H I S T O G R A M                                 **
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
 * @brief   Histogram.
 * @file    Histogram.java
 *
 * @details Provides the interface and procedures for generating histograms.
 *
 * @author  Stephen W. Soliday
 * @date    2017-09-25
 */
// =======================================================================================

package org.trncmp.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class Histogram {
    // -----------------------------------------------------------------------------------

    protected int      nbin  = 0;
    protected int      nfill = 0;
    protected int[]    bin   = null;
    protected double[] ctr   = null;

    private static final Logger logger = LogManager.getLogger();

    // ===================================================================================
    /** @brief Contructor.
     *  @param centers pointer to an array of bin centers..
     *  @param n number of bins.
     *
     *  Create the histogram bins and thier labled centers.
     */
    // -----------------------------------------------------------------------------------
    public Histogram( int n ) {
	// -------------------------------------------------------------------------------
	double[] temp = new double[n];

	for (int i=0; i<n; i++) {
	    temp[i] = (double)i;
	}

	init( temp );
    }

    
    // ===================================================================================
    /** @brief Contructor.
     *  @param centers pointer to an array of bin centers..
     *  @param n number of bins.
     *
     *  Create the histogram bins and thier labled centers.
     */
    // -----------------------------------------------------------------------------------
    public Histogram( double lower, double upper, int n ) {
	// -------------------------------------------------------------------------------
	double[] temp = new double[n];

	double diff = Math2.Abs(upper - lower) / (double)(n-1);

	temp[0] = Math2.Min( lower, upper );
	for (int i=1; i<n; i++) {
	    temp[i] = temp[i-1] + diff;
	}

	init( temp );
    }

    
    // ===================================================================================
    /** @brief Contructor.
     *  @param centers pointer to an array of bin centers..
     *
     *  Create the histogram bins and thier labled centers.
     */
    // -----------------------------------------------------------------------------------
    public Histogram( double[] centers ) {
	// -------------------------------------------------------------------------------
	init( centers );
    }

    
    // ===================================================================================
    /** @brief Initialize.
     *  @param centers pointer to an array of bin centers..
     *
     *  Create the histogram bins and thier labled centers.
     */
    // -----------------------------------------------------------------------------------
    public void init( double[] centers ) {
	// -------------------------------------------------------------------------------
	nbin  = centers.length;
	bin   = new int[nbin];
	ctr   = Math2.clone( centers );
	reset();
    }

    
    // ===================================================================================
    /** @brief Reset.
     *
     *  Clear all bins.  
     */
    // -----------------------------------------------------------------------------------
    public void reset( ) {
	// -------------------------------------------------------------------------------
	for ( int i=0; i<nbin; i++ ) {
	    bin[i] = 0;
	}
	nfill = 0;
    }

    
    // ===================================================================================
    /** @brief Add Single.
     *  @param x value to add to the histogram.
     */
    // -----------------------------------------------------------------------------------
    public void add( int x ) {
	// -------------------------------------------------------------------------------
	if ( x < nbin ) {
	    bin[ x ] += 1;
	    nfill += 1;
	}
    }

    
    // ===================================================================================
    /** @brief Add Single.
     *  @param x value to add to the histogram.
     */
    // -----------------------------------------------------------------------------------
    public void add( double x ) {
	// -------------------------------------------------------------------------------
	add( map( x ) );
    }

    
    // ===================================================================================
    /** @brief Add Multiple.
     *  @param A pointer to an array of values to add.
     *
     *  Add an array of values to this histogram.
     */
    // -----------------------------------------------------------------------------------
    public void add( int[]  A ) {
	// -------------------------------------------------------------------------------
	int n = A.length;
	for ( int i=0; i<n; i++ ) {
	    add( A[i] );
	}
    }

    
    // ===================================================================================
    /** @brief Add Multiple.
     *  @param A pointer to an array of values to add.
     *  @param n number of values to add.
     *
     *  Add an array of values to this histogram.
     */
    // -----------------------------------------------------------------------------------
    public void add( double[] A ) {
	// -------------------------------------------------------------------------------
	int n = A.length;
	for ( int i=0; i<n; i++ ) {
	    add( A[i] );
	}
    }

    
    // ===================================================================================
    /** @brief Smooth.
     *  @param F pointer to a filter.
     *  @param pass number filter passes.
     *  @return true on error.
     *
     *  Smooth the histogram with a filter.
     */
    // -----------------------------------------------------------------------------------
    public boolean smooth( double[] filter, int pass ) {
	// -------------------------------------------------------------------------------
	int nOrg = filter.length;
	int nF   = nOrg;
	if ( 0 == nF%2 ) {
	    nF -= 1;
	    if ( 0 < nF ) {
		logger.warn( "filter must be odd, was " + nOrg + " now using" + nF );
	    } else {
		logger.error( "filter was unusable" );
		return true;
	    }
	}

	int c = (int)nF/2;

	int f0 = -c;
	int f1 = c+1;

	for ( int p=0; p<pass; p++ ) {
	    for ( int i=0; i<nbin; i++ ) {
		double sum = 0.0e0;
		double cnt = 0.0e0;
		for ( int j=f0; j<f1; j++ ) {
		    int hi=i+j;
		    if (( -1<hi ) && ( hi<nbin )) {
			sum += ((double)bin[hi])*filter[j+c];
			cnt += 1.0;
		    }
		}
		bin[i] = (int)Math.floor((sum / cnt) + 0.5);
	    }
	}
	return false;
    }

    
    // ===================================================================================
    /** @brief Smooth.
     *  @param F pointer to a filter.
     *  @param n number elements in the filter.
     *  @return true on error.
     *
     *  Smooth the histogram with one pass of the filter.
     */
    // -----------------------------------------------------------------------------------
    public boolean smooth( double[] filter ) {
	// -------------------------------------------------------------------------------
	return smooth( filter, 1 );
    }

    
    // ===================================================================================
    /** @brief Max Bin.
     *  @return index of the bin with the maximum count.
     *
     *  Find the bin with the highest count.
     */
    // -----------------------------------------------------------------------------------
    public int max( ) {
	// -------------------------------------------------------------------------------
	int idx = 0;
	for ( int i=1; i<nbin; i++ ) {
	    if ( bin[i] > bin[idx] ) { idx = i; }
	}
	return idx;
    }

    
    // ===================================================================================
    /** @brief Map.
     *  @param x value to map.
     *
     *  Locate the bin that this value belongs to.
     */
    // -----------------------------------------------------------------------------------
    public int map( double x ) {
	// -------------------------------------------------------------------------------
	int  idx   = 0;
	double min_d = Math2.dist2( x, ctr[idx] );
	for ( int i=1; i<nbin; i++ ) {
	    double test = Math2.dist2( x, ctr[i] );
	    if ( test < min_d ) {
		min_d = test;
		idx   = i;
	    }
	} 
	return idx;
    }

    
    // ===================================================================================
    /** @brief Bin Count.
     *  @return number of bins.
     */
    // -----------------------------------------------------------------------------------
    public int bins( ) {
	// -------------------------------------------------------------------------------
	return nbin;
    }

    
    // ===================================================================================
    /** @brief Count.
     *  @return total count all bins.
     */
    // -----------------------------------------------------------------------------------
    public int count( ) {
	// -------------------------------------------------------------------------------
	return nfill;
    }

    
    // ===================================================================================
    /** @brief Count.
     *  @param idx bin index.
     *  @return count in bin idx.
     */
    // -----------------------------------------------------------------------------------
    public int count( int idx ) {
	// -------------------------------------------------------------------------------
	return bin[idx];
    }

    
    // ===================================================================================
    /** @brief Center.
     *  @param idx bin index.
     *  @return center of bin idx.
     */
    // -----------------------------------------------------------------------------------
    public double center( int idx ) {
	// -------------------------------------------------------------------------------
	return ctr[idx];
    }

}

// =======================================================================================
// **                                 H I S T O G R A M                                 **
// ======================================================================== END FILE =====
