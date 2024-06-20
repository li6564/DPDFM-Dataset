package junit.textui;
/**
 * A command line based tool to run tests.
 * <pre>
 * java junit.textui.TestRunner [-wait] TestCaseClass
 * </pre>
 * TestRunner expects the name of a TestCase class as argument.
 * If this class defines a static <code>suite</code> method it
 * will be invoked and the returned test is run. Otherwise all
 * the methods starting with "test" having no arguments are run.
 * <p>
 * When the wait command line argument is given TestRunner
 * waits until the users types RETURN.
 * <p>
 * TestRunner prints a trace as the tests are executed followed by a
 * summary at the end.
 */
public class TestRunner extends junit.runner.BaseTestRunner {
    java.io.PrintStream fWriter = java.lang.System.out;

    int fColumn = 0;

    /**
     * Constructs a TestRunner.
     */
    public TestRunner() {
    }

    /**
     * Constructs a TestRunner using the given stream for all the output
     */
    public TestRunner(java.io.PrintStream writer) {
        this();
        if (writer == null)
            throw new java.lang.IllegalArgumentException("Writer can't be null");

        fWriter = writer;
    }

    /**
     * Always use the StandardTestSuiteLoader. Overridden from
     * BaseTestRunner.
     */
    public junit.runner.TestSuiteLoader getLoader() {
        return new junit.runner.StandardTestSuiteLoader();
    }

    public synchronized void addError(junit.framework.Test test, java.lang.Throwable t) {
        writer().print("E");
    }

    public synchronized void addFailure(junit.framework.Test test, junit.framework.AssertionFailedError t) {
        writer().print("F");
    }

    /**
     * Creates the TestResult to be used for the test run.
     */
    protected junit.framework.TestResult createTestResult() {
        return new junit.framework.TestResult();
    }

    public junit.framework.TestResult doRun(junit.framework.Test suite, boolean wait) {
        junit.framework.TestResult result = createTestResult();
        result.addListener(this);
        long startTime = java.lang.System.currentTimeMillis();
        suite.run(result);
        long endTime = java.lang.System.currentTimeMillis();
        long runTime = endTime - startTime;
        writer().println();
        writer().println("Time: " + elapsedTimeAsString(runTime));
        print(result);
        writer().println();
        pause(wait);
        return result;
    }

    protected void pause(boolean wait) {
        if (wait) {
            writer().println("<RETURN> to continue");
            try {
                java.lang.System.in.read();
            } catch (java.lang.Exception e) {
            }
        }
    }

    public synchronized void startTest(junit.framework.Test test) {
        writer().print(".");
        if ((fColumn++) >= 40) {
            writer().println();
            fColumn = 0;
        }
    }

    public void endTest(junit.framework.Test test) {
    }

    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner aTestRunner = new junit.textui.TestRunner();
        try {
            junit.framework.TestResult r = aTestRunner.start(args);
            if (!r.wasSuccessful())
                java.lang.System.exit(-1);

            java.lang.System.exit(0);
        } catch (java.lang.Exception e) {
            java.lang.System.err.println(e.getMessage());
            java.lang.System.exit(-2);
        }
    }

    /**
     * Prints failures to the standard output
     */
    public synchronized void print(junit.framework.TestResult result) {
        printErrors(result);
        printFailures(result);
        printHeader(result);
    }

    /**
     * Prints the errors to the standard output
     */
    public void printErrors(junit.framework.TestResult result) {
        if (result.errorCount() != 0) {
            if (result.errorCount() == 1)
                writer().println(("There was " + result.errorCount()) + " error:");
            else
                writer().println(("There were " + result.errorCount()) + " errors:");

            int i = 1;
            for (java.util.Enumeration e = result.errors(); e.hasMoreElements(); i++) {
                junit.framework.TestFailure failure = ((junit.framework.TestFailure) (e.nextElement()));
                writer().println((i + ") ") + failure.failedTest());
                writer().print(junit.runner.BaseTestRunner.getFilteredTrace(failure.thrownException()));
            }
        }
    }

    /**
     * Prints failures to the standard output
     */
    public void printFailures(junit.framework.TestResult result) {
        if (result.failureCount() != 0) {
            if (result.failureCount() == 1)
                writer().println(("There was " + result.failureCount()) + " failure:");
            else
                writer().println(("There were " + result.failureCount()) + " failures:");

            int i = 1;
            for (java.util.Enumeration e = result.failures(); e.hasMoreElements(); i++) {
                junit.framework.TestFailure failure = ((junit.framework.TestFailure) (e.nextElement()));
                writer().print((i + ") ") + failure.failedTest());
                failure.thrownException();
                writer().print(junit.runner.BaseTestRunner.getFilteredTrace(failure.thrownException()));
            }
        }
    }

    /**
     * Prints the header of the report
     */
    public void printHeader(junit.framework.TestResult result) {
        if (result.wasSuccessful()) {
            writer().println();
            writer().print("OK");
            writer().println((" (" + result.runCount()) + " tests)");
        } else {
            writer().println();
            writer().println("FAILURES!!!");
            writer().println((((("Tests run: " + result.runCount()) + ",  Failures: ") + result.failureCount()) + ",  Errors: ") + result.errorCount());
        }
    }

    /**
     * Runs a suite extracted from a TestCase subclass.
     */
    public static void run(java.lang.Class testClass) {
        junit.textui.TestRunner.run(new junit.framework.TestSuite(testClass));
    }

    /**
     * Runs a single test and collects its results.
     * This method can be used to start a test run
     * from your program.
     * <pre>
     * public static void main (String[] args) {
     *     test.textui.TestRunner.run(suite());
     * }
     * </pre>
     */
    public static void run(junit.framework.Test suite) {
        junit.textui.TestRunner aTestRunner = new junit.textui.TestRunner();
        aTestRunner.doRun(suite, false);
    }

    /**
     * Runs a single test and waits until the user
     * types RETURN.
     */
    public static void runAndWait(junit.framework.Test suite) {
        junit.textui.TestRunner aTestRunner = new junit.textui.TestRunner();
        aTestRunner.doRun(suite, true);
    }

    /**
     * Starts a test run. Analyzes the command line arguments
     * and runs the given test suite.
     */
    protected junit.framework.TestResult start(java.lang.String[] args) throws java.lang.Exception {
        java.lang.String testCase = "";
        boolean wait = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-wait"))
                wait = true;
            else if (args[i].equals("-c"))
                testCase = extractClassName(args[++i]);
            else if (args[i].equals("-v"))
                java.lang.System.err.println(("JUnit " + junit.runner.Version.id()) + " by Kent Beck and Erich Gamma");
            else
                testCase = args[i];

        }
        if (testCase.equals(""))
            throw new java.lang.Exception("Usage: TestRunner [-wait] testCaseName, where name is the name of the TestCase class");

        try {
            junit.framework.Test suite = getTest(testCase);
            return doRun(suite, wait);
        } catch (java.lang.Exception e) {
            throw new java.lang.Exception("Could not create and run test suite: " + e);
        }
    }

    protected void runFailed(java.lang.String message) {
        java.lang.System.err.println(message);
        java.lang.System.exit(-1);
    }

    protected java.io.PrintStream writer() {
        return fWriter;
    }
}