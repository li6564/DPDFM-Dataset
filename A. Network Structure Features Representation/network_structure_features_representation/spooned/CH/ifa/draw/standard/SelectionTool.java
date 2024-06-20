/* @(#)SelectionTool.java 5.1 */
package CH.ifa.draw.standard;
/**
 * Tool to select and manipulate figures.
 * A selection tool is in one of three states, e.g., background
 * selection, figure selection, handle manipulation. The different
 * states are handled by different child tools.
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b><a href=../pattlets/sld032.htm>State</a></b><br>
 * SelectionTool is the StateContext and child is the State.
 * The SelectionTool delegates state specific
 * behavior to its current child tool.
 * <hr>
 */
public class SelectionTool extends CH.ifa.draw.standard.AbstractTool {
    private CH.ifa.draw.framework.Tool fChild = null;

    public SelectionTool(CH.ifa.draw.framework.DrawingView view) {
        super(view);
    }

    /**
     * Handles mouse down events and starts the corresponding tracker.
     */
    public void mouseDown(java.awt.event.MouseEvent e, int x, int y) {
        // on Windows NT: AWT generates additional mouse down events
        // when the left button is down && right button is clicked.
        // To avoid dead locks we ignore such events
        if (fChild != null)
            return;

        view().freezeView();
        CH.ifa.draw.framework.Handle handle = view().findHandle(e.getX(), e.getY());
        if (handle != null) {
            fChild = createHandleTracker(view(), handle);
        } else {
            CH.ifa.draw.framework.Figure figure = drawing().findFigure(e.getX(), e.getY());
            if (figure != null) {
                fChild = createDragTracker(view(), figure);
            } else {
                if (!e.isShiftDown()) {
                    view().clearSelection();
                }
                fChild = createAreaTracker(view());
            }
        }
        fChild.mouseDown(e, x, y);
    }

    /**
     * Handles mouse drag events. The events are forwarded to the
     * current tracker.
     */
    public void mouseDrag(java.awt.event.MouseEvent e, int x, int y) {
        // JDK1.1 doesn't guarantee mouseDown, mouseDrag, mouseUp
        if (fChild != null)
            fChild.mouseDrag(e, x, y);

    }

    /**
     * Handles mouse up events. The events are forwarded to the
     * current tracker.
     */
    public void mouseUp(java.awt.event.MouseEvent e, int x, int y) {
        view().unfreezeView();
        // JDK1.1 doesn't guarantee mouseDown, mouseDrag, mouseUp
        if (fChild != null)
            fChild.mouseUp(e, x, y);

        fChild = null;
    }

    /**
     * Factory method to create a Handle tracker. It is used to track a handle.
     */
    protected CH.ifa.draw.framework.Tool createHandleTracker(CH.ifa.draw.framework.DrawingView view, CH.ifa.draw.framework.Handle handle) {
        return new CH.ifa.draw.standard.HandleTracker(view, handle);
    }

    /**
     * Factory method to create a Drag tracker. It is used to drag a figure.
     */
    protected CH.ifa.draw.framework.Tool createDragTracker(CH.ifa.draw.framework.DrawingView view, CH.ifa.draw.framework.Figure f) {
        return new CH.ifa.draw.standard.DragTracker(view, f);
    }

    /**
     * Factory method to create an area tracker. It is used to select an
     * area.
     */
    protected CH.ifa.draw.framework.Tool createAreaTracker(CH.ifa.draw.framework.DrawingView view) {
        return new CH.ifa.draw.standard.SelectAreaTracker(view);
    }
}