/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.command;
import java.io.IOException;
/**
 * Creates a panel for the selection of packages to view.
 *
 * @author Chris Seguin
 * @created August 10, 1999
 */
public class PackageSelectorPanel extends org.acm.seguin.ide.common.PackageSelectorArea implements java.awt.event.ActionListener , org.acm.seguin.io.Saveable , org.acm.seguin.uml.loader.Reloader {
    /**
     * The root directory
     */
    protected java.lang.String rootDir = null;

    // Instance Variables
    private java.util.HashMap viewList;

    private org.acm.seguin.ide.command.PackageSelectorPanel.ButtonPanel buttons;

    // Class Variables
    private static org.acm.seguin.ide.command.PackageSelectorPanel mainPanel;

    /**
     * Constructor for the PackageSelectorPanel object
     *
     * @param root
     * 		The root directory
     */
    protected PackageSelectorPanel(java.lang.String root) {
        super();
        // Setup the instance variables
        setRootDirectory(root);
        org.acm.seguin.uml.loader.ReloaderSingleton.register(this);
        org.acm.seguin.uml.loader.ReloaderSingleton.reload();
        buttons = new org.acm.seguin.ide.command.PackageSelectorPanel.ButtonPanel(this);
        createFrame();
    }

    /**
     * Handle the button press events
     *
     * @param evt
     * 		the event
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        java.lang.String command = evt.getActionCommand();
        if (command.equals("Show")) {
            java.lang.Object[] selection = listbox.getSelectedValues();
            for (int ndx = 0; ndx < selection.length; ndx++) {
                org.acm.seguin.summary.PackageSummary next = ((org.acm.seguin.summary.PackageSummary) (selection[ndx]));
                showSummary(next);
            }
        } else if (command.equals("Hide")) {
            java.lang.Object[] selection = listbox.getSelectedValues();
            for (int ndx = 0; ndx < selection.length; ndx++) {
                org.acm.seguin.summary.PackageSummary next = ((org.acm.seguin.summary.PackageSummary) (selection[ndx]));
                hideSummary(next);
            }
        } else if (command.equals("Reload")) {
            org.acm.seguin.uml.loader.ReloaderSingleton.reload();
        }
    }

    /**
     * Reloads the package information
     */
    public void reload() {
        loadPackages();
    }

    /**
     * Saves the diagrams
     *
     * @exception IOException
     * 		Description of Exception
     */
    public void save() throws java.io.IOException {
        java.util.Iterator iter = viewList.keySet().iterator();
        while (iter.hasNext()) {
            org.acm.seguin.summary.PackageSummary packageSummary = ((org.acm.seguin.summary.PackageSummary) (iter.next()));
            org.acm.seguin.uml.UMLPackage view = getPackage(packageSummary).getUmlPackage();
            view.save();
        } 
    }

    /**
     * Loads the packages into the listbox and refreshes the UML diagrams
     */
    public void loadPackages() {
        loadSummaries();
        super.loadPackages();
        // Reloads the screens
        org.acm.seguin.uml.UMLPackage view = null;
        org.acm.seguin.summary.PackageSummary packageSummary = null;
        if (viewList == null) {
            viewList = new java.util.HashMap();
            return;
        }
        java.util.Iterator iter = viewList.keySet().iterator();
        while (iter.hasNext()) {
            packageSummary = ((org.acm.seguin.summary.PackageSummary) (iter.next()));
            view = getPackage(packageSummary).getUmlPackage();
            view.reload();
        } 
    }

    /**
     * Load the summaries
     */
    public void loadSummaries() {
        // Load the summaries
        new org.acm.seguin.summary.SummaryTraversal(rootDir).go();
    }

    /**
     * Set the root directory
     *
     * @param root
     * 		the new root directory
     */
    protected void setRootDirectory(java.lang.String root) {
        if (root == null) {
            rootDir = java.lang.System.getProperty("user.dir");
        } else {
            rootDir = root;
        }
    }

    /**
     * Get the package from the central store
     *
     * @param summary
     * 		The package summary that we are looking for
     * @return The UML package
     */
    protected org.acm.seguin.ide.command.UMLFrame getPackage(org.acm.seguin.summary.PackageSummary summary) {
        return ((org.acm.seguin.ide.command.UMLFrame) (viewList.get(summary)));
    }

    /**
     * Add package to central store
     *
     * @param summary
     * 		the summary we are adding
     * @param view
     * 		the associated view
     */
    protected void addPackage(org.acm.seguin.summary.PackageSummary summary, org.acm.seguin.ide.command.UMLFrame view) {
        viewList.put(summary, view);
    }

