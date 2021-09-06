package minispring.beans.factory.support;

import minispring.beans.BeanException;
import minispring.beans.factory.FactoryBean;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lihua
 * @since 2021/9/6
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

	/**
	 * 缓存的是beanFactoryName和object的映射
	 */
	private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

	public Object getCachedObjectForFactoryBean(String name) {
		return factoryBeanObjectCache.get(name);
	}

	public Object getObjectFromFactoryBean(String name, FactoryBean<?> factoryBean) {
		if (factoryBean.isSingleton()) {
			Object bean = getCachedObjectForFactoryBean(name);
			if (Objects.isNull(bean)) {
				bean = doGetObjectFromFactoryBean(name, factoryBean);
				factoryBeanObjectCache.put(name, getValue(bean));
			}
			return getBean(bean);
		} else {
			return doGetObjectFromFactoryBean(name, factoryBean);
		}
	}

	private Object getBean(Object object) {
		return NULL_OBJECT.equals(object) ? null : object;
	}

	private Object getValue(Object bean) {
		return Optional.ofNullable(bean).orElse(NULL_OBJECT);
	}

	private Object doGetObjectFromFactoryBean(String name, FactoryBean<?> factoryBean) {
		try {
			return factoryBean.getObject();
		} catch (Exception e) {
			throw new BeanException("get object [%s] from factory bean error: ", e, name);
		}
	}
}
