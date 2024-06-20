/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * This is a base class that is shared by a number of different
 *  method refactorings.
 *
 * @author Chris Seguin
 */
abstract class MethodRefactoring extends org.acm.seguin.refactor.Refactoring {
    /**
     * Description of the Method
     *
     * @param source
     * 		Description of Parameter
     * @param transform
     * 		Description of Parameter
     * @param rft
     * 		Description of Parameter
     */
    protected void removeMethod(org.acm.seguin.summary.TypeSummary source, org.acm.seguin.refactor.ComplexTransform transform, org.acm.seguin.refactor.method.RemoveMethodTransform rft) {
        transform.add(rft);
        org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (source.getParent()));
        transform.apply(fileSummary.getFile(), fileSummary.getFile());
    }
}