/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * A transform that removes a specific method
 *
 * @author Chris Seguin
 */
public class RemoveMethodTransform extends org.acm.seguin.refactor.TransformAST {
    private org.acm.seguin.parser.ast.SimpleNode methodDecl;

    private org.acm.seguin.summary.MethodSummary method;

    /**
     * Constructor for the RemoveMethodTransform object
     *
     * @param init
     * 		the summary of the method
     */
    public RemoveMethodTransform(org.acm.seguin.summary.MethodSummary init) {
        method = init;
    }

    /**
     * Gets the MethodDeclaration attribute of the RemoveMethodTransform object
     *
     * @return The MethodDeclaration value
     */
    public org.acm.seguin.parser.ast.SimpleNode getMethodDeclaration() {
        return methodDecl;
    }

    /**
     * Updates the root
     *
     * @param root
     * 		the ro
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        org.acm.seguin.refactor.method.RemoveMethodVisitor rfv = new org.acm.seguin.refactor.method.RemoveMethodVisitor(method);
        rfv.visit(root, null);
        methodDecl = rfv.getMethodDeclaration();
    }
}