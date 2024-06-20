package com.jmonkey.office.lexi.support;
/**
 * this class should diea ASAP.
 *
 * @author:  */
public final class Mime {
    private static com.jmonkey.export.Registry _REGISTRY = null;

    /**
     * Don't allow instances to be created.
     */
    private Mime() {
    }

    /**
     * Add an association of extension to mimetype.
     *
     * @param contentType
     * 		java.lang.String
     * @param fileExtension
     * 		java.lang.String
     */
    public static final void addTypeForExtension(java.lang.String contentType, java.lang.String fileExtension) {
        com.jmonkey.office.lexi.support.Mime.ensureProperties();
        // Code.debug("Adding mime type for extension: " + fileExtension.trim().toLowerCase());
        com.jmonkey.office.lexi.support.Mime.getRegistry().setProperty("extensions", fileExtension.trim().toLowerCase(), contentType.trim().toLowerCase());
        try {
            com.jmonkey.office.lexi.support.Mime.getRegistry().commit();
        } catch (java.io.IOException ioe1) {
            throw new java.lang.RuntimeException("Registry save faild. Mime unable to add association.");
        }
    }

    private static final void ensureProperties() {
        if (!com.jmonkey.office.lexi.support.Mime.getRegistry().isGroup("extensions")) {
            com.jmonkey.office.lexi.support.Mime.getRegistry().setProperty("extensions", "htm", "text/html");
            com.jmonkey.office.lexi.support.Mime.getRegistry().setProperty("extensions", "html", "text/html");
            com.jmonkey.office.lexi.support.Mime.getRegistry().setProperty("extensions", "shtml", "text/html");
            com.jmonkey.office.lexi.support.Mime.getRegistry().setProperty("extensions", "java", "text/plain");
            com.jmonkey.office.lexi.support.Mime.getRegistry().setProperty("extensions", "c", "text/plain");
            com.jmonkey.office.lexi.support.Mime.getRegistry().setProperty("extensions", "cc", "text/plain");
            com.jmonkey.office.lexi.support.Mime.getRegistry().setProperty("extensions", "cpp", "text/plain");
            com.jmonkey.office.lexi.support.Mime.getRegistry().setProperty("extensions", "h", "text/plain");
            com.jmonkey.office.lexi.support.Mime.getRegistry().setProperty("extensions", "txt", "text/plain");
            com.jmonkey.office.lexi.support.Mime.getRegistry().setProperty("extensions", "text", "text/plain");
            com.jmonkey.office.lexi.support.Mime.getRegistry().setProperty("extensions", "rtf", "text/rtf");
            // getRegistry()
            try {
                com.jmonkey.office.lexi.support.Mime.getRegistry().commit();
            } catch (java.io.IOException ioe1) {
                throw new java.lang.RuntimeException("Mime unable to ensure extension properties exist.");
            }
        }
    }

    /**
     * This method forcable tries do find out
     * the content type of a particular file.
     * If unable to do so, it return content/unknown.
     * <P>
     * The first step is to check the extension.
     * Other possible ways are to read the content header,
     * and try to  determin it that way, however that is
     * not implemented at this time.
     */
    public static final java.lang.String findContentType(java.io.File file) {
        try {
            com.jmonkey.office.lexi.support.Mime.ensureProperties();
            if (file != null) {
                java.lang.String extn = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
                return com.jmonkey.office.lexi.support.Mime.findContentType(extn);
            } else {
                return "content/unknown";
            }
        } catch (java.lang.StringIndexOutOfBoundsException sioobe0) {
            return "content/unknown";
        }
    }

    /**
     * This method forcable tries do find out
     * the content type of a particular extension string.
     * If unable to do so, it return content/unknown.
     * <P>
     * The first step is to check the extension.
     * Other possible ways are to read the content header,
     * and try to  determin it that way, however that is
     * not implemented at this time.
     */
    public static final java.lang.String findContentType(java.lang.String extension) {
        com.jmonkey.office.lexi.support.Mime.ensureProperties();
        // Code.debug("Checking mime type for extension: " + extension);
        return com.jmonkey.office.lexi.support.Mime.getRegistry().getString("extensions", extension, "content/unknown");
    }

    /**
     * Gets our option registry
     */
    protected static final com.jmonkey.export.Registry getRegistry() {
        if (com.jmonkey.office.lexi.support.Mime._REGISTRY == null) {
            try {
                com.jmonkey.office.lexi.support.Mime._REGISTRY = com.jmonkey.export.Registry.loadForClass(com.jmonkey.office.lexi.support.Mime.class);
            } catch (java.io.IOException ioe0) {
                java.lang.System.err.println(ioe0.toString());
                // ioe0.printStackTrace(System.err);
                // Code.failed(ioe0);
            }
        }
        return com.jmonkey.office.lexi.support.Mime._REGISTRY;
    }
}