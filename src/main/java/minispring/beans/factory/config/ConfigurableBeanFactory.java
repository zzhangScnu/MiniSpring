package minispring.beans.factory.config;

import minispring.beans.factory.BeanFactory;

/**
 * @author lihua
 * @since 2021/8/31
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {

    /**
     * 注册beanPostProcessor
     *
     * @param beanPostProcessor bean实例化后处理器
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
