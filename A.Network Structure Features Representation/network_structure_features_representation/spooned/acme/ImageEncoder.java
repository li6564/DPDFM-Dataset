package acme;
// / Abstract class for writing out an image.
// <P>
// A framework for classes that encode and write out an image in
// a particular file format.
// <P>
// This provides a simplified rendition of the ImageConsumer interface.
// It always delivers the pixels as ints in the RGBdefault color model.
// It always provides them in top-down left-right order.
// If you want more flexibility you can always implement ImageConsumer
// directly.
// <P>
// <A HREF="/resources/classes/Acme/JPM/Encoders/ImageEncoder.java">Fetch the software.</A><BR>
// <A HREF="/resources/classes/Acme.tar.gz">Fetch the entire Acme package.</A>
// <P>
// @see GifEncoder
// @see PpmEncoder
// @see Acme.JPM.Decoders.ImageDecoder
public abstract class ImageEncoder implements java.awt.image.ImageConsumer {
    protected java.io.OutputStream out;

    private java.awt.image.ImageProducer producer;

    private int width = -1;

    private int height = -1;

    private int hintflags = 0;

    private boolean started = false;

    private boolean encoding;

    private java.io.IOException iox;

    private static final java.awt.image.ColorModel rgbModel = java.awt.image.ColorModel.getRGBdefault();

    private java.util.Hashtable props = null;

    // / Constructor.
    // @param img The image to encode.
    // @param out The stream to write the bytes to.
    public ImageEncoder(java.awt.Image img, java.io.OutputStream out) throws java.io.IOException {
        this(img.getSource(), out);
    }

    // / Constructor.
    // @param producer The ImageProducer to encode.
    // @param out The stream to write the bytes to.
    public ImageEncoder(java.awt.image.ImageProducer producer, java.io.OutputStream out) throws java.io.IOException {
        this.producer = producer;
        this.out = out;
    }

    // Methods that subclasses implement.
    // / Subclasses implement this to initialize an encoding.
    abstract void encodeStart(int w, int h) throws java.io.IOException;

    // / Subclasses implement this to actually write out some bits.  They
    // are guaranteed to be delivered in top-down-left-right order.
    // One int per pixel, index is row * scansize + off + col,
    // RGBdefault (AARRGGBB) color model.
    abstract void encodePixels(int x, int y, int w, int h, int[] rgbPixels, int off, int scansize) throws java.io.IOException;

    // / Subclasses implement this to finish an encoding.
    abstract void encodeDone() throws java.io.IOException;

    // Our own methods.
    // / Call this after initialization to get things going.
    public synchronized void encode() throws java.io.IOException {
        encoding = true;
        iox = null;
        producer.startProduction(this);
        while (encoding)
            try {
                wait();
            } catch (java.lang.InterruptedException e) {
            }

        if (iox != null)
            throw iox;

    }

    private boolean accumulate = false;

    private int[] accumulator;

    private void encodePixelsWrapper(int x, int y, int w, int h, int[] rgbPixels, int off, int scansize) throws java.io.IOException {
        if (!started) {
            started = true;
            encodeStart(width, height);
            if ((hintflags & java.awt.image.ImageConsumer.TOPDOWNLEFTRIGHT) == 0) {
                accumulate = true;
                accumulator = new int[width * height];
            }
        }
        if (accumulate)
            for (int row = 0; row < h; ++row)
                java.lang.System.arraycopy(rgbPixels, (row * scansize) + off, accumulator, ((y + row) * width) + x, w);

        else
            encodePixels(x, y, w, h, rgbPixels, off, scansize);

    }

    private void encodeFinish() throws java.io.IOException {
        if (accumulate) {
            encodePixels(0, 0, width, height, accumulator, 0, width);
            accumulator = null;
            accumulate = false;
        }
    }

    private synchronized void stop() {
        encoding = false;
        notifyAll();
    }

    // Methods from ImageConsumer.
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setProperties(java.util.Hashtable props) {
        this.props = props;
    }

    public void setColorModel(java.awt.image.ColorModel model) {
        // Ignore.
    }

    public void setHints(int hintflags) {
        this.hintflags = hintflags;
    }

    public void setPixels(int x, int y, int w, int h, java.awt.image.ColorModel model, byte[] pixels, int off, int scansize) {
        int[] rgbPixels = new int[w];
        for (int row = 0; row < h; ++row) {
            int rowOff = off + (row * scansize);
            for (int col = 0; col < w; ++col)
                rgbPixels[col] = model.getRGB(pixels[rowOff + col] & 0xff);

            try {
                encodePixelsWrapper(x, y + row, w, 1, rgbPixels, 0, w);
            } catch (java.io.IOException e) {
                iox = e;
                stop();
                return;
            }
        }
    }

    public void setPixels(int x, int y, int w, int h, java.awt.image.ColorModel model, int[] pixels, int off, int scansize) {
        if (model == acme.ImageEncoder.rgbModel) {
            try {
                encodePixelsWrapper(x, y, w, h, pixels, off, scansize);
            } catch (java.io.IOException e) {
                iox = e;
                stop();
                return;
            }
        } else {
            int[] rgbPixels = new int[w];
            for (int row = 0; row < h; ++row) {
                int rowOff = off + (row * scansize);
                for (int col = 0; col < w; ++col)
                    rgbPixels[col] = model.getRGB(pixels[rowOff + col]);

                try {
                    encodePixelsWrapper(x, y + row, w, 1, rgbPixels, 0, w);
                } catch (java.io.IOException e) {
                    iox = e;
                    stop();
                    return;
                }
            }
        }
    }

    public void imageComplete(int status) {
        producer.removeConsumer(this);
        if (status == java.awt.image.ImageConsumer.IMAGEABORTED)
            iox = new java.io.IOException("image aborted");
        else {
            try {
                encodeFinish();
                encodeDone();
            } catch (java.io.IOException e) {
                iox = e;
            }
        }
        stop();
    }
}