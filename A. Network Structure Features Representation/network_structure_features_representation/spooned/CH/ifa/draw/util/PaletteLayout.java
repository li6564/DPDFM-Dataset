/* @(#)PaletteLayout.java 5.1 */
package CH.ifa.draw.util;
/**
 * A custom layout manager for the palette.
 *
 * @see PaletteButton
 */
public class PaletteLayout implements java.awt.LayoutManager {
    private int fGap;

    private java.awt.Point fBorder;

    private boolean fVerticalLayout;

    /**
     * Initializes the palette layout.
     *
     * @param gap
     * 		the gap between palette entries.
     */
    public PaletteLayout(int gap) {
        this(gap, new java.awt.Point(0, 0), true);
    }

    public PaletteLayout(int gap, java.awt.Point border) {
        this(gap, border, true);
    }

    public PaletteLayout(int gap, java.awt.Point border, boolean vertical) {
        fGap = gap;
        fBorder = border;
        fVerticalLayout = vertical;
    }

    public void addLayoutComponent(java.lang.String name, java.awt.Component comp) {
    }

    public void removeLayoutComponent(java.awt.Component comp) {
    }

    public java.awt.Dimension preferredLayoutSize(java.awt.Container target) {
        return minimumLayoutSize(target);
    }

    public java.awt.Dimension minimumLayoutSize(java.awt.Container target) {
        java.awt.Dimension dim = new java.awt.Dimension(0, 0);
        int nmembers = target.getComponentCount();
        for (int i = 0; i < nmembers; i++) {
            java.awt.Component m = target.getComponent(i);
            if (m.isVisible()) {
                java.awt.Dimension d = m.getMinimumSize();
                if (fVerticalLayout) {
                    dim.width = java.lang.Math.max(dim.width, d.width);
                    if (i > 0)
                        dim.height += fGap;

                    dim.height += d.height;
                } else {
                    dim.height = java.lang.Math.max(dim.height, d.height);
                    if (i > 0)
                        dim.width += fGap;

                    dim.width += d.width;
                }
            }
        }
        java.awt.Insets insets = target.getInsets();
        dim.width += insets.left + insets.right;
        dim.width += 2 * fBorder.x;
        dim.height += insets.top + insets.bottom;
        dim.height += 2 * fBorder.y;
        return dim;
    }

    public void layoutContainer(java.awt.Container target) {
        java.awt.Insets insets = target.getInsets();
        int nmembers = target.getComponentCount();
        int x = insets.left + fBorder.x;
        int y = insets.top + fBorder.y;
        for (int i = 0; i < nmembers; i++) {
            java.awt.Component m = target.getComponent(i);
            if (m.isVisible()) {
                java.awt.Dimension d = m.getMinimumSize();
                m.setBounds(x, y, d.width, d.height);
                if (fVerticalLayout) {
                    y += d.height;
                    y += fGap;
                } else {
                    x += d.width;
                    x += fGap;
                }
            }
        }
    }
}