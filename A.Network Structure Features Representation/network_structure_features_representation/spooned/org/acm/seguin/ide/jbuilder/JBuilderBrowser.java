package org.acm.seguin.ide.jbuilder;
/**
 * Base class for source browsing. This is the generic base class.
 *
 * @author Chris Seguin
 */
public class JBuilderBrowser extends org.acm.seguin.ide.common.SourceBrowser {
    /**
     * Determines if the system is in a state where it can browse the source
     *  code
     *
     * @return true if the source code browsing is enabled
     */
    public boolean canBrowseSource() {
        return true;
    }

    /**
     * Actually browses to the file
     *
     * @param file
     * 		the file
     * @param line
     * 		the line in the file
     */
    public void gotoSource(java.io.File file, int line) {
        if (file != null) {
            org.acm.seguin.ide.jbuilder.FileNode sourceNode = findSourceFileNode(file);
            showNode(sourceNode);
            gotoLine(line, sourceNode);
        }
    }

    /**
     * Get the FileNode that matches a File (in this project).
     *
     * @param file
     * 		File to look for in this project.
     * @return FileNode The FileNode. *duh
     */
    protected org.acm.seguin.ide.jbuilder.FileNode findSourceFileNode(java.io.File file) {
        org.acm.seguin.ide.jbuilder.Browser browser = org.acm.seguin.ide.jbuilder.Browser.getActiveBrowser();
        org.acm.seguin.ide.jbuilder.Project project = browser.getActiveProject();
        if (project == null) {
            project = browser.getDefaultProject();
        }
        org.acm.seguin.ide.jbuilder.Url url = new org.acm.seguin.ide.jbuilder.Url(file);
        return project.getNode(url);
    }

    /**
     * Show a source file.
     *
     * @param node
     * 		Source file node to show.
     */
    protected void showNode(org.acm.seguin.ide.jbuilder.FileNode node) {
        org.acm.seguin.ide.jbuilder.Browser browser = org.acm.seguin.ide.jbuilder.Browser.getActiveBrowser();
        try {
            browser.setActiveNode(node, true);
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Go to a specific line in a source file.
     *
     * @param lineNumber
     * 		Line number to go to.
     * @param sourceNode
     * 		Source file node.
     */
    protected void gotoLine(int lineNumber, org.acm.seguin.ide.jbuilder.FileNode sourceNode) {
        org.acm.seguin.ide.jbuilder.AbstractTextNodeViewer sourceViewer = ((org.acm.seguin.ide.jbuilder.AbstractTextNodeViewer) (org.acm.seguin.ide.jbuilder.Browser.getActiveBrowser().getViewerOfType(sourceNode, org.acm.seguin.ide.jbuilder.AbstractTextNodeViewer.class)));
        org.acm.seguin.ide.jbuilder.EditorPane editor = sourceViewer.getEditor();
        editor.gotoPosition(lineNumber, 1, false, EditorPane.CENTER_ALWAYS);
    }
}