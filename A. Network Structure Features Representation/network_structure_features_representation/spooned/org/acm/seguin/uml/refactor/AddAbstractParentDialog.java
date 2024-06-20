/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Creates a dialog box to prompt for the new parent name
 *
 * @author Chris Seguin
 */
public class AddAbstractParentDialog extends org.acm.seguin.uml.refactor.ClassNameDialog {
    private org.acm.seguin.summary.TypeSummary[] typeArray;

    /**
     * Constructor for AddAbstractParentDialog
     *
     * @param init
     * 		The package where this operation is occuring
     * @param initPanel
     * 		Description of Parameter
     */
    public AddAbstractParentDialog(org.acm.seguin.uml.UMLPackage init, org.acm.seguin.summary.TypeSummary[] initTypes) {
        super(init, 1);
        typeArray = initTypes;
    }

    /**
     * Returns the window title
     *
     * @return the title
     */
    public java.lang.String getWindowTitle() {
        return "Add an abstract parent";
    }

    /**
     * Gets the label for the text
     *
     * @return the text for the label
     */
    public java.lang.String getLabelText() {
        return "Parent class:";
    }

    /**
     * Adds an abstract parent class to all specified classes.
     *
     * @return the refactoring
     */
    protected org.acm.seguin.refactor.Refactoring createRefactoring() {
        // Create system
        org.acm.seguin.refactor.type.AddAbstractParent aap = org.acm.seguin.refactor.RefactoringFactory.get().addParent();
        aap.setParentName(getClassName());
        // Add the types
        for (int ndx = 0; ndx < typeArray.length; ndx++) {
            aap.addChildClass(typeArray[ndx]);
        }
        return aap;
    }
}