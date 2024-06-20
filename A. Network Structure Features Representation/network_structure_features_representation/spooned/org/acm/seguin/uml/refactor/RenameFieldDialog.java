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
public class RenameFieldDialog extends org.acm.seguin.uml.refactor.ClassNameDialog {
    // Instance Variables
    private org.acm.seguin.summary.FieldSummary fieldSummary;

    /**
     * Constructor for RenameFieldDialog
     *
     * @param init
     * 		Description of Parameter
     * @param type
     * 		Description of Parameter
     */
    public RenameFieldDialog(org.acm.seguin.uml.UMLPackage init, org.acm.seguin.summary.FieldSummary field) {
        super(init, 1);
        fieldSummary = field;
        setTitle(getWindowTitle());
    }

    /**
     * Returns the window title
     *
     * @return the title
     */
    public java.lang.String getWindowTitle() {
        if (fieldSummary == null) {
            return "Rename field";
        } else {
            return "Rename field: " + fieldSummary.getName();
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
        org.acm.seguin.refactor.field.RenameFieldRefactoring rfr = org.acm.seguin.refactor.RefactoringFactory.get().renameField();
        rfr.setClass(((org.acm.seguin.summary.TypeSummary) (fieldSummary.getParent())));
        rfr.setField(fieldSummary.getName());
        rfr.setNewName(getClassName());
        return rfr;
    }

    /**
     * Rename the type summary that has been influenced
     */
    protected void updateSummaries() {
        fieldSummary.setName(getClassName());
    }
}