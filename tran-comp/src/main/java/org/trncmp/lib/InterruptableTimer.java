// ====================================================================== BEGIN FILE =====
// **                        I N T E R R U P T A B L E T I M E R                        **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2018, L3 Technologies Advanced Programs                            **
// **                      One Wall Street #1, Burlington, MA 01803                     **
// **                                                                                   **
// **  -------------------------------------------------------------------------------  **
// **                                                                                   **
// **  This file, and associated source code, is not free software; you may not         **
// **  redistribute it and/or modify it. This file is part of a research project        **
// **  that is in a development phase. No part of this research has been publicly       **
// **  distributed. Research and development for this project has been at the sole      **
// **  cost in both time and funding by L3 Technologies Advanced Programs.              **
// **                                                                                   **
// **  Any reproduction of computer software or portions thereof marked with this       **
// **  legend must also reproduce the markings.  Any person who has been provided       **
// **  access to such software must promptly notify L3 Technologies Advanced Programs.  **
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
