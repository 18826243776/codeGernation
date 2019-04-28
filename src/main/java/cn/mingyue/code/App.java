package cn.mingyue.code;

import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 9:02
 */
public class App {
    public static void main(String[] args) {
        CodeAnalyse analyseProvider = new CodeAnalyseProvider();
//        TestEntity testEntity = new TestEntity();
//        System.out.println(testEntity.getClass().getAnnotations().length);
//        EntityInfo info = analyseProvider.analyse(testEntity);
        EntityInfo info = new EntityInfo();
        info.setClassPackage("cn.mingyue.code").
                addImport(SimpleCrudService.class).
                addModifier("public").
                setClassName("Test").
                setClassType("class").
                addAnnotation(new AnnotationWrapper(Service.class, new HashMap<String, Object>(4) {{
                    put("value", "testService");
                }})).
                addAnnotation(new AnnotationWrapper(Controller.class, new HashMap<String, Object>(4) {{
                    put("value", "testController");
                    put("param", "testParam");
                }})).
                addAnnotation(new AnnotationWrapper(ApiModel.class,new HashMap<String ,Object>(4){{
                    put("value","测试实体");
                }})).
                addField(new EntityField(Arrays.asList("public"),SimpleCrudService.class,Arrays.asList(new AnnotationWrapper(Autowired.class, new HashMap<String ,Object>(4){{
                    put("required",false);
                }})))).
                addField(new EntityField(Arrays.asList("public"),UnsafeCrudService.class,Arrays.asList(new AnnotationWrapper(Autowired.class, new HashMap<String ,Object>(4){{
                    put("required",true);
                }})))).
                addMethod(new EntityMethod(String.class,"test")).
                addMethod(new EntityMethod(Arrays.asList("public"),void.class,"testVoid",Arrays.asList(String.class,Integer.class),Arrays.asList(new AnnotationWrapper(ApiImplicitParams.class))))
        ;

        String result = new ToJava().toJava(info);

        System.out.println(result);
    }
}
