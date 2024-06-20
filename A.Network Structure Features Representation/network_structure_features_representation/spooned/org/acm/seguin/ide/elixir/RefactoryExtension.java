/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.ide.elixir;
import com.sun.corba.se.internal.iiop.messages.Message;
/* </Imports> */
/**
 * Refactory extension loads the refactory tool into memory
 *
 * @author Chris Seguin
 * @created April 4, 2000
 */
public class RefactoryExtension extends org.acm.seguin.ide.elixir.PrettyPrinterExtension implements org.acm.seguin.ide.elixir.ApplicationBus.ICard {
    private org.acm.seguin.ide.elixir.UMLDocManager manager;

    /**
     * Stores the base directory for the source code
     */
    static java.lang.String base;

    /**
     * Gets the Name attribute of the Refactory extension
     *
     * @return The Name value
     */
    public java.lang.String getName() {
        return "Refactory";
    }

    /**
     * Gets the CardName attribute of the Refactory
     *
     * @return The CardName value
     */
    public java.lang.String getCardName() {
        return "Refactory";
    }

    /**
     * Initializes the extension
     *
     * @param args
     * 		the arguments
     * @return true if installed
     */
    public boolean init(java.lang.String[] args) {
        org.acm.seguin.ide.elixir.FrameManager fm = org.acm.seguin.ide.elixir.FrameManager.current();
        if (fm == null) {
            return false;
        }
        new org.acm.seguin.tools.install.RefactoryInstaller(true).run();
        manager = new org.acm.seguin.ide.elixir.UMLDocManager();
        fm.addDocManager(manager);
        org.acm.seguin.ide.elixir.Folder.addOpenFileFilter(".uml", "Class Diagrams (*.uml)");
        org.acm.seguin.ide.elixir.ApplicationBus.addCard(this);
        boolean result = super.init(args);
        org.acm.seguin.ide.elixir.ZoomDiagram.tenPercent();
        new org.acm.seguin.ide.elixir.UndoMenuItem();
        new org.acm.seguin.ide.elixir.ElixirClassDiagramLoader();
        try {
            new org.acm.seguin.ide.elixir.ElixirExtractMethod();
        } catch (java.lang.Exception exc) {
        }
        org.acm.seguin.ide.common.SourceBrowser.set(new org.acm.seguin.ide.elixir.ElixirSourceBrowser());
        org.acm.seguin.ide.elixir.FrameManager.current().addMenuItem("Script|JRefactory|Extract Method=((method \"extractMethod\" \"org.acm.seguin.ide.elixir.ElixirExtractMethod\"))");
        org.acm.seguin.ide.elixir.FrameManager.current().addMenuItem("Script|JRefactory|Reload Diagrams=((method \"reload\" \"org.acm.seguin.ide.elixir.ElixirClassDiagramLoader\"))");
        org.acm.seguin.ide.elixir.FrameManager.current().addMenuItem("Script|JRefactory|Undo Refactoring=((method \"undo\" \"org.acm.seguin.ide.elixir.UndoMenuItem\"))");
        org.acm.seguin.ide.elixir.FrameManager.current().addMenuItem("Script|JRefactory|Zoom|10%=((method \"tenPercent\" \"org.acm.seguin.ide.elixir.ZoomDiagram\"))");
        org.acm.seguin.ide.elixir.FrameManager.current().addMenuItem("Script|JRefactory|Zoom|25%=((method \"twentyfivePercent\" \"org.acm.seguin.ide.elixir.ZoomDiagram\"))");
        org.acm.seguin.ide.elixir.FrameManager.current().addMenuItem("Script|JRefactory|Zoom|50%=((method \"fiftyPercent\" \"org.acm.seguin.ide.elixir.ZoomDiagram\"))");
        org.acm.seguin.ide.elixir.FrameManager.current().addMenuItem("Script|JRefactory|Zoom|100%=((method \"fullSize\" \"org.acm.seguin.ide.elixir.ZoomDiagram\"))");
        org.acm.seguin.ide.elixir.FrameManager.current().addMenuItem("Script|JRefactory|About JRefactory=((method \"run\" \"org.acm.seguin.awt.AboutBox\"))");
        return result;
    }

    /**
     * Removes the extension mechanism
     *
     * @return Always returns true
     */
    public boolean destroy() {
        org.acm.seguin.ide.elixir.ApplicationBus.removeCard(this);
        return super.destroy();
    }

    /**
     * Listener for GUI change events
     *
     * @param msg
     * 		the message
     */
    public void update(com.sun.corba.se.internal.iiop.messages.Message msg) {
        org.acm.seguin.ide.common.SingleDirClassDiagramReloader reloader = manager.getReloader();
        org.acm.seguin.ide.elixir.MsgType type = msg.getType();
        if (type == MsgType.PROJECT_OPENED) {
            org.acm.seguin.ide.elixir.RefactoryExtension.base = org.acm.seguin.ide.elixir.SettingManager.getSetting("WorkRoot");
            reloader.setRootDirectory(org.acm.seguin.ide.elixir.RefactoryExtension.base);
            java.lang.Thread anonymous = new java.lang.Thread() {
                /**
                 * Main processing method for the RefactoryExtension object
                 */
                public void run() {
                    org.acm.seguin.uml.loader.ReloaderSingleton.reload();
                }
            };
            anonymous.start();
        } else if (type == MsgType.PROJECT_CLOSED) {
            reloader.clear();
        } else if (type == MsgType.DOCUMENT_OPENED) {
            if (msg.getData() instanceof org.acm.seguin.ide.elixir.UMLViewManager) {
                org.acm.seguin.ide.elixir.UMLViewManager view = ((org.acm.seguin.ide.elixir.UMLViewManager) (msg.getData()));
                reloader.add(view.getDiagram());
            }
        } else if (type == MsgType.DOCUMENT_CLOSED) {
            if (msg.getData() instanceof org.acm.seguin.ide.elixir.UMLViewManager) {
                org.acm.seguin.ide.elixir.UMLViewManager view = ((org.acm.seguin.ide.elixir.UMLViewManager) (msg.getData()));
                reloader.remove(view.getDiagram());
            }
        }
    }

    /**
     * Opportunity to veto a message
     *
     * @param msg
     * 		the message
     */
    public void veto(com.sun.corba.se.internal.iiop.messages.Message msg) {
        // no veto
    }
}