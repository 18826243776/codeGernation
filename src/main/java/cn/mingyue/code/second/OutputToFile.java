package cn.mingyue.code.second;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/30 16:23
 * 转化成java文件
 */
public class OutputToFile implements Target<String> {

    public String packageUrl = "D:/github/codegeneration/src/main/java/cn/mingyue/code/second";
    public static final String JAVA_FILE_SUFFIX = ".java";

    public void setPackageUrl(String pkgUrl) {
        packageUrl = pkgUrl;
    }

    @Override
    public String output(EntityInfo info) {
        TransferHandler<String> transfer = new TransferToString();
        String result = transfer.handle(info);
        String className = info.getClassName();

        File file = new File(packageUrl, className + JAVA_FILE_SUFFIX);
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
