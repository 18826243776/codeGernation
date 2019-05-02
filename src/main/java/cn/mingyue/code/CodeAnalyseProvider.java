package cn.mingyue.code;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 10:02
 */
public class CodeAnalyseProvider implements CodeAnalyse {

    public static void main(String[] args) {
        CodeAnalyseProvider analyseProvider = new CodeAnalyseProvider();
        analyseProvider.analyse(new TestEntity());
    }

    private Object targetValue;
    private Class target;

    public EntityInfo analyse(Object o) {
        this.targetValue = o;
        this.target = o.getClass();
        EntityInfo entityInfo = new EntityInfo();
        entityInfo.setClassPackage(target.getPackage().getName());
        entityInfo.setClassImports(analyseImports(targetValue));
        entityInfo.setClassAnnotations(analyseAnnotation(targetValue));
        entityInfo.setClassModifier(analyseModifier(targetValue));
        entityInfo.setClassType(analyseClassType(targetValue));
        entityInfo.setClassName(target.getSimpleName());
        return entityInfo;
    }

    private List<AnnotationWrapper> analyseAnnotation(Object targetValue) {
        List<AnnotationWrapper> list = new ArrayList<>(8);
        Annotation[] annotations = target.getAnnotations();
        for (Annotation annotation : annotations) {
            Map<String, Object> map = new HashMap<>(4);
            Class<? extends Annotation> aClass = annotation.annotationType();
            AnnotationWrapper annotationWrapper = new AnnotationWrapper(aClass);
            list.add(annotationWrapper);
        }
        return list;
    }

    private String analyseClassType(Object targetValue) {
        Class<?> aClass = targetValue.getClass();
        if (aClass.isAnnotation()) {
            return "@interface";
        }
        if (aClass.isInterface()) {
            return "interface";
        }
        if (aClass.isEnum()) {
            return "enum";
        }
        return "class";
    }

    @Override
    public List<String> analyseImports(Object o) {
        String classContent = new ObjectToString().transfer(o);
        return RegexUtil.forImports(classContent);
    }

    @Override
    public List<String> analyseModifier(Object o) {
        String classContent = new ObjectToString().transfer(o);
        return RegexUtil.forModifiers(classContent);
    }

}
