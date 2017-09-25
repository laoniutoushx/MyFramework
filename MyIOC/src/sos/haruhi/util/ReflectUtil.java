package sos.haruhi.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by SuzumiyaHaruhi on 2017/9/24.
 */
public class ReflectUtil {

    public static Object invokeMethod(Object instance, String methodName, Object args){
        Class clazz = instance.getClass();
        Method method = null;
        Object result = null;
        try {
            if(methodName != null && "setClass".equals(methodName)){
                methodName = "setBeanClass";
            }
            method = clazz.getDeclaredMethod(methodName, args.getClass());
            //method = clazz.getMethod(methodName, args.getClass());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            result = method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

}
