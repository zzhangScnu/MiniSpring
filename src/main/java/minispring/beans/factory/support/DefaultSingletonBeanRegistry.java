package minispring.beans.factory.support;

import minispring.beans.BeanException;
import minispring.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lihua
 * @since 2021/8/25
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String, Object> singletonMap = new ConcurrentHashMap<>();

    private Map<String, DisposableBeanAdapter> disposableBeanMap = new ConcurrentHashMap<>();

    @Override
    public Object getSingleton(String name) {
        return singletonMap.get(name);
    }

    /**
     * 注册单例bean实例
     */
    protected void putSingleton(String name, Object bean) {
        singletonMap.put(name, bean);
    }

    public void registerDisposableBean(String name, Object bean, String destroyMethodName) {
        DisposableBeanAdapter disposableBeanAdapter = new DisposableBeanAdapter(name, bean, destroyMethodName);
        disposableBeanMap.put(name, disposableBeanAdapter);
    }

    public void destroySingletons() {
		disposableBeanMap.forEach(this::doDestroySingletons);
	}

	private void doDestroySingletons(String name, DisposableBeanAdapter adapter) {
		try {
			adapter.destroy();
		} catch (Exception e) {
			throw new BeanException(String.format("Destroy method on bean with name '%s' threw an exception", name), e);
		}
	}
}
