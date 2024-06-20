package junit.tests;
/**
 * A test case testing the testing framework.
 */
public class Failure extends junit.framework.TestCase {
    public Failure(java.lang.String name) {
        super(name);
    }

    public void test() {
        junit.framework.Assert.fail();
    }
}