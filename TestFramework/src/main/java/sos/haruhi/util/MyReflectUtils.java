package sos.haruhi.util;

import org.apache.commons.beanutils.BeanUtils;
import sos.haruhi.ioc.beans.Property;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName MyReflectUtils
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/15 21:14
 * @Version 10032
 **/
public class MyReflectUtils {
    public static void invokeMethod(Object target, String methodName, String value){
        try {
            Method method = target.getClass().getMethod(methodName);
            System.out.println(target.getClass().getMethods());
            if(method != null){
                method.invoke(target, value);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
