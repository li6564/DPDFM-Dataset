/**
 * Java Diagram Package; An extremely flexible and fast multipurpose diagram
 * component for Swing.
 * Copyright (C) 2001  Eric Crahen <crahen@cse.buffalo.edu>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package util;
import java.util.Collection;
/**
 *
 * @class MultiMap
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

MutliMaps are similar to regular maps except that a MutliMap
can have more than one entry mapped from a single key.
 */
public interface MultiMap extends java.util.Map {
    /**
     * Returns true if this map maps the key to this value.
     *
     * @param Object
     * 		key
     * @param Object
     * 		value
     * @return boolean
     */
    public boolean contains(java.lang.Object key, java.lang.Object value);

    /**
     * Removes a particular mapping, or all mappings to the given
     * value if the key is null.
     *
     * @param Object
     * 		key or all keys if null
     * @param Object
     * 		value
     */
    public void remove(java.lang.Object key, java.lang.Object value);

    /**
     * Removes the all mappings for this key from this map
     *
     * @param Object
     * 		key
     */
    public void removeAll(java.lang.Object key);

    /**
     * Store this item in the map, uses an internal object as the key. This
     * can be retrieved later using a null key.
     *
     * @param Object
     */
    public void put(java.lang.Object value);

    /**
     * Create all of the mappings from the specified key to the elements in
     * the given Collection.
     *
     * @param Object
     * @param Collection
     */
    public void putAll(java.lang.Object key, java.util.Collection c);

    /**
     * Create all of the mappings from the specified key to the elements in
     * the given Collection.
     *
     * @param Object
     * @param Object[]
     */
    public void putAll(java.lang.Object key, java.lang.Object[] o);

    /**
     * Get all the values mapped to the given key
     *
     * @param Object
     * 		key or null for all values
     * @param Object[]
     * 		avoid extra allocation
     */
    public java.lang.Object[] getAll(java.lang.Object key, java.lang.Object[] array);

    /**
     * Returns a collection view of the values contained in this map
     * which map to a certain key.
     *
     * @return Collection
     */
    public java.util.Collection values(java.lang.Object key);

    /**
     *
     * @class Entry

    Simple Map.Entry
     */
    public static class Entry implements java.util.Map.Entry {
        public java.lang.Object key;

        public java.lang.Object val;

        public Entry() {
            this(null, null);
        }

        public Entry(java.lang.Object key, java.lang.Object val) {
            this.key = key;
            this.val = val;
        }

        public java.lang.Object getKey() {
            return this.key;
        }

        public java.lang.Object getValue() {
            return this.val;
        }

        public java.lang.Object setValue(java.lang.Object value) {
            throw new java.lang.UnsupportedOperationException();
        }

        public java.lang.String toString() {
            return ((("[" + key) + "], [") + val) + "]";
        }
    }
}