/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.tools.stub;
/**
 * This object simply reflects all the processing back to the individual
 *  nodes.
 *
 * @author Chris Seguin
 * @created October 13, 1999
 * @date March 4, 1999
 */
public class StubPrintVisitor implements org.acm.seguin.parser.JavaParserVisitor {
    /**
     * Constructor for the StubPrintVisitor object
     */
    public StubPrintVisitor() {
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Accept the children
        node.childrenAccept(this, data);
        // Flush the buffer
        printData.flush();
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.appendKeyword("package");
        printData.space();
        // Traverse the children
        node.childrenAccept(this, data);
        // Print any final tokens
        printData.appendText(";");
        printData.newline();
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.appendKeyword("import ");
        // Traverse the children
        node.childrenAccept(this, data);
        // Print any final tokens
        if (node.isImportingPackage()) {
            printData.appendText(".");
            printData.appendText("*");
            printData.appendText(";");
            printData.newline();
        } else {
            printData.appendText(";");
            printData.newline();
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
            // Get the data
            org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
            printData.appendText(";");
            printData.newline();
        }
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassDeclaration node, java.lang.Object data) {
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Get the child
        org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
        // Indent and insert the modifiers
        printData.indent();
        printData.appendKeyword(node.getModifiersString());
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.appendKeyword("class ");
        printData.appendText(node.getName());
        // Traverse the children
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < lastIndex; ndx++) {
            org.acm.seguin.parser.Node next = node.jjtGetChild(ndx);
            if (next instanceof org.acm.seguin.parser.ast.ASTName) {
                printData.appendKeyword(" extends ");
                next.jjtAccept(this, data);
            } else if (next instanceof org.acm.seguin.parser.ast.ASTNameList) {
                printData.appendKeyword(" implements ");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.beginBlock();
        // Traverse the children
        node.childrenAccept(this, data);
        // Print any tokens
        printData.endBlock();
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
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.beginClass();
        // Get the child
        org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
        // Indent and include the modifiers
        printData.indent();
        printData.appendKeyword(node.getModifiersString());
        // Traverse the children
        node.childrenAccept(this, data);
        printData.endClass();
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
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Get the child
        org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
        // Indent and add the modifiers
        printData.indent();
        printData.appendKeyword(node.getModifiersString());
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
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.beginInterface();
        // Get the child
        org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
        // Force the Javadoc to be included
        if (node.isRequired()) {
            node.finish();
            node.printJavaDocComponents(printData);
        }
        // Indent and include the modifiers
        printData.indent();
        printData.appendKeyword(node.getModifiersString());
        // Traverse the children
        node.childrenAccept(this, data);
        // Finish this interface
        printData.endInterface();
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.appendKeyword("interface ");
        printData.appendText(node.getName());
        // Traverse the children
        int nextIndex = 0;
        org.acm.seguin.parser.Node next = node.jjtGetChild(nextIndex);
        if (next instanceof org.acm.seguin.parser.ast.ASTNameList) {
            printData.appendKeyword(" extends ");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Begin the block
        printData.beginBlock();
        // Travers the children
        node.childrenAccept(this, data);
        // End the block
        printData.endBlock();
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
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.beginField();
        if (node.isRequired()) {
            node.finish();
            node.printJavaDocComponents(printData);
        }
        printData.indent();
        printData.appendKeyword(node.getModifiersString());
        // Handle the first two children (which are required)
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
        next.jjtAccept(this, data);
        printData.space();
        next = node.jjtGetChild(1);
        next.jjtAccept(this, data);
        // Traverse the rest of the children
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 2; ndx < lastIndex; ndx++) {
            printData.appendText(", ");
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }
        // Finish the entry
        printData.appendText(";");
        printData.newline();
        printData.endField();
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Handle the first child (which is required)
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTVariableDeclaratorId node, java.lang.Object data) {
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Handle the first child (which is required)
        printData.appendText(node.getName());
        int last = node.getArrayCount();
        for (int ndx = 0; ndx < last; ndx++) {
            printData.appendText("[");
            printData.appendText("]");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Handle the first child (which is required)
        printData.appendText("{");
        int last = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            if (ndx > 0) {
                printData.appendText(", ");
            }
            org.acm.seguin.parser.Node child = node.jjtGetChild(ndx);
            child.jjtAccept(this, data);
        }
        if (node.isFinalComma()) {
            printData.appendText(",");
        }
        printData.appendText("}");
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMethodDeclaration node, java.lang.Object data) {
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.beginMethod();
        printData.indent();
        printData.appendKeyword(node.getModifiersString());
        // Handle the first two children (which are required)
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
        next.jjtAccept(this, data);
        printData.space();
        next = node.jjtGetChild(1);
        next.jjtAccept(this, data);
        // Traverse the rest of the children
        int lastIndex = node.jjtGetNumChildren();
        boolean foundBlock = false;
        for (int ndx = 2; ndx < lastIndex; ndx++) {
            next = node.jjtGetChild(ndx);
            if (next instanceof org.acm.seguin.parser.ast.ASTNameList) {
                printData.appendKeyword(" throws ");
                next.jjtAccept(this, data);
            } else if (next instanceof org.acm.seguin.parser.ast.ASTBlock) {
                foundBlock = true;
                printData.appendText("{ ");
                next.jjtAccept(this, data);
            }
        }
        // Finish if it is abstract
        if (foundBlock) {
            printData.appendText("}");
        } else {
            printData.appendText(";");
            printData.newline();
        }
        // Note the end of the method
        printData.endMethod();
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Handle the first child (which is required)
        printData.appendText(node.getName());
        node.childrenAccept(this, data);
        int last = node.getArrayCount();
        for (int ndx = 0; ndx < last; ndx++) {
            printData.appendText("[");
            printData.appendText("]");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.beginExpression(node.jjtGetNumChildren() > 0);
        // Traverse the children
        org.acm.seguin.parser.Node next;
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < lastIndex; ndx++) {
            if (ndx > 0) {
                printData.appendText(", ");
            }
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }
        // Finish it
        printData.endExpression(node.jjtGetNumChildren() > 0);
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        if (node.isUsingFinal()) {
            printData.appendKeyword("final ");
        }
        // Traverse the children
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
        next.jjtAccept(this, data);
        printData.space();
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
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.beginMethod();
        printData.indent();
        printData.appendKeyword(node.getModifiersString());
        printData.appendText(node.getName());
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
                printData.space();
                printData.appendKeyword("throws");
                printData.space();
                next.jjtAccept(this, data);
                startAt++;
            }
        }
        // Print the starting block
        printData.beginBlock();
        // Traverse the rest of the children
        boolean foundBlock = false;
        for (int ndx = startAt; ndx < lastIndex; ndx++) {
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }
        // Print the end block
        printData.endBlock();
        printData.endMethod();
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInitializer node, java.lang.Object data) {
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        node.childrenAccept(this, data);
        // Add the array
        int count = node.getArrayCount();
        for (int ndx = 0; ndx < count; ndx++) {
            printData.appendText("[");
            printData.appendText("]");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print the name of the node
        printData.appendKeyword(node.getName());
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        if (node.hasAnyChildren()) {
            node.childrenAccept(this, data);
        } else {
            printData.appendKeyword("void");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print the name of the node
        printData.appendText(node.getName());
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        int countChildren = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < countChildren; ndx++) {
            if (ndx > 0) {
                printData.appendText(", ");
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTConditionalAndExpression node, java.lang.Object data) {
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInclusiveOrExpression node, java.lang.Object data) {
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTExclusiveOrExpression node, java.lang.Object data) {
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTAndExpression node, java.lang.Object data) {
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTEqualityExpression node, java.lang.Object data) {
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInstanceOfExpression node, java.lang.Object data) {
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTRelationalExpression node, java.lang.Object data) {
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTShiftExpression node, java.lang.Object data) {
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTAdditiveExpression node, java.lang.Object data) {
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMultiplicativeExpression node, java.lang.Object data) {
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnaryExpression node, java.lang.Object data) {
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTBlock node, java.lang.Object data) {
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTSwitchStatement node, java.lang.Object data) {
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
        return data;
    }
}