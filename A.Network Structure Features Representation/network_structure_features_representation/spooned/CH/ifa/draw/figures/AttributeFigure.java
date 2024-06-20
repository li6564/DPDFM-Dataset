/* @(#)AttributeFigure.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A figure that can keep track of an open ended set of attributes.
 * The attributes are stored in a dictionary implemented by
 * FigureAttributes.
 *
 * @see Figure
 * @see Handle
 * @see FigureAttributes
 */
public abstract class AttributeFigure extends CH.ifa.draw.standard.AbstractFigure {
    /**
     * The attributes of a figure. Each figure can have
     * an open ended set of attributes. Attributes are
     * identified by name.
     *
     * @see #getAttribute
     * @see #setAttribute
     */
    private CH.ifa.draw.figures.FigureAttributes fAttributes;

    /**
     * The default attributes associated with a figure.
     * If a figure doesn't have an attribute set, a default
     * value from this shared attribute set is returned.
     *
     * @see #getAttribute
     * @see #setAttribute
     */
    private static CH.ifa.draw.figures.FigureAttributes fgDefaultAttributes = null;

    /* Serialization support. */
    private static final long serialVersionUID = -10857585979273442L;

    private int attributeFigureSerializedDataVersion = 1;

    protected AttributeFigure() {
    }

    /**
     * Draws the figure in the given graphics. Draw is a template
     * method calling drawBackground followed by drawFrame.
     */
    public void draw(java.awt.Graphics g) {
        java.awt.Color fill = getFillColor();
        if (!CH.ifa.draw.util.ColorMap.isTransparent(fill)) {
            g.setColor(fill);
            drawBackground(g);
        }
        java.awt.Color frame = getFrameColor();
        if (!CH.ifa.draw.util.ColorMap.isTransparent(frame)) {
            g.setColor(frame);
            drawFrame(g);
        }
    }

    /**
     * Draws the background of the figure.
     *
     * @see #draw
     */
    protected void drawBackground(java.awt.Graphics g) {
    }

    /**
     * Draws the frame of the figure.
     *
     * @see #draw
     */
    protected void drawFrame(java.awt.Graphics g) {
    }

    /**
     * Gets the fill color of a figure. This is a convenience
     * method.
     *
     * @see getAttribute
     */
    public java.awt.Color getFillColor() {
        return ((java.awt.Color) (getAttribute("FillColor")));
    }

    /**
     * Gets the frame color of a figure. This is a convenience
     * method.
     *
     * @see getAttribute
     */
    public java.awt.Color getFrameColor() {
        return ((java.awt.Color) (getAttribute("FrameColor")));
    }

    // ---- figure attributes ----------------------------------
    private static void initializeAttributes() {
        CH.ifa.draw.figures.AttributeFigure.fgDefaultAttributes = new CH.ifa.draw.figures.FigureAttributes();
        CH.ifa.draw.figures.AttributeFigure.fgDefaultAttributes.set("FrameColor", java.awt.Color.black);
        CH.ifa.draw.figures.AttributeFigure.fgDefaultAttributes.set("FillColor", new java.awt.Color(0x70db93));
        CH.ifa.draw.figures.AttributeFigure.fgDefaultAttributes.set("TextColor", java.awt.Color.black);
        CH.ifa.draw.figures.AttributeFigure.fgDefaultAttributes.set("ArrowMode", new java.lang.Integer(0));
        CH.ifa.draw.figures.AttributeFigure.fgDefaultAttributes.set("FontName", "Helvetica");
        CH.ifa.draw.figures.AttributeFigure.fgDefaultAttributes.set("FontSize", new java.lang.Integer(12));
        CH.ifa.draw.figures.AttributeFigure.fgDefaultAttributes.set("FontStyle", new java.lang.Integer(java.awt.Font.PLAIN));
    }

    /**
     * Gets a the default value for a named attribute
     *
     * @see getAttribute
     */
    public static java.lang.Object getDefaultAttribute(java.lang.String name) {
        if (CH.ifa.draw.figures.AttributeFigure.fgDefaultAttributes == null)
            CH.ifa.draw.figures.AttributeFigure.initializeAttributes();

        return CH.ifa.draw.figures.AttributeFigure.fgDefaultAttributes.get(name);
    }

    /**
     * Returns the named attribute or null if a
     * a figure doesn't have an attribute.
     * All figures support the attribute names
     * FillColor and FrameColor
     */
    public java.lang.Object getAttribute(java.lang.String name) {
        if (fAttributes != null) {
            if (fAttributes.hasDefined(name))
                return fAttributes.get(name);

        }
        return CH.ifa.draw.figures.AttributeFigure.getDefaultAttribute(name);
    }

    /**
     * Sets the named attribute to the new value
     */
    public void setAttribute(java.lang.String name, java.lang.Object value) {
        if (fAttributes == null)
            fAttributes = new CH.ifa.draw.figures.FigureAttributes();

        fAttributes.set(name, value);
        changed();
    }

    /**
     * Stores the Figure to a StorableOutput.
     */
    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        if (fAttributes == null)
            dw.writeString("no_attributes");
        else {
            dw.writeString("attributes");
            fAttributes.write(dw);
        }
    }

    /**
     * Reads the Figure from a StorableInput.
     */
    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        java.lang.String s = dr.readString();
        if (s.toLowerCase().equals("attributes")) {
            fAttributes = new CH.ifa.draw.figures.FigureAttributes();
            fAttributes.read(dr);
        }
    }
}