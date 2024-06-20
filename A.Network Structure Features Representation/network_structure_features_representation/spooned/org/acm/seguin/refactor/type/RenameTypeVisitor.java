package org.acm.seguin.refactor.type;
/**
 * Scan through the abstract syntax tree and replace types with a new value.
 *
 * @author Chris Seguin
 * @created September 10, 1999
 */
public class RenameTypeVisitor extends org.acm.seguin.parser.ChildrenVisitor {
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
        if (rtd.getOld().getNameSize() == 1) {
            java.lang.String oldName = rtd.getOld().getName();
            if (oldName.equals(node.getName())) {
                java.lang.String name = rtd.getNew().getName();
                node.setName(name);
            }
        }
        int index = 0;
        if (node.jjtGetChild(index) instanceof org.acm.seguin.parser.ast.ASTName) {
            if (node.jjtGetChild(index).equals(rtd.getOld())) {
                node.jjtAddChild(rtd.getNew(), index);
            }
            index++;
        }
        if (node.jjtGetChild(index) instanceof org.acm.seguin.parser.ast.ASTNameList) {
            org.acm.seguin.parser.ast.ASTNameList namelist = ((org.acm.seguin.parser.ast.ASTNameList) (node.jjtGetChild(index)));
            for (int ndx = 0; ndx < namelist.jjtGetNumChildren(); ndx++) {
                if (namelist.jjtGetChild(ndx).equals(rtd.getOld())) {
                    namelist.jjtAddChild(rtd.getNew(), ndx);
                }
            }
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration node, java.lang.Object data) {
        org.acm.seguin.refactor.type.RenameTypeData rtd = ((org.acm.seguin.refactor.type.RenameTypeData) (data));
        if (rtd.getOld().getNameSize() == 1) {
            java.lang.String oldName = rtd.getOld().getName();
            if (oldName.equals(node.getName())) {
                java.lang.String name = rtd.getNew().getName();
                node.setName(name);
            }
        }
        int index = 0;
        if (node.jjtGetChild(index) instanceof org.acm.seguin.parser.ast.ASTNameList) {
            org.acm.seguin.parser.ast.ASTNameList namelist = ((org.acm.seguin.parser.ast.ASTNameList) (node.jjtGetChild(index)));
            for (int ndx = 0; ndx < namelist.jjtGetNumChildren(); ndx++) {
                if (namelist.jjtGetChild(ndx).equals(rtd.getOld())) {
                    namelist.jjtAddChild(rtd.getNew(), ndx);
                }
            }
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTConstructorDeclaration node, java.lang.Object data) {
        org.acm.seguin.refactor.type.RenameTypeData rtd = ((org.acm.seguin.refactor.type.RenameTypeData) (data));
        if (rtd.getOld().getNameSize() == 1) {
            java.lang.String oldName = rtd.getOld().getName();
            if (oldName.equals(node.getName())) {
                java.lang.String name = rtd.getNew().getName();
                node.setName(name);
            }
        }
        if ((node.jjtGetNumChildren() >= 2) && (node.jjtGetChild(1) instanceof org.acm.seguin.parser.ast.ASTNameList)) {
            org.acm.seguin.parser.ast.ASTNameList nameList = ((org.acm.seguin.parser.ast.ASTNameList) (node.jjtGetChild(1)));
            processExceptions(nameList, rtd);
        }
        return node.childrenAccept(this, data);
    }

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
        if ((node.jjtGetNumChildren() >= 3) && (node.jjtGetChild(2) instanceof org.acm.seguin.parser.ast.ASTNameList)) {
            org.acm.seguin.parser.ast.ASTNameList nameList = ((org.acm.seguin.parser.ast.ASTNameList) (node.jjtGetChild(2)));
            org.acm.seguin.refactor.type.RenameTypeData rtd = ((org.acm.seguin.refactor.type.RenameTypeData) (data));
            processExceptions(nameList, rtd);
        }
        return super.visit(node, data);
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
        if (node.jjtGetNumChildren() == 0) {
            return data;
        }
        if (node.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTName) {
            org.acm.seguin.parser.ast.ASTName child = ((org.acm.seguin.parser.ast.ASTName) (node.jjtGetChild(0)));
            org.acm.seguin.refactor.type.RenameTypeData rtd = ((org.acm.seguin.refactor.type.RenameTypeData) (data));
            if (child.equals(rtd.getOld())) {
                node.jjtAddChild(rtd.getNew(), 0);
            } else if (child.startsWith(rtd.getOld())) {
                node.jjtAddChild(child.changeStartingPart(rtd.getOld(), rtd.getNew()), 0);
            }
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
        if (prefix.jjtGetNumChildren() == 0) {
            return data;
        }
        if (prefix.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTName) {
            org.acm.seguin.parser.ast.ASTName child = ((org.acm.seguin.parser.ast.ASTName) (prefix.jjtGetChild(0)));
            org.acm.seguin.refactor.type.RenameTypeData rtd = ((org.acm.seguin.refactor.type.RenameTypeData) (data));
            if (child.equals(rtd.getOld())) {
                prefix.jjtAddChild(rtd.getNew(), 0);
            } else if (child.startsWith(rtd.getOld())) {
                if (isStatic(rtd, child, isMethod(node))) {
                    org.acm.seguin.parser.ast.ASTName name = child.changeStartingPart(rtd.getOld(), rtd.getNew());
                    prefix.jjtAddChild(name, 0);
                }
            }
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTAllocationExpression node, java.lang.Object data) {
        if (node.jjtGetNumChildren() == 0) {
            return data;
        }
        if (node.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTName) {
            org.acm.seguin.parser.ast.ASTName child = ((org.acm.seguin.parser.ast.ASTName) (node.jjtGetChild(0)));
            org.acm.seguin.refactor.type.RenameTypeData rtd = ((org.acm.seguin.refactor.type.RenameTypeData) (data));
            if (child.equals(rtd.getOld())) {
                node.jjtAddChild(rtd.getNew(), 0);
            } else if (child.startsWith(rtd.getOld())) {
                node.jjtAddChild(child.changeStartingPart(rtd.getOld(), rtd.getNew()), 0);
            }
        }
        return node.childrenAccept(this, data);
    }

    /**
     * Determines if the node is a method invocation
     *
     * @param node
     * 		the node in question
     * @return true when we are looking at a method
     */
    private boolean isMethod(org.acm.seguin.parser.ast.ASTPrimaryExpression node) {
        if (node.jjtGetNumChildren() <= 1) {
            return false;
        }
        org.acm.seguin.parser.ast.ASTPrimarySuffix suffix = ((org.acm.seguin.parser.ast.ASTPrimarySuffix) (node.jjtGetChild(1)));
        return suffix.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTArguments;
    }

    /**
     * Gets the Static attribute of the RenameTypeVisitor object
     *
     * @param data
     * 		Description of Parameter
     * @param name
     * 		Description of Parameter
     * @param isMethod
     * 		Description of Parameter
     * @return The Static value
     */
    private boolean isStatic(org.acm.seguin.refactor.type.RenameTypeData data, org.acm.seguin.parser.ast.ASTName name, boolean isMethod) {
        int last = name.getNameSize();
        java.lang.String value = name.getNamePart(last - 1);
        org.acm.seguin.summary.TypeSummary typeSummary = data.getTypeSummary();
        return org.acm.seguin.summary.query.ContainsStatic.query(typeSummary, value, isMethod);
    }

    /**
     * Description of the Method
     *
     * @param nameList
     * 		Description of Parameter
     * @param rtd
     * 		Description of Parameter
     */
    private void processExceptions(org.acm.seguin.parser.ast.ASTNameList nameList, org.acm.seguin.refactor.type.RenameTypeData rtd) {
        int last = nameList.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            org.acm.seguin.parser.ast.ASTName next = ((org.acm.seguin.parser.ast.ASTName) (nameList.jjtGetChild(ndx)));
            if (next.equals(rtd.getOld())) {
                nameList.jjtAddChild(rtd.getNew(), 0);
            } else if (next.startsWith(rtd.getOld())) {
                nameList.jjtAddChild(next.changeStartingPart(rtd.getOld(), rtd.getNew()), 0);
            }
        }
    }
}