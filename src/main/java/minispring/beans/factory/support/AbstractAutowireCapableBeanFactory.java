package minispring.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import minispring.beans.BeanException;
import minispring.beans.PropertyValue;
import minispring.beans.PropertyValues;
import minispring.beans.factory.BeanClassLoaderAware;
import minispring.beans.factory.BeanFactoryAware;
import minispring.beans.factory.BeanNameAware;
import minispring.beans.factory.DisposableBean;
import minispring.beans.factory.InitializingBean;
import minispring.beans.factory.config.AutowireCapableBeanFactory;
import minispring.beans.factory.config.BeanDefinition;
import minispring.beans.factory.config.BeanPostProcessor;
import minispring.beans.factory.config.BeanReference;
import minispring.beans.factory.strategy.CglibSubclassingInstantiationStrategy;
import minispring.beans.factory.strategy.InstantiationStrategy;
import minispring.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
		setAware(name, bean);
		bean = initializeBean(name, bean, beanDefinition);
		putSingleton(name, bean, beanDefinition);
		registerDisposableBeanIfNecessary(name, bean, beanDefinition);
		return bean;
	}

	/**
	 * bean实例化
	 */
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

	/**
	 * 注入属性
	 */
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

	/**
	 * 执行初始化方法
	 */
	private Object initializeBean(String name, Object bean, BeanDefinition beanDefinition) {
		Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, name);
		invokeInitMethods(name, bean, beanDefinition);
		return applyBeanPostProcessorsAfterInitialization(wrappedBean, name);
	}

	private void invokeInitMethods(String name, Object bean, BeanDefinition beanDefinition) {
		try {
			doInvokeInitMethods(name, bean, beanDefinition);
		} catch (Exception e) {
			throw new BeanException(String.format("Invocation of init method of bean[%s] failed", name), e);
		}
	}

	private void doInvokeInitMethods(String name, Object bean, BeanDefinition beanDefinition) throws Exception {
		if (bean instanceof InitializingBean) {
			((InitializingBean) bean).afterPropertiesSet();
		}
		String initMethodName = beanDefinition.getInitMethodName();
		if (StrUtil.isBlank(initMethodName)) {
			return;
		}
		Method method = beanDefinition.getBeanClass().getMethod(initMethodName);
		if (Objects.isNull(method)) {
			throw new BeanException(String.format("Could not find an init method named '%s' on bean with name '%s'", initMethodName, name));
		}
		method.invoke(bean);
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

	/**
	 * 向虚拟机注册钩子，在容器关闭时销毁实例
	 */
	private void registerDisposableBeanIfNecessary(String name, Object bean, BeanDefinition beanDefinition) {
		if (bean instanceof DisposableBean || StrUtil.isNotBlank(beanDefinition.getDestroyMethodName())) {
			registerDisposableBean(name, bean, beanDefinition.getDestroyMethodName());
		}
	}

	/**
	 * 实现了接口的对象，拥有感知能力
	 */
	private void setAware(String name, Object bean) {
		if (bean instanceof BeanNameAware) {
			((BeanNameAware) bean).setBeanName(name);
		}
		if (bean instanceof BeanClassLoaderAware) {
			((BeanClassLoaderAware) bean).setClassLoader(getClassLoader());
		}
		if (bean instanceof BeanFactoryAware) {
			((BeanFactoryAware) bean).setBeanFactory(this);
		}
	}
}
