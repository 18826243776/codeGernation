package cn.mingyue.code;

import java.io.*;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 11:21
 */
public class ObjectToString {
    public static final String entityPackageUrl = "D:/github/codegeneration/src/main/java/com/zhcs/code" + "/entity";
    public static final String fileSuffix = ".java";

    public String transfer(Object o) {
        String objectString = null;
        File file = new File(entityPackageUrl);
        File file1 = new File(file, o.getClass().getSimpleName() + fileSuffix);
        try (
                FileReader fileReader = new FileReader(file1);
                BufferedReader reader = new BufferedReader(fileReader)
        ) {
            StringBuilder builder = new StringBuilder(1000);
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
//                System.out.println("line " + line + ": " + tempString);
                builder.append(tempString+"\r\n");
                line++;
            }
            objectString = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectString;
    }

    public static void main(String[] args) {
        String transfer = new ObjectToString().transfer(new TestEntity());
        System.out.println(transfer);
    }
}
