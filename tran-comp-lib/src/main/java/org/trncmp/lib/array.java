// ====================================================================== BEGIN FILE =====
// **                                     A R R A Y                                     **
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
 * @file array.java
 *  Provides interface and methods for displaying vectors and matricies as strings.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-08-31
 */
// =======================================================================================

package org.trncmp.lib;

// =======================================================================================
public class array {
  // -----------------------------------------------------------------------------------

    
  static private final String DEFUALT_DELIM = ",";


  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 1D array of 64 bit real values.
   *  @param fmt format specifier of each element.
   *  @param dlm delimeter separating each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( double[] A, String fmt, String dlm ) {
    // -------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    int n = A.length;

    for ( int i=0; i<n; i++ ) {
      if ( 0 < i ) { buffer.append( dlm ); }
      buffer.append( String.format( fmt, A[i] ) );
    }

    return buffer.toString();
  }


  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 1D array of 32 bit real values.
   *  @param fmt format specifier of each element.
   *  @param dlm delimeter separating each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( float[] A, String fmt, String dlm ) {
    // -------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    int n = A.length;

    for ( int i=0; i<n; i++ ) {
      if ( 0 < i ) { buffer.append( dlm ); }
      buffer.append( String.format( fmt, A[i] ) );
    }

    return buffer.toString();
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 1D array of 64 bit integer values.
   *  @param fmt format specifier of each element.
   *  @param dlm delimeter separating each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( long[] A, String fmt, String dlm ) {
    // -------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    int n = A.length;

    for ( int i=0; i<n; i++ ) {
      if ( 0 < i ) { buffer.append( dlm ); }
      buffer.append( String.format( fmt, A[i] ) );
    }

    return buffer.toString();
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 1D array of 32 bit integer values.
   *  @param fmt format specifier of each element.
   *  @param dlm delimeter separating each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( int[] A, String fmt, String dlm ) {
    // -------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    int n = A.length;

    for ( int i=0; i<n; i++ ) {
      if ( 0 < i ) { buffer.append( dlm ); }
      buffer.append( String.format( fmt, A[i] ) );
    }

    return buffer.toString();
  }


  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 1D array of 16 bit integer values.
   *  @param fmt format specifier of each element.
   *  @param dlm delimeter separating each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( short[] A, String fmt, String dlm ) {
    // -------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    int n = A.length;

    for ( int i=0; i<n; i++ ) {
      if ( 0 < i ) { buffer.append( dlm ); }
      buffer.append( String.format( fmt, A[i] ) );
    }

    return buffer.toString();
  }


  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 1D array of 8 bit integer values.
   *  @param fmt format specifier of each element.
   *  @param dlm delimeter separating each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( byte[] A, String fmt, String dlm ) {
    // -------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    int n = A.length;

    for ( int i=0; i<n; i++ ) {
      if ( 0 < i ) { buffer.append( dlm ); }
      buffer.append( String.format( fmt, A[i] ) );
    }

    return buffer.toString();
  }








  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 1D array of 64 bit real values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public String toString( double[] A, String fmt ) {
    // -------------------------------------------------------------------------------
    return array.toString( A, fmt, DEFUALT_DELIM );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 1D array of 32 bit real values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public String toString( float[] A, String fmt ) {
    // -------------------------------------------------------------------------------
    return array.toString( A, fmt, DEFUALT_DELIM );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 1D array of 64 bit integer values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public String toString( long[] A, String fmt ) {
    // -------------------------------------------------------------------------------
    return array.toString( A, fmt, DEFUALT_DELIM );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 1D array of 32 bit integer values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public String toString( int[] A, String fmt ) {
    // -------------------------------------------------------------------------------
    return array.toString( A, fmt, DEFUALT_DELIM );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 1D array of 16 bit integer values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public String toString( short[] A, String fmt ) {
    // -------------------------------------------------------------------------------
    return array.toString( A, fmt, DEFUALT_DELIM );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 1D array of 8 bit integer values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public String toString( byte[] A, String fmt ) {
    // -------------------------------------------------------------------------------
    return array.toString( A, fmt, DEFUALT_DELIM );
  }








  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A pointer to a 1D array of 64 bit real values.
   */
  // -----------------------------------------------------------------------------------
  static public String toString( double[] A ) {
    // -------------------------------------------------------------------------------
    return array.toString( A, "%g", DEFUALT_DELIM );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A pointer to a 1D array of 32 bit real values.
   */
  // -----------------------------------------------------------------------------------
  static public String toString( float[] A ) {
    // -------------------------------------------------------------------------------
    return array.toString( A, "%g", DEFUALT_DELIM );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A pointer to a 1D array of 64 bit integer values.
   */
  // -----------------------------------------------------------------------------------
  static public String toString( long[] A ) {
    // -------------------------------------------------------------------------------
    return array.toString( A, "%d", DEFUALT_DELIM );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A pointer to a 1D array of 32 bit integer values.
   */
  // -----------------------------------------------------------------------------------
  static public String toString( int[] A ) {
    // -------------------------------------------------------------------------------
    return array.toString( A, "%d", DEFUALT_DELIM );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A pointer to a 1D array of 32 bit integer values.
   */
  // -----------------------------------------------------------------------------------
  static public String toString( short[] A ) {
    // -------------------------------------------------------------------------------
    return array.toString( A, "%d", DEFUALT_DELIM );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A pointer to a 1D array of 32 bit integer values.
   */
  // -----------------------------------------------------------------------------------
  static public String toString( byte[] A ) {
    // -------------------------------------------------------------------------------
    return array.toString( A, "%d", DEFUALT_DELIM );
  }








  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 64 bit real values.
   *  @param fmt format specifier of each element.
   *  @param rdlm delimeter separating each row.
   *  @param cdlm delimeter separating each column.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( double[][] A, String fmt, String rdlm, String cdlm ) {
    // -------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    int n = A.length;
    int m = A[0].length;

    for ( int i=0; i<n; i++ ) {
      if ( 0 < i ) { buffer.append( rdlm ); }
      for ( int j=0; j<m; j++ ) {
        if ( 0 < j ) { buffer.append( cdlm ); }
        buffer.append( String.format( fmt, A[i][j] ) );
      }
    }

    return buffer.toString();
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 64 bit real values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( double[][] A, String fmt ) {
    // -------------------------------------------------------------------------------
    return toString( A, fmt, "\n", " " );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 64 bit real values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( double[][] A ) {
    // -------------------------------------------------------------------------------
    return toString( A, "%g", "\n", " " );
  }




  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 32 bit real values.
   *  @param fmt format specifier of each element.
   *  @param rdlm delimeter separating each row.
   *  @param cdlm delimeter separating each column.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( float[][] A, String fmt, String rdlm, String cdlm ) {
    // -------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    int n = A.length;
    int m = A[0].length;

    for ( int i=0; i<n; i++ ) {
      if ( 0 < i ) { buffer.append( rdlm ); }
      for ( int j=0; j<m; j++ ) {
        if ( 0 < j ) { buffer.append( cdlm ); }
        buffer.append( String.format( fmt, A[i][j] ) );
      }
    }

    return buffer.toString();
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 32 bit real values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( float[][] A, String fmt ) {
    // -------------------------------------------------------------------------------
    return toString( A, fmt, "\n", " " );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 32 bit real values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( float[][] A ) {
    // -------------------------------------------------------------------------------
    return toString( A, "%g", "\n", " " );
  }




  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 64 bit integer values.
   *  @param fmt format specifier of each element.
   *  @param rdlm delimeter separating each row.
   *  @param cdlm delimeter separating each column.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( long[][] A, String fmt, String rdlm, String cdlm ) {
    // -------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    int n = A.length;
    int m = A[0].length;

    for ( int i=0; i<n; i++ ) {
      if ( 0 < i ) { buffer.append( rdlm ); }
      for ( int j=0; j<m; j++ ) {
        if ( 0 < j ) { buffer.append( cdlm ); }
        buffer.append( String.format( fmt, A[i][j] ) );
      }
    }

    return buffer.toString();
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 64 bit integer values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( long[][] A, String fmt ) {
    // -------------------------------------------------------------------------------
    return toString( A, fmt, "\n", " " );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 64 bit integer values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( long[][] A ) {
    // -------------------------------------------------------------------------------
    return toString( A, "%d", "\n", " " );
  }




  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 32 bit integer values.
   *  @param fmt format specifier of each element.
   *  @param rdlm delimeter separating each row.
   *  @param cdlm delimeter separating each column.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( int[][] A, String fmt, String rdlm, String cdlm ) {
    // -------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    int n = A.length;
    int m = A[0].length;

    for ( int i=0; i<n; i++ ) {
      if ( 0 < i ) { buffer.append( rdlm ); }
      for ( int j=0; j<m; j++ ) {
        if ( 0 < j ) { buffer.append( cdlm ); }
        buffer.append( String.format( fmt, A[i][j] ) );
      }
    }

    return buffer.toString();
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 32 bit integer values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( int[][] A, String fmt ) {
    // -------------------------------------------------------------------------------
    return toString( A, fmt, "\n", " " );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 32 bit integer values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( int[][] A ) {
    // -------------------------------------------------------------------------------
    return toString( A, "%d", "\n", " " );
  }




  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 16 bit integer values.
   *  @param fmt format specifier of each element.
   *  @param rdlm delimeter separating each row.
   *  @param cdlm delimeter separating each column.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( short[][] A, String fmt, String rdlm, String cdlm ) {
    // -------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    int n = A.length;
    int m = A[0].length;

    for ( int i=0; i<n; i++ ) {
      if ( 0 < i ) { buffer.append( rdlm ); }
      for ( int j=0; j<m; j++ ) {
        if ( 0 < j ) { buffer.append( cdlm ); }
        buffer.append( String.format( fmt, A[i][j] ) );
      }
    }

    return buffer.toString();
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 16 bit integer values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( short[][] A, String fmt ) {
    // -------------------------------------------------------------------------------
    return toString( A, fmt, "\n", " " );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 16 bit integer values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( short[][] A ) {
    // -------------------------------------------------------------------------------
    return toString( A, "%d", "\n", " " );
  }




  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 8 bit integer values.
   *  @param fmt format specifier of each element.
   *  @param rdlm delimeter separating each row.
   *  @param cdlm delimeter separating each column.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( byte[][] A, String fmt, String rdlm, String cdlm ) {
    // -------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    int n = A.length;
    int m = A[0].length;

    for ( int i=0; i<n; i++ ) {
      if ( 0 < i ) { buffer.append( rdlm ); }
      for ( int j=0; j<m; j++ ) {
        if ( 0 < j ) { buffer.append( cdlm ); }
        buffer.append( String.format( fmt, A[i][j] ) );
      }
    }

    return buffer.toString();
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 8 bit integer values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( byte[][] A, String fmt ) {
    // -------------------------------------------------------------------------------
    return toString( A, fmt, "\n", " " );
  }

    
  // ===================================================================================
  /** @brief Convert Array to String.
   *  @param A   pointer to a 2D array of 8 bit integer values.
   *  @param fmt format specifier of each element.
   */
  // -----------------------------------------------------------------------------------
  static public  String toString( byte[][] A ) {
    // ---------------------------------------------------------------------------------
    return toString( A, "%d", "\n", " " );
  }


}

    
// =======================================================================================
// **                                     A R R A Y                                     **
// ======================================================================== END FILE =====
