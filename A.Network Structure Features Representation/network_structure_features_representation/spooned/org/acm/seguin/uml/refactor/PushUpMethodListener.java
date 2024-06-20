/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Pushes a field into the parent class
 *
 * @author Chris Seguin
 */
public class PushUpMethodListener extends org.acm.seguin.uml.refactor.NoInputRefactoringListener {
    private org.acm.seguin.summary.MethodSummary methodSummary;

    /**
     * Constructor for the PushUpMethodListener object
     *
     * @param initPackage
     * 		the UML package that is being operated on
     * @param initMenu
     * 		The popup menu
     * @param initItem
     * 		The current item
     * @param initMethod
     * 		The method
     */
    public PushUpMethodListener(org.acm.seguin.uml.UMLPackage initPackage, org.acm.seguin.summary.MethodSummary initMethod, javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        super(initPackage, initMenu, initItem);
        methodSummary = initMethod;
    }

    /**
     * Creates a refactoring to be performed
     *
     * @return the refactoring
     */
    protected org.acm.seguin.refactor.Refactoring createRefactoring() {
        org.acm.seguin.refactor.method.PushUpMethodRefactoring puff = org.acm.seguin.refactor.RefactoringFactory.get().pushUpMethod();
        puff.setMethod(methodSummary);
        return puff;
    }
}