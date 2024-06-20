/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common;
/**
 * Base class for source browsing. This is the generic base class.
 *
 * @author Chris Seguin
 */
public abstract class SourceBrowser {
    private static org.acm.seguin.ide.common.SourceBrowser singleton = null;

    /**
     * Determines if the system is in a state where it can browse the source
     *  code
     *
     * @return true if the source code browsing is enabled
     */
    public abstract boolean canBrowseSource();

    /**
     * Actually browses to the file
     *
     * @param line
     * 		the line in the file
     * @param file
     * 		Description of Parameter
     */
    public abstract void gotoSource(java.io.File file, int line);

    /**
     * Sets the singleton source browser
     *
     * @param value
     * 		the new singleton
     */
    public static void set(org.acm.seguin.ide.common.SourceBrowser value) {
        org.acm.seguin.ide.common.SourceBrowser.singleton = value;
    }

    /**
     * Gets the singleton source browser
     *
     * @return the current source browser
     */
    public static org.acm.seguin.ide.common.SourceBrowser get() {
        if (org.acm.seguin.ide.common.SourceBrowser.singleton == null) {
            org.acm.seguin.ide.common.SourceBrowser.singleton = new org.acm.seguin.ide.common.NoSourceBrowser();
        }
        return org.acm.seguin.ide.common.SourceBrowser.singleton;
    }
}