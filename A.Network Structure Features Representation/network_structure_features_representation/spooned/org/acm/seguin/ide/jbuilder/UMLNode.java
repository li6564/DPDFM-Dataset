/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
import java.io.IOException;
import com.borland.primetime.PrimeTime;
import com.borland.primetime.actions.ActionGroup;
import com.borland.primetime.actions.UpdateAction;
import com.borland.primetime.editor.EditorManager;
import com.borland.primetime.ide.Browser;
import com.borland.primetime.ide.ProjectView;
import com.borland.primetime.node.DuplicateNodeException;
import com.borland.primetime.node.FileNode;
import com.borland.primetime.node.FileType;
import com.borland.primetime.node.Node;
import com.borland.primetime.node.Project;
import com.borland.primetime.vfs.InvalidUrlException;
import com.borland.primetime.vfs.Url;
/**
 * File node representing a UML class diagram
 *
 * @author Chris Seguin
 */
public class UMLNode extends com.borland.primetime.node.FileNode {
    private org.acm.seguin.uml.UMLPackage packageDiagram;

    private java.lang.String packageName;

    /**
     * Constructor for the UMLNode object
     *
     * @param project
     * 		Description of Parameter
     * @param parent
     * 		Description of Parameter
     * @param url
     * 		Description of Parameter
     * @exception DuplicateNodeException
     * 		Description of Exception
     */
    public UMLNode(com.borland.primetime.node.Project project, com.borland.primetime.node.Node parent, com.borland.primetime.vfs.Url url) throws com.borland.primetime.node.DuplicateNodeException {
        super(project, parent, url);
        org.acm.seguin.ide.common.MultipleDirClassDiagramReloader reloader = org.acm.seguin.ide.jbuilder.UMLNodeViewerFactory.getFactory().getReloader();
        if (!reloader.isNecessary()) {
            reloader.setNecessary(true);
            reloader.reload();
        }
        org.acm.seguin.ide.common.PackageNameLoader loader = new org.acm.seguin.ide.common.PackageNameLoader();
        packageName = loader.load(url.getFile());
    }

    /**
     * Sets the Diagram attribute of the UMLNode object
     *
     * @param diagram
     * 		The new Diagram value
     */
    public void setDiagram(org.acm.seguin.uml.UMLPackage diagram) {
        packageDiagram = diagram;
    }

    /**
     * Gets the Diagram attribute of the UMLNode object
     *
     * @return The Diagram value
     */
    public org.acm.seguin.uml.UMLPackage getDiagram() {
        return packageDiagram;
    }

    /**
     * Gets the Persistant attribute of the UMLNode object
     *
     * @return The Persistant value
     */
    public boolean isPersistant() {
        return false;
    }

    /**
     * Gets the DisplayIcon attribute of the UMLNode object
     *
     * @return The DisplayIcon value
     */
    public javax.swing.Icon getDisplayIcon() {
        return new org.acm.seguin.ide.common.UMLIcon();
    }

    /**
     * Gets the DisplayName attribute of the UMLNode object
     *
     * @return The DisplayName value
     */
    public java.lang.String getDisplayName() {
        if ((com.borland.primetime.PrimeTime.CURRENT_MAJOR_VERSION >= 4) && (com.borland.primetime.PrimeTime.CURRENT_MINOR_VERSION >= 1)) {
            return packageName + ".uml";
        }
        return packageName;
    }

    /**
     * Determines if the diagram has been modified
     *
     * @return true if it has
     */
    public boolean isModified() {
        if (packageDiagram == null) {
            return false;
        }
        return packageDiagram.isDirty();
    }

    /**
     * Description of the Method
     *
     * @param url
     * 		Description of Parameter
     * @exception IOException
     * 		Description of Exception
     * @exception InvalidUrlException
     * 		Description of Exception
     * @exception DuplicateNodeException
     * 		Description of Exception
     */
    public void saveAs(com.borland.primetime.vfs.Url url) throws java.io.IOException, com.borland.primetime.vfs.InvalidUrlException, com.borland.primetime.node.DuplicateNodeException {
        save();
    }

    /**
     * Saves the diagram to the disk
     *
     * @exception IOException
     * 		Description of Exception
     * @exception InvalidUrlException
     * 		Description of Exception
     */
    public void save() throws java.io.IOException, com.borland.primetime.vfs.InvalidUrlException {
        if (packageDiagram != null) {
            packageDiagram.save();
        }
    }

