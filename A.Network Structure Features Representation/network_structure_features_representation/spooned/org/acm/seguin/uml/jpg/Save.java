package org.acm.seguin.uml.jpg;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * Description of the Class
 *
 * @author Chris Seguin
 */
public class Save {
    private java.lang.String filename;

    private org.acm.seguin.uml.UMLPackage diagram;

    /**
     * Constructor for the Save object
     *
     * @param init
     * 		Description of Parameter
     * @param packageDiagram
     * 		Description of Parameter
     */
    public Save(java.lang.String init, org.acm.seguin.uml.UMLPackage packageDiagram) {
        filename = init;
        diagram = packageDiagram;
    }

    /**
     * Main processing method for the Save object
     */
    public void run() {
        try {
            java.awt.Dimension dim = diagram.getPreferredSize();
            java.awt.image.BufferedImage doubleBuffer = new java.awt.image.BufferedImage(dim.width, dim.height, java.awt.image.BufferedImage.TYPE_INT_RGB);
            java.awt.Graphics g = doubleBuffer.getGraphics();
            g.setColor(java.awt.Color.gray);
            g.fillRect(0, 0, dim.width, dim.height);
            diagram.print(g, 0, 0);
            java.io.OutputStream out = new java.io.BufferedOutputStream(new java.io.FileOutputStream(filename));
            com.sun.image.codec.jpeg.JPEGEncodeParam param = com.sun.image.codec.jpeg.JPEGCodec.getDefaultJPEGEncodeParam(doubleBuffer);
            com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(out, param);
            encoder.encode(doubleBuffer);
            out.flush();
            out.close();
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
    }
}