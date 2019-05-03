package cn.mingyue.code.second;

import cn.mingyue.code.Controller;
import cn.mingyue.code.Service;
import cn.mingyue.code.SimpleCrudService;
import cn.mingyue.code.UnsafeCrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/30 17:04
 */
public class App {
    public static void main(String[] args) {
        EntityInfo info = new EntityInfo();
        info.setClassPackage("cn.mingyue.code.second").
                imports(SimpleCrudService.class).
                modifier("public").
                setClassName("TestSecond").
                setClassType("class").
                annotation(new AnnotationWrapper(Service.class, new HashMap<String, Object>(4) {{
                    put("value", new EntityInfo());
                }})).
                annotation(new AnnotationWrapper(Controller.class, new HashMap<String, Object>(4) {{
                    put("value", "testController");
                    put("param", "testParam");
                }})).
                annotation(new AnnotationWrapper(ApiModel.class, new HashMap<String, Object>(4) {{
                    put("value", "测试实体");
                }})).
                field(new EntityField(Arrays.asList("public"), SimpleCrudService.class, Arrays.asList(new AnnotationWrapper(Autowired.class, new HashMap<String, Object>(4) {{
                    put("required", false);
                }})))).
                field(new EntityField(Arrays.asList("public"), UnsafeCrudService.class, Arrays.asList(new AnnotationWrapper(Autowired.class, new HashMap<String, Object>(4) {{
                    put("required", true);
                }})))).
                method(new EntityMethod(String.class, "TestEnum"))
//                method(new EntityMethod(Arrays.asList("public"), void.class, "testVoid", Arrays.asList(String.class, Integer.class), Arrays.asList(new AnnotationWrapper(Controller.class))))
        ;

        String result = new TransferToString().handle(info);
        System.out.println(result);
    }

    @Test
    public void test() {
        EntityInfo entity = TemplateFactory.getSimpleEntity("cn.mingyue.code.second", "TestApi");

        AnnotationWrapper annotation1 = new AnnotationWrapper(Controller.class);
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
        System.out.println(TestEnum.class.getSimpleName());
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
