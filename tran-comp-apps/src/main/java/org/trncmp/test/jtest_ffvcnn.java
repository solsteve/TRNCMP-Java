// ====================================================================== BEGIN FILE =====
// **                              J T E S T _ F F V C N N                              **
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
 * @file jtest_ffvcnn.java
 *  Compare a Feed Forward and convolutional neural network
 *
 * @author Stephen W. Soliday
 * @date 2019-01-25
 */
// =======================================================================================

package org.trncmp.test;

import  org.trncmp.lib.*;

// =======================================================================================
// ---------------------------------------------------------------------------------------
public class jtest_ffvcnn {
  // -------------------------------------------------------------------------------------

  public static final int FF_LAYER   = 0;
  public static final int CONV_LAYER = 1;
  public static final int POOL_LAYER = 2;
  
  public static final int ROW  = 0;
  public static final int COL  = 1;
  public static final int CHAN = 2;
  
  // =====================================================================================
  static abstract class TLayer {
    // -----------------------------------------------------------------------------------

    public int    pos        = 0;
    public int[]  n_input    = null;
    public int[]  n_output   = null;
    public double in_offset  = 0.0;
    public double out_offset = 0.0;

    // ===================================================================================
    public TLayer() {
      // ---------------------------------------------------------------------------------
      n_input  = new int[3];
      n_output = new int[3];
    }

    int getInput()  { return n_input[ROW]  * n_input[COL]  * n_input[CHAN];  }
    int getOutput() { return n_output[ROW] * n_output[COL] * n_output[CHAN]; }
          
    // ===================================================================================
    public void setIO( int ri, int ci, int fi, int ro, int co, int fo ) {
      // ---------------------------------------------------------------------------------
      n_input  = new int[3];
      n_output = new int[3];
      n_input[ROW]   = ri;
      n_input[COL]   = ci;
      n_input[CHAN]  = fi;
      n_output[ROW]  = ro;
      n_output[COL]  = co;
      n_output[CHAN] = fo;
    }
    
    // ===================================================================================
    public void setIO( int[] in, int[] out ) {
      // ---------------------------------------------------------------------------------
      for ( int i=0; i<3; i++ ) {
        n_input[i]  = in[i];
        n_output[i] = out[i];
      }
    }

    // ===================================================================================
    public double setOff( int n ) {
      // ---------------------------------------------------------------------------------
      if ( 0 == (n%2) ) {
        return -0.5 - (double) (n/2);
      }

      return -((double) (n/2));
    }

    // ===================================================================================
    public double computeOffset() {
      // ---------------------------------------------------------------------------------
      int nin  = n_input[ROW]  * n_input[COL]  * n_input[CHAN];
      int nout = n_output[ROW] * n_output[COL] * n_output[CHAN];

      in_offset  = setOff( nin  );
      out_offset = setOff( nout );

      double r = in_offset;
      if ( out_offset < r ) { r = out_offset; }
      return r;
    }
    
      
    public abstract void  draw     ( PSDraw G );
    public abstract double compile ( int[] in );
        
  } // end class TLayer

  
  // =====================================================================================
  static class FeedForwardLayer extends TLayer {
    // -----------------------------------------------------------------------------------

    // ===================================================================================
    public FeedForwardLayer( int nn ) {
      // ---------------------------------------------------------------------------------
      super();
      n_output[ROW]  = 1;
      n_output[COL]  = 1;
      n_output[CHAN] = nn;
    }

    // ===================================================================================
    public void draw( PSDraw G ) {
      // ---------------------------------------------------------------------------------
      double x0 = (double)(pos - 1);
      double x1 = (double) pos;

      int nin  = n_input[ROW]  * n_input[COL]  * n_input[CHAN];
      int nout = n_output[ROW] * n_output[COL] * n_output[CHAN];
     
      for ( int i=0; i<nout; i++ ) {
        double y1 = out_offset + (double)i;
        for ( int j=0; j<nin; j++ ) {
          double y0 = in_offset + (double)j;
          G.drawLine( x0, y0, x1, y1 );
        }
      }
    }
    
    // ===================================================================================
    public double compile( int[] in ) {
      // ---------------------------------------------------------------------------------
      for ( int i=0; i<3; i++ ) {
        n_input[i] = in[i];
      }      

      return computeOffset();
    }
    
  } // end class FeedForwardLayer


  // =====================================================================================
  static class ConvLayer extends TLayer {
    // -----------------------------------------------------------------------------------

    int n_rows    = 0;
    int n_cols    = 0;
    int n_filters = 0;
    
    // ===================================================================================
    public ConvLayer( int nr, int nc, int nf  ) {
      // ---------------------------------------------------------------------------------
      super();
      n_rows    = nr;
      n_cols    = nc;
      n_filters = nf;
    }
   
    // ===================================================================================
    public void draw( PSDraw G ) {
      // ---------------------------------------------------------------------------------
      double x0 = (double)(pos - 1);
      double x1 = (double) pos;

      int nin  = n_input[ROW]  * n_input[COL]  * n_input[CHAN];
      int nout = n_output[ROW] * n_output[COL] * n_output[CHAN];
     
      for ( int i=0; i<nout; i++ ) {
       double y1 = out_offset + (double)i;
        for ( int j=0; j<nin; j++ ) {
         double y0 = in_offset + (double)j;
          G.drawLine( x0, y0, x1, y1 );
        }
      }
    }
    
