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
import org.trncmp.lib.linear.Vec2;
import org.trncmp.lib.linear.Vec3;
import org.trncmp.lib.linear.Vec4;
import org.trncmp.lib.linear.Vector;
import org.trncmp.lib.linear.Mat2;
import org.trncmp.lib.linear.Mat3;
import org.trncmp.lib.linear.Mat4;
import org.trncmp.lib.linear.Matrix;

// =======================================================================================
public class octave_utest {
    // -----------------------------------------------------------------------------------


    // ===================================================================================
    public static void rand( Vec2 v, Dice dd ) {
	// -------------------------------------------------------------------------------
	v.x = dd.normal();
	v.y = dd.normal();
    }
    

    // ===================================================================================
    public static void rand( Vec3 v, Dice dd ) {
	// -------------------------------------------------------------------------------
	v.x = dd.normal();
	v.y = dd.normal();
	v.z = dd.normal();
    }
    

    // ===================================================================================
    public static void rand( Vec4 v, Dice dd ) {
	// -------------------------------------------------------------------------------
	v.x = dd.normal();
	v.y = dd.normal();
	v.z = dd.normal();
	v.t = dd.normal();
    }
    

    // ===================================================================================
    public static void rand( Vector v, Dice dd ) {
	// -------------------------------------------------------------------------------
	for ( int i=0; i<v.nx; i++ ) {
	    v.x[i] = dd.normal();
	}
    }
    

    // ===================================================================================
    public static void rand( Mat2 v, Dice dd ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<2; r++ ) {
	    for ( int c=0; c<2; c++ ) {
		v.A[r][c] = dd.normal();
	    }
	}
    }
    

    // ===================================================================================
    public static void rand( Mat3 v, Dice dd ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<3; r++ ) {
	    for ( int c=0; c<3; c++ ) {
		v.A[r][c] = dd.normal();
	    }
	}
    }
    

    // ===================================================================================
    public static void rand( Mat4 v, Dice dd ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<4; r++ ) {
	    for ( int c=0; c<4; c++ ) {
		v.A[r][c] = dd.normal();
	    }
	}
    }
    

    // ===================================================================================
    public static void rand( Matrix M, Dice dd ) {
	// -------------------------------------------------------------------------------
	for ( int i=0; i<M.nr; i++ ) {
	    for ( int j=0; j<M.nc; j++ ) {
		M.A[i][j] = dd.normal();
	    }
	}
    }
    

    // ===================================================================================
    public static void rand( double[] v, Dice dd ) {
	// -------------------------------------------------------------------------------
	int n = v.length;
	for ( int i=0; i<n; i++ ) {
	    v[i] = dd.normal();
	}
    }
    

    // ===================================================================================
    public static void rand( double[][] v, Dice dd ) {
	// -------------------------------------------------------------------------------
	int nr = v.length;
	int nc = v[0].length;
	for ( int r=0; r<nr; r++ ) {
	    for ( int c=0; c<nc; c++ ) {
		v[r][c] = dd.normal();
	    }
	}
    }


    // ===================================================================================
    public static void copy( double[][] dst, double[] src ) {
	// -------------------------------------------------------------------------------
	int nr = dst.length;
	int nc = dst[0].length;
	int idx = 0;
	for ( int r=0; r<nr; r++ ) {
	    for ( int c=0; c<nc; c++ ) {
		dst[r][c] = src[idx];
		idx += 1;
	    }
	}
    }

     // ===================================================================================
    public static void copy( double[] dst, double[][] src ) {
	// -------------------------------------------------------------------------------
	int nr = src.length;
	int nc = src[0].length;
	int idx = 0;
	for ( int r=0; r<nr; r++ ) {
	    for ( int c=0; c<nc; c++ ) {
		dst[idx] = src[r][c];
		idx += 1;
	    }
	}
    }

   
    

    // ===================================================================================
    static public void oprint( String line ) {
	// -------------------------------------------------------------------------------
	System.out.format( "printf('\\n%%s\\n\\n', '%s');\n", line );
    }

    // ===================================================================================
    static public void oprint( String label, Vec2 v ) {
	// -------------------------------------------------------------------------------
	System.out.println( label + "=" + v.octave_toString("%.15f") );
    }

    // ===================================================================================
    static public void oprint( String label, Vec3 v ) {
	// -------------------------------------------------------------------------------
	System.out.println( label + "=" + v.octave_toString("%.15f") );
    }

    // ===================================================================================
    static public void oprint( String label, Vec4 v ) {
	// -------------------------------------------------------------------------------
	System.out.println( label + "=" + v.octave_toString("%.15f") );
    }

    // ===================================================================================
    static public void oprint( String label, Vector v ) {
	// -------------------------------------------------------------------------------
	System.out.println( label + "=" + v.octave_toString("%.15f") );
    }

    // ===================================================================================
    static public void oprint( String label, Mat2 v ) {
	// -------------------------------------------------------------------------------
	System.out.println( label + "=" + v.octave_toString("%.15f") );
    }

    // ===================================================================================
    static public void oprint( String label, Mat3 v ) {
	// -------------------------------------------------------------------------------
	System.out.println( label + "=" + v.octave_toString("%.15f") );
    }

    // ===================================================================================
    static public void oprint( String label, Mat4 v ) {
	// -------------------------------------------------------------------------------
	System.out.println( label + "=" + v.octave_toString("%.15f") );
    }

    // ===================================================================================
    static public void oprint( String label, Matrix M ) {
	// -------------------------------------------------------------------------------
	System.out.println( label + "=" + M.octave_toString("%.15f") );
    }

    // ===================================================================================
    static public void oprint( String label, double v ) {
	// -------------------------------------------------------------------------------
	System.out.println( label + "=" + String.format("%.15f",v) + ";" );
    }



    // ===================================================================================
    static public void ocheck( String lab, String eval ) {
	// -------------------------------------------------------------------------------
	System.out.format( "printf('%%-20s %%g\\n', '%s', %s);\n", lab, eval );
    }
}

// =======================================================================================
// **                                J T E S T _ S A G E                                **
// ======================================================================== END FILE =====

