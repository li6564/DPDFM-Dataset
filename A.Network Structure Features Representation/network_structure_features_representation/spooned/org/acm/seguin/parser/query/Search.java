/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.query;
/**
 * Performs a search on the parse tree to find specific nodes.
 *
 * @author Chris Seguin
 */
public class Search {
    private org.acm.seguin.parser.query.EqualTree equalTree;

    /**
     * Constructor for the Search object
     */
    public Search() {
        equalTree = new org.acm.seguin.parser.query.EqualTree();
    }

    /**
     * This is the main program for this search. The inputs are the root node
     *  and what it is that we are searching for.
     *
     * @param root
     * 		Description of Parameter
     * @param lookingFor
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public org.acm.seguin.parser.query.Found search(org.acm.seguin.parser.Node root, org.acm.seguin.parser.Node lookingFor) {
        // System.out.println("DEBUG[Search.search]  #1");
        int last = root.jjtGetNumChildren();
        int lookingForLast = lookingFor.jjtGetNumChildren();
        if (last >= lookingForLast) {
            org.acm.seguin.parser.query.Found result = searchAtLevel(root, lookingFor, last - lookingForLast);
            if (result != null) {
                return result;
            }
        }
        for (int ndx = 0; ndx < last; ndx++) {
            org.acm.seguin.parser.query.Found found = search(root.jjtGetChild(ndx), lookingFor);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /**
     * Search at the level we are on. This will search on the level we are at
     *  trying to find the items in the lookingFor node. If it is found, a Found
     *  object is returned.
     *
     * @param root
     * 		Description of Parameter
     * @param lookingFor
     * 		Description of Parameter
     * @param stop
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private org.acm.seguin.parser.query.Found searchAtLevel(org.acm.seguin.parser.Node root, org.acm.seguin.parser.Node lookingFor, int stop) {
        // System.out.println("DEBUG[Search.searchAtLevel]  #1");
        for (int ndx = 0; ndx <= stop; ndx++) {
            org.acm.seguin.parser.Node attempt = root.jjtGetChild(ndx);
            org.acm.seguin.parser.Node lookingForNode = lookingFor.jjtGetChild(0);
            // System.out.println("DEBUG[Search.searchAtLevel]  #2 " + attempt.getClass().getName() + "  " + lookingForNode.getClass().getName());
            java.lang.Boolean same = ((java.lang.Boolean) (attempt.jjtAccept(equalTree, lookingForNode)));
            if (same.equals(java.lang.Boolean.TRUE) && findAll(root, lookingFor, ndx)) {
                // System.out.println("DEBUG[Search.searchAtLevel]  #2");
                return new org.acm.seguin.parser.query.Found(root, ndx);
            }
        }
        // System.out.println("DEBUG[Search.searchAtLevel]  #3");
        return null;
    }

    /**
     * Now that we have a guess where to find all the nodes, we do a thorough
     *  search. This function will return true if the other items all are found
     *  as children
     *
     * @param root
     * 		Description of Parameter
     * @param lookingFor
     * 		Description of Parameter
     * @param offset
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private boolean findAll(org.acm.seguin.parser.Node root, org.acm.seguin.parser.Node lookingFor, int offset) {
        // System.out.println("DEBUG[Search.findAll]  #1");
        for (int ndx = 1; ndx < lookingFor.jjtGetNumChildren(); ndx++) {
            org.acm.seguin.parser.Node attempt = root.jjtGetChild(ndx + offset);
            java.lang.Boolean same = ((java.lang.Boolean) (attempt.jjtAccept(equalTree, lookingFor.jjtGetChild(ndx))));
            if (same.equals(java.lang.Boolean.FALSE)) {
                // System.out.println("DEBUG[Search.findAll]  #2");
                return false;
            }
        }
        // System.out.println("DEBUG[Search.findAll]  #3");
        return specialCase(root, lookingFor, offset);
    }

    /**
     * Certain node types also have names associated with them. These nodes are
     *  mathematical operations such as * or / and < < or >>. We need to check in
     *  the instance that we have such a node that the names all match up. This
     *  method does that computation.
     *
     * @param root
     * 		Description of Parameter
     * @param lookingFor
     * 		Description of Parameter
     * @param offset
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private boolean specialCase(org.acm.seguin.parser.Node root, org.acm.seguin.parser.Node lookingFor, int offset) {
        // System.out.println("DEBUG[Search.specialCase]  #1");
        java.util.Enumeration enum1 = null;
        java.util.Enumeration enum2 = null;
        if (root instanceof org.acm.seguin.parser.ast.ASTAdditiveExpression) {
            enum1 = ((org.acm.seguin.parser.ast.ASTAdditiveExpression) (root)).getNames();
            enum2 = ((org.acm.seguin.parser.ast.ASTAdditiveExpression) (lookingFor)).getNames();
        } else if (root instanceof org.acm.seguin.parser.ast.ASTEqualityExpression) {
            enum1 = ((org.acm.seguin.parser.ast.ASTEqualityExpression) (root)).getNames();
            enum2 = ((org.acm.seguin.parser.ast.ASTEqualityExpression) (lookingFor)).getNames();
        } else if (root instanceof org.acm.seguin.parser.ast.ASTMultiplicativeExpression) {
            enum1 = ((org.acm.seguin.parser.ast.ASTMultiplicativeExpression) (root)).getNames();
            enum2 = ((org.acm.seguin.parser.ast.ASTMultiplicativeExpression) (lookingFor)).getNames();
        } else if (root instanceof org.acm.seguin.parser.ast.ASTRelationalExpression) {
            enum1 = ((org.acm.seguin.parser.ast.ASTRelationalExpression) (root)).getNames();
            enum2 = ((org.acm.seguin.parser.ast.ASTRelationalExpression) (lookingFor)).getNames();
        } else if (root instanceof org.acm.seguin.parser.ast.ASTShiftExpression) {
            enum1 = ((org.acm.seguin.parser.ast.ASTShiftExpression) (root)).getNames();
            enum2 = ((org.acm.seguin.parser.ast.ASTShiftExpression) (lookingFor)).getNames();
        }
        // System.out.println("DEBUG[Search.specialCase]  #2");
        // We don't need to special case this node then
        if (enum1 == null) {
            return true;
        }
        // Skip the unnecessary ones
        for (int ndx = 0; ndx < offset; ndx++) {
            enum1.nextElement();
        }
        // System.out.println("DEBUG[Search.specialCase]  #3");
        // Compare the names
        while (enum2.hasMoreElements()) {
            java.lang.Object value1 = enum1.nextElement();
            java.lang.Object value2 = enum2.nextElement();
            if (!value1.equals(value2)) {
                // System.out.println("DEBUG[Search.specialCase]  #4");
                return false;
            }
        } 
        // All the names are the same, we are done
        // System.out.println("DEBUG[Search.specialCase]  #5");
        return true;
    }
}