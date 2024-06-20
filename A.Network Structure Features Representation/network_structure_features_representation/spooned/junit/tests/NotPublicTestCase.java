package junit.tests;
public class NotPublicTestCase extends junit.framework.TestCase {
    public NotPublicTestCase(java.lang.String name) {
        super(name);
    }

    protected void testNotPublic() {
    }

    public void testPublic() {
    }
}