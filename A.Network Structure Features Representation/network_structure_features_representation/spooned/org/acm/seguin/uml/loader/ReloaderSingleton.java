/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.uml.loader;
/**
 * Stores the current reloader
 *
 * @author Chris Seguin
 */
public class ReloaderSingleton {
    private static boolean isFirstTime = true;

    private static org.acm.seguin.uml.loader.Reloader singleton;

    /**
     * Don't allow an instance of this object to be instantiated.
     */
    private ReloaderSingleton() {
    }

    /**
     * Reload the UML class diagrams
     */
    public static void reload() {
        /* if (isFirstTime) {
        isFirstTime = false;
        (new RapidLoader()).load();
        }
         */
        if (org.acm.seguin.uml.loader.ReloaderSingleton.singleton != null) {
            org.acm.seguin.uml.loader.ReloaderSingleton.singleton.reload();
            // (new RapidLoader()).save();
        }
    }

    /**
     * Register a reloader for the class diagrams
     *
     * @param init
     * 		the reloader object
     */
    public static void register(org.acm.seguin.uml.loader.Reloader init) {
        org.acm.seguin.uml.loader.ReloaderSingleton.singleton = init;
    }
}