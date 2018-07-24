// ====================================================================== BEGIN FILE =====
// **                                    M E T R I C                                    **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2018, L3 Technologies Advanced Programs                            **
// **                      One Wall Street #1, Burlington, MA 01803                     **
// **                                                                                   **
// **  -------------------------------------------------------------------------------  **
// **                                                                                   **
// **  This file, and associated source code, is not free software; you may not         **
// **  redistribute it and/or modify it. This file is part of a research project        **
// **  that is in a development phase. No part of this research has been publicly       **
// **  distributed. Research and development for this project has been at the sole      **
// **  cost in both time and funding by L3 Technologies Advanced Programs.              **
// **                                                                                   **
// **  Any reproduction of computer software or portions thereof marked with this       **
// **  legend must also reproduce the markings.  Any person who has been provided       **
// **  access to such software must promptly notify L3 Technologies Advanced Programs.  **
// **                                                                                   **
// ----- Modification History ------------------------------------------------------------
/**
 * @file Metric.java
 * <p>
 * Provides set of real valued metrics.
 *
 * @date 2018-07-10
 *
 * ---------------------------------------------------------------------------------------
 *
 * @note This code was ported from a C++ version contained in the TRNCMP
 *       Machine learning Research Library. (formerly SolLib)
 *
 * @author Stephen W. Soliday
 * @date 2015-08-08
*/
// =======================================================================================

package org.trncmp.mllib.ea;

import java.io.PrintStream;


// =======================================================================================
/** @class Metric
 *  @brief Real valued metrics.
 *
 */
// ---------------------------------------------------------------------------------------
public class Metric {
  // -------------------------------------------------------------------------------------

  /** buffer containing the real valued parameters for this Metric. */
  protected double[] data = null;

  /** number of values in this metric. */
  protected int data_len = 0;

  /** number of values allocated from memory. */
  protected int alloc_len = 0;

  
  // =====================================================================================
  /** @brief Constructor.
   */
  // -------------------------------------------------------------------------------------
  public Metric() {
    // -----------------------------------------------------------------------------------
  }

  
  // =====================================================================================
  /** @brief Constructor.
   *  @param n number of elements in the metric.
   *
   *  Allocate space for a multi valued metric.
   */
  // -------------------------------------------------------------------------------------
  public Metric( int n ) {
    // -----------------------------------------------------------------------------------
    resize(n);
  }

  
  // =====================================================================================
  /** @brief Constructor.
   *  @param src pointer to a metric to copy.
   *
   *  Allocate clone of another metric.
   */
  // -------------------------------------------------------------------------------------
  public Metric( Metric src ) {
    // -----------------------------------------------------------------------------------
    resize( src.size() );
    copy( src );
  }

  
  // =====================================================================================
  /** @brief Constructor.
   *  @param n number of elements in the metric.
   *
   *  Allocate space for a multi valued metric. Does not reallocate memory if the new
   *  size is less than or equal to the previous allocation.
   */
  // -------------------------------------------------------------------------------------
  public void resize( int n ) {
    // -----------------------------------------------------------------------------------

    if ( n > alloc_len ) {
      data      = new double[n];
      alloc_len = n;
    }

    data_len = n;

    zero();
  }

  
  // =====================================================================================
  /** @brief Size.
   *  @return number of elements being used.
   */
  // -------------------------------------------------------------------------------------
  public int size( ) {
    // -----------------------------------------------------------------------------------
    return data_len;
  }

  
  // =====================================================================================
  /** @brief Get
   *  @param idx index of the desired metric element.
   *  @return metric element indexed by idx.
   */
  // -------------------------------------------------------------------------------------
  public double get( int idx ) {
    // -----------------------------------------------------------------------------------
    return data[idx];
  }

  
  // =====================================================================================
  /** @brief Set
   *  @param idx index of the metric element to be set.
   *  @param val value to set metric element.
   *  @return value to be set.
   */
  // -------------------------------------------------------------------------------------
  public double set( int idx, double val ) {
    // -----------------------------------------------------------------------------------
    return ( data[idx] = val );
  }


  // =====================================================================================
  /** @brief Zero.
   *
   *  Clear the values of the elements.
   */
  // -------------------------------------------------------------------------------------
  public void zero( ) {
    // -----------------------------------------------------------------------------------
    for ( int i=0; i<data_len; i++ ) {
      data[i] = 0.0e0;
    }    
  }


