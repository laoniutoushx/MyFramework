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

    public List<BeanDefinition> parseAnnotationElement(Element root, String targetName) {
        Element component = (Element) root.selectSingleNode(targetName);
        if(component != null){      // aspectj 配置
            // 全局扫描 Aspectj
            List<BeanDefinition> list = parseAnnotation();
            return list;
        }
        return null;
    }
}
