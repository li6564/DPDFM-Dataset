/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.tools.install;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
public class RefactoryStorage {
    private java.util.HashMap map;

    /**
     * Constructor for the RefactoryStorage object
     */
    public RefactoryStorage() {
        map = new java.util.HashMap();
        load();
    }

    /**
     * Gets the Value attribute of the RefactoryStorage object
     *
     * @param key
     * 		Description of Parameter
     * @return The Value value
     */
    public int getValue(java.lang.String key) {
        java.lang.Object obj = map.get(normalize(key));
        if (obj == null) {
            return 1000;
        }
        return ((java.lang.Integer) (obj)).intValue();
    }

    /**
     * Adds a feature to the Key attribute of the RefactoryStorage object
     *
     * @param key
     * 		The feature to be added to the Key attribute
     * @param value
     * 		The feature to be added to the Key attribute
     */
    public void addKey(java.lang.String key, int value) {
        map.put(normalize(key), new java.lang.Integer(value));
    }

    /**
     * Description of the Method
     */
    public void store() {
        try {
            java.lang.String dir = (org.acm.seguin.util.FileSettings.getSettingsRoot() + java.io.File.separator) + ".Refactory";
            java.lang.String filename = (dir + java.io.File.separator) + "refactory.settings";
            java.io.PrintWriter output = new java.io.PrintWriter(new java.io.FileWriter(filename));
            java.util.Iterator iter = map.keySet().iterator();
            while (iter.hasNext()) {
                java.lang.String next = ((java.lang.String) (iter.next()));
                output.println((next + "=") + map.get(next));
            } 
            output.close();
        } catch (java.io.IOException ioe) {
            org.acm.seguin.awt.ExceptionPrinter.print(ioe);
        }
    }

    /**
     * Description of the Method
     *
     * @param input
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private java.lang.String normalize(java.lang.String input) {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        for (int ndx = 0; ndx < input.length(); ndx++) {
            char ch = input.charAt(ndx);
            if (java.lang.Character.isJavaIdentifierPart(ch) || (ch == '.')) {
                buffer.append(ch);
            } else {
                buffer.append('_');
            }
        }
        return buffer.toString();
    }

    /**
     * Description of the Method
     */
    private void load() {
        org.acm.seguin.util.FileSettings settings = org.acm.seguin.util.FileSettings.getSettings("Refactory", "refactory");
        Enumeration = settings.getKeys();
        // Reasonable
    }
}