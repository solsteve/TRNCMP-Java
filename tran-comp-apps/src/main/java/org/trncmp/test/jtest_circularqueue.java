// ====================================================================== BEGIN FILE =====
// **                       J T E S T _ C I R C U L A R Q U E U E                       **
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
 * @file jtest_circularqueue.java
 *  Provides interface and methods to test the CircularQueue.
 *  <p>
 *
 * @author Stephen W. Soliday
 * @date 2018-06-14
 */
// =======================================================================================

package org.trncmp.test;

import org.trncmp.lib.*;

// =======================================================================================
public class jtest_circularqueue {
  // -------------------------------------------------------------------------------------

  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------

    CircularQueue<Integer> C = new CircularQueue<Integer>(5);

    System.out.println( "Size     expect: 0     got: "+C.size() );
    System.out.println( "Empty    expect: true  got: "+C.isEmpty() );
    System.out.println( "Full     expect: false got: "+C.isFull() );
    
    C.add(100);

    System.out.println( "Size     expect: 1     got: "+C.size() );
    System.out.println( "Empty    expect: false got: "+C.isEmpty() );
    System.out.println( "Full     expect: false got: "+C.isFull() );
    System.out.println( "Peek old expect: 100   got: "+C.peekOld() );
    System.out.println( "Peek new expect: 100   got: "+C.peekNew() );

    C.add(200);
    C.add(300);

    System.out.println( "Size     expect: 3     got: "+C.size() );
    System.out.println( "Empty    expect: false got: "+C.isEmpty() );
    System.out.println( "Full     expect: false got: "+C.isFull() );
    System.out.println( "Peek old expect: 100   got: "+C.peekOld() );
    System.out.println( "Peek new expect: 300   got: "+C.peekNew() );

    C.clear();
    System.out.println( "Size     expect: 0     got: "+C.size() );
    System.out.println( "Empty    expect: true  got: "+C.isEmpty() );
    System.out.println( "Full     expect: false got: "+C.isFull() );

    C.add(1100);
    C.add(1200);
    C.add(1300);
    C.add(1400);
    C.add(1500);
    C.add(1600);
    C.add(1700);
    C.add(1800);
    
    System.out.println( "Size     expect: 5     got: "+C.size() );
    System.out.println( "Empty    expect: false got: "+C.isEmpty() );
    System.out.println( "Full     expect: true  got: "+C.isFull() );
    System.out.println( "Peek old expect: 1400  got: "+C.peekOld() );
    System.out.println( "Peek new expect: 1800  got: "+C.peekNew() );
    System.out.println( "Remove   expect: 1400  got: "+C.remove() );
    System.out.println( "Remove   expect: 1500  got: "+C.remove() );
    System.out.println( "Remove   expect: 1600  got: "+C.remove() );
    
    System.out.println( "Size     expect: 2     got: "+C.size() );
    System.out.println( "Empty    expect: false got: "+C.isEmpty() );
    System.out.println( "Full     expect: false got: "+C.isFull() );
    
    C.clear();
    System.out.println( "Size     expect: 0     got: "+C.size() );
    System.out.println( "Empty    expect: true  got: "+C.isEmpty() );
    System.out.println( "Full     expect: false got: "+C.isFull() );

   System.exit(0);
  }


} // end class 


// =======================================================================================
// **                       J T E S T _ C I R C U L A R Q U E U E                       **
// ======================================================================== END FILE =====
