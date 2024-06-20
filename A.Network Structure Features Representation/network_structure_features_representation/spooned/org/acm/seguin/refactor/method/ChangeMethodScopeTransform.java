/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Changes the scope of the method
 *
 * @author Chris Seguin
 */
class ChangeMethodScopeTransform extends org.acm.seguin.refactor.TransformAST {
    private int scope;

    private org.acm.seguin.summary.MethodSummary methodSummary;

    /**
     * Constructor for the ChangeMethodScopeTransform object
     *
     * @param init
     * 		Description of Parameter
     * @param changeTo
     * 		Description of Parameter
     */
    ChangeMethodScopeTransform(org.acm.seguin.summary.MethodSummary init, int changeTo) {
        methodSummary = init;
        scope = changeTo;
    }

    /**
     * Updates the AST
     *
     * @param root
     * 		the root of the AST
     */
    public void update(org.acm.seguin.parser.ast.SimpleNode root) {
        root.jjtAccept(new org.acm.seguin.refactor.method.ChangeMethodScopeVisitor(methodSummary, scope), null);
    }
}