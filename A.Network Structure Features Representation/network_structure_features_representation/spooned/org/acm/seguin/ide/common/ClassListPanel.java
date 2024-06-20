package org.acm.seguin.ide.common;
/**
 * Holds the list of classes
 *
 * @author Chris Seguin
 */
public class ClassListPanel extends javax.swing.JPanel {
    private org.acm.seguin.summary.PackageSummary summary;

    private org.acm.seguin.uml.UMLPackage umlPackage;

    /**
     * Constructor for the ClassListPanel object
     *
     * @param init
     * 		Description of Parameter
     * @param initPackage
     * 		Description of Parameter
     */
    public ClassListPanel(org.acm.seguin.summary.PackageSummary init, org.acm.seguin.uml.UMLPackage initPackage) {
        summary = init;
        umlPackage = initPackage;
        umlPackage.setClassListPanel(this);
        init();
    }

    /**
     * Used to reload the class list
     *
     * @param init
     * 		Description of Parameter
     */
    public void load(org.acm.seguin.summary.PackageSummary init) {
        summary = init;
        removeAll();
        init();
    }

    /**
     * Initializes the panel
     */
    private void init() {
        setLayout(new java.awt.GridBagLayout());
        setBackground(java.awt.Color.white);
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        javax.swing.JLabel title;
        if (summary == null) {
            title = new javax.swing.JLabel("Unknown");
        } else {
            title = new javax.swing.JLabel(summary.getName());
        }
        title.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new java.awt.Insets(0, 10, 0, 10);
        add(title, gbc);
        gbc.gridwidth = 1;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        int count = 1;
        java.util.Iterator iter = listTypes();
        while (iter.hasNext()) {
            org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
            addTypeToPanel(next, gbc, count);
            count++;
        } 
        repaint();
    }

    /**
     * Adds a feature to the TypeToPanel attribute of the ClassListPanel object
     *
     * @param nextType
     * 		The feature to be added to the TypeToPanel attribute
     * @param gbc
     * 		The feature to be added to the TypeToPanel attribute
     * @param count
     * 		The feature to be added to the TypeToPanel attribute
     */
    private void addTypeToPanel(org.acm.seguin.summary.TypeSummary nextType, java.awt.GridBagConstraints gbc, int count) {
        org.acm.seguin.ide.common.JumpToTypeAdapter jumpToType = new org.acm.seguin.ide.common.JumpToTypeAdapter(umlPackage, nextType);
        javax.swing.Icon icon;
        if (nextType.isInterface()) {
            icon = new org.acm.seguin.uml.InterfaceIcon(8, 8);
        } else {
            icon = new org.acm.seguin.uml.ClassIcon(8, 8);
        }
        org.acm.seguin.ide.common.IconPanel classPanel = new org.acm.seguin.ide.common.IconPanel(icon);
        gbc.gridx = 0;
        gbc.gridy = count;
        add(classPanel, gbc);
        classPanel.addMouseListener(jumpToType);
        javax.swing.JLabel classLabel = new javax.swing.JLabel(nextType.getName(), javax.swing.JLabel.LEFT);
        gbc.gridx = 1;
        add(classLabel, gbc);
        classLabel.addMouseListener(jumpToType);
    }

    /**
     * Creates a list of type summaries
     *
     * @return Description of the Returned Value
     */
    private java.util.Iterator listTypes() {
        java.util.TreeMap map = new java.util.TreeMap();
        java.util.Iterator iter = null;
        if (summary != null)
            iter = summary.getFileSummaries();

        while ((iter != null) && iter.hasNext()) {
            org.acm.seguin.summary.FileSummary nextFileSummary = ((org.acm.seguin.summary.FileSummary) (iter.next()));
            java.util.Iterator iter2 = nextFileSummary.getTypes();
            while ((iter2 != null) && iter2.hasNext()) {
                org.acm.seguin.summary.TypeSummary nextType = ((org.acm.seguin.summary.TypeSummary) (iter2.next()));
                map.put(nextType.getName(), nextType);
            } 
        } 
        return map.values().iterator();
    }

    /**
     * The main program for the ClassListPanel class
     *
     * @param args
     * 		The command line arguments
     */
    public static void main(java.lang.String[] args) {
        new org.acm.seguin.summary.SummaryTraversal("c:\\temp\\download").go();
        javax.swing.JFrame frame = new javax.swing.JFrame("Class List");
        frame.getContentPane().add(new org.acm.seguin.ide.common.ClassListPanel(org.acm.seguin.summary.PackageSummary.getPackageSummary("java.lang"), null));
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new org.acm.seguin.ide.common.ExitOnCloseAdapter());
    }
}