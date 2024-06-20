package junit.swingui;
/**
 * A Swing based user interface to run tests.
 * Enter the name of a class which either provides a static
 * suite method or is a subclass of TestCase.
 * <pre>
 * Synopsis: java junit.swingui.TestRunner [-noloading] [TestCase]
 * </pre>
 * TestRunner takes as an optional argument the name of the testcase class to be run.
 */
public class TestRunner extends junit.runner.BaseTestRunner implements junit.swingui.TestRunContext {
    protected javax.swing.JFrame fFrame;

    private java.lang.Thread fRunner;

    private junit.framework.TestResult fTestResult;

    private javax.swing.JComboBox fSuiteCombo;

    private junit.swingui.ProgressBar fProgressIndicator;

    private javax.swing.DefaultListModel fFailures;

    private javax.swing.JLabel fLogo;

    private junit.swingui.CounterPanel fCounterPanel;

    private javax.swing.JButton fRun;

    private javax.swing.JButton fQuitButton;

    private javax.swing.JButton fRerunButton;

    private junit.swingui.StatusLine fStatusLine;

    private junit.runner.FailureDetailView fFailureView;

    private javax.swing.JTabbedPane fTestViewTab;

    private javax.swing.JCheckBox fUseLoadingRunner;

    private java.util.Vector fTestRunViews = new java.util.Vector();// view associated with tab in tabbed pane


    // private static Font PLAIN_FONT= StatusLine.PLAIN_FONT;
    // private static Font BOLD_FONT= StatusLine.BOLD_FONT;
    private static final int GAP = 4;

    private static final int HISTORY_LENGTH = 5;

    private static final java.lang.String TESTCOLLECTOR_KEY = "TestCollectorClass";

    private static final java.lang.String FAILUREDETAILVIEW_KEY = "FailureViewClass";

    public TestRunner() {
    }

    public static void main(java.lang.String[] args) {
        new junit.swingui.TestRunner().start(args);
    }

    public static void run(java.lang.Class test) {
        java.lang.String args[] = new java.lang.String[]{ test.getName() };
        junit.swingui.TestRunner.main(args);
    }

