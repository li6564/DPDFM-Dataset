/* @(#)BufferedUpdateStrategy.java 5.1 */
package CH.ifa.draw.standard;
/**
 * The BufferedUpdateStrategy implements an update
 * strategy that first draws a view into a buffer
 * followed by copying the buffer to the DrawingView.
 *
 * @see DrawingView
 */
public class BufferedUpdateStrategy implements CH.ifa.draw.framework.Painter {
    /**
     * The offscreen image
     */
    private transient java.awt.Image fOffscreen;

    private int fImagewidth = -1;

    private int fImageheight = -1;

    /* Serialization support. */
    private static final long serialVersionUID = 6489532222954612824L;

    private int bufferedUpdateSerializedDataVersion = 1;

    /**
     * Draws the view contents.
     */
    public void draw(java.awt.Graphics g, CH.ifa.draw.framework.DrawingView view) {
        // create the buffer if necessary
        java.awt.Dimension d = view.getSize();
        if (((fOffscreen == null) || (d.width != fImagewidth)) || (d.height != fImageheight)) {
            fOffscreen = view.createImage(d.width, d.height);
            fImagewidth = d.width;
            fImageheight = d.height;
        }
        // let the view draw on offscreen buffer
        java.awt.Graphics g2 = fOffscreen.getGraphics();
        view.drawAll(g2);
        g.drawImage(fOffscreen, 0, 0, view);
    }
}