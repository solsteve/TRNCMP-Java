// ====================================================================== BEGIN FILE =====
// **                             M O D E L E X E C U T O R                             **
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
