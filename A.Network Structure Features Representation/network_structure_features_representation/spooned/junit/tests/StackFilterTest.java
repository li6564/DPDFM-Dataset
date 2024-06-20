package junit.tests;
public class StackFilterTest extends junit.framework.TestCase {
    java.lang.String fFiltered;

    java.lang.String fUnfiltered;

    public StackFilterTest(java.lang.String name) {
        super(name);
    }

    protected void setUp() {
        java.io.StringWriter swin = new java.io.StringWriter();
        java.io.PrintWriter pwin = new java.io.PrintWriter(swin);
        pwin.println("junit.framework.AssertionFailedError");
        pwin.println("	at junit.framework.Assert.fail(Assert.java:144)");
        pwin.println("	at junit.framework.Assert.assert(Assert.java:19)");
        pwin.println("	at junit.framework.Assert.assert(Assert.java:26)");
        pwin.println("	at MyTest.f(MyTest.java:13)");
        pwin.println("	at MyTest.testStackTrace(MyTest.java:8)");
        pwin.println("	at java.lang.reflect.Method.invoke(Native Method)");
        pwin.println("	at junit.framework.TestCase.runTest(TestCase.java:156)");
        pwin.println("	at junit.framework.TestCase.runBare(TestCase.java:130)");
        pwin.println("	at junit.framework.TestResult$1.protect(TestResult.java:100)");
        pwin.println("	at junit.framework.TestResult.runProtected(TestResult.java:118)");
        pwin.println("	at junit.framework.TestResult.run(TestResult.java:103)");
        pwin.println("	at junit.framework.TestCase.run(TestCase.java:121)");
        pwin.println("	at junit.framework.TestSuite.runTest(TestSuite.java:157)");
        pwin.println("	at junit.framework.TestSuite.run(TestSuite.java, Compiled Code)");
        pwin.println("	at junit.swingui.TestRunner$17.run(TestRunner.java:669)");
        fUnfiltered = swin.toString();
        java.io.StringWriter swout = new java.io.StringWriter();
        java.io.PrintWriter pwout = new java.io.PrintWriter(swout);
        pwout.println("junit.framework.AssertionFailedError");
        pwout.println("	at MyTest.f(MyTest.java:13)");
        pwout.println("	at MyTest.testStackTrace(MyTest.java:8)");
        fFiltered = swout.toString();
    }

    public void testFilter() {
        junit.framework.Assert.assertEquals(fFiltered, junit.runner.BaseTestRunner.filterStack(fUnfiltered));
    }
}