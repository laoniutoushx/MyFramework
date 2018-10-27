package sos.haruhi;

import org.dom4j.Attribute;
import org.dom4j.Element;
import sos.haruhi.ioc.beans.BeanDefinition;
import sos.haruhi.ioc.factory.AnnotationReaderFactory;

import java.util.List;
import java.util.Set;

/**
 * @ClassName AopParseFactory
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/27 21:31
 * @Version 10032
 **/
public class AopParseFactory {

    public List<BeanDefinition> parseAnnotationElement(Element root, String targetName) {
        Element component = (Element) root.selectSingleNode(targetName);
        if(component != null){
            // 全局扫描
            List<BeanDefinition> list = parseAnnotation();
            return list;
        }
        return null;
    }

    private List<BeanDefinition> parseAnnotation() {
        Set<Class> clazzs = this.loadClassByFile();
        return null;
    }

    private Set<Class> loadClassByFile() {
//        Thread.currentThread().getContextClassLoader().
        return null;
    }
}
