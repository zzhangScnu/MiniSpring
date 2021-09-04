package minispring.beans.factory;

import minispring.beans.BeanException;

/**
 * @author lihua
 * @since 2021/9/4
 */
public interface BeanFactoryAware extends Aware {

    /**
     * 为实现此接口的类注入bean工厂实例
     *
     * @param beanFactory bean工厂实例
     * @throws BeanException 异常
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeanException;
}
