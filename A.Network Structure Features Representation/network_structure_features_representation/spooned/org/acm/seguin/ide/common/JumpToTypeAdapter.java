package org.acm.seguin.ide.common;
/**
 * Adapter that tells the UML diagram to jump to a specific type
 *
 * @author Chris Seguin
 */
class JumpToTypeAdapter extends java.awt.event.MouseAdapter {
    private org.acm.seguin.uml.UMLPackage umlPackage;

    private org.acm.seguin.summary.TypeSummary typeSummary;

    /**
     * Constructor for the JumpToTypeAdapter object
     *
     * @param initPanel
     * 		Description of Parameter
     * @param initType
     * 		Description of Parameter
     */
    public JumpToTypeAdapter(org.acm.seguin.uml.UMLPackage initPanel, org.acm.seguin.summary.TypeSummary initType) {
        umlPackage = initPanel;
        typeSummary = initType;
    }

    /**
     * The mouse has been clicked on the type
     *
     * @param e
     * 		the mouse click
     */
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (umlPackage == null)
            return;

        umlPackage.jumpTo(typeSummary);
    }
}