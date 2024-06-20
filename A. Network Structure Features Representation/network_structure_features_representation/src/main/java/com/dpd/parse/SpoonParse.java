package com.dpd.parse;

import com.dpd.entity.ClassEntity;
import com.dpd.entity.MethodEntity;
import com.dpd.parse.impl.EntityFactoryImpl;
import com.dpd.relation.ClassToClassRelation;
import com.dpd.type.RelType;
import com.dpd.utils.SpoonUtils;
import spoon.FluentLauncher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  解析源代码
 */
public class SpoonParse {
    private static CtElement root;
    private static EntityFactory entityFactory = new EntityFactoryImpl();
    private static Set<CtTypeReference<?>> reference_set = new HashSet<>();  // 记录所有的引用类
    private static Set<ClassEntity> classEntities = new HashSet<>();  // 记录已创建的类实体
    private static Set<MethodEntity> methodEntities = new HashSet<>(); //记录创建的方法实体


    public void parse(CtModel model) {
        model.getAllTypes().forEach(ctType -> reference_set.add(ctType.getReference()));
        for (CtTypeReference<?> reference : reference_set) {
            if (reference != null && !reference.toStringDebug().equals("<>nulltype")) {
                //创建类节点
                CtType<?> ctType = reference.getTypeDeclaration();
                if (SpoonUtils.isValid(ctType) && !ctType.isPrimitive()) {
                    //创建类实体
                    ClassEntity classEntity = entityFactory.createClassEntity(ctType);
                    //创建方法实体
                    List<MethodEntity> methodEntityList = entityFactory.createMethodEntity(ctType);
                    if (methodEntityList.size()>0){
                        for (MethodEntity methodEntity : methodEntityList) {
                            methodEntities.add(methodEntity);
                        }
                    }
                    if(classEntity!=null){
                        classEntities.add(classEntity);
                    }
                }
            }
        }
        // 抽取CreateObject关系
        parseCreateObjectRealtion(model);
        // 抽取Return关系
        parseReturnRealtion(model);
        // 抽取Dependency关系
        parseDependencyRelation(model);
        // 抽取PrivateConstructMethod关系
        parsePrivateConstructMethodRelation(model);
        classEntities.forEach(classEntity -> {
            // 有超类则创建超类节点，并建立继承关系（排除 shadow 类型）
            parseSuperClass(classEntity, classEntity.getSuper_class());
            // 有超接口则创建超接口节点，并建立实现关系（排除 shadow 类型）
            parseSuperInterfaces(classEntity, classEntity.getSuper_interfaces());
            //建立联系关系
            parseRealtion(classEntity);


        });

        methodEntities.forEach(methodEntity -> {
            //建立重写关系
            parseOverrideOrHas(methodEntity);
            //建立调用关系
            parseInvoke(methodEntity);
        });
    }

    private void parsePrivateConstructMethodRelation(CtModel model) {
        List<CtClass> classes = model.getElements(new TypeFilter<>(CtClass.class));
        for (CtClass ctClass : classes) {
            if (EntityFactory.classEntity_map.containsKey(ctClass.getSimpleName())){
                Set<CtConstructor> constructors = ctClass.getConstructors();
                for (CtConstructor constructor : constructors) {
                    if (constructor.isPrivate()){
                        ClassEntity classEntity = EntityFactory.classEntity_map.get(ctClass.getSimpleName());
                        ClassEntity classEntity1 = EntityFactory.classEntity_map.get(ctClass.getSimpleName());
                        entityFactory.createRelationEntity(classEntity,classEntity1,RelType.PRIVATE_CONSTRUCT);
                    }
                }
            }
        }
    }

    private void parseDependencyRelation(CtModel model) {
        List<CtClass> classes = model.getElements(new TypeFilter<>(CtClass.class));
        for (CtClass<?> ctClass : classes){
            if (EntityFactory.classEntity_map.containsKey(ctClass.getSimpleName())){
                Set<CtMethod<?>> methods = ctClass.getMethods();
                for (CtMethod<?> method : methods){
                    List<CtParameter<?>> parameters = method.getParameters();
                    for (CtParameter<?> parameter : parameters) {
                        if (EntityFactory.classEntity_map.containsKey(parameter.getType().getSimpleName())){
                            ClassEntity classEntity = EntityFactory.classEntity_map.get(ctClass.getSimpleName());
                            ClassEntity classEntity1 = EntityFactory.classEntity_map.get(parameter.getType().getSimpleName());
                            entityFactory.createRelationEntity(classEntity,classEntity1,RelType.DEPENDENCY);
                        }
                    }
                }
            }
        }
    }

