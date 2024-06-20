/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty.sort;
/**
 * Orders the items in a class according to type.
 *
 * @author Chris Seguin
 * @created August 3, 1999
 */
public class ProtectionOrder extends org.acm.seguin.pretty.sort.Ordering {
    // Instance variables
    private boolean publicFirst;

    private static final int PUBLIC = 1;

    private static final int PROTECTED = 2;

    private static final int PACKAGE = 3;

    private static final int PRIVATE = 4;

    /**
     * Constructor for the StaticOrder object <P>
     *
     *  The string should either be "instance", "static", or "class". "instance"
     *  means that instance variables should go first. Either of the other two
     *  ordering strings indicate that the class variables or methods should go
     *  first.
     *
     * @param ordering
     * 		A user specified string that describes the order.
     */
    public ProtectionOrder(java.lang.String ordering) {
        publicFirst = ordering.equalsIgnoreCase("public");
    }

    /**
     * Return the index of the item in the order array
     *
     * @param object
     * 		the object we are checking
     * @return the objects index if it is found or 7 if it is not
     */
    protected int getIndex(java.lang.Object object) {
        java.lang.Object data = ((org.acm.seguin.parser.ast.SimpleNode) (object)).jjtGetChild(0);
        if (data instanceof org.acm.seguin.parser.ast.ASTClassBodyDeclaration) {
            data = ((org.acm.seguin.parser.ast.ASTClassBodyDeclaration) (data)).jjtGetChild(0);
        } else if (data instanceof org.acm.seguin.parser.ast.ASTInterfaceMemberDeclaration) {
            data = ((org.acm.seguin.parser.ast.ASTInterfaceMemberDeclaration) (data)).jjtGetChild(0);
        }
        int protection = 0;
        // Now that we have data, determine the type of data
        if (data instanceof org.acm.seguin.parser.ast.ASTFieldDeclaration) {
            protection = getProtection(((org.acm.seguin.parser.ast.ASTFieldDeclaration) (data)).getModifiers());
        } else if (data instanceof org.acm.seguin.parser.ast.ASTConstructorDeclaration) {
            protection = getProtection(((org.acm.seguin.parser.ast.ASTConstructorDeclaration) (data)).getModifiers());
        } else if (data instanceof org.acm.seguin.parser.ast.ASTMethodDeclaration) {
            protection = getProtection(((org.acm.seguin.parser.ast.ASTMethodDeclaration) (data)).getModifiers());
        } else if (data instanceof org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration) {
            protection = getProtection(((org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration) (data)).getModifiers());
        } else if (data instanceof org.acm.seguin.parser.ast.ASTNestedClassDeclaration) {
            protection = getProtection(((org.acm.seguin.parser.ast.ASTNestedClassDeclaration) (data)).getModifiers());
        } else {
            return 100;
        }
        if (publicFirst) {
            return protection;
        } else {
            return -protection;
        }
    }

    /**
     * Gets the Protection attribute of the ProtectionOrder object
     *
     * @param mods
     * 		Description of Parameter
     * @return The Protection value
     */
    private int getProtection(org.acm.seguin.pretty.ModifierHolder mods) {
        if (mods.isPrivate()) {
            return org.acm.seguin.pretty.sort.ProtectionOrder.PRIVATE;
        } else if (mods.isProtected()) {
            return org.acm.seguin.pretty.sort.ProtectionOrder.PROTECTED;
        } else if (mods.isPublic()) {
            return org.acm.seguin.pretty.sort.ProtectionOrder.PUBLIC;
        } else {
            return org.acm.seguin.pretty.sort.ProtectionOrder.PACKAGE;
        }
    }
}