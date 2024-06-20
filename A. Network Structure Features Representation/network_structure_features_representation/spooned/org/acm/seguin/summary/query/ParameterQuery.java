/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
/**
 * Searches for a parameter with a name in a method
 *
 * @author Chris Seguin
 */
public class ParameterQuery {
    /**
     * Perform the search
     *
     * @param summary
     * 		Description of Parameter
     * @param name
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.summary.ParameterSummary query(org.acm.seguin.summary.MethodSummary summary, java.lang.String name) {
        java.util.Iterator iter = summary.getParameters();
        while (iter.hasNext()) {
            org.acm.seguin.summary.ParameterSummary param = ((org.acm.seguin.summary.ParameterSummary) (iter.next()));
            if (param.getName().equals(name)) {
                return param;
            }
        } 
        return null;
    }
}