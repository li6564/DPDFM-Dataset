package org.acm.seguin.ide.command;
/**
 * The UML frame
 *
 * @author Chris Seguin
 */
public class UMLFrame extends javax.swing.JFrame {
    private org.acm.seguin.summary.PackageSummary packageSummary;

    private org.acm.seguin.uml.UMLPackage view;

    private javax.swing.JSplitPane splitPane;

    /**
     * Constructor for the UMLFrame object
     *
     * @param init
     * 		Description of Parameter
     */
    public UMLFrame(org.acm.seguin.summary.PackageSummary init) {
        super(init.getName());
        packageSummary = init;
        setup();
    }

    /**
     * Gets the UmlPackage attribute of the UMLFrame object
     *
     * @return The UmlPackage value
     */
    public org.acm.seguin.uml.UMLPackage getUmlPackage() {
        return view;
    }

    /**
     * Description of the Method
     */
    private void setup() {
        view = new org.acm.seguin.uml.UMLPackage(packageSummary);
        javax.swing.JScrollPane pane = new javax.swing.JScrollPane(view);
        view.setScrollPane(pane);
        javax.swing.JScrollBar horiz = pane.getHorizontalScrollBar();
        horiz.setUnitIncrement(400);
        javax.swing.JScrollBar vert = pane.getVerticalScrollBar();
        vert.setUnitIncrement(400);
        org.acm.seguin.ide.common.DividedSummaryPanel dsp = new org.acm.seguin.ide.common.DividedSummaryPanel(packageSummary, view);
        splitPane = new javax.swing.JSplitPane(javax.swing.JSplitPane.HORIZONTAL_SPLIT, dsp.getPane(), pane);
        splitPane.setDividerLocation(150);
        splitPane.setOneTouchExpandable(true);
        dsp.getPane().setMinimumSize(new java.awt.Dimension(50, 150));
        pane.setMinimumSize(new java.awt.Dimension(150, 150));
        getContentPane().add(splitPane);
        setSize(500, 350);
        org.acm.seguin.ide.command.CommandLineMenu clm = new org.acm.seguin.ide.command.CommandLineMenu();
        setJMenuBar(clm.getMenuBar(view));
        setVisible(true);
    }
}