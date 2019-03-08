// ====================================================================== BEGIN FILE =====
// **                                     G R O U P                                     **
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
 * @file Group.java
 * <p>
 * Provides the interface and methods for a group of fuzzy partitions.
 *
 * @date 2018-08-12
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

// =======================================================================================
public class Group {
  // -------------------------------------------------------------------------------------

  protected Partition[] partitions = null;
  protected int         num_part   = 0;

  public Group() {};

  public Partition part     ( int p )        { return partitions[p]; }
  public Function  function ( int p, int f ) { return partitions[p].function(f); }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void fuzzify( double[] mu, double[] x ) {
    // -----------------------------------------------------------------------------------
    int idx = 0;
    for ( int i=0; i<num_part; i++ ) {
      partitions[i].mu( mu, idx, x[i] );
      idx += partitions[i].size();
    }
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void defuzzify( double[] x, double[] mu ) {
    // -----------------------------------------------------------------------------------
    int idx = 0;
    for ( int i=0; i<num_part; i++ ) {
      x[i] = partitions[i].coa( mu, idx );
      idx += partitions[i].size();
    }
  }

} //end class Group

// =======================================================================================
// **                                 P A R T I T I O N                                 **
// ======================================================================== END FILE =====
