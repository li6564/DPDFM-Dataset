/* @(#)AbstractTool.java 5.1 */
package CH.ifa.draw.standard;
/**
 * Default implementation support for Tools.
 *
 * @see DrawingView
 * @see Tool
 */
public class AbstractTool implements CH.ifa.draw.framework.Tool {
    protected CH.ifa.draw.framework.DrawingView fView;

    protected int fAnchorX;

    /**
     * The position of the initial mouse down.
     */
    protected int fAnchorY;

    /**
     * Constructs a tool for the given view.
     */
    public AbstractTool(CH.ifa.draw.framework.DrawingView itsView) {
        fView = itsView;
    }

    /**
     * Activates the tool for the given view. This method is called
     * whenever the user switches to this tool. Use this method to
     * reinitialize a tool.
     */
    public void activate() {
        fView.clearSelection();
    }

    /**
     * Deactivates the tool. This method is called whenever the user
     * switches to another tool. Use this method to do some clean-up
     * when the tool is switched. Subclassers should always call
     * super.deactivate.
     */
    public void deactivate() {
        fView.setCursor(java.awt.Cursor.getDefaultCursor());
    }

    /**
     * Handles mouse down events in the drawing view.
     */
    public void mouseDown(java.awt.event.MouseEvent e, int x, int y) {
        fAnchorX = x;
        fAnchorY = y;
    }

    /**
     * Handles mouse drag events in the drawing view.
     */
    public void mouseDrag(java.awt.event.MouseEvent e, int x, int y) {
    }

    /**
     * Handles mouse up in the drawing view.
     */
    public void mouseUp(java.awt.event.MouseEvent e, int x, int y) {
    }

    /**
     * Handles mouse moves (if the mouse button is up).
     */
    public void mouseMove(java.awt.event.MouseEvent evt, int x, int y) {
    }

    /**
     * Handles key down events in the drawing view.
     */
    public void keyDown(java.awt.event.KeyEvent evt, int key) {
    }

    /**
     * Gets the tool's drawing.
     */
    public CH.ifa.draw.framework.Drawing drawing() {
        return fView.drawing();
    }

    /**
     * Gets the tool's editor.
     */
    public CH.ifa.draw.framework.DrawingEditor editor() {
        return fView.editor();
    }

    /**
     * Gets the tool's view.
     */
    public CH.ifa.draw.framework.DrawingView view() {
        return fView;
    }
}