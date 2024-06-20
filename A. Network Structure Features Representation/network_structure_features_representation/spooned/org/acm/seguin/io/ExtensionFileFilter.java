/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.io;
/**
 * Accepts all files
 *
 * @author Chris Seguin
 * @created May 30, 1999
 */
public class ExtensionFileFilter extends javax.swing.filechooser.FileFilter {
    // Local Variables
    private java.lang.String description;

    private java.util.Vector extensions;

    /**
     * Constructor for the ExtensionFileFilter
     */
    public ExtensionFileFilter() {
        description = "Unknown set of files";
        extensions = new java.util.Vector();
    }

    /**
     * Sets the description of the files accepted
     *
     * @param descr
     * 		the new description
     */
    public void setDescription(java.lang.String descr) {
        if (descr != null) {
            description = descr;
        }
    }

    /**
     * Return the description of the files accepted
     *
     * @return the description to be displayed in the file box
     */
    public java.lang.String getDescription() {
        return description;
    }

    /**
     * Add an extension
     *
     * @param ext
     * 		the extension to add
     */
    public void addExtension(java.lang.String ext) {
        if (ext != null) {
            extensions.addElement(ext);
        }
    }

    /**
     * Should this file be accepted
     *
     * @param file
     * 		the file under consideration
     * @return true - all files are accepted
     */
    public boolean accept(java.io.File file) {
        if (file.isDirectory()) {
            return true;
        }
        Enumeration = extensions.elements();
    }
}