// ====================================================================== BEGIN FILE =====
// **                          J T E S T _ S T A T I S T I C S                          **
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
 * @brief   Statistics.
 * @file    jtest_statistics.java
 *
 * @details Provides the interface and procedures for testing the Statistics class.
 *
 * @author  Stephen W. Soliday
 * @date    2015-01-26 Original release.
 * @date    2017-09-28 Migration to the Proxima libraries.
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.*;

// =======================================================================================
public class jtest_statistics {
    // -----------------------------------------------------------------------------------

    public static final int   SAMPLES = 10000;
    public static final int   FIELDS  = 5;

    public static final double[] TEST_MEAN1 = { 1.2, -3.4, 2.7, -4.9,  2.5 };
    public static final double[] TEST_MEAN2 = {-4.1,  5.3, 6.8,  1.7, -8.3 };

    public static final double[] TEST_STDV1 = { 0.2,  0.4, 0.6,  0.8,  1.0 };
    public static final double[] TEST_STDV2 = { 0.3,  0.5, 0.7,  0.9, -1.1 };

    // ===================================================================================
    public static void Test01( Dice dd ) {
	// -------------------------------------------------------------------------------

	double TEST_MINV = Math2.N_MAX_POS;
	double TEST_MAXV = Math2.N_MAX_NEG;
  
	Statistics.single stat = new Statistics.single();

	double[] data = new double[SAMPLES];

	for ( int i=0; i<SAMPLES; i++ ) {
	    double x = TEST_MEAN1[0] + ( TEST_STDV1[0] * dd.normal() );
	    data[i]   = x;
	    if ( x < TEST_MINV ) { TEST_MINV = x; }
	    if ( x > TEST_MAXV ) { TEST_MAXV = x; }
	}

	stat.compile( data, SAMPLES );
	stat.extra(   data, SAMPLES );

	stat.report( System.out, "%8.3f" );

	System.out.format( "Mean:      expected: %8.3f got: %8.3f\n", TEST_MEAN1[0], stat.mean );
	System.out.format( "Std. Dev:  expected: %8.3f got: %8.3f\n", TEST_STDV1[0], stat.std  );
	System.out.format( "Min Value: expected: %8.3f got: %8.3f\n", TEST_MINV,     stat.minv );
	System.out.format( "Max Value: expected: %8.3f got: %8.3f\n", TEST_MAXV,     stat.maxv );
    }

     // ===================================================================================
    public static void Test02( Dice dd ) {
	// -------------------------------------------------------------------------------

	double TEST_MINV1 = Math2.N_MAX_POS;
	double TEST_MAXV1 = Math2.N_MAX_NEG;
  
	double TEST_MINV2 = Math2.N_MAX_POS;
	double TEST_MAXV2 = Math2.N_MAX_NEG;
  
	Statistics.single stat1 = new Statistics.single();
	Statistics.single stat2 = new Statistics.single();

	double[] data = new double[SAMPLES];
	int[]    idx  = new int[SAMPLES];

	for ( int i=0; i<SAMPLES; i+=2 ) {
	    double x = TEST_MEAN1[0] + ( TEST_STDV1[0] * dd.normal() );
	    data[i]   = x;
	    idx[i]    = 1;
	    if ( x < TEST_MINV1 ) { TEST_MINV1 = x; }
	    if ( x > TEST_MAXV1 ) { TEST_MAXV1 = x; }

	     x = TEST_MEAN2[0] + ( TEST_STDV2[0] * dd.normal() );
	    data[i+1]   = x;
	    idx[i+1]    = 2;
	    if ( x < TEST_MINV2 ) { TEST_MINV2 = x; }
	    if ( x > TEST_MAXV2 ) { TEST_MAXV2 = x; }
	}

	stat1.compile( idx, 1, data, SAMPLES );
	stat1.extra(   idx, 1, data, SAMPLES );

	stat2.compile( idx, 2, data, SAMPLES );
	stat2.extra(   idx, 2, data, SAMPLES );
	
	System.out.format( "\n" );
	stat1.report( System.out, "%8.3f" );
	System.out.format( "\n" );

	stat2.report( System.out, "%8.3f" );
	System.out.format( "\n" );

	System.out.format( "1 Mean:      expected: %8.3f got: %8.3f\n", TEST_MEAN1[0], stat1.mean );
	System.out.format( "1 Std. Dev:  expected: %8.3f got: %8.3f\n", TEST_STDV1[0], stat1.std  );
	System.out.format( "1 Min Value: expected: %8.3f got: %8.3f\n", TEST_MINV1,    stat1.minv );
	System.out.format( "1 Max Value: expected: %8.3f got: %8.3f\n", TEST_MAXV1,    stat1.maxv );
	System.out.format( "\n" );

	System.out.format( "2 Mean:      expected: %8.3f got: %8.3f\n", TEST_MEAN2[0], stat2.mean );
	System.out.format( "2 Std. Dev:  expected: %8.3f got: %8.3f\n", TEST_STDV2[0], stat2.std  );
	System.out.format( "2 Min Value: expected: %8.3f got: %8.3f\n", TEST_MINV2,    stat2.minv );
	System.out.format( "2 Max Value: expected: %8.3f got: %8.3f\n", TEST_MAXV2,    stat2.maxv );
	System.out.format( "\n" );
    }

