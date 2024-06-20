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
/**
 *
 * @class WeakList
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Simple WeakList implementation. Stores items in a List using
WeakReferences. The list is pruned as empty WeakReferences
are found.
 */
public class WeakList extends java.util.AbstractList {
    private java.lang.ref.ReferenceQueue queue = new java.lang.ref.ReferenceQueue();

    private java.util.Vector list = new java.util.Vector();

    /**
     * Wrap an object and add it to the list.
     *
     * @param Object
     * @return boolean
     */
    public boolean add(java.lang.Object o) {
        list.addElement(new java.lang.ref.WeakReference(o, queue));
        return true;
    }

    /**
     * Get an object at the given index.
     *
     * @param int
     * @return Object
     */
    public java.lang.Object get(int index) {
        java.lang.ref.WeakReference r = ((java.lang.ref.WeakReference) (list.elementAt(index)));
        java.lang.Object o = null;
        // Unwrap the reference
        if ((r == null) || ((o = r.get()) == null))
            return null;

        return o;
    }

    /**
     * Find the index of a particular item.
     *
     * @param Object
     * @return int
     */
    public int indexOf(java.lang.Object o) {
        for (int i = 0; i < list.size(); i++) {
            // Look at each element
            java.lang.ref.WeakReference r = ((java.lang.ref.WeakReference) (list.elementAt(i)));
            if ((r != null) && (r.get() == o))
                return i;

        }
        cleanUp();
        return -1;
    }

    /**
     * Find the index of a particular item.
     *
     * @param Object
     * @return int
     */
    public boolean remove(java.lang.Object o) {
        boolean found = false;
        for (int i = 0; (!found) && (i < list.size()); i++) {
            // Look at each element
            java.lang.ref.WeakReference r = ((java.lang.ref.WeakReference) (list.elementAt(i)));
            if ((r != null) && (r.get() == o)) {
                list.removeElement(r);
                found = true;
            }
        }
        cleanUp();
        return found;
    }

    /**
     * Get a decent estimate of the lists size. It could really change
     * any time since its only storing references to objects which might
     * be garbage collected after this method returns.
     */
    public int size() {
        cleanUp();
        return list.size();
    }

    /**
     * Eliminate references that are waiting to be cleaned up.
     */
    protected void cleanUp() {
        java.lang.ref.WeakReference r;
        while ((r = ((java.lang.ref.WeakReference) (queue.poll()))) != null)
            list.removeElement(r);

    }
}