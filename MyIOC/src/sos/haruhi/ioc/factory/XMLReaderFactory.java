package sos.haruhi.ioc.factory;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import sos.haruhi.ioc.beans.BeanDefinition;
import sos.haruhi.ioc.beans.Property;
import sos.haruhi.util.ReflectUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XMLReaderFactory {
    private SAXReader reader = null;
    private Document document = null;

    public Element getRootElement(String path){
        try {
            reader = new SAXReader();
            File file = new File("S:\\MyFramework\\MyIOC\\src\\sos\\nagato\\test\\mySpringConfig.xml");
            document = reader.read(file);
        } catch (DocumentException e) {
            try {
                System.out.println(e.getMessage());
                document = reader.read(XMLReaderFactory.class.getResourceAsStream(path));
            } catch (DocumentException e1) {
                throw new RuntimeException("document is null");
            }
        }
        return document.getRootElement();
    }

    public List<BeanDefinition> parseBeanElement(Element root, String targetName) {
        List<Element> beans = (List<Element>) root.selectNodes(targetName);
        List<BeanDefinition> list = new ArrayList<BeanDefinition>();
        for (Element bean : beans) {
            List<Attribute> attributes = bean.attributes();
            BeanDefinition beanDefinition = new BeanDefinition();
            for (Attribute attribute : attributes) {
                System.out.println(attribute.getName() + ":" + attribute.getValue());
                ReflectUtil.invokeMethod(beanDefinition, "set" + attribute.getName().substring(0, 1).toUpperCase() + attribute.getName().substring(1), attribute.getValue());
            }
            beanDefinition.setProperties(parseBeanProperty(bean, "property"));
            list.add(beanDefinition);
        }
        return list;
    }

    public List<Property> parseBeanProperty(Element element, String targetName){
        List<Element> beans = element.selectNodes(targetName);
        List<Property> list = new ArrayList<Property>();
        for (Element bean : beans) {
            List<Attribute> attributes = bean.attributes();
            Property property = new Property();
            for (Attribute attribute : attributes) {
                System.out.println(attribute.getName() + ":" + attribute.getValue());
                ReflectUtil.invokeMethod(property, "set" + attribute.getName().substring(0, 1).toUpperCase() + attribute.getName().substring(1), attribute.getValue());
            }
            list.add(property);
        }
        return list;
    }

    public static void main(String[] args) {
        XMLReaderFactory xmlReaderFactory = new XMLReaderFactory();
        Element root = xmlReaderFactory.getRootElement(null);
        List<BeanDefinition> beans = xmlReaderFactory.parseBeanElement(root, "bean");
        BeanFactory beanFactory = new BeanFactory() {};
        beanFactory.registerBean(beans);

    }

}
