/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.pretty.ai;
/**
 * Basis for the artificial intelligence that analyzes the method and
 *  determines the appropriate javadoc descriptions
 *
 * @author Chris Seguin
 */
public class MethodAnalyzer {
    private org.acm.seguin.parser.ast.ASTMethodDeclaration node;

    private org.acm.seguin.pretty.JavaDocableImpl jdi;

    /**
     * Constructor for the MethodAnalyzer object
     *
     * @param node
     * 		Description of Parameter
     * @param jdi
     * 		Description of Parameter
     */
    public MethodAnalyzer(org.acm.seguin.parser.ast.ASTMethodDeclaration node, org.acm.seguin.pretty.JavaDocableImpl jdi) {
        this.node = node;
        this.jdi = jdi;
    }

    /**
     * Makes sure all the java doc components are present. For methods and
     *  constructors we need to do more work - checking parameters, return types,
     *  and exceptions.
     *
     * @param className
     * 		Description of Parameter
     */
    public void finish(java.lang.String className) {
        // Get the resource bundle
        org.acm.seguin.util.FileSettings bundle = org.acm.seguin.util.FileSettings.getSettings("Refactory", "pretty");
        // Require a description of this method
        requireDescription(bundle, className);
        java.lang.String methodTags = "return,param,exception";
        try {
            methodTags = bundle.getString("method.tags");
        } catch (org.acm.seguin.util.MissingSettingsException mse) {
        }
        // Check that if there is a return type
        if (methodTags.indexOf("return") >= 0) {
            finishReturn(bundle);
        }
        // Check for parameters
        if (methodTags.indexOf("param") >= 0) {
            finishParameters(bundle);
        }
        // Check for exceptions
        if (methodTags.indexOf("exception") >= 0) {
            finishExceptions(bundle);
        }
    }

    /**
     * Determine if this is a setter method
     *
     * @return true if it is a setter
     */
    private boolean isSetter() {
        java.lang.String name = getName();
        return ((name.length() > 3) && name.startsWith("set")) && java.lang.Character.isUpperCase(name.charAt(3));
    }

    /**
     * Determine if this is a getter method
     *
     * @return true if it is a getter
     */
    private boolean isGetter() {
        java.lang.String name = getName();
        return ((name.length() > 3) && (name.startsWith("get") && java.lang.Character.isUpperCase(name.charAt(3)))) || (((name.length() > 2) && name.startsWith("is")) && java.lang.Character.isUpperCase(name.charAt(2)));
    }

    /**
     * Determine if this is a getter method
     *
     * @return true if it is a getter
     */
    private boolean isAdder() {
        java.lang.String name = getName();
        return (name.length() > 3) && (name.startsWith("add") && java.lang.Character.isUpperCase(name.charAt(3)));
    }

    /**
     * Determine if this is a run method
     *
     * @return true if it is a run method
     */
    private boolean isRunMethod() {
        java.lang.String name = getName();
        return name.equals("run");
    }

