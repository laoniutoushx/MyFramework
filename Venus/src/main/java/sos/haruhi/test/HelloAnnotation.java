package sos.haruhi.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description sos.haruhi.test in MyFramework
 * Created by SuzumiyaHaruhi on 2017/10/29.
 */
@Component(value = "anno")
public class HelloAnnotation {

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
}
