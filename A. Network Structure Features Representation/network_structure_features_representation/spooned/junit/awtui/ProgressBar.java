package junit.awtui;
public class ProgressBar extends java.awt.Canvas {
    public boolean fError = false;

    public int fTotal = 0;

    public int fProgress = 0;

    public int fProgressX = 0;

    public ProgressBar() {
        super();
        setSize(20, 30);
    }

    private java.awt.Color getStatusColor() {
        if (fError)
            return java.awt.Color.red;

        return java.awt.Color.green;
    }

    public void paint(java.awt.Graphics g) {
        paintBackground(g);
        paintStatus(g);
    }

    public void paintBackground(java.awt.Graphics g) {
        g.setColor(java.awt.SystemColor.control);
        java.awt.Rectangle r = getBounds();
        g.fillRect(0, 0, r.width, r.height);
        g.setColor(java.awt.Color.darkGray);
        g.drawLine(0, 0, r.width - 1, 0);
        g.drawLine(0, 0, 0, r.height - 1);
        g.setColor(java.awt.Color.white);
        g.drawLine(r.width - 1, 0, r.width - 1, r.height - 1);
        g.drawLine(0, r.height - 1, r.width - 1, r.height - 1);
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
        paint(getGraphics());
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