package sos.nagato.test;

import sos.haruhi.ioc.factory.BeanFactory;
import sos.haruhi.ioc.factory.HaruhiApplication;
import sos.haruhi.ioc.prototype.IBeanFactory;

public class Test {
    public static void main(String[] args) {
        BeanFactory beanFactory = new HaruhiApplication(System.getProperty("user.dir") + "\\MyIOC\\resource\\mySpringConfig.xml");
        ZZZ zzz = (ZZZ) beanFactory.getBean("hello");
        System.out.println(zzz);
    }
}
