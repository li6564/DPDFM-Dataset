/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common.action;
/**
 * For IDEs where the user can select a number of files in the project pane,
 *  this class handles the translation of those selected files into
 *  TypeSummaries and a determination of whether the selected files are .java
 *  files.
 *
 * @author Chris Seguin
 */
public abstract class SelectedFileSet {
    /**
     * Gets the AllJava attribute of the SelectedFileSet object
     *
     * @return The AllJava value
     */
    public abstract boolean isAllJava();

    /**
     * Gets the SingleJavaFile attribute of the SelectedFileSet object
     *
     * @return The SingleJavaFile value
     */
    public abstract boolean isSingleJavaFile();

    /**
     * Gets the TypeSummaryArray attribute of the SelectedFileSet object
     *
     * @return The TypeSummaryArray value
     */
    public abstract org.acm.seguin.summary.TypeSummary[] getTypeSummaryArray();

    /**
     * Returns the type summary that has the same name as the file summary
     *
     * @param summary
     * 		the file summary
     * @return the type summary
     */
    protected org.acm.seguin.summary.TypeSummary getTypeSummary(org.acm.seguin.summary.FileSummary summary) {
        java.util.Iterator iter = summary.getTypes();
        while (iter.hasNext()) {
            org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
            java.lang.String temp = next.getName() + ".java";
            if (temp.equals(summary.getName())) {
                return next;
            }
        } 
        return null;
    }

    /**
     * Reloads the file summary
     *
     * @param file
     * 		Description of Parameter
     * @param input
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    protected org.acm.seguin.summary.FileSummary reloadFile(java.io.File file, java.io.InputStream input) {
        return org.acm.seguin.summary.FileSummary.reloadFromBuffer(file, input);
    }
}