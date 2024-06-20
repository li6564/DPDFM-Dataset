/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.type;
/**
 * Factory for the type refactorings
 *
 * @author Chris Seguin
 */
public class TypeRefactoringFactory {
    /**
     * Adds a feature to the Child attribute of the TypeRefactoringFactory
     *  object
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.type.AddChildRefactoring addChild() {
        return new org.acm.seguin.refactor.type.AddChildRefactoring();
    }

    /**
     * Adds a feature to the Parent attribute of the TypeRefactoringFactory
     *  object
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.type.AddAbstractParent addParent() {
        return new org.acm.seguin.refactor.type.AddAbstractParent();
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.type.MoveClass moveClass() {
        return new org.acm.seguin.refactor.type.MoveClass();
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.type.RenameClassRefactoring renameClass() {
        return new org.acm.seguin.refactor.type.RenameClassRefactoring();
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.type.RemoveEmptyClassRefactoring removeEmptyClass() {
        return new org.acm.seguin.refactor.type.RemoveEmptyClassRefactoring();
    }

    /**
     * Extracts the interface of a class into a new interface object
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.type.ExtractInterfaceRefactoring extractInterface() {
        return new org.acm.seguin.refactor.type.ExtractInterfaceRefactoring();
    }
}