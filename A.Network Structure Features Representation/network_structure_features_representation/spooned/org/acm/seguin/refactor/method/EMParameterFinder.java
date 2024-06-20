/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;
/**
 * Creates a list of arguments to the extacted method
 *
 * @author Chris Seguin
 */
public class EMParameterFinder {
    private org.acm.seguin.summary.FileSummary main;

    private org.acm.seguin.summary.FileSummary extract;

    private org.acm.seguin.parser.Node found;

    private java.util.LinkedList list;

    private java.util.LinkedList cantBe;

    private java.util.LinkedList parameters;

    private java.util.LinkedList returnTypes;

    /**
     * Constructor for the EMParameterFinder object
     */
    public EMParameterFinder() {
        list = new java.util.LinkedList();
        cantBe = new java.util.LinkedList();
        parameters = new java.util.LinkedList();
        returnTypes = new java.util.LinkedList();
    }

    /**
     * Sets the MainFileSummary attribute of the EMParameterFinder object
     *
     * @param value
     * 		The new MainFileSummary value
     */
    public void setMainFileSummary(org.acm.seguin.summary.FileSummary value) {
        main = value;
    }

    /**
     * Sets the ExtractFileSummary attribute of the EMParameterFinder object
     *
     * @param value
     * 		The new ExtractFileSummary value
     */
    public void setExtractFileSummary(org.acm.seguin.summary.FileSummary value) {
        extract = value;
    }

    /**
     * Sets the Location attribute of the EMParameterFinder object
     *
     * @param value
     * 		The new Location value
     */
    public void setLocation(org.acm.seguin.parser.Node value) {
        found = value;
    }

    /**
     * Sets the order of the parameters
     *
     * @param data
     * 		The new ParameterOrder value
     */
    public void setParameterOrder(java.lang.Object[] data) {
        parameters = new java.util.LinkedList();
        for (int ndx = 0; ndx < data.length; ndx++) {
            parameters.add(data[ndx]);
        }
    }

    /**
     * Gets the list of parameters
     *
     * @return The List value
     */
    public java.util.LinkedList getList() {
        return parameters;
    }

    /**
     * Main processing method for the EMParameterFinder object
     */
    public void run() {
        org.acm.seguin.summary.TypeSummary type = ((org.acm.seguin.summary.TypeSummary) (extract.getTypes().next()));
        org.acm.seguin.summary.MethodSummary method = ((org.acm.seguin.summary.MethodSummary) (type.getMethods().next()));
        org.acm.seguin.summary.MethodSummary mainMethod = find();
        java.util.Iterator iter = method.getDependencies();
        if (iter == null) {
            return;
        }
        while (iter.hasNext()) {
            java.lang.Object next = iter.next();
            if (next instanceof org.acm.seguin.summary.FieldAccessSummary) {
                org.acm.seguin.summary.FieldAccessSummary fas = ((org.acm.seguin.summary.FieldAccessSummary) (next));
                java.lang.String fieldName = fas.getFirstObject();
                updateLists(fieldName, mainMethod);
                if (fas.isAssignment()) {
                    org.acm.seguin.summary.VariableSummary paramSummary = find(fieldName, mainMethod);
                    if (paramSummary != null) {
                        addReturnType(paramSummary);
                    }
                }
            } else if (next instanceof org.acm.seguin.summary.MessageSendSummary) {
                org.acm.seguin.summary.MessageSendSummary mss = ((org.acm.seguin.summary.MessageSendSummary) (next));
                java.lang.String name = mss.getFirstObject();
                updateLists(name, mainMethod);
            } else if (next instanceof org.acm.seguin.summary.LocalVariableSummary) {
                org.acm.seguin.summary.LocalVariableSummary lvs = ((org.acm.seguin.summary.LocalVariableSummary) (next));
                cantBe.add(lvs.getName());
                addReturnType(lvs);
            } else if (next instanceof org.acm.seguin.summary.ParameterSummary) {
                org.acm.seguin.summary.ParameterSummary param = ((org.acm.seguin.summary.ParameterSummary) (next));
                cantBe.add(param.getName());
            }
        } 
    }

