// ====================================================================== BEGIN FILE =====
// **                                   S T A T M O D                                   **
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
 * @file StatMod.java
 *  Provides interface and methods to reproduce various gaussian distributions.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2018-03-31
 */
// =======================================================================================

package org.trncmp.lib;

import java.io.*;
import org.trncmp.lib.linear.*;

import org.hipparchus.linear.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class StatMod {
  // -------------------------------------------------------------------------------------

  private static final Logger logger = LogManager.getLogger();
  private static final Dice   dd     = Dice.getInstance();

  private static final double c_epsilon = 1.0e-8;

  
  // =====================================================================================
  /** @brief Single independent gaussian distribution.
   */
  // -------------------------------------------------------------------------------------
  public static class single {
    // -----------------------------------------------------------------------------------

    protected double mean_value = 1.0;  /**< Mean value of the sampled set         */
    protected double std_dev    = 0.1;  /**< Standard deviation of the sampled set */
    protected double min_value  = 0.0;  /**< Minimum value of the sampled set      */
    protected double max_value  = 0.5;  /**< Maximum value of the sampled set      */


    public double getMean()  { return mean_value; }
    public double getSigma() { return std_dev; }
    public double getMin()   { return min_value; }
    public double getMax()   { return max_value; }
    
    // ===================================================================================
    /** @brief Validate.
     *
     *  validate the model parameters make sense.
     */
    // -----------------------------------------------------------------------------------
    protected void validate() {
      // ---------------------------------------------------------------------------------
      if ( max_value < min_value ) {
        System.err.format( "Max value %f is less then the minimum value %f\n",
                           max_value, min_value );
      }

      if ( mean_value < min_value ) {
        System.err.format( "Mean %f is less then the minimum value %f\n",
                           mean_value, min_value );
      }

      if ( mean_value > max_value ) {
        System.err.format( "Mean %f is greater then the maximum value %f\n",
                           mean_value, max_value );
      }
    }

    
    // ===================================================================================
    /** @brief Constructor.
     */
    // -----------------------------------------------------------------------------------
    public single( ) {
      // ---------------------------------------------------------------------------------
    }

    // ===================================================================================
    /** @brief Constructor.
     *  @param br reference to a BufferedReader
     *
     *  Build parameters by sampling data read from a file.
     */
    // -----------------------------------------------------------------------------------
    public single( BufferedReader br ) {
      // ---------------------------------------------------------------------------------
      read( br );
    }

    
    // ===================================================================================
    /** @brief Constructor.
     *  @param mu mean value of the distribution.
     *  @param sd standard deviation of the mean.
     *
     *  Set mean and standard deviation from the arguments. Set min_value and max_value
     *  as +/- six standard deviations.
     */
    // -----------------------------------------------------------------------------------
    public single( double mu, double sd ) {
      // ---------------------------------------------------------------------------------
      mean_value = mu;
      std_dev    = sd;
      min_value  = mu - 6.15*sd;
      max_value  = mu + 6.15*sd;
      validate();
    }

    
    // ===================================================================================
    /** @brief Constructor.
     *  @param mu mean value of the distribution.
     *  @param sd standard deviation of the mean.
     *  @param mn minimum value of the distribution.
     *  @param mx maximum value of the distribution.
     *
     *  Set all parameters from the arguments.
     */
    // -----------------------------------------------------------------------------------
    public single( double mu, double sd, double mn, double mx ) {
      // ---------------------------------------------------------------------------------
      mean_value = mu;
      std_dev    = sd;
      min_value  = mn;
      max_value  = mx;
      validate();
    }

    
    // ===================================================================================
    /** @brief Constructor.
     *  @param x array of sample values.
     *
     *  Build parameters by sampling data.
     */
    // -----------------------------------------------------------------------------------
    public single( double[] x ) {
      // ---------------------------------------------------------------------------------
      int    n  = x.length;
      double nf = (double) n;

      min_value  = x[0];
      max_value  = x[0];
      mean_value = x[0];

      for ( int i=1; i<n; i++ ) {
        double t = x[i];
        mean_value += t;
        if ( min_value > t ) { min_value = t; }
        if ( max_value < t ) { max_value = t; }
      }

      mean_value /= nf;

      double d  = x[0] - mean_value;
      double sd = d*d;

      for ( int i=1; i<n; i++ ) {
        d   = x[i] - mean_value;
        sd += (d*d);
      }

      std_dev = Math.sqrt( sd / (nf - 1.0) );
    }

    
    // ===================================================================================
    /** @brief Display
     *  @param ps  reference to a print stream.
     *  @param fmt edit descripter to format the fields.
     *
     *  Display the model parameters on a print stream.
     */
    // -----------------------------------------------------------------------------------
    public void display( PrintStream ps, String fmt ) {
      // ---------------------------------------------------------------------------------
      ps.format( "Model Parameters\n" );
      ps.format( "  [ " );
      ps.format( fmt, min_value  );
      ps.format( " <= N( " );
      ps.format( fmt, mean_value );
      ps.format( ", " );
      ps.format( fmt, std_dev    );
      ps.format( " ) <= " );
      ps.format( fmt, max_value  );
      ps.format( " ]\n" );
    }

    
    // ===================================================================================
    /** @brief Display
     *  @param ps reference to a print stream.
     *
     *  Display the model parameters on a print stream.
     */
    // -----------------------------------------------------------------------------------
    public void display( PrintStream ps ) {
      // ---------------------------------------------------------------------------------
      display( ps, "%g" );
    }

    
    // ===================================================================================
    /** @brief Next Value.
     *  @return random number.
     *
     *  Generate the next random number that satisfies this distribution.
     */
    // -----------------------------------------------------------------------------------
    public double next() {
      // ---------------------------------------------------------------------------------

      double t = mean_value + std_dev * dd.normal();

      while ( (t < min_value) || (max_value < t) ) {
        t = mean_value + std_dev * dd.normal();
      }

      return t;
    }

    
    // ===================================================================================
    /** @brief Write
     *  @param ps reference to a print stream.
     *
     *  Write parameters to a model file.
     */
    // -----------------------------------------------------------------------------------
    public void write( PrintStream ps ) {
      // ---------------------------------------------------------------------------------
      ps.format( "%17.10e|%17.10e|%17.10e|%17.10e\n",
                 mean_value, std_dev, min_value, max_value );
    }

    
    // ===================================================================================
    /** @brief Read
     *  @param br reference to a buffered reader.
     *
     *  Read parameters from a model file.
     */
    // -----------------------------------------------------------------------------------
    public void read( BufferedReader br ) {
      // ---------------------------------------------------------------------------------
      try {
        String line = br.readLine();
        USMTF.Record rec = new USMTF.Record( line );
        if ( 4 == rec.size() ) {
          mean_value = StringTool.asReal8( rec.field(0) );
          std_dev    = StringTool.asReal8( rec.field(1) );
          min_value  = StringTool.asReal8( rec.field(2) );
          max_value  = StringTool.asReal8( rec.field(3) );
        } else {
          logger.error( "Line {"+line+"} is not a valid StatMod.single: "+rec.size() );
        }
      } catch( java.io.IOException e ) {
        logger.error( e.toString() );
      }
    }

    
  } // end of StatMod.single class


  // =====================================================================================
  /** @brief Multiple Correlated Gaussian Distribution.
   */
  // -------------------------------------------------------------------------------------
  public static class multi {
    // -----------------------------------------------------------------------------------
   
    public    int      n_var    = 0;    //< number of variables
    protected PCA      pca      = null; //< Principle Component Analysis
    protected double[] min_axis = null; //< minimum uncorrelated value
    protected double[] max_axis = null; //< maximum uncorrelated value
    protected double[] work_vec = null; //< work space

     public double getMean( int i )  { return pca.getMean(i); }

    // ===================================================================================
    /** @brief Constructor.
     */
    // -----------------------------------------------------------------------------------
    public multi() {
      // ---------------------------------------------------------------------------------
    }
    
    // ===================================================================================
    /** @brief Constructor.
     *  @param br reference to a BufferedReader
     *
     *  Build parameters by sampling data read from a file.
     */
    // -----------------------------------------------------------------------------------
    public multi( BufferedReader br ) {
      // ---------------------------------------------------------------------------------
      read( br );
    }
    
    // ===================================================================================
    /** @brief Constructor.
     *  @param x array of sample values.
     *
     *  Build parameters by sampling data.
     */
    // -----------------------------------------------------------------------------------
    public multi( double[][] data ) {
      // ---------------------------------------------------------------------------------
      compile( data );
    }
    
    // ===================================================================================
    /** @brief Compile.
     *  @param data array of sample values.
     *
     *  Build parameters by sampling data.
     */
    // -----------------------------------------------------------------------------------
    public void compile( double[][] data ) {
      // ---------------------------------------------------------------------------------
      int    m    = data.length;    // number of samples
      int    n    = data[0].length; // number of variables

      n_var = n;

      pca = new PCA();

      pca.compileFromSamples( data );

      // ----- compute the uncorellated bounds -------------------------------------------

      min_axis = new double[n];
      max_axis = new double[n];
      for ( int j=0; j<n; j++ ) {
        min_axis[j] =  0.0;
        max_axis[j] =  0.0;
      }

      work_vec = new double[n];
      
      for ( int i=0; i<m; i++ ) {
        pca.transform( work_vec, data[i] );

        for ( int j=0; j<n; j++ ) {
          if ( min_axis[j] > work_vec[j] ) { min_axis[j] = work_vec[j]; }
          if ( max_axis[j] < work_vec[j] ) { max_axis[j] = work_vec[j]; }
        }
      }

    }


    // ===================================================================================
    /** @brief Display
     *  @param ps  reference to a print stream.
     *  @param fmt edit descripter to format the fields.
     *
     *  Display the model parameters on a print stream.
     */
    // -----------------------------------------------------------------------------------
    public void display( PrintStream ps, String fmt ) {
      // ---------------------------------------------------------------------------------
      ps.format( "\nModel Parameters (%d)\n\n", n_var );
      ps.println( "Min Value = " + array.toString( min_axis, fmt ) ); 
      ps.println( "Max Value = " + array.toString( max_axis, fmt ) ); 
      ps.format( "\n" );
      pca.report( ps, fmt );
    }
    

    // ===================================================================================
    /** @brief Display
     *  @param ps reference to a print stream.
     *
     *  Display the model parameters on a print stream.
     */
    // -----------------------------------------------------------------------------------
    public void display( PrintStream ps ) {
      // ---------------------------------------------------------------------------------
      display( ps, "%g" );
    }


    // ===================================================================================
    /** @brief Next Value.
     *  @param Vector to receive random numbers.
     *
     *  Generate the next random number that satisfies this distribution.
     */
    // -----------------------------------------------------------------------------------
    public void next( Vector T ) {
      // ---------------------------------------------------------------------------------
      for ( int i=0; i<n_var; i++ ) {
        do {
          work_vec[i] = Math.sqrt(pca.getVariance(i))*dd.normal();
        } while ( ( work_vec[i] < min_axis[i] ) ||
                  ( work_vec[i] > max_axis[i] ) );
      }
      pca.recover( T.x, work_vec );
    }
    

    // ===================================================================================
    /** @brief Next Value.
     *  @param Vector to receive random numbers.
     *
     *  Generate the next random number that satisfies this distribution.
     */
    // -----------------------------------------------------------------------------------
    public Vector next() {
      // ---------------------------------------------------------------------------------
      Vector vec = new Vector( n_var );
      next( vec );
      return vec;
    }
    

     // ===================================================================================
    /** @brief Write
     *  @param ps reference to a PrintStream.
     *
     *  Write the model state to a PrintStream.
     */
    // -----------------------------------------------------------------------------------
    public void write( PrintStream ps ) {
      // ---------------------------------------------------------------------------------
      String fmt  = new String( "%17.10e" );
      String cdlm = new String( "|" );
      String rdlm = new String( "\n" );
      ps.format( "%s\n", array.toString( pca.getMean(),       fmt, cdlm ) );
      ps.format( "%s\n", array.toString( min_axis,            fmt, cdlm ) );
      ps.format( "%s\n", array.toString( max_axis,            fmt, cdlm ) );
      ps.format( "%s\n", array.toString( pca.getCovariance(), fmt, rdlm, cdlm ) );  
    }


    // ===================================================================================
    /** @brief Read
     *  @param br reference to a BufferedReader.
     *
     *  Read the model state from a BufferedReader.
     */
    // -----------------------------------------------------------------------------------
    public void read( BufferedReader br ) {
      // ---------------------------------------------------------------------------------
      try {
        USMTF.Record rec  = new USMTF.Record( br.readLine() );
        n_var             = rec.size();
        work_vec          = new double[ n_var ];

        // ----- read mean ---------------------------------------------------------------
        double[] mu = new double[ n_var ];
        for ( int i=0; i<n_var; i++ ) {
          mu[i] = StringTool.asReal8( rec.field(i) );
        }

        // ----- read uncorrelated minimum -----------------------------------------------
        min_axis = new double[ n_var ];
        rec = new USMTF.Record( br.readLine() );
        for ( int i=0; i<n_var; i++ ) {
          min_axis[i] = StringTool.asReal8( rec.field(i) );
        }
        
        // ----- read uncorelated maximum ------------------------------------------------
        max_axis = new double[ n_var ];
        rec = new USMTF.Record( br.readLine() );
        for ( int i=0; i<n_var; i++ ) {
          max_axis[i] = StringTool.asReal8( rec.field(i) );
        }

        // ----- read variance-covariance matrix -----------------------------------------

        double [][] cov = new double[n_var][n_var];
        for ( int i=0; i<n_var; i++ ) {
          rec = new USMTF.Record( br.readLine() );
          for ( int j=0; j<n_var; j++ ) {
            cov[i][j] = StringTool.asReal8( rec.field(j) );
          }
        }

        pca = new PCA();
        pca.compileFromCovariance( cov, mu );
        
      } catch( java.io.IOException e ) {
        logger.error( e.toString() );
      }
    }

  } // end of StatMod.multi class

} // end of StatMod class


// =======================================================================================
// **                                   S T A T M O D                                   **
// ======================================================================== END FILE =====
