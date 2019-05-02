package cn.mingyue.code.second;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/5/2 13:48
 */
public class TemplateFactory {

    public static final String PUBLIC = "public";
    public static final String CLASS = "class";

    public static EntityInfo getSimpleEntity(String pkg, String name) {
        EntityInfo entity = new EntityInfo();
        entity.setClassPackage(pkg);
        entity.modifier(PUBLIC);
        entity.setClassType(CLASS);
        entity.setClassName(name);
        return entity;
    }

    public static void main(String[] args) {
        TransferToString transferToString = new TransferToString();
        String test = transferToString.handle(getSimpleEntity("com.mingyue.code.second", "Test"));
        System.out.println(test);
    }
}
