// ====================================================================== BEGIN FILE =====
// **                                J T E S T _ D I C E                                **
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
 * @file jtest_dice.java
 *  Provides Unit Test the Dice class.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-08-28
 */
// =======================================================================================

package org.trncmp.lib;

// =======================================================================================
public class jtest_dice {
    // -----------------------------------------------------------------------------------

    // ===================================================================================
    static boolean test01a( ) {
	// -------------------------------------------------------------------------------	

	final int samples = 100000;

	double[] test1 = new double[ samples ];
	double[] test2 = new double[ samples ];

	Dice dd = Dice.getInstance();

	int ns = dd.seed_size();

	SeedMatter seed_data = new SeedMatter( ns );

	dd.seed_set( seed_data );

	for ( int i=0; i<samples; i++ ) { test1[i] = dd.uniform(); }

	dd.seed_set( seed_data );

	for ( int i=0; i<samples; i++ ) { test2[i] = dd.uniform(); }

	double mse = Math2.sumsq( test1, test2 );

	System.out.println( "MSE="+mse+" "+dd.engine_name() );

	
	return true;
    }

    // ===================================================================================
    static boolean test01( ) {
	// -------------------------------------------------------------------------------	

	Dice.set_entropy_engine( Entropy.BUILTIN_ENGINE );

	test01a();

	Dice.dispose();
	
	Dice.set_entropy_engine( Entropy.SIMPLE_LCG_ENGINE );

	test01a();

	Dice.dispose();

	return true;	
    }

    // ===================================================================================
    static boolean test02( ) {
	// -------------------------------------------------------------------------------

	Dice.set_entropy_engine( Entropy.BUILTIN_ENGINE );

	Dice dd = Dice.getInstance();

	int ns = dd.seed_size();

	SeedMatter seed_data = new SeedMatter( ns );

	dd.seed_set( seed_data );

	final long samples = 10000000L;

	RunStat RS = new RunStat();

	double mu = 0.0e0;
	for ( long i=0; i<samples; i++ ) {
	    double x = dd.normal();
	    mu += x;
	    RS.update( x );
	}

	mu /= (double)samples;

	dd.seed_set( seed_data );

	double vr1 = 0.0e0;
	double vr2 = 0.0e0;
	
	for ( long i=0; i<samples; i++ ) {
	    double x = dd.normal();
	    double d = x - mu;
	    vr1 += (d*d);
	    vr2 += (x*x);
	}

	vr1 /= (double)samples;
	vr2 /= (double)(samples - 1);

	System.out.format( "Run Stat:   mean = %15.8e var = %15.8e  (%15.8e,%15.8e)\n",
			   RS.mean(), RS.var(), RS.minv(), RS.maxv() );

	System.out.format( "Sample:     mean = %15.8e var = %15.8e\n", mu, vr1 );
	System.out.format( "Population: mean = %15.8e var = %15.8e\n", mu, vr2 );

	Dice.dispose();

	return true;	
    }


    
    // ===================================================================================
    static boolean main() {
	// -------------------------------------------------------------------------------	

	test02();
	
	return true;
    }

}

// =======================================================================================
// **                             J T E S T _ E N T R O P Y                             **
// ======================================================================== END FILE =====

