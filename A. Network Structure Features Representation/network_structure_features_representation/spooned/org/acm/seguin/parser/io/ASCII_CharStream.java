package org.acm.seguin.parser.io;
/**
 * An implementation of interface CharStream, where the stream is assumed to
 *  contain only ASCII characters (without unicode processing).
 *
 * @author Chris Seguin
 */
final class ASCII_CharStream extends org.acm.seguin.parser.io.CharStream {
    private static char MASK;

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
     */
    public ASCII_CharStream(java.io.Reader dstream, int startline, int startcolumn, int buffersize, boolean fullChar) {
        if (org.acm.seguin.parser.io.CharStream.inputStream != null) {
            throw new java.lang.Error(("\n   ERROR: Second call to the constructor of a static ASCII_CharStream.  You must\n" + "       either use ReInit() or set the JavaCC option STATIC to false\n") + "       during the generation of this class.");
        }
        org.acm.seguin.parser.io.CharStream.inputStream = dstream;
        org.acm.seguin.parser.io.CharStream.line = startline;
        org.acm.seguin.parser.io.CharStream.column = startcolumn - 1;
        org.acm.seguin.parser.io.CharStream.available = org.acm.seguin.parser.io.CharStream.bufsize = buffersize;
        org.acm.seguin.parser.io.CharStream.buffer = new char[buffersize];
        org.acm.seguin.parser.io.CharStream.bufline = new int[buffersize];
        org.acm.seguin.parser.io.CharStream.bufcolumn = new int[buffersize];
        if (fullChar)
            org.acm.seguin.parser.io.ASCII_CharStream.MASK = 0xffff;
        else
            org.acm.seguin.parser.io.ASCII_CharStream.MASK = 0xff;

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
     */
    public ASCII_CharStream(java.io.Reader dstream, int startline, int startcolumn, boolean useMask) {
        this(dstream, startline, startcolumn, 4096, useMask);
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
     */
    public ASCII_CharStream(java.io.InputStream dstream, int startline, int startcolumn, int buffersize, boolean useMask) {
        this(new java.io.InputStreamReader(dstream), startline, startcolumn, 4096, useMask);
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
     */
    public ASCII_CharStream(java.io.InputStream dstream, int startline, int startcolumn, boolean useMask) {
        this(dstream, startline, startcolumn, 4096, useMask);
    }

    /**
     *
     * @return The Column value
     * @deprecated  * @see #getEndColumn
     */
    public static final int getColumn() {
        return org.acm.seguin.parser.io.CharStream.bufcolumn[org.acm.seguin.parser.io.CharStream.bufpos];
    }

    /**
     *
     * @return The Line value
     * @deprecated  * @see #getEndLine
     */
    public static final int getLine() {
        return org.acm.seguin.parser.io.CharStream.bufline[org.acm.seguin.parser.io.CharStream.bufpos];
    }

    /**
     * Gets the EndColumn attribute of the ASCII_CharStream class
     *
     * @return The EndColumn value
     */
    public static final int getEndColumn() {
        return org.acm.seguin.parser.io.CharStream.bufcolumn[org.acm.seguin.parser.io.CharStream.bufpos];
    }

    /**
     * Gets the EndLine attribute of the ASCII_CharStream class
     *
     * @return The EndLine value
     */
    public static final int getEndLine() {
        return org.acm.seguin.parser.io.CharStream.bufline[org.acm.seguin.parser.io.CharStream.bufpos];
    }

    /**
     * Gets the BeginColumn attribute of the ASCII_CharStream class
     *
     * @return The BeginColumn value
     */
    public static final int getBeginColumn() {
        return org.acm.seguin.parser.io.CharStream.bufcolumn[org.acm.seguin.parser.io.CharStream.tokenBegin];
    }

    /**
     * Gets the BeginLine attribute of the ASCII_CharStream class
     *
     * @return The BeginLine value
     */
    public static final int getBeginLine() {
        return org.acm.seguin.parser.io.CharStream.bufline[org.acm.seguin.parser.io.CharStream.tokenBegin];
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public static final java.lang.String GetImage() {
        if (org.acm.seguin.parser.io.CharStream.bufpos >= org.acm.seguin.parser.io.CharStream.tokenBegin) {
            return new java.lang.String(org.acm.seguin.parser.io.CharStream.buffer, org.acm.seguin.parser.io.CharStream.tokenBegin, (org.acm.seguin.parser.io.CharStream.bufpos - org.acm.seguin.parser.io.CharStream.tokenBegin) + 1);
        } else {
            return new java.lang.String(org.acm.seguin.parser.io.CharStream.buffer, org.acm.seguin.parser.io.CharStream.tokenBegin, org.acm.seguin.parser.io.CharStream.bufsize - org.acm.seguin.parser.io.CharStream.tokenBegin) + new java.lang.String(org.acm.seguin.parser.io.CharStream.buffer, 0, org.acm.seguin.parser.io.CharStream.bufpos + 1);
        }
    }

    /**
     * Description of the Method
     *
     * @param len
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public static final char[] GetSuffix(int len) {
        char[] ret = new char[len];
        if ((org.acm.seguin.parser.io.CharStream.bufpos + 1) >= len) {
            java.lang.System.arraycopy(org.acm.seguin.parser.io.CharStream.buffer, (org.acm.seguin.parser.io.CharStream.bufpos - len) + 1, ret, 0, len);
        } else {
            java.lang.System.arraycopy(org.acm.seguin.parser.io.CharStream.buffer, org.acm.seguin.parser.io.CharStream.bufsize - ((len - org.acm.seguin.parser.io.CharStream.bufpos) - 1), ret, 0, (len - org.acm.seguin.parser.io.CharStream.bufpos) - 1);
            java.lang.System.arraycopy(org.acm.seguin.parser.io.CharStream.buffer, 0, ret, (len - org.acm.seguin.parser.io.CharStream.bufpos) - 1, org.acm.seguin.parser.io.CharStream.bufpos + 1);
        }
        return ret;
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     * @exception java.io.IOException
     * 		Description of Exception
     */
    public static final char BeginToken() throws java.io.IOException {
        org.acm.seguin.parser.io.CharStream.tokenBegin = -1;
        char c = org.acm.seguin.parser.io.ASCII_CharStream.readChar();
        org.acm.seguin.parser.io.CharStream.tokenBegin = org.acm.seguin.parser.io.CharStream.bufpos;
        return c;
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     * @exception java.io.IOException
     * 		Description of Exception
     */
    public static final char readChar() throws java.io.IOException {
        if (org.acm.seguin.parser.io.CharStream.inBuf > 0) {
            --org.acm.seguin.parser.io.CharStream.inBuf;
            return ((char) (org.acm.seguin.parser.io.ASCII_CharStream.MASK & org.acm.seguin.parser.io.CharStream.buffer[org.acm.seguin.parser.io.CharStream.bufpos == (org.acm.seguin.parser.io.CharStream.bufsize - 1) ? org.acm.seguin.parser.io.CharStream.bufpos = 0 : ++org.acm.seguin.parser.io.CharStream.bufpos]));
        }
        if ((++org.acm.seguin.parser.io.CharStream.bufpos) >= org.acm.seguin.parser.io.CharStream.maxNextCharInd) {
            org.acm.seguin.parser.io.ASCII_CharStream.FillBuff();
        }
        char c = ((char) (org.acm.seguin.parser.io.ASCII_CharStream.MASK & org.acm.seguin.parser.io.CharStream.buffer[org.acm.seguin.parser.io.CharStream.bufpos]));
        org.acm.seguin.parser.io.ASCII_CharStream.UpdateLineColumn(c);
        return c;
    }

    /**
     * Description of the Method
     *
     * @param amount
     * 		Description of Parameter
     */
    public static final void backup(int amount) {
        org.acm.seguin.parser.io.CharStream.inBuf += amount;
        if ((org.acm.seguin.parser.io.CharStream.bufpos -= amount) < 0) {
            org.acm.seguin.parser.io.CharStream.bufpos += org.acm.seguin.parser.io.CharStream.bufsize;
        }
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
        org.acm.seguin.parser.io.CharStream.inputStream = dstream;
        org.acm.seguin.parser.io.CharStream.line = startline;
        org.acm.seguin.parser.io.CharStream.column = startcolumn - 1;
        if ((org.acm.seguin.parser.io.CharStream.buffer == null) || (buffersize != org.acm.seguin.parser.io.ASCII_CharStream.buffer.length)) {
            org.acm.seguin.parser.io.CharStream.available = org.acm.seguin.parser.io.CharStream.bufsize = buffersize;
            org.acm.seguin.parser.io.CharStream.buffer = new char[buffersize];
            org.acm.seguin.parser.io.CharStream.bufline = new int[buffersize];
            org.acm.seguin.parser.io.CharStream.bufcolumn = new int[buffersize];
        }
        org.acm.seguin.parser.io.CharStream.prevCharIsLF = org.acm.seguin.parser.io.CharStream.prevCharIsCR = false;
        org.acm.seguin.parser.io.CharStream.tokenBegin = org.acm.seguin.parser.io.CharStream.inBuf = org.acm.seguin.parser.io.CharStream.maxNextCharInd = 0;
        org.acm.seguin.parser.io.CharStream.bufpos = -1;
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
        org.acm.seguin.parser.io.ASCII_CharStream.ReInit(dstream, startline, startcolumn, 4096);
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
        org.acm.seguin.parser.io.ASCII_CharStream.ReInit(new java.io.InputStreamReader(dstream), startline, startcolumn, 4096);
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
        org.acm.seguin.parser.io.ASCII_CharStream.ReInit(dstream, startline, startcolumn, 4096);
    }

    /**
     * Description of the Method
     */
    public static void Done() {
        org.acm.seguin.parser.io.CharStream.buffer = null;
        org.acm.seguin.parser.io.CharStream.bufline = null;
        org.acm.seguin.parser.io.CharStream.bufcolumn = null;
    }

    /**
     * Method to adjust line and column numbers for the start of a token. <BR>
     *
     * @param newLine
     * 		Description of Parameter
     * @param newCol
     * 		Description of Parameter
     */
    public static void adjustBeginLineColumn(int newLine, int newCol) {
        int start = org.acm.seguin.parser.io.CharStream.tokenBegin;
        int len;
        if (org.acm.seguin.parser.io.CharStream.bufpos >= org.acm.seguin.parser.io.CharStream.tokenBegin) {
            len = ((org.acm.seguin.parser.io.CharStream.bufpos - org.acm.seguin.parser.io.CharStream.tokenBegin) + org.acm.seguin.parser.io.CharStream.inBuf) + 1;
        } else {
            len = (((org.acm.seguin.parser.io.CharStream.bufsize - org.acm.seguin.parser.io.CharStream.tokenBegin) + org.acm.seguin.parser.io.CharStream.bufpos) + 1) + org.acm.seguin.parser.io.CharStream.inBuf;
        }
        int i = 0;
        int j = 0;
        int k = 0;
        int nextColDiff = 0;
        int columnDiff = 0;
        while ((i < len) && (org.acm.seguin.parser.io.CharStream.bufline[j = start % org.acm.seguin.parser.io.CharStream.bufsize] == org.acm.seguin.parser.io.CharStream.bufline[k = (++start) % org.acm.seguin.parser.io.CharStream.bufsize])) {
            org.acm.seguin.parser.io.CharStream.bufline[j] = newLine;
            nextColDiff = (columnDiff + org.acm.seguin.parser.io.CharStream.bufcolumn[k]) - org.acm.seguin.parser.io.CharStream.bufcolumn[j];
            org.acm.seguin.parser.io.CharStream.bufcolumn[j] = newCol + columnDiff;
            columnDiff = nextColDiff;
            i++;
        } 
        if (i < len) {
            org.acm.seguin.parser.io.CharStream.bufline[j] = newLine++;
            org.acm.seguin.parser.io.CharStream.bufcolumn[j] = newCol + columnDiff;
            while ((i++) < len) {
                if (org.acm.seguin.parser.io.CharStream.bufline[j = start % org.acm.seguin.parser.io.CharStream.bufsize] != org.acm.seguin.parser.io.CharStream.bufline[(++start) % org.acm.seguin.parser.io.CharStream.bufsize]) {
                    org.acm.seguin.parser.io.CharStream.bufline[j] = newLine++;
                } else {
                    org.acm.seguin.parser.io.CharStream.bufline[j] = newLine;
                }
            } 
        }
        org.acm.seguin.parser.io.CharStream.line = org.acm.seguin.parser.io.CharStream.bufline[j];
        org.acm.seguin.parser.io.CharStream.column = org.acm.seguin.parser.io.CharStream.bufcolumn[j];
    }

    /**
     * Description of the Method
     *
     * @param wrapAround
     * 		Description of Parameter
     */
    private static final void ExpandBuff(boolean wrapAround) {
        char[] newbuffer = new char[org.acm.seguin.parser.io.CharStream.bufsize + 2048];
        int newbufline[] = new int[org.acm.seguin.parser.io.CharStream.bufsize + 2048];
        int newbufcolumn[] = new int[org.acm.seguin.parser.io.CharStream.bufsize + 2048];
        try {
            if (wrapAround) {
                java.lang.System.arraycopy(org.acm.seguin.parser.io.CharStream.buffer, org.acm.seguin.parser.io.CharStream.tokenBegin, newbuffer, 0, org.acm.seguin.parser.io.CharStream.bufsize - org.acm.seguin.parser.io.CharStream.tokenBegin);
                java.lang.System.arraycopy(org.acm.seguin.parser.io.CharStream.buffer, 0, newbuffer, org.acm.seguin.parser.io.CharStream.bufsize - org.acm.seguin.parser.io.CharStream.tokenBegin, org.acm.seguin.parser.io.CharStream.bufpos);
                org.acm.seguin.parser.io.CharStream.buffer = newbuffer;
                java.lang.System.arraycopy(org.acm.seguin.parser.io.CharStream.bufline, org.acm.seguin.parser.io.CharStream.tokenBegin, newbufline, 0, org.acm.seguin.parser.io.CharStream.bufsize - org.acm.seguin.parser.io.CharStream.tokenBegin);
                java.lang.System.arraycopy(org.acm.seguin.parser.io.CharStream.bufline, 0, newbufline, org.acm.seguin.parser.io.CharStream.bufsize - org.acm.seguin.parser.io.CharStream.tokenBegin, org.acm.seguin.parser.io.CharStream.bufpos);
                org.acm.seguin.parser.io.CharStream.bufline = newbufline;
                java.lang.System.arraycopy(org.acm.seguin.parser.io.CharStream.bufcolumn, org.acm.seguin.parser.io.CharStream.tokenBegin, newbufcolumn, 0, org.acm.seguin.parser.io.CharStream.bufsize - org.acm.seguin.parser.io.CharStream.tokenBegin);
                java.lang.System.arraycopy(org.acm.seguin.parser.io.CharStream.bufcolumn, 0, newbufcolumn, org.acm.seguin.parser.io.CharStream.bufsize - org.acm.seguin.parser.io.CharStream.tokenBegin, org.acm.seguin.parser.io.CharStream.bufpos);
                org.acm.seguin.parser.io.CharStream.bufcolumn = newbufcolumn;
                org.acm.seguin.parser.io.CharStream.maxNextCharInd = org.acm.seguin.parser.io.CharStream.bufpos += org.acm.seguin.parser.io.CharStream.bufsize - org.acm.seguin.parser.io.CharStream.tokenBegin;
            } else {
                java.lang.System.arraycopy(org.acm.seguin.parser.io.CharStream.buffer, org.acm.seguin.parser.io.CharStream.tokenBegin, newbuffer, 0, org.acm.seguin.parser.io.CharStream.bufsize - org.acm.seguin.parser.io.CharStream.tokenBegin);
                org.acm.seguin.parser.io.CharStream.buffer = newbuffer;
                java.lang.System.arraycopy(org.acm.seguin.parser.io.CharStream.bufline, org.acm.seguin.parser.io.CharStream.tokenBegin, newbufline, 0, org.acm.seguin.parser.io.CharStream.bufsize - org.acm.seguin.parser.io.CharStream.tokenBegin);
                org.acm.seguin.parser.io.CharStream.bufline = newbufline;
                java.lang.System.arraycopy(org.acm.seguin.parser.io.CharStream.bufcolumn, org.acm.seguin.parser.io.CharStream.tokenBegin, newbufcolumn, 0, org.acm.seguin.parser.io.CharStream.bufsize - org.acm.seguin.parser.io.CharStream.tokenBegin);
                org.acm.seguin.parser.io.CharStream.bufcolumn = newbufcolumn;
                org.acm.seguin.parser.io.CharStream.maxNextCharInd = org.acm.seguin.parser.io.CharStream.bufpos -= org.acm.seguin.parser.io.CharStream.tokenBegin;
            }
        } catch (java.lang.Throwable t) {
            throw new java.lang.Error(t.getMessage());
        }
        org.acm.seguin.parser.io.CharStream.bufsize += 2048;
        org.acm.seguin.parser.io.CharStream.available = org.acm.seguin.parser.io.CharStream.bufsize;
        org.acm.seguin.parser.io.CharStream.tokenBegin = 0;
    }

    /**
     * Description of the Method
     *
     * @exception java.io.IOException
     * 		Description of Exception
     */
    private static final void FillBuff() throws java.io.IOException {
        if (org.acm.seguin.parser.io.CharStream.maxNextCharInd == org.acm.seguin.parser.io.CharStream.available) {
            if (org.acm.seguin.parser.io.CharStream.available == org.acm.seguin.parser.io.CharStream.bufsize) {
                if (org.acm.seguin.parser.io.CharStream.tokenBegin > 2048) {
                    org.acm.seguin.parser.io.CharStream.bufpos = org.acm.seguin.parser.io.CharStream.maxNextCharInd = 0;
                    org.acm.seguin.parser.io.CharStream.available = org.acm.seguin.parser.io.CharStream.tokenBegin;
                } else if (org.acm.seguin.parser.io.CharStream.tokenBegin < 0) {
                    org.acm.seguin.parser.io.CharStream.bufpos = org.acm.seguin.parser.io.CharStream.maxNextCharInd = 0;
                } else {
                    org.acm.seguin.parser.io.ASCII_CharStream.ExpandBuff(false);
                }
            } else if (org.acm.seguin.parser.io.CharStream.available > org.acm.seguin.parser.io.CharStream.tokenBegin) {
                org.acm.seguin.parser.io.CharStream.available = org.acm.seguin.parser.io.CharStream.bufsize;
            } else if ((org.acm.seguin.parser.io.CharStream.tokenBegin - org.acm.seguin.parser.io.CharStream.available) < 2048) {
                org.acm.seguin.parser.io.ASCII_CharStream.ExpandBuff(true);
            } else {
                org.acm.seguin.parser.io.CharStream.available = org.acm.seguin.parser.io.CharStream.tokenBegin;
            }
        }
        int i;
        try {
            if ((i = org.acm.seguin.parser.io.CharStream.inputStream.read(org.acm.seguin.parser.io.CharStream.buffer, org.acm.seguin.parser.io.CharStream.maxNextCharInd, org.acm.seguin.parser.io.CharStream.available - org.acm.seguin.parser.io.CharStream.maxNextCharInd)) == (-1)) {
                org.acm.seguin.parser.io.CharStream.inputStream.close();
                throw new java.io.IOException();
            } else {
                org.acm.seguin.parser.io.CharStream.maxNextCharInd += i;
            }
            return;
        } catch (java.io.IOException e) {
            --org.acm.seguin.parser.io.CharStream.bufpos;
            org.acm.seguin.parser.io.ASCII_CharStream.backup(0);
            if (org.acm.seguin.parser.io.CharStream.tokenBegin == (-1)) {
                org.acm.seguin.parser.io.CharStream.tokenBegin = org.acm.seguin.parser.io.CharStream.bufpos;
            }
            throw e;
        }
    }

    /**
     * Description of the Method
     *
     * @param c
     * 		Description of Parameter
     */
    private static final void UpdateLineColumn(char c) {
        org.acm.seguin.parser.io.CharStream.column++;
        if (org.acm.seguin.parser.io.CharStream.prevCharIsLF) {
            org.acm.seguin.parser.io.CharStream.prevCharIsLF = false;
            org.acm.seguin.parser.io.CharStream.line += org.acm.seguin.parser.io.CharStream.column = 1;
        } else if (org.acm.seguin.parser.io.CharStream.prevCharIsCR) {
            org.acm.seguin.parser.io.CharStream.prevCharIsCR = false;
            if (c == '\n') {
                org.acm.seguin.parser.io.CharStream.prevCharIsLF = true;
            } else {
                org.acm.seguin.parser.io.CharStream.line += org.acm.seguin.parser.io.CharStream.column = 1;
            }
        }
        switch (c) {
            case '\r' :
                org.acm.seguin.parser.io.CharStream.prevCharIsCR = true;
                break;
            case '\n' :
                org.acm.seguin.parser.io.CharStream.prevCharIsLF = true;
                break;
            case '\t' :
                org.acm.seguin.parser.io.CharStream.column--;
                org.acm.seguin.parser.io.CharStream.column += 8 - (org.acm.seguin.parser.io.CharStream.column & 07);
                break;
            default :
                break;
        }
        org.acm.seguin.parser.io.CharStream.bufline[org.acm.seguin.parser.io.CharStream.bufpos] = org.acm.seguin.parser.io.CharStream.line;
        org.acm.seguin.parser.io.CharStream.bufcolumn[org.acm.seguin.parser.io.CharStream.bufpos] = org.acm.seguin.parser.io.CharStream.column;
    }
}