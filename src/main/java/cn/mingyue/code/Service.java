package cn.mingyue.code;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @author: 千里明月
 * @date: 2019/4/26 10:43
 */
@Target({ElementType.TYPE})
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
    String value() default "";
}
