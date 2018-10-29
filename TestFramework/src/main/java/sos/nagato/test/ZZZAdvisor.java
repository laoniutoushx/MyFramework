package sos.nagato.test;

import sos.haruhi.ioc.annotations.Around;
import sos.haruhi.ioc.annotations.Aspect;
import sos.haruhi.ioc.annotations.Before;
import sos.haruhi.ioc.annotations.PointCut;
import sos.haruhi.ioc.prototype.ProceedingJoinPoint;

/**
 * @ClassName ZZZAdvisor
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/29 20:37
 * @Version 10032
 **/
@Aspect
public class ZZZAdvisor {

    @PointCut("* *.getName(..)")     // 切入点
    public void zzz(){

    }

    @Before("getName()")
    public void beforeZZZ(){
        System.out.println("Before get ZZZ name");
    }

    @Around("getName()")
    public void arountZZZ(ProceedingJoinPoint joinPoint){

    }
}
