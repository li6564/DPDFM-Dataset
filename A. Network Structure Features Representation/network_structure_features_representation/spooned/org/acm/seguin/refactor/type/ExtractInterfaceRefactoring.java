/* Author:  Chris Seguin

 This software has been developed under the copyleft
 rules of the GNU General Public License.  Please
 consult the GNU General Public License for more
 details about use and distribution of this software.
 */
package org.acm.seguin.refactor.type;
import org.acm.seguin.refactor.RefactoringException;
/**
 * Refactoring that extracts the interface from the dialog
 *
 * @author Grant Watson
 * @created November 27, 2000
 */
public class ExtractInterfaceRefactoring extends org.acm.seguin.refactor.Refactoring {
    private java.lang.String m_interfaceName;

    private java.lang.String m_packageName;

    private java.util.Vector m_summaryList = new java.util.Vector();

    private org.acm.seguin.refactor.ComplexTransform m_complexTransform;

    /**
     * Constructor for the ExtractInterfaceRefactoring object
     */
    protected ExtractInterfaceRefactoring() {
        m_complexTransform = getComplexTransform();
    }

    /**
     * Sets the interface name for the new interface. If the name contains a
     *  package name, then the package name is also set.
     *
     * @param interfaceName
     * 		The new InterfaceName value
     */
    public void setInterfaceName(java.lang.String interfaceName) {
        if (interfaceName.indexOf('.') != (-1)) {
            m_packageName = interfaceName.substring(0, interfaceName.lastIndexOf('.'));
            m_interfaceName = interfaceName.substring(interfaceName.lastIndexOf('.') + 1);
        } else {
            m_interfaceName = interfaceName;
        }
    }

    /**
     * Sets the PackageName attribute of the ExtractInterfaceRefactoring object
     *
     * @param packageName
     * 		The new PackageName value
     */
    public void setPackageName(java.lang.String packageName) {
        m_packageName = packageName;
    }

    /**
     * Gets the Description attribute of the ExtractInterfaceRefactoring object
     *
     * @return The Description value
     */
    public java.lang.String getDescription() {
        return "Extract Interface.";
    }

    /**
     * Gets the ID attribute of the ExtractInterfaceRefactoring object
     *
     * @return The ID value
     */
    public int getID() {
        return org.acm.seguin.refactor.Refactoring.EXTRACT_INTERFACE;
    }

    /**
     * Adds a class that will implement the new interface
     *
     * @param packageName
     * 		The feature to be added to the ImplementingClass
     * 		attribute
     * @param className
     * 		The feature to be added to the ImplementingClass
     * 		attribute
     */
    public void addImplementingClass(java.lang.String packageName, java.lang.String className) {
        org.acm.seguin.summary.TypeSummary summary = org.acm.seguin.summary.query.GetTypeSummary.query(org.acm.seguin.summary.PackageSummary.getPackageSummary(packageName), className);
        addImplementingClass(summary);
    }

    /**
     * Adds a feature to the ImplementingClass attribute of the
     *  ExtractInterfaceRefactoring object
     *
     * @param summary
     * 		The feature to be added to the ImplementingClass attribute
     */
    public void addImplementingClass(org.acm.seguin.summary.TypeSummary summary) {
        if (summary != null) {
            m_summaryList.addElement(summary);
        }
    }

    /**
     * Description of the Method
     *
     * @exception RefactoringException
     * 		Description of Exception
     */
    protected void preconditions() throws org.acm.seguin.refactor.RefactoringException {
        if (m_interfaceName == null) {
            throw new org.acm.seguin.refactor.RefactoringException("Interface name is not specified");
        }
        if (m_summaryList.size() == 0) {
            throw new org.acm.seguin.refactor.RefactoringException("Unable to find type to extract interface from");
        }
    }

