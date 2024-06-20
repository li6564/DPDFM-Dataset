/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty;
/**
 * This object simply reflects all the processing back to the individual
 *  nodes.
 *
 * @author Chris Seguin
 * @created March 4, 1999
 */
public class PrettyPrintVisitor implements org.acm.seguin.parser.JavaParserVisitor {
    // Instance Variables
    org.acm.seguin.pretty.SpecialTokenVisitor specialTokenVisitor;

    private org.acm.seguin.pretty.FieldSizeLookAhead fsla;

    private org.acm.seguin.pretty.LocalVariableLookAhead lvla;

    /**
     * Constructor for the PrettyPrintVisitor object
     */
    public PrettyPrintVisitor() {
        specialTokenVisitor = new org.acm.seguin.pretty.SpecialTokenVisitor();
        fsla = new org.acm.seguin.pretty.FieldSizeLookAhead(org.acm.seguin.pretty.PrintData.DFS_ALIGN_EQUALS);
        lvla = new org.acm.seguin.pretty.LocalVariableLookAhead();
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
        // Sort it
        node.sort(printData.getTopOrder());
        // Check if we have a header
        loadHeader(node, printData);
        // Accept the children
        node.childrenAccept(this, data);
        // Visit EOF special token
        if (!loadFooter(printData)) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("EOF"), printData));
        }
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
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("package"), printData));
        printData.appendKeyword("package");
        printData.space();
        // Traverse the children
        node.childrenAccept(this, data);
        // Print any final tokens
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
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
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("import"), printData));
        printData.appendKeyword("import");
        printData.space();
        // Traverse the children
        node.childrenAccept(this, data);
        // Print any final tokens
        if (node.isImportingPackage()) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("period"), printData));
            printData.appendText(".");
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("star"), printData));
            printData.appendText("*");
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
            printData.appendText(";");
            printData.newline();
        } else {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
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
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
            printData.appendText(";");
            printData.newline();
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("final"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("public"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("abstract"), printData));
        // Get the child
        org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(child.getSpecial("class"), printData));
        // Make sure that the javadoc stuff is there
        if (node.isRequired()) {
            node.finish();
            node.printJavaDocComponents(printData);
        }
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
        printData.appendKeyword("class");
        printData.space();
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
        printData.appendText(node.getName());
        printData.pushCurrentClassName(node.getName());
        // Traverse the children
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < lastIndex; ndx++) {
            org.acm.seguin.parser.Node next = node.jjtGetChild(ndx);
            if (next instanceof org.acm.seguin.parser.ast.ASTName) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("extends"), printData));
                printData.space();
                printData.appendKeyword("extends");
                printData.space();
                next.jjtAccept(this, data);
            } else if (next instanceof org.acm.seguin.parser.ast.ASTNameList) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("implements"), printData));
                printData.space();
                printData.appendKeyword("implements");
                printData.space();
                next.jjtAccept(this, data);
            } else {
                next.jjtAccept(this, data);
            }
        }
        // Finish the class
        printData.popCurrentClassName();
        // Return the data
        return data;
    }

    /**
     * Visits a class body node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		the print data
     * @return the print data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassBody node, java.lang.Object data) {
        return visit(node, data, true);
    }

    /**
     * Visits a class body node
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The print data
     * @param newline
     * 		Whether there should be a new line
     * @return the print data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassBody node, java.lang.Object data, boolean newline) {
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Sort it
        node.sort(printData.getOrder());
        org.acm.seguin.pretty.FieldSizeLookAhead fsla = new org.acm.seguin.pretty.FieldSizeLookAhead(printData.getFieldSpaceCode());
        org.acm.seguin.pretty.FieldSize size = fsla.run(node);
        size.update(printData.getDynamicFieldSpaces());
        printData.pushFieldSize(size);
        // Print any tokens
        printData.classBrace();
        printData.beginBlock();
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("begin"), printData, false));
        // Traverse the children
        node.childrenAccept(this, data);
        // Print any tokens
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("end"), printData));
        printData.classBrace();
        printData.endBlock(newline);
        printData.popFieldSize();
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.beginClass();
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("final"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("public"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("protected"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("private"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("abstract"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("static"), printData));
        // Get the child
        org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(child.getSpecial("class"), printData));
        // Make sure the java doc is there
        if (isJavadocRequired(node, printData)) {
            node.finish();
            node.printJavaDocComponents(printData);
        }
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any special tokens
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("public"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("abstract"), printData));
        // Get the child
        org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(child.getSpecial("interface"), printData));
        // Include the java doc comments
        if (node.isRequired()) {
            node.finish();
            node.printJavaDocComponents(printData);
        }
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.beginInterface();
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("static"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("abstract"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("final"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("public"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("protected"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("private"), printData));
        // Get the child
        org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(child.getSpecial("interface"), printData));
        // Force the Javadoc to be included
        if (isJavadocRequired(node, printData)) {
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
        printData.appendKeyword("interface");
        printData.space();
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
        printData.appendText(node.getName());
        printData.pushCurrentClassName(node.getName());
        // Traverse the children
        int nextIndex = 0;
        org.acm.seguin.parser.Node next = node.jjtGetChild(nextIndex);
        if (next instanceof org.acm.seguin.parser.ast.ASTNameList) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("extends"), printData));
            printData.space();
            printData.appendKeyword("extends");
            printData.space();
            next.jjtAccept(this, data);
            // Get the next node
            nextIndex++;
            next = node.jjtGetChild(nextIndex);
        }
        // Handle the interface body
        next.jjtAccept(this, data);
        // Finish
        printData.popCurrentClassName();
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
        printData.classBrace();
        printData.beginBlock();
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("begin"), printData, false));
        // Travers the children
        node.childrenAccept(this, data);
        // End the block
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("end"), printData));
        printData.classBrace();
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.beginField();
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("static"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("transient"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("volatile"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("final"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("public"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("protected"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("private"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(getInitialToken(((org.acm.seguin.parser.ast.ASTType) (node.jjtGetChild(0)))), printData));
        if (isJavadocRequired(node, printData)) {
            node.finish();
            node.printJavaDocComponents(printData);
        }
        printData.indent();
        java.lang.String modifiers = node.getModifiersString();
        printData.appendKeyword(modifiers);
        if (printData.isDynamicFieldSpacing(node.isJavadocPrinted())) {
            org.acm.seguin.pretty.FieldSize size = printData.topFieldSize();
            int maxModifier = size.getModifierLength();
            for (int ndx = modifiers.length(); ndx < maxModifier; ndx++) {
                printData.space();
            }
        }
        // Handle the first two children (which are required)
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
        next.jjtAccept(this, data);
        if (printData.isDynamicFieldSpacing(node.isJavadocPrinted())) {
            int current = fsla.computeTypeLength(node);
            org.acm.seguin.pretty.FieldSize size = printData.topFieldSize();
            int maxType = size.getTypeLength();
            for (int ndx = current; ndx < maxType; ndx++) {
                printData.space();
            }
        }
        if (printData.getFieldSpaceCode() == org.acm.seguin.pretty.PrintData.DFS_ALIGN_EQUALS) {
            printData.setTempEqualsLength(fsla.computeEqualsLength(node));
        }
        standardFieldIndent(printData);
        next = node.jjtGetChild(1);
        printData.setStoreJavadocPrinted(node.isJavadocPrinted());
        next.jjtAccept(this, data);
        printData.setStoreJavadocPrinted(false);
        // Traverse the rest of the children
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 2; ndx < lastIndex; ndx++) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("comma" + (ndx - 2)), printData));
            printData.appendText(", ");
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }
        // Finish the entry
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
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
        if (printData.isDynamicFieldSpacing(printData.isStoreJavadocPrinted()) && (node.jjtGetNumChildren() > 1)) {
            int current = ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (next)).getName().length();
            org.acm.seguin.pretty.FieldSize size = printData.topFieldSize();
            int maxName = size.getNameLength();
            for (int ndx = current; ndx < maxName; ndx++) {
                printData.space();
            }
        }
        if (((printData.getFieldSpaceCode() == org.acm.seguin.pretty.PrintData.DFS_ALIGN_EQUALS) && (node.jjtGetNumChildren() > 1)) && (!printData.getSkipNameSpacing())) {
            int current = printData.getTempEqualsLength();
            org.acm.seguin.pretty.FieldSize size = printData.topFieldSize();
            int maxName = size.getEqualsLength();
            for (int ndx = current; ndx < maxName; ndx++) {
                printData.space();
            }
        }
        printData.setStoreJavadocPrinted(false);
        // Traverse the rest of the children
        if (node.jjtGetNumChildren() > 1) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("equals"), printData));
            printData.appendText(" = ");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Handle the first child (which is required)
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
        printData.appendText(node.getName());
        int last = node.getArrayCount();
        for (int ndx = 0; ndx < last; ndx++) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("[." + ndx), printData));
            printData.appendText("[");
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("]." + ndx), printData));
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
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("begin"), printData));
        printData.appendText("{");
        int last = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            if (ndx > 0) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("comma" + (ndx - 1)), printData));
                printData.appendText(", ");
            }
            org.acm.seguin.parser.Node child = node.jjtGetChild(ndx);
            child.jjtAccept(this, data);
        }
        if (node.isFinalComma()) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("comma" + (last - 1)), printData));
            printData.appendText(",");
        }
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("end"), printData));
        printData.appendText("}");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.beginMethod();
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("public"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("protected"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("private"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("static"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("abstract"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("final"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("native"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("synchronized"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(getInitialToken(((org.acm.seguin.parser.ast.ASTResultType) (node.jjtGetChild(0)))), printData));
        if (isJavadocRequired(node, printData)) {
            node.finish(printData.getCurrentClassName());
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
        boolean foundBlock = false;
        for (int ndx = 2; ndx < lastIndex; ndx++) {
            next = node.jjtGetChild(ndx);
            if (next instanceof org.acm.seguin.parser.ast.ASTNameList) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("throws"), printData));
                printData.space();
                if (printData.isThrowsOnNewline()) {
                    printData.incrIndent();
                    printData.indent();
                    printData.decrIndent();
                }
                printData.appendKeyword("throws");
                printData.space();
                next.jjtAccept(this, data);
            } else if (next instanceof org.acm.seguin.parser.ast.ASTBlock) {
                foundBlock = true;
                if ((next.jjtGetNumChildren() == 0) && printData.getEmptyBlockOnSingleLine()) {
                    printData.appendText(" { }");
                    printData.newline();
                } else {
                    printData.methodBrace();
                    next.jjtAccept(this, data);
                }
            }
        }
        // Finish if it is abstract
        if (!foundBlock) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
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
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
        printData.appendText(node.getName());
        node.childrenAccept(this, data);
        int last = node.getArrayCount();
        for (int ndx = 0; ndx < last; ndx++) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("[." + ndx), printData));
            printData.appendText("[");
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("]." + ndx), printData));
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
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("begin"), printData));
        printData.beginExpression(node.hasAnyChildren());
        printData.enterMethodDecl();
        // Traverse the children
        org.acm.seguin.parser.Node next;
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < lastIndex; ndx++) {
            if (ndx > 0) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("comma." + (ndx - 1)), printData));
                printData.appendText(", ");
            }
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }
        // Finish it
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("end"), printData));
        printData.endExpression(node.hasAnyChildren());
        printData.exitMethodDecl();
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
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("final"), printData));
            printData.appendKeyword("final");
            printData.space();
        }
        printData.setParamIndent();
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print any tokens
        printData.beginMethod();
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("public"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("protected"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("private"), printData));
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
        if (node.isRequired()) {
            node.finish();
            node.printJavaDocComponents(printData);
        }
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
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("throws"), printData));
                printData.space();
                if (printData.isThrowsOnNewline()) {
                    printData.incrIndent();
                    printData.indent();
                    printData.decrIndent();
                }
                printData.appendKeyword("throws");
                printData.space();
                next.jjtAccept(this, data);
                startAt++;
            }
        }
        // Print the starting block
        if ((lastIndex == startAt) && printData.getEmptyBlockOnSingleLine()) {
            printData.appendText(" { }");
            printData.newline();
        } else {
            org.acm.seguin.pretty.LocalVariableLookAhead lvla = new org.acm.seguin.pretty.LocalVariableLookAhead();
            org.acm.seguin.pretty.FieldSize size = lvla.run(node);
            size.update(printData.getDynamicFieldSpaces());
            printData.pushFieldSize(size);
            printData.methodBrace();
            printData.beginBlock();
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("begin"), printData, false));
            // Traverse the rest of the children
            boolean foundBlock = false;
            for (int ndx = startAt; ndx < lastIndex; ndx++) {
                next = node.jjtGetChild(ndx);
                next.jjtAccept(this, data);
            }
            // Print the end block
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("end"), printData));
            printData.methodBrace();
            printData.endBlock();
        }
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print the name of the node
        int startWith = 0;
        printData.indent();
        if (node.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTPrimaryExpression) {
            node.jjtGetChild(0).jjtAccept(this, data);
            startWith++;
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("."), printData));
            printData.appendText(".");
        }
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("explicit"), printData));
        printData.appendText(node.getName());
        // Traverse the children
        int last = node.jjtGetNumChildren();
        for (int ndx = startWith; ndx < last; ndx++) {
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
        // End of the line
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
        printData.appendText(";");
        printData.newline();
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print the name of the node
        printData.indent();
        if (node.isUsingStatic()) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("static"), printData));
            printData.appendKeyword("static");
        }
        // Traverse the children
        org.acm.seguin.parser.ast.ASTBlock block = ((org.acm.seguin.parser.ast.ASTBlock) (node.jjtGetChild(0)));
        blockProcess(block, printData, true, node.isUsingStatic());
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        node.childrenAccept(this, data);
        // Add the array
        int count = node.getArrayCount();
        for (int ndx = 0; ndx < count; ndx++) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("[." + ndx), printData));
            printData.appendText("[");
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("]." + ndx), printData));
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
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("primitive"), printData));
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
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("primitive"), printData));
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
        int size = node.getNameSize();
        for (int ndx = 0; ndx < size; ndx++) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id" + ndx), printData));
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("period" + ndx), printData));
        }
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
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("comma." + (ndx - 1)), printData));
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print the name of the node
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("operator"), printData));
        printData.appendText((" " + node.getName()) + " ");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        int childCount = node.jjtGetNumChildren();
        if (childCount == 1) {
            node.jjtGetChild(0).jjtAccept(this, data);
        } else {
            node.jjtGetChild(0).jjtAccept(this, data);
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("?"), printData));
            printData.appendText(" ? ");
            node.jjtGetChild(1).jjtAccept(this, data);
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial(":"), printData));
            printData.appendText(" : ");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        org.acm.seguin.parser.Node child = node.jjtGetChild(0);
        if (child instanceof org.acm.seguin.parser.ast.ASTUnaryExpression) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("operator"), printData));
            printData.appendText(node.getName());
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Include the preincrement
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("operator"), printData));
        printData.appendText("++");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Include the preincrement
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("operator"), printData));
        printData.appendText("--");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        org.acm.seguin.parser.Node child = node.jjtGetChild(0);
        if (child instanceof org.acm.seguin.parser.ast.ASTUnaryExpression) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("operator"), printData));
            printData.appendText(node.getName());
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        node.childrenAccept(this, data);
        // Include the increment
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("operator"), printData));
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTCastExpression node, java.lang.Object data) {
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Cast portion
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("begin"), printData));
        printData.beginExpression(printData.isCastSpace());
        node.jjtGetChild(0).jjtAccept(this, data);
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("end"), printData));
        printData.endExpression(printData.isCastSpace());
        if (printData.isSpaceAfterCast()) {
            printData.space();
        }
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        if (node.jjtGetNumChildren() == 0) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("this"), printData));
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
            printData.appendText(node.getName());
        } else {
            org.acm.seguin.parser.Node child = node.jjtGetChild(0);
            if (((child instanceof org.acm.seguin.parser.ast.ASTLiteral) || (child instanceof org.acm.seguin.parser.ast.ASTName)) || (child instanceof org.acm.seguin.parser.ast.ASTAllocationExpression)) {
                child.jjtAccept(this, data);
            } else if (child instanceof org.acm.seguin.parser.ast.ASTExpression) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("begin"), printData));
                printData.beginExpression(node.hasAnyChildren());
                child.jjtAccept(this, data);
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("end"), printData));
                printData.endExpression(node.hasAnyChildren());
            } else if (child instanceof org.acm.seguin.parser.ast.ASTResultType) {
                child.jjtAccept(this, data);
                printData.appendText(".class");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        if (node.jjtGetNumChildren() == 0) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("dot"), printData));
            printData.appendText(".");
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
            printData.appendText(node.getName());
        } else {
            org.acm.seguin.parser.Node child = node.jjtGetChild(0);
            if (child instanceof org.acm.seguin.parser.ast.ASTArguments) {
                child.jjtAccept(this, data);
            } else if (child instanceof org.acm.seguin.parser.ast.ASTExpression) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("["), printData));
                printData.appendText("[");
                child.jjtAccept(this, data);
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("]"), printData));
                printData.appendText("]");
            } else if (child instanceof org.acm.seguin.parser.ast.ASTAllocationExpression) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("dot"), printData));
                printData.appendText(".");
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        if (node.hasAnyChildren()) {
            node.childrenAccept(this, data);
        } else {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
            printData.appendConstant(node.getName());
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print the data
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
        printData.appendConstant(node.getName());
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print the data
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
        printData.appendConstant("null");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Start the parens
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("begin"), printData));
        printData.beginExpression(node.hasAnyChildren());
        // Traverse the children
        node.childrenAccept(this, data);
        // Finish the parens
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("end"), printData));
        printData.endExpression(node.hasAnyChildren());
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        int count = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < count; ndx++) {
            if (ndx > 0) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("comma." + (ndx - 1)), printData));
                printData.appendText(", ");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        if (node.jjtGetNumChildren() == 3) {
            // Indenting
            int lastType = printData.getState();
            printData.setState(org.acm.seguin.pretty.PrintData.EMPTY);
            printData.incrIndent();
            // Print the name of the node
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
            printData.indent();
            printData.appendKeyword("new");
            printData.space();
            // Traverse the children
            org.acm.seguin.parser.ast.SimpleNode nameNode = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
            org.acm.seguin.parser.ast.SimpleNode arguments = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(1)));
            org.acm.seguin.parser.ast.ASTClassBody classBody = ((org.acm.seguin.parser.ast.ASTClassBody) (node.jjtGetChild(2)));
            nameNode.jjtAccept(this, data);
            arguments.jjtAccept(this, data);
            visit(classBody, data, false);
            // Cleanup
            printData.decrIndent();
            // printData.indent();
            printData.setState(lastType);
        } else {
            // Print the name of the node
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
            printData.appendKeyword("new");
            printData.space();
            // Traverse the children
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTArrayDimsAndInits node, java.lang.Object data) {
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        boolean foundInitializer = false;
        int last = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            if (node.jjtGetChild(ndx) instanceof org.acm.seguin.parser.ast.ASTExpression) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("[." + ndx), printData));
                printData.appendText("[");
                node.jjtGetChild(ndx).jjtAccept(this, data);
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("]." + ndx), printData));
                printData.appendText("]");
            } else if (node.jjtGetChild(ndx) instanceof org.acm.seguin.parser.ast.ASTArrayInitializer) {
                foundInitializer = true;
            }
        }
        int looping = node.getArrayCount();
        if (foundInitializer) {
            looping++;
        }
        for (int ndx = last; ndx < looping; ndx++) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("[." + ndx), printData));
            printData.appendText("[");
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("]." + ndx), printData));
            printData.appendText("]");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        printData.indent();
        node.childrenAccept(this, data);
        if (node.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTStatementExpression) {
            // Finish off the statement expression
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTLabeledStatement node, java.lang.Object data) {
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print the data
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
        printData.appendText(node.getName());
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("colon"), printData));
        printData.appendText(" : ");
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTBlock node, java.lang.Object data) {
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        blockProcess(node, printData, true);
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Include the stuff before the class/interface declaration
        if (node.hasAnyChildren()) {
            org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
            if (child instanceof org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration) {
                printData.beginClass();
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(child.getSpecial("class"), printData));
                printData.indent();
            } else if (child instanceof org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration) {
                printData.beginClass();
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(child.getSpecial("interface"), printData));
                printData.indent();
            }
        }
        // Traverse the children
        node.childrenAccept(this, data);
        if (node.hasAnyChildren()) {
            org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
            if (child instanceof org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration) {
                printData.endClass();
            } else if (child instanceof org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration) {
                printData.endClass();
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTLocalVariableDeclaration node, java.lang.Object data) {
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        int last = node.jjtGetNumChildren();
        org.acm.seguin.parser.Node typeNode = node.jjtGetChild(0);
        for (int ndx = 1; ndx < last; ndx++) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("comma." + (ndx - 1)), printData));
            // Indent this type of statement
            if (printData.isVariablesAlignWithBlock()) {
                printData.decrIndent();
                printData.indent();
            } else {
                printData.indent();
            }
            // Print the final token
            int current = 0;
            if (node.isUsingFinal()) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("final"), printData));
                printData.appendKeyword("final");
                printData.space();
                current = 6;
            }
            if (printData.isDynamicFieldSpacing(false)) {
                org.acm.seguin.pretty.FieldSize size = printData.topFieldSize();
                int maxModifier = size.getModifierLength();
                for (int modifierIndex = current; modifierIndex < maxModifier; modifierIndex++) {
                    printData.space();
                }
            }
            // Print the type again
            typeNode.jjtAccept(this, data);
            printData.space();
            if (printData.isVariablesAlignWithBlock()) {
                printData.incrIndent();
            }
            if (printData.isDynamicFieldSpacing(false)) {
                current = lvla.computeTypeLength(node);
                org.acm.seguin.pretty.FieldSize size = printData.topFieldSize();
                int maxType = size.getTypeLength();
                for (int typeIndex = current; typeIndex < maxType; typeIndex++) {
                    printData.space();
                }
            }
            if (printData.getFieldSpaceCode() == org.acm.seguin.pretty.PrintData.DFS_ALIGN_EQUALS) {
                printData.setTempEqualsLength(lvla.computeEqualsLength(node));
            }
            // Visit the child
            node.jjtGetChild(ndx).jjtAccept(this, data);
            // End the variable declaration
            printData.appendText(";");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print the name of the node
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTStatementExpression node, java.lang.Object data) {
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        if (node.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTPrimaryExpression) {
            int last = node.jjtGetNumChildren();
            node.jjtGetChild(0).jjtAccept(this, data);
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
            printData.appendText(node.getName());
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
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTSwitchStatement node, java.lang.Object data) {
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Switch
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("switch"), printData));
        printData.appendKeyword("switch");
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("beginExpr"), printData));
        printData.space();
        printData.beginExpression(true);
        node.jjtGetChild(0).jjtAccept(this, data);
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("endExpr"), printData));
        printData.endExpression(true);
        // Start the block
        printData.beginBlock();
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("beginBlock"), printData, false));
        // Traverse the children
        int last = node.jjtGetNumChildren();
        for (int ndx = 1; ndx < last; ndx++) {
            org.acm.seguin.parser.Node next = node.jjtGetChild(ndx);
            if (next instanceof org.acm.seguin.parser.ast.ASTBlockStatement) {
                boolean indent = shouldIndentSwitchBody(next);
                if (indent) {
                    printData.incrIndent();
                    next.jjtAccept(this, data);
                    printData.decrIndent();
                } else {
                    org.acm.seguin.parser.Node child = next.jjtGetChild(0);
                    org.acm.seguin.parser.Node grandchild = child.jjtGetChild(0);
                    printData.indent();
                    blockProcess(((org.acm.seguin.parser.ast.ASTBlock) (grandchild)), printData, true, false);
                }
            } else {
                next.jjtAccept(this, data);
            }
        }
        // End the block
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("endBlock"), printData));
        printData.endBlock();
        // Return the data
        return data;
    }

    /**
     * Reformats the case XXX: portion of a switch statement
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTSwitchLabel node, java.lang.Object data) {
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Determine if the node has children
        if (node.hasAnyChildren()) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
            printData.indent();
            printData.appendKeyword("case");
            printData.space();
            node.childrenAccept(this, data);
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("colon"), printData));
            printData.appendText(":");
            printData.newline();
        } else {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("colon"), printData));
            printData.indent();
            printData.appendKeyword("default");
            printData.appendText(":");
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTIfStatement node, java.lang.Object data) {
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Determine if the node has children
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("if"), printData));
        printData.appendKeyword("if");
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("beginExpr"), printData));
        if (printData.isSpaceAfterKeyword()) {
            printData.space();
        }
        printData.beginExpression(true);
        node.jjtGetChild(0).jjtAccept(this, data);
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("endExpr"), printData));
        printData.endExpression(true);
        // Determine if the then contains a block
        boolean hasElse = node.jjtGetNumChildren() == 3;
        if (node.jjtGetNumChildren() >= 2) {
            forceBlock(node.jjtGetChild(1), printData, (!hasElse) && printData.isElseOnNewLine());
        }
        // Determine if the else part
        if (hasElse) {
            boolean shouldIndent = isShouldIndentBeforeElse(printData, node);
            if (shouldIndent) {
                printData.indent();
            } else {
                printData.space();
            }
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("else"), printData, shouldIndent));
            printData.appendKeyword("else");
            // Determine if the next item is a statement
            org.acm.seguin.parser.ast.ASTStatement child = ((org.acm.seguin.parser.ast.ASTStatement) (node.jjtGetChild(2)));
            org.acm.seguin.parser.Node next = child.jjtGetChild(0);
            if (next instanceof org.acm.seguin.parser.ast.ASTIfStatement) {
                printData.space();
                next.jjtAccept(this, data);
            } else {
                forceBlock(child, printData);
            }
        }
        // Return the data
        return data;
    }

    /**
     * The rules to determine if we should indent before the block
     * are a little more complex if we are allowed not to force
     * a block statement around the body of the then part.
     */
    private boolean isShouldIndentBeforeElse(org.acm.seguin.pretty.PrintData printData, org.acm.seguin.parser.ast.ASTIfStatement node) {
        if (printData.isElseOnNewLine())
            return true;

        if (printData.isForcingBlock() || (node.jjtGetChild(1) instanceof org.acm.seguin.parser.ast.ASTBlock)) {
            return false;
        }
        return true;
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Determine if the node has children
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("while"), printData));
        printData.appendKeyword("while");
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("beginExpr"), printData));
        if (printData.isSpaceAfterKeyword()) {
            printData.space();
        }
        printData.beginExpression(true);
        node.jjtGetChild(0).jjtAccept(this, data);
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("endExpr"), printData));
        printData.endExpression(true);
        // Process the block
        forceBlock(node.jjtGetChild(1), printData);
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Begin the do block
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("do"), printData));
        printData.appendKeyword("do");
        // Process the block
        forceBlock(node.jjtGetChild(0), printData, false);
        // Process the while block
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("while"), printData, false));
        if (printData.isSpaceAfterKeyword()) {
            printData.space();
        }
        printData.appendKeyword("while");
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("beginExpr"), printData, false));
        printData.space();
        printData.beginExpression(true);
        node.jjtGetChild(1).jjtAccept(this, data);
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("endExpr"), printData));
        printData.endExpression(true);
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
        printData.appendText(";");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Start the for loop
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("for"), printData));
        printData.appendKeyword("for");
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("beginExpr"), printData));
        if (printData.isSpaceAfterKeyword()) {
            printData.space();
        }
        printData.beginExpression(node.hasAnyChildren());
        // Traverse the children
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
        int index = 1;
        if (next instanceof org.acm.seguin.parser.ast.ASTForInit) {
            printData.setSkipNameSpacing(true);
            next.jjtAccept(this, data);
            printData.setSkipNameSpacing(false);
            next = node.jjtGetChild(index);
            index++;
        }
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("init"), printData));
        printData.appendText("; ");
        if (next instanceof org.acm.seguin.parser.ast.ASTExpression) {
            next.jjtAccept(this, data);
            next = node.jjtGetChild(index);
            index++;
        }
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("test"), printData));
        printData.appendText("; ");
        if (next instanceof org.acm.seguin.parser.ast.ASTForUpdate) {
            next.jjtAccept(this, data);
            next = node.jjtGetChild(index);
            index++;
        }
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("endExpr"), printData));
        printData.endExpression(node.hasAnyChildren());
        forceBlock(next, printData);
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        int last = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            if (ndx > 0) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("comma." + (ndx - 1)), printData));
                printData.appendText(", ");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print the break statement
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("break"), printData));
        printData.appendKeyword("break");
        java.lang.String name = node.getName();
        if (!((name == null) || (name.length() == 0))) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
            printData.appendText(" " + node.getName());
        }
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
        printData.appendText(";");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print the continue statement
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("continue"), printData));
        printData.appendKeyword("continue");
        java.lang.String name = node.getName();
        if (!((name == null) || (name.length() == 0))) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("id"), printData));
            printData.appendText(" " + node.getName());
        }
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
        printData.appendText(";");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        if (node.hasAnyChildren()) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("return"), printData));
            printData.appendKeyword("return");
            printData.space();
            node.childrenAccept(this, data);
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
            printData.appendText(";");
        } else {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("return"), printData));
            printData.appendKeyword("return");
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
            printData.appendText(";");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("throw"), printData));
        printData.appendKeyword("throw");
        printData.space();
        node.childrenAccept(this, data);
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("semicolon"), printData));
        printData.appendText(";");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("synchronized"), printData));
        printData.appendKeyword("synchronized");
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("beginExpr"), printData));
        if (printData.isSpaceAfterKeyword()) {
            printData.space();
        }
        printData.beginExpression(true);
        node.jjtGetChild(0).jjtAccept(this, data);
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("endExpr"), printData));
        printData.endExpression(true);
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("try"), printData));
        printData.appendKeyword("try");
        blockProcess(((org.acm.seguin.parser.ast.ASTBlock) (node.jjtGetChild(0))), printData, printData.isCatchOnNewLine());
        // Now work with the pairs
        int last = node.jjtGetNumChildren();
        boolean paired = false;
        int catchCount = 0;
        for (int ndx = 1; ndx < last; ndx++) {
            org.acm.seguin.parser.Node next = node.jjtGetChild(ndx);
            if (next instanceof org.acm.seguin.parser.ast.ASTFormalParameter) {
                if (printData.isCatchOnNewLine()) {
                    printData.indent();
                } else {
                    printData.space();
                }
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("catch" + catchCount), printData, printData.isCatchOnNewLine()));
                printData.appendKeyword("catch");
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("beginExpr" + catchCount), printData));
                if (printData.isSpaceAfterKeyword()) {
                    printData.space();
                }
                printData.beginExpression(true);
                next.jjtAccept(this, data);
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("endExpr" + catchCount), printData));
                printData.endExpression(true);
                paired = true;
                catchCount++;
            } else {
                if (!paired) {
                    node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("finally"), printData, printData.isCatchOnNewLine()));
                    if (printData.isCatchOnNewLine()) {
                        printData.indent();
                    } else {
                        printData.space();
                    }
                    printData.appendKeyword("finally");
                }
                blockProcess(((org.acm.seguin.parser.ast.ASTBlock) (next)), printData, printData.isCatchOnNewLine());
                paired = false;
            }
        }
        if (!printData.isCatchOnNewLine()) {
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
     * @param name
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    protected java.lang.Object binaryExpression(org.acm.seguin.parser.ast.SimpleNode node, java.lang.String name, java.lang.Object data) {
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        int childCount = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < childCount; ndx++) {
            if (ndx > 0) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("operator." + (ndx - 1)), printData));
                printData.appendText((" " + name) + " ");
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Traverse the children
        int childCount = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < childCount; ndx++) {
            if (ndx > 0) {
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("operator." + (ndx - 1)), printData));
                printData.appendText((" " + names.nextElement().toString()) + " ");
            }
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
        // Return the data
        return data;
    }

    /**
     * Forces the block around this object, if this node is not already a block
     *
     * @param node
     * 		the node
     * @param printData
     * 		the print data
     */
    protected void forceBlock(org.acm.seguin.parser.Node node, org.acm.seguin.pretty.PrintData printData) {
        forceBlock(node, printData, true);
    }

    /**
     * Forces a block around this node, if it is not already a block
     *
     * @param node
     * 		the node
     * @param printData
     * 		the print data
     * @param newline
     * 		do we have a newline at the end of the block
     */
    protected void forceBlock(org.acm.seguin.parser.Node node, org.acm.seguin.pretty.PrintData printData, boolean newline) {
        if ((node.jjtGetNumChildren() > 0) && (node.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTBlock)) {
            // We know we have a block
            org.acm.seguin.parser.ast.ASTBlock child = ((org.acm.seguin.parser.ast.ASTBlock) (node.jjtGetChild(0)));
            org.acm.seguin.pretty.LocalVariableLookAhead lvla = new org.acm.seguin.pretty.LocalVariableLookAhead();
            org.acm.seguin.pretty.FieldSize size = lvla.run(child);
            size.update(printData.getDynamicFieldSpaces());
            printData.pushFieldSize(size);
            // Start the block
            printData.beginBlock();
            child.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(child.getSpecial("begin"), printData, false));
            // Accept the children
            child.childrenAccept(this, printData);
            // End the block
            child.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(child.getSpecial("end"), printData));
            printData.endBlock(newline);
            printData.popFieldSize();
        } else {
            if (printData.isForcingBlock()) {
                printData.beginBlock();
            } else {
                printData.incrIndent();
            }
            printData.indent();
            ((org.acm.seguin.parser.ast.SimpleNode) (node)).childrenAccept(this, printData);
            if (node.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTStatementExpression) {
                // Finish off the statement expression
                printData.appendText(";");
                printData.newline();
            }
            if (printData.isForcingBlock()) {
                printData.endBlock(newline);
            } else {
                printData.decrIndent();
            }
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
        // Get the data
        org.acm.seguin.pretty.PrintData printData = ((org.acm.seguin.pretty.PrintData) (data));
        // Print the final token
        if (node.isUsingFinal()) {
            node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("final"), printData));
            printData.appendKeyword("final");
            printData.space();
        }
        // Traverse the children
        int last = node.jjtGetNumChildren();
        org.acm.seguin.parser.Node typeNode = node.jjtGetChild(0);
        typeNode.jjtAccept(this, data);
        printData.space();
        for (int ndx = 1; ndx < last; ndx++) {
            if (ndx > 1) {
                // Add a comma between entries
                node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("comma." + (ndx - 1)), printData));
                printData.appendText(", ");
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
    private org.acm.seguin.parser.Token getInitialToken(org.acm.seguin.parser.ast.ASTResultType top) {
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
     * Check the initial token, and removes it from the object.
     *
     * @param top
     * 		the type
     * @return the initial token
     */
    private org.acm.seguin.parser.Token getInitialToken(org.acm.seguin.parser.ast.ASTType top) {
        if (top.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTPrimitiveType) {
            org.acm.seguin.parser.ast.ASTPrimitiveType primitiveType = ((org.acm.seguin.parser.ast.ASTPrimitiveType) (top.jjtGetChild(0)));
            org.acm.seguin.parser.Token tok = primitiveType.getSpecial("primitive");
            primitiveType.removeSpecial("primitive");
            return tok;
        } else {
            org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (top.jjtGetChild(0)));
            org.acm.seguin.parser.Token tok = name.getSpecial("id0");
            name.removeSpecial("id0");
            return tok;
        }
    }

    /**
     * Determine if the node is a field or a method in an anonymous class
     *
     * @param node
     * 		The node in question
     * @return true if the node is in an anonymous class
     */
    private boolean isInAnonymousClass(org.acm.seguin.parser.Node node) {
        return node.jjtGetParent().jjtGetParent().jjtGetParent() instanceof org.acm.seguin.parser.ast.ASTAllocationExpression;
    }

    /**
     * Determine if the node is a field or a method in an anonymous class
     *
     * @param node
     * 		The node in question
     * @return true if the node is in an anonymous class
     */
    private boolean isInInnerClass(org.acm.seguin.parser.Node node) {
        org.acm.seguin.parser.Node greatGreatGrandparent = node.jjtGetParent().jjtGetParent().jjtGetParent().jjtGetParent();
        boolean topLevelClass = greatGreatGrandparent instanceof org.acm.seguin.parser.ast.ASTClassDeclaration;
        boolean topLevelInterface = greatGreatGrandparent instanceof org.acm.seguin.parser.ast.ASTInterfaceDeclaration;
        return (!topLevelClass) && (!topLevelInterface);
    }

    /**
     * Gets the JavadocRequired attribute of the PrettyPrintVisitor object
     *
     * @param node
     * 		Description of Parameter
     * @param printData
     * 		Description of Parameter
     * @return The JavadocRequired value
     */
    private boolean isJavadocRequired(org.acm.seguin.parser.ast.SimpleNode node, org.acm.seguin.pretty.PrintData printData) {
        if (printData.isNestedClassDocumented()) {
            return node.isRequired() && (!isInAnonymousClass(node));
        } else {
            return ((node.isRequired() && (!isInAnonymousClass(node))) && (!isInInnerClass(node))) && (!isInnerClass(node));
        }
    }

    /**
     * Determine if the class is an inner class
     *
     * @param node
     * 		Description of Parameter
     * @return The InnerClass value
     */
    private boolean isInnerClass(org.acm.seguin.parser.Node node) {
        return (node instanceof org.acm.seguin.parser.ast.ASTNestedClassDeclaration) || (node instanceof org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration);
    }

    /**
     * The standard field indent
     *
     * @param printData
     * 		Description of Parameter
     */
    private void standardFieldIndent(org.acm.seguin.pretty.PrintData printData) {
        printData.space();
        if (printData.isFieldNameIndented()) {
            int currentLength = printData.getLineLength();
            int desiredLength = printData.getFieldNameIndent();
            for (int ndx = currentLength; ndx < desiredLength; ndx++) {
                printData.space();
            }
        }
    }

    /**
     * Determines if we should indent the statement that is contained in the
     *  switch statement.
     *
     * @param next
     * 		the next node
     * @return true if we should use the indent
     */
    private boolean shouldIndentSwitchBody(org.acm.seguin.parser.Node next) {
        org.acm.seguin.parser.Node child = next.jjtGetChild(0);
        if (child instanceof org.acm.seguin.parser.ast.ASTStatement) {
            org.acm.seguin.parser.Node grandchild = child.jjtGetChild(0);
            if (grandchild instanceof org.acm.seguin.parser.ast.ASTBlock) {
                return false;
            }
        }
        return true;
    }

    /**
     * Processes a block object
     *
     * @param node
     * 		Description of Parameter
     * @param printData
     * 		Description of Parameter
     * @param newline
     * 		Description of Parameter
     */
    private void blockProcess(org.acm.seguin.parser.ast.ASTBlock node, org.acm.seguin.pretty.PrintData printData, boolean newline) {
        blockProcess(node, printData, newline, true);
    }

    /**
     * Processes a block object
     *
     * @param node
     * 		Description of Parameter
     * @param printData
     * 		Description of Parameter
     * @param newline
     * 		Description of Parameter
     * @param space
     * 		Description of Parameter
     */
    private void blockProcess(org.acm.seguin.parser.ast.ASTBlock node, org.acm.seguin.pretty.PrintData printData, boolean newline, boolean space) {
        org.acm.seguin.pretty.LocalVariableLookAhead lvla = new org.acm.seguin.pretty.LocalVariableLookAhead();
        org.acm.seguin.pretty.FieldSize size = lvla.run(node);
        size.update(printData.getDynamicFieldSpaces());
        printData.pushFieldSize(size);
        // Start the block
        printData.beginBlock(space);
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("begin"), printData, false));
        // Traverse the children
        node.childrenAccept(this, printData);
        // Finish the block
        node.jjtAccept(specialTokenVisitor, new org.acm.seguin.pretty.SpecialTokenData(node.getSpecial("end"), printData));
        if (node.jjtGetParent() instanceof org.acm.seguin.parser.ast.ASTMethodDeclaration) {
            printData.methodBrace();
        }
        printData.endBlock(newline);
        printData.popFieldSize();
    }

    /**
     * Loads the header
     *
     * @param node
     * 		the compilation unit
     * @param printData
     * 		where we are printing to
     */
    private void loadHeader(org.acm.seguin.parser.ast.ASTCompilationUnit node, org.acm.seguin.pretty.PrintData printData) {
        if (node == null) {
            return;
        }
        try {
            org.acm.seguin.util.FileSettings settings = org.acm.seguin.util.FileSettings.getSettings("Refactory", "pretty");
            java.lang.String temp = settings.getString("header.1");
            /* That would throw an exception if there wasn't a header.
             So now we are free to delete things out of the child node
             */
            org.acm.seguin.parser.ast.SimpleNode child = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(0)));
            if (child == null) {
                return;
            } else if (child instanceof org.acm.seguin.parser.ast.ASTPackageDeclaration) {
                child.removeSpecial("package");
            } else if (child instanceof org.acm.seguin.parser.ast.ASTImportDeclaration) {
                child.removeSpecial("import");
            } else {
                // The first thing is a type declaration - you're on your own
                return;
            }
            int ndx = 1;
            while (true) {
                java.lang.String nextLine = settings.getString("header." + ndx);
                printData.appendComment(nextLine, org.acm.seguin.pretty.PrintData.C_STYLE_COMMENT);
                printData.newline();
                ndx++;
            } 
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
        }
    }

    /**
     * Loads the footer
     *
     * @param node
     * 		the compilation unit
     * @param printData
     * 		where we are printing to
     */
    private boolean loadFooter(org.acm.seguin.pretty.PrintData printData) {
        boolean foundSomething = false;
        try {
            org.acm.seguin.util.FileSettings settings = org.acm.seguin.util.FileSettings.getSettings("Refactory", "pretty");
            java.lang.String temp = settings.getString("footer.1");
            foundSomething = true;
            int ndx = 1;
            while (true) {
                java.lang.String nextLine = settings.getString("footer." + ndx);
                printData.appendComment(nextLine, org.acm.seguin.pretty.PrintData.C_STYLE_COMMENT);
                printData.newline();
                ndx++;
            } 
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
        }
        return foundSomething;
    }
}