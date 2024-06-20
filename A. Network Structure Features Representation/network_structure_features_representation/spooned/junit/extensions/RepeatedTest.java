package junit.extensions;
/**
 * A Decorator that runs a test repeatedly.
 */
public class RepeatedTest extends junit.extensions.TestDecorator {
    private int fTimesRepeat;

    public RepeatedTest(junit.framework.Test test, int repeat) {
        super(test);
        if (repeat < 0)
            throw new java.lang.IllegalArgumentException("Repetition count must be > 0");

        fTimesRepeat = repeat;
    }

    public int countTestCases() {
        return super.countTestCases() * fTimesRepeat;
    }

    public void run(junit.framework.TestResult result) {
        for (int i = 0; i < fTimesRepeat; i++) {
            if (result.shouldStop())
                break;

            super.run(result);
        }
    }

    public java.lang.String toString() {
        return super.toString() + "(repeated)";
    }
}