/* @(#)DecoratorFigure.java 5.1 */
package CH.ifa.draw.standard;
/**
 * DecoratorFigure can be used to decorate other figures with
 * decorations like borders. Decorator forwards all the
 * methods to their contained figure. Subclasses can selectively
 * override these methods to extend and filter their behavior.
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b><a href=../pattlets/sld014.htm>Decorator</a></b><br>
 * DecoratorFigure is a decorator.
 *
 * @see Figure
 */
public abstract class DecoratorFigure extends CH.ifa.draw.standard.AbstractFigure implements CH.ifa.draw.framework.FigureChangeListener {
    /**
     * The decorated figure.
     */
    protected CH.ifa.draw.framework.Figure fComponent;

    /* Serialization support. */
    private static final long serialVersionUID = 8993011151564573288L;

    private int decoratorFigureSerializedDataVersion = 1;

    public DecoratorFigure() {
    }

    /**
     * Constructs a DecoratorFigure and decorates the passed in figure.
     */
    public DecoratorFigure(CH.ifa.draw.framework.Figure figure) {
        decorate(figure);
    }

    /**
     * Forwards the connection insets to its contained figure..
     */
    public java.awt.Insets connectionInsets() {
        return fComponent.connectionInsets();
    }

    /**
     * Forwards the canConnect to its contained figure..
     */
    public boolean canConnect() {
        return fComponent.canConnect();
    }

    /**
     * Forwards containsPoint to its contained figure.
     */
    public boolean containsPoint(int x, int y) {
        return fComponent.containsPoint(x, y);
    }

    /**
     * Decorates the given figure.
     */
    public void decorate(CH.ifa.draw.framework.Figure figure) {
        fComponent = figure;
        fComponent.addToContainer(this);
    }

    /**
     * Removes the decoration from the contained figure.
     */
    public CH.ifa.draw.framework.Figure peelDecoration() {
        fComponent.removeFromContainer(this);// ??? set the container to the listener()?

        return fComponent;
    }

    /**
     * Forwards displayBox to its contained figure.
     */
    public java.awt.Rectangle displayBox() {
        return fComponent.displayBox();
    }

    /**
     * Forwards basicDisplayBox to its contained figure.
     */
    public void basicDisplayBox(java.awt.Point origin, java.awt.Point corner) {
        fComponent.basicDisplayBox(origin, corner);
    }

    /**
     * Forwards draw to its contained figure.
     */
    public void draw(java.awt.Graphics g) {
        fComponent.draw(g);
    }

    /**
     * Forwards findFigureInside to its contained figure.
     */
    public CH.ifa.draw.framework.Figure findFigureInside(int x, int y) {
        return fComponent.findFigureInside(x, y);
    }

    /**
     * Forwards handles to its contained figure.
     */
    public java.util.Vector handles() {
        return fComponent.handles();
    }

    /**
     * Forwards includes to its contained figure.
     */
    public boolean includes(CH.ifa.draw.framework.Figure figure) {
        return super.includes(figure) || fComponent.includes(figure);
    }

    /**
     * Forwards moveBy to its contained figure.
     */
    public void moveBy(int x, int y) {
        fComponent.moveBy(x, y);
    }

    /**
     * Forwards basicMoveBy to its contained figure.
     */
    protected void basicMoveBy(int x, int y) {
        // this will never be called
    }

    /**
     * Releases itself and the contained figure.
     */
    public void release() {
        super.release();
        fComponent.removeFromContainer(this);
        fComponent.release();
    }

    /**
     * Propagates invalidate up the container chain.
     *
     * @see FigureChangeListener
     */
    public void figureInvalidated(CH.ifa.draw.framework.FigureChangeEvent e) {
        if (listener() != null)
            listener().figureInvalidated(e);

    }

    public void figureChanged(CH.ifa.draw.framework.FigureChangeEvent e) {
    }

    public void figureRemoved(CH.ifa.draw.framework.FigureChangeEvent e) {
    }

    /**
     * Propagates figureRequestUpdate up the container chain.
     *
     * @see FigureChangeListener
     */
    public void figureRequestUpdate(CH.ifa.draw.framework.FigureChangeEvent e) {
        if (listener() != null)
            listener().figureRequestUpdate(e);

    }

    /**
     * Propagates the removeFromDrawing request up to the container.
     *
     * @see FigureChangeListener
     */
    public void figureRequestRemove(CH.ifa.draw.framework.FigureChangeEvent e) {
        if (listener() != null)
            listener().figureRequestRemove(new CH.ifa.draw.framework.FigureChangeEvent(this));

    }

    /**
     * Forwards figures to its contained figure.
     */
    public CH.ifa.draw.framework.FigureEnumeration figures() {
        return fComponent.figures();
    }

    /**
     * Forwards decompose to its contained figure.
     */
    public CH.ifa.draw.framework.FigureEnumeration decompose() {
        return fComponent.decompose();
    }

    /**
     * Forwards setAttribute to its contained figure.
     */
    public void setAttribute(java.lang.String name, java.lang.Object value) {
        fComponent.setAttribute(name, value);
    }

    /**
     * Forwards getAttribute to its contained figure.
     */
    public java.lang.Object getAttribute(java.lang.String name) {
        return fComponent.getAttribute(name);
    }

    /**
     * Returns the locator used to located connected text.
     */
    public CH.ifa.draw.framework.Locator connectedTextLocator(CH.ifa.draw.framework.Figure text) {
        return fComponent.connectedTextLocator(text);
    }

    /**
     * Returns the Connector for the given location.
     */
    public CH.ifa.draw.framework.Connector connectorAt(int x, int y) {
        return fComponent.connectorAt(x, y);
    }

    /**
     * Forwards the connector visibility request to its component.
     */
    public void connectorVisibility(boolean isVisible) {
        fComponent.connectorVisibility(isVisible);
    }

    /**
     * Writes itself and the contained figure to the StorableOutput.
     */
    public void write(CH.ifa.draw.util.StorableOutput dw) {
        super.write(dw);
        dw.writeStorable(fComponent);
    }

    /**
     * Reads itself and the contained figure from the StorableInput.
     */
    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        super.read(dr);
        decorate(((CH.ifa.draw.framework.Figure) (dr.readStorable())));
    }

    private void readObject(java.io.ObjectInputStream s) throws java.lang.ClassNotFoundException, java.io.IOException {
        s.defaultReadObject();
        fComponent.addToContainer(this);
    }
}