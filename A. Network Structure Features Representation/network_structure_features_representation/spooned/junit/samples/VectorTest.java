package junit.samples;
/**
 * A sample test case, testing <code>java.util.Vector</code>.
 */
public class VectorTest extends junit.framework.TestCase {
    protected java.util.Vector fEmpty;

    protected java.util.Vector fFull;

    public VectorTest(java.lang.String name) {
        super(name);
    }

    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(junit.samples.VectorTest.suite());
    }

    protected void setUp() {
        fEmpty = new java.util.Vector();
        fFull = new java.util.Vector();
        fFull.addElement(new java.lang.Integer(1));
        fFull.addElement(new java.lang.Integer(2));
        fFull.addElement(new java.lang.Integer(3));
    }

    public static junit.framework.Test suite() {
        return new junit.framework.TestSuite(junit.samples.VectorTest.class);
    }

    public void testCapacity() {
        int size = fFull.size();
        for (int i = 0; i < 100; i++)
            fFull.addElement(new java.lang.Integer(i));

        junit.framework.Assert.assertTrue(fFull.size() == (100 + size));
    }

    public void testClone() {
        java.util.Vector clone = ((java.util.Vector) (fFull.clone()));
        junit.framework.Assert.assertTrue(clone.size() == fFull.size());
        junit.framework.Assert.assertTrue(clone.contains(new java.lang.Integer(1)));
    }

    public void testContains() {
        junit.framework.Assert.assertTrue(fFull.contains(new java.lang.Integer(1)));
        junit.framework.Assert.assertTrue(!fEmpty.contains(new java.lang.Integer(1)));
    }

    public void testElementAt() {
        java.lang.Integer i = ((java.lang.Integer) (fFull.elementAt(0)));
        junit.framework.Assert.assertTrue(i.intValue() == 1);
        try {
            fFull.elementAt(fFull.size());
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            return;
        }
        junit.framework.Assert.fail("Should raise an ArrayIndexOutOfBoundsException");
    }

    public void testRemoveAll() {
        fFull.removeAllElements();
        fEmpty.removeAllElements();
        junit.framework.Assert.assertTrue(fFull.isEmpty());
        junit.framework.Assert.assertTrue(fEmpty.isEmpty());
    }

    public void testRemoveElement() {
        fFull.removeElement(new java.lang.Integer(3));
        junit.framework.Assert.assertTrue(!fFull.contains(new java.lang.Integer(3)));
    }
}