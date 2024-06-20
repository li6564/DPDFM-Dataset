/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * Holds the array initializer
 *
 * @author Chris Seguin
 * @created October 13, 1999
 */
public class ASTArrayInitializer extends org.acm.seguin.parser.ast.SimpleNode {
    // Instance Variables
    private boolean finalComma = false;

    /**
     * Constructor for the ASTArrayInitializer object
     *
     * @param id
     * 		Description of Parameter
     */
    public ASTArrayInitializer(int id) {
        super(id);
    }

    /**
     * Constructor for the ASTArrayInitializer object
     *
     * @param p
     * 		Description of Parameter
     * @param id
     * 		Description of Parameter
     */
    public ASTArrayInitializer(org.acm.seguin.parser.JavaParser p, int id) {
        super(p, id);
    }

    /**
     * Set the final comma
     *
     * @param way
     * 		true if there is a final comma
     */
    public void setFinalComma(boolean way) {
        finalComma = way;
    }

    /**
     * Return true if the construct included a final comma
     *
     * @return true if there was a final comma
     */
    public boolean isFinalComma() {
        return finalComma;
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