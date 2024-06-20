/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
/**
 * Looks up a method in a type
 *
 * @author Chris Seguin
 * @created April 5, 2000
 */
public class GetMethodSummary {
    /**
     * Looks up the method given a type and a name
     *
     * @param type
     * 		the type
     * @param name
     * 		the name
     * @return the method summary
     */
    public static org.acm.seguin.summary.MethodSummary query(org.acm.seguin.summary.TypeSummary type, java.lang.String name) {
        java.util.Iterator iter = type.getMethods();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
                if (next.getName().equals(name)) {
                    return next;
                }
            } 
        }
        return null;
    }

    /**
     * Looks up the method given a type and a name
     *
     * @param type
     * 		the type
     * @param node
     * 		Description of Parameter
     * @return the method summary
     */
    public static org.acm.seguin.summary.MethodSummary query(org.acm.seguin.summary.TypeSummary type, org.acm.seguin.parser.ast.ASTMethodDeclaration node) {
        java.util.Iterator iter = type.getMethods();
        if (iter == null) {
            return null;
        }
        while (iter.hasNext()) {
            org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
            if (org.acm.seguin.summary.query.GetMethodSummary.isMatch(next, node)) {
                return next;
            }
        } 
        return null;
    }

    /**
     * Looks up the method given a type and a name
     *
     * @param type
     * 		the type
     * @param node
     * 		Description of Parameter
     * @return the method summary
     */
    public static org.acm.seguin.summary.MethodSummary query(org.acm.seguin.summary.TypeSummary type, org.acm.seguin.parser.ast.ASTConstructorDeclaration node) {
        java.util.Iterator iter = type.getMethods();
        if (iter == null) {
            return null;
        }
        while (iter.hasNext()) {
            org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
            if (next.isConstructor() && org.acm.seguin.summary.query.GetMethodSummary.isParameterMatch(((org.acm.seguin.parser.ast.ASTFormalParameters) (node.jjtGetChild(0))), next)) {
                return next;
            }
        } 
        return null;
    }

    /**
     * Gets the Match attribute of the GetMethodSummary class
     *
     * @param current
     * 		the current method
     * @param decl
     * 		the declaration
     * @return true if we have found the method summary
     */
    private static boolean isMatch(org.acm.seguin.summary.MethodSummary current, org.acm.seguin.parser.ast.ASTMethodDeclaration decl) {
        org.acm.seguin.parser.ast.ASTMethodDeclarator declarator = ((org.acm.seguin.parser.ast.ASTMethodDeclarator) (decl.jjtGetChild(1)));
        if (!current.getName().equals(declarator.getName())) {
            return false;
        }
        org.acm.seguin.parser.ast.ASTFormalParameters params = ((org.acm.seguin.parser.ast.ASTFormalParameters) (declarator.jjtGetChild(0)));
        return org.acm.seguin.summary.query.GetMethodSummary.isParameterMatch(params, current);
    }

    /**
     * Gets the ParameterMatch attribute of the GetMethodSummary class
     *
     * @param params
     * 		Description of Parameter
     * @param current
     * 		Description of Parameter
     */
    private static boolean isParameterMatch(org.acm.seguin.parser.ast.ASTFormalParameters params, org.acm.seguin.summary.MethodSummary current) {
        int childrenCount = params.jjtGetNumChildren();
        if (childrenCount != current.getParameterCount()) {
            return false;
        }
        if (childrenCount == 0) {
            return true;
        }
        java.util.Iterator iter = current.getParameters();
        for (int ndx = 0; ndx < childrenCount; ndx++) {
            org.acm.seguin.summary.ParameterSummary next = ((org.acm.seguin.summary.ParameterSummary) (iter.next()));
            org.acm.seguin.parser.ast.ASTFormalParameter nextParam = ((org.acm.seguin.parser.ast.ASTFormalParameter) (params.jjtGetChild(ndx)));
            org.acm.seguin.parser.ast.ASTVariableDeclaratorId id = ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (nextParam.jjtGetChild(1)));
            if (!next.getName().equals(id.getName())) {
                return false;
            }
        }
        return true;
    }
}