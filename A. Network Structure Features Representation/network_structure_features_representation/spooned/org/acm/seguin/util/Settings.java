/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.util;
/**
 * The settings interface
 *
 * @author Chris Seguin
 * @created October 3, 1999
 */
public interface Settings {
    /**
     * Gets a string
     *
     * @param code
     * 		The code to look up
     * @return The associated string
     */
    public java.lang.String getString(java.lang.String code);

    /**
     * Gets a integer
     *
     * @param code
     * 		The code to look up
     * @return The associated integer
     */
    public int getInteger(java.lang.String code);

    /**
     * Gets a double
     *
     * @param code
     * 		The code to look up
     * @return The associated double
     */
    public double getDouble(java.lang.String code);

    /**
     * Gets a boolean
     *
     * @param code
     * 		The code to look up
     * @return The associated boolean
     */
    public boolean getBoolean(java.lang.String code);
}