package com.jmonkey.office.lexi.support;
public final class Splash extends javax.swing.JWindow {
    javax.swing.JLabel _VERSION_DATE = new javax.swing.JLabel(new java.util.Date().toString());

    javax.swing.JLabel _VERSION = new javax.swing.JLabel("Version: 0.0.0");

    javax.swing.JLabel _AUTHOR = new javax.swing.JLabel("Author: Authors Name");

    javax.swing.JLabel _COPYRIGHT = new javax.swing.JLabel("GPL");

    javax.swing.JLabel _TITAL = new javax.swing.JLabel("Application Title");

    javax.swing.JLabel _DESCRIPTION = new javax.swing.JLabel("Description...");

    javax.swing.ImageIcon _IMAGE;

    javax.swing.JPanel image;

    int width;

    int height;

    public Splash(int w, int h) {
        super();
        width = w;
        height = h;
        this.init();
    }

    public final javax.swing.JLabel getAuthor() {
        return _AUTHOR;
    }

    public final javax.swing.JLabel getCopyright() {
        return _COPYRIGHT;
    }

    public final javax.swing.JLabel getDescription() {
        return _DESCRIPTION;
    }

    public final javax.swing.ImageIcon getImage() {
        return _IMAGE;
    }

    public final javax.swing.JLabel getTital() {
        return _TITAL;
    }

    public final javax.swing.JLabel getVersion() {
        return _VERSION;
    }

    public final javax.swing.JLabel getVersionDate() {
        return _VERSION_DATE;
    }

    public final void hideSplash() {
        try {
            // Close and dispose Window in AWT thread
            javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
                public void run() {
                    if (isVisible()) {
                        setVisible(false);
                        dispose();
                    }
                }
            });
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        // Set the Look & Feel for the app.
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (java.lang.Exception e) {
        }
        // Setup label params.
        _VERSION_DATE.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _VERSION_DATE.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        _VERSION_DATE.setFont(new java.awt.Font("Dialog", java.awt.Font.ITALIC, 10));
        _VERSION_DATE.setOpaque(false);
        _VERSION.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _VERSION.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        _VERSION.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 20));
        _VERSION.setOpaque(false);
        _AUTHOR.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        _AUTHOR.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        _AUTHOR.setFont(new java.awt.Font("Dialog", java.awt.Font.ITALIC, 12));
        _AUTHOR.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4));
        _AUTHOR.setOpaque(false);
        _COPYRIGHT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        _COPYRIGHT.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        _COPYRIGHT.setFont(new java.awt.Font("Dialog", java.awt.Font.ITALIC, 10));
        _COPYRIGHT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4));
        _COPYRIGHT.setOpaque(false);
        _TITAL.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        _TITAL.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        _TITAL.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 30));
        _TITAL.setOpaque(false);
        _DESCRIPTION.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        _DESCRIPTION.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        _DESCRIPTION.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
        _DESCRIPTION.setOpaque(false);
        javax.swing.JPanel content = new javax.swing.JPanel();
        content.setLayout(new java.awt.BorderLayout());
        content.setBorder(javax.swing.BorderFactory.createRaisedBevelBorder());
        javax.swing.JPanel spacer = new javax.swing.JPanel();
        spacer.setLayout(new java.awt.BorderLayout());
        spacer.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        javax.swing.JPanel title = new javax.swing.JPanel();
        title.setLayout(new java.awt.BorderLayout());
        javax.swing.JPanel version = new javax.swing.JPanel();
        version.setLayout(new java.awt.GridLayout(2, 1));
        image = new javax.swing.JPanel();
        title.add(_TITAL, java.awt.BorderLayout.NORTH);
        title.add(_DESCRIPTION, java.awt.BorderLayout.CENTER);
        version.add(_VERSION);
        version.add(_VERSION_DATE);
        spacer.add(version, java.awt.BorderLayout.SOUTH);
        spacer.add(title, java.awt.BorderLayout.NORTH);
        spacer.add(image, java.awt.BorderLayout.CENTER);
        content.add(_AUTHOR, java.awt.BorderLayout.NORTH);
        content.add(_COPYRIGHT, java.awt.BorderLayout.SOUTH);
        content.add(spacer, java.awt.BorderLayout.CENTER);
        // is.setOpaque(false);
        this.setContentPane(content);
        this.setSize(width, height);
        java.awt.Dimension WindowSize = this.getSize();
        java.awt.Dimension ScreenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((ScreenSize.width - WindowSize.width) / 2, (ScreenSize.height - WindowSize.height) / 2, WindowSize.width, WindowSize.height);
    }

    public static void main(java.lang.String[] args) {
        com.jmonkey.office.lexi.support.Splash s = new com.jmonkey.office.lexi.support.Splash(400, 200);
        s.showSplash();
    }

    public final void setImage(java.awt.Image image) {
        _IMAGE = new javax.swing.ImageIcon(image.getScaledInstance(width, ((int) (height / (200 / 75))), java.awt.Image.SCALE_SMOOTH));
    }

    public void paint(java.awt.Graphics g) {
        super.paint(g);
        if (_IMAGE != null) {
            _IMAGE.paintIcon(image, image.getGraphics(), 0, 0);
        }
    }

    public final void showSplash() {
        if (!this.isVisible()) {
            this.setVisible(true);
        }
    }
}