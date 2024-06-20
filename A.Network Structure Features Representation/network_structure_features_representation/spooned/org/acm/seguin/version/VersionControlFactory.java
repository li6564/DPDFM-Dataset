/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.version;
/**
 * A factory for the version control system
 *
 * @author Chris Seguin
 */
public class VersionControlFactory {
    private static org.acm.seguin.version.VersionControl singleton = null;

    /**
     * Constructor for the VersionControlFactory object
     */
    private VersionControlFactory() {
    }

    /**
     * Gets the current version control system
     *
     * @return the system
     */
    public static org.acm.seguin.version.VersionControl get() {
        if (org.acm.seguin.version.VersionControlFactory.singleton == null) {
            org.acm.seguin.version.VersionControlFactory.init();
        }
        return org.acm.seguin.version.VersionControlFactory.singleton;
    }

    /**
     * Description of the Method
     */
    private static synchronized void init() {
        if (org.acm.seguin.version.VersionControlFactory.singleton == null) {
            try {
                org.acm.seguin.util.FileSettings bundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "vss");
                java.lang.String className = bundle.getString("version.control");
                org.acm.seguin.version.VersionControlFactory.singleton = ((org.acm.seguin.version.VersionControl) (java.lang.Class.forName(className).newInstance()));
            } catch (java.lang.Exception exc) {
                org.acm.seguin.version.VersionControlFactory.singleton = new org.acm.seguin.version.UserDirectedVersionControl();
            }
        }
    }
}