    private void parseReturnRealtion(CtModel model) {
        // 遍历类并查找方法返回值是某个类的实例
        for (CtClass<?> ctClass : model.getElements(new TypeFilter<>(CtClass.class))) {
            if(EntityFactory.classEntity_map.containsKey(ctClass.getSimpleName())){
                for (CtMethod<?> method : ctClass.getMethods()) {
                    // 获取方法的返回类型
                    String returnType = method.getType().getSimpleName();
                    if(EntityFactory.classEntity_map.containsKey(returnType)){
                        // 如果返回类型是你感兴趣的类名，打印出相关信息
                        ClassEntity classEntity = EntityFactory.classEntity_map.get(ctClass.getSimpleName());
                        ClassEntity classEntity1 = EntityFactory.classEntity_map.get(returnType);
                        entityFactory.createRelationEntity(classEntity,classEntity1,RelType.RETURN_OBJECT);
                    }
                }

            }
        }
    }

    private void parseCreateObjectRealtion(CtModel model) {
        // 遍历类并查找方法创建了另一个类的对象
        for (CtClass<?> ctClass : model.getElements(new TypeFilter<>(CtClass.class))) {
            if (EntityFactory.classEntity_map.containsKey(ctClass.getSimpleName())){
                for (CtMethod<?> method : ctClass.getMethods()) {
                    // 遍历方法内部的构造函数调用
                    for (CtConstructorCall<?> constructorCall : method.getElements(new TypeFilter<>(CtConstructorCall.class))) {
                        if(EntityFactory.classEntity_map.containsKey(constructorCall.getType().getSimpleName())){
                            ClassEntity classEntity1 = EntityFactory.classEntity_map.get(constructorCall.getType().getSimpleName());
                            ClassEntity classEntity = EntityFactory.classEntity_map.get(ctClass.getSimpleName());
                            entityFactory.createRelationEntity(classEntity,classEntity1,RelType.CREATE_OBJECT);
                        }

                    }
                }
            }
        }
    }


    private void parseInvoke(MethodEntity methodEntity) {
        try {
            //获取方法的全限定名称
            String methodName = methodEntity.getMethodName();
            //对名称进行分割，前半部为类名称，后半部分为方法名
            String[] strings = methodName.split(":");
            String className = strings[0];
            Map<String, MethodEntity> methodEntityMap = getMethodEntityMap();
            Map<String, ClassEntity> classEntitieMap = getClassEntitie();
            String[] split = className.split("\\.");
            int length = split.length;
            className = split[length-1];
            //获取当前方法所属的类
            ClassEntity classEntity = classEntitieMap.get(className);
            //获取当前方法的引用
            CtMethod method = methodEntity.getMethod();
            List<CtInvocation<?>> methodInvocations = method.getElements(new TypeFilter<>(CtInvocation.class));
            for (CtInvocation<?> invocation : methodInvocations) {
                CtExecutableReference<?> executableRef = invocation.getExecutable();
                if (executableRef != null ) {
                    //获取被调用方法所属的类的全限定的名称
                    CtTypeReference reference = null;
                    CtInterface parent = executableRef.getExecutableDeclaration().getParent(CtInterface.class);

                    if (parent!=null){
                        reference = parent.getReference();
                    }else {
                        reference = executableRef.getExecutableDeclaration().getParent(CtClass.class).getReference();
                    }
                    if (reference!= null){
                        String simpleName = reference.getSimpleName();
                        //获取所属的类
                        ClassEntity classEntity1 = classEntitieMap.get(simpleName);
                        //如果类不为空则简历调用关系
                        if (classEntity1!= null&&!classEntity.getName().equals(classEntity1.getName())){
                            entityFactory.createRelationEntity(classEntity,classEntity1,RelType.INVOKING);
                        }
                    }
                }
            }
        }catch (Exception e) {

        }
    }

