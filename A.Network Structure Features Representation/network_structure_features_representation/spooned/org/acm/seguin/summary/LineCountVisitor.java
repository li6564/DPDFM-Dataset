/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * This object counts the lines and labels them with specific data
 *
 * @author Chris Seguin
 * @created March 4, 1999
 */
public class LineCountVisitor implements org.acm.seguin.parser.JavaParserVisitor {
    // Instance Variables
    private int lineCount;

    /**
     * Constructor for the LineCountVisitor object
     */
    public LineCountVisitor() {
        lineCount = 1;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.SimpleNode node, java.lang.Object data) {
        node.childrenAccept(this, data);
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTCompilationUnit node, java.lang.Object data) {
        // Accept the children
        node.childrenAccept(this, data);
        // Visit EOF special token
        countLines(node.getSpecial("EOF"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPackageDeclaration node, java.lang.Object data) {
        // Print any tokens
        countLines(node.getSpecial("package"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Print any final tokens
        countLines(node.getSpecial("semicolon"));
        // Return the data
        return data;
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
        // Print any tokens
        countLines(node.getSpecial("import"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Print any final tokens
        if (node.isImportingPackage()) {
            countLines(node.getSpecial("period"));
            countLines(node.getSpecial("star"));
            countLines(node.getSpecial("semicolon"));
        } else {
            countLines(node.getSpecial("semicolon"));
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTTypeDeclaration node, java.lang.Object data) {
        if (node.hasAnyChildren()) {
            node.childrenAccept(this, data);
        } else {
            countLines(node.getSpecial("semicolon"));
        }
        return data;
    }

    /**
     * Visit a class declaration
     *
     * @param node
     * 		the class declaration
     * @param data
     * 		the print data
     * @return the result of visiting this node
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassDeclaration node, java.lang.Object data) {
        // Print any tokens
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("abstract"));
        // Get the child
        org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
        countLines(child.getSpecial("class"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Return the data
        return data;
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
        // Print any tokens
        countLines(node.getSpecial("id"));
        // Traverse the children
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < lastIndex; ndx++) {
            org.acm.seguin.parser.Node next = node.jjtGetChild(ndx);
            if (next instanceof org.acm.seguin.parser.ast.ASTName) {
                countLines(node.getSpecial("extends"));
                next.jjtAccept(this, data);
            } else if (next instanceof org.acm.seguin.parser.ast.ASTNameList) {
                countLines(node.getSpecial("implements"));
                next.jjtAccept(this, data);
            } else {
                next.jjtAccept(this, data);
            }
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassBody node, java.lang.Object data) {
        // Print any tokens
        countLines(node.getSpecial("begin"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Print any tokens
        countLines(node.getSpecial("end"));
        // Return the data
        return data;
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
        // Print any tokens
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        countLines(node.getSpecial("abstract"));
        countLines(node.getSpecial("static"));
        // Get the child
        org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
        countLines(child.getSpecial("class"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassBodyDeclaration node, java.lang.Object data) {
        node.childrenAccept(this, data);
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMethodDeclarationLookahead node, java.lang.Object data) {
        node.childrenAccept(this, data);
        return data;
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
        // Print any special tokens
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("abstract"));
        // Get the child
        org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
        countLines(child.getSpecial("interface"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Return the data
        return data;
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
        // Print any tokens
        countLines(node.getSpecial("static"));
        countLines(node.getSpecial("abstract"));
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        // Get the child
        org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
        countLines(child.getSpecial("interface"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Finish this interface
        // Return the data
        return data;
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
        // Local Variables
        boolean first = true;
        // Print any tokens
        countLines(node.getSpecial("id"));
        // Traverse the children
        int nextIndex = 0;
        org.acm.seguin.parser.Node next = node.jjtGetChild(nextIndex);
        if (next instanceof org.acm.seguin.parser.ast.ASTNameList) {
            countLines(node.getSpecial("extends"));
            next.jjtAccept(this, data);
            // Get the next node
            nextIndex++;
            next = node.jjtGetChild(nextIndex);
        }
        // Handle the interface body
        next.jjtAccept(this, data);
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInterfaceBody node, java.lang.Object data) {
        // Begin the block
        countLines(node.getSpecial("begin"));
        // Travers the children
        node.childrenAccept(this, data);
        // End the block
        countLines(node.getSpecial("end"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInterfaceMemberDeclaration node, java.lang.Object data) {
        node.childrenAccept(this, data);
        return data;
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
        // Print any tokens
        countLines(node.getSpecial("static"));
        countLines(node.getSpecial("transient"));
        countLines(node.getSpecial("volatile"));
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        // Handle the first two children (which are required)
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
        next.jjtAccept(this, data);
        next = node.jjtGetChild(1);
        next.jjtAccept(this, data);
        // Traverse the rest of the children
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 2; ndx < lastIndex; ndx++) {
            countLines(node.getSpecial("comma" + (ndx - 2)));
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }
        // Finish the entry
        countLines(node.getSpecial("semicolon"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTVariableDeclarator node, java.lang.Object data) {
        // Handle the first child (which is required)
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
        next.jjtAccept(this, data);
        // Traverse the rest of the children
        if (node.jjtGetNumChildren() > 1) {
            countLines(node.getSpecial("equals"));
            next = node.jjtGetChild(1);
            next.jjtAccept(this, data);
        }
        // Return the data
        return data;
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
        // Handle the first child (which is required)
        countLines(node.getSpecial("id"));
        int last = node.getArrayCount();
        for (int ndx = 0; ndx < last; ndx++) {
            countLines(node.getSpecial("[." + ndx));
            countLines(node.getSpecial("]." + ndx));
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTVariableInitializer node, java.lang.Object data) {
        node.childrenAccept(this, data);
        return data;
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
        // Handle the first child (which is required)
        countLines(node.getSpecial("begin"));
        int last = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("comma" + (ndx - 1)));
            }
            org.acm.seguin.parser.Node child = node.jjtGetChild(ndx);
            child.jjtAccept(this, data);
        }
        if (node.isFinalComma()) {
            countLines(node.getSpecial("comma" + (last - 1)));
        }
        countLines(node.getSpecial("end"));
        // Return the data
        return data;
    }

    /**
     * Pretty prints the method declaration
     *
     * @param node
     * 		the node in the parse tree
     * @param data
     * 		the print data
     * @return the print data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMethodDeclaration node, java.lang.Object data) {
        // Print any tokens
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        countLines(node.getSpecial("static"));
        countLines(node.getSpecial("abstract"));
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("native"));
        countLines(node.getSpecial("synchronized"));
        countLines(getInitialToken(((org.acm.seguin.parser.ast.ASTResultType) (node.jjtGetChild(0)))));
        // Handle the first two children (which are required)
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
        next.jjtAccept(this, data);
        next = node.jjtGetChild(1);
        next.jjtAccept(this, data);
        // Traverse the rest of the children
        int lastIndex = node.jjtGetNumChildren();
        boolean foundBlock = false;
        for (int ndx = 2; ndx < lastIndex; ndx++) {
            next = node.jjtGetChild(ndx);
            if (next instanceof org.acm.seguin.parser.ast.ASTNameList) {
                countLines(node.getSpecial("throws"));
                next.jjtAccept(this, data);
            } else if (next instanceof org.acm.seguin.parser.ast.ASTBlock) {
                foundBlock = true;
                next.jjtAccept(this, data);
            }
        }
        // Finish if it is abstract
        if (!foundBlock) {
            countLines(node.getSpecial("semicolon"));
        }
        // Note the end of the method
        // Return the data
        return data;
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
        // Handle the first child (which is required)
        countLines(node.getSpecial("id"));
        node.childrenAccept(this, data);
        int last = node.getArrayCount();
        for (int ndx = 0; ndx < last; ndx++) {
            countLines(node.getSpecial("[." + ndx));
            countLines(node.getSpecial("]." + ndx));
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTFormalParameters node, java.lang.Object data) {
        // Print any tokens
        countLines(node.getSpecial("begin"));
        // Traverse the children
        org.acm.seguin.parser.Node next;
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < lastIndex; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
            }
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }
        // Finish it
        countLines(node.getSpecial("end"));
        // Return the data
        return data;
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
        // Print any tokens
        if (node.isUsingFinal()) {
            countLines(node.getSpecial("final"));
        }
        // Traverse the children
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
        next.jjtAccept(this, data);
        next = node.jjtGetChild(1);
        next.jjtAccept(this, data);
        // Return the data
        return data;
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
        // Print any tokens
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        countLines(node.getSpecial("id"));
        // Handle the first child (which is required)
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
        next.jjtAccept(this, data);
        // Get the last index
        int lastIndex = node.jjtGetNumChildren();
        int startAt = 1;
        // Handle the name list if it is present
        if (lastIndex > 1) {
            next = node.jjtGetChild(1);
            if (next instanceof org.acm.seguin.parser.ast.ASTNameList) {
                countLines(node.getSpecial("throws"));
                next.jjtAccept(this, data);
                startAt++;
            }
        }
        // Print the starting block
        countLines(node.getSpecial("begin"));
        // Traverse the rest of the children
        boolean foundBlock = false;
        for (int ndx = startAt; ndx < lastIndex; ndx++) {
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }
        // Print the end block
        countLines(node.getSpecial("end"));
        // Return the data
        return data;
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
        // Print the name of the node
        int startWith = 0;
        if (node.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTPrimaryExpression) {
            node.jjtGetChild(0).jjtAccept(this, data);
            startWith++;
            countLines(node.getSpecial("."));
        }
        countLines(node.getSpecial("explicit"));
        // Traverse the children
        int last = node.jjtGetNumChildren();
        for (int ndx = startWith; ndx < last; ndx++) {
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
        // End of the line
        countLines(node.getSpecial("semicolon"));
        // Return the data
        return data;
    }

    /**
     * Formats the initializer
     *
     * @param node
     * 		The initializer node
     * @param data
     * 		The print data
     * @return Nothing interesting
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInitializer node, java.lang.Object data) {
        // Print the name of the node
        if (node.isUsingStatic()) {
            countLines(node.getSpecial("static"));
        }
        // Traverse the children
        node.childrenAccept(this, data);
        // Return the data
        return data;
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
        // Traverse the children
        node.childrenAccept(this, data);
        // Add the array
        int count = node.getArrayCount();
        for (int ndx = 0; ndx < count; ndx++) {
            countLines(node.getSpecial("[." + ndx));
            countLines(node.getSpecial("]." + ndx));
        }
        // Return the data
        return data;
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
        // Print the name of the node
        countLines(node.getSpecial("primitive"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTResultType node, java.lang.Object data) {
        // Traverse the children
        if (node.hasAnyChildren()) {
            node.childrenAccept(this, data);
        } else {
            countLines(node.getSpecial("primitive"));
        }
        // Return the data
        return data;
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
        // Print the name of the node
        int size = node.getNameSize();
        for (int ndx = 0; ndx < size; ndx++) {
            countLines(node.getSpecial("id" + ndx));
            countLines(node.getSpecial("period" + ndx));
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNameList node, java.lang.Object data) {
        // Traverse the children
        int countChildren = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < countChildren; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
            }
            org.acm.seguin.parser.Node child = node.jjtGetChild(ndx);
            child.jjtAccept(this, data);
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTExpression node, java.lang.Object data) {
        node.childrenAccept(this, data);
        return data;
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
        // Print the name of the node
        countLines(node.getSpecial("operator"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTConditionalExpression node, java.lang.Object data) {
        // Traverse the children
        int childCount = node.jjtGetNumChildren();
        if (childCount == 1) {
            node.jjtGetChild(0).jjtAccept(this, data);
        } else {
            node.jjtGetChild(0).jjtAccept(this, data);
            countLines(node.getSpecial("?"));
            node.jjtGetChild(1).jjtAccept(this, data);
            countLines(node.getSpecial(":"));
            node.jjtGetChild(2).jjtAccept(this, data);
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTConditionalOrExpression node, java.lang.Object data) {
        return binaryExpression(node, "||", data);
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTConditionalAndExpression node, java.lang.Object data) {
        return binaryExpression(node, "&&", data);
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInclusiveOrExpression node, java.lang.Object data) {
        return binaryExpression(node, "|", data);
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTExclusiveOrExpression node, java.lang.Object data) {
        return binaryExpression(node, "^", data);
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTAndExpression node, java.lang.Object data) {
        return binaryExpression(node, "&", data);
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
        return binaryExpression(node, node.getNames(), data);
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInstanceOfExpression node, java.lang.Object data) {
        return binaryExpression(node, "instanceof", data);
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
        return binaryExpression(node, node.getNames(), data);
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
        return binaryExpression(node, node.getNames(), data);
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTAdditiveExpression node, java.lang.Object data) {
        return binaryExpression(node, node.getNames(), data);
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
        return binaryExpression(node, node.getNames(), data);
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
        // Traverse the children
        org.acm.seguin.parser.Node child = node.jjtGetChild(0);
        if (child instanceof org.acm.seguin.parser.ast.ASTUnaryExpression) {
            countLines(node.getSpecial("operator"));
        }
        child.jjtAccept(this, data);
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPreIncrementExpression node, java.lang.Object data) {
        // Include the preincrement
        countLines(node.getSpecial("operator"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPreDecrementExpression node, java.lang.Object data) {
        // Include the preincrement
        countLines(node.getSpecial("operator"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Return the data
        return data;
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
        // Traverse the children
        org.acm.seguin.parser.Node child = node.jjtGetChild(0);
        if (child instanceof org.acm.seguin.parser.ast.ASTUnaryExpression) {
            countLines(node.getSpecial("operator"));
        }
        child.jjtAccept(this, data);
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTCastLookahead node, java.lang.Object data) {
        // Traverse the children
        node.childrenAccept(this, data);
        // Return the data
        return data;
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
        // Traverse the children
        node.childrenAccept(this, data);
        // Include the increment
        countLines(node.getSpecial("operator"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTCastExpression node, java.lang.Object data) {
        // Cast portion
        countLines(node.getSpecial("begin"));
        node.jjtGetChild(0).jjtAccept(this, data);
        countLines(node.getSpecial("end"));
        // Expression
        node.jjtGetChild(1).jjtAccept(this, data);
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPrimaryExpression node, java.lang.Object data) {
        node.childrenAccept(this, data);
        return data;
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
        // Traverse the children
        if (node.jjtGetNumChildren() == 0) {
            countLines(node.getSpecial("this"));
            countLines(node.getSpecial("id"));
        } else {
            org.acm.seguin.parser.Node child = node.jjtGetChild(0);
            if (((child instanceof org.acm.seguin.parser.ast.ASTLiteral) || (child instanceof org.acm.seguin.parser.ast.ASTName)) || (child instanceof org.acm.seguin.parser.ast.ASTAllocationExpression)) {
                child.jjtAccept(this, data);
            } else if (child instanceof org.acm.seguin.parser.ast.ASTExpression) {
                countLines(node.getSpecial("begin"));
                child.jjtAccept(this, data);
                countLines(node.getSpecial("end"));
            } else if (child instanceof org.acm.seguin.parser.ast.ASTResultType) {
                child.jjtAccept(this, data);
            }
        }
        // Return the data
        return data;
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
        // Traverse the children
        if (node.jjtGetNumChildren() == 0) {
            countLines(node.getSpecial("dot"));
            countLines(node.getSpecial("id"));
        } else {
            org.acm.seguin.parser.Node child = node.jjtGetChild(0);
            if (child instanceof org.acm.seguin.parser.ast.ASTArguments) {
                child.jjtAccept(this, data);
            } else if (child instanceof org.acm.seguin.parser.ast.ASTExpression) {
                countLines(node.getSpecial("["));
                child.jjtAccept(this, data);
                countLines(node.getSpecial("]"));
            } else if (child instanceof org.acm.seguin.parser.ast.ASTAllocationExpression) {
                countLines(node.getSpecial("dot"));
                countLines(node.getSpecial("id"));
                child.jjtAccept(this, data);
            }
        }
        // Return the data
        return data;
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
        // Traverse the children
        if (node.hasAnyChildren()) {
            node.childrenAccept(this, data);
        } else {
            countLines(node.getSpecial("id"));
        }
        // Return the data
        return data;
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
        // Print the data
        countLines(node.getSpecial("id"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNullLiteral node, java.lang.Object data) {
        // Print the data
        countLines(node.getSpecial("id"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTArguments node, java.lang.Object data) {
        // Start the parens
        countLines(node.getSpecial("begin"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Finish the parens
        countLines(node.getSpecial("end"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTArgumentList node, java.lang.Object data) {
        // Traverse the children
        int count = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < count; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
            }
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTAllocationExpression node, java.lang.Object data) {
        // Traverse the children
        // Print the name of the node
        countLines(node.getSpecial("id"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Return the data
        return data;
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
        // Traverse the children
        boolean foundInitializer = false;
        int last = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            if (node.jjtGetChild(ndx) instanceof org.acm.seguin.parser.ast.ASTExpression) {
                countLines(node.getSpecial("[." + ndx));
                node.jjtGetChild(ndx).jjtAccept(this, data);
                countLines(node.getSpecial("]." + ndx));
            } else if (node.jjtGetChild(ndx) instanceof org.acm.seguin.parser.ast.ASTArrayInitializer) {
                foundInitializer = true;
            }
        }
        int looping = node.getArrayCount();
        if (foundInitializer) {
            looping++;
        }
        for (int ndx = last; ndx < looping; ndx++) {
            countLines(node.getSpecial("[." + ndx));
            countLines(node.getSpecial("]." + ndx));
        }
        if (foundInitializer) {
            node.jjtGetChild(last - 1).jjtAccept(this, data);
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTStatement node, java.lang.Object data) {
        // Traverse the children
        node.childrenAccept(this, data);
        if (node.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTStatementExpression) {
            // Finish off the statement expression
            countLines(node.getSpecial("semicolon"));
        }
        // Return the data
        return data;
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
        // Print the data
        countLines(node.getSpecial("id"));
        countLines(node.getSpecial("colon"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Return the data
        return data;
    }

    /**
     * Visits a block in the parse tree
     *
     * @param node
     * 		the block node
     * @param data
     * 		the information that is used to traverse the tree
     * @return data is returned
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTBlock node, java.lang.Object data) {
        // Start the block
        countLines(node.getSpecial("begin"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Finish the block
        countLines(node.getSpecial("end"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTBlockStatement node, java.lang.Object data) {
        // Include the stuff before the class/interface declaration
        if (node.hasAnyChildren()) {
            org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
            if (child instanceof org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration) {
                countLines(child.getSpecial("class"));
            } else if (child instanceof org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration) {
                countLines(child.getSpecial("interface"));
            }
        }
        countLines(node.getSpecial("semicolon"));
        // Traverse the children
        node.childrenAccept(this, data);
        // Return the data
        return data;
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
        // Traverse the children
        int last = node.jjtGetNumChildren();
        // Print the final token
        if (node.isUsingFinal()) {
            countLines(node.getSpecial("final"));
        }
        for (int ndx = 0; ndx < last; ndx++) {
            countLines(node.getSpecial("comma." + (ndx - 1)));
            // Visit the child
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTEmptyStatement node, java.lang.Object data) {
        // Print the name of the node
        countLines(node.getSpecial("semicolon"));
        // Return the data
        return data;
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
        // Traverse the children
        if (node.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTPrimaryExpression) {
            int last = node.jjtGetNumChildren();
            node.jjtGetChild(0).jjtAccept(this, data);
            countLines(node.getSpecial("id"));
            for (int ndx = 1; ndx < last; ndx++) {
                node.jjtGetChild(ndx).jjtAccept(this, data);
            }
        } else {
            node.childrenAccept(this, data);
        }
        // Return the data
        return data;
    }

    /**
     * Counts the number of lines associated with a switch statement
     *
     * @param node
     * 		the switch node in the parse tree
     * @param data
     * 		the data used to visit this parse tree
     * @return the data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTSwitchStatement node, java.lang.Object data) {
        // Switch
        countLines(node.getSpecial("switch"));
        countLines(node.getSpecial("beginExpr"));
        node.jjtGetChild(0).jjtAccept(this, data);
        countLines(node.getSpecial("endExpr"));
        // Start the block
        countLines(node.getSpecial("beginBlock"));
        // Traverse the children
        int last = node.jjtGetNumChildren();
        for (int ndx = 1; ndx < last; ndx++) {
            org.acm.seguin.parser.Node next = node.jjtGetChild(ndx);
            if (next instanceof org.acm.seguin.parser.ast.ASTBlockStatement) {
                next.jjtAccept(this, data);
            } else {
                next.jjtAccept(this, data);
            }
        }
        // End the block
        countLines(node.getSpecial("endBlock"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTSwitchLabel node, java.lang.Object data) {
        // Determine if the node has children
        if (node.hasAnyChildren()) {
            countLines(node.getSpecial("id"));
            node.childrenAccept(this, data);
            countLines(node.getSpecial("colon"));
        } else {
            countLines(node.getSpecial("id"));
            countLines(node.getSpecial("colon"));
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTIfStatement node, java.lang.Object data) {
        // Determine if the node has children
        countLines(node.getSpecial("if"));
        countLines(node.getSpecial("beginExpr"));
        node.jjtGetChild(0).jjtAccept(this, data);
        countLines(node.getSpecial("endExpr"));
        // Determine if the then contains a block
        boolean hasElse = node.jjtGetNumChildren() == 3;
        if (node.jjtGetNumChildren() >= 2) {
            forceBlock(node.jjtGetChild(1), data);
        }
        // Determine if the else part
        if (hasElse) {
            countLines(node.getSpecial("else"));
            // Determine if the next item is a statement
            org.acm.seguin.parser.ast.ASTStatement child = ((org.acm.seguin.parser.ast.ASTStatement) (node.jjtGetChild(2)));
            org.acm.seguin.parser.Node next = child.jjtGetChild(0);
            if (next instanceof org.acm.seguin.parser.ast.ASTIfStatement) {
                next.jjtAccept(this, data);
            } else {
                forceBlock(child, data);
            }
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTWhileStatement node, java.lang.Object data) {
        // Determine if the node has children
        countLines(node.getSpecial("while"));
        countLines(node.getSpecial("beginExpr"));
        node.jjtGetChild(0).jjtAccept(this, data);
        countLines(node.getSpecial("endExpr"));
        // Process the block
        forceBlock(node.jjtGetChild(1), data);
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTDoStatement node, java.lang.Object data) {
        // Begin the do block
        countLines(node.getSpecial("do"));
        // Process the block
        forceBlock(node.jjtGetChild(0), data);
        // Process the while block
        countLines(node.getSpecial("while"));
        countLines(node.getSpecial("beginExpr"));
        node.jjtGetChild(1).jjtAccept(this, data);
        countLines(node.getSpecial("endExpr"));
        countLines(node.getSpecial("semicolon"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTForStatement node, java.lang.Object data) {
        // Start the for loop
        countLines(node.getSpecial("for"));
        countLines(node.getSpecial("beginExpr"));
        // Traverse the children
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
        int index = 1;
        if (next instanceof org.acm.seguin.parser.ast.ASTForInit) {
            next.jjtAccept(this, data);
            next = node.jjtGetChild(index);
            index++;
        }
        countLines(node.getSpecial("init"));
        if (next instanceof org.acm.seguin.parser.ast.ASTExpression) {
            next.jjtAccept(this, data);
            next = node.jjtGetChild(index);
            index++;
        }
        countLines(node.getSpecial("test"));
        if (next instanceof org.acm.seguin.parser.ast.ASTForUpdate) {
            next.jjtAccept(this, data);
            next = node.jjtGetChild(index);
            index++;
        }
        countLines(node.getSpecial("endExpr"));
        forceBlock(next, data);
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTForInit node, java.lang.Object data) {
        // Traverse the children
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
        if (next instanceof org.acm.seguin.parser.ast.ASTLocalVariableDeclaration) {
            forInit(((org.acm.seguin.parser.ast.ASTLocalVariableDeclaration) (next)), data);
        } else {
            node.childrenAccept(this, data);
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTStatementExpressionList node, java.lang.Object data) {
        // Traverse the children
        int last = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
            }
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTForUpdate node, java.lang.Object data) {
        // Traverse the children
        node.childrenAccept(this, data);
        // Return the data
        return data;
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
        // Print the break statement
        countLines(node.getSpecial("break"));
        java.lang.String name = node.getName();
        if (!((name == null) || (name.length() == 0))) {
            countLines(node.getSpecial("id"));
        }
        countLines(node.getSpecial("semicolon"));
        // Return the data
        return data;
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
        // Print the continue statement
        countLines(node.getSpecial("continue"));
        java.lang.String name = node.getName();
        if (!((name == null) || (name.length() == 0))) {
            countLines(node.getSpecial("id"));
        }
        countLines(node.getSpecial("semicolon"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTReturnStatement node, java.lang.Object data) {
        // Traverse the children
        if (node.hasAnyChildren()) {
            countLines(node.getSpecial("return"));
            node.childrenAccept(this, data);
            countLines(node.getSpecial("semicolon"));
        } else {
            countLines(node.getSpecial("return"));
            countLines(node.getSpecial("semicolon"));
        }
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTThrowStatement node, java.lang.Object data) {
        // Traverse the children
        countLines(node.getSpecial("throw"));
        node.childrenAccept(this, data);
        countLines(node.getSpecial("semicolon"));
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTSynchronizedStatement node, java.lang.Object data) {
        // Traverse the children
        countLines(node.getSpecial("synchronized"));
        countLines(node.getSpecial("beginExpr"));
        node.jjtGetChild(0).jjtAccept(this, data);
        countLines(node.getSpecial("endExpr"));
        node.jjtGetChild(1).jjtAccept(this, data);
        // Return the data
        return data;
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTTryStatement node, java.lang.Object data) {
        // Traverse the children
        countLines(node.getSpecial("try"));
        node.jjtGetChild(0).jjtAccept(this, data);
        // Now work with the pairs
        int last = node.jjtGetNumChildren();
        boolean paired = false;
        int catchCount = 0;
        for (int ndx = 1; ndx < last; ndx++) {
            org.acm.seguin.parser.Node next = node.jjtGetChild(ndx);
            if (next instanceof org.acm.seguin.parser.ast.ASTFormalParameter) {
                countLines(node.getSpecial("catch" + catchCount));
                countLines(node.getSpecial("beginExpr" + catchCount));
                next.jjtAccept(this, data);
                countLines(node.getSpecial("endExpr" + catchCount));
                paired = true;
                catchCount++;
            } else {
                if (!paired) {
                    countLines(node.getSpecial("finally"));
                }
                next.jjtAccept(this, data);
                paired = false;
            }
        }
        // Return the data
        return data;
    }

    /**
     * Returns the current line count
     *
     * @return the line count
     */
    protected int getLineCount() {
        return lineCount;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param name
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    protected java.lang.Object binaryExpression(org.acm.seguin.parser.ast.SimpleNode node, java.lang.String name, java.lang.Object data) {
        // Traverse the children
        int childCount = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < childCount; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("operator." + (ndx - 1)));
            }
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
        // Return the data
        return data;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param names
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    protected java.lang.Object binaryExpression(org.acm.seguin.parser.ast.SimpleNode node, java.util.Enumeration names, java.lang.Object data) {
        // Traverse the children
        int childCount = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < childCount; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("operator." + (ndx - 1)));
            }
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
        // Return the data
        return data;
    }

    /**
     * This code is based on the pretty printer, and was
     *  used to force the source code to have a block for
     *  an if statement.
     *
     * @param node
     * 		The node that should be contained inside a block
     * @param data
     * 		the print data
     */
    protected void forceBlock(org.acm.seguin.parser.Node node, java.lang.Object data) {
        if ((node.jjtGetNumChildren() > 0) && (node.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTBlock)) {
            // We know we have a block
            org.acm.seguin.parser.ast.ASTBlock child = ((org.acm.seguin.parser.ast.ASTBlock) (node.jjtGetChild(0)));
            child.jjtAccept(this, data);
        } else {
            ((org.acm.seguin.parser.ast.SimpleNode) (node)).childrenAccept(this, data);
        }
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     */
    protected void forInit(org.acm.seguin.parser.ast.ASTLocalVariableDeclaration node, java.lang.Object data) {
        // Traverse the children
        int last = node.jjtGetNumChildren();
        org.acm.seguin.parser.Node typeNode = node.jjtGetChild(0);
        typeNode.jjtAccept(this, data);
        for (int ndx = 1; ndx < last; ndx++) {
            if (ndx > 1) {
                // Add a comma between entries
                countLines(node.getSpecial("comma." + (ndx - 1)));
            }
            // Print the final token
            if (node.isUsingFinal()) {
                countLines(node.getSpecial("final"));
            }
            // Visit the child
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
    }

    /**
     * Check the initial token, and removes it from the object.
     *
     * @param top
     * 		the result type
     * @return the initial token
     */
    protected org.acm.seguin.parser.Token getInitialToken(org.acm.seguin.parser.ast.ASTResultType top) {
        // Check to see if we need to go farther down
        if (top.hasAnyChildren()) {
            org.acm.seguin.parser.ast.ASTType type = ((org.acm.seguin.parser.ast.ASTType) (top.jjtGetChild(0)));
            if (type.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTPrimitiveType) {
                org.acm.seguin.parser.ast.ASTPrimitiveType primitiveType = ((org.acm.seguin.parser.ast.ASTPrimitiveType) (type.jjtGetChild(0)));
                org.acm.seguin.parser.Token tok = primitiveType.getSpecial("primitive");
                primitiveType.removeSpecial("primitive");
                return tok;
            } else {
                org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (type.jjtGetChild(0)));
                org.acm.seguin.parser.Token tok = name.getSpecial("id0");
                name.removeSpecial("id0");
                return tok;
            }
        } else {
            // No farther to go - return the token
            org.acm.seguin.parser.Token tok = top.getSpecial("primitive");
            top.removeSpecial("primitive");
            return tok;
        }
    }

    /**
     * Counts the number of lines in the current token
     *
     * @param token
     * 		the token
     */
    protected void countLines(org.acm.seguin.parser.Token token) {
        if (token == null) {
            return;
        }
        org.acm.seguin.parser.Token first = beginning(token);
        org.acm.seguin.parser.Token current = first;
        while (current != null) {
            switch (current.kind) {
                case 4 :
                case 5 :
                case 6 :
                case 7 :
                    lineCount++;
                    break;
                case org.acm.seguin.parser.JavaParserConstants.SINGLE_LINE_COMMENT :
                case org.acm.seguin.parser.JavaParserConstants.FORMAL_COMMENT :
                case org.acm.seguin.parser.JavaParserConstants.MULTI_LINE_COMMENT :
                case org.acm.seguin.parser.JavaParserConstants.CATEGORY_COMMENT :
                    countNewlines(current.image);
                    break;
                default :
                    java.lang.System.out.println("Unknown code:  " + current.kind);
                    break;
            }
            current = current.next;
        } 
    }

    /**
     * Got to the beginning
     *
     * @param tok
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private org.acm.seguin.parser.Token beginning(org.acm.seguin.parser.Token tok) {
        if (tok == null) {
            return null;
        }
        // Find the first token
        org.acm.seguin.parser.Token current = tok;
        org.acm.seguin.parser.Token previous = tok.specialToken;
        while (previous != null) {
            current = previous;
            previous = current.specialToken;
        } 
        // Return the first
        return current;
    }

    /**
     * Counts the lines in the token's string
     *
     * @param value
     * 		the string to count
     */
    private void countNewlines(java.lang.String value) {
        for (int ndx = 0; ndx < value.length(); ndx++) {
            if (value.charAt(ndx) == '\n') {
                lineCount++;
            }
        }
    }
}