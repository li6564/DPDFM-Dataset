/**
 * QuickUML; A simple UML tool that demonstrates one use of the
 * Java Diagram Package
 *
 * Copyright (C) 2001  Eric Crahen <crahen@cse.buffalo.edu>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package uml.ui.border;
/**
 *
 * @class ShadowBorder
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

Draws a drop shadow border. Designed to be used as part of
a compound border in combination with a line border.

Derived from the JAS (http://www-sldnt.slac.stanford.edu/jas/)
 */
public class ShadowBorder extends javax.swing.border.AbstractBorder {
    public static final int NORTHEAST = 1;

    public static final int NORTHWEST = 2;

    public static final int SOUTHEAST = 3;

    public static final int SOUTHWEST = 4;

    private int size;

    private java.awt.Color color;

    private int orientation;

    /**
     * Creates a ShadowBorder with default properties
     */
    public ShadowBorder() {
        this(null, 0, 5);
    }

    /**
     * Creates a ShadowBorder
     *
     * @param c
     * 		The color for the shadow. If <code>null</code> the border
     * 		will be drawn using a darker version of the components background color.
     * @param orientation
     * 		One of NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST
     * @param size
     * 		The size of the shadow in pixels
     */
    public ShadowBorder(java.awt.Color c, int orientation, int size) {
        this.color = c;
        this.orientation = orientation;
        this.size = size;
    }

    public void paintBorder(java.awt.Component comp, java.awt.Graphics g, int x, int y, int width, int height) {
        g.setColor(color == null ? comp.getBackground().darker() : color);
        switch (orientation) {
            case uml.ui.border.ShadowBorder.NORTHWEST :
                g.fillRect(x, y, width - size, size);// horiz

                g.fillRect(x, y, size, height - size);// vert

            case uml.ui.border.ShadowBorder.NORTHEAST :
                g.fillRect(x + size, y, width - size, size);// horiz

                g.fillRect((x + width) - size, y, size, height - size);// vert

            case uml.ui.border.ShadowBorder.SOUTHWEST :
                g.fillRect(x, (y + height) - size, width - size, size);// horiz

                g.fillRect(x, y + size, size, height - size);// vert

            case uml.ui.border.ShadowBorder.SOUTHEAST :
            default :
                g.fillRect(x + size, (y + height) - size, width - size, size);// horiz

                g.fillRect((x + width) - size, y + size, size, height - size);// vert

        }
    }

    public java.awt.Insets getBorderInsets(java.awt.Component c) {
        return getBorderInsets(c, new java.awt.Insets(0, 0, 0, 0));
    }

    public java.awt.Insets getBorderInsets(java.awt.Component c, java.awt.Insets i) {
        switch (orientation) {
            case uml.ui.border.ShadowBorder.NORTHWEST :
                i.left = size;
                i.right = 0;
                i.top = size;
                i.bottom = 0;
                break;
            case uml.ui.border.ShadowBorder.NORTHEAST :
                i.left = 0;
                i.right = size;
                i.top = size;
                i.bottom = 0;
                break;
            case uml.ui.border.ShadowBorder.SOUTHWEST :
                i.left = size;
                i.right = 0;
                i.top = 0;
                i.bottom = size;
                break;
            case uml.ui.border.ShadowBorder.SOUTHEAST :
            default :
                i.left = 0;
                i.right = size;
                i.top = 0;
                i.bottom = size;
        }
        return i;
    }
}