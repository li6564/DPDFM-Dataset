package com.jmonkey.export;
public class Format {
    private static char[] lowercases = new char[]{ '\u0000', '\u0001', '\u0002', '\u0003', '\u0004', '\u0005', '\u0006', '\u0007', '\b', '\t', '\n', '\u000b', '\f', '\r', '\u000e', '\u000f', '\u0010', '\u0011', '\u0012', '\u0013', '\u0014', '\u0015', '\u0016', '\u0017', '\u0018', '\u0019', '\u001a', '\u001b', '\u001c', '\u001d', '\u001e', '\u001f', ' ', '!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '[', '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', '\u007f' };

    public static final char UNIVERSAL_SEPARATOR_CHAR = '/';

    private Format() {
        super();
    }

    public static java.lang.String asciiToLowerCase(java.lang.String s) {
        char[] c = s.toCharArray();
        for (int i = c.length; (i--) > 0;) {
            if (c[i] <= 127)
                c[i] = com.jmonkey.export.Format.lowercases[c[i]];

        }
        return new java.lang.String(c);
    }

    public static final java.lang.String colorToHex(java.awt.Color colour) {
        java.lang.String colorstr = new java.lang.String("#");
        java.lang.String str = java.lang.Integer.toHexString(colour.getRed());
        if (str.length() > 2)
            throw new java.lang.Error("invalid red value");
        else if (str.length() < 2)
            colorstr += "0" + str;
        else
            colorstr += str;

        str = java.lang.Integer.toHexString(colour.getGreen());
        if (str.length() > 2)
            throw new java.lang.Error("invalid green value");
        else if (str.length() < 2)
            colorstr += "0" + str;
        else
            colorstr += str;

        str = java.lang.Integer.toHexString(colour.getBlue());
        if (str.length() > 2)
            throw new java.lang.Error("invalid green value");
        else if (str.length() < 2)
            colorstr += "0" + str;
        else
            colorstr += str;

        return colorstr.toUpperCase();
    }

    public static final java.lang.String escapeSafe(java.lang.String s) {
        java.lang.StringBuffer b = new java.lang.StringBuffer();
        int counter = 0;
        for (counter = 0; counter < s.length(); counter++) {
            if (s.charAt(counter) == '\\') {
                b.append("\\\\");
            } else {
                b.append(s.charAt(counter));
            }
        }
        return b.toString();
    }

    public static final java.lang.String hashToHex(byte[] hash) {
        java.lang.StringBuffer buf = new java.lang.StringBuffer(hash.length * 2);
        for (int i = 0; i < hash.length; i++) {
            if ((((int) (hash[i])) & 0xff) < 0x10)
                buf.append("0");

            buf.append(java.lang.Integer.toHexString(((int) (hash[i])) & 0xff));
        }
        return buf.toString();
    }

    public static final java.awt.Color hexToColor(java.lang.String value) {
        if (value.length() != 7) {
            throw new java.lang.Error("invalid hex color string length");
        } else if (value.startsWith("#")) {
            java.awt.Color c = java.awt.Color.decode(value);
            return c;
        }
        return null;
    }

    public static final int hexToInt(java.lang.String hex) {
        if (hex.length() != 2)
            throw new java.lang.Error("invalid hex string " + hex);

        int pos1 = java.lang.Character.digit(hex.charAt(0), 16) * 16;
        int pos0 = java.lang.Character.digit(hex.charAt(1), 16);
        return pos0 + pos1;
    }

    public static final java.lang.String keepChars(java.lang.String input, java.lang.String wantedChars) {
        char[] cArr = new char[input.length()];
        char curChar = ' ';
        int ox = 0;
        for (int n = 0; n < input.length(); n++) {
            curChar = input.charAt(n);
            if (wantedChars.indexOf(curChar) >= 0) {
                cArr[ox] = curChar;
                ox++;
            }
        }
        return new java.lang.String(cArr, 0, ox);
    }

    public static final java.lang.String nativePath(java.lang.String universalPath) {
        return universalPath.replace('/', java.io.File.separatorChar);
    }

    public static final java.lang.String removeChars(java.lang.String input, java.lang.String remChars) {
        char[] cArr = new char[input.length()];
        char curChar = ' ';
        int ox = 0;
        for (int n = 0; n < input.length(); n++) {
            curChar = input.charAt(n);
            if (remChars.indexOf(curChar) < 0) {
                cArr[ox] = curChar;
                ox++;
            }
        }
        return new java.lang.String(cArr, 0, ox);
    }

    public static final java.lang.String removeSurroundingQuotes(java.lang.String s) {
        java.lang.String result = new java.lang.String(s.trim());
        if (s.startsWith("\"") && s.endsWith("\"")) {
            result = new java.lang.String(s.substring(1, s.length() - 1));
        }
        return result;
    }

    public static java.lang.String replace(java.lang.String s, java.lang.String sub, java.lang.String with) {
        java.lang.StringBuffer buf = new java.lang.StringBuffer(s.length() * 2);
        int c = 0;
        int i = 0;
        while ((i = s.indexOf(sub, c)) != (-1)) {
            buf.append(s.substring(c, i));
            buf.append(with);
            c = i + sub.length();
        } 
        if (c < s.length())
            buf.append(s.substring(c, s.length()));

        return buf.toString();
    }

    public static final java.lang.String systemPath(java.lang.String universalPath) {
        return com.jmonkey.export.Format.nativePath(universalPath);
    }

    public static final java.lang.String universalPath(java.lang.String nativePath) {
        java.lang.String newPath = null;
        int index = nativePath.indexOf(java.io.File.separator);
        if ((index >= 0) && ((index + 1) < nativePath.length())) {
            newPath = nativePath.substring(index + 1, nativePath.length());
        } else {
            newPath = nativePath;
        }
        return newPath.replace(java.io.File.separatorChar, '/');
    }
}