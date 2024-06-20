/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Add all imports associated with a particular method
 *
 * @author Chris Seguin
 */
public class AddMethodTypeVisitor extends org.acm.seguin.summary.TraversalVisitor {
    private boolean methodContents;

    /**
     * Constructor for the AddMethodTypeVisitor object
     */
    public AddMethodTypeVisitor() {
        methodContents = true;
    }

    /**
     * Constructor for the AddMethodTypeVisitor object
     *
     * @param traverseDependencies
     * 		Description of Parameter
     */
    public AddMethodTypeVisitor(boolean traverseDependencies) {
        methodContents = traverseDependencies;
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
        if (node.isPrimitive()) {
            return data;
        }
        org.acm.seguin.summary.TypeSummary typeSummary = org.acm.seguin.summary.query.GetTypeSummary.query(node);
        if (typeSummary != null) {
            addTransform(data, new org.acm.seguin.refactor.AddImportTransform(typeSummary));
        }
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
        org.acm.seguin.summary.TypeDeclSummary decl = node.getReturnType();
        if (decl != null) {
            decl.accept(this, data);
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
        if (methodContents) {
            iter = node.getDependencies();
            if (iter != null) {
                while (iter.hasNext()) {
                    org.acm.seguin.summary.Summary next = ((org.acm.seguin.summary.Summary) (iter.next()));
                    next.accept(this, data);
                } 
            }
        }
        return data;
    }

    /**
     * This transformation adds import statements. If the data object is a
     *  complex transform, it is added into the complex transform. If the data is
     *  a simple node, then the parse tree has been passed in and the transform
     *  should be applied directly to it.
     *
     * @param data
     * 		the object we are updating
     * @param transform
     * 		the transformation
     */
    private void addTransform(java.lang.Object data, org.acm.seguin.refactor.AddImportTransform transform) {
        if (data instanceof org.acm.seguin.refactor.ComplexTransform) {
            org.acm.seguin.refactor.ComplexTransform complexTransform = ((org.acm.seguin.refactor.ComplexTransform) (data));
            complexTransform.add(transform);
        } else if (data instanceof org.acm.seguin.parser.ast.SimpleNode) {
            org.acm.seguin.parser.ast.SimpleNode root = ((org.acm.seguin.parser.ast.SimpleNode) (data));
            transform.update(root);
        }
    }
}