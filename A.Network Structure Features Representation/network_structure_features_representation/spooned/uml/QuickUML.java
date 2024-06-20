/**
 * QuickUML; A simple UML tool that demonstrates one use of the
 * Java Diagram Package
 *
 * Copyright (C) 2001  Eric Crahen <crahen@cse.buffalo.edu>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package uml;
/**
 *
 * @class QuickUML
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class QuickUML extends javax.swing.JFrame {
    protected QuickUML() {
        super("UML Application");
        // Create the content area
        uml.ui.DiagramContainer container = new uml.ui.DiagramContainer();
        uml.ui.ToolPalette palette = new uml.ui.ToolPalette(container);
        // Create the menubar & initialize it
        uml.ui.FlatMenuBar menuBar = new uml.ui.FlatMenuBar();
        container.updateMenus(menuBar);
        palette.updateMenus(menuBar);
        updateMenus(menuBar);
        // Update the content
        java.awt.Container content = getContentPane();
        content.setLayout(new java.awt.BorderLayout());
        content.add(menuBar, java.awt.BorderLayout.NORTH);
        content.add(container);
        content.add(palette, java.awt.BorderLayout.WEST);
    }

    /**
     * Update the JMenuBar before its installed. Add exit option, etc.
     *
     * @param JMenuBar
     */
    public void updateMenus(uml.ui.FlatMenuBar menuBar) {
        javax.swing.JMenu menu = menuBar.getMenu("File");
        menu.add(new javax.swing.JSeparator(), -1);
        menu.add(new javax.swing.JMenuItem(new uml.QuickUML.QuitAction()), -1);
        menu = menuBar.getHelpMenu();
        menu.add(new javax.swing.JMenuItem(new uml.QuickUML.AboutAction()), -1);
    }

    /**
     *
     * @class QuitAction
     */
    class QuitAction extends javax.swing.AbstractAction {
        QuitAction() {
            super("Quit");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            java.lang.System.exit(0);
        }
    }

    /**
     *
     * @class AboutAction
     */
    class AboutAction extends javax.swing.AbstractAction {
        javax.swing.JComponent about = new javax.swing.JLabel("<HTML>Created By: <B>Eric Crahen</B><CENTER>Copyright <B>(c)</B> 2001<CENTER><HTML>", javax.swing.JLabel.CENTER);

        AboutAction() {
            super("About");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            javax.swing.JOptionPane.showOptionDialog(null, about, "About", javax.swing.JOptionPane.OK_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, null, new java.lang.Object[]{ "OK" }, null);
        }
    }

    public static void main(java.lang.String[] args) {
        try {
            uml.QuickUML app = new uml.QuickUML();
            // Fit to screen
            java.awt.Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            app.setBounds(dim.width / 8, dim.height / 8, (dim.width * 3) / 4, (dim.height * 3) / 4);
            app.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            app.setVisible(true);
        } catch (java.lang.Throwable t) {
            t.printStackTrace();
            java.lang.System.exit(0);
        }
    }
}