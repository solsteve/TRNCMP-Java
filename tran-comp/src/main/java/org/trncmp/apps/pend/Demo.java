// ====================================================================== BEGIN FILE =====
// **                                      D E M O                                      **
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
 * @brief   Interactive Grid Display.
 * @file    GridTest.java
 *
 * @details Provides the interface and procedures testing the GridPanel.
 *
 * @author  Stephen W. Soliday
 * @date    2018-12-27
 */
// =======================================================================================

package org.trncmp.apps.pend;

import java.awt.EventQueue;

import org.trncmp.lib.Dice;
import org.trncmp.lib.Math2;
import org.trncmp.lib.RunStat;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import org.trncmp.lib.ConfigDB;


// =======================================================================================
public class Demo implements DemoButtonListener {
  // -------------------------------------------------------------------------------------

  protected static Dice dd = Dice.getInstance();

  protected PendFrame       frame     = null;
  protected PendEngine      engine    = null;
  protected PendControl     controler = null;
  protected ExecutionThread ethread   = null;

  // =====================================================================================
  public Demo() {
    // -----------------------------------------------------------------------------------

    ConfigDB cfg = new ConfigDB();

    if ( cfg.readINI( "IPend.ini" ) ) {
      System.err.println( "Cannot read IPend.ini" );
      System.exit(1);
    }

    try {
      engine    = new PendEngine( cfg.get("PEND") );
      frame     = new PendFrame( engine );
      controler = new PendRandomControl();
    } catch( ConfigDB.NoSection e ) {
      System.err.println( e.toString() );
    }

    frame.addButtonListener( this );
    frame.setVisible(true);
    
    System.out.println( "Demo: set up action listener" );
    
    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          frame.repaint();
        }
      };

    System.out.println( "Demo: start timer" );
    new Timer(33, taskPerformer).start();

  }


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void requestRun() {
    // -----------------------------------------------------------------------------------
    System.out.println( "Demo.requestRun: create thread" );
    ethread = new ExecutionThread( engine, frame, controler, 0.01, 5 );
    ethread.start();
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void requestStop() {
    // -----------------------------------------------------------------------------------
    if ( null != ethread ) {
      ethread.halt();
      try {
        ethread.join();
        System.out.println( "Demo.requestStop: thread joined" );
      } catch( InterruptedException e ) {
      }
    } else {
      System.out.println( "Demo.requestStop: thread does not exist" );
    }
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void resetButtonPressed() {
    // -----------------------------------------------------------------------------------
    controler.reset();
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void loadButtonPressed() {
    // -----------------------------------------------------------------------------------
    System.out.println( "the load button pressed" );
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void saveButtonPressed() {
    // -----------------------------------------------------------------------------------
    System.out.println( "the save button pressed" );
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void closeAction() {
    // -----------------------------------------------------------------------------------

    
    System.out.println( "v===== Demo.closeAction: =================v" );
    requestStop();
    System.out.println( "^===== Demo.closeAction: =================^" );
    
    System.out.println( "Bye..." );
  }






  // =====================================================================================
  // -------------------------------------------------------------------------------------
  static class ExecutionThread extends Thread {
    // -----------------------------------------------------------------------------------
    PendEngine  engine          = null;
    PendFrame   frame           = null;
    PendControl ctrl            = null;
    boolean     running         = false;
    int         integrate_step  = 0;
    double      integrate_delta = 0.0;
    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public ExecutionThread( PendEngine eng, PendFrame frm, PendControl pc, double delay, int step ) {
      // ---------------------------------------------------------------------------------
      engine          = eng;
      frame           = frm;
      ctrl            = pc;
      running         = false;
      integrate_delta = delay;
      integrate_step  = step;      
    }


    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public void halt() {
      // ---------------------------------------------------------------------------------
      System.out.println( "PendEngine.ExecutionThread: request thread stop" );
      if ( running ) {
        running = false;
      } else {
        System.out.println( "PendEngine.ExecutionThread: thread not running" );
      }
    }


    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public void run() {
      // ---------------------------------------------------------------------------------
      long ms = (long)(integrate_delta * 1000.0);

      RunStat stat_V = new RunStat();
      RunStat stat_w = new RunStat();
      RunStat stat_X = new RunStat();
        
      System.out.println( "PendEngine.ExecutionThread: engine reset" );
      engine.reset();
      System.out.println( "PendEngine.ExecutionThread: enter run loop" );
      running = true;
      while( running ) {
        try {
          Thread.sleep(ms);
        } catch( InterruptedException e ) {
        }
        double[] state = engine.getState();

        stat_V.update( state[0] );
        stat_w.update( state[1] * Math2.RAD2DEG );
        stat_X.update( state[2] );

        double force = ctrl.action( state );
        engine.step( integrate_delta, integrate_step, force );
        //frame.update();
      }
      System.out.println( "PendEngine.ExecutionThread: exit run loop" );

      System.out.format( "Linear  Velocity %f %f %f %f\n", stat_V.minv(), stat_V.maxv(), stat_V.mean(), stat_V.sigma() );
      System.out.format( "Angular Velocity %f %f %f %f\n", stat_w.minv(), stat_w.maxv(), stat_w.mean(), stat_w.sigma() );
      System.out.format( "Position         %f %f %f %f\n", stat_X.minv(), stat_X.maxv(), stat_X.mean(), stat_X.sigma() );
    }

   
  } // end class Demo.ExecutionThread











  


  // =====================================================================================
  public static void main( String[] args ) {
    // -----------------------------------------------------------------------------------

    dd.seed_set();

    EventQueue.invokeLater(new Runnable() {
        @Override
        public void run() {
          Demo G = new Demo();
        }
      });
  }

} // end class Demo
