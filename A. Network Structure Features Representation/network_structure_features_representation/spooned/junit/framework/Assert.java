package junit.framework;
/**
 * A set of assert methods.
 */
public class Assert {
    /**
     * Protect constructor since it is a static only class
     */
    protected Assert() {
    }

    /**
     * Asserts that a condition is true. If it isn't it throws
     * an AssertionFailedError with the given message.
     *
     * @deprecated use assertTrue
     */
    java.lang.String message;

    boolean condition;

    {
        if (!condition)
            fail(message);

    }

    /**
     * Asserts that a condition is true. If it isn't it throws
     * an AssertionFailedError.
     *
     * @deprecated use assertTrue
     */
    boolean condition;

    {
        assert null.condition;
    }

    /**
     * Asserts that a condition is true. If it isn't it throws
     * an AssertionFailedError with the given message.
     */
    public static void assertTrue(java.lang.String message, boolean condition) {
        if (!condition)
            junit.framework.Assert.fail(message);

    }

    /**
     * Asserts that a condition is true. If it isn't it throws
     * an AssertionFailedError.
     */
    public static void assertTrue(boolean condition) {
        junit.framework.Assert.assertTrue(null, condition);
    }

    /**
     * Fails a test with the given message.
     */
    public static void fail(java.lang.String message) {
        throw new junit.framework.AssertionFailedError(message);
    }

    /**
     * Fails a test with no message.
     */
    public static void fail() {
        junit.framework.Assert.fail(null);
    }

    /**
     * Asserts that two objects are equal. If they are not
     * an AssertionFailedError is thrown.
     */
    public static void assertEquals(java.lang.String message, java.lang.Object expected, java.lang.Object actual) {
        if ((expected == null) && (actual == null))
            return;

        if ((expected != null) && expected.equals(actual))
            return;

        junit.framework.Assert.failNotEquals(message, expected, actual);
    }

