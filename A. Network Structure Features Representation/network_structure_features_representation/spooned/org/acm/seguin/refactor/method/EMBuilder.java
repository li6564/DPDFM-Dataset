/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Builds the invocation of the method that is extracted for insertion into
 *  the place where the method was extracted
 *
 * @author Chris Seguin
 */
class EMBuilder {
    private java.lang.String methodName;

    private boolean isStatement;

    private java.util.LinkedList parameters;

    private org.acm.seguin.summary.VariableSummary returnSummary;

    private boolean localVariableNeeded = false;

    /**
     * Constructor for the EMBuilder object
     */
    public EMBuilder() {
        returnSummary = null;
    }

    /**
     * Sets the MethodName attribute of the EMBuilder object
     *
     * @param value
     * 		The new MethodName value
     */
    public void setMethodName(java.lang.String value) {
        methodName = value;
    }

    /**
     * Sets the Statement attribute of the EMBuilder object
     *
     * @param value
     * 		The new Statement value
     */
    public void setStatement(boolean value) {
        isStatement = value;
    }

    /**
     * Sets the Parameters attribute of the EMBuilder object
     *
     * @param list
     * 		The new Parameters value
     */
    public void setParameters(java.util.LinkedList list) {
        parameters = list;
    }

    /**
     * Sets the ReturnName attribute of the EMBuilder object
     *
     * @param name
     * 		The new ReturnName value
     */
    public void setReturnSummary(org.acm.seguin.summary.VariableSummary value) {
        returnSummary = value;
    }

    /**
     * Sets the LocalVariableNeeded attribute of the EMBuilder object
     *
     * @param value
     * 		The new LocalVariableNeeded value
     */
    public void setLocalVariableNeeded(boolean value) {
        localVariableNeeded = value;
    }

    /**
     * Builds the statement or assignment or local variable declaration
     *
     * @return The resulting value
     */
    public org.acm.seguin.parser.Node build() {
        org.acm.seguin.parser.ast.ASTBlockStatement blockStatement = new org.acm.seguin.parser.ast.ASTBlockStatement(0);
        if (localVariableNeeded) {
            buildWithLocal(blockStatement);
            return blockStatement;
        }
        org.acm.seguin.parser.ast.ASTStatement statement = new org.acm.seguin.parser.ast.ASTStatement(0);
        blockStatement.jjtAddChild(statement, 0);
        org.acm.seguin.parser.ast.ASTStatementExpression stateExpress = new org.acm.seguin.parser.ast.ASTStatementExpression(0);
        statement.jjtAddChild(stateExpress, 0);
        org.acm.seguin.parser.ast.ASTPrimaryExpression primaryExpression = null;
        if (isStatement && (returnSummary != null)) {
            buildAssignment(stateExpress);
        } else {
            primaryExpression = buildMethodInvocation(stateExpress, 0);
        }
        if (isStatement) {
            return blockStatement;
        } else {
            return primaryExpression;
        }
    }

    /**
     * Builds the assignment
     *
     * @param stateExpress
     * 		Description of Parameter
     */
    private void buildAssignment(org.acm.seguin.parser.ast.ASTStatementExpression stateExpress) {
        // First add what we are returning
        org.acm.seguin.parser.ast.ASTPrimaryExpression primaryExpression = new org.acm.seguin.parser.ast.ASTPrimaryExpression(0);
        stateExpress.jjtAddChild(primaryExpression, 0);
        org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix = new org.acm.seguin.parser.ast.ASTPrimaryPrefix(0);
        primaryExpression.jjtAddChild(prefix, 0);
        org.acm.seguin.parser.ast.ASTName name = new org.acm.seguin.parser.ast.ASTName(0);
        name.addNamePart(returnSummary.getName());
        primaryExpression.jjtAddChild(name, 0);
        // Now add the assignment operator
        org.acm.seguin.parser.ast.ASTAssignmentOperator assign = new org.acm.seguin.parser.ast.ASTAssignmentOperator(0);
        assign.setName("=");
        stateExpress.jjtAddChild(assign, 1);
        // Finally add the rest
        buildMethodInvocation(stateExpress, 2);
    }

