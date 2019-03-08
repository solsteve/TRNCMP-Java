// ====================================================================== BEGIN FILE =====
// **                                    O C T A V E                                    **
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
 * @file octave.java
 *  Provides interface and methods for displaying vectors and matricies as strings
 *  that are compatable with pasting into Octave and/or Matlab. 
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-08-31
 */
// =======================================================================================

package org.trncmp.lib;

// =======================================================================================
public class octave {
    // -----------------------------------------------------------------------------------

    private static final String OHEAD = "[ "; 
    private static final String OTAIL = " ];"; 
    
    private static final String ORDLM = ";";         // vectors may be space delimited
    private static final String OCDLM = ",";         // vectors may be space delimited

    private static final String OTRANS = " ]';";    // creates a column vector

    // private static final String OTRANS = " ]';"  // creates a row vector

    // ===================================================================================
    /** @brief Display vector.
     *  @param A   pointer to a 1D array of 64 bit real values.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( double[] A, String fmt ) {
	// -------------------------------------------------------------------------------
	return OHEAD + array.toString( A, fmt, OCDLM ) + OTRANS;
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A   pointer to a 1D array of 32 bit real values.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( float[] A, String fmt ) {
	// -------------------------------------------------------------------------------
	return OHEAD + array.toString( A, fmt, OCDLM ) + OTRANS;
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A   pointer to a 1D array of 64 bit integer values.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( long[] A, String fmt ) {
	// -------------------------------------------------------------------------------
	return OHEAD + array.toString( A, fmt, OCDLM ) + OTRANS;
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A   pointer to a 1D array of 32 bit integer values.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( int[] A, String fmt ) {
	// -------------------------------------------------------------------------------
	return OHEAD + array.toString( A, fmt, OCDLM ) + OTRANS;
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A   pointer to a 1D array of 16 bit integer values.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( short[] A, String fmt ) {
	// -------------------------------------------------------------------------------
	return OHEAD + array.toString( A, fmt, OCDLM ) + OTRANS;
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A   pointer to a 1D array of 8 bit integer values.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( byte[] A, String fmt ) {
	// -------------------------------------------------------------------------------
	return OHEAD + array.toString( A, fmt, OCDLM ) + OTRANS;
    }





    // ===================================================================================
    /** @brief Display vector.
     *  @param A pointer to a 1D array of 64 bit real values.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( double[] A ) {
	// -------------------------------------------------------------------------------
	return octave.toString( A, "%g" );
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A pointer to a 1D array of 32 bit real values.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( float[] A ) {
	// -------------------------------------------------------------------------------
	return octave.toString( A, "%g" );
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A pointer to a 1D array of 64 bit integer values.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( long[] A ) {
	// -------------------------------------------------------------------------------
	return octave.toString( A, "%d" );
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A pointer to a 1D array of 32 bit integer values.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( int[] A ) {
	// -------------------------------------------------------------------------------
	return octave.toString( A, "%d" );
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A pointer to a 1D array of 16 bit integer values.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( short[] A ) {
	// -------------------------------------------------------------------------------
	return octave.toString( A, "%d" );
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A pointer to a 1D array of 8 bit integer values.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( byte[] A ) {
	// -------------------------------------------------------------------------------
	return octave.toString( A, "%d" );
    }

    









    // ===================================================================================
    /** @brief Display vector.
     *  @param A   pointer to a 2D array of 64 bit real values.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( double[][] A, String fmt ) {
	// -------------------------------------------------------------------------------
	return OHEAD + array.toString( A, fmt, ORDLM, OCDLM ) + OTAIL;
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A   pointer to a 2D array of 32 bit real values.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( float[][] A, String fmt ) {
	// -------------------------------------------------------------------------------
	return OHEAD + array.toString( A, fmt, ORDLM, OCDLM ) + OTAIL;
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A   pointer to a 2D array of 64 bit integer values.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( long[][] A, String fmt ) {
	// -------------------------------------------------------------------------------
	return OHEAD + array.toString( A, fmt, ORDLM, OCDLM ) + OTAIL;
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A   pointer to a 2D array of 32 bit integer values.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( int[][] A, String fmt ) {
	// -------------------------------------------------------------------------------
	return OHEAD + array.toString( A, fmt, ORDLM, OCDLM ) + OTAIL;
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A   pointer to a 2D array of 16 bit integer values.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( short[][] A, String fmt ) {
	// -------------------------------------------------------------------------------
	return OHEAD + array.toString( A, fmt, ORDLM, OCDLM ) + OTAIL;
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A   pointer to a 2D array of 8 bit integer values.
     *  @param fmt format specifier of each element.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( byte[][] A, String fmt ) {
	// -------------------------------------------------------------------------------
	return OHEAD + array.toString( A, fmt, ORDLM, OCDLM ) + OTAIL;
    }




    // ===================================================================================
    /** @brief Display vector.
     *  @param A pointer to a 2D array of 64 bit real values.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( double[][] A ) {
	// -------------------------------------------------------------------------------
	return octave.toString( A, "%g" );
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A pointer to a 2D array of 32 bit real values.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( float[][] A ) {
	// -------------------------------------------------------------------------------
	return octave.toString( A, "%g" );
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A pointer to a 2D array of 64 bit integer values.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( long[][] A ) {
	// -------------------------------------------------------------------------------
	return octave.toString( A, "%d" );
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A pointer to a 2D array of 32 bit integer values.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( int[][] A ) {
	// -------------------------------------------------------------------------------
	return octave.toString( A, "%d" );
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A pointer to a 2D array of 16 bit integer values.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( short[][] A ) {
	// -------------------------------------------------------------------------------
	return octave.toString( A, "%d" );
    }

    
    // ===================================================================================
    /** @brief Display vector.
     *  @param A pointer to a 2D array of 8 bit integer values.
     *
     *  Display as a string a vector that if compatable with Octave/Matlab.
     */
    // -----------------------------------------------------------------------------------
    static public String toString( byte[][] A ) {
	// -------------------------------------------------------------------------------
	return octave.toString( A, "%d" );
    }

};

    
// =======================================================================================
// **                                      S A G E                                      **
// ======================================================================== END FILE =====
