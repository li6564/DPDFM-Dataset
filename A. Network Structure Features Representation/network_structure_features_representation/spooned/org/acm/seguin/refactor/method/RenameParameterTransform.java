/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Transformation responsible for renaming the parameter
 *
 * @author Chris Seguin
 */
class RenameParameterTransform extends org.acm.seguin.refactor.TransformAST {
    private java.lang.String newName;

    private org.acm.seguin.summary.ParameterSummary param;

    private org.acm.seguin.summary.MethodSummary method;

    private boolean rightTree;

    /**
     * Constructor for the RenameParameterTransform object
     */
    public RenameParameterTransform() {
        newName = null;
        param = null;
        method = null;
    }

    /**
     * Sets the NewName attribute of the RenameParameterTransform object
     *
     * @param value
     * 		The new NewName value
     */
    public void setNewName(java.lang.String value) {
        newName = value;
    }

    /**
     * Sets the ParameterSummary attribute of the RenameParameterTransform
     *  object
     *
     * @param value
     * 		The new ParameterSummary value
     */
    public void setParameter(org.acm.seguin.summary.ParameterSummary value) {
        param = value;
    }

    /**
     * Sets the MethodSummary attribute of the RenameParameterTransform object
     *
     * @param value
     * 		The new MethodSummary value
     */
    public void setMethod(org.acm.seguin.summary.MethodSummary value) {
        method = value;
    }

    /**
     * Sets the RightTree attribute of the RenameParameterTransform object
     *
     * @param value
     * 		The new RightTree value
     */
    public void setRightTree(boolean value) {
        rightTree = value;
    }

    /**
     * Gets the Method attribute of the RenameParameterTransform object
     *
     * @return The Method value
     */
    public org.acm.seguin.summary.MethodSummary getMethod() {
        return method;
    }

    /**
     * Gets the Parameter attribute of the RenameParameterTransform object
     *
     * @return The Parameter value
     */
    public org.acm.seguin.summary.ParameterSummary getParameter() {
        return param;
    }

    /**
     * Gets the NewName attribute of the RenameParameterTransform object
     *
     * @return The NewName value
     */
    public java.lang.String getNewName() {
        return newName;
    }

    /**
     * Gets the RightTree attribute of the RenameParameterTransform object
     *
     * @return The RightTree value
     */
    public boolean isRightTree() {
        return rightTree;
    }

    /**
     * Updates the name of the parameter
     *
     * @param root
     * 		the tree to update
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        rightTree = false;
        root.jjtAccept(new org.acm.seguin.refactor.method.RenameParameterVisitor(method), this);
    }
}