/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * Statement that uses the synchronized keyword to perform mutual exclusion
 *
 * @author Chris Seguin
 * @created October 13, 1999
 */
public class ASTSynchronizedStatement extends org.acm.seguin.parser.ast.SimpleNode {
    /**
     * Constructor for the ASTSynchronizedStatement object
     *
     * @param id
     * 		Description of Parameter
     */
    public ASTSynchronizedStatement(int id) {
        super(id);
    }

    /**
     * Constructor for the ASTSynchronizedStatement object
     *
     * @param p
     * 		Description of Parameter
     * @param id
     * 		Description of Parameter
     */
    public ASTSynchronizedStatement(org.acm.seguin.parser.JavaParser p, int id) {
        super(p, id);
    }

    /**
     * Accept the visitor.
     *
     * @param visitor
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object jjtAccept(org.acm.seguin.parser.JavaParserVisitor visitor, java.lang.Object data) {
        return visitor.visit(this, data);
    }
}