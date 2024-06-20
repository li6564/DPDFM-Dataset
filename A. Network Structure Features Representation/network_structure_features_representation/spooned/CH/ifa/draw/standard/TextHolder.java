/* @(#)TextHolder.java 5.1 */
package CH.ifa.draw.standard;
/**
 * The interface of a figure that has some editable text contents.
 *
 * @see Figure
 */
public interface TextHolder {
    public java.awt.Rectangle textDisplayBox();

    /**
     * Gets the text shown by the text figure.
     */
    public java.lang.String getText();

    /**
     * Sets the text shown by the text figure.
     */
    public void setText(java.lang.String newText);

    /**
     * Tests whether the figure accepts typing.
     */
    public boolean acceptsTyping();

    /**
     * Gets the number of columns to be overlaid when the figure is edited.
     */
    public int overlayColumns();

    /**
     * Connects a figure to another figure.
     */
    public void connect(CH.ifa.draw.framework.Figure connectedFigure);

    /**
     * Gets the font.
     */
    public java.awt.Font getFont();
}