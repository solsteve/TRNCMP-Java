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
  
  // =====================================================================================
  static class TLayer {
    // -----------------------------------------------------------------------------------
       
    
  } // end class TLayer

  
  // =====================================================================================
  static class FeedForwardLayer extends TLayer {
    // -----------------------------------------------------------------------------------
    int n_nodes = 0;

    // ===================================================================================
    public FeedForwardLayer( int nn ) {
      // ---------------------------------------------------------------------------------
      super();
      n_nodes = nn;
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
    int      n_layer    = 0;
    int      n_rows = 0;
    int      n_cols = 0;
    int      n_chan = 0;

    // ===================================================================================
    public TNet( int ml ) {
      // ---------------------------------------------------------------------------------
      max_layers = ml;
      layers = new TLayer[ml];
      n_layer = 0;
      for ( int i=0; i<ml; i++ ) {
        layers[i] = null;
      }
      n_rows = 0;
      n_cols = 0;
      n_chan = 0;
     }

    // ===================================================================================
    public TNet() {
      // ---------------------------------------------------------------------------------
      this(100);
    }

    // ===================================================================================
    public void add( TLayer L ) {
      // ---------------------------------------------------------------------------------
      layers[n_layer] = L;
      n_layer += 1;
    }

    // ===================================================================================
    public void setInput( int nr, int nc, int nch ) {
      // ---------------------------------------------------------------------------------
      n_rows = nr;
      n_cols = nc;
      n_chan = nch;
    }

    // ===================================================================================
    public Meta compile() {
      // ---------------------------------------------------------------------------------
      Meta M = new Meta();

      M.min_x = -1.0;
      M.max_x = (double) n_layer;




      

      M.min_y = -10.0;
      M.max_y =  10.0;
      
      return M;
    }

    

  } // end class TNet


  
  // =====================================================================================
  public void Test01() {
    // -----------------------------------------------------------------------------------

    TNet net = new TNet();

    net.setInput( 8, 8, 3 );
    net.add( new ConvLayer( 5, 5, 4 ) );
    net.add( new PoolLayer( 2, 2, 2, 2 ) );
    net.add( new ConvLayer( 4, 4, 2 ) );
    net.add( new PoolLayer( 2, 2, 2, 2 ) );
    net.add( new FeedForwardLayer( 16 ) );
    net.add( new FeedForwardLayer( 8 ) );
    net.add( new FeedForwardLayer( 3 ) );
    
    Meta meta = net.compile();
             
    PSGraph ps = new PSGraph(1);
    PSDraw  pd = new PSDraw( 10.0, 4.0, meta.min_x, meta.min_y, meta.max_x, meta.max_y );

    pd.setRGB( 0.0, 0.0, 1.0 );
    pd.drawBorder();

    pd.setRGB( 1.0, 0.0, 0.0 );
    pd.drawEllipse( 7.0, 9.0, 1.0, 0.25, 10.0 );

    ps.add( pd, 0, 0.5, 2.25 );

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
