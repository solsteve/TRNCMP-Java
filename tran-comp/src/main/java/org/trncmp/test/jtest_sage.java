// ====================================================================== BEGIN FILE =====
// **                                J T E S T _ S A G E                                **
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
 * @file jtest_sage.java
 *  Provides Unit Test the Display functions.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-08-31
 */
// =======================================================================================

package org.trncmp.lib;

// =======================================================================================
public class jtest_sage {
    // -----------------------------------------------------------------------------------

    static final double[] testR64 = { 1.2, 2.3, 3.4, 4.5, 6.7, 7.8 };
    static final float[]  testR32 = { 1.22f, 2.33f, 3.44f, 4.55f };
    static final long[]   testI64 = { 312498789, 56413568, 61415487 };
    static final int[]    testI32 = { 513211, 65498841, 32122, 3646884, 631513, 210315 };
    static final short[]  testI16 = { 215, 6156, 48, 4516, 532 };
    static final byte[]   testI08 = { 1, 2, 3, 4, 5 };


    static final int n_row = 3;
    static final int n_col = 5;

    

    static double[][] testR64_2D = null;
    static float[][]  testR32_2D = null;
    static long[][]   testI64_2D = null;
    static int[][]    testI32_2D = null;
    static short[][]  testI16_2D = null;
    static byte[][]   testI08_2D = null;


     // ===================================================================================
    static void init( ) {
	// -------------------------------------------------------------------------------


	testR64_2D = new double[n_row][n_col];
	testR32_2D = new float[n_row][n_col];
	testI64_2D = new long[n_row][n_col];
	testI32_2D = new int[n_row][n_col];
	testI16_2D = new short[n_row][n_col];
	testI08_2D = new byte[n_row][n_col];



	
   	for ( int i=0; i<n_row; i++ ) {
	    for ( int j=0; j<n_col; j++ ) {
		testR64_2D[i][j] = ((double)((i+1)*10)) + (((double)(j+1))/10.0);
		testR32_2D[i][j] = ((float)((i+1)*10))  + (((float)(j+1))/10.0f);
		testI64_2D[i][j] = (i+1)*1000 + (j+1)*10;
		testI32_2D[i][j] = (i+1)*1000 + (j+1)*10;
		testI16_2D[i][j] = (short)((i+1)*10  + (j+1));
		testI08_2D[i][j] = (byte)((i+1)*10   + (j+1));
	    }
	}

    }



    // ===================================================================================
    static boolean test01( ) {
	// -------------------------------------------------------------------------------
	
	System.out.println( "" );
	System.out.println( "===== ARRAY =====================================" );

	System.out.println( "" );
	System.out.println( array.toString( testR64, "%5.2f", " | " ) );
	System.out.println( array.toString( testR64, "%5.2f" ) );
	System.out.println( array.toString( testR64 ) );
	
	System.out.println( "" );
	System.out.println( array.toString( testR32, "%5.2f", " | " ) );
	System.out.println( array.toString( testR32, "%5.2f" ) );
	System.out.println( array.toString( testR32 ) );
	
	System.out.println( "" );
	System.out.println( array.toString( testI64, "%d", " | " ) );
	System.out.println( array.toString( testI64, "%d" ) );
	System.out.println( array.toString( testI64 ) );
	
	System.out.println( "" );
	System.out.println( array.toString( testI32, "%d", " | " ) );
	System.out.println( array.toString( testI32, "%d" ) );
	System.out.println( array.toString( testI32 ) );
	
	System.out.println( "" );
	System.out.println( array.toString( testI16, "%d", " | " ) );
	System.out.println( array.toString( testI16, "%d" ) );
	System.out.println( array.toString( testI16 ) );
	
	System.out.println( "" );
	System.out.println( array.toString( testI08, "%d", " | " ) );
	System.out.println( array.toString( testI08, "%d" ) );
	System.out.println( array.toString( testI08 ) );
	
	return true;	
    }

    // ===================================================================================
    static boolean test02( ) {
	// -------------------------------------------------------------------------------
	System.out.println( "" );
	System.out.println( "===== OCTAVE/MATLAB =============================" );

	System.out.println( "" );
	System.out.println( octave.toString( testR64, "%7.3f" ) );
	System.out.println( octave.toString( testR64  ) );
		
	System.out.println( "" );
	System.out.println( octave.toString( testR32, "%7.3f" ) );
	System.out.println( octave.toString( testR32  ) );
		
	System.out.println( "" );
	System.out.println( octave.toString( testI64, "%010d" ) );
	System.out.println( octave.toString( testI64  ) );
		
	System.out.println( "" );
	System.out.println( octave.toString( testI32, "%010d" ) );
	System.out.println( octave.toString( testI32  ) );
		
	System.out.println( "" );
	System.out.println( octave.toString( testI16, "%06d" ) );
	System.out.println( octave.toString( testI16  ) );
		
	System.out.println( "" );
	System.out.println( octave.toString( testI08, "%04d" ) );
	System.out.println( octave.toString( testI08  ) );
		
	return true;	
    }
    
