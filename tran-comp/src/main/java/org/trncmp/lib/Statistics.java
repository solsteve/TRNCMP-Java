// ====================================================================== BEGIN FILE =====
// **                                S T A T I S T I C S                                **
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
 * @brief   Statistics.
 * @file    Statistics.java
 *
 * @details Provides the interface and procedures for single and multi-dimensional
 *          Statistics. (This is an alias for legacy code)
 *
 * @author  Stephen W. Soliday
 * @date    2015-01-26 Original release.
 * @date    2017-09-28 Migration to the Proxima libraries.
 */
// =======================================================================================

package org.trncmp.lib;

import org.trncmp.lib.linear.Matrix;
import java.io.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class Statistics {
  // -----------------------------------------------------------------------------------

  private static final Logger logger = LogManager.getLogger();

  // ===================================================================================
  static public class single {
    // -------------------------------------------------------------------------------
    public int    count = 0;   //< Number of samples
    public double minv  = 0.0; //< Minimum sample value
    public double maxv  = 0.0; //< Maximum sample value
    public double mean  = 0.0; //< Mean value of sample
    public double std   = 0.0; //< Standard deviation of the mean
    public double adev  = 0.0; //< Absolute deviation of the mean
    public double var   = 0.0; //< Sample variance
    public double skew  = 0.0; //< Sample skew
    public double kurt  = 0.0; //< Sample kurt

    public int    minidx = -1;
    public int    maxidx = -1;

    protected boolean level1 = false;
    protected boolean level2 = false;


    // ===============================================================================
    public single() {
      // ---------------------------------------------------------------------------
      this.clear();
    }
	
    // ===============================================================================
    public void clear() {
      // ---------------------------------------------------------------------------
      this.count = 0;
      this.minv  = 0.0;
      this.maxv  = 0.0;
      this.mean  = 0.0;
      this.std   = 0.0;
      this.adev  = 0.0;
      this.var   = 0.0;
      this.skew  = 0.0;
      this.kurt  = 0.0;
      this.minidx = -1;
      this.maxidx = -1;

      this.level1 = false;
      this.level2 = false;
    }

    // ===============================================================================
    public void report( PrintStream fp, String fmt ) {
      // ---------------------------------------------------------------------------
      if ( this.level1 ) {
        fp.println( "Num:      " + count );
        fp.println( "MinValue: " + String.format( fmt, minv ) );
        fp.println( "MaxValue: " + String.format( fmt, maxv ) );
        fp.println( "Mean:     " + String.format( fmt, mean ) );
        fp.println( "Var:      " + String.format( fmt, var  ) );

        if ( this.level2 ) {	    
          fp.println( "Std Dev:  " + String.format( fmt, std  ) );
          fp.println( "Avg Dev:  " + String.format( fmt, adev ) );
          fp.println( "Skew:     " + String.format( fmt, skew ) );
          fp.println( "Kurt:     " + String.format( fmt, kurt ) );
        }
      }
    }

    // ===================================================================================
    public boolean compile( double[] sample, int nrows ) {
      // -------------------------------------------------------------------------------
      this.level1 = false;

      this.mean   = Math2.N_ZERO;
      this.minv   = Math2.N_MAX_POS;
      this.maxv   = Math2.N_MAX_NEG;
      this.minidx = nrows + 1;
      this.maxidx = nrows + 1;

      this.count = nrows;
      double N = (double)(this.count);

      for (int i=0; i<nrows; i++) {
        double x = sample[i];
        this.mean += x;
        if ( x < this.minv ) {
          this.minv   = x;
          this.minidx = i;
        }
        if ( x > this.maxv ) {
          this.maxv   = x;
          this.maxidx = i;
        }
      }

      this.mean /= N;

      this.var  = 0.0e0;

      for (int i=0; i<nrows; i++) {
        double x = (sample[i] - this.mean);
        this.var  += (x*x);
      }

      this.var  /= (N-1.0e0);


      if ( nrows < this.minidx ) {
        logger.error( "minimum value/index could not be found" );
        return true;
      }

      if ( nrows < this.maxidx ) {
        logger.error( "maximum value/index could not be found" );
        return true;
      }

      this.level1 = true;

      return false;
    }


    // ===============================================================================
    /** @brief Compile Extras.
     *  @param S      pointer to to single stats data structure.
     *  @param data   pointer to data.
     *  @param num_samples number of samples.
     *  @param stride optional increment to next data element (defualt=1).
     *  @return true is error.
     *
     *  Compile the extra statistics data structure for a single dimensional data set.
     *  This static function calls the protected constructor for single
     */
    // -------------------------------------------------------------------------------
    public boolean extra( double[] data, int num_samples ) {
      // ---------------------------------------------------------------------------
      this.level2 = false;
      double N = (double)(this.count);

      if ( ! this.level1 ) {
        logger.warn( "this requires that you call compile first" );
        if ( this.compile( data, num_samples ) ) {
          logger.warn( "   I tried to do it for you, but it failed" );
          return true;
        }
        logger.warn( "   I did it for you" );
      }

      if ( num_samples != this.count ) {
        logger.warn( "you should run extra with the same data count as compile" );
      }

      this.std = Math.sqrt(this.var);

      this.adev = Math2.N_ZERO;
      this.skew = Math2.N_ZERO;
      this.kurt = Math2.N_ZERO;

      if (Math2.isNotZero(this.std)) {
        for (int i=0; i<num_samples; i++) {
          double sd = data[i] - this.mean;
          double x  = sd / this.std;
          double x3 = x*x*x;
          this.adev += Math2.Abs(sd);
          this.skew += x3;
          this.kurt += (x3*x);
        }

        this.adev /= N;
        this.skew /= N;
        this.kurt /= N;
        this.kurt -= 3.0e0;
      } else {
        logger.error( "Standard deviation was ZERO, no values computed" );
        return true;
      }

      this.level2 = true;

      return false;
    }




    
    // ===================================================================================
    public boolean compile( int[] labels, int id, double[] sample, int nrows ) {
      // -------------------------------------------------------------------------------
      this.level1 = false;

      this.mean   = Math2.N_ZERO;
      this.minv   = Math2.N_MAX_POS;
      this.maxv   = Math2.N_MAX_NEG;
      this.minidx = nrows + 1;
      this.maxidx = nrows + 1;

      this.count = 0;

      for (int i=0; i<nrows; i++) {
        if ( id == labels[i] ) {
          this.count += 1;
          double x = sample[i];
          this.mean += x;
          if ( x < this.minv ) {
            this.minv   = x;
            this.minidx = i;
          }
          if ( x > this.maxv ) {
            this.maxv   = x;
            this.maxidx = i;
          }
        }
      }

      this.mean /= (double)this.count;

      this.var  = 0.0e0;

      for (int i=0; i<nrows; i++) {
        if ( id == labels[i] ) {
          double x = (sample[i] - this.mean);
          this.var  += (x*x);
        }
      }

      this.var  /= (double)(this.count-1);


      if ( nrows < this.minidx ) {
        logger.error( "minimum value/index could not be found" );
        return true;
      }

      if ( nrows < this.maxidx ) {
        logger.error( "maximum value/index could not be found" );
        return true;
      }

      this.level1 = true;

      return false;
    }


    // ===============================================================================
    /** @brief Compile Extras.
     *  @param S      pointer to to single stats data structure.
     *  @param data   pointer to data.
     *  @param num_samples number of samples.
     *  @param stride optional increment to next data element (defualt=1).
     *  @return true is error.
     *
     *  Compile the extra statistics data structure for a single dimensional data set.
     *  This static function calls the protected constructor for single
     */
    // -------------------------------------------------------------------------------
    public boolean extra( int[] labels, int id, double[] data, int num_samples ) {
      // ---------------------------------------------------------------------------
      this.level2 = false;
      double N = (double)(this.count);

      if ( ! this.level1 ) {
        logger.warn( "this requires that you call compile first" );
        if ( this.compile( data, num_samples ) ) {
          logger.warn( "   I tried to do it for you, but it failed" );
          return true;
        }
        logger.warn( "   I did it for you" );
      }

      if ( num_samples != this.count ) {
        logger.warn( "you should run extra with the same data count as compile" );
      }

      this.std = Math.sqrt(this.var);

      this.adev = Math2.N_ZERO;
      this.skew = Math2.N_ZERO;
      this.kurt = Math2.N_ZERO;

      if (Math2.isNotZero(this.std)) {
        for (int i=0; i<num_samples; i++) {
          if ( id == labels[i] ) {
            double sd = data[i] - this.mean;
            double x  = sd / this.std;
            double x3 = x*x*x;
            this.adev += Math2.Abs(sd);
            this.skew += x3;
            this.kurt += (x3*x);
          }
        }

        this.adev /= N;
        this.skew /= N;
        this.kurt /= N;
        this.kurt -= 3.0e0;
      } else {
        logger.error( "Standard deviation was ZERO, no values computed" );
        return true;
      }

      this.level2 = true;

      return false;
    }

  }


  // ===================================================================================
  // -----------------------------------------------------------------------------------
  static public class multi {
    // -------------------------------------------------------------------------------

    public int      nvar  = 0;    //< number of dimensions
    public int      count = 0;    //< Number of samples
    public double[] minv  = null; //< Minimum sample value
    public double[] maxv  = null; //< Maximum sample value
    public double[] mean  = null; //< Mean value of sample
    public double[] std   = null; //< Standard deviation of the mean
    public double[] adev  = null; //< Absolute deviation of the mean
    public double[] var   = null; //< Sample variance
    public double[] skew  = null; //< Sample skew
    public double[] kurt  = null; //< Sample kurt

    public int[]    minidx = null;
    public int[]    maxidx = null;

    public Matrix covariance  = null;   //< Covariance  matrix
    public Matrix correlation = null;   //< Covariance  matrix
    public Matrix inv_cov     = null;   //< Inverse Covariance  matrix

    public boolean invcov  = false;

    protected boolean level1  = false;
    protected boolean level2a = false;
    protected boolean level2b = false;

    // ===============================================================================
    // -------------------------------------------------------------------------------
    public multi( int n ) {
      // ---------------------------------------------------------------------------
      this.nvar   = n;
      this.minv   = new double[ n ];
      this.maxv   = new double[ n ];
      this.mean   = new double[ n ];
      this.std    = new double[ n ];
      this.adev   = new double[ n ];
      this.var    = new double[ n ];
      this.skew   = new double[ n ];
      this.kurt   = new double[ n ];

      this.minidx = new int[ n ];
      this.maxidx = new int[ n ];

      covariance  = new Matrix(n,n);
      correlation = new Matrix(n,n);
      inv_cov     = new Matrix(n,n);
	    
      invcov  = false;

      level1  = false;
      level2a = false;
      level2b = false;
    }


    // ===============================================================================
    // -------------------------------------------------------------------------------
    public void clear() {
      // ---------------------------------------------------------------------------
      for ( int i=0; i<this.nvar; i++ ) {
        this.minv[ i ]   = Math2.N_ZERO;
        this.maxv[ i ]   = Math2.N_ZERO;
        this.mean[ i ]   = Math2.N_ZERO;
        this.std [ i ]   = Math2.N_ZERO;
        this.adev[ i ]   = Math2.N_ZERO;
        this.var [ i ]   = Math2.N_ZERO;
        this.skew[ i ]   = Math2.N_ZERO;
        this.kurt[ i ]   = Math2.N_ZERO;
        this.minidx[ i ] = -1;
        this.maxidx[ i ] = -1;
      }

      covariance.set();
      correlation.set();
      inv_cov.set();
	    
      invcov  = false;
      level1  = false;
      level2a = false;
      level2b = false;
    }


    // ===============================================================================
    // -------------------------------------------------------------------------------
    public void report( PrintStream fp, String fmt ) {
      // ---------------------------------------------------------------------------
      fp.println( "Number of samples = "+count );
      fp.println( "Number of columns = "+nvar );
      fp.println( "Min Value = " + array.toString( minv, fmt ) );
      fp.println( "Max Value = " + array.toString( maxv, fmt ) );
      fp.println( "Mean      = " + array.toString( mean, fmt ) );
      fp.println( "Variance  = " + array.toString( var,  fmt ) );

      if ( this.level2a ) {
        fp.println( "Std Dev   = " + array.toString( std,  fmt ) );
        fp.println( "Abs Dev   = " + array.toString( adev, fmt ) );
        fp.println( "Skew      = " + array.toString( skew, fmt ) );
        fp.println( "Kurtosis  = " + array.toString( kurt, fmt ) );
      }

      if ( this.level2b ) {
        fp.println( "\nCovariance = \n"  + covariance.toString( fmt ) );
        fp.println( "\nCorrelation = \n" + correlation.toString( fmt ) );

        if ( this.invcov ) {
          fp.println( "\nInverse Covariance = \n"  + inv_cov.toString( fmt ) );
        }
      }
    }


    // ===============================================================================
    // -------------------------------------------------------------------------------
    public void report( PrintStream fp ) {
      // ---------------------------------------------------------------------------
      this.report( fp, "%g" );
    }


    // ===============================================================================
    // -------------------------------------------------------------------------------
    public boolean compile( double[][] sample, int nrows, int width ) {
      // ---------------------------------------------------------------------------
      this.level1 = false;

      for ( int c=0; c<width; c++ ) {
        this.mean[c]   = Math2.N_ZERO;
        this.minv[c]   = Math2.N_MAX_POS;
        this.maxv[c]   = Math2.N_MAX_NEG;
        this.minidx[c] = nrows + 1;
        this.maxidx[c] = nrows + 1;
      }
	
      this.count = nrows;
      double N   = (double)(this.count);

      for (int i=0; i<nrows; i++) {
        for ( int c=0; c<width; c++ ) {
          double x = sample[i][c];
          this.mean[c] += x;
          if ( x < this.minv[c] ) {
            this.minv[c]   = x;
            this.minidx[c] = i;
          }
          if ( x > this.maxv[c] ) {
            this.maxv[c]   = x;
            this.maxidx[c] = i;
          }
        }
      }

      for ( int c=0; c<width; c++ ) {
        this.mean[c] /= N;
        this.var[c]  = 0.0e0;
      }
		
      for (int i=0; i<nrows; i++) {
        for ( int c=0; c<width; c++ ) {
          double x = (sample[i][c] - this.mean[c]);
          this.var[c]  += (x*x);
        }
      }

      for ( int c=0; c<width; c++ ) {
        this.var[c]  /= (N-1.0e0);
      }

      for ( int c=0; c<width; c++ ) {
        if ( nrows < this.minidx[c] ) {
          logger.error( "minimum value/index could not be found" );
          return true;
        }

        if ( nrows < this.maxidx[c] ) {
          logger.error( "maximum value/index could not be found" );
          return true;
        }
      }

      this.level1 = true;

      return false;
    }


    // ===============================================================================
    // -------------------------------------------------------------------------------
    public boolean extra( double[][] data, int num_samples, int width ) {
      // ---------------------------------------------------------------------------
      this.level2a = false;
      double N = (double)(this.count);

      if ( ! this.level1 ) {
        logger.warn( "this requires that you call compile first" );
        if ( this.compile( data, num_samples, width ) ) {
          logger.warn( "   I tried to do it for you, but it failed" );
          return true;
        }
        logger.warn( "   I did it for you" );
      }

      if ( num_samples != this.count ) {
        logger.warn( "you should run extra with the same data count as compile" );
      }

      for ( int c=0; c<width; c++ ) {
        this.std[c]  = Math.sqrt(this.var[c]);
        this.adev[c] = Math2.N_ZERO;
        this.skew[c] = Math2.N_ZERO;
        this.kurt[c] = Math2.N_ZERO;
      }

      boolean rv = false;
      for ( int c=0; c<width; c++ ) {
        if (Math2.isNotZero(this.std[c])) {
          for (int i=0; i<num_samples; i++) {
            double sd = data[i][c] - this.mean[c];
            double x  = sd / this.std[c];
            double x3 = x*x*x;
            this.adev[c] += Math2.Abs(sd);
            this.skew[c] += x3;
            this.kurt[c] += (x3*x);
          }

          this.adev[c] /= N;
          this.skew[c] /= N;
          this.kurt[c] /= N;
          this.kurt[c] -= 3.0e0;
        } else {
          logger.error( "Standard deviation["+c+"] was ZERO, no values computed" );
          rv = true;
        }
      }

      this.level2a = ! rv;

      return rv;
    }


    // ===============================================================================
    // -------------------------------------------------------------------------------
    public boolean invert_covariance( ) {
      // ---------------------------------------------------------------------------
      if ( ! this.level2b ) {
        logger.warn( "this requires that you call correlate first" );
        logger.warn( "   I can not do it for you" );
        return true;
      }

      double D = inv_cov.invert( covariance );

      if ( Math2.isZero( D ) ) {
        this.invcov = false;
      } else {
        this.invcov = true;
      }
      return this.invcov;
    }


    // ===============================================================================
    // -------------------------------------------------------------------------------
    public boolean correlate( double[][] data, int num_samples, int width ) {
      // ---------------------------------------------------------------------------
      this.level2b = false;

      if ( ! this.level1 ) {
        logger.warn( "this requires that you call compile first" );
        if ( this.compile( data, num_samples, width ) ) {
          logger.warn( "   I tried to do it for you, but it failed" );
          return true;
        }
        logger.warn( "   I did it for you" );
      }

      if ( num_samples != this.count ) {
        logger.warn( "you should run extra with the same data count as compile" );
      }

      double fnm1 = (double)(num_samples - 1);

      // ----- calculate the covariance matrix -------------------------------------
      for ( int i=0; i<this.nvar; i++ ) {
        double iMean = this.mean[i];
        for ( int j=i; j<this.nvar; j++ ) {
          double jMean = this.mean[j];
          double c_sum = Math2.N_ZERO;
          for ( int k=0; k<num_samples; k++ ) {
            double x = data[k][i] - iMean;
            double y = data[k][j] - jMean;
            c_sum += ( x * y );
          }
          c_sum /= fnm1;
          covariance.A[i][j] = c_sum;
          covariance.A[j][i] = c_sum;
        }
      }

      // ----- calculate the correlation matrix ------------------------------------
      for ( int i=0; i<this.nvar; i++ ) {
        double ivar = Math.sqrt(covariance.A[i][i]);
        for ( int j=i; j<this.nvar; j++ ) {
          if ( i == j ) {
            correlation.A[i][i] = Math2.N_ONE;
          } else {
            double w = covariance.A[i][j] / (ivar * Math.sqrt(covariance.A[j][j]) + 1.0e-10);
            correlation.A[i][j] = w;
            correlation.A[j][i] = w;
          }
        }
      }

      this.level2b = true;
      return false;
    }



    // ===============================================================================
    // -------------------------------------------------------------------------------
    public boolean correlate( int[] labels, int id,
                              double[][] data, int num_samples, int width ) {
      // ---------------------------------------------------------------------------
      this.level2b = false;

      if ( ! this.level1 ) {
        logger.warn( "this requires that you call compile first" );
        if ( this.compile( data, num_samples, width ) ) {
          logger.warn( "   I tried to do it for you, but it failed" );
          return true;
        }
        logger.warn( "   I did it for you" );
      }

      if ( num_samples != this.count ) {
        logger.warn( "you should run extra with the same data count as compile" );
      }

      double fnm1 = (double)(num_samples - 1);

      // ----- calculate the covariance matrix -------------------------------------
      for ( int i=0; i<this.nvar; i++ ) {
        double iMean = this.mean[i];
        for ( int j=i; j<this.nvar; j++ ) {
          double jMean = this.mean[j];
          double c_sum = Math2.N_ZERO;
          double cnt   = Math2.N_ZERO;
          for ( int k=0; k<num_samples; k++ ) {
            if ( id == labels[k] ) {
              cnt += Math2.N_ONE;
              double x = data[k][i] - iMean;
              double y = data[k][j] - jMean;
              c_sum += ( x * y );
            }
          }
          c_sum /= (cnt - Math2.N_ONE);
          covariance.A[i][j] = c_sum;
          covariance.A[j][i] = c_sum;
        }
      }


      // ----- calculate the correlation matrix ------------------------------------
      for ( int i=0; i<this.nvar; i++ ) {
        double ivar = Math.sqrt(covariance.A[i][i]);
        for ( int j=i; j<this.nvar; j++ ) {
          if ( i == j ) {
            correlation.A[i][i] = Math2.N_ONE;
          } else {
            double w = covariance.A[i][j] / (ivar * Math.sqrt(covariance.A[j][j]) + 1.0e-10);
            correlation.A[i][j] = w;
            correlation.A[j][i] = w;
          }
        }
      }


      this.level2b = true;
      return false;
    }



	

  }
    
}

// =======================================================================================
// **                                S T A T I S T I C S                                **
// ======================================================================== END FILE =====
