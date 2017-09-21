package sos.haruhi.ioc.beans;

public class BeanDefinition {
    private String id;

    private String name;

    private String alias;
    private String beanClass;
    private String scope;
    private Property properties;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public Property getProperties() {
        return properties;
    }

    public void setProperties(Property properties) {
        this.properties = properties;
    }
}
