/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.common;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
public class TextPrinter {
    /**
     * Description of the Method
     *
     * @param filename
     * 		Description of Parameter
     * @param contents
     * 		Description of Parameter
     */
    protected void print(java.lang.String filename, java.lang.String contents) {
        org.acm.seguin.print.text.LinePrinter lp = null;
        if (isPropertyFile(filename)) {
            lp = new org.acm.seguin.print.text.PropertyLinePrinter();
        } else if (isMarkupLanguage(filename)) {
            lp = new org.acm.seguin.print.xml.XMLLinePrinter();
        } else {
            lp = new org.acm.seguin.print.text.NumberedLinePrinter();
        }
        new org.acm.seguin.print.text.PrintingThread(filename, contents, lp).start();
    }

    /**
     * Gets the PropertyFile attribute of the ElixirTextPrinter object
     *
     * @param fullFilename
     * 		Description of Parameter
     * @return The PropertyFile value
     */
    private boolean isPropertyFile(java.lang.String fullFilename) {
        java.lang.String ext = getExtension(fullFilename);
        return ext.equals("properties") || ext.equals("settings");
    }

    /**
     * Gets the PropertyFile attribute of the ElixirTextPrinter object
     *
     * @param fullFilename
     * 		Description of Parameter
     * @return The PropertyFile value
     */
    private boolean isMarkupLanguage(java.lang.String fullFilename) {
        java.lang.String ext = getExtension(fullFilename);
        return ext.endsWith("ml");
    }

    /**
     * Gets the Extension attribute of the ElixirTextPrinter object
     *
     * @param filename
     * 		Description of Parameter
     * @return The Extension value
     */
    private java.lang.String getExtension(java.lang.String filename) {
        int ndx = filename.lastIndexOf(".");
        if (ndx == (-1)) {
            return "";
        }
        return filename.substring(ndx + 1);
    }
}