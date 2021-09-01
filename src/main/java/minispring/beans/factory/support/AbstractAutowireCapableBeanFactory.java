package minispring.beans.factory.support;

import minispring.beans.BeanException;
import minispring.beans.PropertyValue;
import minispring.beans.PropertyValues;
import minispring.beans.factory.config.AutowireCapableBeanFactory;
import minispring.beans.factory.config.BeanDefinition;
import minispring.beans.factory.config.BeanPostProcessor;
import minispring.beans.factory.config.BeanReference;
import minispring.beans.factory.strategy.CglibSubclassingInstantiationStrategy;
import minispring.beans.factory.strategy.InstantiationStrategy;
import minispring.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author lihua
 * @since 2021/8/26
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

	private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

	@Override
	protected Object createBean(String name, BeanDefinition beanDefinition, Object[] args) {
		Object bean = createBeanInstance(name, beanDefinition, args);
		applyPropertyValues(bean, beanDefinition.getPropertyValues());
		bean = initializeBean(name, bean);
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

	private Object initializeBean(String name, Object bean) {
		Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, name);
		return applyBeanPostProcessorsAfterInitialization(wrappedBean, name);
	}

	@Override
	public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeanException {
		List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
		for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
			Object beanAfterProcess = beanPostProcessor.postProcessBeforeInitialization(existingBean, beanName);
			existingBean = Optional.ofNullable(beanAfterProcess).orElse(existingBean);
		}
		return existingBean;
	}

	@Override
	public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeanException {
		List<BeanPostProcessor> beanPostProcessors = getBeanPostProcessors();
		for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
			Object beanAfterProcess = beanPostProcessor.postProcessAfterInitialization(existingBean, beanName);
			existingBean = Optional.ofNullable(beanAfterProcess).orElse(existingBean);
		}
		return existingBean;
	}
}
