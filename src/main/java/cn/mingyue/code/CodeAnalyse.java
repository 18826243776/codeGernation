package cn.mingyue.code;

import java.util.List;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/25 9:02
 */
public interface CodeAnalyse {
    EntityInfo analyse(Object o);

    List<String> analyseImports(Object o);

    List<String> analyseModifier(Object o);
}