  // =====================================================================================
  /** @brief Copy.
   *  @param src refernce to a source Metric.
   *
   *  Perform an element by element copy of the source string.
   *  @note This is a deep copy.
   */
  // -------------------------------------------------------------------------------------
  public void copy( Metric src ) {
    // -----------------------------------------------------------------------------------
    if ( null == src ) {
      throw new NullPointerException("Metric.copy( src==NULL )");
    }

    resize( src.data_len );

    for ( int i=0; i<data_len; i++ ) {
      this.data[i] = src.data[i];
    }    
  }


  // =====================================================================================
  /** @brief Compare.
   *  @param rhs pointer to the RHS Metric.
   *  @return integer less than, equal to, or greater than zero if this is found,
   *          respectively, to be less than, to match, or be greater than rhs.
   *
   *  Perform an element by element compare of the RHS Metric.
   *
   *  < 0   if   this[i] < rhs[i]
   *  = 0   if   this[i] = rhs[i]
   *  > 0   if   this[i] > rhs[i]
   */
  // -------------------------------------------------------------------------------------
  public int compareTo( Metric rhs ) {
    // -----------------------------------------------------------------------------------
    if ( null == rhs ) {
      throw new NullPointerException("Metric.compare( rhs==NULL )");
    }

    if ( this.data_len != rhs.data_len ) {
      throw new ArrayIndexOutOfBoundsException("Metric.compare lengths do not match");
    }

    for ( int i=0; i<this.data_len; i++ ) {
      if ( this.data[i] < rhs.data[i] ) { return -(i+1); }
      if ( this.data[i] > rhs.data[i] ) { return   i+1;  }
    }
    return 0;
  }


  // =====================================================================================
  /** @brief Convert Metric to String.
   *  @param fmt edit descriptor of each element.
   *  @return white space separated values of the Metric elements
   */
  // -------------------------------------------------------------------------------------
  public String toString() {
    // -----------------------------------------------------------------------------------
    return format( "%11.4e", " " );
  }

  
  // =====================================================================================
  /** @brief Format Metric as a String.
   *  @param fmt edit descriptor of each element.
   *  @param dlm delimeter separating each element.
   *  @return white space separated values of the Metric elements
   */
  // -------------------------------------------------------------------------------------
  public String format( String fmt, String dlm ) {
    // -----------------------------------------------------------------------------------
    if ( 0 == data_len ) { return "{empty}"; }
    
    StringBuilder buffer = new StringBuilder(String.format( fmt, data[0] ));

    for ( int i=1; i<data_len; i++ ) {
      buffer.append( dlm );
      buffer.append( String.format( fmt, data[i] ) );
    }

    return buffer.toString();
  }

  
  // =====================================================================================
  /** @brief Sum of squares.
   *  @return the sum of the squares.
   *
   *  Sum the squares of each element of the metric array.
   */
  // -------------------------------------------------------------------------------------
  public double sumsq( ) {
    // -----------------------------------------------------------------------------------
    if ( null == data ) {
      throw new NullPointerException("This Metric was instantiated {empty}");
    }
	
    double sum = 0.0e0;

    for ( int i=0; i<data.length; i++ ) {
      sum += ( data[i] * data[i] );
    }

    return sum;
  }

  
  // =====================================================================================
  /** @brief Mean square.
   *  @return the mean square.
   *
   *  Compute the mean of the squares of the elements of the Metric.
   */
  // -------------------------------------------------------------------------------------
  public double mse( ) {
    // -----------------------------------------------------------------------------------
    if ( null == data ) {
      throw new NullPointerException("This Metric was instantiated {empty}");
    }

    return sumsq() / ( double ) data_len;
  }

  
  // =====================================================================================
  /** @brief Normalize.
   *  @return the scaling factor.
   *
   *  Normalize in place the elements of the Metric.
   */
  // -------------------------------------------------------------------------------------
  public double normalize( ) {
    // -----------------------------------------------------------------------------------
    if ( null == data ) {
      throw new NullPointerException("This Metric was instantiated {empty}");
    }

    double div = Math.sqrt( sumsq() );

    for ( int i=0; i<data_len; i++ ) {
      data[i] /= div;
    }

    return div;
  }


} // end class Metric


// =======================================================================================
// **                                    M E T R I C                                    **
// ======================================================================== END FILE =====
