/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.ide.netbeans;
import org.openide.cookies.*;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
public class ExtractMethodAction extends org.openide.util.actions.CookieAction {
    /**
     * Gets the Name attribute of the ExtractMethodAction object
     *
     * @return The Name value
     */
    public java.lang.String getName() {
        return org.openide.util.NbBundle.getMessage(org.acm.seguin.ide.netbeans.ExtractMethodAction.class, "LBL_ExtractMethodAction");
    }

    /**
     * Gets the HelpCtx attribute of the ExtractMethodAction object
     *
     * @return The HelpCtx value
     */
    public org.openide.util.HelpCtx getHelpCtx() {
        return org.openide.util.HelpCtx.DEFAULT_HELP;
        // return new HelpCtx (ExtractMethodAction.class);
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    protected java.lang.Class[] cookieClasses() {
        return new java.lang.Class[]{ org.acm.seguin.ide.netbeans.EditorCookie.class };
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    protected int mode() {
        return MODE_EXACTLY_ONE;
    }

    /**
     * Description of the Method
     *
     * @param nodes
     * 		Description of Parameter
     */
    protected void performAction(org.openide.nodes.Node[] nodes) {
        EditorCookie cookie = ((EditorCookie) (nodes[0].getCookie(org.acm.seguin.ide.netbeans.EditorCookie.class)));
        try {
            // (new NetBeansExtractMethodDialog(cookie)).show();
            new org.acm.seguin.ide.netbeans.NetBeansExtractMethodDialog().show();
        } catch (org.acm.seguin.refactor.RefactoringException re) {
            // (PENDING) NetBeans specific exception
            javax.swing.JOptionPane.showMessageDialog(null, re.getMessage(), "Refactoring Exception", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    protected java.lang.String iconResource() {
        return null;
    }

    /**
     * Perform special enablement check in addition to the normal one.
     *
     * @param nodes
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    protected boolean enable(org.openide.nodes.Node[] nodes) {
        if (!super.enable(nodes)) {
            return false;
        }
        // Any additional checks ...
        return true;
    }

    /**
     * Perform extra initialization of this action's singleton. PLEASE do not
     *  use constructors for this purpose!
     */
    protected void initialize() {
        super.initialize();
        putProperty(org.acm.seguin.ide.netbeans.PrettyPrinterAction.SHORT_DESCRIPTION, org.openide.util.NbBundle.getMessage(org.acm.seguin.ide.netbeans.ExtractMethodAction.class, "HINT_ExtractMethodAction"));
    }
}