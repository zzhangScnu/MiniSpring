package minispring.beans.factory.support;

import minispring.beans.BeanException;
import minispring.beans.factory.ConfigurableListableBeanFactory;
import minispring.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lihua
 * @since 2021/8/26
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

	@Override
	public BeanDefinition getBeanDefinition(String name) {
		return Optional.ofNullable(beanDefinitionMap.get(name))
				.orElseThrow(() -> new BeanException("No bean named '%s' is define", name));
	}

	@Override
	public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
		beanDefinitionMap.put(name, beanDefinition);
	}

	@Override
	public boolean containsBeanDefinition(String name) {
		return beanDefinitionMap.containsKey(name);
	}

	@Override
	public void preInstantiateSingletons() throws BeanException {
		beanDefinitionMap.keySet()
				.forEach(beanName -> getBean(beanName));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
		Map<String, T> resultMap = new HashMap<>(64);
		beanDefinitionMap.entrySet().stream()
				.filter(entry -> entry.getValue().isAssignableFrom(type))
				.map(Map.Entry::getKey)
				.forEach(beanName -> resultMap.put(beanName, (T) getBean(beanName)));
		return resultMap;
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return beanDefinitionMap.keySet().toArray(new String[0]);
	}
}
