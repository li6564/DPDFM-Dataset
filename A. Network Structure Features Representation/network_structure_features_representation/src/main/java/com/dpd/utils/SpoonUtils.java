package com.dpd.utils;

import com.dpd.type.NodeType;
import com.dpd.type.ModiferType;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;

import java.util.HashSet;
import java.util.Set;

/**
 * Spoon 工具类
 */
public class SpoonUtils {
    /**
     * 获取类的类型：类/接口/枚举/注解
     */
    public static Integer getClassType(CtType<?> ctType){
        if (ctType.isInterface()){
            return NodeType.INTERFACE;
        }else if(ctType.isAbstract()){
            return NodeType.ABSTRACT;
        }else if (ctType.isAnonymous()){
            return NodeType.ANNOTAION;
        }else if (ctType.isClass()){
            return NodeType.CLASS;
        }
        return NodeType.ENUM;
    }

    /**
     * 验证目标是否合法
     */
    public static boolean isVaild(CtTypeReference<?> reference){
        if (reference == null){
            return false;
        }
        if (reference.getTypeDeclaration() == null){
            return false;
        }
        CtType<?> ctType = reference.getTypeDeclaration();
        return !"<nulltype>".equals(ctType.toString());
    }

    public static boolean isValid(CtType<?> ctType) {
        if (ctType == null) {
            return false;
        }
        CtTypeReference<?> reference = ctType.getReference();

        return isVaild(reference);
    }

    /**
     * 判断 getter 和setter 方法
     */
    public static boolean isGetter(String method_signature, String return_type){
        if (!method_signature.startsWith("get")){
            return false;
        }
        if (!method_signature.contains("()")){  //get方法无参数
            return false;
        }
        return !"void".equals(return_type);
    }

    public static boolean isSetter(String method_signature){
        if (!method_signature.startsWith("set")) {
            return false;
        }
        // set方法只有一个参数
        return !method_signature.contains("()")&&!method_signature.contains(",");
    }

    /**
     * 判断类的修饰符
     */
    public static Integer getClassModifiers(CtType<?> ctType){
        String modifer = ctType.getModifiers().toString();
        if ("private".equals(modifer)){
            return ModiferType.PRIVATE;
        }else if ("protect".equals(modifer)){
            return ModiferType.PROTECT;
        }
        return ModiferType.PUBLIC;
    }

    public static Integer getMethodModifer(CtMethod<?> ctMethod){
        String modifer = ctMethod.getModifiers().toString();
        if ("private".equals(modifer)){
            return ModiferType.PRIVATE;
        }else if ("protect".equals(modifer)){
            return ModiferType.PROTECT;
        }
        return ModiferType.PUBLIC;
    }
}
