/* @(#)TextFigure.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A text figure.
 *
 * @see TextTool
 */
public class TextFigure extends CH.ifa.draw.figures.AttributeFigure implements CH.ifa.draw.framework.FigureChangeListener , CH.ifa.draw.standard.TextHolder {
    private int fOriginX;

    private int fOriginY;

    // cache of the TextFigure's size
    private transient boolean fSizeIsDirty = true;

    private transient int fWidth;

    private transient int fHeight;

    private java.lang.String fText;

    private java.awt.Font fFont;

    private boolean fIsReadOnly;

    private CH.ifa.draw.framework.Figure fObservedFigure = null;

    private CH.ifa.draw.standard.OffsetLocator fLocator = null;

    private static java.lang.String fgCurrentFontName = "Helvetica";

    private static int fgCurrentFontSize = 12;

    private static int fgCurrentFontStyle = java.awt.Font.PLAIN;

    /* Serialization support. */
    private static final long serialVersionUID = 4599820785949456124L;

    private int textFigureSerializedDataVersion = 1;

    public TextFigure() {
        fOriginX = 0;
        fOriginY = 0;
        fFont = CH.ifa.draw.figures.TextFigure.createCurrentFont();
        setAttribute("FillColor", CH.ifa.draw.util.ColorMap.color("None"));
        fText = new java.lang.String("");
        fSizeIsDirty = true;
    }

    public void moveBy(int x, int y) {
        willChange();
        basicMoveBy(x, y);
        if (fLocator != null)
            fLocator.moveBy(x, y);

        changed();
    }

    protected void basicMoveBy(int x, int y) {
        fOriginX += x;
        fOriginY += y;
    }

    public void basicDisplayBox(java.awt.Point newOrigin, java.awt.Point newCorner) {
        fOriginX = newOrigin.x;
        fOriginY = newOrigin.y;
    }

    public java.awt.Rectangle displayBox() {
        java.awt.Dimension extent = textExtent();
        return new java.awt.Rectangle(fOriginX, fOriginY, extent.width, extent.height);
    }

    public java.awt.Rectangle textDisplayBox() {
        return displayBox();
    }

    /**
     * Tests whether this figure is read only.
     */
    public boolean readOnly() {
        return fIsReadOnly;
    }

    /**
     * Sets the read only status of the text figure.
     */
    public void setReadOnly(boolean isReadOnly) {
        fIsReadOnly = isReadOnly;
    }

    /**
     * Gets the font.
     */
    public java.awt.Font getFont() {
        return fFont;
    }

    /**
     * Sets the font.
     */
    public void setFont(java.awt.Font newFont) {
        willChange();
        fFont = newFont;
        markDirty();
        changed();
    }

    /**
     * Updates the location whenever the figure changes itself.
     */
    public void changed() {
        super.changed();
        updateLocation();
    }

    /**
     * A text figure understands the "FontSize", "FontStyle", and "FontName"
     * attributes.
     */
    public java.lang.Object getAttribute(java.lang.String name) {
        java.awt.Font font = getFont();
        if (name.equals("FontSize"))
            return new java.lang.Integer(font.getSize());

        if (name.equals("FontStyle"))
            return new java.lang.Integer(font.getStyle());

        if (name.equals("FontName"))
            return font.getName();

        return super.getAttribute(name);
    }

    /**
     * A text figure understands the "FontSize", "FontStyle", and "FontName"
     * attributes.
     */
    public void setAttribute(java.lang.String name, java.lang.Object value) {
        java.awt.Font font = getFont();
        if (name.equals("FontSize")) {
            java.lang.Integer s = ((java.lang.Integer) (value));
            setFont(new java.awt.Font(font.getName(), font.getStyle(), s.intValue()));
        } else if (name.equals("FontStyle")) {
            java.lang.Integer s = ((java.lang.Integer) (value));
            int style = font.getStyle();
            if (s.intValue() == java.awt.Font.PLAIN)
                style = java.awt.Font.PLAIN;
            else
                style = style ^ s.intValue();

            setFont(new java.awt.Font(font.getName(), style, font.getSize()));
        } else if (name.equals("FontName")) {
            java.lang.String n = ((java.lang.String) (value));
            setFont(new java.awt.Font(n, font.getStyle(), font.getSize()));
        } else
            super.setAttribute(name, value);

    }

    /**
     * Gets the text shown by the text figure.
     */
    public java.lang.String getText() {
        return fText;
    }

    /**
     * Sets the text shown by the text figure.
     */
    public void setText(java.lang.String newText) {
        if (!newText.equals(fText)) {
            willChange();
            fText = new java.lang.String(newText);
            markDirty();
            changed();
        }
    }

    /**
     * Tests whether the figure accepts typing.
     */
    public boolean acceptsTyping() {
        return !fIsReadOnly;
    }

    public void drawBackground(java.awt.Graphics g) {
        java.awt.Rectangle r = displayBox();
        g.fillRect(r.x, r.y, r.width, r.height);
    }

    public void drawFrame(java.awt.Graphics g) {
        g.setFont(fFont);
        g.setColor(((java.awt.Color) (getAttribute("TextColor"))));
        java.awt.FontMetrics metrics = g.getFontMetrics(fFont);
        g.drawString(fText, fOriginX, fOriginY + metrics.getAscent());
    }

