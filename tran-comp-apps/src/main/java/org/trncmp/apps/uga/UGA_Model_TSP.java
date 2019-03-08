// ====================================================================== BEGIN FILE =====
// **                             U G A _ M O D E L _ T S P                             **
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
 * @file UGA_Model_TSP.java
 * <p>
 * Test model for a TSP valued Genetic Algorithm.
 *
 * @date 2018-07-20
 *
 * ---------------------------------------------------------------------------------------
 *
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 *
 * @author Stephen W. Soliday
 * @date 2015-08-16
 */
// =======================================================================================

package org.trncmp.apps.uga;

import java.util.Scanner;
import java.io.File;

import org.trncmp.lib.ConfigDB;
import org.trncmp.lib.Math2;
import org.trncmp.lib.PSGraph;
import org.trncmp.lib.PSDraw;
import org.trncmp.lib.PSColor;

import org.trncmp.mllib.ea.Model;
import org.trncmp.mllib.Entropy;
import org.trncmp.mllib.ea.Metric;
import org.trncmp.mllib.ea.Encoding;
import org.trncmp.mllib.ea.PMXEncoding;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
// ---------------------------------------------------------------------------------------
public class UGA_Model_TSP extends Model {
  // -------------------------------------------------------------------------------------

  static final Logger logger = LogManager.getLogger();

  protected ConfigDB.Section cfg_sec;

  protected double[][]  weight = null;
  protected double[]    xco    = null;
  protected double[]    yco    = null;
  protected int         num    = 0;

  protected PMXEncoding temp   = null;
  protected ConfigDB config = null;

  // =====================================================================================
  public UGA_Model_TSP( ConfigDB cdb ) {
    // -----------------------------------------------------------------------------------
    super();
    config = cdb;
  }

  
  // =====================================================================================
  /** @brief Metric allocation.
   *  @return pointer to a new metric allocation.
   *
   *  Place code here to allocate and set up metrics for this model.
   */
  // -------------------------------------------------------------------------------------
  public Metric alloc_metric( ) {
    // -----------------------------------------------------------------------------------
    return new Metric( 2 );
  }

  
  // =====================================================================================
  /** @brief Encoding allocation.
   *  @return pointer to a new encoding allocation.
   *
   *  Place code here to allocate and set up encoding for this model.
   */
  // -------------------------------------------------------------------------------------
  public Encoding alloc_encoding( ) {
    // -----------------------------------------------------------------------------------
    return (Encoding) new PMXEncoding( num );
  }

  
  // =====================================================================================
  /** @brief Initialize.
   *  @param cfg_sec pointer to a ConfigDB database object.
   *
   *  This is where you need to initialize your model prior to evolution.
   */
  // -------------------------------------------------------------------------------------
  public boolean config( ) {
    // -----------------------------------------------------------------------------------
    String fspc = null;
    try {
      cfg_sec = config.get( "TSP" );

      fspc = cfg_sec.get( "tspfile" );

      Scanner input = new Scanner( new File(fspc) );

      num = input.nextInt();

      System.err.printf( "Number of nodes: %d\n", num );

      weight = new double[num][num];
      for ( int r=0; r<num; r++ ) {
        for ( int c=0; c<num; c++ ) {
          weight[r][c] = input.nextDouble();
        }
      }

      xco = new double[ num ];
      yco = new double[ num ];

      int idx;

      for ( int i=0; i<num; i++ ) {
        idx = input.nextInt();
        if ( idx < num ) {
          xco[idx] = input.nextDouble();
          yco[idx] = input.nextDouble();
        } else {
          logger.error( "index on line "+(num+2+i)+" of "+fspc+" is to large" );
          System.exit(1);
        }
      }
	
      input.close();

      return false;
    } catch( FileNotFoundException e1 ) {
      logger.error( "Cannot find "+fspc+" - "+e1.toString() );
      // } catch( IOException e2 ) {
      //     logger.error( "Cannot close "+fspc+" - "+e2.toString() );
    } catch( ConfigDB.NoSuchKey e1 ) {
      logger.error( e1.toString() );
    } catch( ConfigDB.NoSection e2 ) {
      logger.error( e2.toString() );
    }
    return true;
  }

  
  // =====================================================================================
  public void save( Encoding dummy ) {
    // -----------------------------------------------------------------------------------
    logger.warn( "Save is not implemented here" );
  }

  
  // =====================================================================================
  /** @brief Display.
   *  @param msg message to prefix.
   *  @param M   pointer to a uga::Metric.
   *  @param E   pointer to a uga::Encoding.
   *
   *  This function may be called at anytime, but should be called once after the evolution
   *  cycle, using the UGA::display_model( UGA::best, std::cout ) function.
   *
   *  You should convert the UGA representation (i.e. TSP -1 < x < +1) into something
   *  meaningful to your model.
   */
  // -------------------------------------------------------------------------------------
  public void display_short( String msg, Metric M, Encoding E ) {
    // -----------------------------------------------------------------------------------
    System.out.format( "%s : %10.3f | %s\n", msg, M.get(0), E.format( "%d", ", " ) );
  }


