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
 * @class InheritanceBuilder
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Scan the DiagramModel in the Context for all GeneralizationLink figures. Using this
information, inheritance information will be added to all the MetaClasses that have
been previously place in the Contex.
 */
public class InheritanceBuilder extends uml.builder.AbstractBuilder {
    /**
     * Assemble the code based on the information represented as a DiagramModel
     */
    public void build(uml.builder.Context ctx) throws uml.builder.BuilderException {
        identifyGeneralizations(ctx);
        checkContext(ctx);
    }

    /**
     */
    protected void identifyGeneralizations(uml.builder.Context ctx) throws uml.builder.BuilderException {
        // Walk through the GeneralizationLinks in the model and identify all the classes
        diagram.DiagramModel model = ctx.getModel();
        for (java.util.Iterator i = new diagram.FigureIterator(model, uml.diagram.GeneralizationLink.class); i.hasNext();) {
            uml.diagram.GeneralizationLink figure = ((uml.diagram.GeneralizationLink) (i.next()));
            diagram.Figure source = figure.getSource();
            diagram.Figure sink = figure.getSink();
            java.lang.String sourceName = getName(ctx, source);
            java.lang.String sinkName = getName(ctx, sink);
            // Make sure the link is somewhat valid before proceeding
            if (compatibleFigures(ctx, source, sink)) {
                // Make sure the targets of the link are present in the context
                uml.builder.MetaClass sourceClass = ctx.getMetaClass(sourceName);
                uml.builder.MetaClass sinkClass = ctx.getMetaClass(sinkName);
                // Found a generalization between two compatible classes that are present in
                // the current build context
                if ((sourceClass != null) && (sinkClass != null))
                    buildGeneralization(ctx, sourceClass, sinkClass);
                else
                    ctx.addWarning(((("skipping generalization '" + sourceName) + " - ") + sinkName) + "'");

            } else
                ctx.addWarning(((("incompatible generalization '" + sourceName) + " - ") + sinkName) + "'");

        }
    }

    /**
     * Course-grained check to determine if the two figures are compatible and valid
     * candidates for a generalization
     */
    protected boolean compatibleFigures(uml.builder.Context ctx, diagram.Figure source, diagram.Figure sink) throws uml.builder.BuilderException {
        // Check to see if the source & sink are compatible classes
        java.lang.Class sourceClass = source.getClass();
        java.lang.Class sinkClass = sink.getClass();
        if (sourceClass == sinkClass) {
            if ((sourceClass == uml.diagram.ClassFigure.class) || (sourceClass == uml.diagram.InterfaceFigure.class))
                return true;

        }
        return false;
    }

    /**
     * Create the generalization if possible. Finer grain checks for cyclical inheritance
     * and for mulitple inheritance are performed.
     */
    protected void buildGeneralization(uml.builder.Context ctx, uml.builder.MetaClass sourceClass, uml.builder.MetaClass sinkClass) throws uml.builder.BuilderException {
        // Attempt to update inheritance on the meta class
        try {
            sourceClass.setSuperClass(sinkClass, true);
        } catch (uml.builder.SyntaxException e1) {
            ctx.addWarning(e1.getMessage());
        } catch (uml.builder.SemanticException e2) {
            ctx.addError(e2.getMessage());
        }
    }
}