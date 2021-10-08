package minispring.aop.framework.autoproxy;

import minispring.aop.AdvisedSupport;
import minispring.aop.Advisor;
import minispring.aop.Pointcut;
import minispring.aop.TargetSource;
import minispring.aop.aspectj.ExpressionPointcutAdvisor;
import minispring.aop.framework.ProxyFactory;
import minispring.beans.BeanException;
import minispring.beans.PropertyValues;
import minispring.beans.factory.BeanFactory;
import minispring.beans.factory.BeanFactoryAware;
import minispring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import minispring.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Objects;

/**
 * 代理对象的组装类
 *
 * @author lihua
 * @since 2021/9/23
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

	private DefaultListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;
	}

	/**
	 * 将目标对象，装配成代理对象
	 *
	 * @param beanClass 目标对象的类对象
	 * @param beanName  对象的名字
	 * @return 被横切的代理对象
	 * @throws BeanException 异常
	 */
	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
		// 如果是AOP相关的类，就走正常的实例化和初始化逻辑，以便在下面的流程可以从容器中取出来装配
		if (isInfrastructureClass(beanClass)) {
			return null;
		}
		return beanFactory.getBeansOfType(ExpressionPointcutAdvisor.class).values().stream()
				.map(advisor -> doForEachAdvisor(advisor, beanClass, beanName))
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);
	}

	private Object doForEachAdvisor(ExpressionPointcutAdvisor advisor, Class<?> beanClass, String beanName) {
		Pointcut pointcut = advisor.getPointcut();
		if (!pointcut.getClassFilter().matches(beanClass)) {
			return null;
		}
		AdvisedSupport advisedSupport = new AdvisedSupport();
		Object target = beanFactory.getBeanPlainly(beanName);
		advisedSupport.setTargetSource(new TargetSource(target));
		advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
		advisedSupport.setMethodMatcher(pointcut.getMethodMatcher());
		ProxyFactory aopFactory = new ProxyFactory(advisedSupport);
		return aopFactory.getProxy();
	}

	private boolean isInfrastructureClass(Class<?> beanClass) {
		return Advice.class.isAssignableFrom(beanClass)
				|| Advisor.class.isAssignableFrom(beanClass);
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		return bean;
	}

	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeanException {
		return pvs;
	}
}
