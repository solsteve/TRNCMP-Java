// ====================================================================== BEGIN FILE =====
// **                         J T E S T _ B P N N _ M A T R I X                         **
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
 * @file jtest_bpnn.java
 *
 * @author Stephen W. Soliday
 * @date 2018-12-23
 */
// =======================================================================================

package org.trncmp.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.trncmp.mllib.nn.BPNN_matrix;
import org.trncmp.lib.Dice;
import org.trncmp.lib.Math2;
import org.trncmp.lib.StopWatch;

// =======================================================================================
// ---------------------------------------------------------------------------------------
public class jtest_bpnn_matrix {
  // -------------------------------------------------------------------------------------

  static String TData = "data/Iris/iris.onehot";
  
  static double[][] iris_input  = null;
  static double[][] iris_output = null;

  static int   num_sample = 0;
  static int   num_input  = 0;
  static int   num_output = 0;
  static int[] num_hidden = { 7, 5 };

  static BPNN_matrix  net = null;

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  static void ReadData() {
    // -----------------------------------------------------------------------------------
    
    InputStream is    = null;
    int         count = 0;

    try {
      is = new FileInputStream( new File( TData ) );
    } catch( FileNotFoundException e ) {
      System.err.println( "ReadData: cannot open "+TData+" for reading" );
      System.exit(1);
    }

    try {
      Scanner scan = new Scanner( is );

      num_sample = scan.nextInt();
      num_input  = scan.nextInt();
      num_output = scan.nextInt();
      count += 1;

      iris_input  = new double[num_sample][num_input];
      iris_output = new double[num_sample][num_output];
      
      for ( int i=0; i<num_sample; i++ ) {
        for ( int j=0; j<num_input; j++ ) {
          iris_input[i][j] = scan.nextDouble();
        }
        for ( int j=0; j<num_output; j++ ) {
          iris_output[i][j] = scan.nextDouble();
        }
        count += 1;
      }
      
    } catch( InputMismatchException e1 ) {
      System.err.println( "ReadData: value error on line: "+count );
      System.exit(2);
    }

    try {
      is.close();
    } catch( IOException e ) {
      System.err.println( "ReadData: cannot close "+TData+" after reading" );
      System.exit(2);
    }

  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  static void BuildNet() {
    // -----------------------------------------------------------------------------------
    net = new BPNN_matrix( num_input, num_hidden, num_output );
    net.randomize( 0.01 );
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  static void TrainNet() {
    // -----------------------------------------------------------------------------------

    double mse = net.mse( iris_input, iris_output );
    if ( 0.0 > mse ) {
      System.out.format( "Error at 0: %d\n", -(int)mse );
      System.exit(1);
    } else {
      System.out.format( "0: %13.6e\n", mse );
    }


    StopWatch SW = new StopWatch();
    SW.reset();
    
    for ( int i=1; i<100000; i++ ) {
      net.backpropagate( iris_input, iris_output, 0.3 );
//      if ( 0 == ( i % 1000 ) ) {
//        mse = net.mse( iris_input, iris_output );
//        if ( 0.0 > mse ) {
//          System.out.format( "Error at %d: %d\n", i, -(int)mse );
//          System.exit(1);
//        } else {
//          System.out.format( "%d: %13.6e\n", i, mse );
//        }
//      }
    }

    double elap = SW.seconds();

    mse = net.mse( iris_input, iris_output );
    if ( 0.0 > mse ) {
      System.out.format( "Error at 100000: %d\n", -(int)mse );
      System.exit(1);
    } else {
      System.out.format( "100000: %13.6e\n", mse );
    }    

    System.out.format( "%f seconds\n", elap );
  }


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  static void ValidateNet() {
    // -----------------------------------------------------------------------------------

  }


  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------
    byte[] ss = { 3,1,4,1,5,9,2,6,5,3,5 };
    Dice.getInstance().seed_set(ss);
    
    ReadData();

    BuildNet();
    
    TrainNet();

    ValidateNet();
    
    System.exit(0);
  }

} // end jtest_bpnn


// =======================================================================================
// **                         J T E S T _ B P N N _ M A T R I X                         **
// ======================================================================== END FILE =====
