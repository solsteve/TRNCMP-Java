// ====================================================================== BEGIN FILE =====
// **                        I N T E R R U P T A B L E T I M E R                        **
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
 * @brief   Interruptable Timer.
 * @file    InterruptableTimer.java
 *
 * @details Provides the interface and procedures for providing an interruptable timer.
 *          This timer may be instantly interrupted. Use this in favor of Thread.sleep()
 *          with very long durrations.
 *
 * @date    2018-08-07
 */
// =======================================================================================

package org.trncmp.lib;

// =======================================================================================
public class InterruptableTimer {
  // -------------------------------------------------------------------------------------
  private   Thread  thread      = null;
  protected boolean forced_stop = true;

  // =====================================================================================
  static public class Stopped extends Exception {
    // -----------------------------------------------------------------------------------
    public Stopped(String message) { super(message); }
  } // end class InterruptableTimer.Stopped

  // =====================================================================================
  static protected class TimerThread extends Thread {
    // -----------------------------------------------------------------------------------
    protected InterruptableTimer parent   = null;
    protected long               duration = 0L;

    // ===================================================================================
    public TimerThread( InterruptableTimer p, long d ) {
      // ---------------------------------------------------------------------------------
      parent   = p;
      duration = d;
    }

    // ===================================================================================
    public void run() {
      // ---------------------------------------------------------------------------------
      try {
        Thread.sleep( duration );
      } catch( InterruptedException e ) {
        parent.forced_stop = true;
      }
    }
 
  } // end InterruptableTimer.TimerThread

  // =====================================================================================
  public InterruptableTimer() {
    // -----------------------------------------------------------------------------------
  }
  
  // =====================================================================================
  /** @brief Sleep.
   *  @param seconds sleep for seconds.
   *
   *  Sleep for a duration measured in floating-point seconds.
   */
  // -------------------------------------------------------------------------------------
  public void sleep( double seconds ) throws InterruptableTimer.Stopped {
    // -----------------------------------------------------------------------------------
    sleep( (long)Math.floor( seconds * 1000.0 + 0.5 ) );
  }
  
  // =====================================================================================
  /** @brief Sleep.
   *
   *  Interrupt this timer immediately.
   */
  // -------------------------------------------------------------------------------------
  public void interrupt() {
    // -----------------------------------------------------------------------------------
    if ( null != thread ) { thread.interrupt(); }
  }
    
  // =====================================================================================
  /** @brief Sleep.
   *  @param milliseconds sleep for milliseconds.
   *
   *  Sleep for a duration measured in integer milliseconds.
   */
  // -------------------------------------------------------------------------------------
  public void sleep( long milliseconds ) throws InterruptableTimer.Stopped {
    // -----------------------------------------------------------------------------------
    forced_stop = false;
    thread = new TimerThread(this, milliseconds);
    thread.start();
    try {
      thread.join();
    } catch( InterruptedException e ) {
      forced_stop = true;
    }
    if ( forced_stop ) { throw new InterruptableTimer.Stopped("timer interrupted"); }
  }

} // end class InterruptableTimer

// =======================================================================================
// **                        I N T E R R U P T A B L E T I M E R                        **
// =========================================================================== END FILE ==
