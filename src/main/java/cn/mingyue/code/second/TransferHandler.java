package cn.mingyue.code.second;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/30 14:14
 * 转出处理
 */
public interface TransferHandler<T> {
    T handle(EntityInfo info);
}
