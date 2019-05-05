package cn.mingyue.code;

import java.util.*;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 17:44
 */
public class TransferToString implements TransferHandler<String> {

    private EntityInfo info;
    private StringBuilder result = new StringBuilder(1000);
    private CodeBuilder<String> builder = null;
    //开启自动导入包模式
    private boolean autoImport = true;

    public TransferToString setAutoImport(boolean autoImport) {
        this.autoImport = autoImport;
        return this;
    }

    public TransferToString setBuilder(CodeBuilder<String> builder) {
        this.builder = builder;
        return this;
    }

    @Override
    public String handle(EntityInfo info) {
        this.info = info;
        builder = builder == null ? builder() : builder;
        return builder.build();
    }

    private CodeBuilder builder() {
        return new StandardBuilder();
    }

    private class StandardBuilder implements CodeBuilder<String> {

        @Override
        public CodeBuilder pkg() {
            String classPackage = info.getClassPackage();
            result.append(FormConstant.packagePrefix + classPackage + FormConstant.lineEndSuffix + FormConstant.nextLineChar);
            return imports();
        }

        @Override
        public CodeBuilder imports() {
            Set<Class> imports = info.getClassImports();
            if (autoImport) {
                List<EntityMethod> methods = info.getMethods();
                List<EntityField> fields = info.getFields();
                List<AnnotationWrapper> classAnnotations = info.getClassAnnotations();
                for (EntityMethod method : methods) {
                    Class methodType = method.getMethodType();
                    imports.add(methodType);
                    Collection<Class> paramTypes =method.getParamTypes().values();
                    imports.addAll(paramTypes);
                    List<AnnotationWrapper> methodAnnotations = method.getMethodAnnotations();
                    for (AnnotationWrapper methodAnnotation : methodAnnotations) {
                        Class annotation = methodAnnotation.getAnnotation();
                        imports.add(annotation);
                    }
                }
                for (EntityField field : fields) {
                    Class fieldType = field.getFieldType();
                    imports.add(fieldType);
                    List<AnnotationWrapper> fieldAnnotations = field.getFieldAnnotations();
                    for (AnnotationWrapper fieldAnnotation : fieldAnnotations) {
                        imports.add(fieldAnnotation.getAnnotation());
                    }
                }
                for (AnnotationWrapper classAnnotation : classAnnotations) {
                    imports.add(classAnnotation.getAnnotation());
                }
            }

            Iterator<Class> iterator = imports.iterator();
            while (iterator.hasNext()) {
                Class next = iterator.next();
                if (next == void.class || next.getClass().isPrimitive() || next == String.class) {
                    iterator.remove();
                }
            }

            for (Class anImport : imports) {
                String importName = FormConstant.importPrefix + anImport.getName();
                result.append(importName + FormConstant.lineEndSuffix + FormConstant.nextLineChar);
            }
            return annotations();
        }

        @Override
        public CodeBuilder annotations() {
            for (AnnotationWrapper annotation : info.getClassAnnotations()) {
                resolveAnnotation(annotation);
            }
            return modifiers();
        }

        //处理注解AnnotationWrapper
        private void resolveAnnotation(AnnotationWrapper annotation) {
            String name = annotation.getName();
            result.append(name);
            Map<String, Object> annotationValue = annotation.getValue();
            if (annotationValue == null || annotationValue.size() == 0) {
                result.append(FormConstant.nextLineChar);
                return;
            }
            result.append(FormConstant.leftBracket);

            resolveAnnotationValue(annotationValue);

            result.append(FormConstant.rightBracket);
            result.append(FormConstant.nextLineChar);
        }

        //处理注解AnnotationWrapper的map
        private void resolveAnnotationValue(Map<String, Object> annotationValue) {
            int count = 1;
            for (Map.Entry<String, Object> entry : annotationValue.entrySet()) {
                String key = entry.getKey();
                result.append(key + FormConstant.equalChar);
                resolve(entry.getValue());
                if (count > 0 && count < annotationValue.size()) {
                    result.append(FormConstant.commaChar);
                }
                count++;
            }
        }

        private void resolve(Object value) {
            if (value instanceof String) {
                result.append(FormConstant.quotation + value + FormConstant.quotation);
                return;
            }
            if (CodeUtils.isPrimitive(value)) {
                result.append(value);
                return;
            }
            if (value.getClass().isEnum()) {
                //todo
                return;
            }
            if (value instanceof Class) {
                result.append(((Class) value).getSimpleName() + FormConstant.classSuffix + FormConstant.blankChar);
                return;
            }
            if (value instanceof AnnotationWrapper) {
                resolveAnnotation((AnnotationWrapper) value);
                return;
            }

            //如果value是集合或数组   不考虑enum的情况
            if (value instanceof Collection || (value.getClass().isArray() && !CodeUtils.isPrimitiveArray(value))) {
                Collection list;
                list = value instanceof Collection ? (Collection) value : Arrays.asList((Object[]) value);
                Iterator<Object> iterator = list.iterator();
                result.append(FormConstant.leftCurly);
                while (iterator.hasNext()) {
                    Object next = iterator.next();
                    resolve(next);
                    result.append(iterator.hasNext() ? FormConstant.commaChar : "");
                }
                result.append(FormConstant.rightCurly);
                return;
            }
            if (CodeUtils.isPrimitiveArray(value)) {
                resolveHelper(value);
                return;
            }
        }

