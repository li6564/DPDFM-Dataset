package junit.tests;
public class SorterTest extends junit.framework.TestCase {
    static class Swapper implements junit.runner.Sorter.Swapper {
        public void swap(java.util.Vector values, int left, int right) {
            java.lang.Object tmp = values.elementAt(left);
            values.setElementAt(values.elementAt(right), left);
            values.setElementAt(tmp, right);
        }
    }

    public SorterTest(java.lang.String name) {
        super(name);
    }

    public void testSort() throws java.lang.Exception {
        java.util.Vector v = new java.util.Vector();
        v.addElement("c");
        v.addElement("b");
        v.addElement("a");
        junit.runner.Sorter.sortStrings(v, 0, v.size() - 1, new junit.tests.SorterTest.Swapper());
        junit.framework.Assert.assertEquals(v.elementAt(0), "a");
        junit.framework.Assert.assertEquals(v.elementAt(1), "b");
        junit.framework.Assert.assertEquals(v.elementAt(2), "c");
    }
}