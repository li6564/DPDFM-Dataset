/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
/**
 * Determines if a package contains a certain type
 *
 * @author Chris Seguin
 * @created November 22, 1999
 */
public class FileSummaryGetter {
    /**
     * Places the query
     *
     * @param packageName
     * 		the name of the package
     * @param typeName
     * 		the name of the type
     * @return true if the package contains a type with that name
     */
    public org.acm.seguin.summary.FileSummary query(java.lang.String packageName, java.lang.String typeName) {
        return query(org.acm.seguin.summary.PackageSummary.getPackageSummary(packageName), typeName);
    }

    /**
     * Checks the package to determine if it contains that type
     *
     * @param summary
     * 		the package
     * @param typeName
     * 		the type
     * @return true if the type is in the package
     */
    public org.acm.seguin.summary.FileSummary query(org.acm.seguin.summary.PackageSummary summary, java.lang.String typeName) {
        java.util.Iterator iter = summary.getFileSummaries();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (iter.next()));
                if (query(fileSummary, typeName)) {
                    return fileSummary;
                }
            } 
        }
        return null;
    }

    /**
     * Checks if a specific file contains a type
     *
     * @param summary
     * 		the file
     * @param typeName
     * 		the type name
     * @return true if the file contains the type
     */
    private boolean query(org.acm.seguin.summary.FileSummary summary, java.lang.String typeName) {
        java.util.Iterator iter = summary.getTypes();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.TypeSummary typeSummary = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
                if (typeName.equals(typeSummary.getName())) {
                    return true;
                }
            } 
        }
        return false;
    }
}