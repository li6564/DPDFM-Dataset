/* @(#)DrawingEditor.java 5.1 */
package CH.ifa.draw.framework;
/**
 * DrawingEditor defines the interface for coordinating
 * the different objects that participate in a drawing editor.
 *
 * <hr>
 * <b>Design Patterns</b><P>
 * <img src="images/red-ball-small.gif" width=6 height=6 alt=" o ">
 * <b><a href=../pattlets/sld022.htm>Mediator</a></b><br>
 * DrawingEditor is the mediator. It decouples the participants
 * of a drawing editor.
 *
 * @see Tool
 * @see DrawingView
 * @see Drawing
 */
public interface DrawingEditor {
    /**
     * Gets the editor's drawing view.
     */
    CH.ifa.draw.framework.DrawingView view();

    /**
     * Gets the editor's drawing.
     */
    CH.ifa.draw.framework.Drawing drawing();

    /**
     * Gets the editor's current tool.
     */
    CH.ifa.draw.framework.Tool tool();

    /**
     * Informs the editor that a tool has done its interaction.
     * This method can be used to switch back to the default tool.
     */
    void toolDone();

    /**
     * Informs that the current selection has changed.
     * Override this method to handle selection changes.
     */
    void selectionChanged(CH.ifa.draw.framework.DrawingView view);

    /**
     * Shows a status message in the editor's user interface
     */
    void showStatus(java.lang.String string);
}