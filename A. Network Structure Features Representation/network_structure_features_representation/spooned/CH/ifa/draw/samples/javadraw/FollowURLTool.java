/* @(#)FollowURLTool.java 5.1 */
package CH.ifa.draw.samples.javadraw;
class FollowURLTool extends CH.ifa.draw.standard.AbstractTool {
    private java.applet.Applet fApplet;

    FollowURLTool(CH.ifa.draw.framework.DrawingView view, java.applet.Applet applet) {
        super(view);
        fApplet = applet;
    }

    /**
     * Handles mouse move events in the drawing view.
     */
    public void mouseMove(java.awt.event.MouseEvent e, int x, int y) {
        java.lang.String urlstring = null;
        CH.ifa.draw.framework.Figure figure = drawing().findFigureInside(x, y);
        if (figure != null)
            urlstring = ((java.lang.String) (figure.getAttribute("URL")));

        if (urlstring != null)
            fApplet.showStatus(urlstring);
        else
            fApplet.showStatus("");

    }

    /**
     * Handles mouse up in the drawing view.
     */
    public void mouseUp(java.awt.event.MouseEvent e, int x, int y) {
        CH.ifa.draw.framework.Figure figure = drawing().findFigureInside(x, y);
        if (figure == null)
            return;

        java.lang.String urlstring = ((java.lang.String) (figure.getAttribute("URL")));
        if (urlstring == null)
            return;

        try {
            java.net.URL url = new java.net.URL(fApplet.getDocumentBase(), urlstring);
            fApplet.getAppletContext().showDocument(url);
        } catch (java.net.MalformedURLException exception) {
            fApplet.showStatus(exception.toString());
        }
    }
}