    public void addError(final junit.framework.Test test, final java.lang.Throwable t) {
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            public void run() {
                fCounterPanel.setErrorValue(fTestResult.errorCount());
                appendFailure("Error", test, t);
            }
        });
    }

    public void addFailure(final junit.framework.Test test, final junit.framework.AssertionFailedError t) {
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            public void run() {
                fCounterPanel.setFailureValue(fTestResult.failureCount());
                appendFailure("Failure", test, t);
            }
        });
    }

    public void startTest(junit.framework.Test test) {
        postInfo("Running: " + test);
    }

    public void endTest(junit.framework.Test test) {
        postEndTest(test);
    }

    private void postEndTest(final junit.framework.Test test) {
        synchUI();
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            public void run() {
                if (fTestResult != null) {
                    fCounterPanel.setRunValue(fTestResult.runCount());
                    fProgressIndicator.step(fTestResult.wasSuccessful());
                }
            }
        });
    }

    public void setSuite(java.lang.String suiteName) {
        fSuiteCombo.getEditor().setItem(suiteName);
    }

    private void addToHistory(final java.lang.String suite) {
        for (int i = 0; i < fSuiteCombo.getItemCount(); i++) {
            if (suite.equals(fSuiteCombo.getItemAt(i))) {
                fSuiteCombo.removeItemAt(i);
                fSuiteCombo.insertItemAt(suite, 0);
                fSuiteCombo.setSelectedIndex(0);
                return;
            }
        }
        fSuiteCombo.insertItemAt(suite, 0);
        fSuiteCombo.setSelectedIndex(0);
        pruneHistory();
    }

    private void pruneHistory() {
        int historyLength = junit.runner.BaseTestRunner.getPreference("maxhistory", junit.swingui.TestRunner.HISTORY_LENGTH);
        if (historyLength < 1)
            historyLength = 1;

        for (int i = fSuiteCombo.getItemCount() - 1; i > (historyLength - 1); i--)
            fSuiteCombo.removeItemAt(i);

    }

    private void appendFailure(java.lang.String kind, junit.framework.Test test, java.lang.Throwable t) {
        fFailures.addElement(new junit.framework.TestFailure(test, t));
        if (fFailures.size() == 1)
            revealFailure(test);

    }

    private void revealFailure(junit.framework.Test test) {
        for (java.util.Enumeration e = fTestRunViews.elements(); e.hasMoreElements();) {
            junit.swingui.TestRunView v = ((junit.swingui.TestRunView) (e.nextElement()));
            v.revealFailure(test);
        }
    }

    protected void aboutToStart(final junit.framework.Test testSuite) {
        for (java.util.Enumeration e = fTestRunViews.elements(); e.hasMoreElements();) {
            junit.swingui.TestRunView v = ((junit.swingui.TestRunView) (e.nextElement()));
            v.aboutToStart(testSuite, fTestResult);
        }
    }

    protected void runFinished(final junit.framework.Test testSuite) {
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            public void run() {
                for (java.util.Enumeration e = fTestRunViews.elements(); e.hasMoreElements();) {
                    junit.swingui.TestRunView v = ((junit.swingui.TestRunView) (e.nextElement()));
                    v.runFinished(testSuite, fTestResult);
                }
            }
        });
    }

    protected junit.swingui.CounterPanel createCounterPanel() {
        return new junit.swingui.CounterPanel();
    }

    protected javax.swing.JPanel createFailedPanel() {
        javax.swing.JPanel failedPanel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1, 0, 2));
        fRerunButton = new javax.swing.JButton("Run");
        fRerunButton.setEnabled(false);
        fRerunButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                rerun();
            }
        });
        failedPanel.add(fRerunButton);
        return failedPanel;
    }

    protected junit.runner.FailureDetailView createFailureDetailView() {
        java.lang.String className = junit.runner.BaseTestRunner.getPreference(junit.swingui.TestRunner.FAILUREDETAILVIEW_KEY);
        if (className != null) {
            java.lang.Class viewClass = null;
            try {
                viewClass = java.lang.Class.forName(className);
                return ((junit.runner.FailureDetailView) (viewClass.newInstance()));
            } catch (java.lang.Exception e) {
                javax.swing.JOptionPane.showMessageDialog(fFrame, "Could not create Failure DetailView - using default view");
            }
        }
        return new junit.swingui.DefaultFailureDetailView();
    }

    /**
     * Creates the JUnit menu. Clients override this
     * method to add additional menu items.
     */
    protected javax.swing.JMenu createJUnitMenu() {
        javax.swing.JMenu menu = new javax.swing.JMenu("JUnit");
        menu.setMnemonic('J');
        javax.swing.JMenuItem mi1 = new javax.swing.JMenuItem("About...");
        mi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                about();
            }
        });
        mi1.setMnemonic('A');
        menu.add(mi1);
        menu.addSeparator();
        javax.swing.JMenuItem mi2 = new javax.swing.JMenuItem(" Exit ");
        mi2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent event) {
                terminate();
            }
        });
        mi2.setMnemonic('x');
        menu.add(mi2);
        return menu;
    }

    protected javax.swing.JFrame createFrame(java.lang.String title) {
        javax.swing.JFrame frame = new javax.swing.JFrame("JUnit");
        java.awt.Image icon = loadFrameIcon();
        if (icon != null)
            frame.setIconImage(icon);

        frame.getContentPane().setLayout(new java.awt.BorderLayout(0, 0));
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                terminate();
            }
        });
        return frame;
    }

    protected javax.swing.JLabel createLogo() {
        javax.swing.JLabel label;
        javax.swing.Icon icon = junit.swingui.TestRunner.getIconResource(junit.runner.BaseTestRunner.class, "logo.gif");
        if (icon != null)
            label = new javax.swing.JLabel(icon);
        else
            label = new javax.swing.JLabel("JV");

        label.setToolTipText("JUnit Version " + junit.runner.Version.id());
        return label;
    }

    protected void createMenus(javax.swing.JMenuBar mb) {
        mb.add(createJUnitMenu());
    }

    protected javax.swing.JCheckBox createUseLoaderCheckBox() {
        boolean useLoader = useReloadingTestSuiteLoader();
        javax.swing.JCheckBox box = new javax.swing.JCheckBox("Reload classes every run", useLoader);
        box.setToolTipText("Use a custom class loader to reload the classes for every run");
        if (junit.runner.BaseTestRunner.inVAJava())
            box.setVisible(false);

        return box;
    }

    protected javax.swing.JButton createQuitButton() {
        // spaces required to avoid layout flicker
        // Exit is shorter than Stop that shows in the same column
        javax.swing.JButton quit = new javax.swing.JButton(" Exit ");
        quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                terminate();
            }
        });
        return quit;
    }

    protected javax.swing.JButton createRunButton() {
        javax.swing.JButton run = new javax.swing.JButton("Run");
        run.setEnabled(true);
        run.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                runSuite();
            }
        });
        return run;
    }

    protected java.awt.Component createBrowseButton() {
        javax.swing.JButton browse = new javax.swing.JButton("...");
        browse.setToolTipText("Select a Test class");
        browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                browseTestClasses();
            }
        });
        return browse;
    }

    protected junit.swingui.StatusLine createStatusLine() {
        return new junit.swingui.StatusLine(420);
    }

    protected javax.swing.JComboBox createSuiteCombo() {
        javax.swing.JComboBox combo = new javax.swing.JComboBox();
        combo.setEditable(true);
        combo.setLightWeightPopupEnabled(false);
        combo.getEditor().getEditorComponent().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                textChanged();
                if (e.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER)
                    runSuite();

            }
        });
        try {
            loadHistory(combo);
        } catch (java.io.IOException e) {
            // fails the first time
        }
        combo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent event) {
                if (event.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                    textChanged();
                }
            }
        });
        return combo;
    }

    protected javax.swing.JTabbedPane createTestRunViews() {
        javax.swing.JTabbedPane pane = new javax.swing.JTabbedPane(javax.swing.JTabbedPane.BOTTOM);
        junit.swingui.FailureRunView lv = new junit.swingui.FailureRunView(this);
        fTestRunViews.addElement(lv);
        lv.addTab(pane);
        junit.swingui.TestHierarchyRunView tv = new junit.swingui.TestHierarchyRunView(this);
        fTestRunViews.addElement(tv);
        tv.addTab(pane);
        pane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                testViewChanged();
            }
        });
        return pane;
    }

    public void testViewChanged() {
        junit.swingui.TestRunView view = ((junit.swingui.TestRunView) (fTestRunViews.elementAt(fTestViewTab.getSelectedIndex())));
        view.activate();
    }

    protected junit.framework.TestResult createTestResult() {
        return new junit.framework.TestResult();
    }

    protected javax.swing.JFrame createUI(java.lang.String suiteName) {
        javax.swing.JFrame frame = createFrame("JUnit");
        javax.swing.JMenuBar mb = new javax.swing.JMenuBar();
        createMenus(mb);
        frame.setJMenuBar(mb);
        javax.swing.JLabel suiteLabel = new javax.swing.JLabel("Test class name:");
        fSuiteCombo = createSuiteCombo();
        fRun = createRunButton();
        frame.getRootPane().setDefaultButton(fRun);
        java.awt.Component browseButton = createBrowseButton();
        fUseLoadingRunner = createUseLoaderCheckBox();
        fProgressIndicator = new junit.swingui.ProgressBar();
        fCounterPanel = createCounterPanel();
        // JLabel failureLabel= new JLabel("Errors and Failures:");
        fFailures = new javax.swing.DefaultListModel();
        fTestViewTab = createTestRunViews();
        javax.swing.JPanel failedPanel = createFailedPanel();
        fFailureView = createFailureDetailView();
        javax.swing.JScrollPane tracePane = new javax.swing.JScrollPane(fFailureView.getComponent(), javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        fStatusLine = createStatusLine();
        fQuitButton = createQuitButton();
        fLogo = createLogo();
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridBagLayout());
        addGrid(panel, suiteLabel, 0, 0, 2, java.awt.GridBagConstraints.HORIZONTAL, 1.0, java.awt.GridBagConstraints.WEST);
        addGrid(panel, fSuiteCombo, 0, 1, 1, java.awt.GridBagConstraints.HORIZONTAL, 1.0, java.awt.GridBagConstraints.WEST);
        addGrid(panel, browseButton, 1, 1, 1, java.awt.GridBagConstraints.NONE, 0.0, java.awt.GridBagConstraints.WEST);
        addGrid(panel, fRun, 2, 1, 1, java.awt.GridBagConstraints.HORIZONTAL, 0.0, java.awt.GridBagConstraints.CENTER);
        addGrid(panel, fUseLoadingRunner, 0, 2, 3, java.awt.GridBagConstraints.HORIZONTAL, 1.0, java.awt.GridBagConstraints.WEST);
        addGrid(panel, new javax.swing.JSeparator(), 0, 3, 3, java.awt.GridBagConstraints.HORIZONTAL, 1.0, java.awt.GridBagConstraints.WEST);
        addGrid(panel, fProgressIndicator, 0, 4, 2, java.awt.GridBagConstraints.HORIZONTAL, 1.0, java.awt.GridBagConstraints.WEST);
        addGrid(panel, fLogo, 2, 4, 1, java.awt.GridBagConstraints.NONE, 0.0, java.awt.GridBagConstraints.NORTH);
        addGrid(panel, fCounterPanel, 0, 5, 2, java.awt.GridBagConstraints.NONE, 0.0, java.awt.GridBagConstraints.CENTER);
        javax.swing.JSplitPane splitter = new javax.swing.JSplitPane(javax.swing.JSplitPane.VERTICAL_SPLIT, fTestViewTab, tracePane);
        addGrid(panel, splitter, 0, 6, 2, java.awt.GridBagConstraints.BOTH, 1.0, java.awt.GridBagConstraints.WEST);
        /* CENTER */
        addGrid(panel, failedPanel, 2, 6, 1, java.awt.GridBagConstraints.HORIZONTAL, 0.0, java.awt.GridBagConstraints.NORTH);
        addGrid(panel, fStatusLine, 0, 8, 2, java.awt.GridBagConstraints.HORIZONTAL, 1.0, java.awt.GridBagConstraints.CENTER);
        addGrid(panel, fQuitButton, 2, 8, 1, java.awt.GridBagConstraints.HORIZONTAL, 0.0, java.awt.GridBagConstraints.CENTER);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocation(200, 200);
        return frame;
    }

    private void addGrid(javax.swing.JPanel p, java.awt.Component co, int x, int y, int w, int fill, double wx, int anchor) {
        java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = w;
        c.anchor = anchor;
        c.weightx = wx;
        c.fill = fill;
        if ((fill == java.awt.GridBagConstraints.BOTH) || (fill == java.awt.GridBagConstraints.VERTICAL))
            c.weighty = 1.0;

        c.insets = new java.awt.Insets(y == 0 ? junit.swingui.TestRunner.GAP : 0, x == 0 ? junit.swingui.TestRunner.GAP : 0, junit.swingui.TestRunner.GAP, junit.swingui.TestRunner.GAP);
        p.add(co, c);
    }

    protected java.lang.String getSuiteText() {
        if (fSuiteCombo == null)
            return "";

        return ((java.lang.String) (fSuiteCombo.getEditor().getItem()));
    }

    public javax.swing.ListModel getFailures() {
        return fFailures;
    }

    public void insertUpdate(javax.swing.event.DocumentEvent event) {
        textChanged();
    }

    public void browseTestClasses() {
        junit.runner.TestCollector collector = createTestCollector();
        junit.swingui.TestSelector selector = new junit.swingui.TestSelector(fFrame, collector);
        if (selector.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(fFrame, "No Test Cases found.\nCheck that the configured \'TestCollector\' is supported on this platform.");
            return;
        }
        selector.show();
        java.lang.String className = selector.getSelectedItem();
        if (className != null)
            setSuite(className);

    }

    junit.runner.TestCollector createTestCollector() {
        java.lang.String className = junit.runner.BaseTestRunner.getPreference(junit.swingui.TestRunner.TESTCOLLECTOR_KEY);
        if (className != null) {
            java.lang.Class collectorClass = null;
            try {
                collectorClass = java.lang.Class.forName(className);
                return ((junit.runner.TestCollector) (collectorClass.newInstance()));
            } catch (java.lang.Exception e) {
                javax.swing.JOptionPane.showMessageDialog(fFrame, "Could not create TestCollector - using default collector");
            }
        }
        return new junit.runner.SimpleTestCollector();
    }

    private java.awt.Image loadFrameIcon() {
        javax.swing.ImageIcon icon = ((javax.swing.ImageIcon) (junit.swingui.TestRunner.getIconResource(junit.runner.BaseTestRunner.class, "smalllogo.gif")));
        if (icon != null)
            return icon.getImage();

        return null;
    }

    private void loadHistory(javax.swing.JComboBox combo) throws java.io.IOException {
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(getSettingsFile()));
        int itemCount = 0;
        try {
            java.lang.String line;
            while ((line = br.readLine()) != null) {
                combo.addItem(line);
                itemCount++;
            } 
            if (itemCount > 0)
                combo.setSelectedIndex(0);

        } finally {
            br.close();
        }
    }

    private java.io.File getSettingsFile() {
        java.lang.String home = java.lang.System.getProperty("user.home");
        return new java.io.File(home, ".junitsession");
    }

    private void postInfo(final java.lang.String message) {
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            public void run() {
                showInfo(message);
            }
        });
    }

    private void postStatus(final java.lang.String status) {
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            public void run() {
                showStatus(status);
            }
        });
    }

    public void removeUpdate(javax.swing.event.DocumentEvent event) {
        textChanged();
    }

    private void rerun() {
        junit.swingui.TestRunView view = ((junit.swingui.TestRunView) (fTestRunViews.elementAt(fTestViewTab.getSelectedIndex())));
        junit.framework.Test rerunTest = view.getSelectedTest();
        if (rerunTest != null)
            rerunTest(rerunTest);

    }

    private void rerunTest(junit.framework.Test test) {
        if (!(test instanceof junit.framework.TestCase)) {
            showInfo("Could not reload " + test.toString());
            return;
        }
        junit.framework.Test reloadedTest = null;
        try {
            java.lang.Class reloadedTestClass = getLoader().reload(test.getClass());
            java.lang.Class[] classArgs = new java.lang.Class[]{ java.lang.String.class };
            java.lang.Object[] args = new java.lang.Object[]{ ((junit.framework.TestCase) (test)).getName() };
            java.lang.reflect.Constructor constructor = reloadedTestClass.getConstructor(classArgs);
            reloadedTest = ((junit.framework.Test) (constructor.newInstance(args)));
        } catch (java.lang.Exception e) {
            showInfo("Could not reload " + test.toString());
            return;
        }
        junit.framework.TestResult result = new junit.framework.TestResult();
        reloadedTest.run(result);
        java.lang.String message = reloadedTest.toString();
        if (result.wasSuccessful())
            showInfo(message + " was successful");
        else if (result.errorCount() == 1)
            showStatus(message + " had an error");
        else
            showStatus(message + " had a failure");

    }

    protected void reset() {
        fCounterPanel.reset();
        fProgressIndicator.reset();
        fRerunButton.setEnabled(false);
        fFailureView.clear();
        fFailures.clear();
    }

    /**
     * runs a suite.
     *
     * @deprecated use runSuite() instead
     */
    public void run() {
        runSuite();
    }

    protected void runFailed(java.lang.String message) {
        showStatus(message);
        fRun.setText("Run");
        fRunner = null;
    }

    public synchronized void runSuite() {
        if (fRunner != null) {
            fTestResult.stop();
        } else {
            setLoading(shouldReload());
            reset();
            showInfo("Load Test Case...");
            final java.lang.String suiteName = getSuiteText();
            final junit.framework.Test testSuite = getTest(suiteName);
            if (testSuite != null) {
                addToHistory(suiteName);
                doRunTest(testSuite);
            }
        }
    }

    private boolean shouldReload() {
        return (!junit.runner.BaseTestRunner.inVAJava()) && fUseLoadingRunner.isSelected();
    }

    protected synchronized void runTest(final junit.framework.Test testSuite) {
        if (fRunner != null) {
            fTestResult.stop();
        } else {
            reset();
            if (testSuite != null) {
                doRunTest(testSuite);
            }
        }
    }

    private void doRunTest(final junit.framework.Test testSuite) {
        setButtonLabel(fRun, "Stop");
        fRunner = new java.lang.Thread("TestRunner-Thread") {
            public void run() {
                TestRunner.this.start(testSuite);
                postInfo("Running...");
                long startTime = java.lang.System.currentTimeMillis();
                testSuite.run(fTestResult);
                if (fTestResult.shouldStop()) {
                    postStatus("Stopped");
                } else {
                    long endTime = java.lang.System.currentTimeMillis();
                    long runTime = endTime - startTime;
                    postInfo(("Finished: " + elapsedTimeAsString(runTime)) + " seconds");
                }
                runFinished(testSuite);
                setButtonLabel(fRun, "Run");
                fRunner = null;
                java.lang.System.gc();
            }
        };
        // make sure that the test result is created before we start the
        // test runner thread so that listeners can register for it.
        fTestResult = createTestResult();
        fTestResult.addListener(this);
        aboutToStart(testSuite);
        fRunner.start();
    }

    private void saveHistory() throws java.io.IOException {
        java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(getSettingsFile()));
        try {
            for (int i = 0; i < fSuiteCombo.getItemCount(); i++) {
                java.lang.String testsuite = fSuiteCombo.getItemAt(i).toString();
                bw.write(testsuite, 0, testsuite.length());
                bw.newLine();
            }
        } finally {
            bw.close();
        }
    }

    private void setButtonLabel(final javax.swing.JButton button, final java.lang.String label) {
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            public void run() {
                button.setText(label);
            }
        });
    }

    // private void setLabelValue(final JTextField label, final int value) {
    // SwingUtilities.invokeLater(
    // new Runnable() {
    // public void run() {
    // label.setText(Integer.toString(value));
    // }
    // }
    // );
    // }
    public void handleTestSelected(junit.framework.Test test) {
        fRerunButton.setEnabled((test != null) && (test instanceof junit.framework.TestCase));
        showFailureDetail(test);
    }

    private void showFailureDetail(junit.framework.Test test) {
        if (test != null) {
            javax.swing.ListModel failures = getFailures();
            for (int i = 0; i < failures.getSize(); i++) {
                junit.framework.TestFailure failure = ((junit.framework.TestFailure) (failures.getElementAt(i)));
                if (failure.failedTest() == test) {
                    fFailureView.showFailure(failure);
                    return;
                }
            }
        }
        fFailureView.clear();
    }

    private void showInfo(java.lang.String message) {
        fStatusLine.showInfo(message);
    }

    private void showStatus(java.lang.String status) {
        fStatusLine.showError(status);
    }

    /**
     * Starts the TestRunner
     */
    public void start(java.lang.String[] args) {
        java.lang.String suiteName = processArguments(args);
        fFrame = createUI(suiteName);
        fFrame.pack();
        fFrame.setVisible(true);
        if (suiteName != null) {
            setSuite(suiteName);
            runSuite();
        }
    }

    private void start(final junit.framework.Test test) {
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            public void run() {
                int total = test.countTestCases();
                fProgressIndicator.start(total);
                fCounterPanel.setTotal(total);
            }
        });
    }

    /**
     * Wait until all the events are processed in the event thread
     */
    private void synchUI() {
        try {
            javax.swing.SwingUtilities.invokeAndWait(new java.lang.Runnable() {
                public void run() {
                }
            });
        } catch (java.lang.Exception e) {
        }
    }

    /**
     * Terminates the TestRunner
     */
    public void terminate() {
        fFrame.dispose();
        try {
            saveHistory();
        } catch (java.io.IOException e) {
            java.lang.System.out.println("Couldn't save test run history");
        }
        java.lang.System.exit(0);
    }

    public void textChanged() {
        fRun.setEnabled(getSuiteText().length() > 0);
        clearStatus();
    }

    protected void clearStatus() {
        fStatusLine.clear();
    }

    public static javax.swing.Icon getIconResource(java.lang.Class clazz, java.lang.String name) {
        java.net.URL url = clazz.getResource(name);
        if (url == null) {
            java.lang.System.err.println(("Warning: could not load \"" + name) + "\" icon");
            return null;
        }
        return new javax.swing.ImageIcon(url);
    }

    private void about() {
        junit.swingui.AboutDialog about = new junit.swingui.AboutDialog(fFrame);
        about.show();
    }
}