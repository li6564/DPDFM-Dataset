/* @(#)PatternPainter.java 5.1 */
package CH.ifa.draw.samples.javadraw;
/**
 * PatternDrawer a background that can be added to a
 * drawing.
 *
 * @see DrawingView
 * @see Painter
 */
public class PatternPainter implements CH.ifa.draw.framework.Painter {
    private java.awt.Image fImage;

    public PatternPainter(java.awt.Image image) {
        fImage = image;
    }

    public void draw(java.awt.Graphics g, CH.ifa.draw.framework.DrawingView view) {
        drawPattern(g, fImage, view);
    }

    /**
     * Draws a pattern background pattern by replicating an image.
     */
    private void drawPattern(java.awt.Graphics g, java.awt.Image image, CH.ifa.draw.framework.DrawingView view) {
        int iwidth = image.getWidth(view);
        int iheight = image.getHeight(view);
        java.awt.Dimension d = view.getSize();
        int x = 0;
        int y = 0;
        while (y < d.height) {
            while (x < d.width) {
                g.drawImage(image, x, y, view);
                x += iwidth;
            } 
            y += iheight;
            x = 0;
        } 
    }
}