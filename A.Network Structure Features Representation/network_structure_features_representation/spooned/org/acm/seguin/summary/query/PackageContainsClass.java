/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
/**
 * Determines if a specified package contains a class with that name
 *
 * @author Chris Seguin
 * @created November 28, 1999
 */
public class PackageContainsClass {
    /**
     * Checks to see if the package contains a class with that name
     *
     * @param packageName
     * 		the name of the package
     * @param className
     * 		the name of the class
     * @return true if it is included
     */
    public static boolean query(java.lang.String packageName, java.lang.String className) {
        return org.acm.seguin.summary.query.PackageContainsClass.query(org.acm.seguin.summary.PackageSummary.getPackageSummary(packageName), className);
    }

    /**
     * Checks to see if the package contains a class with that name
     *
     * @param summary
     * 		the summary of the package
     * @param className
     * 		the name of the class
     * @return true if it is included
     */
    public static boolean query(org.acm.seguin.summary.PackageSummary summary, java.lang.String className) {
        java.util.Iterator iter = summary.getFileSummaries();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.FileSummary next = ((org.acm.seguin.summary.FileSummary) (iter.next()));
                if (org.acm.seguin.summary.query.PackageContainsClass.checkFile(next, className)) {
                    return true;
                }
            } 
        }
        return false;
    }

    /**
     * Checks a single file for the class
     *
     * @param summary
     * 		the file summary
     * @param className
     * 		the name of the class
     * @return true if this particular file contains that class
     */
    private static boolean checkFile(org.acm.seguin.summary.FileSummary summary, java.lang.String className) {
        java.util.Iterator iter = summary.getTypes();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
                if (next.getName().equals(className)) {
                    return true;
                }
            } 
        }
        return false;
    }
}