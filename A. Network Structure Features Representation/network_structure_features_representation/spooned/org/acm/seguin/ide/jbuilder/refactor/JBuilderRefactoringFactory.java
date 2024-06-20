/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder.refactor;
/**
 * Creates refactorings specific to JBuilder. The refactorings make sure to
 *  close out existing files before the refactoring is performed.
 *
 * @author Chris Seguin
 */
public class JBuilderRefactoringFactory extends org.acm.seguin.refactor.RefactoringFactory {
    /**
     * Constructor for the JBuilderRefactoringFactory object
     */
    protected JBuilderRefactoringFactory() {
        super();
    }

    /**
     * Creates a move class refactoring object
     *
     * @return the move class refactoring object
     */
    public org.acm.seguin.refactor.type.MoveClass moveClass() {
        prepare();
        return new org.acm.seguin.ide.jbuilder.refactor.JBuilderMoveClassRefactoring();
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.type.RenameClassRefactoring renameClass() {
        prepare();
        return new org.acm.seguin.ide.jbuilder.refactor.JBuilderRenameClassRefactoring();
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.refactor.type.RemoveEmptyClassRefactoring removeEmptyClass() {
        prepare();
        return new org.acm.seguin.ide.jbuilder.refactor.JBuilderRemoveEmptyClassRefactoring();
    }

    /**
     * We use this as an opportunity to save all the files in the system before
     *  we perform the refactorings.
     */
    protected void prepare() {
        org.acm.seguin.ide.jbuilder.refactor.Browser.getActiveBrowser().doSaveAll(true);
    }

    /**
     * Register this as the factory
     */
    public static void register() {
        org.acm.seguin.refactor.RefactoringFactory.setSingleton(new org.acm.seguin.ide.jbuilder.refactor.JBuilderRefactoringFactory());
    }
}