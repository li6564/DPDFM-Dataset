package junit.samples;
/**
 * Some simple tests.
 */
public class SimpleTest extends junit.framework.TestCase {
    protected int fValue1;

    protected int fValue2;

    public SimpleTest(java.lang.String name) {
        super(name);
    }

    protected void setUp() {
        fValue1 = 2;
        fValue2 = 3;
    }

    public static junit.framework.Test suite() {
        /* the type safe way

        TestSuite suite= new TestSuite();
        suite.addTest(
        new SimpleTest("add") {
        protected void runTest() { testAdd(); }
        }
        );

        suite.addTest(
        new SimpleTest("testDivideByZero") {
        protected void runTest() { testDivideByZero(); }
        }
        );
        return suite;
         */
        /* the dynamic way */
        return new junit.framework.TestSuite(junit.samples.SimpleTest.class);
    }

    public void testAdd() {
        double result = fValue1 + fValue2;
        // forced failure result == 5
        junit.framework.Assert.assertTrue(result == 6);
    }

    public void testDivideByZero() {
        int zero = 0;
        int result = 8 / zero;
        java.lang.System.err.println(result);
    }

    public void testEquals() {
        junit.framework.Assert.assertEquals(12, 12);
        junit.framework.Assert.assertEquals(12L, 12L);
        junit.framework.Assert.assertEquals(new java.lang.Long(12), new java.lang.Long(12));
        junit.framework.Assert.assertEquals("Size", 12, 13);
        junit.framework.Assert.assertEquals("Capacity", 12.0, 11.99, 0.0);
    }
}