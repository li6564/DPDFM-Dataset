/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.type;
import org.acm.seguin.refactor.RefactoringException;
/**
 * This object creates a class from nothing. It is responsible for building
 *  up the parse tree from scratch to create a new class.
 *
 * @author Chris Seguin
 */
public class CreateClass {
    private org.acm.seguin.summary.TypeSummary typeSummary;

    private java.lang.String newClassName;

    private boolean isParent;

    private boolean isAbstract;

    private boolean isFinal;

    private java.lang.String packageNameString;

    private java.lang.String scope;

    /**
     * Constructor for the CreateClass object
     *
     * @param existing
     * 		The existing class we are building upon
     * @param className
     * 		The name of the new class
     * @param parent
     * 		Are we building a parent or child from the existing
     * 		type
     * @param packageName
     * 		the name of the package that the class is in
     */
    public CreateClass(org.acm.seguin.summary.TypeSummary existing, java.lang.String className, boolean parent, java.lang.String packageName) {
        typeSummary = existing;
        newClassName = className;
        isParent = parent;
        isAbstract = true;
        isFinal = false;
        packageNameString = packageName;
        scope = "";
    }

    /**
     * Constructor for the CreateClass object
     *
     * @param existing
     * 		The existing class we are building upon
     * @param className
     * 		The name of the new class
     * @param parent
     * 		Are we building a parent or child from the existing type
     */
    public CreateClass(org.acm.seguin.summary.TypeSummary existing, java.lang.String className, boolean parent) {
        typeSummary = existing;
        newClassName = className;
        isParent = parent;
        isAbstract = true;
        isFinal = false;
        packageNameString = null;
        scope = "";
    }

    /**
     * Constructor for the CreateClass object
     */
    CreateClass() {
        typeSummary = null;
        newClassName = null;
    }

    /**
     * Sets the PackageName attribute of the CreateClass object
     *
     * @param value
     * 		The new PackageName value
     */
    public void setPackageName(java.lang.String value) {
        packageNameString = value;
    }

    /**
     * Sets the Scope attribute of the CreateClass object
     *
     * @param value
     * 		The new Scope value
     */
    public void setScope(java.lang.String value) {
        scope = value;
    }

    /**
     * Sets the Abstract attribute of the CreateClass object
     *
     * @param way
     * 		The new Abstract value
     */
    public void setAbstract(boolean way) {
        isAbstract = way;
    }

    /**
     * Sets the Final attribute of the CreateClass object
     *
     * @param way
     * 		The new Final value
     */
    public void setFinal(boolean way) {
        isFinal = way;
    }

    /**
     * Creates the the designated class
     *
     * @return Description of the Returned Value
     * @exception RefactoringException
     * 		Description of Exception
     */
    public java.io.File run() throws org.acm.seguin.refactor.RefactoringException {
        if (newClassName == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No class name specified");
        }
        if (typeSummary == null) {
            throw new org.acm.seguin.refactor.RefactoringException("No type to build upon");
        }
        if (packageNameString == null) {
            packageNameString = org.acm.seguin.summary.query.GetPackageSummary.query(typeSummary).getName();
        }
        // Create the AST
        org.acm.seguin.parser.ast.ASTCompilationUnit root = new org.acm.seguin.parser.ast.ASTCompilationUnit(0);
        // Create the package statement
        int nextIndex = 0;
        if ((packageNameString != null) && (packageNameString.length() > 0)) {
            org.acm.seguin.parser.ast.ASTPackageDeclaration packDecl = createPackageDeclaration();
            root.jjtAddChild(packDecl, 0);
            nextIndex++;
        }
        org.acm.seguin.summary.TypeSummary parentSummary = null;
        org.acm.seguin.parser.ast.ASTName parentName;
        if (isParent) {
            org.acm.seguin.summary.TypeDeclSummary parentDecl = typeSummary.getParentClass();
            parentSummary = org.acm.seguin.summary.query.GetTypeSummary.query(parentDecl);
            if (parentSummary == null) {
                parentSummary = org.acm.seguin.summary.query.GetTypeSummary.query(org.acm.seguin.summary.PackageSummary.getPackageSummary("java.lang"), "Object");
            }
        } else {
            parentSummary = typeSummary;
        }
        parentName = getNameFromSummary(parentSummary);
        // If necessary, create the import statement
        int typeIndex = nextIndex;
        boolean added = addImportStatement(parentSummary, parentName, root, nextIndex);
        if (added) {
            typeIndex++;
            parentName = new org.acm.seguin.parser.ast.ASTName(0);
            parentName.addNamePart(parentSummary.getName());
        }
        // Create the class
        org.acm.seguin.parser.ast.ASTTypeDeclaration td = createTypeDeclaration(parentName);
        root.jjtAddChild(td, typeIndex);
        addConstructors(parentSummary, root);
        if (!isAbstract) {
            addMethods(typeSummary, root);
        }
        // Print this new one
        java.io.File dest = print(newClassName, root);
        return dest;
    }

