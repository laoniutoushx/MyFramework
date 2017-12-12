package sos.haruhi.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Description sos.haruhi.test in MyFramework
 * Created by SuzumiyaHaruhi on 2017/10/29.
 */
@Component(value = "anno")
public class HelloAnnotation implements ApplicationContextAware {

    private int id;
    @Autowired
    private HelloWorld helloWorld;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HelloWorld getHelloWorld() {
        return helloWorld;
    }

    public void setHelloWorld(HelloWorld helloWorld) {
        this.helloWorld = helloWorld;
    }

    public void scanTest(){
        ScanTest scanTest = (ScanTest) applicationContext.getBean("scanTest");
        System.out.println(scanTest.toString());
    }

    private ApplicationContext applicationContext;

    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * <p>Invoked after population of normal bean properties but before an init callback such
     * as {@link InitializingBean#afterPropertiesSet()}
     * or a custom init-method. Invoked after {@link ResourceLoaderAware#setResourceLoader},
     * {@link ApplicationEventPublisherAware#setApplicationEventPublisher} and
     * {@link MessageSourceAware}, if applicable.
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws ApplicationContextException in case of context initialization errors
     * @throws BeansException              if thrown by application context methods
     * @see BeanInitializationException
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
