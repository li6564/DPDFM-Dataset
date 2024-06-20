/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * All items that want to visit a summary tree should implement this
 *  interface.
 *
 * @author Chris Seguin
 * @created May 15, 1999
 */
public class TraversalVisitor implements org.acm.seguin.summary.SummaryVisitor {
    /**
     * Visit a summary node. This is the default method.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.Summary node, java.lang.Object data) {
        // Shouldn't have to do anything about one of these nodes
        return data;
    }

    /**
     * Visit all nodes.
     *
     * @param data
     * 		the data that was passed in
     */
    public void visit(java.lang.Object data) {
        java.util.Iterator iter = org.acm.seguin.summary.PackageSummary.getAllPackages();
        if (iter != null) {
            // Create a temporary list to avoid concurrant modification problems
            java.util.LinkedList list = new java.util.LinkedList();
            while (iter.hasNext()) {
                list.add(iter.next());
            } 
            iter = list.iterator();
            while (iter.hasNext()) {
                org.acm.seguin.summary.PackageSummary summary = ((org.acm.seguin.summary.PackageSummary) (iter.next()));
                summary.accept(this, data);
            } 
        }
    }

    /**
     * Visit a package summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.PackageSummary node, java.lang.Object data) {
        java.util.Iterator iter = node.getFileSummaries();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.FileSummary next = ((org.acm.seguin.summary.FileSummary) (iter.next()));
                next.accept(this, data);
            } 
        }
        return data;
    }

    /**
     * Visit a file summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FileSummary node, java.lang.Object data) {
        // Over the imports
        java.util.Iterator iter = node.getImports();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.ImportSummary next = ((org.acm.seguin.summary.ImportSummary) (iter.next()));
                next.accept(this, data);
            } 
        }
        // Over the types
        iter = node.getTypes();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
                next.accept(this, data);
            } 
        }
        // Return some value
        return data;
    }

    /**
     * Visit a import summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.ImportSummary node, java.lang.Object data) {
        // No children so just return
        return data;
    }

    /**
     * Visit a type summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.TypeSummary node, java.lang.Object data) {
        // Over the fields
        java.util.Iterator iter = node.getFields();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.FieldSummary next = ((org.acm.seguin.summary.FieldSummary) (iter.next()));
                next.accept(this, data);
            } 
        }
        // Over the methods
        iter = node.getMethods();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
                next.accept(this, data);
            } 
        }
        // Over the types
        iter = node.getTypes();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
                next.accept(this, data);
            } 
        }
        // Return some value
        return data;
    }

    /**
     * Visit a method summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.MethodSummary node, java.lang.Object data) {
        // First visit the return type
        if (node.getReturnType() != null) {
            node.getReturnType().accept(this, data);
        }
        // Then visit the parameter types
        java.util.Iterator iter = node.getParameters();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.Summary next = ((org.acm.seguin.summary.Summary) (iter.next()));
                next.accept(this, data);
            } 
        }
        // Third visit the exceptions
        iter = node.getExceptions();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.Summary next = ((org.acm.seguin.summary.Summary) (iter.next()));
                next.accept(this, data);
            } 
        }
        // Finally visit the dependencies
        iter = node.getDependencies();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.Summary next = ((org.acm.seguin.summary.Summary) (iter.next()));
                next.accept(this, data);
            } 
        }
        return data;
    }

    /**
     * Visit a field summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FieldSummary node, java.lang.Object data) {
        node.getTypeDecl().accept(this, data);
        return data;
    }

    /**
     * Visit a parameter summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.ParameterSummary node, java.lang.Object data) {
        node.getTypeDecl().accept(this, data);
        return data;
    }

    /**
     * Visit a local variable summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.LocalVariableSummary node, java.lang.Object data) {
        node.getTypeDecl().accept(this, data);
        return data;
    }

    /**
     * Visit a variable summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.VariableSummary node, java.lang.Object data) {
        node.getTypeDecl().accept(this, data);
        return data;
    }

    /**
     * Visit a type declaration summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.TypeDeclSummary node, java.lang.Object data) {
        // This is a leaf.  It does not contain any summary objects
        return data;
    }

    /**
     * Visit a message send summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.MessageSendSummary node, java.lang.Object data) {
        org.acm.seguin.summary.TypeDeclSummary typeDecl = node.getTypeDecl();
        if (typeDecl != null) {
            typeDecl.accept(this, data);
        }
        return data;
    }

    /**
     * Visit a field access summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FieldAccessSummary node, java.lang.Object data) {
        org.acm.seguin.summary.TypeDeclSummary typeDecl = node.getTypeDecl();
        if (typeDecl != null) {
            typeDecl.accept(this, data);
        }
        return data;
    }
}