    /**
     * Converts the type summary into a name
     *
     * @param summary
     * 		the summary
     * @return the name
     */
    org.acm.seguin.parser.ast.ASTName getNameFromSummary(org.acm.seguin.summary.TypeSummary summary) {
        org.acm.seguin.parser.ast.ASTName name = new org.acm.seguin.parser.ast.ASTName(0);
        if ((summary == null) || summary.getName().equals("Object")) {
            name.fromString("Object");
        } else {
            org.acm.seguin.summary.PackageSummary packageSummary = getPackageSummary(summary);
            if (packageSummary.isTopLevel()) {
                name.fromString(summary.getName());
            } else if (!isSamePackage(packageNameString, summary)) {
                name.fromString((packageSummary.getName() + ".") + summary.getName());
            } else {
                name.fromString(summary.getName());
            }
        }
        return name;
    }

    /**
     * Gets the SamePackage attribute of the AddAbstractParent object
     *
     * @param parentSummary
     * 		Description of Parameter
     * @param packageName
     * 		Description of Parameter
     * @return The SamePackage value
     */
    boolean isSamePackage(java.lang.String packageName, org.acm.seguin.summary.TypeSummary parentSummary) {
        return (parentSummary != null) && org.acm.seguin.summary.query.SamePackage.query(packageName, parentSummary);
    }

    /**
     * Creates the package declaration
     *
     * @return the package declaration
     */
    org.acm.seguin.parser.ast.ASTPackageDeclaration createPackageDeclaration() {
        org.acm.seguin.parser.ast.ASTPackageDeclaration packDecl = new org.acm.seguin.parser.ast.ASTPackageDeclaration(0);
        org.acm.seguin.parser.ast.ASTName packName = new org.acm.seguin.parser.ast.ASTName(0);
        packName.fromString(packageNameString);
        packDecl.jjtAddChild(packName, 0);
        return packDecl;
    }

    /**
     * Adds the import statement and returns true if the import statement was
     *  necessary
     *
     * @param parentSummary
     * 		the parent summary
     * @param parentName
     * 		the parent name
     * @param root
     * 		the tree being built
     * @return true if the import statement was added
     */
    boolean addImportStatement(org.acm.seguin.summary.TypeSummary parentSummary, org.acm.seguin.parser.ast.ASTName parentName, org.acm.seguin.parser.ast.ASTCompilationUnit root, int index) {
        if (!isImportRequired(parentSummary)) {
            return false;
        }
        // Create the import statement
        org.acm.seguin.parser.ast.ASTImportDeclaration importDecl = new org.acm.seguin.parser.ast.ASTImportDeclaration(0);
        importDecl.jjtAddChild(parentName, 0);
        root.jjtAddChild(importDecl, index);
        return true;
    }

    /**
     * Creates the type declaration
     *
     * @param grandparentName
     * 		Description of Parameter
     * @return the modified class
     */
    org.acm.seguin.parser.ast.ASTTypeDeclaration createTypeDeclaration(org.acm.seguin.parser.ast.ASTName grandparentName) {
        org.acm.seguin.parser.ast.ASTTypeDeclaration td = new org.acm.seguin.parser.ast.ASTTypeDeclaration(0);
        org.acm.seguin.parser.ast.ASTClassDeclaration cd = createModifiedClass(grandparentName);
        td.jjtAddChild(cd, 0);
        return td;
    }

    /**
     * Creates the modified class
     *
     * @param grandparentName
     * 		The name of the parent class
     * @return the modified class
     */
    org.acm.seguin.parser.ast.ASTClassDeclaration createModifiedClass(org.acm.seguin.parser.ast.ASTName grandparentName) {
        org.acm.seguin.parser.ast.ASTClassDeclaration cd = new org.acm.seguin.parser.ast.ASTClassDeclaration(0);
        if (isAbstract) {
            cd.addModifier("abstract");
        }
        if (isFinal) {
            cd.addModifier("final");
        }
        if (scope.length() > 0) {
            cd.addModifier(scope);
        }
        org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration ucd = createClassBody(newClassName, grandparentName);
        cd.jjtAddChild(ucd, 0);
        return cd;
    }

    /**
     * Creates the body. The protection level is package so it can be easily
     *  tested.
     *
     * @param parentName
     * 		Description of Parameter
     * @param grandparentName
     * 		Description of Parameter
     * @return the class
     */
    org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration createClassBody(java.lang.String parentName, org.acm.seguin.parser.ast.ASTName grandparentName) {
        org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration ucd = new org.acm.seguin.parser.ast.ASTUnmodifiedClassDeclaration(0);
        ucd.setName(parentName);
        ucd.jjtAddChild(grandparentName, 0);
        ucd.jjtAddChild(new org.acm.seguin.parser.ast.ASTClassBody(0), 1);
        return ucd;
    }

