package sos.haruhi.ioc.factory;

import com.sun.istack.internal.NotNull;
import org.dom4j.Element;
import sos.haruhi.ioc.beans.BeanDefinition;
import sos.haruhi.ioc.beans.Property;
import sos.haruhi.ioc.prototype.IBeanFactory;
import sos.haruhi.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by SuzumiyaHaruhi on 2017/9/24.
 */
public class BeanFactory implements IBeanFactory {


    private final static Map<String, Object> map = new ConcurrentHashMap<String, Object>();
    private final static Set<String> set = new HashSet<>();

    public BeanFactory(String configPath){
        List<BeanDefinition> beans = this.initXMLConfig(configPath);
        this.registerBean(beans);
    }

    private List<BeanDefinition> initXMLConfig(String configPath){
        XMLReaderFactory xmlReaderFactory = new XMLReaderFactory(configPath) {
            @Override
            public Element getRootElement(String configPath) {
                return super.getRootElement(configPath);
            }

            @Override
            public List<BeanDefinition> parseBeanElement(Element root, String targetName) {
                return super.parseBeanElement(root, targetName);
            }
        };
        return xmlReaderFactory.getBeans();
    }

    protected void registerBean(List<BeanDefinition> beans){
        for(BeanDefinition bean:beans){
            set.add(bean.getId());
            map.put(bean.getId(), bean);
        }
    }
    public Object get(String key){
        if(map.containsKey(key))
            return map.get(key);
        else
            throw new RuntimeException("未找到此类");
    }

    @Override
    public Object getBean(String name) {
        BeanDefinition target = (BeanDefinition) map.get(name);
        Object obj = populateBean(target);

        return obj;
    }

    private Object populateBean(BeanDefinition target){
        Object result = null;
        try {
            Class clz = Class.forName(target.getBeanClass());
            result = clz.newInstance();
            setPropertyValues(clz, result, target);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Object setPropertyValues(Class clz, Object result, BeanDefinition bean){
        List<Property> properties = bean.getProperties();
        for(Property property:properties){
            String name = property.getName();
            String value = property.getValue();
            String ref = property.getRef();
            if(StringUtils.isNotBlank(ref)){

                Object obj = this.getBean(ref);

                Method method = null;
                try {
                    method = clz.getMethod("set" + StringUtils.upperCaseFirst(name), obj.getClass());
                    method.invoke(result, obj);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }else{
                try {
                    Method method = clz.getMethod("set" + StringUtils.upperCaseFirst(name), String.class);
                    method.invoke(result, value);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                     e.printStackTrace();
                }
            }
        }

        return result;
    }
}
