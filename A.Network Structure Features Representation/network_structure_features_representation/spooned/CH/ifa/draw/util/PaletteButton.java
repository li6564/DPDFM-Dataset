/* @(#)PaletteButton.java 5.1 */
package CH.ifa.draw.util;
/**
 * A palette button is a three state button. The states are normal
 * pressed and selected. It uses to the palette listener interface
 * to notify about state changes.
 *
 * @see PaletteListener
 * @see PaletteLayout
 */
public abstract class PaletteButton extends java.awt.Canvas implements java.awt.event.MouseListener , java.awt.event.MouseMotionListener {
    static final int NORMAL = 1;

    static final int PRESSED = 2;

    static final int SELECTED = 3;

    private CH.ifa.draw.util.PaletteListener fListener;

    private int fState;

    private int fOldState;

    /**
     * Constructs a PaletteButton.
     *
     * @param listener
     * 		the listener to be notified.
     */
    public PaletteButton(CH.ifa.draw.util.PaletteListener listener) {
        fListener = listener;
        fState = fOldState = CH.ifa.draw.util.PaletteButton.NORMAL;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public abstract void paintBackground(java.awt.Graphics g);

    public abstract void paintNormal(java.awt.Graphics g);

    public abstract void paintPressed(java.awt.Graphics g);

    public abstract void paintSelected(java.awt.Graphics g);

    public java.lang.Object value() {
        return null;
    }

    public java.lang.String name() {
        return "";
    }

    public void reset() {
        fState = CH.ifa.draw.util.PaletteButton.NORMAL;
        repaint();
    }

    public void select() {
        fState = CH.ifa.draw.util.PaletteButton.SELECTED;
        repaint();
    }

    public void mousePressed(java.awt.event.MouseEvent e) {
        fOldState = fState;
        fState = CH.ifa.draw.util.PaletteButton.PRESSED;
        repaint();
    }

    public void mouseDragged(java.awt.event.MouseEvent e) {
        if (contains(e.getX(), e.getY()))
            fState = CH.ifa.draw.util.PaletteButton.PRESSED;
        else
            fState = fOldState;

        repaint();
    }

    public void mouseReleased(java.awt.event.MouseEvent e) {
        fState = fOldState;
        repaint();
        if (contains(e.getX(), e.getY()))
            fListener.paletteUserSelected(this);

    }

    public void mouseMoved(java.awt.event.MouseEvent e) {
        fListener.paletteUserOver(this, true);
    }

    public void mouseExited(java.awt.event.MouseEvent e) {
        // JDK1.1 on Windows sometimes looses mouse released
        if (fState == CH.ifa.draw.util.PaletteButton.PRESSED)
            mouseDragged(e);

        fListener.paletteUserOver(this, false);
    }

    public void mouseClicked(java.awt.event.MouseEvent e) {
    }

    public void mouseEntered(java.awt.event.MouseEvent e) {
    }

    public void update(java.awt.Graphics g) {
        paint(g);
    }

    public void paint(java.awt.Graphics g) {
        paintBackground(g);
        switch (fState) {
            case CH.ifa.draw.util.PaletteButton.PRESSED :
                paintPressed(g);
                break;
            case CH.ifa.draw.util.PaletteButton.SELECTED :
                paintSelected(g);
                break;
            case CH.ifa.draw.util.PaletteButton.NORMAL :
            default :
                paintNormal(g);
                break;
        }
    }
}