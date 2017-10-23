package sos.haruhi.ioc.factory;

import org.dom4j.Element;
import sos.haruhi.ioc.beans.BeanDefinition;
import sos.haruhi.ioc.prototype.IBeanFactory;

import java.util.List;

public class HaruhiApplication extends BeanFactory {
    public HaruhiApplication(String configPath){
        super(configPath);
    }
}
