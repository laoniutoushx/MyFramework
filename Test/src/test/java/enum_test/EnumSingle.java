package enum_test;

/**
 * 调用
 * EnumSingle.INSTANCE
 * 访问
 */
public enum EnumSingle {
    INSTANCE;
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
