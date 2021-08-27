package minispring.beans.factory.strategy;

import minispring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @author lihua
 * @since 2021/8/26
 */
@SuppressWarnings("rawtypes")
public interface InstantiationStrategy {

	/**
	 * 实例化bean
	 *
	 * @param beanDefinition bean定义
	 * @param constructor    构造方法
	 * @param args           bean的构造方法参数
	 * @return bean
	 */
	Object instantiate(BeanDefinition beanDefinition, Constructor constructor, Object[] args);
}
