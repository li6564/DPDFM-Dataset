/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * Holds the package declaration at the beginning of the java file
 *
 * @author Chris Seguin
 * @created October 13, 1999
 */
public class ASTPackageDeclaration extends org.acm.seguin.parser.ast.SimpleNode implements org.acm.seguin.pretty.JavaDocable {
    // Instance Variables
    org.acm.seguin.pretty.JavaDocableImpl jdi;

    /**
     * Constructor for the ASTPackageDeclaration object
     *
     * @param id
     * 		Description of Parameter
     */
    public ASTPackageDeclaration(int id) {
        super(id);
        jdi = new org.acm.seguin.pretty.JavaDocableImpl();
    }

    /**
     * Constructor for the ASTPackageDeclaration object
     *
     * @param p
     * 		Description of Parameter
     * @param id
     * 		Description of Parameter
     */
    public ASTPackageDeclaration(org.acm.seguin.parser.JavaParser p, int id) {
        super(p, id);
        jdi = new org.acm.seguin.pretty.JavaDocableImpl();
    }

    /**
     * Checks to see if it was printed
     *
     * @return true if it still needs to be printed
     */
    public boolean isRequired() {
        return false;
    }

    /**
     * Allows you to add a java doc component
     *
     * @param component
     * 		the component that can be added
     */
    public void addJavaDocComponent(org.acm.seguin.pretty.JavaDocComponent component) {
        jdi.addJavaDocComponent(component);
    }

    /**
     * Prints all the java doc components
     *
     * @param printData
     * 		the print data
     */
    public void printJavaDocComponents(org.acm.seguin.pretty.PrintData printData) {
        jdi.printJavaDocComponents(printData, "since");
    }

    /**
     * Makes sure all the java doc components are present
     */
    public void finish() {
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