  // =====================================================================================
  /** @brief Execute.
   *  @param M pointer to a uga::Metric.
   *  @param E pointer to a uga::Encoding.
   *
   *  This is where you run one instance of your model and generate metrics.
   *  Optimization is important this function fill be called once for each population
   *  member every generation. pop=500 gen=1000, this function will execute half a million
   *  times. Try not to do any disk IO.
   */
  // -------------------------------------------------------------------------------------
  public void execute( Metric M, Encoding E ) {
    // -----------------------------------------------------------------------------------
    PMXEncoding param = (PMXEncoding)E;

    double sum = Math2.N_ZERO;

    for ( int i=1; i<num; i++ ) {
      sum += weight[param.get(i-1)][param.get(i)];
    }
    sum += weight[param.get(num-1)][param.get(0)];

    M.set( 0, sum );
  }

  
  // =====================================================================================
  /** @brief Example TSP Metric.
   *  @param lhs pointer to a uga::Metric object.
   *  @param rhs pointer to a uga::Metric object.
   *  @return true if the lhs set of metrics is better than the rhs set.
   *
   *  This is probably the most important function. It determines which of two metrics is
   *  better. lhs = left hand side. rhs = right and side. Return a true if lhs is a better
   *  set of metrics than rhs. Optimization is critical. Imagine that pop=500 gen=1000 and
   *  tour=7. This function could be called over 3 million times.
   */
  // -------------------------------------------------------------------------------------
  public boolean isLeftBetter( Metric lhs, Metric rhs ) {
    // -----------------------------------------------------------------------------------

    if ( lhs.get(0) < rhs.get(0) ) { return true; }
    if ( lhs.get(0) > rhs.get(0) ) { return false; }

    return true;
  }

  
  // =====================================================================================
  /** @brief Threshold.
   *  @param M pointer to a uga::Metric set.
   *  @return true is the metric set is good enough.
   *
   *  You may use this to prematurely terminate the evolution if the metric set is 
   *  good enough. Just return a false if you do not want this feature.
   */
  // -------------------------------------------------------------------------------------
  public boolean meetsThreshold( Metric M ) {
    // -----------------------------------------------------------------------------------
    double x = M.get(0);
    if ( 1.0e-14 > x ) { return true; }
    return false;
  }

  
  // =====================================================================================
  /** @brief Find.
   *  @param dst pointer to a destination encoding.
   *  @param src pointer to a encoding.
   *  @param t value to test for.
   *  @return index where t was found.
   *
   *  Copy every element from src into dst, and return the index that holds t.
   */
  // -------------------------------------------------------------------------------------
  protected static int copy_find( PMXEncoding dst, PMXEncoding src, int t ) {
    // -----------------------------------------------------------------------------------
    int m = src.size();
    int r = 0;
    for ( int j=0; j<m; j++ ) {
      int s = src.get( j );
      if ( t == s ) { r = j; }
      dst.set( j, s );
    } 
    return r;
  }

  
  // =====================================================================================
  /** @brief Pre-process.
   *  @param A pointer to an array of encodings.
   *  @param n number of encodings in the array.
   *
   *  Apply this algorithm to every member of the population.
   */
  // -------------------------------------------------------------------------------------
  public void pre_process( Encoding[] A, int n ) {
    // -----------------------------------------------------------------------------------
    if ( null == temp ) { temp = (PMXEncoding)alloc_encoding(); }

    int m = temp.size();

    for ( int i=0; i<n; i++ ) {
      int z = copy_find( temp, (PMXEncoding)(A[i]), 0 );
    
      for ( int j=0; j<m; j++ ) {
        ((PMXEncoding)(A[i])).set( j, temp.get( (j + z) % m ) );
      }
    }
  }

  
  // =====================================================================================
  /** @brief Run After..
   *  @param BM pointer to the best metric.
   *  @param BE pointer to the best encoding.
   *  @param WM pointer to the worst metric.
   *  @param WE pointer to the worst encoding.
   *
   *  Apply this algorithm after the max-gen has been reached (or the threshold trips).
   */
  // -------------------------------------------------------------------------------------
  public void run_after( Metric dum1, Encoding BE,
                         Metric dum2, Encoding dum3 ) {
    // -----------------------------------------------------------------------------------

    double minX = xco[0];
    double maxX = xco[0];
    double minY = yco[0];
    double maxY = yco[0];

    for ( int i=1; i<num; i++ ) {
      if ( xco[i] < minX ) { minX = xco[i]; }
      if ( xco[i] > maxX ) { maxX = xco[i]; }
      if ( yco[i] < minY ) { minY = yco[i]; }
      if ( yco[i] > maxY ) { maxY = yco[i]; }
    }

    double difX = maxX - minX;
    double difY = maxY - minY;

    double width  = 9.0;
    double height = (width * difY / difX) * 1.5;

    PSGraph ps = new PSGraph(1);

    PSDraw pd = new PSDraw( width, height, minX, minY, maxX, maxY );

    //pd.drawBorder();

    difX /= 300.0;
    difY /= 100.0;

    for ( int i=0; i<num; i++ ) {
      pd.drawEllipse( xco[i], yco[i], difX, difY, 0.0 );
    }

    int p1, p2;

    if ( null == temp ) { temp = (PMXEncoding)alloc_encoding(); }

    try {
      String optfile = cfg_sec.get( "optpath" );

      Scanner input = new Scanner( new File( optfile ) );

      int on = input.nextInt();
      
      int[] path = new int[on];
      for ( int i=0; i<on; i++ ) {
        path[i] = input.nextInt();
        temp.set( i, path[i] );
      }

      pd.setRGB( PSColor.red );

      for ( int i=1; i<num; i++ ) {
        p1 = path[i-1];
        p2 = path[i];
	
        pd.drawLine( xco[p1], yco[p1], xco[p2], yco[p2] );
      }
      p1 = path[num-1];
      p2 = path[0];
      pd.drawLine( xco[p1], yco[p1], xco[p2], yco[p2] );

    } catch( FileNotFoundException e1 ) {
      logger.error( e1.toString() );
    } catch( ConfigDB.NoSuchKey e ) {}

    pd.setRGB( PSColor.blue );

    PMXEncoding param = (PMXEncoding)BE;

    System.out.print( param.get(0) );
    for ( int i=1; i<num; i++ ) {
      System.out.print( " "+param.get(i) );
      p1 = param.get(i-1);
      p2 = param.get(i);

      pd.drawLine( xco[p1], yco[p1], xco[p2], yco[p2] );
    }
    System.out.println( "" );
    p1 = param.get(num-1);
    p2 = param.get(0);
    pd.drawLine( xco[p1], yco[p1], xco[p2], yco[p2] );
    
    ps.add( pd, 0, 1.0, 2.0 );

    try {
      String pfile = cfg_sec.get( "plot" );
      ps.pswrite( pfile );
    } catch( ConfigDB.NoSuchKey e) {
      ps.pswrite( "tsp.ps" );
    }

    // ----- display optimal -------------------------------------------------------------

    Metric M = alloc_metric();

    execute( M, temp );

    display_long( "Optimal", M, temp );
  }

  
}


// =======================================================================================
// **                             U G A _ M O D E L _ T S P                             **
// ======================================================================== END FILE =====
