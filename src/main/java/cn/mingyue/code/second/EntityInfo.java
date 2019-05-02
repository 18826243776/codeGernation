package cn.mingyue.code.second;

import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 2.0
 * @author: 千里明月
 * @date: 2019/4/25 9:04
 * 转化实体
 */
public class EntityInfo implements Serializable {
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
        Assert.notNull(classPackage);
        this.classPackage = classPackage;
        return this;
    }

    public List<String> getClassImports() {
        return classImports;
    }

    public EntityInfo setClassImports(List<String> classImports) {
        Assert.notNull(classImports);
        this.classImports = classImports;
        return this;
    }

    public List<AnnotationWrapper> getClassAnnotations() {
        return classAnnotations;
    }

    public EntityInfo setClassAnnotations(List<AnnotationWrapper> classAnnotations) {
        Assert.notNull(classAnnotations);
        this.classAnnotations = classAnnotations;
        return this;
    }

    public List<String> getClassModifier() {
        return classModifier;
    }

    public EntityInfo setClassModifier(List<String> classModifier) {
        Assert.notNull(classModifier);
        this.classModifier = classModifier;
        return this;
    }

    public String getClassType() {
        return classType;
    }

    public EntityInfo setClassType(String classType) {
        Assert.notNull(classType);
        this.classType = classType;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public EntityInfo setClassName(String className) {
        Assert.notNull(className);
        this.className = className;
        return this;
    }

    public List<EntityField> getFields() {
        return fields;
    }

    public EntityInfo setFields(List<EntityField> fields) {
        Assert.notNull(fields);
        this.fields = fields;
        return this;
    }

    public List<EntityMethod> getMethods() {
        return methods;
    }

    public EntityInfo setMethods(List<EntityMethod> methods) {
        Assert.notNull(methods);
        this.methods = methods;
        return this;
    }


    public EntityInfo imports(Class<?>... clazz) {
        List<String> classImports = getClassImports();
        for (Class<?> aClass : clazz) {
            String name = aClass.getName();
            classImports.add(name);
        }
        return this;
    }

    public EntityInfo modifier(String... modifier) {
        List<String> classModifier = getClassModifier();
        for (String m : modifier) {
            classModifier.add(m);
        }
        return this;
    }

    public EntityInfo annotation(AnnotationWrapper... annotation) {
        List<AnnotationWrapper> annotations = getClassAnnotations();
        for (AnnotationWrapper wrapper : annotation) {
            annotations.add(wrapper);
        }
        return this;
    }

    public EntityInfo field(EntityField... field) {
        List<EntityField> fields = getFields();
        for (EntityField entityField : field) {
            fields.add(entityField);
        }
        return this;
    }

    public EntityInfo method(EntityMethod... method) {
        List<EntityMethod> methods = getMethods();
        for (EntityMethod entityMethod : method) {
            methods.add(entityMethod);
        }
        return this;
    }


}
