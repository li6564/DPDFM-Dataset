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
 * @interface DiagramModelListener
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public interface DiagramModelListener {
    /**
     * Notify of a new figure being added to a DiagramModel
     *
     * @param DiagramModel
     * @param Figure
     */
    public void figureAdded(diagram.DiagramModel model, diagram.Figure figure);

    /**
     * Notify of a figure being removed from a DiagramModel
     *
     * @param DiagramModel
     * @param Figure
     */
    public void figureRemoved(diagram.DiagramModel model, diagram.Figure figure);
}