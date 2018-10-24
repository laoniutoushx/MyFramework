package aop.method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @ClassName SecurityInterceptor
 * @Description 模拟安全切面
 * @Author Suzumiya Haruhi
 * @Date 2018/10/24 21:37
 * @Version 10032
 **/
public class SecurityInterceptor implements MethodInterceptor {
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("------------------ security check ---------------");
        return invocation.proceed();
    }
}
