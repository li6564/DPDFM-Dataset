/* @(#)FloatingTextField.java 5.1 */
package CH.ifa.draw.util;
/**
 * A text field overlay that is used to edit a TextFigure.
 * A FloatingTextField requires a two step initialization:
 * In a first step the overlay is created and in a
 * second step it can be positioned.
 *
 * @see TextFigure
 */
public class FloatingTextField extends java.lang.Object {
    private java.awt.TextField fEditWidget;

    private java.awt.Container fContainer;

    public FloatingTextField() {
        fEditWidget = new java.awt.TextField(20);
    }

    /**
     * Creates the overlay for the given Component.
     */
    public void createOverlay(java.awt.Container container) {
        createOverlay(container, null);
    }

    /**
     * Creates the overlay for the given Container using a
     * specific font.
     */
    public void createOverlay(java.awt.Container container, java.awt.Font font) {
        container.add(fEditWidget, 0);
        if (font != null)
            fEditWidget.setFont(font);

        fContainer = container;
    }

    /**
     * Adds an action listener
     */
    public void addActionListener(java.awt.event.ActionListener listener) {
        fEditWidget.addActionListener(listener);
    }

    /**
     * Remove an action listener
     */
    public void removeActionListener(java.awt.event.ActionListener listener) {
        fEditWidget.removeActionListener(listener);
    }

    /**
     * Positions the overlay.
     */
    public void setBounds(java.awt.Rectangle r, java.lang.String text) {
        fEditWidget.setText(text);
        fEditWidget.setBounds(r.x, r.y, r.width, r.height);
        fEditWidget.setVisible(true);
        fEditWidget.selectAll();
        fEditWidget.requestFocus();
    }

    /**
     * Gets the text contents of the overlay.
     */
    public java.lang.String getText() {
        return fEditWidget.getText();
    }

    /**
     * Gets the preferred size of the overlay.
     */
    public java.awt.Dimension getPreferredSize(int cols) {
        return fEditWidget.getPreferredSize(cols);
    }

    /**
     * Removes the overlay.
     */
    public void endOverlay() {
        fContainer.requestFocus();
        if (fEditWidget == null)
            return;

        fEditWidget.setVisible(false);
        fContainer.remove(fEditWidget);
        java.awt.Rectangle bounds = fEditWidget.getBounds();
        fContainer.repaint(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}