    /**
     * Prints the file
     *
     * @param name
     * 		The name of the object
     * @param root
     * 		The root of the tree
     * @return The file that the parse tree was written to
     */
    java.io.File print(java.lang.String name, org.acm.seguin.parser.ast.SimpleNode root) {
        java.io.File parent = getDirectory();
        java.io.File destFile = new java.io.File(parent, name + ".java");
        try {
            new org.acm.seguin.pretty.PrettyPrintFile().apply(destFile, root);
        } catch (java.lang.Throwable thrown) {
            thrown.printStackTrace(java.lang.System.out);
        }
        return destFile;
    }

    /**
     * Determines if we need to add an import
     *
     * @param parentSummary
     * 		the parent summary
     * @return true if the import is necessary
     */
    private boolean isImportRequired(org.acm.seguin.summary.TypeSummary parentSummary) {
        return (!isSamePackage(packageNameString, parentSummary)) && (!isSamePackage("java.lang", parentSummary));
    }

    /**
     * Gets the package summary
     *
     * @param base
     * 		The type whose package was are concerned about
     * @return the package summary
     */
    private org.acm.seguin.summary.PackageSummary getPackageSummary(org.acm.seguin.summary.TypeSummary base) {
        return org.acm.seguin.summary.query.GetPackageSummary.query(base);
    }

    /**
     * Gets the SameParent attribute of the AddAbstractParent object
     *
     * @param one
     * 		Description of Parameter
     * @param two
     * 		Description of Parameter
     * @return The SameParent value
     */
    private boolean isSameParent(org.acm.seguin.summary.TypeSummary one, org.acm.seguin.summary.TypeSummary two) {
        if (isObject(one)) {
            return isObject(two);
        }
        if (isObject(two)) {
            return false;
        }
        return one.equals(two);
    }

    /**
     * Gets the Object attribute of the AddAbstractParent object
     *
     * @param item
     * 		Description of Parameter
     * @return The Object value
     */
    private boolean isObject(org.acm.seguin.summary.TypeSummary item) {
        if (item == null) {
            return true;
        }
        if (item.getName().equals("Object")) {
            return true;
        }
        return false;
    }

    /**
     * Creates a file object pointing to the directory that this class should be
     *  created into
     *
     * @return the directory
     */
    private java.io.File getDirectory() {
        return org.acm.seguin.summary.query.TopLevelDirectory.getPackageDirectory(typeSummary, packageNameString);
    }

    /**
     * Adds the constructors
     *
     * @param parentType
     * 		The feature to be added to the Constructors attribute
     * @param root
     * 		The feature to be added to the Constructors attribute
     */
    private void addConstructors(org.acm.seguin.summary.TypeSummary parentType, org.acm.seguin.parser.ast.SimpleNode root) {
        java.util.Iterator iter = parentType.getMethods();
        if (iter != null) {
            while (iter.hasNext()) {
                org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
                if (next.isConstructor()) {
                    org.acm.seguin.refactor.method.AddConstructor ac = new org.acm.seguin.refactor.method.AddConstructor(next, newClassName);
                    ac.update(root);
                    org.acm.seguin.refactor.method.AddMethodTypeVisitor amtv = new org.acm.seguin.refactor.method.AddMethodTypeVisitor(false);
                    amtv.visit(next, root);
                }
            } 
        }
    }

    /**
     * Adds the methods
     *
     * @param type
     * 		The feature to be added to the Methods attribute
     * @param root
     * 		The feature to be added to the Methods attribute
     */
    private void addMethods(org.acm.seguin.summary.TypeSummary type, org.acm.seguin.parser.ast.SimpleNode root) {
        org.acm.seguin.refactor.type.AbstractMethodFinder finder = new org.acm.seguin.refactor.type.AbstractMethodFinder(type);
        java.util.LinkedList list = finder.getList();
        java.util.Iterator iter = list.iterator();
        while (iter.hasNext()) {
            org.acm.seguin.summary.MethodSummary next = ((org.acm.seguin.summary.MethodSummary) (iter.next()));
            org.acm.seguin.refactor.method.AddConcreteMethod ac = new org.acm.seguin.refactor.method.AddConcreteMethod(next);
            ac.update(root);
            org.acm.seguin.refactor.method.AddMethodTypeVisitor amtv = new org.acm.seguin.refactor.method.AddMethodTypeVisitor(false);
            amtv.visit(next, root);
        } 
    }
}