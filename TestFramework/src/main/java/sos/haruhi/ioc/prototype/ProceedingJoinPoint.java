package sos.haruhi.ioc.prototype;

public interface ProceedingJoinPoint {
    //void set$AroundClosure(AroundClosure var1);

    Object proceed() throws Throwable;

    Object proceed(Object[] var1) throws Throwable;
}
