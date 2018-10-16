package sos.haruhi.reflect;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName ReflectTest
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/16 20:22
 * @Version 10032
 **/
public class ReflectTest {

    @Test
    public void reflect() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Book book = new Book();
        Method method = book.getClass().getMethod("setName", String.class);
        method.invoke(book, "haruhi");
        System.out.println(book.getName());
    }
}
