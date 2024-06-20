/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common;
/**
 * The package list filter that determines if the package should be included
 *  in the PackageSelectorArea.
 *
 * @author Chris Seguin
 */
public abstract class PackageListFilter {
    private static org.acm.seguin.ide.common.PackageListFilter singleton;

    /**
     * Returns true if we should include it
     *
     * @param summary
     * 		the summary in question
     * @return true if it should be included
     */
    public abstract boolean isIncluded(org.acm.seguin.summary.PackageSummary summary);

    /**
     * Sets the Singleton attribute of the PackageListFilter class
     *
     * @param value
     * 		The new Singleton value
     */
    public static void setSingleton(org.acm.seguin.ide.common.PackageListFilter value) {
        org.acm.seguin.ide.common.PackageListFilter.singleton = value;
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.ide.common.PackageListFilter get() {
        if (org.acm.seguin.ide.common.PackageListFilter.singleton == null) {
            org.acm.seguin.ide.common.PackageListFilter.singleton = new org.acm.seguin.ide.common.DefaultPackageListFilter();
        }
        return org.acm.seguin.ide.common.PackageListFilter.singleton;
    }
}