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
 * @class MetaConstructor
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This class represents a methods contract - its name, its parameters, return type
and its exceptions.
 */
public class MetaConstructor extends uml.builder.MetaMethod {
    private uml.builder.MetaAttribute[] associations;

    private uml.builder.MetaAttribute[] compositions;

    /**
     * Create a default constructor
     */
    public MetaConstructor() {
        this(null, null);
    }

    /**
     * Create a constructor based on a set of attributes that should be assigned values
     * from parameters, and a set of attributes that should be initialized with thier
     * default constructors.
     */
    public MetaConstructor(uml.builder.MetaAttribute[] associations, uml.builder.MetaAttribute[] compositions) {
        // Create the signature
        super("public void <init>()");
        if (associations != null)
            initParameters(associations);

        this.associations = associations;
        this.compositions = compositions;
    }

    public void initParameters(uml.builder.MetaAttribute[] associations) {
        for (int i = 0; i < associations.length; i++)
            addParameter(new uml.builder.MetaParameter((associations[i].getType() + " ") + associations[i].getName()));

    }

    public java.util.Iterator getAssociations() {
        return getAttributeIterator(associations);
    }

    public java.util.Iterator getCompositions() {
        return getAttributeIterator(compositions);
    }

    /**
     * Get an Iterator over a set of attributes
     */
    private final java.util.Iterator getAttributeIterator(uml.builder.MetaAttribute[] attr) {
        final uml.builder.MetaAttribute[] array = attr;
        return new java.util.Iterator() {
            int n = 0;

            public boolean hasNext() {
                return (array != null) && (n < array.length);
            }

            public java.lang.Object next() {
                if (!hasNext())
                    throw new java.util.NoSuchElementException();

                return array[n++];
            }

            public void remove() {
            }
        };
    }
}