    /**
     * Gets the MainMethod attribute of the MethodAnalyzer object
     *
     * @return The MainMethod value
     */
    private boolean isMainMethod() {
        java.lang.String name = getName();
        if (!(name.equals("main") && node.isStatic())) {
            return false;
        }
        // Check for the void return type
        org.acm.seguin.parser.ast.ASTResultType result = ((org.acm.seguin.parser.ast.ASTResultType) (node.jjtGetChild(0)));
        if (result.hasAnyChildren()) {
            return false;
        }
        // Check the parameters
        org.acm.seguin.parser.ast.ASTMethodDeclarator decl = ((org.acm.seguin.parser.ast.ASTMethodDeclarator) (node.jjtGetChild(1)));
        org.acm.seguin.parser.ast.ASTFormalParameters params = ((org.acm.seguin.parser.ast.ASTFormalParameters) (decl.jjtGetChild(0)));
        int childCount = params.jjtGetNumChildren();
        if (childCount != 1) {
            return false;
        }
        org.acm.seguin.parser.ast.ASTFormalParameter nextParam = ((org.acm.seguin.parser.ast.ASTFormalParameter) (params.jjtGetChild(0)));
        org.acm.seguin.parser.ast.ASTType type = ((org.acm.seguin.parser.ast.ASTType) (nextParam.jjtGetChild(0)));
        if (type.getArrayCount() != 1) {
            return false;
        }
        org.acm.seguin.parser.Node child = type.jjtGetChild(0);
        if (child instanceof org.acm.seguin.parser.ast.ASTName) {
            org.acm.seguin.parser.ast.ASTName nameNode = ((org.acm.seguin.parser.ast.ASTName) (child));
            if (nameNode.getName().equals("String") || nameNode.getName().equals("java.lang.String")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if this is a JUnit setUp method
     *
     * @return true if it is a JUnit setUp method
     */
    private boolean isJUnitSetupMethod() {
        java.lang.String name = getName();
        return name.equals("setUp");
    }

    /**
     * Determine if this is a JUnit test method
     *
     * @return true if it is a JUnit test method
     */
    private boolean isJUnitTestMethod() {
        java.lang.String name = getName();
        return name.startsWith("test");
    }

    /**
     * Determine if this is a JUnit tearDown method
     *
     * @return true if it is a JUnit tearDown method
     */
    private boolean isJUnitTeardownMethod() {
        java.lang.String name = getName();
        return name.equals("tearDown");
    }

    /**
     * Determine if this is a JUnit suite method
     *
     * @return true if it is a JUnit suite method
     */
    private boolean isJUnitSuiteMethod() {
        java.lang.String name = getName();
        return name.equals("suite");
    }

    /**
     * Returns the name of the method
     *
     * @return the name
     */
    private java.lang.String getName() {
        org.acm.seguin.parser.ast.ASTMethodDeclarator decl = ((org.acm.seguin.parser.ast.ASTMethodDeclarator) (node.jjtGetChild(1)));
        return decl.getName();
    }

    /**
     * Guesses the name ofthe setter or getter's attribute
     *
     * @return the attribute name
     */
    private java.lang.String getAttributeName() {
        java.lang.String name = getName();
        if (((!isGetter()) && (!isSetter())) && (!isAdder())) {
            return "";
        } else if (name.startsWith("is")) {
            return name.substring(2);
        } else {
            return name.substring(3);
        }
    }

    /**
     * Gets the ParameterDescription attribute of the MethodAnalyzer object
     *
     * @param bundle
     * 		Description of Parameter
     * @param param
     * 		Description of Parameter
     * @return The ParameterDescription value
     */
    private java.lang.String getParameterDescription(org.acm.seguin.util.FileSettings bundle, java.lang.String param) {
        java.lang.String pattern = "";
        if (isSetter()) {
            pattern = bundle.getString("setter.param.descr");
        } else if (isAdder()) {
            pattern = bundle.getString("adder.param.descr");
        } else if (isMainMethod()) {
            pattern = bundle.getString("main.param.descr");
        } else {
            pattern = bundle.getString("param.descr");
        }
        return createDescription(pattern, getAttributeName(), param);
    }

    /**
     * Gets the ReturnDescription attribute of the MethodAnalyzer object
     *
     * @param bundle
     * 		Description of Parameter
     * @return The ReturnDescription value
     */
    private java.lang.String getReturnDescription(org.acm.seguin.util.FileSettings bundle) {
        java.lang.String pattern = "";
        if (isJUnitSuiteMethod()) {
            pattern = bundle.getString("junit.suite.return.descr");
        } else if (isGetter()) {
            pattern = bundle.getString("getter.return.descr");
        } else {
            pattern = bundle.getString("return.descr");
        }
        return createDescription(pattern, getAttributeName(), "");
    }

    /**
     * Description of the Method
     *
     * @param bundle
     * 		Description of Parameter
     */
    private void finishReturn(org.acm.seguin.util.FileSettings bundle) {
        org.acm.seguin.parser.ast.ASTResultType result = ((org.acm.seguin.parser.ast.ASTResultType) (node.jjtGetChild(0)));
        if (result.hasAnyChildren()) {
            if (!jdi.contains("@return")) {
                jdi.require("@return", getReturnDescription(bundle));
            }
        }
    }

    /**
     * Description of the Method
     *
     * @param bundle
     * 		Description of Parameter
     */
    private void finishParameters(org.acm.seguin.util.FileSettings bundle) {
        org.acm.seguin.parser.ast.ASTMethodDeclarator decl = ((org.acm.seguin.parser.ast.ASTMethodDeclarator) (node.jjtGetChild(1)));
        org.acm.seguin.parser.ast.ASTFormalParameters params = ((org.acm.seguin.parser.ast.ASTFormalParameters) (decl.jjtGetChild(0)));
        int childCount = params.jjtGetNumChildren();
        for (int ndx = 0; ndx < childCount; ndx++) {
            org.acm.seguin.parser.ast.ASTFormalParameter nextParam = ((org.acm.seguin.parser.ast.ASTFormalParameter) (params.jjtGetChild(ndx)));
            org.acm.seguin.parser.ast.ASTVariableDeclaratorId id = ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (nextParam.jjtGetChild(1)));
            if (!jdi.contains("@param", id.getName())) {
                jdi.require("@param", id.getName(), getParameterDescription(bundle, id.getName()));
            }
        }
    }

    /**
     * Description of the Method
     *
     * @param bundle
     * 		Description of Parameter
     */
    private void finishExceptions(org.acm.seguin.util.FileSettings bundle) {
        if ((node.jjtGetNumChildren() > 2) && (node.jjtGetChild(2) instanceof org.acm.seguin.parser.ast.ASTNameList)) {
            java.lang.String exceptionTagName = "@exception";
            try {
                exceptionTagName = bundle.getString("exception.tag.name");
                if (exceptionTagName.length() == 0)
                    exceptionTagName = "@exception";
                else if (exceptionTagName.charAt(0) != '@')
                    exceptionTagName = "@" + exceptionTagName;

            } catch (org.acm.seguin.util.MissingSettingsException mse) {
            }
            org.acm.seguin.parser.ast.ASTNameList exceptions = ((org.acm.seguin.parser.ast.ASTNameList) (node.jjtGetChild(2)));
            int childCount = exceptions.jjtGetNumChildren();
            for (int ndx = 0; ndx < childCount; ndx++) {
                org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (exceptions.jjtGetChild(ndx)));
                if ((!jdi.contains("@exception", name.getName())) && (!jdi.contains("@throws", name.getName()))) {
                    jdi.require(exceptionTagName, name.getName(), bundle.getString("exception.descr"));
                }
            }
        }
    }

    /**
     * Create the description string
     *
     * @param pattern
     * 		Description of Parameter
     * @param attribute
     * 		Description of Parameter
     * @param className
     * 		Description of Parameter
     * @return the expanded string
     */
    private java.lang.String createDescription(java.lang.String pattern, java.lang.String attribute, java.lang.String className) {
        // Description of the constructor
        java.lang.Object[] nameArray = new java.lang.Object[4];
        nameArray[0] = attribute;
        nameArray[1] = className;
        if (node.isStatic()) {
            nameArray[2] = "class";
        } else {
            nameArray[2] = "object";
        }
        nameArray[3] = lowerCaseFirstLetter(attribute);
        java.lang.String msg = java.text.MessageFormat.format(pattern, nameArray);
        return msg;
    }

    /**
     * Require the description
     *
     * @param bundle
     * 		Description of Parameter
     * @param className
     * 		Description of Parameter
     */
    private void requireDescription(org.acm.seguin.util.FileSettings bundle, java.lang.String className) {
        java.lang.String pattern = "";
        if (isJUnitSetupMethod()) {
            pattern = bundle.getString("junit.setUp.descr");
        } else if (isJUnitTestMethod()) {
            pattern = bundle.getString("junit.test.descr");
        } else if (isJUnitTeardownMethod()) {
            pattern = bundle.getString("junit.tearDown.descr");
        } else if (isJUnitSuiteMethod()) {
            pattern = bundle.getString("junit.suite.descr");
        } else if (isGetter()) {
            pattern = bundle.getString("getter.descr");
        } else if (isSetter()) {
            pattern = bundle.getString("setter.descr");
        } else if (isRunMethod()) {
            pattern = bundle.getString("run.descr");
        } else if (isMainMethod()) {
            pattern = bundle.getString("main.descr");
        } else if (isAdder()) {
            pattern = bundle.getString("adder.descr");
        } else {
            pattern = bundle.getString("method.descr");
        }
        java.lang.String msg = createDescription(pattern, getAttributeName(), className);
        jdi.require("", msg);
    }

    /**
     * Description of the Method
     *
     * @param value
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private java.lang.String lowerCaseFirstLetter(java.lang.String value) {
        if ((value == null) || (value.length() == 0)) {
            return "";
        }
        if (value.length() == 1) {
            return value.toLowerCase();
        }
        return java.lang.Character.toLowerCase(value.charAt(0)) + value.substring(1);
    }
}