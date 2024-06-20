/* @(#)SelectAreaTracker.java 5.1 */
package CH.ifa.draw.standard;
/**
 * SelectAreaTracker implements a rubberband selection of an area.
 */
public class SelectAreaTracker extends CH.ifa.draw.standard.AbstractTool {
    private java.awt.Rectangle fSelectGroup;

    public SelectAreaTracker(CH.ifa.draw.framework.DrawingView view) {
        super(view);
    }

    public void mouseDown(java.awt.event.MouseEvent e, int x, int y) {
        // use event coordinates to supress any kind of
        // transformations like constraining points to a grid
        super.mouseDown(e, e.getX(), e.getY());
        rubberBand(fAnchorX, fAnchorY, fAnchorX, fAnchorY);
    }

    public void mouseDrag(java.awt.event.MouseEvent e, int x, int y) {
        super.mouseDrag(e, x, y);
        eraseRubberBand();
        rubberBand(fAnchorX, fAnchorY, x, y);
    }

    public void mouseUp(java.awt.event.MouseEvent e, int x, int y) {
        super.mouseUp(e, x, y);
        eraseRubberBand();
        selectGroup(e.isShiftDown());
    }

    private void rubberBand(int x1, int y1, int x2, int y2) {
        fSelectGroup = new java.awt.Rectangle(new java.awt.Point(x1, y1));
        fSelectGroup.add(new java.awt.Point(x2, y2));
        drawXORRect(fSelectGroup);
    }

    private void eraseRubberBand() {
        drawXORRect(fSelectGroup);
    }

    private void drawXORRect(java.awt.Rectangle r) {
        java.awt.Graphics g = view().getGraphics();
        g.setXORMode(view().getBackground());
        g.setColor(java.awt.Color.black);
        g.drawRect(r.x, r.y, r.width, r.height);
    }

    private void selectGroup(boolean toggle) {
        CH.ifa.draw.framework.FigureEnumeration k = drawing().figuresReverse();
        while (k.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = k.nextFigure();
            java.awt.Rectangle r2 = figure.displayBox();
            if (fSelectGroup.contains(r2.x, r2.y) && fSelectGroup.contains(r2.x + r2.width, r2.y + r2.height)) {
                if (toggle)
                    view().toggleSelection(figure);
                else
                    view().addToSelection(figure);

            }
        } 
    }
}