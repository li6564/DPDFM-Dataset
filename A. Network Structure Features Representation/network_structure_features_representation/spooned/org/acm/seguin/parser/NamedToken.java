/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 * @created October 14, 1999
 */
public class NamedToken {
    // Instance Variables
    private java.lang.String id;

    private org.acm.seguin.parser.Token token;

    /**
     * Creates a named token
     *
     * @param initID
     * 		the id
     * @param initToken
     * 		the token
     */
    public NamedToken(java.lang.String initID, org.acm.seguin.parser.Token initToken) {
        id = initID.intern();
        token = initToken;
    }

    /**
     * Return the id
     *
     * @return the id
     */
    public java.lang.String getID() {
        return id;
    }

    /**
     * Return the token
     *
     * @return the token
     */
    public org.acm.seguin.parser.Token getToken() {
        return token;
    }

    /**
     * Check if the id matches
     *
     * @param match
     * 		does it match
     * @return true if it matches
     */
    public boolean check(java.lang.String match) {
        return match.equals(id);
    }
}