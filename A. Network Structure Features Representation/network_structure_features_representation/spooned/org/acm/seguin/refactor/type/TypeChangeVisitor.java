package org.acm.seguin.refactor.type;
/**
 * Scans through the summary objects to create a list of files that reference
 *  a particular class.
 *
 * @author Chris Seguin
 */
public abstract class TypeChangeVisitor extends org.acm.seguin.summary.TraversalVisitor {
    // Instance Variables
    private org.acm.seguin.refactor.ComplexTransform refactoring;

    /**
     * Visitor for type changes
     *
     * @param complex
     * 		Description of Parameter
     */
    public TypeChangeVisitor(org.acm.seguin.refactor.ComplexTransform complex) {
        refactoring = complex;
    }

    /**
     * Visit a summary node. This is the default method.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.Summary node, java.lang.Object data) {
        // Shouldn't have to do anything about one of these nodes
        return data;
    }

    /**
     * Visit a file summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FileSummary node, java.lang.Object data) {
        if (node.getFile() == null) {
            return null;
        }
        if (!preconditions(node)) {
            return null;
        }
        refactoring.clear();
        java.util.LinkedList list = getAppropriateClasses(node);
        java.util.Iterator iter = list.iterator();
        while (iter.hasNext()) {
            // Get the name of the class
            java.lang.String className = ((java.lang.String) (iter.next()));
            // First check to see if any of the classes were imported
            boolean foundImport = checkImports(node, className);
            // Now we get down to the business of checking individual types
            if (checkTypes(node, getState(foundImport, node, className))) {
                org.acm.seguin.refactor.AddImportTransform ait = getNewImports(node, className);
                if (ait != null) {
                    ait.setIgnorePackageName(true);
                    refactoring.add(ait);
                }
                addRenamingTransforms(refactoring, node, className);
            }
        } 
        refactoring.add(getFileSpecificTransform(node));
        if (refactoring.hasAnyChanges()) {
            java.io.File oldFile = node.getFile();
            java.io.File newFile = getNewFile(node);
            java.lang.System.out.println("Updating:  " + oldFile.getName());
            refactoring.add(new org.acm.seguin.refactor.type.RemoveSamePackageTransform());
            refactoring.apply(oldFile, newFile);
        }
        // Return some value
        return refactoring;
    }

    /**
     * Visit a import summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.ImportSummary node, java.lang.Object data) {
        // Local Variables
        boolean importedClass = false;
        boolean importedPackage = false;
        boolean gettingPackage = node.getPackage().getName().equals(getCurrentPackage());
        // Check to see if we have a specific class
        if (gettingPackage) {
            java.lang.String typeName = node.getType();
            if (typeName == null) {
                importedPackage = true;
            } else {
                java.lang.String className = ((java.lang.String) (data));
                importedClass = className.equals(typeName);
            }
        }
        // At this point we know if we specifically imported the class
        if (importedClass) {
            refactoring.add(getRemoveImportTransform(node));
        }
        // Return an integer code for what was found in this import
        return new java.lang.Boolean(importedPackage || importedClass);
    }

    /**
     * Visit a type summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.TypeSummary node, java.lang.Object data) {
        java.lang.Boolean result = new java.lang.Boolean(false);
        // Check extension
        org.acm.seguin.summary.TypeDeclSummary parent = node.getParentClass();
        if (parent != null) {
            result = ((java.lang.Boolean) (parent.accept(this, data)));
        }
        if (result.booleanValue()) {
            return result;
        }
        // Check list of implemented interfaces
        java.util.Iterator iter = node.getImplementedInterfaces();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.TypeDeclSummary next = ((org.acm.seguin.summary.TypeDeclSummary) (iter.next()));
                result = ((java.lang.Boolean) (next.accept(this, data)));
                if (result.booleanValue()) {
                    return result;
                }
            } 
        }
        // Over the fields
        iter = node.getFields();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.FieldSummary next = ((org.acm.seguin.summary.FieldSummary) (iter.next()));
                result = ((java.lang.Boolean) (next.accept(this, data)));
                if (result.booleanValue()) {
                    return result;
                }
            } 
        }
        // Over the methods
        iter = node.getMethods();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
                result = ((java.lang.Boolean) (next.accept(this, data)));
                if (result.booleanValue()) {
                    return result;
                }
            } 
        }
        // Over the types
        iter = node.getTypes();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
                result = ((java.lang.Boolean) (next.accept(this, data)));
                if (result.booleanValue()) {
                    return result;
                }
            } 
        }
        // Return the last false value
        return result;
    }

    /**
     * Visit a method summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.MethodSummary node, java.lang.Object data) {
        java.lang.Boolean result = new java.lang.Boolean(false);
        // Check the return type
        org.acm.seguin.summary.TypeDeclSummary returnType = node.getReturnType();
        if (returnType != null) {
            result = ((java.lang.Boolean) (returnType.accept(this, data)));
            if (result.booleanValue()) {
                return result;
            }
        }
        // Check the parameters
        java.util.Iterator iter = node.getParameters();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.ParameterSummary next = ((org.acm.seguin.summary.ParameterSummary) (iter.next()));
                result = ((java.lang.Boolean) (next.accept(this, data)));
                if (result.booleanValue()) {
                    return result;
                }
            } 
        }
        // Check the exceptions
        iter = node.getExceptions();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.TypeDeclSummary next = ((org.acm.seguin.summary.TypeDeclSummary) (iter.next()));
                result = ((java.lang.Boolean) (next.accept(this, data)));
                if (result.booleanValue()) {
                    return result;
                }
            } 
        }
        // Check the dependencies
        iter = node.getDependencies();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.Summary next = ((org.acm.seguin.summary.Summary) (iter.next()));
                result = ((java.lang.Boolean) (next.accept(this, data)));
                if (result.booleanValue()) {
                    return result;
                }
            } 
        }
        return result;
    }

    /**
     * Visit a field summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FieldSummary node, java.lang.Object data) {
        return visit(((org.acm.seguin.summary.VariableSummary) (node)), data);
    }

    /**
     * Visit a parameter summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.ParameterSummary node, java.lang.Object data) {
        return visit(((org.acm.seguin.summary.VariableSummary) (node)), data);
    }

    /**
     * Visit a local variable summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.LocalVariableSummary node, java.lang.Object data) {
        return visit(((org.acm.seguin.summary.VariableSummary) (node)), data);
    }

    /**
     * Visit a variable summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.VariableSummary node, java.lang.Object data) {
        return node.getTypeDecl().accept(this, data);
    }

    /**
     * Visit a type declaration summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.TypeDeclSummary node, java.lang.Object data) {
        // Local Variables
        org.acm.seguin.refactor.type.State state = ((org.acm.seguin.refactor.type.State) (data));
        boolean mustUsePackage = state.isPackageRequired();
        java.lang.String className = state.getClassName();
        java.lang.String nodePackageName = node.getPackage();
        // Check if the package names match
        if (isMatchingPackage(nodePackageName, mustUsePackage)) {
            // Check for the specific type name
            return new java.lang.Boolean(className.equals(node.getType()));
        }
        return new java.lang.Boolean(false);
    }

    /**
     * Visit a message send summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.MessageSendSummary node, java.lang.Object data) {
        // Local Variables
        org.acm.seguin.refactor.type.State state = ((org.acm.seguin.refactor.type.State) (data));
        boolean mustUsePackage = state.isPackageRequired();
        java.lang.String className = state.getClassName();
        // Check if the package names match
        boolean classNameMatches = (node.getObjectName() != null) && node.getObjectName().equals(className);
        boolean packageNameMatches = isMatchingPackage(node.getPackageName(), mustUsePackage);
        return new java.lang.Boolean(classNameMatches && packageNameMatches);
    }

    /**
     * Visit a field access summary.
     *
     * @param node
     * 		the summary that we are visiting
     * @param data
     * 		the data that was passed in
     * @return the result
     */
    public java.lang.Object visit(org.acm.seguin.summary.FieldAccessSummary node, java.lang.Object data) {
        // Local Variables
        org.acm.seguin.refactor.type.State state = ((org.acm.seguin.refactor.type.State) (data));
        boolean mustUsePackage = state.isPackageRequired();
        java.lang.String className = state.getClassName();
        boolean classNameMatches = (node.getObjectName() != null) && node.getObjectName().equals(className);
        boolean packageNameMatches = isMatchingPackage(node.getPackageName(), mustUsePackage);
        return new java.lang.Boolean(classNameMatches && packageNameMatches);
    }

