// ====================================================================== BEGIN FILE =====
// **                          J T E S T _ M L _ E N T R O P Y                          **
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

package org.trncmp.test;

import java.util.List;
import java.util.LinkedList;

import org.trncmp.mllib.Entropy;
import org.trncmp.lib.RunStat;
import org.trncmp.lib.FileTools;

// =======================================================================================
public class jtest_ml_entropy {
  // -------------------------------------------------------------------------------------

  static final int SAMPLES = 10000000;

  static final int max_index = 31415;

  // =====================================================================================
  static boolean testIndex( ) {
    // -----------------------------------------------------------------------------------
    Entropy ent = Entropy.getInstance();

    int min_val = ent.index( max_index );
    int max_val = ent.index( max_index );

    for ( int i=0; i<SAMPLES; i++ ) {
      int x = ent.index( max_index );
      if ( x < min_val ) { min_val = x; }
      if ( x > max_val ) { max_val = x; }
    }

    System.out.format( "Index min expect: %8d got: %8d\n", 0,           min_val );
    System.out.format( "      max expect: %8d got: %8d\n", max_index-1, max_val );
    
    return true;
  }


    
  // =====================================================================================
  static boolean testUniform( ) {
    // -----------------------------------------------------------------------------------
    Entropy ent = Entropy.getInstance();

    double min_val = ent.uniform();
    double max_val = ent.uniform();

    for ( int i=0; i<SAMPLES; i++ ) {
      double x = ent.uniform();
      if ( x < min_val ) { min_val = x; }
      if ( x > max_val ) { max_val = x; }
    }

    System.out.format( "Uniform min expect: %15.13f got: %15.13f\n", 0.0, min_val );
    System.out.format( "        max expect: %15.13f got: %15.13f\n", 1.0, max_val );
    
    return true;
  }


    
  // =====================================================================================
  static boolean testBool( ) {
    // -----------------------------------------------------------------------------------
    Entropy ent = Entropy.getInstance();

    int count_false = 0;
    int count_true  = 0;

    for ( int i=0; i<SAMPLES; i++ ) {
      if ( ent.bool() ) {
        count_true += 1;
      } else {
       count_false += 1;
      } 
    }

    int nt = SAMPLES/2;
    int nf = SAMPLES - nt;
    
    System.out.format( "Boolean false expect: %d got: %d\n", nf, count_false );
    System.out.format( "        true  expect: %d got: %d\n", nt, count_true );

    double rf = (double)count_false / (double)SAMPLES;
    double rt = (double)count_true  / (double)SAMPLES;

    System.out.format( "Ratio   false expect: %f got: %f\n", 0.5, rf );
    System.out.format( "        true  expect: %f got: %f\n", 0.5, rt );
    
    return true;
  }


    
  // =====================================================================================
  static boolean testBoolWeight( ) {
    // -----------------------------------------------------------------------------------
    Entropy ent = Entropy.getInstance();

    double ratio = 1.0e0 / 3.0e0;

    int count_false = 0;
    int count_true  = 0;

    for ( int i=0; i<SAMPLES; i++ ) {
      if ( ent.bool( ratio ) ) {
        count_true += 1;
      } else {
       count_false += 1;
      } 
    }

    int nt = (int)Math.floor( ratio * (double)SAMPLES + 5.0e-1 );
    int nf = SAMPLES - nt;
    
    System.out.format( "Weighted false expect: %d got: %d\n", nf, count_false );
    System.out.format( "         true  expect: %d got: %d\n", nt, count_true );

    double rf = (double)count_false / (double)SAMPLES;
    double rt = (double)count_true  / (double)SAMPLES;

    System.out.format( "Ratio    false expect: %f got: %f\n", 1.0e0 - ratio, rf );
    System.out.format( "         true  expect: %f got: %f\n", ratio, rt );
    
    return true;
  }


  // =====================================================================================
  static boolean testGaussian( ) {
    // -----------------------------------------------------------------------------------
    Entropy ent = Entropy.getInstance();

    double mu = 7.0;
    double mn = mu - 0.8;
    double mx = mu + 0.8;
    double sg = 0.2;

    RunStat S = new RunStat();

    for ( int i=0; i<SAMPLES; i++ ) {
      S.update( ent.gauss( mn, mx, mu, sg ) );
    }

    System.out.format( "Min Value expect %6.3f got %22.18f\n", mn, S.minv() );
    System.out.format( "Max Value expect %6.3f got %22.18f\n", mx, S.maxv() );
    System.out.format( "Mean      expect %6.3f got %22.18f\n", mu, S.mean() );
    System.out.format( "Sigma     expect %6.3f got %22.18f\n", sg, Math.sqrt( S.var() ) );

    return true;
  }
  
    
  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------

    System.out.println( "ML Entropy" );
    System.out.println( "\n  Test Index:    "+((testIndex())   ? "PASS\n" : "FAIL\n") );
    System.out.println( "\n  Test Uniform:  "+((testUniform()) ? "PASS\n" : "FAIL\n") );
    System.out.println( "\n  Test Boolean:  "+((testBool()) ? "PASS\n" : "FAIL\n") );
    System.out.println( "\n  Test Weighted: "+((testBoolWeight()) ? "PASS\n" : "FAIL\n") );
    System.out.println( "\n  Test Gaussian: "+((testGaussian()) ? "PASS\n" : "FAIL\n") );
    
    System.exit(0);
  }

} // end class jtest_ml_entropy

// =======================================================================================
// **                          J T E S T _ M L _ E N T R O P Y                          **
// ======================================================================== END FILE =====

