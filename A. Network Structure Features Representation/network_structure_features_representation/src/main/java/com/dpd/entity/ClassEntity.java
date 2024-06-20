package com.dpd.entity;

import spoon.reflect.declaration.CtField;
import spoon.reflect.reference.CtTypeReference;

import java.util.List;
import java.util.Objects;
import java.util.Set;
/**
 * class 实体类
 *
 */
public class ClassEntity {

    private String name; //唯一表示,权限定名

    private String simpleName;

    private Integer classType; //类的类型

    private Integer modifiers;//类的修饰符

    private Integer isFinal; //是否被final修饰

    private CtTypeReference<?> super_class;

    private Set<CtTypeReference<?>> super_interfaces;

    private List<CtField<?>> fields;

    private Integer isTopLevel;             //用于判断给定的类型（ctType）是否为顶层类型的方法。顶层类型指的是独立存在的、非内部类的类型。

    private Integer isShadow;               //ctType.isShadow() 是一个用于判断给定的类型（ctType）是否为阴影类型的方法。阴影类型是指在代码转换过程中由于重命名或修改等操作而产生的临时类型。

    private Integer isAnonymous;            //用于判断给定的类型（ctType）是否为匿名类型的方法。匿名类型是指在代码中直接创建、没有明确名称的类型。

    private Integer isParameterized;        //用于判断给定的类型（ctType）是否是参数化类型的方法。参数化类型是指具有泛型参数的类型，例如 List<String> 或 Map<Integer, String>。

    private Integer isPrimitive;            //ctType.isPrimitive() 是一个用于判断给定的类型（ctType）是否是基本类型的方法

    private Integer isArray;                //判断给定的类型（ctType）是否是数组类型

    private Integer isGenerics;             //判断一个类型是否是泛型类型。

    private Integer isLocalType;            //用于判断给定的类型（ctType）是否为局部类型（Local Type）的方法。局部类型是指在方法或代码块内部定义的嵌套类。

    private Integer isNative;               //判断给定的类型（ctType）是否为本地类型（Native Type）的方法。本地类型是指由底层语言实现的类型，通常与Java语言的本地方法（Native Method）相关。

    public ClassEntity() {
    }

    public Integer getClassType() {
        return classType;
    }

    public void setClassType(Integer classType) {
        this.classType = classType;
    }

    public Integer getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Integer isFinal) {
        this.isFinal = isFinal;
    }

    public Integer getModifiers() {
        return modifiers;
    }

    public void setModifiers(Integer modifiers) {
        this.modifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CtTypeReference<?> getSuper_class() {
        return super_class;
    }

    public void setSuper_class(CtTypeReference<?> super_class) {
        this.super_class = super_class;
    }

    public Set<CtTypeReference<?>> getSuper_interfaces() {
        return super_interfaces;
    }

    public void setSuper_interfaces(Set<CtTypeReference<?>> super_interfaces) {
        this.super_interfaces = super_interfaces;
    }

    public List<CtField<?>> getFields() {
        return fields;
    }

    public void setFields(List<CtField<?>> fields) {
        this.fields = fields;
    }

    public Integer getIsTopLevel() {
        return isTopLevel;
    }

    public void setIsTopLevel(Integer isTopLevel) {
        this.isTopLevel = isTopLevel;
    }

    public Integer getIsShadow() {
        return isShadow;
    }

    public void setIsShadow(Integer isShadow) {
        this.isShadow = isShadow;
    }

    public Integer getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Integer isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public void setIsParameterized(Integer isParameterized) {
        this.isParameterized = isParameterized;
    }

    public void setIsPrimitive(Integer isPrimitive) {
        this.isPrimitive = isPrimitive;
    }

    public void setIsArray(Integer isArray) {
        this.isArray = isArray;
    }

    public Integer getIsGenerics() {
        return isGenerics;
    }

    public void setIsGenerics(Integer isGenerics) {
        this.isGenerics = isGenerics;
    }

    public Integer getIsLocalType() {
        return isLocalType;
    }

    public void setIsLocalType(Integer isLocalType) {
        this.isLocalType = isLocalType;
    }

    public void setIsNative(Integer isNative) {
        this.isNative = isNative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassEntity that = (ClassEntity) o;
        return name.equals(that.name) &&
                simpleName.equals(that.simpleName) &&
                classType == that.classType &&
                modifiers.equals(that.modifiers) &&
                Objects.equals(super_class, that.super_class) &&
                Objects.equals(super_interfaces, that.super_interfaces) &&
                Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, simpleName, classType, modifiers);
    }
}
