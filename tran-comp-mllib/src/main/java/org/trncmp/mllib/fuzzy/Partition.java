// ====================================================================== BEGIN FILE =====
// **                                 P A R T I T I O N                                 **
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
 * @file Partition.java
 * <p>
 * Provides an interface and methods for a fuzzy partition.
 *
 *   NEG    SN  ZERO  SP    POS
 *  ____                    ____ 1
 *      \   /\   /\   /\   /
 *       \ /  \ /  \ /  \ /
 *        X    X    X    X          This partition contains 5 fuzzy functions
 *       / \  / \  / \  / \         1 LeftTrapezoid 3 Triangle, and 1 RightTrapezoid
 *  ____/   \/   \/   \/   \____
 *  ============================ 0
 *
 * @date 2018-08-09
 *
 * ---------------------------------------------------------------------------------------
 *
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 *
 * @author Stephen W. Soliday
 * @date 2014-06-27
 */
// =======================================================================================

package org.trncmp.mllib.fuzzy;

import java.io.PrintStream;
import java.io.BufferedReader;
import java.util.Scanner;

import org.trncmp.lib.Math2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class Partition {
  // -------------------------------------------------------------------------------------

  private static final Logger logger = LogManager.getLogger();

  protected Function[] functions = null;
  protected int        num_func =  0;
  protected double     min_ctr  = -1.0e0;
  protected double     max_ctr  =  1.0e0;

  
  public Partition() { logger.info( "new empty partition" ); }

  public int      size()            { return num_func; }
  public Function function( int i ) { return functions[i]; }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected void destroy() {
    // -----------------------------------------------------------------------------------
    if ( null != functions ) {
      int n = functions.length;
      for ( int i=0; i<n; i++ ) {
        functions[i] = null;
      }
    }

    functions = null;
    num_func  =  0;
    min_ctr   = -1.0e0;
    max_ctr   =  1.0e0;
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  protected boolean resize( int n ) {
    // -----------------------------------------------------------------------------------
    logger.info( "request resize("+n+")" );

    if ( 1 > n ) {
      logger.warn( "There needs to be at least one function only got "+n );
      return false;
    }

    if ( n == num_func ) {
      logger.info( "Resize called with no change" );
      return true;
    }

    logger.info( "Resize results in change" );

    destroy();

    functions = new Function[n];
    num_func = n;

    for ( int i=0; i<n; i++ ) {
      functions[i] = null;
    }
    
    return true;
  }


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void set( double[] ctrs, int offset, int len ) {
    // -----------------------------------------------------------------------------------
    if ( resize( len ) ) {

      switch( len ) {
        case 0: // ----- this is an error ------------------------------------------------
          logger.error( "fuzzy.Partition.resize shoule have rejected this" );
          System.exit(1);

        case 1: // ----- special case n=1 ------------------------------------------------
          logger.info( "fuzzy.Partition.set(1)" );
          functions[0] = new TriangleFunction( ctrs[offset]-1.0e0,
                                               ctrs[offset],
                                               ctrs[offset]+1.0e0 );
          break;

        case 2: // ----- special case n=2 ------------------------------------------------
          logger.info( "fuzzy.Partition.set(2)" );
          functions[0] = new LeftTrapezoidFunction(  ctrs[offset],
                                                     ctrs[offset+1] );
          functions[1] = new RightTrapezoidFunction( ctrs[offset],
                                                     ctrs[offset+1] );
          break;

        case 3: // ----- special case n=3 ------------------------------------------------
          logger.info( "fuzzy.Partition.set(3)" );
          functions[0] = new LeftTrapezoidFunction(  ctrs[offset],
                                                     ctrs[offset+1] );
          functions[1] = new TriangleFunction(       ctrs[offset],
                                                     ctrs[offset+1],
                                                     ctrs[offset+2] );
          functions[2] = new RightTrapezoidFunction( ctrs[offset+1],
                                                     ctrs[offset+2] );
          break;

        default: // ----- general case n>3 -----------------------------------------------
          logger.info( "fuzzy.Partition.set("+len+")" );
          functions[0] = new LeftTrapezoidFunction(  ctrs[offset],
                                                     ctrs[offset+1] );

          for ( int i=1; i<len-1; i++ ) {
            functions[i] = new TriangleFunction( ctrs[offset+i-1],
                                                 ctrs[offset+i],
                                                 ctrs[offset+i+1] );
          }
          
          functions[len-1] = new RightTrapezoidFunction( ctrs[offset+len-2],
                                                         ctrs[offset+len-1] );
          break;
      }

    } else {
      logger.error( "fuzzy.partition failed to instantiate" );
    }
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void set( double[] ctrs ) {
    // -----------------------------------------------------------------------------------
    set( ctrs, 0, ctrs.length );
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void set( double minv, double maxv ) {
    // -----------------------------------------------------------------------------------
    double[] temp = { minv, maxv };
    set( temp, 0, 2 );
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void set( int n, double minv, double maxv ) {
    // -----------------------------------------------------------------------------------
    double[] temp = new double[n];
    double   dx   = (maxv - minv)/(n-1);

    temp[0] = minv;
    for ( int i=1; i<n-1; i++ ) {
      temp[i] = temp[i-1] + dx;
    }
    temp[n-1] = maxv;
    
    set( temp, 0, n );
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void set( int n ) {
    // -----------------------------------------------------------------------------------
    set( n, -1.0e0, 1.0e0 );
  }



  // =====================================================================================
  /** @brief Membership.
   *  @param id index of the fuzzy functions in this partion.
   *  @param x crisp value.
   *  @return membersip of the crisp value in the fuzzy functions.
   */
  // -------------------------------------------------------------------------------------
  public double mu( int id, double x ) {
    // -----------------------------------------------------------------------------------
    return functions[id].mu(x);
  }

  
  // =====================================================================================
  /** @brief Area.
   *  @param id index of the fuzzy functions in this partion.
   *  @param d degree of membership in the fuzzy functions.
   *  @return the area of the functions under the membership.
   */
  // -------------------------------------------------------------------------------------
  public double area( int id, double d ) {
    // -----------------------------------------------------------------------------------
    return functions[id].area(d);
  }

  
  // =====================================================================================
  /** @brief Center of Area.
   *  @param id index of the fuzzy functions in this partion.
   *  @param d degree of membership in the fuzzy functions.
   *  @return center of area of the fuzzy functions under the degree of membership.
   */
  // -------------------------------------------------------------------------------------
  public double coa( int id, double d ) {
    // -----------------------------------------------------------------------------------
    return functions[id].coa(d);
  }

  
  // =====================================================================================
  /** @brief Membership.
   *  @param degree return array of memberships.
   *  @param x crisp value.
   *
   *  Return, through the supplied array, the membership in each functions, in this
   *  partition, associated with the crisp value x.
   */
  // -------------------------------------------------------------------------------------
  public void mu( double[] degree, int offset, double x ) {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<num_func; i++ ) {
      degree[offset+i] = functions[i].mu(x);
    }
  }

  
  // =====================================================================================
  /** @brief Area.
   *  @param degree array of degrees of membership for each functions in this partition.
   *  @return sum of the areas.
   *
   *  Compute the sum of the areas of each functions in this partition, under the
   *  degrees of membership in the array.
   */
  // -------------------------------------------------------------------------------------
  public double area( double[] degree, int offset ) {
    // -----------------------------------------------------------------------------------
    double a = 0.0e0;
    for ( int i=0; i<num_func; i++ ) {
      a += functions[i].area(degree[offset+i]);
    }
    return a;
  }

  
  // =====================================================================================
  /** @brief Center of Area.
   *  @param id index of the fuzzy functions in this partion.
   *  @param degree degree of membership in the fuzzy functions.
   *  @return center of area of the fuzzy functions under the degree of membership.
   */
  // -------------------------------------------------------------------------------------
  public double coa( double[] degree, int offset ) {
    // -----------------------------------------------------------------------------------
    double sumAX = Math2.N_EPSILON;
    double sumA  = Math2.N_EPSILON;
    for ( int i=0; i<num_func; i++ ) {
      double a = functions[i].area(degree[offset+i]);
      double x = functions[i].coa(degree[offset+i]);
      sumAX += ( a * x );
      sumA  +=   a;
    }
    return sumAX / sumA;
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public String toString( String fmt ) {
    // -----------------------------------------------------------------------------------
    StringBuffer buffer = new StringBuffer();

    buffer.append( functions[0].toString( fmt ) );
    for ( int i=1; i<num_func; i++ ) {
      buffer.append( " "+functions[i].toString( fmt ) );
    }
    
    return buffer.toString();
  }

  
  // =====================================================================================
  /** @brief Load.
   *  @param src source array.
   *  @param offset start index for source data.
   *  @return next index for available data.
   *
   *  Read the parameters for this partition from a source array starting at the
   *  provided offset.
   */
  // -------------------------------------------------------------------------------------
  public int load( double[] src, int offset ) {
    // -----------------------------------------------------------------------------------
    int idx = offset;
    
    for ( int i=0; i<num_func; i++ ) {
      idx = functions[i].load( src, idx );
    }

    return idx;
  }

  
  // =====================================================================================
  /** @brief Store.
   *  @param src source array.
   *  @param offset start index for destination data.
   *  @return next index for available data.
   *
   *  Read the parameters for this function from a source array starting at the
   *  provided offset.
   */
  // -------------------------------------------------------------------------------------
  public int store( double[] dst, int offset ) {
    // -----------------------------------------------------------------------------------
    int idx = offset;
    
    for ( int i=0; i<num_func; i++ ) {
      idx = functions[i].store( dst, idx );
    }

    return idx;    
  }


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void write( PrintStream ps ) {
    // -----------------------------------------------------------------------------------
    ps.format( "%d", num_func );
    for ( int i=0; i<num_func; i++ ) {
      ps.format( " %15.8e", functions[i].getCenter() );
    }
    ps.format( "\n" );
  }

  
  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void read( Scanner scan ) {
    // -----------------------------------------------------------------------------------
    int nf = scan.nextInt();

    double[] x = new double[nf];
    
    for ( int i=0; i<nf; i++ ) {
      x[i] = scan.nextDouble();
    }

    this.set( x, 0, nf );
  }
  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void read( BufferedReader br ) {
    // -----------------------------------------------------------------------------------
    read( new Scanner( br ) );
  }
  
  
} // end class Partition


// =======================================================================================
// **                                 P A R T I T I O N                                 **
// ======================================================================== END FILE =====