     // ===================================================================================
    static boolean test03( ) {
	// --------------------------------------------------------------------------------
	
	System.out.println( "" );
	System.out.println( "===== SAGE ======================================" );

	System.out.println( "" );
	System.out.println( sage.toString( testR64, "%7.3f" ) );
	System.out.println( sage.toString( testR64  ) );
		
	System.out.println( "" );
	System.out.println( sage.toString( testR32, "%7.3f" ) );
	System.out.println( sage.toString( testR32  ) );
		
	System.out.println( "" );
	System.out.println( sage.toString( testI64, "%010d" ) );
	System.out.println( sage.toString( testI64  ) );
		
	System.out.println( "" );
	System.out.println( sage.toString( testI32, "%010d" ) );
	System.out.println( sage.toString( testI32  ) );
		
	System.out.println( "" );
	System.out.println( sage.toString( testI16, "%06d" ) );
	System.out.println( sage.toString( testI16  ) );
		
	System.out.println( "" );
	System.out.println( sage.toString( testI08, "%04d" ) );
	System.out.println( sage.toString( testI08  ) );
		
	return true;	
    }


    
     // ===================================================================================
    static boolean test04() {
	// --------------------------------------------------------------------------------


	System.out.println( "" );
	System.out.println( "===== ARRAY == 2D ===================================" );

	System.out.println( "" );
	System.out.println( array.toString( testR64_2D, "%5.2f", " | ", " : " ) );
	System.out.println( array.toString( testR64_2D, "%5.2f" ) );
	System.out.println( array.toString( testR64_2D ) );
	
	System.out.println( "" );
	System.out.println( array.toString( testR32_2D, "%5.2f", " | ", " : " ) );
	System.out.println( array.toString( testR32_2D, "%5.2f" ) );
	System.out.println( array.toString( testR32_2D ) );
	
	System.out.println( "" );
	System.out.println( array.toString( testI64_2D, "%d", " | ", " : " ) );
	System.out.println( array.toString( testI64_2D, "%06d" ) );
	System.out.println( array.toString( testI64_2D ) );
	
	System.out.println( "" );
	System.out.println( array.toString( testI32_2D, "%d", " | ", " : " ) );
	System.out.println( array.toString( testI32_2D, "%06d" ) );
	System.out.println( array.toString( testI32_2D ) );
	
	System.out.println( "" );
	System.out.println( array.toString( testI16_2D, "%d", " | ", " : " ) );
	System.out.println( array.toString( testI16_2D, "%03d" ) );
	System.out.println( array.toString( testI16_2D ) );
	
	System.out.println( "" );
	System.out.println( array.toString( testI08_2D, "%d", " | ", " : " ) );
	System.out.println( array.toString( testI08_2D, "%03d" ) );
	System.out.println( array.toString( testI08_2D ) );

	return true;
    }
    
     // ===================================================================================
    static boolean test05() {
	// --------------------------------------------------------------------------------


	System.out.println( "" );
	System.out.println( "===== OCTAVE == 2D ===================================" );

	System.out.println( "" );
	System.out.println( octave.toString( testR64_2D, "%5.2f" ) );
	System.out.println( octave.toString( testR64_2D ) );
	
	System.out.println( "" );
	System.out.println( octave.toString( testR32_2D, "%5.2f" ) );
	System.out.println( octave.toString( testR32_2D ) );
	
	System.out.println( "" );
	System.out.println( octave.toString( testI64_2D, "%06d" ) );
	System.out.println( octave.toString( testI64_2D ) );
	
	System.out.println( "" );
	System.out.println( octave.toString( testI32_2D, "%06d" ) );
	System.out.println( octave.toString( testI32_2D ) );
	
	System.out.println( "" );
	System.out.println( octave.toString( testI16_2D, "%03d" ) );
	System.out.println( octave.toString( testI16_2D ) );
	
	System.out.println( "" );
	System.out.println( octave.toString( testI08_2D, "%03d" ) );
	System.out.println( octave.toString( testI08_2D ) );

	return true;
    }
    
      // ===================================================================================
    static boolean test06() {
	// --------------------------------------------------------------------------------


	System.out.println( "" );
	System.out.println( "===== SAGE == 2D ===================================" );

	System.out.println( "" );
	System.out.println( sage.toString( testR64_2D, "%5.2f" ) );
	System.out.println( sage.toString( testR64_2D ) );
	
	System.out.println( "" );
	System.out.println( sage.toString( testR32_2D, "%5.2f" ) );
	System.out.println( sage.toString( testR32_2D ) );
	
	System.out.println( "" );
	System.out.println( sage.toString( testI64_2D, "%06d" ) );
	System.out.println( sage.toString( testI64_2D ) );
	
	System.out.println( "" );
	System.out.println( sage.toString( testI32_2D, "%06d" ) );
	System.out.println( sage.toString( testI32_2D ) );
	
	System.out.println( "" );
	System.out.println( sage.toString( testI16_2D, "%03d" ) );
	System.out.println( sage.toString( testI16_2D ) );
	
	System.out.println( "" );
	System.out.println( sage.toString( testI08_2D, "%03d" ) );
	System.out.println( sage.toString( testI08_2D ) );

	return true;
    }
    
  // ===================================================================================
    static boolean main() {
	// -------------------------------------------------------------------------------

	init();
	
	System.out.println( "" );

	test01();
	test02();
	test03();
	
	System.out.println( "" );
	System.out.println( "===========================================================" );
	System.out.println( "" );
	
	test04();
	test05();
	test06();
	
	return true;
    }

}

// =======================================================================================
// **                                J T E S T _ S A G E                                **
// ======================================================================== END FILE =====

