package junit.tests;
public class NotVoidTestCase extends junit.framework.TestCase {
    public NotVoidTestCase(java.lang.String name) {
        super(name);
    }

    public int testNotVoid() {
        return 1;
    }

    public void testVoid() {
    }
}