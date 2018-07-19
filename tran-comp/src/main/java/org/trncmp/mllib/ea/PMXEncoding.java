// ====================================================================== BEGIN FILE =====
// **                               P M X E N C O D I N G                               **
// =======================================================================================
// **                                                                                   **
// **  This file is part of the TRNCMP Research Library. (formerly SolLib)              **
// **                                                                                   **
// **  Copyright (c) 2015, Stephen W. Soliday                                           **
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
//
// @file PMXEncoding.java
// <p>
// Provides a partial mapped crossover Encoding for ordered parameters.
//
// @author Stephen W. Soliday
// @date 2015-08-24
//
// =======================================================================================

package org.trncmp.mllib.ea;

// =======================================================================================
public class PMXEncoding extends OrderEncoding {
    // -----------------------------------------------------------------------------------

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public PMXEncoding( int n ) {
	// -------------------------------------------------------------------------------
	super(n);
    }

    // ===================================================================================
    /** @brief Is In Front.
     *  @param x test value.
     *  @param P pointer to an ordered set.
     *  @param cut cut point.
     *  @return true if the test point is in the front.
     *
     *  Test to determine if the test value is found before the cut point in the ordered set.
     */
    // -----------------------------------------------------------------------------------
    protected static boolean inFront( int x, PMXEncoding P, int cut ) {
	// -------------------------------------------------------------------------------
	for ( int i=0; i<cut; i++ ) {
	    if ( P.get( i ) == x ) { return true; }
	}
	return false;
    }

    // ===================================================================================
    /** @brief Is In Rear.
     *  @param x test value.
     *  @param P pointer to an ordered set.
     *  @param cut cut point.
     *  @return true if the test point is in the rear.
     *
     *  Test to determine if the test value is found after the cut point in the ordered set.
     */
    // -----------------------------------------------------------------------------------
    protected static boolean inRear( int x, PMXEncoding P, int cut ) {
	// -------------------------------------------------------------------------------
	int num = P.size();
	for ( int i=cut; i<num; i++ ) {
	    if ( P.get( i ) == x ) { return true; }
	}
	return false;
    }

    // ===================================================================================
    /** @brief Front Cross.
     *  @param C pointer to child data.
     *  @param P pointer to parent data.
     *  @param T pointer to template data.
     *  @param cut cut point in the ordered string.
     *
     *  Create a child by keeping the front half fixed and reorder the rear half based on
     *  the template data.
     */
    // -----------------------------------------------------------------------------------
    protected static void FrontCross( PMXEncoding C, PMXEncoding P, PMXEncoding T, int cut ) {
	// -------------------------------------------------------------------------------
	int num = C.size();

	for ( int i=0; i<cut; i++ ) {
	    C.set( i, P.get( i ) );
	}

	int idx = cut;
	for ( int i=0; i<num; i++ ) {
	    int x = T.get( i );
	    if ( inRear( x, P, cut ) ) {
		C.set( idx, x );
		idx += 1;
		if ( idx == num ) { break; }
	    }
	}
    }

    // ===================================================================================
    /** @brief Rear Cross.
     *  @param C pointer to child data.
     *  @param P pointer to parent data.
     *  @param T pointer to template data.
     *  @param cut cut point in the ordered string.
     *
     *  Create a child by keeping the rear half fixed and reorder the front half based on
     *  the template data.
     */
    // -----------------------------------------------------------------------------------
    protected static void RearCross( PMXEncoding C, PMXEncoding P, PMXEncoding T, int cut ) {
	// -------------------------------------------------------------------------------
	int num = C.size();

	for ( int i=cut; i<num; i++ ) {
	    C.set( i, P.get( i ) );
	}

	int idx = 0;
	for ( int i=0; i<num; i++ ) {
	    int x = T.get( i );
	    if ( inFront( x, P, cut ) ) {
		C.set( idx, x );
		idx += 1;
		if ( idx == cut ) { break; }
	    }
	}
    }

    // ===================================================================================
    /** @brief Crossover.
     *  @param ap1 pointer to parent number one
     *  @param ap2 pointer to parent number two
     *  @param ac2 pointer to child number two ( this is child number one )
     *  @return true if crossover took place.
     *
     *  The go/no-go decision was made higher up. At this point we are going to perform
     *  Crossover.
     */
    // -----------------------------------------------------------------------------------
    public void crossover( Encoding ap1, Encoding ap2, Encoding ac2 ) {
	// -------------------------------------------------------------------------------
	if ( null == ap1 ) {
	    throw new NullPointerException("Parent 1 ( NULL )");
	}
      
	if ( null == ap2 ) {
	    throw new NullPointerException("Parent 2 ( NULL )");
	}

	if ( null == ac2 ) {
	    throw new NullPointerException("Child 2 ( NULL )");
	}

	PMXEncoding p1 = (PMXEncoding)ap1;
	PMXEncoding p2 = (PMXEncoding)ap2;
	PMXEncoding c1 = this;
	PMXEncoding c2 = (PMXEncoding)ac2;

	int a = size() / 20;
	int b = size() - a - a;

	// ----- child 1 --------------------------------------------------
	int cut1 = a + ent.index( b );
	if ( ent.bool() ) {
	    FrontCross( c1, p1, p2, cut1 );
	} else {
	    RearCross( c1, p1, p2, cut1 );
	}

	// ----- child 2 --------------------------------------------------
	int cut2 = a + ent.index( b );
	if ( ent.bool() ) {
	    FrontCross( c2, p2, p1, cut2 );
	} else {
	    RearCross( c2, p2, p1, cut2 );
	}

    }

}

// =======================================================================================
// **                               P M X E N C O D I N G                               **
// ======================================================================== END FILE =====
