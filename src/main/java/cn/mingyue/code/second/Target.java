package cn.mingyue.code.second;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/30 17:30
 * 输出目标
 */
public interface Target<T> {
    T output(EntityInfo info);
}
