package sos.haruhi.ioc.factory;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import sos.haruhi.ioc.beans.BeanDefinition;
import sos.haruhi.ioc.beans.Property;

import java.io.File;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XMLReaderFactory {
    private SAXReader reader = new SAXReader();
    private Document document = null;

    public Document getDocument(String path){
        try {
            document = reader.read(new File((path)));
        } catch (DocumentException e) {
            try {
                document = reader.read(XMLReaderFactory.class.getResourceAsStream(path));
            } catch (DocumentException e1) {
                e1.printStackTrace();
            }
        }
        return document;
    }

    public Element parseDocument(Document document){
        List<Node> nodes = document.selectNodes("/bean");
        List<BeanDefinition> beans = new ArrayList<BeanDefinition>();
        for(Node node:nodes){
            String id = node.valueOf("id");
            String className = node.valueOf("class");
            BeanDefinition bean = new BeanDefinition();
            bean.setId(id);
            bean.setBeanClass(className);
            this.parseBeanProperty(node);
            beans.add(bean);
        }
        return null;
    }

    public List<Property> parseBeanProperty(Element node){
        List<Property> list = new ArrayList<>();
        Iterator it = node.attributeIterator();
        while(it.hasNext()){
            Attribute attr = (Attribute) it.next();
            attr.get
        }
        for(Iterator it:node.attributeIterator())
    }

    public



}
