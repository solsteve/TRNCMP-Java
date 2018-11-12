// ====================================================================== BEGIN FILE =====
// **                                    T I M A G E                                    **
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
 * @brief   K-D Tree.
 * @file    KD_Tree.java
 *
 * @details Provides a simple image class.
 *
 * @author  Stephen W. Soliday
 * @date    2018-11-07
 */
// =======================================================================================

package org.trncmp.lib;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

// =======================================================================================
public class TImage {
  // -------------------------------------------------------------------------------------

  protected int[][] data = null;

  public int  nRow () { return data.length;    }
  public int  nCol () { return data[0].length; }

  public int  get  ( int r, int c )              { return data[r][c]; }
  public void set  ( int r, int c, final int v ) { data[r][c] = v;    }

  
  // =====================================================================================
  public TImage() {
    // -----------------------------------------------------------------------------------
  }

  
  // =====================================================================================
  public TImage( String fspc ) {
    // -----------------------------------------------------------------------------------
    read( fspc );
  }

  
  // =====================================================================================
  public TImage( int[][] src ) {
    // -----------------------------------------------------------------------------------
    int nr = src.length;
    int nc = src[0].length;
    data = new int[nr][nc];
    for ( int r=0; r<nr; r++ ) {
      for ( int c=0; c<nc; c++ ) {
        data[r][c] = src[r][c];
      }
    }
  }

  
  // =====================================================================================
  public TImage( int nr, int nc ) {
    // -----------------------------------------------------------------------------------
    data = new int[nr][nc];
  }


  // =====================================================================================
  public void writePGM( String fspc ) {
    // -----------------------------------------------------------------------------------
  }

  
  // =====================================================================================
  public String toString() {
    // -----------------------------------------------------------------------------------
    return String.format( "R:%d C:%d", nRow(), nCol() );
  }

  
  // =====================================================================================
  public void read( String fspc ) {
    // -----------------------------------------------------------------------------------
    try {
      BufferedImage img = ImageIO.read( new File( fspc ) );
      int nr = img.getHeight();
      int nc = img.getWidth();
      data = new int[nr][nc];
    } catch ( java.io.IOException e ) {
      System.err.println( e.toString() );
    }
  }

  
  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------
    TImage img = new TImage( args[0] );
    System.out.println( img.toString() );
  }

  
}

// =======================================================================================
// **                                    T I M A G E                                    **
// ======================================================================== END FILE =====
