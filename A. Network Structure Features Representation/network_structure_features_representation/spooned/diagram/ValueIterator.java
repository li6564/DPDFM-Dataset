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
 * @class ValueIterator
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Allows the values for a particular class of Figures to be iterated over, including nulls.
 */
public class ValueIterator extends diagram.FigureIterator {
    private diagram.DiagramModel model;

    private java.lang.Object value;

    private boolean haveNext = false;

    private boolean includeNulls;

    /**
     * Create an iterator that will pull values for a a certain class of Figures from the
     * given DiagramModel
     *
     * @param DiagramModel
     * @param Class
     */
    public ValueIterator(diagram.DiagramModel model, java.lang.Class figureClass) {
        this(model, figureClass, true);
    }

    public ValueIterator(diagram.DiagramModel model, java.lang.Class figureClass, boolean includeNulls) {
        super(model, figureClass);
        this.model = model;
        this.includeNulls = includeNulls;
    }

    public java.lang.Object next() {
        // Check for the next element
        if (!haveNext)
            haveNext = hasNext();

        if (!haveNext)
            throw new java.util.NoSuchElementException();

        haveNext = false;
        return value;
    }

    public boolean hasNext() {
        if (super.hasNext() && (!haveNext)) {
            value = model.getValue(((diagram.Figure) (super.next())));
            if (((!includeNulls) && (value == null)) || includeNulls)
                haveNext = true;

        }
        return haveNext;
    }
}