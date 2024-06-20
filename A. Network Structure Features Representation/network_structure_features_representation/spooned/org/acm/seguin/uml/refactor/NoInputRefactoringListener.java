/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Performs a refactoring that requires no further user input
 *
 * @author Chris Seguin
 */
public abstract class NoInputRefactoringListener extends org.acm.seguin.uml.PopupMenuListener {
    private org.acm.seguin.uml.UMLPackage currentPackage;

    /**
     * Constructor for the NoInputRefactoringListener object
     *
     * @param initPackage
     * 		the UML package that is being operated on
     * @param initMenu
     * 		The popup menu
     * @param initItem
     * 		The current item
     */
    public NoInputRefactoringListener(org.acm.seguin.uml.UMLPackage initPackage, javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        super(initMenu, initItem);
        currentPackage = initPackage;
    }

    /**
     * A menu item has been selected, display the dialog box
     *
     * @param evt
     * 		the action event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        super.actionPerformed(evt);
        runRefactoring();
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
        }
        updateSummaries();
        // Update the GUIs
        org.acm.seguin.uml.loader.ReloaderSingleton.reload();
    }
}