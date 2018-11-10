package sos.haruhi.ioc.factory;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import sos.haruhi.AopParseFactory;
import sos.haruhi.ioc.beans.AopBeanDefinition;
import sos.haruhi.ioc.beans.BeanDefinition;
import sos.haruhi.ioc.beans.Property;
import sos.haruhi.util.MyReflectUtils;
import sos.haruhi.util.MyStringUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class XMLReaderFactory {
    private SAXReader reader = null;
    private Document document = null;
    private List<BeanDefinition> beans;
    private List<AopBeanDefinition> aopBeans;
    private Map<String, AopBeanDefinition> beanDefinitionWhichHasAopBeanDefinition = new ConcurrentHashMap<>();

    public XMLReaderFactory(String configPath){
        Element root = this.getRootElement(configPath);
        // 1. 解析 apo annotation 配置
        List<AopBeanDefinition> aopbeans = new AopParseFactory().parseAnnotationElement(root,"component-scan", "aspectj-autoproxy");
        this.aopBeans = aopbeans;

        // 2. 解析所有 bean
        List<BeanDefinition> xmlbeans = this.parseBeanElement(root, "bean");


//        this.parseAopElemnt
        // 3. 解析 annotation 配置
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

            String id = beanDefinition.getId(); // 获取 bean 的id
            String beanClass = beanDefinition.getBeanClass(); // 获取bean 的class
            for(AopBeanDefinition aopBeanDefinition:this.aopBeans){
                // 检测是否有匹配 代理类
                // 1. 获取切入点字符串
                String point_cut_str = aopBeanDefinition.getPointCut();
                // 2. 获取 class 实例
                Class clz = null;
                try {
                    clz = Class.forName(beanClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                // 3. 遍历 class 方法，确认是否需要增强器
                Method[] methods = clz.getDeclaredMethods();
                for(Method method:methods){
                    String method_name = method.getName();
                    if(StringUtils.isNotBlank(point_cut_str)){
                        int index = point_cut_str.indexOf(method_name);
                        if(index > -1){
                            beanDefinitionWhichHasAopBeanDefinition.put(id, aopBeanDefinition);
                        }
                    }
                }
            }

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

    public Map<String, AopBeanDefinition> getAopBeans(){
        return this.beanDefinitionWhichHasAopBeanDefinition;
    }

}
