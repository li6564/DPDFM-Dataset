/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.type;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Main program for renaming a class.
 *
 * @author Chris Seguin
 */
public class RenameClassRefactoring extends org.acm.seguin.refactor.Refactoring {
    // Instance Variables
    private java.lang.String initDir;

    private java.lang.String oldPackage;

    private java.lang.String oldClassName;

    private java.lang.String newClassName;

    private java.lang.String srcPackage;

    private java.io.File base;

    /**
     * Constructor for repackage
     */
    protected RenameClassRefactoring() {
        initDir = java.lang.System.getProperty("user.dir");
    }

    /**
     * Set the directory
     *
     * @param dir
     * 		the initial directory
     */
    public void setDirectory(java.lang.String dir) {
        initDir = dir;
    }

    /**
     * Sets the OldClassName attribute of the RenameClass object
     *
     * @param value
     * 		The new OldClassName value
     */
    public void setOldClassName(java.lang.String value) {
        oldClassName = value;
    }

    /**
     * Sets the NewClassName attribute of the RenameClass object
     *
     * @param value
     * 		The new NewClassName value
     */
    public void setNewClassName(java.lang.String value) {
        newClassName = value;
    }

    /**
     * Gets the description of the refactoring
     *
     * @return the description
     */
    public java.lang.String getDescription() {
        return (("Renames a class from " + oldClassName) + " to ") + newClassName;
    }

    /**
     * Gets the id for this refactoring to track which refactorings are used.
     *
     * @return the id
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.RENAME_CLASS;
    }

    /**
     * Gets the file summary that we are changing
     *
     * @return The FileSummary value
     */
    protected org.acm.seguin.summary.FileSummary getFileSummary() {
        org.acm.seguin.summary.PackageSummary packageSummary = org.acm.seguin.summary.PackageSummary.getPackageSummary(srcPackage);
        org.acm.seguin.summary.TypeSummary typeSummary = org.acm.seguin.summary.query.GetTypeSummary.query(packageSummary, oldClassName);
        return ((org.acm.seguin.summary.FileSummary) (typeSummary.getParent()));
    }

    /**
     * Preconditions for the refactoring to be applied
     *
     * @exception RefactoringException
     * 		Description of Exception
     */
    protected void preconditions() throws org.acm.seguin.refactor.RefactoringException {
        if (oldClassName == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No old class specified");
        }
        if (newClassName == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No new class specified");
        }
        java.io.File startDir = new java.io.File(initDir);
        java.lang.String firstFilename = oldClassName + ".java";
        org.acm.seguin.parser.ast.ASTName srcPackageName = org.acm.seguin.parser.query.PackageNameGetter.query(startDir, firstFilename);
        srcPackage = "";
        if (srcPackageName != null) {
            srcPackage = srcPackageName.getName();
        }
        base = org.acm.seguin.summary.query.TopLevelDirectory.query(startDir, firstFilename);
        java.lang.String topLevelDir = base.getPath();
        try {
            topLevelDir = base.getCanonicalPath();
        } catch (java.io.IOException ioe) {
        }
        new org.acm.seguin.summary.SummaryTraversal(topLevelDir).go();
        if (org.acm.seguin.summary.query.PackageContainsClass.query(srcPackage, newClassName)) {
            throw new org.acm.seguin.refactor.RefactoringException((srcPackage + " already contains a class named ") + newClassName);
        }
    }

    /**
     * The transformation of all the source files
     */
    protected void transform() {
        java.lang.System.out.println((("Renaming " + oldClassName) + " to ") + newClassName);
        org.acm.seguin.refactor.ComplexTransform complex = getComplexTransform();
        org.acm.seguin.refactor.EliminatePackageImportVisitor epiv = new org.acm.seguin.refactor.EliminatePackageImportVisitor(complex);
        epiv.setPackageSummary(org.acm.seguin.summary.PackageSummary.getPackageSummary(srcPackage));
        epiv.addFilterClass(oldClassName);
        epiv.visit(null);
        org.acm.seguin.refactor.type.RenameClassVisitor rcv = new org.acm.seguin.refactor.type.RenameClassVisitor(srcPackage, oldClassName, newClassName, base, complex);
        rcv.visit(null);
    }
}