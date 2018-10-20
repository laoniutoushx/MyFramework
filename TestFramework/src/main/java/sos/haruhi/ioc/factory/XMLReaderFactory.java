package sos.haruhi.ioc.factory;

import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import sos.haruhi.ioc.beans.BeanDefinition;
import sos.haruhi.ioc.beans.Property;
import sos.haruhi.util.MyReflectUtils;
import sos.haruhi.util.MyStringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class XMLReaderFactory {
    private SAXReader reader = null;
    private Document document = null;
    private List<BeanDefinition> beans;

    public XMLReaderFactory(String configPath){
        Element root = this.getRootElement(configPath);
        // 1. 解析所有 bean
        List<BeanDefinition> xmlbeans = this.parseBeanElement(root, "bean");
        // 2. 解析 annotation 配置
        List<BeanDefinition> anbeans = this.parseAnnotationElement(root, "component-scan");
        this.beans = (List<BeanDefinition>) CollectionUtils.union(xmlbeans, anbeans);
    }

    public Element getRootElement(String configPath){
        try {
            reader = new SAXReader();
            System.out.println(configPath);
            File file = new File(configPath);
            document = reader.read(file);
        } catch (DocumentException e) {
            try {
                System.out.println(e.getMessage());
                document = reader.read(XMLReaderFactory.class.getResourceAsStream(configPath));
            } catch (DocumentException e1) {
                throw new RuntimeException("document is null");
            }
        }
        return document.getRootElement();
    }

    public List<BeanDefinition> parseAnnotationElement(Element root, String targetName){
        Element component = (Element) root.selectSingleNode(targetName);
        if(component != null){
            Attribute packageName = component.attribute("base-package");
            List<BeanDefinition> list = new AnnotationReaderFactory().parseAnnotation(packageName.getValue());
            return list;
        }
        return null;
    }

    public List<BeanDefinition> parseBeanElement(Element root, String targetName) {
        List<Element> beans = (List<Element>) root.selectNodes(targetName);
        List<BeanDefinition> list = new ArrayList<BeanDefinition>();
        for (Element bean : beans) {
            List<Attribute> attributes = bean.attributes();
            BeanDefinition beanDefinition = new BeanDefinition();
            for (Attribute attribute : attributes) {
                //System.out.println(attribute.getName() + ":" + attribute.getValue());
                MyReflectUtils.invokeMethod(beanDefinition, "set" + MyStringUtils.upperCaseStrFirst(attribute.getName()), attribute.getValue());

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
                //System.out.println(attribute.getName() + ":" + attribute.getValue());
                MyReflectUtils.invokeMethod(property, "set" + MyStringUtils.upperCaseStrFirst(attribute.getName()), attribute.getValue());
            }
            list.add(property);
        }
        return list;
    }

    public List<BeanDefinition> getBeans(){
        return this.beans;
    }

}
