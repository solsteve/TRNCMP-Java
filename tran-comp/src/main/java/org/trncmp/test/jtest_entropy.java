// ====================================================================== BEGIN FILE =====
// **                             J T E S T _ E N T R O P Y                             **
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
 * @file jtest_entropy.java
 *  Provides Unit Test the Entropy class.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-08-25
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.*;

// =======================================================================================
public class jtest_entropy {
    // -----------------------------------------------------------------------------------

    // ===================================================================================
    static double test_integer( long samples, Entropy ent ) {
	// -------------------------------------------------------------------------------	
	long mean = ent.get_integer();
	long minV = mean;
	long maxV = mean;
  
	for ( long i=1; i<samples; i++ ) {
	    long x = (long)ent.get_integer();
	    mean += x;
	    if ( x < minV ) { minV = x; }
	    if ( x > maxV ) { maxV = x; }
	}

	mean /= samples;

	long maxI = (long) ent.max_integer();

	long expected_mean = maxI / 2;
	long expected_var  = ((maxI+1)*(maxI+1) - 1) / 12;

	long var = 0;

	//StopWatch timer;

	//timer.reset();

	for ( long i=0; i<samples; i++ ) {
	    long d = ent.get_integer() - mean;
	    var += (d*d);
	}

	var /= (long) (samples - 1);

	//double elapsed = timer.seconds()
	double elapsed = 2.3;
	double rate    = ((double) samples) / elapsed;
  
	System.out.format( "----- get_integer() [ %s ] -----------------------\n", ent.name() );
	System.out.format( "minv = %016X expected = %016X\n", minV, 0 );
	System.out.format( "maxv = %016X expected = %016X\n", maxV, maxI );
	System.out.format( "mean = %016X expected = %016X\n", mean, expected_mean );
	System.out.format( "var  = %016X expected = %016X\n", var,  expected_var  );
	System.out.format( "rate =   %11.4e rolls / second\n\n", rate );
  
	return rate;
    }

    // ===================================================================================
    static double test_real( long samples, Entropy ent ) {
	// -------------------------------------------------------------------------------	
	double mean = ent.get_real();
	double minV = mean;
	double maxV = mean;
  
	for ( long i=1; i<samples; i++ ) {
	    double x = ent.get_real();
	    mean += x;
	    if ( x < minV ) { minV = x; }
	    if ( x > maxV ) { maxV = x; }
	}

	mean /= (double) samples;

	double expected_mean = 5.0e-1;
	double expected_var  = 1.0e0 / 1.2e1;

	double var = 0.0e0;

	//StopWatch timer;

	//timer.reset();

	for ( long i=0; i<samples; i++ ) {
	    double d = ent.get_real() - mean;
	    var += (d*d);
	}

	var /= (double) (samples - 1);

	//double elapsed = timer.seconds()
	double elapsed = 2.3;
	double rate    = ((double) samples) / elapsed;
  
	System.out.format( "----- get_real() [ %s ] --------------------------\n", ent.name() );
	System.out.format( "minv = %25.16e expected = %25.16e\n", minV, 0.0 );
	System.out.format( "maxv = %25.16e expected = %25.16e\n", maxV, 1.0 );
	System.out.format( "mean = %25.16e expected = %25.16e\n", mean, expected_mean );
	System.out.format( "var  = %25.16e expected = %25.16e\n", var,  expected_var  );
	System.out.format( "rate =   %11.4e rolls / second\n\n", rate );
  
	return rate;
    }


    // ===================================================================================
    static boolean main() {
	// -------------------------------------------------------------------------------	
	long samples = 10000000;

	int[] trial = { Entropy.BUILTIN_ENGINE, Entropy.SIMPLE_LCG_ENGINE };
	int nt = trial.length;

	double   best_rate = 0.0e0;
	boolean  best_real = false;
	String   best_name = "";
  
	for ( int i=0; i<nt; i++ ) {
	    Entropy ent   = new Entropy( trial[i] );
	    double  rtest = test_real( samples, ent );
	    double  itest = test_integer( samples, ent );
	    if ( rtest > itest ) {
		if ( rtest > best_rate ) {
		    best_rate = rtest;
		    best_name = ent.name();
		    best_real = true;
		}
	    }
  
	    if ( rtest < itest ) {
		if ( itest > best_rate ) {
		    best_rate = itest;
		    best_name = ent.name();
		    best_real = false;
		}
	    }
	}

	System.out.format( "The best rate was %12.5e rnds / second ", best_rate );

	if ( best_real ) {
	    System.out.format( "get_real()" );
	} else {
	    System.out.format( "get_integer()" );
	}

	System.out.format( " from %s\n\n", best_name );
  
	return true;
    }

}

// =======================================================================================
// **                             J T E S T _ E N T R O P Y                             **
// ======================================================================== END FILE =====

