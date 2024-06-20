/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.util;
/**
 * A missing setting was discovered
 *
 * @author Chris Seguin
 * @created October 3, 1999
 */
public class MissingSettingsException extends java.lang.RuntimeException {
    private java.lang.String type;

    private java.lang.String app;

    /**
     * Constructor for the MissingSettingsException object
     *
     * @param app
     * 		the name of the application
     * @param type
     * 		the name of the type
     */
    public MissingSettingsException(java.lang.String app, java.lang.String type) {
        this.type = type;
        this.app = app;
    }

    /**
     * Returns a message describing this exception
     *
     * @return the message
     */
    protected java.lang.String getType() {
        return type;
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    protected java.lang.String getApplication() {
        return app;
    }
}