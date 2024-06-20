package junit.runner;
/**
 * A custom class loader which enables the reloading
 * of classes for each test run. The class loader
 * can be configured with a list of package paths that
 * should be excluded from loading. The loading
 * of these packages is delegated to the system class
 * loader. They will be shared across test runs.
 * <p>
 * The list of excluded package paths is specified in
 * a properties file "excluded.properties" that is located in
 * the same place as the TestCaseClassLoader class.
 * <p>
 * <b>Known limitation:</b> the TestCaseClassLoader cannot load classes
 * from jar files.
 */
public class TestCaseClassLoader extends java.lang.ClassLoader {
    /**
     * scanned class path
     */
    private java.util.Vector fPathItems;

    /**
     * default excluded paths
     */
    private java.lang.String[] defaultExclusions = new java.lang.String[]{ "junit.framework.", "junit.extensions.", "junit.runner." };

    /**
     * name of excluded properties file
     */
    static final java.lang.String EXCLUDED_FILE = "excluded.properties";

    /**
     * excluded paths
     */
    private java.util.Vector fExcluded;

    /**
     * Constructs a TestCaseLoader. It scans the class path
     * and the excluded package paths
     */
    public TestCaseClassLoader() {
        this(java.lang.System.getProperty("java.class.path"));
    }

    /**
     * Constructs a TestCaseLoader. It scans the class path
     * and the excluded package paths
     */
    public TestCaseClassLoader(java.lang.String classPath) {
        scanPath(classPath);
        readExcludedPackages();
    }

    private void scanPath(java.lang.String classPath) {
        java.lang.String separator = java.lang.System.getProperty("path.separator");
        fPathItems = new java.util.Vector(10);
        java.util.StringTokenizer st = new java.util.StringTokenizer(classPath, separator);
        while (st.hasMoreTokens()) {
            fPathItems.addElement(st.nextToken());
        } 
    }

    public java.net.URL getResource(java.lang.String name) {
        return java.lang.ClassLoader.getSystemResource(name);
    }

    public java.io.InputStream getResourceAsStream(java.lang.String name) {
        return java.lang.ClassLoader.getSystemResourceAsStream(name);
    }

    public boolean isExcluded(java.lang.String name) {
        for (int i = 0; i < fExcluded.size(); i++) {
            if (name.startsWith(((java.lang.String) (fExcluded.elementAt(i))))) {
                return true;
            }
        }
        return false;
    }

    public synchronized java.lang.Class loadClass(java.lang.String name, boolean resolve) throws java.lang.ClassNotFoundException {
        java.lang.Class c = findLoadedClass(name);
        if (c != null)
            return c;

        // 
        // Delegate the loading of excluded classes to the
        // standard class loader.
        // 
        if (isExcluded(name)) {
            try {
                c = findSystemClass(name);
                return c;
            } catch (java.lang.ClassNotFoundException e) {
                // keep searching
            }
        }
        if (c == null) {
            byte[] data = lookupClassData(name);
            if (data == null)
                throw new java.lang.ClassNotFoundException();

            c = defineClass(name, data, 0, data.length);
        }
        if (resolve)
            resolveClass(c);

        return c;
    }

    private byte[] lookupClassData(java.lang.String className) throws java.lang.ClassNotFoundException {
        byte[] data = null;
        for (int i = 0; i < fPathItems.size(); i++) {
            java.lang.String path = ((java.lang.String) (fPathItems.elementAt(i)));
            java.lang.String fileName = className.replace('.', '/') + ".class";
            if (isJar(path)) {
                data = loadJarData(path, fileName);
            } else {
                data = loadFileData(path, fileName);
            }
            if (data != null)
                return data;

        }
        throw new java.lang.ClassNotFoundException(className);
    }

    boolean isJar(java.lang.String pathEntry) {
        return pathEntry.endsWith(".jar") || pathEntry.endsWith(".zip");
    }

    private byte[] loadFileData(java.lang.String path, java.lang.String fileName) {
        java.io.File file = new java.io.File(path, fileName);
        if (file.exists()) {
            return getClassData(file);
        }
        return null;
    }

    private byte[] getClassData(java.io.File f) {
        try {
            java.io.FileInputStream stream = new java.io.FileInputStream(f);
            java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != (-1))
                out.write(b, 0, n);

            stream.close();
            out.close();
            return out.toByteArray();
        } catch (java.io.IOException e) {
        }
        return null;
    }

    private byte[] loadJarData(java.lang.String path, java.lang.String fileName) {
        java.util.zip.ZipFile zipFile = null;
        java.io.InputStream stream = null;
        java.io.File archive = new java.io.File(path);
        if (!archive.exists())
            return null;

        try {
            zipFile = new java.util.zip.ZipFile(archive);
        } catch (java.io.IOException io) {
            return null;
        }
        java.util.zip.ZipEntry entry = zipFile.getEntry(fileName);
        if (entry == null)
            return null;

        int size = ((int) (entry.getSize()));
        try {
            stream = zipFile.getInputStream(entry);
            byte[] data = new byte[size];
            int pos = 0;
            while (pos < size) {
                int n = stream.read(data, pos, data.length - pos);
                pos += n;
            } 
            zipFile.close();
            return data;
        } catch (java.io.IOException e) {
        } finally {
            try {
                if (stream != null)
                    stream.close();

            } catch (java.io.IOException e) {
            }
        }
        return null;
    }

    private void readExcludedPackages() {
        fExcluded = new java.util.Vector(10);
        for (int i = 0; i < defaultExclusions.length; i++)
            fExcluded.addElement(defaultExclusions[i]);

        java.io.InputStream is = getClass().getResourceAsStream(junit.runner.TestCaseClassLoader.EXCLUDED_FILE);
        if (is == null)
            return;

        java.util.Properties p = new java.util.Properties();
        try {
            p.load(is);
        } catch (java.io.IOException e) {
            return;
        } finally {
            try {
                is.close();
            } catch (java.io.IOException e) {
            }
        }
        for (java.util.Enumeration e = p.propertyNames(); e.hasMoreElements();) {
            java.lang.String key = ((java.lang.String) (e.nextElement()));
            if (key.startsWith("excluded.")) {
                java.lang.String path = p.getProperty(key);
                path = path.trim();
                if (path.endsWith("*"))
                    path = path.substring(0, path.length() - 1);

                if (path.length() > 0)
                    fExcluded.addElement(path);

            }
        }
    }
}