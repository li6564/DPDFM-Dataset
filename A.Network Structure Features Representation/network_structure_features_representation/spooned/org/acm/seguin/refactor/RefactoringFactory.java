/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor;
/**
 * Factory for all refactorings
 *
 * @author Chris Seguin
 */
public class RefactoringFactory {
    /**
     * Generates the type refactorings
     */
    private org.acm.seguin.refactor.type.TypeRefactoringFactory typeFactory;

    /**
     * Generates the field refactorings
     */
    private org.acm.seguin.refactor.field.FieldRefactoringFactory fieldFactory;

    /**
     * Generates the method refactorings
     */
    private org.acm.seguin.refactor.method.MethodRefactoringFactory methodFactory;

    /**
     * Stores the singleton
     */
    private static org.acm.seguin.refactor.RefactoringFactory singleton;

    /**
     * Constructor for the RefactoringFactory object
     */
    protected RefactoringFactory() {
        typeFactory = new org.acm.seguin.refactor.type.TypeRefactoringFactory();
        fieldFactory = new org.acm.seguin.refactor.field.FieldRefactoringFactory();
        methodFactory = new org.acm.seguin.refactor.method.MethodRefactoringFactory();
    }

    /**
     * Adds a feature to the Child attribute of the TypeRefactoringFactory
     *  object
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.type.AddChildRefactoring addChild() {
        prepare();
        return typeFactory.addChild();
    }

    /**
     * Adds a feature to the Parent attribute of the TypeRefactoringFactory
     *  object
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.type.AddAbstractParent addParent() {
        prepare();
        return typeFactory.addParent();
    }

    /**
     * Creates a move class refactoring object
     *
     * @return the move class refactoring object
     */
    public org.acm.seguin.refactor.type.MoveClass moveClass() {
        prepare();
        return typeFactory.moveClass();
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.type.RenameClassRefactoring renameClass() {
        prepare();
        return typeFactory.renameClass();
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.type.RemoveEmptyClassRefactoring removeEmptyClass() {
        prepare();
        return typeFactory.removeEmptyClass();
    }

    /**
     * Extracts the interface of a class into a new interface object
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.type.ExtractInterfaceRefactoring extractInterface() {
        prepare();
        return typeFactory.extractInterface();
    }

    /**
     * Moves the field into the parent class
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.field.PushDownFieldRefactoring pushDownField() {
        prepare();
        return fieldFactory.pushDownField();
    }

    /**
     * Renames a field
     *
     * @return The refactoring
     */
    public org.acm.seguin.refactor.field.RenameFieldRefactoring renameField() {
        prepare();
        return fieldFactory.renameField();
    }

    /**
     * Moves the field into the child class
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.field.PushUpFieldRefactoring pushUpField() {
        prepare();
        return fieldFactory.pushUpField();
    }

    /**
     * Moves the method into the parent class
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.method.PushUpMethodRefactoring pushUpMethod() {
        prepare();
        return methodFactory.pushUpMethod();
    }

    /**
     * Moves the method signature into the parent class
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.method.PushUpAbstractMethodRefactoring pushUpAbstractMethod() {
        prepare();
        return methodFactory.pushUpAbstractMethod();
    }

    /**
     * Moves the method into a child class
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.method.PushDownMethodRefactoring pushDownMethod() {
        prepare();
        return methodFactory.pushDownMethod();
    }

    /**
     * Moves the method into another class
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.method.MoveMethodRefactoring moveMethod() {
        prepare();
        return methodFactory.moveMethod();
    }

    /**
     * Renames a parameter
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.method.RenameParameterRefactoring renameParameter() {
        prepare();
        return methodFactory.renameParameter();
    }

    /**
     * Extracts code from one method to create a new method
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.method.ExtractMethodRefactoring extractMethod() {
        return methodFactory.extractMethod();
    }

    /**
     * Prepare to create a refactoring that operates on files on the disk. This
     *  is an IDE's last opportunity to save files before the refactoring is
     *  performed. This is not used for ExtractMethod which works on code that is
     *  in memory (rather than on the disk)
     */
    protected void prepare() {
    }

    /**
     * This allows someone to replace this factory
     *
     * @param value
     * 		The new Singleton value
     */
    public static void setSingleton(org.acm.seguin.refactor.RefactoringFactory value) {
        org.acm.seguin.refactor.RefactoringFactory.singleton = value;
    }

    /**
     * A standard method to get the factory
     *
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.refactor.RefactoringFactory get() {
        if (org.acm.seguin.refactor.RefactoringFactory.singleton == null) {
            org.acm.seguin.refactor.RefactoringFactory.init();
        }
        return org.acm.seguin.refactor.RefactoringFactory.singleton;
    }

    /**
     * Initializes the singleton
     */
    private static synchronized void init() {
        if (org.acm.seguin.refactor.RefactoringFactory.singleton == null) {
            org.acm.seguin.refactor.RefactoringFactory.singleton = new org.acm.seguin.refactor.RefactoringFactory();
        }
    }
}