    /**
     * Returns the state object to be used to determine if the particular type
     *  we are deleting is present
     *
     * @param foundImport
     * 		Description of Parameter
     * @param node
     * 		Description of Parameter
     * @param className
     * 		Description of Parameter
     * @return The State value
     */
    protected org.acm.seguin.refactor.type.State getState(boolean foundImport, org.acm.seguin.summary.FileSummary node, java.lang.String className) {
        boolean mustUsesFullPackageName = !(foundImport || isSamePackage(node));
        return new org.acm.seguin.refactor.type.State(className, mustUsesFullPackageName);
    }

    /**
     * Gets the File Specific Transform
     *
     * @param summary
     * 		Gets a file specific transform
     * @return The FileSpecificTransform value
     */
    protected abstract org.acm.seguin.refactor.TransformAST getFileSpecificTransform(org.acm.seguin.summary.FileSummary summary);

    /**
     * Gets the New Imports transform
     *
     * @param node
     * 		the file summary
     * @param className
     * 		the name of the class that is changing
     * @return The NewImports value
     */
    protected abstract org.acm.seguin.refactor.AddImportTransform getNewImports(org.acm.seguin.summary.FileSummary node, java.lang.String className);

    /**
     * Gets the Remove Imports transform
     *
     * @param node
     * 		the import summary
     * @return The transform
     */
    protected abstract org.acm.seguin.refactor.RemoveImportTransform getRemoveImportTransform(org.acm.seguin.summary.ImportSummary node);

