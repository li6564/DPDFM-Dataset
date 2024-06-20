/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.metrics;
/**
 * Reports metrics in a text format
 *
 * @author Chris Seguin
 * @created July 1, 1999
 */
public class TextReport extends org.acm.seguin.metrics.MetricsReport {
    // Instance Variables
    private org.acm.seguin.util.TextFormatter format;

    /**
     * Constructor for the TextReport object
     */
    public TextReport() {
        format = new org.acm.seguin.util.TextFormatter();
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
        java.lang.System.out.println((((((("[000] " + format.rightJustifyNumber(count, 10)) + "  statements in ") + pack) + " . ") + type) + " . ") + name);
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
        java.lang.System.out.println((((((("[008] " + format.rightJustifyNumber(count, 10)) + "  parameters in ") + pack) + " . ") + type) + " . ") + name);
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
        java.lang.System.out.println((((((("[010] " + format.rightJustifyNumber(count, 10)) + "  lines of code in ") + pack) + " . ") + type) + " . ") + name);
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
        java.lang.System.out.println((((((("[011] " + format.rightJustifyNumber(count, 10)) + "  block depth in ") + pack) + " . ") + type) + " . ") + name);
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
        java.lang.System.out.println((((("[001] " + format.rightJustifyNumber(count, 10)) + " public methods in ") + pack) + " . ") + type);
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
        java.lang.System.out.println((((("[002] " + format.rightJustifyNumber(count, 10)) + " non public methods in ") + pack) + " . ") + type);
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
        java.lang.System.out.println((((("[003] " + format.rightJustifyNumber(count, 10)) + " static methods in ") + pack) + " . ") + type);
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
        java.lang.System.out.println((((("[004] " + format.rightJustifyNumber(count, 10)) + " instance variables in ") + pack) + " . ") + type);
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
        java.lang.System.out.println((((("[005] " + format.rightJustifyNumber(count, 10)) + " class variables in ") + pack) + " . ") + type);
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
        java.lang.System.out.println(((("[006] " + projectData.getAbstractClassTotal()) + " total abstract classes in ") + projectData.getClassTotal()) + " classes.");
        java.lang.System.out.println((("[006] " + "Percentage:  ") + ((100 * top) / bottom)) + "%");
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
        java.lang.System.out.println(((("[007] " + projectData.getInterfaceTotal()) + " total interfaces in ") + projectData.getClassTotal()) + " classes.");
        java.lang.System.out.println((("[007] " + "Percentage:  ") + ((100 * top) / bottom)) + "%");
    }

    /**
     * Reports on the number of classes
     *
     * @param projectData
     * 		Description of Parameter
     */
    protected void reportClasses(org.acm.seguin.metrics.ProjectMetrics projectData) {
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
        java.lang.System.out.println(((("[000] " + projectData.getStatementTotal()) + " total number of statements in ") + projectData.getMethodTotal()) + " methods.");
        java.lang.System.out.println(("[000] " + "Average:  ") + (top / bottom));
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
        java.lang.System.out.println(((("[008] " + projectData.getParameterTotal()) + " total number of parameters in ") + projectData.getMethodTotal()) + " methods.");
        java.lang.System.out.println(("[008] " + "Average:  ") + (top / bottom));
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
        java.lang.System.out.println(((("[001] " + projectData.getPublicMethodTotal()) + " total public methods in ") + projectData.getClassTotal()) + " classes.");
        java.lang.System.out.println(("[001] " + "Average:  ") + (top / bottom));
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
        java.lang.System.out.println(((("[002] " + projectData.getOtherMethodTotal()) + " total other methods in ") + projectData.getClassTotal()) + " classes.");
        java.lang.System.out.println(("[002] " + "Average:  ") + (top / bottom));
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
        java.lang.System.out.println(((("[003] " + projectData.getClassMethodTotal()) + " total class methods in ") + projectData.getClassTotal()) + " classes.");
        java.lang.System.out.println(("[003] " + "Average:  ") + (top / bottom));
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
        java.lang.System.out.println(((("[004] " + projectData.getInstanceVariableTotal()) + " total instance variables in ") + projectData.getClassTotal()) + " classes.");
        java.lang.System.out.println(("[004] " + "Average:  ") + (top / bottom));
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
        java.lang.System.out.println(((("[005] " + projectData.getClassVariableTotal()) + " total class variables in ") + projectData.getClassTotal()) + " classes.");
        java.lang.System.out.println(("[005] " + "Average:  ") + (top / bottom));
    }
}