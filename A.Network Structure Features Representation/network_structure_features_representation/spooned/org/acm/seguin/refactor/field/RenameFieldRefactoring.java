/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.field;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Renames a field
 *
 * @author Chris Seguin
 */
public class RenameFieldRefactoring extends org.acm.seguin.refactor.field.FieldRefactoring {
    private java.lang.String newName;

    private org.acm.seguin.summary.FieldSummary oldField;

    /**
     * Constructor for the RenameFieldRefactoring object
     */
    public RenameFieldRefactoring() {
        super();
    }

    /**
     * Sets the NewName attribute of the RenameFieldRefactoring object
     *
     * @param value
     * 		The new NewName value
     */
    public void setNewName(java.lang.String value) {
        newName = value;
    }

    /**
     * Gets the Description attribute of the RenameFieldRefactoring object
     *
     * @return The Description value
     */
    public java.lang.String getDescription() {
        return (("Renames the field " + field) + " to ") + newName;
    }

    /**
     * Gets the ID attribute of the RenameFieldRefactoring object
     *
     * @return The ID value
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.RENAME_FIELD;
    }

    /**
     * Check that thsi refactoring can be performed
     *
     * @exception RefactoringException
     * 		Description of Exception
     */
    protected void preconditions() throws org.acm.seguin.refactor.RefactoringException {
        java.util.Iterator iter = typeSummary.getFields();
        if (iter == null) {
            throw new org.acm.seguin.refactor.RefactoringException(typeSummary.getName() + " has no fields associated with it, so it cannot be renamed");
        }
        boolean found = false;
        while (iter.hasNext()) {
            org.acm.seguin.summary.FieldSummary next = ((org.acm.seguin.summary.FieldSummary) (iter.next()));
            if (next.getName().equals(field)) {
                found = true;
                oldField = next;
            }
            if (next.getName().equals(newName)) {
                throw new org.acm.seguin.refactor.RefactoringException((("A field named " + newName) + " already exists in class ") + typeSummary.getName());
            }
        } 
        if (!found) {
            throw new org.acm.seguin.refactor.RefactoringException((("No field named " + field) + " is contained in ") + typeSummary.getName());
        }
    }

    /**
     * Applies the transformation to the system to rename the method
     */
    protected void transform() {
        org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (getFileSummary(typeSummary)));
        org.acm.seguin.refactor.field.RenameFieldTransform rft = new org.acm.seguin.refactor.field.RenameFieldTransform(oldField, newName);
        org.acm.seguin.refactor.ComplexTransform transform = getComplexTransform();
        transform.add(rft);
        transform.apply(fileSummary.getFile(), fileSummary.getFile());
        if (oldField.getModifiers().isPrivate()) {
            // We are done
        } else if (oldField.getModifiers().isPackage()) {
            org.acm.seguin.refactor.field.RenameSystemTraversal rsv = new org.acm.seguin.refactor.field.RenameSystemTraversal();
            rsv.visit(getPackage(), new org.acm.seguin.refactor.field.RenameFieldData(oldField, newName, transform));
        } else {
            org.acm.seguin.refactor.field.RenameSystemTraversal rsv = new org.acm.seguin.refactor.field.RenameSystemTraversal();
            rsv.visit(new org.acm.seguin.refactor.field.RenameFieldData(oldField, newName, transform));
        }
    }

    /**
     * Gets the Package attribute of the RenameFieldRefactoring object
     *
     * @return The Package value
     */
    private org.acm.seguin.summary.PackageSummary getPackage() {
        org.acm.seguin.summary.Summary current = oldField;
        while (!(current instanceof org.acm.seguin.summary.PackageSummary)) {
            current = current.getParent();
        } 
        return ((org.acm.seguin.summary.PackageSummary) (current));
    }
}