    private void parseOverrideOrHas(MethodEntity methodEntity) {
        //获取方法的全限定名称
        String methodName = methodEntity.getMethodName();
        //对名称进行分割，前半部为类名称，后半部分为方法名
        String[] strings = methodName.split(":");
        String className = strings[0];
        String name = strings[1];
        Map<String, ClassEntity> classEntitieMap = getClassEntitie();
        String[] split = className.split("\\.");
        int length = split.length;
        className = split[length-1];
        //获取当前方法所属的类
        ClassEntity classEntity = classEntitieMap.get(className);
        //获取当前类的父类
        CtTypeReference<?> super_class = classEntity.getSuper_class();
        //获取当前类的接口
        Set<CtTypeReference<?>> super_interfaces = classEntity.getSuper_interfaces();
        for (CtTypeReference<?> super_interface : super_interfaces) {
            if (SpoonUtils.isVaild(super_interface)&&!super_interface.isShadow()){
                CtType<?> typeDeclaration = super_interface.getTypeDeclaration();
                for (CtMethod<?> method : typeDeclaration.getMethods()) {
                    if (method.getSignature().equals(name)&&method.getType().toString().equals(methodEntity.getReturnType().toString())){
                        String interfaceMethodName = typeDeclaration.getQualifiedName()+":"+method.getSignature();
                        ClassEntity classEntity1 = entityFactory.createClassEntity(super_interface.getTypeDeclaration());
                        if (classEntity1!= null){
                            entityFactory.createRelationEntity(classEntity,classEntity1,RelType.OVERRIDE);
                        }
                    }
                }
            }
        }
        //获取父类全限定名称
        if (SpoonUtils.isVaild(super_class) && !super_class.isShadow()){
            CtType<?> superctType = super_class.getTypeDeclaration();
            String qualifiedName = superctType.getQualifiedName();
            //获取父类中的所有方法
            for (CtMethod<?> method : superctType.getMethods()) {
                //父类方法的名称
                String signature = method.getSignature();
                //判断父类方法的后面是否与当前方法一致
                if (signature.equals(name)&& method.getType().toString().equals(methodEntity.getReturnType().toString())){
                    //如果一致，则获取当前父类方法的节点，并建立Override关系
                    String superMethodName = superctType.getQualifiedName()+":"+signature;
                    ClassEntity classEntity1 = entityFactory.createClassEntity(super_class.getTypeDeclaration());
                    if (classEntity1!= null){
                        entityFactory.createRelationEntity(classEntity,classEntity1,RelType.OVERRIDE);
                    }
                }
            }
        }

    }

    private void parseSuperClass(ClassEntity son_entity, CtTypeReference<?> super_class){
        if (SpoonUtils.isVaild(super_class) && !super_class.isShadow()&&!super_class.getQualifiedName().startsWith("java")){
            ClassEntity classEntity = entityFactory.createClassEntity(super_class.getTypeDeclaration());
            entityFactory.createRelationEntity(son_entity,classEntity, RelType.EXTENDS);
        }
    }

    private void parseSuperInterfaces(ClassEntity son_entity,Set<CtTypeReference<?>> superInterfaces){
        superInterfaces.forEach(superInterface->{
            if (SpoonUtils.isVaild(superInterface)&&!superInterface.isShadow()&&!superInterface.getQualifiedName().startsWith("java")){
                ClassEntity classEntity = entityFactory.createClassEntity(superInterface.getTypeDeclaration());
                entityFactory.createRelationEntity(son_entity,classEntity,RelType.IMPLEMENT);
            }
        });
    }

    private void parseRealtion(ClassEntity classEntity){
        for (CtField<?> field : classEntity.getFields()) {
            String simpleName = field.getType().getSimpleName();
            Map<String, ClassEntity> classEntitieMap = getClassEntitie();
            ClassEntity entity = classEntitieMap.get(simpleName);
            if (entity!=null){
                entityFactory.createRelationEntity(classEntity,entity,RelType.ASSOCIATION);
            }
        }
    }


    /**
     *
     * @return
     */
    public Set<ClassEntity> getClassEntities() {
        return EntityFactory.classEntity_set;
    }

    public Map<String,MethodEntity> getMethodEntityMap(){
        return EntityFactory.methodEntity_map;
    }

    public Map<String,ClassEntity> getClassEntitie(){
        return EntityFactory.classEntity_map;
    }

    public Set<ClassToClassRelation> getClassToClassRelation() {
        return EntityFactory.classToClassShip_set;
    }


    /**
     * 普通项目分析器，可手动追加依赖库
     *
     * @param module_dir    项目模块路径
     * @param compile_level 目标源码的编译级别
     * @param libs          需手动添加的依赖库
     * @return CtModel 表示一个项目模块
     */
    public CtModel getFluentModel(String module_dir, int compile_level, String... libs) {
        CtModel model = new FluentLauncher()    // 快速启动器
                .inputResource(module_dir)    // 传目录，可以是模块目录也可以是项目目录
                .complianceLevel(compile_level)     // 设置目标源码的编译级别
                .sourceClassPath(libs)  // 手动添加依赖库：.jar or directory
                .buildModel();
        root = model.getRootPackage();
        return model;
    }
}
