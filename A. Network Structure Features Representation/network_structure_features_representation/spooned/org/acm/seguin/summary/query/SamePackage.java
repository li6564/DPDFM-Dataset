/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
/**
 * Determines if the package name matches the summary
 *
 * @author Chris Seguin
 * @created November 28, 1999
 */
public class SamePackage {
    /**
     * Check to see if they are the same package
     *
     * @param packageName
     * 		the name of the package
     * @param summary
     * 		the summary
     * @return true if they come from the same package
     */
    public static boolean query(java.lang.String packageName, org.acm.seguin.summary.FileSummary summary) {
        org.acm.seguin.summary.PackageSummary parent = ((org.acm.seguin.summary.PackageSummary) (summary.getParent()));
        return packageName.equals(parent.getName());
    }

    /**
     * Check to see if they are the same package
     *
     * @param packageName
     * 		the name of the package
     * @param summary
     * 		the summary
     * @return true if they come from the same package
     */
    public static boolean query(java.lang.String packageName, org.acm.seguin.summary.TypeSummary summary) {
        org.acm.seguin.summary.PackageSummary parent = org.acm.seguin.summary.query.GetPackageSummary.query(summary);
        return packageName.equals(parent.getName());
    }

    /**
     * Check to see if they are the same package
     *
     * @param one
     * 		Description of Parameter
     * @param two
     * 		Description of Parameter
     * @return true if they come from the same package
     */
    public static boolean query(org.acm.seguin.summary.TypeSummary one, org.acm.seguin.summary.TypeSummary two) {
        if ((one == null) || (two == null)) {
            return false;
        }
        org.acm.seguin.summary.PackageSummary firstPackage = org.acm.seguin.summary.query.SamePackage.getPackageSummary(one);
        org.acm.seguin.summary.PackageSummary secondPackage = org.acm.seguin.summary.query.SamePackage.getPackageSummary(two);
        return firstPackage.equals(secondPackage);
    }

    /**
     * Gets the package summary
     *
     * @param base
     * 		Description of Parameter
     * @return the package summary
     */
    private static org.acm.seguin.summary.PackageSummary getPackageSummary(org.acm.seguin.summary.Summary base) {
        org.acm.seguin.summary.Summary current = base;
        while (!(current instanceof org.acm.seguin.summary.PackageSummary)) {
            current = current.getParent();
        } 
        return ((org.acm.seguin.summary.PackageSummary) (current));
    }
}