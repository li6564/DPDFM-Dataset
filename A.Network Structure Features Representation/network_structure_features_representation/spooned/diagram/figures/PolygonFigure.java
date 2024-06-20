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
 * @class PolygonFigure
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class PolygonFigure extends java.awt.Polygon implements java.lang.Cloneable , diagram.Figure , java.io.Serializable {
    /**
     * Get the rectangular bounds of this figure.
     *
     * @param Rectangle2D,
     * 		use to avoid allocating a new object
     * @return Rectangle2D
     */
    public java.awt.geom.Rectangle2D getBounds2D(java.awt.geom.Rectangle2D rc) {
        if (rc == null)
            return new java.awt.geom.Rectangle2D.Double(java.awt.Polygon.bounds.x, java.awt.Polygon.bounds.y, java.awt.Polygon.bounds.width, java.awt.Polygon.bounds.height);

        rc.setRect(java.awt.Polygon.bounds.x, java.awt.Polygon.bounds.y, java.awt.Polygon.bounds.width, java.awt.Polygon.bounds.height);
        return rc;
    }

    /**
     * Get the center of this figure.
     *
     * @param Point2D
     * 		use to avoid allocating a new object
     * @return Point2D
     */
    public java.awt.geom.Point2D getCenter(java.awt.geom.Point2D pt) {
        double centerX = java.awt.Polygon.bounds.x + (java.awt.Polygon.bounds.width / 2);
        double centerY = java.awt.Polygon.bounds.y + (java.awt.Polygon.bounds.height / 2);
        if (pt == null)
            return new java.awt.geom.Point2D.Double(centerX, centerY);

        pt.setLocation(centerX, centerY);
        return pt;
    }

    /**
     * Translate this figure.
     *
     * @param double
     * @param double
     */
    public void translate(double x, double y) {
        super.translate(((int) (x)), ((int) (y)));
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
        // compute the intersection of the line segment passing through
        // (x0,y0) and (x1,y1) with the ray passing through
        // (xCenter, yCenter) and (px,py)
        double x0;
        double x1;
        double y0;
        double y1;
        // Get the angle
        double centerX = java.awt.Polygon.bounds.x + (java.awt.Polygon.bounds.width / 2);
        double centerY = java.awt.Polygon.bounds.y + (java.awt.Polygon.bounds.height / 2);
        double dy = ptFrom.getY() - centerY;
        double dx = ptFrom.getX() - centerX;
        double theta = java.lang.Math.atan2(dy, dx);
        double px = centerX + java.lang.Math.cos(theta);
        double py = centerY + java.lang.Math.sin(theta);
        double A = 0;
        double B = 0;
        double max = 0;
        x1 = this.xpoints[this.npoints - 1];
        y1 = this.ypoints[this.npoints - 1];
        for (int i = 0; i < this.npoints; i++) {
            x0 = x1;
            y0 = y1;
            x1 = this.xpoints[i];
            y1 = this.ypoints[i];
            double n = ((x0 - centerX) * (py - centerY)) - ((y0 - centerY) * (px - centerX));
            double m = ((y1 - y0) * (px - centerX)) - ((x1 - x0) * (py - centerY));
            double t = n / m;
            if ((0 <= t) && (t <= 1)) {
                double tx = x0 + ((x1 - x0) * t);
                double ty = y0 + ((y1 - y0) * t);
                boolean xGood = ((tx >= centerX) && (px >= centerX)) || ((tx < centerX) && (px < centerX));
                boolean yGood = ((ty >= centerY) && (py >= centerY)) || ((ty < centerY) && (py < centerY));
                if (xGood && yGood) {
                    double r = ((tx - centerX) * (tx - centerX)) + ((ty - centerY) * (ty - centerY));
                    if (r > max) {
                        A = tx;
                        B = ty;
                        max = r;
                    }
                }
            }
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
    }

    public java.lang.Object clone() {
        diagram.figures.PolygonFigure fig = new diagram.figures.PolygonFigure();
        fig.bounds = ((java.awt.Rectangle) (bounds.clone()));
        fig.npoints = npoints;
        fig.xpoints = new int[java.awt.Polygon.xpoints.length];
        fig.ypoints = new int[java.awt.Polygon.ypoints.length];
        java.lang.System.arraycopy(xpoints, 0, fig.xpoints, 0, java.awt.Polygon.xpoints.length);
        java.lang.System.arraycopy(ypoints, 0, fig.ypoints, 0, java.awt.Polygon.ypoints.length);
        return fig;
    }

    /**
     * Generate a hashcode that will not be affected by the bounds of the
     * Figure.
     */
    private static int generateLocalHash() {
        java.lang.Class c = diagram.figures.PolygonFigure.class;
        return c.hashCode() + (diagram.figures.PolygonFigure.figureId++);
    }

    private int hash = diagram.figures.PolygonFigure.generateLocalHash();

    private static int figureId = 0;

    public int hashCode() {
        return diagram.figures.PolygonFigure.figureId;
    }
}