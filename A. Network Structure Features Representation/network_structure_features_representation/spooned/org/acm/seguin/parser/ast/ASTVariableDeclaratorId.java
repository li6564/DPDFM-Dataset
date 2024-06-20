/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * Declares a variable.  Contains the name of the variable and
 *  the number of [] afterwards.
 *
 * @author Chris Seguin
 * @created October 13, 1999
 */
public class ASTVariableDeclaratorId extends org.acm.seguin.parser.ast.SimpleNode {
    // Instance Variables
    private java.lang.String name;

    private int arrayCount = 0;

    /**
     * Constructor for the ASTVariableDeclaratorId object
     *
     * @param id
     * 		Description of Parameter
     */
    public ASTVariableDeclaratorId(int id) {
        super(id);
    }

    /**
     * Constructor for the ASTVariableDeclaratorId object
     *
     * @param p
     * 		Description of Parameter
     * @param id
     * 		Description of Parameter
     */
    public ASTVariableDeclaratorId(org.acm.seguin.parser.JavaParser p, int id) {
        super(p, id);
    }

    /**
     * Set the object's name
     *
     * @param newName
     * 		the new name
     */
    public void setName(java.lang.String newName) {
        name = newName.intern();
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
     * Get the object's name
     *
     * @return the name
     */
    public java.lang.String getName() {
        return name;
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
     * Convert this object to a string
     *
     * @return a string representing this object
     */
    public java.lang.String toString() {
        return ((super.toString() + " [") + getName()) + "]";
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