package org.acm.seguin.refactor.type;
/**
 * Renames a class from one name to another.
 *
 * @author Chris Seguin
 */
public class RenameClassVisitor extends org.acm.seguin.refactor.type.TypeChangeVisitor {
    // Instance Variables
    /**
     * Description of the Field
     */
    protected java.lang.String packageName;

    /**
     * Description of the Field
     */
    protected java.lang.String oldClassName;

    /**
     * Description of the Field
     */
    protected java.lang.String newClassName;

    private java.io.File base;

    private java.io.File targetFile;

    /**
     * Determine if anything in this tree references these classes.
     *
     * @param base
     * 		the base directory
     * @param packageName
     * 		Description of Parameter
     * @param oldClass
     * 		Description of Parameter
     * @param newClass
     * 		Description of Parameter
     * @param complex
     * 		Description of Parameter
     */
    public RenameClassVisitor(java.lang.String packageName, java.lang.String oldClass, java.lang.String newClass, java.io.File base, org.acm.seguin.refactor.ComplexTransform complex) {
        super(complex);
        this.packageName = packageName;
        this.base = base;
        oldClassName = oldClass;
        newClassName = newClass;
    }

    /**
     * Gets the File Specific Transform
     *
     * @param summary
     * 		Gets a file specific transform
     * @return The FileSpecificTransform value
     */
    protected org.acm.seguin.refactor.TransformAST getFileSpecificTransform(org.acm.seguin.summary.FileSummary summary) {
        if (isRenamingTarget(summary)) {
            org.acm.seguin.parser.ast.ASTName oldName = new org.acm.seguin.parser.ast.ASTName(0);
            oldName.fromString(oldClassName);
            org.acm.seguin.parser.ast.ASTName newName = new org.acm.seguin.parser.ast.ASTName(0);
            newName.fromString(newClassName);
            return new org.acm.seguin.refactor.type.RenameTypeTransform(oldName, newName, org.acm.seguin.summary.query.GetTypeSummary.query(packageName, oldClassName));
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
        if (org.acm.seguin.summary.query.GetTypeSummary.query(node, newClassName) == null) {
            return new org.acm.seguin.refactor.AddImportTransform(packageName, newClassName);
        } else {
            return null;
        }
    }

    /**
     * Gets the Remove Imports transform
     *
     * @param node
     * 		the import summary
     * @return The transform
     */
    protected org.acm.seguin.refactor.RemoveImportTransform getRemoveImportTransform(org.acm.seguin.summary.ImportSummary node) {
        return new org.acm.seguin.refactor.RemoveImportTransform(packageName, oldClassName);
    }

    /**
     * Gets the AppropriateClasses attribute of the TypeChangeVisitor object
     *
     * @param node
     * 		Description of Parameter
     * @return The AppropriateClasses value
     */
    protected java.util.LinkedList getAppropriateClasses(org.acm.seguin.summary.FileSummary node) {
        java.util.LinkedList result = new java.util.LinkedList();
        result.add(oldClassName);
        return result;
    }

    /**
     * Gets the reference to the file where the refactored output should be sent
     *
     * @param node
     * 		the files summary
     * @return The NewFile value
     */
    protected java.io.File getNewFile(org.acm.seguin.summary.FileSummary node) {
        java.io.File current = base;
        java.util.StringTokenizer tok = new java.util.StringTokenizer(packageName, ".");
        while (tok.hasMoreTokens()) {
            current = new java.io.File(current, tok.nextToken());
        } 
        java.io.File input = new java.io.File(current, oldClassName + ".java");
        if (checkFiles(input, node.getFile())) {
            java.io.File result = new java.io.File(current, newClassName + ".java");
            return result;
        } else {
            return node.getFile();
        }
    }

    /**
     * Return the current package
     *
     * @return the current package of the class
     */
    protected java.lang.String getCurrentPackage() {
        return packageName;
    }

    /**
     * Gets the new name
     *
     * @return an ASTName containing the new name
     */
    protected org.acm.seguin.parser.ast.ASTName getNewName() {
        org.acm.seguin.parser.ast.ASTName result = new org.acm.seguin.parser.ast.ASTName(0);
        result.fromString(packageName);
        result.addNamePart(newClassName);
        return result;
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
        org.acm.seguin.parser.ast.ASTName oldOne = new org.acm.seguin.parser.ast.ASTName(0);
        oldOne.addNamePart(oldClassName);
        org.acm.seguin.summary.TypeSummary importedType = org.acm.seguin.summary.query.GetTypeSummary.query(node, newClassName);
        if ((importedType != null) && isRenamingTarget(node)) {
            renameRefactoringTarget(refactoring, node, className, oldOne, importedType);
        } else if (importedType != null) {
            alreadyImportsType(refactoring, oldOne, node, importedType);
        } else {
            simpleRename(refactoring, oldOne);
        }
        refactoring.add(new org.acm.seguin.refactor.type.RenameTypeTransform(getOldName(), getNewName(), org.acm.seguin.summary.query.GetTypeSummary.query(packageName, oldClassName)));
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
        refactoring.add(new org.acm.seguin.refactor.type.RenameTypeTransform(oldOne, getNewName(), org.acm.seguin.summary.query.GetTypeSummary.query(packageName, oldClassName)));
    }

    /**
     * Checks to see if this is the target
     *
     * @param summary
     * 		the file summary
     * @return true if this file summary represents the file that is
    being renamed
     */
    private boolean isRenamingTarget(org.acm.seguin.summary.FileSummary summary) {
        if (targetFile == null) {
            java.io.File current = base;
            java.util.StringTokenizer tok = new java.util.StringTokenizer(packageName, ".");
            while (tok.hasMoreTokens()) {
                current = new java.io.File(current, tok.nextToken());
            } 
            targetFile = new java.io.File(current, oldClassName + ".java");
        }
        return checkFiles(targetFile, summary.getFile());
    }

    /**
     * Creates a name from a type summary
     *
     * @param summary
     * 		the type summary
     * @return the name
     */
    private org.acm.seguin.parser.ast.ASTName getImport(org.acm.seguin.summary.TypeSummary summary) {
        org.acm.seguin.parser.ast.ASTName name = new org.acm.seguin.parser.ast.ASTName(0);
        org.acm.seguin.summary.Summary current = summary.getParent();
        while (!(current instanceof org.acm.seguin.summary.PackageSummary)) {
            current = current.getParent();
        } 
        name.fromString(((org.acm.seguin.summary.PackageSummary) (current)).getName());
        name.addNamePart(summary.getName());
        return name;
    }

    /**
     * Gets the old name
     *
     * @return an ASTName containing the old name
     */
    private org.acm.seguin.parser.ast.ASTName getOldName() {
        org.acm.seguin.parser.ast.ASTName result = new org.acm.seguin.parser.ast.ASTName(0);
        result.fromString(packageName);
        result.addNamePart(oldClassName);
        return result;
    }

    /**
     * This file does not import the type, so the rename operation is simple
     *
     * @param refactoring
     * 		the complex transformation
     * @param oldOne
     * 		the old one
     */
    private void simpleRename(org.acm.seguin.refactor.ComplexTransform refactoring, org.acm.seguin.parser.ast.ASTName oldOne) {
        org.acm.seguin.parser.ast.ASTName newOne = new org.acm.seguin.parser.ast.ASTName(0);
        newOne.addNamePart(newClassName);
        refactoring.add(new org.acm.seguin.refactor.type.RenameTypeTransform(oldOne, newOne, org.acm.seguin.summary.query.GetTypeSummary.query(packageName, oldClassName)));
    }

    /**
     * We are attempting to perform the rename operation on the targeted file.
     *  This change is complex because the file already imported another class
     *  with the same name
     *
     * @param refactoring
     * 		the complex transformation
     * @param node
     * 		the node
     * @param className
     * 		the new name
     * @param oldOne
     * 		the old class
     * @param importedType
     * 		the type that was imported that has the same name
     */
    private void renameRefactoringTarget(org.acm.seguin.refactor.ComplexTransform refactoring, org.acm.seguin.summary.FileSummary node, java.lang.String className, org.acm.seguin.parser.ast.ASTName oldOne, org.acm.seguin.summary.TypeSummary importedType) {
        org.acm.seguin.parser.ast.ASTName newOne = new org.acm.seguin.parser.ast.ASTName(0);
        newOne.addNamePart(newClassName);
        org.acm.seguin.parser.ast.ASTName importedTypeName = getImport(importedType);
        refactoring.add(new org.acm.seguin.refactor.type.RenameTypeTransform(newOne, importedTypeName, org.acm.seguin.summary.query.GetTypeSummary.query(packageName, oldClassName)));
        refactoring.add(new org.acm.seguin.refactor.RemoveImportTransform(importedTypeName));
        refactoring.add(new org.acm.seguin.refactor.type.RenameTypeTransform(oldOne, newOne, org.acm.seguin.summary.query.GetTypeSummary.query(packageName, oldClassName)));
    }

    /**
     * Description of the Method
     *
     * @param file1
     * 		Description of Parameter
     * @param file2
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private boolean checkFiles(java.io.File file1, java.io.File file2) {
        try {
            java.lang.String one = file1.getCanonicalPath();
            java.lang.String two = file2.getCanonicalPath();
            return one.equals(two);
        } catch (java.io.IOException ioe) {
            return false;
        }
    }
}