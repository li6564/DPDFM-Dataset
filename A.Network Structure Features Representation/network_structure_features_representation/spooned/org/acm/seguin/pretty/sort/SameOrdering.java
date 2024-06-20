/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty.sort;
/**
 * Does not change the order
 *
 * @author Chris Seguin
 */
public class SameOrdering extends org.acm.seguin.pretty.sort.Ordering {
    /**
     * Return the index of the item in the order array
     *
     * @param object
     * 		the object we are checking
     * @return the objects index if it is found or 7 if it is not
     */
    protected int getIndex(java.lang.Object object) {
        return 0;
    }
}