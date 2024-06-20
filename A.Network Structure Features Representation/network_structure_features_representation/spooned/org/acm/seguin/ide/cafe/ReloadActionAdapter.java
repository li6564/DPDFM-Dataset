/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.ide.cafe;
/**
 * Reloads class diagrams for Visual Cafe
 *
 * @author Chris Seguin
 */
public class ReloadActionAdapter implements java.awt.event.ActionListener {
    private static org.acm.seguin.ide.common.MultipleDirClassDiagramReloader reloader = null;

    /**
     * The reload action
     *
     * @param evt
     * 		the action that occurred
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (org.acm.seguin.ide.cafe.ReloadActionAdapter.reloader == null) {
            org.acm.seguin.ide.cafe.ReloadActionAdapter.reloader = new org.acm.seguin.ide.common.MultipleDirClassDiagramReloader();
            org.acm.seguin.uml.loader.ReloaderSingleton.register(org.acm.seguin.ide.cafe.ReloadActionAdapter.reloader);
        }
        try {
            org.acm.seguin.ide.cafe.VisualCafe vc = org.acm.seguin.ide.cafe.VisualCafe.getVisualCafe();
            org.acm.seguin.ide.cafe.VisualProject[] vps = vc.getProjects();
            for (int ndx = 0; ndx < vps.length; ndx++) {
                org.acm.seguin.ide.cafe.ReloadActionAdapter.reloader.addRootDirectory(getDirectory(vps[ndx]));
            }
        } catch (java.lang.Exception exc) {
            exc.printStackTrace(java.lang.System.out);
        }
        org.acm.seguin.ide.cafe.ReloadActionAdapter.reloader.setNecessary(true);
        org.acm.seguin.ide.cafe.ReloadActionAdapter.reloader.reload();
    }

    private java.lang.String getDirectory(org.acm.seguin.ide.cafe.VisualProject proj) throws java.net.MalformedURLException {
        java.net.URL url = proj.getDocumentBase();
        java.lang.System.out.println("url:  " + url.toString());
        java.lang.String filename = url.getFile().substring(1);
        java.lang.System.out.println("filename:  " + filename);
        int index = filename.lastIndexOf('/');
        java.lang.String parent = filename.substring(0, index);
        java.lang.System.out.println("Document base:  " + parent);
        return parent;
    }
}