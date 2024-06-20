/**
 * QuickUML; A simple UML tool that demonstrates one use of the
 * Java Diagram Package
 *
 * Copyright (C) 2001  Eric Crahen <crahen@cse.buffalo.edu>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package uml.ui;
/**
 *
 * @class BuildAction
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 */
public class BuildAction extends javax.swing.AbstractAction {
    protected uml.ui.DiagramContainer container;

    public BuildAction(uml.ui.DiagramContainer container) {
        super("Build ...");
        this.container = container;
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        uml.ui.BuildAction.BuildDialog dlg = new uml.ui.BuildAction.BuildDialog(container.getFrame());
        dlg.show();
    }

    /**
     *
     * @class BuildDialog
     */
    protected class BuildDialog extends javax.swing.JDialog implements java.awt.event.ActionListener {
        protected javax.swing.JTextArea textArea = new javax.swing.JTextArea();

        protected javax.swing.JTextField currentPath = new javax.swing.JTextField();

        protected javax.swing.JCheckBox useArrays;

        protected uml.ui.BuildAction.BuildDialog.BrowseAction browseAction = new uml.ui.BuildAction.BuildDialog.BrowseAction();

        protected java.lang.String currentLanguage;

        public BuildDialog(java.awt.Frame frame) {
            super(frame, "Build source code", true);
            javax.swing.JPanel content = new javax.swing.JPanel();
            content.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
            java.awt.GridBagLayout gridbag = new java.awt.GridBagLayout();
            java.awt.GridBagConstraints gc = new java.awt.GridBagConstraints();
            content.setLayout(gridbag);
            javax.swing.JLabel lbl = new javax.swing.JLabel("Output:");
            gc.gridheight = 1;
            gc.gridwidth = 1;
            gc.weightx = 1.0;
            gc.weighty = 1.0;
            gc.fill = java.awt.GridBagConstraints.NONE;
            gc.anchor = java.awt.GridBagConstraints.WEST;
            gridbag.setConstraints(lbl, gc);
            content.add(lbl);
            currentPath = new javax.swing.JTextField();
            currentPath.setText(browseAction.getChooser().getLastDirectory());
            gc.gridwidth = 2;
            gc.anchor = java.awt.GridBagConstraints.CENTER;
            gc.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridbag.setConstraints(currentPath, gc);
            content.add(currentPath);
            javax.swing.JButton btn = new javax.swing.JButton(browseAction);
            btn.setFont(btn.getFont().deriveFont(java.awt.Font.PLAIN));
            gc.anchor = java.awt.GridBagConstraints.EAST;
            gc.gridwidth = java.awt.GridBagConstraints.REMAINDER;
            gc.fill = java.awt.GridBagConstraints.NONE;
            gc.weightx = 1.0;
            gridbag.setConstraints(btn, gc);
            content.add(btn);
            lbl = new javax.swing.JLabel("Language:");
            gc.gridheight = 1;
            gc.gridwidth = 1;
            gc.weightx = 1.0;
            gc.weighty = 1.0;
            gc.fill = java.awt.GridBagConstraints.NONE;
            gc.anchor = java.awt.GridBagConstraints.WEST;
            gridbag.setConstraints(lbl, gc);
            content.add(lbl);
            javax.swing.JComboBox box = new javax.swing.JComboBox(new java.lang.Object[]{ "Java", "C++" }) {
                protected void fireActionEvent() {
                    currentLanguage = getSelectedItem().toString();
                }
            };
            box.setFont(box.getFont().deriveFont(java.awt.Font.PLAIN));
            currentLanguage = box.getSelectedItem().toString();
            gc.gridwidth = java.awt.GridBagConstraints.REMAINDER;
            gridbag.setConstraints(box, gc);
            content.add(box);
            lbl = new javax.swing.JLabel("Use arrays:");
            gc.gridheight = 1;
            gc.gridwidth = 1;
            gc.weightx = 1.0;
            gc.weighty = 1.0;
            gc.fill = java.awt.GridBagConstraints.NONE;
            gc.anchor = java.awt.GridBagConstraints.WEST;
            gridbag.setConstraints(lbl, gc);
            content.add(lbl);
            useArrays = new javax.swing.JCheckBox();
            // box.setFont(box.getFont().deriveFont(Font.PLAIN));
            gc.gridwidth = java.awt.GridBagConstraints.REMAINDER;
            gridbag.setConstraints(useArrays, gc);
            content.add(useArrays);
            lbl = new javax.swing.JLabel("Messages:");
            gc.gridwidth = java.awt.GridBagConstraints.REMAINDER;
            gc.anchor = java.awt.GridBagConstraints.WEST;
            gridbag.setConstraints(lbl, gc);
            content.add(lbl);
            // Message panel
            javax.swing.JScrollPane pane = new javax.swing.JScrollPane(textArea);
            textArea.setEditable(false);
            browseAction.getChooser().setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
            gc.fill = java.awt.GridBagConstraints.BOTH;
            gc.gridheight = java.awt.GridBagConstraints.RELATIVE;
            gc.weighty = 20.0;
            gridbag.setConstraints(pane, gc);
            content.add(pane);
            // Add the build button
            javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.GridLayout(1, 2, 4, 4));
            btn = new javax.swing.JButton("Build");
            btn.addActionListener(this);
            buttonPanel.add(btn);
            btn = new javax.swing.JButton("Cancel");
            btn.addActionListener(this);
            buttonPanel.add(btn);
            gc.gridheight = java.awt.GridBagConstraints.REMAINDER;
            gc.weighty = 1;
            gridbag.setConstraints(buttonPanel, gc);
            buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
            content.add(buttonPanel);
            setContentPane(content);
            // Center on the frame
            int w = frame.getWidth();
            int h = frame.getHeight();
            int x = ((int) (frame.getX() + (w * 0.25)));
            int y = ((int) (frame.getY() + (h * 0.25)));
            setBounds(x, y, ((int) (w * 0.5)), ((int) (h * 0.5)));
        }

