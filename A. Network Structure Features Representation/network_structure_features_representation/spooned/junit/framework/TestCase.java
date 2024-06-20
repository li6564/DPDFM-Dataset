package junit.framework;
/**
 * A test case defines the fixture to run multiple tests. To define a test case<br>
 * 1) implement a subclass of TestCase<br>
 * 2) define instance variables that store the state of the fixture<br>
 * 3) initialize the fixture state by overriding <code>setUp</code><br>
 * 4) clean-up after a test by overriding <code>tearDown</code>.<br>
 * Each test runs in its own fixture so there
 * can be no side effects among test runs.
 * Here is an example:
 * <pre>
 * public class MathTest extends TestCase {
 *     protected double fValue1;
 *     protected double fValue2;
 *
 *     public MathTest(String name) {
 *         super(name);
 *     }
 *
 *    protected void setUp() {
 *         fValue1= 2.0;
 *         fValue2= 3.0;
 *     }
 * }
 * </pre>
 *
 * For each test implement a method which interacts
 * with the fixture. Verify the expected results with assertions specified
 * by calling <code>assert</code> with a boolean.
 * <pre>
 *    protected void testAdd() {
 *        double result= fValue1 + fValue2;
 *        assert(result == 5.0);
 *    }
 * </pre>
 * Once the methods are defined you can run them. The framework supports
 * both a static type safe and more dynamic way to run a test.
 * In the static way you override the runTest method and define the method to
 * be invoked. A convenient way to do so is with an anonymous inner class.
 * <pre>
 * Test test= new MathTest("add") {
 *        public void runTest() {
 *            testAdd();
 *        }
 * };
 * test.run();
 * </pre>
 * The dynamic way uses reflection to implement <code>runTest</code>. It dynamically finds
 * and invokes a method.
 * In this case the name of the test case has to correspond to the test method
 * to be run.
 * <pre>
 * Test= new MathTest("testAdd");
 * test.run();
 * </pre>
 * The tests to be run can be collected into a TestSuite. JUnit provides
 * different <i>test runners</i> which can run a test suite and collect the results.
 * A test runner either expects a static method <code>suite</code> as the entry
 * point to get a test to run or it will extract the suite automatically.
 * <pre>
 * public static Test suite() {
 *      suite.addTest(new MathTest("testAdd"));
 *      suite.addTest(new MathTest("testDivideByZero"));
 *      return suite;
 *  }
 * </pre>
 *
 * @see TestResult
 * @see TestSuite
 */
public abstract class TestCase extends junit.framework.Assert implements junit.framework.Test {
    /**
     * the name of the test case
     */
    private java.lang.String fName;

    /**
     * No-arg constructor to enable serialization. This method
     * is not intended to be used by mere mortals.
     */
    TestCase() {
        fName = null;
    }

    /**
     * Constructs a test case with the given name.
     */
    public TestCase(java.lang.String name) {
        fName = name;
    }

    /**
     * Counts the number of test cases executed by run(TestResult result).
     */
    public int countTestCases() {
        return 1;
    }

    /**
     * Creates a default TestResult object
     *
     * @see TestResult
     */
    protected junit.framework.TestResult createResult() {
        return new junit.framework.TestResult();
    }

    /**
     * Gets the name of the test case.
     *
     * @deprecated use getName()
     */
    public java.lang.String name() {
        return fName;
    }

    /**
     * A convenience method to run this test, collecting the results with a
     * default TestResult object.
     *
     * @see TestResult
     */
    public junit.framework.TestResult run() {
        junit.framework.TestResult result = createResult();
        run(result);
        return result;
    }

    /**
     * Runs the test case and collects the results in TestResult.
     */
    public void run(junit.framework.TestResult result) {
        result.run(this);
    }

    /**
     * Runs the bare test sequence.
     *
     * @exception Throwable
     * 		if any exception is thrown
     */
    public void runBare() throws java.lang.Throwable {
        setUp();
        try {
            runTest();
        } finally {
            tearDown();
        }
    }

    /**
     * Override to run the test and assert its state.
     *
     * @exception Throwable
     * 		if any exception is thrown
     */
    protected void runTest() throws java.lang.Throwable {
        java.lang.reflect.Method runMethod = null;
        try {
            // use getMethod to get all public inherited
            // methods. getDeclaredMethods returns all
            // methods of this class but excludes the
            // inherited ones.
            runMethod = getClass().getMethod(fName, null);
        } catch (java.lang.NoSuchMethodException e) {
            junit.framework.Assert.fail(("Method \"" + fName) + "\" not found");
        }
        if (!java.lang.reflect.Modifier.isPublic(runMethod.getModifiers())) {
            junit.framework.Assert.fail(("Method \"" + fName) + "\" should be public");
        }
        try {
            runMethod.invoke(this, new java.lang.Class[0]);
        } catch (java.lang.reflect.InvocationTargetException e) {
            e.fillInStackTrace();
            throw e.getTargetException();
        } catch (java.lang.IllegalAccessException e) {
            e.fillInStackTrace();
            throw e;
        }
    }

    /**
     * Sets up the fixture, for example, open a network connection.
     * This method is called before a test is executed.
     */
    protected void setUp() throws java.lang.Exception {
    }

    /**
     * Tears down the fixture, for example, close a network connection.
     * This method is called after a test is executed.
     */
    protected void tearDown() throws java.lang.Exception {
    }

    /**
     * Returns a string representation of the test case
     */
    public java.lang.String toString() {
        return ((name() + "(") + getClass().getName()) + ")";
    }

    /**
     * Gets the name of a TestCase
     *
     * @return returns a String
     */
    public java.lang.String getName() {
        return fName;
    }

    /**
     * Sets the name of a TestCase
     *
     * @param name
     * 		The name to set
     */
    public void setName(java.lang.String name) {
        fName = name;
    }
}