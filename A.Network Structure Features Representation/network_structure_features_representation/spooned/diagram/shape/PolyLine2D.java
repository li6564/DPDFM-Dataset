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
package diagram.shape;
import java.awt.geom.Point2D;
/**
 *
 * @class PolyLine2D
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0

This class implements Shape, and
consists of a series of straight-line segments.

It allows segements to be added and split quickly, and
is very efficent, minimizing the number of new objects
created for most frequently used operations.

Segment distance methods should be updated as well as
the relativeCCW methods for a complete PolyLine2D implementation
 */
public abstract class PolyLine2D extends java.awt.geom.Line2D implements java.io.Serializable {
    protected int pointCount = 2;

    /**
     * Get an arbitrary point in this line
     */
    public abstract java.awt.geom.Point2D getPN(int index);

    /**
     * Get the n'th Point2D in the line
     */
    public abstract java.awt.geom.Point2D getPN(int index, java.awt.geom.Point2D pt);

    /**
     * Set an arbitrary point in this line
     */
    public void setPN(int index, java.awt.geom.Point2D pt) {
        setPN(index, pt.getX(), pt.getY());
    }

    /**
     * Set an arbitrary point in this line
     */
    public abstract void setPN(int index, double x, double y);

    /**
     * Get the x-value of the n'th point
     */
    public abstract double getXN(int index);

    /**
     * Get the y-value of the n'th point
     */
    public abstract double getYN(int index);

    /**
     * Count the Points in this line
     */
    public int getPointCount() {
        return pointCount;
    }

    /**
     * Count the segments in this Line
     */
    public int getSegmentCount() {
        return getPointCount() - 1;
    }

    /**
     * Get the segment closest to the given point
     */
    public int segmentFor(java.awt.geom.Point2D pt, double tolerance) {
        return segmentFor(pt.getX(), pt.getY(), tolerance);
    }

    /**
     * Get the segment closest to the given point
     */
    public abstract int segmentFor(double x, double y, double tolerance);

    /**
     * Get the closest point in the line to another point
     */
    public abstract int pointFor(double x, double y, double tolerance);

    /**
     * Get the boudns for this rectangle
     */
    public abstract java.awt.geom.Rectangle2D getBounds2D(java.awt.geom.Rectangle2D rc);

    /**
     * Get the boudns for this rectangle
     */
    public java.awt.geom.Rectangle2D getBounds2D() {
        return getBounds2D(null);
    }

    /**
     * See if this Point falls on or close to the line
     */
    public boolean contains(java.awt.geom.Point2D pt) {
        return contains(pt.getX(), pt.getY());
    }

    /**
     * See if this Point falls on or close to the line
     */
    public boolean contains(double x, double y) {
        return contains(x, y, 3.0);
    }

    /**
     * See if this Point falls on or close to the line
     */
    public boolean contains(double x, double y, double tolerance) {
        return segmentFor(x, y, tolerance) != (-1);
    }

    /**
     * Get the center of the bounding rectangle for this line.
     *
     * @param Point2D
     * @return Point2D
     */
    public java.awt.geom.Point2D getCenter(java.awt.geom.Point2D pt) {
        double x1 = getX1();
        double x2 = getX2();
        double y1 = getY1();
        double y2 = getY2();
        double x = (x1 > x2) ? (x1 - x2) / 2.0 : (x2 - x1) / 2.0;
        double y = (y1 > y2) ? (y1 - y2) / 2.0 : (y2 - y1) / 2.0;
        if (pt == null)
            return new java.awt.geom.Point2D.Double(x, y);

        pt.setLocation(x, y);
        return pt;
    }

    /**
     * See if this line intersects the rectangle.
     */
    public boolean intersects(double x, double y, double w, double h) {
        // Implementation should NOT start by creating a new rectangle
        throw new java.lang.UnsupportedOperationException();
    }

    /**
     * Translate the entire line
     */
    public abstract void translate(double x, double y);

    /**
     * Split an existing segment into two bu inserting a point
     */
    public abstract java.awt.geom.Point2D split(int segment, double x, double y);

    /**
     * Split an existing segment into two bu inserting a point
     */
    public java.awt.geom.Point2D split(int segment, java.awt.geom.Point2D pt) {
        return split(segment, pt.getX(), pt.getY());
    }

    /**
     * Join together the two segments joined by a single point in the line
     */
    public abstract void join(int point);

