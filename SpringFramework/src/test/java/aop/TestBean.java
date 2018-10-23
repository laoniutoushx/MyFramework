package aop;

/**
 * @ClassName aop.TestBean
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/23 20:43
 * @Version 10032
 **/
public class TestBean {
    private String testStr = "testStr";

    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }
/**
 * @title:
 * @desc:   目标： 在此方法前后织入动态方法
 * @param:
 * @return:
 * @auther: Suzumiya Haruhi
 * @date:   2018/10/23 20:44
 **/
    public void test() {
        System.out.println("test");
    }
}
