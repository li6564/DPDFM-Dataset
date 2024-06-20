/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.command;
/**
 * Launch from the source browser
 *
 * @author Chris Seguin
 */
public class CommandLineSourceBrowser extends org.acm.seguin.ide.common.SourceBrowser {
    private java.lang.String pattern;

    /**
     * Constructor for the CommandLineSourceBrowser object
     */
    public CommandLineSourceBrowser() {
        try {
            org.acm.seguin.util.FileSettings umlBundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "uml");
            pattern = umlBundle.getString("source.editor");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            pattern = null;
        }
    }

    /**
     * Determine if we can go to the source code
     *
     * @return Description of the Returned Value
     */
    public boolean canBrowseSource() {
        return pattern != null;
    }

    /**
     * Command to go to the source code
     *
     * @param file
     * 		Description of Parameter
     * @param line
     * 		Description of Parameter
     */
    public void gotoSource(java.io.File file, int line) {
        try {
            java.lang.StringBuffer buffer = new java.lang.StringBuffer(pattern);
            int start = pattern.indexOf("$FILE");
            buffer.replace(start, start + 5, file.getCanonicalPath());
            java.lang.String temp = buffer.toString();
            start = temp.indexOf("$LINE");
            if (start != (-1)) {
                buffer.replace(start, start + 5, "" + line);
            }
            java.lang.String execute = buffer.toString();
            java.lang.System.out.println("Executing:  " + execute);
            java.lang.Runtime.getRuntime().exec(execute);
        } catch (java.lang.Exception exc) {
            java.lang.System.out.println("Unable to launch the editor from the command line");
            exc.printStackTrace();
        }
    }
}