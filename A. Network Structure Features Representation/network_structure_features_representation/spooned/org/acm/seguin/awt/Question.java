package org.acm.seguin.awt;
/**
 * Asks the user a yes no question.  Has the capability of
 *  automatically answering yes for junit tests.
 *
 * @author Chris Seguin
 */
public class Question {
    private static boolean autoYes = false;

    /**
     * Determines if we should always answer yes to any question
     *
     * @param way
     * 		true if we should always answer yes
     */
    public static void setAlwaysYes(boolean way) {
        org.acm.seguin.awt.Question.autoYes = way;
    }

    /**
     * Asks the user a question and returns true if the answer is yes
     *
     * @param title
     * 		The title of the message displayed to the user
     * @param question
     * 		The question that we asked of the user
     * @return true if they answered yes
     */
    public static boolean isYes(java.lang.String title, java.lang.String question) {
        if (org.acm.seguin.awt.Question.autoYes) {
            return true;
        }
        int result = javax.swing.JOptionPane.showConfirmDialog(null, question, title, javax.swing.JOptionPane.YES_NO_OPTION);
        return result == javax.swing.JOptionPane.YES_OPTION;
    }
}