    private java.awt.Dimension textExtent() {
        if (!fSizeIsDirty)
            return new java.awt.Dimension(fWidth, fHeight);

        java.awt.FontMetrics metrics = java.awt.Toolkit.getDefaultToolkit().getFontMetrics(fFont);
        fWidth = metrics.stringWidth(fText);
        fHeight = metrics.getHeight();
        fSizeIsDirty = false;
        return new java.awt.Dimension(metrics.stringWidth(fText), metrics.getHeight());
    }

    private void markDirty() {
        fSizeIsDirty = true;
    }

    /**
     * Gets the number of columns to be overlaid when the figure is edited.
     */
    public int overlayColumns() {
        int length = getText().length();
        int columns = 20;
        if (length != 0)
            columns = getText().length() + 3;

        return columns;
    }

    public java.util.Vector handles() {
        java.util.Vector handles = new java.util.Vector();
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.northWest()));
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.northEast()));
        handles.addElement(new CH.ifa.draw.standard.NullHandle(this, CH.ifa.draw.standard.RelativeLocator.southEast()));
        handles.addElement(new CH.ifa.draw.figures.FontSizeHandle(this, CH.ifa.draw.standard.RelativeLocator.southWest()));
        return handles;
    }

    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeInt(fOriginX);
        dw.writeInt(fOriginY);
        dw.writeString(fText);
        dw.writeString(fFont.getName());
        dw.writeInt(fFont.getStyle());
        dw.writeInt(fFont.getSize());
        dw.writeBoolean(fIsReadOnly);
        dw.writeStorable(fObservedFigure);
        dw.writeStorable(fLocator);
    }

    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        markDirty();
        fOriginX = dr.readInt();
        fOriginY = dr.readInt();
        fText = dr.readString();
        fFont = new java.awt.Font(dr.readString(), dr.readInt(), dr.readInt());
        fIsReadOnly = dr.readBoolean();
        fObservedFigure = ((CH.ifa.draw.framework.Figure) (dr.readStorable()));
        if (fObservedFigure != null)
            fObservedFigure.addFigureChangeListener(this);

        fLocator = ((CH.ifa.draw.standard.OffsetLocator) (dr.readStorable()));
    }

    private void readObject(java.io.ObjectInputStream s) throws java.lang.ClassNotFoundException, java.io.IOException {
        s.defaultReadObject();
        if (fObservedFigure != null)
            fObservedFigure.addFigureChangeListener(this);

        markDirty();
    }

    public void connect(CH.ifa.draw.framework.Figure figure) {
        if (fObservedFigure != null)
            fObservedFigure.removeFigureChangeListener(this);

        fObservedFigure = figure;
        fLocator = new CH.ifa.draw.standard.OffsetLocator(figure.connectedTextLocator(this));
        fObservedFigure.addFigureChangeListener(this);
        updateLocation();
    }

    public void figureChanged(CH.ifa.draw.framework.FigureChangeEvent e) {
        updateLocation();
    }

    public void figureRemoved(CH.ifa.draw.framework.FigureChangeEvent e) {
        if (listener() != null)
            listener().figureRequestRemove(new CH.ifa.draw.framework.FigureChangeEvent(this));

    }

    public void figureRequestRemove(CH.ifa.draw.framework.FigureChangeEvent e) {
    }

    public void figureInvalidated(CH.ifa.draw.framework.FigureChangeEvent e) {
    }

    public void figureRequestUpdate(CH.ifa.draw.framework.FigureChangeEvent e) {
    }

    /**
     * Updates the location relative to the connected figure.
     * The TextFigure is centered around the located point.
     */
    protected void updateLocation() {
        if (fLocator != null) {
            java.awt.Point p = fLocator.locate(fObservedFigure);
            p.x -= (size().width / 2) + fOriginX;
            p.y -= (size().height / 2) + fOriginY;
            if ((p.x != 0) || (p.y != 0)) {
                willChange();
                basicMoveBy(p.x, p.y);
                changed();
            }
        }
    }

    public void release() {
        super.release();
        if (fObservedFigure != null)
            fObservedFigure.removeFigureChangeListener(this);

    }

    /**
     * Disconnects the text figure.
     */
    public void disconnect() {
        fObservedFigure.removeFigureChangeListener(this);
        fObservedFigure = null;
        fLocator = null;
    }

    /**
     * Creates the current font to be used for new text figures.
     */
    public static java.awt.Font createCurrentFont() {
        return new java.awt.Font(CH.ifa.draw.figures.TextFigure.fgCurrentFontName, CH.ifa.draw.figures.TextFigure.fgCurrentFontStyle, CH.ifa.draw.figures.TextFigure.fgCurrentFontSize);
    }

    /**
     * Sets the current font name
     */
    public static void setCurrentFontName(java.lang.String name) {
        CH.ifa.draw.figures.TextFigure.fgCurrentFontName = name;
    }

    /**
     * Sets the current font size.
     */
    public static void setCurrentFontSize(int size) {
        CH.ifa.draw.figures.TextFigure.fgCurrentFontSize = size;
    }

    /**
     * Sets the current font style.
     */
    public static void setCurrentFontStyle(int style) {
        CH.ifa.draw.figures.TextFigure.fgCurrentFontStyle = style;
    }
}