package minispring.beans.factory.support;

import minispring.beans.factory.BeanFactory;
import minispring.beans.factory.config.BeanDefinition;

import java.util.Objects;

/**
 * @author lihua
 * @since 2021/8/25
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

	@Override
	public Object getBean(String name, Object... args) {
		Object singletonBean = getSingleton(name);
		if (Objects.nonNull(singletonBean)) {
			return singletonBean;
		}
		BeanDefinition beanDefinition = getBeanDefinition(name);
		return createBean(name, beanDefinition, args);
	}

	/**
	 * 获取bean定义
	 *
	 * @param name bean的名字
	 * @return beanDefinition
	 */
	protected abstract BeanDefinition getBeanDefinition(String name);

	/**
	 * 创建一个新的bean
	 *
	 * @param name           bean的名字
	 * @param beanDefinition bean定义
	 * @param args           构造方法的参数
	 * @return bean
	 */
	protected abstract Object createBean(String name, BeanDefinition beanDefinition, Object[] args);
}
