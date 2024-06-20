package org.acm.seguin.ide.common;
/**
 * Generic adapter for browsing source code
 *
 * @author Chris Seguin
 */
public class SourceBrowserAdapter implements java.awt.event.ActionListener {
    private org.acm.seguin.uml.ISourceful activeComponent;

    /**
     * Constructor for the SourceBrowserAdapter object
     *
     * @param component
     * 		Description of Parameter
     */
    public SourceBrowserAdapter(org.acm.seguin.uml.ISourceful component) {
        activeComponent = component;
    }

    /**
     * Responds to this item being selected
     *
     * @param evt
     * 		Description of Parameter
     */
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        java.io.File file = findFile();
        int line = getLine();
        org.acm.seguin.ide.common.SourceBrowser.get().gotoSource(file, line);
    }

    /**
     * Get the line number of the start of the current activeComponent.
     *
     * @return The line number.
     */
    protected int getLine() {
        return getSummary().getDeclarationLine();
    }

    /**
     * Get the Summary of the activeComponent.
     *
     * @return The Summary of the activeComponent.
     */
    protected org.acm.seguin.summary.Summary getSummary() {
        return activeComponent.getSourceSummary();
    }

    /**
     * Look up the chain of Summary parents to find the File the activeComponent
     *  is sourced in.
     *
     * @return The File.
     */
    protected java.io.File findFile() {
        org.acm.seguin.summary.Summary summary = getSummary();
        while (!(summary instanceof org.acm.seguin.summary.FileSummary)) {
            summary = summary.getParent();
        } 
        org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (summary));
        return fileSummary.getFile();
    }
}