package cn.mingyue.code;

import io.swagger.annotations.*;
import org.junit.Test;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/5/3 19:31
 */
public class SWTemplateFactory extends TemplateFactory {

    public static EntityInfo getSWController(String pkg, Class originEntity) {
        String name = originEntity.getSimpleName() + "Controller";
        EntityInfo entity = getSimpleEntity(pkg, name);
        entity.imports(ApiImplicitParam.class, ApiImplicitParams.class);
        AnnotationWrapper controller = new AnnotationWrapper(RestController.class);
        entity.annotation(controller);

        Annotation apiModelAnno = originEntity.getAnnotation(ApiModel.class);
        String value = ((ApiModel) apiModelAnno).value();
        AnnotationWrapper tags = new AnnotationWrapper(Api.class, new HashMap<String, Object>(2) {{
            put("tags", value);
        }});
        entity.annotation(tags);

        List<AnnotationWrapper> paramList = new ArrayList<>(8);
        Field[] declaredFields = originEntity.getDeclaredFields();
        HashMap<String, Class> paramTypes = new LinkedHashMap<>(8);
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            paramTypes.put(fieldName, field.getType());

            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
            if (annotation == null) {
                continue;
            }
            String value1 = annotation.value();
            AnnotationWrapper paramAnno = new AnnotationWrapper(ApiImplicitParam.class, new HashMap<String, Object>(4) {{
                put("name", fieldName);
                put("value", value1);
                put("paramType", "query");
                put("required", true);
            }});
            paramList.add(paramAnno);
        }
        AnnotationWrapper paramsAnno = new AnnotationWrapper(ApiImplicitParams.class, new HashMap<String, Object>(8) {{
            put("value", paramList);
        }});

        EntityMethod add = new EntityMethod(Arrays.asList("public"), void.class, "add", paramTypes, Arrays.asList(paramsAnno));
        EntityMethod update = new EntityMethod(Arrays.asList("public"), void.class, "update", paramTypes, Arrays.asList(paramsAnno));
        entity.method(add, update);

        return entity;
    }

    @Test
    public void test() {
//        String testSwController = new TransferToString().handle(getSWController("cn.mingyue.code.second", "TestSwController", TestEntity.class));
//        System.out.println(testSwController);
        new OutputToFile().output(getSWController("cn.mingyue.code", TestEntity.class));
    }
}
