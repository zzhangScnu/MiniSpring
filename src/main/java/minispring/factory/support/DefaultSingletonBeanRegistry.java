package minispring.factory.support;

import minispring.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lihua
 * @since 2021/8/25
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

	private Map<String, Object> singletonMap = new ConcurrentHashMap<>();

	@Override
	public Object getSingleton(String name) {
		return singletonMap.get(name);
	}

	protected void putSingleton(String name, Object bean) {
		singletonMap.put(name, bean);
	}
}
