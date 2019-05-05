package cn.mingyue.code;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 16:53
 */
public class RegexUtil {

    public static List<String> forImports(String source) {
        List<String> imports = new ArrayList<>(8);
        String regexImport = "import (.*?);";
        Matcher m = Pattern.compile(regexImport).matcher(source);
        while (m.find()) {
            String group = m.group();
            group = group.replace("import", "").replace(";","");
            imports.add(group.trim());
        }
        return imports;
    }

    public static List<String> forModifiers(String source) {
        List<String> imports = new ArrayList<>(8);
        String regexImport = "(.*?) class";
        Matcher m = Pattern.compile(regexImport).matcher(source);
        if (m.find()) {
            String result = m.group();
            result = result.replace("class", "");
            String[] split = result.split(" ");
            for (String s : split) {
                if (StringUtils.isBlank(s)) {
                    continue;
                }
                imports.add(s.trim());
            }
        }
        return imports;
    }

}
