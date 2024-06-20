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
 * @class MetaClass
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This is a MetaObject for describing Classes. In also includes some simplistic validation
where possible for things like multiple inheritance, cyclical inheritance and names
 */
public class MetaClass extends uml.builder.MetaComponent {
    private uml.builder.NamingComponent attributeNamingComponent = new uml.builder.AttributeNamingComponent();

    private uml.builder.MetaClass superClass;

    private boolean anInterface;

    private java.util.Vector interfaceList = new java.util.Vector();

    private java.util.Vector attributeList;

    private java.util.Vector methodList;

    /**
     * Create a new MetaClass
     *
     * @param String
     * 		name
     * @param boolean
     * 		interface flag
     */
    public MetaClass(java.lang.String name, boolean anInterface) {
        isInterface(anInterface);
        setName(name);
    }

    /**
     * Set the name of this MetaClass. Performs a simple validaton on the class
     * name to make sure that it is vaguely valid (not null & starts with a letter).
     *
     * @exception SyntaxException
     * 		- throw if the class name is not valid
     */
    public void setName(java.lang.String name) {
        // Check for empty names
        int len = (name == null) ? 0 : name.length();
        if (len < 1)
            throw new uml.builder.SyntaxException(getType() + " has no name!");

        // Simple check for invalid class names
        if (!java.lang.Character.isLetter(name.charAt(0)))
            throw new uml.builder.SyntaxException(getType() + " must start with a letter!");

        super.setName(name);
    }

    /**
     * Get the name of this MetaClass, decorated mainly for user messages
     *
     * @return String proper name for the class
     */
    public java.lang.String getProperName() {
        return ((getType() + " '") + getName()) + "'";
    }

    /**
     * Test weather or not this MetaClass represents an interface.
     *
     * @return boolean
     */
    public boolean isInterface() {
        return anInterface;
    }

    /**
     * Set weather or not this MetaClass represents an interface.
     *
     * @param boolean
     */
    public void isInterface(boolean anInterface) {
        this.anInterface = anInterface;
    }

    /**
     * Get the type of MetaClass
     *
     * @return String "interface" or "class"
     */
    public java.lang.String getType() {
        return isInterface() ? "interface" : "class";
    }

    /**
     * Set the superclass for this MetaClass
     *
     * @param MetaClass
     */
    public void setSuperClass(uml.builder.MetaClass c) {
        setSuperClass(c, false);
    }

    /**
     * Set the superclass for this MetaClass, check for possible multiple inhertence
     * or cyclical inheritance.
     *
     * @param MetaClass
     * 		new super class
     * @param boolean
     * 		should validate
     * @exception SyntaxException
     * 		thrown when inheritance between invalid types is
     * 		attempted
     * @exception SemanticException
     * 		thrown when multiple or cyclical inheritance is
     * 		discovered when validating
     */
    public void setSuperClass(uml.builder.MetaClass c, boolean validate) {
        if ((c == null) || (!getType().equals(c.getType())))
            throw new uml.builder.SyntaxException("invalid super class");

        if (validate) {
            if (superClass != null)
                throw new uml.builder.SemanticException("multiple inheritence detected in " + getProperName());
            else if (c.isAncestorOf(this)) {
                java.lang.String msg = ((("cyclical " + getType()) + " inheritance detected in ") + "'") + getName();
                for (uml.builder.MetaClass meta = c; meta != this; meta = meta.getSuperClass())
                    msg += ", " + meta.getName();

                msg += "'";
                throw new uml.builder.SemanticException(msg);
            }
        }
        superClass = c;
    }

    /**
     * Get the MetaClass representing the super class
     *
     * @return MetaClass
     */
    public uml.builder.MetaClass getSuperClass() {
        return superClass;
    }

    /**
     * Test if this MetaClass is a child of another MetaClass
     *
     * @param MetaClass
     * 		class to test
     * @return boolean
     */
    public boolean isAncestorOf(uml.builder.MetaClass c) {
        for (uml.builder.MetaClass meta = getSuperClass(); meta != null; meta = meta.getSuperClass())
            if (meta.equals(c))
                return true;


        return false;
    }

    /**
     * Add an interface to this MetaClass
     *
     * @param MetaClass
     * 		representing the interface to add
     */
    public void addInterface(uml.builder.MetaClass c) {
        addInterface(c, true);
    }

    /**
     * Add an interface to this MetaClass
     *
     * @param MetaClass
     * 		representing the interface to add
     * @param boolean
     * 		should validate
     * @exception SyntaxException
     * 		thrown when realization between invalid types is
     * 		attempted
     * @exception SemanticException
     * 		thrown when reduntant interfaces are detected
     * @post the MetaClass is always left with smallest set of interfaces that define
    the desired contract, redunacies are removed, even when validated
     */
    public void addInterface(uml.builder.MetaClass c, boolean validate) {
        java.lang.String msg = null;
        if (!c.isInterface())
            throw new uml.builder.SyntaxException((("invalid interface " + c.getProperName()) + " on ") + getProperName());

        // Redundant interface check, phase 1 - immediate realizations
        // of the interface class or a subclass of the interface class
        if (hasInterface(c)) {
            if (validate)
                throw new uml.builder.SemanticException((("redundant interface " + c.getProperName()) + " on ") + getProperName());

            // Redundant interface check, phase 2 - indirect realizations
            // of the interface through a super class of the interface class
            for (java.util.Iterator i = interfaceList.iterator(); i.hasNext();) {
                // If such a match is found on this class, the super interface should be removed
                // and the sub interface put in its place
                uml.builder.MetaClass interfaceClass = ((uml.builder.MetaClass) (i.next()));
                if (c.isAncestorOf(interfaceClass)) {
                    i.remove();
                    msg = (("redundant interface " + interfaceClass.getProperName()) + " on ") + getProperName();
                    break;
                }
            }
        }
        interfaceList.add(c);
        if ((msg != null) && validate)
            throw new uml.builder.SemanticException(msg);

    }