    /**
     */
    public java.awt.geom.PathIterator getPathIterator(java.awt.geom.AffineTransform at, double flatness) {
        return getPathIterator(at);
    }

    /**
     */
    public java.awt.geom.PathIterator getPathIterator(java.awt.geom.AffineTransform at) {
        return new diagram.shape.PolyLine2D.Iterator(at);
    }

    /**
     *
     * @class Double
     */
    public static class Double extends diagram.shape.PolyLine2D implements java.io.Serializable {
        protected java.awt.geom.Point2D.Double[] points;

        public Double() {
            points = new java.awt.geom.Point2D.Double[]{ new java.awt.geom.Point2D.Double(0, 0), new java.awt.geom.Point2D.Double(0, 0) };
        }

        public Double(java.awt.geom.Point2D p1, java.awt.geom.Point2D p2) {
            points[0].setLocation(p1);
            points[1].setLocation(p2);
        }

        /**
         * Set an arbitrary point in this line
         */
        public void setPN(int index, double x, double y) {
            if (index >= pointCount)
                throw new java.util.NoSuchElementException("index out of bounds");

            points[index].x = x;
            points[index].y = y;
        }

        /**
         * Get an arbitrary point in this line
         */
        public java.awt.geom.Point2D getPN(int index) {
            if (index >= pointCount)
                throw new java.util.NoSuchElementException("index out of bounds");

            return points[index];
        }

        /**
         * Get an arbitrary point in this line
         */
        public java.awt.geom.Point2D getPN(int index, java.awt.geom.Point2D pt) {
            if (index >= pointCount)
                throw new java.util.NoSuchElementException("index out of bounds");

            if (pt == null)
                return new java.awt.geom.Point2D.Double(points[index].x, points[index].y);

            pt.setLocation(points[index]);
            return pt;
        }

        public java.awt.geom.Point2D getP1() {
            return points[0];
        }

        public java.awt.geom.Point2D getP2() {
            return points[pointCount - 1];
        }

        public double getXN(int index) {
            if (index >= pointCount)
                throw new java.util.NoSuchElementException("index out of bounds");

            return points[index].x;
        }

        public double getYN(int index) {
            if (index >= pointCount)
                throw new java.util.NoSuchElementException("index out of bounds");

            return points[index].y;
        }

        public double getX1() {
            return points[0].x;
        }

        public double getY1() {
            return points[0].y;
        }

        public double getX2() {
            return points[pointCount - 1].x;
        }

        public double getY2() {
            return points[pointCount - 1].y;
        }

        public java.awt.geom.Rectangle2D getBounds2D(java.awt.geom.Rectangle2D rc) {
            double x1 = java.lang.Double.MAX_VALUE;
            double y1 = java.lang.Double.MAX_VALUE;
            double x2 = 0;
            double y2 = 0;
            for (int i = 0; i < pointCount; i++) {
                double x = points[i].x;
                double y = points[i].y;
                if (x < x1)
                    x1 = x;

                if (x > x2)
                    x2 = x;

                if (y < y1)
                    y1 = y;

                if (y > y2)
                    y2 = y;

            }
            if (((x2 - x1) < 0) || ((y2 - y1) < 0))
                throw new java.lang.RuntimeException(((((((("Bad Bounds2D [" + x1) + ", ") + y1) + ", ") + x2) + ", ") + y2) + "]");

            if (rc == null)
                return new java.awt.geom.Rectangle2D.Double(x1, y1, (x2 - x1) + 1, (y2 - y1) + 1);

            rc.setRect(x1, y1, (x2 - x1) + 1, (y2 - y1) + 1);
            return rc;
        }

        /**
         * Get the segment closest to the given point
         */
        public int segmentFor(double x, double y, double tolerance) {
            double x1 = points[0].x;
            double y1 = points[0].y;
            double x2;
            double y2;
            int s = -1;
            for (int i = 1; i < getPointCount(); i++) {
                x2 = points[i].x;
                y2 = points[i].y;
                // Get the smallest distance to this point.
                double dist;
                if ((dist = java.awt.geom.Line2D.ptSegDist(x1, y1, x2, y2, x, y)) < tolerance) {
                    tolerance = dist;
                    s = i - 1;
                }
                x1 = x2;
                y1 = y2;
            }
            // Return the closest segment
            return s;
        }

