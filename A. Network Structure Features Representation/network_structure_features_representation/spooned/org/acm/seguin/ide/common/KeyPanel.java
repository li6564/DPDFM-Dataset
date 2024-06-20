package org.acm.seguin.ide.common;
/**
 * Stores the key for the UML diagram
 *
 * @author Chris Seguin
 */
public class KeyPanel extends javax.swing.JPanel {
    /**
     * Constructor for the KeyPanel object
     */
    public KeyPanel() {
        init();
    }

    /**
     * Initializes the panel
     */
    private void init() {
        setLayout(new java.awt.GridBagLayout());
        setBackground(java.awt.Color.white);
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        javax.swing.JLabel title = new javax.swing.JLabel("Key");
        title.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new java.awt.Insets(0, 10, 0, 10);
        add(title, gbc);
        gbc.gridwidth = 1;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        org.acm.seguin.uml.ProtectionIcon pi = new org.acm.seguin.uml.ProtectionIcon(8, 8);
        pi.setProtection(org.acm.seguin.uml.UMLLine.PRIVATE);
        org.acm.seguin.ide.common.IconPanel privatePanel = new org.acm.seguin.ide.common.IconPanel(pi);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(privatePanel, gbc);
        javax.swing.JLabel privateLabel = new javax.swing.JLabel("Private Scope", javax.swing.JLabel.LEFT);
        gbc.gridx = 1;
        add(privateLabel, gbc);
        pi = new org.acm.seguin.uml.ProtectionIcon(8, 8);
        pi.setProtection(org.acm.seguin.uml.UMLLine.DEFAULT);
        org.acm.seguin.ide.common.IconPanel packagePanel = new org.acm.seguin.ide.common.IconPanel(pi);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(packagePanel, gbc);
        javax.swing.JLabel packageLabel = new javax.swing.JLabel("Package or Default Scope", javax.swing.JLabel.LEFT);
        gbc.gridx = 1;
        add(packageLabel, gbc);
        pi = new org.acm.seguin.uml.ProtectionIcon(8, 8);
        pi.setProtection(org.acm.seguin.uml.UMLLine.PROTECTED);
        org.acm.seguin.ide.common.IconPanel protectedPanel = new org.acm.seguin.ide.common.IconPanel(pi);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(protectedPanel, gbc);
        javax.swing.JLabel protectedLabel = new javax.swing.JLabel("Protected Scope", javax.swing.JLabel.LEFT);
        gbc.gridx = 1;
        add(protectedLabel, gbc);
        pi = new org.acm.seguin.uml.ProtectionIcon(8, 8);
        pi.setProtection(org.acm.seguin.uml.UMLLine.PUBLIC);
        org.acm.seguin.ide.common.IconPanel publicPanel = new org.acm.seguin.ide.common.IconPanel(pi);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(publicPanel, gbc);
        javax.swing.JLabel publicLabel = new javax.swing.JLabel("Public Scope", javax.swing.JLabel.LEFT);
        gbc.gridx = 1;
        add(publicLabel, gbc);
        org.acm.seguin.uml.ClassIcon ci = new org.acm.seguin.uml.ClassIcon(8, 8);
        org.acm.seguin.ide.common.IconPanel classPanel = new org.acm.seguin.ide.common.IconPanel(ci);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(classPanel, gbc);
        javax.swing.JLabel classLabel = new javax.swing.JLabel("Class", javax.swing.JLabel.LEFT);
        gbc.gridx = 1;
        add(classLabel, gbc);
        org.acm.seguin.uml.InterfaceIcon ii = new org.acm.seguin.uml.InterfaceIcon(8, 8);
        org.acm.seguin.ide.common.IconPanel interfacePanel = new org.acm.seguin.ide.common.IconPanel(ii);
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(interfacePanel, gbc);
        javax.swing.JLabel interfaceLabel = new javax.swing.JLabel("Interface", javax.swing.JLabel.LEFT);
        gbc.gridx = 1;
        add(interfaceLabel, gbc);
        javax.swing.JLabel instanceItem = new javax.swing.JLabel("plain");
        instanceItem.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(instanceItem, gbc);
        javax.swing.JLabel instanceLabel = new javax.swing.JLabel("Instance Variable or Method", javax.swing.JLabel.LEFT);
        gbc.gridx = 1;
        add(instanceLabel, gbc);
        javax.swing.JLabel staticItem = new javax.swing.JLabel("bold");
        staticItem.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(staticItem, gbc);
        javax.swing.JLabel staticLabel = new javax.swing.JLabel("Static Variable or Method", javax.swing.JLabel.LEFT);
        gbc.gridx = 1;
        add(staticLabel, gbc);
        javax.swing.JLabel abstractItem = new javax.swing.JLabel("italic");
        abstractItem.setFont(new java.awt.Font("Dialog", java.awt.Font.ITALIC, 12));
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(abstractItem, gbc);
        javax.swing.JLabel abstractLabel = new javax.swing.JLabel("Abstract Class or Method", javax.swing.JLabel.LEFT);
        gbc.gridx = 1;
        add(abstractLabel, gbc);
    }

    /**
     * The main program for the KeyPanel class
     *
     * @param args
     * 		The command line arguments
     */
    public static void main(java.lang.String[] args) {
        javax.swing.JFrame frame = new javax.swing.JFrame("Key Panel");
        frame.getContentPane().add(new org.acm.seguin.ide.common.KeyPanel());
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new org.acm.seguin.ide.common.ExitOnCloseAdapter());
    }
}