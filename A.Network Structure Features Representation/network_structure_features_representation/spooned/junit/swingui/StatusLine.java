package junit.swingui;
/**
 * A status line component.
 */
public class StatusLine extends javax.swing.JTextField {
    public static final java.awt.Font PLAIN_FONT = new java.awt.Font("dialog", java.awt.Font.PLAIN, 12);

    public static final java.awt.Font BOLD_FONT = new java.awt.Font("dialog", java.awt.Font.BOLD, 12);

    public StatusLine(int preferredWidth) {
        super();
        setFont(junit.swingui.StatusLine.BOLD_FONT);
        setEditable(false);
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        java.awt.Dimension d = getPreferredSize();
        d.width = preferredWidth;
        setPreferredSize(d);
    }

    public void showInfo(java.lang.String message) {
        setFont(junit.swingui.StatusLine.PLAIN_FONT);
        setForeground(java.awt.Color.black);
        setText(message);
    }

    public void showError(java.lang.String status) {
        setFont(junit.swingui.StatusLine.BOLD_FONT);
        setForeground(java.awt.Color.red);
        setText(status);
        setToolTipText(status);
    }

    public void clear() {
        setText("");
        setToolTipText(null);
    }
}