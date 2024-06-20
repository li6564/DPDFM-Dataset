/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Refactoring to allow a user to rename a parameter
 *
 * @author Chris Seguin
 */
public class RenameParameterRefactoring extends org.acm.seguin.refactor.Refactoring {
    private java.lang.String newName;

    private org.acm.seguin.summary.ParameterSummary param;

    private org.acm.seguin.summary.MethodSummary method;

    /**
     * Constructor for the RenameParameterRefactoring object
     */
    protected RenameParameterRefactoring() {
        newName = null;
        param = null;
        method = null;
    }

    /**
     * Sets the NewName attribute of the RenameParameterRefactoring object
     *
     * @param value
     * 		The new NewName value
     */
    public void setNewName(java.lang.String value) {
        newName = value;
    }

    /**
     * Sets the ParameterSummary attribute of the RenameParameterRefactoring
     *  object
     *
     * @param value
     * 		The new ParameterSummary value
     */
    public void setParameterSummary(org.acm.seguin.summary.ParameterSummary value) {
        param = value;
    }

    /**
     * Sets the MethodSummary attribute of the RenameParameterRefactoring object
     *
     * @param value
     * 		The new MethodSummary value
     */
    public void setMethodSummary(org.acm.seguin.summary.MethodSummary value) {
        method = value;
    }

    /**
     * Gets the Description attribute of the RenameParameterRefactoring object
     *
     * @return The Description value
     */
    public java.lang.String getDescription() {
        return (((("Renaming " + param.getName()) + " to ") + newName) + " in ") + method.toString();
    }

    /**
     * Gets the ID attribute of the RenameParameterRefactoring object
     *
     * @return The ID value
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.RENAME_PARAMETER;
    }

    /**
     * Description of the Method
     *
     * @exception RefactoringException
     * 		Description of Exception
     */
    protected void preconditions() throws org.acm.seguin.refactor.RefactoringException {
        if ((newName == null) || (newName.length() == 0)) {
            throw new org.acm.seguin.refactor.RefactoringException("No new name specified");
        }
        if (param == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No parameter specified");
        }
        if (method == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No method specified");
        }
    }

    /**
     * Perform the transformation
     */
    protected void transform() {
        // Get the complex transformation
        org.acm.seguin.refactor.ComplexTransform transform = getComplexTransform();
        // Add the parameter rename transformation
        org.acm.seguin.refactor.method.RenameParameterTransform rpt = new org.acm.seguin.refactor.method.RenameParameterTransform();
        rpt.setMethod(method);
        rpt.setParameter(param);
        rpt.setNewName(newName);
        transform.add(rpt);
        // Apply the refactoring
        org.acm.seguin.summary.Summary current = method;
        while (!(current instanceof org.acm.seguin.summary.FileSummary)) {
            current = current.getParent();
        } 
        org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (current));
        transform.apply(fileSummary.getFile(), fileSummary.getFile());
    }
}