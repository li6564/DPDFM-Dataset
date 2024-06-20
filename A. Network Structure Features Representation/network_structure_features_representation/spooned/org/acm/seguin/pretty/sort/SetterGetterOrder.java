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
public class SetterGetterOrder extends org.acm.seguin.pretty.sort.Ordering {
    // Instance variables
    private int[] order;

    private static final int SETTER = 1;

    private static final int GETTER = 2;

    private static final int OTHER = 3;

    /**
     * Constructor for the SetterGetterOrder object <P>
     *
     *  The string should either be "instance", "static", or "class". "instance"
     *  means that instance variables should go first. Either of the other two
     *  ordering strings indicate that the class variables or methods should go
     *  first.
     *
     * @param ordering
     * 		A user specified string that describes the order.
     */
    public SetterGetterOrder(java.lang.String ordering) {
        order = new int[3];
        order[0] = org.acm.seguin.pretty.sort.SetterGetterOrder.SETTER;
        order[1] = org.acm.seguin.pretty.sort.SetterGetterOrder.GETTER;
        order[2] = org.acm.seguin.pretty.sort.SetterGetterOrder.OTHER;
        java.util.StringTokenizer tok = new java.util.StringTokenizer(ordering, ", \t");
        if (tok.hasMoreTokens()) {
            order[0] = getCode(tok.nextToken());
        }
        if (tok.hasMoreTokens()) {
            order[1] = getCode(tok.nextToken());
        }
        if (tok.hasMoreTokens()) {
            order[2] = getCode(tok.nextToken());
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
        // Now that we have data, determine the type of data
        if (data instanceof org.acm.seguin.parser.ast.ASTMethodDeclaration) {
            org.acm.seguin.parser.ast.ASTMethodDeclaration declaration = ((org.acm.seguin.parser.ast.ASTMethodDeclaration) (data));
            org.acm.seguin.parser.ast.ASTMethodDeclarator declar = ((org.acm.seguin.parser.ast.ASTMethodDeclarator) (declaration.jjtGetChild(1)));
            java.lang.String name = declar.getName();
            return findCode(getCode(name));
        } else {
            return 100;
        }
    }

    /**
     * Gets the Code attribute of the SetterGetterOrder object
     *
     * @param val
     * 		Description of Parameter
     * @return The Code value
     */
    private int getCode(java.lang.String val) {
        java.lang.String shortValue;
        if (val.length() < 2) {
            return org.acm.seguin.pretty.sort.SetterGetterOrder.OTHER;
        }
        shortValue = val.substring(0, 2);
        if (shortValue.equals("is")) {
            return org.acm.seguin.pretty.sort.SetterGetterOrder.GETTER;
        }
        if (val.length() < 3) {
            return org.acm.seguin.pretty.sort.SetterGetterOrder.OTHER;
        }
        shortValue = val.substring(0, 3);
        if (shortValue.equalsIgnoreCase("set")) {
            return org.acm.seguin.pretty.sort.SetterGetterOrder.SETTER;
        } else if (shortValue.equalsIgnoreCase("get")) {
            return org.acm.seguin.pretty.sort.SetterGetterOrder.GETTER;
        }
        return org.acm.seguin.pretty.sort.SetterGetterOrder.OTHER;
    }

    /**
     * Description of the Method
     *
     * @param code
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private int findCode(int code) {
        for (int ndx = 0; ndx < 3; ndx++) {
            if (order[ndx] == code) {
                return ndx;
            }
        }
        return 100;
    }
}