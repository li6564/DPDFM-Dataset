/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.type;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Removes a particular class that is an abstract parent.
 *
 * @author Chris Seguin
 */
public class RemoveEmptyClassRefactoring extends org.acm.seguin.refactor.Refactoring {
    private org.acm.seguin.summary.TypeSummary typeSummary;

    private java.io.File base;

    /**
     * Constructor for the RemoveEmptyClassRefactoring object
     */
    protected RemoveEmptyClassRefactoring() {
    }

    /**
     * Sets the ChildClass attribute of the RemoveAbstractParent object
     *
     * @param packageName
     * 		The new Class value
     * @param className
     * 		The new Class value
     */
    public void setClass(java.lang.String packageName, java.lang.String className) {
        setClass(org.acm.seguin.summary.query.GetTypeSummary.query(org.acm.seguin.summary.PackageSummary.getPackageSummary(packageName), className));
    }

    /**
     * Sets the ChildClass attribute of the RemoveAbstractParent object
     *
     * @param summary
     * 		The new Class value
     */
    public void setClass(org.acm.seguin.summary.TypeSummary summary) {
        typeSummary = summary;
    }

    /**
     * Gets the description of the refactoring
     *
     * @return the description
     */
    public java.lang.String getDescription() {
        return "Removes the class named " + typeSummary.getName();
    }

    /**
     * Gets the id for this refactoring to track which refactorings are used.
     *
     * @return the id
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.REMOVE_CLASS;
    }

    /**
     * Gets the FileSummary attribute of the RemoveEmptyClassRefactoring object
     *
     * @return The FileSummary value
     */
    protected org.acm.seguin.summary.FileSummary getFileSummary() {
        org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (typeSummary.getParent()));
        return fileSummary;
    }

    /**
     * Checks the preconditions that must be true for this refactoring to be
     *  applied.
     *
     * @exception RefactoringException
     * 		The exception
     */
    protected void preconditions() throws org.acm.seguin.refactor.RefactoringException {
        if (typeSummary == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No type specified");
        }
        org.acm.seguin.summary.TypeDeclSummary parentDecl = typeSummary.getParentClass();
        org.acm.seguin.summary.TypeSummary parentSummary;
        if (parentDecl == null) {
            parentSummary = org.acm.seguin.summary.query.GetTypeSummary.query(org.acm.seguin.summary.PackageSummary.getPackageSummary("java.lang"), "Object");
        } else {
            parentSummary = org.acm.seguin.summary.query.GetTypeSummary.query(parentDecl);
        }
        if (parentSummary == null) {
            throw new org.acm.seguin.refactor.RefactoringException("Could not find the parent class for the specified class in the metadata");
        }
        org.acm.seguin.summary.FileSummary fileSummary = getFileSummary();
        if (fileSummary.getFile() == null) {
            throw new org.acm.seguin.refactor.RefactoringException("This type is contained in a stub.  No refactorings allowed.");
        }
        if (fileSummary.getTypeCount() != 1) {
            throw new org.acm.seguin.refactor.RefactoringException(("This refactoring works only when the " + "type is alone in a file.  Please remove other types from ") + fileSummary.getFile().getName());
        }
        if ((typeSummary.getFieldCount() > 0) || (typeSummary.getMethodCount() > 0)) {
            throw new org.acm.seguin.refactor.RefactoringException(("The " + typeSummary.getName()) + " class has at least one method or field");
        }
        // Finish the setup
        java.io.File deadFile = fileSummary.getFile();
        java.lang.String path = null;
        try {
            path = deadFile.getCanonicalPath();
        } catch (java.io.IOException ioe) {
            path = deadFile.getPath();
        }
        java.io.File startDir = new java.io.File(path).getParentFile();
        java.lang.String firstFilename = deadFile.getName();
        base = org.acm.seguin.summary.query.TopLevelDirectory.query(startDir, firstFilename);
    }

    /**
     * Performs the refactoring by traversing through the files and updating
     *  them.
     */
    protected void transform() {
        org.acm.seguin.refactor.ComplexTransform complex = getComplexTransform();
        org.acm.seguin.summary.FileSummary fileSummary = getFileSummary();
        complex.removeFile(fileSummary.getFile());
        java.lang.String srcPackage = ((org.acm.seguin.summary.PackageSummary) (fileSummary.getParent())).getName();
        java.lang.String oldClassName = typeSummary.getName();
        java.lang.String newClassName;
        org.acm.seguin.summary.TypeDeclSummary parent = typeSummary.getParentClass();
        java.lang.String destPackage;
        if (parent == null) {
            newClassName = "Object";
            destPackage = "java.lang";
        } else {
            newClassName = parent.getType();
            org.acm.seguin.summary.TypeSummary parentTypeSummary = org.acm.seguin.summary.query.GetTypeSummary.query(parent);
            org.acm.seguin.summary.Summary one = parentTypeSummary.getParent();
            while (!(one instanceof org.acm.seguin.summary.PackageSummary)) {
                one = one.getParent();
            } 
            destPackage = ((org.acm.seguin.summary.PackageSummary) (one)).getName();
        }
        org.acm.seguin.summary.FileSummary.removeFileSummary(fileSummary.getFile());
        org.acm.seguin.refactor.type.RemoveClassVisitor rcv = new org.acm.seguin.refactor.type.RemoveClassVisitor(srcPackage, oldClassName, destPackage, newClassName, base, complex);
        rcv.visit(null);
    }
}