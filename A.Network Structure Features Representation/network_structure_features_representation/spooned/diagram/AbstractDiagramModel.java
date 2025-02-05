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
 * @class AbstractDiagramModel
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public abstract class AbstractDiagramModel implements diagram.DiagramModel {
    private util.WeakList listeners = new util.WeakList();

    /**
     * Add a new listener to this model.
     *
     * @param DiagramModelListener
     */
    public void addDiagramDataListener(diagram.DiagramModelListener l) {
        listeners.add(l);
    }

    /**
     * Remove a listener to this model.
     *
     * @param DiagramModelListener
     */
    public void removeDiagramDataListener(diagram.DiagramModelListener l) {
        listeners.remove(l);
    }

    /**
     * Fire the figure selected event.
     *
     * @param Figure
     */
    protected void fireFigureAdded(diagram.Figure figure) {
        for (int i = 0; i < listeners.size(); i++) {
            diagram.DiagramModelListener l = ((diagram.DiagramModelListener) (listeners.get(i)));
            if (l != null)
                l.figureAdded(this, figure);

        }
    }

    /**
     * Fire the figure removed event.
     *
     * @param Figure
     */
    protected void fireFigureRemoved(diagram.Figure figure) {
        for (int i = 0; i < listeners.size(); i++) {
            diagram.DiagramModelListener l = ((diagram.DiagramModelListener) (listeners.get(i)));
            if (l != null)
                l.figureRemoved(this, figure);

        }
    }
}