/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
/**
 * Removes a class that has no body to it
 *
 * @author Chris Seguin
 */
public class RemoveClassListener extends org.acm.seguin.uml.refactor.NoInputRefactoringListener {
    private org.acm.seguin.summary.TypeSummary typeSummary;

    /**
     * Constructor for the RemoveClassListener object
     *
     * @param initPackage
     * 		the UML package that is being operated on
     * @param initType
     * 		the type that is being removed
     * @param initMenu
     * 		The popup menu
     * @param initItem
     * 		The current item
     */
    public RemoveClassListener(org.acm.seguin.uml.UMLPackage initPackage, org.acm.seguin.summary.TypeSummary initType, javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        super(initPackage, initMenu, initItem);
        typeSummary = initType;
    }

    /**
     * Creates a refactoring to be performed
     *
     * @return the refactoring
     */
    protected org.acm.seguin.refactor.Refactoring createRefactoring() {
        org.acm.seguin.refactor.type.RemoveEmptyClassRefactoring recr = org.acm.seguin.refactor.RefactoringFactory.get().removeEmptyClass();
        recr.setClass(typeSummary);
        return recr;
    }
}