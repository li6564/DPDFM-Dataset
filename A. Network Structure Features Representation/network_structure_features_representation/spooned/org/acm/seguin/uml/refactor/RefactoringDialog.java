/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Dialog box that runs a refactoring
 *
 * @author Chris Seguin
 */
abstract class RefactoringDialog extends javax.swing.JDialog implements java.awt.event.ActionListener {
    private org.acm.seguin.uml.UMLPackage currentPackage;

    /**
     * Constructor for the RefactoringDialog object
     *
     * @param init
     * 		the current package
     */
    public RefactoringDialog(org.acm.seguin.uml.UMLPackage init) {
        currentPackage = init;
    }

    /**
     * Constructor for the RefactoringDialog object
     *
     * @param init
     * 		the current package
     */
    public RefactoringDialog(org.acm.seguin.uml.UMLPackage init, javax.swing.JFrame frame) {
        super(frame);
        currentPackage = init;
    }

    /**
     * Respond to a button press
     *
     * @param evt
     * 		The action event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getActionCommand().equals("OK")) {
            dispose();
            runRefactoring();
        } else if (evt.getActionCommand().equals("Cancel")) {
            dispose();
        }
        if (currentPackage != null) {
            currentPackage.repaint();
        }
    }

    /**
     * Returns the current UML package
     *
     * @return the package
     */
    protected org.acm.seguin.uml.UMLPackage getUMLPackage() {
        return currentPackage;
    }

    /**
     * Creates a refactoring to be performed
     *
     * @return the refactoring
     */
    protected abstract org.acm.seguin.refactor.Refactoring createRefactoring();

    /**
     * Do any necessary updates to the summaries after the refactoring is
     *  complete
     */
    protected void updateSummaries() {
    }

    /**
     * Adds an abstract parent class to all specified classes.
     */
    private void runRefactoring() {
        org.acm.seguin.refactor.Refactoring refactoring = createRefactoring();
        // Update the code
        try {
            refactoring.run();
        } catch (org.acm.seguin.refactor.RefactoringException re) {
            javax.swing.JOptionPane.showMessageDialog(null, re.getMessage(), "Refactoring Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (java.lang.Throwable thrown) {
            org.acm.seguin.awt.ExceptionPrinter.print(thrown);
            org.acm.seguin.awt.ExceptionPrinter.dialog(thrown);
        }
        followup(refactoring);
    }

    /**
     * Follows up the refactoring by updating the
     *  class diagrams
     */
    protected void followup(org.acm.seguin.refactor.Refactoring refactoring) {
        updateSummaries();
        // Update the GUIs
        org.acm.seguin.uml.loader.ReloaderSingleton.reload();
    }
}