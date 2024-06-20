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
 * @class CPlusPlusBuilder
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class CPlusPlusBuilder extends uml.builder.ObjectBuilder {
    protected static final uml.builder.MetaAccessComparator attributeComparator = new uml.builder.MetaAccessComparator();

    protected java.lang.String prefix;

    /**
     */
    public CPlusPlusBuilder(java.lang.String prefix) {
        this.prefix = prefix;
    }

    /**
     */
    public void build(uml.builder.Context ctx) throws uml.builder.BuilderException {
        super.build(ctx);
        try {
            // Create the code for the headers first
            for (java.util.Iterator i = ctx.getInterfaces(); i.hasNext();) {
                uml.builder.MetaClass metaClass = ((uml.builder.MetaClass) (i.next()));
                java.io.PrintWriter out = getWriter(metaClass, "h");
                writeHeader(metaClass, out);
                out.flush();
            }
            for (java.util.Iterator i = ctx.getClasses(); i.hasNext();) {
                uml.builder.MetaClass metaClass = ((uml.builder.MetaClass) (i.next()));
                java.io.PrintWriter out = getWriter(metaClass, "h");
                writeHeader(metaClass, out);
                writeSource(metaClass, getWriter(metaClass, "cc"));
                out.flush();
            }
        } catch (java.io.IOException e) {
            throw new uml.builder.BuilderException("I/O Error: " + e.getMessage());
        }
    }

    /**
     * Get an output stream for a class
     */
    protected java.io.PrintWriter getWriter(uml.builder.MetaClass meta, java.lang.String ext) throws java.io.IOException {
        return new java.io.PrintWriter(new java.io.FileOutputStream((((prefix + "/") + meta.getName()) + ".") + ext));
    }

    /**
     * Write the header
     */
    protected void writeHeader(uml.builder.MetaClass metaClass, java.io.PrintWriter out) throws java.io.IOException {
        // Write the ifndefs
        java.lang.String def = ("_" + metaClass.getName().toUpperCase()) + "_H_";
        out.println("#ifndef " + def);
        out.println(("#define " + def) + "\n");
        // Collect all inheritance infor
        java.util.Vector v = new java.util.Vector();
        uml.builder.MetaClass superClass = metaClass.getSuperClass();
        if (superClass != null)
            v.add(superClass.getName());

        for (java.util.Iterator i = metaClass.getInterfaces(); i.hasNext();)
            v.add(((uml.builder.MetaClass) (i.next())).getName());

        // Write the includes needed
        for (int j = 0; j < v.size(); j++)
            out.println(("#include \"" + v.get(j)) + ".h\"");

        java.util.Vector used = new java.util.Vector();
        for (java.util.Iterator i = new util.FilteredIterator(metaClass.getAttributes(), uml.builder.MetaAssociation.class); i.hasNext();) {
            uml.builder.MetaAttribute attr = ((uml.builder.MetaAttribute) (i.next()));
            java.lang.String name = attr.getType();
            if ((!v.contains(name)) && (!used.contains(name))) {
                out.println(("#include \"" + name) + ".h\"");
                used.add(name);
            }
        }
        // Write declaration
        out.print("\nclass " + metaClass.getName());
        int sz = v.size();
        if (sz > 0) {
            out.print(" : public " + v.get(0));
            for (int j = 1; j < sz; j++)
                out.print(", public " + v.get(j));

        }
        out.print(" {\n");
        // Write the attributes if this is a class
        if (!metaClass.isInterface())
            writeHeaderAttributes(metaClass, out);

        // Write the methods
        writeHeaderMethods(metaClass, out);
        out.println("};\n");
        out.println("#endif // " + def);
    }

    /**
     * Write the attributes
     */
    protected void writeHeaderAttributes(uml.builder.MetaClass metaClass, java.io.PrintWriter out) throws java.io.IOException {
        // Collect & sort all the attributes
        java.util.Vector v = new java.util.Vector();
        for (java.util.Iterator i = metaClass.getAttributes(); i.hasNext();)
            v.add(i.next());

        java.util.Collections.sort(v, uml.builder.CPlusPlusBuilder.attributeComparator);
        int n;
        int last = 0;
        for (int j = 0; j < v.size(); j++) {
            uml.builder.MetaAttribute attr = ((uml.builder.MetaAttribute) (v.get(j)));
            // C's default acces is private, so clarify it in the header
            n = attr.getAccess() & (~(uml.builder.MetaAccess.ABSTRACT | uml.builder.MetaAccess.STATIC));
            if (n == 0)
                n = uml.builder.MetaAccess.PRIVATE;

            if (last != n) {
                last = n;
                out.println(uml.builder.MetaAccess.toString(n) + ":\n");
            }
            out.println(((("\t" + attr.getType()) + " ") + attr.getName()) + ";");
        }
        out.print("\n");
    }

    /**
     * Write the attributes section, with a little space in between top and bottom
     */
    protected void writeHeaderMethods(uml.builder.MetaClass metaClass, java.io.PrintWriter out) throws java.io.IOException {
        // Collect & sort all the methods
        int last = uml.builder.MetaAccess.PUBLIC;
        out.println("public: ");
        java.util.Vector v = new java.util.Vector();
        for (java.util.Iterator i = metaClass.getMethods(); i.hasNext();) {
            java.lang.Object o = i.next();
            if (o instanceof uml.builder.MetaConstructor)
                writeHeaderConstructor(metaClass, ((uml.builder.MetaConstructor) (o)), out);
            else
                v.add(o);

        }
        // Write a descrutor
        writeHeaderDestructor(metaClass, out);
        int n;
        for (int j = 0; j < v.size(); j++) {
            uml.builder.MetaMethod meth = ((uml.builder.MetaMethod) (v.get(j)));
            // C's default acces is private, so clarify it in the header
            n = meth.getAccess() & (~(uml.builder.MetaAccess.ABSTRACT | uml.builder.MetaAccess.STATIC));
            if (n == 0)
                n = uml.builder.MetaAccess.PRIVATE;

            if (last != n) {
                last = n;
                out.println(("\n" + uml.builder.MetaAccess.toString(n)) + ":\n");
            }
            java.lang.String signature = ((((meth.getType() + " ") + meth.getName()) + "(") + uml.builder.MetaParameter.toString(meth)) + ")";
            if (meth.hasExceptions())
                signature += (" throw(" + uml.builder.MetaException.toString(meth)) + ")";

            if (metaClass.isInterface())
                out.println(("\tvirtual " + signature) + " = 0;");
            else {
                if (uml.builder.MetaAccess.isAbstract(meth.getAccess()))
                    signature = ("virtual " + signature) + " = 0";
                else if (uml.builder.MetaAccess.isStatic(meth.getAccess()))
                    signature = "static " + signature;

                out.println(("\t" + signature) + ";");
            }
        }
        out.print("\n");
    }

    /**
     */
    protected void writeHeaderDestructor(uml.builder.MetaClass metaClass, java.io.PrintWriter out) throws java.io.IOException {
        out.print(("\tvirtual ~" + metaClass.getName()) + "()");
        out.println(metaClass.isInterface() ? " {};" : ";");
    }

    /**
     */
    protected void writeHeaderConstructor(uml.builder.MetaClass metaClass, uml.builder.MetaConstructor cons, java.io.PrintWriter out) throws java.io.IOException {
        java.lang.String signature = ((metaClass.getName() + "(") + uml.builder.MetaParameter.toString(cons)) + ")";
        if (cons.hasExceptions())
            signature += (" throw(" + uml.builder.MetaException.toString(cons)) + ")";

        out.print("\t" + signature);
        out.println(metaClass.isInterface() ? " {};" : ";");
    }

    /**
     */
    protected void writeSource(uml.builder.MetaClass metaClass, java.io.PrintWriter out) throws java.io.IOException {
        out.println(("#include \"" + metaClass.getName()) + ".h\"\n");
        boolean writtenDestructor = false;
        for (java.util.Iterator i = metaClass.getMethods(); i.hasNext();) {
            uml.builder.MetaMethod method = ((uml.builder.MetaMethod) (i.next()));
            if (method instanceof uml.builder.MetaConstructor)
                writeSourceConstructor(metaClass, ((uml.builder.MetaConstructor) (method)), out);
            else {
                if (!writtenDestructor) {
                    writtenDestructor = true;
                    writeSourceDestructor(metaClass, out);
                }
                out.print((((method.getType() + " ") + metaClass.getName()) + "::") + method.getName());
                out.print(("(" + uml.builder.MetaParameter.toString(method)) + ")");
                if (method.hasExceptions())
                    out.print((" throw(" + uml.builder.MetaException.toString(method)) + ")");

                out.print(" { \n\n");
                out.print("}\n\n");
            }
        }
        out.flush();
    }

    /**
     */
    protected void writeSourceConstructor(uml.builder.MetaClass metaClass, uml.builder.MetaConstructor cons, java.io.PrintWriter out) throws java.io.IOException {
        out.print((metaClass.getName() + "::") + metaClass.getName());
        out.print(("(" + uml.builder.MetaParameter.toString(cons)) + ")");
        if (cons.hasExceptions())
            out.print((" throw(" + uml.builder.MetaException.toString(cons)) + ")");

        // Write an initializations
        int n = 0;
        for (java.util.Iterator i = cons.getAssociations(), j = cons.getParameters(); i.hasNext();) {
            uml.builder.MetaAttribute attr = ((uml.builder.MetaAttribute) (i.next()));
            uml.builder.MetaParameter param = ((uml.builder.MetaParameter) (j.next()));
            if ((n++) == 0)
                out.print("\n\t: ");
            else
                out.print(", ");

            out.print(attr.getName());
            out.print(("(" + param.getName()) + ")");
        }
        // Write the constructor body
        out.println(" {\n");
        for (java.util.Iterator i = cons.getCompositions(); i.hasNext();) {
            uml.builder.MetaAttribute attr = ((uml.builder.MetaAttribute) (i.next()));
            out.println(((("\t" + attr.getName()) + " = new ") + attr.getType()) + "();");
        }
        out.println("\n}\n");
    }

    /**
     */
    protected void writeSourceDestructor(uml.builder.MetaClass metaClass, java.io.PrintWriter out) throws java.io.IOException {
        out.println(((metaClass.getName() + "::~") + metaClass.getName()) + "{\n");
        for (java.util.Iterator i = new util.FilteredIterator(metaClass.getAttributes(), uml.builder.MetaComposition.class); i.hasNext();) {
            uml.builder.MetaAttribute attr = ((uml.builder.MetaAttribute) (i.next()));
            out.println(("\tdelete " + attr.getName()) + ";");
        }
        out.println("\n}\n");
    }
}