    /**
     * Returns the list of possible return types
     *
     * @return The ReturnTypes value
     */
    java.lang.Object[] getReturnTypes() {
        java.lang.Object[] result = new java.lang.Object[returnTypes.size() + 1];
        java.util.Iterator iter = returnTypes.iterator();
        result[0] = "void";
        int count = 1;
        while (iter.hasNext()) {
            result[count] = iter.next();
            count++;
        } 
        return result;
    }

    /**
     * Determines if the method summary and the method parse tree are the same
     *
     * @param methodSummary
     * 		the summary in question
     * @param classBodyDecl
     * 		Description of Parameter
     * @return true if they appear to be the same
     */
    private boolean isSame(org.acm.seguin.summary.MethodSummary methodSummary, org.acm.seguin.parser.ast.ASTClassBodyDeclaration classBodyDecl) {
        org.acm.seguin.parser.Node child = classBodyDecl.jjtGetChild(0);
        if (child instanceof org.acm.seguin.parser.ast.ASTMethodDeclaration) {
            org.acm.seguin.parser.ast.ASTMethodDeclarator decl = ((org.acm.seguin.parser.ast.ASTMethodDeclarator) (child.jjtGetChild(1)));
            if (decl.getName().equals(methodSummary.getName())) {
                org.acm.seguin.parser.ast.ASTFormalParameters params = ((org.acm.seguin.parser.ast.ASTFormalParameters) (decl.jjtGetChild(0)));
                return isParametersSame(params, methodSummary);
            }
            return false;
        }
        if (child instanceof org.acm.seguin.parser.ast.ASTConstructorDeclaration) {
            org.acm.seguin.parser.ast.ASTConstructorDeclaration decl = ((org.acm.seguin.parser.ast.ASTConstructorDeclaration) (child));
            if (methodSummary.isConstructor()) {
                org.acm.seguin.parser.ast.ASTFormalParameters params = ((org.acm.seguin.parser.ast.ASTFormalParameters) (decl.jjtGetChild(0)));
                return isParametersSame(params, methodSummary);
            }
            return false;
        }
        if (child instanceof org.acm.seguin.parser.ast.ASTInitializer) {
            return methodSummary.isInitializer();
        }
        return false;
    }

