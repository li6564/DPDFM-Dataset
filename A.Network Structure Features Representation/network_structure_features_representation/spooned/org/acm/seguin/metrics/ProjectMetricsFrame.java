/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.metrics;
/**
 * Project metrics frame
 *
 * @author Chris Seguin
 * @created July 26, 1999
 */
public class ProjectMetricsFrame extends org.acm.seguin.metrics.MetricsFrame {
    // Instance Variables
    private org.acm.seguin.metrics.ProjectMetrics metrics;

    /**
     * Constructor for the ProjectMetricsFrame object
     */
    public ProjectMetricsFrame() {
        org.acm.seguin.metrics.GatherData data = new org.acm.seguin.metrics.GatherData(null);
        metrics = ((org.acm.seguin.metrics.ProjectMetrics) (data.visit("")));
        descriptions = new java.lang.String[]{ "Description", "Statement Total", "Statement Average", "Parameter Total", "Parameter Average", "Public Method Total", "Public Method Average", "Other Method Total", "Other Method Average", "Class Method Total", "Class Method Average", "Instance Variable Total", "Instance Variable Average", "Class Variable Total", "Class Variable Average", "Total Classes", "Abstract Class Total", "Percent Abstract Classes", "Interface Total", "Percent Interfaces" };
        // Fill in the value array
        values = new java.lang.String[20];
        values[0] = "Values";
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        values[1] = nf.format(metrics.getStatementTotal());
        values[2] = nf.format(metrics.getStatementAverage());
        values[3] = nf.format(metrics.getParameterTotal());
        values[4] = nf.format(metrics.getParameterAverage());
        values[5] = nf.format(metrics.getPublicMethodTotal());
        values[6] = nf.format(metrics.getPublicMethodAverage());
        values[7] = nf.format(metrics.getOtherMethodTotal());
        values[8] = nf.format(metrics.getOtherMethodAverage());
        values[9] = nf.format(metrics.getClassMethodTotal());
        values[10] = nf.format(metrics.getClassMethodAverage());
        values[11] = nf.format(metrics.getInstanceVariableTotal());
        values[12] = nf.format(metrics.getInstanceVariableAverage());
        values[13] = nf.format(metrics.getClassVariableTotal());
        values[14] = nf.format(metrics.getClassVariableAverage());
        values[15] = nf.format(metrics.getClassTotal());
        values[16] = nf.format(metrics.getAbstractClassTotal());
        values[17] = nf.format(metrics.getAbstractClassPercentage()) + " %";
        values[18] = nf.format(metrics.getInterfaceTotal());
        values[19] = nf.format(metrics.getInterfacePercentage()) + " %";
        // Create the frame
        createFrame();
    }

    /**
     * Returns the title of this frame
     *
     * @return Description of the Returned Value
     */
    protected java.lang.String getTitle() {
        return "Metrics for the project";
    }
}