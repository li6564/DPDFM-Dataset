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
package uml.builder;
/**
 *
 * @class AbstractBuilder
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Contains a couple useful methods used by many builders
 */
public abstract class AbstractBuilder implements uml.builder.CodeBuilder {
    /**
     * Handle any errors that were detected
     */
    protected void checkContext(uml.builder.Context ctx) throws uml.builder.BuilderException {
        if (ctx.hasErrors())
            throw new uml.builder.BuilderException("Errors were detected while identifying classes");

    }

    /**
     * Get the class name for a Figure in the diagram
     */
    protected java.lang.String getName(uml.builder.Context ctx, diagram.Figure figure) {
        java.lang.Object value = ctx.getModel().getValue(figure);
        java.lang.String name = null;
        if (value instanceof uml.diagram.ClassItem)
            name = ((uml.diagram.ClassItem) (value)).getName();
        else if (value instanceof uml.diagram.InterfaceItem)
            name = ((uml.diagram.InterfaceItem) (value)).getName();
        else if (value instanceof uml.diagram.CompositionItem)
            name = ((uml.diagram.CompositionItem) (value)).getName();

        // Remove invalid names
        return (name == null) || (name.length() < 0) ? "<no name>" : name;
    }
}