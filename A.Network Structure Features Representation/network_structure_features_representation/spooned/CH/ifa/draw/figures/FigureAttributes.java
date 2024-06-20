/* @(#)FigureAttributes.java 5.1 */
package CH.ifa.draw.figures;
/**
 * A container for a figure's attributes. The attributes are stored
 * as key/value pairs.
 *
 * @see Figure
 */
public class FigureAttributes extends java.lang.Object implements java.lang.Cloneable , java.io.Serializable {
    private java.util.Hashtable fMap;

    /* Serialization support. */
    private static final long serialVersionUID = -6886355144423666716L;

    private int figureAttributesSerializedDataVersion = 1;

    /**
     * Constructs the FigureAttributes.
     */
    public FigureAttributes() {
        fMap = new java.util.Hashtable();
    }

    /**
     * Gets the attribute with the given name.
     *
     * @returns attribute or null if the key is not defined
     */
    public java.lang.Object get(java.lang.String name) {
        return fMap.get(name);
    }

    /**
     * Sets the attribute with the given name and
     * overwrites its previous value.
     */
    public void set(java.lang.String name, java.lang.Object value) {
        fMap.put(name, value);
    }

    /**
     * Tests if an attribute is defined.
     */
    public boolean hasDefined(java.lang.String name) {
        return fMap.containsKey(name);
    }

    /**
     * Clones the attributes.
     */
    public java.lang.Object clone() {
        try {
            CH.ifa.draw.figures.FigureAttributes a = ((CH.ifa.draw.figures.FigureAttributes) (super.clone()));
            a.fMap = ((java.util.Hashtable) (fMap.clone()));
            return a;
        } catch (java.lang.CloneNotSupportedException e) {
            throw new java.lang.InternalError();
        }
    }

    /**
     * Reads the attributes from a StorableInput.
     * FigureAttributes store the following types directly:
     * Color, Boolean, String, Int. Other attribute types
     * have to implement the Storable interface or they
     * have to be wrapped by an object that implements Storable.
     *
     * @see Storable
     * @see #write
     */
    public void read(CH.ifa.draw.util.StorableInput dr) throws java.io.IOException {
        java.lang.String s = dr.readString();
        if (!s.toLowerCase().equals("attributes"))
            throw new java.io.IOException("Attributes expected");

        fMap = new java.util.Hashtable();
        int size = dr.readInt();
        for (int i = 0; i < size; i++) {
            java.lang.String key = dr.readString();
            java.lang.String valtype = dr.readString();
            java.lang.Object val = null;
            if (valtype.equals("Color"))
                val = new java.awt.Color(dr.readInt(), dr.readInt(), dr.readInt());
            else if (valtype.equals("Boolean"))
                val = new java.lang.Boolean(dr.readString());
            else if (valtype.equals("String"))
                val = dr.readString();
            else if (valtype.equals("Int"))
                val = new java.lang.Integer(dr.readInt());
            else if (valtype.equals("Storable"))
                val = dr.readStorable();
            else if (valtype.equals("UNKNOWN"))
                continue;

            fMap.put(key, val);
        }
    }

    /**
     * Writes the attributes to a StorableInput.
     * FigureAttributes store the following types directly:
     * Color, Boolean, String, Int. Other attribute types
     * have to implement the Storable interface or they
     * have to be wrapped by an object that implements Storable.
     *
     * @see Storable
     * @see #write
     */
    public void write(CH.ifa.draw.util.StorableOutput dw) {
        dw.writeString("attributes");
        dw.writeInt(fMap.size());// number of attributes

        java.util.Enumeration k = fMap.keys();
        while (k.hasMoreElements()) {
            java.lang.String s = ((java.lang.String) (k.nextElement()));
            dw.writeString(s);
            java.lang.Object v = fMap.get(s);
            if (v instanceof java.lang.String) {
                dw.writeString("String");
                dw.writeString(((java.lang.String) (v)));
            } else if (v instanceof java.awt.Color) {
                dw.writeString("Color");
                dw.writeInt(((java.awt.Color) (v)).getRed());
                dw.writeInt(((java.awt.Color) (v)).getGreen());
                dw.writeInt(((java.awt.Color) (v)).getBlue());
            } else if (v instanceof java.lang.Boolean) {
                dw.writeString("Boolean");
                if (((java.lang.Boolean) (v)).booleanValue())
                    dw.writeString("TRUE");
                else
                    dw.writeString("FALSE");

            } else if (v instanceof java.lang.Integer) {
                dw.writeString("Int");
                dw.writeInt(((java.lang.Integer) (v)).intValue());
            } else if (v instanceof CH.ifa.draw.util.Storable) {
                dw.writeString("Storable");
                dw.writeStorable(((CH.ifa.draw.util.Storable) (v)));
            } else {
                java.lang.System.out.println(v);
                dw.writeString("UNKNOWN");
            }
        } 
    }
}