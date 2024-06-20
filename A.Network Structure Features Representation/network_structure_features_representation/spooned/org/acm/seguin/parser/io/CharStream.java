package org.acm.seguin.parser.io;
/**
 * Basic character stream. The javacc tool creates one of four different
 *  types of character streams. To be able to switch between different types,
 *  I've created this parent class. The parent class invokes the appropriate
 *  child class that was created by javacc. <P>
 *
 *  <B>ASCII_CharStream</B> processes western files where characters are
 *  stored as a single byte. <P>
 *
 *  <B>UCode_CharStream</B> processes far eastern files where unicode (2 byte)
 *  characters are used.
 *
 * @author Chris Seguin
 */
public class CharStream extends java.lang.Object {
    /**
     * Is this a static parser
     */
    public static final boolean staticFlag = true;

    /**
     * The buffer location
     */
    public static int bufpos = -1;

    /**
     * The buffer size
     */
    protected static int bufsize;

    /**
     * Are there more characters available
     */
    protected static int available;

    /**
     * Index of the current token's starting point
     */
    protected static int tokenBegin;

    /**
     * The buffer line
     */
    protected static int bufline[];

    /**
     * The buffer column
     */
    protected static int bufcolumn[];

    /**
     * The column index
     */
    protected static int column = 0;

    /**
     * The line index
     */
    protected static int line = 1;

    /**
     * Was the previous character a CR?
     */
    protected static boolean prevCharIsCR = false;

    /**
     * Was the previous character a LF?
     */
    protected static boolean prevCharIsLF = false;

    /**
     * The input
     */
    protected static java.io.Reader inputStream;

    /**
     * The buffer
     */
    protected static char[] buffer;

    /**
     * The maximum next character index
     */
    protected static int maxNextCharInd = 0;

    /**
     * Index into the buffer
     */
    protected static int inBuf = 0;

    /**
     * This field determines which type of character stream to use
     */
    private static int charStreamType = -1;

    /**
     * Use the ascii character stream
     */
    private static final int ASCII = 1;

    /**
     * Use the unicode character stream
     */
    private static final int UNICODE = 2;

    /**
     * Use the unicode character stream
     */
    private static final int FULL_CHAR = 3;

    /**
     * Gets the Column attribute of the CharStream class
     *
     * @return The Column value
     */
    public static int getColumn() {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return org.acm.seguin.parser.io.ASCII_CharStream.getColumn();
        else
            return org.acm.seguin.parser.io.UCode_CharStream.getColumn();

    }

    /**
     * Gets the Line attribute of the CharStream class
     *
     * @return The Line value
     */
    public static int getLine() {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return org.acm.seguin.parser.io.ASCII_CharStream.getLine();
        else
            return org.acm.seguin.parser.io.UCode_CharStream.getLine();

    }

    /**
     * Gets the EndColumn attribute of the CharStream class
     *
     * @return The EndColumn value
     */
    public static int getEndColumn() {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return org.acm.seguin.parser.io.ASCII_CharStream.getEndColumn();
        else
            return org.acm.seguin.parser.io.UCode_CharStream.getEndColumn();

    }

    /**
     * Gets the EndLine attribute of the CharStream class
     *
     * @return The EndLine value
     */
    public static int getEndLine() {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return org.acm.seguin.parser.io.ASCII_CharStream.getEndLine();
        else
            return org.acm.seguin.parser.io.UCode_CharStream.getEndLine();

    }

    /**
     * Gets the BeginColumn attribute of the CharStream class
     *
     * @return The BeginColumn value
     */
    public static int getBeginColumn() {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return org.acm.seguin.parser.io.ASCII_CharStream.getBeginColumn();
        else
            return org.acm.seguin.parser.io.UCode_CharStream.getBeginColumn();

    }

    /**
     * Gets the BeginLine attribute of the CharStream class
     *
     * @return The BeginLine value
     */
    public static int getBeginLine() {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return org.acm.seguin.parser.io.ASCII_CharStream.getBeginLine();
        else
            return org.acm.seguin.parser.io.UCode_CharStream.getBeginLine();

    }

