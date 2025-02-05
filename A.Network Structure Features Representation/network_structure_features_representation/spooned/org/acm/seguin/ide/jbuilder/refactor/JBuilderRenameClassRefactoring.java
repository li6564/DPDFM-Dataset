/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder.refactor;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
class JBuilderRenameClassRefactoring extends org.acm.seguin.refactor.type.RenameClassRefactoring {
    /**
     * Constructor for the JBuilderRenameClassRefactoring object
     */
    protected JBuilderRenameClassRefactoring() {
        super();
    }

    /**
     * Performs the refactoring by traversing through the files and updating
     *  them.
     */
    protected void transform() {
        org.acm.seguin.summary.FileSummary summary = getFileSummary();
        org.acm.seguin.ide.jbuilder.refactor.FileCloser.close(summary.getFile());
        super.transform();
    }
}