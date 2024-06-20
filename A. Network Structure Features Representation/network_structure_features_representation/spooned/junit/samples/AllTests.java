package junit.samples;
/**
 * TestSuite that runs all the sample tests
 */
public class AllTests {
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(junit.samples.AllTests.suite());
    }

    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite("All JUnit Tests");
        suite.addTest(junit.samples.VectorTest.suite());
        suite.addTest(new junit.framework.TestSuite(junit.samples.money.MoneyTest.class));
        suite.addTest(junit.tests.AllTests.suite());
        return suite;
    }
}