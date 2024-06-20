/* @(#)URLTool.java 5.1 */
package CH.ifa.draw.samples.javadraw;
/**
 * A tool to attach URLs to figures.
 * The URLs are stored in the figure's "URL" attribute.
 * The URL text is entered with a FloatingTextField.
 *
 * @see FloatingTextField
 */
public class URLTool extends CH.ifa.draw.standard.AbstractTool {
    private CH.ifa.draw.util.FloatingTextField fTextField;

    private CH.ifa.draw.framework.Figure fURLTarget;

    public URLTool(CH.ifa.draw.framework.DrawingView view) {
        super(view);
    }

    public void mouseDown(java.awt.event.MouseEvent e, int x, int y) {
        CH.ifa.draw.framework.Figure pressedFigure;
        pressedFigure = drawing().findFigureInside(x, y);
        if (pressedFigure != null) {
            beginEdit(pressedFigure);
            return;
        }
        endEdit();
    }

    public void mouseUp(java.awt.event.MouseEvent e, int x, int y) {
    }

    public void deactivate(CH.ifa.draw.framework.DrawingView view) {
        super.deactivate();
        endEdit();
    }

    public void endAction(java.awt.event.ActionEvent e) {
        endEdit();
    }

    private void beginEdit(CH.ifa.draw.framework.Figure figure) {
        if (fTextField == null) {
            fTextField = new CH.ifa.draw.util.FloatingTextField();
            fTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent event) {
                    endAction(event);
                }
            });
        }
        if ((figure != fURLTarget) && (fURLTarget != null))
            endEdit();

        if (figure != fURLTarget) {
            fTextField.createOverlay(((java.awt.Container) (view())));
            fTextField.setBounds(fieldBounds(figure), getURL(figure));
            fURLTarget = figure;
        }
    }

    private void endEdit() {
        if (fURLTarget != null) {
            setURL(fURLTarget, fTextField.getText());
            fURLTarget = null;
            fTextField.endOverlay();
        }
    }

    private java.awt.Rectangle fieldBounds(CH.ifa.draw.framework.Figure figure) {
        java.awt.Rectangle box = figure.displayBox();
        int nChars = java.lang.Math.max(20, getURL(figure).length());
        java.awt.Dimension d = fTextField.getPreferredSize(nChars);
        box.x = java.lang.Math.max(0, box.x + ((box.width - d.width) / 2));
        box.y = java.lang.Math.max(0, box.y + ((box.height - d.height) / 2));
        return new java.awt.Rectangle(box.x, box.y, d.width, d.height);
    }

    private java.lang.String getURL(CH.ifa.draw.framework.Figure figure) {
        java.lang.String url = ((java.lang.String) (figure.getAttribute("URL")));
        if (url == null)
            url = "";

        return url;
    }

    private void setURL(CH.ifa.draw.framework.Figure figure, java.lang.String url) {
        figure.setAttribute("URL", url);
    }
}