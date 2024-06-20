package com.jmonkey.office.lexi.support;
/**
 * A nice dialog so the user can choose what type of file to make
 *
 * @author Matthew Schmidt
 * @version 1.0 Revision 0
 */
public class NewDialog extends javax.swing.JFrame {
    javax.swing.JButton plain;

    javax.swing.JButton rtf;

    javax.swing.JButton html;

    javax.swing.JButton ok;

    javax.swing.JButton cancel;

    public NewDialog(com.jmonkey.office.lexi.support.FileActionListener listen) {
        super("Start a new File..");
        this.setSize(300, 200);
        this.setLocation(45, 20);
        // Make the left panel
        javax.swing.JPanel left = new javax.swing.JPanel();
        left.setLayout(new java.awt.FlowLayout());
        left.setBackground(java.awt.Color.black);
        // Make the buttons
        plain = new javax.swing.JButton(new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("new_document16.gif")));
        rtf = new javax.swing.JButton(new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("new_document16.gif")));
        html = new javax.swing.JButton(new javax.swing.ImageIcon(com.jmonkey.office.lexi.support.images.Loader.load("new_document16.gif")));
        // Add the buttons to the panel
        left.add(plain);
        left.add(rtf);
        left.add(html);
        // Make a panel to the right.
        javax.swing.JPanel right = new javax.swing.JPanel();
        right.setLayout(new javax.swing.BoxLayout(right, javax.swing.BoxLayout.Y_AXIS));
        right.setBackground(java.awt.Color.black);
        // Make the buttons
        ok = new javax.swing.JButton("Ok");
        cancel = new javax.swing.JButton("Cancel");
        // Add the buttons to the other panel
        right.add(ok);
        right.add(cancel);
        // Make our Frame show up
        this.getContentPane().setBackground(java.awt.Color.black);
        this.getContentPane().setLayout(new java.awt.BorderLayout());
        this.getContentPane().add(left, java.awt.BorderLayout.WEST);
        this.getContentPane().add(right, java.awt.BorderLayout.EAST);
        this.setVisible(true);
    }
}