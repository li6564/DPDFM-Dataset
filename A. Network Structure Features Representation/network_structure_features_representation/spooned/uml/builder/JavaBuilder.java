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
 * @class JavaBuilder
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class JavaBuilder extends uml.builder.ObjectBuilder {
    protected java.lang.String prefix;

    /**
     */
    public JavaBuilder(java.lang.String prefix) {
        this.prefix = prefix;
    }

    /**
     */
    public void build(uml.builder.Context ctx) throws uml.builder.BuilderException {
        super.build(ctx);
        try {
            // Create the code for the interfaces first
            for (java.util.Iterator i = ctx.getInterfaces(); i.hasNext();) {
                uml.builder.MetaClass metaClass = ((uml.builder.MetaClass) (i.next()));
                java.io.PrintWriter out = getWriter(metaClass);
                out.println(("public " + metaClass) + " {");
                writeMethods(metaClass, out, false);
                out.println("}");
                out.flush();
            }
            // Write the classes next
            for (java.util.Iterator i = ctx.getClasses(); i.hasNext();) {
                uml.builder.MetaClass metaClass = ((uml.builder.MetaClass) (i.next()));
                java.io.PrintWriter out = getWriter(metaClass);
                // Check is ArrayList is needed
                for (java.util.Iterator j = metaClass.getAttributes(); j.hasNext();) {
                    uml.builder.MetaAttribute attr = ((uml.builder.MetaAttribute) (j.next()));
                    if (attr.isArray() && (attr.getArraySize() == (-1))) {
                        out.println("import java.util.ArrayList;\n");
                        break;
                    }
                }
                out.println(("public " + metaClass) + " {");
                writeAttributes(metaClass, out);
                writeMethods(metaClass, out, true);
                // Write the methods
                out.println("}");
                out.flush();
            }
        } catch (java.io.IOException e) {
            throw new uml.builder.BuilderException("I/O Error: " + e.getMessage());
        }
    }

    /**
     * Get an output stream for a class
     */
    protected java.io.PrintWriter getWriter(uml.builder.MetaClass meta) throws java.io.IOException {
        java.lang.String last = "/";
        java.lang.String name = meta.getName();
        int m = 0;
        int n = 0;
        do {
            if ((n = name.indexOf('.', m)) > 0) {
                java.lang.String dir = name.substring(m, n);
                m = ++n;
                last += dir;
                java.io.File f = new java.io.File(prefix + last);
                f.mkdir();
            }
        } while (n > 0 );
        return new java.io.PrintWriter(new java.io.FileOutputStream(((prefix + "/") + name.replace('.', '/')) + ".java"));
    }

    /**
     * Write the attributes section, with a little space in between top and bottom
     */
    protected void writeAttributes(uml.builder.MetaClass metaClass, java.io.PrintWriter out) throws java.io.IOException {
        out.println("");
        for (java.util.Iterator i = metaClass.getAttributes(); i.hasNext();) {
            uml.builder.MetaAttribute attr = ((uml.builder.MetaAttribute) (i.next()));
            if (attr.isArray() && (attr.getArraySize() < 0))
                out.println(("\tprivate ArrayList " + attr.getName()) + ";");
            else
                out.println(("\t" + attr) + ";");

        }
    }

    /**
     * Write the attributes section, with a little space in between top and bottom
     */
    protected void writeMethods(uml.builder.MetaClass metaClass, java.io.PrintWriter out, boolean withBody) throws java.io.IOException {
        out.println("");
        for (java.util.Iterator i = metaClass.getMethods(); i.hasNext();) {
            uml.builder.MetaMethod m = ((uml.builder.MetaMethod) (i.next()));
            if (m instanceof uml.builder.MetaConstructor)
                writeConstructor(metaClass, ((uml.builder.MetaConstructor) (m)), out);
            else {
                out.print("\t" + m);
                out.print(withBody ? " {\n\n\t}\n\n" : ";\n");
            }
        }
    }

    protected void writeConstructor(uml.builder.MetaClass metaClass, uml.builder.MetaConstructor cons, java.io.PrintWriter out) throws java.io.IOException {
        java.lang.String signature = cons.toString();
        int n = signature.indexOf("void <init>");
        java.lang.StringBuffer buf = new java.lang.StringBuffer(signature);
        buf.replace(n, n + 11, metaClass.getName());
        signature = buf.toString();
        out.print("\t" + signature);
        out.print(" {\n\n");
        // Write the constructor body
        for (java.util.Iterator i = cons.getAssociations(), j = cons.getParameters(); i.hasNext();) {
            uml.builder.MetaAttribute attr = ((uml.builder.MetaAttribute) (i.next()));
            uml.builder.MetaParameter param = ((uml.builder.MetaParameter) (j.next()));
            out.print("\t\tthis.");
            out.print(attr.getName());
            out.print(" = ");
            out.print(param.getName());
            out.print(";\n");
        }
        for (java.util.Iterator i = cons.getCompositions(); i.hasNext();) {
            uml.builder.MetaAttribute attr = ((uml.builder.MetaAttribute) (i.next()));
            out.print("\t\tthis.");
            out.print(attr.getName());
            out.print(" = new ");
            // Check for an array name
            java.lang.String type = attr.getType();
            if (!attr.isArray()) {
                out.print(type);
                out.print("();\n");
            } else {
                int len = attr.getArraySize();
                // '*' cardinality
                if (len < 0)
                    out.print("ArrayList()");
                else {
                    // array
                    out.print(type + "[] {");
                    for (n = 0; n < len; n++) {
                        if (n != 0)
                            out.print(", ");

                        out.print(("new " + type) + "()");
                    }
                    out.print("}");
                }
                out.print(";\n");
            }
        }
        out.print("\n\t}\n\n");
    }
}