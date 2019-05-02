package cn.mingyue.code.second;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/30 14:22
 * 解析器
 */
public interface AnalyseProvider<T> {
    EntityInfo analyse(T source);
}
