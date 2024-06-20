/* @(#)PolyLineFigure.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A poly line figure consists of a list of points.
 * It has an optional line decoration at the start and end.
 *
 * @see LineDecoration
 */
public class PolyLineFigure extends CH.ifa.draw.standard.AbstractFigure {
    public static final int ARROW_TIP_NONE = 0;

    public static final int ARROW_TIP_START = 1;

    public static final int ARROW_TIP_END = 2;

    public static final int ARROW_TIP_BOTH = 3;

    protected java.util.Vector fPoints;

    protected CH.ifa.draw.figures.LineDecoration fStartDecoration = null;

    protected CH.ifa.draw.figures.LineDecoration fEndDecoration = null;

    protected java.awt.Color fFrameColor = java.awt.Color.black;

    /* Serialization support. */
    private static final long serialVersionUID = -7951352179906577773L;

    private int polyLineFigureSerializedDataVersion = 1;

    public PolyLineFigure() {
        fPoints = new java.util.Vector(4);
    }

    public PolyLineFigure(int size) {
        fPoints = new java.util.Vector(size);
    }

    public PolyLineFigure(int x, int y) {
        fPoints = new java.util.Vector();
        fPoints.addElement(new java.awt.Point(x, y));
    }

    public java.awt.Rectangle displayBox() {
        java.util.Enumeration k = points();
        java.awt.Rectangle r = new java.awt.Rectangle(((java.awt.Point) (k.nextElement())));
        while (k.hasMoreElements())
            r.add(((java.awt.Point) (k.nextElement())));

        return r;
    }

    public boolean isEmpty() {
        return (size().width < 3) && (size().height < 3);
    }

    public java.util.Vector handles() {
        java.util.Vector handles = new java.util.Vector(fPoints.size());
        for (int i = 0; i < fPoints.size(); i++)
            handles.addElement(new CH.ifa.draw.figures.PolyLineHandle(this, CH.ifa.draw.figures.PolyLineFigure.locator(i), i));

        return handles;
    }

    public void basicDisplayBox(java.awt.Point origin, java.awt.Point corner) {
    }

    /**
     * Adds a node to the list of points.
     */
    public void addPoint(int x, int y) {
        fPoints.addElement(new java.awt.Point(x, y));
        changed();
    }

    public java.util.Enumeration points() {
        return fPoints.elements();
    }

    public int pointCount() {
        return fPoints.size();
    }

    protected void basicMoveBy(int dx, int dy) {
        java.util.Enumeration k = fPoints.elements();
        while (k.hasMoreElements())
            ((java.awt.Point) (k.nextElement())).translate(dx, dy);

    }

    /**
     * Changes the position of a node.
     */
    public void setPointAt(java.awt.Point p, int i) {
        willChange();
        fPoints.setElementAt(p, i);
        changed();
    }

    /**
     * Insert a node at the given point.
     */
    public void insertPointAt(java.awt.Point p, int i) {
        fPoints.insertElementAt(p, i);
        changed();
    }

    public void removePointAt(int i) {
        willChange();
        fPoints.removeElementAt(i);
        changed();
    }

    /**
     * Splits the segment at the given point if a segment was hit.
     *
     * @return the index of the segment or -1 if no segment was hit.
     */
    public int splitSegment(int x, int y) {
        int i = findSegment(x, y);
        if (i != (-1))
            insertPointAt(new java.awt.Point(x, y), i + 1);

        return i + 1;
    }

    public java.awt.Point pointAt(int i) {
        return ((java.awt.Point) (fPoints.elementAt(i)));
    }

    /**
     * Joins to segments into one if the given point hits a node
     * of the polyline.
     *
     * @return true if the two segments were joined.
     */
    public boolean joinSegments(int x, int y) {
        for (int i = 1; i < (fPoints.size() - 1); i++) {
            java.awt.Point p = pointAt(i);
            if (CH.ifa.draw.util.Geom.length(x, y, p.x, p.y) < 3) {
                removePointAt(i);
                return true;
            }
        }
        return false;
    }

    public CH.ifa.draw.framework.Connector connectorAt(int x, int y) {
        return new CH.ifa.draw.figures.PolyLineConnector(this);
    }

    /**
     * Sets the start decoration.
     */
    public void setStartDecoration(CH.ifa.draw.figures.LineDecoration l) {
        fStartDecoration = l;
    }

    /**
     * Sets the end decoration.
     */
    public void setEndDecoration(CH.ifa.draw.figures.LineDecoration l) {
        fEndDecoration = l;
    }

