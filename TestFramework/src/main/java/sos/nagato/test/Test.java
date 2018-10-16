package sos.nagato.test;

import sos.haruhi.ioc.factory.BeanFactory;
import sos.haruhi.ioc.factory.HaruhiApplication;

public class Test {
    public static void main(String[] args) {
        BeanFactory beanFactory = new HaruhiApplication(Test.class.getResource("/sos-spring-core.xml").getPath());
        ZZZ zzz = (ZZZ) beanFactory.getBean("zzz");
        XXX xxx = (XXX) beanFactory.getBean("xxx");
        System.out.println(zzz.getId() + ":" + zzz.getName() + ":" + zzz.getPeople().getName());
        System.out.println(xxx.getId() + ":" + xxx.getName() + ":" + xxx.getStudent().getId() + ":" + xxx.getStudent().getName());
    }
}
