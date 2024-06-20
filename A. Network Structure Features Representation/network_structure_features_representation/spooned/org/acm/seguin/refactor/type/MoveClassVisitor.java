package org.acm.seguin.refactor.type;
/**
 * Scans through the summary objects to create a list of files that reference
 *  a particular class.
 *
 * @author Chris Seguin
 */
public class MoveClassVisitor extends org.acm.seguin.refactor.type.TypeChangeVisitor {
    // Instance Variables
    private java.lang.String oldPackageName;

    private java.lang.String newPackageName;

    private java.io.File base;

    /**
     * Determine if anything in this tree references these classes.
     *
     * @param oldPackage
     * 		the name of the old package
     * @param newPackage
     * 		the name of the new package
     * @param base
     * 		the base directory
     * @param complex
     * 		Description of Parameter
     */
    public MoveClassVisitor(java.lang.String oldPackage, java.lang.String newPackage, java.io.File base, org.acm.seguin.refactor.ComplexTransform complex) {
        super(complex);
        oldPackageName = oldPackage;
        newPackageName = newPackage;
        this.base = base;
    }

    /**
     * Gets the File Specific Transform
     *
     * @param summary
     * 		Gets a file specific transform
     * @return The FileSpecificTransform value
     */
    protected org.acm.seguin.refactor.TransformAST getFileSpecificTransform(org.acm.seguin.summary.FileSummary summary) {
        if (summary.isMoving()) {
            return new org.acm.seguin.refactor.type.ChangePackageTransform(newPackageName);
        }
        return null;
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
        java.lang.String currentPackage = "";
        java.lang.String otherPackage = "";
        if (node.isMoving()) {
            currentPackage = oldPackageName;
            otherPackage = newPackageName;
        } else {
            currentPackage = newPackageName;
            otherPackage = oldPackageName;
        }
        return new org.acm.seguin.refactor.AddImportTransform(currentPackage, className);
    }

    /**
     * Gets the Remove Imports transform
     *
     * @param node
     * 		the import summary
     * @return The transform
     */
    protected org.acm.seguin.refactor.RemoveImportTransform getRemoveImportTransform(org.acm.seguin.summary.ImportSummary node) {
        if (node.getType() == null) {
            return null;
        } else {
            return new org.acm.seguin.refactor.RemoveImportTransform(oldPackageName, node.getType());
        }
    }

    /**
     * Gets the AppropriateClasses attribute of the TypeChangeVisitor object
     *
     * @param node
     * 		Description of Parameter
     * @return The AppropriateClasses value
     */
    protected java.util.LinkedList getAppropriateClasses(org.acm.seguin.summary.FileSummary node) {
        if (!node.isMoving()) {
            org.acm.seguin.summary.query.MovingTypeList mtl = new org.acm.seguin.summary.query.MovingTypeList();
            return mtl.query(oldPackageName);
        } else {
            org.acm.seguin.summary.query.StayingTypeList stl = new org.acm.seguin.summary.query.StayingTypeList();
            return stl.query(oldPackageName);
        }
    }

    /**
     * Gets the reference to the file where the refactored output should be sent
     *
     * @param node
     * 		the files summary
     * @return The NewFile value
     */
    protected java.io.File getNewFile(org.acm.seguin.summary.FileSummary node) {
        if (!node.isMoving()) {
            return node.getFile();
        }
        java.io.File current = base;
        java.util.StringTokenizer tok = new java.util.StringTokenizer(newPackageName, ".");
        while (tok.hasMoreTokens()) {
            current = new java.io.File(current, tok.nextToken());
        } 
        return new java.io.File(current, node.getName());
    }

    /**
     * Return the current package
     *
     * @return the current package of the class
     */
    protected java.lang.String getCurrentPackage() {
        return oldPackageName;
    }

    /**
     * Gets the RenamingTransform
     *
     * @param refactoring
     * 		the refactoring
     * @param node
     * 		the file summary to reference
     * @param className
     * 		the name of the class that is changing
     */
    protected void addRenamingTransforms(org.acm.seguin.refactor.ComplexTransform refactoring, org.acm.seguin.summary.FileSummary node, java.lang.String className) {
        java.lang.String currentPackage = "";
        java.lang.String otherPackage = "";
        if (node.isMoving()) {
            currentPackage = oldPackageName;
            otherPackage = newPackageName;
        } else {
            currentPackage = newPackageName;
            otherPackage = oldPackageName;
        }
        if (otherPackage.length() > 0) {
            refactoring.add(new org.acm.seguin.refactor.type.RenameTypeTransform(otherPackage, currentPackage, className));
        }
    }

    /**
     * Set the class name. Allows sub classes of this to reuse themselves for
     *  different classes in the same package.
     *
     * @param newClassName
     * 		the new class name
     */
    protected void add(java.lang.String newClassName) {
        org.acm.seguin.summary.FileSummary summary = new org.acm.seguin.summary.query.FileSummaryGetter().query(oldPackageName, newClassName);
        if (summary != null) {
            summary.setMoving(true);
        } else {
            java.lang.System.out.println((("WARNING:  Unable to find the class " + newClassName) + " in the package ") + oldPackageName);
        }
    }
}