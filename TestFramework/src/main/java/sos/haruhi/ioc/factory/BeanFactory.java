package sos.haruhi.ioc.factory;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import sos.haruhi.ioc.beans.BeanDefinition;
import sos.haruhi.ioc.beans.Property;
import sos.haruhi.ioc.prototype.IBeanFactory;
import sos.haruhi.util.MyStringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by SuzumiyaHaruhi on 2017/9/24.
 */
public class BeanFactory implements IBeanFactory {


    private final static Map<String, Object> map = new ConcurrentHashMap<String, Object>();
    private final static Set<String> set = new ConcurrentSkipListSet<>();

    private final static Map<String, Object> singleInstances = new ConcurrentHashMap<>();


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
        Object result = null;
        if (singleInstances.get(name) == null) {
            synchronized (this) {
                BeanDefinition target = (BeanDefinition) map.get(name);
                result = populateBean(target);
                singleInstances.put(name, result);
            }
        } else {
            result = singleInstances.get(name);
        }
        return result;
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
                    method = clz.getMethod("set" + MyStringUtils.upperCaseStrFirst(name), obj.getClass());
                    method.invoke(result, obj);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }


            }else{
                /***
                 * 实例化 bean
                 */
                try {
                    Method[] methods = clz.getDeclaredMethods();
                    for(Method m:methods){
                        System.out.println(m.getName());
                        if(m.getName().equals("set" + MyStringUtils.upperCaseStrFirst(name))) {
                            Class[] clazzs = m.getParameterTypes();
                            System.out.println(clazzs);
                            Class type = clazzs[0];
                            if(type != String.class){
                                Method valueOf = type.getMethod("valueOf", String.class);
                                Object v = valueOf.invoke(type, value);
                                m.invoke(result, v);
                            }
                        }
                    }
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                     e.printStackTrace();
                }
            }
        }

        return result;
    }
}
