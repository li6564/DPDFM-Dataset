/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Determines if there is a problem in performing this refactoring
 *  because of a near miss
 *
 * @author Chris Seguin
 */
public class NearMissVisitor extends org.acm.seguin.summary.TraversalVisitor {
    private org.acm.seguin.summary.MethodSummary target;

    private org.acm.seguin.summary.TypeSummary ancestor;

    private org.acm.seguin.summary.TypeSummary notHere;

    private org.acm.seguin.summary.MethodSummary problem;

    /**
     * Constructor for the NearMissVisitor object
     *
     * @param type
     * 		the ancestor type
     * @param init
     * 		the method
     * @param notThisOne
     * 		a type to skip
     */
    public NearMissVisitor(org.acm.seguin.summary.TypeSummary type, org.acm.seguin.summary.MethodSummary init, org.acm.seguin.summary.TypeSummary notThisOne) {
        target = init;
        ancestor = type;
        notHere = notThisOne;
        problem = null;
    }

    /**
     * Visits a type summary and updates it
     *
     * @param typeSummary
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.summary.TypeSummary typeSummary, java.lang.Object data) {
        if ((typeSummary != notHere) && org.acm.seguin.summary.query.Ancestor.query(typeSummary, ancestor)) {
            java.util.Iterator iter = typeSummary.getMethods();
            if (iter != null) {
                while (iter.hasNext()) {
                    visit(((org.acm.seguin.summary.MethodSummary) (iter.next())), data);
                } 
            }
        }
        return data;
    }

    /**
     * Visits the method summary and determines if it should be removed.
     *
     * @param methodSummary
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.summary.MethodSummary methodSummary, java.lang.Object data) {
        if (methodSummary.isNearMiss(target)) {
            problem = methodSummary;
        }
        return data;
    }

    /**
     * Returns at least one near miss conflict
     *
     * @return the problem method
     */
    public org.acm.seguin.summary.MethodSummary getProblem() {
        return problem;
    }
}