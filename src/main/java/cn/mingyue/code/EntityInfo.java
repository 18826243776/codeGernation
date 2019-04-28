package cn.mingyue.code;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 9:04
 */
public class EntityInfo {
    private String classPackage;
    private List<String> classImports = new ArrayList<>(16);
    private List<AnnotationWrapper> classAnnotations = new ArrayList<>(8);

    private List<String> classModifier = new ArrayList<>(4);
    private String classType = "class";
    private String className;
    private List<EntityField> fields = new ArrayList<>(8);
    private List<EntityMethod> methods = new ArrayList<>(16);

    public String getClassPackage() {
        return classPackage;
    }

    public EntityInfo setClassPackage(String classPackage) {
        this.classPackage = classPackage;
        return this;
    }

    public List<String> getClassImports() {
        return classImports;
    }

    public EntityInfo setClassImports(List<String> classImports) {
        this.classImports = classImports;
        return this;
    }

    public List<AnnotationWrapper> getClassAnnotations() {
        return classAnnotations;
    }

    public void setClassAnnotations(List<AnnotationWrapper> classAnnotations) {
        this.classAnnotations = classAnnotations;
    }

    public List<String> getClassModifier() {
        return classModifier;
    }

    public void setClassModifier(List<String> classModifier) {
        this.classModifier = classModifier;
    }

    public String getClassType() {
        return classType;
    }

    public EntityInfo setClassType(String classType) {
        this.classType = classType;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public EntityInfo setClassName(String className) {
        this.className = className;
        return this;
    }

    public List<EntityField> getFields() {
        return fields;
    }

    public void setFields(List<EntityField> fields) {
        this.fields = fields;
    }

    public List<EntityMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<EntityMethod> methods) {
        this.methods = methods;
    }

    public EntityInfo addImport(Class<?> clazz) {
        List<String> classImports = getClassImports();
        String name = clazz.getName();
        classImports.add(name);
        return this;
    }

    public EntityInfo addModifier(String value) {
        List<String> classModifier = getClassModifier();
        classModifier.add(value);
        return this;
    }

    public EntityInfo addAnnotation(AnnotationWrapper annotation) {
        getClassAnnotations().add(annotation);
        return this;
    }

    public EntityInfo addField(EntityField field) {
        getFields().add(field);
        return this;
    }

    public EntityInfo addMethod(EntityMethod method) {
        getMethods().add(method);
        return this;
    }
}
