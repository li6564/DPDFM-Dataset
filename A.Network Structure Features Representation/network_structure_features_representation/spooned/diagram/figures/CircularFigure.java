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
package diagram.figures;
import java.awt.geom.Point2D;
/**
 *
 * @class CircularFigure
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class CircularFigure extends java.awt.geom.Ellipse2D.Double implements java.lang.Cloneable , diagram.Figure , java.io.Serializable {
    public CircularFigure() {
        this(0, 0, 0, 0);
    }

    public CircularFigure(double w, double h) {
        this(0, 0, w, h);
    }

    public CircularFigure(double x, double y, double w, double h) {
        setFrame(x, y, w, h);
    }

    /**
     * Get the rectangular bounds of this figure.
     *
     * @param Rectangle2D,
     * 		use to avoid allocating a new object
     * @return Rectangle2D
     */
    public java.awt.geom.Rectangle2D getBounds2D(java.awt.geom.Rectangle2D rc) {
        if (rc == null)
            return getBounds2D();

        rc.setRect(x, y, width, height);
        return rc;
    }

    /**
     * Get the center of this figure.
     *
     * @param Point2D,
     * 		use to avoid allocating a new object
     * @return Point2D
     */
    public java.awt.geom.Point2D getCenter(java.awt.geom.Point2D pt) {
        if (pt == null)
            return new java.awt.geom.Point2D.Double(getCenterX(), getCenterY());

        pt.setLocation(getCenterX(), getCenterY());
        return pt;
    }

    /**
     * Translate this figure.
     *
     * @param double
     * @param double
     */
    public void translate(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Point on the boundary of this figure closest to some
     * point outside this figures.
     *
     * @param Point2D
     * 		point outside this Figure to connect to
     * @param Point2D
     * 		use to avoid allocating a new object
     * @return Point2D
     */
    public java.awt.geom.Point2D getConnection(java.awt.geom.Point2D ptFrom, java.awt.geom.Point2D pt) {
        if (contains(ptFrom)) {
            if (pt == null)
                pt = new java.awt.geom.Point2D.Double();

            pt.setLocation(ptFrom);
            return pt;
        }
        // Get the angle
        double centerX = getCenterX();
        double centerY = getCenterY();
        double dy = ptFrom.getY() - centerY;
        double dx = ptFrom.getX() - centerX;
        double theta = java.lang.Math.atan2(dy, dx);
        double rx = width / 2.0;
        double ry = height / 2.0;
        double A = centerX;
        double B = centerY;
        if (rx == ry) {
            // Circle
            A += rx * java.lang.Math.cos(theta);
            B += ry * java.lang.Math.sin(theta);
        } else // Ellipse
        if (java.lang.Math.abs(theta - (java.lang.Math.PI / 2.0)) == 0)
            B += ry;
        else if (java.lang.Math.abs(theta + (java.lang.Math.PI / 2.0)) == 0)
            B -= ry;
        else {
            double m = java.lang.Math.tan(theta);
            double n = (rx * ry) / java.lang.Math.sqrt((ry * ry) + (((rx * rx) * m) * m));
            if ((theta > (java.lang.Math.PI / 2.0)) || (theta < (-(java.lang.Math.PI / 2.0))))
                n = -n;

            A += n;
            B += m * n;
        }
        if (pt == null)
            return new java.awt.geom.Point2D.Double(A, B);

        pt.setLocation(A, B);
        return pt;
    }

    /**
     * Get the anchor for the Figure. This is usually the center
     * of the Figure but does not always have to be.
     *
     * @param Point2D
     * 		use to avoid allocating a new object
     * @return Point2D
     */
    public java.awt.geom.Point2D getAnchor(java.awt.geom.Point2D pt) {
        return getCenter(pt);
    }

    /**
     * Reshape this figure.
     *
     * @param double
     * @param double
     * @param double
     * @param double
     */
    public void setBounds(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    /**
     * Generate a hashcode that will not be affected by the bounds of the
     * Figure.
     */
    private static int generateLocalHash() {
        java.lang.Class c = diagram.figures.CircularFigure.class;
        return c.hashCode() + (diagram.figures.CircularFigure.figureId++);
    }

    private int hash = diagram.figures.CircularFigure.generateLocalHash();

    private static int figureId = 0;

    public int hashCode() {
        return diagram.figures.CircularFigure.figureId;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(width);
        out.writeDouble(height);
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
        x = in.readDouble();
        y = in.readDouble();
        width = in.readDouble();
        height = in.readDouble();
    }
}