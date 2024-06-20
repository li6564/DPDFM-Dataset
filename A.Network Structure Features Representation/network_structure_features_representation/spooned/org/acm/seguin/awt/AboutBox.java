package org.acm.seguin.awt;
/**
 * About box for the JRefactory software component
 *
 * @author Chris Seguin
 */
public class AboutBox extends javax.swing.JWindow implements java.awt.event.ActionListener {
    /**
     * Constructor for the AboutBox object
     */
    public AboutBox() {
        super();
        getContentPane().setLayout(null);
        java.awt.Toolkit kit = getToolkit();
        setBackground(java.awt.Color.black);
        getContentPane().setBackground(java.awt.Color.black);
        org.acm.seguin.awt.ImagePanel imagePanel = new org.acm.seguin.awt.ImagePanel("JRefactory.jpg");
        int width = 0;
        int maxWidth = 0;
        int currentHeight = 0;
        java.awt.Dimension dim = imagePanel.getPreferredSize();
        imagePanel.setLocation(25, 5);
        getContentPane().add(imagePanel);
        width = 10 + dim.width;
        maxWidth = java.lang.Math.max(maxWidth, dim.width);
        currentHeight = 10 + dim.height;
        javax.swing.JLabel label1 = new javax.swing.JLabel("Version:  " + new org.acm.seguin.JRefactoryVersion().toString());
        label1.setHorizontalAlignment(label1.CENTER);
        label1.setForeground(java.awt.Color.red);
        dim = label1.getPreferredSize();
        maxWidth = java.lang.Math.max(maxWidth, dim.width);
        label1.setSize(dim);
        label1.setLocation((width - dim.width) / 2, currentHeight);
        currentHeight += 5 + dim.height;
        getContentPane().add(label1);
        javax.swing.JLabel label2 = new javax.swing.JLabel("Author:  Chris Seguin");
        label2.setHorizontalAlignment(label2.CENTER);
        label2.setForeground(java.awt.Color.red);
        dim = label2.getPreferredSize();
        maxWidth = java.lang.Math.max(maxWidth, dim.width);
        label2.setSize(dim);
        label2.setLocation((width - dim.width) / 2, currentHeight);
        currentHeight += 5 + dim.height;
        getContentPane().add(label2);
        javax.swing.JLabel label3 = new javax.swing.JLabel("Email:  seguin@acm.org");
        label3.setHorizontalAlignment(label3.CENTER);
        label3.setForeground(java.awt.Color.red);
        dim = label3.getPreferredSize();
        maxWidth = java.lang.Math.max(maxWidth, dim.width);
        label3.setSize(dim);
        label3.setLocation((width - dim.width) / 2, currentHeight);
        currentHeight += 5 + dim.height;
        getContentPane().add(label3);
        javax.swing.JLabel label4 = new javax.swing.JLabel("Home:  http://jrefactory.sourceforge.net");
        label4.setHorizontalAlignment(label4.CENTER);
        dim = label4.getPreferredSize();
        maxWidth = java.lang.Math.max(maxWidth, dim.width);
        label4.setSize(dim);
        label4.setLocation((width - dim.width) / 2, currentHeight);
        label4.setForeground(java.awt.Color.red);
        currentHeight += 5 + dim.height;
        getContentPane().add(label4);
        javax.swing.JButton okButton = new javax.swing.JButton("OK");
        okButton.setForeground(java.awt.Color.red);
        okButton.setBackground(java.awt.Color.black);
        okButton.addActionListener(this);
        dim = okButton.getPreferredSize();
        maxWidth = java.lang.Math.max(maxWidth, dim.width);
        okButton.setSize(dim);
        okButton.setLocation((width - dim.width) / 2, currentHeight);
        currentHeight += 5 + dim.height;
        getContentPane().add(okButton);
        currentHeight += 5;
        maxWidth += 10;
        setSize(maxWidth, currentHeight);
        java.awt.Point pt = imagePanel.getLocation();
        dim = imagePanel.getSize();
        imagePanel.setLocation((maxWidth - dim.width) / 2, 5);
        pt = label1.getLocation();
        dim = label1.getSize();
        label1.setLocation((maxWidth - dim.width) / 2, pt.y);
        pt = label2.getLocation();
        dim = label2.getSize();
        label2.setLocation((maxWidth - dim.width) / 2, pt.y);
        pt = label3.getLocation();
        dim = label3.getSize();
        label3.setLocation((maxWidth - dim.width) / 2, pt.y);
        pt = label4.getLocation();
        dim = label4.getSize();
        label4.setLocation((maxWidth - dim.width) / 2, pt.y);
        pt = okButton.getLocation();
        dim = okButton.getSize();
        okButton.setLocation((maxWidth - dim.width) / 2, pt.y);
        dim = kit.getScreenSize();
        setLocation((dim.width - width) / 2, (dim.height - currentHeight) / 2);
    }

    /**
     * The main program for the AboutBox class
     *
     * @param args
     * 		The command line arguments
     */
    public static void main(java.lang.String[] args) {
        org.acm.seguin.awt.AboutBox.run();
    }

    public void actionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    public static void run() {
        new org.acm.seguin.awt.AboutBox().show();
    }
}