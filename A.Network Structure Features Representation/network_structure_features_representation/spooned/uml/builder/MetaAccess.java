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
 * @class MetaAccess
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This class helps manipulate access modifiers
 */
public abstract class MetaAccess {
    public static int NONE = 0x0;

    public static int PRIVATE = 0x1;

    public static int PROTECTED = 0x2;

    public static int PUBLIC = 0x4;

    public static int STATIC = 0x8;

    public static int ABSTRACT = 0x10;

    /**
     * Reduce a set of flags to the smallest reasonable representation.
     * Only considers the general access modifiers (public, protected, ...)
     */
    public static int compress(int access) {
        if (uml.builder.MetaAccess.isPublic(access))
            access &= (~uml.builder.MetaAccess.PROTECTED) & (~uml.builder.MetaAccess.PRIVATE);
        else if (uml.builder.MetaAccess.isProtected(access))
            access &= (~uml.builder.MetaAccess.PROTECTED) & (~uml.builder.MetaAccess.PRIVATE);

        return access;
    }

    /**
     * Test two sets of access flags are complimentary.
     *
     * @return boolean
     */
    public static boolean isComplementary(int a, int b) {
        // static && abstract are not valid
        return !(((a == b) || (uml.builder.MetaAccess.isAbstract(a) && uml.builder.MetaAccess.isStatic(b))) || (uml.builder.MetaAccess.isAbstract(b) && uml.builder.MetaAccess.isStatic(a)));
    }

    /**
     * Test two sets of access flags to see a is compatible (can override) with b.
     *
     * @return boolean
     */
    public static boolean isCompatible(int a, int b) {
        if (uml.builder.MetaAccess.isStatic(a) != uml.builder.MetaAccess.isStatic(b))
            return false;

        if (uml.builder.MetaAccess.isAbstract(a) && (!uml.builder.MetaAccess.isAbstract(b)))
            return false;

        // Can not make more private
        if ((uml.builder.MetaAccess.isPublic(b) && (!uml.builder.MetaAccess.isPublic(a))) || (uml.builder.MetaAccess.isProtected(b) && uml.builder.MetaAccess.isPrivate(a)))
            return false;

        return true;
    }

    public static boolean isPublic(int access) {
        return (access & uml.builder.MetaAccess.PUBLIC) != 0;
    }

    public static boolean isProtected(int access) {
        return (access & uml.builder.MetaAccess.PROTECTED) != 0;
    }

    public static boolean isPrivate(int access) {
        return (access & uml.builder.MetaAccess.PRIVATE) != 0;
    }

    public static boolean isAbstract(int access) {
        return (access & uml.builder.MetaAccess.ABSTRACT) != 0;
    }

    public static boolean isStatic(int access) {
        return (access & uml.builder.MetaAccess.STATIC) != 0;
    }

    /**
     * Check a set of flags to be sure they are valid
     */
    public static boolean isValid(int access) {
        if (uml.builder.MetaAccess.isAbstract(access) && uml.builder.MetaAccess.isStatic(access))
            return false;

        return !(((uml.builder.MetaAccess.isPublic(access) && uml.builder.MetaAccess.isProtected(access)) || (uml.builder.MetaAccess.isPrivate(access) && uml.builder.MetaAccess.isProtected(access))) || (uml.builder.MetaAccess.isPublic(access) && uml.builder.MetaAccess.isPrivate(access)));
    }

    /**
     */
    public static int parse(java.lang.String access) {
        int x = uml.builder.MetaAccess.NONE;
        if (access.indexOf("public") >= 0)
            x |= uml.builder.MetaAccess.PUBLIC;

        if (access.indexOf("protected") >= 0)
            x |= uml.builder.MetaAccess.PROTECTED;

        if (access.indexOf("private") >= 0)
            x |= uml.builder.MetaAccess.PRIVATE;

        if (access.indexOf("abstract") >= 0)
            x |= uml.builder.MetaAccess.ABSTRACT;

        if (access.indexOf("static") >= 0)
            x |= uml.builder.MetaAccess.STATIC;

        return x;
    }

    /**
     * Convert a set of access flags into a String
     *
     * @return String
     */
    public static java.lang.String toString(int access) {
        java.lang.StringBuffer buf = null;
        if (uml.builder.MetaAccess.isPrivate(access))
            buf = uml.builder.MetaAccess.append(buf, "private");

        if (uml.builder.MetaAccess.isProtected(access))
            buf = uml.builder.MetaAccess.append(buf, "protected");

        if (uml.builder.MetaAccess.isPublic(access))
            buf = uml.builder.MetaAccess.append(buf, "public");

        if (uml.builder.MetaAccess.isStatic(access))
            buf = uml.builder.MetaAccess.append(buf, "static");

        if (uml.builder.MetaAccess.isAbstract(access))
            buf = uml.builder.MetaAccess.append(buf, "abstract");

        return buf == null ? "" : buf.toString();
    }

    /**
     * Used internally to construct the access String
     */
    private static java.lang.StringBuffer append(java.lang.StringBuffer buf, java.lang.String s) {
        if (buf != null)
            buf.append(' ').append(s);
        else
            buf = new java.lang.StringBuffer(s);

        return buf;
    }
}