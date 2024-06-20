package org.acm.seguin.refactor.type;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
public class RenameParentVisitor extends org.acm.seguin.parser.ChildrenVisitor {
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
        org.acm.seguin.refactor.type.RenameTypeData rtd = ((org.acm.seguin.refactor.type.RenameTypeData) (data));
        java.lang.String oldName = rtd.getOld().getName();
        if (oldName.equals(node.getName())) {
            if (node.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTName) {
                node.jjtAddChild(rtd.getNew(), 0);
            } else {
                node.jjtInsertChild(rtd.getNew(), 0);
            }
        }
        return data;
    }
}