// ====================================================================== BEGIN FILE =====
// **                          J T E S T _ B I N C O N V E R T                          **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2018, Stephen W. Soliday                                           **
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
 * @file jtest_binconvert.java
 *  Provides Unit Test the conversion of data into and out of binary.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2018-09-10
 */
// =======================================================================================

package org.trncmp.test;

import java.nio.ByteBuffer;

// =======================================================================================
public class jtest_binconvert {
  // -------------------------------------------------------------------------------------

  
  // =====================================================================================
  public static void test01() {
    // -----------------------------------------------------------------------------------
    double origValue = 3.14159265e23;

    long   bitrep    = Double.doubleToLongBits( origValue );

    String hex = Long.toHexString( bitrep );

    System.out.println( hex );
    
    long   received  = bitrep;
    double testValue = Double.longBitsToDouble( received );

    double dif = origValue - testValue;

    System.out.println( "Conversion error = "+(dif*dif) );

  }


  // =====================================================================================
  public static void test02() {
    // -----------------------------------------------------------------------------------
    double V1 =  3.141592653589793e+23;
    double V2 = -2.718281828459045e-18;

    ByteBuffer bufferOut = ByteBuffer.allocate( Double.BYTES * 2 );
    ByteBuffer bufferIn  = ByteBuffer.allocate( Double.BYTES * 2 );

    bufferOut.putDouble( V1 );
    bufferOut.putDouble( V2 );

    byte[] xferOut = bufferOut.array();
    byte[] xferIn  = bufferIn.array();
    int    n    = xferOut.length;

    System.out.println( "Transfer "+n+" bytes" );

    for ( int j=0; j<n; j++ ) {
      xferIn[j] = xferOut[j];
    }

    double X1 = bufferIn.getDouble();
    double X2 = bufferIn.getDouble();

    double d1 = V1 - X1;
    double d2 = V2 - X2;

    double mse = (d1*d1)+(d2*d2);

    System.out.println( "Conversion error = "+mse );

     X1 = bufferIn.getDouble();
     X2 = bufferIn.getDouble();

     d1 = V1 - X1;
     d2 = V2 - X2;

     mse = (d1*d1)+(d2*d2);

    System.out.println( "Conversion error = "+mse );

  }


  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------

    test02();
    
    System.exit(0);
  }

}

// =======================================================================================
// **                          J T E S T _ B I N C O N V E R T                          **
// ======================================================================== END FILE =====