        private void resolveHelper(Object value) {
            boolean[] valueB = value instanceof boolean[] ? (boolean[]) value : new boolean[0];
            for (boolean b : valueB) {
                resolve(b);
            }
            byte[] valuebyte = value instanceof byte[] ? (byte[]) value : new byte[0];
            for (byte b : valuebyte) {
                resolve(b);
            }
            char[] valueC = value instanceof char[] ? (char[]) value : new char[0];
            for (char b : valueC) {
                resolve(b);
            }
            double[] valueD = value instanceof double[] ? (double[]) value : new double[0];
            for (double b : valueD) {
                resolve(b);
            }
            float[] valueF = value instanceof float[] ? (float[]) value : new float[0];
            for (float b : valueF) {
                resolve(b);
            }
            int[] valueI = value instanceof int[] ? (int[]) value : new int[0];
            for (int b : valueI) {
                resolve(b);
            }
            long[] valueL = value instanceof long[] ? (long[]) value : new long[0];
            for (long b : valueL) {
                resolve(b);
            }
            short[] valueS = value instanceof short[] ? (short[]) value : new short[0];
            for (short b : valueS) {
                resolve(b);
            }
        }

        @Override
        public CodeBuilder modifiers() {
            List<String> classModifier = info.getClassModifier();
            for (String s : classModifier) {
                result.append(s + FormConstant.blankChar);
            }
            return classType();
        }

        @Override
        public CodeBuilder classType() {
            result.append(info.getClassType() + FormConstant.blankChar);
            return className();
        }

        @Override
        public CodeBuilder className() {
            result.append(info.getClassName() + FormConstant.blankChar);
            result.append(FormConstant.beginChar);
            return fields();
        }

        @Override
        public CodeBuilder fields() {
            List<EntityField> fields = info.getFields();
            for (EntityField field : fields) {
                List<AnnotationWrapper> fieldAnnotations = field.getFieldAnnotations();
                for (AnnotationWrapper fieldAnnotation : fieldAnnotations) {
                    resolveAnnotation(fieldAnnotation);
                }
                List<String> fieldModifier = field.getFieldModifier();
                Class fieldType = field.getFieldType();
                String fieldName = field.getFieldName();


                for (String s : fieldModifier) {
                    result.append(FormConstant.tabChar);
                    result.append(s + FormConstant.blankChar);
                }
                result.append(fieldType.getSimpleName() + FormConstant.blankChar);
                result.append(fieldName + FormConstant.lineEndSuffix + FormConstant.nextLineChar);
            }
            return methods();
        }

        @Override
        public CodeBuilder methods() {
            List<EntityMethod> methods = info.getMethods();
            for (EntityMethod method : methods) {
                List<AnnotationWrapper> methodAnnotations = method.getMethodAnnotations();
                for (AnnotationWrapper methodAnnotation : methodAnnotations) {
                    resolveAnnotation(methodAnnotation);
                }
                result.append(FormConstant.nextLineChar);
                List<String> modifier = method.getMethodModifier();
                for (String s : modifier) {
                    result.append(FormConstant.tabChar);
                    result.append(s + FormConstant.blankChar);
                }

                Class methodType = method.getMethodType();
                result.append(FormConstant.tabChar + methodType.getSimpleName() + FormConstant.blankChar);
                result.append(method.getMethodName());

                result.append(FormConstant.leftBracket);

                Map<String, Class> paramTypes = method.getParamTypes();
                int count = 1;
                for (Map.Entry<String, Class> entry : paramTypes.entrySet()) {
                    String paramName = entry.getKey();
                    Class paramType = entry.getValue();
                    String paramTypeName = paramType.getSimpleName();
                    result.append(paramTypeName + FormConstant.blankChar + paramName);
                    if (count < paramTypes.size()) {
                        result.append(FormConstant.commaChar);
                    }
                    count++;
                }

                result.append(FormConstant.rightBracket);

                result.append(FormConstant.beginChar);

                if (void.class != methodType) {
                    result.append(FormConstant.tabChar + FormConstant.tabChar + "return null ;" + FormConstant.nextLineChar);
                }
                result.append(FormConstant.tabChar + FormConstant.endChar);
            }
            return this;
        }

        @Override
        public String build() {
            pkg();
            result.append(FormConstant.endChar);
            return result.toString();
        }
    }
}
