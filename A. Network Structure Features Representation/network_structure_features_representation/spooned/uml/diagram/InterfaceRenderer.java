/**
 * QuickUML; A simple UML tool that demonstrates one use of the
 * Java Diagram Package
 *
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
package uml.diagram;
/**
 *
 * @class InterfaceRenderer
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class InterfaceRenderer extends diagram.DefaultFigureRenderer {
    protected uml.diagram.InterfaceComponent interfaceComponent = new uml.diagram.InterfaceComponent();

    /**
     * Get the Component to be fixed to the Figure bounds.
     */
    public java.awt.Component getUserComponent() {
        uml.diagram.InterfaceItem item = ((uml.diagram.InterfaceItem) (getDiagram().getModel().getValue(getFigure())));
        interfaceComponent.setTitle(item == null ? null : item.getName());
        interfaceComponent.setMembers(item == null ? "" : item.getDescription());
        return interfaceComponent;
    }
}