package minispring.factory.support;

import minispring.BeanException;
import minispring.PropertyValue;
import minispring.PropertyValues;
import minispring.factory.config.BeanDefinition;
import minispring.factory.config.BeanReference;
import minispring.factory.strategy.CglibSubclassingInstantiationStrategy;
import minispring.factory.strategy.InstantiationStrategy;
import minispring.util.ReflectionUtils;

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
		applyPropertyValues(bean, beanDefinition.getPropertyValues());
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

	private void applyPropertyValues(Object bean, PropertyValues propertyValues) {
		propertyValues.getPropertyValueList()
				.forEach(propertyValue -> doApplyPropertyValue(bean, propertyValue));
	}

	private void doApplyPropertyValue(Object bean, PropertyValue propertyValue) {
		String name = propertyValue.getName();
		Object value = propertyValue.getValue();
		if (value instanceof BeanReference) {
			String beanName = ((BeanReference) value).getBeanName();
			value = getBean(beanName);
		}
		ReflectionUtils.setValue(bean, name, value);
	}

	public InstantiationStrategy getInstantiationStrategy() {
		return instantiationStrategy;
	}

	public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
		this.instantiationStrategy = instantiationStrategy;
	}
}
