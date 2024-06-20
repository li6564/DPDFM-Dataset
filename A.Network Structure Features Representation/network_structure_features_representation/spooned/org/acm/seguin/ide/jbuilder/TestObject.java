/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
public class TestObject {
    /**
     * Description of the Method
     *
     * @param other
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public boolean equals(java.lang.Object other) {
        java.lang.System.out.println("Comparing to: " + other.toString());
        java.lang.System.out.println("  is a " + other.getClass().getName());
        return false;
    }
}