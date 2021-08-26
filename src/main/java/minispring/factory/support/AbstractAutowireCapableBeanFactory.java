package minispring.factory.support;

import minispring.BeanException;
import minispring.factory.config.BeanDefinition;
import minispring.factory.strategy.CglibSubclassingInstantiationStrategy;
import minispring.factory.strategy.InstantiationStrategy;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * @author lihua
 * @since 2021/8/26
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

	private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

	@Override
	protected Object createBean(String name, BeanDefinition beanDefinition, Object[] args) {
		Object bean = createBeanInstance(name, beanDefinition, args);
		putSingleton(name, bean);
		return bean;
	}

	private Object createBeanInstance(String name, BeanDefinition beanDefinition, Object[] args) {
		try {
			Constructor constructor = getConstructor(beanDefinition, args);
			return getInstantiationStrategy().instantiate(beanDefinition, constructor, args);
		} catch (NoSuchMethodException e) {
			throw new BeanException(String.format("get declared constructor of bean %s failed", name));
		}
	}

	private Constructor getConstructor(BeanDefinition beanDefinition, Object[] args) throws NoSuchMethodException {
		Class beanClass = beanDefinition.getBeanClass();
		if (args.length == 0) {
			return beanClass.getDeclaredConstructor();
		}
		return Arrays.stream(beanClass.getDeclaredConstructors())
				.filter(constructor -> constructor.getParameterCount() == args.length)
				.findFirst()
				.orElseThrow(NoSuchMethodException::new);
	}

	public InstantiationStrategy getInstantiationStrategy() {
		return instantiationStrategy;
	}

	public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
		this.instantiationStrategy = instantiationStrategy;
	}
}