    /**
     * this performs the refactoring
     */
    protected void transform() {
        java.io.File newFile = createInterfaceFile();
        // Add declarations of the common methods to the interface
        java.util.Vector methodSummaries = getMethodSummaries();
        for (int i = 0; i < methodSummaries.size(); i++) {
            org.acm.seguin.summary.MethodSummary ms = ((org.acm.seguin.summary.MethodSummary) (methodSummaries.elementAt(i)));
            m_complexTransform.add(new org.acm.seguin.refactor.method.AddConcreteMethod(ms));
        }
        // Add necessary import statements to support parameter and return types
        java.util.Iterator importTypes = getImportTypes(methodSummaries);
        while ((importTypes != null) && importTypes.hasNext()) {
            org.acm.seguin.summary.TypeDeclSummary decl = ((org.acm.seguin.summary.TypeDeclSummary) (importTypes.next()));
            org.acm.seguin.summary.TypeSummary type = org.acm.seguin.summary.query.GetTypeSummary.query(decl);
            // If the type is not found, don't attempt to add an import statement
            if (type != null) {
                m_complexTransform.add(new org.acm.seguin.refactor.AddImportTransform(type));
            }
        } 
        m_complexTransform.apply(newFile, newFile);
        /* Delete the backup file for the intermediate new interface file to
         ensure that an 'undo' does not recover it.
         */
        newFile = new java.io.File(newFile.getAbsolutePath() + ".0");
        newFile.delete();
        addInterfaceToClasses();
    }

    /**
     * Gets a list of public method summaries that are common to all classes for
     *  which an interface is being extracted.
     *
     * @return The MethodSummaries value
     */
    private java.util.Vector getMethodSummaries() {
        java.util.Vector firstClassMethods = new java.util.Vector();
        // Add all relevant methods from the first class.
        org.acm.seguin.summary.TypeSummary ts = ((org.acm.seguin.summary.TypeSummary) (m_summaryList.elementAt(0)));
        java.util.Iterator methods = ts.getMethods();
        while (methods.hasNext()) {
            org.acm.seguin.summary.MethodSummary ms = ((org.acm.seguin.summary.MethodSummary) (methods.next()));
            org.acm.seguin.pretty.ModifierHolder mh = ms.getModifiers();
            /* Include only public, non-static, non-constructor methods.
             Private and protected methods are not allowed in interfaces and
             methods that are package-protected in an interface need to be
             implemented by public methods in implementing classes (I think).
             */
            if ((mh.isPublic() && (!ms.isConstructor())) && (!mh.isStatic())) {
                // synchronized modifier is not allowed for interfaces.
                mh.setSynchronized(false);
                firstClassMethods.addElement(ms);
            }
        } 
        return commonMethods(firstClassMethods);
    }

    /**
     * Gets a list of the TypeDeclSummaries for the return types and parameters
     *  in the list of MethodSummaries supplied.
     *
     * @param methodSummaries
     * 		Description of Parameter
     * @return The ImportTypes value
     */
    private java.util.Iterator getImportTypes(java.util.Vector methodSummaries) {
        java.util.HashMap importTypes = new java.util.HashMap();
        for (int i = 0; i < methodSummaries.size(); i++) {
            org.acm.seguin.summary.MethodSummary ms = ((org.acm.seguin.summary.MethodSummary) (methodSummaries.elementAt(i)));
            // Add return type to list
            org.acm.seguin.summary.TypeDeclSummary retType = ms.getReturnType();
            java.lang.String typeName = retType.getName();
            if ((!typeName.equals("void")) && (importTypes.get(typeName) == null)) {
                importTypes.put(typeName, retType);
            }
            java.util.Iterator params = ms.getParameters();
            // Add parameter types to list
            while ((params != null) && params.hasNext()) {
                org.acm.seguin.summary.VariableSummary vs = ((org.acm.seguin.summary.VariableSummary) (params.next()));
                org.acm.seguin.summary.TypeDeclSummary param = vs.getTypeDecl();
                typeName = param.getName();
                if (importTypes.get(typeName) == null) {
                    importTypes.put(typeName, param);
                }
            } 
            // Add exception types to list
            java.util.Iterator exceptions = ms.getExceptions();
            while ((exceptions != null) && exceptions.hasNext()) {
                org.acm.seguin.summary.TypeDeclSummary exception = ((org.acm.seguin.summary.TypeDeclSummary) (exceptions.next()));
                typeName = exception.getName();
                if (importTypes.get(typeName) == null) {
                    importTypes.put(typeName, exception);
                }
            } 
        }
        return importTypes.values().iterator();
    }

