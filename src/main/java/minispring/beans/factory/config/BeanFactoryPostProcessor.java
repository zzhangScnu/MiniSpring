package minispring.beans.factory.config;

import minispring.beans.BeanException;
import minispring.beans.factory.ConfigurableListableBeanFactory;

/**
 * @author lihua
 * @since 2021/8/31
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有beanDefinition加载之后、bean对象实例化之前，提供修改beanDefinition的属性的机制
     *
     * @param beanFactory bean工厂
     * @throws BeanException 异常
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException;
}
