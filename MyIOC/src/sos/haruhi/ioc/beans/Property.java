package sos.haruhi.ioc.beans;

public class Property {
    private String name;
    private Object value;
    private String ref;

    public Property(String name, Object value, String ref) {
        this.name = name;
        this.value = value;
        this.ref = ref;
    }
}
