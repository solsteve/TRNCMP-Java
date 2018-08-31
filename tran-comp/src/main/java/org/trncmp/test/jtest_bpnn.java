// ====================================================================== BEGIN FILE =====
// **                                J T E S T _ B P N N                                **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2019, Stephen W. Soliday                                           **
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
 * @date 2018-08-27
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

import org.trncmp.mllib.nn.BPNN;
import org.trncmp.lib.Dice;

// =======================================================================================
// ---------------------------------------------------------------------------------------
public class jtest_bpnn {
  // -------------------------------------------------------------------------------------

  static String TData = "data/Iris/iris.onehot";
  
  static double[][] iris_input  = null;
  static double[][] iris_output = null;

  static int num_sample = 0;
  static int num_input  = 0;
  static int num_output = 0;
  static int num_hidden = 0;

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
  static void TrainNet() {
    // -----------------------------------------------------------------------------------

    int num_rep  = 100;
    int report   =  1000;
    double eta   = 0.36;
    double alpha = 0.5;

    BPNN net = new BPNN.Builder().io(num_input,num_output).hidden(num_input*num_output).build();

    Dice.getInstance().seed_set();

    net.init_weights( eta );

    net.reset();

    int g = 0;
    double err = 0.0e0;
    for ( int k=0; k<num_rep; k++ ) {
      for ( int j=0; j<report; j++ ) {
        err = 0.0e0;
        for ( int i=0; i<num_sample; i++ ) {
          net.forward_pass( iris_input[i] );
          err += net.backwards_pass( iris_input[i], iris_output[i] );
        }
        net.update( alpha/(double)num_sample );
        g += 1;
      }
      System.out.format( "%d\t%g\n", g, err );
    }
    
}


  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------

    ReadData();
    TrainNet();
    
    System.exit(0);
  }

} // end jtest_bpnn


// =======================================================================================
// **                                J T E S T _ B P N N                                **
// ======================================================================== END FILE =====
