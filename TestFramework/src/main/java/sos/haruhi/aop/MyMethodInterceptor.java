package sos.haruhi.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sos.haruhi.ioc.annotations.Around;
import sos.haruhi.ioc.annotations.Before;
import sos.haruhi.ioc.beans.AopBeanDefinition;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName MyMethodInterceptor
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/11/11 17:16
 * @Version 10032
 **/
public class MyMethodInterceptor implements MethodInterceptor {

    private Object target;          // Advisor 对象
    private Method beforeMethod;
    private Method aroundMethod;

    public MyMethodInterceptor(AopBeanDefinition candidateAdivsor) {
        // 获取增强器
        Class candidateClazz = null;
        try {
            candidateClazz = Class.forName(candidateAdivsor.getBeanClass());
            this.target = candidateClazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Method[] methods = candidateClazz.getDeclaredMethods();
        for(Method method:methods){
            if(method.getAnnotation(Before.class) != null){
                beforeMethod = method;
            }
            if(method.getAnnotation(Around.class) != null){
                aroundMethod = method;
            }
        }
    }

    /**
     * @title:  intercept
     * @desc:   TODO
     * @param:  [o 目标代理对象, method 目标代理方法, objects 代理参数, methodProxy 代理对象]
     * @return: java.lang.Object
     * @auther: Suzumiya Haruhi
     * @date:   2018/11/11 17:44
     **/
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        if(beforeMethod != null){
            beforeMethod.invoke(target);
        }
        Object result = methodProxy.invokeSuper(o, objects);

        return result;
    }
}
