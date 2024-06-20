/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Extracts an interface from a class
 *
 * @author Grant Watson
 * @created November 30, 2000
 */
public class ExtractInterfaceDialog extends org.acm.seguin.uml.refactor.ClassNameDialog {
    private org.acm.seguin.summary.TypeSummary[] typeArray;

    private javax.swing.JComboBox packageNameBox;

    /**
     * Constructor for ExtractInterfaceDialog
     *
     * @param init
     * 		The package where this operation is occuring
     * @param initTypes
     * 		Description of Parameter
     */
    public ExtractInterfaceDialog(org.acm.seguin.uml.UMLPackage init, org.acm.seguin.summary.TypeSummary[] initTypes) {
        super(init, 2);
        typeArray = initTypes;
        org.acm.seguin.uml.refactor.PackageList pl = new org.acm.seguin.uml.refactor.PackageList();
        packageNameBox = pl.add(this);
        java.lang.String name;
        if (init == null) {
            name = org.acm.seguin.summary.query.GetPackageSummary.query(initTypes[0]).getName();
        } else {
            name = init.getSummary().getName();
        }
        packageNameBox.setSelectedItem(name);
        pack();
        org.acm.seguin.awt.CenterDialog.center(this, init);
    }

    /**
     * Returns the window title
     *
     * @return the title
     */
    public java.lang.String getWindowTitle() {
        return "Extract Interface";
    }

    /**
     * Gets the label for the text
     *
     * @return the text for the label
     */
    public java.lang.String getLabelText() {
        return "Interface:";
    }

    /**
     * Extracts an interface from all specified classes.
     *
     * @return the refactoring
     */
    protected org.acm.seguin.refactor.Refactoring createRefactoring() {
        // Create system
        org.acm.seguin.refactor.type.ExtractInterfaceRefactoring eir = org.acm.seguin.refactor.RefactoringFactory.get().extractInterface();
        java.lang.String interfaceName = getClassName();
        if (interfaceName.indexOf(".") > 0) {
            eir.setInterfaceName(interfaceName);
        } else {
            java.lang.String packageName = ((java.lang.String) (packageNameBox.getSelectedItem()));
            if (packageName.indexOf("<") == (-1)) {
                eir.setInterfaceName((packageName + ".") + interfaceName);
            } else {
                eir.setInterfaceName(interfaceName);
            }
        }
        // Add the types
        for (int ndx = 0; ndx < typeArray.length; ndx++) {
            eir.addImplementingClass(typeArray[ndx]);
        }
        return eir;
    }
}