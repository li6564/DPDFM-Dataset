/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser;
/**
 * Scan through the abstract syntax tree and does nothing
 *
 * @author Chris Seguin
 * @created December 10, 1999
 */
public class ChildrenVisitor implements org.acm.seguin.parser.JavaParserVisitor {
    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.SimpleNode node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTCompilationUnit node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPackageDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTImportDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTTypeDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassBody node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNestedClassDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassBodyDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMethodDeclarationLookahead node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInterfaceDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInterfaceBody node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInterfaceMemberDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTFieldDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTVariableDeclarator node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTVariableDeclaratorId node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTVariableInitializer node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTArrayInitializer node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMethodDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMethodDeclarator node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTFormalParameters node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTFormalParameter node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTConstructorDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTExplicitConstructorInvocation node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInitializer node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTType node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPrimitiveType node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTResultType node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTName node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNameList node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTAssignmentOperator node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTConditionalExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTConditionalOrExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTConditionalAndExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInclusiveOrExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTExclusiveOrExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTAndExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTEqualityExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInstanceOfExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTRelationalExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTShiftExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTAdditiveExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMultiplicativeExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnaryExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPreIncrementExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPreDecrementExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnaryExpressionNotPlusMinus node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTCastLookahead node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPostfixExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTCastExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPrimaryExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPrimaryPrefix node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPrimarySuffix node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTLiteral node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTBooleanLiteral node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNullLiteral node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTArguments node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTArgumentList node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTAllocationExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTArrayDimsAndInits node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTLabeledStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTBlock node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTBlockStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTLocalVariableDeclaration node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTEmptyStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTStatementExpression node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTSwitchStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTSwitchLabel node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTIfStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTWhileStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTDoStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTForStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTForInit node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTStatementExpressionList node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTForUpdate node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTBreakStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTContinueStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTReturnStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTThrowStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTSynchronizedStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }

    /**
     * To visit a node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTTryStatement node, java.lang.Object data) {
        return node.childrenAccept(this, data);
    }
}