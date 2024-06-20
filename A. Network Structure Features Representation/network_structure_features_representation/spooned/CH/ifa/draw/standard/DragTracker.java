/* @(#)DragTracker.java 5.1 */
package CH.ifa.draw.standard;
/**
 * DragTracker implements the dragging of the clicked
 * figure.
 *
 * @see SelectionTool
 */
public class DragTracker extends CH.ifa.draw.standard.AbstractTool {
    private CH.ifa.draw.framework.Figure fAnchorFigure;

    private int fLastX;

    private int fLastY;

    // previous mouse position
    private boolean fMoved = false;

    public DragTracker(CH.ifa.draw.framework.DrawingView view, CH.ifa.draw.framework.Figure anchor) {
        super(view);
        fAnchorFigure = anchor;
    }

    public void mouseDown(java.awt.event.MouseEvent e, int x, int y) {
        super.mouseDown(e, x, y);
        fLastX = x;
        fLastY = y;
        if (e.isShiftDown()) {
            view().toggleSelection(fAnchorFigure);
            fAnchorFigure = null;
        } else if (!view().selection().contains(fAnchorFigure)) {
            view().clearSelection();
            view().addToSelection(fAnchorFigure);
        }
    }

    public void mouseDrag(java.awt.event.MouseEvent e, int x, int y) {
        super.mouseDrag(e, x, y);
        fMoved = (java.lang.Math.abs(x - fAnchorX) > 4) || (java.lang.Math.abs(y - fAnchorY) > 4);
        if (fMoved) {
            CH.ifa.draw.framework.FigureEnumeration figures = view().selectionElements();
            while (figures.hasMoreElements())
                figures.nextFigure().moveBy(x - fLastX, y - fLastY);

        }
        fLastX = x;
        fLastY = y;
    }
}