    // ===================================================================================
    public double compile( int[] in ) {
      // ---------------------------------------------------------------------------------
      n_input[ROW]   = in[ROW];
      n_input[COL]   = in[COL];
      n_input[CHAN]  = in[CHAN];

      n_output[ROW]  = n_input[ROW] - n_rows + 1;
      n_output[COL]  = n_input[COL] - n_cols + 1;
      n_output[CHAN] = n_filters;

      return computeOffset();
    }
    
  } // end class ConvLayer

  
  // =====================================================================================
  static class PoolLayer extends TLayer {
    // -----------------------------------------------------------------------------------

    int n_rows     = 0;
    int n_cols     = 0;
    int row_stride = 0;
    int col_stride = 0;
    
    // ===================================================================================
    public PoolLayer( int nr, int nc, int rs, int cs  ) {
      // ---------------------------------------------------------------------------------
      super();
      n_rows     = nr;
      n_cols     = nc;
      row_stride = rs;
      col_stride = cs;
    }
    
    // ===================================================================================
    public void draw( PSDraw G ) {
      // ---------------------------------------------------------------------------------
      double x0 = (double)(pos - 1);
      double x1 = (double) pos;

      int nin  = n_input[ROW]  * n_input[COL]  * n_input[CHAN];
      int nout = n_output[ROW] * n_output[COL] * n_output[CHAN];
     
      for ( int i=0; i<nout; i++ ) {
        double y1 = out_offset + (double)i;
        for ( int j=0; j<nin; j++ ) {
          double y0 = in_offset + (double)j;
          G.drawLine( x0, y0, x1, y1 );
        }
      }
    }
    
    // ===================================================================================
    public double compile( int[] in ) {
      // ---------------------------------------------------------------------------------
      n_input[ROW]   = in[ROW];
      n_input[COL]   = in[COL];
      n_input[CHAN]  = in[CHAN];

      n_output[ROW]  = 1 + (n_input[ROW] - n_rows)/row_stride;
      n_output[COL]  = 1 + (n_input[COL] - n_cols)/col_stride;
      n_output[CHAN] = in[CHAN];

      return computeOffset();
    }
    
  } // end class PoolLayer

  
  // =====================================================================================
  static class Meta {
    // -----------------------------------------------------------------------------------
    double min_x = 0.0;
    double max_x = 0.0;
    double min_y = 0.0;
    double max_y = 0.0;
  } // end class Meta

  
  // =====================================================================================
  static class TNet {
    // -----------------------------------------------------------------------------------
    TLayer[] layers     = null;
    int      max_layers = 0;
    int      n_layers    = 0;
    int[]    n_input    = null;

    // ===================================================================================
    public TNet( int ml ) {
      // ---------------------------------------------------------------------------------
      max_layers = ml;
      layers = new TLayer[ml];
      n_layers = 0;
      for ( int i=0; i<ml; i++ ) {
        layers[i] = null;
      }
      n_input = new int[3];
     }

    // ===================================================================================
    public TNet() {
      // ---------------------------------------------------------------------------------
      this(100);
    }

    // ===================================================================================
    public void add( TLayer L ) {
      // ---------------------------------------------------------------------------------
      layers[n_layers] = L;
      L.pos = n_layers;
      n_layers += 1;
    }

    // ===================================================================================
    public void setInput( int nr, int nc, int nch ) {
      // ---------------------------------------------------------------------------------
     n_input[ROW]  = nr;
     n_input[COL]  = nc;
     n_input[CHAN] = nch;
    }

    // ===================================================================================
    public int getInput( ) {
      // ---------------------------------------------------------------------------------
     return n_input[ROW] + n_input[COL] + n_input[CHAN];
    }

    // ===================================================================================
    public Meta compile() {
      // ---------------------------------------------------------------------------------
      Meta M = new Meta();

      M.min_x = -1.0;
      M.max_x = (double) n_layers;

      double min_k = 0.0;
      int[] last = n_input;
      for ( int i=0; i<n_layers; i++ ) {
        double k = layers[i].compile(last);
        if ( k < min_k ) { min_k = k; }
        last = layers[i].n_output;
      }


      min_k -= 1.0;

      M.min_y = min_k;
      M.max_y = -M.min_y;
      
      return M;
    }

    // ===================================================================================
    public void draw( PSDraw G ) {
      // ---------------------------------------------------------------------------------

      System.out.format( "%d\n", getInput() );
      
      for ( int i=0; i<n_layers; i++ ) {
        System.out.format( "  %d\n", layers[i].getInput() );
        layers[i].draw( G );
      }
    }

  } // end class TNet


  
  // =====================================================================================
  public void Test01() {
    // -----------------------------------------------------------------------------------

    TNet net = new TNet();

    net.setInput( 6, 6, 1 );
    net.add( new ConvLayer( 4, 4, 1 ) );
    net.add( new ConvLayer( 4, 4, 1 ) );
    net.add( new FeedForwardLayer( 16 ) );
    net.add( new FeedForwardLayer( 8 ) );
    net.add( new FeedForwardLayer( 3 ) );
    
    Meta meta = net.compile();
             
    PSGraph ps = new PSGraph(1);
    PSDraw  pd = new PSDraw( 10.0, 7.5, meta.min_x, meta.min_y, meta.max_x, meta.max_y );

    pd.setRGB( 1.0, 0.0, 0.0 );
    
    pd.drawLine( meta.min_x, 0.0, meta.max_x, 0.0 ); 

    pd.setRGB( 0.0, 0.0, 1.0 );
    pd.drawBorder();

    net.draw( pd );

   ps.add( pd, 0, 0.5, 0.5 );

    ps.pswrite( "fix.ps" );

    ps.delete();    
  }


  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------

    jtest_ffvcnn T = new jtest_ffvcnn();
    
    T.Test01();
      
    System.exit(0);
  }

  
} // end class jtest_ffvcnn


// =======================================================================================
// **                              J T E S T _ F F V C N N                              **
// ======================================================================== END FILE =====
