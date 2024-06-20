/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
/**
 * Determines if a particular type is imported
 *
 * @author Chris Seguin
 */
public class ImportsType {
    /**
     * Checks to see if the type is imported
     *
     * @param summary
     * 		Description of Parameter
     * @param type
     * 		Description of Parameter
     * @return true if it is imported
     */
    public static boolean query(org.acm.seguin.summary.Summary summary, org.acm.seguin.summary.TypeSummary type) {
        // Check the special cases first
        org.acm.seguin.summary.PackageSummary packageSummary = org.acm.seguin.summary.query.ImportsType.getPackageSummary(type);
        if (packageSummary.getName().equals("java.lang")) {
            return true;
        }
        org.acm.seguin.summary.PackageSummary destPackage = org.acm.seguin.summary.query.ImportsType.getPackageSummary(summary);
        if (packageSummary == destPackage) {
            return true;
        }
        // Now we need to search the list of imports
        org.acm.seguin.summary.FileSummary fileSummary = org.acm.seguin.summary.query.ImportsType.getFileSummary(summary);
        java.util.Iterator iter = fileSummary.getImports();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.ImportSummary next = ((org.acm.seguin.summary.ImportSummary) (iter.next()));
                if (packageSummary == next.getPackage()) {
                    if (next.getType() == null) {
                        return true;
                    } else if (next.getType().equals(type.getName())) {
                        return true;
                    }
                }
            } 
        }
        return false;
    }

    /**
     * Gets the FileSummary attribute of the ImportsType class
     *
     * @param summary
     * 		Description of Parameter
     * @return The FileSummary value
     */
    private static org.acm.seguin.summary.FileSummary getFileSummary(org.acm.seguin.summary.Summary summary) {
        org.acm.seguin.summary.Summary current = summary;
        while ((current != null) && (!(current instanceof org.acm.seguin.summary.FileSummary))) {
            current = current.getParent();
        } 
        return ((org.acm.seguin.summary.FileSummary) (current));
    }

    /**
     * Gets the PackageSummary attribute of the ImportsType class
     *
     * @param summary
     * 		Description of Parameter
     * @return The PackageSummary value
     */
    private static org.acm.seguin.summary.PackageSummary getPackageSummary(org.acm.seguin.summary.Summary summary) {
        org.acm.seguin.summary.Summary current = summary;
        while ((current != null) && (!(current instanceof org.acm.seguin.summary.PackageSummary))) {
            current = current.getParent();
        } 
        return ((org.acm.seguin.summary.PackageSummary) (current));
    }
}