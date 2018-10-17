package sos.haruhi.ioc.factory;

import sos.haruhi.ioc.beans.BeanDefinition;

import java.util.List;

/**
 * @ClassName AnnotationReaderFactory
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/17 21:06
 * @Version 10032
 **/
public class AnnotationReaderFactory {
    private String packagePath;

    public List<BeanDefinition> parseAnnotation(String value) {
        System.out.println(value);
        // 包地址
        this.getClass().getResource(value);
        return null;
    }

}
