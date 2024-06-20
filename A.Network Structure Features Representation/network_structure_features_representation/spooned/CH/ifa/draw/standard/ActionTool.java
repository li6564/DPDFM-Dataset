/* @(#)ActionTool.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A tool that performs an action when it is active and
 * the mouse is clicked.
 */
public abstract class ActionTool extends CH.ifa.draw.standard.AbstractTool {
    public ActionTool(CH.ifa.draw.framework.DrawingView itsView) {
        super(itsView);
    }

    /**
     * Add the touched figure to the selection an invoke action
     *
     * @see #action()
     */
    public void mouseDown(java.awt.event.MouseEvent e, int x, int y) {
        CH.ifa.draw.framework.Figure target = drawing().findFigure(x, y);
        if (target != null) {
            view().addToSelection(target);
            action(target);
        }
    }

    public void mouseUp(java.awt.event.MouseEvent e, int x, int y) {
        editor().toolDone();
    }

    /**
     * Performs an action with the touched figure.
     */
    public abstract void action(CH.ifa.draw.framework.Figure figure);
}