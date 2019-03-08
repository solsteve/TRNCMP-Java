// ====================================================================== BEGIN FILE =====
// **                               F I X E D N O R M A L                               **
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
 * @brief   Produce fixed or normal distributions.
 * @file    FixedNormal.java
 *
 * @details Provides the interface and procedures for generating either a fixed number
 *          or a normal distribution.
 *
 * @date    2018-01-03
 */
// =======================================================================================

package org.trncmp.lib;

import org.trncmp.lib.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class FixedNormal {
  // -------------------------------------------------------------------------------------
  private static final Logger logger = LogManager.getLogger();

  protected boolean fixed      = true;
  protected double  mean_value = 0.5;
  protected double  std_dev    = 0.1;
  protected double  min_value  = 0.0;
  protected double  max_value  = 1.0;
  protected Dice    dd         = null;


  // =====================================================================================
  public String toString() {
    // -----------------------------------------------------------------------------------
    String rv = null;

    if ( fixed ) {
      rv = String.format("fixed = %f", mean_value);
    } else {
      rv = String.format("%f <= N(%f, %f) <= %f",
                         min_value, mean_value, std_dev, max_value );
    }

    return rv;
  }
    
  // =====================================================================================
  /** @brief Constructor.
   *  @param x the fixed value returned by this object.
   *
   *  Construct this object to return the same fixed value with each call to next().
   */
  // -------------------------------------------------------------------------------------
  public FixedNormal( double x ) {
    // -----------------------------------------------------------------------------------
    mean_value = x;
    fixed      = true;
    dd         = Dice.getInstance();
  }

  // =====================================================================================
  /** @brief Constructor.
   *  @param mean the man     value returned by this object.
   *  @param std  the standard deviation of values returned by this object.
   *
   *  Construct this object to return a normally distributed random variable with each
   *  call to next().
   */
  // -------------------------------------------------------------------------------------
  public FixedNormal( double mean, double std ) {
    // -----------------------------------------------------------------------------------
    mean_value = mean;
    std_dev    = std;
    min_value  = mean_value - 3.0*std_dev;
    max_value  = mean_value + 3.0*std_dev;
    fixed      = false;
    dd         = Dice.getInstance();
  }
    
  // =====================================================================================
  /** @brief Constructor.
   *  @param mean the man     value returned by this object.
   *  @param std  the standard deviation of values returned by this object.
   *  @param minv the minimum value returned by this object.
   *  @param maxv the maximum value returned by this object.
   *
   *  Construct this object to return a normally distributed random variable with each
   *  call to next().
   */
  // -------------------------------------------------------------------------------------
  public FixedNormal( double mean, double std, double minv, double maxv ) {
    // -----------------------------------------------------------------------------------
    mean_value = mean;
    std_dev    = std;
    min_value  = minv;
    max_value  = maxv;
    fixed      = false;
    dd         = Dice.getInstance();
  }
    
  // =====================================================================================
  /** @brief Constructor.
   *  @param param array [ mean, sigma, min, max ]
   *
   *  Construct this object to return a normally distributed random variable with each
   *  call to next().
   */
  // -------------------------------------------------------------------------------------
  public FixedNormal( double[] param ) {
    // -----------------------------------------------------------------------------------

    dd = Dice.getInstance();
    
    switch( param.length ) {
      case 1:
        mean_value = param[0];
        fixed      = true;
        break;
        
      case 2:
        mean_value = param[0];
        std_dev    = param[1];
        min_value  = mean_value - 3.0*std_dev;
        max_value  = mean_value + 3.0*std_dev;
        fixed      = false;
        break;
        
      case 4:
        mean_value = param[0];
        std_dev    = param[1];
        min_value  = param[2];
        max_value  = param[3];
        fixed      = false;
        break;
        
      default:
          logger.error( "FixedNormal: improper interval format, used" );
        break;
    }
  }
    
  // =====================================================================================
  /** @brief Constructor.
   *  @param params string consisting of either a fixed value or a list of [mean, std]
   *
   *  If a list is given the minimum and maximum values will be 
   *  mean +/- 3*std.
   */
  // -------------------------------------------------------------------------------------
  public FixedNormal( String params ) {
    // -----------------------------------------------------------------------------------
    try {
      String[] list = StringTool.asStringList( params );
      int      n    = list.length;

      dd = Dice.getInstance();

      switch(n) {
        case 1:
          fixed      = true;	
          mean_value = StringTool.asReal8( params );
          break;
	    
        case 2:
          fixed      = false;	
          mean_value = StringTool.asReal8( list[0] );
          std_dev    = StringTool.asReal8( list[1] );
          min_value  = mean_value - 3.0*std_dev;
          max_value  = mean_value + 3.0*std_dev;
          break;
	    
        case 4:
          fixed      = false;	
          mean_value = StringTool.asReal8( list[0] );
          std_dev    = StringTool.asReal8( list[1] );
          min_value  = StringTool.asReal8( list[2] );
          max_value  = StringTool.asReal8( list[3] );
          break;

        default:
          logger.error( "FixedNormal: improper interval format, used: "+params );
          break;
      }
    } catch( java.lang.NumberFormatException e ) {
      System.err.println( e.toString() );
    }
  }

  // =====================================================================================
  /** @brief  Normal Distribution.
   *  @return if fixed=true return the mean,
   *          if false return normally distributed random number.
   */
  // -------------------------------------------------------------------------------------
  public double next( ) {
    // -----------------------------------------------------------------------------------
    if ( fixed ) { return mean_value; }
	
    double x = mean_value + std_dev*dd.normal();

    while ( ( x < min_value ) || ( x > max_value ) ) {
      x = mean_value + std_dev*dd.normal();
    }

    return x;
  }
    
  // =====================================================================================
  /** @brief  Normal Distribution.
   *  @return if fixed=true return the mean,
   *          if false return normally distributed random number.
   */
  // -------------------------------------------------------------------------------------
  public int nextInteger( ) {
    // -----------------------------------------------------------------------------------
    if ( fixed ) { return (int) Math.floor( mean_value + 0.5 ); }
	
    double x = Math.floor(mean_value + std_dev*dd.normal() + 0.5);

    while ( ( x < min_value ) || ( x > max_value ) ) {
      x = Math.floor(mean_value + std_dev*dd.normal() + 0.5);
    }

    return (int) x;
  }
    
  // =====================================================================================
  /** @brief  Normal Distribution.
   *  @return if fixed=true return the mean,
   *          if false return normally distributed random number.
   */
  // -------------------------------------------------------------------------------------
  public long nextLong( ) {
    // -----------------------------------------------------------------------------------
    if ( fixed ) { return (long) Math.floor( mean_value + 0.5 ); }
	
    double x = Math.floor(mean_value + std_dev*dd.normal() + 0.5);

    while ( ( x < min_value ) || ( x > max_value ) ) {
      x = Math.floor(mean_value + std_dev*dd.normal() + 0.5);
    }

    return (long) x;
  }
}

// =======================================================================================
// **                               F I X E D N O R M A L                               **
// =========================================================================== END FILE ==
