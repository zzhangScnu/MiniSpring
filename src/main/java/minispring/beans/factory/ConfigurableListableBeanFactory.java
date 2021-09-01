package minispring.beans.factory;

import minispring.beans.BeanException;
import minispring.beans.factory.config.AutowireCapableBeanFactory;
import minispring.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author lihua
 * @since 2021/8/31
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 实例化单例bean
     *
     * @throws BeanException 异常
     */
    void preInstantiateSingletons() throws BeanException;
}
