package org.acm.seguin.ide.elixir;
/**
 * Handles all the diagram zooming for Elixir IDE
 *
 * @author Chris Seguin
 */
public class ZoomDiagram {
    /**
     * Scales to 10%
     */
    public static void tenPercent() {
        org.acm.seguin.ide.elixir.ZoomDiagram.work(0.1);
    }

    /**
     * Scales to 25%
     */
    public static void twentyfivePercent() {
        org.acm.seguin.ide.elixir.ZoomDiagram.work(0.25);
    }

    /**
     * Scales to 50%
     */
    public static void fiftyPercent() {
        org.acm.seguin.ide.elixir.ZoomDiagram.work(0.5);
    }

    /**
     * Scales to 100%
     */
    public static void fullSize() {
        org.acm.seguin.ide.elixir.ZoomDiagram.work(1.0);
    }

    /**
     * Gets the Manager attribute of the ZoomDiagram class
     *
     * @return The Manager value
     */
    private static org.acm.seguin.ide.elixir.ViewManager getManager() {
        org.acm.seguin.ide.elixir.FrameManager fm = org.acm.seguin.ide.elixir.FrameManager.current();
        if (fm == null) {
            return null;
        }
        if (fm.getViewSite() == null) {
            return null;
        }
        return ((org.acm.seguin.ide.elixir.ViewManager) (fm.getViewSite().getCurrentViewManager()));
    }

    /**
     * Scales the diagram
     *
     * @param manager
     * 		the manager
     * @param scaleFactor
     * 		the amount to scale
     */
    private static void scale(org.acm.seguin.ide.elixir.UMLViewManager manager, double scaleFactor) {
        manager.getDiagram().scale(scaleFactor);
        manager.getDiagram().repaint();
    }

    /**
     * actually performs the scaling if it is appropriate
     *
     * @param scaleFactor
     * 		The amount to scale the diagram by
     */
    private static void work(double scaleFactor) {
        org.acm.seguin.ide.elixir.ViewManager bvm = org.acm.seguin.ide.elixir.ZoomDiagram.getManager();
        if ((bvm != null) && (bvm instanceof org.acm.seguin.ide.elixir.UMLViewManager)) {
            org.acm.seguin.ide.elixir.ZoomDiagram.scale(((org.acm.seguin.ide.elixir.UMLViewManager) (bvm)), scaleFactor);
        }
    }
}