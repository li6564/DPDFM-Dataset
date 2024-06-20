/* @(#)FigureSelection.java 5.1 */
package CH.ifa.draw.framework;
/**
 * FigureSelection enables to transfer the selected figures
 * to a clipboard.<p>
 * Will soon be converted to the JDK 1.1 Transferable interface.
 *
 * @see Clipboard
 */
public class FigureSelection extends java.lang.Object {
    private byte[] fData;// flattend figures, ready to be resurrected


    /**
     * The type identifier of the selection.
     */
    public static final java.lang.String TYPE = "CH.ifa.draw.Figures";

    /**
     * Constructes the Figure selection for the vecotor of figures.
     */
    public FigureSelection(java.util.Vector figures) {
        // a FigureSelection is represented as a flattened ByteStream
        // of figures.
        java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream(200);
        CH.ifa.draw.util.StorableOutput writer = new CH.ifa.draw.util.StorableOutput(output);
        writer.writeInt(figures.size());
        java.util.Enumeration selected = figures.elements();
        while (selected.hasMoreElements()) {
            CH.ifa.draw.framework.Figure figure = ((CH.ifa.draw.framework.Figure) (selected.nextElement()));
            writer.writeStorable(figure);
        } 
        writer.close();
        fData = output.toByteArray();
    }

    /**
     * Gets the type of the selection.
     */
    public java.lang.String getType() {
        return CH.ifa.draw.framework.FigureSelection.TYPE;
    }

    /**
     * Gets the data of the selection. The result is returned
     * as a Vector of Figures.
     *
     * @return a copy of the figure selection.
     */
    public java.lang.Object getData(java.lang.String type) {
        if (type.equals(CH.ifa.draw.framework.FigureSelection.TYPE)) {
            java.io.InputStream input = new java.io.ByteArrayInputStream(fData);
            java.util.Vector result = new java.util.Vector(10);
            CH.ifa.draw.util.StorableInput reader = new CH.ifa.draw.util.StorableInput(input);
            int numRead = 0;
            try {
                int count = reader.readInt();
                while (numRead < count) {
                    CH.ifa.draw.framework.Figure newFigure = ((CH.ifa.draw.framework.Figure) (reader.readStorable()));
                    result.addElement(newFigure);
                    numRead++;
                } 
            } catch (java.io.IOException e) {
                java.lang.System.out.println(e.toString());
            }
            return result;
        }
        return null;
    }
}