        /**
         * Get the closest point in the line to another point
         */
        public int pointFor(double x, double y, double tolerance) {
            int p = -1;
            double dist = tolerance;
            // Find the closest point
            for (int i = 0; i < getPointCount(); i++) {
                dist = java.awt.geom.Point2D.distanceSq(x, y, points[i].x, points[i].y);
                if (dist < tolerance) {
                    tolerance = dist;
                    p = i;
                }
            }
            return p;
        }

        /**
         * Reset the line, removing all intermediate segments
         */
        public void setLine(double X1, double Y1, double X2, double Y2) {
            points[0].setLocation(X1, Y1);
            points[1].setLocation(X2, Y2);
            pointCount = 2;
        }

        /**
         * Translate the entire line
         */
        public void translate(double x, double y) {
            for (int i = 0; i < pointCount; i++) {
                points[i].x += x;
                points[i].y += y;
            }
        }

        /**
         * Split an existing segment into two
         */
        public java.awt.geom.Point2D split(int segment, double x, double y) {
            if ((segment++) >= (pointCount - 1))
                throw new java.util.NoSuchElementException("segment index out of bounds");

            // expand the point array
            if (pointCount == points.length) {
                java.awt.geom.Point2D.Double[] temp = new java.awt.geom.Point2D.Double[pointCount * 2];
                java.lang.System.arraycopy(points, 0, temp, 0, pointCount);
                points = temp;
            }
            // Shift to the right
            java.lang.System.arraycopy(points, segment, points, segment + 1, pointCount - segment);
            points[segment] = new java.awt.geom.Point2D.Double(x, y);
            pointCount++;
            return points[segment];
        }

        /**
         * Join together the two segments joined by a single point in the line
         */
        public void join(int point) {
            if ((point > (pointCount - 2)) || (point < 1))
                throw new java.util.NoSuchElementException("point index out of bounds");

            // Shift left
            java.lang.System.arraycopy(points, point + 1, points, point, (pointCount - point) - 1);
            pointCount--;
        }

        /**
         * See if this line intersects the rectangle
         */
        public boolean intersects(java.awt.geom.Rectangle2D rc) {
            for (int i = 0; i < (pointCount - 1); i++) {
                if (rc.intersectsLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y))
                    return true;

            }
            return false;
        }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            out.writeInt(pointCount);
            for (int i = 0; i < pointCount; i++) {
                out.writeDouble(points[i].x);
                out.writeDouble(points[i].y);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
            pointCount = in.readInt();
            points = new java.awt.geom.Point2D.Double[pointCount];
            for (int i = 0; i < pointCount; i++)
                points[i] = new java.awt.geom.Point2D.Double(in.readDouble(), in.readDouble());

        }

        /**
         * Implement a deep copy, rather than the default shallow copy
         */
        public java.lang.Object clone() {
            // Clone all the internal points
            diagram.shape.PolyLine2D.Double link = ((diagram.shape.PolyLine2D.Double) (super.clone()));
            link.points = new java.awt.geom.Point2D.Double[pointCount];
            for (int i = 0; i < pointCount; i++)
                link.points[i] = new java.awt.geom.Point2D.Double(points[i].x, points[i].y);

            return link;
        }
    }

    /**
     *
     * @class Iterator
     */
    protected class Iterator implements java.awt.geom.PathIterator {
        private java.awt.geom.AffineTransform affine;

        private int index;

        public Iterator(java.awt.geom.AffineTransform at) {
            this.affine = at;
            this.index = 0;
        }

        public int currentSegment(double[] coords) {
            coords[0] = getXN(index);
            coords[1] = getYN(index);
            if (affine != null)
                affine.transform(coords, 0, coords, 0, 1);

            return index == 0 ? java.awt.geom.PathIterator.SEG_MOVETO : java.awt.geom.PathIterator.SEG_LINETO;
        }

        public int currentSegment(float[] coords) {
            coords[0] = ((float) (getXN(index)));
            coords[1] = ((float) (getYN(index)));
            if (affine != null)
                affine.transform(coords, 0, coords, 0, 1);

            return index == 0 ? java.awt.geom.PathIterator.SEG_MOVETO : java.awt.geom.PathIterator.SEG_LINETO;
        }

        public int getWindingRule() {
            return java.awt.geom.PathIterator.WIND_NON_ZERO;
        }

        public boolean isDone() {
            return index >= getPointCount();
        }

        public void next() {
            index++;
        }
    }

    public java.lang.String toString() {
        return (((getClass() + "[") + getBounds()) + "]@") + hashCode();
    }
}