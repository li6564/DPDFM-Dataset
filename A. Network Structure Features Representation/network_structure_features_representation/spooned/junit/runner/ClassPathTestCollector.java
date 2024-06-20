package junit.runner;
/**
 * An implementation of a TestCollector that consults the
 * class path. It considers all classes on the class path
 * excluding classes in JARs. It leaves it up to subclasses
 * to decide whether a class is a runnable Test.
 *
 * @see TestCollector
 */
public abstract class ClassPathTestCollector implements junit.runner.TestCollector {
    static final int SUFFIX_LENGTH = ".class".length();

    public ClassPathTestCollector() {
    }

    public java.util.Enumeration collectTests() {
        java.lang.String classPath = java.lang.System.getProperty("java.class.path");
        java.lang.String separator = java.lang.System.getProperty("path.separator");
        java.util.Hashtable result = new java.util.Hashtable(100);
        collectFilesInRoots(splitClassPath(classPath, separator), result);
        return result.elements();
    }

    void collectFilesInRoots(java.util.Vector roots, java.util.Hashtable result) {
        java.util.Enumeration e = roots.elements();
        while (e.hasMoreElements())
            gatherFiles(new java.io.File(((java.lang.String) (e.nextElement()))), "", result);

    }

    void gatherFiles(java.io.File classRoot, java.lang.String classFileName, java.util.Hashtable result) {
        java.io.File thisRoot = new java.io.File(classRoot, classFileName);
        if (thisRoot.isFile()) {
            if (isTestClass(classFileName)) {
                java.lang.String className = classNameFromFile(classFileName);
                result.put(className, className);
            }
            return;
        }
        java.lang.String[] contents = thisRoot.list();
        if (contents != null) {
            for (int i = 0; i < contents.length; i++)
                gatherFiles(classRoot, (classFileName + java.io.File.separatorChar) + contents[i], result);

        }
    }

    java.util.Vector splitClassPath(java.lang.String classPath, java.lang.String separator) {
        java.util.Vector result = new java.util.Vector();
        java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(classPath, separator);
        while (tokenizer.hasMoreTokens())
            result.addElement(tokenizer.nextToken());

        return result;
    }

    protected boolean isTestClass(java.lang.String classFileName) {
        return (classFileName.endsWith(".class") && (classFileName.indexOf('$') < 0)) && (classFileName.indexOf("Test") > 0);
    }

    protected java.lang.String classNameFromFile(java.lang.String classFileName) {
        // convert /a/b.class to a.b
        java.lang.String s = classFileName.substring(0, classFileName.length() - junit.runner.ClassPathTestCollector.SUFFIX_LENGTH);
        java.lang.String s2 = s.replace(java.io.File.separatorChar, '.');
        if (s2.startsWith("."))
            return s2.substring(1);

        return s2;
    }
}