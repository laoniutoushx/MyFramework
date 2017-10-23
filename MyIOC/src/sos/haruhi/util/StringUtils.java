package sos.haruhi.util;

/**
 * Description sos.haruhi.util in MyFramework
 * Created by SuzumiyaHaruhi on 2017/10/22.
 */
public class StringUtils {
    public static boolean isNotBlank(String target){
        return target != null && target.trim().length() > 0 && !target.trim().equals("");
    }

    public static boolean isBlank(String target){
        return target == null || target.trim().length() == 0 || target.trim().equals("");
    }

    public static String upperCaseFirst(String target){
        return target.substring(0,1).toUpperCase() + target.substring(1);
    }
}
