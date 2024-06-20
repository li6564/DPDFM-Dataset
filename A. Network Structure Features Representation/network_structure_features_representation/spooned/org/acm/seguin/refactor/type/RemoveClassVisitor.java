package org.acm.seguin.refactor.type;
/**
 * The visitor object for removing a class from the system.
 *
 * @author Chris Seguin
 */
public class RemoveClassVisitor extends org.acm.seguin.refactor.type.RenameClassVisitor {
    private java.lang.String parentPackage;

    private java.lang.String oldPackage;

    /**
     * Constructor for the remove class visitor object
     *
     * @param packageName
     * 		the package name
     * @param oldClass
     * 		the name of the class being deleted
     * @param newClass
     * 		the parent class of that being deleted
     * @param base
     * 		the base directory
     * @param initParentPackage
     * 		Description of Parameter
     * @param complex
     * 		Description of Parameter
     */
    public RemoveClassVisitor(java.lang.String packageName, java.lang.String oldClass, java.lang.String initParentPackage, java.lang.String newClass, java.io.File base, org.acm.seguin.refactor.ComplexTransform complex) {
        super(packageName, oldClass, newClass, base, complex);
        parentPackage = initParentPackage;
        oldPackage = packageName;
    }

    /**
     * Gets the New Imports transform
     *
     * @param node
     * 		the file summary
     * @param className
     * 		the name of the class that is changing
     * @return The NewImports value
     */
    protected org.acm.seguin.refactor.AddImportTransform getNewImports(org.acm.seguin.summary.FileSummary node, java.lang.String className) {
        if (newClassName.equals("Object")) {
            return null;
        } else if (org.acm.seguin.summary.query.GetTypeSummary.query(node, newClassName) == null) {
            return new org.acm.seguin.refactor.AddImportTransform(parentPackage, newClassName);
        } else {
            return null;
        }
    }

    /**
     * Gets the new name
     *
     * @return an ASTName containing the new name
     */
    protected org.acm.seguin.parser.ast.ASTName getNewName() {
        if (newClassName.equals("Object")) {
            org.acm.seguin.parser.ast.ASTName result = new org.acm.seguin.parser.ast.ASTName(0);
            result.addNamePart(newClassName);
            return result;
        }
        if (oldPackage.equals(parentPackage)) {
            return super.getNewName();
        }
        org.acm.seguin.parser.ast.ASTName result = new org.acm.seguin.parser.ast.ASTName(0);
        result.fromString(parentPackage);
        result.addNamePart(newClassName);
        return result;
    }

    /**
     * We are performing the transformation on a refactoring that already has
     *  that type imported from another class
     *
     * @param refactoring
     * 		the complex transformation
     * @param oldOne
     * 		the old class name
     * @param node
     * 		the file that is being changed
     * @param importedType
     * 		the type that we are supposedly importing
     */
    protected void alreadyImportsType(org.acm.seguin.refactor.ComplexTransform refactoring, org.acm.seguin.parser.ast.ASTName oldOne, org.acm.seguin.summary.FileSummary node, org.acm.seguin.summary.TypeSummary importedType) {
        if (isSamePackage(node, importedType) || isParent(importedType)) {
            org.acm.seguin.parser.ast.ASTName newOne = new org.acm.seguin.parser.ast.ASTName(0);
            newOne.addNamePart(newClassName);
            refactoring.add(new org.acm.seguin.refactor.type.RenameTypeTransform(oldOne, newOne, null));
        } else {
            refactoring.add(new org.acm.seguin.refactor.type.RenameTypeTransform(oldOne, getNewName(), null));
        }
    }

    /**
     * Gets the SamePackage attribute of the RemoveClassVisitor object
     *
     * @param fileSummary
     * 		Description of Parameter
     * @param typeSummary
     * 		Description of Parameter
     * @return The SamePackage value
     */
    private boolean isSamePackage(org.acm.seguin.summary.FileSummary fileSummary, org.acm.seguin.summary.TypeSummary typeSummary) {
        org.acm.seguin.summary.Summary one = fileSummary;
        org.acm.seguin.summary.Summary two = typeSummary;
        while (!(one instanceof org.acm.seguin.summary.PackageSummary)) {
            one = one.getParent();
        } 
        while (!(two instanceof org.acm.seguin.summary.PackageSummary)) {
            two = two.getParent();
        } 
        return one.equals(two);
    }

    /**
     * Gets the Parent attribute of the RemoveClassVisitor object
     *
     * @param typeSummary
     * 		Description of Parameter
     * @return The Parent value
     */
    private boolean isParent(org.acm.seguin.summary.TypeSummary typeSummary) {
        org.acm.seguin.summary.Summary one = typeSummary;
        while (!(one instanceof org.acm.seguin.summary.PackageSummary)) {
            one = one.getParent();
        } 
        org.acm.seguin.summary.PackageSummary packageSummary = ((org.acm.seguin.summary.PackageSummary) (one));
        java.lang.String packageName = packageSummary.getName();
        return packageName.equals(parentPackage);
    }
}