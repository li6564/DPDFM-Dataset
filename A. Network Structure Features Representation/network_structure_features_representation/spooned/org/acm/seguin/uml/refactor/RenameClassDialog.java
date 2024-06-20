/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Creates a dialog box to prompt for the new package name
 *
 * @author Chris Seguin
 */
public class RenameClassDialog extends org.acm.seguin.uml.refactor.ClassNameDialog {
    // Instance Variables
    private org.acm.seguin.summary.TypeSummary typeSummary;

    /**
     * Constructor for RenameClassDialog
     *
     * @param init
     * 		Description of Parameter
     * @param type
     * 		Description of Parameter
     */
    public RenameClassDialog(org.acm.seguin.uml.UMLPackage init, org.acm.seguin.summary.TypeSummary type) {
        super(init, 1);
        typeSummary = type;
        setTitle(getWindowTitle());
    }

    /**
     * Returns the window title
     *
     * @return the title
     */
    public java.lang.String getWindowTitle() {
        if (typeSummary == null) {
            return "Rename class";
        } else {
            return "Rename class: " + typeSummary.getName();
        }
    }

    /**
     * Gets the label for the text
     *
     * @return the text for the label
     */
    public java.lang.String getLabelText() {
        return "New Name:";
    }

    /**
     * Creates a refactoring to be performed
     *
     * @return the refactoring
     */
    protected org.acm.seguin.refactor.Refactoring createRefactoring() {
        java.lang.String oldClassName = typeSummary.getName();
        java.lang.String newClassName = getClassName();
        java.io.File file = ((org.acm.seguin.summary.FileSummary) (typeSummary.getParent())).getFile();
        java.lang.String path = file.getPath();
        try {
            path = file.getCanonicalPath();
        } catch (java.io.IOException ioe) {
        }
        file = new java.io.File(path);
        java.io.File initialStarting = file.getParentFile();
        org.acm.seguin.refactor.type.RenameClassRefactoring rc = org.acm.seguin.refactor.RefactoringFactory.get().renameClass();
        rc.setDirectory(initialStarting.getPath());
        rc.setOldClassName(oldClassName);
        rc.setNewClassName(newClassName);
        return rc;
    }

    /**
     * Rename the type summary that has been influenced
     */
    protected void updateSummaries() {
        typeSummary.setName(getClassName());
    }
}