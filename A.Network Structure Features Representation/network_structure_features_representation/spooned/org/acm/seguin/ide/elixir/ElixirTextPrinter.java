/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.elixir;
/**
 * Text printer for elixir
 *
 * @author Chris Seguin
 * @created May 31, 1999
 */
public class ElixirTextPrinter extends org.acm.seguin.ide.common.TextPrinter {
    /**
     * Prints the current document
     */
    public void print() {
        // Make sure everything is installed properly
        new org.acm.seguin.tools.install.RefactoryInstaller(false).run();
        // Get the data from the window
        org.acm.seguin.ide.elixir.FrameManager fm = org.acm.seguin.ide.elixir.FrameManager.current();
        org.acm.seguin.ide.elixir.ViewManager currentView = fm.getViewSite().getCurrentViewManager();
        if (currentView instanceof org.acm.seguin.ide.elixir.UMLViewManager) {
            org.acm.seguin.ide.elixir.UMLViewManager node = ((org.acm.seguin.ide.elixir.UMLViewManager) (currentView));
            new org.acm.seguin.uml.print.PrintingThread(node.getDiagram()).start();
        } else {
            org.acm.seguin.ide.elixir.BasicViewManager bvm = ((org.acm.seguin.ide.elixir.BasicViewManager) (currentView));
            java.lang.String windowText = bvm.getContentsString();
            java.lang.String fullFilename = bvm.getTitle();
            java.io.File file = new java.io.File(fullFilename);
            print(file.getName(), windowText);
        }
    }

    /**
     * Prints the current document
     */
    public static void printCurrent() {
        new org.acm.seguin.ide.elixir.ElixirTextPrinter().print();
    }
}