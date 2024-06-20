/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.metrics;
/**
 * Gathers metrics data
 *
 * @author Chris Seguin
 * @created July 1, 1999
 */
public class GatherData implements org.acm.seguin.summary.SummaryVisitor {
    // Instance Variables
    private org.acm.seguin.metrics.MetricsReport metricsReport;

    /**
     * Constructor for the StatementReportVisitor object
     *
     * @param init
     * 		Description of Parameter
     */
    public GatherData(org.acm.seguin.metrics.MetricsReport init) {
        metricsReport = init;
    }

    /**
     * Visit everything in all packages
     *
     * @param data
     * 		a data value
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(java.lang.Object data) {
        org.acm.seguin.metrics.ProjectMetrics projectData = new org.acm.seguin.metrics.ProjectMetrics();
        java.util.Iterator iter = org.acm.seguin.summary.PackageSummary.getAllPackages();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.PackageSummary next = ((org.acm.seguin.summary.PackageSummary) (iter.next()));
                next.accept(this, projectData);
            } 
        }
        return projectData;
    }

    /**
     * Visit a summary node. This is the default method.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.Summary node, java.lang.Object data) {
        // Shouldn't have to do anything about one of these nodes
        return data;
    }

    /**
     * Visit a package summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.PackageSummary node, java.lang.Object data) {
        org.acm.seguin.metrics.PackageMetrics packageData = new org.acm.seguin.metrics.PackageMetrics(node.getName());
        java.util.Iterator iter = node.getFileSummaries();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.FileSummary next = ((org.acm.seguin.summary.FileSummary) (iter.next()));
                next.accept(this, packageData);
            } 
        }
        // Add to total
        org.acm.seguin.metrics.ProjectMetrics projectData = ((org.acm.seguin.metrics.ProjectMetrics) (data));
        projectData.add(packageData);
        // Return the metrics gathered at this level
        return packageData;
    }

    /**
     * Visit a file summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FileSummary node, java.lang.Object data) {
        if (node.getFile() == null) {
            return data;
        }
        // Over the types
        java.util.Iterator iter = node.getTypes();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
                next.accept(this, data);
            } 
        }
        // Return some value
        return data;
    }

    /**
     * Visit a import summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.ImportSummary node, java.lang.Object data) {
        // No children so just return
        return data;
    }

    /**
     * Visit a type summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.TypeSummary node, java.lang.Object data) {
        // Local Variables
        org.acm.seguin.metrics.PackageMetrics packageData = ((org.acm.seguin.metrics.PackageMetrics) (data));
        org.acm.seguin.metrics.TypeMetrics typeData = new org.acm.seguin.metrics.TypeMetrics(packageData.getPackageName(), node.getName());
        // Over the fields
        java.util.Iterator iter = node.getFields();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.FieldSummary next = ((org.acm.seguin.summary.FieldSummary) (iter.next()));
                if (next.getModifiers().isStatic()) {
                    typeData.incrClassVariableCount();
                } else {
                    typeData.incrInstanceVariableCount();
                }
            } 
        }
        // Over the methods
        iter = node.getMethods();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
                next.accept(this, typeData);
                if (next.getModifiers().isStatic()) {
                    typeData.incrClassMethodCount();
                } else if (next.getModifiers().isPublic()) {
                    typeData.incrPublicMethodCount();
                } else {
                    typeData.incrOtherMethodCount();
                }
            } 
        }
        // Over the types
        iter = node.getTypes();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
                next.accept(this, data);
            } 
        }
        // Print the results
        if (metricsReport != null) {
            metricsReport.typeReport(typeData);
        }
        // Update the totals
        packageData.add(typeData);
        if (node.getModifiers().isAbstract()) {
            packageData.incrAbstractClassCount();
        }
        if (node.isInterface()) {
            packageData.incrInterfaceCount();
        }
        // Return the metrics gathered at this level
        return typeData;
    }

    /**
     * Visit a method summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.MethodSummary node, java.lang.Object data) {
        // Local Variables
        org.acm.seguin.metrics.TypeMetrics typeData = ((org.acm.seguin.metrics.TypeMetrics) (data));
        // Gather metrics
        int count = node.getStatementCount();
        int params = node.getParameterCount();
        // Create method metrics object
        org.acm.seguin.metrics.MethodMetrics methodMetrics = new org.acm.seguin.metrics.MethodMetrics(typeData.getPackageName(), typeData.getTypeName(), node.getName());
        methodMetrics.setStatementCount(count);
        methodMetrics.setParameterCount(params);
        methodMetrics.setLinesOfCode(node.getEndLine() - node.getStartLine());
        methodMetrics.setBlockDepth(node.getMaxBlockDepth());
        // Report the metrics
        if (metricsReport != null) {
            metricsReport.methodReport(methodMetrics);
        }
        // Type data
        typeData.add(methodMetrics);
        // Return the metrics collected
        return methodMetrics;
    }

    /**
     * Visit a field summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FieldSummary node, java.lang.Object data) {
        return data;
    }

    /**
     * Visit a parameter summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.ParameterSummary node, java.lang.Object data) {
        return data;
    }

    /**
     * Visit a local variable summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.LocalVariableSummary node, java.lang.Object data) {
        return data;
    }

    /**
     * Visit a variable summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.VariableSummary node, java.lang.Object data) {
        return data;
    }

    /**
     * Visit a type declaration summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.TypeDeclSummary node, java.lang.Object data) {
        return data;
    }

    /**
     * Visit a message send summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.MessageSendSummary node, java.lang.Object data) {
        return data;
    }

    /**
     * Visit a field access summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FieldAccessSummary node, java.lang.Object data) {
        return data;
    }

    /**
     * Main program
     *
     * @param args
     * 		the command line arguments
     */
    public static void main(java.lang.String[] args) {
        if (args.length == 0) {
            new org.acm.seguin.summary.SummaryTraversal(java.lang.System.getProperty("user.dir")).go();
        } else {
            new org.acm.seguin.summary.SummaryTraversal(args[0]).go();
        }
        // Now print everything
        org.acm.seguin.metrics.MetricsReport metricsReport = new org.acm.seguin.metrics.TextReport();
        org.acm.seguin.metrics.GatherData visitor = new org.acm.seguin.metrics.GatherData(metricsReport);
        org.acm.seguin.metrics.ProjectMetrics projectData = ((org.acm.seguin.metrics.ProjectMetrics) (visitor.visit("")));
        metricsReport.finalReport(projectData);
    }
}