/**
 * Gathers metrics data
 *
 * @author Chris Seguin
 * @created July 25, 1999
 */
public class Metrics {
    /**
     * Main program
     *
     * @param args
     * 		the command line arguments
     */
    public static void main(java.lang.String[] args) {
        // Make sure everything is installed properly
        new org.acm.seguin.tools.install.RefactoryInstaller(false).run();
        // Local Variables
        java.lang.String startDir = java.lang.System.getProperty("user.dir");
        int type = 0;
        org.acm.seguin.summary.load.LoadStatus status = new org.acm.seguin.summary.load.TextLoadStatus();
        // Process command line arguments
        int argIndex = 0;
        while (argIndex < args.length) {
            if (args[argIndex].equals("-help")) {
                java.lang.System.out.println("Syntax:  java Metrics [-text|-comma] [dir]");
                return;
            } else if (args[argIndex].equals("-text")) {
                type = 0;
                argIndex++;
            } else if (args[argIndex].equals("-comma")) {
                type = 1;
                argIndex++;
            } else if (args[argIndex].equals("-quiet")) {
                status = new org.acm.seguin.summary.load.SilentLoadStatus();
                argIndex++;
            } else {
                startDir = args[argIndex];
                argIndex++;
            }
        } 
        // Gather the summary
        new org.acm.seguin.summary.SummaryTraversal(startDir, status).go();
        // Now print everything
        org.acm.seguin.metrics.MetricsReport metricsReport;
        if (type == 0) {
            metricsReport = new org.acm.seguin.metrics.TextReport();
        } else {
            metricsReport = new org.acm.seguin.metrics.CommaDelimitedReport();
        }
        org.acm.seguin.metrics.GatherData visitor = new org.acm.seguin.metrics.GatherData(metricsReport);
        org.acm.seguin.metrics.ProjectMetrics projectData = ((org.acm.seguin.metrics.ProjectMetrics) (visitor.visit("")));
        metricsReport.finalReport(projectData);
    }
}