package cn.mingyue.code.second;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CodeUtils {

//	public static void main(String[] args) {
//		String packageName = ""; //填入完整包名，如com.org.String
//		Set<String> classNames = getClassName(packageName, false);
//		if (classNames != null) {
//			for (String className : classNames) {
//				System.out.println(className);
//			}
//		}
//	}

    /**
     * 判断对象是否基本类型和其封装类型
     *
     * @param obj
     * @return
     */
    public static boolean isPrimitive(Object obj) {
        try {
            return ((Class<?>) obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param array
     * @return
     */
    public static boolean isPrimitiveArray(Object array) {
        if (array instanceof boolean[]) {
            return true;
        } else if (array instanceof byte[]) {
            return true;
        } else if (array instanceof char[]) {
            return true;
        } else if (array instanceof double[]) {
            return true;
        } else if (array instanceof float[]) {
            return true;
        } else if (array instanceof int[]) {
            return true;
        } else if (array instanceof long[]) {
            return true;
        } else if (array instanceof short[]) {
            return true;
        }
        return false;
    }

    /**
     * 获取某包下所有类
     *
     * @param packageName 包名
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    public static Set<String> getClassName(String packageName, boolean isRecursion) {
        Set<String> classNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");

        URL url = loader.getResource(packagePath);
        if (url != null) {
            String protocol = url.getProtocol();
            if (protocol.equals("file")) {
                classNames = getClassNameFromDir(url.getPath(), packageName, isRecursion);
            } else if (protocol.equals("jar")) {
                JarFile jarFile = null;
                try {
                    jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (jarFile != null) {
                    getClassNameFromJar(jarFile.entries(), packageName, isRecursion);
                }
            }
        } else {
            /*从所有的jar包中查找包名*/
            classNames = getClassNameFromJars(((URLClassLoader) loader).getURLs(), packageName, isRecursion);
        }

        return classNames;
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath    文件路径
     * @param className   类名集合
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    @SuppressWarnings("unused")
    private static Set<String> getClassNameFromDir(String filePath, String packageName, boolean isRecursion) {
        Set<String> className = new HashSet<String>();
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File childFile : files) {
            //检查一个对象是否是文件夹
            if (childFile.isDirectory()) {
                if (isRecursion) {
                    className.addAll(getClassNameFromDir(childFile.getPath(), packageName + "." + childFile.getName(), isRecursion));
                }
            } else {
                String fileName = childFile.getName();
                //endsWith() 方法用于测试字符串是否以指定的后缀结束。  !fileName.contains("$") 文件名中不包含 '$'
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                    className.add(packageName + "." + fileName.replace(".class", ""));
                }
            }
        }

        return className;
    }


    /**
     * @param jarEntries
     * @param packageName
     * @param isRecursion
     * @return
     */
    private static Set<String> getClassNameFromJar(Enumeration<JarEntry> jarEntries, String packageName, boolean isRecursion) {
        Set<String> classNames = new HashSet<String>();

        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            if (!jarEntry.isDirectory()) {
                /*
                 * 这里是为了方便，先把"/" 转成 "." 再判断 ".class" 的做法可能会有bug
                 * (FIXME: 先把"/" 转成 "." 再判断 ".class" 的做法可能会有bug)
                 */
                String entryName = jarEntry.getName().replace("/", ".");
                if (entryName.endsWith(".class") && !entryName.contains("$") && entryName.startsWith(packageName)) {
                    entryName = entryName.replace(".class", "");
                    if (isRecursion) {
                        classNames.add(entryName);
                    } else if (!entryName.replace(packageName + ".", "").contains(".")) {
                        classNames.add(entryName);
                    }
                }
            }
        }

        return classNames;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     *
     * @param urls        URL集合
     * @param packageName 包路径
     * @param isRecursion 是否遍历子包
     * @return 类的完整名称
     */
    private static Set<String> getClassNameFromJars(URL[] urls, String packageName, boolean isRecursion) {
        Set<String> classNames = new HashSet<String>();

        for (int i = 0; i < urls.length; i++) {
            String classPath = urls[i].getPath();

            //不必搜索classes文件夹
            if (classPath.endsWith("classes/")) {
                continue;
            }

            JarFile jarFile = null;
            try {
                jarFile = new JarFile(classPath.substring(classPath.indexOf("/")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (jarFile != null) {
                classNames.addAll(getClassNameFromJar(jarFile.entries(), packageName, isRecursion));
            }
        }

        return classNames;
    }


    /**
     * S
     * 首字母转小写
     *
     * @param str
     * @return
     */
    public static String firstCharToLower(String str) {
        String substring = str.substring(0, 1);

        String newstr = substring.toLowerCase() + str.substring(1, str.length());
        return newstr;
    }

    /**
     * S
     * 首字母转大写
     *
     * @param str
     * @return
     */
    public static String firstCharToUpper(String str) {
        String substring = str.substring(0, 1);

        String newstr = substring.toUpperCase() + str.substring(1, str.length());
        return newstr;
    }

    /**
     * 简化字段名
     *
     * @param str
     * @return
     */
    public static String simplyFieldName(String str) {
        String substring = str.substring(str.lastIndexOf(".") + 1, str.length());
        return substring;
    }

}
