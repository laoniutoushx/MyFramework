package sos.haruhi.aop;

import net.sf.cglib.proxy.CallbackFilter;
import org.apache.commons.lang3.StringUtils;
import sos.haruhi.ioc.beans.AopBeanDefinition;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName MyCallBackFilter
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/11/11 17:50
 * @Version 10032
 **/
public class MyCallBackFilter implements CallbackFilter {

    private List<AopBeanDefinition> aops;

    public MyCallBackFilter(List<AopBeanDefinition> aops) {
        this.aops = aops;
    }

    @Override
    public int accept(Method method) {
        if(aops != null && aops.size() > 0){
            for(int i = 0; i < aops.size(); i++){
                if(StringUtils.equals(method.getName(), aops.get(i).getInterceptorMethodName())){
                    return i;
                }
            }
            return aops.size();
        }
        return 0;
    }
}
