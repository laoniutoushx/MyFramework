package sos.haruhi.ioc.factory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import sos.haruhi.ioc.annotations.Component;
import sos.haruhi.ioc.annotations.Inject;
import sos.haruhi.ioc.beans.BeanDefinition;
import sos.haruhi.ioc.beans.Property;
import sos.haruhi.util.MyStringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * @ClassName AnnotationReaderFactory
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/17 21:06
 * @Version 10032
 **/
public class AnnotationReaderFactory {
    private String packagePath;
    private Map<String, BeanDefinition> tempDefinition = new HashMap<>();
    private Map<Class, String> tempClass = new HashMap<>();

    public List<BeanDefinition> parseAnnotation(String value) {
        System.out.println(value);
        // 包地址
        this.packagePath = value.replace(".", "/");
        Set<Class> cls = this.loadClass(value);
        Set<Class> annotationCls = getAnnotationClass(cls);
        List<BeanDefinition> beanDefinitions = packageBeanDefiniton(annotationCls);
        return beanDefinitions;
    }

    private List<BeanDefinition> packageBeanDefiniton(Set<Class> annotationCls) {
        if(CollectionUtils.isNotEmpty(annotationCls)){
            // 设置 class
            for(Class clz:annotationCls){
                BeanDefinition definition = new BeanDefinition();
                Component component = (Component) clz.getAnnotation(Component.class);
                String id = component.value();
                if(StringUtils.isBlank(id)){
                    id = MyStringUtils.lowerCaseStrFirst(clz.getSimpleName());
                    definition.setId(id);
                }else{
                    definition.setId(id);
                }
                definition.setBeanClass(clz.getName());
                tempDefinition.put(id, definition);
                tempClass.put(clz, id);
            }
            // 设置 property
            for(Class clz:annotationCls){
                BeanDefinition definition = tempDefinition.get(tempClass.get(clz));
                List<Property> properties = packageBeanProporities(clz);
                definition.setProperties(properties);
            }
        }
        return new ArrayList<>(tempDefinition.values());
    }

    private List<Property> packageBeanProporities(Class clz) {
        List<Property> properties = new ArrayList<>();
        Field[] fields = clz.getDeclaredFields();
        for(Field field:fields){
            Inject inject = field.getAnnotation(Inject.class);
            if(inject != null){
                Property property = new Property();
                String id = MyStringUtils.lowerCaseStrFirst(field.getType().getSimpleName());
                property.setName(field.getName());
                property.setRef(id);
                properties.add(property);
            }
        }
        return properties;
    }

    private Set<Class> getAnnotationClass(Set<Class> cls) {
        Set<Class> annotationCls = new HashSet<>();
        for (Class clz : cls) {
                Annotation[] annotations = clz.getDeclaredAnnotations();
                if (CollectionUtils.isNotEmpty(Arrays.asList(annotations))) {
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Component) {
                            annotationCls.add(clz);
                        }
                    }
                }
        }
        return annotationCls;
    }

    public Set<Class> loadClass(String packageName) {
        Enumeration<URL> dirs = null;
        Set<Class> cls = new HashSet<>();
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(this.packagePath);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                // 获取协议名称
                String protocol = url.getProtocol();

                if (StringUtils.equals("file", protocol)) {
                    System.out.println("file 类型扫描");
                    // 获取文件路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    System.out.println("文件路径：" + filePath);
                    addFileClass(cls, filePath, packageName);
                } else {
                    System.out.println("不支持的文件协议");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cls;
    }

    private void addFileClass(Set<Class> cls, String packagePath, String packageName) throws ClassNotFoundException {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class"))
                        || file.isDirectory();
            }
        });

        for (File file : files) {
            // 目录
            if (file.isDirectory()) {
                addFileClass(cls, file.getAbsolutePath(), packageName + "." + file.getName());
            } else {
                // class 文件
                String className = file.getName().substring(0, file.getName().length() - 6);
                // 加载类
                //Class clz = Class.forName(classPath);   // 此种方式会触发 static 静态方法，股改用类加载器

                Class clz = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);

                cls.add(clz);
            }
        }
    }

}
