package sos.haruhi;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import sos.haruhi.ioc.annotations.Around;
import sos.haruhi.ioc.annotations.Aspect;
import sos.haruhi.ioc.annotations.Before;
import sos.haruhi.ioc.annotations.PointCut;
import sos.haruhi.ioc.beans.AopBeanDefinition;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * @ClassName AopParseFactory
 * @Description TODO
 * @Author Suzumiya Haruhi
 * @Date 2018/10/27 21:31
 * @Version 10032
 **/
public class AopParseFactory {

    private String aspectPath;
    private String packagePath;

    public List<AopBeanDefinition> parseAnnotationElement(Element root, String packageXml, String aspectXml){
        this.aspectPath = aspectXml;
        this.packagePath = packageXml;
        Element component = (Element) root.selectSingleNode(packageXml);
        if(component != null){
            Attribute packageName = component.attribute("base-package");
            List<AopBeanDefinition> list = parseAnnotation(packageName.getValue());
            return list;
        }
        return null;
    }

    public List<AopBeanDefinition> parseAnnotation(String value) {
        System.out.println(value);
        // 包地址
        String packageName = value.replace(".", "/");
        Set<Class> cls = this.loadClass(value);
        Set<Class> annotationCls = getAnnotationClass(cls);
        List<AopBeanDefinition> beanDefinitions = packageBeanDefiniton(annotationCls);
        return beanDefinitions;
    }

    public Set<Class> loadClass(String packageName) {
        Enumeration<URL> dirs = null;
        Set<Class> cls = new HashSet<>();
        try {
            System.out.println("测试-=======" + this.packagePath);
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

    private Set<Class> getAnnotationClass(Set<Class> cls) {
        Set<Class> annotationCls = new HashSet<>();
        for (Class clz : cls) {
            Annotation[] annotations = clz.getDeclaredAnnotations();
            if (CollectionUtils.isNotEmpty(Arrays.asList(annotations))) {
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Aspect) {
                        annotationCls.add(clz);
                    }
                }
            }
        }
        return annotationCls;
    }

    /**
     * @title:  解析所有 aspect 对象
     * @desc:   TODO
     * @param:
     * @return:
     * @auther: Suzumiya Haruhi
     * @date:   2018/11/8 20:58
     **/
    private List<AopBeanDefinition> packageBeanDefiniton(Set<Class> annotationCls) {
        List<AopBeanDefinition> beans = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(annotationCls)){
            // 设置 aspectj
            for(Class clz:annotationCls){
                AopBeanDefinition aopBeanDefinition = new AopBeanDefinition();
                Aspect aspect = (Aspect) clz.getAnnotation(Aspect.class);
                Method[] methods = clz.getDeclaredMethods();
                for (Method method : methods) {
                    PointCut pointCut = method.getAnnotation(PointCut.class);
                    if(pointCut != null)
                        aopBeanDefinition.setPointCut(pointCut.value());
                    Around around = method.getAnnotation(Around.class);
                    if(around != null)
                        aopBeanDefinition.setArount(around.value());
                    Before before = method.getAnnotation(Before.class);
                    if(before != null)
                        aopBeanDefinition.setBefore(before.value());
                }
                beans.add(aopBeanDefinition);
            }
        }
        return beans;
    }
}