    public void draw(java.awt.Graphics g) {
        g.setColor(getFrameColor());
        java.awt.Point p1;
        java.awt.Point p2;
        for (int i = 0; i < (fPoints.size() - 1); i++) {
            p1 = ((java.awt.Point) (fPoints.elementAt(i)));
            p2 = ((java.awt.Point) (fPoints.elementAt(i + 1)));
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
        decorate(g);
    }

    public boolean containsPoint(int x, int y) {
        java.awt.Rectangle bounds = displayBox();
        bounds.grow(4, 4);
        if (!bounds.contains(x, y))
            return false;

        java.awt.Point p1;
        java.awt.Point p2;
        for (int i = 0; i < (fPoints.size() - 1); i++) {
            p1 = ((java.awt.Point) (fPoints.elementAt(i)));
            p2 = ((java.awt.Point) (fPoints.elementAt(i + 1)));
            if (CH.ifa.draw.util.Geom.lineContainsPoint(p1.x, p1.y, p2.x, p2.y, x, y))
                return true;

        }
        return false;
    }

    /**
     * Gets the segment of the polyline that is hit by
     * the given point.
     *
     * @return the index of the segment or -1 if no segment was hit.
     */
    public int findSegment(int x, int y) {
        java.awt.Point p1;
        java.awt.Point p2;
        for (int i = 0; i < (fPoints.size() - 1); i++) {
            p1 = ((java.awt.Point) (fPoints.elementAt(i)));
            p2 = ((java.awt.Point) (fPoints.elementAt(i + 1)));
            if (CH.ifa.draw.util.Geom.lineContainsPoint(p1.x, p1.y, p2.x, p2.y, x, y))
                return i;

        }
        return -1;
    }

    private void decorate(java.awt.Graphics g) {
        if (fStartDecoration != null) {
            java.awt.Point p1 = ((java.awt.Point) (fPoints.elementAt(0)));
            java.awt.Point p2 = ((java.awt.Point) (fPoints.elementAt(1)));
            fStartDecoration.draw(g, p1.x, p1.y, p2.x, p2.y);
        }
        if (fEndDecoration != null) {
            java.awt.Point p3 = ((java.awt.Point) (fPoints.elementAt(fPoints.size() - 2)));
            java.awt.Point p4 = ((java.awt.Point) (fPoints.elementAt(fPoints.size() - 1)));
            fEndDecoration.draw(g, p4.x, p4.y, p3.x, p3.y);
        }
    }

    /**
     * Gets the attribute with the given name.
     * PolyLineFigure maps "ArrowMode"to a
     * line decoration.
     */
    public java.lang.Object getAttribute(java.lang.String name) {
        if (name.equals("FrameColor")) {
            return getFrameColor();
        } else if (name.equals("ArrowMode")) {
            int value = 0;
            if (fStartDecoration != null)
                value |= CH.ifa.draw.figures.PolyLineFigure.ARROW_TIP_START;

            if (fEndDecoration != null)
                value |= CH.ifa.draw.figures.PolyLineFigure.ARROW_TIP_END;

            return new java.lang.Integer(value);
        }
        return super.getAttribute(name);
    }

    /**
     * Sets the attribute with the given name.
     * PolyLineFigure interprets "ArrowMode"to set
     * the line decoration.
     */
    public void setAttribute(java.lang.String name, java.lang.Object value) {
        if (name.equals("FrameColor")) {
            setFrameColor(((java.awt.Color) (value)));
            changed();
        } else if (name.equals("ArrowMode")) {
            java.lang.Integer intObj = ((java.lang.Integer) (value));
            if (intObj != null) {
                int decoration = intObj.intValue();
                if ((decoration & CH.ifa.draw.figures.PolyLineFigure.ARROW_TIP_START) != 0)
                    fStartDecoration = new CH.ifa.draw.figures.ArrowTip();
                else
                    fStartDecoration = null;

                if ((decoration & CH.ifa.draw.figures.PolyLineFigure.ARROW_TIP_END) != 0)
                    fEndDecoration = new CH.ifa.draw.figures.ArrowTip();
                else
                    fEndDecoration = null;

            }
            changed();
        } else
            super.setAttribute(name, value);

    }

    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fPoints.size());
        java.util.Enumeration k = fPoints.elements();
        while (k.hasMoreElements()) {
            java.awt.Point p = ((java.awt.Point) (k.nextElement()));
            dw.writeInt(p.x);
            dw.writeInt(p.y);
        } 
        dw.writeStorable(fStartDecoration);
        dw.writeStorable(fEndDecoration);
        dw.writeColor(fFrameColor);
    }

    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        int size = dr.readInt();
        fPoints = new java.util.Vector(size);
        for (int i = 0; i < size; i++) {
            int x = dr.readInt();
            int y = dr.readInt();
            fPoints.addElement(new java.awt.Point(x, y));
        }
        fStartDecoration = ((CH.ifa.draw.figures.LineDecoration) (dr.readStorable()));
        fEndDecoration = ((CH.ifa.draw.figures.LineDecoration) (dr.readStorable()));
        fFrameColor = dr.readColor();
    }

    /**
     * Creates a locator for the point with the given index.
     */
    public static CH.ifa.draw.framework.Locator locator(int pointIndex) {
        return new CH.ifa.draw.figures.PolyLineLocator(pointIndex);
    }

    protected java.awt.Color getFrameColor() {
        return fFrameColor;
    }

    protected void setFrameColor(java.awt.Color c) {
        fFrameColor = c;
    }
}