package org.acm.seguin.ide.netbeans;
import org.openide.cookies.*;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.*;
/**
 * Applies the JRefactory pretty printer to the currently selected editor. Will
 *  only be applied if only one editor is selected.
 */
public class PrettyPrinterAction extends CookieAction implements Presenter.Menu {
    public javax.swing.JMenuItem getMenuPresenter() {
        javax.swing.JMenuItem item = new javax.swing.JMenuItem(getName());
        item.addActionListener(this);
        return item;
    }

    public java.lang.String getName() {
        return org.openide.util.NbBundle.getMessage(org.acm.seguin.ide.netbeans.PrettyPrinterAction.class, "LBL_PrettyPrinterAction");
    }

    public org.openide.util.HelpCtx getHelpCtx() {
        return org.openide.util.HelpCtx.DEFAULT_HELP;
        // (PENDING) context help
        // return new HelpCtx (PrettyPrinterAction.class);
    }

    protected java.lang.Class[] cookieClasses() {
        return new java.lang.Class[]{ org.acm.seguin.ide.netbeans.EditorCookie.class };
    }

    /**
     *
     * @return MODE_EXACTLY_ONE
     */
    protected int mode() {
        return MODE_EXACTLY_ONE;
    }

    protected void performAction(org.openide.nodes.Node[] nodes) {
        EditorCookie cookie = ((EditorCookie) (nodes[0].getCookie(org.acm.seguin.ide.netbeans.EditorCookie.class)));
        // (PENDING) check for null editor pane
        org.acm.seguin.ide.netbeans.NetBeansPrettyPrinter prettyPrinter = new org.acm.seguin.ide.netbeans.NetBeansPrettyPrinter(cookie);
        prettyPrinter.prettyPrintCurrentWindow();
    }

    protected java.lang.String iconResource() {
        return null;
    }

    /**
     * Perform special enablement check in addition to the normal one.
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
        putProperty(org.acm.seguin.ide.netbeans.PrettyPrinterAction.SHORT_DESCRIPTION, org.openide.util.NbBundle.getMessage(org.acm.seguin.ide.netbeans.PrettyPrinterAction.class, "HINT_PrettyPrinterAction"));
    }
}