/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.command;
/**
 * Creates the menubar for the command line program
 *
 * @author Chris Seguin
 */
class CommandLineMenu {
    /**
     * Gets the MenuBar attribute of the CommandLineMenu object
     *
     * @param panel
     * 		Description of Parameter
     * @return The MenuBar value
     */
    public javax.swing.JMenuBar getMenuBar(javax.swing.JPanel panel) {
        javax.swing.JMenuBar menubar = new javax.swing.JMenuBar();
        menubar.add(createFileMenu(panel));
        menubar.add(createEditMenu());
        if (panel instanceof org.acm.seguin.uml.line.LinedPanel) {
            menubar.add(createZoomMenu(panel));
        }
        return menubar;
    }

    /**
     * Creates the file menu
     *
     * @param panel
     * 		the panel
     * @return the file menu
     */
    private javax.swing.JMenu createFileMenu(javax.swing.JPanel panel) {
        javax.swing.JMenu fileMenu = new javax.swing.JMenu("File");
        javax.swing.JMenuItem saveMenuItem = new javax.swing.JMenuItem("Save");
        if (panel instanceof org.acm.seguin.io.Saveable) {
            saveMenuItem.addActionListener(new org.acm.seguin.uml.SaveMenuSelection(((org.acm.seguin.io.Saveable) (panel))));
        } else {
            saveMenuItem.setEnabled(false);
        }
        fileMenu.add(saveMenuItem);
        javax.swing.JMenuItem jpgMenuItem = new javax.swing.JMenuItem("JPG");
        if (panel instanceof org.acm.seguin.uml.UMLPackage) {
            jpgMenuItem.addActionListener(new org.acm.seguin.uml.jpg.SaveAdapter(((org.acm.seguin.uml.UMLPackage) (panel))));
        } else {
            jpgMenuItem.setEnabled(false);
        }
        fileMenu.add(jpgMenuItem);
        fileMenu.addSeparator();
        javax.swing.JMenuItem printSetupMenuItem = new javax.swing.JMenuItem("Print Setup");
        printSetupMenuItem.addActionListener(new org.acm.seguin.uml.print.PrintSetupAdapter());
        fileMenu.add(printSetupMenuItem);
        javax.swing.JMenuItem printMenuItem = new javax.swing.JMenuItem("Print");
        if (panel instanceof org.acm.seguin.uml.UMLPackage) {
            printMenuItem.addActionListener(new org.acm.seguin.uml.print.PrintAdapter(((org.acm.seguin.uml.UMLPackage) (panel))));
        } else {
            printMenuItem.setEnabled(false);
        }
        fileMenu.add(printMenuItem);
        fileMenu.addSeparator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem("Exit");
        exitMenuItem.addActionListener(new org.acm.seguin.ide.command.ExitMenuSelection());
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    /**
     * Creates edit menu
     *
     * @return returns the edit menu
     */
    private javax.swing.JMenu createEditMenu() {
        javax.swing.JMenu editMenu = new javax.swing.JMenu("Edit");
        javax.swing.JMenuItem undoMenuItem = new javax.swing.JMenuItem("Undo Refactoring");
        undoMenuItem.addActionListener(new org.acm.seguin.ide.common.UndoAdapter());
        editMenu.add(undoMenuItem);
        return editMenu;
    }

    /**
     * Creates the zoom menu
     *
     * @param panel
     * 		the panel
     * @return the zoom menu
     */
    private javax.swing.JMenu createZoomMenu(javax.swing.JPanel panel) {
        org.acm.seguin.uml.line.LinedPanel linedPanel = ((org.acm.seguin.uml.line.LinedPanel) (panel));
        javax.swing.JMenu zoomMenu = new javax.swing.JMenu("Zoom");
        javax.swing.JMenuItem tenPercent = new javax.swing.JMenuItem("10%");
        tenPercent.addActionListener(new org.acm.seguin.ide.command.ZoomAdapter(linedPanel, 0.1));
        zoomMenu.add(tenPercent);
        javax.swing.JMenuItem twentyFivePercent = new javax.swing.JMenuItem("25%");
        twentyFivePercent.addActionListener(new org.acm.seguin.ide.command.ZoomAdapter(linedPanel, 0.25));
        zoomMenu.add(twentyFivePercent);
        javax.swing.JMenuItem fiftyPercent = new javax.swing.JMenuItem("50%");
        fiftyPercent.addActionListener(new org.acm.seguin.ide.command.ZoomAdapter(linedPanel, 0.5));
        zoomMenu.add(fiftyPercent);
        javax.swing.JMenuItem normal = new javax.swing.JMenuItem("100%");
        normal.addActionListener(new org.acm.seguin.ide.command.ZoomAdapter(linedPanel, 1.0));
        zoomMenu.add(normal);
        javax.swing.JMenuItem twoHunderd = new javax.swing.JMenuItem("200%");
        twoHunderd.addActionListener(new org.acm.seguin.ide.command.ZoomAdapter(linedPanel, 2.0));
        zoomMenu.add(twoHunderd);
        return zoomMenu;
    }
}