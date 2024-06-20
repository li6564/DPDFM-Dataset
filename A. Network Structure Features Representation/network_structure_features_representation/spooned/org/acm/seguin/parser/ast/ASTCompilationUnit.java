/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * Top level unit for compilation which is able to sort
 *  the import statements.
 *
 * @author Chris Seguin
 * @created October 13, 1999
 */
public class ASTCompilationUnit extends org.acm.seguin.parser.ast.SimpleNode {
    /**
     * Constructor for the ASTCompilationUnit object
     *
     * @param id
     * 		Description of Parameter
     */
    public ASTCompilationUnit(int id) {
        super(id);
    }

    /**
     * Constructor for the ASTCompilationUnit object
     *
     * @param p
     * 		Description of Parameter
     * @param id
     * 		Description of Parameter
     */
    public ASTCompilationUnit(org.acm.seguin.parser.JavaParser p, int id) {
        super(p, id);
    }

    /**
     * Sorts the arrays
     *
     * @param order
     * 		the order of items
     */
    public void sort(org.acm.seguin.util.Comparator order) {
        if (children != null) {
            new org.acm.seguin.util.InsertionSortArray().sort(children, order);
        }
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