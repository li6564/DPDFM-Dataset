package com.dpd.entity;

import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtTypeReference;

import java.util.Objects;
/**
 * Method实体类
 *
 */
public class MethodEntity {
    private String methodName;      //方法全限定名
    private boolean isOverride;    //是否是重写方法
    private CtTypeReference<?> returnType;      //返回值类型（为空代表无返回值）
    private Integer modifer;        //方法修饰符
    private Integer isStatic;        //是否是静态方法
    private Integer isFinal;        //是否被final修饰
    private Integer isDefaultMethod;    // 是一个用于判断给定的方法（ctMethod）是否为默认方法的方法。默认方法是Java 8引入的概念，允许在接口中定义具有默认实现的方法。默认方法通过在接口中提供默认的方法实现，解决了接口的扩展性问题。
    private Integer isImplicit;          // 是一个用于判断给定的方法（ctMethod）是否为隐式方法（Implicit Method）的方法。隐式方法是指由编译器自动生成的方法，而不是由开发人员显式地定义的方法。这些方法通常用于支持特定语言特性或编译器的内部操作。
    private Integer isShadow;           //ctMethod.isShadow() 是一个用于判断给定的方法（ctMethod）是否为影子方法（Shadow Method）的方法。影子方法是指在继承关系中，子类中定义了一个与父类中方法具有相同签名的方法，从而隐藏（shadow）了父类中的方法。当通过子类对象调用该方法时，将执行子类中的方法而不是父类中的方法。
    private CtMethod method;      //当前方法的引用

    public MethodEntity(String methodName, boolean isOverride, CtTypeReference<?> returnType) {
        this.methodName = methodName;
        this.isOverride = isOverride;
        this.returnType = returnType;
    }

    public MethodEntity() {
    }

    public CtMethod getMethod() {
        return method;
    }

    public void setMethod(CtMethod method) {
        this.method = method;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public boolean isOverride() {
        return isOverride;
    }

    public void setOverride(boolean override) {
        isOverride = override;
    }

    public CtTypeReference<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(CtTypeReference<?> returnType) {
        this.returnType = returnType;
    }

    public void setModifer(Integer modifer) {
        this.modifer = modifer;
    }

    public Integer getIsStatic() {
        return isStatic;
    }

    public void setIsStatic(Integer isStatic) {
        this.isStatic = isStatic;
    }

    public Integer getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Integer isFinal) {
        this.isFinal = isFinal;
    }

    public void setIsDefaultMethod(Integer isDefaultMethod) {
        this.isDefaultMethod = isDefaultMethod;
    }

    public void setIsImplicit(Integer isImplicit) {
        this.isImplicit = isImplicit;
    }

    public void setIsShadow(Integer isShadow) {
        this.isShadow = isShadow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodEntity that = (MethodEntity) o;
        return isOverride == that.isOverride &&
                methodName.equals(that.methodName) &&
                returnType.equals(that.returnType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodName, isOverride, returnType);
    }

    @Override
    public String toString() {
        return "MethodEntity{" +
                "methodName='" + methodName + '\'' +
                ", isOverride=" + isOverride +
                ", returnType=" + returnType +
                '}';
    }
}