    /**
     * Initialize the open tools
     *
     * @param majorVersion
     * 		the version number
     * @param minorVersion
     * 		the version number
     */
    public static void initOpenTool(byte majorVersion, byte minorVersion) {
        if (majorVersion != 4) {
            return;
        }
        java.lang.System.out.println(((((((("Version:  " + majorVersion) + ".") + minorVersion) + "     (Primetime:  ") + com.borland.primetime.PrimeTime.CURRENT_MAJOR_VERSION) + ".") + com.borland.primetime.PrimeTime.CURRENT_MINOR_VERSION) + ")");
        // Create the property files
        new org.acm.seguin.tools.install.RefactoryInstaller(true).run();
        org.acm.seguin.ide.jbuilder.UMLNode.cleanJBuilderSetting();
        // Register the source browser
        org.acm.seguin.ide.common.SourceBrowser.set(new org.acm.seguin.ide.jbuilder.JBuilderBrowser());
        org.acm.seguin.ide.common.EditorOperations.register(new org.acm.seguin.ide.jbuilder.JBuilderEditorOperations());
        org.acm.seguin.ide.common.action.CurrentSummary.register(new org.acm.seguin.ide.jbuilder.refactor.JBuilderCurrentSummary());
        // Initialize OpenTool here...
        com.borland.primetime.node.FileType.registerFileType("uml", new com.borland.primetime.node.FileType("Class Diagram", org.acm.seguin.ide.jbuilder.UMLNode.class, new org.acm.seguin.ide.jbuilder.TestObject(), new org.acm.seguin.ide.common.UMLIcon()));
        com.borland.primetime.node.FileNode.registerFileNodeClass("uml", "Class Diagram", org.acm.seguin.ide.jbuilder.UMLNode.class, new org.acm.seguin.ide.common.UMLIcon());
        com.borland.primetime.ide.Browser.registerNodeViewerFactory(org.acm.seguin.ide.jbuilder.UMLNodeViewerFactory.getFactory(), true);
        com.borland.primetime.ide.Browser.addStaticBrowserListener(new org.acm.seguin.ide.jbuilder.NewProjectAdapter());
        // Browser.addStaticBrowserListener(new RefactoringAdapter());
        // Adds a menu item
        com.borland.primetime.actions.ActionGroup group = new com.borland.primetime.actions.ActionGroup("JRefactory");
        javax.swing.Action prettyPrintAction = new org.acm.seguin.ide.common.action.PrettyPrinterAction();
        prettyPrintAction.putValue(UpdateAction.ACCELERATOR, prettyPrintAction.getValue(org.acm.seguin.ide.common.action.GenericAction.ACCELERATOR));
        group.add(prettyPrintAction);
        javax.swing.Action extractMethodAction = new org.acm.seguin.ide.common.action.ExtractMethodAction();
        extractMethodAction.putValue(UpdateAction.ACCELERATOR, extractMethodAction.getValue(org.acm.seguin.ide.common.action.GenericAction.ACCELERATOR));
        group.add(extractMethodAction);
        group.add(new org.acm.seguin.ide.jbuilder.ReloadAction());
        group.add(new org.acm.seguin.ide.jbuilder.NewClassDiagramAction());
        group.add(org.acm.seguin.ide.jbuilder.refactor.MenuBuilder.build());
        group.add(new org.acm.seguin.ide.jbuilder.UndoAction());
        group.add(new org.acm.seguin.ide.jbuilder.PrintAction());
        group.add(new org.acm.seguin.ide.jbuilder.JPGFileAction());
        com.borland.primetime.actions.ActionGroup zoomGroup = new com.borland.primetime.actions.ActionGroup("Zoom");
        zoomGroup.setPopup(true);
        zoomGroup.add(new org.acm.seguin.ide.jbuilder.ZoomAction(0.1));
        zoomGroup.add(new org.acm.seguin.ide.jbuilder.ZoomAction(0.25));
        zoomGroup.add(new org.acm.seguin.ide.jbuilder.ZoomAction(0.5));
        zoomGroup.add(new org.acm.seguin.ide.jbuilder.ZoomAction(1.0));
        group.add(zoomGroup);
        group.add(new org.acm.seguin.ide.jbuilder.AboutAction());
        com.borland.primetime.ide.Browser.addMenuGroup(8, group);
        com.borland.primetime.ide.ProjectView.registerContextActionProvider(new org.acm.seguin.ide.jbuilder.ProjectViewRefactorings());
        org.acm.seguin.ide.jbuilder.refactor.JBuilderRefactoringFactory.register();
        org.acm.seguin.ide.jbuilder.UMLNode.setupKeys(prettyPrintAction, extractMethodAction);
    }

    /**
     * Setup the key maps
     *
     * @param prettyPrint
     * 		the pretty print action
     * @param extractMethod
     * 		the extract method action
     */
    private static void setupKeys(javax.swing.Action prettyPrint, javax.swing.Action extractMethod) {
        org.acm.seguin.ide.jbuilder.ModifyKeyBinding m = new org.acm.seguin.ide.jbuilder.ModifyKeyBinding(prettyPrint, extractMethod);
        com.borland.primetime.editor.EditorManager.addPropertyChangeListener(m);
    }

    /**
     * Description of the Method
     */
    private static void cleanJBuilderSetting() {
        java.lang.String dir = (org.acm.seguin.util.FileSettings.getSettingsRoot() + java.io.File.separator) + ".Refactory";
        java.lang.String filename = (dir + java.io.File.separator) + "jbuilder.settings";
        java.io.File file = new java.io.File(filename);
        if (file.exists()) {
            file.delete();
        }
    }
}