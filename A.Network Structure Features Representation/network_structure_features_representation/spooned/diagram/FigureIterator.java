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
 * @class FigureIterator
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This class allows a particular class of figures to be iterated over
 */
public class FigureIterator implements java.util.Iterator {
    private java.lang.Class figureClass;

    private diagram.Figure[] figures = new diagram.Figure[4];

    private int current = 0;

    /**
     * Create an iterator that will pull a certain class of Figures from the
     * given DiagramModel
     *
     * @param DiagramModel
     * @param Class
     */
    public FigureIterator(diagram.DiagramModel model, java.lang.Class figureClass) {
        java.lang.Class c = diagram.Figure.class;
        if (!c.isAssignableFrom(figureClass))
            throw new java.lang.RuntimeException("Can only scan a DiagramModel for Figures");

        this.figureClass = figureClass;
        // Copy the matching figures
        figures = ((diagram.Figure[]) (java.lang.reflect.Array.newInstance(figureClass, 4)));
        figures = ((diagram.Figure[]) (model.toArray(((java.lang.Object[]) (figures)))));
    }

    public java.lang.Object next() {
        return figures[current++];
    }

    public boolean hasNext() {
        return (current < figures.length) && (figures[current] != null);
    }

    public void remove() {
    }
}