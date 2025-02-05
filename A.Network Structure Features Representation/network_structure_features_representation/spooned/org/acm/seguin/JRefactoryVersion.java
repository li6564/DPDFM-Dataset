package org.acm.seguin;
/**
 * The current version of JRefactory
 *
 * @author Chris Seguin
 */
public class JRefactoryVersion {
    /**
     * Gets the MajorVersion attribute of the JRefactoryVersion object
     *
     * @return The MajorVersion value
     */
    public int getMajorVersion() {
        return 2;
    }

    /**
     * Gets the MinorVersion attribute of the JRefactoryVersion object
     *
     * @return The MinorVersion value
     */
    public int getMinorVersion() {
        return 6;
    }

    /**
     * Gets the Build attribute of the JRefactoryVersion object
     *
     * @return The Build value
     */
    public int getBuild() {
        return 24;
    }

    /**
     * Converts the JRefactoryVersion to a string
     *
     * @return a string representing the version
     */
    public java.lang.String toString() {
        java.lang.StringBuffer buffer = new java.lang.StringBuffer();
        buffer.append(getMajorVersion());
        buffer.append('.');
        buffer.append(getMinorVersion());
        buffer.append('.');
        buffer.append(getBuild());
        return buffer.toString();
    }

    public static void main(java.lang.String[] args) {
        java.lang.System.out.println("Version:  " + new org.acm.seguin.JRefactoryVersion().toString());
    }
}