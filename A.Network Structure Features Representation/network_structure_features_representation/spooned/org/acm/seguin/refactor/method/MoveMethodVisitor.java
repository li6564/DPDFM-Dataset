package org.acm.seguin.refactor.method;
/**
 * Visitor that prepares a method for being incorporated into another class.
 *
 * @author Chris Seguin
 */
public class MoveMethodVisitor extends org.acm.seguin.parser.ChildrenVisitor {
    private org.acm.seguin.summary.MethodSummary methodSummary;

    private org.acm.seguin.summary.TypeSummary typeSummary;

    private org.acm.seguin.summary.Summary destination;

    /**
     * Constructor for the MoveMethodVisitor object
     *
     * @param initType
     * 		Description of Parameter
     * @param initMethod
     * 		Description of Parameter
     * @param initDest
     * 		Description of Parameter
     */
    public MoveMethodVisitor(org.acm.seguin.summary.TypeSummary initType, org.acm.seguin.summary.MethodSummary initMethod, org.acm.seguin.summary.Summary initDest) {
        typeSummary = initType;
        methodSummary = initMethod;
        destination = initDest;
    }

    /**
     * Don't go into any class definitions
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration node, java.lang.Object data) {
        return data;
    }

    /**
     * Don't go into any interface definitions
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration node, java.lang.Object data) {
        return data;
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
        if (destination instanceof org.acm.seguin.summary.ParameterSummary) {
            java.lang.String name = destination.getName();
            int last = node.jjtGetNumChildren();
            for (int ndx = 0; ndx < last; ndx++) {
                org.acm.seguin.parser.ast.ASTFormalParameter param = ((org.acm.seguin.parser.ast.ASTFormalParameter) (node.jjtGetChild(ndx)));
                org.acm.seguin.parser.ast.ASTVariableDeclaratorId decl = ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (param.jjtGetChild(1)));
                if (decl.getName().equals(name)) {
                    node.jjtDeleteChild(ndx);
                    ndx--;
                    last--;
                }
            }
            if (org.acm.seguin.refactor.method.ObjectReference.isReferenced(methodSummary)) {
                org.acm.seguin.parser.ast.ASTFormalParameter newParam = new org.acm.seguin.parser.ast.ASTFormalParameter(0);
                org.acm.seguin.parser.ast.ASTType type = new org.acm.seguin.parser.ast.ASTType(0);
                newParam.jjtAddChild(type, 0);
                org.acm.seguin.parser.ast.ASTName nameNode = new org.acm.seguin.parser.ast.ASTName(0);
                java.lang.String typeName = typeSummary.getName();
                nameNode.addNamePart(typeName);
                type.jjtAddChild(nameNode, 0);
                org.acm.seguin.parser.ast.ASTVariableDeclaratorId id = new org.acm.seguin.parser.ast.ASTVariableDeclaratorId(0);
                id.setName(typeName.substring(0, 1).toLowerCase() + typeName.substring(1));
                newParam.jjtAddChild(id, 1);
                last = node.jjtGetNumChildren();
                node.jjtAddChild(newParam, last);
            }
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
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTExpression node, java.lang.Object data) {
        if (node.jjtGetNumChildren() > 1) {
            java.lang.Object result = super.visit(node, java.lang.Boolean.TRUE);
            if (result != null) {
                org.acm.seguin.parser.ast.ASTArguments args = ((org.acm.seguin.parser.ast.ASTArguments) (result));
                org.acm.seguin.parser.ast.ASTArgumentList list = new org.acm.seguin.parser.ast.ASTArgumentList(0);
                args.jjtAddChild(list, 0);
                list.jjtAddChild(node.jjtGetChild(2), 0);
                node.jjtDeleteChild(2);
                node.jjtDeleteChild(1);
            }
            return null;
        } else {
            return super.visit(node, java.lang.Boolean.FALSE);
        }
    }

    /**
     * Visit method for primary expressions
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPrimaryExpression node, java.lang.Object data) {
        java.lang.Object result = null;
        if (destination instanceof org.acm.seguin.summary.ParameterSummary) {
            java.lang.String parameterName = destination.getName();
            org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix = ((org.acm.seguin.parser.ast.ASTPrimaryPrefix) (node.jjtGetChild(0)));
            if ("this".equals(prefix.getName())) {
                org.acm.seguin.parser.ast.ASTName nameNode = new org.acm.seguin.parser.ast.ASTName(0);
                nameNode.addNamePart(getReplacementVariableName());
                prefix.jjtAddChild(nameNode, 0);
            } else if (isMethod(node, prefix)) {
                updatePrimaryPrefix(prefix, parameterName);
            } else if (isVariable(node, prefix)) {
                org.acm.seguin.parser.ast.ASTName nameNode = ((org.acm.seguin.parser.ast.ASTName) (prefix.jjtGetChild(0)));
                if (!isLocalVariable(nameNode.getNamePart(0))) {
                    java.lang.Boolean value = ((java.lang.Boolean) (data));
                    result = updatePrivateField(node, prefix, nameNode, parameterName, value.booleanValue());
                }
            }
        }
        super.visit(node, data);
        return result;
    }

    /**
     * Determine if we have a method
     *
     * @param node
     * 		Description of Parameter
     * @param prefix
     * 		Description of Parameter
     * @return The Method value
     */
    private boolean isMethod(org.acm.seguin.parser.ast.ASTPrimaryExpression node, org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix) {
        return ((node.jjtGetNumChildren() > 1) && (node.jjtGetChild(1).jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTArguments)) && (prefix.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTName);
    }

