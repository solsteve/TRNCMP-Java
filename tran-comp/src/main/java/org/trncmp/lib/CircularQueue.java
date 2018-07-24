// ====================================================================== BEGIN FILE =====
// **                             C I R C U L A R Q U E U E                             **
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
 * @brief   Circular Queue.
 * @file    CircularQueue.java
 *
 * @details Provides the interface and procedures for simple circular queue.
 *
 * @date    2018-06-14
 */
// =======================================================================================

package org.trncmp.lib;

import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class CircularQueue<E> {
  // -------------------------------------------------------------------------------------
  private static final Logger logger = LogManager.getLogger();

  private int                      max_elements = 0;
  private ConcurrentLinkedDeque<E> queue        = null;

  // =====================================================================================
  /** @brief Constructor.
   *  @param n maximum capacity of the buffer.
   */
  // -------------------------------------------------------------------------------------
  public CircularQueue( int n ) {
    // -----------------------------------------------------------------------------------
    max_elements = n;
    queue = new ConcurrentLinkedDeque<E>();
  }

  // =====================================================================================
  /** @brief Clear.
   *
   *  Removes all of the elements from this circular queue.
   */
  // -------------------------------------------------------------------------------------
  public void clear() {
    // -----------------------------------------------------------------------------------
    queue.clear();
  }

  // =====================================================================================
  /** @brief Size.
   *  @return the number of elements in this circular queue.
   */
  // -------------------------------------------------------------------------------------
  public int size() {
    // -----------------------------------------------------------------------------------
    return queue.size();
  }

  // =====================================================================================
  /** @brief Capacity.
   *  @return the maximum capacity of this circular queue.
   */
  // -------------------------------------------------------------------------------------
  public int capacity() {
    // -----------------------------------------------------------------------------------
    return max_elements;
  }

  // =====================================================================================
  /** @brief Is Full.
   *  @return true if thi circular queue is full.
   */
  // -------------------------------------------------------------------------------------
  public boolean isFull() {
    // -----------------------------------------------------------------------------------
    return ( size() == max_elements );
  }

  // =====================================================================================
  /** @brief Is Empty.
   *  @return true if the buffer is empty.
   */
  // -------------------------------------------------------------------------------------
  public boolean isEmpty() {
    // -----------------------------------------------------------------------------------
    return queue.isEmpty();
  }

  // =====================================================================================
  /** @brief Add.
   *  @param e element to be added to the tail of the queue.
   *
   *  Add a new element to the circular queue. If the queue is full the oldest element
   *  will be overwritten. This element is the youngest element in the queue.
   */
  // -------------------------------------------------------------------------------------
  public void add( E e ) {
    // -----------------------------------------------------------------------------------
    if ( isFull() ) {
      queue.poll();
    }
    queue.add( e );
  }

  // =====================================================================================
  /** @brief Remove.
   *  @return the oldest element of this circular queue, or null if this queue is empty.
   *
   *  Retrieves and removes the oldest element in the queue,
   *  or returns null if this deque is empty. 
   */
  // -------------------------------------------------------------------------------------
  public E remove() {
    // -----------------------------------------------------------------------------------
    return queue.remove();
  }

  // =====================================================================================
  /** @brief Peek Old.
   *  @return the oldest element in this queue, or null if this queue is empty
   *
   *  Retrieves, but does not remove, the oldest element of this circular queue,
   *  or returns null if this deque is empty.
   */
  // -------------------------------------------------------------------------------------
  public E peekOld() {
    // -----------------------------------------------------------------------------------
    return queue.peekFirst();
  }

  // =====================================================================================
  /** @brief Peek New.
   *  @return the newest element in this queue, or null if this queue is empty
   *
   *  Retrieves, but does not remove, the newest element of this circular queue,
   *  or returns null if this deque is empty. 
   */
  // -------------------------------------------------------------------------------------
  public E peekNew() {
    // -----------------------------------------------------------------------------------
    return queue.peekLast();
  }


  // =====================================================================================
  /** @brief To Array.
   *  @return an array containing all of the elements in this queue.
   *
   *  Returns an array containing all of the elements in this circular queue, in proper
   *  sequence (from oldest to newest element).  
   */
  // -------------------------------------------------------------------------------------
  public E[] toArray( E[] array ) {
    // -----------------------------------------------------------------------------------
    int count = 0;
    for ( E obj : queue ) {
      array[count] = obj;
      count += 1;
    }

    return array;
  }


} // end class CircularQueue




// =======================================================================================
// **                             C I R C U L A R Q U E U E                             **
// =========================================================================== END FILE ==
