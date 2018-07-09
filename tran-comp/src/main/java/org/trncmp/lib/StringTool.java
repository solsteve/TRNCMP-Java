// ====================================================================== BEGIN FILE =====
// **                                S T R I N G T O O L                                **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2013, 17, Stephen W. Soliday                                       **
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
 * @file StringTool.java
 *  Provides a set of string manipulation.
 *
 * @author Stephen W. Soliday
 * @date 2013-08-21 Original release.
 * @date 2017-09-12 Migration to the Proxima libraries.
 */
// =======================================================================================

package org.trncmp.lib;
import java.io.*;


// =======================================================================================
/** 
 */
// ---------------------------------------------------------------------------------------
public class StringTool {
    // -----------------------------------------------------------------------------------

    // ===================================================================================
    /** @brief Find.
     *  @param ref  pointer to a test string.
     *  @param test pointer to a set characters to test for.
     *  @param pos  first position in the string to search.
     *  @return first position of any of the test characters.
     *
     *  Find any of 'test' in 'ref' starting at pos return index in ref
     */
    // -----------------------------------------------------------------------------------
    public static int find_any( String ref, String test, int pos ) {
	// -------------------------------------------------------------------------------
	int end = ref.length();
	int ts  = test.length();
	for (int x=pos; x<end; x++) {
	    for (int y=0; y<ts; y++) {
		if (ref.charAt(x) == test.charAt(y)) {
		    return x;
		}
	    }
	}

	return -1;
    }

    // ===================================================================================
    /** @brief Find.
     *  @param ref  pointer to a test string.
     *  @param test pointer to a set characters to test for.
     *  @return first position of any of the test characters.
     *
     *  Find any of 'test' in 'ref' starting at pos return index in ref
     */
    // -----------------------------------------------------------------------------------
    public static int find_any( String ref, String test ) {
	// -------------------------------------------------------------------------------
	return find_any( ref, test, 0 );
    }

