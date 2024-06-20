package junit.swingui;
/**
 * A panel with test run counters
 */
public class CounterPanel extends java.awt.Panel {
    private javax.swing.JTextField fNumberOfErrors;

    private javax.swing.JTextField fNumberOfFailures;

    private javax.swing.JTextField fNumberOfRuns;

    private int fTotal;

    public CounterPanel() {
        super(new java.awt.GridLayout(2, 3));
        add(new javax.swing.JLabel("Runs:"));
        add(new javax.swing.JLabel("Errors:"));
        add(new javax.swing.JLabel("Failures: "));
        fNumberOfErrors = createOutputField();
        fNumberOfFailures = createOutputField();
        fNumberOfRuns = createOutputField();
        add(fNumberOfRuns);
        add(fNumberOfErrors);
        add(fNumberOfFailures);
    }

    private javax.swing.JTextField createOutputField() {
        javax.swing.JTextField field = new javax.swing.JTextField("0", 4);
        field.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        field.setFont(junit.swingui.StatusLine.BOLD_FONT);
        field.setEditable(false);
        field.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        return field;
    }

    public void reset() {
        setLabelValue(fNumberOfErrors, 0);
        setLabelValue(fNumberOfFailures, 0);
        setLabelValue(fNumberOfRuns, 0);
        fTotal = 0;
    }

    public void setTotal(int value) {
        fTotal = value;
    }

    public void setRunValue(int value) {
        fNumberOfRuns.setText((java.lang.Integer.toString(value) + "/") + fTotal);
    }

    public void setErrorValue(int value) {
        setLabelValue(fNumberOfErrors, value);
    }

    public void setFailureValue(int value) {
        setLabelValue(fNumberOfFailures, value);
    }

    // private String asString(int value) {
    // return Integer.toString(value);
    // }
    private void setLabelValue(javax.swing.JTextField label, int value) {
        label.setText(java.lang.Integer.toString(value));
    }
}