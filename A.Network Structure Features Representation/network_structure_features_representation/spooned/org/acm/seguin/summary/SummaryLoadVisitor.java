/* Author:  Chris Seguin

This software has been developed under the copyleft
rules of the GNU General Public License.  Please
consult the GNU General Public License for more
details about use and distribution of this software.
 */
package org.acm.seguin.summary;
/**
 * This object visits an abstract syntax tree with the purpose of gathering
 *  summary information.
 *
 * @author Chris Seguin
 * @created May 30, 1999
 */
public class SummaryLoadVisitor extends org.acm.seguin.summary.LineCountVisitor {
    private int anonCount = 1;

    /**
     * Visits a package declaration
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPackageDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        // Get the name
        org.acm.seguin.parser.ast.ASTName name = ((org.acm.seguin.parser.ast.ASTName) (node.jjtGetChild(0)));
        // Lookup the summary
        org.acm.seguin.summary.PackageSummary packageSummary = org.acm.seguin.summary.PackageSummary.getPackageSummary(name.getName());
        // Create the file summary
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.INITIALIZE) {
            state.startSummary(new org.acm.seguin.summary.FileSummary(packageSummary, state.getFile()));
            state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_FILE);
        }
        return super.visit(node, data);
    }

    /**
     * Visits an import statement
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTImportDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        // Get the current file summary - add the import
        org.acm.seguin.summary.FileSummary current = ((org.acm.seguin.summary.FileSummary) (state.getCurrentSummary()));
        org.acm.seguin.summary.ImportSummary importSummary = new org.acm.seguin.summary.ImportSummary(current, node);
        current.add(importSummary);
        int start = getLineCount() + 1;
        // Done
        java.lang.Object obj = super.visit(node, data);
        int end = getLineCount();
        importSummary.setStartLine(start);
        importSummary.setEndLine(end);
        return obj;
    }

    /**
     * Visits a type declaration
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTTypeDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        // Get the current file summary
        org.acm.seguin.summary.FileSummary current = ((org.acm.seguin.summary.FileSummary) (state.getCurrentSummary()));
        // Create Type Summary
        org.acm.seguin.summary.TypeSummary next = new org.acm.seguin.summary.TypeSummary(current, node);
        current.add(next);
        // Set the next type summary as the current summary
        state.startSummary(next);
        int oldCode = state.getCode();
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_TYPE);
        // Traverse Children
        super.visit(node, data);
        // Back to loading the file
        state.finishSummary();
        state.setCode(oldCode);
        int end = getLineCount();
        next.setStartLine(start);
        next.setEndLine(end);
        // Done
        return data;
    }

    /**
     * Visits a class declaration
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        // Get the current type summary
        org.acm.seguin.summary.TypeSummary current = ((org.acm.seguin.summary.TypeSummary) (state.getCurrentSummary()));
        // Save the modifiers
        current.setModifiers(node.getModifiers());
        // Traverse the children
        return super.visit(node, data);
    }

    /**
     * Visits a class declaration
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        int code = state.getCode();
        // Get the current type summary
        org.acm.seguin.summary.Summary currentSummary = state.getCurrentSummary();
        org.acm.seguin.summary.TypeSummary current;
        if (currentSummary instanceof org.acm.seguin.summary.TypeSummary) {
            current = ((org.acm.seguin.summary.TypeSummary) (currentSummary));
        } else {
            // Create Type Summary
            current = new org.acm.seguin.summary.TypeSummary(currentSummary, node);
            ((org.acm.seguin.summary.MethodSummary) (currentSummary)).addDependency(current);
            state.startSummary(current);
        }
        // Remember the name
        current.setName(node.getName().intern());
        // Iterate through the children
        // Add the class body
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_TYPE);
        super.visit(node, data);
        state.setCode(code);
        // This is a class
        current.setInterface(false);
        if (currentSummary instanceof org.acm.seguin.summary.TypeSummary) {
        } else {
            state.finishSummary();
        }
        return data;
    }

    /**
     * Visit the items in the class body
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTClassBody node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        int oldCode = state.getCode();
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_CLASSBODY);
        super.visit(node, data);
        state.setCode(oldCode);
        return data;
    }

    /**
     * Visit a class that is nested in another class
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNestedClassDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        int code = state.getCode();
        // Get the current type summary
        org.acm.seguin.summary.Summary current = state.getCurrentSummary();
        org.acm.seguin.summary.TypeSummary nested = new org.acm.seguin.summary.TypeSummary(current, node);
        // Add it in
        if (current instanceof org.acm.seguin.summary.TypeSummary) {
            ((org.acm.seguin.summary.TypeSummary) (current)).add(nested);
        } else {
            ((org.acm.seguin.summary.MethodSummary) (current)).addDependency(nested);
        }
        // Continue deeper
        state.startSummary(nested);
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_TYPE);
        // Save the modifiers
        nested.setModifiers(node.getModifiers());
        // Traverse the children
        super.visit(node, data);
        int end = getLineCount();
        nested.setStartLine(start);
        nested.setEndLine(end);
        // Finish the summary
        state.finishSummary();
        state.setCode(code);
        // Return something
        return data;
    }

    /**
     * Visit an interface declaration
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInterfaceDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        // Get the current type summary
        org.acm.seguin.summary.TypeSummary current = ((org.acm.seguin.summary.TypeSummary) (state.getCurrentSummary()));
        // Save the modifiers
        current.setModifiers(node.getModifiers());
        // Traverse the children
        return super.visit(node, data);
    }

    /**
     * Visit an interface that is nested in a class
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNestedInterfaceDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        int code = state.getCode();
        // Get the current type summary
        org.acm.seguin.summary.Summary current = state.getCurrentSummary();
        // Create the new nested type summary
        org.acm.seguin.summary.TypeSummary nested = new org.acm.seguin.summary.TypeSummary(current, node);
        // Add it in
        if (current instanceof org.acm.seguin.summary.TypeSummary) {
            ((org.acm.seguin.summary.TypeSummary) (current)).add(nested);
        } else {
            ((org.acm.seguin.summary.MethodSummary) (current)).addDependency(nested);
        }
        // Continue deeper
        state.startSummary(nested);
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_TYPE);
        // Save the modifiers
        nested.setModifiers(node.getModifiers());
        // Traverse the children
        super.visit(node, data);
        int end = getLineCount();
        nested.setStartLine(start);
        nested.setEndLine(end);
        // Finish the summary
        state.finishSummary();
        state.setCode(code);
        // Return something
        return data;
    }

    /**
     * Visit an interface
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTUnmodifiedInterfaceDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        int code = state.getCode();
        // Get the current type summary
        org.acm.seguin.summary.Summary currentSummary = state.getCurrentSummary();
        org.acm.seguin.summary.TypeSummary current;
        if (currentSummary instanceof org.acm.seguin.summary.TypeSummary) {
            current = ((org.acm.seguin.summary.TypeSummary) (currentSummary));
        } else {
            // Create Type Summary
            current = new org.acm.seguin.summary.TypeSummary(currentSummary, node);
            ((org.acm.seguin.summary.MethodSummary) (currentSummary)).addDependency(current);
            state.startSummary(current);
        }
        // Remember the name
        current.setName(node.getName().intern());
        // Iterate through the children of the interface
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_TYPE);
        super.visit(node, data);
        state.setCode(code);
        // This is an interface
        current.setInterface(true);
        if (currentSummary instanceof org.acm.seguin.summary.TypeSummary) {
        } else {
            state.finishSummary();
        }
        // Done
        return data;
    }

    /**
     * Visit the body of an interface
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInterfaceBody node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        int oldCode = state.getCode();
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_CLASSBODY);
        super.visit(node, data);
        state.setCode(oldCode);
        return data;
    }

    /**
     * Visit a field declaration
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTFieldDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        int code = state.getCode();
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.IGNORE);
        countFieldStart(node, data);
        state.setCode(code);
        // Get the current type summary
        org.acm.seguin.summary.TypeSummary current = ((org.acm.seguin.summary.TypeSummary) (state.getCurrentSummary()));
        // Local Variables
        org.acm.seguin.summary.FieldSummary result = null;
        int last = node.jjtGetNumChildren();
        org.acm.seguin.parser.ast.ASTType type = ((org.acm.seguin.parser.ast.ASTType) (node.jjtGetChild(0)));
        // Create a summary for each field
        for (int ndx = 1; ndx < last; ndx++) {
            org.acm.seguin.parser.Node next = node.jjtGetChild(ndx);
            result = new org.acm.seguin.summary.FieldSummary(current, type, ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (next.jjtGetChild(0))));
            // Count anything on the declarator
            state.setCode(org.acm.seguin.summary.SummaryLoaderState.IGNORE);
            next.jjtGetChild(0).jjtAccept(this, data);
            state.setCode(code);
            // Continue setting up the field summary
            result.setModifiers(node.getModifiers());
            result.setStartLine(start);
            // Load the initializer
            if (next.jjtGetNumChildren() > 1) {
                loadInitializer(current, state, ((org.acm.seguin.parser.ast.SimpleNode) (next.jjtGetChild(1))), node.getModifiers().isStatic());
            }
            // Finish setting variables for the field summary
            countLines(node.getSpecial("comma" + (ndx - 2)));
            result.setEndLine(getLineCount());
            current.add(result);
        }
        countLines(node.getSpecial("semicolon"));
        if (result != null) {
            result.setEndLine(getLineCount());
        }
        return data;
    }

    /**
     * Visits a method
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTMethodDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        countMethodHeader(node, data);
        int declStart = getLineCount();
        int code = state.getCode();
        if (code == org.acm.seguin.summary.SummaryLoaderState.LOAD_CLASSBODY) {
            org.acm.seguin.parser.ast.ASTMethodDeclarator decl = ((org.acm.seguin.parser.ast.ASTMethodDeclarator) (node.jjtGetChild(1)));
            // Get the current type summary
            org.acm.seguin.summary.TypeSummary current = ((org.acm.seguin.summary.TypeSummary) (state.getCurrentSummary()));
            org.acm.seguin.summary.MethodSummary methodSummary = new org.acm.seguin.summary.MethodSummary(current);
            state.startSummary(methodSummary);
            current.add(methodSummary);
            // Load the method summary
            // Remember the modifiers
            methodSummary.setModifiers(node.getModifiers());
            // Load the method names
            methodSummary.setName(decl.getName());
            // Load the return type
            loadMethodReturn(node, methodSummary, decl.getArrayCount());
            // Load the parameters
            loadMethodParams(decl, state);
            // Load the exceptions
            loadMethodExceptions(node, state, 2);
            // Initialize the dependency list
            loadMethodBody(node, state);
            // Done
            state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_CLASSBODY);
            state.finishSummary();
            int end = getLineCount();
            methodSummary.setStartLine(start);
            methodSummary.setDeclarationLine(declStart);
            methodSummary.setEndLine(end);
            return data;
        } else {
            java.lang.System.out.println("Encountered a method in state:  " + code);
            return data;
        }
    }

    /**
     * Visit a formal parameter
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTFormalParameter node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        int code = state.getCode();
        if (code == org.acm.seguin.summary.SummaryLoaderState.LOAD_PARAMETERS) {
            // Local Variables
            org.acm.seguin.summary.MethodSummary methodSummary = ((org.acm.seguin.summary.MethodSummary) (state.getCurrentSummary()));
            // For each variable create a summary
            methodSummary.add(new org.acm.seguin.summary.ParameterSummary(methodSummary, ((org.acm.seguin.parser.ast.ASTType) (node.jjtGetChild(0))), ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (node.jjtGetChild(1)))));
            // Continue with the state
            return state;
        } else {
            // Get the parent
            org.acm.seguin.summary.MethodSummary parent = ((org.acm.seguin.summary.MethodSummary) (state.getCurrentSummary()));
            // Add the dependency
            parent.addDependency(new org.acm.seguin.summary.ParameterSummary(parent, ((org.acm.seguin.parser.ast.ASTType) (node.jjtGetChild(0))), ((org.acm.seguin.parser.ast.ASTVariableDeclaratorId) (node.jjtGetChild(1)))));
            // Return something
            return data;
        }
    }

    /**
     * Visit a constructor
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTConstructorDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int code = state.getCode();
        if (code == org.acm.seguin.summary.SummaryLoaderState.LOAD_CLASSBODY) {
            int start = getLineCount() + 1;
            countConstructorHeader(node);
            int declStart = getLineCount();
            // Get the current type summary
            org.acm.seguin.summary.TypeSummary current = ((org.acm.seguin.summary.TypeSummary) (state.getCurrentSummary()));
            org.acm.seguin.summary.MethodSummary methodSummary = new org.acm.seguin.summary.MethodSummary(current);
            current.add(methodSummary);
            state.startSummary(methodSummary);
            // Load the constructor
            // Remember the modifiers
            methodSummary.setModifiers(node.getModifiers());
            // Load the method names
            methodSummary.setName(node.getName());
            // Load the parameters
            loadMethodParams(node, state);
            // Load the exceptions
            loadMethodExceptions(node, state, 1);
            // Initialize the dependency list
            methodSummary.beginBlock();
            state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_METHODBODY);
            int last = node.jjtGetNumChildren();
            for (int ndx = 1; ndx < last; ndx++) {
                org.acm.seguin.parser.ast.SimpleNode body = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(ndx)));
                if (body instanceof org.acm.seguin.parser.ast.ASTExplicitConstructorInvocation) {
                    body.jjtAccept(this, data);
                } else if (body instanceof org.acm.seguin.parser.ast.ASTBlockStatement) {
                    body.jjtAccept(this, data);
                }
            }
            methodSummary.endBlock();
            // Done
            state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_CLASSBODY);
            state.finishSummary();
            countLines(node.getSpecial("end"));
            int end = getLineCount();
            methodSummary.setStartLine(start);
            methodSummary.setDeclarationLine(declStart);
            methodSummary.setEndLine(end);
            return data;
        } else {
            java.lang.System.out.println("Encountered a constructor in state:  " + code);
            return data;
        }
    }

    /**
     * Visit an initializer
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTInitializer node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        int code = state.getCode();
        if (code == org.acm.seguin.summary.SummaryLoaderState.LOAD_CLASSBODY) {
            // Get the current type summary
            org.acm.seguin.summary.TypeSummary current = ((org.acm.seguin.summary.TypeSummary) (state.getCurrentSummary()));
            int last = node.jjtGetNumChildren();
            org.acm.seguin.parser.ast.SimpleNode body = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(last - 1)));
            if (body instanceof org.acm.seguin.parser.ast.ASTBlock) {
                loadInitializer(current, state, body, node.isUsingStatic());
            }
            return data;
        } else {
            java.lang.System.out.println("Encountered a method in state:  " + code);
            return data;
        }
    }

    /**
     * Visit a type
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTType node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        org.acm.seguin.summary.MethodSummary parent = ((org.acm.seguin.summary.MethodSummary) (state.getCurrentSummary()));
        // Add the dependency
        parent.addDependency(org.acm.seguin.summary.TypeDeclSummary.getTypeDeclSummary(parent, node));
        // Return the data
        return super.visit(node, data);
    }

    /**
     * Visit a return type
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTResultType node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        org.acm.seguin.summary.MethodSummary parent = ((org.acm.seguin.summary.MethodSummary) (state.getCurrentSummary()));
        // Add the dependency
        parent.addDependency(org.acm.seguin.summary.TypeDeclSummary.getTypeDeclSummary(parent, node));
        // Return the data
        return data;
    }

    /**
     * Visit a name
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTName node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int code = state.getCode();
        // Get the current type summary
        if (code == org.acm.seguin.summary.SummaryLoaderState.LOAD_TYPE) {
            org.acm.seguin.summary.TypeSummary current = ((org.acm.seguin.summary.TypeSummary) (state.getCurrentSummary()));
            current.setParentClass(new org.acm.seguin.summary.TypeDeclSummary(current, node));
        }
        return super.visit(node, data);
    }

    /**
     * Visit a list of names
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTNameList node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int code = state.getCode();
        // Get the current type summary
        if (code == org.acm.seguin.summary.SummaryLoaderState.LOAD_TYPE) {
            org.acm.seguin.summary.TypeSummary current = ((org.acm.seguin.summary.TypeSummary) (state.getCurrentSummary()));
            // Local Variables
            int last = node.jjtGetNumChildren();
            // For each interface it implements create a summary
            state.setCode(org.acm.seguin.summary.SummaryLoaderState.IGNORE);
            for (int ndx = 0; ndx < last; ndx++) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
                org.acm.seguin.parser.ast.ASTName next = ((org.acm.seguin.parser.ast.ASTName) (node.jjtGetChild(ndx)));
                current.add(new org.acm.seguin.summary.TypeDeclSummary(current, next));
                next.jjtAccept(this, data);
            }
            state.setCode(code);
            return data;
        } else if (code == org.acm.seguin.summary.SummaryLoaderState.LOAD_EXCEPTIONS) {
            // Local Variables
            int last = node.jjtGetNumChildren();
            org.acm.seguin.summary.MethodSummary methodSummary = ((org.acm.seguin.summary.MethodSummary) (state.getCurrentSummary()));
            // For each variable create a summary
            state.setCode(org.acm.seguin.summary.SummaryLoaderState.IGNORE);
            for (int ndx = 0; ndx < last; ndx++) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
                org.acm.seguin.parser.ast.ASTName next = ((org.acm.seguin.parser.ast.ASTName) (node.jjtGetChild(ndx)));
                methodSummary.add(new org.acm.seguin.summary.TypeDeclSummary(methodSummary, next));
                next.jjtAccept(this, data);
            }
            state.setCode(code);
            // Return something
            return data;
        } else {
            return super.visit(node, data);
        }
    }

    /**
     * Visit an expression
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTPrimaryExpression node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        // Local Variables
        org.acm.seguin.parser.ast.ASTName name;
        // Check out the prefix
        org.acm.seguin.parser.ast.ASTPrimaryPrefix prefix = ((org.acm.seguin.parser.ast.ASTPrimaryPrefix) (node.jjtGetChild(0)));
        if (!prefix.hasAnyChildren()) {
            // Count the lines
            countLines(node.getSpecial("this"));
            countLines(node.getSpecial("id"));
            // It is entirely controlled by the name of the node
            java.lang.String prefixName = prefix.getName();
            // Create the name
            name = new org.acm.seguin.parser.ast.ASTName(0);
            // Check out the name
            if (prefixName.equals("this")) {
                name.addNamePart(prefixName);
            } else {
                name.addNamePart("super");
                name.addNamePart(prefixName.substring(7, prefixName.length()));
            }
        } else if (prefix.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTName) {
            // Count the items in the name
            int code = state.getCode();
            state.setCode(org.acm.seguin.summary.SummaryLoaderState.IGNORE);
            super.visit(prefix, data);
            state.setCode(code);
            // Remember the name
            name = ((org.acm.seguin.parser.ast.ASTName) (prefix.jjtGetChild(0)));
        } else {
            // Our work is done here
            return super.visit(node, data);
        }
        // Get the parent
        org.acm.seguin.summary.MethodSummary parent = ((org.acm.seguin.summary.MethodSummary) (state.getCurrentSummary()));
        // Check out the suffix
        boolean isMessageSend = false;
        int suffixCount = node.jjtGetNumChildren();
        boolean sentLast = false;
        if (suffixCount > 1) {
            for (int ndx = 1; ndx < suffixCount; ndx++) {
                org.acm.seguin.parser.ast.ASTPrimarySuffix suffix = ((org.acm.seguin.parser.ast.ASTPrimarySuffix) (node.jjtGetChild(ndx)));
                if (!suffix.hasAnyChildren()) {
                    // Count the lines
                    countLines(node.getSpecial("dot"));
                    countLines(node.getSpecial("id"));
                    name = new org.acm.seguin.parser.ast.ASTName(0);
                    name.addNamePart(suffix.getName());
                    sentLast = false;
                } else if (suffix.jjtGetChild(0) instanceof org.acm.seguin.parser.ast.ASTArguments) {
                    addAccess(parent, name, true);
                    sentLast = true;
                    super.visit(((org.acm.seguin.parser.ast.SimpleNode) (suffix.jjtGetChild(0))), data);
                } else if (!sentLast) {
                    addAccess(parent, name, false);
                    sentLast = true;
                    // Count the lines
                    countLines(node.getSpecial("["));
                    countLines(node.getSpecial("]"));
                    countLines(node.getSpecial("dot"));
                    countLines(node.getSpecial("id"));
                    super.visit(((org.acm.seguin.parser.ast.SimpleNode) (suffix.jjtGetChild(0))), data);
                }
            }
        }
        // Get the parent
        if (!sentLast) {
            addAccess(parent, name, false);
        }
        // Return some value
        return data;
    }

    /**
     * Visit an allocation
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTAllocationExpression node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        org.acm.seguin.summary.MethodSummary parent = ((org.acm.seguin.summary.MethodSummary) (state.getCurrentSummary()));
        // Add the dependency
        org.acm.seguin.parser.Node child = node.jjtGetChild(0);
        org.acm.seguin.summary.TypeDeclSummary parentClass = null;
        if (child instanceof org.acm.seguin.parser.ast.ASTName) {
            parentClass = new org.acm.seguin.summary.TypeDeclSummary(parent, ((org.acm.seguin.parser.ast.ASTName) (child)));
        } else if (child instanceof org.acm.seguin.parser.ast.ASTPrimitiveType) {
            parentClass = new org.acm.seguin.summary.TypeDeclSummary(parent, ((org.acm.seguin.parser.ast.ASTPrimitiveType) (child)));
        }
        parent.addDependency(parentClass);
        // Count the lines in the type and before
        countLines(node.getSpecial("id"));
        int code = state.getCode();
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.IGNORE);
        child.jjtAccept(this, data);
        state.setCode(code);
        int last = node.jjtGetNumChildren();
        for (int ndx = 1; ndx < last; ndx++) {
            org.acm.seguin.parser.Node next = node.jjtGetChild(ndx);
            if (next instanceof org.acm.seguin.parser.ast.ASTClassBody) {
                // Create Type Summary
                org.acm.seguin.summary.TypeSummary typeSummary = new org.acm.seguin.summary.TypeSummary(parent, null);
                typeSummary.setName("Anonymous" + anonCount);
                anonCount++;
                typeSummary.setParentClass(parentClass);
                parent.addDependency(typeSummary);
                // Set the next type summary as the current summary
                state.startSummary(typeSummary);
                int oldCode = state.getCode();
                state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_TYPE);
                // Traverse Children
                next.jjtAccept(this, data);
                // Back to loading the file
                state.finishSummary();
                state.setCode(oldCode);
            } else {
                ((org.acm.seguin.parser.ast.SimpleNode) (next)).jjtAccept(this, data);
            }
        }
        int end = getLineCount();
        parentClass.setStartLine(start);
        parentClass.setEndLine(end);
        // Return the data
        return data;
    }

    /**
     * Visit a statement
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTStatement node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        org.acm.seguin.parser.Node child = node.jjtGetChild(0);
        if (child instanceof org.acm.seguin.parser.ast.ASTBlock) {
            // Don't count blocks as statements
        } else {
            org.acm.seguin.summary.MethodSummary parent = ((org.acm.seguin.summary.MethodSummary) (state.getCurrentSummary()));
            parent.incrStatementCount();
        }
        return super.visit(node, data);
    }

    /**
     * Explicit constructor invocation gets one statement count
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTExplicitConstructorInvocation node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        org.acm.seguin.summary.MethodSummary parent = ((org.acm.seguin.summary.MethodSummary) (state.getCurrentSummary()));
        parent.incrStatementCount();
        return super.visit(node, data);
    }

    /**
     * Visit the local variables
     *
     * @param node
     * 		the node we are visiting
     * @param data
     * 		the state we are in
     * @return nothing of interest
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTLocalVariableDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        int start = getLineCount() + 1;
        countLocalVariable(node, data);
        int end = getLineCount();
        org.acm.seguin.summary.MethodSummary parent = ((org.acm.seguin.summary.MethodSummary) (state.getCurrentSummary()));
        parent.incrStatementCount();
        // Get the field summaries
        org.acm.seguin.summary.LocalVariableSummary[] result = org.acm.seguin.summary.LocalVariableSummary.createNew(parent, node);
        // Add the dependencies into the method
        int last = result.length;
        for (int ndx = 0; ndx < last; ndx++) {
            parent.addDependency(result[ndx]);
            result[ndx].setStartLine(start);
            result[ndx].setEndLine(end);
        }
        // Return some result
        return data;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     */
    protected void forInit(org.acm.seguin.parser.ast.ASTLocalVariableDeclaration node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            super.visit(node, data);
            return;
        }
        int start = getLineCount() + 1;
        countLocalVariable(node, data);
        int end = getLineCount();
        org.acm.seguin.summary.MethodSummary parent = ((org.acm.seguin.summary.MethodSummary) (state.getCurrentSummary()));
        // Get the field summaries
        org.acm.seguin.summary.LocalVariableSummary[] result = org.acm.seguin.summary.LocalVariableSummary.createNew(parent, node);
        // Add the dependencies into the method
        int last = result.length;
        for (int ndx = 0; ndx < last; ndx++) {
            parent.addDependency(result[ndx]);
            result[ndx].setStartLine(start);
            result[ndx].setEndLine(end);
        }
    }

    /**
     * Visits a block in the parse tree. This code counts the block depth
     *  associated with a method. Deeply nested blocks in a method is a sign of
     *  poor design.
     *
     * @param node
     * 		the block node
     * @param data
     * 		the information that is used to traverse the tree
     * @return data is returned
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTBlock node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        // Get the current file summary
        org.acm.seguin.summary.Summary current = state.getCurrentSummary();
        if (current instanceof org.acm.seguin.summary.MethodSummary) {
            ((org.acm.seguin.summary.MethodSummary) (current)).beginBlock();
        }
        java.lang.Object result = super.visit(node, data);
        if (current instanceof org.acm.seguin.summary.MethodSummary) {
            ((org.acm.seguin.summary.MethodSummary) (current)).endBlock();
        }
        return result;
    }

    /**
     * A switch statement counts as a block, even though it
     *  does not use the block parse token.
     *
     * @param node
     * 		the switch node in the parse tree
     * @param data
     * 		the data used to visit this parse tree
     * @return the data
     */
    public java.lang.Object visit(org.acm.seguin.parser.ast.ASTSwitchStatement node, java.lang.Object data) {
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        if (state.getCode() == org.acm.seguin.summary.SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }
        // Get the current file summary
        org.acm.seguin.summary.Summary current = state.getCurrentSummary();
        if (current instanceof org.acm.seguin.summary.MethodSummary) {
            ((org.acm.seguin.summary.MethodSummary) (current)).beginBlock();
        }
        java.lang.Object result = super.visit(node, data);
        if (current instanceof org.acm.seguin.summary.MethodSummary) {
            ((org.acm.seguin.summary.MethodSummary) (current)).endBlock();
        }
        return result;
    }

    /**
     * Adds an access to the method
     *
     * @param parent
     * 		the parent
     * @param name
     * 		the name
     * @param isMessageSend
     * 		is this a message send
     */
    protected void addAccess(org.acm.seguin.summary.MethodSummary parent, org.acm.seguin.parser.ast.ASTName name, boolean isMessageSend) {
        // Record the access
        if (isMessageSend) {
            // Add the dependency
            parent.addDependency(new org.acm.seguin.summary.MessageSendSummary(parent, ((org.acm.seguin.parser.ast.ASTName) (name))));
        } else {
            // Add the dependency
            parent.addDependency(new org.acm.seguin.summary.FieldAccessSummary(parent, ((org.acm.seguin.parser.ast.ASTName) (name))));
        }
    }

    /**
     * Description of the Method
     *
     * @param current
     * 		Description of Parameter
     * @param state
     * 		Description of Parameter
     * @param body
     * 		Description of Parameter
     * @param isStatic
     * 		Description of Parameter
     */
    void loadInitializer(org.acm.seguin.summary.TypeSummary current, org.acm.seguin.summary.SummaryLoaderState state, org.acm.seguin.parser.ast.SimpleNode body, boolean isStatic) {
        org.acm.seguin.summary.MethodSummary methodSummary = current.getInitializer(isStatic);
        state.startSummary(methodSummary);
        // Load the method summary's dependency list
        int oldCode = state.getCode();
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_METHODBODY);
        body.jjtAccept(this, state);
        // Done
        state.setCode(oldCode);
        state.finishSummary();
    }

    /**
     * Counts the lines associated with the field declaration and the associated
     *  type
     *
     * @param node
     * 		the field declaration
     * @param data
     * 		the data for the visitor
     */
    private void countFieldStart(org.acm.seguin.parser.ast.ASTFieldDeclaration node, java.lang.Object data) {
        // Print any tokens
        countLines(node.getSpecial("static"));
        countLines(node.getSpecial("transient"));
        countLines(node.getSpecial("volatile"));
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        // Handle the first two children (which are required)
        org.acm.seguin.parser.Node next = node.jjtGetChild(0);
        next.jjtAccept(this, data);
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param data
     * 		Description of Parameter
     */
    private void countMethodHeader(org.acm.seguin.parser.ast.ASTMethodDeclaration node, java.lang.Object data) {
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        countLines(node.getSpecial("static"));
        countLines(node.getSpecial("abstract"));
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("native"));
        countLines(node.getSpecial("synchronized"));
        countLines(getInitialToken(((org.acm.seguin.parser.ast.ASTResultType) (node.jjtGetChild(0)))));
        countLines(node.getSpecial("throws"));
        countLines(node.getSpecial("semicolon"));
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     */
    private void countConstructorHeader(org.acm.seguin.parser.ast.ASTConstructorDeclaration node) {
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        countLines(node.getSpecial("id"));
        countLines(node.getSpecial("throws"));
        countLines(node.getSpecial("begin"));
    }

    /**
     * Description of the Method
     *
     * @param decl
     * 		Description of Parameter
     * @param state
     * 		Description of Parameter
     */
    private void loadMethodParams(org.acm.seguin.parser.ast.SimpleNode decl, org.acm.seguin.summary.SummaryLoaderState state) {
        org.acm.seguin.parser.Node child = decl.jjtGetChild(0);
        if (!(child instanceof org.acm.seguin.parser.ast.ASTFormalParameters)) {
            java.lang.System.out.println("ERROR!  Not formal parameters");
            return;
        }
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_PARAMETERS);
        child.jjtAccept(this, state);
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param methodSummary
     * 		Description of Parameter
     * @param count
     * 		Description of Parameter
     */
    private void loadMethodReturn(org.acm.seguin.parser.ast.ASTMethodDeclaration node, org.acm.seguin.summary.MethodSummary methodSummary, int count) {
        org.acm.seguin.summary.TypeDeclSummary returnType = org.acm.seguin.summary.TypeDeclSummary.getTypeDeclSummary(methodSummary, ((org.acm.seguin.parser.ast.ASTResultType) (node.jjtGetChild(0))));
        returnType.setArrayCount(returnType.getArrayCount() + count);
        methodSummary.setReturnType(returnType);
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param state
     * 		Description of Parameter
     * @param index
     * 		Description of Parameter
     */
    private void loadMethodExceptions(org.acm.seguin.parser.ast.SimpleNode node, org.acm.seguin.summary.SummaryLoaderState state, int index) {
        if (node.jjtGetNumChildren() > index) {
            org.acm.seguin.parser.Node child = node.jjtGetChild(index);
            if (child instanceof org.acm.seguin.parser.ast.ASTNameList) {
                state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_EXCEPTIONS);
                child.jjtAccept(this, state);
            }
        }
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		Description of Parameter
     * @param state
     * 		Description of Parameter
     */
    private void loadMethodBody(org.acm.seguin.parser.ast.ASTMethodDeclaration node, org.acm.seguin.summary.SummaryLoaderState state) {
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.LOAD_METHODBODY);
        int last = node.jjtGetNumChildren();
        org.acm.seguin.parser.ast.SimpleNode body = ((org.acm.seguin.parser.ast.SimpleNode) (node.jjtGetChild(last - 1)));
        if (body instanceof org.acm.seguin.parser.ast.ASTBlock) {
            body.jjtAccept(this, state);
        }
    }

    private void countLocalVariable(org.acm.seguin.parser.ast.ASTLocalVariableDeclaration node, java.lang.Object data) {
        // Traverse the children
        int last = node.jjtGetNumChildren();
        // Print the final token
        if (node.isUsingFinal()) {
            countLines(node.getSpecial("final"));
        }
        // Convert the data into the correct form
        org.acm.seguin.summary.SummaryLoaderState state = ((org.acm.seguin.summary.SummaryLoaderState) (data));
        // The first child is special - it is the type
        int code = state.getCode();
        state.setCode(org.acm.seguin.summary.SummaryLoaderState.IGNORE);
        node.jjtGetChild(0).jjtAccept(this, data);
        state.setCode(code);
        // Traverse the rest of the children
        for (int ndx = 1; ndx < last; ndx++) {
            countLines(node.getSpecial("comma." + (ndx - 1)));
            // Visit the child
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
    }
}