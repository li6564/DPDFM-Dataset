/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * Holds an invocation of super() or this() in a constructor.
 *
 * @author Chris Seguin
 * @created October 13, 1999
 */
public class ASTExplicitConstructorInvocation extends org.acm.seguin.parser.ast.SimpleNode {
    // Instance Variables
    private java.lang.String name;

    /**
     * Constructor for the ASTExplicitConstructorInvocation object
     *
     * @param id
     * 		Description of Parameter
     */
    public ASTExplicitConstructorInvocation(int id) {
        super(id);
    }

    /**
     * Constructor for the ASTExplicitConstructorInvocation object
     *
     * @param p
     * 		Description of Parameter
     * @param id
     * 		Description of Parameter
     */
    public ASTExplicitConstructorInvocation(org.acm.seguin.parser.JavaParser p, int id) {
        super(p, id);
    }

    /**
     * Set the name we are invoking
     *
     * @param name
     * 		Description of Parameter
     */
    public void setName(java.lang.String name) {
        this.name = name.intern();
    }

    /**
     * Return the name we are invoking
     *
     * @return the name's name
     */
    public java.lang.String getName() {
        return name;
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