    /**
     * Asserts that two objects are equal. If they are not
     * an AssertionFailedError is thrown.
     */
    public static void assertEquals(java.lang.Object expected, java.lang.Object actual) {
        junit.framework.Assert.assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two doubles are equal concerning a delta. If the expected
     * value is infinity then the delta value is ignored.
     */
    public static void assertEquals(java.lang.String message, double expected, double actual, double delta) {
        // handle infinity specially since subtracting to infinite values gives NaN and the
        // the following test fails
        if (java.lang.Double.isInfinite(expected)) {
            if (!(expected == actual))
                junit.framework.Assert.failNotEquals(message, new java.lang.Double(expected), new java.lang.Double(actual));

        } else // Because comparison with NaN always returns false
        if (!(java.lang.Math.abs(expected - actual) <= delta))
            junit.framework.Assert.failNotEquals(message, new java.lang.Double(expected), new java.lang.Double(actual));

    }

    /**
     * Asserts that two doubles are equal concerning a delta. If the expected
     * value is infinity then the delta value is ignored.
     */
    public static void assertEquals(double expected, double actual, double delta) {
        junit.framework.Assert.assertEquals(null, expected, actual, delta);
    }

    /**
     * Asserts that two floats are equal concerning a delta. If the expected
     * value is infinity then the delta value is ignored.
     */
    public static void assertEquals(java.lang.String message, float expected, float actual, float delta) {
        // handle infinity specially since subtracting to infinite values gives NaN and the
        // the following test fails
        if (java.lang.Float.isInfinite(expected)) {
            if (!(expected == actual))
                junit.framework.Assert.failNotEquals(message, new java.lang.Float(expected), new java.lang.Float(actual));

        } else if (!(java.lang.Math.abs(expected - actual) <= delta))
            junit.framework.Assert.failNotEquals(message, new java.lang.Float(expected), new java.lang.Float(actual));

    }

    /**
     * Asserts that two floats are equal concerning a delta. If the expected
     * value is infinity then the delta value is ignored.
     */
    public static void assertEquals(float expected, float actual, float delta) {
        junit.framework.Assert.assertEquals(null, expected, actual, delta);
    }

    /**
     * Asserts that two longs are equal.
     */
    public static void assertEquals(java.lang.String message, long expected, long actual) {
        junit.framework.Assert.assertEquals(message, new java.lang.Long(expected), new java.lang.Long(actual));
    }

    /**
     * Asserts that two longs are equal.
     */
    public static void assertEquals(long expected, long actual) {
        junit.framework.Assert.assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two booleans are equal.
     */
    public static void assertEquals(java.lang.String message, boolean expected, boolean actual) {
        junit.framework.Assert.assertEquals(message, new java.lang.Boolean(expected), new java.lang.Boolean(actual));
    }

    /**
     * Asserts that two booleans are equal.
     */
    public static void assertEquals(boolean expected, boolean actual) {
        junit.framework.Assert.assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two bytes are equal.
     */
    public static void assertEquals(java.lang.String message, byte expected, byte actual) {
        junit.framework.Assert.assertEquals(message, new java.lang.Byte(expected), new java.lang.Byte(actual));
    }

    /**
     * Asserts that two bytes are equal.
     */
    public static void assertEquals(byte expected, byte actual) {
        junit.framework.Assert.assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two chars are equal.
     */
    public static void assertEquals(java.lang.String message, char expected, char actual) {
        junit.framework.Assert.assertEquals(message, new java.lang.Character(expected), new java.lang.Character(actual));
    }

    /**
     * Asserts that two chars are equal.
     */
    public static void assertEquals(char expected, char actual) {
        junit.framework.Assert.assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two shorts are equal.
     */
    public static void assertEquals(java.lang.String message, short expected, short actual) {
        junit.framework.Assert.assertEquals(message, new java.lang.Short(expected), new java.lang.Short(actual));
    }

    /**
     * Asserts that two shorts are equal.
     */
    public static void assertEquals(short expected, short actual) {
        junit.framework.Assert.assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two ints are equal.
     */
    public static void assertEquals(java.lang.String message, int expected, int actual) {
        junit.framework.Assert.assertEquals(message, new java.lang.Integer(expected), new java.lang.Integer(actual));
    }

    /**
     * Asserts that two ints are equal.
     */
    public static void assertEquals(int expected, int actual) {
        junit.framework.Assert.assertEquals(null, expected, actual);
    }

    /**
     * Asserts that an object isn't null.
     */
    public static void assertNotNull(java.lang.Object object) {
        junit.framework.Assert.assertNotNull(null, object);
    }

    /**
     * Asserts that an object isn't null.
     */
    public static void assertNotNull(java.lang.String message, java.lang.Object object) {
        junit.framework.Assert.assertTrue(message, object != null);
    }

    /**
     * Asserts that an object is null.
     */
    public static void assertNull(java.lang.Object object) {
        junit.framework.Assert.assertNull(null, object);
    }

    /**
     * Asserts that an object is null.
     */
    public static void assertNull(java.lang.String message, java.lang.Object object) {
        junit.framework.Assert.assertTrue(message, object == null);
    }

    /**
     * Asserts that two objects refer to the same object. If they are not
     * an AssertionFailedError is thrown.
     */
    public static void assertSame(java.lang.String message, java.lang.Object expected, java.lang.Object actual) {
        if (expected == actual)
            return;

        junit.framework.Assert.failNotSame(message, expected, actual);
    }

    /**
     * Asserts that two objects refer to the same object. If they are not
     * the same an AssertionFailedError is thrown.
     */
    public static void assertSame(java.lang.Object expected, java.lang.Object actual) {
        junit.framework.Assert.assertSame(null, expected, actual);
    }

    private static void failNotEquals(java.lang.String message, java.lang.Object expected, java.lang.Object actual) {
        java.lang.String formatted = "";
        if (message != null)
            formatted = message + " ";

        junit.framework.Assert.fail(((((formatted + "expected:<") + expected) + "> but was:<") + actual) + ">");
    }

    private static void failNotSame(java.lang.String message, java.lang.Object expected, java.lang.Object actual) {
        java.lang.String formatted = "";
        if (message != null)
            formatted = message + " ";

        junit.framework.Assert.fail(formatted + "expected same");
    }
}