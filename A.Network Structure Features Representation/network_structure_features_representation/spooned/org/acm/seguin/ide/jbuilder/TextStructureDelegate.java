package org.acm.seguin.ide.jbuilder;
public class TextStructureDelegate extends org.acm.seguin.ide.jbuilder.TextStructure {
    private org.acm.seguin.ide.jbuilder.TextStructure delegate;

    // Constructors
    public TextStructureDelegate(org.acm.seguin.ide.jbuilder.TextStructure init) {
        delegate = init;
    }

    // Methods
    public void setExpandState(java.util.List p0) {
        delegate.setExpandState(p0);
    }

    public java.util.List getExpandState() {
        return delegate.getExpandState();
    }

    public void mouseExited(java.awt.event.MouseEvent p0) {
        delegate.mouseExited(p0);
    }

    public void mouseEntered(java.awt.event.MouseEvent p0) {
        delegate.mouseEntered(p0);
    }

    public void mouseReleased(java.awt.event.MouseEvent p0) {
        delegate.mouseReleased(p0);
    }

    public void mousePressed(java.awt.event.MouseEvent p0) {
        delegate.mousePressed(p0);
    }

    public void mouseClicked(java.awt.event.MouseEvent p0) {
        delegate.mouseClicked(p0);
    }

    public void keyTyped(java.awt.event.KeyEvent p0) {
        delegate.keyTyped(p0);
    }

    public void keyReleased(java.awt.event.KeyEvent p0) {
        delegate.keyReleased(p0);
    }

    public void keyPressed(java.awt.event.KeyEvent p0) {
        delegate.keyPressed(p0);
    }

    public java.awt.Component getTreeCellRendererComponent(javax.swing.JTree p0, java.lang.Object p1, boolean p2, boolean p3, boolean p4, int p5, boolean p6) {
        return delegate.getTreeCellRendererComponent(p0, p1, p2, p3, p4, p5, p6);
    }

    public void setCaretPosition(int p0, boolean p1) {
        delegate.setCaretPosition(p0, p1);
    }

    public void setCaretPosition(int p0, int p1, boolean p2) {
        delegate.setCaretPosition(p0, p1, p2);
    }

    public void setCaretOffset(int p0, boolean p1) {
        delegate.setCaretOffset(p0, p1);
    }

    public org.acm.seguin.ide.jbuilder.EditorPane getEditorPane() {
        return delegate.getEditorPane();
    }

    public javax.swing.Icon getStructureIcon(java.lang.Object p0) {
        return delegate.getStructureIcon(p0);
    }

    public void nodeActivated(javax.swing.tree.DefaultMutableTreeNode p0) {
        delegate.nodeActivated(p0);
    }

    public void nodeSelected(javax.swing.tree.DefaultMutableTreeNode p0) {
        delegate.nodeSelected(p0);
    }

    public javax.swing.JPopupMenu getPopup() {
        return delegate.getPopup();
    }

    public void updateStructure(javax.swing.text.Document p0) {
        delegate.updateStructure(p0);
    }

    public javax.swing.JTree getTree() {
        return delegate.getTree();
    }

    public void setTree(javax.swing.JTree p0) {
        delegate.setTree(p0);
    }

    public void setFileNode(org.acm.seguin.ide.jbuilder.FileNode p0) {
        delegate.setFileNode(p0);
    }
}