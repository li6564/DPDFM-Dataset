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
public class AddChildClassDialog extends org.acm.seguin.uml.refactor.ClassNameDialog {
    private org.acm.seguin.summary.TypeSummary typeSummary;

    private javax.swing.JComboBox packageNameBox;

    /**
     * Constructor for AddAbstractParentDialog
     *
     * @param init
     * 		The package where this operation is occuring
     * @param initType
     * 		the type summary
     */
    public AddChildClassDialog(org.acm.seguin.uml.UMLPackage init, org.acm.seguin.summary.TypeSummary initType) {
        super(init, 2);
        org.acm.seguin.uml.refactor.PackageList pl = new org.acm.seguin.uml.refactor.PackageList();
        packageNameBox = pl.add(this);
        java.lang.String name;
        if (init == null) {
            name = org.acm.seguin.summary.query.GetPackageSummary.query(initType).getName();
        } else {
            name = init.getSummary().getName();
        }
        packageNameBox.setSelectedItem(name);
        typeSummary = initType;
        pack();
        org.acm.seguin.awt.CenterDialog.center(this, init);
    }

    /**
     * Returns the window title
     *
     * @return the title
     */
    public java.lang.String getWindowTitle() {
        return "Add a child class";
    }

    /**
     * Gets the label for the text
     *
     * @return the text for the label
     */
    public java.lang.String getLabelText() {
        return "Child class:";
    }

    /**
     * Adds an abstract parent class to all specified classes.
     *
     * @return the refactoring
     */
    protected org.acm.seguin.refactor.Refactoring createRefactoring() {
        // Create system
        org.acm.seguin.refactor.type.AddChildRefactoring refactoring = org.acm.seguin.refactor.RefactoringFactory.get().addChild();
        refactoring.setChildName(getClassName());
        // Add the type
        refactoring.setParentClass(typeSummary);
        refactoring.setPackageName(((java.lang.String) (packageNameBox.getSelectedItem())));
        return refactoring;
    }
}