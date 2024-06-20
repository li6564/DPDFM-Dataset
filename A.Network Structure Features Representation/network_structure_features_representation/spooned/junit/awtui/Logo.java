package junit.awtui;
public class Logo extends java.awt.Canvas {
    private java.awt.Image fImage;

    private int fWidth;

    private int fHeight;

    public Logo() {
        fImage = loadImage("logo.gif");
        java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
        tracker.addImage(fImage, 0);
        try {
            tracker.waitForAll();
        } catch (java.lang.Exception e) {
        }
        if (fImage != null) {
            fWidth = fImage.getWidth(this);
            fHeight = fImage.getHeight(this);
        } else {
            fWidth = 20;
            fHeight = 20;
        }
        setSize(fWidth, fHeight);
    }

    public java.awt.Image loadImage(java.lang.String name) {
        java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        try {
            java.net.URL url = junit.runner.BaseTestRunner.class.getResource(name);
            return toolkit.createImage(((java.awt.image.ImageProducer) (url.getContent())));
        } catch (java.lang.Exception ex) {
        }
        return null;
    }

    public void paint(java.awt.Graphics g) {
        paintBackground(g);
        if (fImage != null)
            g.drawImage(fImage, 0, 0, fWidth, fHeight, this);

    }

    public void paintBackground(java.awt.Graphics g) {
        g.setColor(java.awt.SystemColor.control);
        g.fillRect(0, 0, getBounds().width, getBounds().height);
    }
}