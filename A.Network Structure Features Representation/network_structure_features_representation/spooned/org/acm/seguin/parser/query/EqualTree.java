/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.query;
/**
 * This software compares a parse tree to insure that they are identical.
 *
 * @author Chris Seguin
 */
public class EqualTree extends org.acm.seguin.parser.query.CompareParseTreeVisitor {
    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTAdditiveExpression node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            org.acm.seguin.parser.ast.ASTAdditiveExpression other = ((org.acm.seguin.parser.ast.ASTAdditiveExpression) (data));
            java.util.Enumeration enum1 = node.getNames();
            java.util.Enumeration enum2 = other.getNames();
            while (enum1.hasMoreElements() && enum2.hasMoreElements()) {
                java.lang.Object next1 = enum1.nextElement();
                java.lang.Object next2 = enum2.nextElement();
                if (!next1.equals(next2)) {
                    // System.out.println("Different additive expression:  term different");
                    return java.lang.Boolean.FALSE;
                }
            } 
            if (enum1.hasMoreElements() || enum2.hasMoreElements()) {
                // System.out.println("Different additive expression:  different number of terms");
                return java.lang.Boolean.FALSE;
            }
            return java.lang.Boolean.TRUE;
        }
        // System.out.println("Different additive expression:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTArrayDimsAndInits node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getArrayCount() == ((org.acm.seguin.parser.ast.ASTArrayDimsAndInits) (data)).getArrayCount()) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different array dim and inits:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTArrayInitializer node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.isFinalComma() == ((org.acm.seguin.parser.ast.ASTArrayInitializer) (data)).isFinalComma()) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different array initializer:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTAssignmentOperator node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTAssignmentOperator) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different assignment operator:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTBooleanLiteral node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTBooleanLiteral) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different boolean literal:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTBreakStatement node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTBreakStatement) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different break statement:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassDeclaration node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getModifiers().equals(((org.acm.seguin.parser.ast.ASTClassDeclaration) (data)).getModifiers())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different class declaration:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTConstructorDeclaration node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getModifiers().equals(((org.acm.seguin.parser.ast.ASTConstructorDeclaration) (data)).getModifiers()) && node.getName().equals(((org.acm.seguin.parser.ast.ASTConstructorDeclaration) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different constructor declaration:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTContinueStatement node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTContinueStatement) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different continue statement:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTEqualityExpression node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            org.acm.seguin.parser.ast.ASTEqualityExpression other = ((org.acm.seguin.parser.ast.ASTEqualityExpression) (data));
            java.util.Enumeration enum1 = node.getNames();
            java.util.Enumeration enum2 = other.getNames();
            while (enum1.hasMoreElements() && enum2.hasMoreElements()) {
                java.lang.Object next1 = enum1.nextElement();
                java.lang.Object next2 = enum2.nextElement();
                if (!next1.equals(next2)) {
                    // System.out.println("Different equality expression:  different term");
                    return java.lang.Boolean.FALSE;
                }
            } 
            if (enum1.hasMoreElements() || enum2.hasMoreElements()) {
                // System.out.println("Different equality expression:  different number of terms");
                return java.lang.Boolean.FALSE;
            }
            return java.lang.Boolean.TRUE;
        }
        // System.out.println("Different equality expression:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTExplicitConstructorInvocation node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTExplicitConstructorInvocation) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different explicit constructor invoke:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTFieldDeclaration node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            org.acm.seguin.pretty.ModifierHolder nodeModifiers = node.getModifiers();
            org.acm.seguin.pretty.ModifierHolder dataModifiers = ((org.acm.seguin.parser.ast.ASTFieldDeclaration) (data)).getModifiers();
            if (nodeModifiers.equals(dataModifiers)) {
                return java.lang.Boolean.TRUE;
            }
            // System.out.println("Different field modifiers:  [" + nodeModifiers + "] [" + dataModifiers + "}");
        }
        // System.out.println("Different field declaration:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTFormalParameter node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.isUsingFinal() == ((org.acm.seguin.parser.ast.ASTFormalParameter) (data)).isUsingFinal()) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different formal parameter:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTImportDeclaration node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.isImportingPackage() == ((org.acm.seguin.parser.ast.ASTImportDeclaration) (data)).isImportingPackage()) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different import:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInitializer node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.isUsingStatic() == ((org.acm.seguin.parser.ast.ASTInitializer) (data)).isUsingStatic()) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different initializer:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInterfaceDeclaration node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getModifiers().equals(((org.acm.seguin.parser.ast.ASTInterfaceDeclaration) (data)).getModifiers())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different interface declaration:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTLabeledStatement node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTLabeledStatement) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different labeled statement:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTLiteral node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTLiteral) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different literal:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTLocalVariableDeclaration node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.isUsingFinal() == ((org.acm.seguin.parser.ast.ASTLocalVariableDeclaration) (data)).isUsingFinal()) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different local variable declaration:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMethodDeclaration node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            org.acm.seguin.pretty.ModifierHolder nodeModifiers = node.getModifiers();
            org.acm.seguin.pretty.ModifierHolder dataModifiers = ((org.acm.seguin.parser.ast.ASTMethodDeclaration) (data)).getModifiers();
            if (nodeModifiers.equals(dataModifiers)) {
                return java.lang.Boolean.TRUE;
            }
            // System.out.println("Different method modifiers:  [" + nodeModifiers + "] [" + dataModifiers + "}");
        }
        // System.out.println("Different method declaration:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMethodDeclarator node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTMethodDeclarator) (data)).getName()) && (node.getArrayCount() == ((org.acm.seguin.parser.ast.ASTMethodDeclarator) (data)).getArrayCount())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different method declarator:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMultiplicativeExpression node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            org.acm.seguin.parser.ast.ASTMultiplicativeExpression other = ((org.acm.seguin.parser.ast.ASTMultiplicativeExpression) (data));
            java.util.Enumeration enum1 = node.getNames();
            java.util.Enumeration enum2 = other.getNames();
            while (enum1.hasMoreElements() && enum2.hasMoreElements()) {
                java.lang.Object next1 = enum1.nextElement();
                java.lang.Object next2 = enum2.nextElement();
                if (!next1.equals(next2)) {
                    // System.out.println("Different multiplicative expression:  different term");
                    return java.lang.Boolean.FALSE;
                }
            } 
            if (enum1.hasMoreElements() || enum2.hasMoreElements()) {
                // System.out.println("Different multiplicative expression:  different number of terms");
                return java.lang.Boolean.FALSE;
            }
            return java.lang.Boolean.TRUE;
        }
        // System.out.println("Different multiplicative expression:  structure different");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTName node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            org.acm.seguin.parser.ast.ASTName other = ((org.acm.seguin.parser.ast.ASTName) (data));
            if (other.getNameSize() != node.getNameSize()) {
                // System.out.println("Different name:  different size");
                return java.lang.Boolean.FALSE;
            }
            for (int ndx = 0; ndx < node.getNameSize(); ndx++) {
                java.lang.String next1 = node.getNamePart(ndx);
                java.lang.String next2 = other.getNamePart(ndx);
                if (!next1.equals(next2)) {
                    // System.out.println("Different name:  different term " + ndx);
                    return java.lang.Boolean.FALSE;
                }
            }
            return java.lang.Boolean.TRUE;
        }
        // System.out.println("Different name:  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNestedClassDeclaration node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getModifiers().equals(((org.acm.seguin.parser.ast.ASTNestedClassDeclaration) (data)).getModifiers())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different nested class declaration:  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getModifiers().equals(((org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration) (data)).getModifiers())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different nested interface declaration:  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPostfixExpression node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTPostfixExpression) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different postfix expression:  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPrimaryPrefix node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTPrimaryPrefix) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different name:  primary prefix structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPrimarySuffix node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTPrimarySuffix) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different primary suffix:  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPrimitiveType node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTPrimitiveType) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different primitive type:  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTRelationalExpression node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            org.acm.seguin.parser.ast.ASTRelationalExpression other = ((org.acm.seguin.parser.ast.ASTRelationalExpression) (data));
            java.util.Enumeration enum1 = node.getNames();
            java.util.Enumeration enum2 = other.getNames();
            while (enum1.hasMoreElements() && enum2.hasMoreElements()) {
                java.lang.Object next1 = enum1.nextElement();
                java.lang.Object next2 = enum2.nextElement();
                if (!next1.equals(next2)) {
                    // System.out.println("Different relational expression:  different term");
                    return java.lang.Boolean.FALSE;
                }
            } 
            if (enum1.hasMoreElements() || enum2.hasMoreElements()) {
                // System.out.println("Different relational expression:  different number of terms");
                return java.lang.Boolean.FALSE;
            }
            return java.lang.Boolean.TRUE;
        }
        // System.out.println("Different relational expression:  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTShiftExpression node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            org.acm.seguin.parser.ast.ASTShiftExpression other = ((org.acm.seguin.parser.ast.ASTShiftExpression) (data));
            java.util.Enumeration enum1 = node.getNames();
            java.util.Enumeration enum2 = other.getNames();
            while (enum1.hasMoreElements() && enum2.hasMoreElements()) {
                java.lang.Object next1 = enum1.nextElement();
                java.lang.Object next2 = enum2.nextElement();
                if (!next1.equals(next2)) {
                    // System.out.println("Different shift expression:  different term");
                    return java.lang.Boolean.FALSE;
                }
            } 
            if (enum1.hasMoreElements() || enum2.hasMoreElements()) {
                // System.out.println("Different shift expression:  different number of terms");
                return java.lang.Boolean.FALSE;
            }
            return java.lang.Boolean.TRUE;
        }
        // System.out.println("Different relational expression:  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTStatementExpression node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTStatementExpression) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different expression statement:  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTType node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getArrayCount() == ((org.acm.seguin.parser.ast.ASTType) (data)).getArrayCount()) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different type:  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnaryExpression node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTUnaryExpression) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different unary expression:  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnaryExpressionNotPlusMinus node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTUnaryExpressionNotPlusMinus) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different unary expression (not +/-):  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different unmodified class decl:  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration) (data)).getName())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different unmodified interface decl:  different structure");
        return java.lang.Boolean.FALSE;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTVariableDeclaratorId node, java.lang.Object data) {
        if (super.visit(node, data).equals(java.lang.Boolean.TRUE)) {
            if (node.getName().equals(((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (data)).getName()) && (node.getArrayCount() == ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (data)).getArrayCount())) {
                return java.lang.Boolean.TRUE;
            }
        }
        // System.out.println("Different variable declarator id:  different structure");
        return java.lang.Boolean.FALSE;
    }
}