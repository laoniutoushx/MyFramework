package aop.method;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @ClassName LoggerBeforeAdvice
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/24 21:39
 * @Version 10032
 **/
public class LoggerBeforeAdvice implements MethodBeforeAdvice {
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("----------------------- log saved --------------------");
    }
}
