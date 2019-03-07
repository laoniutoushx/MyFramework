package enum_test;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

public class EnumTest {
    public static void main(String[] args) {
        Enum e = Weekend.MONDAY;
        System.out.println(e.name());

        // 空集和
        EnumSet<Weekend> set = EnumSet.noneOf(Weekend.class);


    }
}
