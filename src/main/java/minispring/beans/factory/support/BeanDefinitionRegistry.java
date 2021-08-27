package minispring.beans.factory.support;

import minispring.beans.factory.config.BeanDefinition;

/**
 * @author lihua
 * @since 2021/8/26
 */
public interface BeanDefinitionRegistry {

	/**
	 * 向容器中注册bean定义
	 *
	 * @param name           bean名字
	 * @param beanDefinition bean定义
	 */
	void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
