package junit.extensions;
/**
 * A TestSuite for active Tests. It runs each
 * test in a separate thread and waits until all
 * threads have terminated.
 * -- Aarhus Radisson Scandinavian Center 11th floor
 */
public class ActiveTestSuite extends junit.framework.TestSuite {
    private volatile int fActiveTestDeathCount;

    public void run(junit.framework.TestResult result) {
        fActiveTestDeathCount = 0;
        super.run(result);
        waitUntilFinished();
    }

    public void runTest(final junit.framework.Test test, final junit.framework.TestResult result) {
        java.lang.Thread t = new java.lang.Thread() {
            public void run() {
                try {
                    // inlined due to limitation in VA/Java
                    // ActiveTestSuite.super.runTest(test, result);
                    test.run(result);
                } finally {
                    ActiveTestSuite.this.runFinished(test);
                }
            }
        };
        t.start();
    }

    synchronized void waitUntilFinished() {
        while (fActiveTestDeathCount < testCount()) {
            try {
                wait();
            } catch (java.lang.InterruptedException e) {
                return;// ignore

            }
        } 
    }

    public synchronized void runFinished(junit.framework.Test test) {
        fActiveTestDeathCount++;
        notifyAll();
    }
}