    /**
     * Builds the method invocation
     *
     * @param stateExpress
     * 		Description of Parameter
     * @param index
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private org.acm.seguin.parser.ast.ASTPrimaryExpression buildMethodInvocation(org.acm.seguin.parser.ast.SimpleNode stateExpress, int index) {
        org.acm.seguin.parser.ast.ASTPrimaryExpression primaryExpression = new org.acm.seguin.parser.ast.ASTPrimaryExpression(0);
        stateExpress.jjtAddChild(primaryExpression, index);
        org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix = new org.acm.seguin.parser.ast.ASTPrimaryPrefix(0);
        primaryExpression.jjtAddChild(prefix, 0);
        org.acm.seguin.parser.ast.ASTName name = new org.acm.seguin.parser.ast.ASTName(0);
        name.addNamePart(methodName);
        primaryExpression.jjtAddChild(name, 0);
        org.acm.seguin.parser.ast.ASTPrimarySuffix suffix = new org.acm.seguin.parser.ast.ASTPrimarySuffix(0);
        primaryExpression.jjtAddChild(suffix, 1);
        org.acm.seguin.parser.ast.ASTArguments args = new org.acm.seguin.parser.ast.ASTArguments(0);
        suffix.jjtAddChild(args, 0);
        org.acm.seguin.parser.ast.ASTArgumentList argList = new org.acm.seguin.parser.ast.ASTArgumentList(0);
        args.jjtAddChild(argList, 0);
        int count = 0;
        org.acm.seguin.parser.build.BuildExpression be = new org.acm.seguin.parser.build.BuildExpression();
        java.util.Iterator iter = parameters.iterator();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.VariableSummary next = ((org.acm.seguin.summary.VariableSummary) (iter.next()));
                org.acm.seguin.parser.ast.ASTExpression expr = be.buildName(next.getName());
                argList.jjtAddChild(expr, count);
                count++;
            } 
        }
        return primaryExpression;
    }

    /**
     * Builds a local variable declaration
     *
     * @param blockStatement
     * 		the block statement we are inserting into
     */
    private void buildWithLocal(org.acm.seguin.parser.ast.ASTBlockStatement blockStatement) {
        org.acm.seguin.parser.ast.ASTLocalVariableDeclaration statement = new org.acm.seguin.parser.ast.ASTLocalVariableDeclaration(0);
        blockStatement.jjtAddChild(statement, 0);
        org.acm.seguin.parser.ast.ASTType type = new org.acm.seguin.parser.ast.ASTType(0);
        statement.jjtAddChild(type, 0);
        org.acm.seguin.summary.TypeDeclSummary typeDecl = returnSummary.getTypeDecl();
        type.setArrayCount(typeDecl.getArrayCount());
        if (typeDecl.isPrimitive()) {
            org.acm.seguin.parser.ast.ASTPrimitiveType primitiveType = new org.acm.seguin.parser.ast.ASTPrimitiveType(0);
            primitiveType.setName(typeDecl.getType());
            type.jjtAddChild(primitiveType, 0);
        } else {
            org.acm.seguin.parser.ast.ASTName name = new org.acm.seguin.parser.ast.ASTName(0);
            name.fromString(typeDecl.getLongName());
            type.jjtAddChild(name, 0);
        }
        org.acm.seguin.parser.ast.ASTVariableDeclarator varDecl = new org.acm.seguin.parser.ast.ASTVariableDeclarator(0);
        statement.jjtAddChild(varDecl, 1);
        org.acm.seguin.parser.ast.ASTVariableDeclaratorId varDeclID = new org.acm.seguin.parser.ast.ASTVariableDeclaratorId(0);
        varDeclID.setName(returnSummary.getName());
        varDecl.jjtAddChild(varDeclID, 0);
        org.acm.seguin.parser.ast.ASTVariableInitializer initializer = new org.acm.seguin.parser.ast.ASTVariableInitializer(0);
        varDecl.jjtAddChild(initializer, 1);
        buildMethodInvocation(initializer, 0);
    }
}