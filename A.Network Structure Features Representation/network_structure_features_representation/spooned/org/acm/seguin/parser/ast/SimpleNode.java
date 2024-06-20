/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.parser.ast;
/**
 * This object is the base class for all items in the AST (abstract syntax
 *  tree).
 *
 * @author Chris Seguin
 * @created May 10, 1999
 */
public class SimpleNode implements org.acm.seguin.parser.Node {
    // Instance Variables
    /**
     * Description of the Field
     */
    protected org.acm.seguin.parser.Node parent;

    /**
     * Description of the Field
     */
    protected org.acm.seguin.parser.Node[] children;

    /**
     * Description of the Field
     */
    protected int id;

    /**
     * Description of the Field
     */
    protected org.acm.seguin.parser.JavaParser parser;

    /**
     * Description of the Field
     */
    protected java.util.Vector specials;

    /**
     * Constructor for the SimpleNode object
     *
     * @param i
     * 		Description of Parameter
     */
    public SimpleNode(int i) {
        id = i;
        specials = null;
    }

    /**
     * Constructor for the SimpleNode object
     *
     * @param p
     * 		Description of Parameter
     * @param i
     * 		Description of Parameter
     */
    public SimpleNode(org.acm.seguin.parser.JavaParser p, int i) {
        this(i);
        parser = p;
    }

    /**
     * Return the id for this node
     *
     * @return the id
     */
    public int getID() {
        return id;
    }

    /**
     * Gets the special associated with a particular key
     *
     * @param key
     * 		the key
     * @return the value
     */
    public org.acm.seguin.parser.Token getSpecial(java.lang.String key) {
        if ((specials == null) || (key == null)) {
            return null;
        }
        int last = specials.size();
        for (int ndx = 0; ndx < last; ndx++) {
            org.acm.seguin.parser.NamedToken named = ((org.acm.seguin.parser.NamedToken) (specials.elementAt(ndx)));
            if (named.check(key)) {
                return named.getToken();
            }
        }
        return null;
    }

    /**
     * Description of the Method
     */
    public void jjtOpen() {
    }

    /**
     * Description of the Method
     */
    public void jjtClose() {
    }

    /**
     * Description of the Method
     *
     * @param n
     * 		Description of Parameter
     */
    public void jjtSetParent(org.acm.seguin.parser.Node n) {
        parent = n;
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public org.acm.seguin.parser.Node jjtGetParent() {
        return parent;
    }

    /**
     * Description of the Method
     *
     * @param n
     * 		Description of Parameter
     * @param i
     * 		Description of Parameter
     */
    public void jjtAddChild(org.acm.seguin.parser.Node n, int i) {
        if (children == null) {
            children = new org.acm.seguin.parser.Node[i + 1];
        } else if (i >= children.length) {
            org.acm.seguin.parser.Node c[] = new org.acm.seguin.parser.Node[i + 1];
            java.lang.System.arraycopy(children, 0, c, 0, children.length);
            children = c;
        }
        children[i] = n;
        n.jjtSetParent(this);
    }

    /**
     * Insert the node numbered i
     *
     * @param i
     * 		The index of the node to remove
     * @param n
     * 		Description of Parameter
     */
    public void jjtInsertChild(org.acm.seguin.parser.Node n, int i) {
        if (children == null) {
            children = new org.acm.seguin.parser.Node[i + 1];
        } else {
            org.acm.seguin.parser.Node c[] = new org.acm.seguin.parser.Node[java.lang.Math.max(children.length + 1, i + 1)];
            java.lang.System.arraycopy(children, 0, c, 0, i);
            java.lang.System.arraycopy(children, i, c, i + 1, children.length - i);
            children = c;
        }
        // Store the node
        children[i] = n;
        n.jjtSetParent(this);
    }

    /**
     * Description of the Method
     *
     * @param i
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public org.acm.seguin.parser.Node jjtGetChild(int i) {
        return children[i];
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public int jjtGetNumChildren() {
        return children == null ? 0 : children.length;
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public boolean hasAnyChildren() {
        return (children != null) && (children.length > 0);
    }

    /**
     * Remove the node numbered i
     *
     * @param i
     * 		The index of the node to remove
     */
    public void jjtDeleteChild(int i) {
        if (((children == null) || (children.length < i)) || (i < 0)) {
            java.lang.System.out.println("Skipping this delete operation");
        } else {
            org.acm.seguin.parser.Node c[] = new org.acm.seguin.parser.Node[children.length - 1];
            java.lang.System.arraycopy(children, 0, c, 0, i);
            java.lang.System.arraycopy(children, i + 1, c, i, (children.length - i) - 1);
            children = c;
        }
    }

    /**
     * Description of the Method
     *
     * @param key
     * 		Description of Parameter
     * @param value
     * 		Description of Parameter
     */
    public void addSpecial(java.lang.String key, org.acm.seguin.parser.Token value) {
        if (value == null) {
            return;
        }
        if (specials == null) {
            init();
        }
        specials.addElement(new org.acm.seguin.parser.NamedToken(key, value));
    }

    /**
     * Removes a special associated with a key
     *
     * @param key
     * 		the special to remove
     */
    public void removeSpecial(java.lang.String key) {
        if ((specials == null) || (key == null)) {
            return;
        }
        int last = specials.size();
        for (int ndx = 0; ndx < last; ndx++) {
            org.acm.seguin.parser.NamedToken named = ((org.acm.seguin.parser.NamedToken) (specials.elementAt(ndx)));
            if (named.check(key)) {
                specials.removeElementAt(ndx);
                return;
            }
        }
    }

    /**
     * Accept the visitor.
     *
     * @param visitor
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object jjtAccept(org.acm.seguin.parser.JavaParserVisitor visitor, java.lang.Object data) {
        return visitor.visit(this, data);
    }

    /**
     * Accept the visitor.
     *
     * @param visitor
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object childrenAccept(org.acm.seguin.parser.JavaParserVisitor visitor, java.lang.Object data) {
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                children[i].jjtAccept(visitor, data);
            }
        }
        return data;
    }

    /* You can override these two methods in subclasses of SimpleNode to
    customize the way the node appears when the tree is dumped.  If
    your output uses more than one line you should override
    toString(String), otherwise overriding toString() is probably all
    you need to do.
     */
    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    public java.lang.String toString() {
        return org.acm.seguin.parser.JavaParserTreeConstants.jjtNodeName[id];
    }

    /**
     * Description of the Method
     *
     * @param prefix
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.String toString(java.lang.String prefix) {
        return prefix + toString();
    }

    /* Override this method if you want to customize how the node dumps
    out its children.
     */
    /**
     * Description of the Method
     *
     * @param prefix
     * 		Description of Parameter
     */
    public void dump(java.lang.String prefix) {
        java.lang.System.out.println(prefix + getClass().getName());
        if (children != null) {
            for (int i = 0; i < children.length; ++i) {
                org.acm.seguin.parser.ast.SimpleNode n = ((org.acm.seguin.parser.ast.SimpleNode) (children[i]));
                if (n != null) {
                    n.dump(prefix + " ");
                }
            }
        }
    }

    /**
     * Initializes any variables that are not required
     */
    protected void init() {
        if (specials == null) {
            specials = new java.util.Vector();
        }
    }

    /**
     * Is javadoc required?
     */
    public boolean isRequired() {
        return false;
    }
}