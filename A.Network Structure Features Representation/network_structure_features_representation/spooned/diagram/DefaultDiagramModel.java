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
 * @class DefaultDiagramModel
 * @author Eric Crahen
 * @date 08-20-2001
 * @version 1.0

The DefaultDiagramModel implements a working DiagramModel that is
capable of storing and removing various Figures, but not associating
data with those Figures.

Serialization of the default model is accoomplished by through a simple
format. A table of all Figures that are a part of this model is written,
followed by a table of extra information for each Figure. This allows
shared objects (such as Link endpoints and some compound Figures) to be
handled correctly (no duplicates are created just because a Figure is
references several times).

Derivatives of this class should  override the writeExternal(Figure, ...)
and readExternal(Figure, ...) methods to customize what extra
information is store for each Figure.


TODO: Update the externalization methods to handle links to Figures not
members of the Model.
 */
public class DefaultDiagramModel extends diagram.AbstractDiagramModel implements java.io.Serializable {
    private static diagram.FigureComparator comparator = new diagram.FigureComparator();

    private java.util.ArrayList figures = new java.util.ArrayList();

    private java.util.HashMap valueMap = new java.util.HashMap();

    /**
     *
     * @param Figure
     */
    public void add(diagram.Figure fig) {
        if (!figures.contains(fig)) {
            figures.add(fig);
            fireFigureAdded(fig);
        }
    }

    /**
     *
     * @param Figure
     */
    public void remove(diagram.Figure fig) {
        if (figures.contains(fig)) {
            figures.remove(fig);
            fireFigureRemoved(fig);
        }
    }

    /**
     * Remove all figures from the model
     */
    public void clear() {
        while (!figures.isEmpty())
            fireFigureRemoved(((diagram.Figure) (figures.remove(figures.size() - 1))));

        valueMap.clear();
    }

    /**
     * Get the number of Figures in this model
     */
    public int size() {
        return figures.size();
    }

    /**
     *
     * @return Iterator
     */
    public java.util.Iterator iterator() {
        return new diagram.DefaultDiagramModel.RepeatingIterator(figures);
    }

    /**
     * Associate a value with a Figure
     *
     * @param Figure
     * @param Object
     */
    public void setValue(diagram.Figure figure, java.lang.Object value) {
        valueMap.put(figure, value);
    }

    /**
     * Get value associated with a Figure
     *
     * @param Figure
     * @return Object
     */
    public java.lang.Object getValue(diagram.Figure figure) {
        return valueMap.get(figure);
    }

    /**
     *
     * @class RepeatingIterator

    Iterator implementation that will walk over the elements of some
    List. Once the end of the list is reached, and has been tested once
    with hasNext() returning false the Iterator is reset. It can then
    walk over the set of Objects in the List once again.
     */
    protected class RepeatingIterator implements java.util.Iterator {
        private java.util.List list;

        private int index;

        public RepeatingIterator(java.util.List list) {
            this.list = list;
            this.index = -1;
        }

        public boolean hasNext() {
            if ((++index) == list.size()) {
                index = -1;
                return false;
            }
            return true;
        }

        public java.lang.Object next() {
            return list.get(index);
        }

        public void remove() {
        }
    }

    /**
     * Get all selected items. The items returned will be pruned by
     * the arrays element class if any. Passing a Figure[] array would return
     * all Figure classes & subclasses in the selection model.
     *
     * @param Object[]
     * 		- avoid allocating a new array
     * @return Object[]
     */
    public java.lang.Object[] toArray(java.lang.Object[] a) {
        java.lang.Class itemClass = (a == null) ? java.lang.Object.class : a.getClass().getComponentType();
        if (itemClass == java.lang.Object.class)
            return figures.toArray(a);

        // New array needed, pick a decent element class
        int len = figures.size();
        if (a.length < len)
            a = ((java.lang.Object[]) (java.lang.reflect.Array.newInstance(itemClass, len)));

        // Copy by class
        int n = 0;
        for (int i = 0; i < len; i++) {
            java.lang.Object o = figures.get(i);
            java.lang.Class c = o.getClass();
            if ((c == itemClass) || itemClass.isAssignableFrom(c))
                a[n++] = o;

        }
        // Null terminate
        if (n < a.length)
            java.util.Arrays.fill(a, n, a.length, null);

        return a;
    }
}