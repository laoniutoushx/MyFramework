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
    public static Map<String, >

    public List<BeanDefinition> parseAnnotationElement(Element root, String packageXml, String aspectXml){
        Element component = (Element) root.selectSingleNode(packageXml);
        if(component != null){
            Attribute packageName = component.attribute("base-package");
            List<BeanDefinition> list = new AnnotationReaderFactory().parseAnnotation(packageName.getValue());
            return list;
        }
        return null;
    }
}
