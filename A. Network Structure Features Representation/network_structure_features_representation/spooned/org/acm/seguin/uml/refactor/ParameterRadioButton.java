package org.acm.seguin.uml.refactor;
/**
 * This radio button holds a parameter and sets the name
 *  of the radio button automatically.  You can ask for
 *  the parameter summary.
 *
 * @author Chris Seguin
 */
class ParameterRadioButton extends javax.swing.JRadioButton {
    private org.acm.seguin.summary.ParameterSummary summary;

    /**
     * Constructor for the ParameterRadioButton object
     *
     * @param init
     * 		the parameter summary
     */
    public ParameterRadioButton(org.acm.seguin.summary.ParameterSummary init) {
        summary = init;
        setText(((summary.getName() + " (") + summary.getType()) + ")");
    }

    /**
     * Gets the ParameterSummary
     *
     * @return The ParameterSummary
     */
    public org.acm.seguin.summary.ParameterSummary getParameterSummary() {
        return summary;
    }
}