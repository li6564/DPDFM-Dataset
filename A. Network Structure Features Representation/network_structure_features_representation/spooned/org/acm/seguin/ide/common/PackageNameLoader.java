package org.acm.seguin.ide.common;
/**
 * Loads the name of the package from the package.uml file
 *
 * @author Chris Seguin
 */
public class PackageNameLoader {
    /**
     * Loads the package name from a package.uml file
     *
     * @param filename
     * 		the name of the file
     * @return the package name
     */
    public java.lang.String load(java.lang.String filename) {
        java.lang.String packageName = "Unknown";
        try {
            java.io.BufferedReader input = new java.io.BufferedReader(new java.io.FileReader(filename));
            java.lang.String line = input.readLine();
            packageName = parseLine(line);
            input.close();
        } catch (java.io.IOException ioe) {
        }
        return packageName;
    }

    /**
     * Parses the line
     *
     * @param line
     * 		the line to parse
     * @return the package name
     */
    private java.lang.String parseLine(java.lang.String line) {
        if (line.charAt(0) == 'V') {
            java.util.StringTokenizer tok = new java.util.StringTokenizer(line, "[:]");
            if (tok.hasMoreTokens()) {
                // Skip the first - it is the letter v
                tok.nextToken();
                if (tok.hasMoreTokens()) {
                    // Skip the second - it is the version (1.1)
                    tok.nextToken();
                    if (tok.hasMoreTokens()) {
                        // Third item is the package name
                        return tok.nextToken();
                    }
                }
            }
        }
        return "Unknown";
    }
}