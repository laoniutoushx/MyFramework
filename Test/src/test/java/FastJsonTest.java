import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;

public class FastJsonTest {
    @Test
    public void testToBean(){
        String resStr = "{\"PS0222\":{\"PS0018\":\"1107\",\"PS0019\":\"商户不存在\"},\"PS0223\":\"haruhi\"}";
        YunFuWuRespBean respBean = JSON.parseObject(resStr, new TypeReference<YunFuWuRespBean>(){});
        System.out.println(respBean.PS0222.PS0018);
    }

    private static class YunFuWuRespBean{
        private InnerClazz PS0222;
        private String PS0223;

        public InnerClazz getPS0222() {
            return PS0222;
        }

        public void setPS0222(InnerClazz PS0222) {
            this.PS0222 = PS0222;
        }

        public String getPS0223() {
            return PS0223;
        }

        public void setPS0223(String PS0223) {
            this.PS0223 = PS0223;
        }
    }

    private static class InnerClazz{
        private String PS0018;
        private String PS0019;
        public String getPS0018() {
            return PS0018;
        }
        public void setPS0018(String pS0018) {
            PS0018 = pS0018;
        }
        public String getPS0019() {
            return PS0019;
        }
        public void setPS0019(String pS0019) {
            PS0019 = pS0019;
        }
    }
}
