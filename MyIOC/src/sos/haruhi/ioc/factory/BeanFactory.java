package sos.haruhi.ioc.factory;

import sos.haruhi.ioc.beans.BeanDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SuzumiyaHaruhi on 2017/9/24.
 */
public abstract class BeanFactory {
    private final static Map<String, Object> map = new HashMap<String, Object>();
    protected void registerBean(List<BeanDefinition> beans){
        for(BeanDefinition bean:beans){
            map.put(bean.getId(), bean);
        }
    }
    public Object get(String key){
        if(map.containsKey(key))
            return map.get(key);
        else
            throw new RuntimeException("未找到此类");
    }
}
