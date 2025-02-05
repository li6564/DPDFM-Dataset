/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.util;
/**
 * Reports that the settings is not found
 *
 * @author Chris Seguin
 * @created October 3, 1999
 */
public class SettingNotFoundException extends org.acm.seguin.util.MissingSettingsException {
    private java.lang.String key;

    /**
     * Constructor for the SettingNotFoundException object
     *
     * @param app
     * 		the name of the application
     * @param type
     * 		the name of the type
     * @param key
     * 		the key
     */
    public SettingNotFoundException(java.lang.String app, java.lang.String type, java.lang.String key) {
        super(app, type);
        this.key = key;
    }

    /**
     * Returns a message describing this exception
     *
     * @return the message
     */
    public java.lang.String getMessage() {
        return (((("The key " + key) + " was not found for the application:  ") + getApplication()) + " with the name ") + getType();
    }
}