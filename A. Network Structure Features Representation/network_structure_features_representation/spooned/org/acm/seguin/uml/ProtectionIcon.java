/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.uml;
/**
 * Icon that draws the protection symbol
 *
 * @author Chris Seguin
 */
public class ProtectionIcon extends org.acm.seguin.uml.UMLIcon {
    private int protection;

    private int type;

    private static final int CIRCLE = 0;

    private static final int LETTER = 1;

    /**
     * Constructor for the ProtectionIcon object
     *
     * @param wide
     * 		the size of the icon
     * @param high
     * 		the size of the icon
     */
    public ProtectionIcon(int wide, int high) {
        super(wide, high);
        try {
            org.acm.seguin.util.FileSettings umlBundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "uml");
            umlBundle.setContinuallyReload(false);
            java.lang.String pattern = umlBundle.getString("icon.type");
            if (pattern.equalsIgnoreCase("letter")) {
                type = org.acm.seguin.uml.ProtectionIcon.LETTER;
            } else {
                type = org.acm.seguin.uml.ProtectionIcon.CIRCLE;
            }
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            type = org.acm.seguin.uml.ProtectionIcon.CIRCLE;
        }
    }

    /**
     * Sets the Protection attribute of the ProtectionIcon object
     *
     * @param value
     * 		The new Protection value
     */
    public void setProtection(int value) {
        protection = value;
    }

    /**
     * Draws the icon
     *
     * @param c
     * 		The component on which we are drawing
     * @param g
     * 		The graphics object
     * @param x
     * 		the x location
     * @param y
     * 		the y location
     */
    public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
        if (type == org.acm.seguin.uml.ProtectionIcon.LETTER) {
            drawLetterIcon(g, x, y);
        } else {
            drawCircleIcon(g, x, y);
        }
    }

    /**
     * Draws the protection icon like a circle
     *
     * @param g
     * 		Description of Parameter
     * @param x
     * 		Description of Parameter
     * @param y
     * 		Description of Parameter
     */
    private void drawCircleIcon(java.awt.Graphics g, int x, int y) {
        g.setColor(org.acm.seguin.uml.UMLLine.getProtectionColor(protection));
        int wide = java.lang.Math.max(1, ((int) (iconWidth * scale)));
        int high = java.lang.Math.max(1, ((int) (iconHeight * scale)));
        int margin = ((int) (scale));
        g.fillOval(x + margin, y, wide, high);
        if ((wide > 1) && (high > 1)) {
            g.setColor(java.awt.Color.black);
            g.drawOval(x + margin, y, wide, high);
        }
    }

    /**
     * Draws the protection icon like a letter
     *
     * @param g
     * 		Description of Parameter
     * @param x
     * 		Description of Parameter
     * @param y
     * 		Description of Parameter
     */
    private void drawLetterIcon(java.awt.Graphics g, int x, int y) {
        g.setColor(java.awt.Color.black);
        int wide = java.lang.Math.max(1, ((int) (iconWidth * scale)));
        int high = java.lang.Math.max(1, ((int) (iconHeight * scale)));
        int margin = ((int) (scale));
        int halfHigh = high / 2;
        int halfWide = wide / 2;
        if (protection == org.acm.seguin.uml.UMLLine.PUBLIC) {
            g.drawLine(x + margin, y + halfHigh, (x + margin) + wide, y + halfHigh);
            g.drawLine((x + margin) + halfWide, y, (x + margin) + halfWide, y + high);
        } else if (protection == org.acm.seguin.uml.UMLLine.PROTECTED) {
            g.drawLine(x + margin, (y + halfHigh) + 1, (x + margin) + wide, (y + halfHigh) + 1);
            g.drawLine(x + margin, (y + halfHigh) - 1, (x + margin) + wide, (y + halfHigh) - 1);
            g.drawLine(((x + margin) + halfWide) + 1, y, ((x + margin) + halfWide) + 1, y + high);
            g.drawLine(((x + margin) + halfWide) - 1, y, ((x + margin) + halfWide) - 1, y + high);
        } else if (protection == org.acm.seguin.uml.UMLLine.DEFAULT) {
            g.drawLine(x + margin, (y + halfHigh) + 1, (x + margin) + wide, (y + halfHigh) + 1);
            g.drawLine(x + margin, (y + halfHigh) - 1, (x + margin) + wide, (y + halfHigh) - 1);
        } else if (protection == org.acm.seguin.uml.UMLLine.PRIVATE) {
            g.drawLine(x + margin, y + halfHigh, (x + margin) + wide, y + halfHigh);
        }
    }
}