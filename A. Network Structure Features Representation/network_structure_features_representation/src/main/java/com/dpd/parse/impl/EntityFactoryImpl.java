package com.dpd.parse.impl;

import com.dpd.entity.ClassEntity;
import com.dpd.entity.MethodEntity;
import com.dpd.parse.EntityFactory;
import com.dpd.relation.ClassToClassRelation;
import com.dpd.type.RelType;
import com.dpd.utils.SpoonUtils;
import spoon.reflect.declaration.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 实体工厂
 */
public class EntityFactoryImpl implements EntityFactory {
    @Override
    public ClassEntity createClassEntity(CtType<?> ctType) {
        if (ctType.getQualifiedName().startsWith("java")){
            return null;
        }
        ClassEntity classEntity = getClassEntityByQualifiedName(ctType.getQualifiedName());
        if (classEntity == null){
            classEntity = new ClassEntity();
            classEntity.setName(ctType.getQualifiedName());
            classEntity.setClassType(SpoonUtils.getClassType(ctType));
            classEntity.setModifiers(SpoonUtils.getClassModifiers(ctType));
            classEntity.setSuper_class(ctType.getSuperclass());
            classEntity.setSuper_interfaces(ctType.getSuperInterfaces());
            classEntity.setFields(ctType.getFields());
            classEntity.setSimpleName(ctType.getSimpleName());
            classEntity.setIsFinal(ctType.isFinal()? 1:0);
            classEntity.setIsAnonymous(ctType.isAnonymous()?1:0);
            classEntity.setIsArray(ctType.isArray()?1:0);
            classEntity.setIsLocalType(ctType.isLocalType()?1:0);
            classEntity.setIsNative(ctType.isNative()?1:0);
            classEntity.setIsPrimitive(ctType.isPrimitive()?1:0);
            classEntity.setIsTopLevel(ctType.isTopLevel()?1:0);
            classEntity.setIsParameterized(ctType.isParameterized()?1:0);
            classEntity.setIsShadow(ctType.isShadow()?1:0);
            classEntity.setIsAnonymous(ctType.isAnnotationType()?1:0);
            classEntity.setIsGenerics(ctType.isGenerics()?1:0);
            classEntity_set.add(classEntity);
            classEntity_map.put(classEntity.getSimpleName(),classEntity);
        }
        return classEntity;
    }

    @Override
    public List<MethodEntity> createMethodEntity(CtType<?> ctType) {
        MethodEntity methodEntity = null;
        List<MethodEntity> list = new ArrayList<>();
        for (CtMethod<?> ctMethod : ctType.getMethods()) {
            //判断该方法是否已经创建实体
            methodEntity = getMethodEntityByQualifiedName(ctType.getQualifiedName()+":"+ctMethod.getSignature());
            if (methodEntity == null){
                methodEntity = new MethodEntity();
                //判断是否是getterORsetter方法，如果是则不创建方法实体，否则创建该方法实体
                boolean isgetter = SpoonUtils.isGetter(ctMethod.getSignature(), ctMethod.getType().toString());
                boolean issetter = SpoonUtils.isSetter(ctMethod.getSignature());
                if (!isgetter&&!issetter){
                    methodEntity.setMethodName(ctType.getQualifiedName()+":"+ctMethod.getSignature());  //类全限定名称加方法名称防止重复
                    methodEntity.setReturnType(ctMethod.getType());
                    methodEntity.setIsFinal(ctMethod.isFinal()?1:0);
                    methodEntity.setIsStatic(ctMethod.isStatic()?1:0);
                    methodEntity.setModifer(SpoonUtils.getMethodModifer(ctMethod));
                    methodEntity.setIsDefaultMethod(ctMethod.isDefaultMethod()?1:0);
                    methodEntity.setIsImplicit(ctMethod.isImplicit()?1:0);
                    methodEntity.setIsShadow(ctMethod.isShadow()?1:0);
                    methodEntity.setMethod(ctMethod);
                    methodEntity_set.add(methodEntity);
                    methodEntity_map.put(ctType.getQualifiedName()+":"+ctMethod.getSignature(),methodEntity);
                    list.add(methodEntity);
                }
            }
        }
        return list;
    }

    @Override
    public ClassToClassRelation createRelationEntity(ClassEntity source, ClassEntity target, RelType relType) {
        Boolean flag = getRelation(source, target, relType);
        if (!flag){
            ClassToClassRelation entity = new ClassToClassRelation(source,target,relType);
            classToClassShip_set.add(entity);
            return entity;
        }
        return null;
    }

    @Override
    public ClassEntity getClassEntityByQualifiedName(String name) {
        for (ClassEntity classEntity : classEntity_set) {
            if (name.equals(classEntity.getName())){
                return classEntity;
            }
        }
        return null;
    }

    @Override
    public MethodEntity getMethodEntityByQualifiedName(String name) {
        for (MethodEntity methodEntity : methodEntity_set) {
            if (name.equals(methodEntity.getMethodName())){
                return methodEntity;
            }
        }
        return null;
    }

    @Override
    public Boolean getRelation(ClassEntity targetClassEntity, ClassEntity sourceClassEntity, RelType relTypeEnum) {
        ClassToClassRelation relation = new ClassToClassRelation(targetClassEntity,sourceClassEntity,relTypeEnum);
        boolean flag = classToClassShip_set.contains(relation);
        return flag;
    }
}
