/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * Holds the argument list that contains the specific arguments
 *  of a method invocation.
 *
 * @author Chris Seguin
 * @created October 13, 1999
 */
public class ASTArguments extends org.acm.seguin.parser.ast.SimpleNode {
    /**
     * Constructor for the ASTArguments object
     *
     * @param id
     * 		Description of Parameter
     */
    public ASTArguments(int id) {
        super(id);
    }

    /**
     * Constructor for the ASTArguments object
     *
     * @param p
     * 		Description of Parameter
     * @param id
     * 		Description of Parameter
     */
    public ASTArguments(org.acm.seguin.parser.JavaParser p, int id) {
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