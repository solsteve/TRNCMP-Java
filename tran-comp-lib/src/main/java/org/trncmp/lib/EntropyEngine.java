// ====================================================================== BEGIN FILE =====
// **                             E N T R O P Y E N G I N E                             **
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
 * @file EntropyEngine.java
 *  Provides Source of random numbers.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2017-06-18
 */
// =======================================================================================

package org.trncmp.lib;

// =======================================================================================
public interface EntropyEngine {
    // -----------------------------------------------------------------------------------

    public void    reset        ( );
    public int     seed_size    ( );
    public void    seed_set     ( byte[] sm, int n );
    public String  name         ( );
    public double  get_real     ( );
    public long    get_integer  ( );
    public long    max_integer  ( );
   
}

// =======================================================================================
// **                             E N T R O P Y E N G I N E                             **
// ======================================================================== END FILE =====

