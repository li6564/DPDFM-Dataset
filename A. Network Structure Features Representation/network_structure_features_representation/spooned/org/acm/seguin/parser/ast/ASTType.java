/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * Declares a type
 *
 * @author Chris Seguin
 * @created October 13, 1999
 */
public class ASTType extends org.acm.seguin.parser.ast.SimpleNode {
    // Instance Variable
    private int arrayCount;

    /**
     * Constructor for the ASTType object
     *
     * @param id
     * 		Description of Parameter
     */
    public ASTType(int id) {
        super(id);
    }

    /**
     * Constructor for the ASTType object
     *
     * @param p
     * 		Description of Parameter
     * @param id
     * 		Description of Parameter
     */
    public ASTType(org.acm.seguin.parser.JavaParser p, int id) {
        super(p, id);
    }

    /**
     * Set the number of indirection for the array
     *
     * @param count
     * 		the count
     */
    public void setArrayCount(int count) {
        arrayCount = count;
    }

    /**
     * Get the number of indirection for the array
     *
     * @return the count
     */
    public int getArrayCount() {
        return arrayCount;
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