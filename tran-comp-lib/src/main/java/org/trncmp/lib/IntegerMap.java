// ====================================================================== BEGIN FILE =====
// **                                I N T E G E R M A P                                **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2017, Stephen W. Soliday                                           **
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
 * @brief   Map of template objects using integer as the key.
 * @file    IntegerMap.java
 *
 * @details Provides the interface and procedures for an integer keyed template valued
 *          HashMap.
 *
 * @date    2017-12-14
 */
// =======================================================================================

package org.trncmp.lib;

import java.util.*;

// =======================================================================================
public class IntegerMap<T> {
  // -------------------------------------------------------------------------------------

  protected HashMap<Integer, T> imap = null;

  // =====================================================================================
  /** @brief Section Iterator
   *  Iterator for the hashmap to iterate and return the sections
   */
  // -------------------------------------------------------------------------------------
  static public class Iterator<S> {
    // -----------------------------------------------------------------------------------
    protected HashMap<Integer,S>                         tmp_map           = null;
    protected java.util.Iterator< Map.Entry<Integer,S> > hash_map_iterator = null;


    // ===================================================================================
    /** @brief Constructor.
     *  @param ss reference to a the hash map.
     */
    // -----------------------------------------------------------------------------------
    public Iterator( HashMap<Integer, S> ss ) {
      // ---------------------------------------------------------------------------------
      tmp_map = ss;
      rewind();
    }

    // ===================================================================================
    /** @brief Has next Item.
     *  @return True if map is not empty.
     *
     *  Check the hash map for the presence of entries.
     */
    // -----------------------------------------------------------------------------------
    public boolean hasNext() {
      // ---------------------------------------------------------------------------------
      return hash_map_iterator.hasNext();
    }
	
    // ===================================================================================
    /** @brief Next.
     *  @return next element in the hash map.
     *  @throws NoSuchElementException.
     */
    // -----------------------------------------------------------------------------------
    public S next() throws NoSuchElementException {
      // ---------------------------------------------------------------------------------
      return hash_map_iterator.next().getValue();
    }

    // ===================================================================================
    /** @brief Rewind.
     */
    // -----------------------------------------------------------------------------------
    public void rewind() {
      // ---------------------------------------------------------------------------------
      hash_map_iterator = tmp_map.entrySet().iterator();
    }
  };

    
  // =====================================================================================
  /** @brief Constructor.
   */
  // -------------------------------------------------------------------------------------
  public IntegerMap() {
    // -----------------------------------------------------------------------------------
    imap = new HashMap<Integer, T>();
  }

  // =====================================================================================
  /** @brief Size.
   *  @return number of items in the map.
   */
  // -------------------------------------------------------------------------------------
  public int size() {
    // -----------------------------------------------------------------------------------
    return imap.size();
  }

  // =====================================================================================
  /** @brief Set.
   *  @param key integer key.
   *  @param obj reference to an object.
   */
  // -------------------------------------------------------------------------------------
  public void set( Integer key, T obj ) {
    // -----------------------------------------------------------------------------------
    imap.put( key, obj );
  }
    
  // =====================================================================================
  /** @brief Has Key.
   *  @param key integer key.
   *  @return true if the map contains object referenced by key.
   */
  // -------------------------------------------------------------------------------------
  public boolean hasKey( Integer key ) {
    // -----------------------------------------------------------------------------------
    if ( 0 > key ) {
      return false;
    }
    return imap.containsKey( key );
  }

  // =====================================================================================
  /** @brief Get.
   *  @param key integer key.
   *  @return reference to the object mapped by the key.
   */
  // -------------------------------------------------------------------------------------
  public T get( Integer key ) {
    // -----------------------------------------------------------------------------------
    return imap.get( key );
  }

  // =====================================================================================
  /** @brief Get Iterator.
   *  @return reference to an iterator.
   */
  // -------------------------------------------------------------------------------------
  public IntegerMap.Iterator<T> iterator() {
    // -----------------------------------------------------------------------------------
    return new IntegerMap.Iterator<T>( imap );
  }

  // =====================================================================================
  /** @brief Get Keys.
   *  @return return array of keys.
   */
  // -------------------------------------------------------------------------------------
  public int[] getKeys() {
    // -----------------------------------------------------------------------------------
    Set<Integer> set = imap.keySet();
    java.util.Iterator<Integer> it = set.iterator();
    int n = set.size();
    int[] temp = new int[n];
    int idx = 0;
    while( it.hasNext() ) {
      Integer x = it.next();
      temp[idx] = x.intValue();
      idx += 1;
    }
    return temp;
  }

}


// =======================================================================================
// **                                I N T E G E R M A P                                **
// =========================================================================== END FILE ==
