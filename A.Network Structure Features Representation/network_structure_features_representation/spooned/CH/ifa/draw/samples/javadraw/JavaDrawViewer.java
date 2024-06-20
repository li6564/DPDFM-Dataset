/* @(#)JavaDrawViewer.java 5.1 */
package CH.ifa.draw.samples.javadraw;
public class JavaDrawViewer extends java.applet.Applet implements CH.ifa.draw.framework.DrawingEditor {
    private CH.ifa.draw.framework.Drawing fDrawing;

    private CH.ifa.draw.framework.Tool fTool;

    private CH.ifa.draw.standard.StandardDrawingView fView;

    private CH.ifa.draw.util.Iconkit fIconkit;

    public void init() {
        setLayout(new java.awt.BorderLayout());
        fView = new CH.ifa.draw.standard.StandardDrawingView(this, 400, 370);
        add("Center", fView);
        fTool = new CH.ifa.draw.samples.javadraw.FollowURLTool(view(), this);
        fIconkit = new CH.ifa.draw.util.Iconkit(this);
        java.lang.String filename = getParameter("Drawing");
        if (filename != null) {
            loadDrawing(filename);
            fView.setDrawing(fDrawing);
        } else
            showStatus("Unable to load drawing");

    }

    private void loadDrawing(java.lang.String filename) {
        try {
            java.net.URL url = new java.net.URL(getCodeBase(), filename);
            java.io.InputStream stream = url.openStream();
            CH.ifa.draw.util.StorableInput reader = new CH.ifa.draw.util.StorableInput(stream);
            fDrawing = ((CH.ifa.draw.framework.Drawing) (reader.readStorable()));
        } catch (java.io.IOException e) {
            fDrawing = new CH.ifa.draw.standard.StandardDrawing();
            java.lang.System.out.println("Error when Loading: " + e);
            showStatus("Error when Loading: " + e);
        }
    }

    /**
     * Gets the editor's drawing view.
     */
    public CH.ifa.draw.framework.DrawingView view() {
        return fView;
    }

    /**
     * Gets the editor's drawing.
     */
    public CH.ifa.draw.framework.Drawing drawing() {
        return fDrawing;
    }

    /**
     * Gets the current the tool (there is only one):
     */
    public CH.ifa.draw.framework.Tool tool() {
        return fTool;
    }

    /**
     * Sets the editor's default tool. Do nothing since we only have one tool.
     */
    public void toolDone() {
    }

    /**
     * Ignore selection changes, we don't show any selection
     */
    public void selectionChanged(CH.ifa.draw.framework.DrawingView view) {
    }
}