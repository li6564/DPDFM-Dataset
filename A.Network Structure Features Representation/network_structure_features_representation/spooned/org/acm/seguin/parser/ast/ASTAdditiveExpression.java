/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * Stores an additive expression (+ or -) in an expression.
 *  Created by the parser.
 *
 * @author Chris Seguin
 * @created October 13, 1999
 */
public class ASTAdditiveExpression extends org.acm.seguin.parser.ast.SimpleNode {
    // Instance Variables
    private java.util.Vector names;

    /**
     * Constructor for the ASTAdditiveExpression object
     *
     * @param id
     * 		Description of Parameter
     */
    public ASTAdditiveExpression(int id) {
        super(id);
        names = new java.util.Vector();
    }

    /**
     * Constructor for the ASTAdditiveExpression object
     *
     * @param p
     * 		Description of Parameter
     * @param id
     * 		Description of Parameter
     */
    public ASTAdditiveExpression(org.acm.seguin.parser.JavaParser p, int id) {
        super(p, id);
        names = new java.util.Vector();
    }

    /**
     * Get the object's names
     *
     * @return the names in an enumeration
     */
    public java.util.Enumeration getNames() {
        return names.elements();
    }

    /**
     * Set the object's name
     *
     * @param newName
     * 		the new name
     */
    public void addName(java.lang.String newName) {
        if (newName != null) {
            names.addElement(newName.intern());
        }
    }

    /**
     * Convert this object to a string
     *
     * @return a string representing this object
     */
    public java.lang.String toString() {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer(super.toString());
        buffer.append(" [");
        Enumeration = getNames();
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