/* @(#)ToolButton.java 5.1 */
package CH.ifa.draw.standard;
/**
 * A PaletteButton that is associated with a tool.
 *
 * @see Tool
 */
public class ToolButton extends CH.ifa.draw.util.PaletteButton {
    private java.lang.String fName;

    private CH.ifa.draw.framework.Tool fTool;

    private CH.ifa.draw.util.PaletteIcon fIcon;

    public ToolButton(CH.ifa.draw.util.PaletteListener listener, java.lang.String iconName, java.lang.String name, CH.ifa.draw.framework.Tool tool) {
        super(listener);
        // use a Mediatracker to ensure that all the images are initially loaded
        CH.ifa.draw.util.Iconkit kit = CH.ifa.draw.util.Iconkit.instance();
        if (kit == null)
            throw new CH.ifa.draw.framework.HJDError("Iconkit instance isn't set");

        java.awt.Image im[] = new java.awt.Image[3];
        im[0] = kit.loadImageResource(iconName + "1.gif");
        im[1] = kit.loadImageResource(iconName + "2.gif");
        im[2] = kit.loadImageResource(iconName + "3.gif");
        java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
        for (int i = 0; i < 3; i++) {
            tracker.addImage(im[i], i);
        }
        try {
            tracker.waitForAll();
        } catch (java.lang.Exception e) {
        }
        fIcon = new CH.ifa.draw.util.PaletteIcon(new java.awt.Dimension(24, 24), im[0], im[1], im[2]);
        fTool = tool;
        fName = name;
    }

    public CH.ifa.draw.framework.Tool tool() {
        return fTool;
    }

    public java.lang.String name() {
        return fName;
    }

    public java.lang.Object attributeValue() {
        return tool();
    }

    public java.awt.Dimension getMinimumSize() {
        return new java.awt.Dimension(fIcon.getWidth(), fIcon.getHeight());
    }

    public java.awt.Dimension getPreferredSize() {
        return new java.awt.Dimension(fIcon.getWidth(), fIcon.getHeight());
    }

    public void paintBackground(java.awt.Graphics g) {
    }

    public void paintNormal(java.awt.Graphics g) {
        if (fIcon.normal() != null)
            g.drawImage(fIcon.normal(), 0, 0, this);

    }

    public void paintPressed(java.awt.Graphics g) {
        if (fIcon.pressed() != null)
            g.drawImage(fIcon.pressed(), 0, 0, this);

    }

    public void paintSelected(java.awt.Graphics g) {
        if (fIcon.selected() != null)
            g.drawImage(fIcon.selected(), 0, 0, this);

    }
}