/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.field;
/**
 * Factory for field refactorings
 *
 * @author Chris Seguin
 */
public class FieldRefactoringFactory {
    /**
     * Moves the field into the parent class
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.field.PushDownFieldRefactoring pushDownField() {
        return new org.acm.seguin.refactor.field.PushDownFieldRefactoring();
    }

    /**
     * Moves the field into the child class
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.field.PushUpFieldRefactoring pushUpField() {
        return new org.acm.seguin.refactor.field.PushUpFieldRefactoring();
    }

    /**
     * Renames a field
     *
     * @return The refactoring
     */
    public org.acm.seguin.refactor.field.RenameFieldRefactoring renameField() {
        return new org.acm.seguin.refactor.field.RenameFieldRefactoring();
    }
}