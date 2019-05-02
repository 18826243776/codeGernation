package cn.mingyue.code.second;

import cn.mingyue.code.RegexUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 17:44
 */
public class TransferToString implements TransferHandler<String> {

    private EntityInfo info;
    private StringBuilder result = new StringBuilder(1000);
    private CodeBuilder<String> builder = null;

    public void setBuilder(CodeBuilder<String> builder) {
        this.builder = builder;
    }

    @Override
    public String handle(EntityInfo info) {
        this.info = info;
        builder = builder == null ? builder() : builder;
        builder = builder.pkg().
                imports().
                annotations().
                modifiers().
                classType().
                className().
                fields().
                methods();
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
            return this;
        }

        @Override
        public CodeBuilder imports() {
            List<String> imports = info.getClassImports();
            for (String anImport : imports) {
                anImport = FormConstant.importPrefix + anImport;
                result.append(anImport + FormConstant.lineEndSuffix + FormConstant.nextLineChar);
            }
            return this;
        }

        @Override
        public CodeBuilder annotations() {
            for (AnnotationWrapper annotation : info.getClassAnnotations()) {
                resoveAnnotation(annotation);
            }
            return this;
        }

        private void resoveAnnotation(AnnotationWrapper annotation) {
            String name = annotation.getName();
            result.append(name);
            Map<String, Object> annotationValue = annotation.getValue();
            resove(annotationValue);
            result.append(FormConstant.nextLineChar);
        }

        private void resove(Map<String, Object> annotationValue) {

            if (annotationValue.size() > 0) {
                result.append(FormConstant.leftBracket);
            }
            int count = 1;
            for (Map.Entry<String, Object> entry : annotationValue.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                result.append(key + FormConstant.equalChar);

                if (value instanceof String) {
                    result.append(FormConstant.quotation + value + FormConstant.quotation);
                } else if (value instanceof Class) {
                    //如果是注解类
                    if (((Class) value).isAnnotation()) {
                        resoveAnnotation(new AnnotationWrapper((Class) value));
                    } else {
                        result.append(((Class) value).getSimpleName() + FormConstant.classSuffix + FormConstant.blankChar);
                    }
                } else if (value.getClass().isEnum()) {
                    //todo
                } else if (CodeUtils.isPrimitive(value)) {
                    result.append(value + FormConstant.blankChar);
                } else if (value.getClass().isArray()) {
                    //如果value是数组

                    //string数组
                    if (value instanceof String[]) {
                        String[] valuestrings = (String[]) value;
                        if (valuestrings == null || valuestrings.length == 0) {
                            break;
                        }
                        for (int i = 0; i < valuestrings.length; i++) {
                            if (i == 0) {
                                result.append(FormConstant.leftCurly);
                            }

                            result.append(FormConstant.quotation + valuestrings[i] + FormConstant.quotation);
                            if (i < valuestrings.length - 1) {
                                result.append(FormConstant.commaChar);
                            }

                            if (i == valuestrings.length - 1) {
                                result.append(FormConstant.rightCurly);
                            }
                        }
                    }
                    //class数组
                    else if (value instanceof Class[]) {
                        Class[] value1 = (Class[]) value;
                        if (value1 == null || value1.length == 0) {
                            break;
                        }
                        for (int i = 0; i < value1.length; i++) {
                            if (i == 0) {
                                result.append(FormConstant.leftCurly);
                            }
                            if (value1[i].isAnnotation()) {
                                resoveAnnotation(new AnnotationWrapper(value1[i]));
                            } else {
                                result.append(value1[i].getSimpleName() + FormConstant.classSuffix);
                            }

                            if (i < value1.length - 1) {
                                result.append(FormConstant.commaChar);
                            }

                            if (i == value1.length - 1) {
                                result.append(FormConstant.rightCurly);
                            }
                        }
                    }
                    //其他object数组  不考虑enum的情况
                    else {
                        Object[] valueObjects = (Object[]) value;
                        if (valueObjects == null || valueObjects.length == 0) {
                            break;
                        }
                        for (int i = 0; i < valueObjects.length; i++) {
                            if (i == 0) {
                                result.append(FormConstant.leftCurly);
                            }

                            result.append(valueObjects[i]);
                            if (i < valueObjects.length - 1) {
                                result.append(FormConstant.commaChar);
                            }

                            if (i == valueObjects.length - 1) {
                                result.append(FormConstant.rightCurly);
                            }
                        }
                    }


                }
                //集合的情况
                else if (value instanceof Collection) {
                    result.append(FormConstant.leftCurly);
                    Collection list = (Collection) value;
                    Iterator iterator = list.iterator();
                    while (iterator.hasNext()) {
                        Object next = iterator.next();
                        if (next instanceof AnnotationWrapper) {
                            resoveAnnotation((AnnotationWrapper) next);
                        } else if (next instanceof Class) {
                            String collectionName = ((Class) next).getSimpleName() + FormConstant.classSuffix;
                            result.append(FormConstant.quotation + collectionName + FormConstant.quotation + (iterator.hasNext() ? FormConstant.commaChar : ""));
                        } else if (next instanceof String) {
                            result.append(FormConstant.quotation + next + FormConstant.quotation + (iterator.hasNext() ? FormConstant.commaChar : ""));
                        } else {
                            result.append(next + (iterator.hasNext() ? FormConstant.commaChar : ""));
                        }
                        result.append(iterator.hasNext() ? FormConstant.commaChar : "");
                    }
                    result.append(FormConstant.rightCurly);
                }

                if (count < annotationValue.size()) {
                    result.append(FormConstant.commaChar);
                }
                count++;
            }
            if (annotationValue.size() > 0) {
                result.append(FormConstant.rightBracket);
            }
        }

