/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder.refactor;
/**
 * Make sure to close the files before performing this refactoring
 *
 * @author Chris Seguin
 */
class JBuilderMoveClassRefactoring extends org.acm.seguin.refactor.type.MoveClass {
    /**
     * Constructor for the JBuilderMoveClass object
     */
    protected JBuilderMoveClassRefactoring() {
        super();
    }

    /**
     * Performs the refactoring by traversing through the files and updating
     *  them.
     */
    protected void transform() {
        java.io.File dir = new java.io.File(initDir);
        java.util.Iterator iter = fileList.iterator();
        while (iter.hasNext()) {
            java.io.File next = new java.io.File(dir, ((java.lang.String) (iter.next())));
            org.acm.seguin.ide.jbuilder.refactor.FileCloser.close(next);
        } 
        super.transform();
    }
}