// ====================================================================== BEGIN FILE =====
// **                             J T E S T _ C O N T R O L                             **
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
 * @file jtest_control.java
 *  Perform a system test on ControlReciever.
 *
 * @author Stephen W. Soliday
 * @date 2018-04-16
 */
// =======================================================================================

package org.trncmp.test;

import  org.trncmp.lib.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
// ---------------------------------------------------------------------------------------
public class jtest_control {
  // -------------------------------------------------------------------------------------

  public Logger logger = LogManager.getLogger();


  // =====================================================================================
  public static class TestListener implements ControlListener {
    // -----------------------------------------------------------------------------------

    String my_name = null;
    String my_msg  = null;

    // ===================================================================================
    public TestListener( String nm, String sig ) {
      // ---------------------------------------------------------------------------------
      my_name = new String( nm );
      my_msg  = new String( sig );
    }

    // ===================================================================================
    public void on_receive( String msg ) {
      // ---------------------------------------------------------------------------------
      if ( msg.equals( "SYSEXIT" ) ) {
        System.out.format( "%s received exit notification\n", my_name );
      } else if (msg.equals( my_msg ) ) {
        System.out.format( "%s processed message = %s\n", my_name, msg );
      } else {
        System.out.format( "%s ignored   message = %s\n", my_name, msg );
      }
    }
    
  } // end TestListener


  // =====================================================================================
  public jtest_control() {
    // -----------------------------------------------------------------------------------
  }


  // =====================================================================================
  public void message_received( String msg ) {
    // -----------------------------------------------------------------------------------
    System.out.format( "Lambda PROCESSED message = %s\n", msg );
  }


  // =====================================================================================
  public void Test01() {
    // -----------------------------------------------------------------------------------
    TestListener L1 = new TestListener( "Alpha ", "RELOAD" );
    TestListener L2 = new TestListener( "Beta  ", "PROCESS" );
    TestListener L3 = new TestListener( "Gamma ", "RELOAD" );
    TestListener L4 = new TestListener( "Delta ", "BACKUP" );

    ControlReceiver CR = new ControlReceiver( 44242 );

    CR.setShutdown( "SYSEXIT" );

    CR.add( L1 );
    CR.add( L2 );
    CR.add( L3 );
    CR.add( L4 );


    CR.add( new ControlListener() {
        public void on_receive( String msg ) {
          message_received( msg );
        }
      } );

    CR.receive( "RELOAD"  );
    CR.receive( "PROCESS" );
    CR.receive( "BACKUP"  );

    CR.writeScript( "test.sh" );

    System.out.println( "Waiting 20 seconds" );
    try {
        Thread.sleep( 20000 );
    } catch ( InterruptedException e ) {
      logger.debug( "JTest control was interuppted by exception" );
    }

    CR.shutdown( true );
  }





  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------

    jtest_control T = new jtest_control();
    
    T.Test01();
      
    System.exit(0);
  }
}

// =======================================================================================
// **                             J T E S T _ C O N T R O L                             **
// ======================================================================== END FILE =====
