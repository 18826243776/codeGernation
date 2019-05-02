package cn.mingyue.code;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/26 13:54
 */

import java.lang.annotation.*;


@Target({ElementType.TYPE,ElementType.METHOD})
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    String value() default "";

    String param() default "";

    int parasm() default 1;


}