        protected uml.builder.CodeBuilder getBuilder() {
            java.lang.String path = currentPath.getText();
            if (currentLanguage.equals("C++"))
                return new uml.builder.CPlusPlusBuilder(path);
            else if (currentLanguage.equals("Java"))
                return new uml.builder.JavaBuilder(path);
            else
                throw new java.lang.RuntimeException("No builder available");

        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getActionCommand().equals("Build")) {
                // Build
                uml.builder.Context ctx = null;
                try {
                    ctx = new uml.builder.Context(container.getView().getModel());
                    ctx.enableArrays(useArrays.isSelected());
                    getBuilder().build(ctx);
                } catch (uml.builder.BuilderException x) {
                    /* Catch builder exceptions & stop */
                    java.lang.String msg = x.getMessage();
                    java.lang.String title = "I/O Error";
                    if (msg.startsWith("I/O Error: ")) {
                        javax.swing.JOptionPane.showMessageDialog(this, msg, title, javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                // Display the warnings & errors in the text area
                if (ctx != null) {
                    // Print warnings
                    for (java.util.Iterator i = ctx.getWarnings(); i.hasNext();) {
                        java.lang.String warning = ((java.lang.String) (i.next()));
                        append("warning: ");
                        append(warning);
                        append("\n");
                    }
                    append("\n");
                    // Print errors
                    for (java.util.Iterator i = ctx.getErrors(); i.hasNext();) {
                        java.lang.String error = ((java.lang.String) (i.next()));
                        append("error: ");
                        append(error);
                        append("\n");
                    }
                }
                // Print done msg & disable
                append("\nDONE!\n");
                // ((JButton)e.getSource()).setEnabled(false);
            } else
                dispose();

        }

        private final void append(java.lang.String msg) {
            try {
                javax.swing.text.Document doc = textArea.getDocument();
                doc.insertString(doc.getLength(), msg, null);
            } catch (javax.swing.text.BadLocationException e) {
                e.printStackTrace();
            }
        }

        protected class BrowseAction extends uml.ui.FileAction {
            public BrowseAction() {
                super("...");
            }

            public void actionPerformed(java.awt.event.ActionEvent e) {
                browseAction.getChooser().showDialog(uml.ui.BuildAction.BuildDialog.this, "Select a directory");
                currentPath.setText(browseAction.getChooser().getLastDirectory());
            }
        }
    }
}