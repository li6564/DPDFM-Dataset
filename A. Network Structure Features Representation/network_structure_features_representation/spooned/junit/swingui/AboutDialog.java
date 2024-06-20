package junit.swingui;
/**
 * The AboutDialog.
 */
class AboutDialog extends javax.swing.JDialog {
    public AboutDialog(javax.swing.JFrame parent) {
        super(parent, true);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());
        setSize(330, 138);
        setTitle("About");
        setLocationRelativeTo(parent);
        javax.swing.JButton button = new javax.swing.JButton("Close");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
        javax.swing.JLabel label1 = new javax.swing.JLabel("JUnit");
        label1.setFont(new java.awt.Font("dialog", java.awt.Font.PLAIN, 36));
        javax.swing.JLabel label2 = new javax.swing.JLabel(("JUnit " + junit.runner.Version.id()) + " by Kent Beck and Erich Gamma");
        label2.setFont(new java.awt.Font("dialog", java.awt.Font.PLAIN, 14));
        javax.swing.JLabel logo = createLogo();
        java.awt.GridBagConstraints constraintsLabel1 = new java.awt.GridBagConstraints();
        constraintsLabel1.gridx = 3;
        constraintsLabel1.gridy = 0;
        constraintsLabel1.gridwidth = 1;
        constraintsLabel1.gridheight = 1;
        constraintsLabel1.anchor = java.awt.GridBagConstraints.CENTER;
        getContentPane().add(label1, constraintsLabel1);
        java.awt.GridBagConstraints constraintsLabel2 = new java.awt.GridBagConstraints();
        constraintsLabel2.gridx = 2;
        constraintsLabel2.gridy = 1;
        constraintsLabel2.gridwidth = 2;
        constraintsLabel2.gridheight = 1;
        constraintsLabel2.anchor = java.awt.GridBagConstraints.CENTER;
        getContentPane().add(label2, constraintsLabel2);
        java.awt.GridBagConstraints constraintsButton1 = new java.awt.GridBagConstraints();
        constraintsButton1.gridx = 2;
        constraintsButton1.gridy = 2;
        constraintsButton1.gridwidth = 2;
        constraintsButton1.gridheight = 1;
        constraintsButton1.anchor = java.awt.GridBagConstraints.CENTER;
        constraintsButton1.insets = new java.awt.Insets(8, 0, 8, 0);
        getContentPane().add(button, constraintsButton1);
        java.awt.GridBagConstraints constraintsLogo1 = new java.awt.GridBagConstraints();
        constraintsLogo1.gridx = 2;
        constraintsLogo1.gridy = 0;
        constraintsLogo1.gridwidth = 1;
        constraintsLogo1.gridheight = 1;
        constraintsLogo1.anchor = java.awt.GridBagConstraints.CENTER;
        getContentPane().add(logo, constraintsLogo1);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
            }
        });
    }

    protected javax.swing.JLabel createLogo() {
        javax.swing.Icon icon = junit.swingui.TestRunner.getIconResource(junit.runner.BaseTestRunner.class, "logo.gif");
        return new javax.swing.JLabel(icon);
    }
}