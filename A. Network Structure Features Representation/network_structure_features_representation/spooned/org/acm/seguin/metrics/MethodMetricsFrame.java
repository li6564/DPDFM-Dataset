/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.metrics;
/**
 * Base class for metrics frame
 *
 * @author Chris Seguin
 * @created July 26, 1999
 */
public class MethodMetricsFrame extends org.acm.seguin.metrics.MetricsFrame {
    // Instance Variables
    private org.acm.seguin.summary.MethodSummary method;

    private org.acm.seguin.metrics.MethodMetrics metrics;

    /**
     * Constructor for the MethodMetricsFrame object
     *
     * @param initMethod
     * 		Description of Parameter
     */
    public MethodMetricsFrame(org.acm.seguin.summary.MethodSummary initMethod) {
        method = initMethod;
        org.acm.seguin.metrics.TypeMetrics temp = new org.acm.seguin.metrics.TypeMetrics("-package-", "-type-");
        org.acm.seguin.metrics.GatherData data = new org.acm.seguin.metrics.GatherData(null);
        metrics = ((org.acm.seguin.metrics.MethodMetrics) (data.visit(method, temp)));
        descriptions = new java.lang.String[]{ "Description", "Statement Count", "Parameter Count" };
        values = new java.lang.String[3];
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        values[0] = "Values";
        values[1] = "" + nf.format(metrics.getStatementCount());
        values[2] = "" + nf.format(metrics.getParameterCount());
        createFrame();
    }

    /**
     * Returns the title of this frame
     *
     * @return Description of the Returned Value
     */
    protected java.lang.String getTitle() {
        return "Metrics for the method " + method.getName();
    }
}