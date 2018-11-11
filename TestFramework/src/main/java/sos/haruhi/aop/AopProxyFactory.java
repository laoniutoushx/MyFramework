package sos.haruhi.aop;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import sos.haruhi.ioc.beans.AopBeanDefinition;
import sos.haruhi.ioc.beans.BeanDefinition;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName AopProxyFactory
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/27 21:31
 * @Version 10032
 **/
public class AopProxyFactory {

    public Object createMethodInterceptor(BeanDefinition target, List<AopBeanDefinition> aopBeanDefinitions) {
        Callback[] interceptors = new Callback[aopBeanDefinitions.size() + 1];

        int index = 0;
        for(AopBeanDefinition aopBeanDefinition:aopBeanDefinitions){
            MyMethodInterceptor interceptor = new MyMethodInterceptor(aopBeanDefinition);
            interceptors[index] = interceptor;
            index++;
        }
        interceptors[index] = NoOp.INSTANCE;

        MyCallBackFilter filter = new MyCallBackFilter(aopBeanDefinitions);

        Class targetClazz = null;
        try {
            targetClazz = Class.forName(target.getBeanClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClazz);
        enhancer.setCallbacks(interceptors);
        enhancer.setCallbackFilter(filter);
        enhancer.setInterceptDuringConstruction(false);
        Object result = enhancer.create();
        return result;
    }
}
