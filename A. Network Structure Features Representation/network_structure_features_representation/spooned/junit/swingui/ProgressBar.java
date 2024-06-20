package junit.swingui;
/**
 * A simple progress bar showing the green/red status
 */
class ProgressBar extends javax.swing.JPanel {
    boolean fError = false;

    int fTotal = 0;

    int fProgress = 0;

    int fProgressX = 0;

    public ProgressBar() {
        super();
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
    }

    private java.awt.Color getStatusColor() {
        if (fError)
            return java.awt.Color.red;

        return java.awt.Color.green;
    }

    public void paintBackground(java.awt.Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void paintComponent(java.awt.Graphics g) {
        paintBackground(g);
        paintStatus(g);
    }

    public void paintStatus(java.awt.Graphics g) {
        g.setColor(getStatusColor());
        java.awt.Rectangle r = new java.awt.Rectangle(0, 0, fProgressX, getBounds().height);
        g.fillRect(1, 1, r.width - 1, r.height - 2);
    }

    private void paintStep(int startX, int endX) {
        repaint(startX, 1, endX - startX, getBounds().height - 2);
    }

    public void reset() {
        fProgressX = 1;
        fProgress = 0;
        fError = false;
        repaint();
    }

    public int scale(int value) {
        if (fTotal > 0)
            return java.lang.Math.max(1, (value * (getBounds().width - 1)) / fTotal);

        return value;
    }

    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, y, w, h);
        fProgressX = scale(fProgress);
    }

    public void start(int total) {
        fTotal = total;
        reset();
    }

    public void step(boolean successful) {
        fProgress++;
        int x = fProgressX;
        fProgressX = scale(fProgress);
        if ((!fError) && (!successful)) {
            fError = true;
            x = 1;
        }
        paintStep(x, fProgressX);
    }
}