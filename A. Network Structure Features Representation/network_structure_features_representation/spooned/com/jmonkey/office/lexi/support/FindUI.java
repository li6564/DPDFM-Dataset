package com.jmonkey.office.lexi.support;
/**
 *
 * @version 1.0
 * @author Matthew Schmidt
Modified EditorActionManager to work with this
06/09/99
 */
public class FindUI extends javax.swing.JFrame implements java.awt.event.ActionListener {
    javax.swing.JTextField findWhat;

    javax.swing.JButton okB;

    javax.swing.JButton cancelB;

    javax.swing.JRadioButton up;

    javax.swing.JRadioButton down;

    int isFirstClick = 1;

    java.lang.String editText;

    javax.swing.JEditorPane _EDITOR = null;

    public FindUI(java.lang.String text, javax.swing.JEditorPane edit) {
        super("Find text...");
        editText = text;
        _EDITOR = edit;
        this.getContentPane().setLayout(new java.awt.BorderLayout());
        javax.swing.JPanel right = new javax.swing.JPanel();
        right.setLayout(new javax.swing.BoxLayout(right, javax.swing.BoxLayout.Y_AXIS));
        javax.swing.JPanel left = new javax.swing.JPanel();
        javax.swing.JLabel find = new javax.swing.JLabel("Text to find:");
        findWhat = new javax.swing.JTextField("", 20);
        left.add(find);
        left.add(findWhat);
        okB = new javax.swing.JButton("Find Next");
        okB.addActionListener(this);
        okB.setActionCommand("ok");
        cancelB = new javax.swing.JButton("Cancel");
        cancelB.addActionListener(this);
        cancelB.setActionCommand("cancel");
        right.add(okB);
        right.add(cancelB);
        javax.swing.JPanel bottom = new javax.swing.JPanel();
        bottom.setLayout(new java.awt.BorderLayout());
        javax.swing.JPanel botCenter = new javax.swing.JPanel();
        botCenter.setBorder(new javax.swing.border.LineBorder(java.awt.Color.gray, 1));
        up = new javax.swing.JRadioButton("Up");
        up.addActionListener(this);
        up.setActionCommand("up");
        down = new javax.swing.JRadioButton("Down");
        down.addActionListener(this);
        down.setActionCommand("down");
        down.setSelected(true);
        botCenter.add(up);
        botCenter.add(down);
        bottom.add(botCenter, java.awt.BorderLayout.CENTER);
        this.getContentPane().add(left, java.awt.BorderLayout.WEST);
        this.getContentPane().add(right, java.awt.BorderLayout.EAST);
        this.getContentPane().add(bottom, java.awt.BorderLayout.SOUTH);
        this.setSize(400, 120);
        this.setVisible(true);
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getActionCommand().equals("ok")) {
            if (isFirstClick == 1) {
                /* StringSearch searcher = new StringSearch(findWhat.getText(), editText);
                int pos = searcher.first();
                if (pos != SearchIterator.DONE && pos = searcher.next()) {
                System.out.println("Found match at " + pos + ", length is " + searcher.getMatchLength());
                }
                 */
                java.lang.System.out.println((("We searched for: `" + findWhat.getText()) + "\' in\n") + editText);
                java.lang.System.out.println(("\n======\nIndex at: " + editText.indexOf(findWhat.getText())) + "\n======");
                if (_EDITOR != null) {
                    _EDITOR.setCaretPosition(editText.indexOf(findWhat.getText()));
                    _EDITOR.requestFocus();
                } else {
                }
                isFirstClick = 0;
            } else if (up.isSelected()) {
                // searcher.previous();
            } else if (down.isSelected()) {
                // searcher.next();
            }
        } else if (e.getActionCommand().equals("cancel")) {
            // this.removeActionListener();
            this.dispose();
        } else if (e.getActionCommand().equals("up")) {
            down.setSelected(false);
        } else if (e.getActionCommand().equals("down")) {
            up.setSelected(false);
        }
    }

    public static void main(java.lang.String[] args) {
        new com.jmonkey.office.lexi.support.FindUI("The quick brown fox jumped over the dark red fence", null);
    }
}