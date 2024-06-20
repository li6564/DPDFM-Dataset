/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Basic dialog box taht lists all the children classes
 *
 * @author Chris Seguin
 */
abstract class ChildrenCheckboxDialog extends org.acm.seguin.uml.refactor.RefactoringDialog {
    /**
     * List of type checkboxes
     */
    protected org.acm.seguin.uml.refactor.ChildClassCheckboxPanel children;

    /**
     * The parent type summary
     */
    protected org.acm.seguin.summary.TypeSummary parentType;

    /**
     * Constructor for the ChildrenCheckboxDialog object
     *
     * @param init
     * 		the package diagram
     * @param initType
     * 		the parent type
     */
    public ChildrenCheckboxDialog(org.acm.seguin.uml.UMLPackage init, org.acm.seguin.summary.TypeSummary initType) {
        super(init);
        parentType = initType;
        getContentPane().setLayout(new java.awt.BorderLayout());
        children = new org.acm.seguin.uml.refactor.ChildClassCheckboxPanel(parentType);
        getContentPane().add(children, java.awt.BorderLayout.NORTH);
        javax.swing.JButton okButton = new javax.swing.JButton("OK");
        getContentPane().add(okButton, java.awt.BorderLayout.WEST);
        okButton.addActionListener(this);
        javax.swing.JButton cancelButton = new javax.swing.JButton("Cancel");
        getContentPane().add(cancelButton, java.awt.BorderLayout.EAST);
        cancelButton.addActionListener(this);
        pack();
        org.acm.seguin.awt.CenterDialog.center(this, init);
    }
}