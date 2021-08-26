package minispring.factory.support;

import minispring.BeanException;
import minispring.factory.config.BeanDefinition;

/**
 * @author lihua
 * @since 2021/8/26
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

	@Override
	protected Object createBean(String name, BeanDefinition beanDefinition) {
		Object bean;
		try {
			bean = beanDefinition.getBeanClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new BeanException("create bean failed: ", e);
		}
		putSingleton(name, bean);
		return bean;
	}
}
