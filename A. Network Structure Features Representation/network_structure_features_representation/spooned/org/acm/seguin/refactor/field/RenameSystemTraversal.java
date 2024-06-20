/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.field;
/**
 * All items that want to visit a summary tree should implement this
 *  interface.
 *
 * @author Chris Seguin
 * @created May 15, 1999
 */
public class RenameSystemTraversal extends org.acm.seguin.summary.TraversalVisitor {
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
        if (node.getFile() == null) {
            return data;
        }
        // Over the types
        java.lang.Boolean result = java.lang.Boolean.FALSE;
        java.util.Iterator iter = node.getTypes();
        if (iter != null) {
            while (iter.hasNext() && result.equals(java.lang.Boolean.FALSE)) {
                org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
                result = ((java.lang.Boolean) (next.accept(this, data)));
            } 
        }
        if (result.booleanValue()) {
            // Apply the refactoring to this file
            java.lang.System.out.println("Updating:  " + node.getFile().getPath());
            org.acm.seguin.refactor.field.RenameFieldData rfd = ((org.acm.seguin.refactor.field.RenameFieldData) (data));
            transform(node, rfd.getOldField(), rfd.getNewName(), rfd.getComplexTransform());
        }
        // Return some value
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
        org.acm.seguin.refactor.field.RenameFieldData rfd = ((org.acm.seguin.refactor.field.RenameFieldData) (data));
        if (node.equals(rfd.getTypeSummary())) {
            return java.lang.Boolean.FALSE;
        }
        // Over the fields
        java.util.Iterator iter = node.getMethods();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
                java.lang.Boolean result = ((java.lang.Boolean) (next.accept(this, data)));
                if (result.equals(java.lang.Boolean.TRUE)) {
                    return result;
                }
            } 
        }
        // Over the types
        iter = node.getTypes();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
                java.lang.Boolean result = ((java.lang.Boolean) (next.accept(this, data)));
                if (result.equals(java.lang.Boolean.TRUE)) {
                    return result;
                }
            } 
        }
        // Return some value
        return java.lang.Boolean.FALSE;
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
        // Finally visit the dependencies
        java.util.Iterator iter = node.getDependencies();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.Summary next = ((org.acm.seguin.summary.Summary) (iter.next()));
                java.lang.Boolean result = ((java.lang.Boolean) (next.accept(this, data)));
                if (result.equals(java.lang.Boolean.TRUE)) {
                    return result;
                }
            } 
        }
        return java.lang.Boolean.FALSE;
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
        return java.lang.Boolean.FALSE;
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
        return java.lang.Boolean.FALSE;
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
        return java.lang.Boolean.FALSE;
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
        return java.lang.Boolean.FALSE;
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
        return java.lang.Boolean.FALSE;
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
        java.lang.String message = node.toString();
        org.acm.seguin.refactor.field.RenameFieldData rfd = ((org.acm.seguin.refactor.field.RenameFieldData) (data));
        java.util.StringTokenizer tok = new java.util.StringTokenizer(message, ".");
        while (tok.hasMoreTokens()) {
            java.lang.String next = tok.nextToken();
            if (next.equals(rfd.getOldName())) {
                return java.lang.Boolean.TRUE;
            }
        } 
        return java.lang.Boolean.FALSE;
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
        java.lang.String message = node.getName();
        org.acm.seguin.refactor.field.RenameFieldData rfd = ((org.acm.seguin.refactor.field.RenameFieldData) (data));
        java.util.StringTokenizer tok = new java.util.StringTokenizer(message, ".");
        while (tok.hasMoreTokens()) {
            java.lang.String next = tok.nextToken();
            if (next.equals(rfd.getOldName())) {
                return java.lang.Boolean.TRUE;
            }
        } 
        return java.lang.Boolean.FALSE;
    }

    /**
     * Perform the rename transformation on this particular file
     *
     * @param fileSummary
     * 		Description of Parameter
     * @param oldField
     * 		Description of Parameter
     * @param newName
     * 		Description of Parameter
     * @param transform
     * 		Description of Parameter
     */
    private void transform(org.acm.seguin.summary.FileSummary fileSummary, org.acm.seguin.summary.FieldSummary oldField, java.lang.String newName, org.acm.seguin.refactor.ComplexTransform transform) {
        transform.clear();
        org.acm.seguin.refactor.field.RenameFieldTransform rft = new org.acm.seguin.refactor.field.RenameFieldTransform(oldField, newName);
        transform.add(rft);
        transform.apply(fileSummary.getFile(), fileSummary.getFile());
    }
}