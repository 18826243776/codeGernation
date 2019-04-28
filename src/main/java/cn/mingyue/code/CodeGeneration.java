//package com.zhcs.code;
//
//import com.zhcs.idm.entity.DriverInfo;
//import com.zhcs.sb.common.service.SimpleCrudService;
//import io.swagger.annotations.ApiModelProperty;
//
//import java.io.*;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
//import static java.lang.System.exit;
//
///**
// * Created by Andy on 2017/12/26.
// */
//public class CodeGeneration {
//    //1. 检索实体类
//    //2. 得到所有字段
//    //3. 规避常规字段
//    //4. 生成指定路径下的文件
//    //5. 生成拼接字符串
//    //6. 把字符串写入指定文件
//    String id = "id";
//
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        String path = "D:\\project-idm\\idm\\idm\\src\\main\\java\\com\\zhcs\\idm\\entity";
//        Class clazz = DriverInfo.class;
//        File file = new File(path);
//        if (!file.exists()) {//判断文件目录的存在
//            System.out.println("当前目录不存在，查看目录是否正确");
//            exit(-1);
//        }
////        File[] tempList = file.listFiles();
////        for(int i = 0; i < tempList.length;i++) {        //遍历目录下文件
////        }
//
//        CodeGeneration codeGeneration = new CodeGeneration();
//        //action层路径
//        String actionPath = file.getParent()+"\\controller";
//        //配置包名
//        StringBuilder sb = configPackage(getPackageName(actionPath));
//
//        //配置import
//        sb.append(configImport("io.swagger.annotations.Api", "io.swagger.annotations.ApiImplicitParam",
//                "io.swagger.annotations.ApiImplicitParams","io.swagger.annotations.ApiOperation",
//                "org.springframework.beans.factory.annotation.Autowired",
//                "org.springframework.web.bind.annotation.RequestMapping",
//                "org.springframework.web.bind.annotation.RequestMethod",
//                "org.springframework.web.bind.annotation.RestController"));
//
//        //配置方法可选
//        String[] actionType = {"add","update","get","remove","list"};
//        codeGeneration.generateAction(actionPath,clazz,sb,actionType);
//
//        //service层路径
//        String servicePath = file.getParent()+"\\service";
//        //配置包名
//        StringBuilder stb = configPackage(getPackageName(servicePath));
//        //配置import
//        stb.append(configImport("org.springframework.beans.factory.annotation.Autowired", "org.springframework.stereotype.Service",
//                "org.springframework.transaction.annotation.Transactional","java.util.ArrayList",
//                "java.util.HashMap","java.util.List","java.util.Map", "org.apache.commons.lang.StringUtils"));
//        //在后边可加入其他注解类
//        //配置方法可选
//        String[] serviceType = {"add","update","get","remove","list"};
//        codeGeneration.generateService(servicePath,clazz,stb,SimpleCrudService.class,serviceType);
//
//    }
//
//    private static StringBuilder configImport(String...args) {
//
//        StringBuilder sb= new StringBuilder(1000);
//        for (String s: args) {
//            sb.append("import ");
//            sb.append(s);
//            sb.append(";\n");
//        }
//        sb.append("\n\n");
//        return sb;
//    }
//    private static StringBuilder configPackage(String arg) {
//        StringBuilder sb =new StringBuilder(100);
//        sb.append("package ").append(arg).append(";\n\n");
//        return sb;
//    }
//
//    /*
//       根据当前路径获得package包名
//     */
//    private static String getPackageName(String path) {
//        int startIndex = path.lastIndexOf("com");
//        String packageName = "";
//        if (startIndex != -1 ) {
//            packageName =  path.substring(startIndex).replace("\\", ".");
//        }
//        return packageName;
//    }
//
//    private static StringBuilder addAutowired(Class...clazz) {
//        StringBuilder sb= new StringBuilder(1000);
//        for (Class c: clazz) {
//            sb.append("\t@Autowired\n");
//            sb.append("\tprivate " + c.getSimpleName()+" "+ firstCharToLower(c.getSimpleName()) + " ;\n");
//        }
//        sb.append(";\n");
//        return sb;
//    }
//
//
//    /**
//     * 解析实体类的字段
//     *
//     * @param clazz
//     * @return 返回字段集合
//     */
//    public static List<Field> analyzeEntity(Class clazz) {
//        List<Field> list = new ArrayList<>(10);
//        Field[] fields = clazz.getDeclaredFields();
//        for (int i = 0; i < fields.length; i++) {
//            if (!"is_deleted".equals(fields[i].getName()) && /*!"id".equals(fields[i].getName()) && */!"gmt_modified".equals(fields[i].getName()) && !"gmt_create".equals(fields[i].getName())) {
//                list.add(fields[i]);
//            }
//        }
//        return list;
//    }
//
//    /**
//     * 在指定路径下生成对应实体类的Action代码文件
//     *
//     * @param path  action层路径
//     * @param clazz entity实体类
//     * @param sb    StringBuilder
//     * @throws IOException
//     */
//    public void generateAction(String path, Class clazz,StringBuilder sb,String...args) throws IOException {
//        String simpleName = clazz.getSimpleName();
//
//        String name = "\\" + simpleName + "Controller.java";
//        String serviceName = firstCharToLower(simpleName)+"Service";
//        File file = new File(path + name);
//        if (file.exists()) {
//            file.delete();
//        } else {
//            file.createNewFile();
//        }
//        FileOutputStream fos = new FileOutputStream(file);
//        OutputStreamWriter osw = new OutputStreamWriter(fos);
//        BufferedWriter bw = new BufferedWriter(osw);
//
//
//
//        sb.append("@RestController\n" +
//                "@RequestMapping(\"/rest/api/"+firstCharToLower(simpleName)+"\")\n" +
//                "@Api(description = \"\")\n")
//                .append("public class " + simpleName + "Controller" + " { \n")
//                .append("\n")
//                .append("\t@Autowired\n")
//                .append("\tprivate " + simpleName + "Service " + serviceName + " ;\n\n");
//
//        //sb = createActionAddMethod(sb, clazz, serviceName, "add");
//        //sb = createActionUpdateMethod(sb, clazz, serviceName, "update");
//        //sb = createActionGetMethod(sb, clazz, serviceName, "get");
//        //sb = createActionMethod(sb, clazz, serviceName, "remove");
//        //sb = createActionMethod(sb, clazz, serviceName, "list");
//        for (String type: args) {
//            sb = createActionMethod(sb, clazz, serviceName, type);
//        }
//
//        sb.append("}");
//
//        bw.write(sb.toString());
//        bw.close();
//        osw.close();
//        fos.close();
//    }
//
////    private StringBuilder createActionAddMethod(StringBuilder sb, Class clazz, String serviceName, String type) {
////        List<Field> fields = analyzeEntity(clazz);
////        String simpleName = clazz.getSimpleName();
////        sb.append("\n");
////        /*
////        去掉id
////        */
////        sb.append("\t@ApiOperation(value = \"新建\")\n")
////                .append("\t@ApiImplicitParams({\n");
////        for (Field field: fields) {
////            if(!field.getName().equals(id)) {
////                if(field.isAnnotationPresent(ApiModelProperty.class)) {
////                    sb.append("   \t\t@ApiImplicitParam(name = \"" + field.getName() + "\",value = \"" +field.getAnnotation(ApiModelProperty.class).value() + "\"" + ",paramType = \"query\"" + ",dataType = \"" + firstCharToLower(field.getName().getClass().getSimpleName().toString()) + "\"" + ",required = true" + "),\n");
////                } else {
////                    sb.append("   \t\t@ApiImplicitParam(name = \"" + field.getName() + "\",value = \"" + "\"" + ",paramType = \"query\"" + ",dataType = \"" + firstCharToLower(field.getName().getClass().getSimpleName().toString()) + "\"" + ",required = true" + "),\n");
////                }
////            }
////        }
////        sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() -2) +"\n\t})\n");
////        sb.append("\t@RequestMapping(value = \"/"+type+"\",method = RequestMethod.PUT)\n");
////        sb.append(" \tpublic Result " + type + simpleName + "(");
////        for (Field field : fields) {
////            if(!field.getName().equals(id))
////                sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
////        }
////        sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() - 1) + " ){\n");
////        sb.append("\t\t return  " + serviceName + "."+type + clazz.getSimpleName() + "(");
////        if(type.equals("add")) {
////            for (Field field : fields) {
////                if(!(type.equals("add")&&field.getName().equals(id)))
////                    sb.append(field.getName() + ",");
////            }
////        }
////        sb = sb.deleteCharAt(sb.length() - 1);
////        sb.append(");\n");
////        sb.append("\t}\n");
////        return sb;
////    }
////
////    private StringBuilder createActionUpdateMethod(StringBuilder sb, Class clazz, String serviceName, String type) {
////        List<Field> fields = analyzeEntity(clazz);
////        String simpleName = clazz.getSimpleName();
////        sb.append("\n");
////        /*
////            两次循环，第一次得到id，第二次得到不包括id的其他内容，为了让id放在最前面
////            */
////        sb.append("\t@ApiOperation(value = \"编辑\")\n")
////                .append("\t@ApiImplicitParams({\n");
////        for (Field field: fields) {
////            if(field.getName().equals(id)) {
////                if(field.isAnnotationPresent(ApiModelProperty.class)) {
////                    sb.append("   \t\t@ApiImplicitParam(name = \"" + field.getName() + "\",value = \"" +field.getAnnotation(ApiModelProperty.class).value() + "\"" + ",paramType = \"query\"" + ",dataType = \"" + firstCharToLower(field.getName().getClass().getSimpleName().toString()) + "\"" + ",required = true" + "),\n");
////                } else {
////                    sb.append("   \t\t@ApiImplicitParam(name = \"" + field.getName() + "\",value = \"" + "\"" + ",paramType = \"query\"" + ",dataType = \"" + firstCharToLower(field.getName().getClass().getSimpleName().toString()) + "\"" + ",required = true" + "),\n");
////                }
////            }
////        }
////        for (Field field: fields) {
////            if(!field.getName().equals(id))
////                sb.append("   \t\t@ApiImplicitParam(name = \""+field.getName()+"\",value = \""+field.getAnnotation(ApiModelProperty.class).value()+"\""+",paramType = \"query\""+",dataType = \""+firstCharToLower(field.getName().getClass().getSimpleName().toString())+"\"),\n");
////        }
////        sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() -2) +"\n\t})\n");
////        sb.append("\t@RequestMapping(value = \"/"+type+"\",method = RequestMethod.POST)\n");
////        sb.append(" \tpublic Result " + type + simpleName + "(");
////        for (Field field : fields) {
////            if(field.getName().equals(id))
////                sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
////        }
////        for (Field field : fields) {
////            if(!field.getName().equals(id))
////                sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
////        }
////        sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() - 1) + " ) {\n");
////        sb.append("\t\t return  " + serviceName + "."+type + clazz.getSimpleName() + "(");
////        sb = sb.deleteCharAt(sb.length() - 1);
////        sb.append(");\n");
////        sb.append("\t}\n");
////        return sb;
////    }
////
////    private StringBuilder createActionGetMethod(StringBuilder sb, Class clazz, String serviceName, String type) {
////
////        List<Field> fields = analyzeEntity(clazz);
////        String simpleName = clazz.getSimpleName();
////        sb.append("\n");
////        sb.append("\t@ApiOperation(value = \"根据id查询具体的设备\")\n")
////                .append("\t@ApiImplicitParams( {\n");
////        for (Field field: fields) {
////            if(field.getName().equals(id)) {
////                if(field.isAnnotationPresent(ApiModelProperty.class)) {
////                    sb.append("   \t\t@ApiImplicitParam(name = \"" + field.getName() + "\",value = \"" +field.getAnnotation(ApiModelProperty.class).value() + "\"" + ",paramType = \"query\"" + ",dataType = \"" + firstCharToLower(field.getName().getClass().getSimpleName().toString()) + "\"" + ",required = true" + "),\n");
////                } else {
////                    sb.append("   \t\t@ApiImplicitParam(name = \"" + field.getName() + "\",value = \"" + "\"" + ",paramType = \"query\"" + ",dataType = \"" + firstCharToLower(field.getName().getClass().getSimpleName().toString()) + "\"" + ",required = true" + "),\n");
////                }
////            }
////        }
////        sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() -2) +"\n\t})\n");
////        sb.append("\t@RequestMapping(value = \"/"+type+"\",method = RequestMethod.GET)\n");
////        sb.append(" \tpublic "+simpleName+" "+type + simpleName + "(");
////        for (Field field : fields) {
////            if(field.getName().equals(id))
////                sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
////        }
////        for (Field field : fields) {
////            if(field.getName().equals(id)) {
////                sb.append(field.getName() + ",");
////            }
////        }
////        sb = sb.deleteCharAt(sb.length() - 1);
////        sb.append(");\n");
////        sb.append("\t}\n");
////        return sb;
////    }
//
//    private StringBuilder createActionMethod(StringBuilder sb, Class clazz, String serviceName, String type) {
//        sb.append("\n");
//        List<Field> fields = analyzeEntity(clazz);
//        String simpleName = clazz.getSimpleName();
//        if(type.equals("add")) {
//            /*
//            去掉id
//            */
//            sb.append("\t@ApiOperation(value = \"新建\")\n")
//                    .append("\t@ApiImplicitParams({\n");
//            for (Field field: fields) {
//                if(!field.getName().equals(id)) {
//                    sb.append("   \t\t@ApiImplicitParam(name = \"" + field.getName() + "\",value = \"");
//                    //字段有注释，加上注释
//                    if(field.isAnnotationPresent(ApiModelProperty.class)) {
//                        sb.append(field.getAnnotation(ApiModelProperty.class).value());
//                    }
//                    //firstCharToLower(field.getName().getClass().getSimpleName().toString()) 获取数据类型，如:string
//                    sb.append("\"" + ",paramType = \"query\"" + ",dataType = \"" + firstCharToLower(field.getName().getClass().getSimpleName().toString()) + "\""  + "),\n");
//                }
//            }
//            sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() -2) +"\n\t})\n");
//            sb.append("\t@RequestMapping(value = \"/"+type+"\",method = RequestMethod.PUT)\n");
//
//        }
//        if(type.equals("update")) {
//            /*
//            两次循环，第一次得到id，第二次得到不包括id的其他内容，为了让id放在最前面
//            */
//            sb.append("\t@ApiOperation(value = \"编辑\")\n")
//                    .append("\t@ApiImplicitParams({\n");
//            for (Field field: fields) {
//                if(field.getName().equals(id)) {
//                    sb.append("   \t\t@ApiImplicitParam(name = \"" + field.getName() + "\",value = \"");
//                    //字段有注释，加上注释
//                    if(field.isAnnotationPresent(ApiModelProperty.class)) {
//                        sb.append(field.getAnnotation(ApiModelProperty.class).value());
//                    }
//                    //firstCharToLower(field.getName().getClass().getSimpleName().toString()) 获取数据类型，如:string
//                    sb.append("\"" + ",paramType = \"query\"" + ",dataType = \"" + firstCharToLower(field.getName().getClass().getSimpleName().toString()) + "\"" + ",required = true" + "),\n");
//                }
//            }
//            for (Field field: fields) {
//                if(!field.getName().equals(id)) {
//                    sb.append("   \t\t@ApiImplicitParam(name = \"" + field.getName() + "\",value = \"");
//                    //字段有注释，加上注释
//                    if(field.isAnnotationPresent(ApiModelProperty.class)) {
//                        sb.append(field.getAnnotation(ApiModelProperty.class).value());
//                    }
//                    //firstCharToLower(field.getName().getClass().getSimpleName().toString()) 获取数据类型，如:string
//                    sb.append("\"" + ",paramType = \"query\"" + ",dataType = \"" + firstCharToLower(field.getName().getClass().getSimpleName().toString()) + "\""  + "),\n");
//                }
//            }
//            sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() -2) +"\n\t})\n");
//            sb.append("\t@RequestMapping(value = \"/"+type+"\",method = RequestMethod.POST)\n");
//        }
//        System.out.println(sb);
//        if(type.equals("get")) {
//            sb.append("\t@ApiOperation(value = \"根据id查询具体的设备\")\n")
//                    .append("\t@ApiImplicitParams({\n");
//            for (Field field: fields) {
//                if(field.getName().equals(id)) {
//                    sb.append("   \t\t@ApiImplicitParam(name = \"" + field.getName() + "\",value = \"");
//                    //字段有注释，加上注释
//                    if(field.isAnnotationPresent(ApiModelProperty.class)) {
//                        sb.append(field.getAnnotation(ApiModelProperty.class).value());
//                    }
//                    //firstCharToLower(field.getName().getClass().getSimpleName().toString()) 获取数据类型，如:string
//                    sb.append("\"" + ",paramType = \"query\"" + ",dataType = \"" + firstCharToLower(field.getName().getClass().getSimpleName().toString()) + "\"" + ",required = true" + "),\n");
//                }
//            }
//            sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() -2) +"\n\t})\n");
//            sb.append("\t@RequestMapping(value = \"/"+type+"\",method = RequestMethod.GET)\n");
//        }
//        if(type.equals("remove")) {
//            sb.append("\t@ApiOperation(value = \"删除\")\n")
//                    .append("\t@ApiImplicitParams({\n");
//            for (Field field: fields) {
//                if(field.getName().equals(id)) {
//                    sb.append("   \t\t@ApiImplicitParam(name = \"" + field.getName() + "\",value = \"");
//                    //字段有注释，加上注释
//                    if(field.isAnnotationPresent(ApiModelProperty.class)) {
//                        sb.append(field.getAnnotation(ApiModelProperty.class).value());
//                    }
//                    //firstCharToLower(field.getName().getClass().getSimpleName().toString()) 获取数据类型，如:string
//                    sb.append("\"" + ",paramType = \"query\"" + ",dataType = \"" + firstCharToLower(field.getName().getClass().getSimpleName().toString()) + "\"" + ",required = true" + "),\n");
//                }
//            }
//            sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() -2) +"\n\t})\n");
//            sb.append("\t@RequestMapping(value = \"/"+type+"\",method = RequestMethod.DELETE)\n");
//
//        }
//        if(type.equals("list")) {
//            sb.append("\t@ApiOperation(value = \"遍历素材\")\n")
//                    .append("\t@ApiImplicitParams({\n");
//            for (Field field: fields) {
//                if(field.getName().equals(id)) {
//                    sb.append("   \t\t@ApiImplicitParam(name = \"" + field.getName() + "\",value = \"");
//                    //字段有注释，加上注释
//                    if(field.isAnnotationPresent(ApiModelProperty.class)) {
//                        sb.append(field.getAnnotation(ApiModelProperty.class).value());
//                    }
//                    //firstCharToLower(field.getName().getClass().getSimpleName().toString()) 获取数据类型，如:string
//                    sb.append("\"" + ",paramType = \"query\"" + ",dataType = \"" + firstCharToLower(field.getName().getClass().getSimpleName().toString()) + "\"" + ",required = true" + "),\n");
//                }
//            }
//            sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() -2) +"\n\t})\n");
//            sb.append("\t@RequestMapping(value = \"/"+type+"\",method = RequestMethod.GET)\n");
//        }
//
//        if(type.equals("add")) {
//            sb.append(" \tpublic Result " + type + simpleName + "(");
//            for (Field field : fields) {
//                if(!field.getName().equals(id))
//                    sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
//            }
//        }
//        if(type.equals("update")) {
//            sb.append(" \tpublic Result " + type + simpleName + "(");
//            for (Field field : fields) {
//                if(field.getName().equals(id))
//                    sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
//            }
//            for (Field field : fields) {
//                if(!field.getName().equals(id))
//                sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
//            }
//        }
//        if(type.equals("get")) {
//            sb.append(" \tpublic "+simpleName+" "+type + simpleName + "(");
//            for (Field field : fields) {
//                if(field.getName().equals(id))
//                sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
//            }
//
//        }
//        if(type.equals("remove")) {
//            sb.append(" \tpublic boolean " + type + simpleName + "(");
//            for (Field field : fields) {
//                if(field.getName().equals(id))
//                sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
//            }
//
//        }
//        if(type.equals("list")) {
//            sb.append(" \tpublic "+"List<"+simpleName+">"+" "+type + simpleName + "(");
//            for (Field field : fields) {
//                if(field.getName().equals(id))
//                    sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
//            }
//
//        }
//
//        sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() - 1) + " ) {\n");
//        sb.append("\t\t return  " + serviceName + "."+type + clazz.getSimpleName() + "(");
//        if(type.equals("add")) {
//            for (Field field : fields) {
//              if(!(type.equals("add")&&field.getName().equals(id)))
//             sb.append(field.getName() + ",");
//            }
//        }
//        if(type.equals("update")) {
//            for (Field field : fields) {
//                if(field.getName().equals(id))
//                    sb.append(field.getName() + ",");
//            }
//            for (Field field : fields) {
//                if(!field.getName().equals(id))
//                    sb.append(field.getName() + ",");
//            }
//        }
//        if(type.equals("get") || type.equals("remove") || type.equals("list")) {
//            for (Field field : fields) {
//                if(field.getName().equals(id)) {
//                    sb.append(field.getName() + ",");
//                }
//            }
//        }
//        sb = sb.deleteCharAt(sb.length() - 1);
//        sb.append(");\n");
//        sb.append("\t}\n");
//        return sb;
//    }
//
//
//    /**
//     * 在指定路径下生成对应实体类的Service代码文件
//     *
//     * @param path  service层路径
//     * @param clazz entity实体类
//     * @param sb    StringBuilder
//     * @param clacc 注入类
//     * @throws IOException
//     */
//    public void generateService(String path, Class clazz,StringBuilder sb,Class clacc,String...args) throws IOException {
//        String simpleName = clazz.getSimpleName();
//
//        String name = "\\" + simpleName + "Service.java";
//        //String servicename = firstCharToLower(simpleName)+"Service";
//        File file = new File(path + name);
//        if (file.exists()) {
//            file.delete();
//            //return;
//        } else {
//            file.createNewFile();
//        }
//        FileOutputStream fos = new FileOutputStream(file);
//        OutputStreamWriter osw = new OutputStreamWriter(fos);
//        BufferedWriter bw = new BufferedWriter(osw);
//
//        sb.append("@Service\n")
//                .append("public class " + simpleName + "Service" + " { \n")
//                .append("\n")
//                .append(addAutowired(clacc));
//
//        for (String type: args) {
//            sb = createServiceMethod(sb, clazz, clacc, type);
//        }
////        sb = createServiceMethod(sb, clazz, clacc,"add");
////        sb = createServiceMethod(sb, clazz, clacc,"update");
////        sb = createServiceMethod(sb, clazz, clacc, "get");
////        sb = createServiceMethod(sb, clazz, clacc, "remove");
////        sb = createServiceMethod(sb, clazz, clacc, "list");
//        sb.append("}");
//
//        bw.write(sb.toString());
//        bw.close();
//        osw.close();
//        fos.close();
//    }
//
//    private StringBuilder createServiceMethod(StringBuilder sb, Class clazz, Class clacc,String type) {
//        List<Field> fields = analyzeEntity(clazz);
//        String simpleName = clazz.getSimpleName();
//        if(type.equals("add") ) {
//            sb.append("\tpublic Result "+type+simpleName+"(");
//            for (Field field : fields) {
//                if(!field.getName().equals(id))
//                    sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
//            }
//        }
//
//        if(type.equals("update") ) {
//            sb.append("\tpublic Result "+type+simpleName+"(");
//            for (Field field : fields) {
//                if(field.getName().equals(id))
//                    sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
//            }
//            for (Field field : fields) {
//                if(!field.getName().equals(id))
//                    sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
//            }
//
//        }
//
//        if(type.equals("get") ) {
//            sb.append("\tpublic "+simpleName+" "+type+simpleName+"(");
//            for (Field field : fields) {
//                if(field.getName().equals(id))
//                    sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
//            }
//
//        }
//
//        if(type.equals("remove") ) {
//            sb.append("\tpublic boolean "+type+simpleName+"(");
//            for (Field field : fields) {
//                if(field.getName().equals(id))
//                    sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
//            }
//        }
//
//        if(type.equals("list") ) {
//            sb.append("\tpublic List<"+simpleName+"> "+type+simpleName+"(");
//            for (Field field : fields) {
//                if(field.getName().equals(id))
//                    sb.append( simplyFieldName(field.getType().getName()) + " " + field.getName() + ",");
//            }
//        }
//
//        sb = new StringBuilder(sb.toString().substring(0, sb.toString().length() - 1) + " ) {\n");
//        if(type.equals("add")) {
//            for (Field field : fields) {
//                if(field.getName().equals(id)) {
//                    sb.append("\t\t"+simpleName+" "+firstCharToLower(simpleName)+" = new "+simpleName+"();\n");
//                }
//            }
//            for (Field field : fields) {
//                if(!field.getName().equals(id)) {
//                    sb.append("\t\t"+firstCharToLower(simpleName)+"."+"set"+firstCharToUpper(field.getName())+"("+field.getName()+");\n");
//                }
//            }
//            sb.append("\n\t\treturn new Result(("+firstCharToLower(clacc.getSimpleName())+".add("+firstCharToLower(simpleName)+"))!=null);\n\t}\n");
//        }
//
//        if(type.equals("update")) {
//            for (Field field : fields) {
//                if(field.getName().equals(id)) {
//                    sb.append("\t\t"+simpleName+" "+firstCharToLower(simpleName)+" = "+firstCharToLower(clacc.getSimpleName())+".get("+id+", "+simpleName+".class);\n");
//                }
//            }
//            for (Field field : fields) {
//                if(!field.getName().equals(id)) {
//                    sb.append("\t\t"+firstCharToLower(simpleName)+"."+"set"+firstCharToUpper(field.getName())+"("+field.getName()+");\n");
//                }
//            }
//            sb.append("\t\treturn new Result("+firstCharToLower(clacc.getSimpleName())+".update("+firstCharToLower(simpleName)+"));\n\t}\n");
//        }
//
//        if(type.equals("get")) {
//            for (Field field : fields) {
//                if(field.getName().equals(id)) {
//                    sb.append("\t\t"+simpleName+" "+firstCharToLower(simpleName)+" = "+firstCharToLower(clacc.getSimpleName())+".get("+id+", "+simpleName+".class);\n");
//                }
//            }
//            sb.append("\t\treturn "+firstCharToLower(simpleName)+";\n\t}\n");
//        }
//
//        if(type.equals("remove")) {
//            for (Field field : fields) {
//                if(field.getName().equals(id)) {
//                    sb.append("\t\t"+simpleName+" "+firstCharToLower(simpleName)+" = "+firstCharToLower(clacc.getSimpleName())+".get("+id+", "+simpleName+".class);\n");
//                }
//            }
//            sb.append("\t\treturn "+firstCharToLower(clacc.getSimpleName())+".delete("+firstCharToLower(simpleName)+");\n\t}\n");
//        }
//
//        if(type.equals("list")) {
//            for (Field field : fields) {
//                if(field.getName().equals(id)) {
//                    sb.append("\t\tList<"+simpleName+"> "+"devices"+" = new ArrayList<>(10);\n")
//                            .append("\t\tMap<String,Object> map = new HashMap<>(10);\n")
//                            .append("\t\tif (StringUtils.isNotBlank("+field.getName()+")) {\n")
//                            .append("\t\t\tmap.put(\""+field.getName()+"\", \"%\"+"+field.getName()+"+\"%\");\n\t\t}\n")
//                            .append("\t\ttry {\n\t\t\tdevices = "+firstCharToLower(clacc.getSimpleName())+".getQueryService().query(\"findMsDevice\", map, "+simpleName+".class, null, null);\n")
//                            .append("\t\t} catch (Exception e) {\n\t\t\te.printStackTrace();\n\t\t}\n\t\treturn devices;\n\t}\n");
//                }
//            }
//        }
//
//        return sb;
//    }
//
//        /**S
//         * 首字母转小写
//         *
//         * @param str
//         * @return
//         */
//    public static String firstCharToLower(String str) {
//        String substring = str.substring(0, 1);
//
//        String newstr = substring.toLowerCase() + str.substring(1, str.length()) ;
//        return newstr;
//    }
//    /**S
//     * 首字母转大写
//     *
//     * @param str
//     * @return
//     */
//    public static String firstCharToUpper(String str) {
//        String substring = str.substring(0, 1);
//
//        String newstr = substring.toUpperCase() + str.substring(1, str.length()) ;
//        return newstr;
//    }
//
//    /**
//     * 简化字段名
//     *
//     * @param str
//     * @return
//     */
//    public static String simplyFieldName(String str) {
//        String substring = str.substring(str.lastIndexOf(".") + 1, str.length());
//        return substring;
//    }
//}
