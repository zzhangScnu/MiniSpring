package minispring.beans.factory;

import minispring.beans.BeanException;

/**
 * @author lihua
 * @since 2021/9/4
 */
public interface BeanClassLoaderAware extends Aware {

    /**
     * 为实现此接口的类注入资源加载类实例
     *
     * @param classLoader 资源加载类实例
     * @throws BeanException 异常
     */
    void setClassLoader(ClassLoader classLoader) throws BeanException;
}
