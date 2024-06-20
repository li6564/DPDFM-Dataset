/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.elixir;
/* </Imports> */
/**
 * Pretty Printer extension mechanism for Elixir 2.4
 *
 * @author Chris Seguin
 */
public class PrettyPrinterExtension implements org.acm.seguin.ide.elixir.IExtension {
    /**
     * Gets the Name attribute of the PrettyPrinterExtension object
     *
     * @return The Name value
     */
    public java.lang.String getName() {
        return "Pretty Printer";
    }

    /**
     * Gets the CardName attribute of the PrettyPrinterExtension object
     *
     * @return The CardName value
     */
    public java.lang.String getCardName() {
        return "Pretty Printer";
    }

    /**
     * Gets the Version attribute of the PrettyPrinterExtension object
     *
     * @return The Version value
     */
    public java.lang.String getVersion() {
        return new org.acm.seguin.JRefactoryVersion().toString();
    }

    /**
     * Gets the ReleaseNo attribute of the PrettyPrinterExtension object
     *
     * @return The ReleaseNo value
     */
    public int getReleaseNo() {
        return new org.acm.seguin.JRefactoryVersion().getBuild();
    }

    /**
     * Initializes the extension
     *
     * @param args
     * 		the arguments
     * @return true if installed
     */
    public boolean init(java.lang.String[] args) {
        if (org.acm.seguin.ide.elixir.FrameManager.current() == null) {
            java.lang.System.out.println((("Not installing " + getName()) + " ") + getVersion());
            return false;
        }
        java.lang.System.out.println((("Installing " + getName()) + " ") + getVersion());
        // Load the objects
        new org.acm.seguin.ide.elixir.ElixirPrettyPrinter();
        new org.acm.seguin.ide.elixir.ElixirTextPrinter();
        // Add the menu items
        org.acm.seguin.ide.elixir.FrameManager.current().addMenuItem("Script|JRefactory|Refresh=((method \"reload\" \"com.elixirtech.ide.edit.BasicViewManager\") (curr-vm))");
        org.acm.seguin.ide.elixir.FrameManager.current().addMenuItem("Script|JRefactory|Pretty Printer=((method \"prettyPrint\" \"org.acm.seguin.ide.elixir.ElixirPrettyPrinter\"))");
        org.acm.seguin.ide.elixir.FrameManager.current().addMenuItem("Script|JRefactory|Print=((method \"printCurrent\" \"org.acm.seguin.ide.elixir.ElixirTextPrinter\"))");
        return true;
    }

    /**
     * Removes the extension mechanism
     *
     * @return Always returns true
     */
    public boolean destroy() {
        return true;
    }
}