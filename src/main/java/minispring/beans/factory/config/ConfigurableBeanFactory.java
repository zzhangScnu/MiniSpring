package minispring.beans.factory.config;

import minispring.beans.factory.BeanFactory;
import minispring.util.StringValueResolver;

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
     * 将内置的注解内容解析器添加到容器中
     *
     * @param stringValueResolver 注解内容解析器
     */
    void addEmbeddedValueResolver(StringValueResolver stringValueResolver);

    /**
     * 将注解的占位符内容解析为真正的值
     *
     * @param value 占位符内容，${...}
     * @return 值
     */
    String resolveEmbeddedValue(String value);

    /**
     * 销毁单例对象
     */
    void destroySingletons();
}
