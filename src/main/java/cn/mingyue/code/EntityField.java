package cn.mingyue.code;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 9:08
 */
public class EntityField {
    private List<String> fieldModifier;
    private String fieldName;
    private Class fieldType;
    private List<AnnotationWrapper> fieldAnnotations;

    public EntityField(Class fieldType) {
        this(new ArrayList<>(4), RegexUtil.simplyFieldName(fieldType.getName()), fieldType,new ArrayList<>(4));
    }

    public EntityField(List<String> fieldModifier, Class fieldType) {
        this(fieldModifier, RegexUtil.simplyFieldName(fieldType.getName()), fieldType, new ArrayList<>(4));
    }

    public EntityField(List<String> fieldModifier, Class fieldType,List<AnnotationWrapper> fieldAnnotations) {
        this(fieldModifier, RegexUtil.simplyFieldName(fieldType.getName()), fieldType, fieldAnnotations);
    }

    public EntityField(List<String> fieldModifier, String fieldName, Class fieldType, List<AnnotationWrapper> fieldAnnotations) {
        this.fieldModifier = fieldModifier;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.fieldAnnotations = fieldAnnotations;
    }

    public List<String> getFieldModifier() {
        return fieldModifier;
    }

    public void setFieldModifier(List<String> fieldModifier) {
        this.fieldModifier = fieldModifier;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class fieldType) {
        this.fieldType = fieldType;
    }

    public List<AnnotationWrapper> getFieldAnnotations() {
        return fieldAnnotations;
    }

    public void setFieldAnnotations(List<AnnotationWrapper> fieldAnnotations) {
        this.fieldAnnotations = fieldAnnotations;
    }
}