    /**
     * Description of the Method
     *
     * @param len
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public static char[] GetSuffix(int len) {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return org.acm.seguin.parser.io.ASCII_CharStream.GetSuffix(len);
        else
            return org.acm.seguin.parser.io.UCode_CharStream.GetSuffix(len);

    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public static java.lang.String GetImage() {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return org.acm.seguin.parser.io.ASCII_CharStream.GetImage();
        else
            return org.acm.seguin.parser.io.UCode_CharStream.GetImage();

    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     * @exception java.io.IOException
     * 		Description of Exception
     */
    public static char BeginToken() throws java.io.IOException {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return org.acm.seguin.parser.io.ASCII_CharStream.BeginToken();
        else
            return org.acm.seguin.parser.io.UCode_CharStream.BeginToken();

    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     * @exception java.io.IOException
     * 		Description of Exception
     */
    public static char readChar() throws java.io.IOException {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return org.acm.seguin.parser.io.ASCII_CharStream.readChar();
        else
            return org.acm.seguin.parser.io.UCode_CharStream.readChar();

    }

    /**
     * Description of the Method
     *
     * @param amount
     * 		Description of Parameter
     */
    public static void backup(int amount) {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            org.acm.seguin.parser.io.ASCII_CharStream.backup(amount);
        else
            org.acm.seguin.parser.io.UCode_CharStream.backup(amount);

    }

    /**
     * Description of the Method
     *
     * @param dstream
     * 		Description of Parameter
     * @param startline
     * 		Description of Parameter
     * @param startcolumn
     * 		Description of Parameter
     * @param buffersize
     * 		Description of Parameter
     */
    public static void ReInit(java.io.Reader dstream, int startline, int startcolumn, int buffersize) {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            org.acm.seguin.parser.io.ASCII_CharStream.ReInit(dstream, startline, startcolumn, buffersize);
        else
            org.acm.seguin.parser.io.UCode_CharStream.ReInit(dstream, startline, startcolumn, buffersize);

    }

    /**
     * Description of the Method
     *
     * @param dstream
     * 		Description of Parameter
     * @param startline
     * 		Description of Parameter
     * @param startcolumn
     * 		Description of Parameter
     */
    public static void ReInit(java.io.Reader dstream, int startline, int startcolumn) {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            org.acm.seguin.parser.io.ASCII_CharStream.ReInit(dstream, startline, startcolumn);
        else
            org.acm.seguin.parser.io.UCode_CharStream.ReInit(dstream, startline, startcolumn);

    }

    /**
     * Description of the Method
     *
     * @param dstream
     * 		Description of Parameter
     * @param startline
     * 		Description of Parameter
     * @param startcolumn
     * 		Description of Parameter
     * @param buffersize
     * 		Description of Parameter
     */
    public static void ReInit(java.io.InputStream dstream, int startline, int startcolumn, int buffersize) {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            org.acm.seguin.parser.io.ASCII_CharStream.ReInit(dstream, startline, startcolumn, buffersize);
        else
            org.acm.seguin.parser.io.UCode_CharStream.ReInit(dstream, startline, startcolumn, buffersize);

    }

    /**
     * Description of the Method
     *
     * @param dstream
     * 		Description of Parameter
     * @param startline
     * 		Description of Parameter
     * @param startcolumn
     * 		Description of Parameter
     */
    public static void ReInit(java.io.InputStream dstream, int startline, int startcolumn) {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            org.acm.seguin.parser.io.ASCII_CharStream.ReInit(dstream, startline, startcolumn);
        else
            org.acm.seguin.parser.io.UCode_CharStream.ReInit(dstream, startline, startcolumn);

    }

    /**
     * Description of the Method
     *
     * @param newLine
     * 		Description of Parameter
     * @param newCol
     * 		Description of Parameter
     */
    public static void adjustBeginLineColumn(int newLine, int newCol) {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            org.acm.seguin.parser.io.ASCII_CharStream.adjustBeginLineColumn(newLine, newCol);
        else
            org.acm.seguin.parser.io.UCode_CharStream.adjustBeginLineColumn(newLine, newCol);

    }

    /**
     * Constructor for the ASCII_CharStream object
     *
     * @param dstream
     * 		Description of Parameter
     * @param startline
     * 		Description of Parameter
     * @param startcolumn
     * 		Description of Parameter
     * @param buffersize
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.parser.io.CharStream make(java.io.Reader dstream, int startline, int startcolumn, int buffersize) {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return new org.acm.seguin.parser.io.ASCII_CharStream(dstream, startline, startcolumn, buffersize, org.acm.seguin.parser.io.CharStream.getCharStreamType() == org.acm.seguin.parser.io.CharStream.FULL_CHAR);
        else
            return new org.acm.seguin.parser.io.UCode_CharStream(dstream, startline, startcolumn, buffersize);

    }

    /**
     * Constructor for the ASCII_CharStream object
     *
     * @param dstream
     * 		Description of Parameter
     * @param startline
     * 		Description of Parameter
     * @param startcolumn
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.parser.io.CharStream make(java.io.Reader dstream, int startline, int startcolumn) {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return new org.acm.seguin.parser.io.ASCII_CharStream(dstream, startline, startcolumn, org.acm.seguin.parser.io.CharStream.getCharStreamType() == org.acm.seguin.parser.io.CharStream.FULL_CHAR);
        else
            return new org.acm.seguin.parser.io.UCode_CharStream(dstream, startline, startcolumn);

    }

    /**
     * Constructor for the ASCII_CharStream object
     *
     * @param dstream
     * 		Description of Parameter
     * @param startline
     * 		Description of Parameter
     * @param startcolumn
     * 		Description of Parameter
     * @param buffersize
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.parser.io.CharStream make(java.io.InputStream dstream, int startline, int startcolumn, int buffersize) {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return new org.acm.seguin.parser.io.ASCII_CharStream(dstream, startline, startcolumn, buffersize, org.acm.seguin.parser.io.CharStream.getCharStreamType() == org.acm.seguin.parser.io.CharStream.FULL_CHAR);
        else
            return new org.acm.seguin.parser.io.UCode_CharStream(dstream, startline, startcolumn, buffersize);

    }

    /**
     * Constructor for the ASCII_CharStream object
     *
     * @param dstream
     * 		Description of Parameter
     * @param startline
     * 		Description of Parameter
     * @param startcolumn
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public static org.acm.seguin.parser.io.CharStream make(java.io.InputStream dstream, int startline, int startcolumn) {
        if (org.acm.seguin.parser.io.CharStream.getCharStreamType() != org.acm.seguin.parser.io.CharStream.UNICODE)
            return new org.acm.seguin.parser.io.ASCII_CharStream(dstream, startline, startcolumn, org.acm.seguin.parser.io.CharStream.getCharStreamType() == org.acm.seguin.parser.io.CharStream.FULL_CHAR);
        else
            return new org.acm.seguin.parser.io.UCode_CharStream(dstream, startline, startcolumn);

    }

    /**
     * Gets the type of character stream
     *
     * @return The CharStreamType value
     */
    protected static int getCharStreamType() {
        if (org.acm.seguin.parser.io.CharStream.charStreamType == (-1)) {
            org.acm.seguin.parser.io.CharStream.initCharStreamType();
        }
        return org.acm.seguin.parser.io.CharStream.charStreamType;
    }

    /**
     * Initializes the character stream type from the pretty.settings file
     */
    private static synchronized void initCharStreamType() {
        try {
            org.acm.seguin.util.FileSettings bundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "pretty");
            org.acm.seguin.parser.io.CharStream.charStreamType = bundle.getInteger("char.stream.type");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
            org.acm.seguin.parser.io.CharStream.charStreamType = org.acm.seguin.parser.io.CharStream.ASCII;
        }
    }
}