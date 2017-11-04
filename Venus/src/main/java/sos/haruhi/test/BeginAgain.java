package sos.haruhi.test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeginAgain {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring-context.xml");
        HelloWorld helloWorld = (HelloWorld) beanFactory.getBean("hello");
        HelloAnnotation helloAnnotation = (HelloAnnotation) beanFactory.getBean("anno");
        System.out.println(helloWorld.getName());
        System.out.println(helloAnnotation.getHelloWorld().getName());
    }
}
