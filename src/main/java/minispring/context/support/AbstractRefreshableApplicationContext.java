package minispring.context.support;

import minispring.beans.BeanException;
import minispring.beans.factory.ConfigurableListableBeanFactory;
import minispring.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author lihua
 * @since 2021/8/31
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeanException {
        DefaultListableBeanFactory defaultListableBeanFactory = createBeanFactory();
        loadBeanDefinitions(defaultListableBeanFactory);
        this.beanFactory = defaultListableBeanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * 加载所有的beanDefinition
     *
     * @param beanFactory bean工厂
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
