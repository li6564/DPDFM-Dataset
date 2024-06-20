package junit.tests;
public class AssertTest extends junit.framework.TestCase {
    public AssertTest(java.lang.String name) {
        super(name);
    }

    /**
     * Test for the special Double.NaN value.
     */
    public void testAssertEqualsNaNFails() {
        try {
            junit.framework.Assert.assertEquals(1.234, java.lang.Double.NaN, 0.0);
        } catch (junit.framework.AssertionFailedError e) {
            return;
        }
        junit.framework.Assert.fail();
    }

    public void testAssertNaNEqualsFails() {
        try {
            junit.framework.Assert.assertEquals(java.lang.Double.NaN, 1.234, 0.0);
        } catch (junit.framework.AssertionFailedError e) {
            return;
        }
        junit.framework.Assert.fail();
    }

    public void testAssertNaNEqualsNaNFails() {
        try {
            junit.framework.Assert.assertEquals(java.lang.Double.NaN, java.lang.Double.NaN, 0.0);
        } catch (junit.framework.AssertionFailedError e) {
            return;
        }
        junit.framework.Assert.fail();
    }

    public void testAssertPosInfinityNotEqualsNegInfinity() {
        try {
            junit.framework.Assert.assertEquals(java.lang.Double.POSITIVE_INFINITY, java.lang.Double.NEGATIVE_INFINITY, 0.0);
        } catch (junit.framework.AssertionFailedError e) {
            return;
        }
        junit.framework.Assert.fail();
    }

    public void testAssertPosInfinityNotEquals() {
        try {
            junit.framework.Assert.assertEquals(java.lang.Double.POSITIVE_INFINITY, 1.23, 0.0);
        } catch (junit.framework.AssertionFailedError e) {
            return;
        }
        junit.framework.Assert.fail();
    }

    public void testAssertPosInfinityEqualsInfinity() {
        junit.framework.Assert.assertEquals(java.lang.Double.POSITIVE_INFINITY, java.lang.Double.POSITIVE_INFINITY, 0.0);
    }

    public void testAssertNegInfinityEqualsInfinity() {
        junit.framework.Assert.assertEquals(java.lang.Double.NEGATIVE_INFINITY, java.lang.Double.NEGATIVE_INFINITY, 0.0);
    }

    public void testAssertEquals() {
        java.lang.Object o = new java.lang.Object();
        junit.framework.Assert.assertEquals(o, o);
    }

    public void testAssertEqualsNull() {
        junit.framework.Assert.assertEquals(null, null);
    }

    public void testAssertNull() {
        junit.framework.Assert.assertNull(null);
    }

    public void testAssertNullNotEqualsNull() {
        try {
            junit.framework.Assert.assertEquals(null, new java.lang.Object());
        } catch (junit.framework.AssertionFailedError e) {
            return;
        }
        junit.framework.Assert.fail();
    }

    public void testAssertSame() {
        java.lang.Object o = new java.lang.Object();
        junit.framework.Assert.assertSame(o, o);
    }

    public void testAssertSameFails() {
        try {
            junit.framework.Assert.assertSame(new java.lang.Integer(1), new java.lang.Integer(1));
        } catch (junit.framework.AssertionFailedError e) {
            return;
        }
        junit.framework.Assert.fail();
    }

    public void testFail() {
        try {
            junit.framework.Assert.fail();
        } catch (junit.framework.AssertionFailedError e) {
            return;
        }
        throw new junit.framework.AssertionFailedError();// You can't call fail() here

    }

    public void testFailAssertNotNull() {
        try {
            junit.framework.Assert.assertNotNull(null);
        } catch (junit.framework.AssertionFailedError e) {
            return;
        }
        junit.framework.Assert.fail();
    }

    public void testSucceedAssertNotNull() {
        junit.framework.Assert.assertNotNull(new java.lang.Object());
    }
}