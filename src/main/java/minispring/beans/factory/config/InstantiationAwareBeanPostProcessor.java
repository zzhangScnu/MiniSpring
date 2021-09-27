package minispring.beans.factory.config;

import minispring.beans.BeanException;

/**
 * 在bean对象实例化之前调用，是代理对象生成并返回的时机
 *
 * @author lihua
 * @since 2021/9/26
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

	/**
	 * 在bean对象实例化之前调用的方法
	 *
	 * @param beanClass 类对象
	 * @param beanName  对象的名字
	 * @return 处理后的bean对象
	 * @throws BeanException 异常
	 */
	Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException;
}