    /**
     * Adds the name of the newly created interface to the implements clause of
     *  each class selected for the refactoring.
     */
    private void addInterfaceToClasses() {
        for (int i = 0; i < m_summaryList.size(); i++) {
            org.acm.seguin.summary.TypeSummary ts = ((org.acm.seguin.summary.TypeSummary) (m_summaryList.elementAt(i)));
            org.acm.seguin.summary.FileSummary fileSummary = ((org.acm.seguin.summary.FileSummary) (ts.getParent()));
            java.io.File file = fileSummary.getFile();
            org.acm.seguin.parser.ast.ASTName interfaceName = new org.acm.seguin.parser.ast.ASTName(0);
            java.lang.String currentPackageName = ts.getPackageSummary().getName();
            /* If the interface package differs from the class package, then
             specify the interface package name
             */
            if ((m_packageName.length() > 0) && (!currentPackageName.equals(m_packageName))) {
                interfaceName.fromString((m_packageName + ".") + m_interfaceName);
            } else {
                interfaceName.fromString(m_interfaceName);
            }
            m_complexTransform.clear();
            // Very Important so we don't re-apply the interface transforms
            m_complexTransform.add(new org.acm.seguin.refactor.type.AddImplementedInterfaceTransform(interfaceName));
            if (!m_packageName.equals(currentPackageName)) {
                m_complexTransform.add(new org.acm.seguin.refactor.AddImportTransform(interfaceName));
            }
            m_complexTransform.apply(file, new java.io.File(file.getAbsolutePath()));
        }
    }

    /**
     * Eliminates methods that don't occurr in all classes and returns the
     *  resulting Vector of common methods.
     *
     * @param initialMethods
     * 		Description of Parameter
     * @return Description of the Returned Value
     */
    private java.util.Vector commonMethods(java.util.Vector initialMethods) {
        java.util.Vector result = new java.util.Vector();
        for (int i = 0; i < initialMethods.size(); i++) {
            boolean keep = true;
            outerloop : for (int j = 1; j < m_summaryList.size(); j++) {
                org.acm.seguin.summary.TypeSummary ts = ((org.acm.seguin.summary.TypeSummary) (m_summaryList.elementAt(j)));
                java.util.Iterator methods = ts.getMethods();
                while (methods.hasNext()) {
                    org.acm.seguin.summary.MethodSummary ms = ((org.acm.seguin.summary.MethodSummary) (methods.next()));
                    if (ms.equals(((org.acm.seguin.summary.MethodSummary) (initialMethods.elementAt(i))))) {
                        continue outerloop;
                    }
                } 
                keep = false;
            }
            if (keep) {
                org.acm.seguin.summary.MethodSummary ms = ((org.acm.seguin.summary.MethodSummary) (initialMethods.elementAt(i)));
                result.addElement(initialMethods.elementAt(i));
            }
        }
        return result;
    }

    /**
     * Creates a new interface file.
     *
     * @return Description of the Returned Value
     */
    private java.io.File createInterfaceFile() {
        java.io.File newFile = null;
        org.acm.seguin.summary.TypeSummary ts = ((org.acm.seguin.summary.TypeSummary) (m_summaryList.elementAt(0)));
        org.acm.seguin.summary.PackageSummary ps = ts.getPackageSummary();
        if (m_packageName == null) {
            m_packageName = ps.getName();
        }
        org.acm.seguin.refactor.type.CreateNewInterface cni = new org.acm.seguin.refactor.type.CreateNewInterface(ts, m_packageName, m_interfaceName);
        try {
            newFile = cni.run();
        } catch (org.acm.seguin.refactor.RefactoringException re) {
            re.printStackTrace();
            return null;
        }
        m_complexTransform.createFile(newFile);
        return newFile;
    }
}