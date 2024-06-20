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
public class PushUpFieldListener extends org.acm.seguin.uml.refactor.NoInputRefactoringListener {
    private org.acm.seguin.summary.TypeSummary typeSummary;

    private java.lang.String name;

    /**
     * Constructor for the PushUpFieldListener object
     *
     * @param initPackage
     * 		the UML package that is being operated on
     * @param initType
     * 		the type that is being removed
     * @param initMenu
     * 		The popup menu
     * @param initItem
     * 		The current item
     * @param initField
     * 		Description of Parameter
     */
    public PushUpFieldListener(org.acm.seguin.uml.UMLPackage initPackage, org.acm.seguin.summary.TypeSummary initType, org.acm.seguin.summary.FieldSummary fieldSummary, javax.swing.JPopupMenu initMenu, javax.swing.JMenuItem initItem) {
        super(initPackage, initMenu, initItem);
        typeSummary = initType;
        name = fieldSummary.getName();
        if (typeSummary == null) {
            typeSummary = ((org.acm.seguin.summary.TypeSummary) (fieldSummary.getParent()));
        }
    }

    /**
     * Creates a refactoring to be performed
     *
     * @return the refactoring
     */
    protected org.acm.seguin.refactor.Refactoring createRefactoring() {
        org.acm.seguin.refactor.field.PushUpFieldRefactoring puff = org.acm.seguin.refactor.RefactoringFactory.get().pushUpField();
        puff.setClass(typeSummary);
        puff.setField(name);
        return puff;
    }
}