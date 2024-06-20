/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.field;
/**
 * Object used to store the state of the rename field visitor
 *
 * @author Chris Seguin
 */
class RenameFieldData {
    private java.lang.String oldName;

    private java.lang.String newName;

    private boolean thisRequired;

    private org.acm.seguin.summary.Summary current;

    private boolean mustInsertThis;

    private org.acm.seguin.summary.TypeSummary typeSummary;

    private boolean canBeFirst;

    private boolean canBeThis;

    private org.acm.seguin.summary.FieldSummary oldField;

    private org.acm.seguin.refactor.ComplexTransform transform;

    private java.lang.String fullName;

    private java.lang.String importedName;

    /**
     * Constructor for the RenameFieldData object
     *
     * @param newName
     * 		the new field name
     * @param oldField
     * 		Description of Parameter
     */
    public RenameFieldData(org.acm.seguin.summary.FieldSummary oldField, java.lang.String newName) {
        this.oldName = oldField.getName();
        this.newName = newName;
        this.oldField = oldField;
        thisRequired = false;
        canBeFirst = false;
        canBeThis = false;
        mustInsertThis = false;
        current = null;
        typeSummary = ((org.acm.seguin.summary.TypeSummary) (oldField.getParent()));
        initNames(oldField);
    }

    /**
     * Constructor for the RenameFieldData object
     *
     * @param newName
     * 		the new field name
     * @param oldField
     * 		Description of Parameter
     * @param transform
     * 		Description of Parameter
     */
    public RenameFieldData(org.acm.seguin.summary.FieldSummary oldField, java.lang.String newName, org.acm.seguin.refactor.ComplexTransform transform) {
        this.newName = newName;
        this.oldField = oldField;
        this.transform = transform;
        oldName = oldField.getName();
        typeSummary = ((org.acm.seguin.summary.TypeSummary) (oldField.getParent()));
    }

    /**
     * Sets the ThisRequired attribute of the RenameFieldData object
     *
     * @param way
     * 		The new ThisRequired value
     */
    public void setThisRequired(boolean way) {
        thisRequired = way;
    }

    /**
     * Sets the CurrentSummary attribute of the RenameFieldData object
     *
     * @param value
     * 		The new CurrentSummary value
     */
    public void setCurrentSummary(org.acm.seguin.summary.Summary value) {
        current = value;
        if (current instanceof org.acm.seguin.summary.TypeSummary) {
            check(((org.acm.seguin.summary.TypeSummary) (current)));
        }
    }

    /**
     * Sets the MustInsertThis attribute of the RenameFieldData object
     *
     * @param value
     * 		The new MustInsertThis value
     */
    public void setMustInsertThis(boolean value) {
        mustInsertThis = value;
    }

    /**
     * Gets the OldName attribute of the RenameFieldData object
     *
     * @return The OldName value
     */
    public java.lang.String getOldName() {
        return oldName;
    }

    /**
     * Gets the NewName attribute of the RenameFieldData object
     *
     * @return The NewName value
     */
    public java.lang.String getNewName() {
        return newName;
    }

    /**
     * Gets the ThisRequired attribute of the RenameFieldData object
     *
     * @return The ThisRequired value
     */
    public boolean isThisRequired() {
        return thisRequired;
    }

    /**
     * Gets the CurrentSummary attribute of the RenameFieldData object
     *
     * @return The CurrentSummary value
     */
    public org.acm.seguin.summary.Summary getCurrentSummary() {
        return current;
    }

    /**
     * Gets the MustInsertThis attribute of the RenameFieldData object
     *
     * @return The MustInsertThis value
     */
    public boolean isMustInsertThis() {
        return mustInsertThis;
    }

    /**
     * Returns the type summary where the field is changing
     *
     * @return The TypeSummary value
     */
    public org.acm.seguin.summary.TypeSummary getTypeSummary() {
        return typeSummary;
    }

    /**
     * Gets the AllowedToChangeFirst attribute of the RenameFieldData object
     *
     * @return The AllowedToChangeFirst value
     */
    public boolean isAllowedToChangeFirst() {
        return canBeFirst;
    }

    /**
     * Gets the AllowedToChangeThis attribute of the RenameFieldData object
     *
     * @return The AllowedToChangeThis value
     */
    public boolean isAllowedToChangeThis() {
        return canBeThis;
    }

    /**
     * Gets the OldField attribute of the RenameFieldData object
     *
     * @return The OldField value
     */
    public org.acm.seguin.summary.FieldSummary getOldField() {
        return oldField;
    }

    /**
     * Gets the ComplexTransform attribute of the RenameFieldData object
     *
     * @return The ComplexTransform value
     */
    public org.acm.seguin.refactor.ComplexTransform getComplexTransform() {
        return transform;
    }

    /**
     * Gets the FullName attribute of the RenameFieldData object
     *
     * @return The FullName value
     */
    public java.lang.String getFullName() {
        return fullName;
    }

    /**
     * Gets the ImportedName attribute of the RenameFieldData object
     *
     * @return The ImportedName value
     */
    public java.lang.String getImportedName() {
        return importedName;
    }

    /**
     * Returns true if the system can change the first name in an array
     *
     * @param current
     * 		the type summary in question
     */
    private void check(org.acm.seguin.summary.TypeSummary current) {
        if ((current == typeSummary) || org.acm.seguin.summary.query.Ancestor.query(current, typeSummary)) {
            canBeFirst = true;
            canBeThis = true;
            return;
        }
        org.acm.seguin.summary.Summary cs = current;
        while (cs != null) {
            if (cs == typeSummary) {
                canBeThis = false;
                canBeFirst = true;
                return;
            }
            cs = cs.getParent();
        } 
        canBeThis = false;
        canBeFirst = false;
    }

    /**
     * Initialize the names
     *
     * @param field
     * 		the field summary
     */
    private void initNames(org.acm.seguin.summary.FieldSummary field) {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer(field.getName());
        org.acm.seguin.summary.Summary current = field;
        while (current != null) {
            if (current instanceof org.acm.seguin.summary.TypeSummary) {
                buffer.insert(0, ".");
                buffer.insert(0, current.getName());
            }
            if (current instanceof org.acm.seguin.summary.PackageSummary) {
                importedName = buffer.toString();
                buffer.insert(0, ".");
                buffer.insert(0, current.getName());
                fullName = buffer.toString();
                return;
            }
            current = current.getParent();
        } 
        // We should never get here
        importedName = buffer.toString();
        fullName = buffer.toString();
    }
}