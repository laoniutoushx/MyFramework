package sos.nagato.test;

import sos.haruhi.ioc.annotations.Component;
import sos.haruhi.ioc.annotations.Inject;
import sos.haruhi.ioc.factory.BeanFactory;
import sos.haruhi.ioc.factory.HaruhiApplication;

@Component
public class Test {

    @Inject
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    @org.junit.Test
    public void testContainer(){
        BeanFactory beanFactory = new HaruhiApplication(Test.class.getResource("/sos-spring-core.xml").getPath());
        ZZZ zzz = (ZZZ) beanFactory.getBean("zzz");
        XXX xxx = (XXX) beanFactory.getBean("xxx");
        System.out.println(zzz.getId() + ":" + zzz.getName() + ":" + zzz.getPeople().getName());
        System.out.println(xxx.getId() + ":" + xxx.getName() + ":" + xxx.getStudent().getId() + ":" + xxx.getStudent().getName());

        Test test = (Test) beanFactory.getBean("test");
        System.out.println(test.order.getOrderDetail());
    }
}
