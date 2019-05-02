package cn.mingyue.code;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 17:44
 */
public class TransferForString implements Transfer<String> {
    private static final String nextLineChar = "\r\n";
    private static final String lineEndSuffix = " ; ";
    private static final String packagePrefix = "package ";
    private static final String importPrefix = "import ";
    private static final String beginIndex = " { " + nextLineChar;
    private static final String endIndex = " } " + nextLineChar;


    private EntityInfo info;
    StringBuilder result = new StringBuilder(1000);

    @Override
    public String transto(EntityInfo info) {
        this.info = info;
        StringBuilder result = builder().
                buildPackage().
                buildImport().
                buildAnnotation().
                buildModifier().
                buildClassType().
                buildClassName().
                buildField().
                buildMethod().
                build();
        return result.toString();
    }

    private Builder builder() {
        return new Builder();
    }

    private class Builder {

        public Builder buildPackage() {
            String classPackage = info.getClassPackage();
            result.append(packagePrefix + classPackage + lineEndSuffix + nextLineChar);
            return this;
        }

        public Builder buildImport() {
            List<String> imports = info.getClassImports();
            for (String anImport : imports) {
                anImport = importPrefix + anImport;
                result.append(anImport + lineEndSuffix + nextLineChar);
            }
            return this;
        }

        public Builder buildModifier() {
            List<String> classModifier = info.getClassModifier();
            for (String s : classModifier) {
                result.append(s + " ");
            }
            return this;
        }

        public Builder buildClassType() {
            result.append(info.getClassType() + " ");
            return this;
        }

        public Builder buildClassName() {
            result.append(info.getClassName() + " ");
            return buildBeginIndex();
        }

        private Builder buildBeginIndex() {
            result.append(beginIndex);
            return this;
        }

        public StringBuilder build() {
            result.append(endIndex);
            return result;
        }

        public Builder buildAnnotation() {
            for (AnnotationWrapper annotation : info.getClassAnnotations()) {
                String name = annotation.getName();
                result.append(name);
                Map<String, Object> annotationValue = annotation.getValue();
                resove(annotationValue);
                result.append(nextLineChar);
            }
            return this;
        }

        private void resove(Map<String, Object> annotationValue) {
            if (annotationValue.size() > 0) {
                result.append("(");
            }
            int count = 1;
            for (Map.Entry<String, Object> entry : annotationValue.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (count > 1) {
                    result.append(" , ");
                }
                if (value instanceof String) {
                    result.append(key + " = \"" + value + "\"");
                } else {
                    result.append(key + " = " + value + "");
                }
                count++;
            }
            if (annotationValue.size() > 0) {
                result.append(")");
            }
        }

        public Builder buildField() {
            List<EntityField> fields = info.getFields();
            for (EntityField field : fields) {
                List<AnnotationWrapper> fieldAnnotations = field.getFieldAnnotations();
                List<String> fieldModifier = field.getFieldModifier();
                Class fieldType = field.getFieldType();
                String fieldName = field.getFieldName();

                for (AnnotationWrapper fieldAnnotation : fieldAnnotations) {
                    result.append("\t");
                    String name = fieldAnnotation.getName();
                    result.append(name);
                    Map<String, Object> annotationValue = fieldAnnotation.getValue();
                    resove(annotationValue);
                    result.append(nextLineChar);
                }

                for (String s : fieldModifier) {
                    result.append("\t");
                    result.append(s + " ");
                }

                result.append(fieldType.getSimpleName() + " ");
                result.append(fieldName + lineEndSuffix + nextLineChar);
            }
            return this;
        }

        public Builder buildMethod() {
            List<EntityMethod> methods = info.getMethods();
            for (EntityMethod method : methods) {
                List<AnnotationWrapper> methodAnnotations = method.getMethodAnnotations();
                for (AnnotationWrapper methodAnnotation : methodAnnotations) {
                    String name = methodAnnotation.getName();
                    result.append("\t" + name);
                    resove(methodAnnotation.getValue());
                }
                result.append(nextLineChar);
                List<String> modifier = method.getMethodModifier();
                for (String s : modifier) {
                    result.append("\t");
                    result.append(s + " ");
                }

                Class methodType = method.getMethodType();
                result.append("\t" + methodType.getSimpleName() + " ");
                result.append(method.getMethodName());

                result.append(" ( ");

                List<Class> paramTypes = method.getParamTypes();
                int count = 1;
                for (Class paramType : paramTypes) {
                    String name = paramType.getSimpleName();
                    String lowerName = RegexUtil.firstCharToLower(name);
                    result.append(name + " " + lowerName);
                    if (count < paramTypes.size()) {
                        result.append(" , ");
                    }
                    count++;
                }

                result.append(" ) ");

                result.append(beginIndex);

                if (void.class != methodType) {
                    result.append("\t\treturn null ;" + nextLineChar);
                }

                result.append("\t"+endIndex);

            }
            return this;
        }

    }
}
