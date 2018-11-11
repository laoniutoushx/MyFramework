package sos.haruhi.ioc.beans;

/**
 * @ClassName AopBeanDefinition
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/29 20:29
 * @Version 10032
 **/
public class AopBeanDefinition {
    private String beanClass;
    private String pointCut;
    private String before;
    private String arount;
    private String after;
    private String interceptorMethodName;

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getPointCut() {
        return pointCut;
    }

    public void setPointCut(String pointCut) {
        this.pointCut = pointCut;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getArount() {
        return arount;
    }

    public void setArount(String arount) {
        this.arount = arount;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getInterceptorMethodName() {
        return interceptorMethodName;
    }

    public void setInterceptorMethodName(String interceptorMethodName) {
        this.interceptorMethodName = interceptorMethodName;
    }
}
