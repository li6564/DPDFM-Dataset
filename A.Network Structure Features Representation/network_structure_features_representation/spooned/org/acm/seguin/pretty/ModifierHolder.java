/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty;
/**
 * Holds a description of the modifiers for a field or a class
 *
 * @author Chris Seguin
 */
public class ModifierHolder implements java.io.Serializable {
    // Instance Variables
    private int modifiers = 0;

    // Class Variables
    /**
     * Description of the Field
     */
    protected static int ABSTRACT = 0x1;

    /**
     * Description of the Field
     */
    protected static int EXPLICIT = 0x2;

    /**
     * Description of the Field
     */
    protected static int FINAL = 0x4;

    /**
     * Description of the Field
     */
    protected static int INTERFACE = 0x8;

    /**
     * Description of the Field
     */
    protected static int NATIVE = 0x10;

    /**
     * Description of the Field
     */
    protected static int PRIVATE = 0x20;

    /**
     * Description of the Field
     */
    protected static int PROTECTED = 0x40;

    /**
     * Description of the Field
     */
    protected static int PUBLIC = 0x80;

    /**
     * Description of the Field
     */
    protected static int STATIC = 0x100;

    /**
     * Description of the Field
     */
    protected static int STRICT = 0x200;

    /**
     * Description of the Field
     */
    protected static int SYNCHRONIZED = 0x400;

    /**
     * Description of the Field
     */
    protected static int TRANSIENT = 0x800;

    /**
     * Description of the Field
     */
    protected static int VOLATILE = 0x1000;

    /**
     * Description of the Field
     */
    protected static int STRICTFP = 0x2000;

    /**
     * Description of the Field
     */
    protected static java.lang.String[] names = new java.lang.String[]{ "abstract", "explicit", "final", "interface", "native", "private", "protected", "public", "static", "strict", "strictfp", "synchronized", "transient", "volatile" };

    /**
     * Sets the private bit in the modifiers
     *
     * @param value
     * 		true if we are setting the private modifier
     */
    public void setPrivate(boolean value) {
        setCode(value, org.acm.seguin.pretty.ModifierHolder.PRIVATE);
    }

    /**
     * Sets the protected bit in the modifiers
     *
     * @param value
     * 		true if we are setting the protected modifier
     */
    public void setProtected(boolean value) {
        setCode(value, org.acm.seguin.pretty.ModifierHolder.PROTECTED);
    }

    /**
     * Sets the public bit in the modifiers
     *
     * @param value
     * 		true if we are setting the public modifier
     */
    public void setPublic(boolean value) {
        setCode(value, org.acm.seguin.pretty.ModifierHolder.PUBLIC);
    }

    /**
     * Sets the abstract bit in the modifiers
     *
     * @param value
     * 		true if we are setting the modifier
     */
    public void setAbstract(boolean value) {
        setCode(value, org.acm.seguin.pretty.ModifierHolder.ABSTRACT);
    }

    /**
     * Sets the Synchronized attribute of the ModifierHolder object
     *
     * @param value
     * 		The new Synchronized value
     */
    public void setSynchronized(boolean value) {
        setCode(value, org.acm.seguin.pretty.ModifierHolder.SYNCHRONIZED);
    }

    /**
     * Sets the Static attribute of the ModifierHolder object
     *
     * @param value
     * 		The new Static value
     */
    public void setStatic(boolean value) {
        setCode(value, org.acm.seguin.pretty.ModifierHolder.STATIC);
    }

