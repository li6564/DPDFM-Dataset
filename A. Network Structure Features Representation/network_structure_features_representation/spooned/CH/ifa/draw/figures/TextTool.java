/* @(#)TextTool.java 5.1 */
package CH.ifa.draw.figures;
/**
 * Tool to create new or edit existing text figures.
 * The editing behavior is implemented by overlaying the
 * Figure providing the text with a FloatingTextField.<p>
 * A tool interaction is done once a Figure that is not
 * a TextHolder is clicked.
 *
 * @see TextHolder
 * @see FloatingTextField
 */
public class TextTool extends CH.ifa.draw.standard.CreationTool {
    private CH.ifa.draw.util.FloatingTextField fTextField;

    private CH.ifa.draw.standard.TextHolder fTypingTarget;

    public TextTool(CH.ifa.draw.framework.DrawingView view, CH.ifa.draw.framework.Figure prototype) {
        super(view, prototype);
    }

    /**
     * If the pressed figure is a TextHolder it can be edited otherwise
     * a new text figure is created.
     */
    public void mouseDown(java.awt.event.MouseEvent e, int x, int y) {
        CH.ifa.draw.framework.Figure pressedFigure;
        CH.ifa.draw.standard.TextHolder textHolder = null;
        pressedFigure = drawing().findFigureInside(x, y);
        if (pressedFigure instanceof CH.ifa.draw.standard.TextHolder) {
            textHolder = ((CH.ifa.draw.standard.TextHolder) (pressedFigure));
            if (!textHolder.acceptsTyping())
                textHolder = null;

        }
        if (textHolder != null) {
            beginEdit(textHolder);
            return;
        }
        if (fTypingTarget != null) {
            editor().toolDone();
            endEdit();
        } else {
            super.mouseDown(e, x, y);
            textHolder = ((CH.ifa.draw.standard.TextHolder) (createdFigure()));
            beginEdit(textHolder);
        }
    }

    public void mouseDrag(java.awt.event.MouseEvent e, int x, int y) {
    }

    public void mouseUp(java.awt.event.MouseEvent e, int x, int y) {
    }

    /**
     * Terminates the editing of a text figure.
     */
    public void deactivate() {
        super.deactivate();
        endEdit();
    }

    /**
     * Sets the text cursor.
     */
    public void activate() {
        super.activate();
        view().clearSelection();
        // JDK1.1 TEXT_CURSOR has an incorrect hot spot
        // view.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
    }

    protected void beginEdit(CH.ifa.draw.standard.TextHolder figure) {
        if (fTextField == null)
            fTextField = new CH.ifa.draw.util.FloatingTextField();

        if ((figure != fTypingTarget) && (fTypingTarget != null))
            endEdit();

        fTextField.createOverlay(((java.awt.Container) (view())), figure.getFont());
        fTextField.setBounds(fieldBounds(figure), figure.getText());
        fTypingTarget = figure;
    }

    protected void endEdit() {
        if (fTypingTarget != null) {
            if (fTextField.getText().length() > 0)
                fTypingTarget.setText(fTextField.getText());
            else
                drawing().remove(((CH.ifa.draw.framework.Figure) (fTypingTarget)));

            fTypingTarget = null;
            fTextField.endOverlay();
            view().checkDamage();
        }
    }

    private java.awt.Rectangle fieldBounds(CH.ifa.draw.standard.TextHolder figure) {
        java.awt.Rectangle box = figure.textDisplayBox();
        int nChars = figure.overlayColumns();
        java.awt.Dimension d = fTextField.getPreferredSize(nChars);
        return new java.awt.Rectangle(box.x, box.y, d.width, d.height);
    }
}