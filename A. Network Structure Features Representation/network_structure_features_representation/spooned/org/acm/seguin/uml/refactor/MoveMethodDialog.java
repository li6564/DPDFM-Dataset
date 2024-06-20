/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * This dialog box selects which parameter the method is being moved into.
 *
 * @author Chris Seguin
 */
class MoveMethodDialog extends org.acm.seguin.uml.refactor.RefactoringDialog {
    /**
     * parameter panel
     */
    private org.acm.seguin.uml.refactor.ParameterPanel params;

    /**
     * The parent type summary
     */
    private org.acm.seguin.summary.MethodSummary methodSummary;

    /**
     * Constructor for the MoveMethodDialog object
     *
     * @param init
     * 		the package diagram
     * @param initMethod
     * 		Description of Parameter
     */
    public MoveMethodDialog(org.acm.seguin.uml.UMLPackage init, org.acm.seguin.summary.MethodSummary initMethod) {
        super(init);
        methodSummary = initMethod;
        getContentPane().setLayout(new java.awt.BorderLayout());
        params = new org.acm.seguin.uml.refactor.ParameterPanel(methodSummary);
        getContentPane().add(params, java.awt.BorderLayout.NORTH);
        javax.swing.JButton okButton = new javax.swing.JButton("OK");
        getContentPane().add(okButton, java.awt.BorderLayout.WEST);
        okButton.addActionListener(this);
        javax.swing.JButton cancelButton = new javax.swing.JButton("Cancel");
        getContentPane().add(cancelButton, java.awt.BorderLayout.EAST);
        cancelButton.addActionListener(this);
        setTitle(("Move method " + methodSummary.toString()) + "  to:");
        pack();
        org.acm.seguin.awt.CenterDialog.center(this, init);
    }

    /**
     * Creates a refactoring to be performed
     *
     * @return the refactoring
     */
    protected org.acm.seguin.refactor.Refactoring createRefactoring() {
        org.acm.seguin.refactor.method.MoveMethodRefactoring moveMethod = org.acm.seguin.refactor.RefactoringFactory.get().moveMethod();
        moveMethod.setMethod(methodSummary);
        moveMethod.setDestination(params.get());
        return moveMethod;
    }
}