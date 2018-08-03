// ====================================================================== BEGIN FILE =====
// **                             M O D E L E X E C U T O R                             **
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
 * @file ModelExecutor.java
 * <p>
 * Provides a task execution queue, for concurrent model evaluation.
 *
 * @date 2018-07-25
 */
// =======================================================================================

package org.trncmp.mllib.ea;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;

// =======================================================================================
public class ModelExecutor {
  // -------------------------------------------------------------------------------------

  protected final ExecutorService pool;
  protected Model      model     = null;
  protected int        num_proc  = 0;
  protected Task[] tasks     = null;
  protected int        num_tasks = 0;

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  static class Task implements Runnable {
    // -----------------------------------------------------------------------------------
    protected final Model            model;
    protected       PopulationMember member  = null;
    protected       CountDownLatch   counter = null;

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public Task( Model mod ) {
      // ---------------------------------------------------------------------------------
      model = mod;
    }

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public void setMember( PopulationMember M ) {
      // ---------------------------------------------------------------------------------
      member = M;
    }
    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public void setCounter( CountDownLatch C ) {
      // ---------------------------------------------------------------------------------
      counter = C;
    }

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public void run() {
      // ---------------------------------------------------------------------------------
      if ( null != member ) {
        model.execute( member.metric, member.param );
        counter.countDown();
      }
    }
  
  } // end class ModelExecutor.Task

  
  // =====================================================================================
  /** @brief Constructor.
   *  @param mod pointer to a fitness model.
   *  @param np  number of processors
   */
  // -------------------------------------------------------------------------------------
  public ModelExecutor( Model mod, int np, int nt ) {
    // -----------------------------------------------------------------------------------
    model     = mod;
    num_proc  = np;
    pool      = Executors.newFixedThreadPool( np );

    num_tasks = nt;
    tasks = new ModelExecutor.Task[ num_tasks ];
    for ( int i=0; i<num_tasks; i++ ) {
      tasks[i] = new ModelExecutor.Task( model );
    }  
  }

  // =====================================================================================
  /** @brief Execute.
   *  @param pop reference to a population.
   *
   *  Blocks until all tasks are complete.
   */
  // -------------------------------------------------------------------------------------
  public void execute( Population pop ) {
    // -----------------------------------------------------------------------------------
    CountDownLatch counter = new CountDownLatch(num_tasks);

    for ( int i=0; i<num_tasks; i++ ) {
      tasks[i].setMember( pop.get(i) );
      tasks[i].setCounter( counter );
      pool.execute( tasks[i] );
    }

    try {
      counter.await();
    } catch( InterruptedException e ) {
    }
  }

} // end class ModelExecutor

// =======================================================================================
// **                             M O D E L E X E C U T O R                             **
// ======================================================================== END FILE =====
