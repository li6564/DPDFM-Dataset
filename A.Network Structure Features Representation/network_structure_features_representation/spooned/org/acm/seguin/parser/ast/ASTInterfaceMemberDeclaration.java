/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * Structures that can be contained within the interface's body
 *
 * @author Chris Seguin
 * @created October 13, 1999
 */
public class ASTInterfaceMemberDeclaration extends org.acm.seguin.parser.ast.SimpleNode {
    /**
     * Constructor for the ASTInterfaceMemberDeclaration object
     *
     * @param id
     * 		Description of Parameter
     */
    public ASTInterfaceMemberDeclaration(int id) {
        super(id);
    }

    /**
     * Constructor for the ASTInterfaceMemberDeclaration object
     *
     * @param p
     * 		Description of Parameter
     * @param id
     * 		Description of Parameter
     */
    public ASTInterfaceMemberDeclaration(org.acm.seguin.parser.JavaParser p, int id) {
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