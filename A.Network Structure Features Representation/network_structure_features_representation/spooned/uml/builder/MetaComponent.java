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
 * @class MetaComponent
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public abstract class MetaComponent implements java.lang.Comparable {
    private java.lang.String type;

    private java.lang.String name;

    public java.lang.String getType() {
        return type;
    }

    public void setType(java.lang.String type) {
        if (((type == null) || (type.length() < 1)) || (uml.builder.MetaAccess.parse(type) != uml.builder.MetaAccess.NONE))
            throw new uml.builder.SyntaxException("Invalid type");

        this.type = type;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        if ((name == null) || (uml.builder.MetaAccess.parse(name) != uml.builder.MetaAccess.NONE))
            throw new uml.builder.SyntaxException("Invalid name");

        this.name = name;
    }

    /**
     * Compare by name
     */
    public int compareTo(java.lang.Object o) {
        if (o instanceof uml.builder.MetaComponent) {
            uml.builder.MetaComponent m = ((uml.builder.MetaComponent) (o));
            // Compare names
            if (getName() != null)
                return getName().compareTo(m.getName());

            return m.getName() == null ? -1 : 0;
        }
        return -1;
    }

    public boolean equals(java.lang.Object o) {
        return compareTo(o) == 0;
    }

    public java.lang.String toString() {
        java.lang.StringBuffer buf = new java.lang.StringBuffer(type);
        if (name != null)
            buf.append(' ').append(name);

        return buf.toString();
    }
}