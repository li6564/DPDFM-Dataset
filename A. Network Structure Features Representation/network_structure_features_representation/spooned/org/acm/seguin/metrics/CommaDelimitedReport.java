/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.metrics;
/**
 * Reports metrics in a comma delimited format
 *
 * @author Chris Seguin
 * @created July 1, 1999
 */
public class CommaDelimitedReport extends org.acm.seguin.metrics.MetricsReport {
    /**
     * Constructor for the CommaDelimitedReport object
     */
    public CommaDelimitedReport() {
        java.lang.System.out.println("Metric Code,Value,Package,Type,Method,Raw/Avg");
    }

    /**
     * Make a final report on totals
     *
     * @param projectData
     * 		Description of Parameter
     */
    public void finalReport(org.acm.seguin.metrics.ProjectMetrics projectData) {
        super.finalReport(projectData);
        printKey();
    }

    /**
     * Prints out the key
     */
    private void printKey() {
        java.lang.System.out.println(" ");
        java.lang.System.out.println(" ");
        java.lang.System.out.println("Key");
        java.lang.System.out.println(",Metric Code,Description");
        java.lang.System.out.println(",000,Statement Count");
        java.lang.System.out.println(",001,Number of Public Methods");
        java.lang.System.out.println(",002,Number of Other Methods");
        java.lang.System.out.println(",003,Number of Class Methods");
        java.lang.System.out.println(",004,Number of Instance Variables");
        java.lang.System.out.println(",005,Number of Class Variables");
        java.lang.System.out.println(",006,Number of Abstract Classes");
        java.lang.System.out.println(",007,Number of Interfaces");
        java.lang.System.out.println(",008,Parameter Count");
        java.lang.System.out.println(",009,Total Classes");
        java.lang.System.out.println(",010,Lines of Code");
        java.lang.System.out.println(",011,Block Depth");
    }

    /**
     * Reports on the number of statements
     *
     * @param pack
     * 		the name of the package
     * @param type
     * 		the name of the class or interface
     * @param name
     * 		the name of the method
     * @param count
     * 		the number of statements
     */
    protected void reportStatement(java.lang.String pack, java.lang.String type, java.lang.String name, int count) {
        java.lang.System.out.println(((((((("000," + count) + ",") + pack) + ",") + type) + ",") + name) + ",raw");
    }

    /**
     * Reports on the number of parameters
     *
     * @param pack
     * 		the name of the package
     * @param type
     * 		the name of the class or interface
     * @param name
     * 		the name of the method
     * @param count
     * 		the number of parameters
     */
    protected void reportParameters(java.lang.String pack, java.lang.String type, java.lang.String name, int count) {
        java.lang.System.out.println(((((((("008," + count) + ",") + pack) + ",") + type) + ",") + name) + ",raw");
    }

    /**
     * Reports on the number of lines of code
     *
     * @param pack
     * 		the name of the package
     * @param type
     * 		the name of the class or interface
     * @param name
     * 		the name of the method
     * @param count
     * 		the number of lines of code
     */
    protected void reportLinesOfCode(java.lang.String pack, java.lang.String type, java.lang.String name, int count) {
        java.lang.System.out.println(((((((("010," + count) + ",") + pack) + ",") + type) + ",") + name) + ",raw");
    }

    /**
     * Reports on the block depth of code
     *
     * @param pack
     * 		the name of the package
     * @param type
     * 		the name of the class or interface
     * @param name
     * 		the name of the method
     * @param count
     * 		the number of blocks deep
     */
    protected void reportBlockDepth(java.lang.String pack, java.lang.String type, java.lang.String name, int count) {
        java.lang.System.out.println(((((((("011," + count) + ",") + pack) + ",") + type) + ",") + name) + ",raw");
    }

    /**
     * Reports on the number of public methods
     *
     * @param pack
     * 		the name of the package
     * @param type
     * 		the name of the class or interface
     * @param count
     * 		the number of public methods
     */
    protected void reportPublicMethods(java.lang.String pack, java.lang.String type, int count) {
        java.lang.System.out.println(((((("001," + count) + ",") + pack) + ",") + type) + ",---,raw");
    }

    /**
     * Reports on the number of other methods
     *
     * @param pack
     * 		the name of the package
     * @param type
     * 		the name of the class or interface
     * @param count
     * 		the number of other methods
     */
    protected void reportOtherMethods(java.lang.String pack, java.lang.String type, int count) {
        java.lang.System.out.println(((((("002," + count) + ",") + pack) + ",") + type) + ",---,raw");
    }

    /**
     * Reports on the number of class methods
     *
     * @param pack
     * 		the name of the package
     * @param type
     * 		the name of the class or interface
     * @param count
     * 		the number of class methods
     */
    protected void reportClassMethods(java.lang.String pack, java.lang.String type, int count) {
        java.lang.System.out.println(((((("003," + count) + ",") + pack) + ",") + type) + ",---,raw");
    }

    /**
     * Reports on the number of instance variables
     *
     * @param pack
     * 		the name of the package
     * @param type
     * 		the name of the class or interface
     * @param count
     * 		the number of instance variables
     */
    protected void reportInstanceVariables(java.lang.String pack, java.lang.String type, int count) {
        java.lang.System.out.println(((((("004," + count) + ",") + pack) + ",") + type) + ",---,raw");
    }

