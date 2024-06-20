package org.acm.seguin.ide.common;
/**
 * Creates an object that holds the divided summary panel
 *
 * @author Chris Seguin
 */
public class DividedSummaryPanel {
    javax.swing.JScrollPane keyPane;

    javax.swing.JScrollPane summaryPane;

    javax.swing.JSplitPane splitPane;

    /**
     * Constructor for the DividedSummaryPanel object
     *
     * @param summary
     * 		Description of Parameter
     * @param umlPackage
     * 		Description of Parameter
     */
    public DividedSummaryPanel(org.acm.seguin.summary.PackageSummary summary, org.acm.seguin.uml.UMLPackage umlPackage) {
        init(summary, umlPackage);
    }

    /**
     * Gets the Pane attribute of the DividedSummaryPanel object
     *
     * @return The Pane value
     */
    public javax.swing.JComponent getPane() {
        return splitPane;
    }

    /**
     * Initializes the splitpane
     *
     * @param summary
     * 		Description of Parameter
     * @param umlPackage
     * 		Description of Parameter
     */
    private void init(org.acm.seguin.summary.PackageSummary summary, org.acm.seguin.uml.UMLPackage umlPackage) {
        keyPane = new javax.swing.JScrollPane(new org.acm.seguin.ide.common.KeyPanel());
        summaryPane = new javax.swing.JScrollPane(new org.acm.seguin.ide.common.ClassListPanel(summary, umlPackage));
        splitPane = new javax.swing.JSplitPane(javax.swing.JSplitPane.VERTICAL_SPLIT, keyPane, summaryPane);
        splitPane.setDividerLocation(150);
        splitPane.setOneTouchExpandable(true);
        keyPane.setMinimumSize(new java.awt.Dimension(50, 50));
        summaryPane.setMinimumSize(new java.awt.Dimension(50, 50));
    }

    /**
     * The main program for the DividedSummaryPanel class
     *
     * @param args
     * 		The command line arguments
     */
    public static void main(java.lang.String[] args) {
        new org.acm.seguin.summary.SummaryTraversal("c:\\temp\\download").go();
        javax.swing.JFrame frame = new javax.swing.JFrame("Divided Summary");
        org.acm.seguin.ide.common.DividedSummaryPanel dsp = new org.acm.seguin.ide.common.DividedSummaryPanel(org.acm.seguin.summary.PackageSummary.getPackageSummary("java.lang"), null);
        frame.getContentPane().add(dsp.getPane());
        frame.pack();
        frame.setSize(200, 400);
        frame.setVisible(true);
        frame.addWindowListener(new org.acm.seguin.ide.common.ExitOnCloseAdapter());
    }
}