   // ===================================================================================
    public static void Test03( Dice dd ) {
	// -------------------------------------------------------------------------------

	double[] TEST_MINV = new double[FIELDS];
	double[] TEST_MAXV = new double[FIELDS];
	
	for ( int j=0; j<FIELDS; j++ ) {
	    TEST_MINV[j] = Math2.N_MAX_POS;
	    TEST_MAXV[j] = Math2.N_MAX_NEG;
	}
  
	Statistics.multi stat = new Statistics.multi( FIELDS );

	double[][] data = new double[SAMPLES][FIELDS];

	for ( int i=0; i<SAMPLES; i++ ) {
	    for ( int j=0; j<FIELDS; j++ ) {
		double x = TEST_MEAN1[j] + ( TEST_STDV1[j] * dd.normal() );
		data[i][j]   = x;
		if ( x < TEST_MINV[j] ) { TEST_MINV[j] = x; }
		if ( x > TEST_MAXV[j] ) { TEST_MAXV[j] = x; }
	    }
	}

	stat.compile( data, SAMPLES, FIELDS );
	stat.extra(   data, SAMPLES, FIELDS );

	stat.report( System.out, "%8.3f" );
	System.out.format( "\n" );

	for ( int j=0; j<FIELDS; j++ ) {
	    System.out.format( "%d Mean:      expected: %8.3f got: %8.3f\n",
			       j, TEST_MEAN1[j], stat.mean[j] );
	    System.out.format( "%d Std. Dev:  expected: %8.3f got: %8.3f\n",
			       j, TEST_STDV1[j], stat.std[j]  );
	    System.out.format( "%d Min Value: expected: %8.3f got: %8.3f\n",
			       j, TEST_MINV[j],     stat.minv[j] );
	    System.out.format( "%d Max Value: expected: %8.3f got: %8.3f\n\n",
			       j, TEST_MAXV[j],     stat.maxv[j] );
	}
    }

    // ===================================================================================
    public static void main(String[] args) {
	// -------------------------------------------------------------------------------

	Dice dd = Dice.getInstance();

	dd.seed_set();
	
	System.out.format( "\n----- test 1 --------------------------------------\n\n" );
	Test01( dd );

	System.out.format( "\n----- test 2 --------------------------------------\n\n" );
	Test02( dd );

	System.out.format( "\n----- test 3 --------------------------------------\n\n" );
	Test03( dd );

    }

}

// =======================================================================================
// **                          J T E S T _ S T A T I S T I C S                          **
// ======================================================================== END FILE =====