    /**
     * Reports on the number of class variables
     *
     * @param pack
     * 		the name of the package
     * @param type
     * 		the name of the class or interface
     * @param count
     * 		the number of class variables
     */
    protected void reportClassVariables(java.lang.String pack, java.lang.String type, int count) {
        java.lang.System.out.println(((((("005," + count) + ",") + pack) + ",") + type) + ",---,raw");
    }

    /**
     * Reports on the number of abstract classes
     *
     * @param projectData
     * 		Description of Parameter
     */
    protected void reportAbstractClasses(org.acm.seguin.metrics.ProjectMetrics projectData) {
        // Abstract Classes
        double top = projectData.getAbstractClassTotal();
        double bottom = projectData.getClassTotal();
        java.lang.System.out.println(("006," + projectData.getAbstractClassTotal()) + ",---,---,---,raw");
        java.lang.System.out.println(("006," + ((100 * top) / bottom)) + ",---,---,---,percent");
    }

    /**
     * Reports on the number of interfaces
     *
     * @param projectData
     * 		Description of Parameter
     */
    protected void reportInterfaces(org.acm.seguin.metrics.ProjectMetrics projectData) {
        // Interfaces
        double top = projectData.getInterfaceTotal();
        double bottom = projectData.getClassTotal();
        java.lang.System.out.println(("007," + projectData.getInterfaceTotal()) + ",---,---,---,raw");
        java.lang.System.out.println(("007," + ((100 * top) / bottom)) + ",---,---,---,percent");
    }

    /**
     * Reports on the number of classes
     *
     * @param projectData
     * 		Description of Parameter
     */
    protected void reportClasses(org.acm.seguin.metrics.ProjectMetrics projectData) {
        java.lang.System.out.println(("009," + projectData.getClassTotal()) + ",---,---,---,raw");
    }

    /**
     * Reports on the average number of statements
     *
     * @param projectData
     * 		Description of Parameter
     */
    protected void reportAverageStatements(org.acm.seguin.metrics.ProjectMetrics projectData) {
        // Public Methods
        double top = projectData.getStatementTotal();
        double bottom = projectData.getMethodTotal();
        java.lang.System.out.println(("000," + projectData.getStatementTotal()) + ",---,---,---,total");
        java.lang.System.out.println(("000," + (top / bottom)) + ",---,---,---,avg");
    }

    /**
     * Reports on the average number of parameters
     *
     * @param projectData
     * 		Description of Parameter
     */
    protected void reportAverageParameters(org.acm.seguin.metrics.ProjectMetrics projectData) {
        // Public Methods
        double top = projectData.getParameterTotal();
        double bottom = projectData.getMethodTotal();
        java.lang.System.out.println(("008," + projectData.getParameterTotal()) + ",---,---,---,total");
        java.lang.System.out.println(("008," + (top / bottom)) + ",---,---,---,avg");
    }

    /**
     * Reports on the average number of public methods
     *
     * @param projectData
     * 		Description of Parameter
     */
    protected void reportAveragePublicMethods(org.acm.seguin.metrics.ProjectMetrics projectData) {
        // Public Methods
        double top = projectData.getPublicMethodTotal();
        double bottom = projectData.getClassTotal();
        java.lang.System.out.println(("001," + projectData.getPublicMethodTotal()) + ",---,---,---,total");
        java.lang.System.out.println(("001," + (top / bottom)) + ",---,---,---,avg");
    }

    /**
     * Reports on the average number of other methods
     *
     * @param projectData
     * 		Description of Parameter
     */
    protected void reportAverageOtherMethods(org.acm.seguin.metrics.ProjectMetrics projectData) {
        // Other Methods
        double top = projectData.getOtherMethodTotal();
        double bottom = projectData.getClassTotal();
        java.lang.System.out.println(("002," + projectData.getOtherMethodTotal()) + ",---,---,---,total");
        java.lang.System.out.println(("002," + (top / bottom)) + ",---,---,---,avg");
    }

    /**
     * Reports on the average number of class methods
     *
     * @param projectData
     * 		Description of Parameter
     */
    protected void reportAverageClassMethods(org.acm.seguin.metrics.ProjectMetrics projectData) {
        // Class Methods
        double top = projectData.getClassMethodTotal();
        double bottom = projectData.getClassTotal();
        java.lang.System.out.println(("003," + projectData.getClassMethodTotal()) + ",---,---,---,total");
        java.lang.System.out.println(("003," + (top / bottom)) + ",---,---,---,avg");
    }

    /**
     * Reports on the average number of instance variables
     *
     * @param projectData
     * 		Description of Parameter
     */
    protected void reportAverageInstanceVariables(org.acm.seguin.metrics.ProjectMetrics projectData) {
        // Instance Variables
        double top = projectData.getInstanceVariableTotal();
        double bottom = projectData.getClassTotal();
        java.lang.System.out.println(("004," + projectData.getInstanceVariableTotal()) + ",---,---,---,total");
        java.lang.System.out.println(("004," + (top / bottom)) + ",---,---,---,avg");
    }

    /**
     * Reports on the average number of class variables
     *
     * @param projectData
     * 		Description of Parameter
     */
    protected void reportAverageClassVariables(org.acm.seguin.metrics.ProjectMetrics projectData) {
        // Class Variables
        double top = projectData.getClassVariableTotal();
        double bottom = projectData.getClassTotal();
        java.lang.System.out.println(("005," + projectData.getClassVariableTotal()) + ",---,---,---,total");
        java.lang.System.out.println(("005," + (top / bottom)) + ",---,---,---,avg");
    }
}