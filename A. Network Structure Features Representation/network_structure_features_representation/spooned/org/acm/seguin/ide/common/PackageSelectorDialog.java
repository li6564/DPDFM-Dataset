/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common;
/**
 * The package selector dialog box
 *
 * @author Chris Seguin
 */
public class PackageSelectorDialog extends javax.swing.JDialog implements java.awt.event.ActionListener {
    private org.acm.seguin.ide.common.PackageSelectorArea selection;

    private org.acm.seguin.summary.PackageSummary summary;

    private org.acm.seguin.ide.common.PackageSelectorDialog.ButtonPanel buttons;

    /**
     * Constructor for the PackageSelectorDialog object
     *
     * @param parent
     * 		the parent dialog rame
     */
    public PackageSelectorDialog(javax.swing.JFrame parent) {
        super(parent, "Select package to view", true);
        getContentPane().setLayout(new java.awt.BorderLayout());
        super.setSize(350, 325);
        selection = new org.acm.seguin.ide.common.PackageSelectorArea();
        selection.loadPackages();
        javax.swing.JScrollPane pane = selection.getScrollPane();
        pane.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
        getContentPane().add(pane, java.awt.BorderLayout.CENTER);
        buttons = new org.acm.seguin.ide.common.PackageSelectorDialog.ButtonPanel(this);
        buttons.setLocation(220, 0);
        getContentPane().add(buttons, java.awt.BorderLayout.EAST);
        org.acm.seguin.awt.CenterDialog.center(this, parent);
    }

    /**
     * Gets the summary that has been selected
     *
     * @return the selected package summary
     */
    public org.acm.seguin.summary.PackageSummary getSummary() {
        return summary;
    }

    /**
     * Selects the package when the user presses OK
     *
     * @param evt
     * 		the action event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getActionCommand().equals("OK")) {
            summary = selection.getSelection();
            dispose();
        }
        if (evt.getActionCommand().equals("Cancel")) {
            summary = null;
            dispose();
        }
    }

    /**
     * The main program for the PackageSelectorDialog class
     *
     * @param args
     * 		The command line arguments
     */
    public static void main(java.lang.String[] args) {
        new org.acm.seguin.ide.common.PackageSelectorDialog(null).setVisible(true);
    }

    /**
     * Quick and dirty panel to hold the buttons so that they are not resized as
     *  the window is adjusted.
     *
     * @author Chris Seguin
     */
    private class ButtonPanel extends javax.swing.JPanel {
        private java.awt.event.ActionListener listener;

        private java.awt.Dimension preferredSize;

        /**
         * Constructor for the ButtonPanel object
         *
         * @param listener
         * 		Description of Parameter
         */
        public ButtonPanel(java.awt.event.ActionListener listener) {
            this.listener = listener;
            init();
            preferredSize = new java.awt.Dimension();
            preferredSize.width = 110;
            preferredSize.height = 80;
            this.setSize(preferredSize);
        }

        /**
         * Gets the PreferredSize attribute of the ButtonPanel object
         *
         * @return The PreferredSize value
         */
        public java.awt.Dimension getPreferredSize() {
            return preferredSize;
        }

        /**
         * Gets the MaximumSize attribute of the ButtonPanel object
         *
         * @return The MaximumSize value
         */
        public java.awt.Dimension getMaximumSize() {
            return preferredSize;
        }

        /**
         * Gets the MinimumSize attribute of the ButtonPanel object
         *
         * @return The MinimumSize value
         */
        public java.awt.Dimension getMinimumSize() {
            return preferredSize;
        }

        /**
         * Initialize the components of this panel
         */
        private void init() {
            setLayout(null);
            javax.swing.JButton okButton = new javax.swing.JButton("OK");
            okButton.setBounds(0, 10, 100, 25);
            add(okButton);
            okButton.addActionListener(listener);
            javax.swing.JButton cancelButton = new javax.swing.JButton("Cancel");
            cancelButton.setBounds(0, 45, 100, 25);
            add(cancelButton);
            cancelButton.addActionListener(listener);
        }
    }
}