package cn.mingyue.code;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/26 13:38
 */
public class ToJava {

    public static final String packageUrl = "D:/github/codegeneration/src/main/java/cn/mingyue/code";

    public String toJava(EntityInfo info) {
        TransferForString transfer = new TransferForString();
        String result = transfer.transto(info);
        String className = info.getClassName();

        File file = new File(packageUrl, className + ".java");
        if (file.exists()) {
//            return null;
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try (FileOutputStream outputStream = new FileOutputStream(file)
        ) {
            outputStream.write(result.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
