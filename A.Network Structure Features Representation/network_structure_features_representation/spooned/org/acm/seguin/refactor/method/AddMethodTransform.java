/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Adds a method declaration to a AST
 *
 * @author Chris Seguin
 */
public class AddMethodTransform extends org.acm.seguin.refactor.TransformAST {
    private org.acm.seguin.parser.ast.SimpleNode methodDecl;

    /**
     * Constructor for the AddMethodTransform object
     *
     * @param init
     * 		the method declaration to add
     */
    public AddMethodTransform(org.acm.seguin.parser.ast.SimpleNode init) {
        methodDecl = init;
    }

    /**
     * Updates the AST
     *
     * @param root
     * 		the root of the AST
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        // Apply each individual transformation
        org.acm.seguin.refactor.method.AddMethodVisitor afv = new org.acm.seguin.refactor.method.AddMethodVisitor(methodDecl);
        afv.visit(root, null);
    }
}