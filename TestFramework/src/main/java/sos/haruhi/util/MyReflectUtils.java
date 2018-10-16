package sos.haruhi.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
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
            if(StringUtils.equals(methodName, "setClass")){
                methodName = "setBeanClass";
            }
            Method method = target.getClass().getMethod(methodName, String.class);
            if(method != null){
                method.invoke(target, value);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
