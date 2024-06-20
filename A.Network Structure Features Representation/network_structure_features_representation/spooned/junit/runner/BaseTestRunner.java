package junit.runner;
/**
 * Base class for all test runners.
 * This class was born live on stage in Sardinia during XP2000.
 */
public abstract class BaseTestRunner implements junit.framework.TestListener {
    public static final java.lang.String SUITE_METHODNAME = "suite";

    static java.util.Properties fPreferences;

    static int fgMaxMessageLength = 500;

    static boolean fgFilterStack = true;

    boolean fLoading = true;

    /**
     * Returns the Test corresponding to the given suite. This is
     * a template method, subclasses override runFailed(), clearStatus().
     */
    public junit.framework.Test getTest(java.lang.String suiteClassName) {
        if (suiteClassName.length() <= 0) {
            clearStatus();
            return null;
        }
        java.lang.Class testClass = null;
        try {
            testClass = loadSuiteClass(suiteClassName);
        } catch (java.lang.ClassNotFoundException e) {
            java.lang.String clazz = e.getMessage();
            if (clazz == null)
                clazz = suiteClassName;

            runFailed(("Class not found \"" + clazz) + "\"");
            return null;
        } catch (java.lang.Exception e) {
            runFailed("Error: " + e.toString());
            return null;
        }
        java.lang.reflect.Method suiteMethod = null;
        try {
            suiteMethod = testClass.getMethod(junit.runner.BaseTestRunner.SUITE_METHODNAME, new java.lang.Class[0]);
        } catch (java.lang.Exception e) {
            // try to extract a test suite automatically
            clearStatus();
            return new junit.framework.TestSuite(testClass);
        }
        junit.framework.Test test = null;
        try {
            test = ((junit.framework.Test) (suiteMethod.invoke(null, new java.lang.Class[0])));// static method

            if (test == null)
                return test;

        } catch (java.lang.reflect.InvocationTargetException e) {
            runFailed("Failed to invoke suite():" + e.getTargetException().toString());
            return null;
        } catch (java.lang.IllegalAccessException e) {
            runFailed("Failed to invoke suite():" + e.toString());
            return null;
        }
        clearStatus();
        return test;
    }

    /**
     * Returns the formatted string of the elapsed time.
     */
    public java.lang.String elapsedTimeAsString(long runTime) {
        return java.text.NumberFormat.getInstance().format(((double) (runTime)) / 1000);
    }

