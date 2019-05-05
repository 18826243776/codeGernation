package cn.mingyue.code;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/30 17:04
 */
public class App {

    @Test
    public void test() {
        EntityInfo entity = TemplateFactory.getSimpleEntity("cn.mingyue.code", "TestApi");

        AnnotationWrapper annotation1 = new AnnotationWrapper(RestController.class);
        AnnotationWrapper annotation2 = new AnnotationWrapper(Api.class, new HashMap<String, Object>(2) {{
            put("tags", "栏目管理");
        }});
        AnnotationWrapper annotation3 = new AnnotationWrapper(RequestMapping.class, new HashMap<String, Object>(2) {{
            put("value", "/admin/column");
        }});
        List<AnnotationWrapper> annotationWrappers = Arrays.asList(annotation1, annotation2, annotation3);
        entity.setClassAnnotations(annotationWrappers);

        EntityField field = new EntityField(TestService.class, Arrays.asList(new AnnotationWrapper(Autowired.class)));
        entity.field(field);


        EntityMethod addMethod = new EntityMethod(void.class, "add");
        AnnotationWrapper methodAnnotation1 = new AnnotationWrapper(ApiImplicitParam.class, new HashMap<String, Object>(2) {{
            put("value", "name");
            put("required", true);
        }});
        AnnotationWrapper methodAnnotation2 = new AnnotationWrapper(ApiImplicitParam.class, new HashMap<String, Object>(2) {{
            put("value", "des");
            put("required", true);
            put("paramType", "query");
        }});
        AnnotationWrapper annotationWrapper3 = new AnnotationWrapper(RequestMapping.class, new HashMap<String, Object>(2) {{
            put("value", "/test");
        }});

        AnnotationWrapper methodAnnotations = new AnnotationWrapper(ApiImplicitParams.class, new HashMap<String, Object>(4) {{
            put("value", Arrays.asList(methodAnnotation1, methodAnnotation2));
        }});

        addMethod.setMethodAnnotations(Arrays.asList(methodAnnotations,annotationWrapper3));

        entity.method(addMethod);

        TransferHandler transferToString = new TransferToString();
        System.out.println(transferToString.handle(entity));
        String output = new OutputToFile().output(entity);
    }

    @Test
    public void test1() {
        int[] strings = {2, 1};
        t(strings);

        String s = new String();
        ArrayList<Object> objects = new ArrayList<>();
        System.out.println(strings.getClass().isArray());
        System.out.println(s.getClass().isArray());
        System.out.println(objects.getClass().isArray());
    }


    private void t(Object object) {
        System.out.println(object instanceof int[]);
    }

    @Test
    public void test2() {
        Object b = new EntityInfo();
        System.out.println(isPrimitive(b));
    }

    private boolean isPrimitive(Object obj) {
        try {
            return ((Class<?>) obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    public void test5() {
//        Object[] te=(Object[])new String{""};
//        System.out.println(te.length);
        int[] ints = new int[3];
    }
    private void t9(Object object){
    }


}
