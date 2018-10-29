package sos.haruhi.ioc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @title:
 * @desc:   切入点
 * @param:
 * @return:
 * @auther: Suzumiya Haruhi
 * @date:   2018/10/29 20:38
 **/
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface PointCut {
    String value() default "*";
}
