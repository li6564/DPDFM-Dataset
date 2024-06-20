package junit.tests;
/**
 * TestSuite that runs all the sample tests
 */
public class AllTests {
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(junit.tests.AllTests.suite());
    }

    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite("Framework Tests");
        suite.addTestSuite(junit.tests.ExtensionTest.class);
        suite.addTestSuite(junit.tests.TestCaseTest.class);
        suite.addTest(junit.tests.SuiteTest.suite());// Tests suite building, so can't use automatic test extraction

        suite.addTestSuite(junit.tests.ExceptionTestCaseTest.class);
        suite.addTestSuite(junit.tests.TestListenerTest.class);
        suite.addTestSuite(junit.tests.ActiveTestTest.class);
        suite.addTestSuite(junit.tests.AssertTest.class);
        suite.addTestSuite(junit.tests.StackFilterTest.class);
        suite.addTestSuite(junit.tests.SorterTest.class);
        suite.addTestSuite(junit.tests.RepeatedTestTest.class);
        suite.addTestSuite(junit.tests.TestImplementorTest.class);
        if (!junit.runner.BaseTestRunner.inVAJava()) {
            suite.addTestSuite(junit.tests.TextRunnerTest.class);
            if (!junit.tests.AllTests.isJDK11())
                suite.addTest(new junit.framework.TestSuite(junit.tests.TestCaseClassLoaderTest.class));

        }
        return suite;
    }

    static boolean isJDK11() {
        java.lang.String version = java.lang.System.getProperty("java.version");
        return version.startsWith("1.1");
    }
}