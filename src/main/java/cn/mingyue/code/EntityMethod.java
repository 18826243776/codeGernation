package cn.mingyue.code;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 9:11
 */
public class EntityMethod {
    private List<String> methodModifier=new ArrayList<>(4);
    private Class methodType;
    private String methodName;
    private List<Class> paramTypes;
    private List<AnnotationWrapper> methodAnnotations = new ArrayList<>(4);

    public EntityMethod(Class methodType) {
        this(new ArrayList<>(4), methodType, RegexUtil.firstCharToLower(methodType.getSimpleName()),
                new ArrayList<>(4), new ArrayList<>(4));
    }
    public EntityMethod(Class methodType, String methodName) {
        this(new ArrayList<>(4), methodType, methodName,
                new ArrayList<>(4), new ArrayList<>(4));
    }
    public EntityMethod(List<String> methodModifier, Class methodType, String methodName,
                        List<Class> paramTypes, List<AnnotationWrapper> methodAnnotations) {
        this.methodModifier = methodModifier;
        this.methodType = methodType;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.methodAnnotations = methodAnnotations;
    }

    public List<Class> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(List<Class> paramTypes) {
        this.paramTypes = paramTypes;
    }

    public List<String> getMethodModifier() {
        return methodModifier;
    }

    public void setMethodModifier(List<String> methodModifier) {
        this.methodModifier = methodModifier;
    }

    public Class getMethodType() {
        return methodType;
    }

    public void setMethodType(Class methodType) {
        this.methodType = methodType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<AnnotationWrapper> getMethodAnnotations() {
        return this.methodAnnotations;
    }

    public void setMethodAnnotations(List<AnnotationWrapper> methodAnnotations) {
        this.methodAnnotations = methodAnnotations;
    }


}
