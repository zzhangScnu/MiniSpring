package minispring.beans.factory.support;

import minispring.beans.BeanException;
import minispring.beans.factory.config.BeanDefinition;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lihua
 * @since 2021/8/26
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

	@Override
	protected BeanDefinition getBeanDefinition(String name) {
		return Optional.ofNullable(beanDefinitionMap.get(name))
				.orElseThrow(() -> new BeanException(String.format("No bean named '%s' is define", name)));
	}

	@Override
	public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
		beanDefinitionMap.put(name, beanDefinition);
	}

	@Override
	public boolean containsBeanDefinition(String name) {
		return beanDefinitionMap.containsKey(name);
	}
}
