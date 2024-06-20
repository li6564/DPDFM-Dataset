package com.dpd.parse;

import com.dpd.entity.ClassEntity;
import com.dpd.entity.MethodEntity;
import com.dpd.relation.ClassToClassRelation;
import com.dpd.type.RelType;
import spoon.reflect.declaration.CtType;

import java.util.*;

public interface EntityFactory {
    Set<ClassEntity> classEntity_set = new HashSet<>();
    Set<MethodEntity> methodEntity_set = new HashSet<>();
    Set<ClassToClassRelation> classToClassShip_set = new HashSet<>();

    Map<String,ClassEntity> classEntity_map = new HashMap<>();
    Map<String,MethodEntity> methodEntity_map = new HashMap<>();

    /**
     * 创建类实体
     * @param ctType
     * @return
     */
    public ClassEntity createClassEntity(CtType<?> ctType);

    /**
     * 创建关系实体
     * @param source
     * @param target
     * @param relType
     * @return
     */
    public ClassToClassRelation createRelationEntity(ClassEntity source, ClassEntity target, RelType relType);


    /**
     * 创建方法实体
     * @param ctType
     * @return
     */
    public List<MethodEntity> createMethodEntity(CtType<?> ctType);


    /**
     * 通过全限定名称获取类实体
     * @param name
     * @return
     */
    public ClassEntity getClassEntityByQualifiedName(String name);

    /**
     * 通过全限定名称获取方法实体
     * @param name
     * @return
     */
    public MethodEntity getMethodEntityByQualifiedName(String name);


    /**
     * 判断某个关系是否存在
     * @param targetClassEntity
     * @param sourceClassEntity
     * @param relTypeEnum
     * @return
     */
    public Boolean getRelation(ClassEntity targetClassEntity, ClassEntity sourceClassEntity, RelType relTypeEnum);

}
