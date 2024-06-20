/**
 * Java Diagram Package; An extremely flexible and fast multipurpose diagram
 * component for Swing.
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
package diagram;
import diagram.figures.PolyLink;
import java.awt.geom.Rectangle2D;
/**
 *
 * @class DefaultLabelRenderer
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This render paints a PolyLink as its label in the correct position
 */
public class LinkLabel extends javax.swing.JTextField implements diagram.FigureRenderer {
    protected java.awt.geom.Point2D p1 = null;

    protected java.awt.geom.Point2D p2 = null;

    public javax.swing.text.Document createDefaultModel() {
        return new diagram.LinkLabel.SimpleDocument();
    }

    protected void updateBounds() {
    }

    /**
     */
    public java.awt.Component getRendererComponent(Diagram diagram, Figure figure, boolean isSelected) {
        return this;
    }

    /**
     */
    public java.awt.geom.Rectangle2D getDecoratedBounds(Diagram diagram, Figure figure, java.awt.geom.Rectangle2D rcBounds) {
        java.lang.Object label = diagram.getModel().getValue(figure);
        setText(label != null ? label.toString() : "");
        if (rcBounds == null)
            rcBounds = new java.awt.geom.Rectangle2D.Double();

        return calculateLabelBounds(((PolyLink) (figure)), getText(), rcBounds);
    }

    /**
     * Find the longest segment.
     *
     * @param PolyLink
     * @return int segment to draw label on
     */
    protected int calculateLabelSegment(diagram.figures.PolyLink link) {
        double d;
        double max = 0;
        int n = 0;
        // Find the longest segment
        p2 = link.getPN(1, p2);
        p1 = link.getSource().getConnection(p2, p1);
        max = p1.distance(p2);
        for (int i = 1; i < (link.getSegmentCount() - 1); i++) {
            p1 = link.getPN(i, p1);
            p2 = link.getPN(i + 1, p2);
            if ((d = p1.distance(p2)) > max) {
                max = d;
                n = i;
            }
        }
        p2 = link.getPN(link.getPointCount() - 2, p2);
        p1 = link.getSink().getConnection(p2, p1);
        if ((d = p1.distance(p2)) > max)
            n = link.getSegmentCount() - 1;

        return n;
    }

    /**
     * Calculate where the label should be placed
     *
     * @param PolyLink
     * @param Rectangle2D
     * 		rectangle to fill with the bounds
     */
    public java.awt.geom.Rectangle2D calculateLabelBounds(diagram.figures.PolyLink link, java.lang.String text, java.awt.geom.Rectangle2D rc) {
        int seg = calculateLabelSegment(link);
        double dx = 0;// getX();

        double dy = 0;// getY();

        double x1 = link.getXN(seg) - dx;
        double x2 = link.getXN(seg + 1) - dx;
        double y1 = link.getYN(seg) - dy;
        double y2 = link.getYN(seg + 1) - dy;
        dx = (x1 > x2) ? (x1 - x2) * (-1) : x2 - x1;
        dy = (y1 > y2) ? (y1 - y2) * (-1) : y2 - y1;
        java.awt.FontMetrics metrics = getFontMetrics(getFont());
        int w = metrics.charsWidth(text.toCharArray(), 0, text.length());
        int h = metrics.getHeight();
        dx -= w;
        dy -= h;
        dx /= 2;
        dy /= 2;
        if (rc == null)
            return new java.awt.geom.Rectangle2D.Double(x1 + dx, y1 + dy, w, h);

        rc.setFrame(x1 + dx, y1 + dy, w, h);
        return rc;
    }

    /**
     * Modified document that notifies the component of the text changing.
     */
    class SimpleDocument extends javax.swing.text.PlainDocument {
        public void insertString(int offs, java.lang.String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
            super.insertString(offs, str, a);
            updateBounds();
        }

        public void remove(int offs, int len) throws javax.swing.text.BadLocationException {
            super.remove(offs, len);
            updateBounds();
        }
    }
}