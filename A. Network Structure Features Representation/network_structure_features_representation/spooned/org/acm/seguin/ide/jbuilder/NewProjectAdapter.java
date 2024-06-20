/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
/**
 * New project adapter that adds source directories to the current reloader.
 *
 * @author Chris Seguin
 */
public class NewProjectAdapter extends org.acm.seguin.ide.jbuilder.BrowserAdapter {
    private java.util.LinkedList list;

    /**
     * Constructor for the NewProjectAdapter object
     */
    public NewProjectAdapter() {
        list = new java.util.LinkedList();
    }

    /**
     * A particular project was activated
     *
     * @param browser
     * 		The browser that it was activated in
     * @param project
     * 		The project
     */
    public void browserProjectActivated(org.acm.seguin.ide.jbuilder.Browser browser, org.acm.seguin.ide.jbuilder.Project project) {
        if (!list.contains(project)) {
            list.add(project);
            if (project instanceof org.acm.seguin.ide.jbuilder.JBProject) {
                org.acm.seguin.ide.common.MultipleDirClassDiagramReloader reloader = org.acm.seguin.ide.jbuilder.UMLNodeViewerFactory.getFactory().getReloader();
                org.acm.seguin.ide.jbuilder.JBProject jbProject = ((org.acm.seguin.ide.jbuilder.JBProject) (project));
                registerProject(reloader, jbProject);
                reloader.reload();
            }
        }
    }

    /**
     * A project was closed in a particular browser
     *
     * @param browser
     * 		the browser
     * @param project
     * 		the project
     */
    public void browserProjectClosed(org.acm.seguin.ide.jbuilder.Browser browser, org.acm.seguin.ide.jbuilder.Project project) {
        list.remove(project);
        org.acm.seguin.ide.common.MultipleDirClassDiagramReloader reloader = org.acm.seguin.ide.jbuilder.UMLNodeViewerFactory.getFactory().getReloader();
        reloader.clear();
        java.util.Iterator iter = list.iterator();
        while (iter.hasNext()) {
            org.acm.seguin.ide.jbuilder.JBProject jbProject = ((org.acm.seguin.ide.jbuilder.JBProject) (iter.next()));
            registerProject(reloader, jbProject);
        } 
    }

    /**
     * Registers a project with the directory reloader
     *
     * @param reloader
     * 		the reloader
     * @param jbProject
     * 		the JBuilder project
     */
    private void registerProject(org.acm.seguin.ide.common.MultipleDirClassDiagramReloader reloader, org.acm.seguin.ide.jbuilder.JBProject jbProject) {
        org.acm.seguin.ide.jbuilder.ProjectPathSet paths = jbProject.getPaths();
        org.acm.seguin.ide.jbuilder.Url[] dirs = paths.getSourcePath();
        for (int ndx = 0; ndx < dirs.length; ndx++) {
            java.lang.String directory = dirs[ndx].getFile();
            reloader.addRootDirectory(directory);
        }
    }
}