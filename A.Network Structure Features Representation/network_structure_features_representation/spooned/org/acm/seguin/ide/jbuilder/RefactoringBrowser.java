/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.jbuilder;
import com.borland.primetime.ide.Context;
import com.borland.primetime.viewer.TextNodeViewer;
/**
 * A refactoring browser for JBuilder
 *
 * @author Chris Seguin
 */
public class RefactoringBrowser extends com.borland.primetime.viewer.TextNodeViewer {
    /**
     * Constructor for the RefactoringBrowser object
     *
     * @param context
     * 		Information to start up the viewer
     */
    public RefactoringBrowser(com.borland.primetime.ide.Context context) {
        super(context);
    }

    /**
     * Gets the ViewerTitle attribute of the AbstractNodeViewer object
     *
     * @return The ViewerTitle value
     */
    public java.lang.String getViewerTitle() {
        return "JRefactory";
    }
}