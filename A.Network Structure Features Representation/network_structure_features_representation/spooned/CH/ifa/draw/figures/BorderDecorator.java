/* @(#)BorderDecorator.java 5.1 */
package CH.ifa.draw.figures;
/**
 * BorderDecorator decorates an arbitrary Figure with
 * a border.
 */
public class BorderDecorator extends CH.ifa.draw.standard.DecoratorFigure {
    /* Serialization support. */
    private static final long serialVersionUID = 1205601808259084917L;

    private int borderDecoratorSerializedDataVersion = 1;

    public BorderDecorator() {
    }

    public BorderDecorator(CH.ifa.draw.framework.Figure figure) {
        super(figure);
    }

    private java.awt.Point border() {
        return new java.awt.Point(3, 3);
    }

    /**
     * Draws a the figure and decorates it with a border.
     */
    public void draw(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        super.draw(g);
        g.setColor(java.awt.Color.white);
        g.drawLine(r.x, r.y, r.x, r.y + r.height);
        g.drawLine(r.x, r.y, r.x + r.width, r.y);
        g.setColor(java.awt.Color.gray);
        g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height);
        g.drawLine(r.x, r.y + r.height, r.x + r.width, r.y + r.height);
    }

    /**
     * Gets the displaybox including the border.
     */
    public java.awt.Rectangle displayBox() {
        java.awt.Rectangle r = fComponent.displayBox();
        r.grow(border().x, border().y);
        return r;
    }

    /**
     * Invalidates the figure extended by its border.
     */
    public void figureInvalidated(CH.ifa.draw.framework.FigureChangeEvent e) {
        java.awt.Rectangle rect = e.getInvalidatedRectangle();
        rect.grow(border().x, border().y);
        super.figureInvalidated(new CH.ifa.draw.framework.FigureChangeEvent(e.getFigure(), rect));
    }

    public java.awt.Insets connectionInsets() {
        java.awt.Insets i = super.connectionInsets();
        i.top -= 3;
        i.bottom -= 3;
        i.left -= 3;
        i.right -= 3;
        return i;
    }
}