        @Override
        public CodeBuilder modifiers() {
            List<String> classModifier = info.getClassModifier();
            for (String s : classModifier) {
                result.append(s + FormConstant.blankChar);
            }
            return this;
        }

        @Override
        public CodeBuilder classType() {
            result.append(info.getClassType() + FormConstant.blankChar);
            return this;
        }

        @Override
        public CodeBuilder className() {
            result.append(info.getClassName() + FormConstant.blankChar);
            result.append(FormConstant.beginChar);
            return this;
        }

        @Override
        public CodeBuilder fields() {
            List<EntityField> fields = info.getFields();
            for (EntityField field : fields) {
                List<AnnotationWrapper> fieldAnnotations = field.getFieldAnnotations();
                List<String> fieldModifier = field.getFieldModifier();
                Class fieldType = field.getFieldType();
                String fieldName = field.getFieldName();

                for (AnnotationWrapper fieldAnnotation : fieldAnnotations) {
                    result.append(FormConstant.tabChar);
                    String name = fieldAnnotation.getName();
                    result.append(name);
                    Map<String, Object> annotationValue = fieldAnnotation.getValue();
                    resove(annotationValue);
                    result.append(FormConstant.nextLineChar);
                }

                for (String s : fieldModifier) {
                    result.append(FormConstant.tabChar);
                    result.append(s + FormConstant.blankChar);
                }
                result.append(fieldType.getSimpleName() + FormConstant.blankChar);
                result.append(fieldName + FormConstant.lineEndSuffix + FormConstant.nextLineChar);
            }
            return this;
        }

        @Override
        public CodeBuilder methods() {
            List<EntityMethod> methods = info.getMethods();
            for (EntityMethod method : methods) {
                List<AnnotationWrapper> methodAnnotations = method.getMethodAnnotations();
                for (AnnotationWrapper methodAnnotation : methodAnnotations) {
                    String name = methodAnnotation.getName();
                    result.append(FormConstant.tabChar + name);
                    resove(methodAnnotation.getValue());
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

                List<Class> paramTypes = method.getParamTypes();
                int count = 1;
                for (Class paramType : paramTypes) {
                    String name = paramType.getSimpleName();
                    String lowerName = RegexUtil.firstCharToLower(name);
                    result.append(name + FormConstant.blankChar + lowerName);
                    if (count < paramTypes.size()) {
                        result.append(FormConstant.commaChar);
                    }
                    count++;
                }

                result.append(" ) ");

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
            result.append(FormConstant.endChar);
            return result.toString();
        }
    }
}
