package org.acm.seguin.ide.elixir;
/**
 * This source browser allows Elixir to load files.
 *
 * @author Chris Seguin
 */
public class ElixirSourceBrowser extends org.acm.seguin.ide.common.SourceBrowser {
    /**
     * Determines if the system is in a state where
     *  it can browse the source code
     *
     * @return true if the source code browsing is enabled
     */
    public boolean canBrowseSource() {
        return true;
    }

    /**
     * Actually browses to the file
     *
     * @param filename
     * 		the file
     * @param line
     * 		the line in the file
     */
    public void gotoSource(java.io.File file, int line) {
        if ((file == null) || (!file.exists())) {
            return;
        }
        try {
            java.lang.String name = file.getCanonicalPath();
            org.acm.seguin.ide.elixir.ViewManager vm = org.acm.seguin.ide.elixir.FrameManager.current().open(name);
            // System.out.println("View type:  " + vm.getView().getClass().getName());
            if (vm instanceof org.acm.seguin.ide.elixir.BasicViewManager) {
                ((org.acm.seguin.ide.elixir.BasicViewManager) (vm)).setLineNo(line);
            }
        } catch (java.io.IOException ioe) {
        }
    }
}