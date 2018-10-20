package sos.haruhi.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName MyStringUtils
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/15 21:06
 * @Version 10032
 **/
public class MyStringUtils {

    public static String upperCaseStrFirst(String target){
        if(StringUtils.isNotBlank(target)){
            return target.substring(0, 1).toUpperCase() + target.substring(1);
        }
        throw new RuntimeException("字符串转换异常");
    }

    public static String lowerCaseStrFirst(String target){
        if(StringUtils.isNotBlank(target)){
            return target.substring(0, 1).toLowerCase() + target.substring(1);
        }
        throw new RuntimeException("字符串转换异常");
    }
}
