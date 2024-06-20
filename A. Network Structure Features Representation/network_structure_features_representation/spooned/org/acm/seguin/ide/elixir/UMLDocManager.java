/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.elixir;
/**
 * Document manager for UML files
 *
 * @author Chris Seguin
 */
public class UMLDocManager implements org.acm.seguin.ide.elixir.DocManager {
    private org.acm.seguin.ide.common.SingleDirClassDiagramReloader reloader;

    /**
     * Constructor for the UMLDocManager object
     */
    public UMLDocManager() {
        reloader = new org.acm.seguin.ide.common.SingleDirClassDiagramReloader();
        org.acm.seguin.ide.elixir.ElixirClassDiagramLoader.register(reloader);
    }

    /**
     * Get the default extension associated with this document type
     *
     * @return The Extension value
     */
    public java.lang.String getExtension() {
        return "uml";
    }

    /**
     * Get the name of the document type.
     *
     * @return The Name value
     */
    public java.lang.String getName() {
        return "UML Class Diagrams";
    }

    /**
     * Create a new TNode which can represent the given file within the project
     *  tree.
     *
     * @param parent
     * 		Description of Parameter
     * @param file
     * 		Description of Parameter
     * @return The TreeNode value
     */
    public org.acm.seguin.ide.elixir.TNode getTreeNode(org.acm.seguin.ide.elixir.TNode parent, java.io.File file) {
        return new org.acm.seguin.ide.elixir.UMLLeaf(parent, file, this);
    }

    /**
     * Create a new ViewManager which can show the given filename
     *
     * @param filename
     * 		Description of Parameter
     * @return The ViewManager value
     */
    public org.acm.seguin.ide.elixir.ViewManager getViewManager(java.lang.String filename) {
        return new org.acm.seguin.ide.elixir.UMLViewManager(this, filename, org.acm.seguin.ide.elixir.RefactoryExtension.base);
    }

    /**
     * Determine whether this document type can be created by the user through
     *  the "New File" dialog.
     *
     * @return The UserCreated value
     */
    public boolean isUserCreated() {
        return true;
    }

    /**
     * Test whether a filename is valid for a particular kind of document
     *
     * @param filename
     * 		Description of Parameter
     * @return The ValidFilename value
     */
    public boolean isValidFilename(java.lang.String filename) {
        return filename.endsWith(".uml");
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public java.lang.String toString() {
        return "UML Class Diagram";
    }

    /**
     * Gets the reloader object
     */
    public org.acm.seguin.ide.common.SingleDirClassDiagramReloader getReloader() {
        return reloader;
    }
}