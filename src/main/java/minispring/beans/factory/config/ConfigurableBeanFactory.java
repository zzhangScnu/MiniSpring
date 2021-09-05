package minispring.beans.factory.config;

import minispring.beans.factory.BeanFactory;

/**
 * @author lihua
 * @since 2021/8/31
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 注册beanPostProcessor
     *
     * @param beanPostProcessor bean实例化后处理器
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例对象
     */
    void destroySingletons();
}
