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

// =======================================================================================
public class ModelExecutor {
  // -------------------------------------------------------------------------------------

  protected Model       model      = null;
  protected Population  pop        = null;
  protected int         num_thread = 0;
  protected TaskGroup[] group      = null;
  

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public ModelExecutor( Model m, int n, int np ) {
    // -----------------------------------------------------------------------------------
    model      = m;
    num_thread = n;

    group = new TaskGroup[n];
    for ( int i=0; i<num_thread; i++ ) {
      group[i] = new TaskGroup( model, np );
    }

  }

  
  // =====================================================================================
  // Block until all tasks are complete.
  // -------------------------------------------------------------------------------------
  public void runAll( Population pop ) {
    // -----------------------------------------------------------------------------------
    int num = pop.size();
    for ( int i=0; i<num; i++ ) {
      group[i % num_thread].add( pop.get(i) );
    }

    for ( int i=0; i<num_thread; i++ ) {
      group[i].start();
    }

    try {
      for ( int i=0; i<num_thread; i++ ) {
        group[i].join();
      }
    } catch( InterruptedException e ) {
    }
  }






  // =====================================================================================
  // -------------------------------------------------------------------------------------
  static class TaskGroup extends Thread {
    // -----------------------------------------------------------------------------------

    protected Model              model    = null;
    protected PopulationMember[] pop_list = null;
    protected int                max_task = 0;
    protected int                num_task = 0;
    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public TaskGroup( Model m, int n ) {
      // ---------------------------------------------------------------------------------
      model    = m;
      max_task = n;
      num_task = 0;

      pop_list = new PopulationMember[n];
      for ( int i=0; i<n; i++ ) {
        pop_list[i] = null;
      }
    }

    // ===================================================================================
    /** @brief Clear.
     *
     *  Clear the pointers from the list.
     */
    // -----------------------------------------------------------------------------------
    public void clear() {
      // ---------------------------------------------------------------------------------
      for ( int i=0; i<max_task; i++ ) {
        pop_list[i] = null;
      }
      num_task = 0;
    }
      
    // ===================================================================================
    /** @brief Add.
     *  @param m pointer to a PopulationMember.
     *
     *  Add a PopulationMember pointer to the list.
     */
    // -----------------------------------------------------------------------------------
    public void add( PopulationMember m ) {
      // ---------------------------------------------------------------------------------
      pop_list[ num_task ] = m;
      num_task += 1;
    }
      
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public void run() {
      // ---------------------------------------------------------------------------------
      for ( int i=0; i<num_task; i++ ) {
        PopulationMember test = pop_list[i];
        model.execute( test.metric, test.param );
      }
    }

  } // end class ModelExecutor.Task


} // end class ModelExecutor

// =======================================================================================
// **                             M O D E L E X E C U T O R                             **
// ======================================================================== END FILE =====
