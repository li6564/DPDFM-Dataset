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
package uml.ui;
/**
 *
 * @class FileAction
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Action that provides a smarter JFileChooser
 */
public abstract class FileAction extends javax.swing.AbstractAction {
    private uml.ui.FileAction.SmartChooser chooser = new uml.ui.FileAction.SmartChooser();

    /**
     * Create a new action
     */
    public FileAction(java.lang.String name, javax.swing.Icon icon) {
        super(name, icon);
    }

    /**
     * Create a new action
     */
    public FileAction(java.lang.String name) {
        super(name);
    }

    /**
     * Get the JFileChooser shared among all FileActions
     */
    protected uml.ui.FileAction.SmartChooser getChooser() {
        // Remove all the filters before letting another object reuse it
        chooser.resetChoosableFileFilters();
        return chooser;
    }

    /**
     *
     * @class SmartChooser

    File chooser that will remeber the users last selected directory
     */
    protected static class SmartChooser extends javax.swing.JFileChooser {
        protected static java.io.File lastDirectory = new java.io.File(java.lang.System.getProperty("user.dir"));

        /**
         * Remember last directory
         */
        public void approveSelection() {
            super.approveSelection();
            // Remember last directory
            uml.ui.FileAction.SmartChooser.lastDirectory = getSelectedFile();
            if (!uml.ui.FileAction.SmartChooser.lastDirectory.isDirectory())
                uml.ui.FileAction.SmartChooser.lastDirectory = uml.ui.FileAction.SmartChooser.lastDirectory.getParentFile();

        }

        /**
         * Switch to last used directory
         */
        public int showDialog(java.awt.Component parent, java.lang.String approveButtonText) {
            setCurrentDirectory(uml.ui.FileAction.SmartChooser.lastDirectory);
            return super.showDialog(parent, approveButtonText);
        }

        public java.lang.String getLastDirectory() {
            return uml.ui.FileAction.SmartChooser.lastDirectory.getAbsolutePath();
        }
    }

    /**
     *
     * @class SimpleFilter

    Simple file filter
     */
    protected class SimpleFilter extends javax.swing.filechooser.FileFilter {
        private java.lang.String[] extensions;

        private java.lang.String description;

        public SimpleFilter(java.lang.String extension, java.lang.String description) {
            this(new java.lang.String[]{ extension }, description);
        }

        public SimpleFilter(java.lang.String[] extensions, java.lang.String description) {
            this.extensions = extensions;
            this.description = description;
        }

        public boolean accept(java.io.File file) {
            if (file.isDirectory())
                return true;

            // Match an extension
            java.lang.String name = file.getName().toLowerCase();
            for (int i = 0; i < extensions.length; i++)
                if (name.endsWith("." + extensions[i]))
                    return true;


            return false;
        }

        public java.lang.String getDescription() {
            java.lang.StringBuffer buf = new java.lang.StringBuffer(description);
            buf.append('(');
            for (int i = 0; i < extensions.length; i++) {
                buf.append("*.").append(extensions[i]);
                if (i < (extensions.length - 1))
                    buf.append(", ");

            }
            buf.append(')');
            return buf.toString();
        }
    }
}