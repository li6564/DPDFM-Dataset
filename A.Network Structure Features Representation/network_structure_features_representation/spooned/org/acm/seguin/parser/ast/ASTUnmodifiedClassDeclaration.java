/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * Contains the class declaration without any modifiers
 *
 * @author Chris Seguin
 * @created October 13, 1999
 */
public class ASTUnmodifiedClassDeclaration extends org.acm.seguin.parser.ast.SimpleNode {
    // Instance Variables
    private java.lang.String name;

    /**
     * Constructor for the ASTUnmodifiedClassDeclaration object
     *
     * @param id
     * 		Description of Parameter
     */
    public ASTUnmodifiedClassDeclaration(int id) {
        super(id);
    }

    /**
     * Constructor for the ASTUnmodifiedClassDeclaration object
     *
     * @param p
     * 		Description of Parameter
     * @param id
     * 		Description of Parameter
     */
    public ASTUnmodifiedClassDeclaration(org.acm.seguin.parser.JavaParser p, int id) {
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
     * Get the object's name
     *
     * @return the name
     */
    public java.lang.String getName() {
        return name;
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