/* @(#)ConnectedTextTool.java 5.1 */
package CH.ifa.draw.figures;
/**
 * Tool to create new or edit existing text figures.
 * A new text figure is connected with the clicked figure.
 *
 * @see TextHolder
 */
public class ConnectedTextTool extends CH.ifa.draw.figures.TextTool {
    boolean fConnected = false;

    public ConnectedTextTool(CH.ifa.draw.framework.DrawingView view, CH.ifa.draw.framework.Figure prototype) {
        super(view, prototype);
    }

    /**
     * If the pressed figure is a TextHolder it can be edited otherwise
     * a new text figure is created.
     */
    public void mouseDown(java.awt.event.MouseEvent e, int x, int y) {
        super.mouseDown(e, x, y);
        CH.ifa.draw.framework.Figure pressedFigure = drawing().findFigureInside(x, y);
        CH.ifa.draw.standard.TextHolder textHolder = ((CH.ifa.draw.standard.TextHolder) (createdFigure()));
        if ((((!fConnected) && (pressedFigure != null)) && (textHolder != null)) && (pressedFigure != textHolder)) {
            textHolder.connect(pressedFigure);
            fConnected = true;
        }
    }

    /**
     * If the pressed figure is a TextHolder it can be edited otherwise
     * a new text figure is created.
     */
    public void activate() {
        fConnected = false;
    }
}