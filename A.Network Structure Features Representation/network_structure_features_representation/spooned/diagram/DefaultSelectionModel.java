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
package diagram;
/**
 *
 * @class DefaultSelectionModel
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This is a simpled implementation of a model that keeps track
of selected Objects.
 */
public class DefaultSelectionModel extends diagram.AbstractSelectionModel {
    // List to back this model
    private java.util.ArrayList selectionList = new java.util.ArrayList();

    /**
     * Test an item to see if it is a member of the current
     * selection.
     *
     * @param Figure
     * @return boolean
     */
    public boolean contains(diagram.Figure f) {
        return selectionList.contains(f);
    }

    public boolean contains(diagram.Link l) {
        return false;
    }

    /**
     * Add one item to the current selection
     *
     * @param Figure
     */
    public void add(diagram.Figure f) {
        add(f, false);
    }

    /**
     * Add one item to the current selection
     *
     * @param Figure
     * @param boolean
     * 		clear flag
     */
    public void add(diagram.Figure f, boolean reset) {
        if (reset)
            clear();

        selectionList.add(f);
        fireFigureAdded(f);
    }

    /**
     * Remove one item from the current selection
     *
     * @param Figure
     */
    public void remove(diagram.Figure f) {
        selectionList.remove(f);
        fireFigureRemoved(f);
    }

    /**
     * Remove all items from the current selection
     */
    public void clear() {
        // Removing the last item involves less internal work by the arraylist
        while (!selectionList.isEmpty())
            remove(((diagram.Figure) (selectionList.get(selectionList.size() - 1))));

    }

    /**
     * Get the size of the current selection
     *
     * @return int
     */
    public int size() {
        return selectionList.size();
    }

    /**
     * Get an Iterator over the currently selected items of a
     * certain Class hierarchy.
     *
     * This implementation returns an Iterator that will auto-reset.
     * Other object could safely cache an Iterator returned for reuse later.
     *
     * @return Iterator
     */
    public java.util.Iterator iterator() {
        return new diagram.DefaultSelectionModel.ValueIterator();
    }

    /**
     * Get all selected items. The items returned will be pruned by
     * the arryas element class if any. Passing a Figure[] array would return
     * all Figure classes & subclasses in the selection model.
     *
     * @param Object[]
     * 		- avoid allocating a new array
     * @return Object[]
     */
    public java.lang.Object[] toArray(java.lang.Object[] a) {
        java.lang.Class itemClass = (a == null) ? java.lang.Object.class : a.getClass().getComponentType();
        if (itemClass == java.lang.Object.class)
            return selectionList.toArray(a);

        // New array needed, pick a decent element class
        int len = selectionList.size();
        if (a.length < len)
            a = ((java.lang.Object[]) (java.lang.reflect.Array.newInstance(itemClass, len)));

        // Copy by class
        int n = 0;
        for (int i = 0; i < len; i++) {
            java.lang.Object o = selectionList.get(i);
            java.lang.Class c = o.getClass();
            if ((c == itemClass) || itemClass.isAssignableFrom(c))
                a[n++] = o;

        }
        // Null terminate
        if (n < a.length)
            java.util.Arrays.fill(a, n, a.length, null);

        return a;
    }

    /**
     *
     * @class ValueIterator

    Simple auto resetting iterator.
     */
    protected class ValueIterator implements java.util.Iterator {
        int n = 0;

        public boolean hasNext() {
            if ((n++) < selectionList.size())
                return true;

            // Auto reset
            reset();
            return false;
        }

        public java.lang.Object next() {
            return selectionList.get(n);
        }

        public void remove() {
        }

        public void reset() {
            n = 0;
        }
    }
}