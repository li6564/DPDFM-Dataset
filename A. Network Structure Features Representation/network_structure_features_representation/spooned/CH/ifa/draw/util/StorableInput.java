/* @(#)StorableInput.java 5.1 */
package CH.ifa.draw.util;
/**
 * An input stream that can be used to resurrect Storable objects.
 * StorableInput preserves the object identity of the stored objects.
 *
 * @see Storable
 * @see StorableOutput
 */
public class StorableInput extends java.lang.Object {
    private java.io.StreamTokenizer fTokenizer;

    private java.util.Vector fMap;

    /**
     * Initializes a Storable input with the given input stream.
     */
    public StorableInput(java.io.InputStream stream) {
        java.io.Reader r = new java.io.BufferedReader(new java.io.InputStreamReader(stream));
        fTokenizer = new java.io.StreamTokenizer(r);
        fMap = new java.util.Vector();
    }

    /**
     * Reads and resurrects a Storable object from the input stream.
     */
    public CH.ifa.draw.util.Storable readStorable() throws java.io.IOException {
        CH.ifa.draw.util.Storable storable;
        java.lang.String s = readString();
        if (s.equals("NULL"))
            return null;

        if (s.equals("REF")) {
            int ref = readInt();
            return ((CH.ifa.draw.util.Storable) (retrieve(ref)));
        }
        storable = ((CH.ifa.draw.util.Storable) (makeInstance(s)));
        map(storable);
        storable.read(this);
        return storable;
    }

    /**
     * Reads a string from the input stream.
     */
    public java.lang.String readString() throws java.io.IOException {
        int token = fTokenizer.nextToken();
        if ((token == java.io.StreamTokenizer.TT_WORD) || (token == '"')) {
            return fTokenizer.sval;
        }
        java.lang.String msg = "String expected in line: " + fTokenizer.lineno();
        throw new java.io.IOException(msg);
    }

    /**
     * Reads an int from the input stream.
     */
    public int readInt() throws java.io.IOException {
        int token = fTokenizer.nextToken();
        if (token == java.io.StreamTokenizer.TT_NUMBER)
            return ((int) (fTokenizer.nval));

        java.lang.String msg = "Integer expected in line: " + fTokenizer.lineno();
        throw new java.io.IOException(msg);
    }

    /**
     * Reads a color from the input stream.
     */
    public java.awt.Color readColor() throws java.io.IOException {
        return new java.awt.Color(readInt(), readInt(), readInt());
    }

    /**
     * Reads a double from the input stream.
     */
    public double readDouble() throws java.io.IOException {
        int token = fTokenizer.nextToken();
        if (token == java.io.StreamTokenizer.TT_NUMBER)
            return fTokenizer.nval;

        java.lang.String msg = "Double expected in line: " + fTokenizer.lineno();
        throw new java.io.IOException(msg);
    }

    /**
     * Reads a boolean from the input stream.
     */
    public boolean readBoolean() throws java.io.IOException {
        int token = fTokenizer.nextToken();
        if (token == java.io.StreamTokenizer.TT_NUMBER)
            return ((int) (fTokenizer.nval)) == 1;

        java.lang.String msg = "Integer expected in line: " + fTokenizer.lineno();
        throw new java.io.IOException(msg);
    }

    private java.lang.Object makeInstance(java.lang.String className) throws java.io.IOException {
        try {
            java.lang.Class cl = java.lang.Class.forName(className);
            return cl.newInstance();
        } catch (java.lang.NoSuchMethodError e) {
            throw new java.io.IOException(("Class " + className) + " does not seem to have a no-arg constructor");
        } catch (java.lang.ClassNotFoundException e) {
            throw new java.io.IOException("No class: " + className);
        } catch (java.lang.InstantiationException e) {
            throw new java.io.IOException("Cannot instantiate: " + className);
        } catch (java.lang.IllegalAccessException e) {
            throw new java.io.IOException(("Class (" + className) + ") not accessible");
        }
    }

    private void map(CH.ifa.draw.util.Storable storable) {
        if (!fMap.contains(storable))
            fMap.addElement(storable);

    }

    private CH.ifa.draw.util.Storable retrieve(int ref) {
        return ((CH.ifa.draw.util.Storable) (fMap.elementAt(ref)));
    }
}