    /**
     * Processes the command line arguments and
     * returns the name of the suite class to run or null
     */
    protected java.lang.String processArguments(java.lang.String[] args) {
        java.lang.String suiteName = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-noloading")) {
                setLoading(false);
            } else if (args[i].equals("-nofilterstack")) {
                junit.runner.BaseTestRunner.fgFilterStack = false;
            } else if (args[i].equals("-c")) {
                if (args.length > (i + 1))
                    suiteName = extractClassName(args[i + 1]);
                else
                    java.lang.System.out.println("Missing Test class name");

                i++;
            } else {
                suiteName = args[i];
            }
        }
        return suiteName;
    }

    /**
     * Sets the loading behaviour of the test runner
     */
    public void setLoading(boolean enable) {
        fLoading = enable;
    }

    /**
     * Extract the class name from a String in VA/Java style
     */
    public java.lang.String extractClassName(java.lang.String className) {
        if (className.startsWith("Default package for"))
            return className.substring(className.lastIndexOf(".") + 1);

        return className;
    }

    /**
     * Truncates a String to the maximum length.
     */
    public static java.lang.String truncate(java.lang.String s) {
        if ((junit.runner.BaseTestRunner.fgMaxMessageLength != (-1)) && (s.length() > junit.runner.BaseTestRunner.fgMaxMessageLength))
            s = s.substring(0, junit.runner.BaseTestRunner.fgMaxMessageLength) + "...";

        return s;
    }

    /**
     * Override to define how to handle a failed loading of
     * a test suite.
     */
    protected abstract void runFailed(java.lang.String message);

    /**
     * Returns the loaded Class for a suite name.
     */
    protected java.lang.Class loadSuiteClass(java.lang.String suiteClassName) throws java.lang.ClassNotFoundException {
        return getLoader().load(suiteClassName);
    }

    /**
     * Clears the status message.
     */
    protected void clearStatus() {
        // Belongs in the GUI TestRunner class
    }

    /**
     * Returns the loader to be used.
     */
    public junit.runner.TestSuiteLoader getLoader() {
        if (useReloadingTestSuiteLoader())
            return new junit.runner.ReloadingTestSuiteLoader();

        return new junit.runner.StandardTestSuiteLoader();
    }

    protected boolean useReloadingTestSuiteLoader() {
        return (junit.runner.BaseTestRunner.getPreference("loading").equals("true") && (!junit.runner.BaseTestRunner.inVAJava())) && fLoading;
    }

    private static java.io.File getPreferencesFile() {
        java.lang.String home = java.lang.System.getProperty("user.home");
        return new java.io.File(home, "junit.properties");
    }

    private static void readPreferences() {
        java.io.InputStream is = null;
        try {
            is = new java.io.FileInputStream(junit.runner.BaseTestRunner.getPreferencesFile());
            junit.runner.BaseTestRunner.fPreferences = new java.util.Properties(junit.runner.BaseTestRunner.fPreferences);
            junit.runner.BaseTestRunner.fPreferences.load(is);
        } catch (java.io.IOException e) {
            try {
                if (is != null)
                    is.close();

            } catch (java.io.IOException e1) {
            }
        }
    }

    public static java.lang.String getPreference(java.lang.String key) {
        return junit.runner.BaseTestRunner.fPreferences.getProperty(key);
    }

    public static int getPreference(java.lang.String key, int dflt) {
        java.lang.String value = junit.runner.BaseTestRunner.getPreference(key);
        int intValue = dflt;
        if (value == null)
            return intValue;

        try {
            intValue = java.lang.Integer.parseInt(value);
        } catch (java.lang.NumberFormatException ne) {
        }
        return intValue;
    }

    public static boolean inVAJava() {
        try {
            java.lang.Class.forName("com.ibm.uvm.tools.DebugSupport");
        } catch (java.lang.Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Returns a filtered stack trace
     */
    public static java.lang.String getFilteredTrace(java.lang.Throwable t) {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        java.io.PrintWriter writer = new java.io.PrintWriter(stringWriter);
        t.printStackTrace(writer);
        java.lang.StringBuffer buffer = stringWriter.getBuffer();
        java.lang.String trace = buffer.toString();
        return junit.runner.BaseTestRunner.filterStack(trace);
    }

    /**
     * Filters stack frames from internal JUnit classes
     */
    public static java.lang.String filterStack(java.lang.String stack) {
        if ((!junit.runner.BaseTestRunner.getPreference("filterstack").equals("true")) || (junit.runner.BaseTestRunner.fgFilterStack == false))
            return stack;

        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        java.io.StringReader sr = new java.io.StringReader(stack);
        java.io.BufferedReader br = new java.io.BufferedReader(sr);
        java.lang.String line;
        try {
            while ((line = br.readLine()) != null) {
                if (!junit.runner.BaseTestRunner.filterLine(line))
                    pw.println(line);

            } 
        } catch (java.lang.Exception IOException) {
            return stack;// return the stack unfiltered

        }
        return sw.toString();
    }

    static boolean filterLine(java.lang.String line) {
        java.lang.String[] patterns = new java.lang.String[]{ "junit.framework.TestCase", "junit.framework.TestResult", "junit.framework.TestSuite", "junit.framework.Assert."// don't filter AssertionFailure
        , "junit.swingui.TestRunner", "junit.awtui.TestRunner", "junit.textui.TestRunner", "java.lang.reflect.Method.invoke(" };
        for (int i = 0; i < patterns.length; i++) {
            if (line.indexOf(patterns[i]) > 0)
                return true;

        }
        return false;
    }

    {
        fPreferences = new java.util.Properties();
        // JDK 1.2 feature
        // fPreferences.setProperty("loading", "true");
        fPreferences.put("loading", "true");
        fPreferences.put("filterstack", "true");
        readPreferences();
        fgMaxMessageLength = getPreference("maxmessage", fgMaxMessageLength);
    }
}