    /**
     * Determine if we have a field access
     *
     * @param node
     * 		Description of Parameter
     * @param prefix
     * 		Description of Parameter
     * @return The Variable value
     */
    private boolean isVariable(org.acm.seguin.parser.ast.ASTPrimaryExpression node, org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix) {
        return (node.jjtGetNumChildren() == 1) && (prefix.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTName);
    }

    /**
     * Checks if the value is a local variable
     *
     * @param name
     * 		the name of the variable
     * @return true if the name is a local variable
     */
    private boolean isLocalVariable(java.lang.String name) {
        java.util.Iterator iter = methodSummary.getDependencies();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.Summary next = ((org.acm.seguin.summary.Summary) (iter.next()));
                if (next instanceof org.acm.seguin.summary.LocalVariableSummary) {
                    org.acm.seguin.summary.LocalVariableSummary lvs = ((org.acm.seguin.summary.LocalVariableSummary) (next));
                    if (lvs.getName().equals(name)) {
                        return true;
                    }
                }
            } 
        }
        return false;
    }

    /**
     * Gets the name of the getter for the field
     *
     * @param summary
     * 		the field summary
     * @return the getter
     */
    private java.lang.String getFieldGetter(org.acm.seguin.summary.FieldSummary summary) {
        java.lang.String typeName = summary.getType();
        java.lang.String prefix = "get";
        if (typeName.equalsIgnoreCase("boolean")) {
            prefix = "is";
        }
        java.lang.String name = summary.getName();
        return (prefix + name.substring(0, 1).toUpperCase()) + name.substring(1);
    }

    /**
     * Gets the name of the setter for the field
     *
     * @param summary
     * 		the field summary
     * @return the setter
     */
    private java.lang.String getFieldSetter(org.acm.seguin.summary.FieldSummary summary) {
        java.lang.String prefix = "set";
        java.lang.String name = summary.getName();
        return (prefix + name.substring(0, 1).toUpperCase()) + name.substring(1);
    }

    /**
     * Update the primary prefix
     *
     * @param prefix
     * 		Description of Parameter
     * @param parameterName
     * 		Description of Parameter
     */
    private void updatePrimaryPrefix(org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix, java.lang.String parameterName) {
        org.acm.seguin.parser.ast.ASTName nameNode = ((org.acm.seguin.parser.ast.ASTName) (prefix.jjtGetChild(0)));
        if (nameNode.getNameSize() == 1) {
            updateLocalReferences(prefix, nameNode);
        } else if ((nameNode.getNameSize() == 2) && nameNode.getNamePart(0).equals(parameterName)) {
            updateParameterReferences(prefix, nameNode);
        }
    }

    /**
     * Updates the local references
     *
     * @param prefix
     * 		Description of Parameter
     * @param nameNode
     * 		Description of Parameter
     */
    private void updateLocalReferences(org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix, org.acm.seguin.parser.ast.ASTName nameNode) {
        org.acm.seguin.parser.ast.ASTName newName = new org.acm.seguin.parser.ast.ASTName(0);
        newName.addNamePart(getReplacementVariableName());
        newName.addNamePart(nameNode.getNamePart(0));
        prefix.jjtAddChild(newName, 0);
    }

    /**
     * Gets the variable name that is replacing "this" in the method
     *
     * @return the name of the variable
     */
    private java.lang.String getReplacementVariableName() {
        java.lang.String typeName = typeSummary.getName();
        return typeName.substring(0, 1).toLowerCase() + typeName.substring(1);
    }

    /**
     * Updates references to the parameter
     *
     * @param prefix
     * 		Description of Parameter
     * @param nameNode
     * 		Description of Parameter
     */
    private void updateParameterReferences(org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix, org.acm.seguin.parser.ast.ASTName nameNode) {
        org.acm.seguin.parser.ast.ASTName newName = new org.acm.seguin.parser.ast.ASTName(0);
        newName.addNamePart(nameNode.getNamePart(1));
        prefix.jjtAddChild(newName, 0);
    }

    /**
     * Updates a private field
     *
     * @param primary
     * 		Description of Parameter
     * @param prefix
     * 		Description of Parameter
     * @param nameNode
     * 		Description of Parameter
     * @param parameterName
     * 		Description of Parameter
     * @param isSetter
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private java.lang.Object updatePrivateField(org.acm.seguin.parser.ast.ASTPrimaryExpression primary, org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix, org.acm.seguin.parser.ast.ASTName nameNode, java.lang.String parameterName, boolean isSetter) {
        if (nameNode.getNameSize() == 1) {
            java.lang.String name = nameNode.getNamePart(0);
            org.acm.seguin.summary.FieldSummary field = org.acm.seguin.summary.query.FieldQuery.find(typeSummary, name);
            if (field.getModifiers().isPrivate()) {
                org.acm.seguin.parser.ast.ASTName newName = new org.acm.seguin.parser.ast.ASTName(0);
                newName.addNamePart(getReplacementVariableName());
                newName.addNamePart(isSetter ? getFieldSetter(field) : getFieldGetter(field));
                prefix.jjtAddChild(newName, 0);
                org.acm.seguin.parser.ast.ASTPrimarySuffix suffix = new org.acm.seguin.parser.ast.ASTPrimarySuffix(0);
                org.acm.seguin.parser.ast.ASTArguments args = new org.acm.seguin.parser.ast.ASTArguments(0);
                suffix.jjtAddChild(args, 0);
                primary.jjtInsertChild(suffix, 1);
                return args;
            }
        }
        updatePrimaryPrefix(prefix, parameterName);
        return null;
    }
}