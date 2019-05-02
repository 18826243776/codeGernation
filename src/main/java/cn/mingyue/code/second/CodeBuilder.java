package cn.mingyue.code.second;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/30 15:46
 */
public interface CodeBuilder<T> {

    CodeBuilder pkg();

    CodeBuilder imports();

    CodeBuilder annotations();

    CodeBuilder modifiers();

    CodeBuilder classType();

    CodeBuilder className();

    CodeBuilder fields();

    CodeBuilder methods();

    T build();
}
