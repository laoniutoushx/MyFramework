package aop.method;

import aop.method.service.IUserService;
import aop.method.service.UserService;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * @ClassName Main
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/24 21:39
 * @Version 10032
 **/
public class Main {
    public static void main(String[] args) {
        // 初始化资源对象
        IUserService target = new UserService();
        // AOP 代理工程
        ProxyFactory proxyFactory = new ProxyFactory(target);
        //  装配 Advice
        proxyFactory.addAdvice(new SecurityInterceptor());
        proxyFactory.addAdvice(new LoggerBeforeAdvice());
//        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(new LoggerBeforeAdvice()));
        // 获取代理对象
        IUserService proxy = (IUserService) proxyFactory.getProxy();
        // 调用业务
        proxy.updateUser();
    }
}
