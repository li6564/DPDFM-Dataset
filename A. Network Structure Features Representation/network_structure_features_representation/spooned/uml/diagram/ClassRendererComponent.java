package uml.diagram;
/**
 *
 * @class ClassRendererComponent
 * @author Eric Crahen
 */
public class ClassRendererComponent extends uml.diagram.CustomComponent {
    protected static final uml.diagram.CustomUI classUI = new uml.diagram.CustomUI("class");

    protected static final java.awt.Insets margin = new java.awt.Insets(1, 1, 1, 1);

    protected javax.swing.JTextField title = new javax.swing.JTextField();

    protected uml.ui.FlatTextArea fields = new uml.ui.FlatTextArea(true);

    protected uml.ui.FlatTextArea members = new uml.ui.FlatTextArea(true);

    protected int divider = -1;

    static {
        // Set up some default colors
        javax.swing.UIManager.put("class.background", new java.awt.Color(0xff, 0xff, 0xdd));
        javax.swing.UIManager.put("class.foreground", java.awt.Color.black);
        javax.swing.UIManager.put("class.border", javax.swing.BorderFactory.createLineBorder(java.awt.Color.black, 1));
    }

    /**
     * Create a new Component for painting classes
     */
    public ClassRendererComponent() {
        // Layout the component
        this.setLayout(null);
        // Title area
        title.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black, 1));
        title.setOpaque(true);
        title.setMargin(uml.diagram.ClassRendererComponent.margin);
        title.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        this.add(title);
        // Field text area
        fields.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black, 1));
        fields.setMargin(uml.diagram.ClassRendererComponent.margin);
        this.add(fields);
        // Member text area
        members.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black, 1));
        members.setMargin(uml.diagram.ClassRendererComponent.margin);
        this.add(members);
        setUI(uml.diagram.ClassRendererComponent.classUI);
    }

    public void setTitle(java.lang.String s) {
        title.setText(s);
    }

    public java.lang.String getTitle() {
        return title.getText();
    }

    public void setFields(java.lang.String s) {
        fields.setText(s);
    }

    public java.lang.String getFields() {
        return fields.getText();
    }

    public void setMembers(java.lang.String s) {
        members.setText(s);
    }

    public java.lang.String getMembers() {
        return members.getText();
    }

    public void setDivider(int divider) {
        this.divider = divider;
    }

    /**
     * Create a built in layout, there seems to be a bug with current LayoutManagers
     * placing TextAreas in scroll panes with borders in the same component correctly.
     * They leave an extra pixel at the bottom with the hieght would be an odd number.
     *
     * This will garuntee the component will be laid out as expected.
     */
    public void doLayout() {
        java.awt.Insets insets = this.getInsets();
        int w = this.getWidth() - (insets.left + insets.right);
        int h = this.getHeight() - (insets.top + insets.bottom);
        int x = insets.left;
        int y = insets.top;
        // Layout the title across the top
        int componentHeight = title.getPreferredSize().height + 2;
        title.reshape(x, y, w, componentHeight);
        // Shift down
        y += componentHeight;
        h -= componentHeight;
        // Layout the fields in the middle
        // componentHeight = (divider == -1) ? (int)((double)h*(3.0/8.0)) : divider;
        componentHeight = (divider == (-1)) ? fields.getPreferredSize().height + 2 : divider + 1;
        fields.reshape(x, y, w, componentHeight);
        // Shift down
        y += componentHeight;
        h -= componentHeight;
        // Layout the members at the bottom
        componentHeight = h;
        members.reshape(x, y, w, componentHeight);
    }
}