/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * Holds the array dimensions and the initialization for the array.
 *
 * @author Chris Seguin
 * @created October 13, 1999
 */
public class ASTArrayDimsAndInits extends org.acm.seguin.parser.ast.SimpleNode {
    // Instance Variables
    private int arrayCount = 0;

    /**
     * Constructor for the ASTArrayDimsAndInits object
     *
     * @param id
     * 		Description of Parameter
     */
    public ASTArrayDimsAndInits(int id) {
        super(id);
    }

    /**
     * Constructor for the ASTArrayDimsAndInits object
     *
     * @param p
     * 		Description of Parameter
     * @param id
     * 		Description of Parameter
     */
    public ASTArrayDimsAndInits(org.acm.seguin.parser.JavaParser p, int id) {
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