// ====================================================================== BEGIN FILE =====
// **                             M O D E L E X E C U T O R                             **
// =======================================================================================
// **                                                                                   **
// **  This file is part of the TRNCMP Research Library. (formerly SolLib)              **
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
//
// @file Population.java
// <p>
//  Provides a task execution queue.
//
// @author Stephen W. Soliday
// @date 2018-07-22
//
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
