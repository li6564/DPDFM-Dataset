/* @(#)BorderTool.java 5.1 */
package CH.ifa.draw.figures;
/**
 * BorderTool decorates the clicked figure with a BorderDecorator.
 *
 * @see BorderDecorator
 */
public class BorderTool extends CH.ifa.draw.standard.ActionTool {
    public BorderTool(CH.ifa.draw.framework.DrawingView view) {
        super(view);
    }

    /**
     * Decorates the clicked figure with a border.
     */
    public void action(CH.ifa.draw.framework.Figure figure) {
        drawing().replace(figure, new CH.ifa.draw.figures.BorderDecorator(figure));
    }
}