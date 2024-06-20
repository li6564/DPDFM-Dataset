package com.jmonkey.office.lexi.support;
public class ButtonBorder extends javax.swing.border.CompoundBorder {
    static com.jmonkey.office.lexi.support.ButtonBorder.ButtonBevelBorder bbb = new com.jmonkey.office.lexi.support.ButtonBorder.ButtonBevelBorder();

    static javax.swing.border.Border ebb = javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2);

    static class ButtonBevelBorder extends javax.swing.border.BevelBorder {
        public ButtonBevelBorder() {
            super(javax.swing.border.BevelBorder.RAISED);
        }

        public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
            bevelType = javax.swing.border.BevelBorder.RAISED;
            if (c instanceof javax.swing.AbstractButton) {
                javax.swing.ButtonModel model = ((javax.swing.AbstractButton) (c)).getModel();
                boolean pressed = model.isPressed();
                bevelType = (pressed) ? javax.swing.border.BevelBorder.LOWERED : javax.swing.border.BevelBorder.RAISED;
            }
            super.paintBorder(c, g, x, y, width, height);
        }
    }

    public ButtonBorder() {
        super(com.jmonkey.office.lexi.support.ButtonBorder.bbb, com.jmonkey.office.lexi.support.ButtonBorder.ebb);
    }

    public static void main(java.lang.String[] args) {
        javax.swing.JFrame frame = new javax.swing.JFrame("Bevel Button Border");
        javax.swing.border.Border border = new com.jmonkey.office.lexi.support.ButtonBorder();
        javax.swing.JButton helloButton = new javax.swing.JButton("Hello");
        helloButton.setBorder(border);
        javax.swing.JButton worldButton = new javax.swing.JButton("World");
        java.awt.Container contentPane = frame.getContentPane();
        contentPane.add(helloButton, java.awt.BorderLayout.NORTH);
        contentPane.add(worldButton, java.awt.BorderLayout.SOUTH);
        frame.setSize(300, 100);
        frame.setVisible(true);
    }
}