    /**
     * Shows the summary
     *
     * @param packageSummary
     * 		the summary to show
     */
    private void showSummary(org.acm.seguin.summary.PackageSummary packageSummary) {
        org.acm.seguin.ide.command.UMLFrame view = getPackage(packageSummary);
        if ((view == null) && (packageSummary.getFileSummaries() != null)) {
            createNewView(packageSummary);
        } else if (packageSummary.getFileSummaries() == null) {
            // Nothing to view
        } else {
            view.getUmlPackage().reload();
            view.setVisible(true);
        }
    }

    /**
     * Hide the summary
     *
     * @param packageSummary
     * 		the summary to hide
     */
    private void hideSummary(org.acm.seguin.summary.PackageSummary packageSummary) {
        org.acm.seguin.ide.command.UMLFrame view = getPackage(packageSummary);
        view.setVisible(false);
    }

    /**
     * Creates a new view
     *
     * @param packageSummary
     * 		The packages summary
     */
    private void createNewView(org.acm.seguin.summary.PackageSummary packageSummary) {
        org.acm.seguin.ide.command.UMLFrame frame = new org.acm.seguin.ide.command.UMLFrame(packageSummary);
        addPackage(packageSummary, frame);
    }

    /**
     * Creates the frame
     */
    private void createFrame() {
        javax.swing.JFrame frame = new javax.swing.JFrame("Package Selector");
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new java.awt.BorderLayout());
        javax.swing.JScrollPane scrollPane = getScrollPane();
        scrollPane.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, java.awt.BorderLayout.CENTER);
        panel.add(buttons, java.awt.BorderLayout.EAST);
        frame.getContentPane().add(panel);
        org.acm.seguin.ide.command.CommandLineMenu clm = new org.acm.seguin.ide.command.CommandLineMenu();
        frame.setJMenuBar(clm.getMenuBar(this));
        frame.addWindowListener(new org.acm.seguin.ide.command.ExitMenuSelection());
        frame.setSize(350, 350);
        frame.setVisible(true);
    }

    /**
     * Get the main panel
     *
     * @param directory
     * 		Description of Parameter
     * @return The MainPanel value
     */
    public static org.acm.seguin.ide.command.PackageSelectorPanel getMainPanel(java.lang.String directory) {
        if (org.acm.seguin.ide.command.PackageSelectorPanel.mainPanel == null) {
            if (directory == null) {
                return null;
            }
            org.acm.seguin.ide.command.PackageSelectorPanel.mainPanel = new org.acm.seguin.ide.command.PackageSelectorPanel(directory);
        }
        org.acm.seguin.ide.command.PackageSelectorPanel.mainPanel.setVisible(true);
        return org.acm.seguin.ide.command.PackageSelectorPanel.mainPanel;
    }

    /**
     * Main program for testing purposes
     *
     * @param args
     * 		The command line arguments
     */
    public static void main(java.lang.String[] args) {
        if (args.length != 1) {
            java.lang.System.out.println("Syntax:  java org.acm.seguin.uml.PackageSelectorPanel <dir>");
            return;
        }
        org.acm.seguin.ide.command.PackageSelectorPanel panel = org.acm.seguin.ide.command.PackageSelectorPanel.getMainPanel(args[0]);
        org.acm.seguin.uml.loader.ReloaderSingleton.register(panel);
    }

    /**
     * Description of the Class
     *
     * @author Chris Seguin
     */
    private class ButtonPanel extends javax.swing.JPanel {
        private java.awt.event.ActionListener listener;

        /**
         * Constructor for the ButtonPanel object
         *
         * @param listener
         * 		Description of Parameter
         */
        public ButtonPanel(java.awt.event.ActionListener listener) {
            this.listener = listener;
            init();
            this.setSize(getPreferredSize());
        }

        /**
         * Gets the PreferredSize attribute of the ButtonPanel object
         *
         * @return The PreferredSize value
         */
        public java.awt.Dimension getPreferredSize() {
            return new java.awt.Dimension(110, 170);
        }

        /**
         * Gets the MaximumSize attribute of the ButtonPanel object
         *
         * @return The MaximumSize value
         */
        public java.awt.Dimension getMaximumSize() {
            return getPreferredSize();
        }

        /**
         * Description of the Method
         */
        private void init() {
            this.setLayout(null);
            // Add the buttons
            javax.swing.JButton showButton = new javax.swing.JButton("Show");
            showButton.setBounds(0, 10, 100, 25);
            add(showButton);
            showButton.addActionListener(listener);
            javax.swing.JButton hideButton = new javax.swing.JButton("Hide");
            hideButton.setBounds(0, 50, 100, 25);
            add(hideButton);
            hideButton.addActionListener(listener);
            javax.swing.JButton reloadButton = new javax.swing.JButton("Reload");
            reloadButton.setBounds(0, 90, 100, 25);
            add(reloadButton);
            reloadButton.addActionListener(listener);
            javax.swing.JButton reloadAllButton = new javax.swing.JButton("Reload All");
            reloadAllButton.setBounds(0, 130, 100, 25);
            reloadAllButton.setEnabled(false);
            add(reloadAllButton);
        }
    }
}