/* @(#)MySelectionTool.java 5.1 */
package CH.ifa.draw.samples.javadraw;
/**
 * A SelectionTool that interprets double clicks to inspect the clicked figure
 */
public class MySelectionTool extends CH.ifa.draw.standard.SelectionTool {
    public MySelectionTool(CH.ifa.draw.framework.DrawingView view) {
        super(view);
    }

    /**
     * Handles mouse down events and starts the corresponding tracker.
     */
    public void mouseDown(java.awt.event.MouseEvent e, int x, int y) {
        if (e.getClickCount() == 2) {
            CH.ifa.draw.framework.Figure figure = drawing().findFigure(e.getX(), e.getY());
            if (figure != null) {
                inspectFigure(figure);
                return;
            }
        }
        super.mouseDown(e, x, y);
    }

    protected void inspectFigure(CH.ifa.draw.framework.Figure f) {
        java.lang.System.out.println("inspect figure" + f);
    }
}