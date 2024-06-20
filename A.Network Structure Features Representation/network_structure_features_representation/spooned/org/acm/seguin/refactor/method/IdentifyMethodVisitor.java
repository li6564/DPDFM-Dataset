/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * A visitor that is able to identify the method that we are operating on
 *
 * @author Chris Seguin
 */
abstract class IdentifyMethodVisitor extends org.acm.seguin.parser.ChildrenVisitor {
    /**
     * The method we are searching for
     */
    private org.acm.seguin.summary.MethodSummary methodSummary;

    /**
     * Constructor for the IdentifyMethodVisitor object
     *
     * @param init
     * 		Description of Parameter
     */
    public IdentifyMethodVisitor(org.acm.seguin.summary.MethodSummary init) {
        methodSummary = init;
    }

    /**
     * Have we found the method declaration that we are going to move?
     *
     * @param next
     * 		Description of Parameter
     * @return The Found value
     */
    protected boolean isFound(org.acm.seguin.parser.ast.SimpleNode next) {
        if (next instanceof org.acm.seguin.parser.ast.ASTMethodDeclaration) {
            return checkDeclaration(((org.acm.seguin.parser.ast.ASTMethodDeclarator) (next.jjtGetChild(1))));
        }
        if (next instanceof org.acm.seguin.parser.ast.ASTConstructorDeclaration) {
            return checkDeclaration(((org.acm.seguin.parser.ast.ASTConstructorDeclaration) (next)));
        }
        return false;
    }

    /**
     * Checks a single variable declaration to see if it is the one we are
     *  looking for
     *
     * @param next
     * 		the method declaration that we are checking
     * @return true if we have found the method
     */
    protected boolean checkDeclaration(org.acm.seguin.parser.ast.ASTMethodDeclarator decl) {
        if (decl.getName().equals(methodSummary.getName())) {
            return checkParameters(((org.acm.seguin.parser.ast.ASTFormalParameters) (decl.jjtGetChild(0))));
        }
        return false;
    }

    /**
     * Checks a single variable declaration to see if it is the one we are
     *  looking for
     *
     * @param next
     * 		the method declaration that we are checking
     * @return true if we have found the method
     */
    protected boolean checkDeclaration(org.acm.seguin.parser.ast.ASTConstructorDeclaration decl) {
        if (methodSummary.isConstructor()) {
            return checkParameters(((org.acm.seguin.parser.ast.ASTFormalParameters) (decl.jjtGetChild(0))));
        }
        return false;
    }

    /**
     * Description of the Method
     *
     * @param params
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    protected boolean checkParameters(org.acm.seguin.parser.ast.ASTFormalParameters params) {
        int length = params.jjtGetNumChildren();
        java.util.Iterator iter = methodSummary.getParameters();
        if (iter == null) {
            return length == 0;
        }
        int ndx;
        for (ndx = 0; iter.hasNext() && (ndx < length); ndx++) {
            org.acm.seguin.parser.ast.ASTFormalParameter param = ((org.acm.seguin.parser.ast.ASTFormalParameter) (params.jjtGetChild(ndx)));
            org.acm.seguin.parser.ast.ASTType type = ((org.acm.seguin.parser.ast.ASTType) (param.jjtGetChild(0)));
            java.lang.String name;
            if (type.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTPrimitiveType) {
                name = ((org.acm.seguin.parser.ast.ASTPrimitiveType) (type.jjtGetChild(0))).getName();
            } else {
                name = ((org.acm.seguin.parser.ast.ASTName) (type.jjtGetChild(0))).getName();
            }
            org.acm.seguin.summary.ParameterSummary paramSummary = ((org.acm.seguin.summary.ParameterSummary) (iter.next()));
            java.lang.String typeName = paramSummary.getTypeDecl().getLongName();
            if (!name.equals(typeName)) {
                return false;
            }
        }
        return (!iter.hasNext()) && (ndx == length);
    }
}