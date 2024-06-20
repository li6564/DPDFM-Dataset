/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
public class MethodRefactoringFactory {
    /**
     * Moves the method into the parent class
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.method.PushUpMethodRefactoring pushUpMethod() {
        return new org.acm.seguin.refactor.method.PushUpMethodRefactoring();
    }

    /**
     * Moves the method signature into the parent class
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.method.PushUpAbstractMethodRefactoring pushUpAbstractMethod() {
        return new org.acm.seguin.refactor.method.PushUpAbstractMethodRefactoring();
    }

    /**
     * Moves the method into a child class
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.method.PushDownMethodRefactoring pushDownMethod() {
        return new org.acm.seguin.refactor.method.PushDownMethodRefactoring();
    }

    /**
     * Moves the method into another class
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.method.MoveMethodRefactoring moveMethod() {
        return new org.acm.seguin.refactor.method.MoveMethodRefactoring();
    }

    /**
     * Extracts code from one method to create a new method
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.method.ExtractMethodRefactoring extractMethod() {
        return new org.acm.seguin.refactor.method.ExtractMethodRefactoring();
    }

    /**
     * Renames a parameter
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.method.RenameParameterRefactoring renameParameter() {
        return new org.acm.seguin.refactor.method.RenameParameterRefactoring();
    }
}