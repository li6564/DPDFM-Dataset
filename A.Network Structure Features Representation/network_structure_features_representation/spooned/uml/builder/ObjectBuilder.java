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
 * @class ObjectBuilder
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Fills the Context with a set of MetaClasses that contain the approriate
relationships.
 */
public class ObjectBuilder implements uml.builder.CodeBuilder {
    protected static uml.builder.ClassBuilder classBuilder = new uml.builder.ClassBuilder();

    protected static uml.builder.InheritanceBuilder inheritanceBuilder = new uml.builder.InheritanceBuilder();

    protected static uml.builder.RealizationBuilder realizationBuilder = new uml.builder.RealizationBuilder();

    protected static uml.builder.CompositionBuilder compositionBuilder = new uml.builder.CompositionBuilder();

    protected static uml.builder.AssociationBuilder associationBuilder = new uml.builder.AssociationBuilder();

    /**
     */
    public void build(uml.builder.Context ctx) throws uml.builder.BuilderException {
        uml.builder.ObjectBuilder.classBuilder.build(ctx);
        uml.builder.ObjectBuilder.inheritanceBuilder.build(ctx);
        uml.builder.ObjectBuilder.realizationBuilder.build(ctx);
        uml.builder.ObjectBuilder.compositionBuilder.build(ctx);
        uml.builder.ObjectBuilder.associationBuilder.build(ctx);
        // Add the methods to the meta classes
        buildConstructors(ctx);
    }

    /**
     * Create constructors for the classes
     */
    protected void buildConstructors(uml.builder.Context ctx) {
        for (java.util.Iterator i = ctx.getClasses(); i.hasNext();) {
            uml.builder.MetaClass metaClass = ((uml.builder.MetaClass) (i.next()));
            // Collect the associations for this class
            java.util.Vector attrs = new java.util.Vector();
            for (java.util.Iterator j = new util.FilteredIterator(metaClass.getAttributes(), uml.builder.MetaAssociation.class); j.hasNext();)
                attrs.add(j.next());

            // Collect the compositions for this class
            java.util.Vector comps = new java.util.Vector();
            for (java.util.Iterator j = new util.FilteredIterator(metaClass.getAttributes(), uml.builder.MetaComposition.class); j.hasNext();)
                comps.add(j.next());

            // no constructor needed?
            if (attrs.isEmpty() && comps.isEmpty())
                continue;

            // Create the constructor for the class
            uml.builder.MetaConstructor cons = new uml.builder.MetaConstructor(((uml.builder.MetaAttribute[]) (attrs.toArray(new uml.builder.MetaAttribute[attrs.size()]))), ((uml.builder.MetaAttribute[]) (comps.toArray(new uml.builder.MetaAttribute[comps.size()]))));
            metaClass.addMethod(cons);
        }
    }
}