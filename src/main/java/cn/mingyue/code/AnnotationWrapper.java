package cn.mingyue.code;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/26 11:25
 */
public class AnnotationWrapper {

    private String name;
    private Class annotation;
    private Map<String, Object> value = new HashMap<>(4);

    public AnnotationWrapper(Class annotation) {
        this(annotation, null);
    }

    public AnnotationWrapper(Class annotation, Map<String, Object> value) {
        this.annotation = annotation;
        if (null != value) {
            this.value = value;
        }
        String name = this.annotation.getSimpleName();
        this.name = "@" + name;
    }

    public static void main(String[] args) {
        new AnnotationWrapper(Service.class);
    }

    public String getName() {
        return name;
    }

    public Class getAnnotation() {
        return annotation;
    }

    public Map<String, Object> getValue() {
        return value;
    }

    public void setValue(Map<String, Object> value) {
        this.value = value;
    }
}
