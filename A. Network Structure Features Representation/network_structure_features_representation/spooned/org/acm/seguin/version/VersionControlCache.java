/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.version;
/**
 * Cache of files - so that it doesn't take so long to get the menu
 *
 * @author Chris Seguin
 * @created June 17, 1999
 */
public class VersionControlCache {
    // Instance Variables
    private java.util.Hashtable cache;

    /**
     * Description of the Field
     */
    public static final int ADD = 0;

    /**
     * Description of the Field
     */
    public static final int CHECK_IN = 1;

    /**
     * Description of the Field
     */
    public static final int CHECK_OUT = 2;

    /**
     * Description of the Field
     */
    public static final int CHECK_IN_PROGRESS = 3;

    /**
     * Description of the Field
     */
    public static final int CHECK_OUT_PROGRESS = 4;

    /**
     * Description of the Field
     */
    public static final int ADD_PROGRESS = 5;

    // Class Variables
    private static org.acm.seguin.version.VersionControlCache ssc = null;

    /**
     * Constructor for the VersionControlCache object
     */
    protected VersionControlCache() {
        cache = new java.util.Hashtable();
    }

    /**
     * Looks up a file in the cache
     *
     * @param filename
     * 		Description of Parameter
     * @return The InCache value
     */
    public boolean isInCache(java.lang.String filename) {
        return cache.get(filename) != null;
    }

    /**
     * Looks up a file in the cache
     *
     * @param filename
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public int lookup(java.lang.String filename) {
        java.lang.Integer stored = ((java.lang.Integer) (cache.get(filename)));
        if (stored == null) {
            return org.acm.seguin.version.VersionControlCache.ADD;
        }
        return stored.intValue();
    }

    /**
     * Add to the cache
     *
     * @param filename
     * 		the name of the file
     * @param type
     * 		the state
     */
    public void add(java.lang.String filename, int type) {
        cache.put(filename, new java.lang.Integer(type));
    }

    /**
     * Return the cache
     *
     * @return the cache
     */
    public static org.acm.seguin.version.VersionControlCache getCache() {
        if (org.acm.seguin.version.VersionControlCache.ssc == null) {
            org.acm.seguin.version.VersionControlCache.init();
        }
        return org.acm.seguin.version.VersionControlCache.ssc;
    }

    /**
     * Create a source safe cache
     */
    private static synchronized void init() {
        if (org.acm.seguin.version.VersionControlCache.ssc == null) {
            org.acm.seguin.version.VersionControlCache.ssc = new org.acm.seguin.version.VersionControlCache();
        }
    }
}