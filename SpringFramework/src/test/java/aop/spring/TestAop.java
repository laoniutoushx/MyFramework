package aop.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName TestAop
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/23 20:51
 * @Version 10032
 **/
public class TestAop {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-aop.xml");
        TestBean test = (TestBean) applicationContext.getBean("test");
        test.test();
    }
}
