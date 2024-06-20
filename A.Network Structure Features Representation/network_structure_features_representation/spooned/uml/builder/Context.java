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
import diagram.DiagramModel;
/**
 *
 * @class Context
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

A Context contains a set of information about the MetaObjects created
from a DiagramModel. A single Context object can be passed to many different
CodeBuilders to incrementially perform different work in interpretting and
organizing the contents of a DiagramModel as meta data.
 */
public class Context {
    private static final uml.builder.Context.MetaClassComparator comparator = new uml.builder.Context.MetaClassComparator();

    private diagram.DiagramModel model;

    private java.util.Vector warnings = new java.util.Vector();

    private java.util.Vector errors = new java.util.Vector();

    private java.util.Vector classes = new java.util.Vector();

    private boolean wantArrays = false;

    /**
     * Create a new Context
     *
     * @param DiagramModel
     */
    public Context(diagram.DiagramModel model) {
        this.model = model;
    }

    /**
     * Get the DiagramModel this Context has been created for.
     *
     * @return DiagramModel
     */
    public diagram.DiagramModel getModel() {
        return model;
    }

    /**
     * Add a class to this context. Checks for duplicates.
     *
     * @param MetaClass
     */
    public void addClass(uml.builder.MetaClass c) {
        if (classes.contains(c))
            throw new uml.builder.SemanticException(("Duplicate for '" + c.getProperName()) + "'");

        classes.add(c);
        // MetaClasses are naturally sorted by name
        java.util.Collections.sort(classes);
    }

    /**
     * Get the MetaClass for the given name
     *
     * @param class
     * 		name
     * @return MetaClass or null
     */
    public uml.builder.MetaClass getMetaClass(java.lang.String name) {
        int index = java.util.Collections.binarySearch(classes, name, uml.builder.Context.comparator);
        if (index < 0)
            return null;

        return ((uml.builder.MetaClass) (classes.get(index)));
    }

    /**
     * Add a warning to the builder Context.
     *
     * @param Warning
     */
    public void addWarning(java.lang.String warning) {
        warnings.add(warning);
    }

    /**
     * Add an error to the builder Context.
     *
     * @param Error
     */
    public void addError(java.lang.String error) {
        errors.add(error);
    }

    public void enableArrays(boolean flag) {
        wantArrays = flag;
    }

    public boolean isArraysEnabled() {
        return wantArrays;
    }

    /**
     * Test the Context for warnings.
     *
     * @return boolean
     */
    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }

    /**
     * Test the Context for errors.
     *
     * @return boolean
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Get an iterator over all MetaClasses previously mapped into this Context
     *
     * @return Iterator
     */
    public java.util.Iterator getClasses() {
        return new util.FilteredIterator(classes.iterator(), new uml.builder.Context.TypeFilter("class"));
    }

    /**
     * Get an iterator over all MetaInterfaces previously mapped into this Context
     *
     * @return Iterator
     */
    public java.util.Iterator getInterfaces() {
        return new util.FilteredIterator(classes.iterator(), new uml.builder.Context.TypeFilter("interface"));
    }

    /**
     * Get an iterator over all warnings placed into this Context
     *
     * @return Iterator
     */
    public java.util.Iterator getWarnings() {
        return warnings.iterator();
    }

    /**
     * Get an iterator over all errors placed into this Context
     *
     * @return Iterator
     */
    public java.util.Iterator getErrors() {
        return errors.iterator();
    }

    /**
     * Test for an item in this Context
     */
    public boolean contains(java.lang.Object o) {
        return classes.contains(o);
    }

    /**
     *
     * @class TypeFilter

    Implement a simple filter for iterating over various types of MetaClasses
     */
    protected static class TypeFilter implements java.lang.Comparable {
        private java.lang.String type;

        /**
         * Create a new TypeFilter
         */
        public TypeFilter(java.lang.String type) {
            if (type == null)
                throw new java.lang.RuntimeException("Invalid type");

            this.type = type;
        }

        /**
         * Compare MetaClass by type to the type string
         */
        public int compareTo(java.lang.Object o) {
            if (o instanceof uml.builder.MetaClass) {
                o = ((uml.builder.MetaClass) (o)).getType();
                return type.compareTo(o);
            }
            return -1;
        }
    }

    /**
     *
     * @class MetaClassComparator

    Implement a comparator that will match String to MetaClasses by class name
     */
    protected static class MetaClassComparator implements java.util.Comparator {
        public int compare(java.lang.Object o1, java.lang.Object o2) {
            // Unwrap the name
            if (o1 instanceof uml.builder.MetaClass)
                o1 = ((uml.builder.MetaClass) (o1)).getName();

            if (o2 instanceof uml.builder.MetaClass)
                o2 = ((uml.builder.MetaClass) (o2)).getName();

            // Do the comparison by name
            if (o1 instanceof java.lang.String)
                return ((java.lang.String) (o1)).compareTo(o2);

            if (o2 instanceof java.lang.String)
                return ((java.lang.String) (o2)).compareTo(o1);

            return -1;
        }
    }
}