    /**
     * Remove an interface from this MetaClass
     *
     * @param MetaClass
     * 		representing the interface to remove
     */
    public void removeInterface(uml.builder.MetaClass c) {
        interfaceList.remove(c);
    }

    /**
     * Search the interfaces implemented by this MetaClass for an interface that is
     * compatible with he specified interface
     *
     * @param MetaClass
     * @return MetaClass - either the MetaClass specified or a subclass that will work
     */
    public uml.builder.MetaClass getCompatibleInterface(uml.builder.MetaClass metaClass) {
        if (!metaClass.isInterface())
            throw new java.lang.IllegalArgumentException();

        // Check ancestory of interfaces for this class
        for (int i = 0; i < interfaceList.size(); i++) {
            uml.builder.MetaClass interfaceClass = ((uml.builder.MetaClass) (interfaceList.elementAt(i)));
            if (interfaceClass.equals(metaClass) || interfaceClass.isAncestorOf(metaClass))
                return interfaceClass;

        }
        return null;
    }

    /**
     * Test if the MetaClass implements an interface, or a super class.
     *
     * @param MetaClass
     * 		interface to test for
     * @param boolean
     * 		check super classes
     * @return boolean
     */
    public boolean hasInterface(uml.builder.MetaClass metaClass) {
        return hasInterface(metaClass, true);
    }

    public boolean hasInterface(uml.builder.MetaClass metaClass, boolean checkParents) {
        if (getCompatibleInterface(metaClass) != null)
            return true;

        if (!checkParents)
            return false;

        // Check ancestory of interfaces for super classes
        uml.builder.MetaClass superClass = getSuperClass();
        return superClass == null ? false : superClass.hasInterface(metaClass, true);
    }

    /**
     * Get an Iterator over the interfaces for this MetaClass
     *
     * @return Iterator
     */
    public java.util.Iterator getInterfaces() {
        return getInterfaces(true);
    }

    protected java.util.Iterator getInterfaces(boolean readOnly) {
        return new util.WrappedIterator(interfaceList.iterator(), readOnly);
    }

    /**
     * Get a component that can be used to create names for the collection
     * of attributes.
     *
     * @return NamingComponent
     */
    protected uml.builder.NamingComponent getNamingComponent() {
        return attributeNamingComponent;
    }

    /**
     * Add a MetaAttribute to this class
     *
     * @param MetaAttribute
     */
    public void addAttribute(uml.builder.MetaAttribute attr) {
        if (isInterface())
            throw new uml.builder.SemanticException("interface may not have attributes " + getProperName());

        getNamingComponent().nameComponent(attr);
        if (attributeList == null)
            attributeList = new java.util.Vector();

        attributeList.add(attr);
    }

    public java.util.Iterator getAttributes() {
        return getAttributes(true);
    }

    protected java.util.Iterator getAttributes(boolean readOnly) {
        return new util.WrappedIterator(attributeList == null ? null : attributeList.iterator(), readOnly);
    }

    /**
     * Add a MetaMethod to this class
     *
     * @param MetaAttribute
     * @param boolean
     * 		replace existing method
     */
    public void addMethod(uml.builder.MetaMethod m) {
        addMethod(m, false);
    }

    /**
     * Add a MetaMethod to this class
     *
     * @param MetaAttribute
     * @param boolean
     * 		replace existing method
     */
    public void addMethod(uml.builder.MetaMethod m, boolean replace) {
        java.lang.String name = m.getName();
        if (getName().equals(name))
            throw new uml.builder.SemanticException(("invalid method name for this class '" + m) + "'");

        if (name.equals("<init>") && isInterface())
            throw new uml.builder.SemanticException(("interface can not have constructor this class '" + m) + "'");

        if (methodList != null) {
            if (methodList.contains(m)) {
                if (!replace)
                    throw new uml.builder.SemanticException("duplicate method signature");
                else
                    methodList.remove(m);

            }
        }
        if (methodList == null)
            methodList = new java.util.Vector();

        // Insert constructors at the front of the list
        if (name.equals("<init>"))
            methodList.add(0, m);
        else
            methodList.add(m);

    }

    public java.util.Iterator getMethods() {
        return getMethods(true);
    }

    protected java.util.Iterator getMethods(boolean readOnly) {
        return new util.WrappedIterator(methodList == null ? null : methodList.iterator(), readOnly);
    }

    /**
     * Comparison is performed by comparing the class name for this MetaClass
     *
     * @param Object
     * @return int
     */
    public int compareTo(java.lang.Object o) {
        java.lang.String name = getName();
        if (o instanceof uml.builder.MetaClass)
            return name.compareTo(((uml.builder.MetaClass) (o)).getName());

        return name.compareTo(o);
    }

    /**
     * Human readable string represnting the class, its inheritance and its interfaces.
     *
     * @return String
     */
    public java.lang.String toString() {
        java.lang.StringBuffer buf = new java.lang.StringBuffer(getType());
        buf.append(' ').append(getName());
        if (getSuperClass() != null)
            buf.append(" extends ").append(getSuperClass().getName());

        for (int i = 0; i < interfaceList.size(); i++) {
            if (i == 0)
                buf.append(" implements ");

            buf.append(((uml.builder.MetaClass) (interfaceList.elementAt(i))).getName());
            if (i < (interfaceList.size() - 1))
                buf.append(", ");

        }
        return buf.toString();
    }
}