    /**
     * Gets the ParametersSame attribute of the EMParameterFinder object
     *
     * @param methodSummary
     * 		Description of Parameter
     * @param params
     * 		Description of Parameter
     * @return The ParametersSame value
     */
    private boolean isParametersSame(org.acm.seguin.parser.Node params, org.acm.seguin.summary.MethodSummary methodSummary) {
        if (params.jjtGetNumChildren() == methodSummary.getParameterCount()) {
            if (methodSummary.getParameterCount() == 0) {
                return true;
            }
            int count = 0;
            java.util.Iterator iter = methodSummary.getParameters();
            while (iter.hasNext()) {
                org.acm.seguin.summary.ParameterSummary next = ((org.acm.seguin.summary.ParameterSummary) (iter.next()));
                org.acm.seguin.parser.Node nextNode = params.jjtGetChild(count);
                org.acm.seguin.parser.ast.ASTVariableDeclaratorId paramName = ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (nextNode.jjtGetChild(1)));
                java.lang.String nextName = next.getName();
                java.lang.String parameterID = paramName.getName();
                if (!nextName.equals(parameterID)) {
                    return false;
                }
                count++;
            } 
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the lists have variables with the same names in it
     *
     * @param var
     * 		Description of Parameter
     * @param list
     * 		Description of Parameter
     * @return The Contained value
     */
    private boolean isContained(org.acm.seguin.summary.VariableSummary var, java.util.LinkedList list) {
        java.util.Iterator iter = list.iterator();
        while (iter.hasNext()) {
            java.lang.Object obj = iter.next();
            if (obj instanceof org.acm.seguin.summary.VariableSummary) {
                org.acm.seguin.summary.VariableSummary next = ((org.acm.seguin.summary.VariableSummary) (obj));
                if (next.getName().equals(var.getName())) {
                    return true;
                }
            }
        } 
        return false;
    }

    /**
     * Finds the object we extracted the code from
     *
     * @return the portion of the parse tree
     */
    private org.acm.seguin.parser.ast.ASTClassBodyDeclaration findDecl() {
        org.acm.seguin.parser.Node current = found;
        while (!(current instanceof org.acm.seguin.parser.ast.ASTClassBodyDeclaration)) {
            current = current.jjtGetParent();
            if (current == null) {
                return null;
            }
        } 
        return ((org.acm.seguin.parser.ast.ASTClassBodyDeclaration) (current));
    }

    /**
     * Finds the method summary
     *
     * @return the method summary
     */
    private org.acm.seguin.summary.MethodSummary find() {
        org.acm.seguin.parser.ast.ASTClassBodyDeclaration classBodyDecl = findDecl();
        if (classBodyDecl == null) {
            return null;
        }
        java.util.Iterator typeIterator = main.getTypes();
        while (typeIterator.hasNext()) {
            org.acm.seguin.summary.TypeSummary nextType = ((org.acm.seguin.summary.TypeSummary) (typeIterator.next()));
            java.util.Iterator methodIterator = nextType.getMethods();
            while (methodIterator.hasNext()) {
                org.acm.seguin.summary.MethodSummary nextMethod = ((org.acm.seguin.summary.MethodSummary) (methodIterator.next()));
                if (isSame(nextMethod, classBodyDecl)) {
                    return nextMethod;
                }
            } 
        } 
        return null;
    }

    /**
     * Searches for a variable declaration of a variable named name in the
     *  method summary method
     *
     * @param name
     * 		the variable name
     * @param method
     * 		the method we are searching in
     * @return the variable summary if one is found
     */
    private org.acm.seguin.summary.VariableSummary find(java.lang.String name, org.acm.seguin.summary.MethodSummary method) {
        if (method == null) {
            return null;
        }
        java.util.Iterator iter = method.getParameters();
        while ((iter != null) && iter.hasNext()) {
            org.acm.seguin.summary.ParameterSummary param = ((org.acm.seguin.summary.ParameterSummary) (iter.next()));
            if (param.getName().equals(name)) {
                return param;
            }
        } 
        iter = method.getDependencies();
        while ((iter != null) && iter.hasNext()) {
            java.lang.Object next = iter.next();
            if (next instanceof org.acm.seguin.summary.LocalVariableSummary) {
                org.acm.seguin.summary.LocalVariableSummary lvs = ((org.acm.seguin.summary.LocalVariableSummary) (next));
                if (lvs.getName().equals(name)) {
                    return lvs;
                }
            } else if (next instanceof org.acm.seguin.summary.ParameterSummary) {
                org.acm.seguin.summary.ParameterSummary ps = ((org.acm.seguin.summary.ParameterSummary) (next));
                if (ps.getName().equals(name)) {
                    return ps;
                }
            }
        } 
        return null;
    }

    /**
     * Updates the list of parameters for the extracted method
     *
     * @param name
     * 		the method to add to the various lists
     * @param mainMethod
     * 		the method summary
     */
    private void updateLists(java.lang.String name, org.acm.seguin.summary.MethodSummary mainMethod) {
        if (name == null) {
            // Do nothing
        } else if (cantBe.contains(name)) {
            // System.out.println("---" + name + " c");
        } else if (!list.contains(name)) {
            // System.out.println("Variable:  " + name);
            list.add(name);
            org.acm.seguin.summary.VariableSummary paramSummary = find(name, mainMethod);
            if (paramSummary != null) {
                // System.out.println("\t***");
                parameters.add(paramSummary);
            }
        } else {
            // System.out.println("---" + name + " a");
        }
    }

    /**
     * Adds a return type to the list
     *
     * @param var
     * 		the variable summary to add
     */
    private void addReturnType(org.acm.seguin.summary.VariableSummary var) {
        if (!isContained(var, returnTypes)) {
            returnTypes.add(var);
        }
    }
}