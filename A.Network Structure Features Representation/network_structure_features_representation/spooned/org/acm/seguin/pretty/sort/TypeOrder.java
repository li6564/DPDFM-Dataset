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
 * @created July 4, 1999
 */
public class TypeOrder extends org.acm.seguin.pretty.sort.Ordering {
    // Instance variables
    private java.lang.Class[] order;

    private boolean usingMain;

    /**
     * Constructor for the TypeOrder object
     *
     * @param ordering
     * 		A user specified string that describes the order
     */
    public TypeOrder(java.lang.String ordering) {
        // Create instance variables
        usingMain = false;
        order = new java.lang.Class[7];
        order[0] = org.acm.seguin.parser.ast.ASTFieldDeclaration.class;
        order[1] = org.acm.seguin.parser.ast.ASTConstructorDeclaration.class;
        order[2] = org.acm.seguin.parser.ast.ASTMethodDeclaration.class;
        order[3] = org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration.class;
        order[4] = org.acm.seguin.parser.ast.ASTNestedClassDeclaration.class;
        order[5] = org.acm.seguin.parser.ast.ASTInitializer.class;
        order[6] = org.acm.seguin.parser.ast.ASTInitializer.class;
        // Local Variables
        int nextItem = 0;
        // Break it up
        java.util.StringTokenizer tok = new java.util.StringTokenizer(ordering, ", \t");
        while (tok.hasMoreTokens() && (nextItem < 7)) {
            java.lang.String next = tok.nextToken();
            java.lang.String lowerCase = next.toLowerCase();
            if (lowerCase.startsWith("f")) {
                order[nextItem] = org.acm.seguin.parser.ast.ASTFieldDeclaration.class;
            } else if (lowerCase.startsWith("c")) {
                order[nextItem] = org.acm.seguin.parser.ast.ASTConstructorDeclaration.class;
            } else if (lowerCase.startsWith("me")) {
                order[nextItem] = org.acm.seguin.parser.ast.ASTMethodDeclaration.class;
            } else if (lowerCase.startsWith("ma")) {
                order[nextItem] = java.lang.String.class;
                usingMain = true;
            } else if (lowerCase.startsWith("i")) {
                order[nextItem] = org.acm.seguin.parser.ast.ASTInitializer.class;
            } else if (lowerCase.startsWith("n")) {
                if (lowerCase.indexOf("i") >= 0) {
                    order[nextItem] = org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration.class;
                } else {
                    order[nextItem] = org.acm.seguin.parser.ast.ASTNestedClassDeclaration.class;
                }
            }
            nextItem++;
        } 
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
        java.lang.Class type = data.getClass();
        for (int ndx = 0; ndx < 7; ndx++) {
            if (isMatch(data, type, order[ndx])) {
                return ndx;
            }
        }
        return 8;
    }

    /**
     * Gets the Match attribute of the TypeOrder object
     *
     * @param data
     * 		Description of Parameter
     * @param type
     * 		Description of Parameter
     * @param current
     * 		Description of Parameter
     * @return The Match value
     */
    private boolean isMatch(java.lang.Object data, java.lang.Class type, java.lang.Class current) {
        if (usingMain && current.equals(java.lang.String.class)) {
            if (type.equals(org.acm.seguin.parser.ast.ASTMethodDeclaration.class)) {
                org.acm.seguin.parser.ast.ASTMethodDeclaration declaration = ((org.acm.seguin.parser.ast.ASTMethodDeclaration) (data));
                org.acm.seguin.parser.ast.ASTMethodDeclarator declar = ((org.acm.seguin.parser.ast.ASTMethodDeclarator) (declaration.jjtGetChild(1)));
                java.lang.String name = declar.getName();
                return name.equals("main") && declaration.getModifiers().isStatic();
            }
            return false;
        } else {
            return type.equals(current);
        }
    }
}