    // ===================================================================================
    /** @brief Contained by.
     *  @param test reference to a test string.
     *  @param lhs  reference to a set of valid left  hand side leading  characters.
     *  @param rhs  reference to a set of valid right hand side trailing characters.
     *  @return a string contained but not including the left and right hand containers.
     */
    // -----------------------------------------------------------------------------------
    public static String containedBy( String test, String lhs, String rhs ) {
	// -------------------------------------------------------------------------------
	int lpos = find_any( test, lhs );

	if ( -1 == lpos ) { return ""; }

	int rpos = find_any( test, rhs, lpos+1 );

	if ( -1 == rpos ) { return ""; }

	return test.substring( lpos+1, rpos-lpos );
    }

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static String bytesToHex( byte[] data, int offset, int len ) {
	// -------------------------------------------------------------------------------
	StringBuffer buf = new StringBuffer();
        for (int i = 0; i < len; i++) { 
            int halfbyte = (data[i+offset] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i+offset] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    }

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static String bytesToHex( byte[] data ) {
	// -------------------------------------------------------------------------------
	return bytesToHex( data, 0, data.length );
    }

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static void hexToBytes( byte[] data, int offset, int len, String str ) {
	// -------------------------------------------------------------------------------
	if ( len == (str.length() / 2) ) {
	    for (int c=0; c<len; c++) {
		int j = c*2;
		short  S = Short.parseShort(str.substring( j, j+2 ), 16);
		data[c+offset] = (byte)S;
	    }
	} else {
	    System.err.println( "string length does not match requested data length" );
	}
    }

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static void hexToBytes( byte[] data, String str ) {
	// -------------------------------------------------------------------------------
        hexToBytes( data, 0, data.length, str );
    }

    // ===================================================================================
    /** Convert to string.
     *  <p>
     *  Returns a string with the representation of the numerical value.
     *
     *  @param val Numerical value.
     *  @return string representation of val.
     */
    // -----------------------------------------------------------------------------------
    public static String toString( float val ) {
	// -------------------------------------------------------------------------------
	return Float.toString( val );
    }

    // ===================================================================================
    /** Convert to string.
     *  <p>
     *  Returns a string with the representation of the numerical value.
     *
     *  @param val Numerical value.
     *  @return string representation of val.
     */
    // -----------------------------------------------------------------------------------
    public static String toString( double val ) {
	// -------------------------------------------------------------------------------
	return Double.toString( val );
    }

    // ===================================================================================
    /** Convert to string.
     *  <p>
     *  Returns a string with the representation of the numerical value.
     *
     *  @param val Numerical value.
     *  @return string representation of val.
     */
    // -----------------------------------------------------------------------------------
    public static String toString( byte val ) {
	// -------------------------------------------------------------------------------
	return Byte.toString( val );
    }

    // ===================================================================================
    /** Convert to string.
     *  <p>
     *  Returns a string with the representation of the numerical value.
     *
     *  @param val Numerical value.
     *  @return string representation of val.
     */
    // -----------------------------------------------------------------------------------
    public static String toString( short val ) {
	// -------------------------------------------------------------------------------
	return Short.toString( val );
    }

    // ===================================================================================
    /** Convert to string.
     *  <p>
     *  Returns a string with the representation of the numerical value.
     *
     *  @param val Numerical value.
     *  @return string representation of val.
     */
    // -----------------------------------------------------------------------------------
    public static String toString( int val ) {
	// -------------------------------------------------------------------------------
	return Integer.toString( val );
    }

    // ===================================================================================
    /** Convert to string.
     *  <p>
     *  Returns a string with the representation of the numerical value.
     *
     *  @param val Numerical value.
     *  @return string representation of val.
     */
    // -----------------------------------------------------------------------------------
    public static String toString( long val ) {
	// -------------------------------------------------------------------------------
	return Long.toString( val );
    }

    // ===================================================================================
    /** Convert to string.
     *  <p>
     *  Returns a string with the representation of the numerical value.
     *
     *  @param val Numerical value.
     *  @return string representation of val.
     */
    // -----------------------------------------------------------------------------------
    public static String toString( boolean val ) {
	// -------------------------------------------------------------------------------
	return Boolean.toString( val );
    }

    // ===================================================================================
    /** Convert to 8 byte real.
     *  <p>
     *  Parse a string for a 8 byte real value.
     *
     *  @param S reference to a String object.
     *  @return parsed 8 byte real value.
     */
    // -----------------------------------------------------------------------------------
    public static double asReal8( String S ) throws NumberFormatException {
	// -------------------------------------------------------------------------------
	return Double.parseDouble( S );
    }

    // ===================================================================================
    /** Convert to 4 byte real.
     *  <p>
     *  Parse a string for a 4 byte real value.
     *
     *  @param S reference to a String object.
     *  @return parsed 4 byte real value.
     */
    // -----------------------------------------------------------------------------------
    public static float asReal4( String S ) throws NumberFormatException {
	// -------------------------------------------------------------------------------
	return Float.parseFloat( S );
    }

    // ===================================================================================
    /** Convert to 8 bit integer.
     *  <p>
     *  Parse a string for a 8 bit integer value.
     *
     *  @param S reference to a String object.
     *  @return parsed 8 bit integer value.
     */
    // -----------------------------------------------------------------------------------
    public static byte asInt8( String S ) throws NumberFormatException {
	// -------------------------------------------------------------------------------
	return Byte.parseByte( S );
    }

    // ===================================================================================
    /** Convert to 16 bit integer.
     *  <p>
     *  Parse a string for a 16 bit integer value.
     *
     *  @param S reference to a String object.
     *  @return parsed 16 bit integer value.
     */
    // -----------------------------------------------------------------------------------
    public static short asInt16( String S ) throws NumberFormatException {
	// -------------------------------------------------------------------------------
	return Short.parseShort( S );
    }

    // ===================================================================================
    /** Convert to 32 bit integer.
     *  <p>
     *  Parse a string for a 32 bit integer value.
     *
     *  @param S reference to a String object.
     *  @return parsed 32 bit integer value.
     */
    // -----------------------------------------------------------------------------------
    public static int asInt32( String S ) throws NumberFormatException {
	// -------------------------------------------------------------------------------
	return Integer.parseInt( S );
    }

    // ===================================================================================
    /** Convert to 64 bit integer.
     *  <p>
     *  Parse a string for a 64 bit integer value.
     *
     *  @param S reference to a String object.
     *  @return parsed 64 bit integer value.
     */
    // -----------------------------------------------------------------------------------
    public static long asInt64( String S ) throws NumberFormatException {
	// -------------------------------------------------------------------------------
	return Long.parseLong( S );
    }

    // ===================================================================================
    /** Convert to Logical.
     *  <p>
     *  Parse a string for a Logical value.
     *
     *  @param S reference to a String object.
     *  @return parsed Logical value.
     */
    // -----------------------------------------------------------------------------------
    public static boolean asBoolean( String S ) {
	// -------------------------------------------------------------------------------
	return Boolean.parseBoolean( S );
    }



    // ===================================================================================
    /** @brief Convert to a list.
     *  @param S reference to String.
     *  @return parsed list of strings.
     *
     *  Take a String that is a comma separated list contained within (), [], or {}
     *  and return a list of the elements as a string.
     */
    // -----------------------------------------------------------------------------------
    public static String[] asStringList( String S ) {
	// -------------------------------------------------------------------------------

	String istr = containedBy( S, "([{", ")]}" );

	String[] mlist = istr.split(",");

	for ( int i=0; i<mlist.length; i++ ) {
	    mlist[i] = mlist[i].trim();
	}

	return mlist;
    }

    // ===================================================================================
    /** @brief Convert to a real list.
     *  @param S reference to String object.
     *  @return parsed list of 4 byte real.
     *
     *  Parse a string for a list of 4 byte reals.
     */
    // -----------------------------------------------------------------------------------
    public static float[]  asReal4List( String S ) {
	// -------------------------------------------------------------------------------
	String[] temp = asStringList(S);

	float[] mlist = new float[temp.length];

	for ( int i=0; i<temp.length; i++ ) {
	    mlist[i] = asReal4( temp[i] );
	}

	return mlist;
    }

    // ===================================================================================
    /** @brief Convert to a real list.
     *  @param S reference to String object.
     *  @return parsed list of 8 byte real.
     *
     *  Parse a string for a list of 8 byte reals.
     */
    // -----------------------------------------------------------------------------------
    public static double[] asReal8List( String S ) {
	// -------------------------------------------------------------------------------
	String[] temp = asStringList(S);

	double[] mlist = new double[temp.length];

	for ( int i=0; i<temp.length; i++ ) {
	    mlist[i] = asReal8( temp[i] );
	}

	return mlist;
    }

    // ===================================================================================
    /** @brief Convert to a integer list.
     *  @param S reference to String object.
     *  @return parsed list of 8 bit integer.
     *
     *  Parse a string for a list of 8 bit integer.
     */
    // -----------------------------------------------------------------------------------
    public static byte[]   asInt8List( String S ) {
	// -------------------------------------------------------------------------------
	String[] temp = asStringList(S);

	byte[] mlist = new byte[temp.length];

	for ( int i=0; i<temp.length; i++ ) {
	    mlist[i] = asInt8( temp[i] );
	}

	return mlist;
    }

    // ===================================================================================
    /** @brief Convert to a integer list.
     *  @param S reference to String object.
     *  @return parsed list of 16 bit integer.
     *
     *  Parse a string for a list of 16 bit integer.
     */
    // -----------------------------------------------------------------------------------
    public static short[]  asInt16List( String S ) {
	// -------------------------------------------------------------------------------
	String[] temp = asStringList(S);

	short[] mlist = new short[temp.length];

	for ( int i=0; i<temp.length; i++ ) {
	    mlist[i] = asInt16( temp[i] );
	}

	return mlist;
    }

    // ===================================================================================
    /** @brief Convert to a integer list.
     *  @param S reference to String object.
     *  @return parsed list of 32 bit integer.
     *
     *  Parse a string for a list of 32 bit integer.
     */
    // -----------------------------------------------------------------------------------
    public static int[]    asInt32List( String S ) {
	// -------------------------------------------------------------------------------
	String[] temp = asStringList(S);

	int[] mlist = new int[temp.length];

	for ( int i=0; i<temp.length; i++ ) {
	    mlist[i] = asInt32( temp[i] );
	}

	return mlist;
    }

    // ===================================================================================
    /** @brief Convert to a integer list.
     *  @param S reference to String object.
     *  @return parsed list of 64 bit integer.
     *
     *  Parse a string for a list of 64 bit integer.
     */
    // -----------------------------------------------------------------------------------
    public static long[]   asInt64List( String S ) {
	// -------------------------------------------------------------------------------
	String[] temp = asStringList(S);

	long[] mlist = new long[temp.length];

	for ( int i=0; i<temp.length; i++ ) {
	    mlist[i] = asInt64( temp[i] );
	}

	return mlist;
    }




    // ===================================================================================
    /** @brief Convert to string.
     *  @param list reference to an array.
     *  @return string representation of lst.
     *
     *  Returns a string with the representation of the numerical list.
     */
    // -----------------------------------------------------------------------------------
    public static String toString( float[] list ) {
	// -------------------------------------------------------------------------------
	String str = "[" + toString(list[0]);

	for ( int i=1; i<list.length; i++ ) {
	    str = str + "," + toString(list[i]);
	}

	return str + "]";
    }

    // ===================================================================================
    /** @brief Convert to string.
     *  @param list reference to an array.
     *  @return string representation of lst.
     *
     *  Returns a string with the representation of the numerical list.
     */
    // -----------------------------------------------------------------------------------
    public static String toString( double[] list ) {
	// -------------------------------------------------------------------------------
	String str = "[" + toString(list[0]);

	for ( int i=1; i<list.length; i++ ) {
	    str = str + "," + toString(list[i]);
	}

	return str + "]";
    }

    // ===================================================================================
    /** @brief Convert to string.
     *  @param list reference to an array.
     *  @return string representation of lst.
     *
     *  Returns a string with the representation of the numerical list.
     */
    // -----------------------------------------------------------------------------------
    public static String toString( byte[] list ) {
	// -------------------------------------------------------------------------------
	String str = "[" + toString(list[0]);

	for ( int i=1; i<list.length; i++ ) {
	    str = str + "," + toString(list[i]);
	}

	return str + "]";
    }

    // ===================================================================================
    /** @brief Convert to string.
     *  @param list reference to an array.
     *  @return string representation of lst.
     *
     *  Returns a string with the representation of the numerical list.
     */
    // -----------------------------------------------------------------------------------
    public static String toString( short[] list ) {
	// -------------------------------------------------------------------------------
	String str = "[" + toString(list[0]);

	for ( int i=1; i<list.length; i++ ) {
	    str = str + "," + toString(list[i]);
	}

	return str + "]";
    }

    // ===================================================================================
    /** @brief Convert to string.
     *  @param list reference to an array.
     *  @return string representation of lst.
     *
     *  Returns a string with the representation of the numerical list.
     */
    // -----------------------------------------------------------------------------------
    public static String toString( int[] list ) {
	// -------------------------------------------------------------------------------
	String str = "[" + toString(list[0]);

	for ( int i=1; i<list.length; i++ ) {
	    str = str + "," + toString(list[i]);
	}

	return str + "]";
    }

    // ===================================================================================
    /** @brief Convert to string.
     *  @param list reference to an array.
     *  @return string representation of lst.
     *
     *  Returns a string with the representation of the numerical list.
     */
    // -----------------------------------------------------------------------------------
    public static String toString( long[] list ) {
	// -------------------------------------------------------------------------------
	String str = "[" + toString(list[0]);

	for ( int i=1; i<list.length; i++ ) {
	    str = str + "," + toString(list[i]);
	}

	return str + "]";
    }

}


// =======================================================================================
// **                                S T R I N G T O O L                                **
// ======================================================================== END FILE =====
