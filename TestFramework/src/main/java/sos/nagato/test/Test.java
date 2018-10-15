package sos.nagato.test;

import sos.haruhi.ioc.factory.BeanFactory;
import sos.haruhi.ioc.factory.HaruhiApplication;

public class Test {
    public static void main(String[] args) {
        BeanFactory beanFactory = new HaruhiApplication(Test.class.getResource("/sos-spring-core.xml").getPath());
        ZZZ zzz = (ZZZ) beanFactory.getBean("hello");
        System.out.println(zzz.getId() + ":" + zzz.getName() + ":" + zzz.getPeople().getName());
    }
}
