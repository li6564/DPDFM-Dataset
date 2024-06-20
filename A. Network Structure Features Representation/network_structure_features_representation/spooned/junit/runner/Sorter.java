package junit.runner;
/**
 * A custom quick sort with support to customize the swap behaviour.
 * NOTICE: We can't use the the sorting support from the JDK 1.2 collection
 * classes because of the JDK 1.1.7 compatibility.
 */
public class Sorter {
    public static interface Swapper {
        public void swap(java.util.Vector values, int left, int right);
    }

    public static void sortStrings(java.util.Vector values, int left, int right, junit.runner.Sorter.Swapper swapper) {
        int oleft = left;
        int oright = right;
        java.lang.String mid = ((java.lang.String) (values.elementAt((left + right) / 2)));
        do {
            while (((java.lang.String) (values.elementAt(left))).compareTo(mid) < 0)
                left++;

            while (mid.compareTo(((java.lang.String) (values.elementAt(right)))) < 0)
                right--;

            if (left <= right) {
                swapper.swap(values, left, right);
                left++;
                right--;
            }
        } while (left <= right );
        if (oleft < right)
            junit.runner.Sorter.sortStrings(values, oleft, right, swapper);

        if (left < oright)
            junit.runner.Sorter.sortStrings(values, left, oright, swapper);

    }
}