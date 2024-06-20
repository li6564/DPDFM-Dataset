package org.acm.seguin.tools.international;
/**
 * Creates a list of strings in the directory that aren't used for internal
 *  information.
 *
 * @author Chris Seguin
 */
public class StringListVisitor extends org.acm.seguin.parser.ChildrenVisitor {
    /**
     * Prints out the literal if it is a string literal
     *
     * @param node
     * 		The node we are visiting
     * @param data
     * 		The rename type data
     * @return The rename type data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTLiteral node, java.lang.Object data) {
        java.lang.String name = node.getName();
        if ((((name != null) && (name.length() > 0)) && (name.charAt(0) == '\"')) && (!name.equals("\"\""))) {
            java.lang.System.out.println("\t" + name);
        }
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
        org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix = ((org.acm.seguin.parser.ast.ASTPrimaryPrefix) (node.jjtGetChild(0)));
        if (prefix.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTName) {
            org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (prefix.jjtGetChild(0)));
            int count = name.getNameSize();
            if (name.getNamePart(0).equals("Debug")) {
                return data;
            } else {
                java.lang.String part = name.getNamePart(count - 1);
                if (part.equals("getBundle")) {
                    return data;
                } else if (part.equals("getCachedBundle")) {
                    return data;
                } else if (part.equals("getString")) {
                    return data;
                }
            }
        }
        return node.childrenAccept(this, data);
    }
}