    /**
     * Determine if the object is abstract
     *
     * @return true if this stores an ABSTRACT flag
     */
    public boolean isAbstract() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.ABSTRACT) != 0;
    }

    /**
     * Determine if the object is explicit
     *
     * @return true if this stores an EXPLICIT flag
     */
    public boolean isExplicit() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.EXPLICIT) != 0;
    }

    /**
     * Determine if the object is final
     *
     * @return true if this stores an FINAL flag
     */
    public boolean isFinal() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.FINAL) != 0;
    }

    /**
     * Determine if the object is interface
     *
     * @return true if this stores an INTERFACE flag
     */
    public boolean isInterface() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.INTERFACE) != 0;
    }

    /**
     * Determine if the object is native
     *
     * @return true if this stores an NATIVE flag
     */
    public boolean isNative() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.NATIVE) != 0;
    }

    /**
     * Determine if the object is private
     *
     * @return true if this stores an PRIVATE flag
     */
    public boolean isPrivate() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.PRIVATE) != 0;
    }

    /**
     * Determine if the object is protected
     *
     * @return true if this stores an PROTECTED flag
     */
    public boolean isProtected() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.PROTECTED) != 0;
    }

    /**
     * Determine if the object is public
     *
     * @return true if this stores an PUBLIC flag
     */
    public boolean isPublic() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.PUBLIC) != 0;
    }

    /**
     * Determine if the object is static
     *
     * @return true if this stores an static flag
     */
    public boolean isStatic() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.STATIC) != 0;
    }

    /**
     * Determine if the object is strict
     *
     * @return true if this stores an STRICT flag
     */
    public boolean isStrict() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.STRICT) != 0;
    }

    /**
     * Determine if the object is strictFP
     *
     * @return true if this stores an STRICTFP flag
     */
    public boolean isStrictFP() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.STRICTFP) != 0;
    }

    /**
     * Determine if the object is synchronized
     *
     * @return true if this stores an SYNCHRONIZED flag
     */
    public boolean isSynchronized() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.SYNCHRONIZED) != 0;
    }

    /**
     * Determine if the object is transient
     *
     * @return true if this stores an TRANSIENT flag
     */
    public boolean isTransient() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.TRANSIENT) != 0;
    }

    /**
     * Determine if the object is volatile
     *
     * @return true if this stores an VOLATILE flag
     */
    public boolean isVolatile() {
        return (modifiers & org.acm.seguin.pretty.ModifierHolder.VOLATILE) != 0;
    }

    /**
     * Determines if this has package scope
     *
     * @return true if this has package scope
     */
    public boolean isPackage() {
        return ((!isPublic()) && (!isProtected())) && (!isPrivate());
    }

    /**
     * Add a modifier
     *
     * @param mod
     * 		the new modifier
     */
    public void add(java.lang.String mod) {
        if ((mod == null) || (mod.length() == 0)) {
            // Nothing to add
            return;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[0])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.ABSTRACT;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[1])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.EXPLICIT;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[2])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.FINAL;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[3])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.INTERFACE;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[4])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.NATIVE;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[5])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.PRIVATE;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[6])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.PROTECTED;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[7])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.PUBLIC;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[8])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.STATIC;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[9])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.STRICT;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[10])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.STRICTFP;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[11])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.SYNCHRONIZED;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[12])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.TRANSIENT;
        } else if (mod.equalsIgnoreCase(org.acm.seguin.pretty.ModifierHolder.names[13])) {
            modifiers = modifiers | org.acm.seguin.pretty.ModifierHolder.VOLATILE;
        }
    }

    /**
     * Convert the object to a string
     *
     * @return a string describing the modifiers
     */
    public java.lang.String toString() {
        // Local Variables
        java.lang.StringBuffer buf = new java.lang.StringBuffer();
        // Protection first
        if (isPrivate()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[5]);
            buf.append(" ");
        }
        if (isProtected()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[6]);
            buf.append(" ");
        }
        if (isPublic()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[7]);
            buf.append(" ");
        }
        // Others next
        if (isAbstract()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[0]);
            buf.append(" ");
        }
        if (isExplicit()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[1]);
            buf.append(" ");
        }
        if (isFinal()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[2]);
            buf.append(" ");
        }
        if (isInterface()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[3]);
            buf.append(" ");
        }
        if (isNative()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[4]);
            buf.append(" ");
        }
        if (isStatic()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[8]);
            buf.append(" ");
        }
        if (isStrict()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[9]);
            buf.append(" ");
        }
        if (isStrictFP()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[10]);
            buf.append(" ");
        }
        if (isSynchronized()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[11]);
            buf.append(" ");
        }
        if (isTransient()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[12]);
            buf.append(" ");
        }
        if (isVolatile()) {
            buf.append(org.acm.seguin.pretty.ModifierHolder.names[13]);
            buf.append(" ");
        }
        return buf.toString();
    }

    /**
     * Copies the modifiers from another source
     *
     * @param source
     * 		the source
     */
    public void copy(org.acm.seguin.pretty.ModifierHolder source) {
        modifiers = source.modifiers;
    }

    /**
     * Compare two of these objects and get it right
     *
     * @param obj
     * 		the object
     * @return true if they are the same
     */
    public boolean equals(java.lang.Object obj) {
        if (obj instanceof org.acm.seguin.pretty.ModifierHolder) {
            org.acm.seguin.pretty.ModifierHolder other = ((org.acm.seguin.pretty.ModifierHolder) (obj));
            return other.modifiers == modifiers;
        }
        return false;
    }

    /**
     * You need to overload the hashcode when you overload the equals operator
     *
     * @return a hashcode for this object
     */
    public int hashCode() {
        return modifiers;
    }

    /**
     * Sets or resets a single bit in the modifiers
     *
     * @param value
     * 		true if we are setting the bit
     * @param code
     * 		The new Code value
     */
    protected void setCode(boolean value, int code) {
        if (value) {
            modifiers = modifiers | code;
        } else {
            modifiers = modifiers & (~code);
        }
    }
}