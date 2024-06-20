/* @(#)FontSizeHandle.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A Handle to change the font size by direct manipulation.
 */
public class FontSizeHandle extends CH.ifa.draw.standard.LocatorHandle {
    private java.awt.Font fFont;

    private int fSize;

    public FontSizeHandle(CH.ifa.draw.framework.Figure owner, CH.ifa.draw.framework.Locator l) {
        super(owner, l);
    }

    public void invokeStart(int x, int y, CH.ifa.draw.framework.DrawingView view) {
        CH.ifa.draw.figures.TextFigure textOwner = ((CH.ifa.draw.figures.TextFigure) (owner()));
        fFont = textOwner.getFont();
        fSize = fFont.getSize();
    }

    public void invokeStep(int x, int y, int anchorX, int anchorY, CH.ifa.draw.framework.DrawingView view) {
        CH.ifa.draw.figures.TextFigure textOwner = ((CH.ifa.draw.figures.TextFigure) (owner()));
        int newSize = (fSize + y) - anchorY;
        textOwner.setFont(new java.awt.Font(fFont.getName(), fFont.getStyle(), newSize));
    }

    public void draw(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.setColor(java.awt.Color.yellow);
        g.fillOval(r.x, r.y, r.width, r.height);
        g.setColor(java.awt.Color.black);
        g.drawOval(r.x, r.y, r.width, r.height);
    }
}