// ====================================================================== BEGIN FILE =====
// **                                     T A B L E                                     **
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
 * @file Table.java
 * <p>
 * Provides an interface and methods for a table of fuzzy rules.
 *
 *
 * @date 2019-01-04
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
import java.util.Vector;


// =======================================================================================
public class Table {
  // -------------------------------------------------------------------------------------

  
  // =====================================================================================
  static class PRec {
    // -----------------------------------------------------------------------------------
    public Partition part = null;
    public double[]  mu   = null;

    
    // =====================================================================================
    public PRec( Partition p ) {
      // -----------------------------------------------------------------------------------
      part = p;
      mu   = new double[p.size()];
    }
    
  }; // end class PRec

  
  protected PRec[]       uod      = null;
  protected Vector<Rule> rules    = new Vector<Rule>();
  protected Partition    out_part = null;
  public    double[]     out_mu   = null;

  
  // =====================================================================================
  protected void resize( int n ) {
    // -----------------------------------------------------------------------------------
    uod = new PRec[n];
    rules.clear();
  }

  
  // =====================================================================================
  public Table() {
    // -----------------------------------------------------------------------------------
  }

  
  // =====================================================================================
  public Table( int n ) {
    // -----------------------------------------------------------------------------------
    resize( n );
  }

  
  // =====================================================================================
  public void setInput( int i, Partition p ) {
    // -----------------------------------------------------------------------------------
    uod[i].part = p;
  }

  
  // =====================================================================================
  public void setOutput( int i, Partition p ) {
    // -----------------------------------------------------------------------------------
    out_part = p;
    out_mu   = new double[p.size()];
  }

  
  // =====================================================================================
  public Partition getInput( int i ) {
    // -----------------------------------------------------------------------------------
    return uod[i].part;
  }

  
  // =====================================================================================
  public Partition getOutput( int i ) {
    // -----------------------------------------------------------------------------------
    return out_part;
  }

  
  // =====================================================================================
  public Rule getRule( int i ) {
    // -----------------------------------------------------------------------------------
    return rules.elementAt(i);
  }

  
  // =====================================================================================
  public void add( Rule rule ) {
    // -----------------------------------------------------------------------------------
    rules.add( rule );
  }

  
  // =====================================================================================
  public void add( int[] in, int out ) {
    // -----------------------------------------------------------------------------------
    rules.add( new Rule( in, out ) );
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void write( PrintStream ps ) {
    // -----------------------------------------------------------------------------------
    int np = uod.length;
    ps.format( "%d\n", np );
    for ( int i=0; i<np; i++ ) {
      uod[i].part.write( ps );
    }
    out_part.write( ps );
    int nr = rules.size();
    for ( Rule rule : rules ) {
      rule.write( ps );
    }
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void read( BufferedReader br, int n ) {
    // -----------------------------------------------------------------------------------
    Scanner scan = new Scanner( br );
    int np = scan.nextInt();
    resize(np);

    for ( int i=0; i<np; i++ ) {
      Partition pt = new Partition();
      pt.read( scan );
      uod[i] = new PRec( pt );
    }

    out_part = new Partition();
    out_part.read( scan );
    
    int nr = scan.nextInt();

    for ( int i=0; i<nr; i++ ) {
      Rule rule = new Rule(np);
      rule.read( scan, np );
      rules.add( rule );
    }
  }

  
} // end class Table


// =======================================================================================
// **                                      R U L E                                      **
// ======================================================================== END FILE =====
