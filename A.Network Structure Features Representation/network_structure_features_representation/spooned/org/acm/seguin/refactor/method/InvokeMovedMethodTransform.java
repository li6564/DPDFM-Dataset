/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Adds an abstract method to the class
 *
 * @author Chris Seguin
 */
public class InvokeMovedMethodTransform extends org.acm.seguin.refactor.method.AddNewMethod {
    private org.acm.seguin.summary.Summary dest;

    /**
     * Constructor for the InvokeMovedMethodTransform object
     *
     * @param init
     * 		The signature of the method that we are adding
     * @param destination
     * 		Description of Parameter
     */
    public InvokeMovedMethodTransform(org.acm.seguin.summary.MethodSummary init, org.acm.seguin.summary.Summary destination) {
        super(init);
        dest = destination;
    }

    /**
     * Determines if the method is abstract
     *
     * @return true if the method is abstract
     */
    protected boolean isAbstract() {
        return false;
    }

    /**
     * Adds the body of the method
     *
     * @param methodDecl
     * 		The feature to be added to the Body attribute
     * @param index
     * 		The feature to be added to the Body attribute
     */
    protected void addBody(org.acm.seguin.parser.ast.SimpleNode methodDecl, int index) {
        org.acm.seguin.parser.ast.ASTBlock block = new org.acm.seguin.parser.ast.ASTBlock(0);
        methodDecl.jjtAddChild(block, index);
        if (dest instanceof org.acm.seguin.summary.ParameterSummary) {
            org.acm.seguin.summary.ParameterSummary paramSummary = ((org.acm.seguin.summary.ParameterSummary) (dest));
            org.acm.seguin.parser.ast.ASTBlockStatement blockStatement = new org.acm.seguin.parser.ast.ASTBlockStatement(0);
            block.jjtAddChild(blockStatement, 0);
            org.acm.seguin.parser.ast.ASTStatement statement = new org.acm.seguin.parser.ast.ASTStatement(0);
            blockStatement.jjtAddChild(statement, 0);
            org.acm.seguin.parser.ast.ASTStatementExpression stateExpress = new org.acm.seguin.parser.ast.ASTStatementExpression(0);
            statement.jjtAddChild(stateExpress, 0);
            org.acm.seguin.parser.ast.ASTPrimaryExpression primaryExpression = new org.acm.seguin.parser.ast.ASTPrimaryExpression(0);
            stateExpress.jjtAddChild(primaryExpression, 0);
            org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix = new org.acm.seguin.parser.ast.ASTPrimaryPrefix(0);
            primaryExpression.jjtAddChild(prefix, 0);
            org.acm.seguin.parser.ast.ASTName name = new org.acm.seguin.parser.ast.ASTName(0);
            name.addNamePart(paramSummary.getName());
            name.addNamePart(methodSummary.getName());
            primaryExpression.jjtAddChild(name, 0);
            org.acm.seguin.parser.ast.ASTPrimarySuffix suffix = new org.acm.seguin.parser.ast.ASTPrimarySuffix(0);
            primaryExpression.jjtAddChild(suffix, 1);
            org.acm.seguin.parser.ast.ASTArguments args = new org.acm.seguin.parser.ast.ASTArguments(0);
            suffix.jjtAddChild(args, 0);
            org.acm.seguin.parser.ast.ASTArgumentList argList = new org.acm.seguin.parser.ast.ASTArgumentList(0);
            args.jjtAddChild(argList, 0);
            int count = 0;
            java.util.Iterator iter = methodSummary.getParameters();
            if (iter != null) {
                while (iter.hasNext()) {
                    org.acm.seguin.summary.ParameterSummary next = ((org.acm.seguin.summary.ParameterSummary) (iter.next()));
                    if (!next.equals(paramSummary)) {
                        org.acm.seguin.parser.ast.ASTExpression expr = buildExpression(next.getName());
                        argList.jjtAddChild(expr, count);
                        count++;
                    }
                } 
            }
            if (isObjectReferenced()) {
                argList.jjtAddChild(buildExpression("this"), count);
            }
        }
    }

    /**
     * Determines if this object is referenced
     *
     * @return The ObjectReferenced value
     */
    private boolean isObjectReferenced() {
        return org.acm.seguin.refactor.method.ObjectReference.isReferenced(methodSummary);
    }

    /**
     * Builds an expression
     *
     * @param name
     * 		the name at the base of the expression
     * @return the expression
     */
    private org.acm.seguin.parser.ast.ASTExpression buildExpression(java.lang.String name) {
        org.acm.seguin.parser.build.BuildExpression be = new org.acm.seguin.parser.build.BuildExpression();
        return be.buildName(name);
    }
}