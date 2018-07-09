// ====================================================================== BEGIN FILE =====
// **                                 J T E S T _ P C A                                 **
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
 * @file PCA.java
 *  Provides interface and methods to perform Principle Component Analysis.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2018-04-20
 */
// =======================================================================================

package org.trncmp.test;

import java.io.*;

import org.trncmp.lib.*;
import org.trncmp.lib.linear.*;

import org.apache.log4j.*;

// =======================================================================================
public class jtest_pca {
  // -------------------------------------------------------------------------------------

  public final Dice dd = Dice.getInstance();
  public final int num_sample = 1000;

  public Mat3 fwdR = null;
  public Mat3 rvsR = null;

  public double[][] table_orig     = null;
  public double[][] table_true_pca = null;

  Vec3 mu  = null;
  Vec3 sig = null;
    
  Statistics.multi stat = null;
  
  // =====================================================================================
  public jtest_pca() {
    // -----------------------------------------------------------------------------------
//    double a =  Math2.N_PI_2*dd.normal();
//    double b =  Math2.N_PI_2*dd.normal();
//    double c =  Math2.N_PI_2*dd.normal();

    double a =  Math2.DEG2RAD * 45.0;
    double b =  Math2.DEG2RAD * 10.0;
    double c =  Math2.DEG2RAD * 30.0;

    mu  = new Vec3( 35.0, 72.0, 51.0 );
    sig = new Vec3( 4.0, 1.0, 0.5 );

    Mat3 R1 = linalg.rot1( a );
    Mat3 R2 = linalg.rot2( b );
    Mat3 R3 = linalg.rot3( c );

    Mat3 tmp  = new Mat3();
    fwdR = new Mat3();

    linalg.dot( tmp,  R2, R1 );
    linalg.dot( fwdR, R3, tmp );

    R1 = linalg.rot1( -a );
    R2 = linalg.rot2( -b );
    R3 = linalg.rot3( -c );

    rvsR = new Mat3();

    linalg.dot( tmp,  R2, R3 );
    linalg.dot( rvsR, R1, tmp );
  }

  
  // =====================================================================================
  public void build_tables() {
    // -----------------------------------------------------------------------------------

    table_orig     = new double[num_sample][3];
    table_true_pca = new double[num_sample][3];

    Vec3 V   = new Vec3();
    Vec3 W   = new Vec3();

    for ( int i=0; i<num_sample; i++ ) {
      V.x = sig.x*dd.normal();
      V.y = sig.y*dd.normal();
      V.z = sig.z*dd.normal();

      table_true_pca[i][0]  = V.x;
      table_true_pca[i][1]  = V.y;
      table_true_pca[i][2]  = V.z;

      linalg.dot( W, rvsR, V );

      table_orig[i][0] = W.x + mu.x;
      table_orig[i][1] = W.y + mu.y;
      table_orig[i][2] = W.z + mu.z;
    }
    
    stat = new Statistics.multi( 3 );

    stat.compile(   table_orig, num_sample, 3 );
    stat.extra(     table_orig, num_sample, 3 );
    stat.correlate( table_orig, num_sample, 3 );
    stat.invert_covariance( );

    plot( "/tmp/test.org.dat", table_orig );
    plot( "/tmp/test.tru.dat", table_true_pca );
    
  }

  
  // =====================================================================================
  public void plot( String fspc, double[][] data ) {
    // -----------------------------------------------------------------------------------
    PrintStream ps = FileTools.openWrite( fspc );
    for ( int i=0; i<num_sample; i++ ) {
      ps.format( "%.6f %.6f\n", data[i][0], data[i][1] );
    }
    ps.close();
  }






  

  // =====================================================================================
  public void test01() {
    // -----------------------------------------------------------------------------------

    double[][] table_pca = new double[num_sample][3];
    double[][] table_rec = new double[num_sample][3];
    double[]   input     = new double[3];
    double[]   output    = new double[3];

    PCA pca = new PCA();

    pca.compileFromSamples( table_orig );

    for ( int i=0; i<num_sample; i++ ) {
      for ( int j=0; j<3; j++ ) {
        input[j] = table_orig[i][j];
      }
      pca.transform( output, input );
      for ( int j=0; j<3; j++ ) {
        table_pca[i][j] = output[j];
      }
    }

    
    for ( int i=0; i<num_sample; i++ ) {
      for ( int j=0; j<3; j++ ) {
        input[j] = table_pca[i][j];
      }
      pca.recover( output, input );
      for ( int j=0; j<3; j++ ) {
        table_rec[i][j] = output[j];
      }
    }

    
    plot( "/tmp/test.pca.dat", table_pca );
    plot( "/tmp/test.rec.dat", table_rec );

    double mse = 0.0e0;
    for ( int i=0; i<num_sample; i++ ) {
      double dx = table_rec[i][0] - table_orig[i][0];
      double dy = table_rec[i][1] - table_orig[i][1];
      double dz = table_rec[i][2] - table_orig[i][2];
      mse += ( (dx*dx) + (dy*dy) + (dz*dz) );
    }

    System.out.println( "MSE="+mse );
  }

  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
    LogManager.getRootLogger().setLevel((Level) Level.WARN);

    jtest_pca JT = new jtest_pca();

    JT.build_tables();

    JT.test01();

    System.exit(0);
  }


} // end of jtest_pca class


// =======================================================================================
// **                                 J T E S T _ P C A                                 **
// ======================================================================== END FILE =====
