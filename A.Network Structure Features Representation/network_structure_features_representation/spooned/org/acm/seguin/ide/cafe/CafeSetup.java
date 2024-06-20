package org.acm.seguin.ide.cafe;
/**
 * This class sets up the pretty printer and all the
 *  associated menu items.
 *
 * @author Chris Seguin
 * @created August 26, 2000
 */
public class CafeSetup implements org.acm.seguin.ide.cafe.Plugin {
    /**
     * Gets the PluginInfo attribute of the CafePrettyPrinter object
     */
    public void getPluginInfo() {
        java.lang.System.out.println("CafePrettyPrinter::getInfo()");
    }

    /**
     * Initializes Visual Cafe settings
     */
    public void init() {
        java.lang.String root = java.lang.System.getProperty("user.home");
        org.acm.seguin.util.FileSettings.setSettingsRoot(root);
        // Make sure everything is installed properly
        new org.acm.seguin.tools.install.RefactoryInstaller(false).run();
        org.acm.seguin.ide.cafe.VisualCafe vc = org.acm.seguin.ide.cafe.VisualCafe.getVisualCafe();
        // Add sample submenus to Visual Cafe MenuBar
        java.awt.MenuBar mb = vc.getMenuBar();
        java.awt.Menu subMenu = getSubMenu();
        mb.add(subMenu);
    }

    /**
     * Used to close out this object
     */
    public void destroy() {
    }

    /**
     * Used to save this object
     *
     * @param os
     * 		the output stream
     * @param b
     * 		boolean if it needs to be saved
     */
    public void save(java.io.OutputStream os, boolean b) {
    }

    /**
     * Restores the state
     *
     * @param is
     * 		The input stream
     * @param b
     * 		a boolean if anything has changed
     */
    public void restore(java.io.InputStream is, boolean b) {
    }

    /**
     * Gets the SubMenu attribute of the CafePrettyPrinter object
     *
     * @return The SubMenu value
     */
    private java.awt.Menu getSubMenu() {
        java.awt.Menu jrefactoryMenu = new java.awt.Menu("JRefactory");
        java.awt.MenuItem prettyPrinterMenuItem = new java.awt.MenuItem("P&retty Printer");
        prettyPrinterMenuItem.addActionListener(new org.acm.seguin.ide.cafe.CafePrettyPrinter());
        jrefactoryMenu.add(prettyPrinterMenuItem);
        try {
            java.awt.MenuItem loadMenuItem = new java.awt.MenuItem("Extract Method");
            loadMenuItem.addActionListener(new org.acm.seguin.ide.cafe.CafeExtractMethod());
            jrefactoryMenu.add(loadMenuItem);
        } catch (java.lang.Throwable re) {
        }
        java.awt.MenuItem extractMenuItem = new java.awt.MenuItem("Load Metadata");
        extractMenuItem.addActionListener(new org.acm.seguin.ide.cafe.ReloadActionAdapter());
        jrefactoryMenu.add(extractMenuItem);
        /* MenuItem viewDiagramMenuItem = new MenuItem("View Class Diagram");
        viewDiagramMenuItem.setEnabled(false);
        jrefactoryMenu.add(viewDiagramMenuItem);

        MenuItem printMenuItem = new MenuItem("Print");
        printMenuItem.setEnabled(false);
        jrefactoryMenu.add(printMenuItem);

        Menu zoomMenu = new Menu("Zoom");
        jrefactoryMenu.add(zoomMenu);

        MenuItem tenMenuItem = new MenuItem("10%");
        tenMenuItem.setEnabled(false);
        zoomMenu.add(tenMenuItem);

        MenuItem twentyfiveMenuItem = new MenuItem("25%");
        twentyfiveMenuItem.setEnabled(false);
        zoomMenu.add(twentyfiveMenuItem);

        MenuItem fiftyMenuItem = new MenuItem("50%");
        fiftyMenuItem.setEnabled(false);
        zoomMenu.add(fiftyMenuItem);

        MenuItem fullMenuItem = new MenuItem("100%");
        fullMenuItem.setEnabled(false);
        zoomMenu.add(fullMenuItem);
         */
        return jrefactoryMenu;
    }
}