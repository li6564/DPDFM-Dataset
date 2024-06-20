package junit.tests;
/**
 * A fixture for testing the "auto" test suite feature.
 */
public class SuiteTest extends junit.framework.TestCase {
    protected junit.framework.TestResult fResult;

    public SuiteTest(java.lang.String name) {
        super(name);
    }

    protected void setUp() {
        fResult = new junit.framework.TestResult();
    }

    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite("Suite Tests");
        // build the suite manually
        suite.addTest(new junit.tests.SuiteTest("testNoTestCaseClass"));
        suite.addTest(new junit.tests.SuiteTest("testNoTestCases"));
        suite.addTest(new junit.tests.SuiteTest("testOneTestCase"));
        suite.addTest(new junit.tests.SuiteTest("testNotPublicTestCase"));
        suite.addTest(new junit.tests.SuiteTest("testNotVoidTestCase"));
        suite.addTest(new junit.tests.SuiteTest("testNotExistingTestCase"));
        suite.addTest(new junit.tests.SuiteTest("testInheritedTests"));
        suite.addTest(new junit.tests.SuiteTest("testShadowedTests"));
        suite.addTest(new junit.tests.SuiteTest("testAddTestSuite"));
        return suite;
    }

    public void testInheritedTests() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(junit.tests.InheritedTestCase.class);
        suite.run(fResult);
        junit.framework.Assert.assertTrue(fResult.wasSuccessful());
        junit.framework.Assert.assertEquals(2, fResult.runCount());
    }

    public void testNoTestCaseClass() {
        junit.framework.Test t = new junit.framework.TestSuite(junit.tests.NoTestCaseClass.class);
        t.run(fResult);
        junit.framework.Assert.assertEquals(1, fResult.runCount());// warning test

        junit.framework.Assert.assertTrue(!fResult.wasSuccessful());
    }

    public void testNoTestCases() {
        junit.framework.Test t = new junit.framework.TestSuite(junit.tests.NoTestCases.class);
        t.run(fResult);
        junit.framework.Assert.assertTrue(fResult.runCount() == 1);// warning test

        junit.framework.Assert.assertTrue(fResult.failureCount() == 1);
        junit.framework.Assert.assertTrue(!fResult.wasSuccessful());
    }

    public void testNotExistingTestCase() {
        junit.framework.Test t = new junit.tests.SuiteTest("notExistingMethod");
        t.run(fResult);
        junit.framework.Assert.assertTrue(fResult.runCount() == 1);
        junit.framework.Assert.assertTrue(fResult.failureCount() == 1);
        junit.framework.Assert.assertTrue(fResult.errorCount() == 0);
    }

    public void testNotPublicTestCase() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(junit.tests.NotPublicTestCase.class);
        // 1 public test case + 1 warning for the non-public test case
        junit.framework.Assert.assertEquals(2, suite.countTestCases());
    }

    public void testNotVoidTestCase() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(junit.tests.NotVoidTestCase.class);
        junit.framework.Assert.assertTrue(suite.countTestCases() == 1);
    }

    public void testOneTestCase() {
        junit.framework.Test t = new junit.framework.TestSuite(junit.tests.OneTestCase.class);
        t.run(fResult);
        junit.framework.Assert.assertTrue(fResult.runCount() == 1);
        junit.framework.Assert.assertTrue(fResult.failureCount() == 0);
        junit.framework.Assert.assertTrue(fResult.errorCount() == 0);
        junit.framework.Assert.assertTrue(fResult.wasSuccessful());
    }

    public void testShadowedTests() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(junit.tests.OverrideTestCase.class);
        suite.run(fResult);
        junit.framework.Assert.assertEquals(1, fResult.runCount());
    }

    public void testAddTestSuite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite();
        suite.addTestSuite(junit.tests.OneTestCase.class);
        suite.run(fResult);
        junit.framework.Assert.assertEquals(1, fResult.runCount());
    }
}