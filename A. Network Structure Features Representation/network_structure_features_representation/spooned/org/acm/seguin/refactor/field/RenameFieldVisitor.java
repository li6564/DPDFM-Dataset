/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.field;
/**
 * Visitor that traverses an AST and removes a specified field
 *
 * @author Chris Seguin
 */
public class RenameFieldVisitor extends org.acm.seguin.parser.ChildrenVisitor {
    /**
     * Visit a package declaration
     *
     * @param node
     * 		the class body node
     * @param data
     * 		the data for the visitor
     * @return the field if it is found
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPackageDeclaration node, java.lang.Object data) {
        org.acm.seguin.refactor.field.RenameFieldData rfd = ((org.acm.seguin.refactor.field.RenameFieldData) (data));
        org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (node.jjtGetChild(0)));
        org.acm.seguin.summary.PackageSummary packageSummary = org.acm.seguin.summary.PackageSummary.getPackageSummary(name.getName());
        rfd.setCurrentSummary(packageSummary);
        return super.visit(node, data);
    }

    /**
     * Visit a class declaration
     *
     * @param node
     * 		the class body node
     * @param data
     * 		the data for the visitor
     * @return the field if it is found
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration node, java.lang.Object data) {
        org.acm.seguin.refactor.field.RenameFieldData rfd = ((org.acm.seguin.refactor.field.RenameFieldData) (data));
        org.acm.seguin.summary.Summary current = rfd.getCurrentSummary();
        if (current == null) {
            rfd.setCurrentSummary(org.acm.seguin.summary.query.GetTypeSummary.query("", node.getName()));
        } else if (current instanceof org.acm.seguin.summary.PackageSummary) {
            rfd.setCurrentSummary(org.acm.seguin.summary.query.GetTypeSummary.query(((org.acm.seguin.summary.PackageSummary) (current)), node.getName()));
        } else if (current instanceof org.acm.seguin.summary.TypeSummary) {
            rfd.setCurrentSummary(org.acm.seguin.summary.query.GetTypeSummary.query(((org.acm.seguin.summary.TypeSummary) (current)), node.getName()));
        } else if (current instanceof org.acm.seguin.summary.MethodSummary) {
            rfd.setCurrentSummary(org.acm.seguin.summary.query.GetTypeSummary.query(((org.acm.seguin.summary.MethodSummary) (current)), node.getName()));
        }
        java.lang.Object result = super.visit(node, data);
        rfd.setCurrentSummary(current);
        return result;
    }

    /**
     * Visit a class declaration
     *
     * @param node
     * 		the class body node
     * @param data
     * 		the data for the visitor
     * @return the field if it is found
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration node, java.lang.Object data) {
        org.acm.seguin.refactor.field.RenameFieldData rfd = ((org.acm.seguin.refactor.field.RenameFieldData) (data));
        org.acm.seguin.summary.Summary current = rfd.getCurrentSummary();
        if (current == null) {
            rfd.setCurrentSummary(org.acm.seguin.summary.query.GetTypeSummary.query("", node.getName()));
        } else if (current instanceof org.acm.seguin.summary.PackageSummary) {
            rfd.setCurrentSummary(org.acm.seguin.summary.query.GetTypeSummary.query(((org.acm.seguin.summary.PackageSummary) (current)), node.getName()));
        } else if (current instanceof org.acm.seguin.summary.TypeSummary) {
            rfd.setCurrentSummary(org.acm.seguin.summary.query.GetTypeSummary.query(((org.acm.seguin.summary.TypeSummary) (current)), node.getName()));
        } else if (current instanceof org.acm.seguin.summary.MethodSummary) {
            rfd.setCurrentSummary(org.acm.seguin.summary.query.GetTypeSummary.query(((org.acm.seguin.summary.MethodSummary) (current)), node.getName()));
        }
        java.lang.Object result = super.visit(node, data);
        rfd.setCurrentSummary(current);
        return result;
    }

    /**
     * Visit a field declaration
     *
     * @param node
     * 		the class body node
     * @param data
     * 		the data for the visitor
     * @return the field if it is found
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTFieldDeclaration node, java.lang.Object data) {
        org.acm.seguin.refactor.field.RenameFieldData rfd = ((org.acm.seguin.refactor.field.RenameFieldData) (data));
        if (rfd.getCurrentSummary() == rfd.getTypeSummary()) {
            for (int ndx = 1; ndx < node.jjtGetNumChildren(); ndx++) {
                org.acm.seguin.parser.ast.ASTVariableDeclarator next = ((org.acm.seguin.parser.ast.ASTVariableDeclarator) (node.jjtGetChild(ndx)));
                org.acm.seguin.parser.ast.ASTVariableDeclaratorId id = ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (next.jjtGetChild(0)));
                if (id.getName().equals(rfd.getOldName())) {
                    id.setName(rfd.getNewName());
                }
            }
        }
        return super.visit(node, data);
    }

    /**
     * Visit a primary expression
     *
     * @param node
     * 		the class body node
     * @param data
     * 		the data for the visitor
     * @return the field if it is found
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPrimaryExpression node, java.lang.Object data) {
        org.acm.seguin.refactor.field.RenameFieldData rfd = ((org.acm.seguin.refactor.field.RenameFieldData) (data));
        org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix = ((org.acm.seguin.parser.ast.ASTPrimaryPrefix) (node.jjtGetChild(0)));
        if ("this".equals(prefix.getName())) {
            processThisExpression(rfd, node, prefix);
        } else if ((prefix.jjtGetNumChildren() >= 1) && (prefix.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTName)) {
            processNameExpression(rfd, node, prefix);
        }
        return super.visit(node, data);
    }

    /**
     * Visit a method declaration
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMethodDeclaration node, java.lang.Object data) {
        org.acm.seguin.refactor.field.RenameFieldData rfd = ((org.acm.seguin.refactor.field.RenameFieldData) (data));
        org.acm.seguin.summary.Summary current = rfd.getCurrentSummary();
        org.acm.seguin.summary.MethodSummary found = org.acm.seguin.summary.query.GetMethodSummary.query(((org.acm.seguin.summary.TypeSummary) (current)), node);
        rfd.setCurrentSummary(found);
        rfd.setMustInsertThis(isAlreadyPresent(found, rfd.getNewName()));
        boolean thisRequired = org.acm.seguin.summary.query.LookupVariable.getLocal(found, rfd.getOldName()) != null;
        rfd.setThisRequired(thisRequired);
        java.lang.Object result = super.visit(node, data);
        rfd.setThisRequired(false);
        rfd.setCurrentSummary(current);
        return result;
    }

    /**
     * Visit a constructor declaration
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTConstructorDeclaration node, java.lang.Object data) {
        org.acm.seguin.refactor.field.RenameFieldData rfd = ((org.acm.seguin.refactor.field.RenameFieldData) (data));
        org.acm.seguin.summary.Summary current = rfd.getCurrentSummary();
        org.acm.seguin.summary.MethodSummary found = org.acm.seguin.summary.query.GetMethodSummary.query(((org.acm.seguin.summary.TypeSummary) (current)), node);
        rfd.setCurrentSummary(found);
        rfd.setMustInsertThis(isAlreadyPresent(found, rfd.getNewName()));
        boolean thisRequired = org.acm.seguin.summary.query.LookupVariable.getLocal(found, rfd.getOldName()) != null;
        rfd.setThisRequired(thisRequired);
        java.lang.Object result = super.visit(node, data);
        rfd.setThisRequired(false);
        rfd.setCurrentSummary(current);
        return result;
    }

    /**
     * Determine if the new name is already present in the method
     *
     * @param method
     * 		Description of Parameter
     * @param newName
     * 		Description of Parameter
     * @return The AlreadyPresent value
     */
    private boolean isAlreadyPresent(org.acm.seguin.summary.MethodSummary method, java.lang.String newName) {
        java.util.Iterator iter = method.getParameters();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.ParameterSummary next = ((org.acm.seguin.summary.ParameterSummary) (iter.next()));
                if (next.getName().equals(newName)) {
                    return true;
                }
            } 
        }
        iter = method.getDependencies();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.Summary next = ((org.acm.seguin.summary.Summary) (iter.next()));
                if ((next instanceof org.acm.seguin.summary.LocalVariableSummary) && next.getName().equals(newName)) {
                    return true;
                }
            } 
        }
        return false;
    }

    /**
     * Description of the Method
     *
     * @param name
     * 		Description of Parameter
     * @param oldName
     * 		Description of Parameter
     * @param current
     * 		Description of Parameter
     * @param hasSuffixArguments
     * 		Description of Parameter
     * @param changingType
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private int shouldChangePart(org.acm.seguin.parser.ast.ASTName name, java.lang.String oldName, org.acm.seguin.summary.Summary current, boolean hasSuffixArguments, org.acm.seguin.summary.TypeSummary changingType) {
        int last = name.getNameSize() - 1;
        if (hasSuffixArguments) {
            last--;
        }
        int forwardTo = -1;
        for (int ndx = last; ndx >= 0; ndx--) {
            if (name.getNamePart(ndx).equals(oldName)) {
                forwardTo = ndx;
            }
        }
        if (forwardTo == (-1)) {
            return -1;
        }
        org.acm.seguin.summary.VariableSummary varSummary = org.acm.seguin.summary.query.LookupVariable.query(((org.acm.seguin.summary.MethodSummary) (current)), name.getNamePart(0));
        if (varSummary == null) {
            return -1;
        }
        org.acm.seguin.summary.TypeSummary currentType = org.acm.seguin.summary.query.GetTypeSummary.query(varSummary.getTypeDecl());
        for (int ndx = 1; ndx < forwardTo; ndx++) {
            varSummary = org.acm.seguin.summary.query.LookupVariable.queryFieldSummary(currentType, name.getNamePart(ndx));
            if (varSummary == null) {
                return -1;
            }
            currentType = org.acm.seguin.summary.query.GetTypeSummary.query(varSummary.getTypeDecl());
        }
        if (currentType == changingType) {
            return forwardTo;
        }
        return -1;
    }

    /**
     * Description of the Method
     *
     * @param rfd
     * 		Description of Parameter
     * @param node
     * 		Description of Parameter
     * @param prefix
     * 		Description of Parameter
     */
    private void processThisExpression(org.acm.seguin.refactor.field.RenameFieldData rfd, org.acm.seguin.parser.ast.ASTPrimaryExpression node, org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix) {
        if (rfd.isAllowedToChangeThis() && (node.jjtGetNumChildren() >= 2)) {
            org.acm.seguin.parser.ast.ASTPrimarySuffix suffix = ((org.acm.seguin.parser.ast.ASTPrimarySuffix) (node.jjtGetChild(1)));
            if (rfd.getOldName().equals(suffix.getName())) {
                boolean change = true;
                if (node.jjtGetNumChildren() >= 3) {
                    org.acm.seguin.parser.ast.ASTPrimarySuffix next = ((org.acm.seguin.parser.ast.ASTPrimarySuffix) (node.jjtGetChild(2)));
                    if ((next.jjtGetChild(0) != null) && (next.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTArguments)) {
                        change = false;
                    }
                }
                if (change) {
                    suffix.setName(rfd.getNewName());
                }
            }
        }
    }

    /**
     * Description of the Method
     *
     * @param rfd
     * 		Description of Parameter
     * @param node
     * 		Description of Parameter
     * @param prefix
     * 		Description of Parameter
     */
    private void processNameExpression(org.acm.seguin.refactor.field.RenameFieldData rfd, org.acm.seguin.parser.ast.ASTPrimaryExpression node, org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix) {
        org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (prefix.jjtGetChild(0)));
        if (!rfd.isThisRequired()) {
            boolean hasSuffixArguments = false;
            if (node.jjtGetNumChildren() >= 2) {
                org.acm.seguin.parser.ast.ASTPrimarySuffix next = ((org.acm.seguin.parser.ast.ASTPrimarySuffix) (node.jjtGetChild(1)));
                if ((next.jjtGetChild(0) != null) && (next.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTArguments)) {
                    hasSuffixArguments = true;
                }
            }
            if ((name.getNameSize() > 1) || (!hasSuffixArguments)) {
                if (rfd.isAllowedToChangeFirst() && name.getNamePart(0).equals(rfd.getOldName())) {
                    name.setNamePart(0, rfd.getNewName());
                    if (rfd.isMustInsertThis()) {
                        name.insertNamePart(0, "this");
                    }
                } else {
                    int index = shouldChangePart(name, rfd.getOldName(), rfd.getCurrentSummary(), hasSuffixArguments, rfd.getTypeSummary());
                    if (index > (-1)) {
                        name.setNamePart(index, rfd.getNewName());
                    }
                }
            }
        }
        if (rfd.getOldField().getModifiers().isStatic()) {
            java.lang.String nameString = name.getName();
            if (nameString.startsWith(rfd.getFullName())) {
                replaceNamePart(name, rfd.getFullName(), rfd.getNewName());
            } else if (nameString.startsWith(rfd.getImportedName()) && org.acm.seguin.summary.query.ImportsType.query(rfd.getCurrentSummary(), rfd.getTypeSummary())) {
                replaceNamePart(name, rfd.getImportedName(), rfd.getNewName());
            }
        }
    }

    /**
     * Description of the Method
     *
     * @param name
     * 		Description of Parameter
     * @param form
     * 		Description of Parameter
     * @param newName
     * 		Description of Parameter
     */
    private void replaceNamePart(org.acm.seguin.parser.ast.ASTName name, java.lang.String form, java.lang.String newName) {
        java.util.StringTokenizer tok = new java.util.StringTokenizer(form, ".");
        int count = -1;
        java.lang.String finalPart = null;
        while (tok.hasMoreTokens()) {
            finalPart = tok.nextToken();
            count++;
        } 
        if (name.getNamePart(count).equals(finalPart)) {
            name.setNamePart(count, newName);
        }
    }
}