    /**
     * Gets the list of classes to iterate over
     *
     * @param node
     * 		the file summary
     * @return The list
     */
    protected abstract java.util.LinkedList getAppropriateClasses(org.acm.seguin.summary.FileSummary node);

    /**
     * Gets the reference to the file where the refactored output should be sent
     *
     * @param node
     * 		the files summary
     * @return The NewFile value
     */
    protected abstract java.io.File getNewFile(org.acm.seguin.summary.FileSummary node);

    /**
     * Return the current package
     *
     * @return the current package of the class
     */
    protected abstract java.lang.String getCurrentPackage();

    /**
     * Checks any preconditions
     *
     * @param summary
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    protected boolean preconditions(org.acm.seguin.summary.FileSummary summary) {
        return true;
    }

    /**
     * Gets the RenamingTransform
     *
     * @param refactoring
     * 		the refactoring
     * @param node
     * 		the file summary to reference
     * @param className
     * 		the name of the class that is changing
     */
    protected abstract void addRenamingTransforms(org.acm.seguin.refactor.ComplexTransform refactoring, org.acm.seguin.summary.FileSummary node, java.lang.String className);

    /**
     * Returns true if the package is the same
     *
     * @param node
     * 		the current node
     * @return true if the object is in the package
     */
    private boolean isSamePackage(org.acm.seguin.summary.FileSummary node) {
        org.acm.seguin.summary.PackageSummary parent = ((org.acm.seguin.summary.PackageSummary) (node.getParent()));
        return parent.getName().equals(getCurrentPackage());
    }

    /**
     * Determines if the package matches
     *
     * @param nodePackageName
     * 		The node's package
     * @param mustUsePackage
     * 		must it use the full package name
     * @return true if the package matches
     */
    private boolean isMatchingPackage(java.lang.String nodePackageName, boolean mustUsePackage) {
        boolean nullPackageName = nodePackageName == null;
        if (mustUsePackage && nullPackageName) {
            return false;
        }
        return nullPackageName || nodePackageName.equals(getCurrentPackage());
    }

    /**
     * Determine if there was anything by that name imported
     *
     * @param node
     * 		The file summary node
     * @param data
     * 		Data used for traversing the tree
     * @return true if the data was imported
     */
    private boolean checkImports(org.acm.seguin.summary.FileSummary node, java.lang.Object data) {
        // Iterate over the import statements
        java.util.Iterator iter = node.getImports();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.ImportSummary next = ((org.acm.seguin.summary.ImportSummary) (iter.next()));
                java.lang.Object nodeReturn = next.accept(this, data);
                if (((java.lang.Boolean) (nodeReturn)).booleanValue()) {
                    return true;
                }
            } 
        }
        // Not found in import statements
        return false;
    }

    /**
     * Description of the Method
     *
     * @param node
     * 		the file summary node to traverse
     * @param data
     * 		data to help with the traversal
     * @return return true if the types used the specified class
     */
    private boolean checkTypes(org.acm.seguin.summary.FileSummary node, java.lang.Object data) {
        java.util.Iterator iter = node.getTypes();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.TypeSummary next = ((org.acm.seguin.summary.TypeSummary) (iter.next()));
                if (((java.lang.Boolean) (next.accept(this, data))).booleanValue()) {
                    return true;
                }
            } 
        }
        return false;
    }
}