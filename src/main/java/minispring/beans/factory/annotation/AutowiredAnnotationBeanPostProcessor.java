package minispring.beans.factory.annotation;

import minispring.beans.BeanException;
import minispring.beans.PropertyValues;
import minispring.beans.factory.BeanFactory;
import minispring.beans.factory.BeanFactoryAware;
import minispring.beans.factory.ConfigurableListableBeanFactory;
import minispring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import minispring.util.ClassUtils;
import minispring.util.ReflectionUtils;

import java.util.Arrays;

/**
 * @author lihua
 * @since 2021/9/30
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

	private ConfigurableListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

	/**
	 * 将bean对象里的属性，有占位符或依赖注入的处理一下
	 * 直接设置到入参bean里面去
	 */
	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeanException {
		Class<?> clazz = ClassUtils.getRealClass(bean);
		setValue(bean, clazz);
		setAutowired(bean, clazz);
		return pvs;
	}

	private void setValue(Object bean, Class<?> clazz) {
		Arrays.stream(clazz.getDeclaredFields())
				.peek(field -> field.setAccessible(true))
				.filter(field -> field.isAnnotationPresent(Value.class))
				.forEach(field -> {
					String valueWithPlaceHolder = field.getAnnotation(Value.class).value();
					String realValue = beanFactory.resolveEmbeddedValue(valueWithPlaceHolder);
					ReflectionUtils.setValue(bean, field.getName(), realValue);
				});
	}

	private void setAutowired(Object bean, Class<?> clazz) {
		Arrays.stream(clazz.getDeclaredFields())
				.peek(field -> field.setAccessible(true))
				.filter(field -> field.isAnnotationPresent(Autowired.class))
				.forEach(field -> {
					Object autowiredBean = beanFactory.getBean(field.getType());
					ReflectionUtils.setValue(bean, field.getName(), autowiredBean);
				});
	}

	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
		// 返回null，表示不走生成代理对象的分支
		return null;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		return bean;
	}
}
