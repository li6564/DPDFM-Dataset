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
import diagram.Diagram;
import diagram.Figure;
/**
 *
 * @class ClassEditor
 * @date 08-27-2001
 * @author Eric Crahen
 * @version 1.1
 */
public class ClassEditor extends diagram.DefaultFigureEditor {
    protected uml.diagram.ClassEditorComponent classComponent = new uml.diagram.ClassEditorComponent();

    protected uml.diagram.ClassFigure lastFigure;

    protected uml.diagram.ClassItem lastValue;

    /**
     * This method is the first invoked when the editing process begins.
     *
     * @param Diagram
     * 		surface that is being rendered upon
     * @param Figure
     * 		item to edit
     * @return suitable A Component that can render the given item is returned.
     * @post the Component returned will most likey be changed, in that it will
    have been reparented & its bounds will be reset. Each time this Component
    is used this will happen, so generally it is not neccessary for the
    returned item to bother setting its own size.
     */
    public java.awt.Component getFigureEditorComponent(Diagram diagram, Figure figure, boolean isSelected) {
        // Reuse the value object
        lastValue = ((uml.diagram.ClassItem) (diagram.getModel().getValue(figure)));
        lastFigure = ((uml.diagram.ClassFigure) (figure));
        int last = lastFigure.getDividerLocation();
        classComponent.setDividerLocation(last);
        return classComponent;
    }

    /**
     * When editing stops, update the last divider bounds in the ClassFigure
     */
    protected void fireEditingStopped() {
        super.fireEditingStopped();
        int last = classComponent.getDividerLocation();
        lastFigure.setDividerLocation(last);
    }

    /**
     */
    public java.lang.Object getCellEditorValue() {
        if (lastValue == null)
            lastValue = new uml.diagram.ClassItem();

        lastValue.setName(classComponent.getTitle());
        lastValue.setAttributes(classComponent.getFields());
        lastValue.setDescription(classComponent.getMembers());
        return lastValue;
    }
}