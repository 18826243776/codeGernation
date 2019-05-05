package cn.mingyue.code;

import java.util.*;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 9:11
 */
public class EntityMethod {
    private List<String> methodModifier=new ArrayList<>(4);
    private Class methodType;
    private String methodName;
    private Map<String, Class> paramTypes;
    private List<AnnotationWrapper> methodAnnotations = new ArrayList<>(4);

    public EntityMethod(Class methodType) {
        this(new ArrayList<>(4), methodType, CodeUtils.firstCharToLower(methodType.getSimpleName()),
                new HashMap<>(4), new ArrayList<>(4));
    }
    public EntityMethod(Class methodType, String methodName) {
        this(new ArrayList<>(4), methodType, methodName,
               new LinkedHashMap<>(4), new ArrayList<>(4));
    }
    public EntityMethod(List<String> methodModifier, Class methodType, String methodName,
                        Map<String, Class> paramTypes, List<AnnotationWrapper> methodAnnotations) {
        this.methodModifier = methodModifier;
        this.methodType = methodType;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.methodAnnotations = methodAnnotations;
    }

    public Map<String, Class> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Map<String, Class> paramTypes) {
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