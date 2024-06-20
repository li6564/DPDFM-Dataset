/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * Contains the body of the interface.  The body consists
 *  of a number of field or method declarations.
 *
 * @author Chris Seguin
 * @created October 13, 1999
 */
public class ASTInterfaceBody extends org.acm.seguin.parser.ast.SimpleNode {
    /**
     * Constructor for the ASTInterfaceBody object
     *
     * @param id
     * 		Description of Parameter
     */
    public ASTInterfaceBody(int id) {
        super(id);
    }

    /**
     * Constructor for the ASTInterfaceBody object
     *
     * @param p
     * 		Description of Parameter
     * @param id
     * 		Description of Parameter
     */
    public ASTInterfaceBody(org.acm.seguin.parser.JavaParser p, int id) {
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