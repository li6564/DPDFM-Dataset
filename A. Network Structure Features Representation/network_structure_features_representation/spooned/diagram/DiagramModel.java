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
 * @interface DiagramModel
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public interface DiagramModel {
    /**
     *
     * @param Figure
     */
    public void add(diagram.Figure figure);

    /**
     *
     * @param Figure
     */
    public void remove(diagram.Figure figure);

    /**
     * Remove all figures from the model
     */
    public void clear();

    /**
     * Get an iterator over the figures in this model
     *
     * @return Iterator
     */
    public java.util.Iterator iterator();

    /**
     * Get the number of Figures in this model
     */
    public int size();

    /**
     * Associate a value with a Figure
     *
     * @param Figure
     * @param Object
     */
    public void setValue(diagram.Figure figure, java.lang.Object value);

    /**
     * Get value associated with a Figure
     *
     * @param Figure
     * @return Object
     */
    public java.lang.Object getValue(diagram.Figure figure);

    /**
     * Add a new listener to this model.
     *
     * @param DiagramModelListener
     */
    public void addDiagramDataListener(diagram.DiagramModelListener listener);

    /**
     * Remove a listener to this model.
     *
     * @param DiagramModelListener
     */
    public void removeDiagramDataListener(diagram.DiagramModelListener listener);

    /**
     * Get all selected items. The items returned will be pruned by
     * the arryas element class if any. Passing a Figure[] array would return
     * all Figure classes & subclasses in the selection model.
     *
     * @param Object[]
     * 		- avoid allocating a new array
     * @return Object[]
     */
    public java.lang.Object[] toArray(java.lang.Object[] array);
}